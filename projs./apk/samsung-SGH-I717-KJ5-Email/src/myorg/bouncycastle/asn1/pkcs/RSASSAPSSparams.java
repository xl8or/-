package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class RSASSAPSSparams extends ASN1Encodable {

   public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM;
   public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION;
   public static final DERInteger DEFAULT_SALT_LENGTH;
   public static final DERInteger DEFAULT_TRAILER_FIELD;
   private AlgorithmIdentifier hashAlgorithm;
   private AlgorithmIdentifier maskGenAlgorithm;
   private DERInteger saltLength;
   private DERInteger trailerField;


   static {
      DERObjectIdentifier var0 = OIWObjectIdentifiers.idSHA1;
      DERNull var1 = new DERNull();
      DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(var0, var1);
      DERObjectIdentifier var2 = PKCSObjectIdentifiers.id_mgf1;
      AlgorithmIdentifier var3 = DEFAULT_HASH_ALGORITHM;
      DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(var2, var3);
      DEFAULT_SALT_LENGTH = new DERInteger(20);
      DEFAULT_TRAILER_FIELD = new DERInteger(1);
   }

   public RSASSAPSSparams() {
      AlgorithmIdentifier var1 = DEFAULT_HASH_ALGORITHM;
      this.hashAlgorithm = var1;
      AlgorithmIdentifier var2 = DEFAULT_MASK_GEN_FUNCTION;
      this.maskGenAlgorithm = var2;
      DERInteger var3 = DEFAULT_SALT_LENGTH;
      this.saltLength = var3;
      DERInteger var4 = DEFAULT_TRAILER_FIELD;
      this.trailerField = var4;
   }

   public RSASSAPSSparams(ASN1Sequence var1) {
      AlgorithmIdentifier var2 = DEFAULT_HASH_ALGORITHM;
      this.hashAlgorithm = var2;
      AlgorithmIdentifier var3 = DEFAULT_MASK_GEN_FUNCTION;
      this.maskGenAlgorithm = var3;
      DERInteger var4 = DEFAULT_SALT_LENGTH;
      this.saltLength = var4;
      DERInteger var5 = DEFAULT_TRAILER_FIELD;
      this.trailerField = var5;
      int var6 = 0;

      while(true) {
         int var7 = var1.size();
         if(var6 == var7) {
            return;
         }

         ASN1TaggedObject var8 = (ASN1TaggedObject)var1.getObjectAt(var6);
         switch(var8.getTagNo()) {
         case 0:
            AlgorithmIdentifier var9 = AlgorithmIdentifier.getInstance(var8, (boolean)1);
            this.hashAlgorithm = var9;
            break;
         case 1:
            AlgorithmIdentifier var10 = AlgorithmIdentifier.getInstance(var8, (boolean)1);
            this.maskGenAlgorithm = var10;
            break;
         case 2:
            DERInteger var11 = DERInteger.getInstance(var8, (boolean)1);
            this.saltLength = var11;
            break;
         case 3:
            DERInteger var12 = DERInteger.getInstance(var8, (boolean)1);
            this.trailerField = var12;
            break;
         default:
            throw new IllegalArgumentException("unknown tag");
         }

         ++var6;
      }
   }

   public RSASSAPSSparams(AlgorithmIdentifier var1, AlgorithmIdentifier var2, DERInteger var3, DERInteger var4) {
      this.hashAlgorithm = var1;
      this.maskGenAlgorithm = var2;
      this.saltLength = var3;
      this.trailerField = var4;
   }

   public static RSASSAPSSparams getInstance(Object var0) {
      RSASSAPSSparams var1;
      if(var0 != null && !(var0 instanceof RSASSAPSSparams)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RSASSAPSSparams(var2);
      } else {
         var1 = (RSASSAPSSparams)var0;
      }

      return var1;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public AlgorithmIdentifier getMaskGenAlgorithm() {
      return this.maskGenAlgorithm;
   }

   public DERInteger getSaltLength() {
      return this.saltLength;
   }

   public DERInteger getTrailerField() {
      return this.trailerField;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlgorithm;
      AlgorithmIdentifier var3 = DEFAULT_HASH_ALGORITHM;
      if(!var2.equals(var3)) {
         AlgorithmIdentifier var4 = this.hashAlgorithm;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 0, var4);
         var1.add(var5);
      }

      AlgorithmIdentifier var6 = this.maskGenAlgorithm;
      AlgorithmIdentifier var7 = DEFAULT_MASK_GEN_FUNCTION;
      if(!var6.equals(var7)) {
         AlgorithmIdentifier var8 = this.maskGenAlgorithm;
         DERTaggedObject var9 = new DERTaggedObject((boolean)1, 1, var8);
         var1.add(var9);
      }

      DERInteger var10 = this.saltLength;
      DERInteger var11 = DEFAULT_SALT_LENGTH;
      if(!var10.equals(var11)) {
         DERInteger var12 = this.saltLength;
         DERTaggedObject var13 = new DERTaggedObject((boolean)1, 2, var12);
         var1.add(var13);
      }

      DERInteger var14 = this.trailerField;
      DERInteger var15 = DEFAULT_TRAILER_FIELD;
      if(!var14.equals(var15)) {
         DERInteger var16 = this.trailerField;
         DERTaggedObject var17 = new DERTaggedObject((boolean)1, 3, var16);
         var1.add(var17);
      }

      return new DERSequence(var1);
   }
}
