package myorg.bouncycastle.crypto.modes;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public interface AEADBlockCipher {

   int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException;

   String getAlgorithmName();

   byte[] getMac();

   int getOutputSize(int var1);

   BlockCipher getUnderlyingCipher();

   int getUpdateOutputSize(int var1);

   void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

   int processByte(byte var1, byte[] var2, int var3) throws DataLengthException;

   int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

   void reset();
}
