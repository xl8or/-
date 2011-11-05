package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public interface AsymmetricBlockCipher {

   int getInputBlockSize();

   int getOutputBlockSize();

   void init(boolean var1, CipherParameters var2);

   byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException;
}
