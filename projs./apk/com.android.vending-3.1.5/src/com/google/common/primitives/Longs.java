package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

@GwtCompatible
public final class Longs {

   public static final int BYTES = 8;


   private Longs() {}

   // $FF: synthetic method
   static int access$000(long[] var0, long var1, int var3, int var4) {
      return indexOf(var0, var1, var3, var4);
   }

   // $FF: synthetic method
   static int access$100(long[] var0, long var1, int var3, int var4) {
      return lastIndexOf(var0, var1, var3, var4);
   }

   public static List<Long> asList(long ... var0) {
      Object var1;
      if(var0.length == 0) {
         var1 = Collections.emptyList();
      } else {
         var1 = new Longs.LongArrayAsList(var0);
      }

      return (List)var1;
   }

   public static int compare(long var0, long var2) {
      byte var4;
      if(var0 < var2) {
         var4 = -1;
      } else if(var0 > var2) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      return var4;
   }

   public static long[] concat(long[] ... var0) {
      Object var1 = null;
      long[][] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4].length;
         var1 += var5;
      }

      Object var6 = var1;
      int var7 = 0;
      long[][] var8 = var0;
      int var9 = var0.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         long[] var11 = var8[var10];
         int var12 = var11.length;
         System.arraycopy(var11, 0, var6, var7, var12);
         int var13 = var11.length;
         var7 += var13;
      }

      return (long[])var6;
   }

   public static boolean contains(long[] var0, long var1) {
      long[] var3 = var0;
      int var4 = var0.length;
      int var5 = 0;

      boolean var6;
      while(true) {
         if(var5 >= var4) {
            var6 = false;
            break;
         }

         if(var3[var5] == var1) {
            var6 = true;
            break;
         }

         ++var5;
      }

      return var6;
   }

   private static long[] copyOf(long[] var0, int var1) {
      int var3 = Math.min(var0.length, (int)var1);
      System.arraycopy(var0, 0, var1, 0, var3);
      return (long[])var1;
   }

   public static long[] ensureCapacity(long[] var0, int var1, int var2) {
      byte var3;
      if(var1 >= 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Object[] var4 = new Object[1];
      Integer var5 = Integer.valueOf(var1);
      var4[0] = var5;
      Preconditions.checkArgument((boolean)var3, "Invalid minLength: %s", var4);
      byte var6;
      if(var2 >= 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      Object[] var7 = new Object[1];
      Integer var8 = Integer.valueOf(var2);
      var7[0] = var8;
      Preconditions.checkArgument((boolean)var6, "Invalid padding: %s", var7);
      if(var0.length < var1) {
         int var9 = var1 + var2;
         var0 = copyOf(var0, var9);
      }

      return var0;
   }

   @GwtIncompatible("doesn\'t work")
   public static long fromByteArray(byte[] var0) {
      byte var1;
      if(var0.length >= 8) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Object[] var2 = new Object[2];
      Integer var3 = Integer.valueOf(var0.length);
      var2[0] = var3;
      Integer var4 = Integer.valueOf(8);
      var2[1] = var4;
      Preconditions.checkArgument((boolean)var1, "array too small: %s < %s", var2);
      long var5 = ((long)var0[0] & 255L) << 56;
      long var7 = ((long)var0[1] & 255L) << 48 | var5;
      long var9 = ((long)var0[2] & 255L) << 40;
      long var11 = var7 | var9;
      long var13 = ((long)var0[3] & 255L) << 32;
      long var15 = var11 | var13;
      long var17 = ((long)var0[4] & 255L) << 24;
      long var19 = var15 | var17;
      long var21 = ((long)var0[5] & 255L) << 16;
      long var23 = var19 | var21;
      long var25 = ((long)var0[6] & 255L) << 8;
      long var27 = var23 | var25;
      long var29 = (long)var0[7] & 255L;
      return var27 | var29;
   }

   public static int hashCode(long var0) {
      return (int)(var0 >>> 32 ^ var0);
   }

   public static int indexOf(long[] var0, long var1) {
      int var3 = var0.length;
      return indexOf(var0, var1, 0, var3);
   }

   private static int indexOf(long[] var0, long var1, int var3, int var4) {
      int var5 = var3;

      while(true) {
         if(var5 >= var4) {
            var5 = -1;
            break;
         }

         if(var0[var5] == var1) {
            break;
         }

         ++var5;
      }

      return var5;
   }

   public static int indexOf(long[] var0, long[] var1) {
      Object var2 = Preconditions.checkNotNull(var0, "array");
      Object var3 = Preconditions.checkNotNull(var1, "target");
      int var4;
      if(var1.length == 0) {
         var4 = 0;
      } else {
         var4 = 0;

         label24:
         while(true) {
            int var5 = var0.length;
            int var6 = var1.length;
            int var7 = var5 - var6 + 1;
            if(var4 < var7) {
               int var8 = 0;

               while(true) {
                  int var9 = var1.length;
                  if(var8 >= var9) {
                     return var4;
                  }

                  int var10 = var4 + var8;
                  long var11 = var0[var10];
                  long var13 = var1[var8];
                  if(var11 != var13) {
                     ++var4;
                     continue label24;
                  }

                  ++var8;
               }
            }

            var4 = -1;
            break;
         }
      }

      return var4;
   }

   public static String join(String var0, long ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      String var3;
      if(var1.length == 0) {
         var3 = "";
      } else {
         int var4 = var1.length * 10;
         StringBuilder var5 = new StringBuilder(var4);
         long var6 = var1[0];
         var5.append(var6);
         int var9 = 1;

         while(true) {
            int var10 = var1.length;
            if(var9 >= var10) {
               var3 = var5.toString();
               break;
            }

            StringBuilder var11 = var5.append(var0);
            long var12 = var1[var9];
            var11.append(var12);
            ++var9;
         }
      }

      return var3;
   }

   public static int lastIndexOf(long[] var0, long var1) {
      int var3 = var0.length;
      return lastIndexOf(var0, var1, 0, var3);
   }

   private static int lastIndexOf(long[] var0, long var1, int var3, int var4) {
      int var5 = var4 + -1;

      while(true) {
         if(var5 < var3) {
            var5 = -1;
            break;
         }

         if(var0[var5] == var1) {
            break;
         }

         var5 += -1;
      }

      return var5;
   }

   public static Comparator<long[]> lexicographicalComparator() {
      return Longs.LexicographicalComparator.INSTANCE;
   }

   public static long max(long ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      long var2 = var0[0];
      int var4 = 1;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            return var2;
         }

         if(var0[var4] > var2) {
            var2 = var0[var4];
         }

         ++var4;
      }
   }

   public static long min(long ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      long var2 = var0[0];
      int var4 = 1;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            return var2;
         }

         if(var0[var4] < var2) {
            var2 = var0[var4];
         }

         ++var4;
      }
   }

   public static long[] toArray(Collection<Long> var0) {
      long[] var1;
      if(var0 instanceof Longs.LongArrayAsList) {
         var1 = ((Longs.LongArrayAsList)var0).toLongArray();
      } else {
         Object[] var2 = var0.toArray();
         Object var3 = var2.length;
         var1 = (long[])var3;

         for(int var4 = 0; var4 < var3; ++var4) {
            long var5 = ((Long)var2[var4]).longValue();
            var1[var4] = var5;
         }
      }

      return var1;
   }

   @GwtIncompatible("doesn\'t work")
   public static byte[] toByteArray(long var0) {
      byte[] var2 = new byte[8];
      byte var3 = (byte)((int)(var0 >> 56));
      var2[0] = var3;
      byte var4 = (byte)((int)(var0 >> 48));
      var2[1] = var4;
      byte var5 = (byte)((int)(var0 >> 40));
      var2[2] = var5;
      byte var6 = (byte)((int)(var0 >> 32));
      var2[3] = var6;
      byte var7 = (byte)((int)(var0 >> 24));
      var2[4] = var7;
      byte var8 = (byte)((int)(var0 >> 16));
      var2[5] = var8;
      byte var9 = (byte)((int)(var0 >> 8));
      var2[6] = var9;
      byte var10 = (byte)((int)var0);
      var2[7] = var10;
      return var2;
   }

   @GwtCompatible
   private static class LongArrayAsList extends AbstractList<Long> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final long[] array;
      final int end;
      final int start;


      LongArrayAsList(long[] var1) {
         int var2 = var1.length;
         this(var1, 0, var2);
      }

      LongArrayAsList(long[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var7;
         if(var1 instanceof Long) {
            long[] var2 = this.array;
            long var3 = ((Long)var1).longValue();
            int var5 = this.start;
            int var6 = this.end;
            if(Longs.access$000(var2, var3, var5, var6) != -1) {
               var7 = true;
               return var7;
            }
         }

         var7 = false;
         return var7;
      }

      public boolean equals(Object var1) {
         byte var2 = 1;
         if(var1 != this) {
            if(var1 instanceof Longs.LongArrayAsList) {
               Longs.LongArrayAsList var3 = (Longs.LongArrayAsList)var1;
               int var4 = this.size();
               if(var3.size() != var4) {
                  var2 = 0;
               } else {
                  for(int var5 = 0; var5 < var4; ++var5) {
                     long[] var6 = this.array;
                     int var7 = this.start + var5;
                     long var8 = var6[var7];
                     long[] var10 = var3.array;
                     int var11 = var3.start + var5;
                     long var12 = var10[var11];
                     if(var8 != var12) {
                        var2 = 0;
                        break;
                     }
                  }
               }
            } else {
               var2 = super.equals(var1);
            }
         }

         return (boolean)var2;
      }

      public Long get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         long[] var4 = this.array;
         int var5 = this.start + var1;
         return Long.valueOf(var4[var5]);
      }

      public int hashCode() {
         int var1 = 1;
         int var2 = this.start;

         while(true) {
            int var3 = this.end;
            if(var2 >= var3) {
               return var1;
            }

            int var4 = var1 * 31;
            int var5 = Longs.hashCode(this.array[var2]);
            var1 = var4 + var5;
            ++var2;
         }
      }

      public int indexOf(Object var1) {
         int var9;
         if(var1 instanceof Long) {
            long[] var2 = this.array;
            long var3 = ((Long)var1).longValue();
            int var5 = this.start;
            int var6 = this.end;
            int var7 = Longs.access$000(var2, var3, var5, var6);
            if(var7 >= 0) {
               int var8 = this.start;
               var9 = var7 - var8;
               return var9;
            }
         }

         var9 = -1;
         return var9;
      }

      public boolean isEmpty() {
         return false;
      }

      public int lastIndexOf(Object var1) {
         int var9;
         if(var1 instanceof Long) {
            long[] var2 = this.array;
            long var3 = ((Long)var1).longValue();
            int var5 = this.start;
            int var6 = this.end;
            int var7 = Longs.access$100(var2, var3, var5, var6);
            if(var7 >= 0) {
               int var8 = this.start;
               var9 = var7 - var8;
               return var9;
            }
         }

         var9 = -1;
         return var9;
      }

      public Long set(int var1, Long var2) {
         int var3 = this.size();
         Preconditions.checkElementIndex(var1, var3);
         long[] var5 = this.array;
         int var6 = this.start + var1;
         long var7 = var5[var6];
         long[] var9 = this.array;
         int var10 = this.start + var1;
         long var11 = var2.longValue();
         var9[var10] = var11;
         return Long.valueOf(var7);
      }

      public int size() {
         int var1 = this.end;
         int var2 = this.start;
         return var1 - var2;
      }

      public List<Long> subList(int var1, int var2) {
         int var3 = this.size();
         Preconditions.checkPositionIndexes(var1, var2, var3);
         Object var4;
         if(var1 == var2) {
            var4 = Collections.emptyList();
         } else {
            long[] var5 = this.array;
            int var6 = this.start + var1;
            int var7 = this.start + var2;
            var4 = new Longs.LongArrayAsList(var5, var6, var7);
         }

         return (List)var4;
      }

      long[] toLongArray() {
         Object var1 = this.size();
         long[] var3 = this.array;
         int var4 = this.start;
         System.arraycopy(var3, var4, var1, 0, (int)var1);
         return (long[])var1;
      }

      public String toString() {
         int var1 = this.size() * 10;
         StringBuilder var2 = new StringBuilder(var1);
         StringBuilder var3 = var2.append('[');
         long[] var4 = this.array;
         int var5 = this.start;
         long var6 = var4[var5];
         var3.append(var6);
         int var9 = this.start + 1;

         while(true) {
            int var10 = this.end;
            if(var9 >= var10) {
               return var2.append(']').toString();
            }

            StringBuilder var11 = var2.append(", ");
            long var12 = this.array[var9];
            var11.append(var12);
            ++var9;
         }
      }
   }

   private static enum LexicographicalComparator implements Comparator<long[]> {

      // $FF: synthetic field
      private static final Longs.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Longs.LexicographicalComparator[] var0 = new Longs.LexicographicalComparator[1];
         Longs.LexicographicalComparator var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private LexicographicalComparator(String var1, int var2) {}

      public int compare(long[] var1, long[] var2) {
         int var3 = var1.length;
         int var4 = var2.length;
         int var5 = Math.min(var3, var4);
         int var6 = 0;

         int var11;
         while(true) {
            if(var6 >= var5) {
               int var12 = var1.length;
               int var13 = var2.length;
               var11 = var12 - var13;
               break;
            }

            long var7 = var1[var6];
            long var9 = var2[var6];
            var11 = Longs.compare(var7, var9);
            if(var11 != 0) {
               break;
            }

            ++var6;
         }

         return var11;
      }
   }
}
