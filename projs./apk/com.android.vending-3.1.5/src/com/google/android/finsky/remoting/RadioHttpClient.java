package com.google.android.finsky.remoting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Proxy;
import android.net.Uri;
import com.android.volley.AuthFailureException;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpClientStack;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.GservicesValue;
import com.google.android.finsky.utils.FinskyLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpParams;

public class RadioHttpClient extends HttpClientStack {

   private static final String CONNECTION_TYPE_DUN = "DUN";
   private static final String CONNECTION_TYPE_HIPRI = "HIPRI";
   private static final String CONNECTION_TYPE_MMS = "MMS";
   private static final String CONNECTION_TYPE_SUPL = "SUPL";
   private static final int PHONE_APN_ALREADY_ACTIVE = 0;
   private static final int PHONE_APN_REQUEST_STARTED = 1;
   private static final String PHONE_FEATURE_ENABLE_DUN = "enableDUN";
   private static final String PHONE_FEATURE_ENABLE_HIPRI = "enableHIPRI";
   private static final String PHONE_FEATURE_ENABLE_MMS = "enableMMS";
   private static final String PHONE_FEATURE_ENABLE_SUPL = "enableSUPL";
   private static final int POLL_PERIOD_MS = 500;
   private static final int POLL_TIMEOUT_MS = 5000;
   private static final GservicesValue<Boolean> USE_RADIO = GservicesValue.value("vending.use_radio", (boolean)1);
   private ConnectivityManager mConnMgr;
   private int mLastMobileConnectionType;
   private String mLastPhoneFeature;
   private AtomicInteger mNumPendingRequests;
   private final boolean mPerformOverRadio;


   public RadioHttpClient(Context var1, HttpClient var2) {
      super(var2);
      AtomicInteger var3 = new AtomicInteger(0);
      this.mNumPendingRequests = var3;
      this.mLastMobileConnectionType = -1;
      this.mLastPhoneFeature = "PHONE_FEATURE_ENABLE_HIPRI";
      boolean var4 = ((Boolean)USE_RADIO.get()).booleanValue();
      this.mPerformOverRadio = var4;
      ConnectivityManager var5 = (ConnectivityManager)var1.getSystemService("connectivity");
      this.mConnMgr = var5;
   }

   private void ensureRouteToHost(String var1) throws IOException {
      if(this.mPerformOverRadio) {
         if(!var1.startsWith("http://") && !var1.startsWith("https://")) {
            var1 = "http://" + var1;
         }

         Uri var2 = Uri.parse(var1);

         InetAddress var3;
         try {
            var3 = InetAddress.getByName(var2.getHost());
         } catch (UnknownHostException var18) {
            String var17 = "Cannot establish route for " + var1 + ": Unknown host";
            throw new IOException(var17);
         }

         byte[] var5 = var3.getAddress();
         int var6 = (var5[3] & 255) << 24;
         int var7 = (var5[2] & 255) << 16;
         int var8 = var6 | var7;
         int var9 = (var5[1] & 255) << 8;
         int var10 = var8 | var9;
         int var11 = var5[0] & 255;
         int var12 = var10 | var11;
         ConnectivityManager var13 = this.mConnMgr;
         int var14 = this.mLastMobileConnectionType;
         if(!var13.requestRouteToHost(var14, var12)) {
            String var15 = "Cannot establish route to " + var3 + " for " + var1;
            throw new IOException(var15);
         }
      }
   }

   private void fetchEntity(HttpResponse var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      var1.getEntity().writeTo(var2);
      var2.close();
      byte[] var3 = var2.toByteArray();
      ByteArrayEntity var4 = new ByteArrayEntity(var3);
      var1.setEntity(var4);
   }

   private boolean isRadioActive(int var1) {
      boolean var2 = this.mConnMgr.getNetworkInfo(var1).isConnected();
      if(var2) {
         this.mLastMobileConnectionType = var1;
      }

      return var2;
   }

