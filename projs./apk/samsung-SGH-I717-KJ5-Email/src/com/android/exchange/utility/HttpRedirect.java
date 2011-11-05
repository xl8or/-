package com.android.exchange.utility;

import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

public class HttpRedirect {

   private static URI redirectedURI = null;


   public HttpRedirect() {}

   public static URI getRedirectURI(URI var0) {
      redirectedURI = null;
      DefaultHttpClient var1 = new DefaultHttpClient();
      HttpGet var2 = new HttpGet(var0);
      HttpRedirect.CustomRedirectHanlder var3 = new HttpRedirect.CustomRedirectHanlder();
      var1.setRedirectHandler(var3);

      try {
         var1.execute(var2);
      } catch (Exception var6) {
         ;
      }

      return redirectedURI;
   }

   class CustomRedirectHanlder extends DefaultRedirectHandler {

      CustomRedirectHanlder() {}

      public boolean isRedirectRequested(HttpResponse var1, HttpContext var2) {
         int var3 = var1.getStatusLine().getStatusCode();
         if(var3 == 302 || var3 == 301 || var3 == 303 || var3 == 307) {
            Header var4 = var1.getFirstHeader("location");
            if(var4 != null) {
               URI var5 = HttpRedirect.redirectedURI = URI.create(var4.getValue());
            }
         }

         return false;
      }
   }
}
