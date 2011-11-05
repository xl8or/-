package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;

public interface StreamCipher {

   String getAlgorithmName();

   void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

   void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

   void reset();

   byte returnByte(byte var1);
}
