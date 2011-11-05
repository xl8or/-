package myorg.bouncycastle.cms;

import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.cms.CMSSignedHelper;

public class CMSConfig {

   public CMSConfig() {}

   public static void setSigningDigestAlgorithmMapping(String var0, String var1) {
      DERObjectIdentifier var2 = new DERObjectIdentifier(var0);
      CMSSignedHelper.INSTANCE.setSigningDigestAlgorithmMapping(var2, var1);
   }

   public static void setSigningEncryptionAlgorithmMapping(String var0, String var1) {
      DERObjectIdentifier var2 = new DERObjectIdentifier(var0);
      CMSSignedHelper.INSTANCE.setSigningEncryptionAlgorithmMapping(var2, var1);
   }
}
