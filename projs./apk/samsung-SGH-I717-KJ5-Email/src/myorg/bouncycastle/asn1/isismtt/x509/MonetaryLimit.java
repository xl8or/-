package myorg.bouncycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERSequence;

public class MonetaryLimit extends ASN1Encodable {

   DERInteger amount;
   DERPrintableString currency;
   DERInteger exponent;


   public MonetaryLimit(String var1, int var2, int var3) {
      DERPrintableString var4 = new DERPrintableString(var1, (boolean)1);
      this.currency = var4;
      DERInteger var5 = new DERInteger(var2);
      this.amount = var5;
      DERInteger var6 = new DERInteger(var3);
      this.exponent = var6;
   }

   private MonetaryLimit(ASN1Sequence var1) {
      if(var1.size() != 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         DERPrintableString var6 = DERPrintableString.getInstance(var5.nextElement());
         this.currency = var6;
         DERInteger var7 = DERInteger.getInstance(var5.nextElement());
         this.amount = var7;
         DERInteger var8 = DERInteger.getInstance(var5.nextElement());
         this.exponent = var8;
      }
   }

   public static MonetaryLimit getInstance(Object var0) {
      MonetaryLimit var1;
      if(var0 != null && !(var0 instanceof MonetaryLimit)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance");
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new MonetaryLimit(var2);
      } else {
         var1 = (MonetaryLimit)var0;
      }

      return var1;
   }

   public BigInteger getAmount() {
      return this.amount.getValue();
   }

   public String getCurrency() {
      return this.currency.getString();
   }

   public BigInteger getExponent() {
      return this.exponent.getValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERPrintableString var2 = this.currency;
      var1.add(var2);
      DERInteger var3 = this.amount;
      var1.add(var3);
      DERInteger var4 = this.exponent;
      var1.add(var4);
      return new DERSequence(var1);
   }
}
