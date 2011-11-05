package myorg.bouncycastle.crypto.tls;

import myorg.bouncycastle.crypto.encodings.PKCS1Encoding;
import myorg.bouncycastle.crypto.engines.RSABlindedEngine;
import myorg.bouncycastle.crypto.signers.GenericSigner;
import myorg.bouncycastle.crypto.tls.CombinedHash;

class TlsRSASigner extends GenericSigner {

   TlsRSASigner() {
      RSABlindedEngine var1 = new RSABlindedEngine();
      PKCS1Encoding var2 = new PKCS1Encoding(var1);
      CombinedHash var3 = new CombinedHash();
      super(var2, var3);
   }
}
