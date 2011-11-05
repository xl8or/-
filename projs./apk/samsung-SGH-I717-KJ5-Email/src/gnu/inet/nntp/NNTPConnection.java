package gnu.inet.nntp;

import gnu.inet.nntp.ActiveTimesIterator;
import gnu.inet.nntp.ArticleNumberIterator;
import gnu.inet.nntp.ArticleResponse;
import gnu.inet.nntp.ArticleStream;
import gnu.inet.nntp.GroupIterator;
import gnu.inet.nntp.GroupResponse;
import gnu.inet.nntp.HeaderIterator;
import gnu.inet.nntp.LineIterator;
import gnu.inet.nntp.NNTPConstants;
import gnu.inet.nntp.NNTPException;
import gnu.inet.nntp.OverviewIterator;
import gnu.inet.nntp.PairIterator;
import gnu.inet.nntp.PendingData;
import gnu.inet.nntp.PostStream;
import gnu.inet.nntp.Range;
import gnu.inet.nntp.StatusResponse;
import gnu.inet.util.CRLFInputStream;
import gnu.inet.util.CRLFOutputStream;
import gnu.inet.util.LineInputStream;
import gnu.inet.util.MessageInputStream;
import gnu.inet.util.SaslCallbackHandler;
import gnu.inet.util.SaslInputStream;
import gnu.inet.util.SaslOutputStream;
import gnu.inet.util.TraceLevel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;

public class NNTPConnection implements NNTPConstants {

   public static final int DEFAULT_PORT = 119;
   private static final String DOT = ".";
   public static final Level NNTP_TRACE = new TraceLevel("nntp");
   private static final String US_ASCII = "US-ASCII";
   public static final Logger logger = Logger.getLogger("gnu.inet.nntp");
   protected boolean canPost;
   protected String hostname;
   protected LineInputStream in;
   protected CRLFOutputStream out;
   protected PendingData pendingData;
   protected int port;
   protected Socket socket;
   protected String welcome;


   public NNTPConnection(String var1) throws IOException {
      this(var1, 119, 0, 0);
   }

   public NNTPConnection(String var1, int var2) throws IOException {
      this(var1, var2, 0, 0);
   }

   public NNTPConnection(String var1, int var2, int var3, int var4) throws IOException {
      int var5;
      if(var2 < 0) {
         var5 = 119;
      } else {
         var5 = var2;
      }

      this.hostname = var1;
      this.port = var5;
      Socket var6 = new Socket();
      this.socket = var6;
      InetSocketAddress var7 = new InetSocketAddress(var1, var5);
      if(var3 > 0) {
         this.socket.connect(var7, var3);
      } else {
         this.socket.connect(var7);
      }

      if(var4 > 0) {
         this.socket.setSoTimeout(var4);
      }

      InputStream var8 = this.socket.getInputStream();
      CRLFInputStream var9 = new CRLFInputStream(var8);
      LineInputStream var10 = new LineInputStream(var9);
      this.in = var10;
      OutputStream var11 = this.socket.getOutputStream();
      BufferedOutputStream var12 = new BufferedOutputStream(var11);
      CRLFOutputStream var13 = new CRLFOutputStream(var12);
      this.out = var13;
      String var14 = this.read();
      StatusResponse var15 = this.parseResponse(var14);
      switch(var15.status) {
      case 200:
         this.canPost = (boolean)1;
      case 201:
         String var16 = var15.getMessage();
         this.welcome = var16;
         return;
      default:
         throw new NNTPException(var15);
      }
   }

   public ArticleResponse article(int var1) throws IOException {
      String var2 = Integer.toString(var1);
      return this.articleImpl("ARTICLE", var2);
   }

   public ArticleResponse article(String var1) throws IOException {
      return this.articleImpl("ARTICLE", var1);
   }

