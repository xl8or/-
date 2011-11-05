package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;

public interface BlockCipher {

   String getAlgorithmName();

   int getBlockSize();

   void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

   int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException;

   void reset();
}
