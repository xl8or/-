package org.codehaus.jackson.sym;

import org.codehaus.jackson.sym.Name;

public final class Name3 extends Name {

   final int mQuad1;
   final int mQuad2;
   final int mQuad3;


   Name3(String var1, int var2, int var3, int var4, int var5) {
      super(var1, var2);
      this.mQuad1 = var3;
      this.mQuad2 = var4;
      this.mQuad3 = var5;
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      boolean var9;
      if(var2 == 3) {
         int var3 = var1[0];
         int var4 = this.mQuad1;
         if(var3 == var4) {
            int var5 = var1[1];
            int var6 = this.mQuad2;
            if(var5 == var6) {
               int var7 = var1[2];
               int var8 = this.mQuad3;
               if(var7 == var8) {
                  var9 = true;
                  return var9;
               }
            }
         }
      }

      var9 = false;
      return var9;
   }
}
