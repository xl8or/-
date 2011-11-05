package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OtherHashAlgAndValue extends ASN1Encodable {

   private AlgorithmIdentifier hashAlgorithm;
   private ASN1OctetString hashValue;


   public OtherHashAlgAndValue(ASN1Sequence var1) {
      if(var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
         this.hashAlgorithm = var5;
         ASN1OctetString var6 = ASN1OctetString.getInstance(var1.getObjectAt(1));
         this.hashValue = var6;
      }
   }

   public OtherHashAlgAndValue(AlgorithmIdentifier var1, ASN1OctetString var2) {
      this.hashAlgorithm = var1;
      this.hashValue = var2;
   }

   public static OtherHashAlgAndValue getInstance(Object var0) {
      OtherHashAlgAndValue var1;
      if(var0 != null && !(var0 instanceof OtherHashAlgAndValue)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'OtherHashAlgAndValue\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OtherHashAlgAndValue(var2);
      } else {
         var1 = (OtherHashAlgAndValue)var0;
      }

      return var1;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public ASN1OctetString getHashValue() {
      return this.hashValue;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlgorithm;
      var1.add(var2);
      ASN1OctetString var3 = this.hashValue;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
