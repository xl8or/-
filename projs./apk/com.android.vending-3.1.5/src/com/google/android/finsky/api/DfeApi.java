package com.google.android.finsky.api;

import android.net.Uri;
import android.net.Uri.Builder;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeRequest;
import com.google.android.finsky.api.DfeRetryPolicy;
import com.google.android.finsky.api.PaginatedDfeRequest;
import com.google.android.finsky.api.ProtoDfeRequest;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.Browse;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.remoting.protos.BuyInstruments;
import com.google.android.finsky.remoting.protos.DetailsResponse;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.remoting.protos.Log;
import com.google.android.finsky.remoting.protos.PlusOne;
import com.google.android.finsky.remoting.protos.ReviewResponse;
import com.google.android.finsky.remoting.protos.SearchResponse;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.android.finsky.utils.Maps;
import com.google.protobuf.micro.MessageMicro;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DfeApi {

   private static final Uri ADDREVIEW_URI = Uri.parse("addReview");
   public static final Uri BASE_URI = Uri.parse("https://android.clients.google.com/fdfe/");
   private static final Uri CHANNELS_URI = Uri.parse("toc");
   private static final Uri CHECK_INSTRUMENT_URI = Uri.parse("checkInstrument");
   private static final Uri COMPLETE_PURCHASE_URI = Uri.parse("completePurchase");
   private static final Uri LOG_URI = Uri.parse("log");
   private static final Uri PLUSONE_URI = Uri.parse("plusOne");
   private static final float PURCHASE_BACKOFF_MULT = 0.0F;
   private static final int PURCHASE_MAX_RETRIES = 0;
   private static final int PURCHASE_TIMEOUT_MS = ((Integer)G.purchaseStatusTimeoutMs.get()).intValue();
   private static final Uri PURCHASE_URI = Uri.parse("purchase");
   private static final Uri RATEREVIEW_URI = Uri.parse("rateReview");
   private static final String REQUEST_PARAM_ACCEPT_TOS = "ctos";
   private static final String REQUEST_PARAM_CART = "cart";
   private static final String REQUEST_PARAM_CHANNEL = "c";
   private static final String REQUEST_PARAM_CHECKOUT_TOKEN = "ct";
   private static final String REQUEST_PARAM_DOC_ID = "doc";
   private static final String REQUEST_PARAM_IAB_DEV_PAYLOAD = "payload";
   private static final String REQUEST_PARAM_IAB_PACKAGE_NAME = "shpn";
   private static final String REQUEST_PARAM_IAB_PACKAGE_SIGNATURE_HASH = "shh";
   private static final String REQUEST_PARAM_IAB_PACKAGE_VERSION = "shvc";
   private static final String REQUEST_PARAM_OFFER_TYPE = "ot";
   private static final String REQUEST_PARAM_REVIEW_CONTENT = "content";
   private static final String REQUEST_PARAM_REVIEW_ID = "revId";
   private static final String REQUEST_PARAM_REVIEW_RATING = "rating";
   private static final String REQUEST_PARAM_REVIEW_TITLE = "title";
   private static final String REQUEST_PARAM_RISK_HEADER = "chdi";
   private static final String REQUEST_PARAM_SEARCH_QUERY = "q";
   private static final String REQUEST_PARAM_VIDEO_ID = "v";
   private static final Uri SEARCH_CHANNEL_URI = Uri.parse("search");
   private static final Uri UPDATE_INSTRUMENT_URI = Uri.parse("updateInstrument");
   private static final Uri VIDEO_WATCH_URI = Uri.parse("http://www.youtube.com/watch");
   private final DfeApiContext mApiContext;
   private final RequestQueue mQueue;


   public DfeApi(RequestQueue var1, DfeApiContext var2) {
      this.mQueue = var1;
      this.mApiContext = var2;
   }

   public static String createDetailsUrlFromId(int var0, String var1) {
      String var2;
      switch(var0) {
      case 1:
         var2 = "details?doc=book-" + var1;
         break;
      case 2:
      default:
         var2 = null;
         break;
      case 3:
         var2 = "details?doc=app-" + var1;
         break;
      case 4:
         var2 = "details?doc=movie-" + var1;
      }

      return var2;
   }

   public static String formSearchUrl(String var0, int var1) {
      Builder var2 = SEARCH_CHANNEL_URI.buildUpon();
      String var3 = Integer.toString(var1);
      return var2.appendQueryParameter("c", var3).appendQueryParameter("q", var0).build().toString();
   }

   public static String formVideoWatchUrl(String var0) {
      return VIDEO_WATCH_URI.buildUpon().appendQueryParameter("v", var0).build().toString();
   }

   private static Map<String, String> getQueryParameters(Uri var0) {
      HashMap var1 = Maps.newHashMap();
      String var2 = var0.getEncodedQuery();
      if(var2 != null) {
         String[] var3 = var2.split("&");
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String[] var6 = var3[var5].split("=");
            if(var6.length == 2) {
               String var7 = var6[0];
               String var8 = var6[1];
               var1.put(var7, var8);
            }
         }
      }

      return var1;
   }

   public static boolean isTopLevelUrl(String var0) {
      boolean var1;
      if(var0.indexOf("?") != -1 && !var0.matches("\\?c=[0-9]*$")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public Request<?> addReview(String var1, String var2, String var3, int var4, Response.Listener<ReviewResponse> var5, Response.ErrorListener var6) {
      String var7 = ADDREVIEW_URI.toString();
      DfeApiContext var8 = this.mApiContext;
      DfeApi.DfePostRequest var11 = new DfeApi.DfePostRequest(var7, var8, ReviewResponse.class, var5, var6);
      var11.addPostParam("doc", var1);
      var11.addPostParam("title", var2);
      var11.addPostParam("content", var3);
      String var12 = Integer.toString(var4);
      var11.addPostParam("rating", var12);
      return this.mQueue.add(var11);
   }

   public Request<?> checkInstrument(String var1, Response.Listener<BuyInstruments.CheckInstrumentResponse> var2, Response.ErrorListener var3) {
      String var4 = CHECK_INSTRUMENT_URI.toString();
      DfeApiContext var5 = this.mApiContext;
      DfeApi.DfePostRequest var8 = new DfeApi.DfePostRequest(var4, var5, BuyInstruments.CheckInstrumentResponse.class, var2, var3);
      var8.addPostParam("ct", var1);
      return this.mQueue.add(var8);
   }

   public Request<?> completePurchase(String var1, String var2, String var3, Map<String, String> var4, boolean var5, String var6, String var7, Response.Listener<Buy.BuyResponse> var8, Response.ErrorListener var9) {
      String var10 = COMPLETE_PURCHASE_URI.toString();
      DfeApiContext var11 = this.mApiContext;
      DfeApi.DfePostRequest var14 = new DfeApi.DfePostRequest(var10, var11, Buy.BuyResponse.class, var8, var9);
      int var15 = PURCHASE_TIMEOUT_MS;
      DfeApiContext var16 = this.mApiContext;
      DfeRetryPolicy var17 = new DfeRetryPolicy(var15, 0, 0.0F, var16);
      var14.setRetryPolicy(var17);
      var14.addPostParam("doc", var1);
      var14.addPostParam("cart", var2);
      if(var3 != null) {
         var14.addPostParam("ct", var3);
      }

      if(var4 != null) {
         Iterator var18 = var4.entrySet().iterator();

         while(var18.hasNext()) {
            Entry var19 = (Entry)var18.next();
            String var20 = (String)var19.getKey();
            String var21 = (String)var19.getValue();
            var14.addPostParam(var20, var21);
         }
      }

      if(var5) {
         var14.addPostParam("ctos", "true");
      }

      if(var6 != null) {
         var14.addPostParam("chdi", var6);
      }

      if(var7 != null) {
         var14.addPostParam("payload", var7);
      }

      return this.mQueue.add(var14);
   }

   public DfeApiContext getApiContext() {
      return this.mApiContext;
   }

   public Request<?> getBrowseLayout(String var1, Response.Listener<Browse.BrowseResponse> var2, Response.ErrorListener var3) {
      DfeApiContext var4 = this.mApiContext;
      DfeRequest var8 = new DfeRequest(var1, var4, Browse.BrowseResponse.class, var2, var3);
      return this.mQueue.add(var8);
   }

   public Request<?> getBrowserAuthToken(String var1, Response.Listener<String> var2, Response.ErrorListener var3) {
      StringRequest var4 = new StringRequest(var1, var2, var3);
      var4.setShouldCache((boolean)0);
      return this.mQueue.add(var4);
   }

   public Request<?> getChannels(Response.Listener<Toc.TocResponse> var1, Response.ErrorListener var2) {
      String var3 = CHANNELS_URI.toString();
      DfeApiContext var4 = this.mApiContext;
      DfeRequest var7 = new DfeRequest(var3, var4, Toc.TocResponse.class, var1, var2);
      return this.mQueue.add(var7);
   }

   public String getCurrentAccountName() {
      return this.mApiContext.getAccountName();
   }

   public Request<?> getDetails(String var1, Response.Listener<DetailsResponse> var2, Response.ErrorListener var3) {
      DfeApiContext var4 = this.mApiContext;
      DfeRequest var8 = new DfeRequest(var1, var4, DetailsResponse.class, var2, var3);
      return this.mQueue.add(var8);
   }

   public Request<?> getList(String var1, PaginatedDfeRequest.PaginatedListener<DocList.ListResponse> var2, Response.ErrorListener var3) {
      DfeApiContext var4 = this.mApiContext;
      PaginatedDfeRequest var8 = new PaginatedDfeRequest(var1, var4, DocList.ListResponse.class, var2, var3);
      return this.mQueue.add(var8);
   }

   public Request<?> getPurchaseStatus(String var1, Response.Listener<Buy.PurchaseStatusResponse> var2, Response.ErrorListener var3) {
      Uri var4 = Uri.parse(var1);
      String var5 = var4.getLastPathSegment();
      DfeApiContext var6 = this.mApiContext;
      DfeApi.DfePostRequest var9 = new DfeApi.DfePostRequest(var5, var6, Buy.PurchaseStatusResponse.class, var2, var3);
      int var10 = PURCHASE_TIMEOUT_MS;
      DfeApiContext var11 = this.mApiContext;
      DfeRetryPolicy var12 = new DfeRetryPolicy(var10, 0, 0.0F, var11);
      var9.setRetryPolicy(var12);
      Iterator var13 = getQueryParameters(var4).keySet().iterator();

      while(var13.hasNext()) {
         String var14 = (String)var13.next();
         String var15 = var4.getQueryParameter(var14);
         var9.addPostParam(var14, var15);
      }

      return this.mQueue.add(var9);
   }

   public Request<?> getReviews(String var1, PaginatedDfeRequest.PaginatedListener<ReviewResponse> var2, Response.ErrorListener var3) {
      DfeApiContext var4 = this.mApiContext;
      PaginatedDfeRequest var8 = new PaginatedDfeRequest(var1, var4, ReviewResponse.class, var2, var3);
      return this.mQueue.add(var8);
   }

   public void invalidateDetailsCache(String var1, boolean var2) {
      DfeApiContext var3 = this.mApiContext;
      Object var5 = null;
      DfeRequest var6 = new DfeRequest(var1, var3, DetailsResponse.class, (Response.Listener)null, (Response.ErrorListener)var5);
      Cache var7 = FinskyApp.get().getCache();
      String var8 = var6.getCacheKey();
      var7.invalidate(var8, var2);
   }

   public void invalidateReviewsCache(String var1, boolean var2) {
      DfeApiContext var3 = this.mApiContext;
      Object var5 = null;
      PaginatedDfeRequest var6 = new PaginatedDfeRequest(var1, var3, ReviewResponse.class, (PaginatedDfeRequest.PaginatedListener)null, (Response.ErrorListener)var5);
      Cache var7 = FinskyApp.get().getCache();
      String var8 = var6.getCacheKey();
      var7.invalidate(var8, var2);
   }

   public Request<?> log(Log.LogRequest var1, Response.Listener<Log.LogResponse> var2, Response.ErrorListener var3) {
      String var4 = LOG_URI.toString();
      DfeApiContext var5 = this.mApiContext;
      ProtoDfeRequest var9 = new ProtoDfeRequest(var4, var1, var5, Log.LogResponse.class, var2, var3);
      var9.setDrainable((boolean)0);
      return this.mQueue.add(var9);
   }

   public Request<?> makePurchase(String var1, int var2, String var3, String var4, String var5, int var6, Map<String, String> var7, Response.Listener<Buy.BuyResponse> var8, Response.ErrorListener var9) {
      String var10 = PURCHASE_URI.toString();
      DfeApiContext var11 = this.mApiContext;
      DfeApi.DfePostRequest var14 = new DfeApi.DfePostRequest(var10, var11, Buy.BuyResponse.class, var8, var9);
      int var15 = PURCHASE_TIMEOUT_MS;
      DfeApiContext var16 = this.mApiContext;
      DfeRetryPolicy var17 = new DfeRetryPolicy(var15, 0, 0.0F, var16);
      var14.setRetryPolicy(var17);
      var14.addPostParam("doc", var1);
      String var18 = Integer.toString(var2);
      var14.addPostParam("ot", var18);
      if(var3 != null) {
         var14.addPostParam("ct", var3);
      }

      if(var4 != null) {
         var14.addPostParam("shpn", var4);
         var14.addPostParam("shh", var5);
         String var19 = Integer.toString(var6);
         var14.addPostParam("shvc", var19);
      }

      if(var7 != null) {
         Iterator var20 = var7.entrySet().iterator();

         while(var20.hasNext()) {
            Entry var21 = (Entry)var20.next();
            String var22 = (String)var21.getKey();
            String var23 = (String)var21.getValue();
            var14.addPostParam(var22, var23);
         }
      }

      return this.mQueue.add(var14);
   }

   public Request<?> makePurchase(String var1, int var2, String var3, Map<String, String> var4, Response.Listener<Buy.BuyResponse> var5, Response.ErrorListener var6) {
      return this.makePurchase(var1, var2, var3, (String)null, (String)null, -1, var4, var5, var6);
   }

   public Request<?> rateReview(String var1, String var2, int var3, Response.Listener<ReviewResponse> var4, Response.ErrorListener var5) {
      RequestQueue var6 = this.mQueue;
      Builder var7 = RATEREVIEW_URI.buildUpon().appendQueryParameter("doc", var1).appendQueryParameter("revId", var2);
      String var8 = Integer.toString(var3);
      String var9 = var7.appendQueryParameter("rating", var8).build().toString();
      DfeApiContext var10 = this.mApiContext;
      DfeApi.DfePostRequest var13 = new DfeApi.DfePostRequest(var9, var10, ReviewResponse.class, var4, var5);
      return var6.add(var13);
   }

   public Request<?> search(String var1, PaginatedDfeRequest.PaginatedListener<SearchResponse> var2, Response.ErrorListener var3) {
      DfeApiContext var4 = this.mApiContext;
      PaginatedDfeRequest var8 = new PaginatedDfeRequest(var1, var4, SearchResponse.class, var2, var3);
      return this.mQueue.add(var8);
   }

   public Request<?> setPlusOne(String var1, boolean var2, Response.Listener<PlusOne.PlusOneResponse> var3, Response.ErrorListener var4) {
      String var5 = PLUSONE_URI.toString();
      DfeApiContext var6 = this.mApiContext;
      DfeApi.DfePostRequest var9 = new DfeApi.DfePostRequest(var5, var6, PlusOne.PlusOneResponse.class, var3, var4);
      var9.addPostParam("doc", var1);
      byte var10;
      if(var2) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      String var11 = Integer.toString(var10);
      var9.addPostParam("rating", var11);
      return this.mQueue.add(var9);
   }

   public Request<?> updateInstrument(BuyInstruments.UpdateInstrumentRequest var1, String var2, Response.Listener<BuyInstruments.UpdateInstrumentResponse> var3, Response.ErrorListener var4) {
      var1.setCheckoutToken(var2);
      String var6 = UPDATE_INSTRUMENT_URI.toString();
      DfeApiContext var7 = this.mApiContext;
      ProtoDfeRequest var11 = new ProtoDfeRequest(var6, var1, var7, BuyInstruments.UpdateInstrumentResponse.class, var3, var4);
      int var12 = PURCHASE_TIMEOUT_MS;
      DfeApiContext var13 = this.mApiContext;
      DfeRetryPolicy var14 = new DfeRetryPolicy(var12, 0, 0.0F, var13);
      var11.setRetryPolicy(var14);
      return this.mQueue.add(var11);
   }

   private static class DfePostRequest<T extends MessageMicro> extends DfeRequest<T> {

      private final Map<String, String> mPostParams;


      public DfePostRequest(String var1, DfeApiContext var2, Class<T> var3, Response.Listener<T> var4, Response.ErrorListener var5) {
         super(var1, var2, var3, var4, var5);
         HashMap var6 = Maps.newHashMap();
         this.mPostParams = var6;
         this.setShouldCache((boolean)0);
         this.setDrainable((boolean)0);
      }

      public void addPostParam(String var1, String var2) {
         this.mPostParams.put(var1, var2);
      }

      public Map<String, String> getPostParams() {
         return this.mPostParams;
      }
   }
}
