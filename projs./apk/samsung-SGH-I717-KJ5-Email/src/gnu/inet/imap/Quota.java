package gnu.inet.imap;

import gnu.inet.imap.IMAPConnection;
import gnu.inet.imap.Namespaces;
import gnu.inet.imap.UTF7imap;
import java.util.ArrayList;
import java.util.List;

public class Quota {

   String quotaRoot;
   List resources;


   Quota(String var1) {
      int var2 = var1.length();
      ArrayList var3 = new ArrayList();
      Namespaces.parse(var1, 0, var2, var3);
      String var5 = (String)var3.get(0);
      this.quotaRoot = var5;
      ArrayList var6 = new ArrayList();
      this.resources = var6;
      int var7 = var3.size();

      for(int var8 = 1; var8 < var7; ++var8) {
         List var9 = this.resources;
         List var10 = (List)var3.get(var8);
         Quota.Resource var11 = this.parseResource(var10);
         var9.add(var11);
      }

   }

   private Quota.Resource parseResource(List var1) {
      String var2 = (String)var1.get(0);
      String var3 = (String)var1.get(1);
      String var4 = (String)var1.get(2);
      int var5 = Integer.parseInt(var3);
      int var6 = Integer.parseInt(var4);
      return new Quota.Resource(var2, var5, var6);
   }

   public String getQuotaRoot() {
      return this.quotaRoot;
   }

   public Quota.Resource[] getResources() {
      Quota.Resource[] var1 = new Quota.Resource[this.resources.size()];
      this.resources.toArray(var1);
      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = IMAPConnection.quote(UTF7imap.encode(this.quotaRoot));
      var1.append(var2);
      int var4 = this.resources.size();

      for(int var5 = 0; var5 < var4; ++var5) {
         StringBuffer var6 = var1.append(' ');
         Object var7 = this.resources.get(var5);
         var1.append(var7);
      }

      return var1.toString();
   }

   public static class Resource {

      int current;
      int limit;
      String name;


      public Resource(String var1, int var2) {
         this(var1, -1, var2);
      }

      Resource(String var1, int var2, int var3) {
         this.name = var1;
         this.current = var2;
         this.limit = var3;
      }

      public int getCurrentUsage() {
         return this.current;
      }

      public int getLimit() {
         return this.limit;
      }

      public String getName() {
         return this.name;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         StringBuffer var2 = var1.append('(');
         String var3 = this.name;
         var1.append(var3);
         if(this.current >= 0) {
            StringBuffer var5 = var1.append(' ');
            int var6 = this.current;
            var1.append(var6);
         }

         StringBuffer var8 = var1.append(' ');
         int var9 = this.limit;
         var1.append(var9);
         StringBuffer var11 = var1.append(')');
         return var1.toString();
      }
   }
}
