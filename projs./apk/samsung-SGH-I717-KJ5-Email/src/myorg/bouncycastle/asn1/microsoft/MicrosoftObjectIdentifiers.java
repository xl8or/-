package myorg.bouncycastle.asn1.microsoft;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface MicrosoftObjectIdentifiers {

   DERObjectIdentifier microsoft = new DERObjectIdentifier("1.3.6.1.4.1.311");
   DERObjectIdentifier microsoftAppPolicies;
   DERObjectIdentifier microsoftCaVersion;
   DERObjectIdentifier microsoftCertTemplateV1;
   DERObjectIdentifier microsoftCertTemplateV2;
   DERObjectIdentifier microsoftPrevCaCertHash;


   static {
      StringBuilder var0 = new StringBuilder();
      DERObjectIdentifier var1 = microsoft;
      String var2 = var0.append(var1).append(".20.2").toString();
      microsoftCertTemplateV1 = new DERObjectIdentifier(var2);
      StringBuilder var3 = new StringBuilder();
      DERObjectIdentifier var4 = microsoft;
      String var5 = var3.append(var4).append(".21.1").toString();
      microsoftCaVersion = new DERObjectIdentifier(var5);
      StringBuilder var6 = new StringBuilder();
      DERObjectIdentifier var7 = microsoft;
      String var8 = var6.append(var7).append(".21.2").toString();
      microsoftPrevCaCertHash = new DERObjectIdentifier(var8);
      StringBuilder var9 = new StringBuilder();
      DERObjectIdentifier var10 = microsoft;
      String var11 = var9.append(var10).append(".21.7").toString();
      microsoftCertTemplateV2 = new DERObjectIdentifier(var11);
      StringBuilder var12 = new StringBuilder();
      DERObjectIdentifier var13 = microsoft;
      String var14 = var12.append(var13).append(".21.10").toString();
      microsoftAppPolicies = new DERObjectIdentifier(var14);
   }
}