   private void perhapsStopUsingRadio() {
      int var1 = this.mNumPendingRequests.decrementAndGet();
      if(FinskyLog.DEBUG) {
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(this.mNumPendingRequests.get());
         var2[0] = var3;
         FinskyLog.d("Pending requests: %d", var2);
      }

      if(this.mPerformOverRadio) {
         if(var1 == 0) {
            Object[] var4 = new Object[0];
            FinskyLog.d("Giving back radio.", var4);
            ConnectivityManager var5 = this.mConnMgr;
            String var6 = this.mLastPhoneFeature;
            var5.stopUsingNetworkFeature(0, var6);
         }
      }
   }

   private void setProxyForRequest(HttpUriRequest var1) throws IOException {
      String var2 = (String)G.vendingDcbProxyHost.get();
      int var3 = ((Integer)G.vendingDcbProxyPort.get()).intValue();
      if(var2 == null) {
         var2 = Proxy.getDefaultHost();
      }

      if(var3 == -1) {
         var3 = Proxy.getDefaultPort();
      }

      if(var2 != null) {
         if(!var2.equals("")) {
            if(var3 > 0) {
               this.ensureRouteToHost(var2);
               HttpParams var4 = var1.getParams();
               HttpHost var5 = new HttpHost(var2, var3);
               ConnRouteParams.setDefaultProxy(var4, var5);
            }
         }
      }
   }

   private boolean startRadio(String var1) throws IOException {
      byte var2 = 0;
      int var3 = this.mConnMgr.startUsingNetworkFeature(var2, var1);
      switch(var3) {
      case 0:
         var2 = 1;
         break;
      case 1:
         if(FinskyLog.DEBUG) {
            StringBuilder var5 = (new StringBuilder()).append(var1).append(": APN request started: ");
            Thread var6 = Thread.currentThread();
            String var7 = var5.append(var6).toString();
            Object[] var8 = new Object[0];
            FinskyLog.v(var7, var8);
         }
         break;
      default:
         String var4 = var1 + ": Start network failed - " + var3;
         throw new IOException(var4);
      }

      return (boolean)var2;
   }

   private boolean startRadioAndWait(int var1, String var2) throws IOException, InterruptedException {
      boolean var4;
      if(this.isRadioActive(var1)) {
         this.mConnMgr.startUsingNetworkFeature(0, var2);
         var4 = true;
      } else if(this.startRadio(var2)) {
         this.mLastMobileConnectionType = var1;
         this.mLastPhoneFeature = var2;
         var4 = true;
      } else {
         int var5 = ((Integer)G.vendingDcbPollTimeoutMs.get()).intValue();
         if(this.waitForRadio(var1, var5, 500)) {
            var4 = true;
         } else {
            var4 = false;
         }
      }

      return var4;
   }

   private void startRadiosAndWait() throws IOException, InterruptedException {
      int var1 = this.mNumPendingRequests.incrementAndGet();
      if(!this.mPerformOverRadio) {
         Object[] var2 = new Object[0];
         FinskyLog.d("Not using radio.", var2);
      } else {
         String var3 = (String)G.vendingDcbConnectionType.get();
         byte var7;
         String var8;
         if("HIPRI".equals(var3)) {
            if(this.startRadioAndWait(5, "enableHIPRI")) {
               return;
            }

            if(!((Boolean)G.vendingDcbMmsFallback.get()).booleanValue()) {
               Object[] var4 = new Object[0];
               FinskyLog.d("MOBILE_HIPRI connection hasn\'t come up, exiting", var4);
               String var5 = "Could not bring up connection type " + var3;
               throw new IOException(var5);
            }

            Object[] var6 = new Object[0];
            FinskyLog.d("MOBILE_HIPRI connection hasn\'t come up, trying MOBILE_MMS...", var6);
            var7 = 2;
            var8 = "enableMMS";
         } else if("SUPL".equals(var3)) {
            var7 = 3;
            var8 = "enableSUPL";
         } else if("DUN".equals(var3)) {
            var7 = 4;
            var8 = "enableDUN";
         } else {
            var7 = 2;
            var8 = "enableMMS";
         }

         if(!this.startRadioAndWait(var7, var8)) {
            String var9 = "Could not bring up connection type " + var3;
            throw new IOException(var9);
         }
      }
   }

