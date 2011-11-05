package org.apache.commons.httpclient.cookie;

import java.util.Collection;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.MalformedCookieException;

public interface CookieSpec {

   String PATH_DELIM = "/";
   char PATH_DELIM_CHAR = "/".charAt(0);


   boolean domainMatch(String var1, String var2);

   String formatCookie(Cookie var1);

   Header formatCookieHeader(Cookie var1) throws IllegalArgumentException;

   Header formatCookieHeader(Cookie[] var1) throws IllegalArgumentException;

   String formatCookies(Cookie[] var1) throws IllegalArgumentException;

   Collection getValidDateFormats();

   boolean match(String var1, int var2, String var3, boolean var4, Cookie var5);

   Cookie[] match(String var1, int var2, String var3, boolean var4, Cookie[] var5);

   Cookie[] parse(String var1, int var2, String var3, boolean var4, String var5) throws MalformedCookieException, IllegalArgumentException;

   Cookie[] parse(String var1, int var2, String var3, boolean var4, Header var5) throws MalformedCookieException, IllegalArgumentException;

   void parseAttribute(NameValuePair var1, Cookie var2) throws MalformedCookieException, IllegalArgumentException;

   boolean pathMatch(String var1, String var2);

   void setValidDateFormats(Collection var1);

   void validate(String var1, int var2, String var3, boolean var4, Cookie var5) throws MalformedCookieException, IllegalArgumentException;
}
