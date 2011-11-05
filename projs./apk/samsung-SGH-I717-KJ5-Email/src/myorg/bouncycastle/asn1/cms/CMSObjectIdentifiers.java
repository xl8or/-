package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public interface CMSObjectIdentifiers {

   DERObjectIdentifier authEnvelopedData = PKCSObjectIdentifiers.id_ct_authEnvelopedData;
   DERObjectIdentifier authenticatedData = PKCSObjectIdentifiers.id_ct_authData;
   DERObjectIdentifier compressedData = PKCSObjectIdentifiers.id_ct_compressedData;
   DERObjectIdentifier data = PKCSObjectIdentifiers.data;
   DERObjectIdentifier digestedData = PKCSObjectIdentifiers.digestedData;
   DERObjectIdentifier encryptedData = PKCSObjectIdentifiers.encryptedData;
   DERObjectIdentifier envelopedData = PKCSObjectIdentifiers.envelopedData;
   DERObjectIdentifier signedAndEnvelopedData = PKCSObjectIdentifiers.signedAndEnvelopedData;
   DERObjectIdentifier signedData = PKCSObjectIdentifiers.signedData;


}
