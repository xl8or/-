package com.android.volley;


public interface Cache {

   void clear();

   Cache.Entry get(String var1);

   void initialize();

   void invalidate(String var1, boolean var2);

   void put(String var1, Cache.Entry var2);

   void remove(String var1);

   public static class Entry {

      public byte[] data;
      public String etag;
      public long serverDate;
      public long softTtl;
      public long ttl;


      public Entry() {}

      public boolean isExpired() {
         long var1 = this.ttl;
         long var3 = System.currentTimeMillis();
         boolean var5;
         if(var1 < var3) {
            var5 = true;
         } else {
            var5 = false;
         }

         return var5;
      }

      public boolean refreshNeeded() {
         long var1 = this.softTtl;
         long var3 = System.currentTimeMillis();
         boolean var5;
         if(var1 < var3) {
            var5 = true;
         } else {
            var5 = false;
         }

         return var5;
      }
   }
}
