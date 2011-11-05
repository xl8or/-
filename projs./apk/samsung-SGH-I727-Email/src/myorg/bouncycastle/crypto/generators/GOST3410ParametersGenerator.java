package myorg.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import myorg.bouncycastle.crypto.params.GOST3410Parameters;
import myorg.bouncycastle.crypto.params.GOST3410ValidationParameters;

public class GOST3410ParametersGenerator {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private SecureRandom init_random;
   private int size;
   private int typeproc;


   public GOST3410ParametersGenerator() {}

   private int procedure_A(int var1, int var2, BigInteger[] var3, int var4) {
      while(true) {
         if(var1 >= 0) {
            int var6 = 65536;
            if(var1 <= var6) {
               while(true) {
                  if(var2 >= 0) {
                     int var8 = 65536;
                     if(var2 <= var8 && var2 / 2 != 0) {
                        BigInteger var9 = new BigInteger;
                        String var10 = Integer.toString(var2);
                        var9.<init>(var10);
                        BigInteger var13 = new BigInteger;
                        String var15 = "19381";
                        var13.<init>(var15);
                        BigInteger[] var16 = new BigInteger[1];
                        String var17 = Integer.toString(var1);
                        BigInteger var18 = new BigInteger(var17);
                        var16[0] = var18;
                        int[] var19 = new int[]{var4};
                        int var20 = 0;
                        int var21 = 0;

                        while(true) {
                           int var22 = var19[var21];
                           byte var23 = 17;
                           if(var22 < var23) {
                              BigInteger[] var39 = new BigInteger[var20 + 1];
                              BigInteger var40 = new BigInteger("8003", 16);
                              var39[var20] = var40;
                              int var41 = var20 - 1;
                              int var42 = 0;

                              label65:
                              while(true) {
                                 int var142;
                                 if(var42 < var20) {
                                    int var43 = var19[var41] / 16;

                                    while(true) {
                                       BigInteger[] var44 = new BigInteger[var16.length];
                                       int var45 = var16.length;
                                       byte var47 = 0;
                                       byte var49 = 0;
                                       System.arraycopy(var16, var47, var44, var49, var45);
                                       var16 = new BigInteger[var43 + 1];
                                       int var51 = var44.length;
                                       byte var53 = 0;
                                       byte var55 = 0;
                                       System.arraycopy(var44, var53, var16, var55, var51);

                                       for(int var57 = 0; var57 < var43; ++var57) {
                                          int var58 = var57 + 1;
                                          BigInteger var59 = var16[var57];
                                          BigInteger var61 = var59.multiply(var13);
                                          BigInteger var63 = var61.add(var9);
                                          BigInteger var64 = TWO.pow(16);
                                          BigInteger var65 = var63.mod(var64);
                                          var16[var58] = var65;
                                       }

                                       BigInteger var66 = new BigInteger;
                                       String var68 = "0";
                                       var66.<init>(var68);

                                       for(int var69 = 0; var69 < var43; ++var69) {
                                          BigInteger var70 = var16[var69];
                                          BigInteger var71 = TWO;
                                          int var72 = var69 * 16;
                                          BigInteger var73 = var71.pow(var72);
                                          BigInteger var74 = var70.multiply(var73);
                                          var66 = var66.add(var74);
                                       }

                                       BigInteger var77 = var16[var43];
                                       var16[0] = var77;
                                       BigInteger var78 = TWO;
                                       int var79 = var19[var41] - 1;
                                       BigInteger var80 = var78.pow(var79);
                                       int var81 = var41 + 1;
                                       BigInteger var82 = var39[var81];
                                       BigInteger var83 = var80.divide(var82);
                                       BigInteger var84 = TWO;
                                       int var85 = var19[var41] - 1;
                                       BigInteger var86 = var84.pow(var85);
                                       BigInteger var88 = var86.multiply(var66);
                                       int var89 = var41 + 1;
                                       BigInteger var90 = var39[var89];
                                       BigInteger var91 = TWO;
                                       int var92 = var43 * 16;
                                       BigInteger var93 = var91.pow(var92);
                                       BigInteger var94 = var90.multiply(var93);
                                       BigInteger var95 = var88.divide(var94);
                                       BigInteger var96 = var83.add(var95);
                                       BigInteger var97 = TWO;
                                       BigInteger var100 = var96.mod(var97);
                                       BigInteger var101 = ONE;
                                       if(var100.compareTo(var101) == 0) {
                                          BigInteger var102 = ONE;
                                          var96 = var96.add(var102);
                                       }

                                       int var105 = 0;

                                       while(true) {
                                          int var106 = var41 + 1;
                                          BigInteger var107 = var39[var106];
                                          BigInteger var108 = BigInteger.valueOf((long)var105);
                                          BigInteger var111 = var96.add(var108);
                                          BigInteger var112 = var107.multiply(var111);
                                          BigInteger var113 = ONE;
                                          BigInteger var114 = var112.add(var113);
                                          var39[var41] = var114;
                                          BigInteger var115 = var39[var41];
                                          BigInteger var116 = TWO;
                                          int var117 = var19[var41];
                                          BigInteger var118 = var116.pow(var117);
                                          int var119 = var115.compareTo(var118);
                                          byte var120 = 1;
                                          if(var119 == var120) {
                                             break;
                                          }

                                          BigInteger var121 = TWO;
                                          int var122 = var41 + 1;
                                          BigInteger var123 = var39[var122];
                                          BigInteger var124 = BigInteger.valueOf((long)var105);
                                          BigInteger var127 = var96.add(var124);
                                          BigInteger var128 = var123.multiply(var127);
                                          BigInteger var129 = var39[var41];
                                          BigInteger var130 = var121.modPow(var128, var129);
                                          BigInteger var131 = ONE;
                                          if(var130.compareTo(var131) == 0) {
                                             BigInteger var132 = TWO;
                                             BigInteger var133 = BigInteger.valueOf((long)var105);
                                             BigInteger var136 = var96.add(var133);
                                             BigInteger var137 = var39[var41];
                                             BigInteger var138 = var132.modPow(var136, var137);
                                             BigInteger var139 = ONE;
                                             if(var138.compareTo(var139) != 0) {
                                                var41 += -1;
                                                if(var41 >= 0) {
                                                   ++var42;
                                                   continue label65;
                                                }

                                                BigInteger var140 = var39[0];
                                                var3[0] = var140;
                                                BigInteger var141 = var39[1];
                                                var3[1] = var141;
                                                var142 = var16[0].intValue();
                                                return var142;
                                             }
                                          }

                                          var105 += 2;
                                       }
                                    }
                                 }

                                 var142 = var16[0].intValue();
                                 return var142;
                              }
                           }

                           int[] var24 = new int[var19.length + 1];
                           int var25 = var19.length;
                           byte var27 = 0;
                           byte var29 = 0;
                           System.arraycopy(var19, var27, var24, var29, var25);
                           var19 = new int[var24.length];
                           int var31 = var24.length;
                           byte var33 = 0;
                           byte var35 = 0;
                           System.arraycopy(var24, var33, var19, var35, var31);
                           int var37 = var21 + 1;
                           int var38 = var19[var21] / 2;
                           var19[var37] = var38;
                           var20 = var21 + 1;
                           ++var21;
                        }
                     }
                  }

                  var2 = this.init_random.nextInt() / '\u8000' + 1;
               }
            }
         }

         var1 = this.init_random.nextInt() / '\u8000';
      }
   }

