package myorg.bouncycastle.crypto.engines;

import java.util.Hashtable;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithSBox;
import myorg.bouncycastle.util.Strings;

public class GOST28147Engine implements BlockCipher {

   protected static final int BLOCK_SIZE = 8;
   private static byte[] DSbox_A = new byte[]{(byte)10, (byte)4, (byte)5, (byte)6, (byte)8, (byte)1, (byte)3, (byte)7, (byte)13, (byte)12, (byte)14, (byte)0, (byte)9, (byte)2, (byte)11, (byte)15, (byte)5, (byte)15, (byte)4, (byte)0, (byte)2, (byte)13, (byte)11, (byte)9, (byte)1, (byte)7, (byte)6, (byte)3, (byte)12, (byte)14, (byte)10, (byte)8, (byte)7, (byte)15, (byte)12, (byte)14, (byte)9, (byte)4, (byte)1, (byte)0, (byte)3, (byte)11, (byte)5, (byte)2, (byte)6, (byte)10, (byte)8, (byte)13, (byte)4, (byte)10, (byte)7, (byte)12, (byte)0, (byte)15, (byte)2, (byte)8, (byte)14, (byte)1, (byte)6, (byte)5, (byte)13, (byte)11, (byte)9, (byte)3, (byte)7, (byte)6, (byte)4, (byte)11, (byte)9, (byte)12, (byte)2, (byte)10, (byte)1, (byte)8, (byte)0, (byte)14, (byte)15, (byte)13, (byte)3, (byte)5, (byte)7, (byte)6, (byte)2, (byte)4, (byte)13, (byte)9, (byte)15, (byte)0, (byte)10, (byte)1, (byte)5, (byte)11, (byte)8, (byte)14, (byte)12, (byte)3, (byte)13, (byte)14, (byte)4, (byte)1, (byte)7, (byte)0, (byte)5, (byte)10, (byte)3, (byte)12, (byte)8, (byte)15, (byte)6, (byte)2, (byte)9, (byte)11, (byte)1, (byte)3, (byte)10, (byte)9, (byte)5, (byte)11, (byte)4, (byte)15, (byte)8, (byte)6, (byte)7, (byte)14, (byte)13, (byte)0, (byte)2, (byte)12};
   private static byte[] DSbox_Test = new byte[]{(byte)4, (byte)10, (byte)9, (byte)2, (byte)13, (byte)8, (byte)0, (byte)14, (byte)6, (byte)11, (byte)1, (byte)12, (byte)7, (byte)15, (byte)5, (byte)3, (byte)14, (byte)11, (byte)4, (byte)12, (byte)6, (byte)13, (byte)15, (byte)10, (byte)2, (byte)3, (byte)8, (byte)1, (byte)0, (byte)7, (byte)5, (byte)9, (byte)5, (byte)8, (byte)1, (byte)13, (byte)10, (byte)3, (byte)4, (byte)2, (byte)14, (byte)15, (byte)12, (byte)7, (byte)6, (byte)0, (byte)9, (byte)11, (byte)7, (byte)13, (byte)10, (byte)1, (byte)0, (byte)8, (byte)9, (byte)15, (byte)14, (byte)4, (byte)6, (byte)12, (byte)11, (byte)2, (byte)5, (byte)3, (byte)6, (byte)12, (byte)7, (byte)1, (byte)5, (byte)15, (byte)13, (byte)8, (byte)4, (byte)10, (byte)9, (byte)14, (byte)0, (byte)3, (byte)11, (byte)2, (byte)4, (byte)11, (byte)10, (byte)0, (byte)7, (byte)2, (byte)1, (byte)13, (byte)3, (byte)6, (byte)8, (byte)5, (byte)9, (byte)12, (byte)15, (byte)14, (byte)13, (byte)11, (byte)4, (byte)1, (byte)3, (byte)15, (byte)5, (byte)9, (byte)0, (byte)10, (byte)14, (byte)7, (byte)6, (byte)8, (byte)2, (byte)12, (byte)1, (byte)15, (byte)13, (byte)0, (byte)5, (byte)7, (byte)10, (byte)4, (byte)9, (byte)2, (byte)3, (byte)14, (byte)6, (byte)11, (byte)8, (byte)12};
   private static byte[] ESbox_A = new byte[]{(byte)9, (byte)6, (byte)3, (byte)2, (byte)8, (byte)11, (byte)1, (byte)7, (byte)10, (byte)4, (byte)14, (byte)15, (byte)12, (byte)0, (byte)13, (byte)5, (byte)3, (byte)7, (byte)14, (byte)9, (byte)8, (byte)10, (byte)15, (byte)0, (byte)5, (byte)2, (byte)6, (byte)12, (byte)11, (byte)4, (byte)13, (byte)1, (byte)14, (byte)4, (byte)6, (byte)2, (byte)11, (byte)3, (byte)13, (byte)8, (byte)12, (byte)15, (byte)5, (byte)10, (byte)0, (byte)7, (byte)1, (byte)9, (byte)14, (byte)7, (byte)10, (byte)12, (byte)13, (byte)1, (byte)3, (byte)9, (byte)0, (byte)2, (byte)11, (byte)4, (byte)15, (byte)8, (byte)5, (byte)6, (byte)11, (byte)5, (byte)1, (byte)9, (byte)8, (byte)13, (byte)15, (byte)0, (byte)14, (byte)4, (byte)2, (byte)3, (byte)12, (byte)7, (byte)10, (byte)6, (byte)3, (byte)10, (byte)13, (byte)12, (byte)1, (byte)2, (byte)0, (byte)11, (byte)7, (byte)5, (byte)9, (byte)4, (byte)8, (byte)15, (byte)14, (byte)6, (byte)1, (byte)13, (byte)2, (byte)9, (byte)7, (byte)10, (byte)6, (byte)0, (byte)8, (byte)12, (byte)4, (byte)5, (byte)15, (byte)3, (byte)11, (byte)14, (byte)11, (byte)10, (byte)15, (byte)5, (byte)0, (byte)12, (byte)14, (byte)8, (byte)6, (byte)2, (byte)3, (byte)9, (byte)1, (byte)7, (byte)13, (byte)4};
   private static byte[] ESbox_B = new byte[]{(byte)8, (byte)4, (byte)11, (byte)1, (byte)3, (byte)5, (byte)0, (byte)9, (byte)2, (byte)14, (byte)10, (byte)12, (byte)13, (byte)6, (byte)7, (byte)15, (byte)0, (byte)1, (byte)2, (byte)10, (byte)4, (byte)13, (byte)5, (byte)12, (byte)9, (byte)7, (byte)3, (byte)15, (byte)11, (byte)8, (byte)6, (byte)14, (byte)14, (byte)12, (byte)0, (byte)10, (byte)9, (byte)2, (byte)13, (byte)11, (byte)7, (byte)5, (byte)8, (byte)15, (byte)3, (byte)6, (byte)1, (byte)4, (byte)7, (byte)5, (byte)0, (byte)13, (byte)11, (byte)6, (byte)1, (byte)2, (byte)3, (byte)10, (byte)12, (byte)15, (byte)4, (byte)14, (byte)9, (byte)8, (byte)2, (byte)7, (byte)12, (byte)15, (byte)9, (byte)5, (byte)10, (byte)11, (byte)1, (byte)4, (byte)0, (byte)13, (byte)6, (byte)8, (byte)14, (byte)3, (byte)8, (byte)3, (byte)2, (byte)6, (byte)4, (byte)13, (byte)14, (byte)11, (byte)12, (byte)1, (byte)7, (byte)15, (byte)10, (byte)0, (byte)9, (byte)5, (byte)5, (byte)2, (byte)10, (byte)11, (byte)9, (byte)1, (byte)12, (byte)3, (byte)7, (byte)4, (byte)13, (byte)0, (byte)6, (byte)15, (byte)8, (byte)14, (byte)0, (byte)4, (byte)11, (byte)14, (byte)8, (byte)3, (byte)7, (byte)1, (byte)10, (byte)2, (byte)9, (byte)6, (byte)15, (byte)13, (byte)5, (byte)12};
   private static byte[] ESbox_C = new byte[]{(byte)1, (byte)11, (byte)12, (byte)2, (byte)9, (byte)13, (byte)0, (byte)15, (byte)4, (byte)5, (byte)8, (byte)14, (byte)10, (byte)7, (byte)6, (byte)3, (byte)0, (byte)1, (byte)7, (byte)13, (byte)11, (byte)4, (byte)5, (byte)2, (byte)8, (byte)14, (byte)15, (byte)12, (byte)9, (byte)10, (byte)6, (byte)3, (byte)8, (byte)2, (byte)5, (byte)0, (byte)4, (byte)9, (byte)15, (byte)10, (byte)3, (byte)7, (byte)12, (byte)13, (byte)6, (byte)14, (byte)1, (byte)11, (byte)3, (byte)6, (byte)0, (byte)1, (byte)5, (byte)13, (byte)10, (byte)8, (byte)11, (byte)2, (byte)9, (byte)7, (byte)14, (byte)15, (byte)12, (byte)4, (byte)8, (byte)13, (byte)11, (byte)0, (byte)4, (byte)5, (byte)1, (byte)2, (byte)9, (byte)3, (byte)12, (byte)14, (byte)6, (byte)15, (byte)10, (byte)7, (byte)12, (byte)9, (byte)11, (byte)1, (byte)8, (byte)14, (byte)2, (byte)4, (byte)7, (byte)3, (byte)6, (byte)5, (byte)10, (byte)0, (byte)15, (byte)13, (byte)10, (byte)9, (byte)6, (byte)8, (byte)13, (byte)14, (byte)2, (byte)0, (byte)15, (byte)3, (byte)5, (byte)11, (byte)4, (byte)1, (byte)12, (byte)7, (byte)7, (byte)4, (byte)0, (byte)5, (byte)10, (byte)2, (byte)15, (byte)14, (byte)12, (byte)6, (byte)1, (byte)11, (byte)13, (byte)9, (byte)3, (byte)8};
   private static byte[] ESbox_D = new byte[]{(byte)15, (byte)12, (byte)2, (byte)10, (byte)6, (byte)4, (byte)5, (byte)0, (byte)7, (byte)9, (byte)14, (byte)13, (byte)1, (byte)11, (byte)8, (byte)3, (byte)11, (byte)6, (byte)3, (byte)4, (byte)12, (byte)15, (byte)14, (byte)2, (byte)7, (byte)13, (byte)8, (byte)0, (byte)5, (byte)10, (byte)9, (byte)1, (byte)1, (byte)12, (byte)11, (byte)0, (byte)15, (byte)14, (byte)6, (byte)5, (byte)10, (byte)13, (byte)4, (byte)8, (byte)9, (byte)3, (byte)7, (byte)2, (byte)1, (byte)5, (byte)14, (byte)12, (byte)10, (byte)7, (byte)0, (byte)13, (byte)6, (byte)2, (byte)11, (byte)4, (byte)9, (byte)3, (byte)15, (byte)8, (byte)0, (byte)12, (byte)8, (byte)9, (byte)13, (byte)2, (byte)10, (byte)11, (byte)7, (byte)3, (byte)6, (byte)5, (byte)4, (byte)14, (byte)15, (byte)1, (byte)8, (byte)0, (byte)15, (byte)3, (byte)2, (byte)5, (byte)14, (byte)11, (byte)1, (byte)10, (byte)4, (byte)7, (byte)12, (byte)9, (byte)13, (byte)6, (byte)3, (byte)0, (byte)6, (byte)15, (byte)1, (byte)14, (byte)9, (byte)2, (byte)13, (byte)8, (byte)12, (byte)4, (byte)11, (byte)10, (byte)5, (byte)7, (byte)1, (byte)10, (byte)6, (byte)8, (byte)15, (byte)11, (byte)0, (byte)4, (byte)12, (byte)3, (byte)5, (byte)9, (byte)7, (byte)13, (byte)2, (byte)14};
   private static byte[] ESbox_Test = new byte[]{(byte)4, (byte)2, (byte)15, (byte)5, (byte)9, (byte)1, (byte)0, (byte)8, (byte)14, (byte)3, (byte)11, (byte)12, (byte)13, (byte)7, (byte)10, (byte)6, (byte)12, (byte)9, (byte)15, (byte)14, (byte)8, (byte)1, (byte)3, (byte)10, (byte)2, (byte)7, (byte)4, (byte)13, (byte)6, (byte)0, (byte)11, (byte)5, (byte)13, (byte)8, (byte)14, (byte)12, (byte)7, (byte)3, (byte)9, (byte)10, (byte)1, (byte)5, (byte)2, (byte)4, (byte)6, (byte)15, (byte)0, (byte)11, (byte)14, (byte)9, (byte)11, (byte)2, (byte)5, (byte)15, (byte)7, (byte)1, (byte)0, (byte)13, (byte)12, (byte)6, (byte)10, (byte)4, (byte)3, (byte)8, (byte)3, (byte)14, (byte)5, (byte)9, (byte)6, (byte)8, (byte)0, (byte)13, (byte)10, (byte)11, (byte)7, (byte)12, (byte)2, (byte)1, (byte)15, (byte)4, (byte)8, (byte)15, (byte)6, (byte)11, (byte)1, (byte)9, (byte)12, (byte)5, (byte)13, (byte)3, (byte)7, (byte)10, (byte)0, (byte)14, (byte)2, (byte)4, (byte)9, (byte)11, (byte)12, (byte)0, (byte)3, (byte)6, (byte)7, (byte)5, (byte)4, (byte)8, (byte)14, (byte)15, (byte)1, (byte)10, (byte)2, (byte)13, (byte)12, (byte)6, (byte)5, (byte)2, (byte)11, (byte)0, (byte)9, (byte)13, (byte)3, (byte)14, (byte)7, (byte)10, (byte)15, (byte)4, (byte)1, (byte)8};
   private static Hashtable sBoxes = new Hashtable();
   private byte[] S;
   private boolean forEncryption;
   private int[] workingKey = null;


