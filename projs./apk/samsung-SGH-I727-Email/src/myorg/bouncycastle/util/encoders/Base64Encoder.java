package myorg.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.util.encoders.Encoder;

public class Base64Encoder implements Encoder {

   protected final byte[] decodingTable;
   protected final byte[] encodingTable;
   protected byte padding;


   public Base64Encoder() {
      byte[] var1 = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};
      this.encodingTable = var1;
      this.padding = 61;
      byte[] var2 = new byte[128];
      this.decodingTable = var2;
      this.initialiseDecodingTable();
   }

   private int decodeLastBlock(OutputStream var1, char var2, char var3, char var4, char var5) throws IOException {
      byte var6 = this.padding;
      byte var12;
      if(var4 == var6) {
         byte var7 = this.decodingTable[var2];
         byte var8 = this.decodingTable[var3];
         int var9 = var7 << 2;
         int var10 = var8 >> 4;
         int var11 = var9 | var10;
         var1.write(var11);
         var12 = 1;
      } else {
         byte var13 = this.padding;
         if(var5 == var13) {
            byte var14 = this.decodingTable[var2];
            byte var15 = this.decodingTable[var3];
            byte var16 = this.decodingTable[var4];
            int var17 = var14 << 2;
            int var18 = var15 >> 4;
            int var19 = var17 | var18;
            var1.write(var19);
            int var20 = var15 << 4;
            int var21 = var16 >> 2;
            int var22 = var20 | var21;
            var1.write(var22);
            var12 = 2;
         } else {
            byte var23 = this.decodingTable[var2];
            byte var24 = this.decodingTable[var3];
            byte var25 = this.decodingTable[var4];
            byte var26 = this.decodingTable[var5];
            int var27 = var23 << 2;
            int var28 = var24 >> 4;
            int var29 = var27 | var28;
            var1.write(var29);
            int var30 = var24 << 4;
            int var31 = var25 >> 2;
            int var32 = var30 | var31;
            var1.write(var32);
            int var33 = var25 << 6 | var26;
            var1.write(var33);
            var12 = 3;
         }
      }

      return var12;
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

   private int nextI(String var1, int var2, int var3) {
      while(true) {
         if(var2 < var3) {
            char var4 = var1.charAt(var2);
            if(this.ignore(var4)) {
               ++var2;
               continue;
            }
         }

         return var2;
      }
   }

   private int nextI(byte[] var1, int var2, int var3) {
      while(true) {
         if(var2 < var3) {
            char var4 = (char)var1[var2];
            if(this.ignore(var4)) {
               ++var2;
               continue;
            }
         }

         return var2;
      }
   }

   public int decode(String var1, OutputStream var2) throws IOException {
      int var3 = 0;

      int var4;
      for(var4 = var1.length(); var4 > 0; var4 += -1) {
         int var5 = var4 - 1;
         char var8 = var1.charAt(var5);
         if(!this.ignore(var8)) {
            break;
         }
      }

      int var11 = var4 - 4;
      byte var14 = 0;

      int var53;
      for(int var16 = this.nextI(var1, var14, var11); var16 < var11; var16 = this.nextI(var1, var53, var11)) {
         byte[] var19 = this.decodingTable;
         int var20 = var16 + 1;
         char var23 = var1.charAt(var16);
         byte var24 = var19[var23];
         int var29 = this.nextI(var1, var20, var11);
         byte[] var30 = this.decodingTable;
         int var31 = var29 + 1;
         char var34 = var1.charAt(var29);
         byte var35 = var30[var34];
         int var40 = this.nextI(var1, var31, var11);
         byte[] var41 = this.decodingTable;
         int var42 = var40 + 1;
         char var45 = var1.charAt(var40);
         byte var46 = var41[var45];
         int var51 = this.nextI(var1, var42, var11);
         byte[] var52 = this.decodingTable;
         var53 = var51 + 1;
         char var56 = var1.charAt(var51);
         byte var57 = var52[var56];
         int var58 = var24 << 2;
         int var59 = var35 >> 4;
         int var60 = var58 | var59;
         var2.write(var60);
         int var63 = var35 << 4;
         int var64 = var46 >> 2;
         int var65 = var63 | var64;
         var2.write(var65);
         int var68 = var46 << 6 | var57;
         var2.write(var68);
         var3 += 3;
      }

      int var75 = var4 - 4;
      char var78 = var1.charAt(var75);
      int var79 = var4 - 3;
      char var82 = var1.charAt(var79);
      int var83 = var4 - 2;
      char var86 = var1.charAt(var83);
      int var87 = var4 - 1;
      char var90 = var1.charAt(var87);
      int var93 = this.decodeLastBlock(var2, var78, var82, var86, var90);
      return var3 + var93;
   }

   public int decode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException {
      int var5 = 0;

      int var6;
      for(var6 = var2 + var3; var6 > var2; var6 += -1) {
         int var9 = var6 - 1;
         char var10 = (char)var1[var9];
         if(!this.ignore(var10)) {
            break;
         }
      }

      int var14 = var6 - 4;

      int var50;
      for(int var19 = this.nextI(var1, var2, var14); var19 < var14; var19 = this.nextI(var1, var50, var14)) {
         byte[] var22 = this.decodingTable;
         int var23 = var19 + 1;
         byte var24 = var1[var19];
         byte var25 = var22[var24];
         int var30 = this.nextI(var1, var23, var14);
         byte[] var31 = this.decodingTable;
         int var32 = var30 + 1;
         byte var33 = var1[var30];
         byte var34 = var31[var33];
         int var39 = this.nextI(var1, var32, var14);
         byte[] var40 = this.decodingTable;
         int var41 = var39 + 1;
         byte var42 = var1[var39];
         byte var43 = var40[var42];
         int var48 = this.nextI(var1, var41, var14);
         byte[] var49 = this.decodingTable;
         var50 = var48 + 1;
         byte var51 = var1[var48];
         byte var52 = var49[var51];
         int var53 = var25 << 2;
         int var54 = var34 >> 4;
         int var55 = var53 | var54;
         var4.write(var55);
         int var58 = var34 << 4;
         int var59 = var43 >> 2;
         int var60 = var58 | var59;
         var4.write(var60);
         int var63 = var43 << 6 | var52;
         var4.write(var63);
         var5 += 3;
      }

      int var70 = var6 - 4;
      char var71 = (char)var1[var70];
      int var72 = var6 - 3;
      char var73 = (char)var1[var72];
      int var74 = var6 - 2;
      char var75 = (char)var1[var74];
      int var76 = var6 - 1;
      char var77 = (char)var1[var76];
      int var80 = this.decodeLastBlock(var4, var71, var73, var75, var77);
      return var5 + var80;
   }

   public int encode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException {
      int var5 = var3 % 3;
      int var6 = var3 - var5;
      int var7 = var2;

      while(true) {
         int var8 = var2 + var6;
         if(var7 >= var8) {
            switch(var5) {
            case 0:
            default:
               break;
            case 1:
               int var40 = var2 + var6;
               int var41 = var1[var40] & 255;
               int var42 = var41 >>> 2 & 63;
               int var43 = var41 << 4 & 63;
               byte var44 = this.encodingTable[var42];
               var4.write(var44);
               byte var47 = this.encodingTable[var43];
               var4.write(var47);
               byte var50 = this.padding;
               var4.write(var50);
               byte var53 = this.padding;
               var4.write(var53);
               break;
            case 2:
               int var56 = var2 + var6;
               int var57 = var1[var56] & 255;
               int var58 = var2 + var6 + 1;
               int var59 = var1[var58] & 255;
               int var60 = var57 >>> 2 & 63;
               int var61 = var57 << 4;
               int var62 = var59 >>> 4;
               int var63 = (var61 | var62) & 63;
               int var64 = var59 << 2 & 63;
               byte var65 = this.encodingTable[var60];
               var4.write(var65);
               byte var68 = this.encodingTable[var63];
               var4.write(var68);
               byte var71 = this.encodingTable[var64];
               var4.write(var71);
               byte var74 = this.padding;
               var4.write(var74);
            }

            int var38 = var6 / 3 * 4;
            byte var39;
            if(var5 == 0) {
               var39 = 0;
            } else {
               var39 = 4;
            }

            return var38 + var39;
         }

         int var9 = var1[var7] & 255;
         int var10 = var7 + 1;
         int var11 = var1[var10] & 255;
         int var12 = var7 + 2;
         int var13 = var1[var12] & 255;
         byte[] var14 = this.encodingTable;
         int var15 = var9 >>> 2 & 63;
         byte var16 = var14[var15];
         var4.write(var16);
         byte[] var19 = this.encodingTable;
         int var20 = var9 << 4;
         int var21 = var11 >>> 4;
         int var22 = (var20 | var21) & 63;
         byte var23 = var19[var22];
         var4.write(var23);
         byte[] var26 = this.encodingTable;
         int var27 = var11 << 2;
         int var28 = var13 >>> 6;
         int var29 = (var27 | var28) & 63;
         byte var30 = var26[var29];
         var4.write(var30);
         byte[] var33 = this.encodingTable;
         int var34 = var13 & 63;
         byte var35 = var33[var34];
         var4.write(var35);
         var7 += 3;
      }
   }

   protected void initialiseDecodingTable() {
      int var1 = 0;

      while(true) {
         int var2 = this.encodingTable.length;
         if(var1 >= var2) {
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
