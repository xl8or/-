package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class RC4Engine implements StreamCipher {

   private static final int STATE_LENGTH = 256;
   private byte[] engineState = null;
   private byte[] workingKey = null;
   private int x = 0;
   private int y = 0;


   public RC4Engine() {}

   private void setKey(byte[] var1) {
      this.workingKey = var1;
      this.x = 0;
      this.y = 0;
      if(this.engineState == null) {
         byte[] var2 = new byte[256];
         this.engineState = var2;
      }

      for(int var3 = 0; var3 < 256; ++var3) {
         byte[] var4 = this.engineState;
         byte var5 = (byte)var3;
         var4[var3] = var5;
      }

      int var6 = 0;
      int var7 = 0;

      for(int var8 = 0; var8 < 256; ++var8) {
         int var9 = var1[var6] & 255;
         byte var10 = this.engineState[var8];
         var7 = var9 + var10 + var7 & 255;
         byte var11 = this.engineState[var8];
         byte[] var12 = this.engineState;
         byte var13 = this.engineState[var7];
         var12[var8] = var13;
         this.engineState[var7] = var11;
         int var14 = var6 + 1;
         int var15 = var1.length;
         var6 = var14 % var15;
      }

   }

   public String getAlgorithmName() {
      return "RC4";
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         byte[] var3 = ((KeyParameter)var2).getKey();
         this.workingKey = var3;
         byte[] var4 = this.workingKey;
         this.setKey(var4);
      } else {
         StringBuilder var5 = (new StringBuilder()).append("invalid parameter passed to RC4 init - ");
         String var6 = var2.getClass().getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
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
               int var11 = this.x + 1 & 255;
               this.x = var11;
               byte[] var12 = this.engineState;
               int var13 = this.x;
               byte var14 = var12[var13];
               int var15 = this.y;
               int var16 = var14 + var15 & 255;
               this.y = var16;
               byte[] var17 = this.engineState;
               int var18 = this.x;
               byte var19 = var17[var18];
               byte[] var20 = this.engineState;
               int var21 = this.x;
               byte[] var22 = this.engineState;
               int var23 = this.y;
               byte var24 = var22[var23];
               var20[var21] = var24;
               byte[] var25 = this.engineState;
               int var26 = this.y;
               var25[var26] = var19;
               int var27 = var10 + var5;
               int var28 = var10 + var2;
               byte var29 = var1[var28];
               byte[] var30 = this.engineState;
               byte[] var31 = this.engineState;
               int var32 = this.x;
               byte var33 = var31[var32];
               byte[] var34 = this.engineState;
               int var35 = this.y;
               byte var36 = var34[var35];
               int var37 = var33 + var36 & 255;
               byte var38 = var30[var37];
               byte var39 = (byte)(var29 ^ var38);
               var4[var27] = var39;
            }

         }
      }
   }

   public void reset() {
      byte[] var1 = this.workingKey;
      this.setKey(var1);
   }

   public byte returnByte(byte var1) {
      int var2 = this.x + 1 & 255;
      this.x = var2;
      byte[] var3 = this.engineState;
      int var4 = this.x;
      byte var5 = var3[var4];
      int var6 = this.y;
      int var7 = var5 + var6 & 255;
      this.y = var7;
      byte[] var8 = this.engineState;
      int var9 = this.x;
      byte var10 = var8[var9];
      byte[] var11 = this.engineState;
      int var12 = this.x;
      byte[] var13 = this.engineState;
      int var14 = this.y;
      byte var15 = var13[var14];
      var11[var12] = var15;
      byte[] var16 = this.engineState;
      int var17 = this.y;
      var16[var17] = var10;
      byte[] var18 = this.engineState;
      byte[] var19 = this.engineState;
      int var20 = this.x;
      byte var21 = var19[var20];
      byte[] var22 = this.engineState;
      int var23 = this.y;
      byte var24 = var22[var23];
      int var25 = var21 + var24 & 255;
      return (byte)(var18[var25] ^ var1);
   }
}
