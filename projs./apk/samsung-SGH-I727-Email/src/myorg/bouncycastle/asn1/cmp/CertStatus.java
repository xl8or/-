package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cmp.PKIStatusInfo;

public class CertStatus extends ASN1Encodable {

   private ASN1OctetString certHash;
   private DERInteger certReqId;
   private PKIStatusInfo statusInfo;


   private CertStatus(ASN1Sequence var1) {
      ASN1OctetString var2 = ASN1OctetString.getInstance(var1.getObjectAt(0));
      this.certHash = var2;
      DERInteger var3 = DERInteger.getInstance(var1.getObjectAt(1));
      this.certReqId = var3;
      if(var1.size() > 2) {
         PKIStatusInfo var4 = PKIStatusInfo.getInstance(var1.getObjectAt(2));
         this.statusInfo = var4;
      }
   }

   public static CertStatus getInstance(Object var0) {
      CertStatus var1;
      if(var0 instanceof CertStatus) {
         var1 = (CertStatus)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertStatus(var2);
      }

      return var1;
   }

   public DERInteger getCertReqId() {
      return this.certReqId;
   }

   public PKIStatusInfo getStatusInfo() {
      return this.statusInfo;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.certHash;
      var1.add(var2);
      DERInteger var3 = this.certReqId;
      var1.add(var3);
      if(this.statusInfo != null) {
         PKIStatusInfo var4 = this.statusInfo;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
