package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECConstants;
import myorg.bouncycastle.util.Arrays;

class IntArray {

   private int[] m_ints;


   public IntArray(int var1) {
      int[] var2 = new int[var1];
      this.m_ints = var2;
   }

   public IntArray(BigInteger var1) {
      this(var1, 0);
   }

   public IntArray(BigInteger var1, int var2) {
      if(var1.signum() == -1) {
         throw new IllegalArgumentException("Only positive Integers allowed");
      } else {
         BigInteger var3 = ECConstants.ZERO;
         if(var1.equals(var3)) {
            int[] var6 = new int[]{0};
            this.m_ints = var6;
         } else {
            byte[] var8 = var1.toByteArray();
            int var9 = var8.length;
            byte var10 = 0;
            if(var8[0] == 0) {
               var9 += -1;
               var10 = 1;
            }

            int var11 = (var9 + 3) / 4;
            if(var11 < var2) {
               int[] var14 = new int[var2];
               this.m_ints = var14;
            } else {
               int[] var21 = new int[var11];
               this.m_ints = var21;
            }

            int var15 = var11 - 1;
            int var16 = var9 % 4 + var10;
            int var17 = 0;
            int var18 = var10;
            if(var10 < var16) {
               while(var18 < var16) {
                  int var19 = var17 << 8;
                  int var20 = var8[var18];
                  if(var20 < 0) {
                     var20 += 256;
                  }

                  var17 = var19 | var20;
                  ++var18;
               }

               int[] var22 = this.m_ints;
               int var23 = var15 + -1;
               var22[var15] = var17;
               var15 = var23;
            }

            while(var15 >= 0) {
               byte var29 = 0;
               int var24 = 0;

               int var25;
               for(var25 = var18; var24 < 4; var25 = var18) {
                  int var26 = var29 << 8;
                  var18 = var25 + 1;
                  int var27 = var8[var25];
                  if(var27 < 0) {
                     var27 += 256;
                  }

                  int var10000 = var26 | var27;
                  ++var24;
               }

               this.m_ints[var15] = var29;
               var15 += -1;
               var18 = var25;
            }

         }
      }
   }

   public IntArray(int[] var1) {
      this.m_ints = var1;
   }

   private int[] resizedInts(int var1) {
      int[] var2 = new int[var1];
      int var3 = this.m_ints.length;
      int var4;
      if(var3 < var1) {
         var4 = var3;
      } else {
         var4 = var1;
      }

      System.arraycopy(this.m_ints, 0, var2, 0, var4);
      return var2;
   }

   public void addShifted(IntArray var1, int var2) {
      int var3 = var1.getUsedLength();
      int var4 = var3 + var2;
      int var5 = this.m_ints.length;
      if(var4 > var5) {
         int[] var6 = this.resizedInts(var4);
         this.m_ints = var6;
      }

      for(int var7 = 0; var7 < var3; ++var7) {
         int[] var8 = this.m_ints;
         int var9 = var7 + var2;
         int var10 = var8[var9];
         int var11 = var1.m_ints[var7];
         int var12 = var10 ^ var11;
         var8[var9] = var12;
      }

   }

   public int bitLength() {
      int var1 = this.getUsedLength();
      int var2;
      if(var1 == 0) {
         var2 = 0;
      } else {
         int var3 = var1 - 1;
         int var4 = this.m_ints[var3];
         int var5 = (var3 << 5) + 1;
         if((-65536 & var4) != 0) {
            if((-16777216 & var4) != 0) {
               var5 += 24;
               var4 >>>= 24;
            } else {
               var5 += 16;
               var4 >>>= 16;
            }
         } else if(var4 > 255) {
            var5 += 8;
            var4 >>>= 8;
         }

         while(var4 != 1) {
            ++var5;
            var4 >>>= 1;
         }

         var2 = var5;
      }

      return var2;
   }

   public Object clone() {
      int[] var1 = Arrays.clone(this.m_ints);
      return new IntArray(var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof IntArray)) {
         var2 = false;
      } else {
         IntArray var3 = (IntArray)var1;
         int var4 = this.getUsedLength();
         if(var3.getUsedLength() != var4) {
            var2 = false;
         } else {
            int var5 = 0;

            while(true) {
               if(var5 >= var4) {
                  var2 = true;
                  break;
               }

               int var6 = this.m_ints[var5];
               int var7 = var3.m_ints[var5];
               if(var6 != var7) {
                  var2 = false;
                  break;
               }

               ++var5;
            }
         }
      }

