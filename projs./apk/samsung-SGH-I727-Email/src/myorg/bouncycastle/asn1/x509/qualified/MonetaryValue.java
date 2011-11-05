package myorg.bouncycastle.asn1.x509.qualified;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.qualified.Iso4217CurrencyCode;

public class MonetaryValue extends ASN1Encodable {

   DERInteger amount;
   Iso4217CurrencyCode currency;
   DERInteger exponent;


   public MonetaryValue(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      Iso4217CurrencyCode var3 = Iso4217CurrencyCode.getInstance(var2.nextElement());
      this.currency = var3;
      DERInteger var4 = DERInteger.getInstance(var2.nextElement());
      this.amount = var4;
      DERInteger var5 = DERInteger.getInstance(var2.nextElement());
      this.exponent = var5;
   }

   public MonetaryValue(Iso4217CurrencyCode var1, int var2, int var3) {
      this.currency = var1;
      DERInteger var4 = new DERInteger(var2);
      this.amount = var4;
      DERInteger var5 = new DERInteger(var3);
      this.exponent = var5;
   }

   public static MonetaryValue getInstance(Object var0) {
      MonetaryValue var1;
      if(var0 != null && !(var0 instanceof MonetaryValue)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance");
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new MonetaryValue(var2);
      } else {
         var1 = (MonetaryValue)var0;
      }

      return var1;
   }

   public BigInteger getAmount() {
      return this.amount.getValue();
   }

   public Iso4217CurrencyCode getCurrency() {
      return this.currency;
   }

   public BigInteger getExponent() {
      return this.exponent.getValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      Iso4217CurrencyCode var2 = this.currency;
      var1.add(var2);
      DERInteger var3 = this.amount;
      var1.add(var3);
      DERInteger var4 = this.exponent;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
