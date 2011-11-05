package com.google.android.finsky.utils;

import android.net.Uri;
import com.google.android.finsky.utils.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlIntentFilter {

   public static final int BUY = 4;
   public static final int DETAILS = 2;
   public static final int HOME = 1;
   public static final int SEARCH = 3;
   private static final Map<Pattern, Integer> sUrlPatterns = Maps.newLinkedHashMap();


   static {
      addUriPattern("https?://market\\.android\\.com/details\\?.*id=app[:-]([^=&]+).*", 2, 3);
      addUriPattern("https?://market\\.android\\.com/details\\?.*id=book[:-]([^=&]+).*", 2, 1);
      addUriPattern("https?://market\\.android\\.com/details\\?.*id=movie[:-]([^=&]+).*", 2, 4);
      addUriPattern("https?://market\\.android\\.com/details\\?.*id=([^=&]+).*", 2, 3);
      addUriPattern("https?://market\\.android\\.com/search\\?.*q=([^=&]+).*", 3, 0);
      addUriPattern("market://details\\?id=(.*)", 2, 3);
      addUriPattern("market://search\\?q=(.*)", 3, 3);
      addUriPattern("https?://market\\.android\\.com/books/search\\?.*q=([^=&]+).*", 3, 1);
      addUriPattern("https?://market\\.android\\.com/books/(.+)/buy", 4, 1);
      addUriPattern("https?://market\\.android\\.com/books/([^=&]+).*", 2, 1);
      addUriPattern("https?://market\\.android\\.com/?", 1, 3);
      addUriPattern("https?://market\\.android\\.com/apps/?", 1, 3);
      addUriPattern("https?://market\\.android\\.com/books/?", 1, 1);
      addUriPattern("https?://market\\.android\\.com/movies/?", 1, 4);
   }

   public UrlIntentFilter() {}

   private static void addUriPattern(String var0, int var1, int var2) {
      Pattern var3 = Pattern.compile(var0);
      Map var4 = sUrlPatterns;
      Integer var5 = Integer.valueOf(var1 << 16 | var2);
      var4.put(var3, var5);
   }

   public static UrlIntentFilter.Result matchUri(String var0) {
      Object var1 = null;
      Iterator var2 = sUrlPatterns.keySet().iterator();

      while(var2.hasNext()) {
         Pattern var3 = (Pattern)var2.next();
         Matcher var4 = var3.matcher(var0);
         if(var4.matches()) {
            int var5 = ((Integer)sUrlPatterns.get(var3)).intValue();
            int var6 = var5 >> 16;
            int var7 = var5 & '\uffff';
            if(var4.groupCount() > 0) {
               var1 = var4.group(1);
            }

            String var8 = Uri.decode((String)var1);
            var1 = new UrlIntentFilter.Result(var6, var7, var8);
            break;
         }
      }

      return (UrlIntentFilter.Result)var1;
   }

   public static class Result {

      public final int corpus;
      public final String extra;
      public final int type;


      public Result(int var1, int var2, String var3) {
         this.type = var1;
         this.corpus = var2;
         this.extra = var3;
      }
   }
}
