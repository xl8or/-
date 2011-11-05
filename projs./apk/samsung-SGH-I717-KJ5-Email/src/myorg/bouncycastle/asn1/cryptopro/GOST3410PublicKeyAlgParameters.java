package myorg.bouncycastle.asn1.cryptopro;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class GOST3410PublicKeyAlgParameters extends ASN1Encodable {

   private DERObjectIdentifier digestParamSet;
   private DERObjectIdentifier encryptionParamSet;
   private DERObjectIdentifier publicKeyParamSet;


   public GOST3410PublicKeyAlgParameters(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.publicKeyParamSet = var2;
      DERObjectIdentifier var3 = (DERObjectIdentifier)var1.getObjectAt(1);
      this.digestParamSet = var3;
      if(var1.size() > 2) {
         DERObjectIdentifier var4 = (DERObjectIdentifier)var1.getObjectAt(2);
         this.encryptionParamSet = var4;
      }
   }

   public GOST3410PublicKeyAlgParameters(DERObjectIdentifier var1, DERObjectIdentifier var2) {
      this.publicKeyParamSet = var1;
      this.digestParamSet = var2;
      this.encryptionParamSet = null;
   }

   public GOST3410PublicKeyAlgParameters(DERObjectIdentifier var1, DERObjectIdentifier var2, DERObjectIdentifier var3) {
      this.publicKeyParamSet = var1;
      this.digestParamSet = var2;
      this.encryptionParamSet = var3;
   }

   public static GOST3410PublicKeyAlgParameters getInstance(Object var0) {
      GOST3410PublicKeyAlgParameters var1;
      if(var0 != null && !(var0 instanceof GOST3410PublicKeyAlgParameters)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid GOST3410Parameter: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new GOST3410PublicKeyAlgParameters(var2);
      } else {
         var1 = (GOST3410PublicKeyAlgParameters)var0;
      }

      return var1;
   }

   public static GOST3410PublicKeyAlgParameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERObjectIdentifier getDigestParamSet() {
      return this.digestParamSet;
   }

   public DERObjectIdentifier getEncryptionParamSet() {
      return this.encryptionParamSet;
   }

   public DERObjectIdentifier getPublicKeyParamSet() {
      return this.publicKeyParamSet;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.publicKeyParamSet;
      var1.add(var2);
      DERObjectIdentifier var3 = this.digestParamSet;
      var1.add(var3);
      if(this.encryptionParamSet != null) {
         DERObjectIdentifier var4 = this.encryptionParamSet;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
