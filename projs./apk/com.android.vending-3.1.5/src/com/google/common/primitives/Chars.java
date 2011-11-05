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
public final class Chars {

   public static final int BYTES = 2;


   private Chars() {}

   public static List<Character> asList(char ... var0) {
      Object var1;
      if(var0.length == 0) {
         var1 = Collections.emptyList();
      } else {
         var1 = new Chars.CharArrayAsList(var0);
      }

      return (List)var1;
   }

   public static char checkedCast(long var0) {
      char var2 = (char)((int)var0);
      byte var3;
      if((long)var2 == var0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Object[] var4 = new Object[1];
      Long var5 = Long.valueOf(var0);
      var4[0] = var5;
      Preconditions.checkArgument((boolean)var3, "Out of range: %s", var4);
      return var2;
   }

   public static int compare(char var0, char var1) {
      return var0 - var1;
   }

   public static char[] concat(char[] ... var0) {
      int var1 = 0;
      char[][] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4].length;
         var1 += var5;
      }

      char[] var6 = new char[var1];
      int var7 = 0;
      char[][] var8 = var0;
      int var9 = var0.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         char[] var11 = var8[var10];
         int var12 = var11.length;
         System.arraycopy(var11, 0, var6, var7, var12);
         int var13 = var11.length;
         var7 += var13;
      }

      return var6;
   }

   public static boolean contains(char[] var0, char var1) {
      char[] var2 = var0;
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

   private static char[] copyOf(char[] var0, int var1) {
      char[] var2 = new char[var1];
      int var3 = Math.min(var0.length, var1);
      System.arraycopy(var0, 0, var2, 0, var3);
      return var2;
   }

   public static char[] ensureCapacity(char[] var0, int var1, int var2) {
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
   public static char fromByteArray(byte[] var0) {
      byte var1;
      if(var0.length >= 2) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Object[] var2 = new Object[2];
      Integer var3 = Integer.valueOf(var0.length);
      var2[0] = var3;
      Integer var4 = Integer.valueOf(2);
      var2[1] = var4;
      Preconditions.checkArgument((boolean)var1, "array too small: %s < %s", var2);
      int var5 = var0[0] << 8;
      int var6 = var0[1] & 255;
      return (char)(var5 | var6);
   }

   public static int hashCode(char var0) {
      return var0;
   }

   public static int indexOf(char[] var0, char var1) {
      int var2 = var0.length;
      return indexOf(var0, var1, 0, var2);
   }

   private static int indexOf(char[] var0, char var1, int var2, int var3) {
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

   public static int indexOf(char[] var0, char[] var1) {
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
                  char var11 = var0[var10];
                  char var12 = var1[var8];
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

   public static String join(String var0, char ... var1) {
      Object var2 = Preconditions.checkNotNull(var0);
      int var3 = var1.length;
      String var4;
      if(var3 == 0) {
         var4 = "";
      } else {
         int var5 = var0.length();
         int var6 = var3 + -1;
         int var7 = var5 * var6 + var3;
         StringBuilder var8 = new StringBuilder(var7);
         char var9 = var1[0];
         var8.append(var9);

         for(int var11 = 1; var11 < var3; ++var11) {
            StringBuilder var12 = var8.append(var0);
            char var13 = var1[var11];
            var12.append(var13);
         }

         var4 = var8.toString();
      }

      return var4;
   }

   public static int lastIndexOf(char[] var0, char var1) {
      int var2 = var0.length;
      return lastIndexOf(var0, var1, 0, var2);
   }

   private static int lastIndexOf(char[] var0, char var1, int var2, int var3) {
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

   public static Comparator<char[]> lexicographicalComparator() {
      return Chars.LexicographicalComparator.INSTANCE;
   }

   public static char max(char ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      char var2 = var0[0];
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         if(var0[var3] > var2) {
            var2 = var0[var3];
         }

         ++var3;
      }
   }

   public static char min(char ... var0) {
      byte var1;
      if(var0.length > 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Preconditions.checkArgument((boolean)var1);
      char var2 = var0[0];
      int var3 = 1;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         if(var0[var3] < var2) {
            var2 = var0[var3];
         }

         ++var3;
      }
   }

   public static char saturatedCast(long var0) {
      char var2;
      if(var0 > 65535L) {
         var2 = '\uffff';
      } else if(var0 < 0L) {
         var2 = 0;
      } else {
         var2 = (char)((int)var0);
      }

      return var2;
   }

   public static char[] toArray(Collection<Character> var0) {
      char[] var1;
      if(var0 instanceof Chars.CharArrayAsList) {
         var1 = ((Chars.CharArrayAsList)var0).toCharArray();
      } else {
         Object[] var2 = var0.toArray();
         int var3 = var2.length;
         var1 = new char[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = ((Character)var2[var4]).charValue();
            var1[var4] = var5;
         }
      }

      return var1;
   }

   @GwtIncompatible("doesn\'t work")
   public static byte[] toByteArray(char var0) {
      byte[] var1 = new byte[2];
      byte var2 = (byte)(var0 >> 8);
      var1[0] = var2;
      byte var3 = (byte)var0;
      var1[1] = var3;
      return var1;
   }

   @GwtCompatible
   private static class CharArrayAsList extends AbstractList<Character> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final char[] array;
      final int end;
      final int start;


      CharArrayAsList(char[] var1) {
         int var2 = var1.length;
         this(var1, 0, var2);
      }

      CharArrayAsList(char[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var6;
         if(var1 instanceof Character) {
            char[] var2 = this.array;
            char var3 = ((Character)var1).charValue();
            int var4 = this.start;
            int var5 = this.end;
            if(Chars.indexOf(var2, var3, var4, var5) != -1) {
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
            if(var1 instanceof Chars.CharArrayAsList) {
               Chars.CharArrayAsList var3 = (Chars.CharArrayAsList)var1;
               int var4 = this.size();
               if(var3.size() != var4) {
                  var2 = 0;
               } else {
                  for(int var5 = 0; var5 < var4; ++var5) {
                     char[] var6 = this.array;
                     int var7 = this.start + var5;
                     char var8 = var6[var7];
                     char[] var9 = var3.array;
                     int var10 = var3.start + var5;
                     char var11 = var9[var10];
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

      public Character get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         char[] var4 = this.array;
         int var5 = this.start + var1;
         return Character.valueOf(var4[var5]);
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
            int var5 = Chars.hashCode(this.array[var2]);
            var1 = var4 + var5;
            ++var2;
         }
      }

      public int indexOf(Object var1) {
         int var8;
         if(var1 instanceof Character) {
            char[] var2 = this.array;
            char var3 = ((Character)var1).charValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Chars.indexOf(var2, var3, var4, var5);
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
         if(var1 instanceof Character) {
            char[] var2 = this.array;
            char var3 = ((Character)var1).charValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Chars.lastIndexOf(var2, var3, var4, var5);
            if(var6 >= 0) {
               int var7 = this.start;
               var8 = var6 - var7;
               return var8;
            }
         }

         var8 = -1;
         return var8;
      }

      public Character set(int var1, Character var2) {
         int var3 = this.size();
         Preconditions.checkElementIndex(var1, var3);
         char[] var5 = this.array;
         int var6 = this.start + var1;
         char var7 = var5[var6];
         char[] var8 = this.array;
         int var9 = this.start + var1;
         char var10 = var2.charValue();
         var8[var9] = var10;
         return Character.valueOf(var7);
      }

      public int size() {
         int var1 = this.end;
         int var2 = this.start;
         return var1 - var2;
      }

      public List<Character> subList(int var1, int var2) {
         int var3 = this.size();
         Preconditions.checkPositionIndexes(var1, var2, var3);
         Object var4;
         if(var1 == var2) {
            var4 = Collections.emptyList();
         } else {
            char[] var5 = this.array;
            int var6 = this.start + var1;
            int var7 = this.start + var2;
            var4 = new Chars.CharArrayAsList(var5, var6, var7);
         }

         return (List)var4;
      }

      char[] toCharArray() {
         int var1 = this.size();
         char[] var2 = new char[var1];
         char[] var3 = this.array;
         int var4 = this.start;
         System.arraycopy(var3, var4, var2, 0, var1);
         return var2;
      }

      public String toString() {
         int var1 = this.size() * 3;
         StringBuilder var2 = new StringBuilder(var1);
         StringBuilder var3 = var2.append('[');
         char[] var4 = this.array;
         int var5 = this.start;
         char var6 = var4[var5];
         var3.append(var6);
         int var8 = this.start + 1;

         while(true) {
            int var9 = this.end;
            if(var8 >= var9) {
               return var2.append(']').toString();
            }

            StringBuilder var10 = var2.append(", ");
            char var11 = this.array[var8];
            var10.append(var11);
            ++var8;
         }
      }
   }

   private static enum LexicographicalComparator implements Comparator<char[]> {

      // $FF: synthetic field
      private static final Chars.LexicographicalComparator[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Chars.LexicographicalComparator[] var0 = new Chars.LexicographicalComparator[1];
         Chars.LexicographicalComparator var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private LexicographicalComparator(String var1, int var2) {}

      public int compare(char[] var1, char[] var2) {
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

            char var7 = var1[var6];
            char var8 = var2[var6];
            var9 = Chars.compare(var7, var8);
            if(var9 != 0) {
               break;
            }

            ++var6;
         }

         return var9;
      }
   }
}
