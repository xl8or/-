package gnu.inet.ftp;

import gnu.inet.ftp.DTP;
import gnu.inet.ftp.FTPException;
import gnu.inet.ftp.FTPResponse;
import gnu.inet.util.CRLFInputStream;
import gnu.inet.util.CRLFOutputStream;
import gnu.inet.util.EmptyX509TrustManager;
import gnu.inet.util.LineInputStream;
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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class FTPConnection {

   protected static final String ABOR = "ABOR";
   protected static final String ACCT = "ACCT";
   protected static final String ALLO = "ALLO";
   protected static final String APPE = "APPE";
   protected static final String AUTH = "AUTH";
   protected static final String CCC = "CCC";
   protected static final String CDUP = "CDUP";
   protected static final String CWD = "CWD";
   protected static final String DELE = "DELE";
   public static final int FTP_DATA_PORT = 20;
   public static final int FTP_PORT = 21;
   protected static final String HELP = "HELP";
   protected static final String LIST = "LIST";
   protected static final String MKD = "MKD";
   protected static final String MODE = "MODE";
   public static final int MODE_BLOCK = 2;
   public static final int MODE_COMPRESSED = 3;
   public static final int MODE_STREAM = 1;
   protected static final String NLST = "NLST";
   protected static final String NOOP = "NOOP";
   protected static final String PASS = "PASS";
   protected static final String PASV = "PASV";
   protected static final String PBSZ = "PBSZ";
   protected static final String PORT = "PORT";
   protected static final String PROT = "PROT";
   protected static final String PWD = "PWD";
   protected static final String QUIT = "QUIT";
   protected static final String REIN = "REIN";
   protected static final String REST = "REST";
   protected static final String RETR = "RETR";
   protected static final String RMD = "RMD";
   protected static final String RNFR = "RNFR";
   protected static final String RNTO = "RNTO";
   protected static final String SITE = "SITE";
   protected static final String SMNT = "SMNT";
   protected static final String STAT = "STAT";
   protected static final String STOR = "STOR";
   protected static final String STOU = "STOU";
   protected static final String STRU = "STRU";
   public static final int STRUCTURE_FILE = 1;
   public static final int STRUCTURE_PAGE = 3;
   public static final int STRUCTURE_RECORD = 2;
   protected static final String SYST = "SYST";
   protected static final String TLS = "TLS";
   protected static final String TYPE = "TYPE";
   public static final int TYPE_ASCII = 1;
   public static final int TYPE_BINARY = 3;
   public static final int TYPE_EBCDIC = 2;
   protected static final String USER = "USER";
   private static final String US_ASCII = "US-ASCII";
   protected int connectionTimeout;
   protected boolean debug;
   protected DTP dtp;
   protected int fileStructure;
   protected LineInputStream in;
   protected CRLFOutputStream out;
   protected boolean passive;
   protected int representationType;
   protected Socket socket;
   protected int timeout;
   protected int transferMode;


   public FTPConnection(String var1) throws UnknownHostException, IOException {
      byte var4 = 0;
      byte var5 = 0;
      this(var1, -1, 0, var4, (boolean)var5);
   }

   public FTPConnection(String var1, int var2) throws UnknownHostException, IOException {
      byte var6 = 0;
      byte var7 = 0;
      this(var1, var2, 0, var6, (boolean)var7);
   }

   public FTPConnection(String var1, int var2, int var3, int var4, boolean var5) throws UnknownHostException, IOException {
      this.representationType = 1;
      this.fileStructure = 1;
      this.transferMode = 1;
      this.passive = (boolean)0;
      this.connectionTimeout = var3;
      this.timeout = var4;
      this.debug = var5;
      int var6;
      if(var2 <= 0) {
         var6 = 21;
      } else {
         var6 = var2;
      }

      Socket var7 = new Socket();
      this.socket = var7;
      InetSocketAddress var8 = new InetSocketAddress(var1, var6);
      if(var3 > 0) {
         this.socket.connect(var8, var3);
      } else {
         this.socket.connect(var8);
      }

      if(var4 > 0) {
         this.socket.setSoTimeout(var4);
      }

      InputStream var9 = this.socket.getInputStream();
      BufferedInputStream var10 = new BufferedInputStream(var9);
      CRLFInputStream var11 = new CRLFInputStream(var10);
      LineInputStream var12 = new LineInputStream(var11);
      this.in = var12;
      OutputStream var13 = this.socket.getOutputStream();
      BufferedOutputStream var14 = new BufferedOutputStream(var13);
      CRLFOutputStream var15 = new CRLFOutputStream(var14);
      this.out = var15;
      FTPResponse var16 = this.getResponse();
      switch(var16.getCode()) {
      case 220:
         return;
      default:
         throw new FTPException(var16);
      }
   }

   static final int parseCode(String var0) {
      int var1 = 0;
      char[] var2 = new char[3];
      char var3 = var0.charAt(var1);
      var2[var1] = var3;
      char var4 = var0.charAt(1);
      var2[1] = var4;
      char var5 = var0.charAt(2);
      var2[2] = var5;
      int var6 = var1;

      int var8;
      while(true) {
         if(var6 < 3) {
            int var7 = var2[var6] - 48;
            if(var7 >= 0 && var7 <= 9) {
               switch(var6) {
               case 0:
                  int var9 = var7 * 100;
                  var1 += var9;
                  break;
               case 1:
                  int var10 = var7 * 10;
                  var1 += var10;
                  break;
               case 2:
                  var1 += var7;
               }

               ++var6;
               continue;
            }

            var8 = -1;
            break;
         }

         var8 = var1;
         break;
      }

      return var8;
   }

   public boolean abort() throws IOException {
      this.send("ABOR");
      FTPResponse var1 = this.getResponse();
      if(this.dtp != null) {
         boolean var2 = this.dtp.abort();
      }

      boolean var3;
      switch(var1.getCode()) {
      case 226:
         var3 = false;
         break;
      case 426:
         var1 = this.getResponse();
         if(var1.getCode() == 226) {
            var3 = true;
            break;
         }

         throw new FTPException(var1);
      default:
         throw new FTPException(var1);
      }

      return var3;
   }

   public void allocate(long var1) throws IOException {
      String var3 = "ALLO " + var1;
      this.send(var3);
      FTPResponse var4 = this.getResponse();
      switch(var4.getCode()) {
      case 200:
      case 202:
         return;
      case 201:
      default:
         throw new FTPException(var4);
      }
   }

   public OutputStream append(String var1) throws IOException {
      if(this.dtp == null || this.transferMode == 1) {
         this.initialiseDTP();
      }

      String var2 = "APPE " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      switch(var3.getCode()) {
      case 125:
      case 150:
         return this.dtp.getOutputStream();
      default:
         throw new FTPException(var3);
      }
   }

   public boolean authenticate(String var1, String var2) throws IOException {
      String var3 = "USER " + var1;
      this.send(var3);
      FTPResponse var4 = this.getResponse();
      boolean var5;
      switch(var4.getCode()) {
      case 230:
         var5 = true;
         break;
      case 331:
         String var6 = "PASS " + var2;
         this.send(var6);
         FTPResponse var7 = this.getResponse();
         switch(var7.getCode()) {
         case 202:
         case 230:
            var5 = true;
            return var5;
         case 332:
         case 530:
            var5 = false;
            return var5;
         default:
            throw new FTPException(var7);
         }
      case 332:
      case 530:
         var5 = false;
         break;
      default:
         throw new FTPException(var4);
      }

      return var5;
   }

   public boolean changeToParentDirectory() throws IOException {
      this.send("CDUP");
      FTPResponse var1 = this.getResponse();
      boolean var2;
      switch(var1.getCode()) {
      case 250:
         var2 = true;
         break;
      case 550:
         var2 = false;
         break;
      default:
         throw new FTPException(var1);
      }

      return var2;
   }

   public boolean changeWorkingDirectory(String var1) throws IOException {
      String var2 = "CWD " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      boolean var4;
      switch(var3.getCode()) {
      case 250:
         var4 = true;
         break;
      case 550:
         var4 = false;
         break;
      default:
         throw new FTPException(var3);
      }

      return var4;
   }

   public boolean delete(String var1) throws IOException {
      String var2 = "DELE " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      boolean var4;
      switch(var3.getCode()) {
      case 250:
         var4 = true;
         break;
      case 450:
      case 550:
         var4 = false;
         break;
      default:
         throw new FTPException(var3);
      }

      return var4;
   }

   public int getFileStructure() {
      return this.fileStructure;
   }

   public int getRepresentationType() {
      return this.representationType;
   }

   protected FTPResponse getResponse() throws IOException {
      FTPResponse var1 = this.readResponse();
      if(var1.getCode() == 226) {
         if(this.dtp != null) {
            this.dtp.transferComplete();
         }

         var1 = this.readResponse();
      }

      return var1;
   }

   public int getTransferMode() {
      return this.transferMode;
   }

   public String getWorkingDirectory() throws IOException {
      this.send("PWD");
      FTPResponse var1 = this.getResponse();
      switch(var1.getCode()) {
      case 257:
         String var2 = var1.getMessage();
         int var3;
         if(var2.charAt(0) == 34) {
            var3 = var2.indexOf(34, 1);
            if(var3 == -1) {
               throw new ProtocolException(var2);
            }

            var2 = var2.substring(1, var3);
         } else {
            var3 = var2.indexOf(32);
            if(var3 != -1) {
               var2 = var2.substring(0, var3);
            }
         }

         return var2;
      default:
         throw new FTPException(var1);
      }
   }

   protected void initialiseDTP() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public InputStream list(String var1) throws IOException {
      if(this.dtp == null || this.transferMode == 1) {
         this.initialiseDTP();
      }

      if(var1 == null) {
         this.send("LIST");
      } else {
         String var3 = "LIST " + var1;
         this.send(var3);
      }

      FTPResponse var2 = this.getResponse();
      switch(var2.getCode()) {
      case 125:
      case 150:
         return this.dtp.getInputStream();
      default:
         throw new FTPException(var2);
      }
   }

   public void logout() throws IOException {
      this.send("QUIT");

      try {
         FTPResponse var1 = this.getResponse();
      } catch (IOException var5) {
         ;
      }

      if(this.dtp != null) {
         this.dtp.complete();
         this.dtp = null;
      }

      try {
         this.socket.close();
      } catch (IOException var4) {
         ;
      }
   }

   public boolean makeDirectory(String var1) throws IOException {
      String var2 = "MKD " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      boolean var4;
      switch(var3.getCode()) {
      case 257:
         var4 = true;
         break;
      case 550:
         var4 = false;
         break;
      default:
         throw new FTPException(var3);
      }

      return var4;
   }

   public List nameList(String var1) throws IOException {
      if(this.dtp == null || this.transferMode == 1) {
         this.initialiseDTP();
      }

      if(var1 == null) {
         this.send("NLST");
      } else {
         String var3 = "NLST " + var1;
         this.send(var3);
      }

      FTPResponse var2 = this.getResponse();
      switch(var2.getCode()) {
      case 125:
      case 150:
         InputStream var4 = this.dtp.getInputStream();
         BufferedInputStream var5 = new BufferedInputStream(var4);
         CRLFInputStream var6 = new CRLFInputStream(var5);
         LineInputStream var7 = new LineInputStream(var6);
         ArrayList var8 = new ArrayList();

         for(String var9 = var7.readLine(); var9 != null; var9 = var7.readLine()) {
            var8.add(var9);
         }

         var7.close();
         return var8;
      default:
         throw new FTPException(var2);
      }
   }

   public void noop() throws IOException {
      this.send("NOOP");
      FTPResponse var1 = this.getResponse();
      switch(var1.getCode()) {
      case 200:
         return;
      default:
         throw new FTPException(var1);
      }
   }

   protected FTPResponse readResponse() throws IOException {
      String var1 = this.in.readLine();
      if(var1 == null) {
         throw new ProtocolException("EOF");
      } else if(var1.length() < 4) {
         throw new ProtocolException(var1);
      } else {
         int var2 = parseCode(var1);
         if(var2 == -1) {
            throw new ProtocolException(var1);
         } else {
            char var3 = var1.charAt(3);
            FTPResponse var13;
            if(var3 == 32) {
               String var4 = var1.substring(4);
               var13 = new FTPResponse(var2, var4);
            } else {
               if(var3 != 45) {
                  throw new ProtocolException(var1);
               }

               String var5 = var1.substring(4);
               StringBuffer var6 = new StringBuffer(var5);
               StringBuffer var7 = var6.append('\n');

               while(true) {
                  String var8 = this.in.readLine();
                  if(var8 == null) {
                     throw new ProtocolException("EOF");
                  }

                  if(var8.length() >= 4 && var8.charAt(3) == 32 && parseCode(var8) == var2) {
                     String var9 = var8.substring(4);
                     String var10 = var6.toString();
                     var13 = new FTPResponse(var2, var9, var10);
                     break;
                  }

                  var6.append(var8);
                  StringBuffer var12 = var6.append('\n');
               }
            }

            return var13;
         }
      }
   }

   public void reinitialize() throws IOException {
      this.send("REIN");
      FTPResponse var1 = this.getResponse();
      switch(var1.getCode()) {
      case 220:
         if(this.dtp == null) {
            return;
         }

         this.dtp.complete();
         this.dtp = null;
         return;
      default:
         throw new FTPException(var1);
      }
   }

   public boolean removeDirectory(String var1) throws IOException {
      String var2 = "RMD " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      boolean var4;
      switch(var3.getCode()) {
      case 250:
         var4 = true;
         break;
      case 550:
         var4 = false;
         break;
      default:
         throw new FTPException(var3);
      }

      return var4;
   }

   public boolean rename(String var1, String var2) throws IOException {
      String var3 = "RNFR " + var1;
      this.send(var3);
      FTPResponse var4 = this.getResponse();
      boolean var5;
      switch(var4.getCode()) {
      case 350:
         String var6 = "RNTO " + var2;
         this.send(var6);
         FTPResponse var7 = this.getResponse();
         switch(var7.getCode()) {
         case 250:
            var5 = true;
            return var5;
         case 450:
         case 550:
            var5 = false;
            return var5;
         default:
            throw new FTPException(var7);
         }
      case 450:
      case 550:
         var5 = false;
         return var5;
      default:
         throw new FTPException(var4);
      }
   }

   public InputStream retrieve(String var1) throws IOException {
      if(this.dtp == null || this.transferMode == 1) {
         this.initialiseDTP();
      }

      String var2 = "RETR " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      switch(var3.getCode()) {
      case 125:
      case 150:
         return this.dtp.getInputStream();
      default:
         throw new FTPException(var3);
      }
   }

   protected void send(String var1) throws IOException {
      byte[] var2 = var1.getBytes("US-ASCII");
      this.out.write(var2);
      this.out.writeln();
      this.out.flush();
   }

   public void setFileStructure(int var1) throws IOException {
      StringBuffer var2 = new StringBuffer("STRU");
      StringBuffer var3 = var2.append(' ');
      switch(var1) {
      case 1:
         StringBuffer var5 = var2.append('F');
         break;
      case 2:
         StringBuffer var8 = var2.append('R');
         break;
      case 3:
         StringBuffer var9 = var2.append('P');
         break;
      default:
         String var4 = Integer.toString(var1);
         throw new IllegalArgumentException(var4);
      }

      String var6 = var2.toString();
      this.send(var6);
      FTPResponse var7 = this.getResponse();
      switch(var7.getCode()) {
      case 200:
         this.fileStructure = var1;
         return;
      default:
         throw new FTPException(var7);
      }
   }

   public void setPassive(boolean var1) throws IOException {
      if(this.passive != var1) {
         this.passive = var1;
         this.initialiseDTP();
      }
   }

   public void setRepresentationType(int var1) throws IOException {
      StringBuffer var2 = new StringBuffer("TYPE");
      StringBuffer var3 = var2.append(' ');
      switch(var1) {
      case 1:
         StringBuffer var5 = var2.append('A');
         break;
      case 2:
         StringBuffer var8 = var2.append('E');
         break;
      case 3:
         StringBuffer var9 = var2.append('I');
         break;
      default:
         String var4 = Integer.toString(var1);
         throw new IllegalArgumentException(var4);
      }

      String var6 = var2.toString();
      this.send(var6);
      FTPResponse var7 = this.getResponse();
      switch(var7.getCode()) {
      case 200:
         this.representationType = var1;
         return;
      default:
         throw new FTPException(var7);
      }
   }

   public void setTransferMode(int var1) throws IOException {
      StringBuffer var2 = new StringBuffer("MODE");
      StringBuffer var3 = var2.append(' ');
      switch(var1) {
      case 1:
         StringBuffer var5 = var2.append('S');
         break;
      case 2:
         StringBuffer var8 = var2.append('B');
         break;
      case 3:
         StringBuffer var9 = var2.append('C');
         break;
      default:
         String var4 = Integer.toString(var1);
         throw new IllegalArgumentException(var4);
      }

      String var6 = var2.toString();
      this.send(var6);
      FTPResponse var7 = this.getResponse();
      switch(var7.getCode()) {
      case 200:
         this.transferMode = var1;
         if(this.dtp == null) {
            return;
         }

         this.dtp.setTransferMode(var1);
         return;
      default:
         throw new FTPException(var7);
      }
   }

   public boolean starttls(boolean var1) throws IOException {
      EmptyX509TrustManager var2 = new EmptyX509TrustManager();
      return this.starttls(var1, var2);
   }

   public boolean starttls(boolean var1, TrustManager var2) throws IOException {
      boolean var8;
      label38: {
         SSLSocketFactory var5;
         FTPResponse var6;
         try {
            SSLContext var3 = SSLContext.getInstance("TLS");
            TrustManager[] var4 = new TrustManager[]{var2};
            var3.init((KeyManager[])null, var4, (SecureRandom)null);
            var5 = var3.getSocketFactory();
            this.send("AUTH TLS");
            var6 = this.getResponse();
            switch(var6.getCode()) {
            case 234:
               break;
            case 431:
            case 500:
            case 502:
            case 504:
            case 534:
               break label38;
            default:
               throw new FTPException(var6);
            }
         } catch (GeneralSecurityException var24) {
            var8 = false;
            return var8;
         }

         String var9 = this.socket.getInetAddress().getHostName();
         int var10 = this.socket.getPort();
         Socket var11 = this.socket;
         SSLSocket var12 = (SSLSocket)var5.createSocket(var11, var9, var10, (boolean)1);
         String[] var13 = new String[]{"TLSv1", "SSLv3"};
         var12.setEnabledProtocols(var13);
         var12.setUseClientMode((boolean)1);
         var12.startHandshake();
         this.send("PBSZ 2147483647");
         var6 = this.getResponse();
         switch(var6.getCode()) {
         case 200:
            StringBuilder var14 = (new StringBuilder()).append("PROT ");
            char var15;
            if(var1) {
               var15 = 80;
            } else {
               var15 = 67;
            }

            String var16 = var14.append(var15).toString();
            this.send(var16);
            var6 = this.getResponse();
            switch(var6.getCode()) {
            case 200:
               if(var1) {
                  InputStream var17 = var12.getInputStream();
                  BufferedInputStream var18 = new BufferedInputStream(var17);
                  CRLFInputStream var19 = new CRLFInputStream(var18);
                  LineInputStream var20 = new LineInputStream(var19);
                  this.in = var20;
                  OutputStream var21 = var12.getOutputStream();
                  BufferedOutputStream var22 = new BufferedOutputStream(var21);
                  CRLFOutputStream var23 = new CRLFOutputStream(var22);
                  this.out = var23;
               }

               var8 = true;
               return var8;
            case 503:
            case 504:
            case 536:
               var8 = false;
               return var8;
            default:
               throw new FTPException(var6);
            }
         case 501:
         case 503:
            var8 = false;
            return var8;
         default:
            throw new FTPException(var6);
         }
      }

      var8 = false;
      return var8;
   }

   public OutputStream store(String var1) throws IOException {
      if(this.dtp == null || this.transferMode == 1) {
         this.initialiseDTP();
      }

      String var2 = "STOR " + var1;
      this.send(var2);
      FTPResponse var3 = this.getResponse();
      switch(var3.getCode()) {
      case 125:
      case 150:
         return this.dtp.getOutputStream();
      default:
         throw new FTPException(var3);
      }
   }

   public String system() throws IOException {
      this.send("SYST");
      FTPResponse var1 = this.getResponse();
      switch(var1.getCode()) {
      case 215:
         String var2 = var1.getMessage();
         int var3 = var2.indexOf(32);
         if(var3 != -1) {
            var2 = var2.substring(0, var3);
         }

         return var2;
      default:
         throw new FTPException(var1);
      }
   }
}
