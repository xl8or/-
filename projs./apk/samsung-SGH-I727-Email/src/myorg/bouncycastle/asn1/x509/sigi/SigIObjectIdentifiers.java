package myorg.bouncycastle.asn1.x509.sigi;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface SigIObjectIdentifiers {

   DERObjectIdentifier id_sigi = new DERObjectIdentifier("1.3.36.8");
   DERObjectIdentifier id_sigi_cp;
   DERObjectIdentifier id_sigi_cp_sigconform;
   DERObjectIdentifier id_sigi_kp;
   DERObjectIdentifier id_sigi_kp_directoryService;
   DERObjectIdentifier id_sigi_on;
   DERObjectIdentifier id_sigi_on_personalData;


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = id_sigi;
      String var2 = var0.append(var1).append(".2").toString();
      id_sigi_kp = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = id_sigi;
      String var5 = var3.append(var4).append(".1").toString();
      id_sigi_cp = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      DERObjectIdentifier var7 = id_sigi;
      String var8 = var6.append(var7).append(".4").toString();
      id_sigi_on = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      DERObjectIdentifier var10 = id_sigi_kp;
      String var11 = var9.append(var10).append(".1").toString();
      id_sigi_kp_directoryService = new DERObjectIdentifier(var11);
      StringBuilder var12 = new StringBuilder();
      DERObjectIdentifier var13 = id_sigi_on;
      String var14 = var12.append(var13).append(".1").toString();
      id_sigi_on_personalData = new DERObjectIdentifier(var14);
      StringBuilder var15 = new StringBuilder();
      DERObjectIdentifier var16 = id_sigi_cp;
      String var17 = var15.append(var16).append(".1").toString();
      id_sigi_cp_sigconform = new DERObjectIdentifier(var17);
   }
}
