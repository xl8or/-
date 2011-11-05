package myorg.bouncycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import myorg.bouncycastle.jce.interfaces.IESKey;

public class IEKeySpec implements KeySpec, IESKey {

   private PrivateKey privKey;
   private PublicKey pubKey;


   public IEKeySpec(PrivateKey var1, PublicKey var2) {
      this.privKey = var1;
      this.pubKey = var2;
   }

   public String getAlgorithm() {
      return "IES";
   }

   public byte[] getEncoded() {
      return null;
   }

   public String getFormat() {
      return null;
   }

   public PrivateKey getPrivate() {
      return this.privKey;
   }

   public PublicKey getPublic() {
      return this.pubKey;
   }
}
