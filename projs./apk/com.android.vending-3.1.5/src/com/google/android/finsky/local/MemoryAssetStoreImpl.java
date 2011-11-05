package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AsyncWriter;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.local.LocalAssetRecord;
import com.google.android.finsky.local.LocalAssetVersion;
import com.google.android.finsky.local.LocalAssetVersionLookup;
import com.google.android.finsky.local.LocalAssetVersionRecord;
import com.google.android.finsky.local.MemoryAssetStore;
import com.google.android.finsky.local.PersistentAssetStore;
import com.google.android.finsky.local.StoredLocalAsset;
import com.google.android.finsky.local.StoredLocalAssetVersion;
import com.google.android.finsky.local.Writer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class MemoryAssetStoreImpl implements MemoryAssetStore {

   private final HashMap<String, StoredLocalAsset> mAssetLookup;
   private boolean mInitialized;
   private final List<AssetStore.LocalAssetChangeListener> mListeners;
   private final PersistentAssetStore mPersistentStore;
   private final LocalAssetVersionLookup mVersionLookup;
   private final Writer mWriter;


   public MemoryAssetStoreImpl(PersistentAssetStore var1) {
      AsyncWriter var2 = new AsyncWriter(var1);
      this(var1, var2);
   }

   protected MemoryAssetStoreImpl(PersistentAssetStore var1, Writer var2) {
      this.mInitialized = (boolean)0;
      LocalAssetVersionLookup var3 = new LocalAssetVersionLookup();
      this.mVersionLookup = var3;
      HashMap var4 = new HashMap();
      this.mAssetLookup = var4;
      ArrayList var5 = new ArrayList();
      this.mListeners = var5;
      Utils.ensureOnMainThread();
      this.mPersistentStore = var1;
      this.mWriter = var2;
   }

   private Collection<LocalAsset> getAssetsByFilter(MemoryAssetStoreImpl.AssetFilter var1) {
      this.safetyCheck();
      if(var1 == null) {
         throw new IllegalArgumentException("Can\'t get assets by filter when filter is null");
      } else {
         LinkedList var2 = new LinkedList();
         Iterator var3 = this.mAssetLookup.values().iterator();

         while(var3.hasNext()) {
            StoredLocalAsset var4 = (StoredLocalAsset)var3.next();
            if(var4 == null) {
               Object[] var5 = new Object[0];
               FinskyLog.w("A null StoredLocalAsset somehow got into the AssetStore. Removing.", var5);
               LinkedList var6 = new LinkedList();
               Iterator var7 = this.mAssetLookup.keySet().iterator();

               while(var7.hasNext()) {
                  String var8 = (String)var7.next();
                  byte var9;
                  if(this.mAssetLookup.get(var8) == null) {
                     var9 = 1;
                  } else {
                     var9 = 0;
                  }

                  Object[] var10 = new Object[]{var8, null};
                  Boolean var11 = Boolean.valueOf((boolean)var9);
                  var10[1] = var11;
                  FinskyLog.d("Checking stored asset : %s Is null : %b", var10);
                  if(var9 != 0) {
                     var6.add(var8);
                  }
               }

               Iterator var13 = var6.iterator();

               while(var13.hasNext()) {
                  String var14 = (String)var13.next();
                  this.mAssetLookup.remove(var14);
               }
            } else if(var1.matches(var4)) {
               var2.add(var4);
            }
         }

         return var2;
      }
   }

   private void notifyAssetListener(MemoryAssetStoreImpl.ListenerAction var1) {
      this.safetyCheck();
      if(!this.mListeners.isEmpty()) {
         for(int var2 = this.mListeners.size() + -1; var2 >= 0; var2 += -1) {
            AssetStore.LocalAssetChangeListener var3 = (AssetStore.LocalAssetChangeListener)this.mListeners.get(var2);
            var1.run(var3);
         }

      }
   }

   private void safetyCheck() {
      Utils.ensureOnMainThread();
      if(!this.mInitialized) {
         throw new IllegalStateException("MemoryAssetStore must be initialized beforethis method can be called.");
      }
   }

   public void addListener(AssetStore.LocalAssetChangeListener var1) {
      this.safetyCheck();
      this.mListeners.add(var1);
   }

   public boolean deleteAsset(String var1) {
      this.safetyCheck();
      StoredLocalAsset var2 = (StoredLocalAsset)this.mAssetLookup.get(var1);
      boolean var3;
      if(var2 == null) {
         var3 = false;
      } else {
         this.mAssetLookup.remove(var1);
         Writer var5 = this.mWriter;
         LocalAssetRecord var6 = var2.toRecord();
         var5.delete(var6);
         var2.markDeleted();
         Collection var7 = this.mVersionLookup.getByPackageName(var1);
         Iterator var8 = (new LinkedList(var7)).iterator();

         while(var8.hasNext()) {
            StoredLocalAssetVersion var9 = (StoredLocalAssetVersion)var8.next();
            this.deleteAssetVersion(var9);
         }

         this.notifyAssetDeleted(var1);
         var3 = true;
      }

      return var3;
   }

   public void deleteAssetVersion(StoredLocalAssetVersion var1) {
      this.safetyCheck();
      LocalAssetVersionLookup var2 = this.mVersionLookup;
      String var3 = var1.getAssetId();
      var2.remove(var3);
      Writer var5 = this.mWriter;
      LocalAssetVersionRecord var6 = var1.toRecord();
      var5.delete(var6);
      var1.markDeleted();
   }

   public boolean deleteAssetVersion(String var1) {
      this.safetyCheck();
      StoredLocalAssetVersion var2 = this.mVersionLookup.getByServerId(var1);
      boolean var3;
      if(var2 == null) {
         var3 = false;
      } else {
         this.deleteAssetVersion(var2);
         var3 = true;
      }

      return var3;
   }

   public LocalAsset getAsset(Uri var1) {
      Iterator var2 = this.mVersionLookup.getAll().iterator();

      StoredLocalAsset var6;
      while(true) {
         if(var2.hasNext()) {
            StoredLocalAssetVersion var3 = (StoredLocalAssetVersion)var2.next();
            Uri var4 = var3.getContentUri();
            if(!var1.equals(var4)) {
               continue;
            }

            String var5 = var3.getPackageName();
            var6 = this.getAsset(var5);
            break;
         }

         var6 = null;
         break;
      }

      return var6;
   }

   public StoredLocalAsset getAsset(String var1) {
      this.safetyCheck();
      return (StoredLocalAsset)this.mAssetLookup.get(var1);
   }

   public StoredLocalAsset getAssetById(String var1) {
      this.safetyCheck();
      StoredLocalAssetVersion var2 = this.mVersionLookup.getByServerId(var1);
      StoredLocalAsset var3;
      if(var2 == null) {
         var3 = null;
      } else {
         HashMap var4 = this.mAssetLookup;
         String var5 = var2.getPackageName();
         var3 = (StoredLocalAsset)var4.get(var5);
      }

      return var3;
   }

   public Collection<StoredLocalAssetVersion> getAssetVersions(String var1) {
      return Collections.unmodifiableCollection(this.mVersionLookup.getByPackageName(var1));
   }

   public Collection<LocalAsset> getAssets() {
      MemoryAssetStoreImpl.2 var1 = new MemoryAssetStoreImpl.2();
      return this.getAssetsByFilter(var1);
   }

   public Collection<LocalAsset> getAssetsByAccount(String var1) {
      MemoryAssetStoreImpl.1 var2 = new MemoryAssetStoreImpl.1(var1);
      return this.getAssetsByFilter(var2);
   }

   public Collection<LocalAsset> getAssetsByState(AssetState var1) {
      MemoryAssetStoreImpl.3 var2 = new MemoryAssetStoreImpl.3(var1);
      return this.getAssetsByFilter(var2);
   }

   public List<LocalAssetVersion> getCompleteVersionHistory(String var1) {
      this.safetyCheck();
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.mVersionLookup.getAll().iterator();

      while(var3.hasNext()) {
         StoredLocalAssetVersion var4 = (StoredLocalAssetVersion)var3.next();
         String var5 = var4.getPackageName();
         String var6 = this.getAsset(var5).getAccount();
         if(var1.equals(var6)) {
            LocalAssetVersionRecord var7 = var4.toRecord();
            var2.add(var7);
         }
      }

      MemoryAssetStoreImpl.4 var9 = new MemoryAssetStoreImpl.4();
      Collections.sort(var2, var9);
      return var2;
   }

   public MemoryAssetStoreImpl initialize() {
      Utils.ensureOnMainThread();
      if(this.mInitialized) {
         throw new IllegalStateException("Store has already been initialized");
      } else {
         Iterator var1 = this.mPersistentStore.getAllVersions().iterator();

         while(var1.hasNext()) {
            LocalAssetVersionRecord var2 = (LocalAssetVersionRecord)var1.next();
            LocalAssetVersionLookup var3 = this.mVersionLookup;
            Writer var4 = this.mWriter;
            StoredLocalAssetVersion var5 = new StoredLocalAssetVersion(var2, var4, this);
            var3.put(var5);
         }

         Iterator var6 = this.mPersistentStore.getAllAssets().iterator();

         while(var6.hasNext()) {
            LocalAssetRecord var7 = (LocalAssetRecord)var6.next();
            HashMap var8 = this.mAssetLookup;
            String var9 = var7.getPackage();
            Writer var10 = this.mWriter;
            StoredLocalAsset var11 = new StoredLocalAsset(var7, var10, this);
            var8.put(var9, var11);
         }

         this.mInitialized = (boolean)1;
         return this;
      }
   }

   public LocalAsset insertAsset(String var1, AutoUpdateState var2, String var3, int var4, String var5, AssetState var6, long var7, long var9, long var11, long var13, long var15, String var17, Uri var18, boolean var19, Long var20, String var21, String var22, Obb var23, Obb var24) {
      this.safetyCheck();
      LocalAssetRecord var25 = new LocalAssetRecord(var1, var3, var2);
      StoredLocalAsset var30 = new StoredLocalAsset;
      Writer var31 = this.mWriter;
      var30.<init>(var25, var31, this);
      HashMap var35 = this.mAssetLookup;
      var35.put(var1, var30);
      Writer var39 = this.mWriter;
      var39.insert(var25);
      StoredLocalAssetVersion var66 = this.insertAssetVersion(var1, var4, var5, var6, var7, var9, var11, var13, var15, var17, var18, var19, var20, var21, var22, var23, var24);
      this.notifyAssetAdded(var30);
      return var30;
   }

   public LocalAsset insertAsset(String var1, AutoUpdateState var2, String var3, int var4, String var5, String var6, String var7, long var8) {
      AssetState var10 = AssetState.DOWNLOAD_PENDING;
      Obb var12 = ObbFactory.createEmpty((boolean)0, var1);
      Obb var14 = ObbFactory.createEmpty((boolean)1, var1);
      return this.insertAsset(var1, var2, var3, var4, var5, var10, 0L, var8, 0L, 0L, 0L, (String)null, (Uri)null, (boolean)0, (Long)null, var6, var7, var12, var14);
   }

   public StoredLocalAssetVersion insertAssetVersion(String var1, int var2, String var3, AssetState var4, long var5, long var7, long var9, long var11, long var13, String var15, Uri var16, boolean var17, Long var18, String var19, String var20, Obb var21, Obb var22) {
      this.safetyCheck();
      LocalAssetVersionRecord var47 = new LocalAssetVersionRecord(var1, var2, var3, var4, var5, var7, var9, var11, var13, var15, var16, var17, var18, var19, var20, var21, var22);
      StoredLocalAssetVersion var48 = new StoredLocalAssetVersion;
      Writer var49 = this.mWriter;
      var48.<init>(var47, var49, this);
      LocalAssetVersionLookup var52 = this.mVersionLookup;
      var52.put(var48);
      this.mWriter.insert(var47);
      return var48;
   }

   public void notifyAssetAdded(StoredLocalAsset var1) {
      MemoryAssetStoreImpl.6 var2 = new MemoryAssetStoreImpl.6(var1);
      this.notifyAssetListener(var2);
   }

   public void notifyAssetChanged(StoredLocalAsset var1, AssetState var2) {
      MemoryAssetStoreImpl.5 var3 = new MemoryAssetStoreImpl.5(var1, var2);
      this.notifyAssetListener(var3);
   }

   public void notifyAssetDeleted(String var1) {
      MemoryAssetStoreImpl.7 var2 = new MemoryAssetStoreImpl.7(var1);
      this.notifyAssetListener(var2);
   }

   public void notifyAssetVersionChanged(StoredLocalAssetVersion var1, AssetState var2) {
      this.safetyCheck();
      String var3 = var1.getPackageName();
      StoredLocalAsset var4 = this.getAsset(var3);
      if(var4 != null) {
         this.notifyAssetChanged(var4, var2);
      }
   }

   public void removeListener(AssetStore.LocalAssetChangeListener var1) {
      this.safetyCheck();
      this.mListeners.remove(var1);
   }

   public int repairMappings() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.mVersionLookup.getAll().iterator();

      while(var2.hasNext()) {
         StoredLocalAssetVersion var3 = (StoredLocalAssetVersion)var2.next();
         HashMap var4 = this.mAssetLookup;
         String var5 = var3.getPackageName();
         if(!var4.containsKey(var5)) {
            var1.add(var3);
         }
      }

      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         StoredLocalAssetVersion var8 = (StoredLocalAssetVersion)var7.next();
         this.deleteAssetVersion(var8);
      }

      return var1.size();
   }

   interface AssetFilter {

      boolean matches(LocalAsset var1);
   }

   class 7 implements MemoryAssetStoreImpl.ListenerAction {

      // $FF: synthetic field
      final String val$packageName;


      7(String var2) {
         this.val$packageName = var2;
      }

      public void run(AssetStore.LocalAssetChangeListener var1) {
         String var2 = this.val$packageName;
         var1.onAssetDeleted(var2);
      }
   }

   class 5 implements MemoryAssetStoreImpl.ListenerAction {

      // $FF: synthetic field
      final StoredLocalAsset val$asset;
      // $FF: synthetic field
      final AssetState val$previousState;


      5(StoredLocalAsset var2, AssetState var3) {
         this.val$asset = var2;
         this.val$previousState = var3;
      }

      public void run(AssetStore.LocalAssetChangeListener var1) {
         StoredLocalAsset var2 = this.val$asset;
         AssetState var3 = this.val$previousState;
         var1.onAssetChanged(var2, var3);
      }
   }

   class 6 implements MemoryAssetStoreImpl.ListenerAction {

      // $FF: synthetic field
      final StoredLocalAsset val$asset;


      6(StoredLocalAsset var2) {
         this.val$asset = var2;
      }

      public void run(AssetStore.LocalAssetChangeListener var1) {
         StoredLocalAsset var2 = this.val$asset;
         var1.onAssetAdded(var2);
      }
   }

   class 3 implements MemoryAssetStoreImpl.AssetFilter {

      // $FF: synthetic field
      final AssetState val$state;


      3(AssetState var2) {
         this.val$state = var2;
      }

      public boolean matches(LocalAsset var1) {
         AssetState var2 = this.val$state;
         AssetState var3 = var1.getState();
         return var2.equals(var3);
      }
   }

   class 4 implements Comparator<LocalAssetVersion> {

      4() {}

      public int compare(LocalAssetVersion var1, LocalAssetVersion var2) {
         String var3;
         if(var1 == null) {
            var3 = "";
         } else {
            var3 = var1.getAssetId();
         }

         String var4;
         if(var2 == null) {
            var4 = "";
         } else {
            var4 = var2.getAssetId();
         }

         return var3.compareTo(var4);
      }
   }

   class 1 implements MemoryAssetStoreImpl.AssetFilter {

      // $FF: synthetic field
      final String val$account;


      1(String var2) {
         this.val$account = var2;
      }

      public boolean matches(LocalAsset var1) {
         String var2 = this.val$account;
         String var3 = var1.getAccount();
         return var2.equals(var3);
      }
   }

   class 2 implements MemoryAssetStoreImpl.AssetFilter {

      2() {}

      public boolean matches(LocalAsset var1) {
         return true;
      }
   }

   private interface ListenerAction {

      void run(AssetStore.LocalAssetChangeListener var1);
   }
}