   protected ArticleResponse articleImpl(String var1, String var2) throws IOException {
      if(var2 != null) {
         StringBuffer var3 = new StringBuffer(var1);
         StringBuffer var4 = var3.append(' ');
         var3.append(var2);
         String var6 = var3.toString();
         this.send(var6);
      } else {
         this.send(var1);
      }

      String var7 = this.read();
      StatusResponse var8 = this.parseResponse(var7);
      ArticleResponse var12;
      switch(var8.status) {
      case 220:
      case 221:
      case 222:
         var12 = (ArticleResponse)var8;
         LineInputStream var9 = this.in;
         MessageInputStream var10 = new MessageInputStream(var9);
         ArticleStream var11 = new ArticleStream(var10);
         this.pendingData = var11;
         var12.in = var11;
         break;
      case 223:
         var12 = (ArticleResponse)var8;
         break;
      default:
         throw new NNTPException(var8);
      }

      return var12;
   }

   public boolean authinfo(String var1, String var2) throws IOException {
      StringBuffer var3 = new StringBuffer("AUTHINFO USER");
      StringBuffer var4 = var3.append(' ');
      var3.append(var1);
      String var6 = var3.toString();
      this.send(var6);
      String var7 = this.read();
      StatusResponse var8 = this.parseResponse(var7);
      boolean var9;
      switch(var8.status) {
      case 281:
         var9 = true;
         return var9;
      case 381:
         var3.setLength(0);
         StringBuffer var10 = var3.append("AUTHINFO PASS");
         StringBuffer var11 = var3.append(' ');
         var3.append(var2);
         String var13 = var3.toString();
         this.send(var13);
         String var14 = this.read();
         StatusResponse var15 = this.parseResponse(var14);
         switch(var15.status) {
         case 281:
            var9 = true;
            return var9;
         case 502:
            var9 = false;
            return var9;
         default:
            throw new NNTPException(var15);
         }
      default:
         throw new NNTPException(var8);
      }
   }

   public boolean authinfoGeneric(String var1, String var2, String var3) throws IOException {
      String[] var4 = new String[]{var1};
      SaslCallbackHandler var5 = new SaslCallbackHandler(var2, var3);
      HashMap var6 = new HashMap();
      var6.put("gnu.crypto.sasl.username", var2);
      var6.put("gnu.crypto.sasl.password", var3);
      String var9 = this.socket.getInetAddress().getHostName();
      SaslClient var10 = Sasl.createSaslClient(var4, (String)null, "smtp", var9, var6, var5);
      boolean var11;
      if(var10 == null) {
         var11 = false;
      } else {
         StringBuffer var12 = new StringBuffer("AUTHINFO GENERIC");
         StringBuffer var13 = var12.append(' ');
         var12.append(var1);
         if(var10.hasInitialResponse()) {
            StringBuffer var15 = var12.append(' ');
            byte[] var16 = new byte[0];
            byte[] var17 = var10.evaluateChallenge(var16);
            String var18 = new String(var17, "US-ASCII");
            var12.append(var18);
         }

         String var20 = var12.toString();
         this.send(var20);
         String var21 = this.read();
         StatusResponse var22 = this.parseResponse(var21);
         switch(var22.status) {
         case 281:
            String var23 = (String)var10.getNegotiatedProperty("javax.security.sasl.qop");
            if("auth-int".equalsIgnoreCase(var23) || "auth-conf".equalsIgnoreCase(var23)) {
               InputStream var24 = this.socket.getInputStream();
               BufferedInputStream var25 = new BufferedInputStream(var24);
               SaslInputStream var26 = new SaslInputStream(var10, var25);
               CRLFInputStream var27 = new CRLFInputStream(var26);
               LineInputStream var28 = new LineInputStream(var27);
               this.in = var28;
               OutputStream var29 = this.socket.getOutputStream();
               BufferedOutputStream var30 = new BufferedOutputStream(var29);
               SaslOutputStream var31 = new SaslOutputStream(var10, var30);
               CRLFOutputStream var32 = new CRLFOutputStream(var31);
               this.out = var32;
            }

            var11 = true;
            break;
         case 502:
            var11 = false;
            break;
         default:
            throw new NNTPException(var22);
         }
      }

      return var11;
   }

