package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertID extends ASN1Encodable {

   AlgorithmIdentifier hashAlgorithm;
   ASN1OctetString issuerKeyHash;
   ASN1OctetString issuerNameHash;
   DERInteger serialNumber;


   public CertID(ASN1Sequence var1) {
      AlgorithmIdentifier var2 = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.hashAlgorithm = var2;
      ASN1OctetString var3 = (ASN1OctetString)var1.getObjectAt(1);
      this.issuerNameHash = var3;
      ASN1OctetString var4 = (ASN1OctetString)var1.getObjectAt(2);
      this.issuerKeyHash = var4;
      DERInteger var5 = (DERInteger)var1.getObjectAt(3);
      this.serialNumber = var5;
   }

   public CertID(AlgorithmIdentifier var1, ASN1OctetString var2, ASN1OctetString var3, DERInteger var4) {
      this.hashAlgorithm = var1;
      this.issuerNameHash = var2;
      this.issuerKeyHash = var3;
      this.serialNumber = var4;
   }

   public static CertID getInstance(Object var0) {
      CertID var1;
      if(var0 != null && !(var0 instanceof CertID)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertID(var2);
      } else {
         var1 = (CertID)var0;
      }

      return var1;
   }

   public static CertID getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public ASN1OctetString getIssuerKeyHash() {
      return this.issuerKeyHash;
   }

   public ASN1OctetString getIssuerNameHash() {
      return this.issuerNameHash;
   }

   public DERInteger getSerialNumber() {
      return this.serialNumber;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlgorithm;
      var1.add(var2);
      ASN1OctetString var3 = this.issuerNameHash;
      var1.add(var3);
      ASN1OctetString var4 = this.issuerKeyHash;
      var1.add(var4);
      DERInteger var5 = this.serialNumber;
      var1.add(var5);
      return new DERSequence(var1);
   }
}
