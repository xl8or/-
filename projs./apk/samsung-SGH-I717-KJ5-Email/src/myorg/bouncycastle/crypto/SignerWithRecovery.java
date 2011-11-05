package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.Signer;

public interface SignerWithRecovery extends Signer {

   byte[] getRecoveredMessage();

   boolean hasFullMessage();
}
