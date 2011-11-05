package myorg.bouncycastle.asn1.ess;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.IssuerSerial;

public class ESSCertID extends ASN1Encodable {

   private ASN1OctetString certHash;
   private IssuerSerial issuerSerial;


   public ESSCertID(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 2) {
         ASN1OctetString var5 = ASN1OctetString.getInstance(var1.getObjectAt(0));
         this.certHash = var5;
         if(var1.size() > 1) {
            IssuerSerial var6 = IssuerSerial.getInstance(var1.getObjectAt(1));
            this.issuerSerial = var6;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public ESSCertID(byte[] var1) {
      DEROctetString var2 = new DEROctetString(var1);
      this.certHash = var2;
   }

   public ESSCertID(byte[] var1, IssuerSerial var2) {
      DEROctetString var3 = new DEROctetString(var1);
      this.certHash = var3;
      this.issuerSerial = var2;
   }

   public static ESSCertID getInstance(Object var0) {
      ESSCertID var1;
      if(var0 != null && !(var0 instanceof ESSCertID)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'ESSCertID\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ESSCertID(var2);
      } else {
         var1 = (ESSCertID)var0;
      }

      return var1;
   }

   public byte[] getCertHash() {
      return this.certHash.getOctets();
   }

   public IssuerSerial getIssuerSerial() {
      return this.issuerSerial;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.certHash;
      var1.add(var2);
      if(this.issuerSerial != null) {
         IssuerSerial var3 = this.issuerSerial;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
