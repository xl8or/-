package com.htc.android.mail.util;

import android.util.Log;
import com.android.internal.util.ArrayUtils;

public class SparseArray<E extends Object> {

   private static final Object DELETED = new Object();
   private boolean mGarbage;
   private long[] mKeys;
   private int mSize;
   private Object[] mValues;


   public SparseArray() {
      this(10);
   }

   public SparseArray(int var1) {
      this.mGarbage = (boolean)0;
      Object var2 = ArrayUtils.idealIntArraySize(var1);
      this.mKeys = (long[])var2;
      Object[] var4 = new Object[var2];
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
               Object var15 = this.mValues[var9];
               String var16 = var14.append(var15).toString();
               int var17 = Log.e("FAIL", var16);
               ++var9;
            }
         }

         ++var1;
      }
   }

   private void gc() {
      int var1 = this.mSize;
      int var2 = 0;
      long[] var3 = this.mKeys;
      Object[] var4 = this.mValues;

      for(int var5 = 0; var5 < var1; ++var5) {
         Object var6 = var4[var5];
         Object var7 = DELETED;
         if(var6 != var7) {
            if(var5 != var2) {
               long var8 = var3[var5];
               var3[var2] = var8;
               var4[var2] = var6;
            }

            ++var2;
         }
      }

      this.mGarbage = (boolean)0;
      this.mSize = var2;
   }

   public void append(long var1, E var3) {
      if(this.mSize != 0) {
         long[] var4 = this.mKeys;
         int var5 = this.mSize - 1;
         long var6 = var4[var5];
         if(var1 <= var6) {
            this.put(var1, var3);
            return;
         }
      }

      if(this.mGarbage) {
         int var8 = this.mSize;
         int var9 = this.mKeys.length;
         if(var8 >= var9) {
            this.gc();
         }
      }

      int var10 = this.mSize;
      int var11 = this.mKeys.length;
      if(var10 >= var11) {
         Object var12 = ArrayUtils.idealIntArraySize(var10 + 1);
         Object[] var14 = new Object[var12];
         long[] var15 = this.mKeys;
         int var16 = this.mKeys.length;
         System.arraycopy(var15, 0, var12, 0, var16);
         Object[] var17 = this.mValues;
         int var18 = this.mValues.length;
         System.arraycopy(var17, 0, var14, 0, var18);
         this.mKeys = (long[])var12;
         this.mValues = var14;
      }

      this.mKeys[var10] = var1;
      this.mValues[var10] = var3;
      int var19 = var10 + 1;
      this.mSize = var19;
   }

   public void clear() {
      int var1 = this.mSize;
      Object[] var2 = this.mValues;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = false;
      }

      this.mSize = 0;
      this.mGarbage = (boolean)0;
   }

   public void delete(long var1) {
      long[] var3 = this.mKeys;
      int var4 = this.mSize;
      int var5 = binarySearch(var3, 0, var4, var1);
      if(var5 >= 0) {
         Object var6 = this.mValues[var5];
         Object var7 = DELETED;
         if(var6 != var7) {
            Object[] var8 = this.mValues;
            Object var9 = DELETED;
            var8[var5] = var9;
            this.mGarbage = (boolean)1;
         }
      }
   }

   public E get(long var1) {
      return this.get(var1, (Object)null);
   }

   public E get(long var1, E var3) {
      long[] var4 = this.mKeys;
      int var5 = this.mSize;
      int var6 = binarySearch(var4, 0, var5, var1);
      Object var9;
      if(var6 >= 0) {
         Object var7 = this.mValues[var6];
         Object var8 = DELETED;
         if(var7 != var8) {
            var9 = this.mValues[var6];
            return var9;
         }
      }

      var9 = var3;
      return var9;
   }

   public int indexOfKey(long var1) {
      if(this.mGarbage) {
         this.gc();
      }

      long[] var3 = this.mKeys;
      int var4 = this.mSize;
      return binarySearch(var3, 0, var4, var1);
   }

   public int indexOfValue(E var1) {
      if(this.mGarbage) {
         this.gc();
      }

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
      if(this.mGarbage) {
         this.gc();
      }

      return this.mKeys[var1];
   }

   public void put(long var1, E var3) {
      long[] var4 = this.mKeys;
      int var5 = this.mSize;
      int var6 = binarySearch(var4, 0, var5, var1);
      if(var6 >= 0) {
         this.mValues[var6] = var3;
      } else {
         var6 = ~var6;
         int var7 = this.mSize;
         if(var6 < var7) {
            Object var8 = this.mValues[var6];
            Object var9 = DELETED;
            if(var8 == var9) {
               this.mKeys[var6] = var1;
               this.mValues[var6] = var3;
               return;
            }
         }

         if(this.mGarbage) {
            int var10 = this.mSize;
            int var11 = this.mKeys.length;
            if(var10 >= var11) {
               this.gc();
               long[] var12 = this.mKeys;
               int var13 = this.mSize;
               var6 = ~binarySearch(var12, 0, var13, var1);
            }
         }

         int var14 = this.mSize;
         int var15 = this.mKeys.length;
         if(var14 >= var15) {
            Object var16 = ArrayUtils.idealIntArraySize(this.mSize + 1);
            Object[] var18 = new Object[var16];
            long[] var19 = this.mKeys;
            int var20 = this.mKeys.length;
            System.arraycopy(var19, 0, var16, 0, var20);
            Object[] var21 = this.mValues;
            int var22 = this.mValues.length;
            System.arraycopy(var21, 0, var18, 0, var22);
            this.mKeys = (long[])var16;
            this.mValues = var18;
         }

         if(this.mSize - var6 != 0) {
            long[] var23 = this.mKeys;
            long[] var24 = this.mKeys;
            int var25 = var6 + 1;
            int var26 = this.mSize - var6;
            System.arraycopy(var23, var6, var24, var25, var26);
            Object[] var27 = this.mValues;
            Object[] var28 = this.mValues;
            int var29 = var6 + 1;
            int var30 = this.mSize - var6;
            System.arraycopy(var27, var6, var28, var29, var30);
         }

         this.mKeys[var6] = var1;
         this.mValues[var6] = var3;
         int var31 = this.mSize + 1;
         this.mSize = var31;
      }
   }

   public void remove(long var1) {
      this.delete(var1);
   }

   public void setValueAt(int var1, E var2) {
      if(this.mGarbage) {
         this.gc();
      }

      this.mValues[var1] = var2;
   }

   public int size() {
      if(this.mGarbage) {
         this.gc();
      }

      return this.mSize;
   }

   public E valueAt(int var1) {
      if(this.mGarbage) {
         this.gc();
      }

      return this.mValues[var1];
   }
}