   private long procedure_Aa(long var1, long var3, BigInteger[] var5, int var6) {
      while(var1 < 0L || var1 > 4294967296L) {
         var1 = (long)(this.init_random.nextInt() * 2);
      }

      while(var3 < 0L || var3 > 4294967296L || var3 / 2L == 0L) {
         var3 = (long)(this.init_random.nextInt() * 2 + 1);
      }

      BigInteger var7 = new BigInteger;
      String var8 = Long.toString(var3);
      var7.<init>(var8);
      BigInteger var11 = new BigInteger;
      String var13 = "97781173";
      var11.<init>(var13);
      BigInteger[] var14 = new BigInteger[1];
      String var15 = Long.toString(var1);
      BigInteger var16 = new BigInteger(var15);
      var14[0] = var16;
      int[] var17 = new int[]{var6};
      int var18 = 0;
      int var19 = 0;

      while(true) {
         int var20 = var17[var19];
         byte var21 = 33;
         if(var20 < var21) {
            BigInteger[] var37 = new BigInteger[var18 + 1];
            BigInteger var38 = new BigInteger("8000000B", 16);
            var37[var18] = var38;
            int var39 = var18 - 1;
            int var40 = 0;

            label65:
            while(true) {
               long var140;
               if(var40 < var18) {
                  int var41 = var17[var39] / 32;

                  while(true) {
                     BigInteger[] var42 = new BigInteger[var14.length];
                     int var43 = var14.length;
                     byte var45 = 0;
                     byte var47 = 0;
                     System.arraycopy(var14, var45, var42, var47, var43);
                     var14 = new BigInteger[var41 + 1];
                     int var49 = var42.length;
                     byte var51 = 0;
                     byte var53 = 0;
                     System.arraycopy(var42, var51, var14, var53, var49);

                     for(int var55 = 0; var55 < var41; ++var55) {
                        int var56 = var55 + 1;
                        BigInteger var57 = var14[var55];
                        BigInteger var59 = var57.multiply(var11);
                        BigInteger var61 = var59.add(var7);
                        BigInteger var62 = TWO.pow(32);
                        BigInteger var63 = var61.mod(var62);
                        var14[var56] = var63;
                     }

                     BigInteger var64 = new BigInteger;
                     String var66 = "0";
                     var64.<init>(var66);

                     for(int var67 = 0; var67 < var41; ++var67) {
                        BigInteger var68 = var14[var67];
                        BigInteger var69 = TWO;
                        int var70 = var67 * 32;
                        BigInteger var71 = var69.pow(var70);
                        BigInteger var72 = var68.multiply(var71);
                        var64 = var64.add(var72);
                     }

                     BigInteger var75 = var14[var41];
                     var14[0] = var75;
                     BigInteger var76 = TWO;
                     int var77 = var17[var39] - 1;
                     BigInteger var78 = var76.pow(var77);
                     int var79 = var39 + 1;
                     BigInteger var80 = var37[var79];
                     BigInteger var81 = var78.divide(var80);
                     BigInteger var82 = TWO;
                     int var83 = var17[var39] - 1;
                     BigInteger var84 = var82.pow(var83);
                     BigInteger var86 = var84.multiply(var64);
                     int var87 = var39 + 1;
                     BigInteger var88 = var37[var87];
                     BigInteger var89 = TWO;
                     int var90 = var41 * 32;
                     BigInteger var91 = var89.pow(var90);
                     BigInteger var92 = var88.multiply(var91);
                     BigInteger var93 = var86.divide(var92);
                     BigInteger var94 = var81.add(var93);
                     BigInteger var95 = TWO;
                     BigInteger var98 = var94.mod(var95);
                     BigInteger var99 = ONE;
                     if(var98.compareTo(var99) == 0) {
                        BigInteger var100 = ONE;
                        var94 = var94.add(var100);
                     }

                     int var103 = 0;

                     while(true) {
                        int var104 = var39 + 1;
                        BigInteger var105 = var37[var104];
                        BigInteger var106 = BigInteger.valueOf((long)var103);
                        BigInteger var109 = var94.add(var106);
                        BigInteger var110 = var105.multiply(var109);
                        BigInteger var111 = ONE;
                        BigInteger var112 = var110.add(var111);
                        var37[var39] = var112;
                        BigInteger var113 = var37[var39];
                        BigInteger var114 = TWO;
                        int var115 = var17[var39];
                        BigInteger var116 = var114.pow(var115);
                        int var117 = var113.compareTo(var116);
                        byte var118 = 1;
                        if(var117 == var118) {
                           break;
                        }

                        BigInteger var119 = TWO;
                        int var120 = var39 + 1;
                        BigInteger var121 = var37[var120];
                        BigInteger var122 = BigInteger.valueOf((long)var103);
                        BigInteger var125 = var94.add(var122);
                        BigInteger var126 = var121.multiply(var125);
                        BigInteger var127 = var37[var39];
                        BigInteger var128 = var119.modPow(var126, var127);
                        BigInteger var129 = ONE;
                        if(var128.compareTo(var129) == 0) {
                           BigInteger var130 = TWO;
                           BigInteger var131 = BigInteger.valueOf((long)var103);
                           BigInteger var134 = var94.add(var131);
                           BigInteger var135 = var37[var39];
                           BigInteger var136 = var130.modPow(var134, var135);
                           BigInteger var137 = ONE;
                           if(var136.compareTo(var137) != 0) {
                              var39 += -1;
                              if(var39 >= 0) {
                                 ++var40;
                                 continue label65;
                              }

                              BigInteger var138 = var37[0];
                              var5[0] = var138;
                              BigInteger var139 = var37[1];
                              var5[1] = var139;
                              var140 = var14[0].longValue();
                              return var140;
                           }
                        }

                        var103 += 2;
                     }
                  }
               }

               var140 = var14[0].longValue();
               return var140;
            }
         }

         int[] var22 = new int[var17.length + 1];
         int var23 = var17.length;
         byte var25 = 0;
         byte var27 = 0;
         System.arraycopy(var17, var25, var22, var27, var23);
         var17 = new int[var22.length];
         int var29 = var22.length;
         byte var31 = 0;
         byte var33 = 0;
         System.arraycopy(var22, var31, var17, var33, var29);
         int var35 = var19 + 1;
         int var36 = var17[var19] / 2;
         var17[var35] = var36;
         var18 = var19 + 1;
         ++var19;
      }
   }

