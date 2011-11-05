package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AttributeCertificate;

public class SignerAttribute extends ASN1Encodable {

   private AttributeCertificate certifiedAttributes;
   private ASN1Sequence claimedAttributes;


   private SignerAttribute(Object var1) {
      DERTaggedObject var2 = (DERTaggedObject)((ASN1Sequence)var1).getObjectAt(0);
      if(var2.getTagNo() == 0) {
         ASN1Sequence var3 = ASN1Sequence.getInstance(var2, (boolean)1);
         this.claimedAttributes = var3;
      } else if(var2.getTagNo() == 1) {
         AttributeCertificate var4 = AttributeCertificate.getInstance(var2);
         this.certifiedAttributes = var4;
      } else {
         throw new IllegalArgumentException("illegal tag.");
      }
   }

   public SignerAttribute(ASN1Sequence var1) {
      this.claimedAttributes = var1;
   }

   public SignerAttribute(AttributeCertificate var1) {
      this.certifiedAttributes = var1;
   }

   public static SignerAttribute getInstance(Object var0) {
      SignerAttribute var1;
      if(var0 != null && !(var0 instanceof SignerAttribute)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var2 = (new StringBuilder()).append("unknown object in \'SignerAttribute\' factory: ");
            String var3 = var0.getClass().getName();
            String var4 = var2.append(var3).append(".").toString();
            throw new IllegalArgumentException(var4);
         }

         var1 = new SignerAttribute(var0);
      } else {
         var1 = (SignerAttribute)var0;
      }

      return var1;
   }

   public AttributeCertificate getCertifiedAttributes() {
      return this.certifiedAttributes;
   }

   public ASN1Sequence getClaimedAttributes() {
      return this.claimedAttributes;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.claimedAttributes != null) {
         ASN1Sequence var2 = this.claimedAttributes;
         DERTaggedObject var3 = new DERTaggedObject(0, var2);
         var1.add(var3);
      } else {
         AttributeCertificate var4 = this.certifiedAttributes;
         DERTaggedObject var5 = new DERTaggedObject(1, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
