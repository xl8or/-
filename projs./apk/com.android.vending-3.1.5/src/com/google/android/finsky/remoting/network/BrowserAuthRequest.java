package com.google.android.finsky.remoting.network;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import java.io.IOException;

public class BrowserAuthRequest implements Response.Listener<String>, Response.ErrorListener {

   private static final Uri ISSUE_AUTH_TOKEN_URL = Uri.parse("https://www.google.com/accounts/IssueAuthToken?service=gaia&Session=false");
   private static final String PARAM_AUTH = "auth";
   private static final String PARAM_AUTH_TOKEN_SOURCE = (String)G.authTokenType.get();
   private static final String PARAM_CONTINUATION = "continue";
   private static final String PARAM_SERVICE = "service";
   private static final String PARAM_SOURCE = "source";
   private static final Uri TOKEN_AUTH_URL = Uri.parse("https://www.google.com/accounts/TokenAuth");
   private Account account;
   private final String mAuthTokenService;
   private BrowserAuthRequest.Listener<Uri> mListener;
   private final RequestQueue mRequestQueue;
   private String mUrl;


   public BrowserAuthRequest(RequestQueue var1, String var2, String var3, String var4, BrowserAuthRequest.Listener<Uri> var5) {
      this.mRequestQueue = var1;
      this.mListener = var5;
      this.mUrl = var2;
      this.mAuthTokenService = var4;
      this.getSidToken(var3);
   }

   private void deliverUri(Uri var1, Response.ErrorCode var2, String var3) {
      this.mListener.onResponse(var1, var2, var3);
   }

   private void getSidToken(String var1) {
      Account var2 = new Account(var1, "com.google");
      this.account = var2;
      Account var3 = this.account;
      BrowserAuthRequest.SidTokenHandler var4 = new BrowserAuthRequest.SidTokenHandler(var3);
      AccountManager var5 = AccountManager.get(this.mListener.getActivity());
      Account var6 = this.account;
      Activity var7 = this.mListener.getActivity();
      Object var8 = null;
      var5.getAuthToken(var6, "SID", (Bundle)null, var7, var4, (Handler)var8);
   }

   private void getUberToken(String var1, String var2) {
      String var3 = ISSUE_AUTH_TOKEN_URL.buildUpon().appendQueryParameter("SID", var1).appendQueryParameter("LSID", var2).build().toString();
      StringRequest var4 = new StringRequest(var3, this, this);
      var4.setShouldCache((boolean)0);
      this.mRequestQueue.add(var4);
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.deliverUri((Uri)null, var1, var2);
   }

   public void onResponse(String var1) {
      Builder var2 = TOKEN_AUTH_URL.buildUpon();
      String var3 = this.mAuthTokenService;
      Builder var4 = var2.appendQueryParameter("service", var3);
      String var5 = PARAM_AUTH_TOKEN_SOURCE;
      Builder var6 = var4.appendQueryParameter("source", var5);
      String var7 = var1.trim();
      Builder var8 = var6.appendQueryParameter("auth", var7);
      String var9 = this.mUrl;
      Uri var10 = var8.appendQueryParameter("continue", var9).build();
      Response.ErrorCode var11 = Response.ErrorCode.OK;
      this.deliverUri(var10, var11, (String)null);
   }

   private class SidTokenHandler implements AccountManagerCallback<Bundle> {

      private final Account mAccount;


      SidTokenHandler(Account var2) {
         this.mAccount = var2;
      }

      public void run(AccountManagerFuture<Bundle> var1) {
         try {
            String var2 = ((Bundle)var1.getResult()).getString("authtoken");
            BrowserAuthRequest var3 = BrowserAuthRequest.this;
            BrowserAuthRequest.LsidTokenHandler var4 = var3.new LsidTokenHandler(var2);
            AccountManager var5 = AccountManager.get(BrowserAuthRequest.this.mListener.getActivity());
            Account var6 = this.mAccount;
            Activity var7 = BrowserAuthRequest.this.mListener.getActivity();
            var5.getAuthToken(var6, "LSID", (Bundle)null, var7, var4, (Handler)null);
         } catch (OperationCanceledException var21) {
            Object[] var10 = new Object[]{var21};
            FinskyLog.e("Cancelled while acquiring token: %s", var10);
            BrowserAuthRequest var11 = BrowserAuthRequest.this;
            Response.ErrorCode var12 = Response.ErrorCode.AUTH;
            var11.deliverUri((Uri)null, var12, (String)null);
         } catch (AuthenticatorException var22) {
            Object[] var14 = new Object[]{var22};
            FinskyLog.e("Authentication error while acquiring token: %s", var14);
            BrowserAuthRequest var15 = BrowserAuthRequest.this;
            Response.ErrorCode var16 = Response.ErrorCode.AUTH;
            var15.deliverUri((Uri)null, var16, (String)null);
         } catch (IOException var23) {
            Object[] var18 = new Object[]{var23};
            FinskyLog.e("IO error while acquiring token: %s", var18);
            BrowserAuthRequest var19 = BrowserAuthRequest.this;
            Response.ErrorCode var20 = Response.ErrorCode.AUTH;
            var19.deliverUri((Uri)null, var20, (String)null);
         }
      }
   }

   private class LsidTokenHandler implements AccountManagerCallback<Bundle> {

      private final String mSid;


      LsidTokenHandler(String var2) {
         this.mSid = var2;
      }

      public void run(AccountManagerFuture<Bundle> var1) {
         try {
            String var2 = ((Bundle)var1.getResult()).getString("authtoken");
            BrowserAuthRequest var3 = BrowserAuthRequest.this;
            String var4 = this.mSid;
            var3.getUberToken(var4, var2);
         } catch (OperationCanceledException var17) {
            Object[] var6 = new Object[]{var17};
            FinskyLog.e("Cancelled while acquiring token: %s", var6);
            BrowserAuthRequest var7 = BrowserAuthRequest.this;
            Response.ErrorCode var8 = Response.ErrorCode.AUTH;
            var7.deliverUri((Uri)null, var8, (String)null);
         } catch (AuthenticatorException var18) {
            Object[] var10 = new Object[]{var18};
            FinskyLog.e("Authentication error while acquiring token: %s", var10);
            BrowserAuthRequest var11 = BrowserAuthRequest.this;
            Response.ErrorCode var12 = Response.ErrorCode.AUTH;
            var11.deliverUri((Uri)null, var12, (String)null);
         } catch (IOException var19) {
            Object[] var14 = new Object[]{var19};
            FinskyLog.e("IO error while acquiring token: %s", var14);
            BrowserAuthRequest var15 = BrowserAuthRequest.this;
            Response.ErrorCode var16 = Response.ErrorCode.AUTH;
            var15.deliverUri((Uri)null, var16, (String)null);
         }
      }
   }

   public interface Listener<Uri extends Object> {

      Activity getActivity();

      void onResponse(Uri var1, Response.ErrorCode var2, String var3);
   }
}
