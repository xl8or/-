package myorg.bouncycastle.asn1.cms;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.X509Name;

public class IssuerAndSerialNumber extends ASN1Encodable {

   X509Name name;
   DERInteger serialNumber;


   public IssuerAndSerialNumber(ASN1Sequence var1) {
      X509Name var2 = X509Name.getInstance(var1.getObjectAt(0));
      this.name = var2;
      DERInteger var3 = (DERInteger)var1.getObjectAt(1);
      this.serialNumber = var3;
   }

   public IssuerAndSerialNumber(X509Name var1, BigInteger var2) {
      this.name = var1;
      DERInteger var3 = new DERInteger(var2);
      this.serialNumber = var3;
   }

   public IssuerAndSerialNumber(X509Name var1, DERInteger var2) {
      this.name = var1;
      this.serialNumber = var2;
   }

   public static IssuerAndSerialNumber getInstance(Object var0) {
      IssuerAndSerialNumber var1;
      if(var0 instanceof IssuerAndSerialNumber) {
         var1 = (IssuerAndSerialNumber)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Illegal object in IssuerAndSerialNumber: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new IssuerAndSerialNumber(var2);
      }

      return var1;
   }

   public X509Name getName() {
      return this.name;
   }

   public DERInteger getSerialNumber() {
      return this.serialNumber;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      X509Name var2 = this.name;
      var1.add(var2);
      DERInteger var3 = this.serialNumber;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
