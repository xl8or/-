package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class AttCertValidityPeriod extends ASN1Encodable {

   DERGeneralizedTime notAfterTime;
   DERGeneralizedTime notBeforeTime;


   public AttCertValidityPeriod(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         DERGeneralizedTime var5 = DERGeneralizedTime.getInstance(var1.getObjectAt(0));
         this.notBeforeTime = var5;
         DERGeneralizedTime var6 = DERGeneralizedTime.getInstance(var1.getObjectAt(1));
         this.notAfterTime = var6;
      }
   }

   public AttCertValidityPeriod(DERGeneralizedTime var1, DERGeneralizedTime var2) {
      this.notBeforeTime = var1;
      this.notAfterTime = var2;
   }

   public static AttCertValidityPeriod getInstance(Object var0) {
      AttCertValidityPeriod var1;
      if(var0 instanceof AttCertValidityPeriod) {
         var1 = (AttCertValidityPeriod)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AttCertValidityPeriod(var2);
      }

      return var1;
   }

   public DERGeneralizedTime getNotAfterTime() {
      return this.notAfterTime;
   }

   public DERGeneralizedTime getNotBeforeTime() {
      return this.notBeforeTime;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERGeneralizedTime var2 = this.notBeforeTime;
      var1.add(var2);
      DERGeneralizedTime var3 = this.notAfterTime;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
