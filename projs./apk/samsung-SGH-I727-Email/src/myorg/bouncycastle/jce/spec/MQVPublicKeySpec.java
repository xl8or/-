package myorg.bouncycastle.jce.spec;

import java.security.PublicKey;
import java.security.spec.KeySpec;
import myorg.bouncycastle.jce.interfaces.MQVPublicKey;

public class MQVPublicKeySpec implements KeySpec, MQVPublicKey {

   private PublicKey ephemeralKey;
   private PublicKey staticKey;


   public MQVPublicKeySpec(PublicKey var1, PublicKey var2) {
      this.staticKey = var1;
      this.ephemeralKey = var2;
   }

   public String getAlgorithm() {
      return "ECMQV";
   }

   public byte[] getEncoded() {
      return null;
   }

   public PublicKey getEphemeralKey() {
      return this.ephemeralKey;
   }

   public String getFormat() {
      return null;
   }

   public PublicKey getStaticKey() {
      return this.staticKey;
   }
}
