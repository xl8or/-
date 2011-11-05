package com.android.email.mail.transport;

import android.util.Log;
import com.android.email.Email;
import com.android.email.mail.CertificateValidationException;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Transport;
import com.android.email.mail.transport.SSLUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;

public class MailTransport implements Transport {

   public static final int SOCKET_CONNECT_TIMEOUT = 10000;
   public static final int SOCKET_READ_TIMEOUT = 60000;
   private static final String TAG = "MailTransport";
   private int mConnectionSecurity;
   private String mDebugLabel;
   private String mHost;
   private InputStream mIn;
   private OutputStream mOut;
   private int mPort;
   private Socket mSocket;
   private boolean mTrustCertificates;
   private String[] mUserInfoParts;


   public MailTransport(String var1) {
      this.mDebugLabel = var1;
   }

   public boolean canTrustAllCertificates() {
      return this.mTrustCertificates;
   }

   public boolean canTrySslSecurity() {
      boolean var1;
      if(this.mConnectionSecurity == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean canTryTlsSecurity() {
      boolean var1;
      if(this.mConnectionSecurity == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void close() {
      try {
         this.mIn.close();
      } catch (Exception var6) {
         ;
      }

      try {
         this.mOut.close();
      } catch (Exception var5) {
         ;
      }

      try {
         this.mSocket.close();
      } catch (Exception var4) {
         ;
      }

      this.mIn = null;
      this.mOut = null;
      this.mSocket = null;
   }

   public String getHost() {
      return this.mHost;
   }

   public InputStream getInputStream() {
      return this.mIn;
   }

   public OutputStream getOutputStream() {
      return this.mOut;
   }

   public int getPort() {
      if(this.mPort > '\uffff') {
         this.mPort = '\uffff';
      }

      return this.mPort;
   }

   public int getSecurity() {
      return this.mConnectionSecurity;
   }

   public String[] getUserInfoParts() {
      return this.mUserInfoParts;
   }

   public boolean isOpen() {
      boolean var1;
      if(this.mIn != null && this.mOut != null && this.mSocket != null && this.mSocket.isConnected() && !this.mSocket.isClosed()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Transport newInstanceWithConfiguration() {
      String var1 = this.mDebugLabel;
      MailTransport var2 = new MailTransport(var1);
      String var3 = this.mDebugLabel;
      var2.mDebugLabel = var3;
      String var4 = this.mHost;
      var2.mHost = var4;
      int var5 = this.mPort;
      var2.mPort = var5;
      if(this.mUserInfoParts != null) {
         String[] var6 = (String[])this.mUserInfoParts.clone();
         var2.mUserInfoParts = var6;
      }

      int var7 = this.mConnectionSecurity;
      var2.mConnectionSecurity = var7;
      boolean var8 = this.mTrustCertificates;
      var2.mTrustCertificates = var8;
      return var2;
   }

   public void open() throws MessagingException, CertificateValidationException {
      if(Email.DEBUG) {
         StringBuilder var1 = (new StringBuilder()).append("*** ");
         String var2 = this.mDebugLabel;
         StringBuilder var3 = var1.append(var2).append(" open ");
         String var4 = this.getHost();
         StringBuilder var5 = var3.append(var4).append(":");
         String var6 = String.valueOf(this.getPort());
         String var7 = var5.append(var6).toString();
         int var8 = Log.d("Email", var7);
      }

      try {
         String var9 = this.getHost();
         int var10 = this.getPort();
         InetSocketAddress var11 = new InetSocketAddress(var9, var10);
         if(this.canTrySslSecurity()) {
            Socket var12 = SSLUtils.getSSLSocketFactory(this.canTrustAllCertificates()).createSocket();
            this.mSocket = var12;
         } else {
            Socket var22 = new Socket();
            this.mSocket = var22;
         }

         this.mSocket.connect(var11, 10000);
         InputStream var13 = this.mSocket.getInputStream();
         BufferedInputStream var14 = new BufferedInputStream(var13, 1024);
         this.mIn = var14;
         OutputStream var15 = this.mSocket.getOutputStream();
         BufferedOutputStream var16 = new BufferedOutputStream(var15, 512);
         this.mOut = var16;
         this.mSocket.setSoTimeout('\uea60');
         StringBuilder var17 = (new StringBuilder()).append("open :: socket open");
         InputStream var18 = this.mIn;
         StringBuilder var19 = var17.append(var18).append(" | ");
         OutputStream var20 = this.mOut;
         String var21 = var19.append(var20).toString();
         Email.logs("MailTransport", var21);
      } catch (SSLException var35) {
         if(Email.DEBUG) {
            String var24 = var35.toString();
            int var25 = Log.d("Email", var24);
         }

         String var26 = var35.getMessage();
         throw new CertificateValidationException(var26, var35);
      } catch (IOException var36) {
         if(Email.DEBUG) {
            String var28 = var36.toString();
            int var29 = Log.d("Email", var28);
         }

         String var30 = var36.toString();
         throw new MessagingException(1, var30);
      } catch (NullPointerException var37) {
         if(Email.DEBUG) {
            String var32 = var37.toString();
            int var33 = Log.d("Email", var32);
         }

         String var34 = var37.toString();
         throw new MessagingException(1, var34);
      }
   }

   public String readLine() throws IOException {
      StringBuffer var1 = new StringBuffer();
      InputStream var2 = this.getInputStream();
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         int var4;
         while(true) {
            var4 = var2.read();
            if(var4 == -1) {
               break;
            }

            if((char)var4 != 13) {
               if((char)var4 == 10) {
                  break;
               }

               char var9 = (char)var4;
               var1.append(var9);
            }
         }

         if(var4 == -1 && Email.DEBUG) {
            int var5 = Log.d("Email", "End of stream reached while trying to read line.");
         }

         String var6 = var1.toString();
         if(var6.length() == 0) {
            var6 = null;
         }

         if(Email.DEBUG) {
            String var7 = "<<< " + var6;
            int var8 = Log.d("Email", var7);
         }

         var3 = var6;
      }

      return var3;
   }

   public void reopenTls() throws MessagingException {
      try {
         SSLSocketFactory var1 = SSLUtils.getSSLSocketFactory(this.canTrustAllCertificates());
         Socket var2 = this.mSocket;
         String var3 = this.getHost();
         int var4 = this.getPort();
         Socket var5 = var1.createSocket(var2, var3, var4, (boolean)1);
         this.mSocket = var5;
         this.mSocket.setSoTimeout('\uea60');
         InputStream var6 = this.mSocket.getInputStream();
         BufferedInputStream var7 = new BufferedInputStream(var6, 1024);
         this.mIn = var7;
         OutputStream var8 = this.mSocket.getOutputStream();
         BufferedOutputStream var9 = new BufferedOutputStream(var8, 512);
         this.mOut = var9;
      } catch (SSLException var18) {
         if(Email.DEBUG) {
            String var11 = var18.toString();
            int var12 = Log.d("Email", var11);
         }

         String var13 = var18.getMessage();
         throw new CertificateValidationException(var13, var18);
      } catch (IOException var19) {
         if(Email.DEBUG) {
            String var15 = var19.toString();
            int var16 = Log.d("Email", var15);
         }

         String var17 = var19.toString();
         throw new MessagingException(1, var17);
      }
   }

   public void setSecurity(int var1, boolean var2) {
      this.mConnectionSecurity = var1;
      this.mTrustCertificates = var2;
   }

   public void setSoTimeout(int var1) throws SocketException {
      this.mSocket.setSoTimeout(var1);
   }

   public void setUri(URI var1, int var2) {
      String var3 = var1.getHost();
      this.mHost = var3;
      this.mPort = var2;
      if(var1.getPort() != -1) {
         int var4 = var1.getPort();
         this.mPort = var4;
      }

      if(var1.getUserInfo() != null) {
         String[] var5 = var1.getUserInfo().split(":", 2);
         this.mUserInfoParts = var5;
      }
   }

   public void writeLine(String var1, String var2) throws IOException {
      if(Email.DEBUG) {
         if(var2 != null && !Email.DEBUG_SENSITIVE) {
            String var3 = ">>> " + var2;
            int var4 = Log.d("Email", var3);
         } else {
            String var7 = ">>> " + var1;
            int var8 = Log.d("Email", var7);
         }
      }

      OutputStream var5 = this.getOutputStream();
      byte[] var6 = var1.getBytes();
      var5.write(var6);
      var5.write(13);
      var5.write(10);
      var5.flush();
   }
}
