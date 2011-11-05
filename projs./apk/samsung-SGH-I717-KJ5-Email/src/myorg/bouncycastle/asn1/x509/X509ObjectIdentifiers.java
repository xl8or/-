package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface X509ObjectIdentifiers {

   DERObjectIdentifier commonName = new DERObjectIdentifier("2.5.4.3");
   DERObjectIdentifier countryName = new DERObjectIdentifier("2.5.4.6");
   DERObjectIdentifier crlAccessMethod;
   String id = "2.5.4";
   DERObjectIdentifier id_SHA1 = new DERObjectIdentifier("1.3.14.3.2.26");
   DERObjectIdentifier id_ad;
   DERObjectIdentifier id_ad_caIssuers;
   DERObjectIdentifier id_ad_ocsp;
   DERObjectIdentifier id_at_name = new DERObjectIdentifier("2.5.4.41");
   DERObjectIdentifier id_at_telephoneNumber = new DERObjectIdentifier("2.5.4.20");
   DERObjectIdentifier id_ea_rsa = new DERObjectIdentifier("2.5.8.1.1");
   DERObjectIdentifier id_pe;
   DERObjectIdentifier id_pkix = new DERObjectIdentifier("1.3.6.1.5.5.7");
   DERObjectIdentifier localityName = new DERObjectIdentifier("2.5.4.7");
   DERObjectIdentifier ocspAccessMethod;
   DERObjectIdentifier organization = new DERObjectIdentifier("2.5.4.10");
   DERObjectIdentifier organizationalUnitName = new DERObjectIdentifier("2.5.4.11");
   DERObjectIdentifier ripemd160 = new DERObjectIdentifier("1.3.36.3.2.1");
   DERObjectIdentifier ripemd160WithRSAEncryption = new DERObjectIdentifier("1.3.36.3.3.1.2");
   DERObjectIdentifier stateOrProvinceName = new DERObjectIdentifier("2.5.4.8");


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = id_pkix;
      String var2 = var0.append(var1).append(".1").toString();
      id_pe = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = id_pkix;
      String var5 = var3.append(var4).append(".48").toString();
      id_ad = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      DERObjectIdentifier var7 = id_ad;
      String var8 = var6.append(var7).append(".2").toString();
      id_ad_caIssuers = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      DERObjectIdentifier var10 = id_ad;
      String var11 = var9.append(var10).append(".1").toString();
      id_ad_ocsp = new DERObjectIdentifier(var11);
      ocspAccessMethod = id_ad_ocsp;
      crlAccessMethod = id_ad_caIssuers;
   }
}
