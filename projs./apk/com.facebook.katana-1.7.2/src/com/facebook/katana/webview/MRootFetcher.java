package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.webview.FacebookAuthentication;
import com.facebook.katana.webview.MRoot;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;

class MRootFetcher extends HttpOperation {

   MRootFetcher(Context var1, Tuple<String, String> var2, Handler var3, NetworkRequestCallback<Tuple<String, String>, Tuple<String, String>, Tuple<MRoot.LoadError, String>> var4) throws IOException {
      String var5 = (String)var2.d0;
      String var6 = AuthDeepLinkMethod.getDeepLinkUrl(var1, var5);
      ByteArrayOutputStream var7 = new ByteArrayOutputStream(8192);
      MRootFetcher.Listener var8 = new MRootFetcher.Listener(var1, var2, var3, var4);
      String var9 = (String)var2.d1;
      BasicHttpContext var10 = new BasicHttpContext();
      super(var1, "GET", var6, var7, var8, var9, "text/xml, text/html, application/xhtml+xml, image/png, text/plain, */*;q=0.8", var10);
   }

   static class Listener implements HttpOperation.HttpOperationListener {

      public final Tuple<String, String> arg;
      protected NetworkRequestCallback<Tuple<String, String>, Tuple<String, String>, Tuple<MRoot.LoadError, String>> mCb;
      protected Context mContext;
      protected Handler mHandler;


      Listener(Context var1, Tuple<String, String> var2, Handler var3, NetworkRequestCallback<Tuple<String, String>, Tuple<String, String>, Tuple<MRoot.LoadError, String>> var4) {
         this.arg = var2;
         this.mContext = var1;
         this.mHandler = var3;
         this.mCb = var4;
      }

      protected static String getFinalUrl(HttpContext var0) {
         HttpRequest var1 = (HttpRequest)var0.getAttribute("http.request");
         String var2;
         if(!(var1 instanceof HttpUriRequest)) {
            var2 = null;
         } else {
            String var3 = ((HttpUriRequest)var1).getURI().toString();
            Uri var4 = Uri.parse(var3);
            if(var4.isAbsolute()) {
               var2 = var3;
            } else {
               HttpHost var5 = (HttpHost)var0.getAttribute("http.target_host");
               Builder var6 = var4.buildUpon();
               String var7 = var5.getSchemeName();
               var6.scheme(var7);
               String var9 = var5.getHostName();
               var6.authority(var9);
               var2 = var6.toString();
            }
         }

         return var2;
      }

      public void onHttpOperationComplete(HttpOperation var1, int var2, String var3, OutputStream var4, Exception var5) {
         Tuple var6 = this.processResponse(var1, var2, var3, var4, var5);
         if(var6 != null) {
            Handler var7 = this.mHandler;
            MRootFetcher.Listener.1 var8 = new MRootFetcher.Listener.1(var6);
            var7.post(var8);
         }
      }

      public void onHttpOperationProgress(HttpOperation var1, long var2, long var4) {}

      public Tuple<MRoot.LoadError, String> processResponse(HttpOperation var1, int var2, String var3, OutputStream var4, Exception var5) {
         Tuple var7;
         if(var2 == 200 && var5 == null) {
            String var8 = getFinalUrl(var1.httpContext);
            String var9 = (String)this.arg.d0;
            if(!FacebookAuthentication.matchUrlLiberally(var8, var9)) {
               MRoot.LoadError var10 = MRoot.LoadError.UNEXPECTED_REDIRECT;
               var7 = new Tuple(var10, var8);
            } else {
               try {
                  byte[] var11 = ((ByteArrayOutputStream)var4).toByteArray();
                  String var12 = new String(var11);
                  JSONArray var13 = new JSONArray();
                  var13.put(var8);
                  var13.put(var12);
                  Tuple var16 = new Tuple(var8, var12);
                  Handler var17 = this.mHandler;
                  MRootFetcher.Listener.2 var18 = new MRootFetcher.Listener.2(var13, var16);
                  var17.post(var18);
               } catch (Exception var22) {
                  MRoot.LoadError var21 = MRoot.LoadError.UNKNOWN_ERROR;
                  var7 = new Tuple(var21, (Object)null);
                  return var7;
               }

               var7 = null;
            }
         } else {
            MRoot.LoadError var6 = MRoot.LoadError.NETWORK_ERROR;
            var7 = new Tuple(var6, (Object)null);
         }

         return var7;
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final Tuple val$errData;


         1(Tuple var2) {
            this.val$errData = var2;
         }

         public void run() {
            NetworkRequestCallback var1 = Listener.this.mCb;
            Context var2 = Listener.this.mContext;
            Tuple var3 = Listener.this.arg;
            Tuple var4 = this.val$errData;
            Object var5 = null;
            var1.callback(var2, (boolean)0, var3, (String)null, var5, var4);
         }
      }

      class 2 implements Runnable {

         // $FF: synthetic field
         final JSONArray val$array;
         // $FF: synthetic field
         final Tuple val$deserialized;


         2(JSONArray var2, Tuple var3) {
            this.val$array = var2;
            this.val$deserialized = var3;
         }

         public void run() {
            NetworkRequestCallback var1 = Listener.this.mCb;
            Context var2 = Listener.this.mContext;
            Tuple var3 = Listener.this.arg;
            String var4 = this.val$array.toString();
            Tuple var5 = this.val$deserialized;
            var1.callback(var2, (boolean)1, var3, var4, var5, (Object)null);
         }
      }
   }
}
