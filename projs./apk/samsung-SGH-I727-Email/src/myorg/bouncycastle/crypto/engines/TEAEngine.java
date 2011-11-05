package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class TEAEngine implements BlockCipher {

   private static final int block_size = 8;
   private static final int d_sum = -957401312;
   private static final int delta = -1640531527;
   private static final int rounds = 32;
   private int _a;
   private int _b;
   private int _c;
   private int _d;
   private boolean _forEncryption;
   private boolean _initialised = 0;


   public TEAEngine() {}

   private int bytesToInt(byte[] var1, int var2) {
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
      int var5 = this.bytesToInt(var1, var2);
      int var6 = var2 + 4;
      int var7 = this.bytesToInt(var1, var6);
      int var8 = -957401312;

      for(int var9 = 0; var9 != 32; ++var9) {
         int var10 = var5 << 4;
         int var11 = this._c;
         int var12 = var10 + var11;
         int var13 = var5 + var8;
         int var14 = var12 ^ var13;
         int var15 = var5 >>> 5;
         int var16 = this._d;
         int var17 = var15 + var16;
         int var18 = var14 ^ var17;
         var7 -= var18;
         int var19 = var7 << 4;
         int var20 = this._a;
         int var21 = var19 + var20;
         int var22 = var7 + var8;
         int var23 = var21 ^ var22;
         int var24 = var7 >>> 5;
         int var25 = this._b;
         int var26 = var24 + var25;
         int var27 = var23 ^ var26;
         var5 -= var27;
         var8 += 1640531527;
      }

      this.unpackInt(var5, var3, var4);
      int var28 = var4 + 4;
      this.unpackInt(var7, var3, var28);
      return 8;
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.bytesToInt(var1, var2);
      int var6 = var2 + 4;
      int var7 = this.bytesToInt(var1, var6);
      int var8 = 0;

      for(int var9 = 0; var9 != 32; ++var9) {
         var8 -= 1640531527;
         int var10 = var7 << 4;
         int var11 = this._a;
         int var12 = var10 + var11;
         int var13 = var7 + var8;
         int var14 = var12 ^ var13;
         int var15 = var7 >>> 5;
         int var16 = this._b;
         int var17 = var15 + var16;
         int var18 = var14 ^ var17;
         var5 += var18;
         int var19 = var5 << 4;
         int var20 = this._c;
         int var21 = var19 + var20;
         int var22 = var5 + var8;
         int var23 = var21 ^ var22;
         int var24 = var5 >>> 5;
         int var25 = this._d;
         int var26 = var24 + var25;
         int var27 = var23 ^ var26;
         var7 += var27;
      }

      this.unpackInt(var5, var3, var4);
      int var28 = var4 + 4;
      this.unpackInt(var7, var3, var28);
      return 8;
   }

   private void setKey(byte[] var1) {
      int var2 = this.bytesToInt(var1, 0);
      this._a = var2;
      int var3 = this.bytesToInt(var1, 4);
      this._b = var3;
      int var4 = this.bytesToInt(var1, 8);
      this._c = var4;
      int var5 = this.bytesToInt(var1, 12);
      this._d = var5;
   }

   private void unpackInt(int var1, byte[] var2, int var3) {
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

   public String getAlgorithmName() {
      return "TEA";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to TEA init - ");
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
         int var8 = var2 + 8;
         int var9 = var1.length;
         if(var8 > var9) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var10 = var4 + 8;
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
