package myorg.bouncycastle.asn1.esf;

import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public interface ESFAttributes {

   DERObjectIdentifier archiveTimestamp = PKCSObjectIdentifiers.id_aa_ets_archiveTimestamp;
   DERObjectIdentifier certCRLTimestamp = PKCSObjectIdentifiers.id_aa_ets_certCRLTimestamp;
   DERObjectIdentifier certValues = PKCSObjectIdentifiers.id_aa_ets_certValues;
   DERObjectIdentifier certificateRefs = PKCSObjectIdentifiers.id_aa_ets_certificateRefs;
   DERObjectIdentifier commitmentType = PKCSObjectIdentifiers.id_aa_ets_commitmentType;
   DERObjectIdentifier contentTimestamp = PKCSObjectIdentifiers.id_aa_ets_contentTimestamp;
   DERObjectIdentifier escTimeStamp = PKCSObjectIdentifiers.id_aa_ets_escTimeStamp;
   DERObjectIdentifier otherSigCert = PKCSObjectIdentifiers.id_aa_ets_otherSigCert;
   DERObjectIdentifier revocationRefs = PKCSObjectIdentifiers.id_aa_ets_revocationRefs;
   DERObjectIdentifier revocationValues = PKCSObjectIdentifiers.id_aa_ets_revocationValues;
   DERObjectIdentifier sigPolicyId = PKCSObjectIdentifiers.id_aa_ets_sigPolicyId;
   DERObjectIdentifier signerAttr = PKCSObjectIdentifiers.id_aa_ets_signerAttr;
   DERObjectIdentifier signerLocation = PKCSObjectIdentifiers.id_aa_ets_signerLocation;


}
