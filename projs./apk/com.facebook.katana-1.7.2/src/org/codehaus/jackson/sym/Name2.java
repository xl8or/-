package org.codehaus.jackson.sym;

import org.codehaus.jackson.sym.Name;

public final class Name2 extends Name {

   final int mQuad1;
   final int mQuad2;


   Name2(String var1, int var2, int var3, int var4) {
      super(var1, var2);
      this.mQuad1 = var3;
      this.mQuad2 = var4;
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      int var3 = this.mQuad1;
      boolean var5;
      if(var1 == var3) {
         int var4 = this.mQuad2;
         if(var2 == var4) {
            var5 = true;
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   public boolean equals(int[] var1, int var2) {
      boolean var7;
      if(var2 == 2) {
         int var3 = var1[0];
         int var4 = this.mQuad1;
         if(var3 == var4) {
            int var5 = var1[1];
            int var6 = this.mQuad2;
            if(var5 == var6) {
               var7 = true;
               return var7;
            }
         }
      }

      var7 = false;
      return var7;
   }
}
