package org.codehaus.jackson.sym;


public abstract class Name {

   protected final int mHashCode;
   protected final String mName;


   protected Name(String var1, int var2) {
      this.mName = var1;
      this.mHashCode = var2;
   }

   public abstract boolean equals(int var1);

   public abstract boolean equals(int var1, int var2);

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public abstract boolean equals(int[] var1, int var2);

   public String getName() {
      return this.mName;
   }

   public final int hashCode() {
      return this.mHashCode;
   }

   public String toString() {
      return this.mName;
   }
}
