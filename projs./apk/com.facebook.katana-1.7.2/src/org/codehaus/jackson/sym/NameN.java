package org.codehaus.jackson.sym;

import org.codehaus.jackson.sym.Name;

public final class NameN extends Name {

   final int mQuadLen;
   final int[] mQuads;


   NameN(String var1, int var2, int[] var3, int var4) {
      super(var1, var2);
      if(var4 < 3) {
         throw new IllegalArgumentException("Qlen must >= 3");
      } else {
         this.mQuads = var3;
         this.mQuadLen = var4;
      }
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      int var3 = this.mQuadLen;
      boolean var4;
      if(var2 != var3) {
         var4 = false;
      } else {
         int var5 = 0;

         while(true) {
            if(var5 >= var2) {
               var4 = true;
               break;
            }

            int var6 = var1[var5];
            int var7 = this.mQuads[var5];
            if(var6 != var7) {
               var4 = false;
               break;
            }

            ++var5;
         }
      }

      return var4;
   }
}
