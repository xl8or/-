package com.android.volley.toolbox;

import com.android.volley.AuthFailureException;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientStack implements HttpStack {

   protected final HttpClient mClient;


   public HttpClientStack(HttpClient var1) {
      this.mClient = var1;
   }

   private static void addHeaders(HttpUriRequest var0, Map<String, String> var1) {
      Iterator var2 = var1.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = (String)var1.get(var3);
         var0.setHeader(var3, var4);
      }

   }

   private static List<NameValuePair> getPostParameterPairs(Map<String, String> var0) {
      int var1 = var0.size();
      ArrayList var2 = new ArrayList(var1);
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         String var5 = (String)var0.get(var4);
         BasicNameValuePair var6 = new BasicNameValuePair(var4, var5);
         var2.add(var6);
      }

      return var2;
   }

   protected void onPrepareRequest(HttpUriRequest var1) throws IOException {}

   public HttpResponse performRequest(Request<?> var1, Map<String, String> var2) throws IOException, AuthFailureException {
      byte[] var3 = var1.getPostBody();
      Object var8;
      if(var3 != null) {
         String var4 = var1.getUrl();
         HttpPost var5 = new HttpPost(var4);
         String var6 = var1.getPostBodyContentType();
         var5.addHeader("Content-Type", var6);
         ByteArrayEntity var7 = new ByteArrayEntity(var3);
         var5.setEntity(var7);
         var8 = var5;
      } else {
         String var12 = var1.getUrl();
         var8 = new HttpGet(var12);
      }

      addHeaders((HttpUriRequest)var8, var2);
      Map var9 = var1.getHeaders();
      addHeaders((HttpUriRequest)var8, var9);
      this.onPrepareRequest((HttpUriRequest)var8);
      HttpParams var10 = ((HttpUriRequest)var8).getParams();
      int var11 = var1.getTimeoutMs();
      HttpConnectionParams.setConnectionTimeout(var10, 5000);
      HttpConnectionParams.setSoTimeout(var10, var11);
      return this.mClient.execute((HttpUriRequest)var8);
   }
}
