package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.DetailsSummaryViewBinder;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.adapters.DownloadProgressHelper;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.utils.AssetSupport;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.PackageManagerHelper;
import java.util.Date;

public class DetailsSummaryAppsViewBinder extends DetailsSummaryViewBinder implements PackageMonitorReceiver.PackageStatusListener {

   private static final int ALERT_ID_CONFIRM_UNINSTALL = 1;
   private static final int ALERT_ID_UNINSTALL_FAILED = 2;
   private static final String KEY_PACKAGE_NAME = "package_name";
   private static final String TAG_UNINSTALL_DIALOG = "uninstall_dialog";
   private final AssetStore mAssetStore;
   private final PackageInfoCache mPackageInfoCache;
   private final PackageMonitorReceiver mPackageMonitorReceiver;


   public DetailsSummaryAppsViewBinder(PackageMonitorReceiver var1, AssetStore var2, PackageInfoCache var3, DfeToc var4) {
      super(var4);
      this.mPackageMonitorReceiver = var1;
      this.mAssetStore = var2;
      this.mPackageInfoCache = var3;
   }

   private String getUpdateReferrerUrl(String var1) {
      String var4;
      if(this.mExternalReferrer != null && this.mExternalReferrer.contains("details?")) {
         String var2 = this.mExternalReferrer;
         String var3 = "manualUpdate?doc=" + var1;
         var4 = var2.replaceFirst("details", var3);
      } else {
         var4 = "manualUpdate?doc=" + var1;
      }

      return var4;
   }

   private void logEvent(String var1, String var2, String var3) {
      if(FinskyApp.get().getAnalytics() != null) {
         Analytics var4 = FinskyApp.get().getAnalytics();
         StringBuilder var5 = (new StringBuilder()).append(var1).append("?doc=").append(var2);
         String var6;
         if(TextUtils.isEmpty(var3)) {
            var6 = "";
         } else {
            var6 = "&" + var3;
         }

         String var7 = var5.append(var6).toString();
         var4.logPageView((String)null, (String)null, var7);
      }
   }

   private void refundAndUninstallAsset(String var1, boolean var2) {
      LocalAsset var3 = this.mAssetStore.getAsset(var1);
      if(!var2) {
         this.uninstallAsset(var1, var3, (boolean)1);
      } else {
         DetailsSummaryAppsViewBinder.4 var4 = new DetailsSummaryAppsViewBinder.4(var1, var3);
         this.refundAsset(var1, var4);
      }
   }

   private void refundAsset(String var1, Runnable var2) {
      this.mIsPendingRefund = (boolean)1;
      DetailsSummaryAppsViewBinder.5 var3 = new DetailsSummaryAppsViewBinder.5(var2);
      AssetSupport.refund(var1, var3);
   }

   private void showDynamicButtons() {
      this.mDynamicSection.setVisibility(0);
      this.mButtonSection.setVisibility(0);
   }

   private void uninstallAsset(String var1, LocalAsset var2, boolean var3) {
      if(var3 && var2 != null) {
         FragmentManager var4 = this.mContainerFragment.getFragmentManager();
         if(var4.findFragmentByTag("uninstall_dialog") == null) {
            PackageInfoCache var5 = this.mPackageInfoCache;
            String var6 = var2.getPackage();
            boolean var7 = var5.isSystemPackage(var6);
            int var8;
            if(var7) {
               var8 = 2131230938;
            } else {
               var8 = 2131230940;
            }

            int var9;
            if(var7) {
               var9 = 2131230939;
            } else {
               var9 = 2131230941;
            }

            SimpleAlertDialog var10 = SimpleAlertDialog.newInstance(var8, var9, 2131231030, 2131230813);
            Bundle var11 = new Bundle();
            var11.putString("package_name", var1);
            PageFragment var12 = this.mContainerFragment;
            var10.setCallback(var12, 1, var11);
            var10.show(var4, "uninstall_dialog");
         }
      } else {
         this.uninstallAssetSilently(var1, var2);
      }
   }

