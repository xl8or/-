package org.codehaus.jackson.sym;

import org.codehaus.jackson.sym.Name;

public final class Name1 extends Name {

   static final Name1 sEmptyName = new Name1("", 0, 0);
   final int mQuad;


   Name1(String var1, int var2, int var3) {
      super(var1, var2);
      this.mQuad = var3;
   }

   static final Name1 getEmptyName() {
      return sEmptyName;
   }

   public boolean equals(int var1) {
      int var2 = this.mQuad;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean equals(int var1, int var2) {
      int var3 = this.mQuad;
      boolean var4;
      if(var1 == var3 && var2 == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean equals(int[] var1, int var2) {
      boolean var5;
      if(var2 == 1) {
         int var3 = var1[0];
         int var4 = this.mQuad;
         if(var3 == var4) {
            var5 = true;
            return var5;
         }
      }

      var5 = false;
      return var5;
   }
}