   private void procedure_B(int var1, int var2, BigInteger[] var3) {
      while(true) {
         if(var1 >= 0) {
            int var5 = 65536;
            if(var1 <= var5) {
               while(true) {
                  if(var2 >= 0) {
                     int var7 = 65536;
                     if(var2 <= var7 && var2 / 2 != 0) {
                        BigInteger[] var8 = new BigInteger[2];
                        BigInteger var9 = new BigInteger;
                        String var10 = Integer.toString(var2);
                        var9.<init>(var10);
                        BigInteger var13 = new BigInteger;
                        String var15 = "19381";
                        var13.<init>(var15);
                        short var20 = 256;
                        int var21 = this.procedure_A(var1, var2, var8, var20);
                        BigInteger var22 = var8[0];
                        short var27 = 512;
                        int var28 = this.procedure_A(var21, var2, var8, var27);
                        BigInteger var29 = var8[0];
                        BigInteger[] var30 = new BigInteger[65];
                        String var31 = Integer.toString(var28);
                        BigInteger var32 = new BigInteger(var31);
                        var30[0] = var32;
                        short var33 = 1024;

                        label46:
                        while(true) {
                           int var34 = 0;

                           while(true) {
                              byte var36 = 64;
                              if(var34 >= var36) {
                                 BigInteger var45 = new BigInteger;
                                 String var47 = "0";
                                 var45.<init>(var47);
                                 int var48 = 0;

                                 while(true) {
                                    byte var50 = 64;
                                    if(var48 >= var50) {
                                       BigInteger var58 = var30[64];
                                       var30[0] = var58;
                                       BigInteger var59 = TWO;
                                       int var60 = var33 - 1;
                                       BigInteger var61 = var59.pow(var60);
                                       BigInteger var62 = var22.multiply(var29);
                                       BigInteger var63 = var61.divide(var62);
                                       BigInteger var64 = TWO;
                                       int var65 = var33 - 1;
                                       BigInteger var66 = var64.pow(var65);
                                       BigInteger var68 = var66.multiply(var45);
                                       BigInteger var69 = var22.multiply(var29);
                                       BigInteger var70 = TWO.pow(1024);
                                       BigInteger var71 = var69.multiply(var70);
                                       BigInteger var72 = var68.divide(var71);
                                       BigInteger var73 = var63.add(var72);
                                       BigInteger var74 = TWO;
                                       BigInteger var77 = var73.mod(var74);
                                       BigInteger var78 = ONE;
                                       if(var77.compareTo(var78) == 0) {
                                          BigInteger var79 = ONE;
                                          var73 = var73.add(var79);
                                       }

                                       int var82 = 0;

                                       while(true) {
                                          BigInteger var83 = var22.multiply(var29);
                                          BigInteger var84 = BigInteger.valueOf((long)var82);
                                          BigInteger var87 = var73.add(var84);
                                          BigInteger var88 = var83.multiply(var87);
                                          BigInteger var89 = ONE;
                                          BigInteger var90 = var88.add(var89);
                                          BigInteger var91 = TWO;
                                          BigInteger var93 = var91.pow(var33);
                                          int var96 = var90.compareTo(var93);
                                          byte var97 = 1;
                                          if(var96 == var97) {
                                             continue label46;
                                          }

                                          BigInteger var98 = TWO;
                                          BigInteger var99 = var22.multiply(var29);
                                          BigInteger var100 = BigInteger.valueOf((long)var82);
                                          BigInteger var103 = var73.add(var100);
                                          BigInteger var104 = var99.multiply(var103);
                                          BigInteger var108 = var98.modPow(var104, var90);
                                          BigInteger var109 = ONE;
                                          if(var108.compareTo(var109) == 0) {
                                             BigInteger var110 = TWO;
                                             BigInteger var111 = BigInteger.valueOf((long)var82);
                                             BigInteger var114 = var73.add(var111);
                                             BigInteger var117 = var22.multiply(var114);
                                             BigInteger var121 = var110.modPow(var117, var90);
                                             BigInteger var122 = ONE;
                                             if(var121.compareTo(var122) != 0) {
                                                var3[0] = var90;
                                                var3[1] = var22;
                                                return;
                                             }
                                          }

                                          var82 += 2;
                                       }
                                    }

                                    BigInteger var51 = var30[var48];
                                    BigInteger var52 = TWO;
                                    int var53 = var48 * 16;
                                    BigInteger var54 = var52.pow(var53);
                                    BigInteger var55 = var51.multiply(var54);
                                    var45 = var45.add(var55);
                                    ++var48;
                                 }
                              }

                              int var37 = var34 + 1;
                              BigInteger var38 = var30[var34];
                              BigInteger var40 = var38.multiply(var13);
                              BigInteger var42 = var40.add(var9);
                              BigInteger var43 = TWO.pow(16);
                              BigInteger var44 = var42.mod(var43);
                              var30[var37] = var44;
                              ++var34;
                           }
                        }
                     }
                  }

                  var2 = this.init_random.nextInt() / '\u8000' + 1;
               }
            }
         }

         var1 = this.init_random.nextInt() / '\u8000';
      }
   }