   static {
      Hashtable var0 = sBoxes;
      byte[] var1 = ESbox_Test;
      var0.put("E-TEST", var1);
      Hashtable var3 = sBoxes;
      byte[] var4 = ESbox_A;
      var3.put("E-A", var4);
      Hashtable var6 = sBoxes;
      byte[] var7 = ESbox_B;
      var6.put("E-B", var7);
      Hashtable var9 = sBoxes;
      byte[] var10 = ESbox_C;
      var9.put("E-C", var10);
      Hashtable var12 = sBoxes;
      byte[] var13 = ESbox_D;
      var12.put("E-D", var13);
      Hashtable var15 = sBoxes;
      byte[] var16 = DSbox_Test;
      var15.put("D-TEST", var16);
      Hashtable var18 = sBoxes;
      byte[] var19 = DSbox_A;
      var18.put("D-A", var19);
   }

   public GOST28147Engine() {
      byte[] var1 = new byte[]{(byte)4, (byte)10, (byte)9, (byte)2, (byte)13, (byte)8, (byte)0, (byte)14, (byte)6, (byte)11, (byte)1, (byte)12, (byte)7, (byte)15, (byte)5, (byte)3, (byte)14, (byte)11, (byte)4, (byte)12, (byte)6, (byte)13, (byte)15, (byte)10, (byte)2, (byte)3, (byte)8, (byte)1, (byte)0, (byte)7, (byte)5, (byte)9, (byte)5, (byte)8, (byte)1, (byte)13, (byte)10, (byte)3, (byte)4, (byte)2, (byte)14, (byte)15, (byte)12, (byte)7, (byte)6, (byte)0, (byte)9, (byte)11, (byte)7, (byte)13, (byte)10, (byte)1, (byte)0, (byte)8, (byte)9, (byte)15, (byte)14, (byte)4, (byte)6, (byte)12, (byte)11, (byte)2, (byte)5, (byte)3, (byte)6, (byte)12, (byte)7, (byte)1, (byte)5, (byte)15, (byte)13, (byte)8, (byte)4, (byte)10, (byte)9, (byte)14, (byte)0, (byte)3, (byte)11, (byte)2, (byte)4, (byte)11, (byte)10, (byte)0, (byte)7, (byte)2, (byte)1, (byte)13, (byte)3, (byte)6, (byte)8, (byte)5, (byte)9, (byte)12, (byte)15, (byte)14, (byte)13, (byte)11, (byte)4, (byte)1, (byte)3, (byte)15, (byte)5, (byte)9, (byte)0, (byte)10, (byte)14, (byte)7, (byte)6, (byte)8, (byte)2, (byte)12, (byte)1, (byte)15, (byte)13, (byte)0, (byte)5, (byte)7, (byte)10, (byte)4, (byte)9, (byte)2, (byte)3, (byte)14, (byte)6, (byte)11, (byte)8, (byte)12};
      this.S = var1;
   }

