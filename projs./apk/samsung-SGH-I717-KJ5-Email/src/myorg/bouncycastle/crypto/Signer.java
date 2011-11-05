package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.DataLengthException;

public interface Signer {

   byte[] generateSignature() throws CryptoException, DataLengthException;

   void init(boolean var1, CipherParameters var2);

   void reset();

   void update(byte var1);

   void update(byte[] var1, int var2, int var3);

   boolean verifySignature(byte[] var1);
}
