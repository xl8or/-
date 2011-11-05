package myorg.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import myorg.bouncycastle.crypto.engines.GOST28147Engine;

public class GOST28147ParameterSpec implements AlgorithmParameterSpec {

   private byte[] iv;
   private byte[] sBox;


   public GOST28147ParameterSpec(String var1) {
      this.iv = null;
      this.sBox = null;
      byte[] var2 = GOST28147Engine.getSBox(var1);
      this.sBox = var2;
   }

   public GOST28147ParameterSpec(String var1, byte[] var2) {
      this(var1);
      byte[] var3 = new byte[var2.length];
      this.iv = var3;
      byte[] var4 = this.iv;
      int var5 = var2.length;
      System.arraycopy(var2, 0, var4, 0, var5);
   }

   public GOST28147ParameterSpec(byte[] var1) {
      this.iv = null;
      this.sBox = null;
      byte[] var2 = new byte[var1.length];
      this.sBox = var2;
      byte[] var3 = this.sBox;
      int var4 = var1.length;
      System.arraycopy(var1, 0, var3, 0, var4);
   }

   public GOST28147ParameterSpec(byte[] var1, byte[] var2) {
      this(var1);
      byte[] var3 = new byte[var2.length];
      this.iv = var3;
      byte[] var4 = this.iv;
      int var5 = var2.length;
      System.arraycopy(var2, 0, var4, 0, var5);
   }

   public byte[] getIV() {
      byte[] var1;
      if(this.iv == null) {
         var1 = null;
      } else {
         byte[] var2 = new byte[this.iv.length];
         byte[] var3 = this.iv;
         int var4 = var2.length;
         System.arraycopy(var3, 0, var2, 0, var4);
         var1 = var2;
      }

      return var1;
   }

   public byte[] getSbox() {
      return this.sBox;
   }
}
