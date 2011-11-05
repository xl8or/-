package javax.mail;

import java.util.ArrayList;

public class FetchProfile {

   private ArrayList headers = null;
   private ArrayList items = null;


   public FetchProfile() {}

   public void add(String var1) {
      if(this.headers == null) {
         ArrayList var2 = new ArrayList();
         this.headers = var2;
      }

      ArrayList var3 = this.headers;
      synchronized(var3) {
         this.headers.add(var1);
      }
   }

   public void add(FetchProfile.Item var1) {
      if(this.items == null) {
         ArrayList var2 = new ArrayList();
         this.items = var2;
      }

      ArrayList var3 = this.items;
      synchronized(var3) {
         this.items.add(var1);
      }
   }

   public boolean contains(String var1) {
      boolean var2;
      if(this.headers != null && this.headers.contains(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean contains(FetchProfile.Item var1) {
      boolean var2;
      if(this.items != null && this.items.contains(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String[] getHeaderNames() {
      String[] var1;
      if(this.headers == null) {
         var1 = new String[0];
      } else {
         ArrayList var2 = this.headers;
         synchronized(var2) {
            String[] var3 = new String[this.headers.size()];
            this.headers.toArray(var3);
            var1 = var3;
         }
      }

      return var1;
   }

   public FetchProfile.Item[] getItems() {
      FetchProfile.Item[] var1;
      if(this.items == null) {
         var1 = new FetchProfile.Item[0];
      } else {
         ArrayList var2 = this.items;
         synchronized(var2) {
            FetchProfile.Item[] var3 = new FetchProfile.Item[this.items.size()];
            this.items.toArray(var3);
            var1 = var3;
         }
      }

      return var1;
   }

   public static class Item {

      public static final FetchProfile.Item CONTENT_INFO = new FetchProfile.Item("CONTENT_INFO");
      public static final FetchProfile.Item ENVELOPE = new FetchProfile.Item("ENVELOPE");
      public static final FetchProfile.Item FLAGS = new FetchProfile.Item("FLAGS");
      private String name;


      protected Item(String var1) {
         this.name = var1;
      }
   }
}
