package myorg.bouncycastle.jce.provider;


class ReasonsMask {

   static final ReasonsMask allReasons = new ReasonsMask('\u80ff');
   private int _reasons;


   ReasonsMask() {
      this(0);
   }

   ReasonsMask(int var1) {
      this._reasons = var1;
   }

   void addReasons(ReasonsMask var1) {
      int var2 = this._reasons;
      int var3 = var1.getReasons();
      int var4 = var2 | var3;
      this._reasons = var4;
   }

   int getReasons() {
      return this._reasons;
   }

   boolean hasNewReasons(ReasonsMask var1) {
      int var2 = this._reasons;
      int var3 = var1.getReasons();
      int var4 = this._reasons;
      int var5 = var3 ^ var4;
      boolean var6;
      if((var2 | var5) != 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   ReasonsMask intersect(ReasonsMask var1) {
      ReasonsMask var2 = new ReasonsMask();
      int var3 = this._reasons;
      int var4 = var1.getReasons();
      int var5 = var3 & var4;
      ReasonsMask var6 = new ReasonsMask(var5);
      var2.addReasons(var6);
      return var2;
   }

   boolean isAllReasons() {
      int var1 = this._reasons;
      int var2 = allReasons._reasons;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