   private void GOST28147Func(int[] var1, byte[] var2, int var3, byte[] var4, int var5) {
      int var6 = this.bytesToint(var2, var3);
      int var7 = var3 + 4;
      int var8 = this.bytesToint(var2, var7);
      int var9;
      int var14;
      if(this.forEncryption) {
         for(var9 = 0; var9 < 3; ++var9) {
            for(int var10 = 0; var10 < 8; ++var10) {
               int var11 = var6;
               int var12 = var1[var10];
               int var13 = this.GOST28147_mainStep(var6, var12);
               var6 = var8 ^ var13;
               var8 = var11;
            }
         }

         for(var14 = 7; var14 > 0; var14 += -1) {
            int var15 = var6;
            int var16 = var1[var14];
            int var17 = this.GOST28147_mainStep(var6, var16);
            var6 = var8 ^ var17;
            var8 = var15;
         }
      } else {
         int var21;
         for(byte var29 = 0; var29 < 8; var21 = var29 + 1) {
            int var18 = var6;
            int var19 = var1[var29];
            int var20 = this.GOST28147_mainStep(var6, var19);
            var6 = var8 ^ var20;
            var8 = var18;
         }

         for(var9 = 0; var9 < 3; ++var9) {
            for(var14 = 7; var14 >= 0 && (var9 != 2 || var14 != 0); var14 += -1) {
               int var22 = var6;
               int var23 = var1[var14];
               int var24 = this.GOST28147_mainStep(var6, var23);
               var6 = var8 ^ var24;
               var8 = var22;
            }
         }
      }

      int var25 = var1[0];
      int var26 = this.GOST28147_mainStep(var6, var25);
      int var27 = var8 ^ var26;
      this.intTobytes(var6, var4, var5);
      int var28 = var5 + 4;
      this.intTobytes(var27, var4, var28);
   }

