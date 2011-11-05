package com.google.android.finsky.services;

import android.content.Context;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.services.ContentSyncService;
import com.google.android.finsky.utils.Utils;

public class CheckinAssetStoreListener implements AssetStore.LocalAssetChangeListener {

   private final Context mContext;
   private boolean mDirty = 0;
   private boolean mSuspended = 0;
   private final ContentSyncService.Facade mSyncService;


   public CheckinAssetStoreListener(Context var1, ContentSyncService.Facade var2) {
      this.mContext = var1;
      this.mSyncService = var2;
   }

   public boolean isSuspended() {
      Utils.ensureOnMainThread();
      return this.mSuspended;
   }

   public void onAssetAdded(LocalAsset var1) {
      if(this.mSuspended) {
         this.mDirty = (boolean)1;
      } else {
         ContentSyncService.Facade var2 = this.mSyncService;
         Context var3 = this.mContext;
         var2.sync(var3, (boolean)0);
      }
   }

   public void onAssetChanged(LocalAsset var1, AssetState var2) {
      if(!this.mSuspended && var1 != null) {
         label25: {
            AssetState var3 = var1.getState();
            AssetState var4 = AssetState.INSTALLED;
            if(var3 == var4) {
               AssetState var5 = AssetState.INSTALLED;
               if(var2 != var5) {
                  break label25;
               }
            }

            AssetState var6 = var1.getState();
            AssetState var7 = AssetState.UNINSTALLED;
            if(var6 != var7) {
               return;
            }

            AssetState var8 = AssetState.UNINSTALLED;
            if(var2 == var8) {
               return;
            }
         }

         ContentSyncService.Facade var9 = this.mSyncService;
         Context var10 = this.mContext;
         var9.sync(var10, (boolean)0);
      } else {
         this.mDirty = (boolean)1;
      }
   }

   public void onAssetDeleted(String var1) {
      if(this.mSuspended) {
         this.mDirty = (boolean)1;
      } else {
         ContentSyncService.Facade var2 = this.mSyncService;
         Context var3 = this.mContext;
         var2.sync(var3, (boolean)0);
      }
   }

   public void resume() {
      Utils.ensureOnMainThread();
      this.mSuspended = (boolean)0;
      if(this.mDirty) {
         ContentSyncService.Facade var1 = this.mSyncService;
         Context var2 = this.mContext;
         var1.sync(var2, (boolean)0);
         this.mDirty = (boolean)0;
      }
   }

   public void suspend() {
      Utils.ensureOnMainThread();
      this.mSuspended = (boolean)1;
      this.mDirty = (boolean)0;
   }
}
