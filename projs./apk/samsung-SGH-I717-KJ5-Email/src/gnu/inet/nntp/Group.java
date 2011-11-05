package gnu.inet.nntp;


public final class Group {

   boolean canPost;
   int first;
   int last;
   String name;


   Group(String var1, int var2, int var3, boolean var4) {
      this.name = var1;
      this.last = var2;
      this.first = var3;
      this.canPost = var4;
   }

   public int getFirst() {
      return this.first;
   }

   public int getLast() {
      return this.last;
   }

   public String getName() {
      return this.name;
   }

   public boolean isCanPost() {
      return this.canPost;
   }
}