   private int GOST28147_mainStep(int var1, int var2) {
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

   private int[] generateWorkingKey(boolean var1, byte[] var2) {
      this.forEncryption = var1;
      if(var2.length != 32) {
         throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
      } else {
         int[] var3 = new int[8];

         for(int var4 = 0; var4 != 8; ++var4) {
            int var5 = var4 * 4;
            int var6 = this.bytesToint(var2, var5);
            var3[var4] = var6;
         }

         return var3;
      }
   }

   public static byte[] getSBox(String var0) {
      Hashtable var1 = sBoxes;
      String var2 = Strings.toUpperCase(var0);
      byte[] var3 = (byte[])((byte[])var1.get(var2));
      if(var3 != null) {
         byte[] var4 = new byte[var3.length];
         int var5 = var4.length;
         System.arraycopy(var3, 0, var4, 0, var5);
         return var4;
      } else {
         throw new IllegalArgumentException("Unknown S-Box - possible types: \"E-Test\", \"E-A\", \"E-B\", \"E-C\", \"E-D\", \"D-Test\", \"D-A\".");
      }
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

   public String getAlgorithmName() {
      return "GOST28147";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof ParametersWithSBox) {
         ParametersWithSBox var3 = (ParametersWithSBox)var2;
         byte[] var4 = var3.getSBox();
         byte[] var5 = this.S;
         int var6 = var3.getSBox().length;
         System.arraycopy(var4, 0, var5, 0, var6);
         if(var3.getParameters() != null) {
            byte[] var7 = ((KeyParameter)var3.getParameters()).getKey();
            int[] var8 = this.generateWorkingKey(var1, var7);
            this.workingKey = var8;
         }
      } else if(var2 instanceof KeyParameter) {
         byte[] var9 = ((KeyParameter)var2).getKey();
         int[] var10 = this.generateWorkingKey(var1, var9);
         this.workingKey = var10;
      } else {
         StringBuilder var11 = (new StringBuilder()).append("invalid parameter passed to GOST28147 init - ");
         String var12 = var2.getClass().getName();
         String var13 = var11.append(var12).toString();
         throw new IllegalArgumentException(var13);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey == null) {
         throw new IllegalStateException("GOST28147 engine not initialised");
      } else {
         int var5 = var2 + 8;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 8;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               int[] var9 = this.workingKey;
               this.GOST28147Func(var9, var1, var2, var3, var4);
               return 8;
            }
         }
      }
   }

   public void reset() {}
}
