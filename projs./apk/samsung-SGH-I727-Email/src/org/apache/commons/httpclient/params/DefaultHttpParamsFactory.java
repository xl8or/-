package org.apache.commons.httpclient.params;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.httpclient.params.HttpParamsFactory;

public class DefaultHttpParamsFactory implements HttpParamsFactory {

   private HttpParams httpParams;


   public DefaultHttpParamsFactory() {}

   protected HttpParams createParams() {
      HttpClientParams var1 = new HttpClientParams((HttpParams)null);
      var1.setParameter("http.useragent", "Jakarta Commons-HttpClient/3.1");
      HttpVersion var2 = HttpVersion.HTTP_1_1;
      var1.setVersion(var2);
      var1.setConnectionManagerClass(SimpleHttpConnectionManager.class);
      var1.setCookiePolicy("default");
      var1.setHttpElementCharset("US-ASCII");
      var1.setContentCharset("ISO-8859-1");
      DefaultHttpMethodRetryHandler var3 = new DefaultHttpMethodRetryHandler();
      var1.setParameter("http.method.retry-handler", var3);
      ArrayList var4 = new ArrayList();
      String[] var5 = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
      List var6 = Arrays.asList(var5);
      var4.addAll(var6);
      var1.setParameter("http.dateparser.patterns", var4);
      String var8 = null;

      String var9;
      label58: {
         try {
            var9 = System.getProperty("httpclient.useragent");
         } catch (SecurityException var19) {
            break label58;
         }

         var8 = var9;
      }

      if(var8 != null) {
         var1.setParameter("http.useragent", var8);
      }

      String var10 = null;

      label51: {
         try {
            var9 = System.getProperty("httpclient.authentication.preemptive");
         } catch (SecurityException var18) {
            break label51;
         }

         var10 = var9;
      }

      if(var10 != null) {
         var10 = var10.trim().toLowerCase();
         if(var10.equals("true")) {
            Boolean var11 = Boolean.TRUE;
            var1.setParameter("http.authentication.preemptive", var11);
         } else if(var10.equals("false")) {
            Boolean var13 = Boolean.FALSE;
            var1.setParameter("http.authentication.preemptive", var13);
         }
      }

      String var12 = null;

      label44: {
         try {
            var9 = System.getProperty("apache.commons.httpclient.cookiespec");
         } catch (SecurityException var17) {
            break label44;
         }

         var12 = var9;
      }

      if(var12 != null) {
         if("COMPATIBILITY".equalsIgnoreCase(var12)) {
            var1.setCookiePolicy("compatibility");
         } else if("NETSCAPE_DRAFT".equalsIgnoreCase(var12)) {
            var1.setCookiePolicy("netscape");
         } else if("RFC2109".equalsIgnoreCase(var12)) {
            var1.setCookiePolicy("rfc2109");
         }
      }

      return var1;
   }

   public HttpParams getDefaultParams() {
      synchronized(this){}

      HttpParams var2;
      try {
         if(this.httpParams == null) {
            HttpParams var1 = this.createParams();
            this.httpParams = var1;
         }

         var2 = this.httpParams;
      } finally {
         ;
      }

      return var2;
   }
}
