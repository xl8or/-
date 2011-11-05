package gnu.inet.http;

import gnu.inet.http.Cookie;
import gnu.inet.http.CookieManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleCookieManager implements CookieManager {

   protected Map cookies;


   public SimpleCookieManager() {
      HashMap var1 = new HashMap();
      this.cookies = var1;
   }

   private void addCookies(List var1, String var2, boolean var3, String var4, Date var5) {
      Map var6 = (Map)this.cookies.get(var2);
      if(var6 != null) {
         ArrayList var7 = new ArrayList();
         Iterator var8 = var6.entrySet().iterator();

         while(var8.hasNext()) {
            Entry var9 = (Entry)var8.next();
            Cookie var10 = (Cookie)var9.getValue();
            Date var11 = var10.getExpiryDate();
            if(var11 != null && var11.before(var5)) {
               Object var12 = var9.getKey();
               var7.add(var12);
            } else if(!var3 || var10.isSecure()) {
               String var14 = var10.getPath();
               if(var4.startsWith(var14)) {
                  var1.add(var10);
               }
            }
         }

         Iterator var16 = var7.iterator();

         while(var16.hasNext()) {
            Object var17 = var16.next();
            var6.remove(var17);
         }

      }
   }

   public Cookie[] getCookies(String var1, boolean var2, String var3) {
      ArrayList var4 = new ArrayList();
      Date var5 = new Date();
      String var7;
      if(Character.isLetter(var1.charAt(0))) {
         int var6 = var1.indexOf(46);

         String var11;
         for(var7 = var1; var6 != -1; var7 = var11) {
            this.addCookies(var4, var7, var2, var3, var5);
            var11 = var7.substring(var6);
            var6 = var11.indexOf(46, 1);
         }
      } else {
         var7 = var1;
      }

      this.addCookies(var4, var7, var2, var3, var5);
      Cookie[] var15 = new Cookie[var4.size()];
      var4.toArray(var15);
      return var15;
   }

   public void setCookie(Cookie var1) {
      String var2 = var1.getDomain();
      Object var3 = (Map)this.cookies.get(var2);
      if(var3 == null) {
         var3 = new HashMap();
         this.cookies.put(var2, var3);
      }

      String var5 = var1.getName();
      ((Map)var3).put(var5, var1);
   }
}