   private void procedure_Bb(long var1, long var3, BigInteger[] var5) {
      while(var1 < 0L || var1 > 4294967296L) {
         var1 = (long)(this.init_random.nextInt() * 2);
      }

      while(var3 < 0L || var3 > 4294967296L || var3 / 2L == 0L) {
         var3 = (long)(this.init_random.nextInt() * 2 + 1);
      }

      BigInteger[] var6 = new BigInteger[2];
      String var7 = Long.toString(var3);
      BigInteger var8 = new BigInteger(var7);
      BigInteger var9 = new BigInteger("97781173");
      long var15 = this.procedure_Aa(var1, var3, var6, 256);
      BigInteger var17 = var6[0];
      long var23 = this.procedure_Aa(var15, var3, var6, 512);
      BigInteger var25 = var6[0];
      BigInteger[] var26 = new BigInteger[33];
      String var27 = Long.toString(var23);
      BigInteger var28 = new BigInteger(var27);
      var26[0] = var28;
      short var29 = 1024;

      while(true) {
         for(int var30 = 0; var30 < 32; ++var30) {
            int var31 = var30 + 1;
            BigInteger var32 = var26[var30].multiply(var9).add(var8);
            BigInteger var33 = TWO.pow(32);
            BigInteger var34 = var32.mod(var33);
            var26[var31] = var34;
         }

         BigInteger var35 = new BigInteger("0");

         for(int var36 = 0; var36 < 32; ++var36) {
            BigInteger var37 = var26[var36];
            BigInteger var38 = TWO;
            int var39 = var36 * 32;
            BigInteger var40 = var38.pow(var39);
            BigInteger var41 = var37.multiply(var40);
            var35 = var35.add(var41);
         }

         BigInteger var42 = var26[32];
         var26[0] = var42;
         BigInteger var43 = TWO;
         int var44 = var29 - 1;
         BigInteger var45 = var43.pow(var44);
         BigInteger var48 = var17.multiply(var25);
         BigInteger var49 = var45.divide(var48);
         BigInteger var50 = TWO;
         int var51 = var29 - 1;
         BigInteger var52 = var50.pow(var51).multiply(var35);
         BigInteger var55 = var17.multiply(var25);
         BigInteger var56 = TWO.pow(1024);
         BigInteger var57 = var55.multiply(var56);
         BigInteger var58 = var52.divide(var57);
         BigInteger var59 = var49.add(var58);
         BigInteger var60 = TWO;
         BigInteger var61 = var59.mod(var60);
         BigInteger var62 = ONE;
         if(var61.compareTo(var62) == 0) {
            BigInteger var63 = ONE;
            var59 = var59.add(var63);
         }

         int var64 = 0;

         while(true) {
            BigInteger var67 = var17.multiply(var25);
            BigInteger var68 = BigInteger.valueOf((long)var64);
            BigInteger var69 = var59.add(var68);
            BigInteger var70 = var67.multiply(var69);
            BigInteger var71 = ONE;
            BigInteger var72 = var70.add(var71);
            BigInteger var73 = TWO;
            BigInteger var75 = var73.pow(var29);
            if(var72.compareTo(var75) == 1) {
               break;
            }

            BigInteger var78 = TWO;
            BigInteger var81 = var17.multiply(var25);
            BigInteger var82 = BigInteger.valueOf((long)var64);
            BigInteger var83 = var59.add(var82);
            BigInteger var84 = var81.multiply(var83);
            BigInteger var88 = var78.modPow(var84, var72);
            BigInteger var89 = ONE;
            if(var88.compareTo(var89) == 0) {
               BigInteger var90 = TWO;
               BigInteger var91 = BigInteger.valueOf((long)var64);
               BigInteger var92 = var59.add(var91);
               BigInteger var95 = var17.multiply(var92);
               BigInteger var99 = var90.modPow(var95, var72);
               BigInteger var100 = ONE;
               if(var99.compareTo(var100) != 0) {
                  var5[0] = var72;
                  var5[1] = var17;
                  return;
               }
            }

            var64 += 2;
         }
      }
   }

