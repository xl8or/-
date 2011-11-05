package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECFieldElement;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.SimpleBigDecimal;
import myorg.bouncycastle.math.ec.ZTauElement;

class Tnaf {

   private static final BigInteger MINUS_ONE = ECConstants.ONE.negate();
   private static final BigInteger MINUS_THREE = ECConstants.THREE.negate();
   private static final BigInteger MINUS_TWO = ECConstants.TWO.negate();
   public static final byte POW_2_WIDTH = 16;
   public static final byte WIDTH = 4;
   public static final ZTauElement[] alpha0;
   public static final byte[][] alpha0Tnaf;
   public static final ZTauElement[] alpha1;
   public static final byte[][] alpha1Tnaf;


   static {
      ZTauElement[] var0 = new ZTauElement[9];
      var0[0] = false;
      BigInteger var1 = ECConstants.ONE;
      BigInteger var2 = ECConstants.ZERO;
      ZTauElement var3 = new ZTauElement(var1, var2);
      var0[1] = var3;
      var0[2] = false;
      BigInteger var4 = MINUS_THREE;
      BigInteger var5 = MINUS_ONE;
      ZTauElement var6 = new ZTauElement(var4, var5);
      var0[3] = var6;
      var0[4] = false;
      BigInteger var7 = MINUS_ONE;
      BigInteger var8 = MINUS_ONE;
      ZTauElement var9 = new ZTauElement(var7, var8);
      var0[5] = var9;
      var0[6] = false;
      BigInteger var10 = ECConstants.ONE;
      BigInteger var11 = MINUS_ONE;
      ZTauElement var12 = new ZTauElement(var10, var11);
      var0[7] = var12;
      var0[8] = false;
      alpha0 = var0;
      byte[] var13 = new byte[8];
      var13[0] = 0;
      byte[] var14 = new byte[]{(byte)1};
      var13[1] = (byte)var14;
      var13[2] = 0;
      byte[] var15 = new byte[]{(byte)255, (byte)0, (byte)1};
      var13[3] = (byte)var15;
      var13[4] = 0;
      byte[] var16 = new byte[]{(byte)1, (byte)0, (byte)1};
      var13[5] = (byte)var16;
      var13[6] = 0;
      byte[] var17 = new byte[]{(byte)255, (byte)0, (byte)0, (byte)1};
      var13[7] = (byte)var17;
      alpha0Tnaf = (byte[][])var13;
      ZTauElement[] var18 = new ZTauElement[9];
      var18[0] = false;
      BigInteger var19 = ECConstants.ONE;
      BigInteger var20 = ECConstants.ZERO;
      ZTauElement var21 = new ZTauElement(var19, var20);
      var18[1] = var21;
      var18[2] = false;
      BigInteger var22 = MINUS_THREE;
      BigInteger var23 = ECConstants.ONE;
      ZTauElement var24 = new ZTauElement(var22, var23);
      var18[3] = var24;
      var18[4] = false;
      BigInteger var25 = MINUS_ONE;
      BigInteger var26 = ECConstants.ONE;
      ZTauElement var27 = new ZTauElement(var25, var26);
      var18[5] = var27;
      var18[6] = false;
      BigInteger var28 = ECConstants.ONE;
      BigInteger var29 = ECConstants.ONE;
      ZTauElement var30 = new ZTauElement(var28, var29);
      var18[7] = var30;
      var18[8] = false;
      alpha1 = var18;
      byte[] var31 = new byte[8];
      var31[0] = 0;
      byte[] var32 = new byte[]{(byte)1};
      var31[1] = (byte)var32;
      var31[2] = 0;
      byte[] var33 = new byte[]{(byte)255, (byte)0, (byte)1};
      var31[3] = (byte)var33;
      var31[4] = 0;
      byte[] var34 = new byte[]{(byte)1, (byte)0, (byte)1};
      var31[5] = (byte)var34;
      var31[6] = 0;
      byte[] var35 = new byte[]{(byte)255, (byte)0, (byte)0, (byte)255};
      var31[7] = (byte)var35;
      alpha1Tnaf = (byte[][])var31;
   }

   Tnaf() {}

