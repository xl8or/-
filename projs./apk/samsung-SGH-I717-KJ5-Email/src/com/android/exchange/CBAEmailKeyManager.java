package com.android.exchange;

import com.android.exchange.SyncManager;
import com.android.exchange.cba.SSLCBAClient;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509ExtendedKeyManager;

public class CBAEmailKeyManager extends X509ExtendedKeyManager {

   private String mAlias;
   private String mCertificatePwd;
   private PrivateKeyEntry mEntry = null;
   private SSLCBAClient mSslClient;


   public CBAEmailKeyManager(SSLCBAClient var1) {
      this.mSslClient = var1;
   }

   public String chooseClientAlias(String[] var1, Principal[] var2, Socket var3) {
      KeyStore var4 = this.mSslClient.getKeyStore();
      if(var4 != null) {
         try {
            String var5 = SyncManager.getDeviceId();
            this.mCertificatePwd = var5;
            String var6 = (String)var4.aliases().nextElement();
            this.mAlias = var6;
            String var7 = this.mAlias;
            char[] var8 = this.mCertificatePwd.toCharArray();
            PasswordProtection var9 = new PasswordProtection(var8);
            PrivateKeyEntry var10 = (PrivateKeyEntry)var4.getEntry(var7, var9);
            this.mEntry = var10;
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      } else {
         this.mCertificatePwd = null;
         this.mAlias = null;
         this.mEntry = null;
      }

      return this.mAlias;
   }

   public String chooseServerAlias(String var1, Principal[] var2, Socket var3) {
      return null;
   }

   public X509Certificate[] getCertificateChain(String var1) {
      X509Certificate[] var2;
      if(this.mEntry != null) {
         var2 = (X509Certificate[])((X509Certificate[])this.mEntry.getCertificateChain());
      } else {
         var2 = null;
      }

      return var2;
   }

   public String[] getClientAliases(String var1, Principal[] var2) {
      String[] var3;
      if(this.mAlias != null) {
         var3 = new String[1];
         String var4 = this.mAlias;
         var3[0] = var4;
      } else {
         var3 = null;
      }

      return var3;
   }

   public PrivateKey getPrivateKey(String var1) {
      PrivateKey var2;
      if(this.mEntry != null) {
         var2 = this.mEntry.getPrivateKey();
      } else {
         var2 = null;
      }

      return var2;
   }

   public String[] getServerAliases(String var1, Principal[] var2) {
      return null;
   }
}
