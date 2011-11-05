package myorg.bouncycastle.asn1.ess;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DigestInfo;
import myorg.bouncycastle.asn1.x509.IssuerSerial;

public class OtherCertID extends ASN1Encodable {

   private IssuerSerial issuerSerial;
   private ASN1Encodable otherCertHash;


   public OtherCertID(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 2) {
         if(var1.getObjectAt(0).getDERObject() instanceof ASN1OctetString) {
            ASN1OctetString var5 = ASN1OctetString.getInstance(var1.getObjectAt(0));
            this.otherCertHash = var5;
         } else {
            DigestInfo var8 = DigestInfo.getInstance(var1.getObjectAt(0));
            this.otherCertHash = var8;
         }

         if(var1.size() > 1) {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var1.getObjectAt(1));
            IssuerSerial var7 = new IssuerSerial(var6);
            this.issuerSerial = var7;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public OtherCertID(AlgorithmIdentifier var1, byte[] var2) {
      DigestInfo var3 = new DigestInfo(var1, var2);
      this.otherCertHash = var3;
   }

   public OtherCertID(AlgorithmIdentifier var1, byte[] var2, IssuerSerial var3) {
      DigestInfo var4 = new DigestInfo(var1, var2);
      this.otherCertHash = var4;
      this.issuerSerial = var3;
   }

   public static OtherCertID getInstance(Object var0) {
      OtherCertID var1;
      if(var0 != null && !(var0 instanceof OtherCertID)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'OtherCertID\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new OtherCertID(var2);
      } else {
         var1 = (OtherCertID)var0;
      }

      return var1;
   }

   public AlgorithmIdentifier getAlgorithmHash() {
      AlgorithmIdentifier var1;
      if(this.otherCertHash.getDERObject() instanceof ASN1OctetString) {
         var1 = new AlgorithmIdentifier("1.3.14.3.2.26");
      } else {
         var1 = DigestInfo.getInstance(this.otherCertHash).getAlgorithmId();
      }

      return var1;
   }

   public byte[] getCertHash() {
      byte[] var1;
      if(this.otherCertHash.getDERObject() instanceof ASN1OctetString) {
         var1 = ((ASN1OctetString)this.otherCertHash.getDERObject()).getOctets();
      } else {
         var1 = DigestInfo.getInstance(this.otherCertHash).getDigest();
      }

      return var1;
   }

   public IssuerSerial getIssuerSerial() {
      return this.issuerSerial;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1Encodable var2 = this.otherCertHash;
      var1.add(var2);
      if(this.issuerSerial != null) {
         IssuerSerial var3 = this.issuerSerial;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
