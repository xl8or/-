package com.android.exchange;

import android.content.Context;
import android.util.Log;
import com.android.exchange.SyncManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509ExtendedKeyManager;

public class EmailKeyManager extends X509ExtendedKeyManager {

   private static final String mAliasName = "emailalias";
   private static final String mCertificateExt = ".p12";
   private static final String mCertificatePwd = "emailcert";
   private static final String mCertificateType = "PKCS12";
   private String mAlias = null;
   private PrivateKeyEntry mEntry = null;


   public EmailKeyManager() {}

   public String chooseClientAlias(String[] var1, Principal[] var2, Socket var3) {
      long var4 = SyncManager.getAccountId(Thread.currentThread().getId());
      if(var4 != 65535L) {
         try {
            Context var6 = SyncManager.getContext();
            StringBuilder var7 = new StringBuilder();
            String var8 = Long.toString(var4);
            String var9 = var7.append(var8).append(".p12").toString();
            FileInputStream var10 = var6.openFileInput(var9);
            KeyStore var11 = KeyStore.getInstance("PKCS12");
            char[] var12 = "emailcert".toCharArray();
            var11.load(var10, var12);
            var10.close();
            char[] var13 = "emailcert".toCharArray();
            PasswordProtection var14 = new PasswordProtection(var13);
            PrivateKeyEntry var15 = (PrivateKeyEntry)var11.getEntry("emailalias", var14);
            this.mEntry = var15;
            this.mAlias = "emailalias";
            String var16 = "Found Certificate for account id " + var4;
            int var17 = Log.v("EmailKeyManager", var16);
         } catch (Exception var21) {
            String var19 = "No Certificate found for account id " + var4;
            int var20 = Log.v("EmailKeyManager", var19);
         }
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

   public static final class EmailKeyStore {

      private static final String TAG = "EmailKeyStore";


      public EmailKeyStore() {}

      private static PrivateKeyEntry extractPkcs12(byte[] param0, String param1) {
         // $FF: Couldn't be decompiled
      }

      public static void installCertificate(Context var0, byte[] var1, String var2, long var3) throws KeyStoreException, IllegalArgumentException, IOException, NoSuchAlgorithmException, CertificateException {
         if(var0 != null && var1 != null && var2 != null && var3 >= 1L) {
            KeyStore var5 = KeyStore.getInstance("PKCS12");
            char[] var6 = "emailcert".toCharArray();
            var5.load((InputStream)null, var6);
            PrivateKeyEntry var7 = extractPkcs12(var1, var2);
            if(var7 == null) {
               throw new KeyStoreException("Failed to extract pkcs12 file");
            } else {
               StringBuilder var8 = new StringBuilder();
               String var9 = Long.toString(var3);
               String var10 = var8.append(var9).append(".p12").toString();
               var0.deleteFile(var10);
               StringBuilder var12 = new StringBuilder();
               String var13 = Long.toString(var3);
               String var14 = var12.append(var13).append(".p12").toString();
               FileOutputStream var15 = var0.openFileOutput(var14, 0);
               char[] var16 = "emailcert".toCharArray();
               PasswordProtection var17 = new PasswordProtection(var16);
               var5.setEntry("emailalias", var7, var17);
               char[] var18 = "emailcert".toCharArray();
               var5.store(var15, var18);
               var15.close();
            }
         } else {
            throw new IllegalArgumentException("Invalid arguments");
         }
      }

      public static void removeCertificate(Context var0, long var1) {
         String var3 = "Deleting certificate file for account " + var1;
         int var4 = Log.d("EmailKeyStore", var3);
         StringBuilder var5 = new StringBuilder();
         String var6 = Long.toString(var1);
         String var7 = var5.append(var6).append(".p12").toString();
         var0.deleteFile(var7);
      }

      public static void renameCertificate(Context var0, long var1, long var3) {
         String var5 = "Renaming certificate file to account" + var1;
         int var6 = Log.d("EmailKeyStore", var5);
         StringBuilder var7 = new StringBuilder();
         String var8 = Long.toString(var3);
         String var9 = var7.append(var8).append(".p12").toString();
         File var10 = var0.getFileStreamPath(var9);
         StringBuilder var11 = new StringBuilder();
         String var12 = Long.toString(var1);
         String var13 = var11.append(var12).append(".p12").toString();
         File var14 = var0.getFileStreamPath(var13);
         var10.renameTo(var14);
      }
   }
}
