package myorg.bouncycastle.util;


public final class Arrays {

   private Arrays() {}

   public static boolean areEqual(byte[] var0, byte[] var1) {
      boolean var2;
      if(var0 == var1) {
         var2 = true;
      } else if(var0 != null && var1 != null) {
         int var3 = var0.length;
         int var4 = var1.length;
         if(var3 != var4) {
            var2 = false;
         } else {
            int var5 = 0;

            while(true) {
               int var6 = var0.length;
               if(var5 == var6) {
                  var2 = true;
                  break;
               }

               byte var7 = var0[var5];
               byte var8 = var1[var5];
               if(var7 != var8) {
                  var2 = false;
                  break;
               }

               ++var5;
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(int[] var0, int[] var1) {
      boolean var2;
      if(var0 == var1) {
         var2 = true;
      } else if(var0 != null && var1 != null) {
         int var3 = var0.length;
         int var4 = var1.length;
         if(var3 != var4) {
            var2 = false;
         } else {
            int var5 = 0;

            while(true) {
               int var6 = var0.length;
               if(var5 == var6) {
                  var2 = true;
                  break;
               }

               int var7 = var0[var5];
               int var8 = var1[var5];
               if(var7 != var8) {
                  var2 = false;
                  break;
               }

               ++var5;
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(boolean[] var0, boolean[] var1) {
      boolean var2;
      if(var0 == var1) {
         var2 = true;
      } else if(var0 != null && var1 != null) {
         int var3 = var0.length;
         int var4 = var1.length;
         if(var3 != var4) {
            var2 = false;
         } else {
            int var5 = 0;

            while(true) {
               int var6 = var0.length;
               if(var5 == var6) {
                  var2 = true;
                  break;
               }

               boolean var7 = var0[var5];
               boolean var8 = var1[var5];
               if(var7 != var8) {
                  var2 = false;
                  break;
               }

               ++var5;
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public static byte[] clone(byte[] var0) {
      byte[] var1;
      if(var0 == null) {
         var1 = null;
      } else {
         byte[] var2 = new byte[var0.length];
         int var3 = var0.length;
         System.arraycopy(var0, 0, var2, 0, var3);
         var1 = var2;
      }

      return var1;
   }

   public static int[] clone(int[] var0) {
      int[] var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int[] var2 = new int[var0.length];
         int var3 = var0.length;
         System.arraycopy(var0, 0, var2, 0, var3);
         var1 = var2;
      }

      return var1;
   }

   public static boolean constantTimeAreEqual(byte[] var0, byte[] var1) {
      boolean var2;
      if(var0 == var1) {
         var2 = true;
      } else if(var0 != null && var1 != null) {
         int var3 = var0.length;
         int var4 = var1.length;
         if(var3 != var4) {
            var2 = false;
         } else {
            int var5 = 0;
            int var6 = 0;

            while(true) {
               int var7 = var0.length;
               if(var6 == var7) {
                  if(var5 == 0) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }
                  break;
               }

               byte var8 = var0[var6];
               byte var9 = var1[var6];
               int var10 = var8 ^ var9;
               var5 |= var10;
               ++var6;
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public static void fill(byte[] var0, byte var1) {
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return;
         }

         var0[var2] = var1;
         ++var2;
      }
   }

   public static void fill(long[] var0, long var1) {
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return;
         }

         var0[var3] = var1;
         ++var3;
      }
   }

   public static void fill(short[] var0, short var1) {
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return;
         }

         var0[var2] = var1;
         ++var2;
      }
   }

   public static int hashCode(byte[] var0) {
      int var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         int var2 = var0.length;
         int var3 = var2 + 1;

         while(true) {
            var2 += -1;
            if(var2 < 0) {
               var1 = var3;
               break;
            }

            int var4 = var3 * 257;
            byte var5 = var0[var2];
            var3 = var4 ^ var5;
         }
      }

      return var1;
   }
}
