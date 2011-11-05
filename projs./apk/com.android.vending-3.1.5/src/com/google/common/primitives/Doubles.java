package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

@GwtCompatible
public final class Doubles {

   private Doubles() {}

   // $FF: synthetic method
   static int access$000(double[] var0, double var1, int var3, int var4) {
      return indexOf(var0, var1, var3, var4);
   }

   // $FF: synthetic method
   static int access$100(double[] var0, double var1, int var3, int var4) {
      return lastIndexOf(var0, var1, var3, var4);
   }

   public static List<Double> asList(double ... var0) {
      Object var1;
      if(var0.length == 0) {
         var1 = Collections.emptyList();
      } else {
         var1 = new Doubles.DoubleArrayAsList(var0);
      }

      return (List)var1;
   }

   public static int compare(double var0, double var2) {
      return Double.compare(var0, var2);
   }

   public static double[] concat(double[] ... var0) {
      int var1 = 0;
      double[][] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4].length;
         var1 += var5;
      }

      double[] var6 = new double[var1];
      int var7 = 0;
      double[][] var8 = var0;
      int var9 = var0.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         double[] var11 = var8[var10];
         int var12 = var11.length;
         System.arraycopy(var11, 0, var6, var7, var12);
         int var13 = var11.length;
         var7 += var13;
      }

      return var6;
   }

   public static boolean contains(double[] var0, double var1) {
      double[] var3 = var0;
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

   private static double[] copyOf(double[] var0, int var1) {
      double[] var2 = new double[var1];
      int var3 = Math.min(var0.length, var1);
      System.arraycopy(var0, 0, var2, 0, var3);
      return var2;
   }

   public static double[] ensureCapacity(double[] var0, int var1, int var2) {
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

   public static int hashCode(double var0) {
      return Double.valueOf(var0).hashCode();
   }

   public static int indexOf(double[] var0, double var1) {
      int var3 = var0.length;
      return indexOf(var0, var1, 0, var3);
   }

   private static int indexOf(double[] var0, double var1, int var3, int var4) {
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

   public static int indexOf(double[] var0, double[] var1) {
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
                  double var11 = var0[var10];
                  double var13 = var1[var8];
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

   public static String join(String var0, double ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      String var3;
      if(var1.length == 0) {
         var3 = "";
      } else {
         int var4 = var1.length * 12;
         StringBuilder var5 = new StringBuilder(var4);
         double var6 = var1[0];
         var5.append(var6);
         int var9 = 1;

         while(true) {
            int var10 = var1.length;
            if(var9 >= var10) {
               var3 = var5.toString();
               break;
            }

            StringBuilder var11 = var5.append(var0);
            double var12 = var1[var9];
            var11.append(var12);
            ++var9;
         }
      }

      return var3;
   }

   public static int lastIndexOf(double[] var0, double var1) {
      int var3 = var0.length;
      return lastIndexOf(var0, var1, 0, var3);
   }

   private static int lastIndexOf(double[] var0, double var1, int var3, int var4) {
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

   public static Comparator<double[]> lexicographicalComparator() {
      return Doubles.LexicographicalComparator.INSTANCE;
   }

   public static double max(double ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      double var2 = var0[0];
      int var4 = 1;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            return var2;
         }

         double var6 = var0[var4];
         var2 = Math.max(var2, var6);
         ++var4;
      }
   }

   public static double min(double ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      double var2 = var0[0];
      int var4 = 1;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            return var2;
         }

         double var6 = var0[var4];
         var2 = Math.min(var2, var6);
         ++var4;
      }
   }

   public static double[] toArray(Collection<Double> var0) {
      double[] var1;
      if(var0 instanceof Doubles.DoubleArrayAsList) {
         var1 = ((Doubles.DoubleArrayAsList)var0).toDoubleArray();
      } else {
         Object[] var2 = var0.toArray();
         int var3 = var2.length;
         var1 = new double[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            double var5 = ((Double)var2[var4]).doubleValue();
            var1[var4] = var5;
         }
      }

      return var1;
   }

   @GwtCompatible
   private static class DoubleArrayAsList extends AbstractList<Double> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final double[] array;
      final int end;
      final int start;


      DoubleArrayAsList(double[] var1) {
         int var2 = var1.length;
         this(var1, 0, var2);
      }

      DoubleArrayAsList(double[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var7;
         if(var1 instanceof Double) {
            double[] var2 = this.array;
            double var3 = ((Double)var1).doubleValue();
            int var5 = this.start;
            int var6 = this.end;
            if(Doubles.access$000(var2, var3, var5, var6) != -1) {
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
            if(var1 instanceof Doubles.DoubleArrayAsList) {
               Doubles.DoubleArrayAsList var3 = (Doubles.DoubleArrayAsList)var1;
               int var4 = this.size();
               if(var3.size() != var4) {
                  var2 = 0;
               } else {
                  for(int var5 = 0; var5 < var4; ++var5) {
                     double[] var6 = this.array;
                     int var7 = this.start + var5;
                     double var8 = var6[var7];
                     double[] var10 = var3.array;
                     int var11 = var3.start + var5;
                     double var12 = var10[var11];
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

      public Double get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         double[] var4 = this.array;
         int var5 = this.start + var1;
         return Double.valueOf(var4[var5]);
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
            int var5 = Doubles.hashCode(this.array[var2]);
            var1 = var4 + var5;
            ++var2;
         }
      }

      public int indexOf(Object var1) {
         int var9;
         if(var1 instanceof Double) {
            double[] var2 = this.array;
            double var3 = ((Double)var1).doubleValue();
            int var5 = this.start;
            int var6 = this.end;
            int var7 = Doubles.access$000(var2, var3, var5, var6);
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
         if(var1 instanceof Double) {
            double[] var2 = this.array;
            double var3 = ((Double)var1).doubleValue();
            int var5 = this.start;
            int var6 = this.end;
            int var7 = Doubles.access$100(var2, var3, var5, var6);
            if(var7 >= 0) {
               int var8 = this.start;
               var9 = var7 - var8;
               return var9;
            }
         }

         var9 = -1;
         return var9;
      }

      public Double set(int var1, Double var2) {
         int var3 = this.size();
         Preconditions.checkElementIndex(var1, var3);
         double[] var5 = this.array;
         int var6 = this.start + var1;
         double var7 = var5[var6];
         double[] var9 = this.array;
         int var10 = this.start + var1;
         double var11 = var2.doubleValue();
         var9[var10] = var11;
         return Double.valueOf(var7);
      }

      public int size() {
         int var1 = this.end;
         int var2 = this.start;
         return var1 - var2;
      }

      public List<Double> subList(int var1, int var2) {
         int var3 = this.size();
         Preconditions.checkPositionIndexes(var1, var2, var3);
         Object var4;
         if(var1 == var2) {
            var4 = Collections.emptyList();
         } else {
            double[] var5 = this.array;
            int var6 = this.start + var1;
            int var7 = this.start + var2;
            var4 = new Doubles.DoubleArrayAsList(var5, var6, var7);
         }

         return (List)var4;
      }

      double[] toDoubleArray() {
         int var1 = this.size();
         double[] var2 = new double[var1];
         double[] var3 = this.array;
         int var4 = this.start;
         System.arraycopy(var3, var4, var2, 0, var1);
         return var2;
      }

      public String toString() {
         int var1 = this.size() * 12;
         StringBuilder var2 = new StringBuilder(var1);
         StringBuilder var3 = var2.append('[');
         double[] var4 = this.array;
         int var5 = this.start;
         double var6 = var4[var5];
         var3.append(var6);
         int var9 = this.start + 1;

         while(true) {
            int var10 = this.end;
            if(var9 >= var10) {
               return var2.append(']').toString();
            }

            StringBuilder var11 = var2.append(", ");
            double var12 = this.array[var9];
            var11.append(var12);
            ++var9;
         }
      }
   }

   private static enum LexicographicalComparator implements Comparator<double[]> {

      // $FF: synthetic field
      private static final Doubles.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Doubles.LexicographicalComparator[] var0 = new Doubles.LexicographicalComparator[1];
         Doubles.LexicographicalComparator var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private LexicographicalComparator(String var1, int var2) {}

      public int compare(double[] var1, double[] var2) {
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

            double var7 = var1[var6];
            double var9 = var2[var6];
            var11 = Doubles.compare(var7, var9);
            if(var11 != 0) {
               break;
            }

            ++var6;
         }

         return var11;
      }
   }
}
