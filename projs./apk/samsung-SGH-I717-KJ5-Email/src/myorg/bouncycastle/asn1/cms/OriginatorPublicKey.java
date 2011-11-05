package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OriginatorPublicKey extends ASN1Encodable {

   private AlgorithmIdentifier algorithm;
   private DERBitString publicKey;


   public OriginatorPublicKey(ASN1Sequence var1) {
      AlgorithmIdentifier var2 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.algorithm = var2;
      DERBitString var3 = (DERBitString)var1.getObjectAt(1);
      this.publicKey = var3;
   }

   public OriginatorPublicKey(AlgorithmIdentifier var1, byte[] var2) {
      this.algorithm = var1;
      DERBitString var3 = new DERBitString(var2);
      this.publicKey = var3;
   }

   public static OriginatorPublicKey getInstance(Object var0) {
      OriginatorPublicKey var1;
      if(var0 != null && !(var0 instanceof OriginatorPublicKey)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid OriginatorPublicKey: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OriginatorPublicKey(var2);
      } else {
         var1 = (OriginatorPublicKey)var0;
      }

      return var1;
   }

   public static OriginatorPublicKey getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getAlgorithm() {
      return this.algorithm;
   }

   public DERBitString getPublicKey() {
      return this.publicKey;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.algorithm;
      var1.add(var2);
      DERBitString var3 = this.publicKey;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
