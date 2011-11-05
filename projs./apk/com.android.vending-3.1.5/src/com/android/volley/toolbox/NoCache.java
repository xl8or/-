package com.android.volley.toolbox;

import com.android.volley.Cache;

public class NoCache implements Cache {

   public NoCache() {}

   public void clear() {}

   public Cache.Entry get(String var1) {
      return null;
   }

   public void initialize() {}

   public void invalidate(String var1, boolean var2) {}

   public void put(String var1, Cache.Entry var2) {}

   public void remove(String var1) {}
}
