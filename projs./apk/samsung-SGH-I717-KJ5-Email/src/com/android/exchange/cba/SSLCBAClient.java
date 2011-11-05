package com.android.exchange.cba;

import android.content.Context;
import android.util.Log;
import com.android.email.certificateManager.CertificateMgr;
import com.android.exchange.CBAEmailKeyManager;
import com.android.exchange.SyncManager;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLCBAClient {

   private static final TrustManager[] INSECURE_TRUST_MANAGER;
   public static final String PKCS12 = "PKCS12";
   private static final String TAG = "SSLCBAClient";
   public static final String TLS = "TLS";
   public static KeyStore gTrustStore;
   private Hashtable<String, KeyStore> keyStoreMap;
   String mAlias;
   Context mContext;
   boolean mInsecure;
   String mKeyStorePassword;
   SSLSocketFactory mSocketFactory;
   String mTempKeyStorePassword;


   static {
      TrustManager[] var0 = new TrustManager[1];
      SSLCBAClient.1 var1 = new SSLCBAClient.1();
      var0[0] = var1;
      INSECURE_TRUST_MANAGER = var0;
   }

   private SSLCBAClient(Context var1) {
      this.mContext = var1;
   }

   private String chooseAlias() {
      return SyncManager.getAliasFromMap(Thread.currentThread().getId());
   }

   public static SSLSocketFactory getSSLSocketFactory(Context var0, String var1, boolean var2) {
      SSLCBAClient var3 = new SSLCBAClient(var0);
      var3.init(var1, var2);
      return var3.mSocketFactory;
   }

   private KeyStore setupKeyStore(String var1, String var2) {
      KeyStore var12;
      if(var1 != null) {
         KeyStore var3;
         try {
            var3 = KeyStore.getInstance("PKCS12");
            char[] var4 = var2.toCharArray();
            var3.load((InputStream)null, var4);
            String var5 = SyncManager.getDeviceId();
            this.mKeyStorePassword = var5;
            String var6 = this.mKeyStorePassword;
            Context var7 = this.mContext;
            CertificateMgr var8 = CertificateMgr.getInstance(var6, var7);
            Key var9 = var8.getPrivateKey(var1);
            Certificate[] var10 = var8.getCertificateChain(var1);
            char[] var11 = var1.toCharArray();
            var3.setKeyEntry(var1, var9, var11, var10);
         } catch (Exception var15) {
            String var13 = var15.getMessage();
            int var14 = Log.d("SSL", var13);
            var12 = null;
            return var12;
         }

         var12 = var3;
      } else {
         var12 = null;
      }

      return var12;
   }

   private SSLContext setupSSLContext(KeyStore var1, String var2, KeyStore var3) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      var4.init(var3);
      KeyManager[] var5;
      if(var1 == null) {
         var5 = new KeyManager[1];
         CBAEmailKeyManager var6 = new CBAEmailKeyManager(this);
         var5[0] = var6;
      } else {
         KeyManagerFactory var9 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
         char[] var10 = var2.toCharArray();
         var9.init(var1, var10);
         var5 = var9.getKeyManagers();
      }

      SSLContext var7 = SSLContext.getInstance("TLS");
      if(!this.mInsecure) {
         TrustManager[] var8 = var4.getTrustManagers();
         var7.init(var5, var8, (SecureRandom)null);
      } else {
         TrustManager[] var11 = INSECURE_TRUST_MANAGER;
         var7.init(var5, var11, (SecureRandom)null);
      }

      return var7;
   }

   private KeyStore setupTrustStore() {
      synchronized(this){}
      return null;
   }

   public KeyStore getKeyStore() {
      if(this.keyStoreMap == null) {
         Hashtable var1 = new Hashtable();
         this.keyStoreMap = var1;
      }

      String var2 = this.chooseAlias();
      KeyStore var8;
      if(var2 != null) {
         String var3 = "Alias is " + var2 + "in getKeyStore()";
         int var4 = Log.v("SSLCBAClient", var3);

         KeyStore var5;
         try {
            var5 = (KeyStore)this.keyStoreMap.get(var2);
         } catch (Exception var10) {
            var10.printStackTrace();
         }

         if(var5 == null) {
            String var6 = this.mKeyStorePassword;
            var5 = this.setupKeyStore(var2, var6);
            if(var2 != null) {
               this.keyStoreMap.put(var2, var5);
            } else {
               int var9 = Log.e("SSLCBAClient", "Alias is NULL! in getKeyStore()");
            }
         }

         var8 = var5;
      } else {
         var8 = null;
      }

      return var8;
   }

   public void init(String var1, boolean var2) {
      try {
         this.mAlias = var1;
         String var3 = SyncManager.getDeviceId();
         this.mKeyStorePassword = var3;
         this.mInsecure = var2;
         this.mTempKeyStorePassword = var1;
         String var4 = this.mAlias;
         String var5 = this.mTempKeyStorePassword;
         KeyStore var6 = this.setupKeyStore(var4, var5);
         KeyStore var7 = this.setupTrustStore();
         String var8 = this.mTempKeyStorePassword;
         SSLSocketFactory var9 = this.setupSSLContext(var6, var8, var7).getSocketFactory();
         this.mSocketFactory = var9;
      } catch (Exception var13) {
         String var10 = var13.getMessage();
         int var11 = Log.d("SSL", var10);
         SSLSocketFactory var12 = (SSLSocketFactory)SSLSocketFactory.getDefault();
         this.mSocketFactory = var12;
      }
   }

   public void setKeyStore(KeyStore var1, String var2) {
      String var3 = this.mKeyStorePassword;
      KeyStore var4 = this.setupKeyStore(var2, var3);
      this.keyStoreMap.put(var2, var4);
   }

   static class 1 implements X509TrustManager {

      1() {}

      public void checkClientTrusted(X509Certificate[] var1, String var2) {}

      public void checkServerTrusted(X509Certificate[] var1, String var2) {}

      public X509Certificate[] getAcceptedIssuers() {
         return null;
      }
   }
}
