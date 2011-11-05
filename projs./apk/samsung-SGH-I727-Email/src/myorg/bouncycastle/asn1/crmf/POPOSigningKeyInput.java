package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class POPOSigningKeyInput extends ASN1Encodable {

   private ASN1Encodable authInfo;
   private SubjectPublicKeyInfo publicKey;


   private POPOSigningKeyInput(ASN1Sequence var1) {
      ASN1Encodable var2 = (ASN1Encodable)var1.getObjectAt(0);
      this.authInfo = var2;
      SubjectPublicKeyInfo var3 = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(1));
      this.publicKey = var3;
   }

   public static POPOSigningKeyInput getInstance(Object var0) {
      POPOSigningKeyInput var1;
      if(var0 instanceof POPOSigningKeyInput) {
         var1 = (POPOSigningKeyInput)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new POPOSigningKeyInput(var2);
      }

      return var1;
   }

   public SubjectPublicKeyInfo getPublicKey() {
      return this.publicKey;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1Encodable var2 = this.authInfo;
      var1.add(var2);
      SubjectPublicKeyInfo var3 = this.publicKey;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
