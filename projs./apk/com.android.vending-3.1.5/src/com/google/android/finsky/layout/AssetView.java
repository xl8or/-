package com.google.android.finsky.layout;

import android.content.Context;
import android.content.pm.PermissionInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.layout.AppSecurityPermissions;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.AssetSupport;
import com.google.android.finsky.utils.BitmapLoader;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.ThumbnailUtils;
import com.google.android.vending.model.Asset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AssetView extends LinearLayout {

   private Asset mAsset;
   private AssetView.AssetActionHandler mHandler;


   public AssetView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public AssetView(Context var1, AttributeSet var2) {
      super(var1, var2);
      inflate(var1, 2130968582, this);
   }

   private void bind(FragmentManager var1, Bundle var2) {
      this.resetViewState();
      AssetStore var3 = FinskyInstance.get().getAssetStore();
      String var4 = this.mAsset.getPackageName();
      if(var3.getAsset(var4) != null) {
         this.showInstalledButtons();
      } else {
         this.showUninstalledButtons();
      }

      this.bindPermissions(var1, var2);
      this.bindAutoUpdate();
      this.bindButtons();
      this.bindHeader();
   }

   private void bindAutoUpdate() {
      AssetSupport.bindAutoUpdateSection(this.mAsset.getPackageName(), this);
   }

   private void bindBitmapForAsset(Asset var1, ImageView var2) {
      BitmapLoader var3 = FinskyApp.get().getBitmapLoader();
      String var4 = var1.getId();
      AssetView.7 var5 = new AssetView.7(var2);
      if(var3.getOrBindImmediately(var4, 3, var2, (Bitmap)null, var5).getBitmap() == null) {
         Drawable var7 = this.getResources().getDrawable(2130837608);
         var2.setImageDrawable(var7);
      }
   }

   private void bindButtons() {
      this.bindLaunchButton();
      this.bindUpdateButton();
      this.bindInstallButton();
      this.bindRefundButton();
      this.bindUninstallButton();
   }

   private void bindHeader() {
      TextView var1 = (TextView)this.findViewById(2131755102);
      String var2 = this.mAsset.getTitle();
      var1.setText(var2);
      TextView var3 = (TextView)this.findViewById(2131755176);
      String var4 = this.mAsset.getDevName();
      var3.setText(var4);
      View var5 = this.findViewById(2131755030);
      int var6 = CorpusMetadata.getBackendHintColor(this.getContext(), 3);
      var5.setBackgroundColor(var6);
      Asset var7 = this.mAsset;
      ImageView var8 = (ImageView)this.findViewById(2131755023);
      this.bindBitmapForAsset(var7, var8);
      View var9 = this.findViewById(2131755022);
      AssetView.6 var10 = new AssetView.6();
      var9.setOnClickListener(var10);
   }

   private void bindInstallButton() {
      View var1 = this.findViewById(2131755029);
      AssetView.3 var2 = new AssetView.3();
      var1.setOnClickListener(var2);
   }

   private void bindLaunchButton() {
      View var1 = this.findViewById(2131755025);
      AssetView.1 var2 = new AssetView.1();
      var1.setOnClickListener(var2);
   }

   private void bindPermissions(FragmentManager var1, Bundle var2) {
      String var3 = this.mAsset.getPackageName();
      ArrayList var4 = Lists.newArrayList();
      Iterator var5 = this.mAsset.getPermissions().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();

         try {
            PermissionInfo var7 = FinskyApp.get().getPackageManager().getPermissionInfo(var6, 0);
            var4.add(var7);
         } catch (NameNotFoundException var10) {
            ;
         }
      }

      ((AppSecurityPermissions)this.findViewById(2131755035)).bindInfo(var1, var3, var4, var2);
   }

   private void bindRefundButton() {
      View var1 = this.findViewById(2131755028);
      AssetView.4 var2 = new AssetView.4();
      var1.setOnClickListener(var2);
   }

   private void bindUninstallButton() {
      View var1 = this.findViewById(2131755027);
      AssetView.5 var2 = new AssetView.5();
      var1.setOnClickListener(var2);
   }

   private void bindUpdateButton() {
      View var1 = this.findViewById(2131755026);
      AssetView.2 var2 = new AssetView.2();
      var1.setOnClickListener(var2);
   }

   private void resetViewState() {
      this.findViewById(2131755024).setVisibility(8);
      this.findViewById(2131755178).setVisibility(8);
      this.findViewById(2131755025).setVisibility(8);
      this.findViewById(2131755028).setVisibility(8);
      this.findViewById(2131755027).setVisibility(8);
      this.findViewById(2131755026).setVisibility(8);
      this.findViewById(2131755029).setVisibility(8);
   }

   private void showInstalledButtons() {
      AssetStore var1 = FinskyInstance.get().getAssetStore();
      String var2 = this.mAsset.getPackageName();
      LocalAsset var3 = var1.getAsset(var2);
      AssetState var4 = var3.getState();
      this.findViewById(2131755024).setVisibility(0);
      AssetState var5 = AssetState.INSTALLED;
      if(var4 == var5) {
         long var6 = (long)var3.getVersionCode();
         long var8 = this.mAsset.getVersionCode();
         if(var6 < var8) {
            this.findViewById(2131755026).setVisibility(0);
         }

         if(FinskyApp.get().getPackageInfoCache().canLaunch(var2)) {
            this.findViewById(2131755025).setVisibility(0);
         }

         this.showRefundOrUninstallButton(var2, var3);
      } else if(var3.isDownloadingOrInstalling()) {
         this.findViewById(2131755178).setVisibility(0);
      } else {
         this.findViewById(2131755024).setVisibility(0);
         this.findViewById(2131755029).setVisibility(0);
      }
   }

   private void showRefundOrUninstallButton(String var1, LocalAsset var2) {
      PackageInfoCache var3 = FinskyApp.get().getPackageInfoCache();
      boolean var4;
      if(var3.isSystemPackage(var1) && !var3.isUpdatedSystemPackage(var1)) {
         var4 = false;
      } else {
         var4 = true;
      }

      if(var4 && var2 != null && var2.getRefundPeriodEndTime() != null) {
         long var5 = var2.getRefundPeriodEndTime().longValue();
         Date var7 = new Date(var5);
         Date var8 = new Date();
         if(var7.after(var8)) {
            this.findViewById(2131755028).setVisibility(0);
            return;
         }
      }

      if(var4) {
         this.findViewById(2131755027).setVisibility(0);
      }
   }

   private void showUninstalledButtons() {
      this.findViewById(2131755024).setVisibility(0);
      this.findViewById(2131755029).setVisibility(0);
   }

   public void bind(FragmentManager var1, Asset var2, AssetView.AssetActionHandler var3, Bundle var4) {
      this.mAsset = var2;
      this.mHandler = var3;
      this.bind(var1, var4);
   }

   public void saveInstanceState(Bundle var1) {
      ((AppSecurityPermissions)this.findViewById(2131755035)).saveInstanceState(var1);
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         AssetView.this.mHandler.install();
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(View var1) {
         AssetView.this.mHandler.refund();
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(View var1) {
         AssetView.this.mHandler.uninstall();
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(View var1) {
         AssetView.this.mHandler.viewDetails();
      }
   }

   class 7 implements BitmapLoader.BitmapLoadedHandler {

      // $FF: synthetic field
      final ImageView val$imageView;


      7(ImageView var2) {
         this.val$imageView = var2;
      }

      public void onResponse(BitmapLoader.BitmapContainer var1) {
         if(var1.getBitmap() != null) {
            ImageView var2 = this.val$imageView;
            Bitmap var3 = var1.getBitmap();
            ThumbnailUtils.setImageBitmapWithFade(var2, var3);
         }
      }
   }

   public interface AssetActionHandler {

      void install();

      void launch();

      void refund();

      void uninstall();

      void update();

      void viewDetails();
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         AssetView.this.mHandler.update();
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         AssetView.this.mHandler.launch();
      }
   }
}
