package org.codehaus.jackson.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class InternCache extends LinkedHashMap<String, String> {

   private static final int MAX_ENTRIES = 192;
   public static final InternCache instance = new InternCache();


   private InternCache() {
      super(192, 0.8F, (boolean)1);
   }

   public String intern(String var1) {
      synchronized(this){}

      String var2;
      try {
         var2 = (String)this.get(var1);
         if(var2 == null) {
            var2 = var1.intern();
            this.put(var2, var2);
         }
      } finally {
         ;
      }

      return var2;
   }

   protected boolean removeEldestEntry(Entry<String, String> var1) {
      boolean var2;
      if(this.size() > 192) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
