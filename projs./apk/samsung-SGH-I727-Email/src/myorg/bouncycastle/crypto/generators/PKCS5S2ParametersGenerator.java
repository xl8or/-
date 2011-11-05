package myorg.bouncycastle.crypto.generators;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.macs.HMac;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS5S2ParametersGenerator extends PBEParametersGenerator {

   private Mac hMac;


   public PKCS5S2ParametersGenerator() {
      SHA1Digest var1 = new SHA1Digest();
      HMac var2 = new HMac(var1);
      this.hMac = var2;
   }

   private void F(byte[] var1, byte[] var2, int var3, byte[] var4, byte[] var5, int var6) {
      byte[] var7 = new byte[this.hMac.getMacSize()];
      KeyParameter var8 = new KeyParameter(var1);
      this.hMac.init(var8);
      if(var2 != null) {
         Mac var9 = this.hMac;
         int var10 = var2.length;
         var9.update(var2, 0, var10);
      }

      Mac var11 = this.hMac;
      int var12 = var4.length;
      var11.update(var4, 0, var12);
      this.hMac.doFinal(var7, 0);
      int var14 = var7.length;
      System.arraycopy(var7, 0, var5, var6, var14);
      if(var3 == 0) {
         throw new IllegalArgumentException("iteration count must be at least 1.");
      } else {
         int var15 = 1;

         while(var15 < var3) {
            this.hMac.init(var8);
            Mac var16 = this.hMac;
            int var17 = var7.length;
            var16.update(var7, 0, var17);
            this.hMac.doFinal(var7, 0);
            int var19 = 0;

            while(true) {
               int var20 = var7.length;
               if(var19 == var20) {
                  ++var15;
                  break;
               }

               int var21 = var6 + var19;
               byte var22 = var5[var21];
               byte var23 = var7[var19];
               byte var24 = (byte)(var22 ^ var23);
               var5[var21] = var24;
               ++var19;
            }
         }

      }
   }

   private byte[] generateDerivedKey(int var1) {
      int var2 = this.hMac.getMacSize();
      int var3 = (var1 + var2 - 1) / var2;
      byte[] var4 = new byte[4];
      byte[] var5 = new byte[var3 * var2];

      for(int var6 = 1; var6 <= var3; ++var6) {
         this.intToOctet(var4, var6);
         byte[] var7 = this.password;
         byte[] var8 = this.salt;
         int var9 = this.iterationCount;
         int var10 = (var6 - 1) * var2;
         this.F(var7, var8, var9, var4, var5, var10);
      }

      return var5;
   }

   private void intToOctet(byte[] var1, int var2) {
      byte var3 = (byte)(var2 >>> 24);
      var1[0] = var3;
      byte var4 = (byte)(var2 >>> 16);
      var1[1] = var4;
      byte var5 = (byte)(var2 >>> 8);
      var1[2] = var5;
      byte var6 = (byte)var2;
      var1[3] = var6;
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
}