   private void uninstallAssetSilently(String var1, LocalAsset var2) {
      this.logEvent("uninstall", var1, (String)null);
      if(var2 != null) {
         var2.setStateUninstalling();
         this.refresh();
      }

      PackageManagerHelper.uninstallPackage(var1);
   }

   protected void handleRefundFailure() {
      this.mIsPendingRefund = (boolean)0;
      AssetSupport.showRefundFailureDialog(this.mContainerFragment.getFragmentManager());
      this.refresh();
   }

   public void init(Context var1, NavigationManager var2, BitmapLoader var3, PageFragment var4, boolean var5, boolean var6, String var7) {
      super.init(var1, var2, var3, var4, var5, var6, var7);
      if(var6) {
         this.mPackageMonitorReceiver.detach(this);
         this.mPackageMonitorReceiver.attach(this);
      }
   }

   public void onDestroyView() {
      this.mPackageMonitorReceiver.detach(this);
      super.onDestroyView();
   }

   public void onNegativeClick(int var1, Bundle var2) {}

   public void onPackageAdded(String var1) {
      if(this.mDoc != null) {
         if(this.mDoc.getAppDetails() != null) {
            if(this.mDoc.getAppDetails().getPackageName().equals(var1)) {
               this.syncDynamicSection();
               this.mNavigationManager.refreshPage();
            }
         }
      }
   }

   public void onPackageAvailabilityChanged(String[] var1, boolean var2) {}

   public void onPackageChanged(String var1) {}

   public void onPackageRemoved(String var1, boolean var2) {}

   public void onPositiveClick(int var1, Bundle var2) {
      switch(var1) {
      case 1:
         String var5 = var2.getString("package_name");
         if(var5 == null) {
            return;
         } else if(this.mAssetStore == null) {
            return;
         } else {
            LocalAsset var6 = this.mAssetStore.getAsset(var5);
            if(var6 == null) {
               return;
            }

            this.uninstallAssetSilently(var5, var6);
            return;
         }
      default:
         Object[] var3 = new Object[1];
         Integer var4 = Integer.valueOf(var1);
         var3[0] = var4;
         FinskyLog.wtf("Unexpected requestCode %d", var3);
      case 2:
      }
   }

