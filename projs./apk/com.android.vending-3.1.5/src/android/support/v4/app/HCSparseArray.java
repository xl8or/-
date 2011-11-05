package android.support.v4.app;

import android.util.Log;

public class HCSparseArray<E extends Object> {

   private static final Object DELETED = new Object();
   private boolean mGarbage;
   private int[] mKeys;
   private int mSize;
   private Object[] mValues;


   public HCSparseArray() {
      this(10);
   }

   public HCSparseArray(int var1) {
      this.mGarbage = (boolean)0;
      int var2 = idealIntArraySize(var1);
      int[] var3 = new int[var2];
      this.mKeys = var3;
      Object[] var4 = new Object[var2];
      this.mValues = var4;
      this.mSize = 0;
   }

   private static int binarySearch(int[] var0, int var1, int var2, int var3) {
      int var4 = var1 + var2;
      int var5 = var1 + -1;

      while(var4 - var5 > 1) {
         int var6 = (var4 + var5) / 2;
         if(var0[var6] < var3) {
            var5 = var6;
         }
      }

      int var8 = var1 + var2;
      if(var4 == var8) {
         var4 = ~(var1 + var2);
      } else if(var0[var4] != var3) {
         var4 = ~var4;
      }

      return var4;
   }

   private void checkIntegrity() {
      int var1 = 1;

      while(true) {
         int var2 = this.mSize;
         if(var1 >= var2) {
            return;
         }

         int var3 = this.mKeys[var1];
         int[] var4 = this.mKeys;
         int var5 = var1 + -1;
         int var6 = var4[var5];
         if(var3 <= var6) {
            int var7 = 0;

            while(true) {
               int var8 = this.mSize;
               if(var7 >= var8) {
                  throw new RuntimeException();
               }

               StringBuilder var9 = (new StringBuilder()).append(var7).append(": ");
               int var10 = this.mKeys[var7];
               StringBuilder var11 = var9.append(var10).append(" -> ");
               Object var12 = this.mValues[var7];
               String var13 = var11.append(var12).toString();
               int var14 = Log.e("FAIL", var13);
               ++var7;
            }
         }

         ++var1;
      }
   }

   private void gc() {
      int var1 = this.mSize;
      int var2 = 0;
      int[] var3 = this.mKeys;
      Object[] var4 = this.mValues;

      for(int var5 = 0; var5 < var1; ++var5) {
         Object var6 = var4[var5];
         Object var7 = DELETED;
         if(var6 != var7) {
            if(var5 != var2) {
               int var8 = var3[var5];
               var3[var2] = var8;
               var4[var2] = var6;
            }

            ++var2;
         }
      }

      this.mGarbage = (boolean)0;
      this.mSize = var2;
   }

   static int idealByteArraySize(int var0) {
      for(int var1 = 4; var1 < 32; ++var1) {
         int var2 = (1 << var1) + -12;
         if(var0 <= var2) {
            var0 = (1 << var1) + -12;
            break;
         }
      }

      return var0;
   }

   static int idealIntArraySize(int var0) {
      return idealByteArraySize(var0 * 4) / 4;
   }

