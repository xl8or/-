package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithSBox;

public class GOST28147Mac implements Mac {

   private byte[] S;
   private int blockSize = 8;
   private byte[] buf;
   private int bufOff;
   private boolean firstStep = 1;
   private byte[] mac;
   private int macSize = 4;
   private int[] workingKey = null;


   public GOST28147Mac() {
      byte[] var1 = new byte[]{(byte)9, (byte)6, (byte)3, (byte)2, (byte)8, (byte)11, (byte)1, (byte)7, (byte)10, (byte)4, (byte)14, (byte)15, (byte)12, (byte)0, (byte)13, (byte)5, (byte)3, (byte)7, (byte)14, (byte)9, (byte)8, (byte)10, (byte)15, (byte)0, (byte)5, (byte)2, (byte)6, (byte)12, (byte)11, (byte)4, (byte)13, (byte)1, (byte)14, (byte)4, (byte)6, (byte)2, (byte)11, (byte)3, (byte)13, (byte)8, (byte)12, (byte)15, (byte)5, (byte)10, (byte)0, (byte)7, (byte)1, (byte)9, (byte)14, (byte)7, (byte)10, (byte)12, (byte)13, (byte)1, (byte)3, (byte)9, (byte)0, (byte)2, (byte)11, (byte)4, (byte)15, (byte)8, (byte)5, (byte)6, (byte)11, (byte)5, (byte)1, (byte)9, (byte)8, (byte)13, (byte)15, (byte)0, (byte)14, (byte)4, (byte)2, (byte)3, (byte)12, (byte)7, (byte)10, (byte)6, (byte)3, (byte)10, (byte)13, (byte)12, (byte)1, (byte)2, (byte)0, (byte)11, (byte)7, (byte)5, (byte)9, (byte)4, (byte)8, (byte)15, (byte)14, (byte)6, (byte)1, (byte)13, (byte)2, (byte)9, (byte)7, (byte)10, (byte)6, (byte)0, (byte)8, (byte)12, (byte)4, (byte)5, (byte)15, (byte)3, (byte)11, (byte)14, (byte)11, (byte)10, (byte)15, (byte)5, (byte)0, (byte)12, (byte)14, (byte)8, (byte)6, (byte)2, (byte)3, (byte)9, (byte)1, (byte)7, (byte)13, (byte)4};
      this.S = var1;
      byte[] var2 = new byte[this.blockSize];
      this.mac = var2;
      byte[] var3 = new byte[this.blockSize];
      this.buf = var3;
      this.bufOff = 0;
   }

   private byte[] CM5func(byte[] var1, int var2, byte[] var3) {
      byte[] var4 = new byte[var1.length - var2];
      int var5 = var3.length;
      System.arraycopy(var1, var2, var4, 0, var5);
      int var6 = 0;

      while(true) {
         int var7 = var3.length;
         if(var6 == var7) {
            return var4;
         }

         byte var8 = var4[var6];
         byte var9 = var3[var6];
         byte var10 = (byte)(var8 ^ var9);
         var4[var6] = var10;
         ++var6;
      }
   }

   private int bytesToint(byte[] var1, int var2) {
      int var3 = var2 + 3;
      int var4 = var1[var3] << 24 & -16777216;
      int var5 = var2 + 2;
      int var6 = var1[var5] << 16 & 16711680;
      int var7 = var4 + var6;
      int var8 = var2 + 1;
      int var9 = var1[var8] << 8 & '\uff00';
      int var10 = var7 + var9;
      int var11 = var1[var2] & 255;
      return var10 + var11;
   }

   private int[] generateWorkingKey(byte[] var1) {
      if(var1.length != 32) {
         throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
      } else {
         int[] var2 = new int[8];

         for(int var3 = 0; var3 != 8; ++var3) {
            int var4 = var3 * 4;
            int var5 = this.bytesToint(var1, var4);
            var2[var3] = var5;
         }

         return var2;
      }
   }

