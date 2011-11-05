package myorg.bouncycastle.crypto;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;

public interface BasicAgreement {

   BigInteger calculateAgreement(CipherParameters var1);

   void init(CipherParameters var1);
}
