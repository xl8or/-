package com.google.android.finsky.activities;

import android.accounts.Account;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureException;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.activities.PhoneskyActivity;
import com.google.android.finsky.activities.SettingsActivity;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.GetBillingCountriesAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierBillingAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierParamsAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierProvisioningAction;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDebugActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.layout.CustomActionBarFactory;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.android.finsky.utils.BgDataDisabledException;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyDebug;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.NotificationListener;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.UrlIntentFilter;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends PhoneskyActivity implements PageFragmentHost, SimpleAlertDialog.Listener {

   public static final String KEY_ERROR_DIALOG_HTML_MESSAGE = "error_html_message";
   public static final String KEY_ERROR_DIALOG_TITLE = "error_title";
   private static final String KEY_LAST_ERROR_DIALOG_HASH = "last_shown_error_hash";
   private static final int REQUEST_CODE_SETTINGS = 31;
   public static final String VIEW_MY_DOWNLOADS_ACTION = "com.google.android.finsky.VIEW_MY_DOWNLOADS";
   private static boolean sBillingInitialized;
   private CustomActionBar mActionBar;
   private int mLastShownErrorHash;
   private MenuItem mMyCollectionItem;
   private NavigationManager mNavigationManager;
   private NotificationListener mNotificationListener;
   private Bundle mSavedInstanceState;
   private int mSequenceNumberToDrainFrom = -1;
   private boolean mStateSaved;


   public MainActivity() {
      MainActivity.1 var1 = new MainActivity.1();
      this.mNotificationListener = var1;
   }

   private void buildSelectEnvironmentDialog() {
      Builder var1 = new Builder(this);
      Builder var2 = var1.setTitle(2131231009);
      Set var3 = FinskyDebug.ENVIRONMENTS.keySet();
      CharSequence[] var4 = new CharSequence[var3.size()];
      int var5 = 0;
      int var6 = 0;

      for(Iterator var7 = var3.iterator(); var7.hasNext(); ++var5) {
         String var8 = (String)var7.next();
         FinskyDebug.Environment var9 = (FinskyDebug.Environment)FinskyDebug.ENVIRONMENTS.get(var8);
         if(FinskyDebug.isEnvironmentSelected(this, var9)) {
            var6 = var5;
         }

         var4[var5] = var8;
      }

      MainActivity.10 var10 = new MainActivity.10(var4);
      var1.setSingleChoiceItems(var4, var6, var10);
      var1.create().show();
   }

   private int getCurrentBackend() {
      return this.mActionBar.getCurrentBackendId();
   }

   private String getExternalReferrer(Uri var1) {
      String var2 = var1.getQueryParameter("referrer");
      if(TextUtils.isEmpty(var2)) {
         var2 = null;
      }

      return var2;
   }

   private void handleIntent() {
      NavigationManager var1 = this.mNavigationManager;
      MainActivity.2 var2 = new MainActivity.2();
      var1.addOnBackStackChangedListener(var2);
      Intent var3 = this.getIntent();
      String var4 = var3.getStringExtra("authAccount");
      if(var4 != null) {
         var3.removeExtra("authAccount");
         this.setIntent(var3);
         this.switchAccount(var4);
      } else {
         this.maybeShowErrorDialog(var3);
         String var5 = var3.getAction();
         if("android.intent.action.SEARCH".equals(var5)) {
            this.handleSearchIntent(var3);
         } else if("android.intent.action.VIEW".equals(var5)) {
            this.mNavigationManager.clear();
            this.handleViewIntent(var3);
         } else if("com.google.android.finsky.VIEW_MY_DOWNLOADS".equals(var5)) {
            this.mNavigationManager.clear();
            this.mNavigationManager.goToMyDownloads();
         } else {
            this.mNavigationManager.clear();
            NavigationManager var6 = this.mNavigationManager;
            DfeToc var7 = FinskyApp.get().getToc();
            var6.goToAggregatedHome(var7);
         }
      }
   }

   private void handleSearchIntent(Intent var1) {
      if(this.isTosAccepted()) {
         String var2 = var1.getStringExtra("query");
         FinskyApp.get().getRecentSuggestions().saveRecentQuery(var2, (String)null);
         NavigationManager var3 = this.mNavigationManager;
         int var4 = this.getCurrentBackend();
         var3.goToSearch(var2, var4, (String)null);
      }
   }

   private void handleViewIntent(Intent var1) {
      String var2 = var1.getDataString();
      UrlIntentFilter.Result var3 = UrlIntentFilter.matchUri(var2);
      if(var3 == null) {
         NavigationManager var4 = this.mNavigationManager;
         DfeToc var5 = FinskyApp.get().getToc();
         var4.goToAggregatedHome(var5);
      } else {
         if(var3.corpus != 0) {
            PackageManager var6 = this.getPackageManager();
            int var7 = var3.corpus;
            if(!IntentUtils.isChannelEnabled(this, var6, var7)) {
               Uri var8 = var1.getData();
               Intent var9 = new Intent("android.intent.action.VIEW", var8);
               this.startActivity(var9);
               this.finish();
               return;
            }
         }

         if(var3.type == 2) {
            int var10 = var3.corpus;
            String var11 = var3.extra;
            String var12 = DfeApi.createDetailsUrlFromId(var10, var11);
            NavigationManager var13 = this.mNavigationManager;
            Uri var14 = var1.getData();
            String var15 = this.getExternalReferrer(var14);
            var13.goToDetails(var12, var15);
         } else if(var3.type == 3) {
            NavigationManager var16 = this.mNavigationManager;
            String var17 = var3.extra;
            int var18 = var3.corpus;
            var16.goToSearch(var17, var18, (String)null);
         } else if(var3.type == 1) {
            DfeToc var19 = FinskyApp.get().getToc();
            int var20 = var3.corpus;
            Toc.CorpusMetadata var21 = var19.getCorpus(var20);
            if(var21 != null && var21.getBackend() != 0 && var21.hasLandingUrl()) {
               NavigationManager var22 = this.mNavigationManager;
               String var23 = var21.getLandingUrl();
               String var24 = var21.getName();
               int var25 = var21.getBackend();
               var22.goToCorpusHome(var23, var24, var25);
            } else {
               NavigationManager var26 = this.mNavigationManager;
               DfeToc var27 = FinskyApp.get().getToc();
               var26.goToAggregatedHome(var27);
            }
         } else if(var3.type == 4) {
            if(var3.corpus != 1) {
               StringBuilder var28 = (new StringBuilder()).append("Buy links supported only for books: ");
               int var29 = var3.corpus;
               StringBuilder var30 = var28.append(var29).append(":");
               String var31 = var3.extra;
               String var32 = var30.append(var31).toString();
               Object[] var33 = new Object[0];
               FinskyLog.e(var32, var33);
               this.finish();
            } else {
               int var34 = var3.corpus;
               String var35 = var3.extra;
               String var36 = DfeApi.createDetailsUrlFromId(var34, var35);
               NavigationManager var37 = this.mNavigationManager;
               byte var38 = 1;
               Object var39 = null;
               Object var40 = null;
               var37.goToPurchase(var36, 1, (boolean)var38, (String)null, (String)var39, (String)var40);
               this.finish();
            }
         } else {
            Object[] var41 = new Object[]{var2};
            FinskyLog.wtf("Unhandled URL %s", var41);
            this.finish();
         }
      }
   }

   private void initializeBilling() {
      if(!sBillingInitialized) {
         sBillingInitialized = (boolean)1;
         Object[] var1 = new Object[0];
         FinskyLog.d("Optimistically initializing billing parameters.", var1);
         CarrierBillingAction var2 = new CarrierBillingAction();
         MainActivity.4 var3 = new MainActivity.4();
         var2.run(var3);
         GetBillingCountriesAction var4 = new GetBillingCountriesAction();
         String var5 = FinskyApp.get().getCurrentAccountName();
         var4.run(var5, (Runnable)null);
      }
   }

   private void initializeCarrierBillingParams() {
      VendingProtos.GetMarketMetadataResponseProto var1 = FinskyInstance.get().getMarketMetadata();
      CarrierParamsAction var2 = new CarrierParamsAction(var1);
      MainActivity.5 var3 = new MainActivity.5();
      var2.run(var3);
   }

   private void initializeCarrierBillingProvisioning() {
      (new CarrierProvisioningAction()).runIfNotOnWifi(this, (Runnable)null);
   }

   private void maybeShowErrorDialog(Intent var1) {
      if(var1.hasExtra("error_html_message")) {
         String var2 = null;
         if(var1.hasExtra("error_title")) {
            var2 = var1.getStringExtra("error_title");
         }

         String var3 = var1.getStringExtra("error_html_message");
         int var4 = (var3 + var2).hashCode();
         if(this.mLastShownErrorHash != var4) {
            ErrorDialog.show(this.getSupportFragmentManager(), var2, var3, (boolean)0);
            this.mLastShownErrorHash = var4;
         }
      }
   }

   private void onMyCollectionSelected() {
      PackageManager var1 = this.getPackageManager();
      int var2 = this.getCurrentBackend();
      switch(var2) {
      case 1:
      case 4:
         if(!IntentUtils.isConsumptionAppInstalled(var1, var2)) {
            this.mNavigationManager.showAppNeededDialog(var2);
            return;
         }

         String var3 = FinskyApp.get().getDfeApi().getCurrentAccountName();
         Intent var4 = IntentUtils.buildConsumptionAppLaunchIntent(var1, var2, var3);
         this.startActivity(var4);
         return;
      case 2:
      case 3:
      default:
         this.mNavigationManager.goToMyDownloads();
      }
   }

   private void setupDcbDebugMenu(Menu var1) {
      MenuItem var2 = var1.findItem(2131755338);
      MenuItem var3 = var2.setVisible((boolean)1);
      MainActivity.9 var4 = new MainActivity.9();
      var2.setOnMenuItemClickListener(var4);
   }

   private void setupDebugMenu(Menu var1) {
      MenuItem var2 = var1.findItem(2131755336);
      MenuItem var3 = var2.setVisible((boolean)1);
      MainActivity.7 var4 = new MainActivity.7();
      var2.setOnMenuItemClickListener(var4);
      MenuItem var6 = var1.findItem(2131755337);
      MenuItem var7 = var6.setVisible((boolean)1);
      MainActivity.8 var8 = new MainActivity.8();
      var6.setOnMenuItemClickListener(var8);
   }

   private void showErrorMessage(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(var3 instanceof BgDataDisabledException) {
         this.showBackgroundDataDialog();
      } else {
         View var4 = this.findViewById(2131755244);
         var4.setVisibility(0);
         ((TextView)this.findViewById(2131755191)).setText(var2);
         View var5 = this.findViewById(2131755192);
         MainActivity.11 var6 = new MainActivity.11(var4);
         var5.setOnClickListener(var6);
      }
   }

   private void updateConsumptionAppMenu() {
      if(this.mMyCollectionItem != null) {
         NavigationState var1 = this.mNavigationManager.getCurrentPageType();
         MenuItem var2 = this.mMyCollectionItem;
         NavigationState var3 = NavigationState.MY_DOWNLOADS;
         byte var4;
         if(var1 != var3) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         var2.setVisible((boolean)var4);
         if(this.mMyCollectionItem.isVisible()) {
            int var6 = this.getCurrentBackend();
            String var7 = CorpusMetadata.getCorpusMyCollectionDescription(var6);
            if(TextUtils.isEmpty(var7)) {
               MenuItem var8 = this.mMyCollectionItem.setVisible((boolean)0);
            } else {
               this.mMyCollectionItem.setTitle(var7);
               MenuItem var10 = this.mMyCollectionItem;
               int var11 = CorpusMetadata.getCorpusMyCollectionIcon(var6);
               var10.setIcon(var11);
            }
         }
      }
   }

   public BitmapLoader getBitmapLoader() {
      return FinskyApp.get().getBitmapLoader();
   }

   public CustomActionBar getCustomActionBar() {
      return this.mActionBar;
   }

   public DfeApi getDfeApi() {
      return FinskyApp.get().getDfeApi();
   }

   public NavigationManager getNavigationManager() {
      return this.mNavigationManager;
   }

   public void goBack() {
      this.onBackPressed();
   }

   protected void handleAuthenticationError(Response.ErrorCode var1, String var2, NetworkError var3) {
      if(var3 instanceof AuthFailureException) {
         Intent var4 = ((AuthFailureException)var3).getResolutionIntent();
         if(var4 != null) {
            this.handleUserAuthentication(var4);
            return;
         }
      }

      String var5 = ErrorStrings.get(this, var1, var2);
      this.hideLoadingIndicator();
      this.findViewById(2131755243).setVisibility(8);
      this.showErrorMessage(var1, var5, var3);
   }

   public boolean isStateSaved() {
      return this.mStateSaved;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 31 && var2 == 40) {
         this.restart((Account)null, (boolean)1);
      } else {
         this.mStateSaved = (boolean)0;
         super.onActivityResult(var1, var2, var3);
      }
   }

   public void onApisChanged() {
      this.mNavigationManager.init(this);
   }

   public void onBackPressed() {
      if(!this.mNavigationManager.goBack()) {
         super.onBackPressed();
      }
   }

   protected void onCleanup() {
      FinskyApp var1 = FinskyApp.get();
      int var2 = RequestQueue.getSequenceNumber();
      var1.drainAllRequests(var2);
      FinskyApp.get().clearCacheAsync((Runnable)null);
      if(!this.mStateSaved) {
         this.mNavigationManager.clear();
         boolean var3 = this.mNavigationManager.flush();
      }

      ViewGroup var4 = (ViewGroup)this.getWindow().findViewById(2131755130);
      int var5 = var4.getChildCount();
      ArrayList var6 = Lists.newArrayList();

      for(int var7 = 0; var7 < var5; ++var7) {
         View var8 = var4.getChildAt(var7);
         int var9 = var8.getId();
         if(var9 != 2131755243 && var9 != 2131755244) {
            var6.add(var8);
         }
      }

      Iterator var11 = var6.iterator();

      while(var11.hasNext()) {
         View var12 = (View)var11.next();
         var4.removeView(var12);
      }

      this.showLoadingIndicator();
   }

   public void onCreate(Bundle var1) {
      this.mSavedInstanceState = var1;
      this.setContentView(2130968665);
      NavigationManager var2 = new NavigationManager(this);
      this.mNavigationManager = var2;
      if(var1 != null) {
         int var3 = var1.getInt("last_shown_error_hash");
         this.mLastShownErrorHash = var3;
      }

      super.onCreate(var1);
      CustomActionBar var4 = CustomActionBarFactory.getInstance(this);
      this.mActionBar = var4;
      CustomActionBar var5 = this.mActionBar;
      NavigationManager var6 = this.mNavigationManager;
      var5.initialize(var6, this);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuInflater var3 = this.getMenuInflater();
      var3.inflate(2131689474, var1);
      var3.inflate(2131689473, var1);
      var3.inflate(2131689472, var1);
      this.mActionBar.configureMenu(this, var1);
      MenuItem var4 = var1.findItem(2131755339);
      this.mMyCollectionItem = var4;
      this.updateConsumptionAppMenu();
      NavigationManager var5 = this.mNavigationManager;
      MainActivity.6 var6 = new MainActivity.6();
      var5.addOnBackStackChangedListener(var6);
      return true;
   }

   protected void onDestroy() {
      this.mNavigationManager.terminate();
      super.onDestroy();
   }

   public boolean onMenuItemSelected(int var1, MenuItem var2) {
      boolean var3 = true;
      boolean var4;
      switch(var2.getItemId()) {
      case 2131755332:
         FinskyApp var5 = FinskyApp.get();
         Intent var6 = new Intent(var5, SettingsActivity.class);
         this.startActivityForResult(var6, 31);
         break;
      case 2131755333:
         this.showDialog(0);
         break;
      case 2131755334:
         FinskyApp.get().getAnalytics().logPageView((String)null, (String)null, "help");
         Uri var7 = Uri.parse(BillingUtils.replaceLocale((String)G.vendingSupportUrl.get()));
         Intent var8 = new Intent("android.intent.action.VIEW", var7);
         String var9 = this.getPackageName();
         var8.putExtra("com.android.browser.application_id", var9);
         this.startActivity(var8);
         var4 = true;
         return var4;
      case 2131755335:
      case 2131755336:
      case 2131755337:
      case 2131755338:
      default:
         var3 = super.onMenuItemSelected(var1, var2);
         break;
      case 2131755339:
         this.onMyCollectionSelected();
      }

      var4 = var3;
      return var4;
   }

   public void onNegativeClick(int var1, Bundle var2) {}

   protected void onNewIntent(Intent var1) {
      this.setIntent(var1);
      if(!this.mStateSaved) {
         this.handleIntent();
      } else {
         this.mStateSaved = (boolean)0;
         super.onNewIntent(var1);
      }
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2 = true;
      switch(var1.getItemId()) {
      case 16908332:
         this.mNavigationManager.goUp();
         break;
      case 2131755013:
         this.mActionBar.shareButtonClicked(this);
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   protected void onPause() {
      super.onPause();
      FinskyInstance.get().getNotifier().setNotificationListener((NotificationListener)null);
      int var1 = RequestQueue.getSequenceNumber();
      this.mSequenceNumberToDrainFrom = var1;
   }

   public void onPositiveClick(int var1, Bundle var2) {
      if(this.mNavigationManager != null) {
         this.mNavigationManager.onPositiveClick(var1, var2);
      }
   }

   public boolean onPrepareOptionsMenu(Menu var1) {
      super.onPrepareOptionsMenu(var1);
      if(((Boolean)G.debugOptionsEnabled.get()).booleanValue()) {
         this.setupDebugMenu(var1);
      }

      if(((Boolean)G.dcbDebugOptionsEnabled.get()).booleanValue()) {
         this.setupDcbDebugMenu(var1);
      }

      return true;
   }

   protected void onReady(boolean var1) {
      label13: {
         if(this.mSavedInstanceState != null) {
            NavigationManager var2 = this.mNavigationManager;
            Bundle var3 = this.mSavedInstanceState;
            if(var2.deserialize(var3)) {
               break label13;
            }
         }

         if(var1) {
            this.handleIntent();
         }
      }

      this.mSavedInstanceState = null;
      Looper var4 = Looper.getMainLooper();
      Handler var5 = new Handler(var4);
      MainActivity.3 var6 = new MainActivity.3();
      long var7 = (long)((Integer)G.initializeBillingDelayMs.get()).intValue();
      var5.postDelayed(var6, var7);
   }

   protected void onRestart() {
      super.onRestart();
      this.mNavigationManager.refreshPage();
   }

   protected void onResume() {
      super.onResume();
      Notifier var1 = FinskyInstance.get().getNotifier();
      NotificationListener var2 = this.mNotificationListener;
      var1.setNotificationListener(var2);
      this.mStateSaved = (boolean)0;
   }

   protected void onSaveInstanceState(Bundle var1) {
      if(this.mSavedInstanceState != null) {
         Bundle var2 = this.mSavedInstanceState;
         var1.putAll(var2);
      } else {
         this.mNavigationManager.serialize(var1);
      }

      super.onSaveInstanceState(var1);
      this.mStateSaved = (boolean)1;
      int var3 = this.mLastShownErrorHash;
      var1.putInt("last_shown_error_hash", var3);
   }

   protected void onStart() {
      super.onStart();
      this.mStateSaved = (boolean)0;
   }

   protected void onStop() {
      super.onStop();
      if(this.mSequenceNumberToDrainFrom == -1) {
         FinskyApp var1 = FinskyApp.get();
         int var2 = RequestQueue.getSequenceNumber();
         var1.drainAllRequests(var2);
      } else {
         FinskyApp var3 = FinskyApp.get();
         int var4 = this.mSequenceNumberToDrainFrom;
         var3.drainAllRequests(var4);
         this.mSequenceNumberToDrainFrom = -1;
      }
   }

   public void showErrorDialog(String var1, String var2, boolean var3) {
      if(!TextUtils.isEmpty(var2)) {
         if(this.isStateSaved()) {
            Object[] var4 = new Object[0];
            FinskyLog.e(var2, var4);
         } else {
            ErrorDialog.show(this.getSupportFragmentManager(), (String)null, var2, var3);
         }
      } else {
         Object[] var6 = new Object[0];
         FinskyLog.wtf("Unknown error with empty error message.", var6);
      }
   }

   public void updateBreadcrumb(String var1) {
      this.mActionBar.updateBreadcrumb(var1);
      this.updateConsumptionAppMenu();
   }

   public void updateCurrentBackendId(int var1) {
      this.mActionBar.updateCurrentBackendId(var1);
   }

   class 2 implements FragmentManager.OnBackStackChangedListener {

      2() {}

      public void onBackStackChanged() {
         MainActivity.this.findViewById(2131755243).setVisibility(8);
         MainActivity.this.mNavigationManager.removeOnBackStackChangedListener(this);
      }
   }

   class 1 implements NotificationListener {

      1() {}

      public boolean showAppAlert(String var1, String var2, String var3) {
         boolean var4 = false;
         Document var5 = MainActivity.this.mNavigationManager.getCurrentDocument();
         if(var5 != null && var5.getBackend() == 3 && var5.getAppDetails().getPackageName().equals(var1)) {
            MainActivity.this.showErrorDialog(var2, var3, (boolean)0);
            var4 = true;
         }

         return var4;
      }

      public boolean showDocAlert(String var1, String var2, String var3) {
         boolean var4 = false;
         if(MainActivity.this.mNavigationManager.getCurrentDocument() != null && MainActivity.this.mNavigationManager.getCurrentDocument().getDocId().equals(var1)) {
            MainActivity.this.showErrorDialog(var2, var3, (boolean)0);
            var4 = true;
         }

         return var4;
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         MainActivity.this.initializeCarrierBillingParams();
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         MainActivity.this.initializeBilling();
      }
   }

   class 10 implements OnClickListener {

      // $FF: synthetic field
      final CharSequence[] val$options;


      10(CharSequence[] var2) {
         this.val$options = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
         Toast.makeText(MainActivity.this, "Switching environment...", 1).show();
         MainActivity var3 = MainActivity.this;
         Map var4 = FinskyDebug.ENVIRONMENTS;
         CharSequence var5 = this.val$options[var2];
         FinskyDebug.Environment var6 = (FinskyDebug.Environment)var4.get(var5);
         FinskyDebug.selectEnvironment(var3, var6);
         MainActivity.10.1 var7 = new MainActivity.10.1();
         Looper var8 = Looper.getMainLooper();
         boolean var9 = (new Handler(var8)).postDelayed(var7, 2000L);
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            MainActivity.this.restart((Account)null, (boolean)0);
         }
      }
   }

   class 6 implements FragmentManager.OnBackStackChangedListener {

      6() {}

      public void onBackStackChanged() {
         MainActivity.this.updateConsumptionAppMenu();
      }
   }

   class 11 implements android.view.View.OnClickListener {

      // $FF: synthetic field
      final View val$container;


      11(View var2) {
         this.val$container = var2;
      }

      public void onClick(View var1) {
         String var2 = FinskyApp.get().getCurrentAccountName();
         if(var2 == null) {
            Object[] var3 = new Object[0];
            FinskyLog.d("No account, restarting activity after network error", var3);
            MainActivity.this.restart();
         } else {
            MainActivity var4 = MainActivity.this;
            Account var5 = AccountHandler.findAccount(var2, var4);
            MainActivity.this.restart(var5, (boolean)0);
            MainActivity.this.showLoadingIndicator();
            MainActivity.this.findViewById(2131755243).setVisibility(0);
            this.val$container.setVisibility(8);
         }
      }
   }

   class 5 implements Runnable {

      5() {}

      public void run() {
         MainActivity.this.initializeCarrierBillingProvisioning();
      }
   }

   class 8 implements OnMenuItemClickListener {

      8() {}

      public boolean onMenuItemClick(MenuItem var1) {
         MainActivity.this.buildSelectEnvironmentDialog();
         return true;
      }
   }

   class 7 implements OnMenuItemClickListener {

      7() {}

      public boolean onMenuItemClick(MenuItem var1) {
         FinskyApp var2 = FinskyApp.get();
         MainActivity.7.1 var3 = new MainActivity.7.1();
         var2.clearCacheAsync(var3);
         return true;
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Process.killProcess(Process.myPid());
         }
      }
   }

   class 9 implements OnMenuItemClickListener {

      9() {}

      public boolean onMenuItemClick(MenuItem var1) {
         MainActivity var2 = MainActivity.this;
         Intent var3 = new Intent(var2, DcbDebugActivity.class);
         MainActivity.this.startActivity(var3);
         return true;
      }
   }
}