   public static SimpleBigDecimal approximateDivisionByN(BigInteger var0, BigInteger var1, BigInteger var2, byte var3, int var4, int var5) {
      int var6 = (var4 + 5) / 2 + var5;
      int var7 = var4 - var6 - 2 + var3;
      BigInteger var8 = var0.shiftRight(var7);
      BigInteger var9 = var1.multiply(var8);
      BigInteger var10 = var9.shiftRight(var4);
      BigInteger var11 = var2.multiply(var10);
      BigInteger var12 = var9.add(var11);
      int var13 = var6 - var5;
      BigInteger var14 = var12.shiftRight(var13);
      int var15 = var6 - var5 - 1;
      if(var12.testBit(var15)) {
         BigInteger var16 = ECConstants.ONE;
         var14 = var14.add(var16);
      }

      return new SimpleBigDecimal(var14, var5);
   }

   public static BigInteger[] getLucas(byte var0, int var1, boolean var2) {
      if(var0 != 1 && var0 != -1) {
         throw new IllegalArgumentException("mu must be 1 or -1");
      } else {
         BigInteger var3;
         BigInteger var4;
         if(var2) {
            var3 = ECConstants.TWO;
            var4 = BigInteger.valueOf((long)var0);
         } else {
            var3 = ECConstants.ZERO;
            var4 = ECConstants.ONE;
         }

         for(int var5 = 1; var5 < var1; ++var5) {
            BigInteger var6;
            if(var0 == 1) {
               var6 = var4;
            } else {
               var6 = var4.negate();
            }

            BigInteger var7 = var3.shiftLeft(1);
            BigInteger var8 = var6.subtract(var7);
            var3 = var4;
            var4 = var8;
         }

         BigInteger[] var9 = new BigInteger[]{var3, var4};
         return var9;
      }
   }

   public static byte getMu(ECCurve.F2m var0) {
      BigInteger var1 = var0.getA().toBigInteger();
      BigInteger var2 = ECConstants.ZERO;
      byte var3;
      if(var1.equals(var2)) {
         var3 = -1;
      } else {
         BigInteger var4 = ECConstants.ONE;
         if(!var1.equals(var4)) {
            throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
         }

         var3 = 1;
      }

      return var3;
   }

   public static ECPoint.F2m[] getPreComp(ECPoint.F2m var0, byte var1) {
      ECPoint.F2m[] var2 = new ECPoint.F2m[16];
      var2[1] = var0;
      byte[][] var3;
      if(var1 == 0) {
         var3 = alpha0Tnaf;
      } else {
         var3 = alpha1Tnaf;
      }

      int var4 = var3.length;

      for(int var5 = 3; var5 < var4; var5 += 2) {
         byte[] var6 = var3[var5];
         ECPoint.F2m var7 = multiplyFromTnaf(var0, var6);
         var2[var5] = var7;
      }

      return var2;
   }

   public static BigInteger[] getSi(ECCurve.F2m var0) {
      if(!var0.isKoblitz()) {
         throw new IllegalArgumentException("si is defined for Koblitz curves only");
      } else {
         int var1 = var0.getM();
         int var2 = var0.getA().toBigInteger().intValue();
         byte var3 = var0.getMu();
         int var4 = var0.getH().intValue();
         int var5 = var1 + 3 - var2;
         BigInteger[] var6 = getLucas(var3, var5, (boolean)0);
         BigInteger var9;
         BigInteger var12;
         if(var3 == 1) {
            BigInteger var7 = ECConstants.ONE;
            BigInteger var8 = var6[1];
            var9 = var7.subtract(var8);
            BigInteger var10 = ECConstants.ONE;
            BigInteger var11 = var6[0];
            var12 = var10.subtract(var11);
         } else {
            if(var3 != -1) {
               throw new IllegalArgumentException("mu must be 1 or -1");
            }

            BigInteger var16 = ECConstants.ONE;
            BigInteger var17 = var6[1];
            var9 = var16.add(var17);
            BigInteger var18 = ECConstants.ONE;
            BigInteger var19 = var6[0];
            var12 = var18.add(var19);
         }

         BigInteger[] var13 = new BigInteger[2];
         if(var4 == 2) {
            BigInteger var14 = var9.shiftRight(1);
            var13[0] = var14;
            BigInteger var15 = var12.shiftRight(1).negate();
            var13[1] = var15;
         } else {
            if(var4 != 4) {
               throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
            }

            BigInteger var20 = var9.shiftRight(2);
            var13[0] = var20;
            BigInteger var21 = var12.shiftRight(2).negate();
            var13[1] = var21;
         }

         return var13;
      }
   }

