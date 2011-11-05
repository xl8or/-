package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.AsymmetricCipherKeyPair;
import myorg.bouncycastle.crypto.KeyGenerationParameters;

public interface AsymmetricCipherKeyPairGenerator {

   AsymmetricCipherKeyPair generateKeyPair();

   void init(KeyGenerationParameters var1);
}
