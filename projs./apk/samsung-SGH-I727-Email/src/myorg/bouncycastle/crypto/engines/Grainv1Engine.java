package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.StreamCipher;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;

public class Grainv1Engine implements StreamCipher {

   private static final int STATE_SIZE = 5;
   private int index = 2;
   private boolean initialised = 0;
   private int[] lfsr;
   private int[] nfsr;
   private byte[] out;
   private int output;
   private byte[] workingIV;
   private byte[] workingKey;


   public Grainv1Engine() {}

   private byte getKeyStream() {
      if(this.index > 1) {
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
      int var1 = this.nfsr[0] >>> 1;
      int var2 = this.nfsr[1] << 15;
      int var3 = var1 | var2;
      int var4 = this.nfsr[0] >>> 2;
      int var5 = this.nfsr[1] << 14;
      int var6 = var4 | var5;
      int var7 = this.nfsr[0] >>> 4;
      int var8 = this.nfsr[1] << 12;
      int var9 = var7 | var8;
      int var10 = this.nfsr[0] >>> 10;
      int var11 = this.nfsr[1] << 6;
      int var12 = var10 | var11;
      int var13 = this.nfsr[1] >>> 15;
      int var14 = this.nfsr[2] << 1;
      int var15 = var13 | var14;
      int var16 = this.nfsr[2] >>> 11;
      int var17 = this.nfsr[3] << 5;
      int var18 = var16 | var17;
      int var19 = this.nfsr[3] >>> 8;
      int var20 = this.nfsr[4] << 8;
      int var21 = var19 | var20;
      int var22 = this.nfsr[3] >>> 15;
      int var23 = this.nfsr[4] << 1;
      int var24 = var22 | var23;
      int var25 = this.lfsr[0] >>> 3;
      int var26 = this.lfsr[1] << 13;
      int var27 = var25 | var26;
      int var28 = this.lfsr[1] >>> 9;
      int var29 = this.lfsr[2] << 7;
      int var30 = var28 | var29;
      int var31 = this.lfsr[2] >>> 14;
      int var32 = this.lfsr[3] << 2;
      int var33 = var31 | var32;
      int var34 = this.lfsr[4];
      int var35 = var30 ^ var24;
      int var36 = var27 & var34;
      int var37 = var35 ^ var36;
      int var38 = var33 & var34;
      int var39 = var37 ^ var38;
      int var40 = var34 & var24;
      int var41 = var39 ^ var40;
      int var42 = var27 & var30 & var33;
      int var43 = var41 ^ var42;
      int var44 = var27 & var33 & var34;
      int var45 = var43 ^ var44;
      int var46 = var27 & var33 & var24;
      int var47 = var45 ^ var46;
      int var48 = var30 & var33 & var24;
      int var49 = var47 ^ var48;
      int var50 = var33 & var34 & var24;
      return (var49 ^ var50 ^ var3 ^ var6 ^ var9 ^ var12 ^ var15 ^ var18 ^ var21) & '\uffff';
   }

   private int getOutputLFSR() {
      int var1 = this.lfsr[0];
      int var2 = this.lfsr[0] >>> 13;
      int var3 = this.lfsr[1] << 3;
      int var4 = var2 | var3;
      int var5 = this.lfsr[1] >>> 7;
      int var6 = this.lfsr[2] << 9;
      int var7 = var5 | var6;
      int var8 = this.lfsr[2] >>> 6;
      int var9 = this.lfsr[3] << 10;
      int var10 = var8 | var9;
      int var11 = this.lfsr[3] >>> 3;
      int var12 = this.lfsr[4] << 13;
      int var13 = var11 | var12;
      int var14 = this.lfsr[3] >>> 14;
      int var15 = this.lfsr[4] << 2;
      int var16 = var14 | var15;
      return (var1 ^ var4 ^ var7 ^ var10 ^ var13 ^ var16) & '\uffff';
   }

   private int getOutputNFSR() {
      int var1 = this.nfsr[0];
      int var2 = this.nfsr[0] >>> 9;
      int var3 = this.nfsr[1] << 7;
      int var4 = var2 | var3;
      int var5 = this.nfsr[0] >>> 14;
      int var6 = this.nfsr[1] << 2;
      int var7 = var5 | var6;
      int var8 = this.nfsr[0] >>> 15;
      int var9 = this.nfsr[1] << 1;
      int var10 = var8 | var9;
      int var11 = this.nfsr[1] >>> 5;
      int var12 = this.nfsr[2] << 11;
      int var13 = var11 | var12;
      int var14 = this.nfsr[1] >>> 12;
      int var15 = this.nfsr[2] << 4;
      int var16 = var14 | var15;
      int var17 = this.nfsr[2] >>> 1;
      int var18 = this.nfsr[3] << 15;
      int var19 = var17 | var18;
      int var20 = this.nfsr[2] >>> 5;
      int var21 = this.nfsr[3] << 11;
      int var22 = var20 | var21;
      int var23 = this.nfsr[2] >>> 13;
      int var24 = this.nfsr[3] << 3;
      int var25 = var23 | var24;
      int var26 = this.nfsr[3] >>> 4;
      int var27 = this.nfsr[4] << 12;
      int var28 = var26 | var27;
      int var29 = this.nfsr[3] >>> 12;
      int var30 = this.nfsr[4] << 4;
      int var31 = var29 | var30;
      int var32 = this.nfsr[3] >>> 14;
      int var33 = this.nfsr[4] << 2;
      int var34 = var32 | var33;
      int var35 = this.nfsr[3] >>> 15;
      int var36 = this.nfsr[4] << 1;
      int var37 = var35 | var36;
      int var38 = var34 ^ var31 ^ var28 ^ var25 ^ var22 ^ var19 ^ var16 ^ var13 ^ var7 ^ var4 ^ var1;
      int var39 = var37 & var31;
      int var40 = var38 ^ var39;
      int var41 = var22 & var19;
      int var42 = var40 ^ var41;
      int var43 = var10 & var4;
      int var44 = var42 ^ var43;
      int var45 = var31 & var28 & var25;
      int var46 = var44 ^ var45;
      int var47 = var19 & var16 & var13;
      int var48 = var46 ^ var47;
      int var49 = var37 & var25 & var16 & var4;
      int var50 = var48 ^ var49;
      int var51 = var31 & var28 & var22 & var19;
      int var52 = var50 ^ var51;
      int var53 = var37 & var31 & var13 & var10;
      int var54 = var52 ^ var53;
      int var55 = var37 & var31 & var28 & var25 & var22;
      int var56 = var54 ^ var55;
      int var57 = var19 & var16 & var13 & var10 & var4;
      int var58 = var56 ^ var57;
      int var59 = var28 & var25 & var22 & var19 & var16 & var13;
      return (var58 ^ var59) & '\uffff';
   }

   private void initGrain() {
      for(int var1 = 0; var1 < 10; ++var1) {
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
      int[] var6 = this.nfsr;
      int var7 = this.getOutputNFSR();
      int var8 = this.lfsr[0];
      int var9 = var7 ^ var8;
      int[] var10 = this.shift(var6, var9);
      this.nfsr = var10;
      int[] var11 = this.lfsr;
      int var12 = this.getOutputLFSR();
      int[] var13 = this.shift(var11, var12);
      this.lfsr = var13;
   }

   private void setKey(byte[] var1, byte[] var2) {
      var2[8] = -1;
      var2[9] = -1;
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
         int var8 = var3 + 1;
         int var9 = var7[var8] << 8;
         int var10 = this.workingKey[var3] & 255;
         int var11 = (var9 | var10) & '\uffff';
         var6[var4] = var11;
         int[] var12 = this.lfsr;
         byte[] var13 = this.workingIV;
         int var14 = var3 + 1;
         int var15 = var13[var14] << 8;
         int var16 = this.workingIV[var3] & 255;
         int var17 = (var15 | var16) & '\uffff';
         var12[var4] = var17;
         var3 += 2;
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
      int var6 = var1[4];
      var1[3] = var6;
      var1[4] = var2;
      return var1;
   }

   public String getAlgorithmName() {
      return "Grain v1";
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      if(!(var2 instanceof ParametersWithIV)) {
         throw new IllegalArgumentException("Grain v1 Init parameters must include an IV");
      } else {
         ParametersWithIV var3 = (ParametersWithIV)var2;
         byte[] var4 = var3.getIV();
         if(var4 != null && var4.length == 8) {
            if(!(var3.getParameters() instanceof KeyParameter)) {
               throw new IllegalArgumentException("Grain v1 Init parameters must include a key");
            } else {
               KeyParameter var5 = (KeyParameter)var3.getParameters();
               byte[] var6 = new byte[var5.getKey().length];
               this.workingIV = var6;
               byte[] var7 = new byte[var5.getKey().length];
               this.workingKey = var7;
               int[] var8 = new int[5];
               this.lfsr = var8;
               int[] var9 = new int[5];
               this.nfsr = var9;
               byte[] var10 = new byte[2];
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
            throw new IllegalArgumentException("Grain v1 requires exactly 8 bytes of IV");
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
      this.index = 2;
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
