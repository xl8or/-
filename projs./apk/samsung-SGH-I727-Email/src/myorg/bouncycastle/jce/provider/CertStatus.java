package myorg.bouncycastle.jce.provider;

import java.util.Date;

class CertStatus {

   public static final int UNDETERMINED = 12;
   public static final int UNREVOKED = 11;
   int certStatus = 11;
   Date revocationDate = null;


   CertStatus() {}

   public int getCertStatus() {
      return this.certStatus;
   }

   public Date getRevocationDate() {
      return this.revocationDate;
   }

   public void setCertStatus(int var1) {
      this.certStatus = var1;
   }

   public void setRevocationDate(Date var1) {
      this.revocationDate = var1;
   }
}
