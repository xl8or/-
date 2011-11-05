package com.android.volley.toolbox;

import android.content.Context;
import com.android.volley.AuthFailureException;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.UrlTools;
import com.google.android.finsky.utils.Maps;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class HurlStack implements HttpStack {

   private static final boolean DEBUG = VolleyLog.DEBUG;
   private final Context mContext;


   public HurlStack(Context var1) {
      this.mContext = var1;
   }

   private static HttpEntity entityFromConnection(HttpURLConnection var0) {
      BasicHttpEntity var1 = new BasicHttpEntity();

      InputStream var3;
      label13: {
         InputStream var2;
         try {
            var2 = var0.getInputStream();
         } catch (IOException var9) {
            var3 = var0.getErrorStream();
            break label13;
         }

         var3 = var2;
      }

      var1.setContent(var3);
      long var4 = (long)var0.getContentLength();
      var1.setContentLength(var4);
      String var6 = var0.getContentEncoding();
      var1.setContentEncoding(var6);
      String var7 = var0.getContentType();
      var1.setContentType(var7);
      return var1;
   }

   private void handlePost(HttpURLConnection var1, Request<?> var2) throws IOException, AuthFailureException {
      byte[] var3 = var2.getPostBody();
      if(var3 != null) {
         var1.setDoOutput((boolean)1);
         var1.setRequestMethod("POST");
         String var4 = var2.getPostBodyContentType();
         var1.addRequestProperty("Content-Type", var4);
         OutputStream var5 = var1.getOutputStream();
         DataOutputStream var6 = new DataOutputStream(var5);
         var6.write(var3);
         var6.close();
      }
   }

   private HttpURLConnection openConnection(URL var1, Request<?> var2) throws IOException {
      HttpURLConnection var3 = (HttpURLConnection)var1.openConnection();
      int var4 = var2.getTimeoutMs();
      var3.setConnectTimeout(var4);
      var3.setReadTimeout(var4);
      var3.setUseCaches((boolean)0);
      var3.setDoInput((boolean)1);
      return var3;
   }

   public HttpResponse performRequest(Request<?> var1, Map<String, String> var2) throws IOException, AuthFailureException {
      String var3 = var1.getUrl();
      HashMap var4 = Maps.newHashMap();
      Map var5 = var1.getHeaders();
      var4.putAll(var5);
      var4.putAll(var2);
      String var7 = UrlTools.rewrite(this.mContext, var3);
      if(DEBUG) {
         Object[] var8 = new Object[]{var3, var7};
         VolleyLog.v("HTTP get %s -> %s", var8);
      }

      if(var7 == null) {
         String var9 = "URL blocked by UrlRules: " + var3;
         throw new IOException(var9);
      } else {
         URL var10 = new URL(var7);
         HttpURLConnection var13 = this.openConnection(var10, var1);
         Iterator var14 = var4.keySet().iterator();

         while(var14.hasNext()) {
            String var15 = (String)var14.next();
            String var16 = (String)var4.get(var15);
            var13.addRequestProperty(var15, var16);
         }

         this.handlePost(var13, var1);
         byte var19 = 1;
         byte var20 = 1;
         ProtocolVersion var21 = new ProtocolVersion("HTTP", var19, var20);
         if(var13.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
         } else {
            int var22 = var13.getResponseCode();
            String var23 = var13.getResponseMessage();
            BasicStatusLine var24 = new BasicStatusLine(var21, var22, var23);
            BasicHttpResponse var25 = new BasicHttpResponse(var24);
            HttpEntity var26 = entityFromConnection(var13);
            var25.setEntity(var26);
            Iterator var27 = var13.getHeaderFields().entrySet().iterator();

            while(var27.hasNext()) {
               Entry var28 = (Entry)var27.next();
               if(var28.getKey() != null) {
                  String var29 = (String)var28.getKey();
                  String var30 = (String)((List)var28.getValue()).get(0);
                  BasicHeader var31 = new BasicHeader(var29, var30);
                  var25.addHeader(var31);
               }
            }

            return var25;
         }
      }
   }
}
