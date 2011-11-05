package myorg.bouncycastle.asn1.x509.qualified;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERPrintableString;

public class Iso4217CurrencyCode extends ASN1Encodable implements ASN1Choice {

   final int ALPHABETIC_MAXSIZE = 3;
   final int NUMERIC_MAXSIZE = 999;
   final int NUMERIC_MINSIZE = 1;
   int numeric;
   DEREncodable obj;


   public Iso4217CurrencyCode(int var1) {
      if(var1 <= 999 && var1 >= 1) {
         DERInteger var2 = new DERInteger(var1);
         this.obj = var2;
      } else {
         throw new IllegalArgumentException("wrong size in numeric code : not in (1..999)");
      }
   }

   public Iso4217CurrencyCode(String var1) {
      if(var1.length() > 3) {
         throw new IllegalArgumentException("wrong size in alphabetic code : max size is 3");
      } else {
         DERPrintableString var2 = new DERPrintableString(var1);
         this.obj = var2;
      }
   }

   public static Iso4217CurrencyCode getInstance(Object var0) {
      Iso4217CurrencyCode var1;
      if(var0 != null && !(var0 instanceof Iso4217CurrencyCode)) {
         if(var0 instanceof DERInteger) {
            int var2 = DERInteger.getInstance(var0).getValue().intValue();
            var1 = new Iso4217CurrencyCode(var2);
         } else {
            if(!(var0 instanceof DERPrintableString)) {
               throw new IllegalArgumentException("unknown object in getInstance");
            }

            String var3 = DERPrintableString.getInstance(var0).getString();
            var1 = new Iso4217CurrencyCode(var3);
         }
      } else {
         var1 = (Iso4217CurrencyCode)var0;
      }

      return var1;
   }

   public String getAlphabetic() {
      return ((DERPrintableString)this.obj).getString();
   }

   public int getNumeric() {
      return ((DERInteger)this.obj).getValue().intValue();
   }

   public boolean isAlphabetic() {
      return this.obj instanceof DERPrintableString;
   }

   public DERObject toASN1Object() {
      return this.obj.getDERObject();
   }
}
