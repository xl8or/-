package myorg.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class IESParameterSpec implements AlgorithmParameterSpec {

   private byte[] derivation;
   private byte[] encoding;
   private int macKeySize;


   public IESParameterSpec(byte[] var1, byte[] var2, int var3) {
      byte[] var4 = new byte[var1.length];
      this.derivation = var4;
      byte[] var5 = this.derivation;
      int var6 = var1.length;
      System.arraycopy(var1, 0, var5, 0, var6);
      byte[] var7 = new byte[var2.length];
      this.encoding = var7;
      byte[] var8 = this.encoding;
      int var9 = var2.length;
      System.arraycopy(var2, 0, var8, 0, var9);
      this.macKeySize = var3;
   }

   public byte[] getDerivationV() {
      return this.derivation;
   }

   public byte[] getEncodingV() {
      return this.encoding;
   }

   public int getMacKeySize() {
      return this.macKeySize;
   }
}
