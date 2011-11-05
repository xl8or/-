package org.acra.util;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.acra.ACRA;
import org.acra.util.FakeSocketFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpRequest {

   UsernamePasswordCredentials creds = null;
   DefaultHttpClient httpClient;
   HttpGet httpGet = null;
   HttpPost httpPost = null;
   HttpContext localContext;


   public HttpRequest(String var1, String var2) {
      if(var1 != null || var2 != null) {
         UsernamePasswordCredentials var3 = new UsernamePasswordCredentials(var1, var2);
         this.creds = var3;
      }

      BasicHttpParams var4 = new BasicHttpParams();
      int var5 = ACRA.getConfig().socketTimeout();
      HttpConnectionParams.setConnectionTimeout(var4, var5);
      int var6 = ACRA.getConfig().socketTimeout();
      HttpConnectionParams.setSoTimeout(var4, var6);
      HttpConnectionParams.setSocketBufferSize(var4, 8192);
      SchemeRegistry var7 = new SchemeRegistry();
      PlainSocketFactory var8 = new PlainSocketFactory();
      Scheme var9 = new Scheme("http", var8, 80);
      var7.register(var9);
      FakeSocketFactory var11 = new FakeSocketFactory();
      Scheme var12 = new Scheme("https", var11, 443);
      var7.register(var12);
      ThreadSafeClientConnManager var14 = new ThreadSafeClientConnManager(var4, var7);
      DefaultHttpClient var15 = new DefaultHttpClient(var14, var4);
      this.httpClient = var15;
      BasicHttpContext var16 = new BasicHttpContext();
      this.localContext = var16;
   }

   public void abort() {
      try {
         if(this.httpClient != null) {
            int var1 = Log.d(ACRA.LOG_TAG, "Abort HttpClient request.");
            this.httpPost.abort();
         }
      } catch (Exception var4) {
         int var3 = Log.e(ACRA.LOG_TAG, "Error while aborting HttpClient request", var4);
      }
   }

   public void clearCookies() {
      this.httpClient.getCookieStore().clear();
   }

   public InputStream getHttpStream(String var1) throws IOException {
      InputStream var2 = null;
      URLConnection var3 = (new URL(var1)).openConnection();
      if(!(var3 instanceof HttpURLConnection)) {
         throw new IOException("Not an HTTP connection");
      } else {
         InputStream var5;
         try {
            HttpURLConnection var4 = (HttpURLConnection)var3;
            var4.setAllowUserInteraction((boolean)0);
            var4.setInstanceFollowRedirects((boolean)1);
            var4.setRequestMethod("GET");
            var4.connect();
            if(var4.getResponseCode() != 200) {
               return var2;
            }

            var5 = var4.getInputStream();
         } catch (Exception var7) {
            throw new IOException("Error connecting");
         }

         var2 = var5;
         return var2;
      }
   }

   public String sendGet(String var1) throws ClientProtocolException, IOException {
      HttpGet var2 = new HttpGet(var1);
      this.httpGet = var2;
      DefaultHttpClient var3 = this.httpClient;
      HttpGet var4 = this.httpGet;
      return EntityUtils.toString(var3.execute(var4).getEntity());
   }

   public String sendPost(String var1, String var2) throws ClientProtocolException, IOException {
      return this.sendPost(var1, var2, (String)null);
   }

   public String sendPost(String var1, String var2, String var3) throws ClientProtocolException, IOException {
      HttpParams var4 = this.httpClient.getParams().setParameter("http.protocol.cookie-policy", "rfc2109");
      HttpPost var5 = new HttpPost(var1);
      this.httpPost = var5;
      int var6 = Log.d(ACRA.LOG_TAG, "Setting httpPost headers");
      if(this.creds != null) {
         HttpPost var7 = this.httpPost;
         Header var8 = BasicScheme.authenticate(this.creds, "UTF-8", (boolean)0);
         var7.addHeader(var8);
      }

      this.httpPost.setHeader("User-Agent", "Android");
      this.httpPost.setHeader("Accept", "text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
      if(var3 != null) {
         this.httpPost.setHeader("Content-Type", var3);
      } else {
         this.httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
      }

      StringEntity var9 = new StringEntity(var2, "UTF-8");
      this.httpPost.setEntity(var9);
      String var10 = ACRA.LOG_TAG;
      String var11 = "Sending request to " + var1;
      Log.d(var10, var11);
      DefaultHttpClient var13 = this.httpClient;
      HttpPost var14 = this.httpPost;
      HttpContext var15 = this.localContext;
      HttpResponse var16 = var13.execute(var14, var15);
      String var19;
      if(var16 != null) {
         if(var16.getStatusLine() != null) {
            String var17 = Integer.toString(var16.getStatusLine().getStatusCode());
            if(var17.startsWith("4") || var17.startsWith("5")) {
               String var18 = "Host returned error code " + var17;
               throw new IOException(var18);
            }
         }

         var19 = EntityUtils.toString(var16.getEntity());
      } else {
         var19 = null;
      }

      return var19;
   }
}
