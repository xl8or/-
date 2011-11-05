package gnu.inet.imap;

import gnu.inet.imap.IMAPConstants;
import gnu.inet.imap.IMAPException;
import gnu.inet.imap.IMAPResponse;
import gnu.inet.imap.IMAPResponseTokenizer;
import gnu.inet.imap.ListEntry;
import gnu.inet.imap.MailboxStatus;
import gnu.inet.imap.MessageStatus;
import gnu.inet.imap.Namespaces;
import gnu.inet.imap.Quota;
import gnu.inet.imap.UTF7imap;
import gnu.inet.util.CRLFOutputStream;
import gnu.inet.util.EmptyX509TrustManager;
import gnu.inet.util.TraceLevel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class IMAPConnection implements IMAPConstants {

   protected static final int DEFAULT_PORT = 143;
   protected static final int DEFAULT_SSL_PORT = 993;
   public static final Level IMAP_TRACE = new TraceLevel("imap");
   protected static final String TAG_PREFIX = "A";
   protected static final String US_ASCII = "US-ASCII";
   public static final Logger logger = Logger.getLogger("gnu.inet.imap");
   private List alerts;
   private boolean ansiDebug;
   protected List asyncResponses;
   protected IMAPResponseTokenizer in;
   protected CRLFOutputStream out;
   protected Socket socket;
   private int tagIndex;


   public IMAPConnection(String var1) throws UnknownHostException, IOException {
      byte var4 = 0;
      byte var5 = 0;
      this(var1, -1, 0, var4, (boolean)var5, (TrustManager)null);
   }

   public IMAPConnection(String var1, int var2) throws UnknownHostException, IOException {
      byte var6 = 0;
      byte var7 = 0;
      this(var1, var2, 0, var6, (boolean)var7, (TrustManager)null);
   }

   public IMAPConnection(String var1, int var2, int var3, int var4) throws UnknownHostException, IOException {
      this(var1, var2, var3, var4, (boolean)0, (TrustManager)null);
   }

   public IMAPConnection(String var1, int var2, int var3, int var4, boolean var5, TrustManager var6) throws UnknownHostException, IOException {
      this.tagIndex = 0;
      this.ansiDebug = (boolean)0;
      int var7;
      if(var2 < 0) {
         if(var5) {
            var7 = 993;
         } else {
            var7 = 143;
         }
      } else {
         var7 = var2;
      }

      try {
         Socket var8 = new Socket();
         this.socket = var8;
         InetSocketAddress var9 = new InetSocketAddress(var1, var7);
         if(var3 > 0) {
            this.socket.connect(var9, var3);
         } else {
            this.socket.connect(var9);
         }

         if(var4 > 0) {
            this.socket.setSoTimeout(var4);
         }

         if(var5) {
            SSLSocketFactory var10 = this.getSSLSocketFactory(var6);
            Socket var11 = this.socket;
            SSLSocket var12 = (SSLSocket)var10.createSocket(var11, var1, var7, (boolean)1);
            String[] var13 = new String[]{"TLSv1", "SSLv3"};
            var12.setEnabledProtocols(var13);
            var12.setUseClientMode((boolean)1);
            var12.startHandshake();
            this.socket = var12;
         }
      } catch (GeneralSecurityException var25) {
         IOException var23 = new IOException();
         Throwable var24 = var23.initCause(var25);
         throw var23;
      }

      InputStream var14 = this.socket.getInputStream();
      BufferedInputStream var15 = new BufferedInputStream(var14);
      IMAPResponseTokenizer var16 = new IMAPResponseTokenizer(var15);
      this.in = var16;
      OutputStream var17 = this.socket.getOutputStream();
      BufferedOutputStream var18 = new BufferedOutputStream(var17);
      CRLFOutputStream var19 = new CRLFOutputStream(var18);
      this.out = var19;
      ArrayList var20 = new ArrayList();
      this.asyncResponses = var20;
      ArrayList var21 = new ArrayList();
      this.alerts = var21;
   }

   public IMAPConnection(String var1, int var2, TrustManager var3) throws UnknownHostException, IOException {
      byte var7 = 0;
      this(var1, var2, 0, var7, (boolean)1, var3);
   }

   private void addTokens(List var1, String var2) {
      int var3 = var2.indexOf(32);
      int var4 = 0;

      int var9;
      for(int var5 = var3; var5 != -1; var5 = var9) {
         String var6 = var2.substring(var4, var5);
         if(!var1.contains(var6)) {
            var1.add(var6);
         }

         int var8 = var5 + 1;
         var9 = var2.indexOf(32, var8);
         var4 = var8;
      }

      String var10 = var2.substring(var4);
      if(var10.length() > 0) {
         if(!var1.contains(var10)) {
            var1.add(var10);
         }
      }
   }

   private MessageStatus[] fetchImpl(String var1, String var2, String[] var3) throws IOException {
      String var4 = this.newTag();
      StringBuffer var5 = new StringBuffer(var1);
      StringBuffer var6 = var5.append(' ');
      var5.append(var2);
      StringBuffer var8 = var5.append(' ');
      StringBuffer var9 = var5.append('(');
      int var10 = 0;

      while(true) {
         int var11 = var3.length;
         if(var10 >= var11) {
            StringBuffer var15 = var5.append(')');
            String var16 = var5.toString();
            this.sendCommand(var4, var16);
            ArrayList var17 = new ArrayList();

            while(true) {
               IMAPResponse var18 = this.readResponse();
               String var19 = var18.getID();
               if(!var18.isUntagged()) {
                  String var25 = var18.getTag();
                  if(var4.equals(var25)) {
                     this.processAlerts(var18);
                     if(var19 == "OK") {
                        MessageStatus[] var26 = new MessageStatus[var17.size()];
                        var17.toArray(var26);
                        return var26;
                     }

                     String var28 = var18.getText();
                     throw new IMAPException(var19, var28);
                  }

                  String var29 = var18.getText();
                  throw new IMAPException(var19, var29);
               }

               if(var19 == "FETCH") {
                  int var20 = var18.getCount();
                  List var21 = var18.getResponseCode();
                  MessageStatus var22 = new MessageStatus(var20, var21);
                  var17.add(var22);
               } else {
                  this.asyncResponses.add(var18);
               }
            }
         }

         if(var10 > 0) {
            StringBuffer var12 = var5.append(' ');
         }

         String var13 = var3[var10];
         var5.append(var13);
         ++var10;
      }
   }

   private List parseACL(String var1, int var2) {
      int var3 = var1.length();
      ArrayList var4 = new ArrayList();
      StringBuffer var5 = new StringBuffer();
      int var6 = 0;

      for(boolean var7 = false; var6 < var3; ++var6) {
         char var8 = var1.charAt(var6);
         switch(var8) {
         case 32:
            if(!var7 && var4.size() <= var2) {
               String var12 = UTF7imap.decode(var5.toString());
               var4.add(var12);
               var5.setLength(0);
            } else {
               var5.append(var8);
            }
            break;
         case 33:
         default:
            var5.append(var8);
            break;
         case 34:
            if(var7) {
               boolean var10 = false;
            }
         }
      }

      String var14 = var5.toString();
      var4.add(var14);
      return var4;
   }

   private void processAlerts(IMAPResponse var1) {
      List var2 = var1.getResponseCode();
      if(var2 != null) {
         if(var2.contains("ALERT")) {
            List var3 = this.alerts;
            String var4 = var1.getText();
            var3.add(var4);
         }
      }
   }

   static String quote(String var0) {
      String var5;
      if(var0.length() != 0 && var0.indexOf(32) == -1) {
         var5 = var0;
      } else {
         StringBuffer var1 = new StringBuffer();
         StringBuffer var2 = var1.append('\"');
         var1.append(var0);
         StringBuffer var4 = var1.append('\"');
         var5 = var1.toString();
      }

      return var5;
   }

   private String rightsToString(int var1) {
      StringBuffer var2 = new StringBuffer();
      if((var1 & 1) != 0) {
         StringBuffer var3 = var2.append('l');
      }

      if((var1 & 2) != 0) {
         StringBuffer var4 = var2.append('r');
      }

      if((var1 & 4) != 0) {
         StringBuffer var5 = var2.append('s');
      }

      if((var1 & 8) != 0) {
         StringBuffer var6 = var2.append('w');
      }

      if((var1 & 16) != 0) {
         StringBuffer var7 = var2.append('i');
      }

      if((var1 & 32) != 0) {
         StringBuffer var8 = var2.append('p');
      }

      if((var1 & 64) != 0) {
         StringBuffer var9 = var2.append('c');
      }

      if((var1 & 128) != 0) {
         StringBuffer var10 = var2.append('d');
      }

      if((var1 & 256) != 0) {
         StringBuffer var11 = var2.append('a');
      }

      return var2.toString();
   }

   private MessageStatus[] storeImpl(String var1, String var2, String var3, String[] var4) throws IOException {
      char var5 = 32;
      String var6 = this.newTag();
      StringBuffer var7 = new StringBuffer(var1);
      var7.append(var5);
      var7.append(var2);
      var7.append(var5);
      var7.append(var3);
      var7.append(var5);
      StringBuffer var13 = var7.append('(');
      int var14 = 0;

      while(true) {
         int var15 = var4.length;
         if(var14 >= var15) {
            StringBuffer var19 = var7.append(')');
            String var20 = var7.toString();
            this.sendCommand(var6, var20);
            ArrayList var21 = new ArrayList();

            while(true) {
               IMAPResponse var22 = this.readResponse();
               String var23 = var22.getID();
               if(!var22.isUntagged()) {
                  String var33 = var22.getTag();
                  if(var6.equals(var33)) {
                     this.processAlerts(var22);
                     if(var23 == "OK") {
                        MessageStatus[] var34 = new MessageStatus[var21.size()];
                        var21.toArray(var34);
                        return var34;
                     }

                     String var36 = var22.getText();
                     throw new IMAPException(var23, var36);
                  }

                  String var37 = var22.getText();
                  throw new IMAPException(var23, var37);
               }

               int var38 = var22.getCount();
               List var24 = var22.getResponseCode();
               if(var23 == "FETCH") {
                  MessageStatus var25 = new MessageStatus(var38, var24);
                  var21.add(var25);
               } else if(var23 == "FETCH FLAGS") {
                  ArrayList var27 = new ArrayList();
                  boolean var28 = var27.add("FLAGS");
                  var27.add(var24);
                  MessageStatus var30 = new MessageStatus(var38, var27);
                  var21.add(var30);
               } else {
                  this.asyncResponses.add(var22);
               }
            }
         }

         if(var14 > 0) {
            StringBuffer var16 = var7.append(' ');
         }

         String var17 = var4[var14];
         var7.append(var17);
         ++var14;
      }
   }

   private int stringToRights(String var1) {
      int var2 = 0;
      int var3 = var1.length();

      int var4;
      for(var4 = var2; var2 < var3; ++var2) {
         switch(var1.charAt(var2)) {
         case 97:
            var4 |= 256;
            break;
         case 99:
            var4 |= 64;
            break;
         case 100:
            var4 |= 128;
            break;
         case 105:
            var4 |= 16;
            break;
         case 108:
            var4 |= 1;
            break;
         case 112:
            var4 |= 32;
            break;
         case 114:
            var4 |= 2;
            break;
         case 115:
            var4 |= 4;
            break;
         case 119:
            var4 |= 8;
         }
      }

      return var4;
   }

   static String stripQuotes(String var0) {
      String var4;
      if(var0.charAt(0) == 34) {
         int var1 = var0.length();
         int var2 = var1 - 1;
         if(var0.charAt(var2) == 34) {
            int var3 = var1 - 1;
            var4 = var0.substring(1, var3);
            return var4;
         }
      }

      var4 = var0;
      return var4;
   }

   public boolean alertsPending() {
      boolean var1;
      if(this.alerts.size() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean append(String var1, String[] var2, byte[] var3) throws IOException {
      String var4 = this.newTag();
      StringBuffer var5 = (new StringBuffer("APPEND")).append(' ');
      String var6 = quote(UTF7imap.encode(var1));
      StringBuffer var7 = var5.append(var6).append(' ');
      if(var2 != null) {
         StringBuffer var8 = var7.append('(');
         int var9 = 0;

         while(true) {
            int var10 = var2.length;
            if(var9 >= var10) {
               StringBuffer var14 = var7.append(')');
               StringBuffer var15 = var7.append(' ');
               break;
            }

            if(var9 > 0) {
               StringBuffer var11 = var7.append(' ');
            }

            String var12 = var2[var9];
            var7.append(var12);
            ++var9;
         }
      }

      StringBuffer var16 = var7.append('{');
      int var17 = var3.length;
      var7.append(var17);
      StringBuffer var19 = var7.append('}');
      String var20 = var7.toString();
      this.sendCommand(var4, var20);
      IMAPResponse var21 = this.readResponse();
      if(!var21.isContinuation()) {
         String var22 = var21.getID();
         String var23 = var21.getText();
         throw new IMAPException(var22, var23);
      } else {
         this.out.write(var3);
         this.out.writeln();
         this.out.flush();

         while(true) {
            IMAPResponse var24 = this.readResponse();
            String var25 = var24.getID();
            String var26 = var24.getTag();
            if(var4.equals(var26)) {
               this.processAlerts(var24);
               boolean var27;
               if(var25 == "OK") {
                  var27 = true;
               } else {
                  if(var25 != "NO") {
                     String var28 = var24.getText();
                     throw new IMAPException(var25, var28);
                  }

                  var27 = false;
               }

               return var27;
            }

            if(!var24.isUntagged()) {
               String var30 = var24.getText();
               throw new IMAPException(var25, var30);
            }

            this.asyncResponses.add(var24);
         }
      }
   }

   public boolean authenticate(String param1, String param2, String param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public List capability() throws IOException {
      String var1 = this.newTag();
      this.sendCommand(var1, "CAPABILITY");
      ArrayList var2 = new ArrayList();

      label52:
      while(true) {
         IMAPResponse var3 = this.readResponse();
         String var4 = var3.getID();
         String var5 = var3.getTag();
         if(var1.equals(var5)) {
            this.processAlerts(var3);
            if(var4 == "OK") {
               if(var2.size() == 0) {
                  String var6 = var3.getText();
                  this.addTokens(var2, var6);
               }

               return var2;
            } else {
               String var7 = var3.getText();
               throw new IMAPException(var4, var7);
            }
         }

         if(!var3.isUntagged()) {
            String var16 = var3.getText();
            throw new IMAPException(var4, var16);
         }

         if(var4 == "CAPABILITY") {
            String var8 = var3.getText();
            this.addTokens(var2, var8);
         } else if(var4 == "OK") {
            List var17 = var3.getResponseCode();
            int var9;
            if(var17 == null) {
               var9 = 0;
            } else {
               var9 = var17.size();
            }

            if(var9 > 0) {
               Object var10 = var17.get(0);
               if("CAPABILITY".equals(var10)) {
                  int var11 = 1;

                  while(true) {
                     if(var11 >= var9) {
                        continue label52;
                     }

                     String var12 = (String)var17.get(var11);
                     if(!var2.contains(var12)) {
                        var2.add(var12);
                     }

                     ++var11;
                  }
               }
            }

            this.asyncResponses.add(var3);
         } else {
            this.asyncResponses.add(var3);
         }
      }
   }

   public void check() throws IOException {
      boolean var1 = this.invokeSimpleCommand("CHECK");
   }

   public boolean close() throws IOException {
      return this.invokeSimpleCommand("CLOSE");
   }

   public boolean copy(int[] var1, String var2) throws IOException {
      boolean var3;
      if(var1 != null && var1.length >= 1) {
         StringBuffer var4 = (new StringBuffer("COPY")).append(' ');
         int var5 = 0;

         while(true) {
            int var6 = var1.length;
            if(var5 >= var6) {
               StringBuffer var10 = var4.append(' ');
               String var11 = quote(UTF7imap.encode(var2));
               var10.append(var11);
               String var13 = var4.toString();
               var3 = this.invokeSimpleCommand(var13);
               break;
            }

            if(var5 > 0) {
               StringBuffer var7 = var4.append(',');
            }

            int var8 = var1[var5];
            var4.append(var8);
            ++var5;
         }
      } else {
         var3 = true;
      }

      return var3;
   }

   public boolean create(String var1) throws IOException {
      StringBuilder var2 = (new StringBuilder()).append("CREATE ");
      String var3 = quote(UTF7imap.encode(var1));
      String var4 = var2.append(var3).toString();
      return this.invokeSimpleCommand(var4);
   }

   public boolean delete(String var1) throws IOException {
      StringBuilder var2 = (new StringBuilder()).append("DELETE ");
      String var3 = quote(UTF7imap.encode(var1));
      String var4 = var2.append(var3).toString();
      return this.invokeSimpleCommand(var4);
   }

   public boolean deleteacl(String var1, String var2) throws IOException {
      StringBuilder var3 = (new StringBuilder()).append("DELETEACL ");
      String var4 = quote(UTF7imap.encode(var1));
      StringBuilder var5 = var3.append(var4).append(' ');
      String var6 = UTF7imap.encode(var2);
      String var7 = var5.append(var6).toString();
      return this.invokeSimpleCommand(var7);
   }

   public MailboxStatus examine(String var1) throws IOException {
      return this.selectImpl(var1, "EXAMINE");
   }

   public int[] expunge() throws IOException {
      String var1 = this.newTag();
      this.sendCommand(var1, "EXPUNGE");
      ArrayList var2 = new ArrayList();

      while(true) {
         IMAPResponse var3 = this.readResponse();
         String var4 = var3.getID();
         if(!var3.isUntagged()) {
            String var9 = var3.getTag();
            if(!var1.equals(var9)) {
               String var15 = var3.getText();
               throw new IMAPException(var4, var15);
            } else {
               this.processAlerts(var3);
               if(var4 != "OK") {
                  String var14 = var3.getText();
                  throw new IMAPException(var4, var14);
               } else {
                  int var10 = var2.size();
                  int[] var11 = new int[var10];

                  for(int var12 = 0; var12 < var10; ++var12) {
                     int var13 = ((Integer)var2.get(var12)).intValue();
                     var11[var12] = var13;
                  }

                  return var11;
               }
            }
         }

         if(var4 == "EXPUNGE") {
            int var5 = var3.getCount();
            Integer var6 = new Integer(var5);
            var2.add(var6);
         } else {
            this.asyncResponses.add(var3);
         }
      }
   }

   public MessageStatus fetch(int var1, String[] var2) throws IOException {
      String var3;
      if(var1 == -1) {
         var3 = "*";
      } else {
         var3 = Integer.toString(var1);
      }

      return this.fetchImpl("FETCH", var3, var2)[0];
   }

   public MessageStatus[] fetch(int var1, int var2, String[] var3) throws IOException {
      StringBuffer var4 = new StringBuffer();
      int var5;
      if(var1 == -1) {
         var5 = 42;
      } else {
         var5 = var1;
      }

      var4.append(var5);
      StringBuffer var7 = var4.append(':');
      int var8;
      if(var2 == -1) {
         var8 = 42;
      } else {
         var8 = var2;
      }

      var4.append(var8);
      String var10 = var4.toString();
      return this.fetchImpl("FETCH", var10, var3);
   }

   public MessageStatus[] fetch(int[] var1, String[] var2) throws IOException {
      StringBuffer var3 = new StringBuffer();
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            String var9 = var3.toString();
            return this.fetchImpl("FETCH", var9, var2);
         }

         if(var4 > 0) {
            StringBuffer var6 = var3.append(',');
         }

         int var7 = var1[var4];
         var3.append(var7);
         ++var4;
      }
   }

   public String[] getAlerts() {
      String[] var1 = new String[this.alerts.size()];
      this.alerts.toArray(var1);
      this.alerts.clear();
      return var1;
   }

   protected SSLSocketFactory getSSLSocketFactory(TrustManager var1) throws GeneralSecurityException {
      Object var2;
      if(var1 == null) {
         var2 = new EmptyX509TrustManager();
      } else {
         var2 = var1;
      }

      SSLContext var3 = SSLContext.getInstance("TLS");
      TrustManager[] var4 = new TrustManager[]{(TrustManager)var2};
      var3.init((KeyManager[])null, var4, (SecureRandom)null);
      return var3.getSocketFactory();
   }

   public Map getacl(String var1) throws IOException {
      String var2 = this.newTag();
      StringBuilder var3 = (new StringBuilder()).append("GETACL ");
      String var4 = quote(UTF7imap.encode(var1));
      String var5 = var3.append(var4).toString();
      this.sendCommand(var2, var5);
      TreeMap var6 = new TreeMap();

      while(true) {
         IMAPResponse var7 = this.readResponse();
         String var8 = var7.getID();
         String var9 = var7.getTag();
         if(var2.equals(var9)) {
            this.processAlerts(var7);
            TreeMap var10;
            if(var8 == "OK") {
               var10 = var6;
            } else {
               if(var8 != "NO") {
                  String var11 = var7.getText();
                  throw new IMAPException(var8, var11);
               }

               var10 = null;
            }

            return var10;
         }

         if(!var7.isUntagged()) {
            String var21 = var7.getText();
            throw new IMAPException(var8, var21);
         }

         String var12 = var7.getID();
         if("ACL".equals(var12)) {
            String var13 = var7.getText();
            List var14 = this.parseACL(var13, 1);
            String var15 = (String)var14.get(2);
            Object var16 = var14.get(1);
            int var17 = this.stringToRights(var15);
            Integer var18 = new Integer(var17);
            var6.put(var16, var18);
         } else {
            this.asyncResponses.add(var7);
         }
      }
   }

   public Quota getquota(String var1) throws IOException {
      String var2 = this.newTag();
      StringBuilder var3 = (new StringBuilder()).append("GETQUOTA ");
      String var4 = quote(UTF7imap.encode(var1));
      String var5 = var3.append(var4).toString();
      this.sendCommand(var2, var5);
      Quota var6 = null;

      while(true) {
         IMAPResponse var7 = this.readResponse();
         String var8 = var7.getID();
         String var9 = var7.getTag();
         if(var2.equals(var9)) {
            this.processAlerts(var7);
            Quota var10;
            if(var8 == "OK") {
               var10 = var6;
            } else {
               if(var8 != "NO") {
                  String var11 = var7.getText();
                  throw new IMAPException(var8, var11);
               }

               var10 = null;
            }

            return var10;
         }

         if(!var7.isUntagged()) {
            String var15 = var7.getText();
            throw new IMAPException(var8, var15);
         }

         String var12 = var7.getID();
         if("QUOTA".equals(var12)) {
            String var13 = var7.getText();
            var6 = new Quota(var13);
         } else {
            this.asyncResponses.add(var7);
         }
      }
   }

   public Quota[] getquotaroot(String var1) throws IOException {
      String var2 = this.newTag();
      StringBuilder var3 = (new StringBuilder()).append("GETQUOTAROOT ");
      String var4 = quote(UTF7imap.encode(var1));
      String var5 = var3.append(var4).toString();
      this.sendCommand(var2, var5);
      ArrayList var6 = new ArrayList();

      while(true) {
         IMAPResponse var7 = this.readResponse();
         String var8 = var7.getID();
         String var9 = var7.getTag();
         if(var2.equals(var9)) {
            this.processAlerts(var7);
            Quota[] var10;
            if(var8 == "OK") {
               var10 = new Quota[var6.size()];
               var6.toArray(var10);
            } else {
               if(var8 != "NO") {
                  String var12 = var7.getText();
                  throw new IMAPException(var8, var12);
               }

               var10 = null;
            }

            return var10;
         }

         if(!var7.isUntagged()) {
            String var18 = var7.getText();
            throw new IMAPException(var8, var18);
         }

         String var13 = var7.getID();
         if("QUOTA".equals(var13)) {
            String var14 = var7.getText();
            Quota var15 = new Quota(var14);
            var6.add(var15);
         } else {
            this.asyncResponses.add(var7);
         }
      }
   }

   protected boolean invokeSimpleCommand(String var1) throws IOException {
      String var2 = this.newTag();
      this.sendCommand(var2, var1);

      while(true) {
         IMAPResponse var3 = this.readResponse();
         String var4 = var3.getID();
         String var5 = var3.getTag();
         if(var2.equals(var5)) {
            this.processAlerts(var3);
            boolean var6;
            if(var4 == "OK") {
               var6 = true;
            } else {
               if(var4 != "NO") {
                  String var7 = var3.getText();
                  throw new IMAPException(var4, var7);
               }

               var6 = false;
            }

            return var6;
         }

         if(!var3.isUntagged()) {
            String var9 = var3.getText();
            throw new IMAPException(var4, var9);
         }

         this.asyncResponses.add(var3);
      }
   }

   public ListEntry[] list(String var1, String var2) throws IOException {
      return this.listImpl("LIST", var1, var2);
   }

   protected ListEntry[] listImpl(String var1, String var2, String var3) throws IOException {
      String var4;
      if(var2 == null) {
         var4 = "";
      } else {
         var4 = var2;
      }

      String var5;
      if(var3 == null) {
         var5 = "";
      } else {
         var5 = var3;
      }

      String var6 = this.newTag();
      StringBuilder var7 = (new StringBuilder()).append(var1).append(' ');
      String var8 = quote(UTF7imap.encode(var4));
      StringBuilder var9 = var7.append(var8).append(' ');
      String var10 = quote(UTF7imap.encode(var5));
      String var11 = var9.append(var10).toString();
      this.sendCommand(var6, var11);
      ArrayList var12 = new ArrayList();

      while(true) {
         IMAPResponse var13 = this.readResponse();
         String var14 = var13.getID();
         if(!var13.isUntagged()) {
            String var47 = var13.getTag();
            if(var6.equals(var47)) {
               this.processAlerts(var13);
               if(var14 == "OK") {
                  ListEntry[] var48 = new ListEntry[var12.size()];
                  var12.toArray(var48);
                  return var48;
               }

               String var50 = var13.getText();
               throw new IMAPException(var14, var50);
            }

            String var51 = var13.getText();
            throw new IMAPException(var14, var51);
         }

         if(var14.equals(var1)) {
            List var15 = var13.getResponseCode();
            String var16 = var13.getText();
            int var17;
            if(var15 == null) {
               var17 = 0;
            } else {
               var17 = var15.size();
            }

            byte var18 = 0;
            byte var19 = 0;
            byte var20 = 0;
            byte var21 = 0;

            byte var28;
            for(int var22 = 0; var22 < var17; var19 = var28) {
               var2 = (String)var15.get(var22);
               byte var25;
               byte var27;
               if(var2.equalsIgnoreCase("\\Noinferiors")) {
                  byte var24 = 1;
                  var25 = var21;
                  var27 = var20;
                  var28 = var19;
               } else if(var2.equalsIgnoreCase("\\Noselect")) {
                  var25 = var21;
                  byte var33 = 1;
                  var27 = var20;
                  var28 = var33;
               } else if(var2.equalsIgnoreCase("\\Marked")) {
                  var25 = var21;
                  var27 = 1;
                  var28 = var19;
               } else if(var2.equalsIgnoreCase("\\Unmarked")) {
                  byte var36 = 1;
                  var25 = var36;
                  var27 = var20;
                  var28 = var19;
               } else {
                  var25 = var21;
                  var27 = var20;
                  var28 = var19;
               }

               ++var22;
               var21 = var25;
               var20 = var27;
            }

            int var38 = var16.indexOf(32);
            char var39 = 0;
            String var40 = var16.substring(0, var38);
            if(!var40.equalsIgnoreCase("NIL")) {
               char var41 = stripQuotes(var40).charAt(0);
            }

            int var42 = var38 + 1;
            String var43 = UTF7imap.decode(stripQuotes(var16.substring(var42)));
            ListEntry var44 = new ListEntry(var43, var39, (boolean)var18, (boolean)var19, (boolean)var20, (boolean)var21);
            var12.add(var44);
         } else {
            this.asyncResponses.add(var13);
         }
      }
   }

   public int listrights(String var1, String var2) throws IOException {
      String var3 = this.newTag();
      StringBuilder var4 = (new StringBuilder()).append("LISTRIGHTS ");
      String var5 = quote(UTF7imap.encode(var1));
      StringBuilder var6 = var4.append(var5).append(' ');
      String var7 = UTF7imap.encode(var2);
      String var8 = var6.append(var7).toString();
      this.sendCommand(var3, var8);
      int var9 = -1;

      while(true) {
         IMAPResponse var10 = this.readResponse();
         String var11 = var10.getID();
         String var12 = var10.getTag();
         if(var3.equals(var12)) {
            this.processAlerts(var10);
            if(var11 != "OK") {
               if(var11 != "NO") {
                  String var13 = var10.getText();
                  throw new IMAPException(var11, var13);
               }

               var9 = -1;
            }

            return var9;
         }

         if(!var10.isUntagged()) {
            String var18 = var10.getText();
            throw new IMAPException(var11, var18);
         }

         String var14 = var10.getID();
         if("LISTRIGHTS".equals(var14)) {
            String var15 = var10.getText();
            String var16 = (String)this.parseACL(var15, 1).get(2);
            var9 = this.stringToRights(var16);
         } else {
            this.asyncResponses.add(var10);
         }
      }
   }

   public boolean login(String var1, String var2) throws IOException {
      StringBuilder var3 = (new StringBuilder()).append("LOGIN ");
      String var4 = quote(var1);
      StringBuilder var5 = var3.append(var4).append(' ');
      String var6 = quote(var2);
      String var7 = var5.append(var6).toString();
      return this.invokeSimpleCommand(var7);
   }

   public void logout() throws IOException {
      String var1 = this.newTag();
      this.sendCommand(var1, "LOGOUT");

      while(true) {
         IMAPResponse var2 = this.readResponse();
         if(var2.isTagged()) {
            String var3 = var2.getTag();
            if(var1.equals(var3)) {
               this.processAlerts(var2);
               String var4 = var2.getID();
               if(var4 == "OK") {
                  this.socket.close();
                  return;
               }

               String var5 = var2.getText();
               throw new IMAPException(var4, var5);
            }
         }

         this.asyncResponses.add(var2);
      }
   }

   public ListEntry[] lsub(String var1, String var2) throws IOException {
      return this.listImpl("LSUB", var1, var2);
   }

   public int myrights(String var1) throws IOException {
      String var2 = this.newTag();
      StringBuilder var3 = (new StringBuilder()).append("MYRIGHTS ");
      String var4 = quote(UTF7imap.encode(var1));
      String var5 = var3.append(var4).toString();
      this.sendCommand(var2, var5);
      int var6 = -1;

      while(true) {
         IMAPResponse var7 = this.readResponse();
         String var8 = var7.getID();
         String var9 = var7.getTag();
         if(var2.equals(var9)) {
            this.processAlerts(var7);
            if(var8 != "OK") {
               if(var8 != "NO") {
                  String var10 = var7.getText();
                  throw new IMAPException(var8, var10);
               }

               var6 = -1;
            }

            return var6;
         }

         if(!var7.isUntagged()) {
            String var15 = var7.getText();
            throw new IMAPException(var8, var15);
         }

         String var11 = var7.getID();
         if("MYRIGHTS".equals(var11)) {
            String var12 = var7.getText();
            String var13 = (String)this.parseACL(var12, 0).get(2);
            var6 = this.stringToRights(var13);
         } else {
            this.asyncResponses.add(var7);
         }
      }
   }

   public Namespaces namespace() throws IOException {
      String var1 = this.newTag();
      this.sendCommand(var1, "NAMESPACE");
      Namespaces var2 = null;

      while(true) {
         IMAPResponse var3 = this.readResponse();
         String var4 = var3.getID();
         String var5 = var3.getTag();
         if(var1.equals(var5)) {
            this.processAlerts(var3);
            if(var4 == "OK") {
               return var2;
            } else {
               String var6 = var3.getText();
               throw new IMAPException(var4, var6);
            }
         }

         if(!var3.isUntagged()) {
            String var10 = var3.getText();
            throw new IMAPException(var4, var10);
         }

         String var7 = var3.getID();
         if("NAMESPACE".equals(var7)) {
            String var8 = var3.getText();
            var2 = new Namespaces(var8);
         } else {
            this.asyncResponses.add(var3);
         }
      }
   }

   protected String newTag() {
      StringBuilder var1 = (new StringBuilder()).append("A");
      int var2 = this.tagIndex + 1;
      this.tagIndex = var2;
      return var1.append(var2).toString();
   }

   public MailboxStatus noop() throws IOException {
      String var1 = this.newTag();
      this.sendCommand(var1, "NOOP");
      MailboxStatus var2 = new MailboxStatus();
      Iterator var3 = this.asyncResponses.iterator();
      boolean var4 = false;

      while(true) {
         IMAPResponse var5;
         if(var3.hasNext()) {
            var5 = (IMAPResponse)var3.next();
            var3.remove();
         } else {
            var5 = this.readResponse();
         }

         String var6 = var5.getID();
         if(!var5.isUntagged()) {
            String var8 = var5.getTag();
            if(var1.equals(var8)) {
               this.processAlerts(var5);
               if(var6 == "OK") {
                  MailboxStatus var11;
                  if(var4) {
                     var11 = var2;
                  } else {
                     var11 = null;
                  }

                  return var11;
               }

               String var9 = var5.getText();
               throw new IMAPException(var6, var9);
            }

            String var10 = var5.getText();
            throw new IMAPException(var6, var10);
         }

         boolean var7;
         if(!var4 && !this.updateMailboxStatus(var2, var6, var5)) {
            var7 = false;
         } else {
            var7 = true;
         }

         var4 = var7;
      }
   }

   protected IMAPResponse readResponse() throws IOException {
      IMAPResponse var1 = this.in.next();
      if(var1 == null) {
         Logger var2 = logger;
         Level var3 = IMAP_TRACE;
         var2.log(var3, "<EOF");
         throw new EOFException();
      } else {
         if(this.ansiDebug) {
            Logger var4 = logger;
            Level var5 = IMAP_TRACE;
            StringBuilder var6 = (new StringBuilder()).append("< ");
            String var7 = var1.toANSIString();
            String var8 = var6.append(var7).toString();
            var4.log(var5, var8);
         } else {
            Logger var9 = logger;
            Level var10 = IMAP_TRACE;
            StringBuilder var11 = (new StringBuilder()).append("< ");
            String var12 = var1.toString();
            String var13 = var11.append(var12).toString();
            var9.log(var10, var13);
         }

         return var1;
      }
   }

   public boolean rename(String var1, String var2) throws IOException {
      StringBuilder var3 = (new StringBuilder()).append("RENAME ");
      String var4 = quote(UTF7imap.encode(var1));
      StringBuilder var5 = var3.append(var4).append(' ');
      String var6 = quote(UTF7imap.encode(var2));
      String var7 = var5.append(var6).toString();
      return this.invokeSimpleCommand(var7);
   }

   public int[] search(String param1, String[] param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public MailboxStatus select(String var1) throws IOException {
      return this.selectImpl(var1, "SELECT");
   }

   protected MailboxStatus selectImpl(String var1, String var2) throws IOException {
      String var3 = this.newTag();
      StringBuilder var4 = (new StringBuilder()).append(var2).append(' ');
      String var5 = quote(UTF7imap.encode(var1));
      String var6 = var4.append(var5).toString();
      this.sendCommand(var3, var6);
      MailboxStatus var7 = new MailboxStatus();

      while(true) {
         IMAPResponse var8 = this.readResponse();
         String var9 = var8.getID();
         if(!var8.isUntagged()) {
            String var11 = var8.getTag();
            if(var3.equals(var11)) {
               this.processAlerts(var8);
               if(var9 == "OK") {
                  List var12 = var8.getResponseCode();
                  if(var12 != null && var12.size() > 0 && var12.get(0) == "READ-WRITE") {
                     var7.readWrite = (boolean)1;
                  }

                  return var7;
               }

               String var13 = var8.getText();
               throw new IMAPException(var9, var13);
            }

            String var14 = var8.getText();
            throw new IMAPException(var9, var14);
         }

         if(!this.updateMailboxStatus(var7, var9, var8)) {
            this.asyncResponses.add(var8);
         }
      }
   }

   protected void sendCommand(String var1, String var2) throws IOException {
      Logger var3 = logger;
      Level var4 = IMAP_TRACE;
      String var5 = "> " + var1 + " " + var2;
      var3.log(var4, var5);
      CRLFOutputStream var6 = this.out;
      String var7 = var1 + ' ' + var2;
      var6.write(var7);
      this.out.writeln();
      this.out.flush();
   }

   public void setAnsiDebug(boolean var1) {
      this.ansiDebug = var1;
   }

   public boolean setacl(String var1, String var2, int var3) throws IOException {
      StringBuilder var4 = (new StringBuilder()).append("SETACL ");
      String var5 = quote(UTF7imap.encode(var1));
      StringBuilder var6 = var4.append(var5).append(' ');
      String var7 = UTF7imap.encode(var2);
      StringBuilder var8 = var6.append(var7).append(' ');
      String var9 = this.rightsToString(var3);
      String var10 = var8.append(var9).toString();
      return this.invokeSimpleCommand(var10);
   }

   public Quota setquota(String var1, Quota.Resource[] var2) throws IOException {
      StringBuffer var3 = new StringBuffer();
      if(var2 != null) {
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               break;
            }

            if(var4 > 0) {
               StringBuffer var6 = var3.append(' ');
            }

            String var7 = var2[var4].toString();
            var3.append(var7);
            ++var4;
         }
      }

      String var9 = this.newTag();
      StringBuilder var10 = (new StringBuilder()).append("SETQUOTA ");
      String var11 = quote(UTF7imap.encode(var1));
      StringBuilder var12 = var10.append(var11).append(' ');
      String var13 = var3.toString();
      String var14 = var12.append(var13).toString();
      this.sendCommand(var9, var14);
      Quota var15 = null;

      while(true) {
         IMAPResponse var16 = this.readResponse();
         String var17 = var16.getID();
         String var18 = var16.getTag();
         if(var9.equals(var18)) {
            this.processAlerts(var16);
            if(var17 != "OK") {
               if(var17 != "NO") {
                  String var19 = var16.getText();
                  throw new IMAPException(var17, var19);
               }

               var15 = null;
            }

            return var15;
         }

         if(!var16.isUntagged()) {
            String var23 = var16.getText();
            throw new IMAPException(var17, var23);
         }

         String var20 = var16.getID();
         if("QUOTA".equals(var20)) {
            String var21 = var16.getText();
            var15 = new Quota(var21);
         } else {
            this.asyncResponses.add(var16);
         }
      }
   }

   public boolean starttls() throws IOException {
      EmptyX509TrustManager var1 = new EmptyX509TrustManager();
      return this.starttls(var1);
   }

   public boolean starttls(TrustManager param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public MailboxStatus status(String var1, String[] var2) throws IOException {
      char var3 = 32;
      String var4 = this.newTag();
      StringBuffer var5 = (new StringBuffer("STATUS")).append(var3);
      String var6 = quote(UTF7imap.encode(var1));
      StringBuffer var7 = var5.append(var6).append(var3).append('(');
      int var8 = 0;

      while(true) {
         int var9 = var2.length;
         if(var8 >= var9) {
            StringBuffer var13 = var7.append(')');
            String var14 = var7.toString();
            this.sendCommand(var4, var14);
            MailboxStatus var15 = new MailboxStatus();

            while(true) {
               IMAPResponse var16 = this.readResponse();
               String var17 = var16.getID();
               if(!var16.isUntagged()) {
                  String var25 = var16.getTag();
                  if(var4.equals(var25)) {
                     this.processAlerts(var16);
                     if(var17 == "OK") {
                        return var15;
                     }

                     String var26 = var16.getText();
                     throw new IMAPException(var17, var26);
                  }

                  String var27 = var16.getText();
                  throw new IMAPException(var17, var27);
               }

               if(var17 == "STATUS") {
                  List var29 = var16.getResponseCode();
                  int var18;
                  if(var29 == null) {
                     var18 = 0;
                  } else {
                     var18 = var29.size() - 1;
                  }

                  for(int var19 = 0; var19 < var18; var19 += 2) {
                     try {
                        String var20 = ((String)var29.get(var19)).intern();
                        int var21 = var19 + 1;
                        int var30 = Integer.parseInt((String)var29.get(var21));
                        if(var20 == "MESSAGES") {
                           var15.messageCount = var30;
                        } else if(var20 == "RECENT") {
                           var15.newMessageCount = var30;
                        } else if(var20 == "UIDNEXT") {
                           var15.uidNext = var30;
                        } else if(var20 == "UIDVALIDITY") {
                           var15.uidValidity = var30;
                        } else if(var20 == "UNSEEN") {
                           var15.firstUnreadMessage = var30;
                        }
                     } catch (NumberFormatException var28) {
                        String var23 = "Invalid code: " + var29;
                        throw new IMAPException(var17, var23);
                     }
                  }
               } else {
                  this.asyncResponses.add(var16);
               }
            }
         }

         if(var8 > 0) {
            StringBuffer var10 = var7.append(' ');
         }

         String var11 = var2[var8];
         var7.append(var11);
         ++var8;
      }
   }

   public MessageStatus store(int var1, String var2, String[] var3) throws IOException {
      String var4;
      if(var1 == -1) {
         var4 = "*";
      } else {
         var4 = Integer.toString(var1);
      }

      return this.storeImpl("STORE", var4, var2, var3)[0];
   }

   public MessageStatus[] store(int var1, int var2, String var3, String[] var4) throws IOException {
      StringBuffer var5 = new StringBuffer();
      int var6;
      if(var1 == -1) {
         var6 = 42;
      } else {
         var6 = var1;
      }

      var5.append(var6);
      StringBuffer var8 = var5.append(':');
      int var9;
      if(var2 == -1) {
         var9 = 42;
      } else {
         var9 = var2;
      }

      var5.append(var9);
      String var11 = var5.toString();
      return this.storeImpl("STORE", var11, var3, var4);
   }

   public MessageStatus[] store(int[] var1, String var2, String[] var3) throws IOException {
      StringBuffer var4 = new StringBuffer();
      int var5 = 0;

      while(true) {
         int var6 = var1.length;
         if(var5 >= var6) {
            String var10 = var4.toString();
            return this.storeImpl("STORE", var10, var2, var3);
         }

         if(var5 > 0) {
            StringBuffer var7 = var4.append(',');
         }

         int var8 = var1[var5];
         var4.append(var8);
         ++var5;
      }
   }

   public boolean subscribe(String var1) throws IOException {
      StringBuilder var2 = (new StringBuilder()).append("SUBSCRIBE ");
      String var3 = quote(UTF7imap.encode(var1));
      String var4 = var2.append(var3).toString();
      return this.invokeSimpleCommand(var4);
   }

   public MessageStatus uidFetch(long var1, String[] var3) throws IOException {
      String var4;
      if(var1 == 65535L) {
         var4 = "*";
      } else {
         var4 = Long.toString(var1);
      }

      return this.fetchImpl("UID FETCH", var4, var3)[0];
   }

   public MessageStatus[] uidFetch(long var1, long var3, String[] var5) throws IOException {
      StringBuffer var6 = new StringBuffer();
      long var7;
      if(var1 == 65535L) {
         var7 = 42L;
      } else {
         var7 = var1;
      }

      var6.append(var7);
      StringBuffer var10 = var6.append(':');
      long var11;
      if(var3 == 65535L) {
         var11 = 42L;
      } else {
         var11 = var3;
      }

      var6.append(var11);
      String var14 = var6.toString();
      return this.fetchImpl("UID FETCH", var14, var5);
   }

   public MessageStatus[] uidFetch(long[] var1, String[] var2) throws IOException {
      StringBuffer var3 = new StringBuffer();
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            String var10 = var3.toString();
            return this.fetchImpl("UID FETCH", var10, var2);
         }

         if(var4 > 0) {
            StringBuffer var6 = var3.append(',');
         }

         long var7 = var1[var4];
         var3.append(var7);
         ++var4;
      }
   }

   public MessageStatus uidStore(long var1, String var3, String[] var4) throws IOException {
      String var5;
      if(var1 == 65535L) {
         var5 = "*";
      } else {
         var5 = Long.toString(var1);
      }

      return this.storeImpl("UID STORE", var5, var3, var4)[0];
   }

   public MessageStatus[] uidStore(long var1, long var3, String var5, String[] var6) throws IOException {
      StringBuffer var7 = new StringBuffer();
      long var8;
      if(var1 == 65535L) {
         var8 = 42L;
      } else {
         var8 = var1;
      }

      var7.append(var8);
      StringBuffer var11 = var7.append(':');
      long var12;
      if(var3 == 65535L) {
         var12 = 42L;
      } else {
         var12 = var3;
      }

      var7.append(var12);
      String var15 = var7.toString();
      return this.storeImpl("UID STORE", var15, var5, var6);
   }

   public MessageStatus[] uidStore(long[] var1, String var2, String[] var3) throws IOException {
      StringBuffer var4 = new StringBuffer();
      int var5 = 0;

      while(true) {
         int var6 = var1.length;
         if(var5 >= var6) {
            String var11 = var4.toString();
            return this.storeImpl("UID STORE", var11, var2, var3);
         }

         if(var5 > 0) {
            StringBuffer var7 = var4.append(',');
         }

         long var8 = var1[var5];
         var4.append(var8);
         ++var5;
      }
   }

   public boolean unsubscribe(String var1) throws IOException {
      StringBuilder var2 = (new StringBuilder()).append("UNSUBSCRIBE ");
      String var3 = quote(UTF7imap.encode(var1));
      String var4 = var2.append(var3).toString();
      return this.invokeSimpleCommand(var4);
   }

   protected boolean updateMailboxStatus(MailboxStatus var1, String var2, IMAPResponse var3) throws IOException {
      int var4 = 0;
      boolean var18;
      if(var2 == "OK") {
         List var5 = var3.getResponseCode();
         int var6;
         if(var5 == null) {
            var6 = 0;
         } else {
            var6 = var5.size();
         }

         boolean var7;
         int var13;
         for(var7 = false; var4 < var6; var4 = var13) {
            int var11;
            boolean var12;
            label63: {
               Object var23 = var5.get(var4);
               if(var23 instanceof String) {
                  String var24 = (String)var23;
                  if(var4 + 1 < var6) {
                     int var8 = var4 + 1;
                     Object var9 = var5.get(var8);
                     if(var9 instanceof String) {
                        label73: {
                           String var25 = (String)var9;

                           label57: {
                              try {
                                 if(var24 != "UNSEEN") {
                                    if(var24 != "UIDVALIDITY") {
                                       break label73;
                                    }

                                    int var14 = Integer.parseInt(var25);
                                    var1.uidValidity = var14;
                                    break label57;
                                 }

                                 int var10 = Integer.parseInt(var25);
                                 var1.firstUnreadMessage = var10;
                              } catch (NumberFormatException var22) {
                                 String var16 = "Illegal " + var24 + " value: " + var25;
                                 throw new ProtocolException(var16);
                              }

                              var11 = var4 + 1;
                              var12 = true;
                              break label63;
                           }

                           var11 = var4 + 1;
                           var12 = true;
                           break label63;
                        }
                     } else if(var9 instanceof List && var24 == "PERMANENTFLAGS") {
                        List var17 = (List)var9;
                        var1.permanentFlags = var17;
                        var11 = var4 + 1;
                        var12 = true;
                        break label63;
                     }
                  }
               }

               var11 = var4;
               var12 = var7;
            }

            var13 = var11 + 1;
            var7 = var12;
         }

         var18 = var7;
      } else if(var2 == "EXISTS") {
         int var19 = var3.getCount();
         var1.messageCount = var19;
         var18 = true;
      } else if(var2 == "RECENT") {
         int var20 = var3.getCount();
         var1.newMessageCount = var20;
         var18 = true;
      } else if(var2 == "FLAGS") {
         List var21 = var3.getResponseCode();
         var1.flags = var21;
         var18 = true;
      } else {
         var18 = false;
      }

      return var18;
   }
}
