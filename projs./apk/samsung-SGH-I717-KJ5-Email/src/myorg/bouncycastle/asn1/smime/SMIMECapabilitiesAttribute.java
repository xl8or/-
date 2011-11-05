package myorg.bouncycastle.asn1.smime;

import myorg.bouncycastle.asn1.DEREncodableVector;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.smime.SMIMEAttributes;
import myorg.bouncycastle.asn1.smime.SMIMECapabilityVector;

public class SMIMECapabilitiesAttribute extends Attribute {

   public SMIMECapabilitiesAttribute(SMIMECapabilityVector var1) {
      DERObjectIdentifier var2 = SMIMEAttributes.smimeCapabilities;
      DEREncodableVector var3 = var1.toDEREncodableVector();
      DERSequence var4 = new DERSequence(var3);
      DERSet var5 = new DERSet(var4);
      super(var2, var5);
   }
}
