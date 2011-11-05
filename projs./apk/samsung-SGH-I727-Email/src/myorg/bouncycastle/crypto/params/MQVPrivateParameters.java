package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.ECPrivateKeyParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;

public class MQVPrivateParameters implements CipherParameters {

   private ECPrivateKeyParameters ephemeralPrivateKey;
   private ECPublicKeyParameters ephemeralPublicKey;
   private ECPrivateKeyParameters staticPrivateKey;


   public MQVPrivateParameters(ECPrivateKeyParameters var1, ECPrivateKeyParameters var2) {
      this(var1, var2, (ECPublicKeyParameters)null);
   }

   public MQVPrivateParameters(ECPrivateKeyParameters var1, ECPrivateKeyParameters var2, ECPublicKeyParameters var3) {
      this.staticPrivateKey = var1;
      this.ephemeralPrivateKey = var2;
      this.ephemeralPublicKey = var3;
   }

   public ECPrivateKeyParameters getEphemeralPrivateKey() {
      return this.ephemeralPrivateKey;
   }

   public ECPublicKeyParameters getEphemeralPublicKey() {
      return this.ephemeralPublicKey;
   }

   public ECPrivateKeyParameters getStaticPrivateKey() {
      return this.staticPrivateKey;
   }
}
