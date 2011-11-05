package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class XTEAEngine implements BlockCipher {

   private static final int block_size = 8;
   private static final int delta = -1640531527;
   private static final int rounds = 32;
   private int[] _S;
   private boolean _forEncryption;
   private boolean _initialised;
   private int[] _sum0;
   private int[] _sum1;


   public XTEAEngine() {
      int[] var1 = new int[4];
      this._S = var1;
      int[] var2 = new int[32];
      this._sum0 = var2;
      int[] var3 = new int[32];
      this._sum1 = var3;
      this._initialised = (boolean)0;
   }

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

      for(int var8 = 31; var8 >= 0; var8 += -1) {
         int var9 = var5 << 4;
         int var10 = var5 >>> 5;
         int var11 = (var9 ^ var10) + var5;
         int var12 = this._sum1[var8];
         int var13 = var11 ^ var12;
         var7 -= var13;
         int var14 = var7 << 4;
         int var15 = var7 >>> 5;
         int var16 = (var14 ^ var15) + var7;
         int var17 = this._sum0[var8];
         int var18 = var16 ^ var17;
         var5 -= var18;
      }

      this.unpackInt(var5, var3, var4);
      int var19 = var4 + 4;
      this.unpackInt(var7, var3, var19);
      return 8;
   }

   private int encryptBlock(byte[] var1, int var2, byte[] var3, int var4) {
      int var5 = this.bytesToInt(var1, var2);
      int var6 = var2 + 4;
      int var7 = this.bytesToInt(var1, var6);

      for(int var8 = 0; var8 < 32; ++var8) {
         int var9 = var7 << 4;
         int var10 = var7 >>> 5;
         int var11 = (var9 ^ var10) + var7;
         int var12 = this._sum0[var8];
         int var13 = var11 ^ var12;
         var5 += var13;
         int var14 = var5 << 4;
         int var15 = var5 >>> 5;
         int var16 = (var14 ^ var15) + var5;
         int var17 = this._sum1[var8];
         int var18 = var16 ^ var17;
         var7 += var18;
      }

      this.unpackInt(var5, var3, var4);
      int var19 = var4 + 4;
      this.unpackInt(var7, var3, var19);
      return 8;
   }

   private void setKey(byte[] var1) {
      int var2 = 0;

      for(int var3 = var2; var3 < 4; var2 += 4) {
         int[] var4 = this._S;
         int var5 = this.bytesToInt(var1, var2);
         var4[var3] = var5;
         ++var3;
      }

      int var6 = 0;

      for(int var7 = var6; var7 < 32; ++var7) {
         int[] var8 = this._sum0;
         int[] var9 = this._S;
         int var10 = var6 & 3;
         int var11 = var9[var10] + var6;
         var8[var7] = var11;
         var6 -= 1640531527;
         int[] var12 = this._sum1;
         int[] var13 = this._S;
         int var14 = var6 >>> 11 & 3;
         int var15 = var13[var14] + var6;
         var12[var7] = var15;
      }

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
      return "XTEA";
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
