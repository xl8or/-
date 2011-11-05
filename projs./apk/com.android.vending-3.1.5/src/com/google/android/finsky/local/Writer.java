package com.google.android.finsky.local;

import com.google.android.finsky.local.Writable;

interface Writer {

   void delete(Writable var1);

   void insert(Writable var1);

   void kill();

   public static class Item {

      public final Writer.Op mOperation;
      public final Writable mWritable;


      public Item(Writer.Op var1, Writable var2) {
         this.mOperation = var1;
         this.mWritable = var2;
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if(this != var1) {
            if(var1 != null) {
               Class var3 = this.getClass();
               Class var4 = var1.getClass();
               if(var3 == var4) {
                  Writer.Item var5 = (Writer.Item)var1;
                  Writer.Op var6 = this.mOperation;
                  Writer.Op var7 = var5.mOperation;
                  if(var6 != var7) {
                     var2 = false;
                  } else {
                     if(this.mWritable != null) {
                        Writable var8 = this.mWritable;
                        Writable var9 = var5.mWritable;
                        if(var8.equals(var9)) {
                           return var2;
                        }
                     } else if(var5.mWritable == null) {
                        return var2;
                     }

                     var2 = false;
                  }

                  return var2;
               }
            }

            var2 = false;
         }

         return var2;
      }

      public int hashCode() {
         int var1 = 0;
         int var2;
         if(this.mOperation != null) {
            var2 = this.mOperation.hashCode();
         } else {
            var2 = 0;
         }

         int var3 = var2 * 31;
         if(this.mWritable != null) {
            var1 = this.mWritable.hashCode();
         }

         return var3 + var1;
      }
   }

   public static enum Op {

      // $FF: synthetic field
      private static final Writer.Op[] $VALUES;
      DELETE("DELETE", 1),
      INSERT("INSERT", 0),
      POISON("POISON", 2);


      static {
         Writer.Op[] var0 = new Writer.Op[3];
         Writer.Op var1 = INSERT;
         var0[0] = var1;
         Writer.Op var2 = DELETE;
         var0[1] = var2;
         Writer.Op var3 = POISON;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Op(String var1, int var2) {}
   }
}
