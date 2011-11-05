package kankan.wheel.widget;


public class ItemsRange {

   private int count;
   private int first;


   public ItemsRange() {
      this(0, 0);
   }

   public ItemsRange(int var1, int var2) {
      this.first = var1;
      this.count = var2;
   }

   public boolean contains(int var1) {
      int var2 = this.getFirst();
      boolean var4;
      if(var1 >= var2) {
         int var3 = this.getLast();
         if(var1 <= var3) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public int getCount() {
      return this.count;
   }

   public int getFirst() {
      return this.first;
   }

   public int getLast() {
      int var1 = this.getFirst();
      int var2 = this.getCount();
      return var1 + var2 - 1;
   }
}
