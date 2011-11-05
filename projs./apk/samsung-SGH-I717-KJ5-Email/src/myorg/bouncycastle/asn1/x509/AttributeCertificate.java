package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.AttributeCertificateInfo;

public class AttributeCertificate extends ASN1Encodable {

   AttributeCertificateInfo acinfo;
   AlgorithmIdentifier signatureAlgorithm;
   DERBitString signatureValue;


   public AttributeCertificate(ASN1Sequence var1) {
      if(var1.size() != 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         AttributeCertificateInfo var5 = AttributeCertificateInfo.getInstance(var1.getObjectAt(0));
         this.acinfo = var5;
         AlgorithmIdentifier var6 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
         this.signatureAlgorithm = var6;
         DERBitString var7 = DERBitString.getInstance(var1.getObjectAt(2));
         this.signatureValue = var7;
      }
   }

   public AttributeCertificate(AttributeCertificateInfo var1, AlgorithmIdentifier var2, DERBitString var3) {
      this.acinfo = var1;
      this.signatureAlgorithm = var2;
      this.signatureValue = var3;
   }

   public static AttributeCertificate getInstance(Object var0) {
      AttributeCertificate var1;
      if(var0 instanceof AttributeCertificate) {
         var1 = (AttributeCertificate)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AttributeCertificate(var2);
      }

      return var1;
   }

   public AttributeCertificateInfo getAcinfo() {
      return this.acinfo;
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public DERBitString getSignatureValue() {
      return this.signatureValue;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AttributeCertificateInfo var2 = this.acinfo;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.signatureAlgorithm;
      var1.add(var3);
      DERBitString var4 = this.signatureValue;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
