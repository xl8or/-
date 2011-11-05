package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.ApiLogging;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class ApiMethod {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   protected static final String ALBUM_ID_PARAM = "aid";
   public static final int API_EC_PARAM_SESSION_KEY = 102;
   public static final String API_KEY_PARAM = "api_key";
   public static final String API_VERSION = "1.0";
   public static final String APPLICATION_API_KEY = "882a8490361da98702bf97a021ddc14d";
   protected static final String APP_SECRET = "62f8ce9f74b12f84c123cc23437a4a32";
   protected static final String BODY_PARAM = "body";
   public static final String CALL_ID_PARAM = "call_id";
   protected static final String CAPTION_PARAM = "caption";
   protected static final String CHECKIN_ID_PARAM = "checkin_id";
   protected static final String CONFIRM_PARAM = "confirm";
   protected static final String COUNTRY_CODE_PARAM = "country_code";
   protected static final String DESCRIPTION_PARAM = "description";
   protected static final String EID_PARAM = "eid";
   protected static final String EMAIL_PARAM = "email";
   protected static final String ERROR_CODE = "error_code";
   protected static final String ERROR_MSG = "error_msg";
   public static final String EXTRA_SESSION_SECRET = "ApiMethod.secret";
   public static final long FACEBOOK_APP_ID = 350685531728L;
   protected static final String FOLDER_PARAM = "folder";
   protected static final String FORMAT_PARAM = "format";
   protected static final String JSON_FORMAT = "JSON";
   protected static final String LIMIT_PARAM = "limit";
   protected static final String LOCATION_PARAM = "location";
   protected static final String MESSAGE_PARAM = "message";
   public static final String METHOD_PARAM = "method";
   protected static final String MIGRATIONS_OVERRIDE_PARAM = "migrations_override";
   protected static final String NAME_PARAM = "name";
   protected static final String PAGE_ID_PARAM = "page_id";
   protected static final String PASSWORD_PARAM = "password";
   protected static final String PHOTO_IDS_PARAM = "pids";
   protected static final String PHOTO_ID_PARAM = "pid";
   protected static final String POST_ID_PARAM = "post_id";
   protected static final String PUSH_PROTOCOL_PARAMS = "protocol_params";
   protected static final String PUSH_SETTINGS_PARAM = "settings";
   protected static final String QUERIES_PARAM = "queries";
   protected static final String QUERY_PARAM = "query";
   protected static final String RSVP_STATUS_PARAM = "rsvp_status";
   public static final String SESSION_KEY_PARAM = "session_key";
   public static final String SIG_PARAM = "sig";
   protected static final String SOURCE_IDS_PARAM = "source_ids";
   protected static final String SSL_RESOURCES_PARAM = "return_ssl_resources";
   protected static final String START_PARAM = "start";
   protected static final String SUBJECT_PARAM = "subject";
   private static final String TAG = "ApiMethod";
   protected static final String TAGGED_UIDS_PARAM = "tagged_uids";
   protected static final String TAGS_PARAM = "tags";
   protected static final String THREAD_ID_PARAM = "tid";
   protected static final String TITLE_PARAM = "title";
   protected static final String UIDS_PARAM = "uids";
   public static final String UID_PARAM = "uid";
   public static final String VERSION_PARAM = "v";
   protected static final String VIEWER_ID_PARAM = "viewer_id";
   protected static final String VISIBILITY_PARAM = "visible";
   protected static final Handler mHandler;
   protected static final FBJsonFactory mJsonFactory;
   protected final String mBaseUrl;
   protected final Context mContext;
   protected final String mFacebookMethod;
   protected HttpOperation.HttpOperationListener mHttpListener;
   protected final String mHttpMethod;
   protected HttpOperation mHttpOp;
   protected ApiMethodListener mListener;
   protected final Map<String, String> mParams;
   protected Intent mReqIntent;


   static {
      byte var0;
      if(!ApiMethod.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      mHandler = new Handler();
      mJsonFactory = new FBJsonFactory();
   }

   protected ApiMethod(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6) {
      TreeMap var7 = new TreeMap();
      this.mParams = var7;
      this.mContext = var1;
      this.mReqIntent = var2;
      this.mHttpMethod = var3;
      this.mFacebookMethod = var4;
      this.mBaseUrl = var5;
      this.mListener = var6;
      ApiMethod.ApiHttpListener var8 = new ApiMethod.ApiHttpListener();
      this.mHttpListener = var8;
   }

   protected ApiMethod(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6, HttpOperation.HttpOperationListener var7) {
      TreeMap var8 = new TreeMap();
      this.mParams = var8;
      this.mContext = var1;
      this.mReqIntent = var2;
      this.mHttpMethod = var3;
      this.mFacebookMethod = var4;
      this.mBaseUrl = var5;
      this.mListener = var6;
      this.mHttpListener = var7;
   }

   private String buildApiCallInfo() {
      return this.generateLogParams().substring(1);
   }

   public static boolean isSessionKeyError(int var0, Exception var1) {
      boolean var2;
      if(var1 != null && var1 instanceof FacebookApiException && ((FacebookApiException)var1).getErrorCode() == 102) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected static void printJson(String var0) {}

   protected static String removeChar(String var0, char var1) {
      StringBuffer var2 = new StringBuffer(128);
      int var3 = 0;

      while(true) {
         int var4 = var0.length();
         if(var3 >= var4) {
            return var2.toString();
         }

         if(var0.charAt(var3) != var1) {
            char var5 = var0.charAt(var3);
            var2.append(var5);
         }

         ++var3;
      }
   }

   public void addAuthenticationData(FacebookSessionInfo var1) {
      if(!$assertionsDisabled && var1 == null) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && var1.sessionKey == null) {
         throw new AssertionError();
      } else {
         Map var2 = this.mParams;
         String var3 = var1.sessionKey;
         var2.put("session_key", var3);
      }
   }

   protected void addCommonParameters() {
      Object var1 = this.mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
      Object var2 = this.mParams.put("format", "JSON");
      Map var3 = this.mParams;
      String var4 = this.mFacebookMethod;
      var3.put("method", var4);
      Object var6 = this.mParams.put("v", "1.0");
      Object var7 = this.mParams.put("migrations_override", "{\'empty_json\': true}");
      Object var8 = this.mParams.put("return_ssl_resources", "0");
      Object var9 = this.mParams.put("locale", "user");
   }

   public void addIntentAndListener(Intent var1, ApiMethodListener var2) {
      if(!$assertionsDisabled && (this.mReqIntent != null || this.mListener != null)) {
         throw new AssertionError();
      } else if(!$assertionsDisabled && (var1 == null || var2 == null)) {
         throw new AssertionError();
      } else {
         this.mReqIntent = var1;
         this.mListener = var2;
      }
   }

   protected void addSignature() throws NoSuchAlgorithmException, UnsupportedEncodingException {
      Map var1 = this.mParams;
      String var2 = this.buildSignature();
      var1.put("sig", var2);
   }

   protected String buildGETUrl(String var1) throws UnsupportedEncodingException {
      StringBuilder var2 = new StringBuilder(var1);
      StringBuilder var3 = this.buildQueryString();
      String var10;
      if(var3.length() == 0) {
         String var4 = this.getClass().getName();
         String var9;
         if("ApiMethod" != var4) {
            StringBuffer var5 = new StringBuffer("ApiMethod");
            StringBuffer var6 = var5.append("(");
            var5.append(var4);
            StringBuffer var8 = var5.append(")");
            var9 = var5.toString();
         } else {
            var9 = "ApiMethod";
         }

         Log.e(var9, "We always should have something in the query (e.g., the signature)");
         var10 = var1;
      } else {
         StringBuilder var11 = var2.append("?");
         var2.append(var3);
         var10 = var2.toString();
      }

      return var10;
   }

   protected StringBuilder buildQueryString() {
      return URLQueryBuilder.buildQueryString(this.mParams);
   }

   protected String buildSignature() throws NoSuchAlgorithmException, UnsupportedEncodingException {
      return URLQueryBuilder.buildSignature(this.signatureString());
   }

   public void cancel(boolean var1) {
      if(this.mHttpOp != null) {
         this.mHttpOp.cancel();
         if(var1) {
            this.dispatchOnOperationComplete(this, 0, (String)null, (Exception)null);
         }

         this.mHttpOp = null;
      }
   }

   protected void dispatchOnOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
      Object var6 = var4;
      if(this.simulateSessionKeyError()) {
         var6 = new FacebookApiException(102, "Invalid credentials");
      }

      this.mListener.onOperationComplete(var1, var2, var3, (Exception)var6);
   }

   protected String generateLogParams() {
      StringBuilder var1 = new StringBuilder(500);
      StringBuilder var2 = var1.append(",\"method\":\"");
      String var3 = this.mFacebookMethod;
      var1.append(var3);
      return var1.toString();
   }

   public Intent getIntent() {
      return this.mReqIntent;
   }

   protected void onComplete(int var1, String var2, Exception var3) {
      if(this.mListener != null) {
         this.mListener.onProcessComplete(this, var1, var2, var3);
         Handler var4 = mHandler;
         ApiMethod.2 var5 = new ApiMethod.2(var1, var3, var2);
         var4.post(var5);
      }
   }

   protected void onHttpComplete(int var1, String var2, Exception var3) {
      if(this.mListener != null) {
         this.mListener.onProcessComplete(this, var1, var2, var3);
         Handler var4 = mHandler;
         ApiMethod.1 var5 = new ApiMethod.1(var1, var2, var3);
         var4.post(var5);
      }
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {}

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      JsonParser var2 = mJsonFactory.createJsonParser(var1);
      JsonToken var3 = var2.nextToken();
      this.parseJSON(var2);
   }

   protected String signatureKey() {
      return this.mReqIntent.getStringExtra("ApiMethod.secret");
   }

   protected String signatureString() {
      Map var1 = this.mParams;
      String var2 = this.signatureKey();
      return URLQueryBuilder.signatureString(var1, var2);
   }

   protected boolean simulateSessionKeyError() {
      return false;
   }

   public void start() {
      try {
         this.addCommonParameters();
         this.addSignature();
         if(this.mHttpMethod.equals("GET")) {
            Context var1 = this.mContext;
            String var2 = this.mHttpMethod;
            String var3 = this.mBaseUrl;
            String var4 = this.buildGETUrl(var3);
            ByteArrayOutputStream var5 = new ByteArrayOutputStream(8192);
            HttpOperation.HttpOperationListener var6 = this.mHttpListener;
            HttpOperation var7 = new HttpOperation(var1, var2, var4, var5, var6, (boolean)1);
            this.mHttpOp = var7;
         } else if(this.mHttpMethod.equals("POST")) {
            byte[] var8 = this.buildQueryString().toString().getBytes("UTF-8");
            ByteArrayInputStream var9 = new ByteArrayInputStream(var8);
            Context var10 = this.mContext;
            String var11 = this.mBaseUrl;
            ByteArrayOutputStream var12 = new ByteArrayOutputStream(8192);
            HttpOperation.HttpOperationListener var13 = this.mHttpListener;
            HttpOperation var14 = new HttpOperation(var10, var11, var9, var12, "application/x-www-form-urlencoded", var13, (boolean)1);
            this.mHttpOp = var14;
         }

         this.mHttpOp.start();
      } catch (Exception var16) {
         var16.printStackTrace();
         if(this.mListener != null) {
            this.mListener.onOperationComplete(this, 0, (String)null, var16);
         }
      }
   }

   protected class ApiHttpListener implements HttpOperation.HttpOperationListener {

      protected ApiHttpListener() {}

      public void onHttpOperationComplete(HttpOperation var1, int var2, String var3, OutputStream var4, Exception var5) {
         int var6 = 0;
         if(var2 == 200 && var5 == null) {
            var6 = ((ByteArrayOutputStream)var4).size();

            try {
               byte[] var7 = ((ByteArrayOutputStream)var4).toByteArray();
               String var8 = new String(var7);
               ApiMethod.this.parseResponse(var8);
            } catch (FacebookApiException var23) {
               StringBuilder var15 = (new StringBuilder()).append("FacebookApiException: ");
               int var16 = var23.getErrorCode();
               StringBuilder var17 = var15.append(var16).append("/");
               String var18 = var23.getErrorMsg();
               String var19 = var17.append(var18).toString();
               Log.e("ApiMethod.onHttpOperationComplete", var19);
               var2 = 0;
               var3 = null;
               var5 = var23;
            } catch (Exception var24) {
               var24.printStackTrace();
               var2 = 0;
               var3 = null;
               var5 = var24;
            } catch (OutOfMemoryError var25) {
               ((ByteArrayOutputStream)var4).reset();
               String var22 = ApiMethod.this.buildApiCallInfo();
               com.facebook.katana.util.Utils.reportSoftError("ApiMethod OutOfMemoryError", var22);
            }
         }

         ApiMethod.this.onHttpComplete(var2, var3, (Exception)var5);
         if(ApiLogging.reportAndCheckApi((Exception)var5)) {
            Context var9 = ApiMethod.this.mContext;
            String var10 = ApiMethod.this.generateLogParams();
            long var11 = var1.calculateTimeElapsed();
            ApiLogging.logApiResponse(var9, var10, var11, var6, (Exception)var5);
         }
      }

      public void onHttpOperationProgress(HttpOperation var1, long var2, long var4) {}
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final int val$errorCode;
      // $FF: synthetic field
      final Exception val$ex;
      // $FF: synthetic field
      final String val$reasonPhrase;


      1(int var2, String var3, Exception var4) {
         this.val$errorCode = var2;
         this.val$reasonPhrase = var3;
         this.val$ex = var4;
      }

      public void run() {
         if(ApiMethod.this.mHttpOp != null) {
            ApiMethod.this.mHttpOp = null;
            ApiMethod var1 = ApiMethod.this;
            ApiMethod var2 = ApiMethod.this;
            int var3 = this.val$errorCode;
            String var4 = this.val$reasonPhrase;
            Exception var5 = this.val$ex;
            var1.dispatchOnOperationComplete(var2, var3, var4, var5);
         }
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final int val$errorCode;
      // $FF: synthetic field
      final Exception val$ex;
      // $FF: synthetic field
      final String val$reasonPhrase;


      2(int var2, Exception var3, String var4) {
         this.val$errorCode = var2;
         this.val$ex = var3;
         this.val$reasonPhrase = var4;
      }

      public void run() {
         int var1 = this.val$errorCode;
         Object var2 = this.val$ex;
         if(ApiMethod.this.simulateSessionKeyError()) {
            var2 = new FacebookApiException(102, "Invalid credentials");
         }

         ApiMethodListener var3 = ApiMethod.this.mListener;
         ApiMethod var4 = ApiMethod.this;
         String var5 = this.val$reasonPhrase;
         var3.onOperationComplete(var4, var1, var5, (Exception)var2);
      }
   }
}
