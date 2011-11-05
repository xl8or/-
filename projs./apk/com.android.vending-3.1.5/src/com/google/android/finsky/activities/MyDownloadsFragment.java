package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.adapters.MyDownloadsAdapter;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.AssetView;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.AssetSupport;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.model.Asset;
import com.google.android.vending.model.AssetList;
import java.util.List;

public class MyDownloadsFragment extends PageFragment implements Response.Listener<AssetList>, SimpleAlertDialog.Listener, OnClickListener {

   private static final String KEY_ASSET = "MyDownloadsFragment.Asset";
   private static final String KEY_LIST_POSITION = "MyDownloadsFragment.ListPosition";
   private MyDownloadsAdapter mAdapter;
   private AssetStore.LocalAssetChangeListener mAssetChangeListener;
   private AssetList mAssetList;
   private Asset mCurrentAsset;
   private DownloadQueueListener mDownloadQueueListener;
   private Installer.InstallerRequestListener mInstallerRequestListener;
   private ListView mListView;
   private Bundle mSavedInstanceState;
   private Parcelable mSavedListState;


   public MyDownloadsFragment() {}

   private void asyncRefreshAdapter() {
      Looper var1 = Looper.getMainLooper();
      Handler var2 = new Handler(var1);
      MyDownloadsFragment.4 var3 = new MyDownloadsFragment.4();
      var2.post(var3);
   }

   private void attachAssetChangeListener() {
      AssetStore var1 = FinskyInstance.get().getAssetStore();
      if(this.mAssetChangeListener != null) {
         AssetStore.LocalAssetChangeListener var2 = this.mAssetChangeListener;
         var1.removeListener(var2);
      }

      MyDownloadsFragment.1 var3 = new MyDownloadsFragment.1();
      this.mAssetChangeListener = var3;
      AssetStore.LocalAssetChangeListener var4 = this.mAssetChangeListener;
      var1.addListener(var4);
   }

   private void attachDownloadQueueListener() {
      DownloadQueue var1 = FinskyInstance.get().getDownloadQueue();
      if(this.mDownloadQueueListener != null) {
         DownloadQueueListener var2 = this.mDownloadQueueListener;
         var1.removeListener(var2);
      }

      MyDownloadsFragment.2 var3 = new MyDownloadsFragment.2();
      this.mDownloadQueueListener = var3;
      DownloadQueueListener var4 = this.mDownloadQueueListener;
      var1.addListener(var4);
   }

   private void attachInstallerRequestListener() {
      Installer var1 = FinskyInstance.get().getInstaller();
      if(this.mInstallerRequestListener != null) {
         Installer.InstallerRequestListener var2 = this.mInstallerRequestListener;
         var1.removeListener(var2);
      }

      MyDownloadsFragment.3 var3 = new MyDownloadsFragment.3();
      this.mInstallerRequestListener = var3;
      Installer.InstallerRequestListener var4 = this.mInstallerRequestListener;
      var1.addListener(var4);
   }

   private void clearAssetDetails() {
      View var1 = this.mDataView.findViewById(2131755254);
      if(var1 != null) {
         var1.setVisibility(8);
         this.mDataView.findViewById(2131755253).setVisibility(0);
      }
   }

   private void goToAssetDetails(Asset var1) {
      String var2 = makeDetailsUrl(var1);
      FinskyApp.get().getDfeApi().invalidateDetailsCache(var2, (boolean)1);
      this.mNavigationManager.goToDetails(var2, "myApps");
   }

   private void goToPurchase(Asset var1) {
      NavigationManager var2 = this.mNavigationManager;
      String var3 = makeDetailsUrl(var1);
      Object var4 = null;
      var2.goToPurchase(var3, 1, "myApps", (String)null, (String)var4);
   }

