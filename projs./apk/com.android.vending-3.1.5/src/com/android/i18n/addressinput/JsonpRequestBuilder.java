package com.android.i18n.addressinput;

import com.android.i18n.addressinput.JsoMap;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

class JsonpRequestBuilder {

   JsonpRequestBuilder() {}

   private static String encodeUrl(String param0) {
      // $FF: Couldn't be decompiled
   }

   void requestObject(String var1, JsonpRequestBuilder.AsyncCallback<JsoMap> var2) {
      String var3 = encodeUrl(var1);
      HttpGet var4 = new HttpGet(var3);
      (new JsonpRequestBuilder.AsyncHttp(var4, var2)).start();
   }

   void setTimeout(int var1) {
      HttpParams var2 = JsonpRequestBuilder.AsyncHttp.CLIENT.getParams();
      HttpConnectionParams.setConnectionTimeout(var2, var1);
      HttpConnectionParams.setSoTimeout(var2, var1);
   }

   private static class AsyncHttp extends Thread {

      private static final HttpClient CLIENT = new DefaultHttpClient();
      private JsonpRequestBuilder.AsyncCallback<JsoMap> mCallback;
      private HttpUriRequest mRequest;


      protected AsyncHttp(HttpUriRequest var1, JsonpRequestBuilder.AsyncCallback<JsoMap> var2) {
         this.mRequest = var1;
         this.mCallback = var2;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   interface AsyncCallback<T extends Object> {

      void onFailure(Throwable var1);

      void onSuccess(T var1);
   }
}
