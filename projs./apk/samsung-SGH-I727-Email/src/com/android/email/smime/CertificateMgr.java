package com.android.email.smime;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.android.email.provider.EmailContent;
import com.android.email.smime.CertificateInfo;
import com.android.email.smime.CertificateManagerException;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

public class CertificateMgr {

   public static final String CERTIFICATE_ALIAS = "CERTIFICATE_ALIAS";
   public static final String KEYSTORE_PASSWORD = "KEYSTORE_PASSWORD";
   private static final String TAG = CertificateMgr.class.getSimpleName();
   private static final String mKeyStoreFileName = "keystore";
   private static final String mKeyStoreType = "PKCS12";
   private static CertificateMgr mSelf = null;
   private Context mContext;
   private KeyStore mKeyStore;
   private String mPassword;


   private CertificateMgr() {}

   public static final CertificateMgr getInstance(String param0, Context param1) throws CertificateManagerException {
      // $FF: Couldn't be decompiled
   }

   private void log(FileOutputStream var1, String var2) {
      if(var1 != null) {
         try {
            byte[] var3 = var2.getBytes();
            var1.write(var3);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      System.out.println(var2);
   }

   private void saveKeystore() throws CertificateManagerException {
      // $FF: Couldn't be decompiled
   }

   public Enumeration<String> getAliases() throws CertificateManagerException {
      Enumeration var1;
      Enumeration var2;
      label25: {
         try {
            if(this.mKeyStore != null) {
               var1 = this.mKeyStore.aliases();
               break label25;
            }
         } catch (Exception var4) {
            String var3 = var4.getMessage();
            throw new CertificateManagerException(var3);
         }

         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public X509Certificate getCertificate(String var1) throws CertificateManagerException {
      try {
         X509Certificate var2 = (X509Certificate)this.mKeyStore.getCertificate(var1);
         return var2;
      } catch (Exception var4) {
         String var3 = var4.getMessage();
         throw new CertificateManagerException(var3);
      }
   }

   public Certificate[] getCertificateChain(String var1) throws CertificateManagerException {
      try {
         Certificate[] var2 = this.mKeyStore.getCertificateChain(var1);
         return var2;
      } catch (Exception var4) {
         String var3 = var4.getMessage();
         throw new CertificateManagerException(var3);
      }
   }

   public CertificateInfo getCertificateInfo(String var1) throws CertificateManagerException {
      CertificateInfo var6;
      try {
         if(this.mKeyStore != null && this.mKeyStore != null) {
            X509Certificate var2 = (X509Certificate)this.mKeyStore.getCertificate(var1);
            boolean[] var3 = var2.getKeyUsage();
            List var4 = var2.getExtendedKeyUsage();
            String var5 = var2.getSubjectX500Principal().getName();
            var6 = new CertificateInfo(var3, var4, var5);
            return var6;
         }
      } catch (Exception var8) {
         String var7 = var8.getMessage();
         throw new CertificateManagerException(var7);
      }

      var6 = null;
      return var6;
   }

   public Key getPrivateKey(String var1) throws CertificateManagerException {
      Key var2 = null;

      boolean var5;
      try {
         if(this.mKeyStore == null) {
            return var2;
         }

         KeyStore var3 = this.mKeyStore;
         char[] var4 = this.mPassword.toCharArray();
         var2 = var3.getKey(var1, var4);
         var5 = var2 instanceof PrivateKey;
      } catch (Exception var7) {
         String var6 = var7.getMessage();
         throw new CertificateManagerException(var6);
      }

      if(!var5) {
         var2 = null;
      }

      return var2;
   }

   public String getSubject(String var1) throws CertificateManagerException {
      return this.getCertificate(var1).getSubjectDN().getName();
   }

   public String importCertificate(File param1, String param2) throws CertificateManagerException {
      // $FF: Couldn't be decompiled
   }

   public void printKsInfo(File param1, String param2, File param3) {
      // $FF: Couldn't be decompiled
   }

   public boolean removeCertificate(String var1) throws CertificateManagerException {
      try {
         if(this.mKeyStore != null) {
            this.mKeyStore.deleteEntry(var1);
            this.saveKeystore();
         }

         ContentResolver var2 = this.mContext.getContentResolver();
         String[] var3 = new String[]{"_id", "smimeOwnCertificateAlias"};
         String var4 = "smimeOwnCertificateAlias=?";
         Uri var5 = EmailContent.Account.CONTENT_URI;
         String[] var6 = new String[]{var1};
         Cursor var7 = var2.query(var5, var3, var4, var6, (String)null);
         if(var7 != null && var7.getCount() == 1) {
            boolean var8 = var7.moveToNext();
            ContentValues var9 = new ContentValues();
            var9.put("smimeOwnCertificateAlias", (String)null);
            Uri var10 = EmailContent.Account.CONTENT_URI;
            String[] var11 = new String[]{var1};
            var2.update(var10, var9, var4, var11);
         }

         return true;
      } catch (Exception var14) {
         String var13 = var14.getMessage();
         throw new CertificateManagerException(var13);
      }
   }

   public void renameCertificate(String var1, String var2) throws CertificateManagerException {
      if(!TextUtils.isEmpty(var1)) {
         if(!TextUtils.isEmpty(var2)) {
            if(!var2.equals(var1)) {
               try {
                  KeyStore var3 = this.mKeyStore;
                  char[] var4 = this.mPassword.toCharArray();
                  Key var5 = var3.getKey(var2, var4);
                  Certificate[] var6 = this.mKeyStore.getCertificateChain(var2);
                  KeyStore var7 = this.mKeyStore;
                  char[] var8 = this.mPassword.toCharArray();
                  var7.setKeyEntry(var1, var5, var8, var6);
                  this.mKeyStore.deleteEntry(var2);
                  this.saveKeystore();
               } catch (Exception var10) {
                  String var9 = var10.getMessage();
                  throw new CertificateManagerException(var9);
               }
            }
         }
      }
   }
}
