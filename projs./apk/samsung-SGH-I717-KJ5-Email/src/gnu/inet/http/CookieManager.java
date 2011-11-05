package gnu.inet.http;

import gnu.inet.http.Cookie;

public interface CookieManager {

   Cookie[] getCookies(String var1, boolean var2, String var3);

   void setCookie(Cookie var1);
}
