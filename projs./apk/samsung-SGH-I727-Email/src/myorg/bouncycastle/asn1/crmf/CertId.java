package myorg.bouncycastle.asn1.crmf;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class CertId extends ASN1Encodable {

   private GeneralName issuer;
   private DERInteger serialNumber;


   private CertId(ASN1Sequence var1) {
      GeneralName var2 = GeneralName.getInstance(var1.getObjectAt(0));
      this.issuer = var2;
      DERInteger var3 = DERInteger.getInstance(var1.getObjectAt(1));
      this.serialNumber = var3;
   }

   public static CertId getInstance(Object var0) {
      CertId var1;
      if(var0 instanceof CertId) {
         var1 = (CertId)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertId(var2);
      }

      return var1;
   }

   public static CertId getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public GeneralName getIssuer() {
      return this.issuer;
   }

   public DERInteger getSerialNumber() {
      return this.serialNumber;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      GeneralName var2 = this.issuer;
      var1.add(var2);
      DERInteger var3 = this.serialNumber;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
