package myorg.bouncycastle.asn1.pkcs;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertificationRequest extends ASN1Encodable {

   protected CertificationRequestInfo reqInfo = null;
   protected AlgorithmIdentifier sigAlgId = null;
   protected DERBitString sigBits = null;


   protected CertificationRequest() {}

   public CertificationRequest(ASN1Sequence var1) {
      CertificationRequestInfo var2 = CertificationRequestInfo.getInstance(var1.getObjectAt(0));
      this.reqInfo = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
      this.sigAlgId = var3;
      DERBitString var4 = (DERBitString)var1.getObjectAt(2);
      this.sigBits = var4;
   }

   public CertificationRequest(CertificationRequestInfo var1, AlgorithmIdentifier var2, DERBitString var3) {
      this.reqInfo = var1;
      this.sigAlgId = var2;
      this.sigBits = var3;
   }

   public static CertificationRequest getInstance(Object var0) {
      CertificationRequest var1;
      if(var0 instanceof CertificationRequest) {
         var1 = (CertificationRequest)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertificationRequest(var2);
      }

      return var1;
   }

   public CertificationRequestInfo getCertificationRequestInfo() {
      return this.reqInfo;
   }

   public DERBitString getSignature() {
      return this.sigBits;
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.sigAlgId;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CertificationRequestInfo var2 = this.reqInfo;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.sigAlgId;
      var1.add(var3);
      DERBitString var4 = this.sigBits;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
