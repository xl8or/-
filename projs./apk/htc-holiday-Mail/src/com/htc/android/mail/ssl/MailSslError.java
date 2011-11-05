package com.htc.android.mail.ssl;

import com.htc.android.mail.ssl.MailSslCertificate;
import java.security.cert.X509Certificate;

public class MailSslError {

   public static final int SSL_EXPIRED = 1;
   public static final int SSL_IDMISMATCH = 2;
   public static final int SSL_MAX_ERROR = 4;
   public static final int SSL_NOTYETVALID = 0;
   public static final int SSL_UNTRUSTED = 3;
   MailSslCertificate mCertificate;
   int mErrors;


   public MailSslError(int var1, MailSslCertificate var2) {
      this.addError(var1);
      this.mCertificate = var2;
   }

   public MailSslError(int var1, X509Certificate var2) {
      this.addError(var1);
      MailSslCertificate var4 = new MailSslCertificate(var2);
      this.mCertificate = var4;
   }

   public boolean addError(int var1) {
      boolean var2;
      if(var1 >= 0 && var1 < 4) {
         var2 = true;
      } else {
         var2 = false;
      }

      if(var2) {
         int var3 = this.mErrors;
         int var4 = 1 << var1;
         int var5 = var3 | var4;
         this.mErrors = var5;
      }

      return var2;
   }

   public MailSslCertificate getCertificate() {
      return this.mCertificate;
   }

   public int getPrimaryError() {
      int var4;
      if(this.mErrors != 0) {
         for(int var1 = 3; var1 >= 0; var1 += -1) {
            int var2 = this.mErrors;
            int var3 = 1 << var1;
            if((var2 & var3) != 0) {
               var4 = var1;
               return var4;
            }
         }
      }

      var4 = 0;
      return var4;
   }

   public boolean hasError(int var1) {
      boolean var2;
      if(var1 >= 0 && var1 < 4) {
         var2 = true;
      } else {
         var2 = false;
      }

      if(var2) {
         int var3 = this.mErrors;
         int var4 = 1 << var1;
         if((var3 & var4) != 0) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("primary error: ");
      int var2 = this.getPrimaryError();
      StringBuilder var3 = var1.append(var2).append(" certificate: ");
      MailSslCertificate var4 = this.getCertificate();
      return var3.append(var4).toString();
   }
}
