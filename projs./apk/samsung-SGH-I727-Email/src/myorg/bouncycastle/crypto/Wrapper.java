package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public interface Wrapper {

   String getAlgorithmName();

   void init(boolean var1, CipherParameters var2);

   byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException;

   byte[] wrap(byte[] var1, int var2, int var3);
}
