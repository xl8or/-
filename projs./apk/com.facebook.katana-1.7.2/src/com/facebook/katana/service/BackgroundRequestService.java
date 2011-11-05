package com.facebook.katana.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.facebook.katana.provider.LoggingProvider;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;

public class BackgroundRequestService extends IntentService {

   private static final int CONNECT_TIMEOUT = 20000;
   public static final String DATA_PARAM = "com.facebook.katana.service.BackgroundRequestService.data";
   public static final String OPERATION_PARAM = "com.facebook.katana.service.BackgroundRequestService.operation";
   private static final int READ_TIMEOUT = 105000;
   private static final String TAG = "BackgroundRequestService";
   public static final String URI_PARAM = "com.facebook.katana.service.BackgroundRequestService.uri";
   private WakeLock mWakeLock;


   public BackgroundRequestService() {
      super("BackgroundRequestService");
   }

   private void acquireWakeLock(Context var1) {
      if(this.mWakeLock == null) {
         WakeLock var2 = ((PowerManager)var1.getSystemService("power")).newWakeLock(1, "FacebookService");
         this.mWakeLock = var2;
         this.mWakeLock.acquire();
      }
   }

   private HttpRequestBase getLoggingMethod(Intent var1) {
      HttpPost var2 = null;
      String var3 = var1.getStringExtra("com.facebook.katana.service.BackgroundRequestService.data");
      String var4 = var1.getStringExtra("com.facebook.katana.service.BackgroundRequestService.uri");

      HttpPost var5;
      try {
         var5 = new HttpPost(var4);
      } catch (Exception var10) {
         return var2;
      }

      try {
         StringEntity var6 = new StringEntity(var3);
         var5.setEntity(var6);
         var5.addHeader("Content-Type", "application/x-www-form-urlencoded");
      } catch (Exception var9) {
         var2 = var5;
         return var2;
      }

      var2 = var5;
      return var2;
   }

   private HttpRequestBase getRequestMethod(Intent var1) {
      HttpGet var2 = null;
      String var3 = var1.getStringExtra("com.facebook.katana.service.BackgroundRequestService.uri");

      HttpGet var4;
      try {
         var4 = new HttpGet(var3);
      } catch (Exception var6) {
         return var2;
      }

      var2 = var4;
      return var2;
   }

   private void releaseWakeLock() {
      if(this.mWakeLock != null) {
         this.mWakeLock.release();
         this.mWakeLock = null;
      }
   }

   protected void onHandleIntent(Intent var1) {
      this.acquireWakeLock(this);
      BackgroundRequestService.Operation var2 = (BackgroundRequestService.Operation)var1.getSerializableExtra("com.facebook.katana.service.BackgroundRequestService.operation");
      int[] var3 = BackgroundRequestService.1.$SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation;
      int var4 = var2.ordinal();
      HttpRequestBase var5;
      switch(var3[var4]) {
      case 1:
         var5 = this.getLoggingMethod(var1);
         break;
      case 2:
         var5 = this.getRequestMethod(var1);
         break;
      default:
         this.releaseWakeLock();
         return;
      }

      if(var5 == null) {
         this.releaseWakeLock();
      } else {
         try {
            BasicHttpParams var6 = new BasicHttpParams();
            Integer var7 = new Integer(105000);
            var6.setParameter("http.socket.timeout", var7);
            Integer var9 = new Integer(20000);
            var6.setParameter("http.connection.timeout", var9);
            HttpOperation.initSchemeRegistry(this);
            SchemeRegistry var11 = HttpOperation.supportedSchemes();
            SingleClientConnManager var12 = new SingleClientConnManager(var6, var11);
            DefaultHttpClient var13 = new DefaultHttpClient(var12, var6);
            var13.removeRequestInterceptorByClass(RequestAddCookies.class);
            var13.removeResponseInterceptorByClass(ResponseProcessCookies.class);
            HttpResponse var14 = var13.execute(var5);
            int var15 = var14.getStatusLine().getStatusCode();
            var14.getEntity().consumeContent();
            if(var15 == 200) {
               BackgroundRequestService.Operation var16 = BackgroundRequestService.Operation.LOG;
               if(var2 == var16) {
                  ContentResolver var17 = this.getContentResolver();
                  Uri var18 = LoggingProvider.SESSIONS_CONTENT_URI;
                  var17.delete(var18, (String)null, (String[])null);
               }
            }
         } catch (Exception var21) {
            String var20 = var21.toString();
            Log.d("BackgroundRequestService", var20);
         }

         this.releaseWakeLock();
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation = new int[BackgroundRequestService.Operation.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation;
            int var1 = BackgroundRequestService.Operation.LOG.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$service$BackgroundRequestService$Operation;
            int var3 = BackgroundRequestService.Operation.HTTP_REQUEST.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }

   public static enum Operation {

      // $FF: synthetic field
      private static final BackgroundRequestService.Operation[] $VALUES;
      HTTP_REQUEST("HTTP_REQUEST", 1),
      LOG("LOG", 0);


      static {
         BackgroundRequestService.Operation[] var0 = new BackgroundRequestService.Operation[2];
         BackgroundRequestService.Operation var1 = LOG;
         var0[0] = var1;
         BackgroundRequestService.Operation var2 = HTTP_REQUEST;
         var0[1] = var2;
         $VALUES = var0;
      }

      private Operation(String var1, int var2) {}
   }
}
