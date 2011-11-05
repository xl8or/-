package com.htc.android.mail.eassvc.common;

import android.content.Context;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASCommon;
import com.htc.android.mail.eassvc.common.EASHostnameVerifier;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.util.AccountUtil;
import com.htc.android.mail.eassvc.util.CertificateChainValidator;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.android.mail.server.Server;
import com.htc.android.mail.ssl.MailSslError;
import com.htc.android.mail.ssl.SimpleX509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class HttpClientFactory {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   public static final String EAS_USE_PROXY_FILE_GLOBAL = "/data/data/com.htc.android.mail/app_config/eas_use_proxy_global";
   private static final String PASS_ALL_CERTIFICATE = "eas_pass_certificate";
   private static final String TAG = "EAS_HTTP";
   private static final String USER_AGENT = EASSyncCommon.EAS_HTTP_AGENT;
   private static String[] blockProxyList;


   static {
      String[] var0 = new String[]{"web.wireless.bell.ca"};
      blockProxyList = var0;
   }

   public HttpClientFactory() {}

   public static AndroidHttpClient createHttpClient(Context var0) {
      return createHttpClient(var0, 65535L);
   }

   public static AndroidHttpClient createHttpClient(Context param0, long param1) {
      // $FF: Couldn't be decompiled
   }

   public static EASCommon.CertificateError getCertificate(Context var0, String var1) {
      MailSslError var2 = null;
      TrustManager[] var3 = null;

      label44: {
         TrustManager[] var5;
         MailSslError var13;
         label43: {
            Exception var18;
            label48: {
               SSLContext var4;
               try {
                  var4 = SSLContext.getInstance("TLS");
                  var5 = new TrustManager[1];
                  SimpleX509TrustManager var6 = new SimpleX509TrustManager();
                  var5[0] = var6;
               } catch (Exception var23) {
                  var18 = var23;
                  break label48;
               }

               Object var7 = null;

               try {
                  SecureRandom var8 = new SecureRandom();
                  var4.init((KeyManager[])var7, var5, var8);
                  Socket var9 = var4.getSocketFactory().createSocket();
                  InetSocketAddress var10 = new InetSocketAddress(var1, 443);
                  var9.connect(var10, 30000);
                  var2 = null;
                  var9.setSoTimeout(30000);
                  CertificateChainValidator var11 = CertificateChainValidator.getInstance(var0);
                  SSLSocket var12 = (SSLSocket)var9;
                  var13 = var11.doHandshakeAndValidateServerCertificates((Server)null, var12, var1);
                  break label43;
               } catch (Exception var22) {
                  var18 = var22;
                  var3 = var5;
               }
            }

            var18.printStackTrace();
            break label44;
         }

         var2 = var13;
         var3 = var5;
      }

      EASCommon.CertificateError var17;
      if(var2 == null) {
         if(var3 != null && ((SimpleX509TrustManager)var3[0]).chain != null) {
            String var14 = "getCertificate(): trustManagers=" + var3;
            EASLog.v("EAS_HTTP", var14);
            EASCommon.CertificateError var15 = new EASCommon.CertificateError();
            X509Certificate var16 = ((SimpleX509TrustManager)var3[0]).chain[0];
            var15.certificate = var16;
            var15.errorCode = '\ufff3';
            var17 = var15;
         } else {
            var17 = null;
         }
      } else {
         String var19 = var2.toString();
         EASLog.v("EAS_HTTP", var19);
         EASCommon.CertificateError var20 = new EASCommon.CertificateError();
         X509Certificate var21 = var2.getCertificate().getX509Certificate();
         var20.certificate = var21;
         switch(var2.getPrimaryError()) {
         case 0:
            var20.errorCode = '\ufff4';
            break;
         case 1:
            var20.errorCode = '\ufff6';
            break;
         case 2:
            var20.errorCode = '\ufff5';
            break;
         default:
            var20.errorCode = '\ufff3';
         }

         var17 = var20;
      }

      return var17;
   }

   public static X509Certificate getCertificate(SSLSession param0, boolean param1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   private static File getPassAllCertificatePath(Context var0, long var1) {
      File var4;
      if(var1 > 0L) {
         File var3 = AccountUtil.getAccountConfigPath(var0, var1);
         var4 = new File(var3, "eas_pass_certificate");
      } else {
         File var5 = var0.getDir("config", 0);
         var4 = new File(var5, "eas_pass_certificate");
      }

      return var4;
   }

   public static String getPassCertificateAccount(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isDeviceSetProxy(Context var0) {
      boolean var1;
      if(proxyInBlockList(var0)) {
         var1 = false;
      } else {
         String var2 = Proxy.getHost(var0);
         int var3 = Proxy.getPort(var0);
         if(var2 != null && var2.length() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public static boolean isPassAllCertificate(Context var0, long var1) {
      File var3 = getPassAllCertificatePath(var0, var1);
      boolean var4;
      if(var3 != null && var3.exists()) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static boolean isUseProxy(Context var0) {
      boolean var1;
      if(proxyInBlockList(var0)) {
         var1 = false;
      } else {
         boolean var2 = false;
         String var3 = "eas_use_proxy";
         synchronized(var3) {
            if((new File("/data/data/com.htc.android.mail/app_config/eas_use_proxy_global")).exists()) {
               var2 = true;
            }

            var1 = var2;
         }
      }

      return var1;
   }

   public static boolean isUseProxy(Context var0, long var1) {
      boolean var3;
      if(proxyInBlockList(var0)) {
         var3 = false;
      } else if(var1 < 0L) {
         var3 = isUseProxy(var0);
      } else {
         boolean var4 = false;
         String var5 = "eas_use_proxy";
         synchronized("eas_use_proxy") {
            File var6 = AccountUtil.getAccountConfigPath(var0, var1);
            if((new File(var6, "eas_use_proxy")).exists()) {
               var4 = true;
            }

            var3 = var4;
         }
      }

      return var3;
   }

   public static boolean proxyInBlockList(Context var0) {
      String var1 = Proxy.getHost(var0);
      String[] var2 = blockProxyList;
      int var3 = var2.length;
      int var4 = 0;

      boolean var5;
      while(true) {
         if(var4 >= var3) {
            var5 = false;
            break;
         }

         if(var2[var4].equalsIgnoreCase(var1)) {
            var5 = true;
            break;
         }

         ++var4;
      }

      return var5;
   }

   public static void saveCertificateError(AndroidHttpClient var0, Context var1, String var2, int var3, Exception var4) {
      Scheme var5 = var0.getConnectionManager().getSchemeRegistry().get("https");
      if(var5 == null) {
         EASLog.e("EAS_HTTP", "saveCertificateError(): Error! https scheme is null.");
         var4.printStackTrace();
      } else {
         EASHostnameVerifier var6 = null;
         if(var5.getSocketFactory() instanceof SSLSocketFactory) {
            SSLSocketFactory var7 = (SSLSocketFactory)var5.getSocketFactory();
            if(var7.getHostnameVerifier() instanceof EASHostnameVerifier) {
               var6 = (EASHostnameVerifier)var7.getHostnameVerifier();
            }
         } else if(var5.getSocketFactory() instanceof com.htc.android.mail.eassvc.util.SSLSocketFactory) {
            com.htc.android.mail.eassvc.util.SSLSocketFactory var13 = (com.htc.android.mail.eassvc.util.SSLSocketFactory)var5.getSocketFactory();
            if(var13.getHostnameVerifier() instanceof EASHostnameVerifier) {
               var6 = (EASHostnameVerifier)var13.getHostnameVerifier();
            }
         }

         if(var6 == null) {
            StringBuilder var8 = (new StringBuilder()).append("saveCertificateError(): Error! cannot get hostnameVerifier,factory=");
            SocketFactory var9 = var5.getSocketFactory();
            String var10 = var8.append(var9).toString();
            EASLog.e("EAS_HTTP", var10);
         } else {
            EASCommon.CertificateError var11 = new EASCommon.CertificateError();
            if(var3 == 518) {
               var11.errorCode = '\ufff6';
            } else if(var3 == 517) {
               var11.errorCode = '\ufff5';
            } else if(var3 == 519) {
               var11.errorCode = '\ufff4';
            } else {
               var11.errorCode = '\ufff3';
            }

            X509Certificate var12 = var6.certificate;
            var11.certificate = var12;
            if(var11.certificate == null || var6.host == null || !var6.host.equals(var2)) {
               var11 = getCertificate(var1, var2);
            }

            saveCertificateError(var11);
         }
      }
   }

   private static void saveCertificateError(EASCommon.CertificateError param0) {
      // $FF: Couldn't be decompiled
   }

   public static void setPassAllCertificate(Context var0, long var1, String var3) {
      synchronized("eas_pass_certificate") {
         File var4 = getPassAllCertificatePath(var0, var1);
         if(var3 == null) {
            if(var4.exists()) {
               boolean var5 = var4.delete();
            }
         } else {
            try {
               if(!var4.exists()) {
                  boolean var6 = var4.createNewFile();
               }

               FileOutputStream var7 = new FileOutputStream(var4);
               byte[] var8 = var3.getBytes();
               var7.write(var8);
               var7.close();
            } catch (Exception var11) {
               var11.printStackTrace();
            }
         }

      }
   }

   public static void setUseProxy(Context param0, long param1, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   public static void setUseProxy(Context param0, boolean param1) {
      // $FF: Couldn't be decompiled
   }
}
