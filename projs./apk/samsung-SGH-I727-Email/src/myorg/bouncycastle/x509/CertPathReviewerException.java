package myorg.bouncycastle.x509;

import java.security.cert.CertPath;
import myorg.bouncycastle.i18n.ErrorBundle;
import myorg.bouncycastle.i18n.LocalizedException;

public class CertPathReviewerException extends LocalizedException {

   private CertPath certPath = null;
   private int index = -1;


   public CertPathReviewerException(ErrorBundle var1) {
      super(var1);
   }

   public CertPathReviewerException(ErrorBundle var1, Throwable var2) {
      super(var1, var2);
   }

   public CertPathReviewerException(ErrorBundle var1, Throwable var2, CertPath var3, int var4) {
      super(var1, var2);
      if(var3 != null && var4 != -1) {
         if(var4 >= -1) {
            if(var3 != null) {
               int var5 = var3.getCertificates().size();
               if(var4 >= var5) {
                  throw new IndexOutOfBoundsException();
               }
            }

            this.certPath = var3;
            this.index = var4;
         } else {
            throw new IndexOutOfBoundsException();
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public CertPathReviewerException(ErrorBundle var1, CertPath var2, int var3) {
      super(var1);
      if(var2 != null && var3 != -1) {
         if(var3 >= -1) {
            if(var2 != null) {
               int var4 = var2.getCertificates().size();
               if(var3 >= var4) {
                  throw new IndexOutOfBoundsException();
               }
            }

            this.certPath = var2;
            this.index = var3;
         } else {
            throw new IndexOutOfBoundsException();
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public CertPath getCertPath() {
      return this.certPath;
   }

   public int getIndex() {
      return this.index;
   }
}
