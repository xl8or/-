package myorg.bouncycastle.asn1.nist;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface NISTObjectIdentifiers {

   String aes = "2.16.840.1.101.3.4.1";
   DERObjectIdentifier dsa_with_sha224;
   DERObjectIdentifier dsa_with_sha256;
   DERObjectIdentifier dsa_with_sha384;
   DERObjectIdentifier dsa_with_sha512;
   DERObjectIdentifier id_aes128_CBC = new DERObjectIdentifier("2.16.840.1.101.3.4.1.2");
   DERObjectIdentifier id_aes128_CCM = new DERObjectIdentifier("2.16.840.1.101.3.4.1.7");
   DERObjectIdentifier id_aes128_CFB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.4");
   DERObjectIdentifier id_aes128_ECB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.1");
   DERObjectIdentifier id_aes128_GCM = new DERObjectIdentifier("2.16.840.1.101.3.4.1.6");
   DERObjectIdentifier id_aes128_OFB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.3");
   DERObjectIdentifier id_aes128_wrap = new DERObjectIdentifier("2.16.840.1.101.3.4.1.5");
   DERObjectIdentifier id_aes192_CBC = new DERObjectIdentifier("2.16.840.1.101.3.4.1.22");
   DERObjectIdentifier id_aes192_CCM = new DERObjectIdentifier("2.16.840.1.101.3.4.1.27");
   DERObjectIdentifier id_aes192_CFB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.24");
   DERObjectIdentifier id_aes192_ECB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.21");
   DERObjectIdentifier id_aes192_GCM = new DERObjectIdentifier("2.16.840.1.101.3.4.1.26");
   DERObjectIdentifier id_aes192_OFB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.23");
   DERObjectIdentifier id_aes192_wrap = new DERObjectIdentifier("2.16.840.1.101.3.4.1.25");
   DERObjectIdentifier id_aes256_CBC = new DERObjectIdentifier("2.16.840.1.101.3.4.1.42");
   DERObjectIdentifier id_aes256_CCM = new DERObjectIdentifier("2.16.840.1.101.3.4.1.47");
   DERObjectIdentifier id_aes256_CFB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.44");
   DERObjectIdentifier id_aes256_ECB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.41");
   DERObjectIdentifier id_aes256_GCM = new DERObjectIdentifier("2.16.840.1.101.3.4.1.46");
   DERObjectIdentifier id_aes256_OFB = new DERObjectIdentifier("2.16.840.1.101.3.4.1.43");
   DERObjectIdentifier id_aes256_wrap = new DERObjectIdentifier("2.16.840.1.101.3.4.1.45");
   DERObjectIdentifier id_dsa_with_sha2 = new DERObjectIdentifier("2.16.840.1.101.3.4.3");
   DERObjectIdentifier id_sha224 = new DERObjectIdentifier("2.16.840.1.101.3.4.2.4");
   DERObjectIdentifier id_sha256 = new DERObjectIdentifier("2.16.840.1.101.3.4.2.1");
   DERObjectIdentifier id_sha384 = new DERObjectIdentifier("2.16.840.1.101.3.4.2.2");
   DERObjectIdentifier id_sha512 = new DERObjectIdentifier("2.16.840.1.101.3.4.2.3");
   String nistAlgorithm = "2.16.840.1.101.3.4";


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = id_dsa_with_sha2;
      String var2 = var0.append(var1).append(".1").toString();
      dsa_with_sha224 = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = id_dsa_with_sha2;
      String var5 = var3.append(var4).append(".2").toString();
      dsa_with_sha256 = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      DERObjectIdentifier var7 = id_dsa_with_sha2;
      String var8 = var6.append(var7).append(".3").toString();
      dsa_with_sha384 = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      DERObjectIdentifier var10 = id_dsa_with_sha2;
      String var11 = var9.append(var10).append(".4").toString();
      dsa_with_sha512 = new DERObjectIdentifier(var11);
   }
}
