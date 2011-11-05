package myorg.bouncycastle.asn1.icao;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface ICAOObjectIdentifiers {

   String id_icao = "2.23.136";
   DERObjectIdentifier id_icao_ldsSecurityObject;
   DERObjectIdentifier id_icao_mrtd = new DERObjectIdentifier("2.23.136.1");
   DERObjectIdentifier id_icao_mrtd_security;


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = id_icao_mrtd;
      String var2 = var0.append(var1).append(".1").toString();
      id_icao_mrtd_security = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = id_icao_mrtd_security;
      String var5 = var3.append(var4).append(".1").toString();
      id_icao_ldsSecurityObject = new DERObjectIdentifier(var5);
   }
}
