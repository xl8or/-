package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.FakeNavigationManager;
import com.google.android.finsky.activities.ReviewsFragment;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.layout.CustomActionBarFactory;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.BitmapLoader;

public class ReviewsActivity extends AuthenticatedActivity implements PageFragmentHost {

   private static final String KEY_DOCUMENT = "finsky.ReviewsActivity.document";
   private CustomActionBar mActionBar;
   private Document mDocument;
   private NavigationManager mNavigationManager;


   public ReviewsActivity() {}

   public static void show(Context var0, Document var1) {
      Intent var2 = new Intent(var0, ReviewsActivity.class);
      var2.putExtra("finsky.ReviewsActivity.document", var1);
      Intent var4 = var2.setFlags(536870912);
      var0.startActivity(var2);
   }

   public BitmapLoader getBitmapLoader() {
      return FinskyApp.get().getBitmapLoader();
   }

   public DfeApi getDfeApi() {
      return FinskyApp.get().getDfeApi();
   }

   public NavigationManager getNavigationManager() {
      return this.mNavigationManager;
   }

   public void goBack() {
      this.finish();
   }

   protected void handleAuthenticationError(Response.ErrorCode var1, String var2, NetworkError var3) {}

   protected void onApisChanged() {}

   protected void onCleanup() {}

   public void onCreate(Bundle var1) {
      this.setContentView(2130968665);
      super.onCreate(var1);
      Document var2 = (Document)this.getIntent().getParcelableExtra("finsky.ReviewsActivity.document");
      this.mDocument = var2;
      FakeNavigationManager var3 = new FakeNavigationManager(this);
      this.mNavigationManager = var3;
      CustomActionBar var4 = CustomActionBarFactory.getInstance(this);
      this.mActionBar = var4;
      CustomActionBar var5 = this.mActionBar;
      NavigationManager var6 = this.mNavigationManager;
      var5.initialize(var6, this);
      CustomActionBar var7 = this.mActionBar;
      String var8 = this.mDocument.getTitle();
      var7.updateBreadcrumb(var8);
      CustomActionBar var9 = this.mActionBar;
      int var10 = this.mDocument.getBackend();
      var9.updateCurrentBackendId(var10);
      FragmentManager var11 = this.getSupportFragmentManager();
      if(var11.findFragmentById(2131755130) == null) {
         Document var12 = this.mDocument;
         ReviewsFragment var13 = ReviewsFragment.newInstance(this, var12);
         FragmentTransaction var14 = var11.beginTransaction();
         var14.replace(2131755130, var13);
         int var16 = var14.commit();
      }
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 16908332:
         this.mNavigationManager.goUp();
         var2 = true;
         break;
      default:
         var2 = false;
      }

      return var2;
   }

   protected void onReady(boolean var1) {}

   protected void onTocLoaded(DfeToc var1) {}

   public void showErrorDialog(String var1, String var2, boolean var3) {}

   public void updateBreadcrumb(String var1) {
      this.mActionBar.updateBreadcrumb(var1);
   }

   public void updateCurrentBackendId(int var1) {
      this.mActionBar.updateCurrentBackendId(var1);
   }
}