   private void gost28147MacFunc(int[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      int var6 = this.bytesToint(var2, var3);
      int var7 = var3 + 4;
      int var8 = this.bytesToint(var2, var7);

      for(int var9 = 0; var9 < 2; ++var9) {
         for(int var10 = 0; var10 < 8; ++var10) {
            int var11 = var6;
            int var12 = var1[var10];
            int var13 = this.gost28147_mainStep(var6, var12);
            var6 = var8 ^ var13;
            var8 = var11;
         }
      }

      this.intTobytes(var6, var4, var5);
      int var14 = var5 + 4;
      this.intTobytes(var8, var4, var14);
   }

   private int gost28147_mainStep(int var1, int var2) {
      int var3 = var2 + var1;
      byte[] var4 = this.S;
      int var5 = (var3 >> 0 & 15) + 0;
      int var6 = var4[var5] << 0;
      byte[] var7 = this.S;
      int var8 = (var3 >> 4 & 15) + 16;
      int var9 = var7[var8] << 4;
      int var10 = var6 + var9;
      byte[] var11 = this.S;
      int var12 = (var3 >> 8 & 15) + 32;
      int var13 = var11[var12] << 8;
      int var14 = var10 + var13;
      byte[] var15 = this.S;
      int var16 = (var3 >> 12 & 15) + 48;
      int var17 = var15[var16] << 12;
      int var18 = var14 + var17;
      byte[] var19 = this.S;
      int var20 = (var3 >> 16 & 15) + 64;
      int var21 = var19[var20] << 16;
      int var22 = var18 + var21;
      byte[] var23 = this.S;
      int var24 = (var3 >> 20 & 15) + 80;
      int var25 = var23[var24] << 20;
      int var26 = var22 + var25;
      byte[] var27 = this.S;
      int var28 = (var3 >> 24 & 15) + 96;
      int var29 = var27[var28] << 24;
      int var30 = var26 + var29;
      byte[] var31 = this.S;
      int var32 = (var3 >> 28 & 15) + 112;
      int var33 = var31[var32] << 28;
      int var34 = var30 + var33;
      int var35 = var34 << 11;
      int var36 = var34 >>> 21;
      return var35 | var36;
   }

   private void intTobytes(int var1, byte[] var2, int var3) {
      int var4 = var3 + 3;
      byte var5 = (byte)(var1 >>> 24);
      var2[var4] = var5;
      int var6 = var3 + 2;
      byte var7 = (byte)(var1 >>> 16);
      var2[var6] = var7;
      int var8 = var3 + 1;
      byte var9 = (byte)(var1 >>> 8);
      var2[var8] = var9;
      byte var10 = (byte)var1;
      var2[var3] = var10;
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException {
      while(true) {
         int var3 = this.bufOff;
         int var4 = this.blockSize;
         if(var3 >= var4) {
            byte[] var8 = new byte[this.buf.length];
            byte[] var9 = this.buf;
            int var10 = this.mac.length;
            System.arraycopy(var9, 0, var8, 0, var10);
            if(this.firstStep) {
               this.firstStep = (boolean)0;
            } else {
               byte[] var20 = this.buf;
               byte[] var21 = this.mac;
               var8 = this.CM5func(var20, 0, var21);
            }

            int[] var11 = this.workingKey;
            byte[] var12 = this.mac;
            byte var14 = 0;
            this.gost28147MacFunc(var11, var8, 0, var12, var14);
            byte[] var15 = this.mac;
            int var16 = this.mac.length / 2;
            int var17 = this.macSize;
            int var18 = var16 - var17;
            int var19 = this.macSize;
            System.arraycopy(var15, var18, var1, var2, var19);
            this.reset();
            return this.macSize;
         }

         byte[] var5 = this.buf;
         int var6 = this.bufOff;
         var5[var6] = 0;
         int var7 = this.bufOff + 1;
         this.bufOff = var7;
      }
   }

   public String getAlgorithmName() {
      return "GOST28147Mac";
   }

   public int getMacSize() {
      return this.macSize;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      this.reset();
      byte[] var2 = new byte[this.blockSize];
      this.buf = var2;
      if(var1 instanceof ParametersWithSBox) {
         ParametersWithSBox var3 = (ParametersWithSBox)var1;
         byte[] var4 = var3.getSBox();
         byte[] var5 = this.S;
         int var6 = var3.getSBox().length;
         System.arraycopy(var4, 0, var5, 0, var6);
         if(var3.getParameters() != null) {
            byte[] var7 = ((KeyParameter)var3.getParameters()).getKey();
            int[] var8 = this.generateWorkingKey(var7);
            this.workingKey = var8;
         }
      } else if(var1 instanceof KeyParameter) {
         byte[] var9 = ((KeyParameter)var1).getKey();
         int[] var10 = this.generateWorkingKey(var9);
         this.workingKey = var10;
      } else {
         StringBuilder var11 = (new StringBuilder()).append("invalid parameter passed to GOST28147 init - ");
         String var12 = var1.getClass().getName();
         String var13 = var11.append(var12).toString();
         throw new IllegalArgumentException(var13);
      }
   }

   public void reset() {
      int var1 = 0;

      while(true) {
         int var2 = this.buf.length;
         if(var1 >= var2) {
            this.bufOff = 0;
            this.firstStep = (boolean)1;
            return;
         }

         this.buf[var1] = 0;
         ++var1;
      }
   }

   public void update(byte var1) throws IllegalStateException {
      int var2 = this.bufOff;
      int var3 = this.buf.length;
      if(var2 == var3) {
         byte[] var4 = new byte[this.buf.length];
         byte[] var5 = this.buf;
         int var6 = this.mac.length;
         System.arraycopy(var5, 0, var4, 0, var6);
         if(this.firstStep) {
            this.firstStep = (boolean)0;
         } else {
            byte[] var14 = this.buf;
            byte[] var15 = this.mac;
            var4 = this.CM5func(var14, 0, var15);
         }

         int[] var7 = this.workingKey;
         byte[] var8 = this.mac;
         byte var10 = 0;
         this.gost28147MacFunc(var7, var4, 0, var8, var10);
         this.bufOff = 0;
      }

      byte[] var11 = this.buf;
      int var12 = this.bufOff;
      int var13 = var12 + 1;
      this.bufOff = var13;
      var11[var12] = var1;
   }

   public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException {
      if(var3 < 0) {
         throw new IllegalArgumentException("Can\'t have a negative input length!");
      } else {
         int var4 = this.blockSize;
         int var5 = this.bufOff;
         int var6 = var4 - var5;
         if(var3 > var6) {
            byte[] var7 = this.buf;
            int var8 = this.bufOff;
            System.arraycopy(var1, var2, var7, var8, var6);
            byte[] var9 = new byte[this.buf.length];
            byte[] var10 = this.buf;
            int var11 = this.mac.length;
            System.arraycopy(var10, 0, var9, 0, var11);
            if(this.firstStep) {
               this.firstStep = (boolean)0;
            } else {
               byte[] var25 = this.buf;
               byte[] var26 = this.mac;
               var9 = this.CM5func(var25, 0, var26);
            }

            int[] var12 = this.workingKey;
            byte[] var13 = this.mac;
            byte var15 = 0;
            this.gost28147MacFunc(var12, var9, 0, var13, var15);
            this.bufOff = 0;
            var3 -= var6;
            var2 += var6;

            while(true) {
               int var16 = this.blockSize;
               if(var3 <= var16) {
                  break;
               }

               byte[] var17 = this.mac;
               byte[] var18 = this.CM5func(var1, var2, var17);
               int[] var19 = this.workingKey;
               byte[] var20 = this.mac;
               byte var22 = 0;
               this.gost28147MacFunc(var19, var18, 0, var20, var22);
               int var23 = this.blockSize;
               var3 -= var23;
               int var24 = this.blockSize;
               var2 += var24;
            }
         }

         byte[] var27 = this.buf;
         int var28 = this.bufOff;
         System.arraycopy(var1, var2, var27, var28, var3);
         int var29 = this.bufOff + var3;
         this.bufOff = var29;
      }
   }
}