   public void append(int var1, E var2) {
      if(this.mSize != 0) {
         int[] var3 = this.mKeys;
         int var4 = this.mSize + -1;
         int var5 = var3[var4];
         if(var1 <= var5) {
            this.put(var1, var2);
            return;
         }
      }

      if(this.mGarbage) {
         int var6 = this.mSize;
         int var7 = this.mKeys.length;
         if(var6 >= var7) {
            this.gc();
         }
      }

      int var8 = this.mSize;
      int var9 = this.mKeys.length;
      if(var8 >= var9) {
         int var10 = idealIntArraySize(var8 + 1);
         int[] var11 = new int[var10];
         Object[] var12 = new Object[var10];
         int[] var13 = this.mKeys;
         int var14 = this.mKeys.length;
         System.arraycopy(var13, 0, var11, 0, var14);
         Object[] var15 = this.mValues;
         int var16 = this.mValues.length;
         System.arraycopy(var15, 0, var12, 0, var16);
         this.mKeys = var11;
         this.mValues = var12;
      }

      this.mKeys[var8] = var1;
      this.mValues[var8] = var2;
      int var17 = var8 + 1;
      this.mSize = var17;
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

   public void delete(int var1) {
      int[] var2 = this.mKeys;
      int var3 = this.mSize;
      int var4 = binarySearch(var2, 0, var3, var1);
      if(var4 >= 0) {
         Object var5 = this.mValues[var4];
         Object var6 = DELETED;
         if(var5 != var6) {
            Object[] var7 = this.mValues;
            Object var8 = DELETED;
            var7[var4] = var8;
            this.mGarbage = (boolean)1;
         }
      }
   }

   public E get(int var1) {
      return this.get(var1, (Object)null);
   }

   public E get(int var1, E var2) {
      int[] var3 = this.mKeys;
      int var4 = this.mSize;
      int var5 = binarySearch(var3, 0, var4, var1);
      if(var5 >= 0) {
         Object var6 = this.mValues[var5];
         Object var7 = DELETED;
         if(var6 != var7) {
            var2 = this.mValues[var5];
         }
      }

      return var2;
   }

   public int indexOfKey(int var1) {
      if(this.mGarbage) {
         this.gc();
      }

      int[] var2 = this.mKeys;
      int var3 = this.mSize;
      return binarySearch(var2, 0, var3, var1);
   }

   public int indexOfValue(E var1) {
      if(this.mGarbage) {
         this.gc();
      }

      int var2 = 0;

      while(true) {
         int var3 = this.mSize;
         if(var2 >= var3) {
            var2 = -1;
            break;
         }

         if(this.mValues[var2] == var1) {
            break;
         }

         ++var2;
      }

      return var2;
   }

   public int keyAt(int var1) {
      if(this.mGarbage) {
         this.gc();
      }

      return this.mKeys[var1];
   }

   public void put(int var1, E var2) {
      int[] var3 = this.mKeys;
      int var4 = this.mSize;
      int var5 = binarySearch(var3, 0, var4, var1);
      if(var5 >= 0) {
         this.mValues[var5] = var2;
      } else {
         var5 = ~var5;
         int var6 = this.mSize;
         if(var5 < var6) {
            Object var7 = this.mValues[var5];
            Object var8 = DELETED;
            if(var7 == var8) {
               this.mKeys[var5] = var1;
               this.mValues[var5] = var2;
               return;
            }
         }

         if(this.mGarbage) {
            int var9 = this.mSize;
            int var10 = this.mKeys.length;
            if(var9 >= var10) {
               this.gc();
               int[] var11 = this.mKeys;
               int var12 = this.mSize;
               var5 = ~binarySearch(var11, 0, var12, var1);
            }
         }

         int var13 = this.mSize;
         int var14 = this.mKeys.length;
         if(var13 >= var14) {
            int var15 = idealIntArraySize(this.mSize + 1);
            int[] var16 = new int[var15];
            Object[] var17 = new Object[var15];
            int[] var18 = this.mKeys;
            int var19 = this.mKeys.length;
            System.arraycopy(var18, 0, var16, 0, var19);
            Object[] var20 = this.mValues;
            int var21 = this.mValues.length;
            System.arraycopy(var20, 0, var17, 0, var21);
            this.mKeys = var16;
            this.mValues = var17;
         }

         if(this.mSize - var5 != 0) {
            int[] var22 = this.mKeys;
            int[] var23 = this.mKeys;
            int var24 = var5 + 1;
            int var25 = this.mSize - var5;
            System.arraycopy(var22, var5, var23, var24, var25);
            Object[] var26 = this.mValues;
            Object[] var27 = this.mValues;
            int var28 = var5 + 1;
            int var29 = this.mSize - var5;
            System.arraycopy(var26, var5, var27, var28, var29);
         }

         this.mKeys[var5] = var1;
         this.mValues[var5] = var2;
         int var30 = this.mSize + 1;
         this.mSize = var30;
      }
   }

   public void remove(int var1) {
      this.delete(var1);
   }

   public void removeAt(int var1) {
      Object var2 = this.mValues[var1];
      Object var3 = DELETED;
      if(var2 != var3) {
         Object[] var4 = this.mValues;
         Object var5 = DELETED;
         var4[var1] = var5;
         this.mGarbage = (boolean)1;
      }
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
