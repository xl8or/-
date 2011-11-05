package myorg.bouncycastle.cms;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import myorg.bouncycastle.asn1.cms.RecipientInfo;

interface RecipientInfoGenerator {

   RecipientInfo generate(SecretKey var1, SecureRandom var2, Provider var3) throws GeneralSecurityException;
}
