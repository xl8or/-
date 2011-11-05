package myorg.bouncycastle.asn1.smime;

import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.RecipientKeyIdentifier;
import myorg.bouncycastle.asn1.smime.SMIMEAttributes;

public class SMIMEEncryptionKeyPreferenceAttribute extends Attribute {

   public SMIMEEncryptionKeyPreferenceAttribute(ASN1OctetString var1) {
      DERObjectIdentifier var2 = SMIMEAttributes.encrypKeyPref;
      DERTaggedObject var3 = new DERTaggedObject((boolean)0, 2, var1);
      DERSet var4 = new DERSet(var3);
      super(var2, var4);
   }

   public SMIMEEncryptionKeyPreferenceAttribute(IssuerAndSerialNumber var1) {
      DERObjectIdentifier var2 = SMIMEAttributes.encrypKeyPref;
      DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var1);
      DERSet var4 = new DERSet(var3);
      super(var2, var4);
   }

   public SMIMEEncryptionKeyPreferenceAttribute(RecipientKeyIdentifier var1) {
      DERObjectIdentifier var2 = SMIMEAttributes.encrypKeyPref;
      DERTaggedObject var3 = new DERTaggedObject((boolean)0, 1, var1);
      DERSet var4 = new DERSet(var3);
      super(var2, var4);
   }
}
