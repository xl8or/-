package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;

public interface Mac {

   int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException;

   String getAlgorithmName();

   int getMacSize();

   void init(CipherParameters var1) throws IllegalArgumentException;

   void reset();

   void update(byte var1) throws IllegalStateException;

   void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException;
}