   public static BigInteger getTw(byte var0, int var1) {
      BigInteger var2;
      if(var1 == 4) {
         if(var0 == 1) {
            var2 = BigInteger.valueOf(6L);
         } else {
            var2 = BigInteger.valueOf(10L);
         }
      } else {
         BigInteger[] var3 = getLucas(var0, var1, (boolean)0);
         BigInteger var4 = ECConstants.ZERO.setBit(var1);
         BigInteger var5 = var3[1].modInverse(var4);
         BigInteger var6 = ECConstants.TWO;
         BigInteger var7 = var3[0];
         var2 = var6.multiply(var7).multiply(var5).mod(var4);
      }

      return var2;
   }

   public static ECPoint.F2m multiplyFromTnaf(ECPoint.F2m var0, byte[] var1) {
      ECPoint.F2m var2 = (ECPoint.F2m)((ECCurve.F2m)var0.getCurve()).getInfinity();

      for(int var3 = var1.length - 1; var3 >= 0; var3 += -1) {
         var2 = tau(var2);
         if(var1[var3] == 1) {
            var2 = var2.addSimple(var0);
         } else if(var1[var3] == -1) {
            var2 = var2.subtractSimple(var0);
         }
      }

      return var2;
   }

   public static ECPoint.F2m multiplyRTnaf(ECPoint.F2m var0, BigInteger var1) {
      ECCurve.F2m var2 = (ECCurve.F2m)var0.getCurve();
      int var3 = var2.getM();
      byte var4 = (byte)var2.getA().toBigInteger().intValue();
      byte var5 = var2.getMu();
      BigInteger[] var6 = var2.getSi();
      ZTauElement var7 = partModReduction(var1, var3, var4, var6, var5, (byte)10);
      return multiplyTnaf(var0, var7);
   }

   public static ECPoint.F2m multiplyTnaf(ECPoint.F2m var0, ZTauElement var1) {
      byte[] var2 = tauAdicNaf(((ECCurve.F2m)var0.getCurve()).getMu(), var1);
      return multiplyFromTnaf(var0, var2);
   }

   public static BigInteger norm(byte var0, ZTauElement var1) {
      BigInteger var2 = var1.u;
      BigInteger var3 = var1.u;
      BigInteger var4 = var2.multiply(var3);
      BigInteger var5 = var1.u;
      BigInteger var6 = var1.v;
      BigInteger var7 = var5.multiply(var6);
      BigInteger var8 = var1.v;
      BigInteger var9 = var1.v;
      BigInteger var10 = var8.multiply(var9).shiftLeft(1);
      BigInteger var11;
      if(var0 == 1) {
         var11 = var4.add(var7).add(var10);
      } else {
         if(var0 != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
         }

         var11 = var4.subtract(var7).add(var10);
      }

      return var11;
   }

   public static SimpleBigDecimal norm(byte var0, SimpleBigDecimal var1, SimpleBigDecimal var2) {
      SimpleBigDecimal var3 = var1.multiply(var1);
      SimpleBigDecimal var4 = var1.multiply(var2);
      SimpleBigDecimal var5 = var2.multiply(var2).shiftLeft(1);
      SimpleBigDecimal var6;
      if(var0 == 1) {
         var6 = var3.add(var4).add(var5);
      } else {
         if(var0 != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
         }

         var6 = var3.subtract(var4).add(var5);
      }

      return var6;
   }

