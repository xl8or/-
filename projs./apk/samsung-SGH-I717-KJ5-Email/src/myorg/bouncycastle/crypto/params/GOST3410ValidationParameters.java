package myorg.bouncycastle.crypto.params;


public class GOST3410ValidationParameters {

   private int c;
   private long cL;
   private int x0;
   private long x0L;


   public GOST3410ValidationParameters(int var1, int var2) {
      this.x0 = var1;
      this.c = var2;
   }

   public GOST3410ValidationParameters(long var1, long var3) {
      this.x0L = var1;
      this.cL = var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof GOST3410ValidationParameters)) {
         var2 = false;
      } else {
         GOST3410ValidationParameters var3 = (GOST3410ValidationParameters)var1;
         int var4 = var3.c;
         int var5 = this.c;
         if(var4 != var5) {
            var2 = false;
         } else {
            int var6 = var3.x0;
            int var7 = this.x0;
            if(var6 != var7) {
               var2 = false;
            } else {
               long var8 = var3.cL;
               long var10 = this.cL;
               if(var8 != var10) {
                  var2 = false;
               } else {
                  long var12 = var3.x0L;
                  long var14 = this.x0L;
                  if(var12 != var14) {
                     var2 = false;
                  } else {
                     var2 = true;
                  }
               }
            }
         }
      }

      return var2;
   }

   public int getC() {
      return this.c;
   }

   public long getCL() {
      return this.cL;
   }

   public int getX0() {
      return this.x0;
   }

   public long getX0L() {
      return this.x0L;
   }

   public int hashCode() {
      int var1 = this.x0;
      int var2 = this.c;
      int var3 = var1 ^ var2;
      int var4 = (int)this.x0L;
      int var5 = var3 ^ var4;
      int var6 = (int)(this.x0L >> 32);
      int var7 = var5 ^ var6;
      int var8 = (int)this.cL;
      int var9 = var7 ^ var8;
      int var10 = (int)(this.cL >> 32);
      return var9 ^ var10;
   }
}
