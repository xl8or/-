package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class RSAESOAEPparams extends ASN1Encodable {

   public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM;
   public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION;
   public static final AlgorithmIdentifier DEFAULT_P_SOURCE_ALGORITHM;
   private AlgorithmIdentifier hashAlgorithm;
   private AlgorithmIdentifier maskGenAlgorithm;
   private AlgorithmIdentifier pSourceAlgorithm;


   static {
      DERObjectIdentifier var0 = OIWObjectIdentifiers.idSHA1;
      DERNull var1 = new DERNull();
      DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(var0, var1);
      DERObjectIdentifier var2 = PKCSObjectIdentifiers.id_mgf1;
      AlgorithmIdentifier var3 = DEFAULT_HASH_ALGORITHM;
      DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(var2, var3);
      DERObjectIdentifier var4 = PKCSObjectIdentifiers.id_pSpecified;
      byte[] var5 = new byte[0];
      DEROctetString var6 = new DEROctetString(var5);
      DEFAULT_P_SOURCE_ALGORITHM = new AlgorithmIdentifier(var4, var6);
   }

   public RSAESOAEPparams() {
      AlgorithmIdentifier var1 = DEFAULT_HASH_ALGORITHM;
      this.hashAlgorithm = var1;
      AlgorithmIdentifier var2 = DEFAULT_MASK_GEN_FUNCTION;
      this.maskGenAlgorithm = var2;
      AlgorithmIdentifier var3 = DEFAULT_P_SOURCE_ALGORITHM;
      this.pSourceAlgorithm = var3;
   }

   public RSAESOAEPparams(ASN1Sequence var1) {
      AlgorithmIdentifier var2 = DEFAULT_HASH_ALGORITHM;
      this.hashAlgorithm = var2;
      AlgorithmIdentifier var3 = DEFAULT_MASK_GEN_FUNCTION;
      this.maskGenAlgorithm = var3;
      AlgorithmIdentifier var4 = DEFAULT_P_SOURCE_ALGORITHM;
      this.pSourceAlgorithm = var4;
      int var5 = 0;

      while(true) {
         int var6 = var1.size();
         if(var5 == var6) {
            return;
         }

         ASN1TaggedObject var7 = (ASN1TaggedObject)var1.getObjectAt(var5);
         switch(var7.getTagNo()) {
         case 0:
            AlgorithmIdentifier var8 = AlgorithmIdentifier.getInstance(var7, (boolean)1);
            this.hashAlgorithm = var8;
            break;
         case 1:
            AlgorithmIdentifier var9 = AlgorithmIdentifier.getInstance(var7, (boolean)1);
            this.maskGenAlgorithm = var9;
            break;
         case 2:
            AlgorithmIdentifier var10 = AlgorithmIdentifier.getInstance(var7, (boolean)1);
            this.pSourceAlgorithm = var10;
            break;
         default:
            throw new IllegalArgumentException("unknown tag");
         }

         ++var5;
      }
   }

   public RSAESOAEPparams(AlgorithmIdentifier var1, AlgorithmIdentifier var2, AlgorithmIdentifier var3) {
      this.hashAlgorithm = var1;
      this.maskGenAlgorithm = var2;
      this.pSourceAlgorithm = var3;
   }

   public static RSAESOAEPparams getInstance(Object var0) {
      RSAESOAEPparams var1;
      if(var0 instanceof RSAESOAEPparams) {
         var1 = (RSAESOAEPparams)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RSAESOAEPparams(var2);
      }

      return var1;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public AlgorithmIdentifier getMaskGenAlgorithm() {
      return this.maskGenAlgorithm;
   }

   public AlgorithmIdentifier getPSourceAlgorithm() {
      return this.pSourceAlgorithm;
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

      AlgorithmIdentifier var10 = this.pSourceAlgorithm;
      AlgorithmIdentifier var11 = DEFAULT_P_SOURCE_ALGORITHM;
      if(!var10.equals(var11)) {
         AlgorithmIdentifier var12 = this.pSourceAlgorithm;
         DERTaggedObject var13 = new DERTaggedObject((boolean)1, 2, var12);
         var1.add(var13);
      }

      return new DERSequence(var1);
   }
}
