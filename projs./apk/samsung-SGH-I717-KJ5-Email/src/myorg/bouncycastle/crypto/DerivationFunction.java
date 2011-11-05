package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.DerivationParameters;
import myorg.bouncycastle.crypto.Digest;

public interface DerivationFunction {

   int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException;

   Digest getDigest();

   void init(DerivationParameters var1);
}