   protected void setupActionButtons(boolean var1) {
      Button var2 = (Button)this.mLayout.findViewById(2131755077);
      Button var3 = (Button)this.mLayout.findViewById(2131755025);
      Button var4 = (Button)this.mLayout.findViewById(2131755027);
      Button var5 = (Button)this.mLayout.findViewById(2131755026);
      var3.setVisibility(8);
      var2.setVisibility(8);
      var4.setVisibility(8);
      var5.setVisibility(8);
      if(!this.mHideDynamicFeatures) {
         if(!var1) {
            String var6 = this.mDoc.getAppDetails().getPackageName();
            LocalAsset var7 = this.mAssetStore.getAsset(var6);
            byte var8 = 0;
            if(var7 != null) {
               label66: {
                  Long var9 = var7.getRefundPeriodEndTime();
                  if(var9 != null) {
                     long var10 = var9.longValue();
                     Date var12 = new Date(var10);
                     Date var13 = new Date();
                     if(var12.after(var13)) {
                        var8 = 1;
                        break label66;
                     }
                  }

                  var8 = 0;
               }
            }

            Document var14 = this.mDoc;
            PackageInfoCache var15 = this.mPackageInfoCache;
            boolean var16 = var14.isLocallyAvailable(var15);
            if(var16 || var8 != 0) {
               int var17 = 0;
               if(var7 != null) {
                  boolean var18 = var7.isUninstallable();
                  if(this.mPackageInfoCache.isSystemPackage(var6)) {
                     var18 = this.mPackageInfoCache.isUpdatedSystemPackage(var6);
                  }

                  if(var18) {
                     var4.setVisibility(0);
                     ++var17;
                     int var19;
                     if(var8 != 0) {
                        var19 = 2131231032;
                     } else {
                        var19 = 2131230937;
                     }

                     var4.setText(var19);
                     DetailsSummaryAppsViewBinder.2 var22 = new DetailsSummaryAppsViewBinder.2(var7, (boolean)var8);
                     var4.setOnClickListener(var22);
                  } else if(var8 != 0) {
                     var4.setVisibility(0);
                     ++var17;
                     var4.setText(2131231032);
                     DetailsSummaryAppsViewBinder.3 var50 = new DetailsSummaryAppsViewBinder.3(var7);
                     var4.setOnClickListener(var50);
                  }
               }

               int var23 = this.mPackageInfoCache.getPackageVersion(var6);
               if(var23 >= 0) {
                  int var24 = this.mDoc.getAppDetails().getVersionCode();
                  if(var23 < var24) {
                     Document var25 = this.mDoc;
                     DfeToc var26 = this.mDfeToc;
                     if(var25.isAvailable(var26)) {
                        var5.setVisibility(0);
                        NavigationManager var27 = this.mNavigationManager;
                        Document var28 = this.mDoc;
                        String var29 = this.getUpdateReferrerUrl(var6);
                        String var30 = this.mExternalReferrer;
                        OnClickListener var31 = var27.getBuyImmediateClickListener(var28, 1, var29, var30);
                        var5.setOnClickListener(var31);
                        ++var17;
                     }
                  }
               }

               if(var17 < 2) {
                  Document var32 = this.mDoc;
                  PackageInfoCache var33 = this.mPackageInfoCache;
                  if(var32.canLaunch(var33)) {
                     var3.setVisibility(0);
                     NavigationManager var34 = this.mNavigationManager;
                     Document var35 = this.mDoc;
                     String var36 = this.mAccountName;
                     OnClickListener var37 = var34.getOpenClickListener(var35, var36);
                     var3.setOnClickListener(var37);
                  }
               }

               this.showDynamicButtons();
            }

            if(!var16) {
               Document var38 = this.mDoc;
               DfeToc var39 = this.mDfeToc;
               if(var38.isAvailable(var39)) {
                  var2.setVisibility(0);
                  Document var40 = this.mDoc;
                  PackageInfoCache var41 = this.mPackageInfoCache;
                  boolean var42 = var40.ownedByUser(var41);
                  String var43 = this.getBuyButtonString(var42);
                  var2.setText(var43);
                  NavigationManager var44 = this.mNavigationManager;
                  Document var45 = this.mDoc;
                  String var46 = this.mDoc.getDetailsUrl();
                  String var47 = this.mExternalReferrer;
                  OnClickListener var48 = var44.getBuyImmediateClickListener(var45, 1, var46, var47);
                  var2.setOnClickListener(var48);
               }
            }
         }
      }
   }

   protected void syncDynamicSection() {
      this.resetDynamicStatus();
      if(this.mDoc.getBackend() != 3) {
         super.syncDynamicSection();
      } else {
         AssetStore var1 = FinskyInstance.get().getAssetStore();
         String var2 = this.mDoc.getAppDetails().getPackageName();
         LocalAsset var3 = var1.getAsset(var2);
         if(var3 != null) {
            label38: {
               AssetState var4 = var3.getState();
               AssetState var5 = AssetState.INSTALLING;
               if(var4 != var5) {
                  AssetState var6 = AssetState.UNINSTALLING;
                  if(var4 != var6) {
                     break label38;
                  }
               }

               AssetState var7 = AssetState.INSTALLING;
               int var8;
               if(var4 == var7) {
                  var8 = 2131231028;
               } else {
                  var8 = 2131231029;
               }

               this.showDynamicStatus(var8);
               this.setupActionButtons((boolean)1);
               return;
            }
         }

         DownloadQueue var9 = FinskyInstance.get().getDownloadQueue();
         if(var9 == null) {
            super.syncDynamicSection();
         } else if(!TextUtils.isEmpty(this.mDoc.getAppDetails().getPackageName())) {
            String var10 = this.mDoc.getAppDetails().getPackageName();
            Download var11 = var9.getByPackageName(var10);
            if(var11 == null) {
               super.syncDynamicSection();
            } else {
               this.mDynamicSection.setVisibility(0);
               ViewGroup var12 = this.mDynamicSection;
               Context var13 = this.mContext;
               int var14 = this.mDoc.getBackend();
               int var15 = CorpusMetadata.getBackendHintColor(var13, var14);
               var12.setBackgroundColor(var15);
               ViewGroup var16 = (ViewGroup)this.mDynamicSection.findViewById(2131755178);
               var16.setVisibility(0);
               TextView var17 = (TextView)var16.findViewById(2131755135);
               TextView var18 = (TextView)var16.findViewById(2131755134);
               ProgressBar var19 = (ProgressBar)var16.findViewById(2131755136);
               DownloadProgressHelper.configureDownloadProgressUi(this.mContext, var3, var11, var17, var18, var19);
               ImageView var20 = (ImageView)var16.findViewById(2131755133);
               DetailsSummaryAppsViewBinder.1 var21 = new DetailsSummaryAppsViewBinder.1(var11);
               var20.setOnClickListener(var21);
               ((TextView)this.mLayout.findViewById(2131755102)).setSelected((boolean)0);
               this.setupActionButtons((boolean)1);
            }
         }
      }
   }

