package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class NoekeonEngine implements BlockCipher {

   private static final int genericSize = 16;
   private static final int[] nullVector = new int[]{0, 0, 0, 0};
   private static final int[] roundConstants = new int[]{128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212};
   private boolean _forEncryption;
   private boolean _initialised;
   private int[] decryptKeys;
   private int[] state;
   private int[] subKeys;


   public NoekeonEngine() {
      int[] var1 = new int[4];
      this.state = var1;
      int[] var2 = new int[4];
      this.subKeys = var2;
      int[] var3 = new int[4];
      this.decryptKeys = var3;
      this._initialised = (boolean)0;
   }

   private int bytesToIntBig(byte[] var1, int var2) {
      int var3 = var2 + 1;
      int var4 = var1[var2] << 24;
      int var5 = var3 + 1;
      int var6 = (var1[var3] & 255) << 16;
      int var7 = var4 | var6;
      int var8 = var5 + 1;
      int var9 = (var1[var5] & 255) << 8;
      int var10 = var7 | var9;
      int var11 = var1[var8] & 255;
      return var10 | var11;
   }

   private int decryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int[] var5 = this.state;
      int var6 = this.bytesToIntBig(var1, var2);
      var5[0] = var6;
      int[] var7 = this.state;
      int var8 = var2 + 4;
      int var9 = this.bytesToIntBig(var1, var8);
      var7[1] = var9;
      int[] var10 = this.state;
      int var11 = var2 + 8;
      int var12 = this.bytesToIntBig(var1, var11);
      var10[2] = var12;
      int[] var13 = this.state;
      int var14 = var2 + 12;
      int var15 = this.bytesToIntBig(var1, var14);
      var13[3] = var15;
      int[] var16 = this.subKeys;
      int[] var17 = this.decryptKeys;
      int var18 = this.subKeys.length;
      System.arraycopy(var16, 0, var17, 0, var18);
      int[] var19 = this.decryptKeys;
      int[] var20 = nullVector;
      this.theta(var19, var20);

      int var21;
      for(var21 = 16; var21 > 0; var21 += -1) {
         int[] var22 = this.state;
         int[] var23 = this.decryptKeys;
         this.theta(var22, var23);
         int[] var24 = this.state;
         int var25 = var24[0];
         int var26 = roundConstants[var21];
         int var27 = var25 ^ var26;
         var24[0] = var27;
         int[] var28 = this.state;
         this.pi1(var28);
         int[] var29 = this.state;
         this.gamma(var29);
         int[] var30 = this.state;
         this.pi2(var30);
      }

      int[] var31 = this.state;
      int[] var32 = this.decryptKeys;
      this.theta(var31, var32);
      int[] var33 = this.state;
      int var34 = var33[0];
      int var35 = roundConstants[var21];
      int var36 = var34 ^ var35;
      var33[0] = var36;
      int var37 = this.state[0];
      this.intToBytesBig(var37, var3, var4);
      int var38 = this.state[1];
      int var39 = var4 + 4;
      this.intToBytesBig(var38, var3, var39);
      int var40 = this.state[2];
      int var41 = var4 + 8;
      this.intToBytesBig(var40, var3, var41);
      int var42 = this.state[3];
      int var43 = var4 + 12;
      this.intToBytesBig(var42, var3, var43);
      return 16;
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int[] var5 = this.state;
      int var6 = this.bytesToIntBig(var1, var2);
      var5[0] = var6;
      int[] var7 = this.state;
      int var8 = var2 + 4;
      int var9 = this.bytesToIntBig(var1, var8);
      var7[1] = var9;
      int[] var10 = this.state;
      int var11 = var2 + 8;
      int var12 = this.bytesToIntBig(var1, var11);
      var10[2] = var12;
      int[] var13 = this.state;
      int var14 = var2 + 12;
      int var15 = this.bytesToIntBig(var1, var14);
      var13[3] = var15;

      int var16;
      for(var16 = 0; var16 < 16; ++var16) {
         int[] var17 = this.state;
         int var18 = var17[0];
         int var19 = roundConstants[var16];
         int var20 = var18 ^ var19;
         var17[0] = var20;
         int[] var21 = this.state;
         int[] var22 = this.subKeys;
         this.theta(var21, var22);
         int[] var23 = this.state;
         this.pi1(var23);
         int[] var24 = this.state;
         this.gamma(var24);
         int[] var25 = this.state;
         this.pi2(var25);
      }

      int[] var26 = this.state;
      int var27 = var26[0];
      int var28 = roundConstants[var16];
      int var29 = var27 ^ var28;
      var26[0] = var29;
      int[] var30 = this.state;
      int[] var31 = this.subKeys;
      this.theta(var30, var31);
      int var32 = this.state[0];
      this.intToBytesBig(var32, var3, var4);
      int var33 = this.state[1];
      int var34 = var4 + 4;
      this.intToBytesBig(var33, var3, var34);
      int var35 = this.state[2];
      int var36 = var4 + 8;
      this.intToBytesBig(var35, var3, var36);
      int var37 = this.state[3];
      int var38 = var4 + 12;
      this.intToBytesBig(var37, var3, var38);
      return 16;
   }

   private void gamma(int[] var1) {
      int var2 = var1[1];
      int var3 = ~var1[3];
      int var4 = ~var1[2];
      int var5 = var3 & var4;
      int var6 = var2 ^ var5;
      var1[1] = var6;
      int var7 = var1[0];
      int var8 = var1[2];
      int var9 = var1[1];
      int var10 = var8 & var9;
      int var11 = var7 ^ var10;
      var1[0] = var11;
      int var12 = var1[3];
      int var13 = var1[0];
      var1[3] = var13;
      var1[0] = var12;
      int var14 = var1[2];
      int var15 = var1[0];
      int var16 = var1[1];
      int var17 = var15 ^ var16;
      int var18 = var1[3];
      int var19 = var17 ^ var18;
      int var20 = var14 ^ var19;
      var1[2] = var20;
      int var21 = var1[1];
      int var22 = ~var1[3];
      int var23 = ~var1[2];
      int var24 = var22 & var23;
      int var25 = var21 ^ var24;
      var1[1] = var25;
      int var26 = var1[0];
      int var27 = var1[2];
      int var28 = var1[1];
      int var29 = var27 & var28;
      int var30 = var26 ^ var29;
      var1[0] = var30;
   }

   private void intToBytesBig(int var1, byte[] var2, int var3) {
      int var4 = var3 + 1;
      byte var5 = (byte)(var1 >>> 24);
      var2[var3] = var5;
      int var6 = var4 + 1;
      byte var7 = (byte)(var1 >>> 16);
      var2[var4] = var7;
      int var8 = var6 + 1;
      byte var9 = (byte)(var1 >>> 8);
      var2[var6] = var9;
      byte var10 = (byte)var1;
      var2[var8] = var10;
   }

   private void pi1(int[] var1) {
      int var2 = var1[1];
      int var3 = this.rotl(var2, 1);
      var1[1] = var3;
      int var4 = var1[2];
      int var5 = this.rotl(var4, 5);
      var1[2] = var5;
      int var6 = var1[3];
      int var7 = this.rotl(var6, 2);
      var1[3] = var7;
   }

   private void pi2(int[] var1) {
      int var2 = var1[1];
      int var3 = this.rotl(var2, 31);
      var1[1] = var3;
      int var4 = var1[2];
      int var5 = this.rotl(var4, 27);
      var1[2] = var5;
      int var6 = var1[3];
      int var7 = this.rotl(var6, 30);
      var1[3] = var7;
   }

   private int rotl(int var1, int var2) {
      int var3 = var1 << var2;
      int var4 = 32 - var2;
      int var5 = var1 >>> var4;
      return var3 | var5;
   }

   private void setKey(byte[] var1) {
      int[] var2 = this.subKeys;
      int var3 = this.bytesToIntBig(var1, 0);
      var2[0] = var3;
      int[] var4 = this.subKeys;
      int var5 = this.bytesToIntBig(var1, 4);
      var4[1] = var5;
      int[] var6 = this.subKeys;
      int var7 = this.bytesToIntBig(var1, 8);
      var6[2] = var7;
      int[] var8 = this.subKeys;
      int var9 = this.bytesToIntBig(var1, 12);
      var8[3] = var9;
   }

   private void theta(int[] var1, int[] var2) {
      int var3 = var1[0];
      int var4 = var1[2];
      int var5 = var3 ^ var4;
      int var6 = this.rotl(var5, 8);
      int var7 = this.rotl(var5, 24);
      int var8 = var6 ^ var7;
      int var9 = var5 ^ var8;
      int var10 = var1[1] ^ var9;
      var1[1] = var10;
      int var11 = var1[3] ^ var9;
      var1[3] = var11;

      for(int var12 = 0; var12 < 4; ++var12) {
         int var13 = var1[var12];
         int var14 = var2[var12];
         int var15 = var13 ^ var14;
         var1[var12] = var15;
      }

      int var16 = var1[1];
      int var17 = var1[3];
      int var18 = var16 ^ var17;
      int var19 = this.rotl(var18, 8);
      int var20 = this.rotl(var18, 24);
      int var21 = var19 ^ var20;
      int var22 = var18 ^ var21;
      int var23 = var1[0] ^ var22;
      var1[0] = var23;
      int var24 = var1[2] ^ var22;
      var1[2] = var24;
   }

   public String getAlgorithmName() {
      return "Noekeon";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to Noekeon init - ");
         String var4 = var2.getClass().getName();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      } else {
         this._forEncryption = var1;
         this._initialised = (boolean)1;
         byte[] var6 = ((KeyParameter)var2).getKey();
         this.setKey(var6);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(!this._initialised) {
         StringBuilder var5 = new StringBuilder();
         String var6 = this.getAlgorithmName();
         String var7 = var5.append(var6).append(" not initialised").toString();
         throw new IllegalStateException(var7);
      } else {
         int var8 = var2 + 16;
         int var9 = var1.length;
         if(var8 > var9) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var10 = var4 + 16;
            int var11 = var3.length;
            if(var10 > var11) {
               throw new DataLengthException("output buffer too short");
            } else {
               int var12;
               if(this._forEncryption) {
                  var12 = this.encryptBlock(var1, var2, var3, var4);
               } else {
                  var12 = this.decryptBlock(var1, var2, var3, var4);
               }

               return var12;
            }
         }
      }
   }

   public void reset() {}
}
