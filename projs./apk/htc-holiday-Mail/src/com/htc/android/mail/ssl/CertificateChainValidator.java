package com.htc.android.mail.ssl;

import android.content.Context;
import com.htc.android.mail.Mail;
import com.htc.android.mail.server.Server;
import com.htc.android.mail.ssl.MailSslError;
import java.io.File;
import java.io.IOException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;

public class CertificateChainValidator {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "CertificateChainValidator";
   private static CertificateChainValidator sInstance;
   private X509TrustManager mDefaultTrustManager;
   private long mLastModified;
   private X509TrustManager mOwnTrustManager;


   private CertificateChainValidator(Context var1) {
      File var2 = null;
      if(var1 != null) {
         label13: {
            File var3;
            try {
               var3 = var1.getFileStreamPath("keystore");
            } catch (Exception var5) {
               break label13;
            }

            var2 = var3;
         }
      }

      this.init(var1, var2);
   }

   private void closeSocketThrowException(SSLSocket var1, String var2) throws SSLHandshakeException, IOException {
      if(var1 != null) {
         var1.close();
      }

      throw new SSLHandshakeException(var2);
   }

   private void closeSocketThrowException(SSLSocket var1, String var2, String var3) throws SSLHandshakeException, IOException {
      String var4;
      if(var2 != null) {
         var4 = var2;
      } else {
         var4 = var3;
      }

      this.closeSocketThrowException(var1, var4);
   }

   public static CertificateChainValidator getInstance(Context var0) {
      if(sInstance == null) {
         sInstance = new CertificateChainValidator(var0);
      } else {
         CertificateChainValidator var1 = sInstance;
         synchronized(var1) {
            sInstance.reinit(var0);
         }
      }

      return sInstance;
   }

   private void init(Context param1, File param2) {
      // $FF: Couldn't be decompiled
   }

   public MailSslError doHandshakeAndValidateServerCertificates(Server param1, SSLSocket param2, String param3) throws SSLHandshakeException, IOException {
      // $FF: Couldn't be decompiled
   }

   public void reinit(Context var1) {
      if(var1 != null) {
         try {
            File var2 = var1.getFileStreamPath("keystore");
            long var3 = var2.lastModified();
            if(this.mLastModified < var3) {
               this.mLastModified = var3;
               this.init(var1, var2);
            }
         } catch (Exception var6) {
            ;
         }
      }
   }
}
