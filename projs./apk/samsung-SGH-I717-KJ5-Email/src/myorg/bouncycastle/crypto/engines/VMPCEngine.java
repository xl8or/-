package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class VMPCEngine implements StreamCipher {

   protected byte[] P = null;
   protected byte n = 0;
   protected byte s = 0;
   protected byte[] workingIV;
   protected byte[] workingKey;


   public VMPCEngine() {}

   public String getAlgorithmName() {
      return "VMPC";
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("VMPC init parameters must include an IV");
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         KeyParameter var4 = (KeyParameter)var3.getParameters();
         if(!(var3.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC init parameters must include a key");
         } else {
            byte[] var5 = var3.getIV();
            this.workingIV = var5;
            if(this.workingIV != null && this.workingIV.length >= 1 && this.workingIV.length <= 768) {
               byte[] var6 = var4.getKey();
               this.workingKey = var6;
               byte[] var7 = this.workingKey;
               byte[] var8 = this.workingIV;
               this.initKey(var7, var8);
            } else {
               throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
            }
         }
      }
   }

   protected void initKey(byte[] var1, byte[] var2) {
      this.s = 0;
      byte[] var3 = new byte[256];
      this.P = var3;

      for(int var4 = 0; var4 < 256; ++var4) {
         byte[] var5 = this.P;
         byte var6 = (byte)var4;
         var5[var4] = var6;
      }

      for(int var7 = 0; var7 < 768; ++var7) {
         byte[] var8 = this.P;
         byte var9 = this.s;
         byte[] var10 = this.P;
         int var11 = var7 & 255;
         byte var12 = var10[var11];
         int var13 = var9 + var12;
         int var14 = var1.length;
         int var15 = var7 % var14;
         byte var16 = var1[var15];
         int var17 = var13 + var16 & 255;
         byte var18 = var8[var17];
         this.s = var18;
         byte[] var19 = this.P;
         int var20 = var7 & 255;
         byte var21 = var19[var20];
         byte[] var22 = this.P;
         int var23 = var7 & 255;
         byte[] var24 = this.P;
         int var25 = this.s & 255;
         byte var26 = var24[var25];
         var22[var23] = var26;
         byte[] var27 = this.P;
         int var28 = this.s & 255;
         var27[var28] = var21;
      }

      for(int var29 = 0; var29 < 768; ++var29) {
         byte[] var30 = this.P;
         byte var31 = this.s;
         byte[] var32 = this.P;
         int var33 = var29 & 255;
         byte var34 = var32[var33];
         int var35 = var31 + var34;
         int var36 = var2.length;
         int var37 = var29 % var36;
         byte var38 = var2[var37];
         int var39 = var35 + var38 & 255;
         byte var40 = var30[var39];
         this.s = var40;
         byte[] var41 = this.P;
         int var42 = var29 & 255;
         byte var43 = var41[var42];
         byte[] var44 = this.P;
         int var45 = var29 & 255;
         byte[] var46 = this.P;
         int var47 = this.s & 255;
         byte var48 = var46[var47];
         var44[var45] = var48;
         byte[] var49 = this.P;
         int var50 = this.s & 255;
         var49[var50] = var43;
      }

      this.n = 0;
   }

   public void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      int var6 = var2 + var3;
      int var7 = var1.length;
      if(var6 > var7) {
         throw new DataLengthException("input buffer too short");
      } else {
         int var8 = var5 + var3;
         int var9 = var4.length;
         if(var8 > var9) {
            throw new DataLengthException("output buffer too short");
         } else {
            for(int var10 = 0; var10 < var3; ++var10) {
               byte[] var11 = this.P;
               byte var12 = this.s;
               byte[] var13 = this.P;
               int var14 = this.n & 255;
               byte var15 = var13[var14];
               int var16 = var12 + var15 & 255;
               byte var17 = var11[var16];
               this.s = var17;
               byte[] var18 = this.P;
               byte[] var19 = this.P;
               byte[] var20 = this.P;
               int var21 = this.s & 255;
               int var22 = var20[var21] & 255;
               int var23 = var19[var22] + 1 & 255;
               byte var24 = var18[var23];
               byte[] var25 = this.P;
               int var26 = this.n & 255;
               byte var27 = var25[var26];
               byte[] var28 = this.P;
               int var29 = this.n & 255;
               byte[] var30 = this.P;
               int var31 = this.s & 255;
               byte var32 = var30[var31];
               var28[var29] = var32;
               byte[] var33 = this.P;
               int var34 = this.s & 255;
               var33[var34] = var27;
               byte var35 = (byte)(this.n + 1 & 255);
               this.n = var35;
               int var36 = var10 + var5;
               int var37 = var10 + var2;
               byte var38 = (byte)(var1[var37] ^ var24);
               var4[var36] = var38;
            }

         }
      }
   }

   public void reset() {
      byte[] var1 = this.workingKey;
      byte[] var2 = this.workingIV;
      this.initKey(var1, var2);
   }

   public byte returnByte(byte var1) {
      byte[] var2 = this.P;
      byte var3 = this.s;
      byte[] var4 = this.P;
      int var5 = this.n & 255;
      byte var6 = var4[var5];
      int var7 = var3 + var6 & 255;
      byte var8 = var2[var7];
      this.s = var8;
      byte[] var9 = this.P;
      byte[] var10 = this.P;
      byte[] var11 = this.P;
      int var12 = this.s & 255;
      int var13 = var11[var12] & 255;
      int var14 = var10[var13] + 1 & 255;
      byte var15 = var9[var14];
      byte[] var16 = this.P;
      int var17 = this.n & 255;
      byte var18 = var16[var17];
      byte[] var19 = this.P;
      int var20 = this.n & 255;
      byte[] var21 = this.P;
      int var22 = this.s & 255;
      byte var23 = var21[var22];
      var19[var20] = var23;
      byte[] var24 = this.P;
      int var25 = this.s & 255;
      var24[var25] = var18;
      byte var26 = (byte)(this.n + 1 & 255);
      this.n = var26;
      return (byte)(var1 ^ var15);
   }
}
