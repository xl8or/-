package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.ECPublicKeyParameters;

public class MQVPublicParameters implements CipherParameters {

   private ECPublicKeyParameters ephemeralPublicKey;
   private ECPublicKeyParameters staticPublicKey;


   public MQVPublicParameters(ECPublicKeyParameters var1, ECPublicKeyParameters var2) {
      this.staticPublicKey = var1;
      this.ephemeralPublicKey = var2;
   }

   public ECPublicKeyParameters getEphemeralPublicKey() {
      return this.ephemeralPublicKey;
   }

   public ECPublicKeyParameters getStaticPublicKey() {
      return this.staticPublicKey;
   }
}
