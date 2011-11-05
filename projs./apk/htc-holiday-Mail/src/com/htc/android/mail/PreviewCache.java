package com.htc.android.mail;

import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PreviewCache {

   public static final int LRU_LIST_SIZE = 1000;
   private static final String TAG = "PreviewCache";
   public static ArrayList<Long> lru = new ArrayList(1000);
   public static HashMap<Long, String> map = new HashMap();


   public PreviewCache() {}

   public static void clear() {
      synchronized(PreviewCache.class){}

      try {
         map.clear();
         lru.clear();
      } finally {
         ;
      }

   }

   public static String find(long var0) {
      synchronized(PreviewCache.class){}

      String var4;
      try {
         HashMap var2 = map;
         Long var3 = Long.valueOf(var0);
         var4 = (String)var2.get(var3);
      } finally {
         ;
      }

      return var4;
   }

   public static String get(long param0) {
      // $FF: Couldn't be decompiled
   }

   private static void lru_update(long var0) {
      ArrayList var2 = lru;
      Long var3 = Long.valueOf(var0);
      int var4 = var2.indexOf(var3);
      if(var4 < 0) {
         if(Mail.MAIL_DETAIL_DEBUG) {
            String var5 = "*error: lru_update not found " + var0;
            ll.d("PreviewCache", var5);
         }
      } else {
         Object var6 = lru.remove(var4);
         ArrayList var7 = lru;
         Long var8 = Long.valueOf(var0);
         var7.add(var8);
      }
   }

   public static void put(long var0, String var2) {
      synchronized(PreviewCache.class){}

      try {
         if(lru.size() >= 1000) {
            long var3 = ((Long)lru.remove(0)).longValue();
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var5 = "lru_remove " + var3;
               ll.d("PreviewCache", var5);
            }

            HashMap var6 = map;
            Long var7 = Long.valueOf(var3);
            if((String)var6.remove(var7) == null && Mail.MAIL_DETAIL_DEBUG) {
               String var8 = "*error: map remove not found" + var3;
               ll.d("PreviewCache", var8);
            }
         }

         HashMap var9 = map;
         Long var10 = Long.valueOf(var0);
         if((String)var9.put(var10, var2) != null) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var11 = "*error: put key exist " + var0;
               ll.d("PreviewCache", var11);
            }

            ArrayList var12 = lru;
            Long var13 = Long.valueOf(var0);
            int var14 = var12.indexOf(var13);
            if(var14 >= 0) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  String var15 = "lru found " + var0;
                  ll.d("PreviewCache", var15);
               }

               Object var16 = lru.remove(var14);
            } else if(Mail.MAIL_DETAIL_DEBUG) {
               String var20 = "lru not found " + var0;
               ll.d("PreviewCache", var20);
            }
         }

         ArrayList var17 = lru;
         Long var18 = Long.valueOf(var0);
         var17.add(var18);
      } finally {
         ;
      }

   }

   public static void remove(long var0) {
      synchronized(PreviewCache.class){}

      try {
         ArrayList var2 = lru;
         Long var3 = Long.valueOf(var0);
         int var4 = var2.indexOf(var3);
         if(var4 < 0) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var5 = "*error: lru_remove not found " + var0;
               ll.d("PreviewCache", var5);
            }
         } else {
            Object var6 = lru.remove(var4);
            HashMap var7 = map;
            Long var8 = Long.valueOf(var0);
            var7.remove(var8);
         }
      } finally {
         ;
      }

   }

   public static void removeEmpty() {
      synchronized(PreviewCache.class){}

      try {
         Set var0 = map.keySet();
         Iterator var1 = (new HashSet(var0)).iterator();

         while(var1.hasNext()) {
            Long var2 = (Long)var1.next();
            String var3 = find(var2.longValue());
            if(var3 != null && var3.length() == 0) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  String var4 = "removeEmpty: " + var2;
                  ll.d("PreviewCache", var4);
               }

               remove(var2.longValue());
            }
         }
      } finally {
         ;
      }

   }
}
