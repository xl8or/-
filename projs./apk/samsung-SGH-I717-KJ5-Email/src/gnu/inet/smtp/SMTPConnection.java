package gnu.inet.smtp;

import gnu.inet.smtp.ParameterList;
import gnu.inet.util.CRLFInputStream;
import gnu.inet.util.CRLFOutputStream;
import gnu.inet.util.EmptyX509TrustManager;
import gnu.inet.util.LineInputStream;
import gnu.inet.util.MessageOutputStream;
import gnu.inet.util.TraceLevel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SMTPConnection {

   protected static final int AMBIGUOUS = 553;
   protected static final String AUTH = "AUTH";
   protected static final String DATA = "DATA";
   public static final int DEFAULT_PORT = 25;
   protected static final String EHLO = "EHLO";
   protected static final String EXPN = "EXPN";
   protected static final String FINISH_DATA = "\n.";
   protected static final String HELO = "HELO";
   protected static final String HELP = "HELP";
   protected static final int INFO = 214;
   protected static final String MAIL_FROM = "MAIL FROM:";
   protected static final String NOOP = "NOOP";
   protected static final int OK = 250;
   protected static final int OK_NOT_LOCAL = 251;
   protected static final int OK_UNVERIFIED = 252;
   protected static final String QUIT = "QUIT";
   protected static final String RCPT_TO = "RCPT TO:";
   protected static final int READY = 220;
   protected static final String RSET = "RSET";
   protected static final int SEND_DATA = 354;
   public static final Level SMTP_TRACE = new TraceLevel("smtp");
   protected static final String SP = " ";
   protected static final String STARTTLS = "STARTTLS";
   protected static final String VRFY = "VRFY";
   public static final Logger logger = Logger.getLogger("gnu.inet.smtp");
   protected boolean continuation;
   protected final String greeting;
   protected LineInputStream in;
   protected CRLFOutputStream out;
   protected String response;
   protected Socket socket;


   public SMTPConnection(String var1) throws IOException {
      byte var4 = 0;
      byte var5 = 0;
      this(var1, 25, 0, var4, (boolean)var5, (TrustManager)null);
   }

   public SMTPConnection(String var1, int var2) throws IOException {
      byte var6 = 0;
      byte var7 = 0;
      this(var1, var2, 0, var6, (boolean)var7, (TrustManager)null);
   }

   public SMTPConnection(String var1, int var2, int var3, int var4) throws IOException {
      this(var1, var2, var3, var4, (boolean)0, (TrustManager)null);
   }

   public SMTPConnection(String var1, int var2, int var3, int var4, boolean var5, TrustManager var6) throws IOException {
      int var7;
      if(var2 <= 0) {
         var7 = 25;
      } else {
         var7 = var2;
      }

      this.response = null;
      this.continuation = (boolean)0;

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
      } catch (GeneralSecurityException var31) {
         IOException var25 = new IOException();
         Throwable var26 = var25.initCause(var31);
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
      StringBuffer var21 = new StringBuffer();
      boolean var22 = false;

      do {
         if(this.getResponse() != 220) {
            String var23 = this.response;
            throw new ProtocolException(var23);
         }

         if(var22) {
            StringBuffer var27 = var21.append('\n');
         } else {
            var22 = true;
         }

         String var28 = this.response;
         var21.append(var28);
      } while(this.continuation);

      String var30 = var21.toString();
      this.greeting = var30;
   }

   public boolean authenticate(String param1, String param2, String param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public OutputStream data() throws IOException {
      this.send("DATA");
      switch(this.getAllResponses()) {
      case 354:
         CRLFOutputStream var2 = this.out;
         return new MessageOutputStream(var2);
      default:
         String var1 = this.response;
         throw new ProtocolException(var1);
      }
   }

   public List ehlo(String var1) throws IOException {
      String var2 = "EHLO " + var1;
      this.send(var2);
      ArrayList var3 = new ArrayList();

      while(true) {
         List var6;
         switch(this.getResponse()) {
         case 250:
            String var4 = this.response;
            var3.add(var4);
            if(this.continuation) {
               break;
            }

            var6 = Collections.unmodifiableList(var3);
            return var6;
         default:
            var6 = null;
            return var6;
         }
      }
   }

   public List expn(String var1) throws IOException {
      String var2 = "EXPN " + var1;
      this.send(var2);
      ArrayList var3 = new ArrayList();

      while(true) {
         List var7;
         switch(this.getResponse()) {
         case 250:
            String var4 = this.response.trim();
            this.response = var4;
            String var5 = this.response;
            var3.add(var5);
            if(this.continuation) {
               break;
            }

            var7 = Collections.unmodifiableList(var3);
            return var7;
         default:
            var7 = null;
            return var7;
         }
      }
   }

   public boolean finishData() throws IOException {
      this.send("\n.");
      boolean var1;
      switch(this.getAllResponses()) {
      case 250:
         var1 = true;
         break;
      default:
         var1 = false;
      }

      return var1;
   }

   protected int getAllResponses() throws IOException {
      boolean var1 = false;
      int var2 = this.getResponse();
      int var3 = var2;

      while(this.continuation) {
         var3 = this.getResponse();
         if(var3 != var2) {
            var1 = true;
         }
      }

      if(var1) {
         throw new ProtocolException("Conflicting response codes");
      } else {
         return var3;
      }
   }

   public String getGreeting() {
      return this.greeting;
   }

   public String getLastResponse() {
      return this.response;
   }

   protected int getResponse() throws IOException {
      // $FF: Couldn't be decompiled
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

   public boolean helo(String var1) throws IOException {
      String var2 = "HELO " + var1;
      this.send(var2);
      boolean var3;
      if(this.getAllResponses() == 250) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public List help(String var1) throws IOException {
      String var2;
      if(var1 == null) {
         var2 = "HELP";
      } else {
         var2 = "HELP " + var1;
      }

      this.send(var2);
      ArrayList var3 = new ArrayList();

      while(true) {
         List var4;
         switch(this.getResponse()) {
         case 214:
            String var5 = this.response;
            var3.add(var5);
            if(this.continuation) {
               break;
            }

            var4 = Collections.unmodifiableList(var3);
            return var4;
         default:
            var4 = null;
            return var4;
         }
      }
   }

   public boolean mailFrom(String var1, ParameterList var2) throws IOException {
      StringBuffer var3 = new StringBuffer("MAIL FROM:");
      StringBuffer var4 = var3.append('<');
      var3.append(var1);
      StringBuffer var6 = var3.append('>');
      if(var2 != null) {
         StringBuffer var7 = var3.append(" ");
         var3.append(var2);
      }

      String var9 = var3.toString();
      this.send(var9);
      boolean var10;
      switch(this.getAllResponses()) {
      case 250:
      case 251:
      case 252:
         var10 = true;
         break;
      default:
         var10 = false;
      }

      return var10;
   }

   public void noop() throws IOException {
      this.send("NOOP");
      int var1 = this.getAllResponses();
   }

   public void quit() throws IOException {
      try {
         this.send("QUIT");
         int var1 = this.getAllResponses();
         return;
      } catch (IOException var6) {
         ;
      } finally {
         this.socket.close();
      }

   }

   public boolean rcptTo(String var1, ParameterList var2) throws IOException {
      StringBuffer var3 = new StringBuffer("RCPT TO:");
      StringBuffer var4 = var3.append('<');
      var3.append(var1);
      StringBuffer var6 = var3.append('>');
      if(var2 != null) {
         StringBuffer var7 = var3.append(" ");
         var3.append(var2);
      }

      String var9 = var3.toString();
      this.send(var9);
      boolean var10;
      switch(this.getAllResponses()) {
      case 250:
      case 251:
      case 252:
         var10 = true;
         break;
      default:
         var10 = false;
      }

      return var10;
   }

   public void rset() throws IOException {
      this.send("RSET");
      if(this.getAllResponses() != 250) {
         String var1 = this.response;
         throw new ProtocolException(var1);
      }
   }

   protected void send(String var1) throws IOException {
      Logger var2 = logger;
      Level var3 = SMTP_TRACE;
      String var4 = "> " + var1;
      var2.log(var3, var4);
      CRLFOutputStream var5 = this.out;
      byte[] var6 = var1.getBytes("US-ASCII");
      var5.write(var6);
      this.out.write(13);
      this.out.flush();
   }

   public boolean starttls() throws IOException {
      EmptyX509TrustManager var1 = new EmptyX509TrustManager();
      return this.starttls(var1);
   }

   public boolean starttls(TrustManager param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public List vrfy(String var1) throws IOException {
      String var2 = "VRFY " + var1;
      this.send(var2);
      ArrayList var3 = new ArrayList();

      while(true) {
         List var11;
         switch(this.getResponse()) {
         case 250:
         case 553:
            String var4 = this.response.trim();
            this.response = var4;
            if(this.response.indexOf(64) != -1) {
               String var5 = this.response;
               var3.add(var5);
            } else if(this.response.indexOf(60) != -1) {
               String var7 = this.response;
               var3.add(var7);
            } else if(this.response.indexOf(32) == -1) {
               String var9 = this.response;
               var3.add(var9);
            }

            if(this.continuation) {
               break;
            }

            var11 = Collections.unmodifiableList(var3);
            return var11;
         default:
            var11 = null;
            return var11;
         }
      }
   }
}