   public static ZTauElement partModReduction(BigInteger var0, int var1, byte var2, BigInteger[] var3, byte var4, byte var5) {
      BigInteger var8;
      if(var4 == 1) {
         BigInteger var6 = var3[0];
         BigInteger var7 = var3[1];
         var8 = var6.add(var7);
      } else {
         BigInteger var39 = var3[0];
         BigInteger var40 = var3[1];
         var8 = var39.subtract(var40);
      }

      BigInteger var9 = getLucas(var4, var1, (boolean)1)[1];
      BigInteger var10 = var3[0];
      SimpleBigDecimal var15 = approximateDivisionByN(var0, var10, var9, var2, var1, var5);
      BigInteger var16 = var3[1];
      SimpleBigDecimal var21 = approximateDivisionByN(var0, var16, var9, var2, var1, var5);
      ZTauElement var22 = round(var15, var21, var4);
      BigInteger var23 = var22.u;
      BigInteger var24 = var8.multiply(var23);
      BigInteger var25 = var0.subtract(var24);
      BigInteger var26 = BigInteger.valueOf(2L);
      BigInteger var27 = var3[1];
      BigInteger var28 = var26.multiply(var27);
      BigInteger var29 = var22.v;
      BigInteger var30 = var28.multiply(var29);
      BigInteger var31 = var25.subtract(var30);
      BigInteger var32 = var3[1];
      BigInteger var33 = var22.u;
      BigInteger var34 = var32.multiply(var33);
      BigInteger var35 = var3[0];
      BigInteger var36 = var22.v;
      BigInteger var37 = var35.multiply(var36);
      BigInteger var38 = var34.subtract(var37);
      return new ZTauElement(var31, var38);
   }

   public static ZTauElement round(SimpleBigDecimal var0, SimpleBigDecimal var1, byte var2) {
      int var3 = var0.getScale();
      if(var1.getScale() != var3) {
         throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
      } else if(var2 != 1 && var2 != -1) {
         throw new IllegalArgumentException("mu must be 1 or -1");
      } else {
         BigInteger var4 = var0.round();
         BigInteger var5 = var1.round();
         SimpleBigDecimal var6 = var0.subtract(var4);
         SimpleBigDecimal var7 = var1.subtract(var5);
         SimpleBigDecimal var8 = var6.add(var6);
         SimpleBigDecimal var9;
         if(var2 == 1) {
            var9 = var8.add(var7);
         } else {
            var9 = var8.subtract(var7);
         }

         SimpleBigDecimal var10 = var7.add(var7).add(var7);
         SimpleBigDecimal var11 = var10.add(var7);
         SimpleBigDecimal var12;
         SimpleBigDecimal var13;
         if(var2 == 1) {
            var12 = var6.subtract(var10);
            var13 = var6.add(var11);
         } else {
            var12 = var6.add(var10);
            var13 = var6.subtract(var11);
         }

         byte var27 = 0;
         byte var14 = 0;
         BigInteger var15 = ECConstants.ONE;
         if(var9.compareTo(var15) >= 0) {
            BigInteger var16 = MINUS_ONE;
            if(var12.compareTo(var16) < 0) {
               var14 = var2;
            } else {
               var27 = 1;
            }
         } else {
            BigInteger var25 = ECConstants.TWO;
            if(var13.compareTo(var25) >= 0) {
               var14 = var2;
            }
         }

         BigInteger var17 = MINUS_ONE;
         byte var19;
         byte var20;
         if(var9.compareTo(var17) < 0) {
            BigInteger var18 = ECConstants.ONE;
            if(var12.compareTo(var18) >= 0) {
               var19 = (byte)(-var2);
               var20 = var27;
            } else {
               var20 = -1;
               var19 = var14;
            }
         } else {
            BigInteger var26 = MINUS_TWO;
            if(var13.compareTo(var26) < 0) {
               var19 = (byte)(-var2);
               var20 = var27;
            } else {
               var19 = var14;
               var20 = var27;
            }
         }

         BigInteger var21 = BigInteger.valueOf((long)var20);
         BigInteger var22 = var4.add(var21);
         BigInteger var23 = BigInteger.valueOf((long)var19);
         BigInteger var24 = var5.add(var23);
         return new ZTauElement(var22, var24);
      }
   }

   public static ECPoint.F2m tau(ECPoint.F2m var0) {
      ECPoint.F2m var1;
      if(var0.isInfinity()) {
         var1 = var0;
      } else {
         ECFieldElement var2 = var0.getX();
         ECFieldElement var3 = var0.getY();
         ECCurve var4 = var0.getCurve();
         ECFieldElement var5 = var2.square();
         ECFieldElement var6 = var3.square();
         boolean var7 = var0.isCompressed();
         var1 = new ECPoint.F2m(var4, var5, var6, var7);
      }

      return var1;
   }

