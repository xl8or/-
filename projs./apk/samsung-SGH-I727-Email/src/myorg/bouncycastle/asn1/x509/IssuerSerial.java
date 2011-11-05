package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.GeneralNames;

public class IssuerSerial extends ASN1Encodable {

   GeneralNames issuer;
   DERBitString issuerUID;
   DERInteger serial;


   public IssuerSerial(ASN1Sequence var1) {
      if(var1.size() != 2 && var1.size() != 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         GeneralNames var5 = GeneralNames.getInstance(var1.getObjectAt(0));
         this.issuer = var5;
         DERInteger var6 = DERInteger.getInstance(var1.getObjectAt(1));
         this.serial = var6;
         if(var1.size() == 3) {
            DERBitString var7 = DERBitString.getInstance(var1.getObjectAt(2));
            this.issuerUID = var7;
         }
      }
   }

   public IssuerSerial(GeneralNames var1, DERInteger var2) {
      this.issuer = var1;
      this.serial = var2;
   }

   public static IssuerSerial getInstance(Object var0) {
      IssuerSerial var1;
      if(var0 != null && !(var0 instanceof IssuerSerial)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new IssuerSerial(var2);
      } else {
         var1 = (IssuerSerial)var0;
      }

      return var1;
   }

   public static IssuerSerial getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public GeneralNames getIssuer() {
      return this.issuer;
   }

   public DERBitString getIssuerUID() {
      return this.issuerUID;
   }

   public DERInteger getSerial() {
      return this.serial;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      GeneralNames var2 = this.issuer;
      var1.add(var2);
      DERInteger var3 = this.serial;
      var1.add(var3);
      if(this.issuerUID != null) {
         DERBitString var4 = this.issuerUID;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
