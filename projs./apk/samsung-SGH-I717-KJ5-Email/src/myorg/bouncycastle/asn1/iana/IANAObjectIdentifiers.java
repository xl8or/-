package myorg.bouncycastle.asn1.iana;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface IANAObjectIdentifiers {

   DERObjectIdentifier hmacMD5;
   DERObjectIdentifier hmacRIPEMD160;
   DERObjectIdentifier hmacSHA1;
   DERObjectIdentifier hmacTIGER;
   DERObjectIdentifier isakmpOakley = new DERObjectIdentifier("1.3.6.1.5.5.8.1");


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = isakmpOakley;
      String var2 = var0.append(var1).append(".1").toString();
      hmacMD5 = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = isakmpOakley;
      String var5 = var3.append(var4).append(".2").toString();
      hmacSHA1 = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      DERObjectIdentifier var7 = isakmpOakley;
      String var8 = var6.append(var7).append(".3").toString();
      hmacTIGER = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      DERObjectIdentifier var10 = isakmpOakley;
      String var11 = var9.append(var10).append(".4").toString();
      hmacRIPEMD160 = new DERObjectIdentifier(var11);
   }
}
