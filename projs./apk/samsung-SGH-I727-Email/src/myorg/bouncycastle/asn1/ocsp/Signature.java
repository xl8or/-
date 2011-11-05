package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class Signature extends ASN1Encodable {

   ASN1Sequence certs;
   DERBitString signature;
   AlgorithmIdentifier signatureAlgorithm;


   public Signature(ASN1Sequence var1) {
      AlgorithmIdentifier var2 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.signatureAlgorithm = var2;
      DERBitString var3 = (DERBitString)var1.getObjectAt(1);
      this.signature = var3;
      if(var1.size() == 3) {
         ASN1Sequence var4 = ASN1Sequence.getInstance((ASN1TaggedObject)var1.getObjectAt(2), (boolean)1);
         this.certs = var4;
      }
   }

   public Signature(AlgorithmIdentifier var1, DERBitString var2) {
      this.signatureAlgorithm = var1;
      this.signature = var2;
   }

   public Signature(AlgorithmIdentifier var1, DERBitString var2, ASN1Sequence var3) {
      this.signatureAlgorithm = var1;
      this.signature = var2;
      this.certs = var3;
   }

   public static Signature getInstance(Object var0) {
      Signature var1;
      if(var0 != null && !(var0 instanceof Signature)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Signature(var2);
      } else {
         var1 = (Signature)var0;
      }

      return var1;
   }

   public static Signature getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Sequence getCerts() {
      return this.certs;
   }

   public DERBitString getSignature() {
      return this.signature;
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.signatureAlgorithm;
      var1.add(var2);
      DERBitString var3 = this.signature;
      var1.add(var3);
      if(this.certs != null) {
         ASN1Sequence var4 = this.certs;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 0, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
