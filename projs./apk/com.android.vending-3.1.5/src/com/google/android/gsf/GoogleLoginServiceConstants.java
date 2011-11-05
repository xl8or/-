package com.google.android.gsf;

import android.content.Intent;

public class GoogleLoginServiceConstants {

   public static final String ACCOUNTS_KEY = "accounts";
   public static final String ACCOUNT_TYPE = "com.google";
   public static final String ACTION_GET_GLS = "com.google.android.gsf.action.GET_GLS";
   public static final String AUTHTOKEN_KEY = "authtoken";
   public static final String AUTH_ACCOUNT_KEY = "authAccount";
   public static final int ERROR_CODE_GLS_NOT_FOUND = 0;
   public static final int ERROR_CODE_GLS_VERIFICATION_FAILED = 1;
   public static final String ERROR_CODE_KEY = "errorCode";
   public static final String FEATURE_GOOGLE = "google";
   public static final String FEATURE_HOSTED_OR_GOOGLE = "hosted_or_google";
   public static final String FEATURE_LEGACY_GOOGLE = "google";
   public static final String FEATURE_LEGACY_HOSTED_OR_GOOGLE = "hosted_or_google";
   public static final String FEATURE_SAML_ACCOUNT = "saml";
   public static final String FEATURE_SERVICE_PREFIX = "service_";
   public static final String FEATURE_YOUTUBE = "youtubelinked";
   public static final int FLAG_GOOGLE_ACCOUNT = 1;
   public static final int FLAG_HOSTED_ACCOUNT = 2;
   public static final int FLAG_SAML_ACCOUNT = 8;
   public static final int FLAG_YOUTUBE_ACCOUNT = 4;
   public static final String LOGIN_ACCOUNTS_MISSING_ACTION = "com.google.android.gsf.LOGIN_ACCOUNTS_MISSING";
   public static final boolean PREFER_HOSTED = false;
   public static final String PRE_FROYO_AID_FILENAME = "pre_froyo_aid";
   public static final String REQUEST_EXTRAS = "callerExtras";
   public static final boolean REQUIRE_GOOGLE = true;
   public static final Intent SERVICE_INTENT = new Intent("com.google.android.gsf.action.GET_GLS");
   public static final String YOUTUBE_USER_KEY = "YouTubeUser";


   private GoogleLoginServiceConstants() {}

   public static String featureForService(String var0) {
      return "service_" + var0;
   }

   static String getErrorCodeMessage(int var0) {
      String var1;
      switch(var0) {
      case 0:
         var1 = "The Google login service cannot be found.";
         break;
      case 1:
         var1 = "The Google login service cannot be verified.";
         break;
      default:
         var1 = "Unknown error";
      }

      return var1;
   }
}
