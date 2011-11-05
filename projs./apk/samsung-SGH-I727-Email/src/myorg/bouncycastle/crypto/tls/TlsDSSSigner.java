package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.signers.DSADigestSigner;
import myorg.bouncycastle.crypto.signers.DSASigner;

class TlsDSSSigner extends DSADigestSigner {

   TlsDSSSigner() {
      DSASigner var1 = new DSASigner();
      SHA1Digest var2 = new SHA1Digest();
      super(var1, var2);
   }
}