      return var2;
   }

   public void flipBit(int var1) {
      int var2 = var1 >> 5;
      int var3 = var1 & 31;
      int var4 = 1 << var3;
      int[] var5 = this.m_ints;
      int var6 = var5[var2] ^ var4;
      var5[var2] = var6;
   }

   public int getLength() {
      return this.m_ints.length;
   }

   public int getUsedLength() {
      int var1 = this.m_ints.length;
      int var2;
      if(var1 < 1) {
         var2 = 0;
      } else if(this.m_ints[0] != 0) {
         int[] var3;
         do {
            var3 = this.m_ints;
            var1 += -1;
         } while(var3[var1] == 0);

         var2 = var1 + 1;
      } else {
         while(true) {
            int[] var4 = this.m_ints;
            var1 += -1;
            if(var4[var1] != 0) {
               var2 = var1 + 1;
               break;
            }

            if(var1 <= 0) {
               var2 = 0;
               break;
            }
         }
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this.getUsedLength();
      int var2 = 1;

      for(int var3 = 0; var3 < var1; ++var3) {
         int var4 = var2 * 31;
         int var5 = this.m_ints[var3];
         var2 = var4 + var5;
      }

      return var2;
   }

   public boolean isZero() {
      boolean var1;
      if(this.m_ints.length != 0 && (this.m_ints[0] != 0 || this.getUsedLength() != 0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public IntArray multiply(IntArray var1, int var2) {
      int var3 = var2 + 31 >> 5;
      if(this.m_ints.length < var3) {
         int[] var4 = this.resizedInts(var3);
         this.m_ints = var4;
      }

      int var5 = var1.getLength() + 1;
      int[] var6 = var1.resizedInts(var5);
      IntArray var7 = new IntArray(var6);
      int var8 = var2 + var2 + 31 >> 5;
      IntArray var9 = new IntArray(var8);
      int var10 = 1;

      for(int var11 = 0; var11 < 32; ++var11) {
         for(int var12 = 0; var12 < var3; ++var12) {
            if((this.m_ints[var12] & var10) != 0) {
               var9.addShifted(var7, var12);
            }
         }

         var10 <<= 1;
         var7.shiftLeft();
      }

      return var9;
   }

   public void reduce(int var1, int[] var2) {
      for(int var3 = var1 + var1 - 2; var3 >= var1; var3 += -1) {
         if(this.testBit(var3)) {
            int var4 = var3 - var1;
            this.flipBit(var4);
            this.flipBit(var3);
            int var5 = var2.length;

            while(true) {
               var5 += -1;
               if(var5 < 0) {
                  break;
               }

               int var6 = var2[var5] + var4;
               this.flipBit(var6);
            }
         }
      }

      int var7 = var1 + 31 >> 5;
      int[] var8 = this.resizedInts(var7);
      this.m_ints = var8;
   }

   public void setBit(int var1) {
      int var2 = var1 >> 5;
      int var3 = var1 & 31;
      int var4 = 1 << var3;
      int[] var5 = this.m_ints;
      int var6 = var5[var2] | var4;
      var5[var2] = var6;
   }

   public IntArray shiftLeft(int var1) {
      int var2 = this.getUsedLength();
      IntArray var3;
      if(var2 == 0) {
         var3 = this;
      } else if(var1 == 0) {
         var3 = this;
      } else {
         if(var1 > 31) {
            String var4 = "shiftLeft() for max 31 bits , " + var1 + "bit shift is not possible";
            throw new IllegalArgumentException(var4);
         }

         int[] var5 = new int[var2 + 1];
         int var6 = 32 - var1;
         int var7 = this.m_ints[0] << var1;
         var5[0] = var7;

         for(int var8 = 1; var8 < var2; ++var8) {
            int var9 = this.m_ints[var8] << var1;
            int[] var10 = this.m_ints;
            int var11 = var8 - 1;
            int var12 = var10[var11] >>> var6;
            int var13 = var9 | var12;
            var5[var8] = var13;
         }

         int[] var14 = this.m_ints;
         int var15 = var2 - 1;
         int var16 = var14[var15] >>> var6;
         var5[var2] = var16;
         var3 = new IntArray(var5);
      }

      return var3;
   }

   public void shiftLeft() {
      int var1 = this.getUsedLength();
      if(var1 != 0) {
         int[] var2 = this.m_ints;
         int var3 = var1 - 1;
         if(var2[var3] < 0) {
            ++var1;
            int var4 = this.m_ints.length;
            if(var1 > var4) {
               int var5 = this.m_ints.length + 1;
               int[] var6 = this.resizedInts(var5);
               this.m_ints = var6;
            }
         }

         boolean var7 = false;

         for(int var8 = 0; var8 < var1; ++var8) {
            boolean var9;
            if(this.m_ints[var8] < 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            int[] var10 = this.m_ints;
            int var11 = var10[var8] << 1;
            var10[var8] = var11;
            if(var7) {
               int[] var12 = this.m_ints;
               int var13 = var12[var8] | 1;
               var12[var8] = var13;
            }

            var7 = var9;
         }

      }
   }

   public IntArray square(int var1) {
      int[] var2 = new int[]{0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85};
      int var3 = var1 + 31 >> 5;
      if(this.m_ints.length < var3) {
         int[] var4 = this.resizedInts(var3);
         this.m_ints = var4;
      }

      int var5 = var3 + var3;
      IntArray var6 = new IntArray(var5);

      for(int var7 = 0; var7 < var3; ++var7) {
         int var8 = 0;

         for(int var9 = 0; var9 < 4; ++var9) {
            int var10 = var8 >>> 8;
            int var11 = this.m_ints[var7];
            int var12 = var9 * 4;
            int var13 = var11 >>> var12 & 15;
            int var14 = var2[var13] << 24;
            var8 = var10 | var14;
         }

         int[] var15 = var6.m_ints;
         int var16 = var7 + var7;
         var15[var16] = var8;
         int var17 = 0;
         int var18 = this.m_ints[var7] >>> 16;

         for(int var19 = 0; var19 < 4; ++var19) {
            int var20 = var17 >>> 8;
            int var21 = var19 * 4;
            int var22 = var18 >>> var21 & 15;
            int var23 = var2[var22] << 24;
            var17 = var20 | var23;
         }

         int[] var24 = var6.m_ints;
         int var25 = var7 + var7 + 1;
         var24[var25] = var17;
      }

      return var6;
   }

   public boolean testBit(int var1) {
      int var2 = var1 >> 5;
      int var3 = var1 & 31;
      int var4 = 1 << var3;
      boolean var5;
      if((this.m_ints[var2] & var4) != 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public BigInteger toBigInteger() {
      int var1 = this.getUsedLength();
      BigInteger var2;
      if(var1 == 0) {
         var2 = ECConstants.ZERO;
      } else {
         int[] var3 = this.m_ints;
         int var4 = var1 - 1;
         int var5 = var3[var4];
         byte[] var6 = new byte[4];
         boolean var7 = false;
         int var8 = 3;

         int var9;
         int var12;
         for(var9 = 0; var8 >= 0; var9 = var12) {
            int var10 = var8 * 8;
            byte var11 = (byte)(var5 >>> var10);
            if(!var7 && var11 == 0) {
               var12 = var9;
            } else {
               var7 = true;
               var12 = var9 + 1;
               var6[var9] = var11;
            }

            var8 += -1;
         }

         byte[] var13 = new byte[(var1 - 1) * 4 + var9];

         for(int var14 = 0; var14 < var9; ++var14) {
            byte var15 = var6[var14];
            var13[var14] = var15;
         }

         int var16 = var1 - 2;

         for(int var17 = var9; var16 >= 0; var17 = var9) {
            byte var24 = 3;

            int var22;
            for(var9 = var17; var24 >= 0; var22 = var24 + -1) {
               int var18 = var9 + 1;
               int var19 = this.m_ints[var16];
               int var20 = var24 * 8;
               byte var21 = (byte)(var19 >>> var20);
               var13[var9] = var21;
            }

            var16 += -1;
         }

         var2 = new BigInteger(1, var13);
      }

      return var2;
   }

   public String toString() {
      int var1 = this.getUsedLength();
      String var2;
      if(var1 == 0) {
         var2 = "0";
      } else {
         int[] var3 = this.m_ints;
         int var4 = var1 - 1;
         String var5 = Integer.toBinaryString(var3[var4]);
         StringBuffer var6 = new StringBuffer(var5);

         for(int var7 = var1 - 2; var7 >= 0; var7 += -1) {
            String var8 = Integer.toBinaryString(this.m_ints[var7]);

            for(int var9 = var8.length(); var9 < 8; ++var9) {
               var8 = "0" + var8;
            }

            var6.append(var8);
         }

         var2 = var6.toString();
      }

      return var2;
   }
}