   public static byte[] tauAdicNaf(byte var0, ZTauElement var1) {
      if(var0 != 1 && var0 != -1) {
         throw new IllegalArgumentException("mu must be 1 or -1");
      } else {
         int var2 = norm(var0, var1).bitLength();
         int var3;
         if(var2 > 30) {
            var3 = var2 + 4;
         } else {
            var3 = 34;
         }

         byte[] var4 = new byte[var3];
         BigInteger var5 = var1.u;
         BigInteger var6 = var1.v;
         int var7 = 0;
         byte var8 = 0;
         BigInteger var9 = var5;
         BigInteger var10 = var6;

         while(true) {
            BigInteger var11 = ECConstants.ZERO;
            if(var9.equals(var11)) {
               BigInteger var12 = ECConstants.ZERO;
               if(var10.equals(var12)) {
                  int var25 = var8 + 1;
                  byte[] var26 = new byte[var25];
                  System.arraycopy(var4, 0, var26, 0, var25);
                  return var26;
               }
            }

            if(var9.testBit(0)) {
               BigInteger var13 = ECConstants.TWO;
               BigInteger var14 = var10.shiftLeft(1);
               BigInteger var15 = var9.subtract(var14);
               BigInteger var16 = ECConstants.FOUR;
               BigInteger var17 = var15.mod(var16);
               byte var18 = (byte)var13.subtract(var17).intValue();
               var4[var7] = var18;
               if(var4[var7] == 1) {
                  var9 = var9.clearBit(0);
               } else {
                  BigInteger var23 = ECConstants.ONE;
                  var9 = var9.add(var23);
               }
            } else {
               var4[var7] = 0;
            }

            BigInteger var21 = var9.shiftRight(1);
            if(var0 == 1) {
               var10.add(var21);
            } else {
               var10.subtract(var21);
            }

            var10 = var9.shiftRight(1).negate();
            ++var7;
         }
      }
   }

   public static byte[] tauAdicWNaf(byte var0, ZTauElement var1, byte var2, BigInteger var3, BigInteger var4, ZTauElement[] var5) {
      if(var0 != 1 && var0 != -1) {
         throw new IllegalArgumentException("mu must be 1 or -1");
      } else {
         int var6 = norm(var0, var1).bitLength();
         int var8;
         if(var6 > 30) {
            int var7 = var6 + 4;
            var8 = var2 + var7;
         } else {
            var8 = var2 + 34;
         }

         byte[] var9 = new byte[var8];
         BigInteger var10 = var3.shiftRight(1);
         BigInteger var11 = var1.u;
         BigInteger var12 = var1.v;
         int var13 = 0;

         while(true) {
            BigInteger var14 = ECConstants.ZERO;
            if(var11.equals(var14)) {
               BigInteger var15 = ECConstants.ZERO;
               if(var12.equals(var15)) {
                  return var9;
               }
            }

            if(var11.testBit(0)) {
               BigInteger var16 = var12.multiply(var4);
               BigInteger var17 = var11.add(var16).mod(var3);
               byte var18;
               if(var17.compareTo(var10) >= 0) {
                  var18 = (byte)var17.subtract(var3).intValue();
               } else {
                  var18 = (byte)var17.intValue();
               }

               var9[var13] = var18;
               boolean var19 = true;
               if(var18 < 0) {
                  var19 = false;
                  var18 = (byte)(-var18);
               }

               if(var19) {
                  BigInteger var20 = var5[var18].u;
                  var11 = var11.subtract(var20);
                  BigInteger var21 = var5[var18].v;
                  var12 = var12.subtract(var21);
               } else {
                  BigInteger var26 = var5[var18].u;
                  var11 = var11.add(var26);
                  BigInteger var27 = var5[var18].v;
                  var12 = var12.add(var27);
               }
            } else {
               var9[var13] = 0;
            }

            if(var0 == 1) {
               BigInteger var23 = var11.shiftRight(1);
               var12.add(var23);
            } else {
               BigInteger var28 = var11.shiftRight(1);
               var12.subtract(var28);
            }

            BigInteger var25 = var11.shiftRight(1).negate();
            ++var13;
         }
      }
   }
}
