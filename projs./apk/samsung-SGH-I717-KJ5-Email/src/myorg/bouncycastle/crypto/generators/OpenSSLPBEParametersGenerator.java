package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class OpenSSLPBEParametersGenerator extends PBEParametersGenerator {

   private Digest digest;


   public OpenSSLPBEParametersGenerator() {
      MD5Digest var1 = new MD5Digest();
      this.digest = var1;
   }

   private byte[] generateDerivedKey(int var1) {
      byte[] var2 = new byte[this.digest.getDigestSize()];
      byte[] var3 = new byte[var1];
      int var4 = 0;

      while(true) {
         Digest var5 = this.digest;
         byte[] var6 = this.password;
         int var7 = this.password.length;
         var5.update(var6, 0, var7);
         Digest var8 = this.digest;
         byte[] var9 = this.salt;
         int var10 = this.salt.length;
         var8.update(var9, 0, var10);
         this.digest.doFinal(var2, 0);
         int var12 = var2.length;
         int var13;
         if(var1 > var12) {
            var13 = var2.length;
         } else {
            var13 = var1;
         }

         System.arraycopy(var2, 0, var3, var4, var13);
         var4 += var13;
         var1 -= var13;
         if(var1 == 0) {
            return var3;
         }

         this.digest.reset();
         Digest var14 = this.digest;
         int var15 = var2.length;
         var14.update(var2, 0, var15);
      }
   }

   public CipherParameters generateDerivedMacParameters(int var1) {
      return this.generateDerivedParameters(var1);
   }

   public CipherParameters generateDerivedParameters(int var1) {
      int var2 = var1 / 8;
      byte[] var3 = this.generateDerivedKey(var2);
      return new KeyParameter(var3, 0, var2);
   }

   public CipherParameters generateDerivedParameters(int var1, int var2) {
      int var3 = var1 / 8;
      int var4 = var2 / 8;
      int var5 = var3 + var4;
      byte[] var6 = this.generateDerivedKey(var5);
      KeyParameter var7 = new KeyParameter(var6, 0, var3);
      return new ParametersWithIV(var7, var6, var3, var4);
   }

   public void init(byte[] var1, byte[] var2) {
      super.init(var1, var2, 1);
   }
}
