package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.util.Strings;

public abstract class X509NameEntryConverter {

   public X509NameEntryConverter() {}

   protected boolean canBePrintable(String var1) {
      return DERPrintableString.isPrintableString(var1);
   }

   protected DERObject convertHexEncoded(String var1, int var2) throws IOException {
      String var3 = Strings.toLowerCase(var1);
      byte[] var4 = new byte[(var3.length() - var2) / 2];
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 == var6) {
            return (new ASN1InputStream(var4)).readObject();
         }

         int var7 = var5 * 2 + var2;
         char var8 = var3.charAt(var7);
         int var9 = var5 * 2 + var2 + 1;
         char var10 = var3.charAt(var9);
         if(var8 < 97) {
            byte var11 = (byte)(var8 - 48 << 4);
            var4[var5] = var11;
         } else {
            byte var15 = (byte)(var8 - 97 + 10 << 4);
            var4[var5] = var15;
         }

         if(var10 < 97) {
            byte var12 = var4[var5];
            byte var13 = (byte)(var10 - 48);
            byte var14 = (byte)(var12 | var13);
            var4[var5] = var14;
         } else {
            byte var16 = var4[var5];
            byte var17 = (byte)(var10 - 97 + 10);
            byte var18 = (byte)(var16 | var17);
            var4[var5] = var18;
         }

         ++var5;
      }
   }

   public abstract DERObject getConvertedValue(DERObjectIdentifier var1, String var2);
}
