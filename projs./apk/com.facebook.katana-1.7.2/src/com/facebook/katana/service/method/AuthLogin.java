package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.katana.Constants;
import com.facebook.katana.UserTask;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class AuthLogin extends ApiMethod {

   protected static final int INVALID_ERROR_CODE = 255;
   private FacebookSessionInfo mSessionInfo;
   private final ApiMethodListener mUserListener;


   public AuthLogin(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "auth.login", var6, (ApiMethodListener)null);
      this.mParams.put("email", var3);
      this.mParams.put("password", var4);
      AuthLogin.LoginListener var12 = new AuthLogin.LoginListener((AuthLogin.1)null);
      this.mListener = var12;
      this.mUserListener = var5;
   }

   public FacebookSessionInfo getSessionInfo() {
      return this.mSessionInfo;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      AuthLogin.FacebookApiSessionInfo var2 = (AuthLogin.FacebookApiSessionInfo)JMParser.parseObjectJson(var1, AuthLogin.FacebookApiSessionInfo.class);
      if(var2.mErrorCode != -1) {
         int var3 = var2.mErrorCode;
         String var4 = var2.mErrorMsg;
         throw new FacebookApiException(var3, var4);
      } else if(var2.sessionKey != null && var2.userId != 65535L) {
         this.mSessionInfo = var2;
         FacebookSessionInfo var5 = this.mSessionInfo;
         String var6 = (String)this.mParams.get("email");
         var5.setString("username", var6);
      } else {
         throw new IOException("Session info not found");
      }
   }

   protected String signatureKey() {
      return "62f8ce9f74b12f84c123cc23437a4a32";
   }

   protected boolean simulateSessionKeyError() {
      return false;
   }

   protected static class FacebookApiSessionInfo extends FacebookSessionInfo {

      @JMAutogen.InferredType(
         jsonFieldName = "error_code"
      )
      public final int mErrorCode = -1;
      @JMAutogen.InferredType(
         jsonFieldName = "error_msg"
      )
      public final String mErrorMsg = null;


      protected FacebookApiSessionInfo() {}
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class LoginListener implements ApiMethodListener {

      private LoginListener() {}

      // $FF: synthetic method
      LoginListener(AuthLogin.1 var2) {
         this();
      }

      public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         AuthLogin var5 = AuthLogin.this;
         String var6 = (String)AuthLogin.this.mParams.get("email");
         (var5.new ClearAccountDataTask(var6, var2, var3, var4)).execute();
      }

      public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

      public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {}
   }

   private class ClearAccountDataTask extends UserTask {

      private final int mErrorCode;
      private final Exception mEx;
      private final String mReasonPhrase;
      private final String mUsername;


      public ClearAccountDataTask(String var2, int var3, String var4, Exception var5) {
         Handler var6 = ApiMethod.mHandler;
         super(var6);
         this.mUsername = var2;
         this.mErrorCode = var3;
         this.mReasonPhrase = var4;
         this.mEx = var5;
      }

      protected void doInBackground() {
         boolean var1 = true;

         label30: {
            boolean var5;
            try {
               String var2 = UserValuesManager.loadActiveSessionInfo(AuthLogin.this.mContext);
               if(var2 == null) {
                  break label30;
               }

               String var3 = FacebookSessionInfo.parseFromJson(var2).username;
               if(var3 == null) {
                  break label30;
               }

               String var4 = this.mUsername;
               var5 = var3.equals(var4);
            } catch (Throwable var9) {
               break label30;
            }

            if(!var5) {
               var1 = true;
            } else {
               var1 = false;
            }
         }

         if(var1) {
            FacebookDatabaseHelper.clearPrivateData(AuthLogin.this.mContext);
            if(PlatformUtils.platformStorageSupported(AuthLogin.this.mContext)) {
               Context var6 = AuthLogin.this.mContext;
               String var7 = this.mUsername;
               FacebookAuthenticationService.removeSessionInfo(var6, var7);
            }
         }
      }

      protected void onPostExecute() {
         ApiMethodListener var1 = AuthLogin.this.mUserListener;
         AuthLogin var2 = AuthLogin.this;
         int var3 = this.mErrorCode;
         String var4 = this.mReasonPhrase;
         Exception var5 = this.mEx;
         var1.onOperationComplete(var2, var3, var4, var5);
      }
   }
}
