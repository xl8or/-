package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.DERObjectIdentifier;

public class PolicyQualifierId extends DERObjectIdentifier {

   private static final String id_qt = "1.3.6.1.5.5.7.2";
   public static final PolicyQualifierId id_qt_cps = new PolicyQualifierId("1.3.6.1.5.5.7.2.1");
   public static final PolicyQualifierId id_qt_unotice = new PolicyQualifierId("1.3.6.1.5.5.7.2.2");


   private PolicyQualifierId(String var1) {
      super(var1);
   }
}
