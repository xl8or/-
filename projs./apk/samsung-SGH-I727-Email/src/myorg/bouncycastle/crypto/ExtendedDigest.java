package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.Digest;

public interface ExtendedDigest extends Digest {

   int getByteLength();
}
