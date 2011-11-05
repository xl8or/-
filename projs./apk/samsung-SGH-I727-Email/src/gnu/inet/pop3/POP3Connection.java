package gnu.inet.pop3;

import gnu.inet.util.CRLFInputStream;
import gnu.inet.util.CRLFOutputStream;
import gnu.inet.util.EmptyX509TrustManager;
import gnu.inet.util.LineInputStream;
import gnu.inet.util.MessageInputStream;
import gnu.inet.util.TraceLevel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class POP3Connection {

   protected static final String APOP = "APOP";
   protected static final String AUTH = "AUTH";
   protected static final String CAPA = "CAPA";
   public static final int DEFAULT_PORT = 110;
   protected static final String DELE = "DELE";
   protected static final int ERR = 1;
   protected static final String LIST = "LIST";
   protected static final String NOOP = "NOOP";
   protected static final int OK = 0;
   protected static final String PASS = "PASS";
   public static final Level POP3_TRACE = new TraceLevel("pop3");
   protected static final String QUIT = "QUIT";
   protected static final int READY = 2;
   protected static final String RETR = "RETR";
   protected static final String RSET = "RSET";
   protected static final String STAT = "STAT";
   protected static final String STLS = "STLS";
   protected static final String TOP = "TOP";
   protected static final String UIDL = "UIDL";
   protected static final String USER = "USER";
   private static final String _ERR = "-ERR";
   private static final String _OK = "+OK";
   private static final String _READY = "+ ";
   public static final Logger logger = Logger.getLogger("gnu.inet.pop3");
   protected LineInputStream in;
   protected CRLFOutputStream out;
   protected String response;
   protected Socket socket;
   protected byte[] timestamp;


   public POP3Connection(String var1) throws UnknownHostException, IOException {
      byte var4 = 0;
      byte var5 = 0;
      this(var1, -1, 0, var4, (boolean)var5, (TrustManager)null);
   }

   public POP3Connection(String var1, int var2) throws UnknownHostException, IOException {
      byte var6 = 0;
      byte var7 = 0;
      this(var1, var2, 0, var6, (boolean)var7, (TrustManager)null);
   }

   public POP3Connection(String var1, int var2, int var3, int var4) throws UnknownHostException, IOException {
      this(var1, var2, var3, var4, (boolean)0, (TrustManager)null);
   }

   public POP3Connection(String var1, int var2, int var3, int var4, boolean var5, TrustManager var6) throws UnknownHostException, IOException {
      int var7;
      if(var2 <= 0) {
         var7 = 110;
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
      } catch (GeneralSecurityException var29) {
         IOException var25 = new IOException();
         Throwable var26 = var25.initCause(var29);
         throw var25;
      }

      InputStream var14 = this.socket.getInputStream();
      BufferedInputStream var15 = new BufferedInputStream(var14);
      CRLFInputStream var16 = new CRLFInputStream(var15);
      LineInputStream var17 = new LineInputStream(var16);
      this.in = var17;
      OutputStream var18 = this.socket.getOutputStream();
      BufferedOutputStream var19 = new BufferedOutputStream(var18);
      CRLFOutputStream var20 = new CRLFOutputStream(var19);
      this.out = var20;
      if(this.getResponse() != 0) {
         StringBuilder var21 = (new StringBuilder()).append("Connect failed: ");
         String var22 = this.response;
         String var23 = var21.append(var22).toString();
         throw new ProtocolException(var23);
      } else {
         String var27 = this.response;
         byte[] var28 = this.parseTimestamp(var27);
         this.timestamp = var28;
      }
   }

   public boolean apop(String var1, String var2) throws IOException {
      boolean var3;
      if(var1 != null && var2 != null && this.timestamp != null) {
         int var24;
         try {
            byte[] var4 = var2.getBytes("US-ASCII");
            int var5 = this.timestamp.length;
            int var6 = var4.length;
            byte[] var7 = new byte[var5 + var6];
            byte[] var8 = this.timestamp;
            int var9 = this.timestamp.length;
            System.arraycopy(var8, 0, var7, 0, var9);
            int var10 = this.timestamp.length;
            int var11 = var4.length;
            System.arraycopy(var4, 0, var7, var10, var11);
            byte[] var12 = MessageDigest.getInstance("MD5").digest(var7);
            StringBuffer var13 = new StringBuffer();
            int var14 = 0;

            while(true) {
               int var15 = var12.length;
               if(var14 >= var15) {
                  StringBuffer var21 = (new StringBuffer("APOP")).append(' ').append(var1).append(' ');
                  String var22 = var13.toString();
                  String var23 = var21.append(var22).toString();
                  this.send(var23);
                  var24 = this.getResponse();
                  break;
               }

               int var16 = var12[var14];
               if(var16 < 0) {
                  var16 += 256;
               }

               String var17 = Integer.toHexString((var16 & 240) >> 4);
               var13.append(var17);
               String var19 = Integer.toHexString(var16 & 15);
               var13.append(var19);
               ++var14;
            }
         } catch (NoSuchAlgorithmException var28) {
            Logger var26 = logger;
            Level var27 = POP3_TRACE;
            var26.log(var27, "MD5 algorithm not found");
            var3 = false;
            return var3;
         }

         if(var24 == 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean auth(String param1, String param2, String param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public List capa() throws IOException {
      this.send("CAPA");
      List var4;
      if(this.getResponse() == 0) {
         ArrayList var1 = new ArrayList();

         for(String var2 = this.in.readLine(); !".".equals(var2); var2 = this.in.readLine()) {
            var1.add(var2);
         }

         var4 = Collections.unmodifiableList(var1);
      } else {
         var4 = null;
      }

      return var4;
   }

   public void dele(int var1) throws IOException {
      String var2 = "DELE " + var1;
      this.send(var2);
      if(this.getResponse() != 0) {
         StringBuilder var3 = (new StringBuilder()).append("DELE failed: ");
         String var4 = this.response;
         String var5 = var3.append(var4).toString();
         throw new ProtocolException(var5);
      }
   }

   protected int getResponse() throws IOException {
      String var1 = this.in.readLine();
      this.response = var1;
      Logger var2 = logger;
      Level var3 = POP3_TRACE;
      StringBuilder var4 = (new StringBuilder()).append("< ");
      String var5 = this.response;
      String var6 = var4.append(var5).toString();
      var2.log(var3, var6);
      byte var8;
      if(this.response.indexOf("+OK") == 0) {
         String var7 = this.response.substring(3).trim();
         this.response = var7;
         var8 = 0;
      } else if(this.response.indexOf("-ERR") == 0) {
         String var9 = this.response.substring(4).trim();
         this.response = var9;
         var8 = 1;
      } else {
         if(this.response.indexOf("+ ") != 0) {
            StringBuilder var11 = (new StringBuilder()).append("Unexpected response: ");
            String var12 = this.response;
            String var13 = var11.append(var12).toString();
            throw new ProtocolException(var13);
         }

         String var10 = this.response.substring(2).trim();
         this.response = var10;
         var8 = 2;
      }

      return var8;
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

   public int list(int var1) throws IOException {
      String var2 = "LIST " + var1;
      this.send(var2);
      if(this.getResponse() != 0) {
         StringBuilder var3 = (new StringBuilder()).append("LIST failed: ");
         String var4 = this.response;
         String var5 = var3.append(var4).toString();
         throw new ProtocolException(var5);
      } else {
         try {
            String var6 = this.response;
            int var7 = this.response.indexOf(32) + 1;
            int var8 = Integer.parseInt(var6.substring(var7));
            return var8;
         } catch (NumberFormatException var13) {
            StringBuilder var10 = (new StringBuilder()).append("Not a number: ");
            String var11 = this.response;
            String var12 = var10.append(var11).toString();
            throw new ProtocolException(var12);
         }
      }
   }

   public boolean login(String var1, String var2) throws IOException {
      boolean var3;
      if(var1 != null && var2 != null) {
         String var4 = "USER " + var1;
         this.send(var4);
         if(this.getResponse() != 0) {
            var3 = false;
         } else {
            String var5 = "PASS " + var2;
            this.send(var5);
            if(this.getResponse() == 0) {
               var3 = true;
            } else {
               var3 = false;
            }
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public void noop() throws IOException {
      this.send("NOOP");
      if(this.getResponse() != 0) {
         StringBuilder var1 = (new StringBuilder()).append("NOOP failed: ");
         String var2 = this.response;
         String var3 = var1.append(var2).toString();
         throw new ProtocolException(var3);
      }
   }

   byte[] parseTimestamp(String var1) throws IOException {
      int var2 = var1.indexOf(60);
      byte[] var6;
      if(var2 != -1) {
         int var3 = var1.indexOf(62, var2);
         if(var3 != -1) {
            int var4 = var3 + 1;
            String var5 = var1.substring(var2, var4);
            if(var5.indexOf(64) != -1) {
               var6 = var5.getBytes("US-ASCII");
               return var6;
            }
         }
      }

      var6 = null;
      return var6;
   }

   public boolean quit() throws IOException {
      this.send("QUIT");
      int var1 = this.getResponse();
      this.socket.close();
      boolean var2;
      if(var1 == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public InputStream retr(int var1) throws IOException {
      String var2 = "RETR " + var1;
      this.send(var2);
      if(this.getResponse() != 0) {
         StringBuilder var3 = (new StringBuilder()).append("RETR failed: ");
         String var4 = this.response;
         String var5 = var3.append(var4).toString();
         throw new ProtocolException(var5);
      } else {
         LineInputStream var6 = this.in;
         return new MessageInputStream(var6);
      }
   }

   public void rset() throws IOException {
      this.send("RSET");
      if(this.getResponse() != 0) {
         StringBuilder var1 = (new StringBuilder()).append("RSET failed: ");
         String var2 = this.response;
         String var3 = var1.append(var2).toString();
         throw new ProtocolException(var3);
      }
   }

   protected void send(String var1) throws IOException {
      Logger var2 = logger;
      Level var3 = POP3_TRACE;
      String var4 = "> " + var1;
      var2.log(var3, var4);
      this.out.write(var1);
      this.out.writeln();
      this.out.flush();
   }

   public int stat() throws IOException {
      this.send("STAT");
      if(this.getResponse() != 0) {
         StringBuilder var1 = (new StringBuilder()).append("STAT failed: ");
         String var2 = this.response;
         String var3 = var1.append(var2).toString();
         throw new ProtocolException(var3);
      } else {
         try {
            String var4 = this.response;
            int var5 = this.response.indexOf(32);
            int var6 = Integer.parseInt(var4.substring(0, var5));
            return var6;
         } catch (NumberFormatException var15) {
            StringBuilder var8 = (new StringBuilder()).append("Not a number: ");
            String var9 = this.response;
            String var10 = var8.append(var9).toString();
            throw new ProtocolException(var10);
         } catch (ArrayIndexOutOfBoundsException var16) {
            StringBuilder var12 = (new StringBuilder()).append("Not a STAT response: ");
            String var13 = this.response;
            String var14 = var12.append(var13).toString();
            throw new ProtocolException(var14);
         }
      }
   }

   public boolean stls() throws IOException {
      EmptyX509TrustManager var1 = new EmptyX509TrustManager();
      return this.stls(var1);
   }

   public boolean stls(TrustManager param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public InputStream top(int var1) throws IOException {
      String var2 = "TOP " + var1 + ' ' + '0';
      this.send(var2);
      if(this.getResponse() != 0) {
         StringBuilder var3 = (new StringBuilder()).append("TOP failed: ");
         String var4 = this.response;
         String var5 = var3.append(var4).toString();
         throw new ProtocolException(var5);
      } else {
         LineInputStream var6 = this.in;
         return new MessageInputStream(var6);
      }
   }

   public String uidl(int var1) throws IOException {
      String var2 = "UIDL " + var1;
      this.send(var2);
      if(this.getResponse() != 0) {
         StringBuilder var3 = (new StringBuilder()).append("UIDL failed: ");
         String var4 = this.response;
         String var5 = var3.append(var4).toString();
         throw new ProtocolException(var5);
      } else {
         String var6 = this.response;
         int var7 = this.response.indexOf(32) + 1;
         return var6.substring(var7);
      }
   }

   public Map uidl() throws IOException {
      this.send("UIDL");
      if(this.getResponse() != 0) {
         StringBuilder var1 = (new StringBuilder()).append("UIDL failed: ");
         String var2 = this.response;
         String var3 = var1.append(var2).toString();
         throw new ProtocolException(var3);
      } else {
         LinkedHashMap var4 = new LinkedHashMap();
         String var5 = this.in.readLine();

         while(var5 != null && !".".equals(var5)) {
            int var6 = var5.indexOf(32);
            if(var6 < 1) {
               String var7 = "Invalid UIDL response: " + var5;
               throw new ProtocolException(var7);
            }

            try {
               String var8 = var5.substring(0, var6);
               Integer var9 = new Integer(var8);
               int var10 = var6 + 1;
               String var11 = var5.substring(var10);
               var4.put(var9, var11);
            } catch (NumberFormatException var15) {
               String var14 = "Invalid message number: " + var5;
               throw new ProtocolException(var14);
            }
         }

         return Collections.unmodifiableMap(var4);
      }
   }
}
