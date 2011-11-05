package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class ObjectDigestInfo extends ASN1Encodable {

   public static final int otherObjectDigest = 2;
   public static final int publicKey = 0;
   public static final int publicKeyCert = 1;
   AlgorithmIdentifier digestAlgorithm;
   DEREnumerated digestedObjectType;
   DERBitString objectDigest;
   DERObjectIdentifier otherObjectTypeID;


   public ObjectDigestInfo(int var1, String var2, AlgorithmIdentifier var3, byte[] var4) {
      DEREnumerated var5 = new DEREnumerated(var1);
      this.digestedObjectType = var5;
      if(var1 == 2) {
         DERObjectIdentifier var6 = new DERObjectIdentifier(var2);
         this.otherObjectTypeID = var6;
      }

      this.digestAlgorithm = var3;
      DERBitString var7 = new DERBitString(var4);
      this.objectDigest = var7;
   }

   private ObjectDigestInfo(ASN1Sequence var1) {
      if(var1.size() <= 4 && var1.size() >= 3) {
         DEREnumerated var5 = DEREnumerated.getInstance(var1.getObjectAt(0));
         this.digestedObjectType = var5;
         int var6 = 0;
         if(var1.size() == 4) {
            DERObjectIdentifier var7 = DERObjectIdentifier.getInstance(var1.getObjectAt(1));
            this.otherObjectTypeID = var7;
            ++var6;
         }

         int var8 = var6 + 1;
         AlgorithmIdentifier var9 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var8));
         this.digestAlgorithm = var9;
         int var10 = var6 + 2;
         DERBitString var11 = DERBitString.getInstance(var1.getObjectAt(var10));
         this.objectDigest = var11;
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public static ObjectDigestInfo getInstance(Object var0) {
      ObjectDigestInfo var1;
      if(var0 != null && !(var0 instanceof ObjectDigestInfo)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ObjectDigestInfo(var2);
      } else {
         var1 = (ObjectDigestInfo)var0;
      }

      return var1;
   }

   public static ObjectDigestInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getDigestAlgorithm() {
      return this.digestAlgorithm;
   }

   public DEREnumerated getDigestedObjectType() {
      return this.digestedObjectType;
   }

   public DERBitString getObjectDigest() {
      return this.objectDigest;
   }

   public DERObjectIdentifier getOtherObjectTypeID() {
      return this.otherObjectTypeID;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DEREnumerated var2 = this.digestedObjectType;
      var1.add(var2);
      if(this.otherObjectTypeID != null) {
         DERObjectIdentifier var3 = this.otherObjectTypeID;
         var1.add(var3);
      }

      AlgorithmIdentifier var4 = this.digestAlgorithm;
      var1.add(var4);
      DERBitString var5 = this.objectDigest;
      var1.add(var5);
      return new DERSequence(var1);
   }
}
