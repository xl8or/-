package myorg.bouncycastle.asn1.bc;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface BCObjectIdentifiers {

   DERObjectIdentifier bc = new DERObjectIdentifier("1.3.6.1.4.1.22554");
   DERObjectIdentifier bc_pbe;
   DERObjectIdentifier bc_pbe_sha1;
   DERObjectIdentifier bc_pbe_sha1_pkcs12;
   DERObjectIdentifier bc_pbe_sha1_pkcs12_aes128_cbc;
   DERObjectIdentifier bc_pbe_sha1_pkcs12_aes192_cbc;
   DERObjectIdentifier bc_pbe_sha1_pkcs12_aes256_cbc;
   DERObjectIdentifier bc_pbe_sha1_pkcs5;
   DERObjectIdentifier bc_pbe_sha224;
   DERObjectIdentifier bc_pbe_sha256;
   DERObjectIdentifier bc_pbe_sha256_pkcs12;
   DERObjectIdentifier bc_pbe_sha256_pkcs12_aes128_cbc;
   DERObjectIdentifier bc_pbe_sha256_pkcs12_aes192_cbc;
   DERObjectIdentifier bc_pbe_sha256_pkcs12_aes256_cbc;
   DERObjectIdentifier bc_pbe_sha256_pkcs5;
   DERObjectIdentifier bc_pbe_sha384;
   DERObjectIdentifier bc_pbe_sha512;


   static {
      StringBuilder var0 = new StringBuilder();
      String var1 = bc.getId();
      String var2 = var0.append(var1).append(".1").toString();
      bc_pbe = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      String var4 = bc_pbe.getId();
      String var5 = var3.append(var4).append(".1").toString();
      bc_pbe_sha1 = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      String var7 = bc_pbe.getId();
      String var8 = var6.append(var7).append(".2.1").toString();
      bc_pbe_sha256 = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      String var10 = bc_pbe.getId();
      String var11 = var9.append(var10).append(".2.2").toString();
      bc_pbe_sha384 = new DERObjectIdentifier(var11);
      StringBuilder var12 = new StringBuilder();
      String var13 = bc_pbe.getId();
      String var14 = var12.append(var13).append(".2.3").toString();
      bc_pbe_sha512 = new DERObjectIdentifier(var14);
      StringBuilder var15 = new StringBuilder();
      String var16 = bc_pbe.getId();
      String var17 = var15.append(var16).append(".2.4").toString();
      bc_pbe_sha224 = new DERObjectIdentifier(var17);
      StringBuilder var18 = new StringBuilder();
      String var19 = bc_pbe_sha1.getId();
      String var20 = var18.append(var19).append(".1").toString();
      bc_pbe_sha1_pkcs5 = new DERObjectIdentifier(var20);
      StringBuilder var21 = new StringBuilder();
      String var22 = bc_pbe_sha1.getId();
      String var23 = var21.append(var22).append(".2").toString();
      bc_pbe_sha1_pkcs12 = new DERObjectIdentifier(var23);
      StringBuilder var24 = new StringBuilder();
      String var25 = bc_pbe_sha256.getId();
      String var26 = var24.append(var25).append(".1").toString();
      bc_pbe_sha256_pkcs5 = new DERObjectIdentifier(var26);
      StringBuilder var27 = new StringBuilder();
      String var28 = bc_pbe_sha256.getId();
      String var29 = var27.append(var28).append(".2").toString();
      bc_pbe_sha256_pkcs12 = new DERObjectIdentifier(var29);
      StringBuilder var30 = new StringBuilder();
      String var31 = bc_pbe_sha1_pkcs12.getId();
      String var32 = var30.append(var31).append(".1.2").toString();
      bc_pbe_sha1_pkcs12_aes128_cbc = new DERObjectIdentifier(var32);
      StringBuilder var33 = new StringBuilder();
      String var34 = bc_pbe_sha1_pkcs12.getId();
      String var35 = var33.append(var34).append(".1.22").toString();
      bc_pbe_sha1_pkcs12_aes192_cbc = new DERObjectIdentifier(var35);
      StringBuilder var36 = new StringBuilder();
      String var37 = bc_pbe_sha1_pkcs12.getId();
      String var38 = var36.append(var37).append(".1.42").toString();
      bc_pbe_sha1_pkcs12_aes256_cbc = new DERObjectIdentifier(var38);
      StringBuilder var39 = new StringBuilder();
      String var40 = bc_pbe_sha256_pkcs12.getId();
      String var41 = var39.append(var40).append(".1.2").toString();
      bc_pbe_sha256_pkcs12_aes128_cbc = new DERObjectIdentifier(var41);
      StringBuilder var42 = new StringBuilder();
      String var43 = bc_pbe_sha256_pkcs12.getId();
      String var44 = var42.append(var43).append(".1.22").toString();
      bc_pbe_sha256_pkcs12_aes192_cbc = new DERObjectIdentifier(var44);
      StringBuilder var45 = new StringBuilder();
      String var46 = bc_pbe_sha256_pkcs12.getId();
      String var47 = var45.append(var46).append(".1.42").toString();
      bc_pbe_sha256_pkcs12_aes256_cbc = new DERObjectIdentifier(var47);
   }
}
