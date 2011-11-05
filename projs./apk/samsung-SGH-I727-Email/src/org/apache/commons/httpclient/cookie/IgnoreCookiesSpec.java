package org.apache.commons.httpclient.cookie;

import java.util.Collection;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.cookie.MalformedCookieException;

public class IgnoreCookiesSpec implements CookieSpec {

   public IgnoreCookiesSpec() {}

   public boolean domainMatch(String var1, String var2) {
      return false;
   }

   public String formatCookie(Cookie var1) {
      return null;
   }

   public Header formatCookieHeader(Cookie var1) throws IllegalArgumentException {
      return null;
   }

   public Header formatCookieHeader(Cookie[] var1) throws IllegalArgumentException {
      return null;
   }

   public String formatCookies(Cookie[] var1) throws IllegalArgumentException {
      return null;
   }

   public Collection getValidDateFormats() {
      return null;
   }

   public boolean match(String var1, int var2, String var3, boolean var4, Cookie var5) {
      return false;
   }

   public Cookie[] match(String var1, int var2, String var3, boolean var4, Cookie[] var5) {
      return new Cookie[0];
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, String var5) throws MalformedCookieException {
      return new Cookie[0];
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, Header var5) throws MalformedCookieException, IllegalArgumentException {
      return new Cookie[0];
   }

   public void parseAttribute(NameValuePair var1, Cookie var2) throws MalformedCookieException, IllegalArgumentException {}

   public boolean pathMatch(String var1, String var2) {
      return false;
   }

   public void setValidDateFormats(Collection var1) {}

   public void validate(String var1, int var2, String var3, boolean var4, Cookie var5) throws MalformedCookieException, IllegalArgumentException {}
}