   private boolean isAdditionalInfoShowing() {
      boolean var1;
      if(this.mDataView != null && this.mDataView.findViewById(2131755254) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static String makeDetailsUrl(Asset var0) {
      String var1 = var0.getPackageName();
      return DfeApi.createDetailsUrlFromId(3, var1);
   }

   public static MyDownloadsFragment newInstance() {
      MyDownloadsFragment var0 = new MyDownloadsFragment();
      DfeToc var1 = FinskyApp.get().getToc();
      var0.setArguments(var1);
      return var0;
   }

   private void refundAsset(String var1) {
      MyDownloadsFragment.6 var2 = new MyDownloadsFragment.6(var1);
      AssetSupport.refund(var1, var2);
   }

   private void selectFirstAsset() {
      if(this.mAssetList.getAssets().size() != 0) {
         if(this.mCurrentAsset == null) {
            if(this.isAdditionalInfoShowing()) {
               int var1 = 0;

               while(true) {
                  int var2 = this.mAdapter.getCount();
                  if(var1 >= var2) {
                     return;
                  }

                  if(this.mAdapter.getItemViewType(var1) != 0) {
                     this.mListView.setItemChecked(var1, (boolean)1);
                     Asset var3 = (Asset)this.mAdapter.getItem(var1);
                     this.mCurrentAsset = var3;
                     this.syncCurrentAsset();
                     return;
                  }

                  ++var1;
               }
            }
         }
      }
   }

   private void showAssetDetails(Asset var1) {
      if(var1 == null) {
         this.clearAssetDetails();
      } else {
         this.mCurrentAsset = var1;
         AssetView var2 = (AssetView)this.mDataView.findViewById(2131755254);
         if(var2 != null) {
            var2.setVisibility(0);
            this.mDataView.findViewById(2131755253).setVisibility(8);
            FragmentManager var3 = this.getFragmentManager();
            MyDownloadsFragment.5 var4 = new MyDownloadsFragment.5(var1);
            Bundle var5 = this.mSavedInstanceState;
            var2.bind(var3, var1, var4, var5);
         }
      }
   }

   private void syncCurrentAsset() {
      if(this.mCurrentAsset == null) {
         this.clearAssetDetails();
      } else {
         String var1 = this.mCurrentAsset.getPackageName();
         int var2 = 0;

         while(true) {
            int var3 = this.mAdapter.getCount();
            if(var2 >= var3) {
               this.clearAssetDetails();
               return;
            }

            Object var4 = this.mAdapter.getItem(var2);
            if(var4 != null && var4 instanceof Asset) {
               String var5 = ((Asset)var4).getPackageName();
               if(var1.equals(var5)) {
                  this.mListView.setItemChecked(var2, (boolean)1);
                  Asset var6 = (Asset)var4;
                  this.showAssetDetails(var6);
                  return;
               }
            }

            ++var2;
         }
      }
   }

   protected int getLayoutRes() {
      return 2130968668;
   }

   protected boolean isDataReady() {
      boolean var1;
      if(this.mAssetList != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      this.mSavedInstanceState = var1;
      FinskyApp.get().getAnalytics().logPageView((String)null, (String)null, "myApps");
      if(var1 != null) {
         if(var1.containsKey("MyDownloadsFragment.ListPosition")) {
            Parcelable var2 = var1.getParcelable("MyDownloadsFragment.ListPosition");
            this.mSavedListState = var2;
         }

         Asset var3 = (Asset)var1.getParcelable("MyDownloadsFragment.Asset");
         this.mCurrentAsset = var3;
      }

      if(!this.isDataReady()) {
         this.switchToLoading();
         this.requestData();
         this.rebindActionBar();
      } else {
         this.rebindViews();
      }
   }

   public void onClick(View var1) {
      Asset var2 = MyDownloadsAdapter.getViewAsset(var1);
      if(var2 != null) {
         int var3 = this.mListView.getPositionForView(var1);
         if(var3 != -1) {
            this.mListView.setItemChecked(var3, (boolean)1);
         }

         if(this.isAdditionalInfoShowing()) {
            this.showAssetDetails(var2);
         } else {
            this.goToAssetDetails(var2);
         }
      }
   }

   public void onDestroyView() {
      DownloadQueue var1 = FinskyInstance.get().getDownloadQueue();
      if(this.mDownloadQueueListener != null) {
         DownloadQueueListener var2 = this.mDownloadQueueListener;
         var1.removeListener(var2);
      }

      if(this.mAssetChangeListener != null) {
         AssetStore var3 = FinskyInstance.get().getAssetStore();
         AssetStore.LocalAssetChangeListener var4 = this.mAssetChangeListener;
         var3.removeListener(var4);
      }

      if(this.mInstallerRequestListener != null) {
         Installer var5 = FinskyInstance.get().getInstaller();
         Installer.InstallerRequestListener var6 = this.mInstallerRequestListener;
         var5.removeListener(var6);
      }

      super.onDestroyView();
   }

   protected void onInitViewBinders() {}

   public void onNegativeClick(int var1, Bundle var2) {}

   public void onPositiveClick(int var1, Bundle var2) {
      switch(var1) {
      case 1:
         AssetSupport.uninstall(var2.getString("package_name"));
         return;
      default:
         Object[] var3 = new Object[1];
         Integer var4 = Integer.valueOf(var1);
         var3[0] = var4;
         FinskyLog.wtf("Unexpected requestCode %d", var3);
      }
   }

   public void onResponse(AssetList var1) {
      if(this.isAdded()) {
         this.mAssetList = var1;
         this.onDataChanged();
         this.selectFirstAsset();
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      if(this.isDataReady()) {
         if(this.mListView != null) {
            Parcelable var2 = this.mListView.onSaveInstanceState();
            var1.putParcelable("MyDownloadsFragment.ListPosition", var2);
         }

         if(this.mCurrentAsset != null) {
            Asset var3 = this.mCurrentAsset;
            var1.putParcelable("MyDownloadsFragment.Asset", var3);
         }

         AssetView var4 = (AssetView)this.mDataView.findViewById(2131755254);
         if(var4 != null) {
            var4.saveInstanceState(var1);
         }
      }

      super.onSaveInstanceState(var1);
   }

   public void rebindActionBar() {
      PageFragmentHost var1 = this.mPageFragmentHost;
      String var2 = this.mContext.getString(2131230986);
      var1.updateBreadcrumb(var2);
      this.mPageFragmentHost.updateCurrentBackendId(3);
   }

   protected void rebindViews() {
      if(this.isDataReady()) {
         super.rebindViews();
         this.rebindActionBar();
         Context var1 = this.mDataView.getContext();
         Installer var2 = FinskyInstance.get().getInstaller();
         boolean var3 = this.isAdditionalInfoShowing();
         MyDownloadsAdapter var4 = new MyDownloadsAdapter(var1, var2, this, var3);
         this.mAdapter = var4;
         MyDownloadsAdapter var5 = this.mAdapter;
         List var6 = this.mAssetList.getAssets();
         var5.addAssets(var6);
         ListView var7 = (ListView)this.mDataView.findViewById(2131755248);
         this.mListView = var7;
         ListView var8 = this.mListView;
         MyDownloadsAdapter var9 = this.mAdapter;
         var8.setAdapter(var9);
         this.mListView.setItemsCanFocus((boolean)1);
         if(this.mSavedListState != null) {
            ListView var10 = this.mListView;
            Parcelable var11 = this.mSavedListState;
            var10.onRestoreInstanceState(var11);
            this.mSavedListState = null;
         }

         this.syncCurrentAsset();
         this.attachDownloadQueueListener();
         this.attachAssetChangeListener();
         this.attachInstallerRequestListener();
      }
   }

   protected void requestData() {
      FinskyApp.get().getVendingApi().getVendingHistory(this, this);
   }

   // $FF: synthetic class
   static class 7 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult = new int[AssetSupport.RefundResult.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult;
            int var1 = AssetSupport.RefundResult.SUCCESS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult;
            int var3 = AssetSupport.RefundResult.CANNOT_REFUND.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult;
            int var5 = AssetSupport.RefundResult.BAD_REQUEST.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult;
            int var7 = AssetSupport.RefundResult.NETWORK_ERROR.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }

   class 6 implements AssetSupport.RefundListener {

      // $FF: synthetic field
      final String val$packageName;


      6(String var2) {
         this.val$packageName = var2;
      }

      public void onComplete(AssetSupport.RefundResult var1) {
         int[] var2 = MyDownloadsFragment.7.$SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult;
         int var3 = var1.ordinal();
         switch(var2[var3]) {
         case 1:
            AssetSupport.uninstall(this.val$packageName);
            return;
         case 2:
         default:
            return;
         case 3:
            AssetSupport.showRefundFailureDialog(MyDownloadsFragment.this.getFragmentManager());
            MyDownloadsFragment.this.refresh();
            return;
         case 4:
            AssetSupport.showRefundFailureDialog(MyDownloadsFragment.this.getFragmentManager());
            MyDownloadsFragment.this.refresh();
         }
      }
   }

   class 2 implements DownloadQueueListener {

      2() {}

      public void onAdd(Download var1) {
         MyDownloadsFragment.this.asyncRefreshAdapter();
      }

      public void onUpdate() {
         MyDownloadsFragment.this.asyncRefreshAdapter();
      }
   }

   class 3 implements Installer.InstallerRequestListener {

      3() {}

      public void onUpdate() {
         MyDownloadsFragment.this.asyncRefreshAdapter();
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         MyDownloadsFragment.this.mAdapter.notifyDataSetChanged();
         MyDownloadsFragment.this.syncCurrentAsset();
      }
   }

   class 5 implements AssetView.AssetActionHandler {

      // $FF: synthetic field
      final Asset val$asset;


      5(Asset var2) {
         this.val$asset = var2;
      }

      public void install() {
         PackageManager var1 = FinskyApp.get().getPackageManager();
         String var2 = this.val$asset.getPackageName();
         Intent var3 = var1.getLaunchIntentForPackage(var2);
         if(var3 != null) {
            MyDownloadsFragment.this.startActivity(var3);
         } else {
            MyDownloadsFragment var4 = MyDownloadsFragment.this;
            Asset var5 = this.val$asset;
            var4.goToPurchase(var5);
         }
      }

      public void launch() {
         PackageManager var1 = FinskyApp.get().getPackageManager();
         String var2 = this.val$asset.getPackageName();
         Intent var3 = var1.getLaunchIntentForPackage(var2);
         Intent var4 = var3.setFlags(268435456);
         MyDownloadsFragment.this.startActivity(var3);
      }

      public void refund() {
         MyDownloadsFragment var1 = MyDownloadsFragment.this;
         String var2 = this.val$asset.getPackageName();
         var1.refundAsset(var2);
      }

      public void uninstall() {
         String var1 = this.val$asset.getPackageName();
         MyDownloadsFragment var2 = MyDownloadsFragment.this;
         AssetSupport.showUninstallConfirmationDialog(var1, var2);
      }

      public void update() {
         MyDownloadsFragment var1 = MyDownloadsFragment.this;
         Asset var2 = this.val$asset;
         var1.goToPurchase(var2);
      }

      public void viewDetails() {
         MyDownloadsFragment var1 = MyDownloadsFragment.this;
         Asset var2 = this.val$asset;
         var1.goToAssetDetails(var2);
      }
   }

   class 1 implements AssetStore.LocalAssetChangeListener {

      1() {}

      public void onAssetAdded(LocalAsset var1) {
         MyDownloadsFragment.this.asyncRefreshAdapter();
      }

      public void onAssetChanged(LocalAsset var1, AssetState var2) {
         if(var1.getState() != var2) {
            MyDownloadsFragment.this.asyncRefreshAdapter();
         }
      }

      public void onAssetDeleted(String var1) {
         MyDownloadsFragment.this.asyncRefreshAdapter();
      }
   }
}
