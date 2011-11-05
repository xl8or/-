package myorg.bouncycastle.asn1.cmp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.crmf.CertId;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OOBCertHash extends ASN1Encodable {

   private CertId certId;
   private AlgorithmIdentifier hashAlg;
   private DERBitString hashVal;


   private OOBCertHash(ASN1Sequence var1) {
      int var2 = var1.size() - 1;
      int var3 = var2 + -1;
      DERBitString var4 = DERBitString.getInstance(var1.getObjectAt(var2));
      this.hashVal = var4;

      for(int var5 = var3; var5 >= 0; var5 += -1) {
         ASN1TaggedObject var6 = (ASN1TaggedObject)var1.getObjectAt(var5);
         if(var6.getTagNo() == 0) {
            AlgorithmIdentifier var7 = AlgorithmIdentifier.getInstance(var6, (boolean)1);
            this.hashAlg = var7;
         } else {
            CertId var8 = CertId.getInstance(var6, (boolean)1);
            this.certId = var8;
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if(var3 != null) {
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, var2, var3);
         var1.add(var4);
      }
   }

   public static OOBCertHash getInstance(Object var0) {
      OOBCertHash var1;
      if(var0 instanceof OOBCertHash) {
         var1 = (OOBCertHash)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OOBCertHash(var2);
      }

      return var1;
   }

   public CertId getCertId() {
      return this.certId;
   }

   public AlgorithmIdentifier getHashAlg() {
      return this.hashAlg;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlg;
      this.addOptional(var1, 0, var2);
      CertId var3 = this.certId;
      this.addOptional(var1, 1, var3);
      DERBitString var4 = this.hashVal;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
