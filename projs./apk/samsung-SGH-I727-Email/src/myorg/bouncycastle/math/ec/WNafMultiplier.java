package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECMultiplier;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.PreCompInfo;
import myorg.bouncycastle.math.ec.WNafPreCompInfo;

class WNafMultiplier implements ECMultiplier {

   WNafMultiplier() {}

   public ECPoint multiply(ECPoint var1, BigInteger var2, PreCompInfo var3) {
      WNafPreCompInfo var4;
      if(var3 != null && var3 instanceof WNafPreCompInfo) {
         var4 = (WNafPreCompInfo)var3;
      } else {
         var4 = new WNafPreCompInfo();
      }

      int var5 = var2.bitLength();
      byte var7 = 13;
      byte var8;
      byte var9;
      if(var5 < var7) {
         var8 = 2;
         var9 = 1;
      } else {
         byte var26 = 41;
         if(var5 < var26) {
            var8 = 3;
            var9 = 2;
         } else {
            byte var28 = 121;
            if(var5 < var28) {
               var8 = 4;
               var9 = 4;
            } else {
               short var30 = 337;
               if(var5 < var30) {
                  var8 = 5;
                  var9 = 8;
               } else {
                  short var32 = 897;
                  if(var5 < var32) {
                     var8 = 6;
                     var9 = 16;
                  } else {
                     short var34 = 2305;
                     if(var5 < var34) {
                        var8 = 7;
                        var9 = 32;
                     } else {
                        var8 = 8;
                        var9 = 127;
                     }
                  }
               }
            }
         }
      }

      int var10 = 1;
      ECPoint[] var11 = var4.getPreComp();
      ECPoint var12 = var4.getTwiceP();
      if(var11 == null) {
         var11 = new ECPoint[]{var1};
      } else {
         var10 = var11.length;
      }

      if(var12 == null) {
         var12 = var1.twice();
      }

      if(var10 < var9) {
         ECPoint[] var13 = var11;
         var11 = new ECPoint[var9];
         byte var15 = 0;
         byte var17 = 0;
         System.arraycopy(var13, var15, var11, var17, var10);

         for(int var19 = var10; var19 < var9; ++var19) {
            int var20 = var19 - 1;
            ECPoint var21 = var11[var20];
            ECPoint var24 = var12.add(var21);
            var11[var19] = var24;
         }
      }

      byte[] var38 = this.windowNaf(var8, var2);
      int var39 = var38.length;
      ECPoint var40 = var1.getCurve().getInfinity();

      for(int var41 = var39 - 1; var41 >= 0; var41 += -1) {
         var40 = var40.twice();
         if(var38[var41] != 0) {
            if(var38[var41] > 0) {
               int var42 = (var38[var41] - 1) / 2;
               ECPoint var43 = var11[var42];
               ECPoint var46 = var40.add(var43);
            } else {
               int var47 = (-var38[var41] - 1) / 2;
               ECPoint var48 = var11[var47];
               var40 = var40.subtract(var48);
            }
         }
      }

      var4.setPreComp(var11);
      var4.setTwiceP(var12);
      var1.setPreCompInfo(var4);
      return var40;
   }

   public byte[] windowNaf(byte var1, BigInteger var2) {
      byte[] var3 = new byte[var2.bitLength() + 1];
      short var4 = (short)(1 << var1);
      BigInteger var5 = BigInteger.valueOf((long)var4);
      int var6 = 0;

      int var7;
      for(var7 = 0; var2.signum() > 0; ++var6) {
         if(var2.testBit(0)) {
            BigInteger var8 = var2.mod(var5);
            int var9 = var1 - 1;
            if(var8.testBit(var9)) {
               byte var10 = (byte)(var8.intValue() - var4);
               var3[var6] = var10;
            } else {
               byte var12 = (byte)var8.intValue();
               var3[var6] = var12;
            }

            BigInteger var11 = BigInteger.valueOf((long)var3[var6]);
            var2 = var2.subtract(var11);
            var7 = var6;
         } else {
            var3[var6] = 0;
         }

         var2 = var2.shiftRight(1);
      }

      int var13 = var7 + 1;
      byte[] var14 = new byte[var13];
      System.arraycopy(var3, 0, var14, 0, var13);
      return var14;
   }
}
