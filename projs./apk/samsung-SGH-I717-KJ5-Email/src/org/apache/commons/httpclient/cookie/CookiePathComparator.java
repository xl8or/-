package org.apache.commons.httpclient.cookie;

import java.util.Comparator;
import org.apache.commons.httpclient.Cookie;

public class CookiePathComparator implements Comparator {

   public CookiePathComparator() {}

   private String normalizePath(Cookie var1) {
      String var2 = var1.getPath();
      if(var2 == null) {
         var2 = "/";
      }

      if(!var2.endsWith("/")) {
         var2 = var2 + "/";
      }

      return var2;
   }

   public int compare(Object var1, Object var2) {
      Cookie var3 = (Cookie)var1;
      Cookie var4 = (Cookie)var2;
      String var5 = this.normalizePath(var3);
      String var6 = this.normalizePath(var4);
      byte var7;
      if(var5.equals(var6)) {
         var7 = 0;
      } else if(var5.startsWith(var6)) {
         var7 = -1;
      } else if(var6.startsWith(var5)) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      return var7;
   }
}
