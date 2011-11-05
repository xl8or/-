package myorg.bouncycastle.crypto;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.CipherParameters;

public interface DSA {

   BigInteger[] generateSignature(byte[] var1);

   void init(boolean var1, CipherParameters var2);

   boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3);
}
