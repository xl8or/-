package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS5S1ParametersGenerator extends PBEParametersGenerator {

   private Digest digest;


   public PKCS5S1ParametersGenerator(Digest var1) {
      this.digest = var1;
   }

   private byte[] generateDerivedKey() {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      Digest var2 = this.digest;
      byte[] var3 = this.password;
      int var4 = this.password.length;
      var2.update(var3, 0, var4);
      Digest var5 = this.digest;
      byte[] var6 = this.salt;
      int var7 = this.salt.length;
      var5.update(var6, 0, var7);
      this.digest.doFinal(var1, 0);
      int var9 = 1;

      while(true) {
         int var10 = this.iterationCount;
         if(var9 >= var10) {
            return var1;
         }

         Digest var11 = this.digest;
         int var12 = var1.length;
         var11.update(var1, 0, var12);
         this.digest.doFinal(var1, 0);
         ++var9;
      }
   }

   public CipherParameters generateDerivedMacParameters(int var1) {
      return this.generateDerivedParameters(var1);
   }

   public CipherParameters generateDerivedParameters(int var1) {
      int var2 = var1 / 8;
      int var3 = this.digest.getDigestSize();
      if(var2 > var3) {
         String var4 = "Can\'t generate a derived key " + var2 + " bytes long.";
         throw new IllegalArgumentException(var4);
      } else {
         byte[] var5 = this.generateDerivedKey();
         return new KeyParameter(var5, 0, var2);
      }
   }

   public CipherParameters generateDerivedParameters(int var1, int var2) {
      int var3 = var1 / 8;
      int var4 = var2 / 8;
      int var5 = var3 + var4;
      int var6 = this.digest.getDigestSize();
      if(var5 > var6) {
         StringBuilder var7 = (new StringBuilder()).append("Can\'t generate a derived key ");
         int var8 = var3 + var4;
         String var9 = var7.append(var8).append(" bytes long.").toString();
         throw new IllegalArgumentException(var9);
      } else {
         byte[] var10 = this.generateDerivedKey();
         KeyParameter var11 = new KeyParameter(var10, 0, var3);
         return new ParametersWithIV(var11, var10, var3, var4);
      }
   }
}
