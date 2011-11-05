package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.katana.Constants;
import com.facebook.katana.UserTask;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.PlatformUtils;
import java.util.Map;

public class AuthLogout extends ApiMethod {

   private final ApiMethodListener mUserListener;


   public AuthLogout(Context var1, Intent var2, String var3, ApiMethodListener var4) {
      String var5 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "auth.logout", var5, (ApiMethodListener)null);
      AuthLogout.LogoutListener var9 = new AuthLogout.LogoutListener((AuthLogout.1)null);
      this.mListener = var9;
      this.mUserListener = var4;
      Map var10 = this.mParams;
      String var11 = Long.toString(System.currentTimeMillis());
      var10.put("call_id", var11);
      this.mParams.put("session_key", var3);
   }

   public void start() {
      if(this.mParams.get("session_key") != null) {
         super.start();
      } else {
         (new AuthLogout.ClearAccountDataTask(200, "Ok", (Exception)null)).execute();
      }
   }

   private class ClearAccountDataTask extends UserTask {

      private final int mErrorCode;
      private final Exception mEx;
      private final String mReasonPhrase;


      public ClearAccountDataTask(int var2, String var3, Exception var4) {
         Handler var5 = ApiMethod.mHandler;
         super(var5);
         this.mErrorCode = var2;
         this.mReasonPhrase = var3;
         this.mEx = var4;
      }

      protected void doInBackground() {
         FacebookDatabaseHelper.clearPrivateData(AuthLogout.this.mContext);
         if(PlatformUtils.platformStorageSupported(AuthLogout.this.mContext)) {
            FacebookAuthenticationService.removeSessionInfo(AuthLogout.this.mContext, (String)null);
         }
      }

      protected void onPostExecute() {
         ApiMethodListener var1 = AuthLogout.this.mUserListener;
         AuthLogout var2 = AuthLogout.this;
         int var3 = this.mErrorCode;
         String var4 = this.mReasonPhrase;
         Exception var5 = this.mEx;
         var1.onOperationComplete(var2, var3, var4, var5);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class LogoutListener implements ApiMethodListener {

      private LogoutListener() {}

      // $FF: synthetic method
      LogoutListener(AuthLogout.1 var2) {
         this();
      }

      public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         AuthLogout var5 = AuthLogout.this;
         (var5.new ClearAccountDataTask(var2, var3, var4)).execute();
      }

      public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

      public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {}
   }
}
