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
public final class Floats {

   private Floats() {}

   public static List<Float> asList(float ... var0) {
      Object var1;
      if(var0.length == 0) {
         var1 = Collections.emptyList();
      } else {
         var1 = new Floats.FloatArrayAsList(var0);
      }

      return (List)var1;
   }

   public static int compare(float var0, float var1) {
      return Float.compare(var0, var1);
   }

   public static float[] concat(float[] ... var0) {
      int var1 = 0;
      float[][] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4].length;
         var1 += var5;
      }

      float[] var6 = new float[var1];
      int var7 = 0;
      float[][] var8 = var0;
      int var9 = var0.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         float[] var11 = var8[var10];
         int var12 = var11.length;
         System.arraycopy(var11, 0, var6, var7, var12);
         int var13 = var11.length;
         var7 += var13;
      }

      return var6;
   }

   public static boolean contains(float[] var0, float var1) {
      float[] var2 = var0;
      int var3 = var0.length;
      int var4 = 0;

      boolean var5;
      while(true) {
         if(var4 >= var3) {
            var5 = false;
            break;
         }

         if(var2[var4] == var1) {
            var5 = true;
            break;
         }

         ++var4;
      }

      return var5;
   }

   private static float[] copyOf(float[] var0, int var1) {
      float[] var2 = new float[var1];
      int var3 = Math.min(var0.length, var1);
      System.arraycopy(var0, 0, var2, 0, var3);
      return var2;
   }

   public static float[] ensureCapacity(float[] var0, int var1, int var2) {
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

   public static int hashCode(float var0) {
      return Float.valueOf(var0).hashCode();
   }

   public static int indexOf(float[] var0, float var1) {
      int var2 = var0.length;
      return indexOf(var0, var1, 0, var2);
   }

   private static int indexOf(float[] var0, float var1, int var2, int var3) {
      int var4 = var2;

      while(true) {
         if(var4 >= var3) {
            var4 = -1;
            break;
         }

         if(var0[var4] == var1) {
            break;
         }

         ++var4;
      }

      return var4;
   }

   public static int indexOf(float[] var0, float[] var1) {
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
                  float var11 = var0[var10];
                  float var12 = var1[var8];
                  if(var11 != var12) {
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

   public static String join(String var0, float ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      String var3;
      if(var1.length == 0) {
         var3 = "";
      } else {
         int var4 = var1.length * 12;
         StringBuilder var5 = new StringBuilder(var4);
         float var6 = var1[0];
         var5.append(var6);
         int var8 = 1;

         while(true) {
            int var9 = var1.length;
            if(var8 >= var9) {
               var3 = var5.toString();
               break;
            }

            StringBuilder var10 = var5.append(var0);
            float var11 = var1[var8];
            var10.append(var11);
            ++var8;
         }
      }

      return var3;
   }

   public static int lastIndexOf(float[] var0, float var1) {
      int var2 = var0.length;
      return lastIndexOf(var0, var1, 0, var2);
   }

   private static int lastIndexOf(float[] var0, float var1, int var2, int var3) {
      int var4 = var3 + -1;

      while(true) {
         if(var4 < var2) {
            var4 = -1;
            break;
         }

         if(var0[var4] == var1) {
            break;
         }

         var4 += -1;
      }

      return var4;
   }

   public static Comparator<float[]> lexicographicalComparator() {
      return Floats.LexicographicalComparator.INSTANCE;
   }

   public static float max(float ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      float var2 = var0[0];
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         float var5 = var0[var3];
         var2 = Math.max(var2, var5);
         ++var3;
      }
   }

   public static float min(float ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      float var2 = var0[0];
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         float var5 = var0[var3];
         var2 = Math.min(var2, var5);
         ++var3;
      }
   }

   public static float[] toArray(Collection<Float> var0) {
      float[] var1;
      if(var0 instanceof Floats.FloatArrayAsList) {
         var1 = ((Floats.FloatArrayAsList)var0).toFloatArray();
      } else {
         Object[] var2 = var0.toArray();
         int var3 = var2.length;
         var1 = new float[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            float var5 = ((Float)var2[var4]).floatValue();
            var1[var4] = var5;
         }
      }

      return var1;
   }

   @GwtCompatible
   private static class FloatArrayAsList extends AbstractList<Float> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final float[] array;
      final int end;
      final int start;


      FloatArrayAsList(float[] var1) {
         int var2 = var1.length;
         this(var1, 0, var2);
      }

      FloatArrayAsList(float[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var6;
         if(var1 instanceof Float) {
            float[] var2 = this.array;
            float var3 = ((Float)var1).floatValue();
            int var4 = this.start;
            int var5 = this.end;
            if(Floats.indexOf(var2, var3, var4, var5) != -1) {
               var6 = true;
               return var6;
            }
         }

         var6 = false;
         return var6;
      }

      public boolean equals(Object var1) {
         byte var2 = 1;
         if(var1 != this) {
            if(var1 instanceof Floats.FloatArrayAsList) {
               Floats.FloatArrayAsList var3 = (Floats.FloatArrayAsList)var1;
               int var4 = this.size();
               if(var3.size() != var4) {
                  var2 = 0;
               } else {
                  for(int var5 = 0; var5 < var4; ++var5) {
                     float[] var6 = this.array;
                     int var7 = this.start + var5;
                     float var8 = var6[var7];
                     float[] var9 = var3.array;
                     int var10 = var3.start + var5;
                     float var11 = var9[var10];
                     if(var8 != var11) {
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

      public Float get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         float[] var4 = this.array;
         int var5 = this.start + var1;
         return Float.valueOf(var4[var5]);
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
            int var5 = Floats.hashCode(this.array[var2]);
            var1 = var4 + var5;
            ++var2;
         }
      }

      public int indexOf(Object var1) {
         int var8;
         if(var1 instanceof Float) {
            float[] var2 = this.array;
            float var3 = ((Float)var1).floatValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Floats.indexOf(var2, var3, var4, var5);
            if(var6 >= 0) {
               int var7 = this.start;
               var8 = var6 - var7;
               return var8;
            }
         }

         var8 = -1;
         return var8;
      }

      public boolean isEmpty() {
         return false;
      }

      public int lastIndexOf(Object var1) {
         int var8;
         if(var1 instanceof Float) {
            float[] var2 = this.array;
            float var3 = ((Float)var1).floatValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Floats.lastIndexOf(var2, var3, var4, var5);
            if(var6 >= 0) {
               int var7 = this.start;
               var8 = var6 - var7;
               return var8;
            }
         }

         var8 = -1;
         return var8;
      }

      public Float set(int var1, Float var2) {
         int var3 = this.size();
         Preconditions.checkElementIndex(var1, var3);
         float[] var5 = this.array;
         int var6 = this.start + var1;
         float var7 = var5[var6];
         float[] var8 = this.array;
         int var9 = this.start + var1;
         float var10 = var2.floatValue();
         var8[var9] = var10;
         return Float.valueOf(var7);
      }

      public int size() {
         int var1 = this.end;
         int var2 = this.start;
         return var1 - var2;
      }

      public List<Float> subList(int var1, int var2) {
         int var3 = this.size();
         Preconditions.checkPositionIndexes(var1, var2, var3);
         Object var4;
         if(var1 == var2) {
            var4 = Collections.emptyList();
         } else {
            float[] var5 = this.array;
            int var6 = this.start + var1;
            int var7 = this.start + var2;
            var4 = new Floats.FloatArrayAsList(var5, var6, var7);
         }

         return (List)var4;
      }

      float[] toFloatArray() {
         int var1 = this.size();
         float[] var2 = new float[var1];
         float[] var3 = this.array;
         int var4 = this.start;
         System.arraycopy(var3, var4, var2, 0, var1);
         return var2;
      }

      public String toString() {
         int var1 = this.size() * 12;
         StringBuilder var2 = new StringBuilder(var1);
         StringBuilder var3 = var2.append('[');
         float[] var4 = this.array;
         int var5 = this.start;
         float var6 = var4[var5];
         var3.append(var6);
         int var8 = this.start + 1;

         while(true) {
            int var9 = this.end;
            if(var8 >= var9) {
               return var2.append(']').toString();
            }

            StringBuilder var10 = var2.append(", ");
            float var11 = this.array[var8];
            var10.append(var11);
            ++var8;
         }
      }
   }

   private static enum LexicographicalComparator implements Comparator<float[]> {

      // $FF: synthetic field
      private static final Floats.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Floats.LexicographicalComparator[] var0 = new Floats.LexicographicalComparator[1];
         Floats.LexicographicalComparator var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private LexicographicalComparator(String var1, int var2) {}

      public int compare(float[] var1, float[] var2) {
         int var3 = var1.length;
         int var4 = var2.length;
         int var5 = Math.min(var3, var4);
         int var6 = 0;

         int var9;
         while(true) {
            if(var6 >= var5) {
               int var10 = var1.length;
               int var11 = var2.length;
               var9 = var10 - var11;
               break;
            }

            float var7 = var1[var6];
            float var8 = var2[var6];
            var9 = Float.compare(var7, var8);
            if(var9 != 0) {
               break;
            }

            ++var6;
         }

         return var9;
      }
   }
}
