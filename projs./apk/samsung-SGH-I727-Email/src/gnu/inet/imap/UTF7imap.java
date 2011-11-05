package gnu.inet.imap;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public final class UTF7imap {

   private static final String US_ASCII = "US-ASCII";
   private static final byte[] dst = new byte[256];
   private static final byte[] src = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)44};


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

   private UTF7imap() {}

   public static String decode(String var0) {
      char[] var1 = var0.toCharArray();
      boolean var2 = false;
      StringBuffer var3 = null;
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            String var26;
            if(var3 != null) {
               var26 = var3.toString();
            } else {
               var26 = var0;
            }

            return var26;
         }

         char var6 = var1[var4];
         if(var6 == 38) {
            ByteArrayOutputStream var12;
            StringBuffer var13;
            if(var3 == null) {
               StringBuffer var7 = new StringBuffer();
               ByteArrayOutputStream var8 = new ByteArrayOutputStream();

               int var11;
               for(byte var27 = 0; var27 < var4; var11 = var27 + 1) {
                  char var9 = var1[var27];
                  var7.append(var9);
               }

               var13 = var7;
               var12 = var8;
            } else {
               var12 = null;
               var13 = var3;
            }

            var12.reset();
            var3 = var13;
            var2 = true;
         } else if(var6 == 45 && var2) {
            if(null.size() == 0) {
               StringBuffer var15 = var3.append('&');
            } else {
               int[] var16 = decode(null.toByteArray());
               int var28 = 0;

               while(true) {
                  int var17 = var16.length - 1;
                  if(var28 >= var17) {
                     break;
                  }

                  int var18 = var16[var28];
                  int var19 = var28 + 1;
                  int var20 = var16[var19];
                  char var21 = (char)(var18 * 256 | var20);
                  var3.append(var21);
                  var28 += 2;
               }
            }

            var2 = false;
         } else if(var2) {
            byte var23 = (byte)var6;
            null.write(var23);
         } else if(var3 != null) {
            var3.append(var6);
         }

         ++var4;
      }
   }

   static int[] decode(byte[] var0) {
      int[] var1 = new int[var0.length];
      int var2 = var0.length - 0;
      byte var3 = 0;

      int var4;
      int var30;
      for(var4 = 0; var2 > 0; var2 = var30) {
         byte[] var5 = dst;
         int var6 = var3 + 1;
         int var7 = var0[var3] & 255;
         byte var8 = var5[var7];
         byte[] var9 = dst;
         int var10 = var6 + 1;
         int var11 = var0[var6] & 255;
         byte var12 = var9[var11];
         int var13 = var4 + 1;
         int var14 = var8 << 2 & 252;
         int var15 = var12 >>> 4 & 3;
         int var16 = var14 | var15;
         var1[var4] = var16;
         int var28;
         if(var2 > 2) {
            byte[] var17 = dst;
            var4 = var10 + 1;
            int var18 = var0[var10] & 255;
            var3 = var17[var18];
            var10 = var13 + 1;
            int var19 = var12 << 4 & 240;
            int var20 = var3 >>> 2 & 15;
            int var21 = var19 | var20;
            var1[var13] = var21;
            if(var2 > 3) {
               byte[] var22 = dst;
               int var23 = var4 + 1;
               int var24 = var0[var4] & 255;
               byte var25 = var22[var24];
               var4 = var10 + 1;
               int var26 = var3 << 6 & 192;
               int var27 = var25 & 63 | var26;
               var1[var10] = var27;
               var28 = var23;
            } else {
               var28 = var4;
            }
         } else {
            var28 = var10;
         }

         var30 = var0.length - var28;
      }

      int[] var33 = new int[var4];
      System.arraycopy(var1, 0, var33, 0, var4);
      return var33;
   }

   public static String encode(String param0) {
      // $FF: Couldn't be decompiled
   }

   static byte[] encode(byte[] var0) {
      byte[] var1 = new byte[(var0.length + 2) / 3 * 4 - 1];
      byte var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
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
            int var11 = var7 + 1;
            byte[] var12 = src;
            int var13 = var6 << 4 & 48;
            int var14 = 0 >>> 4 & 15;
            int var15 = var13 + 0;
            byte var16 = var12[var15];
            var1[var7] = var16;
         } else if(var5 == 2) {
            byte var17 = var0[var3];
            int var18 = var3 + 1;
            byte var19 = var0[var18];
            int var20 = var2 + 1;
            byte[] var21 = src;
            int var22 = var17 >>> 2 & 63;
            byte var23 = var21[var22];
            var1[var2] = var23;
            int var24 = var20 + 1;
            byte[] var25 = src;
            int var26 = var17 << 4 & 48;
            int var27 = var19 >>> 4 & 15;
            int var28 = var26 + var27;
            byte var29 = var25[var28];
            var1[var20] = var29;
            int var30 = var24 + 1;
            byte[] var31 = src;
            int var32 = var19 << 2 & 60;
            int var33 = 0 >>> 6 & 3;
            int var34 = var32 + 0;
            byte var35 = var31[var34];
            var1[var24] = var35;
         } else if(var5 == 3) {
            byte var37 = var0[var3];
            int var38 = var3 + 1;
            byte var39 = var0[var38];
            int var40 = var3 + 2;
            byte var41 = var0[var40];
            int var42 = var2 + 1;
            byte[] var43 = src;
            int var44 = var37 >>> 2 & 63;
            byte var45 = var43[var44];
            var1[var2] = var45;
            int var46 = var42 + 1;
            byte[] var47 = src;
            int var48 = var37 << 4 & 48;
            int var49 = var39 >>> 4 & 15;
            int var50 = var48 + var49;
            byte var51 = var47[var50];
            var1[var42] = var51;
            int var52 = var46 + 1;
            byte[] var53 = src;
            int var54 = var39 << 2 & 60;
            int var55 = var41 >>> 6 & 3;
            int var56 = var54 + var55;
            byte var57 = var53[var56];
            var1[var46] = var57;
            int var58 = var52 + 1;
            byte[] var59 = src;
            int var60 = var41 & 63;
            byte var61 = var59[var60];
            var1[var52] = var61;
         }

         var3 += 3;
      }
   }

   public static void main(String[] var0) {
      int var1 = 0;
      boolean var2 = false;

      while(true) {
         int var3 = var0.length;
         if(var1 >= var3) {
            return;
         }

         if(var0[var1].equals("-d")) {
            var2 = true;
         } else {
            String var4;
            if(var2) {
               var4 = decode(var0[var1]);
            } else {
               var4 = encode(var0[var1]);
            }

            String var5 = var0[var1];
            StringBuffer var6 = new StringBuffer(var5);
            StringBuffer var7 = var6.append(" = \"");
            var6.append(var4);
            StringBuffer var9 = var6.append("\"(");
            int var10 = 0;

            while(true) {
               int var11 = var4.length();
               if(var10 >= var11) {
                  StringBuffer var15 = var6.append(")");
                  PrintStream var16 = System.out;
                  String var17 = var6.toString();
                  var16.println(var17);
                  break;
               }

               if(var10 > 0) {
                  StringBuffer var12 = var6.append(' ');
               }

               String var13 = Integer.toString(var4.charAt(var10), 16);
               var6.append(var13);
               ++var10;
            }
         }

         ++var1;
      }
   }
}
