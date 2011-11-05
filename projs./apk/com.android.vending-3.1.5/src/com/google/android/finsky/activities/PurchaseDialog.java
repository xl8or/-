package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.activities.PhoneskyActivity;
import com.google.android.finsky.activities.PurchaseFragment;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.layout.CustomActionBarFactory;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public class PurchaseDialog extends PhoneskyActivity implements PageFragmentHost {

   private static final String KEY_DIRECT_LINK = "is_direct_link";
   private static final String KEY_EXTERNAL_REFERRER = "ext_referrer";
   private static final String KEY_OFFER_TYPE = "offer";
   private static final String KEY_REFERRER_COOKIE = "referrer_cookie";
   private static final String KEY_REFERRER_URL = "referrer";
   private static final String KEY_URL = "url";
   private CustomActionBar mActionBar;
   private String mExternalReferrer;
   private boolean mIsDirectLink;
   private int mOfferType;
   private String mReferrerCookie;
   private String mReferrerUrl;
   private String mUrl;


   public PurchaseDialog() {}

   public static void show(Context var0, String var1, int var2, boolean var3, String var4, String var5, String var6) {
      Intent var7 = new Intent(var0, PurchaseDialog.class);
      var7.putExtra("url", var1);
      var7.putExtra("offer", var2);
      var7.putExtra("referrer", var4);
      var7.putExtra("referrer_cookie", var5);
      var7.putExtra("is_direct_link", var3);
      var7.putExtra("ext_referrer", var6);
      Intent var14 = var7.setFlags(536936448);
      var0.startActivity(var7);
   }

   public BitmapLoader getBitmapLoader() {
      return FinskyApp.get().getBitmapLoader();
   }

   public DfeApi getDfeApi() {
      return FinskyApp.get().getDfeApi();
   }

   public NavigationManager getNavigationManager() {
      return null;
   }

   public void goBack() {
      this.finish();
   }

   protected void onCreate(Bundle var1) {
      this.setContentView(2130968665);
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      String var3 = var2.getStringExtra("url");
      this.mUrl = var3;
      int var4 = var2.getIntExtra("offer", -1);
      this.mOfferType = var4;
      String var5 = var2.getStringExtra("referrer");
      this.mReferrerUrl = var5;
      String var6 = var2.getStringExtra("referrer_cookie");
      this.mReferrerCookie = var6;
      String var7 = var2.getStringExtra("ext_referrer");
      this.mExternalReferrer = var7;
      boolean var8 = var2.getBooleanExtra("is_direct_link", (boolean)0);
      this.mIsDirectLink = var8;
      CustomActionBar var9 = CustomActionBarFactory.getInstance(this);
      this.mActionBar = var9;
      this.mActionBar.initializeNoNavigation(this);
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      byte var2;
      if(var1.getItemId() == 16908332) {
         this.finish();
         var2 = 1;
      } else {
         var2 = super.onOptionsItemSelected(var1);
      }

      return (boolean)var2;
   }

   protected void onReady(boolean var1) {
      if(this.getSupportFragmentManager().findFragmentById(2131755130) == null) {
         String var2 = this.mUrl;
         int var3 = this.mOfferType;
         String var4 = this.mReferrerUrl;
         String var5 = this.mReferrerCookie;
         boolean var6 = this.mIsDirectLink;
         String var7 = this.mExternalReferrer;
         PurchaseFragment var8 = PurchaseFragment.newInstance(var2, var3, var4, var5, var6, var7);
         FragmentTransaction var9 = this.getSupportFragmentManager().beginTransaction();
         var9.add(2131755130, var8);
         int var11 = var9.commit();
      }
   }

   public void showErrorDialog(String var1, String var2, boolean var3) {
      ErrorDialog.show(this.getSupportFragmentManager(), var1, var2, var3);
   }

   public void updateBreadcrumb(String var1) {
      this.mActionBar.updateBreadcrumb(var1);
   }

   public void updateCurrentBackendId(int var1) {
      this.mActionBar.updateCurrentBackendId(var1);
   }
}