   public boolean authinfoSimple(String var1, String var2) throws IOException {
      this.send("AUTHINFO SIMPLE");
      String var3 = this.read();
      StatusResponse var4 = this.parseResponse(var3);
      switch(var4.status) {
      case 350:
         StringBuffer var5 = new StringBuffer(var1);
         StringBuffer var6 = var5.append(' ');
         var5.append(var2);
         String var8 = var5.toString();
         this.send(var8);
         String var9 = this.read();
         StatusResponse var10 = this.parseResponse(var9);
         boolean var11;
         switch(var10.status) {
         case 350:
            var11 = true;
            break;
         case 452:
            var11 = false;
            break;
         default:
            throw new NNTPException(var10);
         }

         return var11;
      default:
         throw new NNTPException(var4);
      }
   }

   public ArticleResponse body(int var1) throws IOException {
      String var2 = Integer.toString(var1);
      return this.articleImpl("BODY", var2);
   }

   public ArticleResponse body(String var1) throws IOException {
      return this.articleImpl("BODY", var1);
   }

   public boolean check(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer("CHECK");
      StringBuffer var3 = var2.append(' ');
      var2.append(var1);
      String var5 = var2.toString();
      this.send(var5);
      String var6 = this.read();
      StatusResponse var7 = this.parseResponse(var6);
      boolean var8;
      switch(var7.status) {
      case 238:
         var8 = true;
         break;
      case 438:
         var8 = false;
         break;
      default:
         throw new NNTPException(var7);
      }

      return var8;
   }

