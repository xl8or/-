package javax.mail;


public class Quota {

   public String quotaRoot;
   public Quota.Resource[] resources;


   public Quota(String var1) {
      this.quotaRoot = var1;
   }

   public void setResourceLimit(String var1, long var2) {
      if(this.resources != null) {
         int var4 = 0;
         boolean var5 = false;

         while(true) {
            int var6 = this.resources.length;
            if(var4 >= var6) {
               if(var5) {
                  return;
               }

               Quota.Resource[] var7 = new Quota.Resource[this.resources.length + 1];
               Quota.Resource[] var8 = this.resources;
               int var9 = this.resources.length;
               System.arraycopy(var8, 0, var7, 0, var9);
               int var10 = this.resources.length;
               Quota.Resource var14 = new Quota.Resource(var1, 0L, var2);
               var7[var10] = var14;
               this.resources = var7;
               return;
            }

            if(this.resources[var4].name.equals(var1)) {
               this.resources[var4].limit = var2;
               var5 = true;
            }

            ++var4;
         }
      } else {
         Quota.Resource[] var15 = new Quota.Resource[1];
         this.resources = var15;
         Quota.Resource[] var16 = this.resources;
         Quota.Resource var20 = new Quota.Resource(var1, 0L, var2);
         var16[0] = var20;
      }
   }

   public static class Resource {

      public long limit;
      public String name;
      public long usage;


      public Resource(String var1, long var2, long var4) {
         this.name = var1;
         this.usage = var2;
         this.limit = var4;
      }
   }
}
