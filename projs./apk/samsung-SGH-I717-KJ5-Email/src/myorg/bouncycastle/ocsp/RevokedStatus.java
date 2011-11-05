package myorg.bouncycastle.ocsp;

import java.text.ParseException;
import java.util.Date;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.ocsp.RevokedInfo;
import myorg.bouncycastle.asn1.x509.CRLReason;
import myorg.bouncycastle.ocsp.CertificateStatus;

public class RevokedStatus implements CertificateStatus {

   RevokedInfo info;


   public RevokedStatus(Date var1, int var2) {
      DERGeneralizedTime var3 = new DERGeneralizedTime(var1);
      CRLReason var4 = new CRLReason(var2);
      RevokedInfo var5 = new RevokedInfo(var3, var4);
      this.info = var5;
   }

   public RevokedStatus(RevokedInfo var1) {
      this.info = var1;
   }

   public int getRevocationReason() {
      if(this.info.getRevocationReason() == null) {
         throw new IllegalStateException("attempt to get a reason where none is available");
      } else {
         return this.info.getRevocationReason().getValue().intValue();
      }
   }

   public Date getRevocationTime() {
      try {
         Date var1 = this.info.getRevocationTime().getDate();
         return var1;
      } catch (ParseException var6) {
         StringBuilder var3 = (new StringBuilder()).append("ParseException:");
         String var4 = var6.getMessage();
         String var5 = var3.append(var4).toString();
         throw new IllegalStateException(var5);
      }
   }

   public boolean hasRevocationReason() {
      boolean var1;
      if(this.info.getRevocationReason() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
