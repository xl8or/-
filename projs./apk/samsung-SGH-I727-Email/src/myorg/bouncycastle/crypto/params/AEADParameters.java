package myorg.bouncycastle.crypto.params;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class AEADParameters implements CipherParameters {

   private byte[] associatedText;
   private KeyParameter key;
   private int macSize;
   private byte[] nonce;


   public AEADParameters(KeyParameter var1, int var2, byte[] var3, byte[] var4) {
      this.key = var1;
      this.nonce = var3;
      this.macSize = var2;
      this.associatedText = var4;
   }

   public byte[] getAssociatedText() {
      return this.associatedText;
   }

   public KeyParameter getKey() {
      return this.key;
   }

   public int getMacSize() {
      return this.macSize;
   }

   public byte[] getNonce() {
      return this.nonce;
   }
}
