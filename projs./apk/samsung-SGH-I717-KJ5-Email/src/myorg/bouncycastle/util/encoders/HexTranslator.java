package myorg.bouncycastle.util.encoders;

import myorg.bouncycastle.util.encoders.Translator;

public class HexTranslator implements Translator {

   private static final byte[] hexTable = new byte[]{(byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102};


   public HexTranslator() {}

   public int decode(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      int var6 = var3 / 2;

      for(int var7 = 0; var7 < var6; ++var7) {
         int var8 = var7 * 2 + var2;
         byte var9 = var1[var8];
         int var10 = var7 * 2 + var2 + 1;
         byte var11 = var1[var10];
         if(var9 < 97) {
            byte var12 = (byte)(var9 - 48 << 4);
            var4[var5] = var12;
         } else {
            byte var16 = (byte)(var9 - 97 + 10 << 4);
            var4[var5] = var16;
         }

         if(var11 < 97) {
            byte var13 = var4[var5];
            byte var14 = (byte)(var11 - 48);
            byte var15 = (byte)(var13 + var14);
            var4[var5] = var15;
         } else {
            byte var17 = var4[var5];
            byte var18 = (byte)(var11 - 97 + 10);
            byte var19 = (byte)(var17 + var18);
            var4[var5] = var19;
         }

         ++var5;
      }

      return var6;
   }

   public int encode(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      int var6 = 0;

      for(int var7 = 0; var6 < var3; var7 += 2) {
         int var8 = var5 + var7;
         byte[] var9 = hexTable;
         int var10 = var1[var2] >> 4 & 15;
         byte var11 = var9[var10];
         var4[var8] = var11;
         int var12 = var5 + var7 + 1;
         byte[] var13 = hexTable;
         int var14 = var1[var2] & 15;
         byte var15 = var13[var14];
         var4[var12] = var15;
         ++var2;
         ++var6;
      }

      return var3 * 2;
   }

   public int getDecodedBlockSize() {
      return 1;
   }

   public int getEncodedBlockSize() {
      return 2;
   }
}
