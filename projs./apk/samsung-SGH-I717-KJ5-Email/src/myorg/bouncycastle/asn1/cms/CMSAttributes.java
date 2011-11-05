package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public interface CMSAttributes {

   DERObjectIdentifier contentHint = PKCSObjectIdentifiers.id_aa_contentHint;
   DERObjectIdentifier contentType = PKCSObjectIdentifiers.pkcs_9_at_contentType;
   DERObjectIdentifier counterSignature = PKCSObjectIdentifiers.pkcs_9_at_counterSignature;
   DERObjectIdentifier messageDigest = PKCSObjectIdentifiers.pkcs_9_at_messageDigest;
   DERObjectIdentifier signingTime = PKCSObjectIdentifiers.pkcs_9_at_signingTime;


}
