package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class Grain128Engine implements StreamCipher {

   private static final int STATE_SIZE = 4;
   private int index = 4;
   private boolean initialised = 0;
   private int[] lfsr;
   private int[] nfsr;
   private byte[] out;
   private int output;
   private byte[] workingIV;
   private byte[] workingKey;


   public Grain128Engine() {}

   private byte getKeyStream() {
      if(this.index > 3) {
         this.oneRound();
         this.index = 0;
      }

      byte[] var1 = this.out;
      int var2 = this.index;
      int var3 = var2 + 1;
      this.index = var3;
      return var1[var2];
   }

   private int getOutput() {
      int var1 = this.nfsr[0] >>> 2;
      int var2 = this.nfsr[1] << 30;
      int var3 = var1 | var2;
      int var4 = this.nfsr[0] >>> 12;
      int var5 = this.nfsr[1] << 20;
      int var6 = var4 | var5;
      int var7 = this.nfsr[0] >>> 15;
      int var8 = this.nfsr[1] << 17;
      int var9 = var7 | var8;
      int var10 = this.nfsr[1] >>> 4;
      int var11 = this.nfsr[2] << 28;
      int var12 = var10 | var11;
      int var13 = this.nfsr[1] >>> 13;
      int var14 = this.nfsr[2] << 19;
      int var15 = var13 | var14;
      int var16 = this.nfsr[2];
      int var17 = this.nfsr[2] >>> 9;
      int var18 = this.nfsr[3] << 23;
      int var19 = var17 | var18;
      int var20 = this.nfsr[2] >>> 25;
      int var21 = this.nfsr[3] << 7;
      int var22 = var20 | var21;
      int var23 = this.nfsr[2] >>> 31;
      int var24 = this.nfsr[3] << 1;
      int var25 = var23 | var24;
      int var26 = this.lfsr[0] >>> 8;
      int var27 = this.lfsr[1] << 24;
      int var28 = var26 | var27;
      int var29 = this.lfsr[0] >>> 13;
      int var30 = this.lfsr[1] << 19;
      int var31 = var29 | var30;
      int var32 = this.lfsr[0] >>> 20;
      int var33 = this.lfsr[1] << 12;
      int var34 = var32 | var33;
      int var35 = this.lfsr[1] >>> 10;
      int var36 = this.lfsr[2] << 22;
      int var37 = var35 | var36;
      int var38 = this.lfsr[1] >>> 28;
      int var39 = this.lfsr[2] << 4;
      int var40 = var38 | var39;
      int var41 = this.lfsr[2] >>> 15;
      int var42 = this.lfsr[3] << 17;
      int var43 = var41 | var42;
      int var44 = this.lfsr[2] >>> 29;
      int var45 = this.lfsr[3] << 3;
      int var46 = var44 | var45;
      int var47 = this.lfsr[2] >>> 31;
      int var48 = this.lfsr[3] << 1;
      int var49 = var47 | var48;
      int var50 = var6 & var28;
      int var51 = var31 & var34;
      int var52 = var50 ^ var51;
      int var53 = var25 & var37;
      int var54 = var52 ^ var53;
      int var55 = var40 & var43;
      int var56 = var54 ^ var55;
      int var57 = var6 & var25 & var49;
      return var56 ^ var57 ^ var46 ^ var3 ^ var9 ^ var12 ^ var15 ^ var16 ^ var19 ^ var22;
   }

   private int getOutputLFSR() {
      int var1 = this.lfsr[0];
      int var2 = this.lfsr[0] >>> 7;
      int var3 = this.lfsr[1] << 25;
      int var4 = var2 | var3;
      int var5 = this.lfsr[1] >>> 6;
      int var6 = this.lfsr[2] << 26;
      int var7 = var5 | var6;
      int var8 = this.lfsr[2] >>> 6;
      int var9 = this.lfsr[3] << 26;
      int var10 = var8 | var9;
      int var11 = this.lfsr[2] >>> 17;
      int var12 = this.lfsr[3] << 15;
      int var13 = var11 | var12;
      int var14 = this.lfsr[3];
      return var1 ^ var4 ^ var7 ^ var10 ^ var13 ^ var14;
   }

   private int getOutputNFSR() {
      int var1 = this.nfsr[0];
      int var2 = this.nfsr[0] >>> 3;
      int var3 = this.nfsr[1] << 29;
      int var4 = var2 | var3;
      int var5 = this.nfsr[0] >>> 11;
      int var6 = this.nfsr[1] << 21;
      int var7 = var5 | var6;
      int var8 = this.nfsr[0] >>> 13;
      int var9 = this.nfsr[1] << 19;
      int var10 = var8 | var9;
      int var11 = this.nfsr[0] >>> 17;
      int var12 = this.nfsr[1] << 15;
      int var13 = var11 | var12;
      int var14 = this.nfsr[0] >>> 18;
      int var15 = this.nfsr[1] << 14;
      int var16 = var14 | var15;
      int var17 = this.nfsr[0] >>> 26;
      int var18 = this.nfsr[1] << 6;
      int var19 = var17 | var18;
      int var20 = this.nfsr[0] >>> 27;
      int var21 = this.nfsr[1] << 5;
      int var22 = var20 | var21;
      int var23 = this.nfsr[1] >>> 8;
      int var24 = this.nfsr[2] << 24;
      int var25 = var23 | var24;
      int var26 = this.nfsr[1] >>> 16;
      int var27 = this.nfsr[2] << 16;
      int var28 = var26 | var27;
      int var29 = this.nfsr[1] >>> 24;
      int var30 = this.nfsr[2] << 8;
      int var31 = var29 | var30;
      int var32 = this.nfsr[1] >>> 27;
      int var33 = this.nfsr[2] << 5;
      int var34 = var32 | var33;
      int var35 = this.nfsr[1] >>> 29;
      int var36 = this.nfsr[2] << 3;
      int var37 = var35 | var36;
      int var38 = this.nfsr[2] >>> 1;
      int var39 = this.nfsr[3] << 31;
      int var40 = var38 | var39;
      int var41 = this.nfsr[2] >>> 3;
      int var42 = this.nfsr[3] << 29;
      int var43 = var41 | var42;
      int var44 = this.nfsr[2] >>> 4;
      int var45 = this.nfsr[3] << 28;
      int var46 = var44 | var45;
      int var47 = this.nfsr[2] >>> 20;
      int var48 = this.nfsr[3] << 12;
      int var49 = var47 | var48;
      int var50 = this.nfsr[2] >>> 27;
      int var51 = this.nfsr[3] << 5;
      int var52 = var50 | var51;
      int var53 = this.nfsr[3];
      int var54 = var1 ^ var19 ^ var31 ^ var52 ^ var53;
      int var55 = var4 & var43;
      int var56 = var54 ^ var55;
      int var57 = var7 & var10;
      int var58 = var56 ^ var57;
      int var59 = var13 & var16;
      int var60 = var58 ^ var59;
      int var61 = var22 & var34;
      int var62 = var60 ^ var61;
      int var63 = var25 & var28;
      int var64 = var62 ^ var63;
      int var65 = var37 & var40;
      int var66 = var64 ^ var65;
      int var67 = var46 & var49;
      return var66 ^ var67;
   }

   private void initGrain() {
      for(int var1 = 0; var1 < 8; ++var1) {
         int var2 = this.getOutput();
         this.output = var2;
         int[] var3 = this.nfsr;
         int var4 = this.getOutputNFSR();
         int var5 = this.lfsr[0];
         int var6 = var4 ^ var5;
         int var7 = this.output;
         int var8 = var6 ^ var7;
         int[] var9 = this.shift(var3, var8);
         this.nfsr = var9;
         int[] var10 = this.lfsr;
         int var11 = this.getOutputLFSR();
         int var12 = this.output;
         int var13 = var11 ^ var12;
         int[] var14 = this.shift(var10, var13);
         this.lfsr = var14;
      }

      this.initialised = (boolean)1;
   }

   private void oneRound() {
      int var1 = this.getOutput();
      this.output = var1;
      byte[] var2 = this.out;
      byte var3 = (byte)this.output;
      var2[0] = var3;
      byte[] var4 = this.out;
      byte var5 = (byte)(this.output >> 8);
      var4[1] = var5;
      byte[] var6 = this.out;
      byte var7 = (byte)(this.output >> 16);
      var6[2] = var7;
      byte[] var8 = this.out;
      byte var9 = (byte)(this.output >> 24);
      var8[3] = var9;
      int[] var10 = this.nfsr;
      int var11 = this.getOutputNFSR();
      int var12 = this.lfsr[0];
      int var13 = var11 ^ var12;
      int[] var14 = this.shift(var10, var13);
      this.nfsr = var14;
      int[] var15 = this.lfsr;
      int var16 = this.getOutputLFSR();
      int[] var17 = this.shift(var15, var16);
      this.lfsr = var17;
   }

   private void setKey(byte[] var1, byte[] var2) {
      var2[12] = -1;
      var2[13] = -1;
      var2[14] = -1;
      var2[15] = -1;
      this.workingKey = var1;
      this.workingIV = var2;
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = this.nfsr.length;
         if(var4 >= var5) {
            return;
         }

         int[] var6 = this.nfsr;
         byte[] var7 = this.workingKey;
         int var8 = var3 + 3;
         int var9 = var7[var8] << 24;
         byte[] var10 = this.workingKey;
         int var11 = var3 + 2;
         int var12 = var10[var11] << 16 & 16711680;
         int var13 = var9 | var12;
         byte[] var14 = this.workingKey;
         int var15 = var3 + 1;
         int var16 = var14[var15] << 8 & '\uff00';
         int var17 = var13 | var16;
         int var18 = this.workingKey[var3] & 255;
         int var19 = var17 | var18;
         var6[var4] = var19;
         int[] var20 = this.lfsr;
         byte[] var21 = this.workingIV;
         int var22 = var3 + 3;
         int var23 = var21[var22] << 24;
         byte[] var24 = this.workingIV;
         int var25 = var3 + 2;
         int var26 = var24[var25] << 16 & 16711680;
         int var27 = var23 | var26;
         byte[] var28 = this.workingIV;
         int var29 = var3 + 1;
         int var30 = var28[var29] << 8 & '\uff00';
         int var31 = var27 | var30;
         int var32 = this.workingIV[var3] & 255;
         int var33 = var31 | var32;
         var20[var4] = var33;
         var3 += 4;
         ++var4;
      }
   }

   private int[] shift(int[] var1, int var2) {
      int var3 = var1[1];
      var1[0] = var3;
      int var4 = var1[2];
      var1[1] = var4;
      int var5 = var1[3];
      var1[2] = var5;
      var1[3] = var2;
      return var1;
   }

   public String getAlgorithmName() {
      return "Grain-128";
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      if(!(var2 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("Grain-128 Init parameters must include an IV");
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         if(var4 != null && var4.length == 12) {
            if(!(var3.getParameters() instanceof KeyParameter)) {
               throw new IllegalArgumentException("Grain-128 Init parameters must include a key");
            } else {
               KeyParameter var5 = (KeyParameter)var3.getParameters();
               byte[] var6 = new byte[var5.getKey().length];
               this.workingIV = var6;
               byte[] var7 = new byte[var5.getKey().length];
               this.workingKey = var7;
               int[] var8 = new int[4];
               this.lfsr = var8;
               int[] var9 = new int[4];
               this.nfsr = var9;
               byte[] var10 = new byte[4];
               this.out = var10;
               byte[] var11 = this.workingIV;
               int var12 = var4.length;
               System.arraycopy(var4, 0, var11, 0, var12);
               byte[] var13 = var5.getKey();
               byte[] var14 = this.workingKey;
               int var15 = var5.getKey().length;
               System.arraycopy(var13, 0, var14, 0, var15);
               byte[] var16 = this.workingKey;
               byte[] var17 = this.workingIV;
               this.setKey(var16, var17);
               this.initGrain();
            }
         } else {
            throw new IllegalArgumentException("Grain-128  requires exactly 12 bytes of IV");
         }
      }
   }

   public void processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
      if(!this.initialised) {
         StringBuilder var6 = new StringBuilder();
         String var7 = this.getAlgorithmName();
         String var8 = var6.append(var7).append(" not initialised").toString();
         throw new IllegalStateException(var8);
      } else {
         int var9 = var2 + var3;
         int var10 = var1.length;
         if(var9 > var10) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var11 = var5 + var3;
            int var12 = var4.length;
            if(var11 > var12) {
               throw new DataLengthException("output buffer too short");
            } else {
               for(int var13 = 0; var13 < var3; ++var13) {
                  int var14 = var5 + var13;
                  int var15 = var2 + var13;
                  byte var16 = var1[var15];
                  byte var17 = this.getKeyStream();
                  byte var18 = (byte)(var16 ^ var17);
                  var4[var14] = var18;
               }

            }
         }
      }
   }

   public void reset() {
      this.index = 4;
      byte[] var1 = this.workingKey;
      byte[] var2 = this.workingIV;
      this.setKey(var1, var2);
      this.initGrain();
   }

   public byte returnByte(byte var1) {
      if(!this.initialised) {
         StringBuilder var2 = new StringBuilder();
         String var3 = this.getAlgorithmName();
         String var4 = var2.append(var3).append(" not initialised").toString();
         throw new IllegalStateException(var4);
      } else {
         return (byte)(this.getKeyStream() ^ var1);
      }
   }
}