   private BigInteger procedure_C(BigInteger var1, BigInteger var2) {
      BigInteger var3 = ONE;
      BigInteger var4 = var1.subtract(var3);
      BigInteger var5 = var4.divide(var2);
      int var6 = var1.bitLength();

      while(true) {
         BigInteger var8;
         BigInteger var9;
         do {
            SecureRandom var7 = this.init_random;
            var8 = new BigInteger(var6, var7);
            var9 = ONE;
         } while(var8.compareTo(var9) <= 0);

         if(var8.compareTo(var4) < 0) {
            BigInteger var10 = var8.modPow(var5, var1);
            BigInteger var11 = ONE;
            if(var10.compareTo(var11) != 0) {
               return var10;
            }
         }
      }
   }

   public GOST3410Parameters generateParameters() {
      BigInteger[] var1 = new BigInteger[2];
      GOST3410Parameters var9;
      if(this.typeproc == 1) {
         int var2 = this.init_random.nextInt();
         int var3 = this.init_random.nextInt();
         switch(this.size) {
         case 512:
            this.procedure_A(var2, var3, var1, 512);
            break;
         case 1024:
            this.procedure_B(var2, var3, var1);
            break;
         default:
            throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
         }

         BigInteger var5 = var1[0];
         BigInteger var6 = var1[1];
         BigInteger var7 = this.procedure_C(var5, var6);
         GOST3410ValidationParameters var8 = new GOST3410ValidationParameters(var2, var3);
         var9 = new GOST3410Parameters(var5, var6, var7, var8);
      } else {
         long var10 = this.init_random.nextLong();
         long var12 = this.init_random.nextLong();
         switch(this.size) {
         case 512:
            this.procedure_Aa(var10, var12, var1, 512);
            break;
         case 1024:
            this.procedure_Bb(var10, var12, var1);
            break;
         default:
            throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
         }

         BigInteger var16 = var1[0];
         BigInteger var17 = var1[1];
         BigInteger var18 = this.procedure_C(var16, var17);
         GOST3410ValidationParameters var19 = new GOST3410ValidationParameters(var10, var12);
         var9 = new GOST3410Parameters(var16, var17, var18, var19);
      }

      return var9;
   }

   public void init(int var1, int var2, SecureRandom var3) {
      this.size = var1;
      this.typeproc = var2;
      this.init_random = var3;
   }
}