   // $FF: synthetic class
   static class 6 {

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

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final Download val$download;


      1(Download var2) {
         this.val$download = var2;
      }

      public void onClick(View var1) {
         this.val$download.cancel();
      }
   }

   class 3 implements OnClickListener {

      // $FF: synthetic field
      final LocalAsset val$localAsset;


      3(LocalAsset var2) {
         this.val$localAsset = var2;
      }

      public void onClick(View var1) {
         DetailsSummaryAppsViewBinder var2 = DetailsSummaryAppsViewBinder.this;
         String var3 = this.val$localAsset.getPackage();
         var2.refundAsset(var3, (Runnable)null);
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final boolean val$finalIsRefundable;
      // $FF: synthetic field
      final LocalAsset val$localAsset;


      2(LocalAsset var2, boolean var3) {
         this.val$localAsset = var2;
         this.val$finalIsRefundable = var3;
      }

      public void onClick(View var1) {
         DetailsSummaryAppsViewBinder var2 = DetailsSummaryAppsViewBinder.this;
         String var3 = this.val$localAsset.getPackage();
         boolean var4 = this.val$finalIsRefundable;
         var2.refundAndUninstallAsset(var3, var4);
      }
   }

   class 5 implements AssetSupport.RefundListener {

      // $FF: synthetic field
      final Runnable val$successRunnable;


      5(Runnable var2) {
         this.val$successRunnable = var2;
      }

      public void onComplete(AssetSupport.RefundResult var1) {
         DetailsSummaryAppsViewBinder.this.mIsPendingRefund = (boolean)0;
         int[] var2 = DetailsSummaryAppsViewBinder.6.$SwitchMap$com$google$android$finsky$utils$AssetSupport$RefundResult;
         int var3 = var1.ordinal();
         switch(var2[var3]) {
         case 1:
            DfeApi var4 = FinskyApp.get().getDfeApi();
            String var5 = DetailsSummaryAppsViewBinder.this.mDoc.getDetailsUrl();
            var4.invalidateDetailsCache(var5, (boolean)1);
            DetailsSummaryAppsViewBinder.this.refreshFragment();
            if(this.val$successRunnable == null) {
               return;
            }

            this.val$successRunnable.run();
            return;
         case 2:
            DetailsSummaryAppsViewBinder.this.refreshFragment();
            return;
         case 3:
            DetailsSummaryAppsViewBinder.this.handleRefundFailure();
            return;
         case 4:
            DetailsSummaryAppsViewBinder.this.handleRefundFailure();
            return;
         default:
         }
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final LocalAsset val$localAsset;
      // $FF: synthetic field
      final String val$packageName;


      4(String var2, LocalAsset var3) {
         this.val$packageName = var2;
         this.val$localAsset = var3;
      }

      public void run() {
         DetailsSummaryAppsViewBinder var1 = DetailsSummaryAppsViewBinder.this;
         String var2 = this.val$packageName;
         LocalAsset var3 = this.val$localAsset;
         var1.uninstallAsset(var2, var3, (boolean)0);
      }
   }
}