   private void throwExceptionIfError(HttpResponse var1) throws IOException {
      int var2 = var1.getStatusLine().getStatusCode();
      if(var2 != 200) {
         if(var2 != 503) {
            StringBuilder var16 = (new StringBuilder()).append("Unexpected HTTP status code from carrier: ").append(var2).append(" ");
            String var17 = var1.getStatusLine().getReasonPhrase();
            String var18 = var16.append(var17).toString();
            throw new IOException(var18);
         } else {
            Header[] var3 = var1.getAllHeaders();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Header var6 = var3[var5];
               if(var6.getName().trim().toLowerCase().equals("retry-after")) {
                  try {
                     int var7 = Integer.parseInt(var6.getValue());
                     throw new RadioHttpClient.RetryAfterIOException(var7);
                  } catch (NumberFormatException var19) {
                     StringBuilder var9 = (new StringBuilder()).append("Invalid retry after: ");
                     String var10 = var6.getValue();
                     String var11 = var9.append(var10).toString();
                     Object[] var12 = new Object[0];
                     FinskyLog.e(var11, var12);
                     StringBuilder var13 = (new StringBuilder()).append("Unexpected: ").append(var2).append(" ");
                     String var14 = var1.getStatusLine().getReasonPhrase();
                     String var15 = var13.append(var14).toString();
                     throw new IOException(var15);
                  }
               }
            }

         }
      }
   }

   private boolean waitForRadio(int var1, int var2, int var3) throws InterruptedException {
      boolean var4 = true;
      long var5 = System.currentTimeMillis();

      while(true) {
         long var7 = System.currentTimeMillis();
         long var9 = (long)var2 + var5;
         if(var7 < var9) {
            Thread.sleep((long)var3);
            if(!this.isRadioActive(var1)) {
               continue;
            }

            if(FinskyLog.DEBUG) {
               Object[] var11 = new Object[3];
               Long var12 = Long.valueOf(System.currentTimeMillis() - var5);
               var11[0] = var12;
               Integer var13 = Integer.valueOf(var2);
               var11[1] = var13;
               Integer var14 = Integer.valueOf(var3);
               var11[2] = var14;
               FinskyLog.v("Radio came up after %dms (timeoutMs=%d, pollIntervalMs=%d).", var11);
            }
            break;
         }

         var4 = false;
         break;
      }

      return var4;
   }

   protected void onPrepareRequest(HttpUriRequest var1) throws IOException {
      String var2 = var1.getURI().toString();
      this.ensureRouteToHost(var2);
      this.setProxyForRequest(var1);
   }

   public HttpResponse performRequest(Request<?> var1, Map<String, String> var2) throws IOException, AuthFailureException {
      HttpResponse var3;
      try {
         this.startRadiosAndWait();
         var3 = super.performRequest(var1, var2);
         this.fetchEntity(var3);
         this.throwExceptionIfError(var3);
      } catch (InterruptedException var11) {
         StringBuilder var5 = (new StringBuilder()).append("Error while waiting for network ");
         String var6 = var11.getMessage();
         String var7 = var5.append(var6).toString();
         throw new IOException(var7);
      } finally {
         this.perhapsStopUsingRadio();
      }

      return var3;
   }

   public static class RetryAfterIOException extends IOException {

      private int mRetryAfterSeconds;


      public RetryAfterIOException(int var1) {
         this.mRetryAfterSeconds = var1;
      }

      public int getRetryAfterSeconds() {
         return this.mRetryAfterSeconds;
      }
   }
}
