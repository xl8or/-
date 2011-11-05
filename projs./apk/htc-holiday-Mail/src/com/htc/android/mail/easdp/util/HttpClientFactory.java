package com.htc.android.mail.easdp.util;

import android.content.Context;
import android.net.Proxy;
import android.net.http.AndroidHttpClient;
import com.htc.android.mail.easdp.EASDirectpush;
import com.htc.android.mail.easdp.util.ConnectCallback;
import com.htc.android.mail.eassvc.util.AccountUtil;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.android.mail.eassvc.util.PowerSavingUtil;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSession;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpParams;

public class HttpClientFactory {

   private static final boolean DEBUG = true;
   private static final String PASS_ALL_CERTIFICATE = "eas_pass_certificate";
   private static final String TAG = "EAS_HTTP";
   private static String[] blockProxyList;
   private static Context mContext = null;


   static {
      String[] var0 = new String[]{"web.wireless.bell.ca"};
      blockProxyList = var0;
   }

   public HttpClientFactory() {}

   public static AndroidHttpClient createHttpClient(String var0, long var1) {
      return createHttpClient(var0, var1, (ConnectCallback)null);
   }

   public static AndroidHttpClient createHttpClient(String param0, long param1, ConnectCallback param3) {
      // $FF: Couldn't be decompiled
   }

   public static X509Certificate getCertificate(SSLSession param0, boolean param1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   private static File getPassAllCertificatePath(long var0) {
      File var3;
      if(var0 > 0L) {
         File var2 = AccountUtil.getAccountConfigPath(mContext, var0);
         var3 = new File(var2, "eas_pass_certificate");
      } else {
         File var4 = mContext.getDir("config", 0);
         var3 = new File(var4, "eas_pass_certificate");
      }

      return var3;
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

   public static boolean isUseProxy(Context var0, long var1) {
      boolean var3;
      if(proxyInBlockList(var0)) {
         var3 = false;
      } else if(var1 < 0L) {
         var3 = false;
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

   public static void setContext(Context var0) {
      mContext = var0;
   }

   static class DpSSLSocketFactory implements LayeredSocketFactory {

      com.htc.android.mail.ssl.SSLSocketFactory mailSocketFactory;
      org.apache.http.conn.ssl.SSLSocketFactory socketFactory;


      public DpSSLSocketFactory() {}

      private LayeredSocketFactory getSocketFactory() {
         Object var1;
         if(this.socketFactory != null) {
            var1 = this.socketFactory;
         } else if(this.mailSocketFactory != null) {
            var1 = this.mailSocketFactory;
         } else {
            var1 = null;
         }

         return (LayeredSocketFactory)var1;
      }

      public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException, UnknownHostException, ConnectTimeoutException {
         EASLog.v("EAS_HTTP", "> connectSocket(..)");
         LayeredSocketFactory var7 = this.getSocketFactory();
         Socket var14 = var7.connectSocket(var1, var2, var3, var4, var5, var6);
         EASLog.v("EAS_HTTP", "< connectSocket(..)");
         return var14;
      }

      public Socket createSocket() throws IOException {
         EASLog.v("EAS_HTTP", "> connectSocket()");
         Socket var1 = this.getSocketFactory().createSocket();
         EASLog.v("EAS_HTTP", "< connectSocket()");
         return var1;
      }

      public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
         EASLog.v("EAS_HTTP", "> createSocket()");
         Socket var5 = this.getSocketFactory().createSocket(var1, var2, var3, var4);
         EASLog.v("EAS_HTTP", "< createSocket()");
         if(EASDirectpush.isScreenOff()) {
            PowerSavingUtil.changeRadioDormantTimer(1, 1);
         }

         return var5;
      }

      public X509HostnameVerifier getHostnameVerifier() {
         X509HostnameVerifier var1;
         if(this.socketFactory != null) {
            var1 = this.socketFactory.getHostnameVerifier();
         } else if(this.mailSocketFactory != null) {
            var1 = this.mailSocketFactory.getHostnameVerifier();
         } else {
            var1 = null;
         }

         return var1;
      }

      public boolean isSecure(Socket var1) throws IllegalArgumentException {
         return this.getSocketFactory().isSecure(var1);
      }

      public void setSocketFactory(com.htc.android.mail.ssl.SSLSocketFactory var1) {
         this.mailSocketFactory = var1;
      }

      public void setSocketFactory(org.apache.http.conn.ssl.SSLSocketFactory var1) {
         this.socketFactory = var1;
      }
   }
}
