package myorg.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import myorg.bouncycastle.crypto.InvalidCipherTextException;

public interface BlockCipherPadding {

   int addPadding(byte[] var1, int var2);

   String getPaddingName();

   void init(SecureRandom var1) throws IllegalArgumentException;

   int padCount(byte[] var1) throws InvalidCipherTextException;
}
