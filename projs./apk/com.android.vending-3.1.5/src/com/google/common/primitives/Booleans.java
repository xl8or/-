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
public final class Booleans {

   private Booleans() {}

   public static List<Boolean> asList(boolean ... var0) {
      Object var1;
      if(var0.length == 0) {
         var1 = Collections.emptyList();
      } else {
         var1 = new Booleans.BooleanArrayAsList(var0);
      }

      return (List)var1;
   }

   public static int compare(boolean var0, boolean var1) {
      byte var2;
      if(var0 == var1) {
         var2 = 0;
      } else if(var0) {
         var2 = 1;
      } else {
         var2 = -1;
      }

      return var2;
   }

   public static boolean[] concat(boolean[] ... var0) {
      int var1 = 0;
      boolean[][] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4].length;
         var1 += var5;
      }

      boolean[] var6 = new boolean[var1];
      int var7 = 0;
      boolean[][] var8 = var0;
      int var9 = var0.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         boolean[] var11 = var8[var10];
         int var12 = var11.length;
         System.arraycopy(var11, 0, var6, var7, var12);
         int var13 = var11.length;
         var7 += var13;
      }

      return var6;
   }

   public static boolean contains(boolean[] var0, boolean var1) {
      boolean[] var2 = var0;
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

   private static boolean[] copyOf(boolean[] var0, int var1) {
      boolean[] var2 = new boolean[var1];
      int var3 = Math.min(var0.length, var1);
      System.arraycopy(var0, 0, var2, 0, var3);
      return var2;
   }

   public static boolean[] ensureCapacity(boolean[] var0, int var1, int var2) {
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

   public static int hashCode(boolean var0) {
      short var1;
      if(var0) {
         var1 = 1231;
      } else {
         var1 = 1237;
      }

      return var1;
   }

   public static int indexOf(boolean[] var0, boolean var1) {
      int var2 = var0.length;
      return indexOf(var0, var1, 0, var2);
   }

   private static int indexOf(boolean[] var0, boolean var1, int var2, int var3) {
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

   public static int indexOf(boolean[] var0, boolean[] var1) {
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
                  boolean var11 = var0[var10];
                  boolean var12 = var1[var8];
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

   public static String join(String var0, boolean ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      String var3;
      if(var1.length == 0) {
         var3 = "";
      } else {
         int var4 = var1.length * 7;
         StringBuilder var5 = new StringBuilder(var4);
         boolean var6 = var1[0];
         var5.append(var6);
         int var8 = 1;

         while(true) {
            int var9 = var1.length;
            if(var8 >= var9) {
               var3 = var5.toString();
               break;
            }

            StringBuilder var10 = var5.append(var0);
            boolean var11 = var1[var8];
            var10.append(var11);
            ++var8;
         }
      }

      return var3;
   }

   public static int lastIndexOf(boolean[] var0, boolean var1) {
      int var2 = var0.length;
      return lastIndexOf(var0, var1, 0, var2);
   }

   private static int lastIndexOf(boolean[] var0, boolean var1, int var2, int var3) {
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

   public static Comparator<boolean[]> lexicographicalComparator() {
      return Booleans.LexicographicalComparator.INSTANCE;
   }

   public static boolean[] toArray(Collection<Boolean> var0) {
      boolean[] var1;
      if(var0 instanceof Booleans.BooleanArrayAsList) {
         var1 = ((Booleans.BooleanArrayAsList)var0).toBooleanArray();
      } else {
         Object[] var2 = var0.toArray();
         int var3 = var2.length;
         var1 = new boolean[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            boolean var5 = ((Boolean)var2[var4]).booleanValue();
            var1[var4] = var5;
         }
      }

      return var1;
   }

   @GwtCompatible
   private static class BooleanArrayAsList extends AbstractList<Boolean> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final boolean[] array;
      final int end;
      final int start;


      BooleanArrayAsList(boolean[] var1) {
         int var2 = var1.length;
         this(var1, 0, var2);
      }

      BooleanArrayAsList(boolean[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var6;
         if(var1 instanceof Boolean) {
            boolean[] var2 = this.array;
            boolean var3 = ((Boolean)var1).booleanValue();
            int var4 = this.start;
            int var5 = this.end;
            if(Booleans.indexOf(var2, var3, var4, var5) != -1) {
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
            if(var1 instanceof Booleans.BooleanArrayAsList) {
               Booleans.BooleanArrayAsList var3 = (Booleans.BooleanArrayAsList)var1;
               int var4 = this.size();
               if(var3.size() != var4) {
                  var2 = 0;
               } else {
                  for(int var5 = 0; var5 < var4; ++var5) {
                     boolean[] var6 = this.array;
                     int var7 = this.start + var5;
                     boolean var8 = var6[var7];
                     boolean[] var9 = var3.array;
                     int var10 = var3.start + var5;
                     boolean var11 = var9[var10];
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

      public Boolean get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         boolean[] var4 = this.array;
         int var5 = this.start + var1;
         return Boolean.valueOf(var4[var5]);
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
            int var5 = Booleans.hashCode(this.array[var2]);
            var1 = var4 + var5;
            ++var2;
         }
      }

      public int indexOf(Object var1) {
         int var8;
         if(var1 instanceof Boolean) {
            boolean[] var2 = this.array;
            boolean var3 = ((Boolean)var1).booleanValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Booleans.indexOf(var2, var3, var4, var5);
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
         if(var1 instanceof Boolean) {
            boolean[] var2 = this.array;
            boolean var3 = ((Boolean)var1).booleanValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Booleans.lastIndexOf(var2, var3, var4, var5);
            if(var6 >= 0) {
               int var7 = this.start;
               var8 = var6 - var7;
               return var8;
            }
         }

         var8 = -1;
         return var8;
      }

      public Boolean set(int var1, Boolean var2) {
         int var3 = this.size();
         Preconditions.checkElementIndex(var1, var3);
         boolean[] var5 = this.array;
         int var6 = this.start + var1;
         boolean var7 = var5[var6];
         boolean[] var8 = this.array;
         int var9 = this.start + var1;
         boolean var10 = var2.booleanValue();
         var8[var9] = var10;
         return Boolean.valueOf(var7);
      }

      public int size() {
         int var1 = this.end;
         int var2 = this.start;
         return var1 - var2;
      }

      public List<Boolean> subList(int var1, int var2) {
         int var3 = this.size();
         Preconditions.checkPositionIndexes(var1, var2, var3);
         Object var4;
         if(var1 == var2) {
            var4 = Collections.emptyList();
         } else {
            boolean[] var5 = this.array;
            int var6 = this.start + var1;
            int var7 = this.start + var2;
            var4 = new Booleans.BooleanArrayAsList(var5, var6, var7);
         }

         return (List)var4;
      }

      boolean[] toBooleanArray() {
         int var1 = this.size();
         boolean[] var2 = new boolean[var1];
         boolean[] var3 = this.array;
         int var4 = this.start;
         System.arraycopy(var3, var4, var2, 0, var1);
         return var2;
      }

      public String toString() {
         int var1 = this.size() * 7;
         StringBuilder var2 = new StringBuilder(var1);
         boolean[] var3 = this.array;
         int var4 = this.start;
         String var5;
         if(var3[var4]) {
            var5 = "[true";
         } else {
            var5 = "[false";
         }

         var2.append(var5);
         int var7 = this.start + 1;

         while(true) {
            int var8 = this.end;
            if(var7 >= var8) {
               return var2.append(']').toString();
            }

            String var9;
            if(this.array[var7]) {
               var9 = ", true";
            } else {
               var9 = ", false";
            }

            var2.append(var9);
            ++var7;
         }
      }
   }

   private static enum LexicographicalComparator implements Comparator<boolean[]> {

      // $FF: synthetic field
      private static final Booleans.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Booleans.LexicographicalComparator[] var0 = new Booleans.LexicographicalComparator[1];
         Booleans.LexicographicalComparator var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private LexicographicalComparator(String var1, int var2) {}

      public int compare(boolean[] var1, boolean[] var2) {
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

            boolean var7 = var1[var6];
            boolean var8 = var2[var6];
            var9 = Booleans.compare(var7, var8);
            if(var9 != 0) {
               break;
            }

            ++var6;
         }

         return var9;
      }
   }
}
