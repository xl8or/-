package com.google.android.vending.remoting.api;

import android.accounts.Account;
import android.content.Context;
import com.android.volley.AuthFailureException;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.android.volley.toolbox.UrlTools;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.Utils;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VendingApiContext {

   private final AndroidAuthenticator mAuthenticator;
   private final Context mContext;
   private boolean mHasPerformedInitialSecureTokenInvalidation;
   private boolean mHasPerformedInitialTokenInvalidation;
   private final Map<String, String> mHeaders;
   private String mLastAuthToken;
   private String mLastSecureAuthToken;
   private boolean mReauthenticate = 0;
   private VendingProtos.RequestPropertiesProto mRequestProperties;


   public VendingApiContext(Context var1, Account var2, Locale var3, String var4, int var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13) {
      HashMap var14 = Maps.newHashMap();
      this.mHeaders = var14;
      this.mContext = var1;
      Object var15 = this.mHeaders.put("User-Agent", "Android-Market/2");
      AndroidAuthenticator var16 = new AndroidAuthenticator(var1, var2);
      this.mAuthenticator = var16;
      VendingProtos.RequestPropertiesProto var17 = new VendingProtos.RequestPropertiesProto();
      this.mRequestProperties = var17;
      this.mRequestProperties.setAid(var4);
      VendingProtos.RequestPropertiesProto var19 = this.mRequestProperties;
      String var20 = var3.getCountry();
      var19.setUserCountry(var20);
      VendingProtos.RequestPropertiesProto var22 = this.mRequestProperties;
      String var23 = var3.getLanguage();
      var22.setUserLanguage(var23);
      this.mRequestProperties.setSoftwareVersion(var5);
      if(var6 != null) {
         this.mRequestProperties.setOperatorName(var6);
      }

      if(var7 != null) {
         this.mRequestProperties.setSimOperatorName(var7);
      }

      if(var8 != null) {
         this.mRequestProperties.setOperatorNumericName(var8);
      }

      if(var9 != null) {
         this.mRequestProperties.setSimOperatorNumericName(var9);
      }

      VendingProtos.RequestPropertiesProto var30 = this.mRequestProperties;
      String var31 = var10 + ":" + var11;
      var30.setProductNameAndVersion(var31);
      VendingProtos.RequestPropertiesProto var33 = this.mRequestProperties;
      var33.setClientId(var12);
      VendingProtos.RequestPropertiesProto var36 = this.mRequestProperties;
      var36.setLoggingId(var13);
      this.checkUrlRewrites();
   }

   private void checkRewrittenToSecureUrl(String var1) {
      String var2 = UrlTools.rewrite(this.mContext, var1);
      if(var2 == null) {
         String var3 = "URL blocked: " + var1;
         throw new RuntimeException(var3);
      } else {
         Utils.checkUrlIsSecure(var2);
      }
   }

   private void checkUrlRewrites() {
      this.checkRewrittenToSecureUrl("https://android.clients.google.com/vending/api/ApiRequest");
   }

   public Account getAccount() {
      return this.mAuthenticator.getAccount();
   }

   public String getAuthToken() throws AuthFailureException {
      AndroidAuthenticator var1 = this.mAuthenticator;
      String var2 = (String)G.vendingAuthTokenType.get();
      boolean var3 = this.mReauthenticate;
      String var4 = var1.getAuthToken(var2, var3);
      this.mLastAuthToken = var4;
      this.mReauthenticate = (boolean)0;
      return this.mLastAuthToken;
   }

   public Map<String, String> getHeaders() {
      return this.mHeaders;
   }

   public VendingProtos.RequestPropertiesProto getRequestProperties(boolean param1) throws AuthFailureException {
      // $FF: Couldn't be decompiled
   }

   public String getSecureAuthToken() throws AuthFailureException {
      AndroidAuthenticator var1 = this.mAuthenticator;
      String var2 = (String)G.vendingSecureAuthTokenType.get();
      boolean var3 = this.mReauthenticate;
      String var4 = var1.getAuthToken(var2, var3);
      this.mLastSecureAuthToken = var4;
      this.mReauthenticate = (boolean)0;
      return this.mLastSecureAuthToken;
   }

   public void invalidateAuthToken(boolean var1) {
      String var2;
      if(var1) {
         var2 = this.mLastSecureAuthToken;
      } else {
         var2 = this.mLastAuthToken;
      }

      if(var2 != null) {
         this.mAuthenticator.invalidateAuthToken(var2);
      }

      if(var1) {
         this.mLastSecureAuthToken = null;
      } else {
         this.mLastAuthToken = null;
      }
   }

   public void scheduleReauthentication(boolean var1) {
      this.mReauthenticate = (boolean)1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("[VendingApiContext]");
      return var1.toString();
   }
}