   public Date date() throws IOException {
      this.send("DATE");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 111:
         String var3 = var2.getMessage();

         try {
            Date var4 = (new SimpleDateFormat("yyyyMMddHHmmss")).parse(var3);
            return var4;
         } catch (ParseException var7) {
            String var6 = "Invalid date: " + var3;
            throw new IOException(var6);
         }
      default:
         throw new NNTPException(var2);
      }
   }

   String formatDate(Date var1) {
      SimpleDateFormat var2 = new SimpleDateFormat("yyMMdd HHmmss \'GMT\'");
      GregorianCalendar var3 = new GregorianCalendar();
      TimeZone var4 = TimeZone.getTimeZone("GMT");
      var3.setTimeZone(var4);
      var2.setCalendar(var3);
      var3.setTime(var1);
      return var2.format(var1);
   }

   public String getWelcome() {
      return this.welcome;
   }

   public GroupResponse group(String var1) throws IOException {
      String var2 = "GROUP " + var1;
      this.send(var2);
      String var3 = this.read();
      StatusResponse var4 = this.parseResponse(var3);
      switch(var4.status) {
      case 211:
         return (GroupResponse)var4;
      default:
         throw new NNTPException(var4);
      }
   }

   public ArticleResponse head(int var1) throws IOException {
      String var2 = Integer.toString(var1);
      return this.articleImpl("HEAD", var2);
   }

   public ArticleResponse head(String var1) throws IOException {
      return this.articleImpl("HEAD", var1);
   }

   public LineIterator help() throws IOException {
      this.send("HELP");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 100:
         LineIterator var3 = new LineIterator(this);
         this.pendingData = var3;
         return var3;
      default:
         throw new NNTPException(var2);
      }
   }

   public PostStream ihave(String var1) throws IOException {
      String var2 = "IHAVE " + var1;
      this.send(var2);
      String var3 = this.read();
      StatusResponse var4 = this.parseResponse(var3);
      PostStream var5;
      switch(var4.status) {
      case 335:
         var5 = new PostStream(this, (boolean)0);
         break;
      case 435:
         var5 = null;
         break;
      default:
         throw new NNTPException(var4);
      }

      return var5;
   }

   public ArticleResponse last() throws IOException {
      return this.articleImpl("LAST", (String)null);
   }

   public GroupIterator list() throws IOException {
      return this.listImpl("LIST");
   }

   public GroupIterator listActive(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer("LIST ACTIVE");
      if(var1 != null) {
         StringBuffer var3 = var2.append(' ');
         var2.append(var1);
      }

      String var5 = var2.toString();
      return this.listImpl(var5);
   }

   public ActiveTimesIterator listActiveTimes() throws IOException {
      this.send("LIST ACTIVE.TIMES");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 215:
         return new ActiveTimesIterator(this);
      default:
         throw new NNTPException(var2);
      }
   }

   public ArticleNumberIterator listGroup(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer("LISTGROUP");
      if(var1 != null) {
         StringBuffer var3 = var2.append(' ');
         var2.append(var1);
      }

      String var5 = var2.toString();
      this.send(var5);
      String var6 = this.read();
      StatusResponse var7 = this.parseResponse(var6, (boolean)1);
      switch(var7.status) {
      case 211:
         ArticleNumberIterator var8 = new ArticleNumberIterator(this);
         this.pendingData = var8;
         return var8;
      default:
         throw new NNTPException(var7);
      }
   }

   GroupIterator listImpl(String var1) throws IOException {
      this.send(var1);
      String var2 = this.read();
      StatusResponse var3 = this.parseResponse(var2);
      switch(var3.status) {
      case 215:
         GroupIterator var4 = new GroupIterator(this);
         this.pendingData = var4;
         return var4;
      default:
         throw new NNTPException(var3);
      }
   }

   public PairIterator listNewsgroups(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer("LIST NEWSGROUPS");
      if(var1 != null) {
         StringBuffer var3 = var2.append(' ');
         var2.append(var1);
      }

      String var5 = var2.toString();
      this.send(var5);
      String var6 = this.read();
      StatusResponse var7 = this.parseResponse(var6);
      switch(var7.status) {
      case 215:
         PairIterator var8 = new PairIterator(this);
         this.pendingData = var8;
         return var8;
      default:
         throw new NNTPException(var7);
      }
   }

   public LineIterator listOverviewFmt() throws IOException {
      this.send("LIST OVERVIEW.FMT");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 215:
         LineIterator var3 = new LineIterator(this);
         this.pendingData = var3;
         return var3;
      default:
         throw new NNTPException(var2);
      }
   }

   public GroupIterator listSubscriptions() throws IOException {
      return this.listImpl("LIST SUBSCRIPTIONS");
   }

   public boolean modeReader() throws IOException {
      this.send("MODE READER");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      boolean var3;
      switch(var2.status) {
      case 200:
         this.canPost = (boolean)1;
         var3 = this.canPost;
         break;
      case 440:
         this.canPost = (boolean)0;
         var3 = this.canPost;
         break;
      default:
         throw new NNTPException(var2);
      }

      return var3;
   }

   public boolean modeStream() throws IOException {
      this.send("MODE STREAM");
      String var1 = this.read();
      boolean var2;
      switch(this.parseResponse(var1).status) {
      case 203:
         var2 = true;
         break;
      default:
         var2 = false;
      }

      return var2;
   }

   public LineIterator newGroups(Date var1, String[] var2) throws IOException {
      StringBuffer var3 = new StringBuffer("NEWGROUPS");
      StringBuffer var4 = var3.append(' ');
      String var5 = this.formatDate(var1);
      var3.append(var5);
      if(var2 != null) {
         StringBuffer var7 = var3.append(' ');
         int var8 = 0;

         while(true) {
            int var9 = var2.length;
            if(var8 >= var9) {
               break;
            }

            if(var8 > 0) {
               StringBuffer var10 = var3.append(',');
            }

            String var11 = var2[var8];
            var3.append(var11);
            ++var8;
         }
      }

      String var13 = var3.toString();
      this.send(var13);
      String var14 = this.read();
      StatusResponse var15 = this.parseResponse(var14);
      switch(var15.status) {
      case 231:
         LineIterator var16 = new LineIterator(this);
         this.pendingData = var16;
         return var16;
      default:
         throw new NNTPException(var15);
      }
   }

   public LineIterator newNews(String var1, Date var2, String[] var3) throws IOException {
      StringBuffer var4 = new StringBuffer("NEWNEWS");
      StringBuffer var5 = var4.append(' ');
      var4.append(var1);
      StringBuffer var7 = var4.append(' ');
      String var8 = this.formatDate(var2);
      var4.append(var8);
      if(var3 != null) {
         StringBuffer var10 = var4.append(' ');
         int var11 = 0;

         while(true) {
            int var12 = var3.length;
            if(var11 >= var12) {
               break;
            }

            if(var11 > 0) {
               StringBuffer var13 = var4.append(',');
            }

            String var14 = var3[var11];
            var4.append(var14);
            ++var11;
         }
      }

      String var16 = var4.toString();
      this.send(var16);
      String var17 = this.read();
      StatusResponse var18 = this.parseResponse(var17);
      switch(var18.status) {
      case 230:
         LineIterator var19 = new LineIterator(this);
         this.pendingData = var19;
         return var19;
      default:
         throw new NNTPException(var18);
      }
   }

   public ArticleResponse next() throws IOException {
      return this.articleImpl("NEXT", (String)null);
   }

   Date parseDate(String var1) throws ParseException {
      return (new SimpleDateFormat("yyMMdd HHmmss \'GMT\'")).parse(var1);
   }

   protected StatusResponse parseResponse(String var1) throws ProtocolException {
      return this.parseResponse(var1, (boolean)0);
   }

   protected StatusResponse parseResponse(String var1, boolean var2) throws ProtocolException {
      if(var1 == null) {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.hostname;
         String var5 = var3.append(var4).append(" closed connection").toString();
         throw new ProtocolException(var5);
      } else {
         byte var6 = 0;
         Object var7 = null;
         int var8 = var1.indexOf(32, var6);
         String var12;
         String var13;
         if(var8 > var6) {
            String var9 = var1.substring(var6, var8);
            int var10 = var8 + 1;
            String var11 = var1.substring(var10);
            var12 = var9;
            var13 = var11;
         } else {
            var13 = (String)var7;
            var12 = var1;
         }

         short var14;
         try {
            var14 = Short.parseShort(var12);
         } catch (NumberFormatException var32) {
            throw new ProtocolException(var1);
         }

         short var34 = var14;
         Object var33;
         switch(var14) {
         case 211:
         case 220:
         case 221:
         case 222:
         case 223:
            if(var14 == 211 && !var2) {
               GroupResponse var35 = new GroupResponse(var14, var13);
               int var22 = var8 + 1;
               byte var23 = 32;

               try {
                  int var36 = var1.indexOf(var23, var22);
                  if(var36 > var22) {
                     int var24 = Integer.parseInt(var1.substring(var22, var36));
                     var35.count = var24;
                  }

                  var22 = var36 + 1;
                  var36 = var1.indexOf(32, var22);
                  if(var36 > var22) {
                     int var25 = Integer.parseInt(var1.substring(var22, var36));
                     var35.first = var25;
                  }

                  var22 = var36 + 1;
                  var36 = var1.indexOf(32, var22);
                  if(var36 > var22) {
                     int var26 = Integer.parseInt(var1.substring(var22, var36));
                     var35.last = var26;
                  }

                  var22 = var36 + 1;
                  var36 = var1.indexOf(32, var22);
                  if(var36 > var22) {
                     String var27 = var1.substring(var22, var36);
                     var35.group = var27;
                  } else {
                     String var28 = var1.substring(var22);
                     var35.group = var28;
                  }

                  var33 = var35;
               } catch (NumberFormatException var30) {
                  throw new ProtocolException(var1);
               }
            } else {
               try {
                  ArticleResponse var16 = new ArticleResponse(var34, var13);
                  ++var8;
                  int var17 = var1.indexOf(32, var8);
                  if(var17 > var8) {
                     int var18 = Integer.parseInt(var1.substring(var8, var17));
                     var16.articleNumber = var18;
                  }

                  var8 = var17 + 1;
                  var17 = var1.indexOf(32, var8);
                  if(var17 > var8) {
                     String var19 = var1.substring(var8, var17);
                     var16.messageId = var19;
                  } else {
                     String var20 = var1.substring(var8);
                     var16.messageId = var20;
                  }

                  var33 = var16;
               } catch (NumberFormatException var31) {
                  var33 = new StatusResponse(var14, var13);
               }
            }
            break;
         default:
            var33 = new StatusResponse(var14, var13);
         }

         return (StatusResponse)var33;
      }
   }

   public OutputStream post() throws IOException {
      this.send("POST");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 340:
         return new PostStream(this, (boolean)0);
      default:
         throw new NNTPException(var2);
      }
   }

   void postComplete() throws IOException {
      this.send(".");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 235:
      case 240:
         return;
      default:
         throw new NNTPException(var2);
      }
   }

   public void quit() throws IOException {
      this.send("QUIT");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 205:
         this.socket.close();
         return;
      default:
         throw new NNTPException(var2);
      }
   }

   protected String read() throws IOException {
      String var1 = this.in.readLine();
      if(var1 == null) {
         Logger var2 = logger;
         Level var3 = NNTP_TRACE;
         var2.log(var3, "<EOF");
      } else {
         Logger var4 = logger;
         Level var5 = NNTP_TRACE;
         String var6 = "<" + var1;
         var4.log(var5, var6);
      }

      return var1;
   }

   protected void send(String var1) throws IOException {
      if(this.pendingData != null) {
         this.pendingData.readToEOF();
         this.pendingData = null;
      }

      Logger var2 = logger;
      Level var3 = NNTP_TRACE;
      String var4 = ">" + var1;
      var2.log(var3, var4);
      byte[] var5 = var1.getBytes("US-ASCII");
      this.out.write(var5);
      this.out.writeln();
      this.out.flush();
   }

   public void slave() throws IOException {
      this.send("SLAVE");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 202:
         return;
      default:
         throw new NNTPException(var2);
      }
   }

   public ArticleResponse stat(int var1) throws IOException {
      String var2 = Integer.toString(var1);
      return this.articleImpl("STAT", var2);
   }

   public ArticleResponse stat(String var1) throws IOException {
      return this.articleImpl("STAT", var1);
   }

   public OutputStream takethis(String var1) throws IOException {
      String var2 = "TAKETHIS " + var1;
      this.send(var2);
      return new PostStream(this, (boolean)1);
   }

   void takethisComplete() throws IOException {
      this.send(".");
      String var1 = this.read();
      StatusResponse var2 = this.parseResponse(var1);
      switch(var2.status) {
      case 239:
         return;
      default:
         throw new NNTPException(var2);
      }
   }

   public PairIterator xgtitle(String var1) throws IOException {
      StringBuffer var2 = new StringBuffer("XGTITLE");
      if(var1 != null) {
         StringBuffer var3 = var2.append(' ');
         var2.append(var1);
      }

      String var5 = var2.toString();
      this.send(var5);
      String var6 = this.read();
      StatusResponse var7 = this.parseResponse(var6);
      switch(var7.status) {
      case 481:
         PairIterator var8 = new PairIterator(this);
         this.pendingData = var8;
         return var8;
      default:
         throw new NNTPException(var7);
      }
   }

   public HeaderIterator xhdr(String var1, String var2) throws IOException {
      StringBuffer var3 = new StringBuffer("XHDR");
      StringBuffer var4 = var3.append(' ');
      var3.append(var1);
      if(var2 != null) {
         StringBuffer var6 = var3.append(' ');
         var3.append(var2);
      }

      String var8 = var3.toString();
      this.send(var8);
      String var9 = this.read();
      StatusResponse var10 = this.parseResponse(var9);
      switch(var10.status) {
      case 221:
         HeaderIterator var11 = new HeaderIterator(this);
         this.pendingData = var11;
         return var11;
      default:
         throw new NNTPException(var10);
      }
   }

   public OverviewIterator xover(Range var1) throws IOException {
      StringBuffer var2 = new StringBuffer("XOVER");
      if(var1 != null) {
         StringBuffer var3 = var2.append(' ');
         String var4 = var1.toString();
         var2.append(var4);
      }

      String var6 = var2.toString();
      this.send(var6);
      String var7 = this.read();
      StatusResponse var8 = this.parseResponse(var7);
      switch(var8.status) {
      case 224:
         OverviewIterator var9 = new OverviewIterator(this);
         this.pendingData = var9;
         return var9;
      default:
         throw new NNTPException(var8);
      }
   }
}
