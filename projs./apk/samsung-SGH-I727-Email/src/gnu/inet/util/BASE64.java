package gnu.inet.util;


public final class BASE64 {

   private static final byte[] dst = new byte[256];
   private static final byte[] src = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};


   static {
      for(int var0 = 0; var0 < 255; ++var0) {
         dst[var0] = -1;
      }

      int var1 = 0;

      while(true) {
         int var2 = src.length;
         if(var1 >= var2) {
            return;
         }

         byte[] var3 = dst;
         byte var4 = src[var1];
         byte var5 = (byte)var1;
         var3[var4] = var5;
         ++var1;
      }
   }

   private BASE64() {}

   public static byte[] decode(byte[] var0) {
      int var1;
      for(var1 = var0.length; var1 > 0; var1 += -1) {
         int var2 = var1 - 1;
         if(var0[var2] != 61) {
            break;
         }
      }

      byte[] var3 = new byte[var1];
      int var4 = var1 - 0;
      byte var5 = 0;

      int var6;
      int var32;
      for(var6 = 0; var4 > 0; var4 = var32) {
         byte[] var7 = dst;
         int var8 = var5 + 1;
         int var9 = var0[var5] & 255;
         byte var10 = var7[var9];
         byte[] var11 = dst;
         int var12 = var8 + 1;
         int var13 = var0[var8] & 255;
         byte var14 = var11[var13];
         int var15 = var6 + 1;
         int var16 = var10 << 2 & 252;
         int var17 = var14 >>> 4 & 3;
         byte var18 = (byte)(var16 | var17);
         var3[var6] = var18;
         int var30;
         if(var4 > 2) {
            byte[] var19 = dst;
            var6 = var12 + 1;
            int var20 = var0[var12] & 255;
            var5 = var19[var20];
            var12 = var15 + 1;
            int var21 = var14 << 4 & 240;
            int var22 = var5 >>> 2 & 15;
            byte var23 = (byte)(var21 | var22);
            var3[var15] = var23;
            if(var4 > 3) {
               byte[] var24 = dst;
               int var25 = var6 + 1;
               int var26 = var0[var6] & 255;
               byte var27 = var24[var26];
               var6 = var12 + 1;
               int var28 = var5 << 6 & 192;
               byte var29 = (byte)(var27 & 63 | var28);
               var3[var12] = var29;
               var30 = var25;
            } else {
               var30 = var6;
            }
         } else {
            var30 = var12;
         }

         var32 = var1 - var30;
      }

      byte[] var35 = new byte[var6];
      System.arraycopy(var3, 0, var35, 0, var6);
      return var35;
   }

   public static byte[] encode(byte[] var0) {
      byte[] var1 = new byte[(var0.length + 2) * 4 / 3];
      int var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            int var59 = var1.length;
            if(var2 < var59) {
               byte[] var60 = new byte[var2];
               System.arraycopy(var1, 0, var60, 0, var2);
               var1 = var60;
            }

            return var1;
         }

         int var5 = var0.length - var3;
         if(var5 == 1) {
            byte var6 = var0[var3];
            int var7 = var2 + 1;
            byte[] var8 = src;
            int var9 = var6 >>> 2 & 63;
            byte var10 = var8[var9];
            var1[var2] = var10;
            var2 = var7 + 1;
            byte[] var11 = src;
            int var12 = var6 << 4 & 48;
            int var13 = 0 >>> 4 & 15;
            int var14 = var12 + 0;
            byte var15 = var11[var14];
            var1[var7] = var15;
         } else if(var5 == 2) {
            byte var16 = var0[var3];
            int var17 = var3 + 1;
            byte var18 = var0[var17];
            int var19 = var2 + 1;
            byte[] var20 = src;
            int var21 = var16 >>> 2 & 63;
            byte var22 = var20[var21];
            var1[var2] = var22;
            int var23 = var19 + 1;
            byte[] var24 = src;
            int var25 = var16 << 4 & 48;
            int var26 = var18 >>> 4 & 15;
            int var27 = var25 + var26;
            byte var28 = var24[var27];
            var1[var19] = var28;
            int var29 = var23 + 1;
            byte[] var30 = src;
            int var31 = var18 << 2 & 60;
            int var32 = 0 >>> 6 & 3;
            int var33 = var31 + 0;
            byte var34 = var30[var33];
            var1[var23] = var34;
            var2 = var29;
         } else {
            byte var35 = var0[var3];
            int var36 = var3 + 1;
            byte var37 = var0[var36];
            int var38 = var3 + 2;
            byte var39 = var0[var38];
            int var40 = var2 + 1;
            byte[] var41 = src;
            int var42 = var35 >>> 2 & 63;
            byte var43 = var41[var42];
            var1[var2] = var43;
            int var44 = var40 + 1;
            byte[] var45 = src;
            int var46 = var35 << 4 & 48;
            int var47 = var37 >>> 4 & 15;
            int var48 = var46 + var47;
            byte var49 = var45[var48];
            var1[var40] = var49;
            int var50 = var44 + 1;
            byte[] var51 = src;
            int var52 = var37 << 2 & 60;
            int var53 = var39 >>> 6 & 3;
            int var54 = var52 + var53;
            byte var55 = var51[var54];
            var1[var44] = var55;
            var2 = var50 + 1;
            byte[] var56 = src;
            int var57 = var39 & 63;
            byte var58 = var56[var57];
            var1[var50] = var58;
         }

         var3 += 3;
      }
   }

   public static void main(String[] param0) {
      // $FF: Couldn't be decompiled
   }
}
