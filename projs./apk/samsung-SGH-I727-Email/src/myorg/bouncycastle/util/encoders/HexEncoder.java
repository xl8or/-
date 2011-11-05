package myorg.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.util.encoders.Encoder;

public class HexEncoder implements Encoder {

   protected final byte[] decodingTable;
   protected final byte[] encodingTable;


   public HexEncoder() {
      byte[] var1 = new byte[]{(byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102};
      this.encodingTable = var1;
      byte[] var2 = new byte[128];
      this.decodingTable = var2;
      this.initialiseDecodingTable();
   }

   private boolean ignore(char var1) {
      boolean var2;
      if(var1 != 10 && var1 != 13 && var1 != 9 && var1 != 32) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int decode(String var1, OutputStream var2) throws IOException {
      int var3 = 0;

      int var4;
      for(var4 = var1.length(); var4 > 0; var4 += -1) {
         int var5 = var4 - 1;
         char var6 = var1.charAt(var5);
         if(!this.ignore(var6)) {
            break;
         }
      }

      int var7 = 0;

      while(var7 < var4) {
         int var8 = var7;

         while(true) {
            if(var8 < var4) {
               char var9 = var1.charAt(var8);
               if(this.ignore(var9)) {
                  ++var8;
                  continue;
               }
            }

            byte[] var10 = this.decodingTable;
            int var11 = var8 + 1;
            char var12 = var1.charAt(var8);
            byte var13 = var10[var12];

            int var14;
            for(var14 = var11; var14 < var4; ++var14) {
               char var15 = var1.charAt(var14);
               if(!this.ignore(var15)) {
                  break;
               }
            }

            byte[] var16 = this.decodingTable;
            var7 = var14 + 1;
            char var17 = var1.charAt(var14);
            byte var18 = var16[var17];
            int var19 = var13 << 4 | var18;
            var2.write(var19);
            ++var3;
            break;
         }
      }

      return var3;
   }

   public int decode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException {
      int var5 = 0;

      int var6;
      for(var6 = var2 + var3; var6 > var2; var6 += -1) {
         int var7 = var6 - 1;
         char var8 = (char)var1[var7];
         if(!this.ignore(var8)) {
            break;
         }
      }

      int var9 = var2;

      while(var9 < var6) {
         int var10 = var9;

         while(true) {
            if(var10 < var6) {
               char var11 = (char)var1[var10];
               if(this.ignore(var11)) {
                  ++var10;
                  continue;
               }
            }

            byte[] var12 = this.decodingTable;
            int var13 = var10 + 1;
            byte var14 = var1[var10];
            byte var15 = var12[var14];

            int var16;
            for(var16 = var13; var16 < var6; ++var16) {
               char var17 = (char)var1[var16];
               if(!this.ignore(var17)) {
                  break;
               }
            }

            byte[] var18 = this.decodingTable;
            var9 = var16 + 1;
            byte var19 = var1[var16];
            byte var20 = var18[var19];
            int var21 = var15 << 4 | var20;
            var4.write(var21);
            ++var5;
            break;
         }
      }

      return var5;
   }

   public int encode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException {
      int var5 = var2;

      while(true) {
         int var6 = var2 + var3;
         if(var5 >= var6) {
            return var3 * 2;
         }

         int var7 = var1[var5] & 255;
         byte[] var8 = this.encodingTable;
         int var9 = var7 >>> 4;
         byte var10 = var8[var9];
         var4.write(var10);
         byte[] var11 = this.encodingTable;
         int var12 = var7 & 15;
         byte var13 = var11[var12];
         var4.write(var13);
         ++var5;
      }
   }

   protected void initialiseDecodingTable() {
      int var1 = 0;

      while(true) {
         int var2 = this.encodingTable.length;
         if(var1 >= var2) {
            byte[] var6 = this.decodingTable;
            byte var7 = this.decodingTable[97];
            var6[65] = var7;
            byte[] var8 = this.decodingTable;
            byte var9 = this.decodingTable[98];
            var8[66] = var9;
            byte[] var10 = this.decodingTable;
            byte var11 = this.decodingTable[99];
            var10[67] = var11;
            byte[] var12 = this.decodingTable;
            byte var13 = this.decodingTable[100];
            var12[68] = var13;
            byte[] var14 = this.decodingTable;
            byte var15 = this.decodingTable[101];
            var14[69] = var15;
            byte[] var16 = this.decodingTable;
            byte var17 = this.decodingTable[102];
            var16[70] = var17;
            return;
         }

         byte[] var3 = this.decodingTable;
         byte var4 = this.encodingTable[var1];
         byte var5 = (byte)var1;
         var3[var4] = var5;
         ++var1;
      }
   }
}
