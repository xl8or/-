package com.htc.android.mail.util;

import android.util.Log;
import com.android.internal.util.ArrayUtils;

public class SparseLongArray {

   private long[] mKeys;
   private int mSize;
   private long[] mValues;


   public SparseLongArray() {
      this(10);
   }

   public SparseLongArray(int var1) {
      Object var2 = ArrayUtils.idealIntArraySize(var1);
      this.mKeys = (long[])var2;
      this.mValues = (long[])var2;
      this.mSize = 0;
   }

   private static int binarySearch(long[] var0, int var1, int var2, long var3) {
      int var5 = var1 + var2;
      int var6 = var1 - 1;

      while(var5 - var6 > 1) {
         int var7 = (var5 + var6) / 2;
         if(var0[var7] < var3) {
            var6 = var7;
         } else {
            var5 = var7;
         }
      }

      int var8 = var1 + var2;
      int var9;
      if(var5 == var8) {
         var9 = ~(var1 + var2);
      } else if(var0[var5] == var3) {
         var9 = var5;
      } else {
         var9 = ~var5;
      }

      return var9;
   }

   private void checkIntegrity() {
      int var1 = 1;

      while(true) {
         int var2 = this.mSize;
         if(var1 >= var2) {
            return;
         }

         long var3 = this.mKeys[var1];
         long[] var5 = this.mKeys;
         int var6 = var1 - 1;
         long var7 = var5[var6];
         if(var3 <= var7) {
            int var9 = 0;

            while(true) {
               int var10 = this.mSize;
               if(var9 >= var10) {
                  throw new RuntimeException();
               }

               StringBuilder var11 = (new StringBuilder()).append(var9).append(": ");
               long var12 = this.mKeys[var9];
               StringBuilder var14 = var11.append(var12).append(" -> ");
               long var15 = this.mValues[var9];
               String var17 = var14.append(var15).toString();
               int var18 = Log.e("FAIL", var17);
               ++var9;
            }
         }

         ++var1;
      }
   }

   public void append(long var1, long var3) {
      if(this.mSize != 0) {
         long[] var5 = this.mKeys;
         int var6 = this.mSize - 1;
         long var7 = var5[var6];
         if(var1 <= var7) {
            this.put(var1, var3);
            return;
         }
      }

      int var9 = this.mSize;
      int var10 = this.mKeys.length;
      if(var9 >= var10) {
         Object var11 = ArrayUtils.idealIntArraySize(var9 + 1);
         long[] var14 = this.mKeys;
         int var15 = this.mKeys.length;
         System.arraycopy(var14, 0, var11, 0, var15);
         long[] var16 = this.mValues;
         int var17 = this.mValues.length;
         System.arraycopy(var16, 0, var11, 0, var17);
         this.mKeys = (long[])var11;
         this.mValues = (long[])var11;
      }

      this.mKeys[var9] = var1;
      this.mValues[var9] = var3;
      int var18 = var9 + 1;
      this.mSize = var18;
   }

   public void clear() {
      this.mSize = 0;
   }

   public void delete(long var1) {
      long[] var3 = this.mKeys;
      int var4 = this.mSize;
      int var5 = binarySearch(var3, 0, var4, var1);
      if(var5 >= 0) {
         this.removeAt(var5);
      }
   }

   public long get(long var1) {
      return this.get(var1, 0L);
   }

   public long get(long var1, long var3) {
      long[] var5 = this.mKeys;
      int var6 = this.mSize;
      int var7 = binarySearch(var5, 0, var6, var1);
      long var8;
      if(var7 < 0) {
         var8 = var3;
      } else {
         var8 = this.mValues[var7];
      }

      return var8;
   }

   public int indexOfKey(long var1) {
      long[] var3 = this.mKeys;
      int var4 = this.mSize;
      return binarySearch(var3, 0, var4, var1);
   }

   public int indexOfValue(long var1) {
      int var3 = 0;

      int var5;
      while(true) {
         int var4 = this.mSize;
         if(var3 >= var4) {
            var5 = -1;
            break;
         }

         if(this.mValues[var3] == var1) {
            var5 = var3;
            break;
         }

         ++var3;
      }

      return var5;
   }

   public long keyAt(int var1) {
      return this.mKeys[var1];
   }

   public void put(long var1, long var3) {
      long[] var5 = this.mKeys;
      int var6 = this.mSize;
      int var7 = binarySearch(var5, 0, var6, var1);
      if(var7 >= 0) {
         this.mValues[var7] = var3;
      } else {
         var7 = ~var7;
         int var8 = this.mSize;
         int var9 = this.mKeys.length;
         if(var8 >= var9) {
            Object var10 = ArrayUtils.idealIntArraySize(this.mSize + 1);
            long[] var13 = this.mKeys;
            int var14 = this.mKeys.length;
            System.arraycopy(var13, 0, var10, 0, var14);
            long[] var15 = this.mValues;
            int var16 = this.mValues.length;
            System.arraycopy(var15, 0, var10, 0, var16);
            this.mKeys = (long[])var10;
            this.mValues = (long[])var10;
         }

         if(this.mSize - var7 != 0) {
            long[] var17 = this.mKeys;
            long[] var18 = this.mKeys;
            int var19 = var7 + 1;
            int var20 = this.mSize - var7;
            System.arraycopy(var17, var7, var18, var19, var20);
            long[] var21 = this.mValues;
            long[] var22 = this.mValues;
            int var23 = var7 + 1;
            int var24 = this.mSize - var7;
            System.arraycopy(var21, var7, var22, var23, var24);
         }

         this.mKeys[var7] = var1;
         this.mValues[var7] = var3;
         int var25 = this.mSize + 1;
         this.mSize = var25;
      }
   }

   public void removeAt(int var1) {
      long[] var2 = this.mKeys;
      int var3 = var1 + 1;
      long[] var4 = this.mKeys;
      int var5 = this.mSize;
      int var6 = var1 + 1;
      int var7 = var5 - var6;
      System.arraycopy(var2, var3, var4, var1, var7);
      long[] var8 = this.mValues;
      int var9 = var1 + 1;
      long[] var10 = this.mValues;
      int var11 = this.mSize;
      int var12 = var1 + 1;
      int var13 = var11 - var12;
      System.arraycopy(var8, var9, var10, var1, var13);
      int var14 = this.mSize - 1;
      this.mSize = var14;
   }

   public int size() {
      return this.mSize;
   }

   public long valueAt(int var1) {
      return this.mValues[var1];
   }
}
