package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.CRLReason;

public class RevokedInfo extends ASN1Encodable {

   private CRLReason revocationReason;
   private DERGeneralizedTime revocationTime;


   public RevokedInfo(ASN1Sequence var1) {
      DERGeneralizedTime var2 = (DERGeneralizedTime)var1.getObjectAt(0);
      this.revocationTime = var2;
      if(var1.size() > 1) {
         DEREnumerated var3 = DEREnumerated.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)1);
         CRLReason var4 = new CRLReason(var3);
         this.revocationReason = var4;
      }
   }

   public RevokedInfo(DERGeneralizedTime var1, CRLReason var2) {
      this.revocationTime = var1;
      this.revocationReason = var2;
   }

   public static RevokedInfo getInstance(Object var0) {
      RevokedInfo var1;
      if(var0 != null && !(var0 instanceof RevokedInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RevokedInfo(var2);
      } else {
         var1 = (RevokedInfo)var0;
      }

      return var1;
   }

   public static RevokedInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public CRLReason getRevocationReason() {
      return this.revocationReason;
   }

   public DERGeneralizedTime getRevocationTime() {
      return this.revocationTime;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERGeneralizedTime var2 = this.revocationTime;
      var1.add(var2);
      if(this.revocationReason != null) {
         CRLReason var3 = this.revocationReason;
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
