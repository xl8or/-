package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

@GwtCompatible
public final class Bytes {

   private Bytes() {}

   public static List<Byte> asList(byte ... var0) {
      Object var1;
      if(var0.length == 0) {
         var1 = Collections.emptyList();
      } else {
         var1 = new Bytes.ByteArrayAsList(var0);
      }

      return (List)var1;
   }

   public static byte[] concat(byte[] ... var0) {
      int var1 = 0;
      byte[][] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4].length;
         var1 += var5;
      }

      byte[] var6 = new byte[var1];
      int var7 = 0;
      byte[][] var8 = var0;
      int var9 = var0.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         byte[] var11 = var8[var10];
         int var12 = var11.length;
         System.arraycopy(var11, 0, var6, var7, var12);
         int var13 = var11.length;
         var7 += var13;
      }

      return var6;
   }

   public static boolean contains(byte[] var0, byte var1) {
      byte[] var2 = var0;
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

   private static byte[] copyOf(byte[] var0, int var1) {
      byte[] var2 = new byte[var1];
      int var3 = Math.min(var0.length, var1);
      System.arraycopy(var0, 0, var2, 0, var3);
      return var2;
   }

   public static byte[] ensureCapacity(byte[] var0, int var1, int var2) {
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

   public static int hashCode(byte var0) {
      return var0;
   }

   public static int indexOf(byte[] var0, byte var1) {
      int var2 = var0.length;
      return indexOf(var0, var1, 0, var2);
   }

   private static int indexOf(byte[] var0, byte var1, int var2, int var3) {
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

   public static int indexOf(byte[] var0, byte[] var1) {
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
                  byte var11 = var0[var10];
                  byte var12 = var1[var8];
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

   public static int lastIndexOf(byte[] var0, byte var1) {
      int var2 = var0.length;
      return lastIndexOf(var0, var1, 0, var2);
   }

   private static int lastIndexOf(byte[] var0, byte var1, int var2, int var3) {
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

   public static byte[] toArray(Collection<Byte> var0) {
      byte[] var1;
      if(var0 instanceof Bytes.ByteArrayAsList) {
         var1 = ((Bytes.ByteArrayAsList)var0).toByteArray();
      } else {
         Object[] var2 = var0.toArray();
         int var3 = var2.length;
         var1 = new byte[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            byte var5 = ((Byte)var2[var4]).byteValue();
            var1[var4] = var5;
         }
      }

      return var1;
   }

   @GwtCompatible
   private static class ByteArrayAsList extends AbstractList<Byte> implements RandomAccess, Serializable {

      private static final long serialVersionUID;
      final byte[] array;
      final int end;
      final int start;


      ByteArrayAsList(byte[] var1) {
         int var2 = var1.length;
         this(var1, 0, var2);
      }

      ByteArrayAsList(byte[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var6;
         if(var1 instanceof Byte) {
            byte[] var2 = this.array;
            byte var3 = ((Byte)var1).byteValue();
            int var4 = this.start;
            int var5 = this.end;
            if(Bytes.indexOf(var2, var3, var4, var5) != -1) {
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
            if(var1 instanceof Bytes.ByteArrayAsList) {
               Bytes.ByteArrayAsList var3 = (Bytes.ByteArrayAsList)var1;
               int var4 = this.size();
               if(var3.size() != var4) {
                  var2 = 0;
               } else {
                  for(int var5 = 0; var5 < var4; ++var5) {
                     byte[] var6 = this.array;
                     int var7 = this.start + var5;
                     byte var8 = var6[var7];
                     byte[] var9 = var3.array;
                     int var10 = var3.start + var5;
                     byte var11 = var9[var10];
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

      public Byte get(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         byte[] var4 = this.array;
         int var5 = this.start + var1;
         return Byte.valueOf(var4[var5]);
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
            int var5 = Bytes.hashCode(this.array[var2]);
            var1 = var4 + var5;
            ++var2;
         }
      }

      public int indexOf(Object var1) {
         int var8;
         if(var1 instanceof Byte) {
            byte[] var2 = this.array;
            byte var3 = ((Byte)var1).byteValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Bytes.indexOf(var2, var3, var4, var5);
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
         if(var1 instanceof Byte) {
            byte[] var2 = this.array;
            byte var3 = ((Byte)var1).byteValue();
            int var4 = this.start;
            int var5 = this.end;
            int var6 = Bytes.lastIndexOf(var2, var3, var4, var5);
            if(var6 >= 0) {
               int var7 = this.start;
               var8 = var6 - var7;
               return var8;
            }
         }

         var8 = -1;
         return var8;
      }

      public Byte set(int var1, Byte var2) {
         int var3 = this.size();
         Preconditions.checkElementIndex(var1, var3);
         byte[] var5 = this.array;
         int var6 = this.start + var1;
         byte var7 = var5[var6];
         byte[] var8 = this.array;
         int var9 = this.start + var1;
         byte var10 = var2.byteValue();
         var8[var9] = var10;
         return Byte.valueOf(var7);
      }

      public int size() {
         int var1 = this.end;
         int var2 = this.start;
         return var1 - var2;
      }

      public List<Byte> subList(int var1, int var2) {
         int var3 = this.size();
         Preconditions.checkPositionIndexes(var1, var2, var3);
         Object var4;
         if(var1 == var2) {
            var4 = Collections.emptyList();
         } else {
            byte[] var5 = this.array;
            int var6 = this.start + var1;
            int var7 = this.start + var2;
            var4 = new Bytes.ByteArrayAsList(var5, var6, var7);
         }

         return (List)var4;
      }

      byte[] toByteArray() {
         int var1 = this.size();
         byte[] var2 = new byte[var1];
         byte[] var3 = this.array;
         int var4 = this.start;
         System.arraycopy(var3, var4, var2, 0, var1);
         return var2;
      }

      public String toString() {
         int var1 = this.size() * 5;
         StringBuilder var2 = new StringBuilder(var1);
         StringBuilder var3 = var2.append('[');
         byte[] var4 = this.array;
         int var5 = this.start;
         byte var6 = var4[var5];
         var3.append(var6);
         int var8 = this.start + 1;

         while(true) {
            int var9 = this.end;
            if(var8 >= var9) {
               return var2.append(']').toString();
            }

            StringBuilder var10 = var2.append(", ");
            byte var11 = this.array[var8];
            var10.append(var11);
            ++var8;
         }
      }
   }
}
