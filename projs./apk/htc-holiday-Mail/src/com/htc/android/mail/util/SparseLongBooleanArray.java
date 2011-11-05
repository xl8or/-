package com.htc.android.mail.util;

import android.util.Log;
import com.android.internal.util.ArrayUtils;

public class SparseLongBooleanArray {

   private long[] mKeys;
   private int mSize;
   private boolean[] mValues;


   public SparseLongBooleanArray() {
      this(10);
   }

   public SparseLongBooleanArray(int var1) {
      Object var2 = ArrayUtils.idealIntArraySize(var1);
      this.mKeys = (long[])var2;
      boolean[] var4 = new boolean[var2];
      this.mValues = var4;
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
               boolean var15 = this.mValues[var9];
               String var16 = var14.append(var15).toString();
               int var17 = Log.e("FAIL", var16);
               ++var9;
            }
         }

         ++var1;
      }
   }

   public void append(long var1, boolean var3) {
      if(this.mSize != 0) {
         long[] var4 = this.mKeys;
         int var5 = this.mSize - 1;
         long var6 = var4[var5];
         if(var1 <= var6) {
            this.put(var1, var3);
            return;
         }
      }

      int var8 = this.mSize;
      int var9 = this.mKeys.length;
      if(var8 >= var9) {
         Object var10 = ArrayUtils.idealIntArraySize(var8 + 1);
         boolean[] var12 = new boolean[var10];
         long[] var13 = this.mKeys;
         int var14 = this.mKeys.length;
         System.arraycopy(var13, 0, var10, 0, var14);
         boolean[] var15 = this.mValues;
         int var16 = this.mValues.length;
         System.arraycopy(var15, 0, var12, 0, var16);
         this.mKeys = (long[])var10;
         this.mValues = var12;
      }

      this.mKeys[var8] = var1;
      this.mValues[var8] = var3;
      int var17 = var8 + 1;
      this.mSize = var17;
   }

   public void clear() {
      this.mSize = 0;
   }

   public void delete(long var1) {
      long[] var3 = this.mKeys;
      int var4 = this.mSize;
      int var5 = binarySearch(var3, 0, var4, var1);
      if(var5 >= 0) {
         long[] var6 = this.mKeys;
         int var7 = var5 + 1;
         long[] var8 = this.mKeys;
         int var9 = this.mSize;
         int var10 = var5 + 1;
         int var11 = var9 - var10;
         System.arraycopy(var6, var7, var8, var5, var11);
         boolean[] var12 = this.mValues;
         int var13 = var5 + 1;
         boolean[] var14 = this.mValues;
         int var15 = this.mSize;
         int var16 = var5 + 1;
         int var17 = var15 - var16;
         System.arraycopy(var12, var13, var14, var5, var17);
         int var18 = this.mSize - 1;
         this.mSize = var18;
      }
   }

   public boolean get(long var1) {
      return this.get(var1, (boolean)0);
   }

   public boolean get(long var1, boolean var3) {
      long[] var4 = this.mKeys;
      int var5 = this.mSize;
      int var6 = binarySearch(var4, 0, var5, var1);
      boolean var7;
      if(var6 < 0) {
         var7 = var3;
      } else {
         var7 = this.mValues[var6];
      }

      return var7;
   }

   public int indexOfKey(long var1) {
      long[] var3 = this.mKeys;
      int var4 = this.mSize;
      return binarySearch(var3, 0, var4, var1);
   }

   public int indexOfValue(boolean var1) {
      int var2 = 0;

      int var4;
      while(true) {
         int var3 = this.mSize;
         if(var2 >= var3) {
            var4 = -1;
            break;
         }

         if(this.mValues[var2] == var1) {
            var4 = var2;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public long keyAt(int var1) {
      return this.mKeys[var1];
   }

   public void put(long var1, boolean var3) {
      long[] var4 = this.mKeys;
      int var5 = this.mSize;
      int var6 = binarySearch(var4, 0, var5, var1);
      if(var6 >= 0) {
         this.mValues[var6] = var3;
      } else {
         var6 = ~var6;
         int var7 = this.mSize;
         int var8 = this.mKeys.length;
         if(var7 >= var8) {
            Object var9 = ArrayUtils.idealIntArraySize(this.mSize + 1);
            boolean[] var11 = new boolean[var9];
            long[] var12 = this.mKeys;
            int var13 = this.mKeys.length;
            System.arraycopy(var12, 0, var9, 0, var13);
            boolean[] var14 = this.mValues;
            int var15 = this.mValues.length;
            System.arraycopy(var14, 0, var11, 0, var15);
            this.mKeys = (long[])var9;
            this.mValues = var11;
         }

         if(this.mSize - var6 != 0) {
            long[] var16 = this.mKeys;
            long[] var17 = this.mKeys;
            int var18 = var6 + 1;
            int var19 = this.mSize - var6;
            System.arraycopy(var16, var6, var17, var18, var19);
            boolean[] var20 = this.mValues;
            boolean[] var21 = this.mValues;
            int var22 = var6 + 1;
            int var23 = this.mSize - var6;
            System.arraycopy(var20, var6, var21, var22, var23);
         }

         this.mKeys[var6] = var1;
         this.mValues[var6] = var3;
         int var24 = this.mSize + 1;
         this.mSize = var24;
      }
   }

   public int size() {
      return this.mSize;
   }

   public boolean valueAt(int var1) {
      return this.mValues[var1];
   }
}
