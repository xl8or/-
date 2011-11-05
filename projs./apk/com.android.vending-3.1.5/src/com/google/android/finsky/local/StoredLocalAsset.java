package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.local.LocalAssetRecord;
import com.google.android.finsky.local.MemoryAssetStore;
import com.google.android.finsky.local.StoredLocalAssetVersion;
import com.google.android.finsky.local.Writer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import java.util.Collection;
import java.util.Iterator;

public class StoredLocalAsset implements LocalAsset {

   public static final StoredLocalAsset.VersionFilter INSTALLED_FILTER = new StoredLocalAsset.2();
   public static final StoredLocalAsset.VersionFilter INSTALLED_OR_UNINSTALLED_FILTER = new StoredLocalAsset.4();
   public static final StoredLocalAsset.VersionFilter INSTALLED_OR_UNINSTALLING_FILTER = new StoredLocalAsset.5();
   public static final StoredLocalAsset.VersionFilter NEITHER_TRANSIENT_NOR_INSTALLED_FILTER = new StoredLocalAsset.3();
   public static final StoredLocalAsset.VersionFilter TRANSIENT_FILTER = new StoredLocalAsset.1();
   private String mAccount;
   private AutoUpdateState mAutoUpdateState;
   private boolean mDeleted = 0;
   private final String mPackage;
   private final MemoryAssetStore mStore;
   private final Writer mWriter;


   protected StoredLocalAsset(LocalAssetRecord var1, Writer var2, MemoryAssetStore var3) {
      String var4 = var1.getPackage();
      this.mPackage = var4;
      AutoUpdateState var5 = var1.getAutoUpdateState();
      this.mAutoUpdateState = var5;
      this.mWriter = var2;
      this.mStore = var3;
      String var6 = var1.getAccountString();
      this.mAccount = var6;
   }

   private Collection<StoredLocalAssetVersion> getAssetVersions() {
      MemoryAssetStore var1 = this.mStore;
      String var2 = this.getPackage();
      return var1.getAssetVersions(var2);
   }

   private StoredLocalAssetVersion getByVersionCode(int var1) {
      Collection var2 = this.getAssetVersions();
      StoredLocalAssetVersion var5;
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not getByVersionCode %s", var3);
         var5 = null;
      } else {
         var5 = null;
         Iterator var6 = var2.iterator();

         while(var6.hasNext()) {
            StoredLocalAssetVersion var7 = (StoredLocalAssetVersion)var6.next();
            if(var7.getVersionCode() == var1) {
               var5 = var7;
               break;
            }
         }
      }

      return var5;
   }

   private StoredLocalAssetVersion getHighest(Collection<StoredLocalAssetVersion> var1, StoredLocalAsset.VersionFilter var2) {
      StoredLocalAssetVersion var3 = null;
      StoredLocalAssetVersion var4;
      if(var1 != null && !var1.isEmpty()) {
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            StoredLocalAssetVersion var6 = (StoredLocalAssetVersion)var5.next();
            if(var2.filter(var6)) {
               if(var3 != null) {
                  int var7 = var6.getVersionCode();
                  int var8 = var3.getVersionCode();
                  if(var7 <= var8) {
                     continue;
                  }
               }

               var3 = var6;
            }
         }

         var4 = var3;
      } else {
         var4 = null;
      }

      return var4;
   }

   private void insertNewAssetVersion(long var1, int var3, String var4, String var5, String var6) {
      String var7 = this.getPackage();
      MemoryAssetStore var8 = this.mStore;
      AssetState var9 = AssetState.DOWNLOAD_PENDING;
      Obb var10 = ObbFactory.createEmpty((boolean)0, var7);
      Obb var11 = ObbFactory.createEmpty((boolean)1, var7);
      var8.insertAssetVersion(var7, var3, var4, var9, 0L, var1, 0L, 0L, 0L, (String)null, (Uri)null, (boolean)0, (Long)null, var5, var6, var10, var11);
   }

   private void safetyCheck() {
      Utils.ensureOnMainThread();
      if(this.mDeleted) {
         throw new IllegalStateException("Can\'t write to or read from a deleted asset");
      }
   }

   private void setMatchingVersionState(AssetState var1) {
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not setMatchingVersionState %s", var3);
      } else {
         var2.setState(var1);
         var2.writeThrough();
      }
   }

   public void cleanupInstallFailure() {
      StoredLocalAssetVersion var1 = null;
      boolean var2 = false;
      Iterator var3 = this.getAssetVersions().iterator();

      while(var3.hasNext()) {
         StoredLocalAssetVersion var4 = (StoredLocalAssetVersion)var3.next();
         int[] var5 = StoredLocalAsset.8.$SwitchMap$com$google$android$finsky$local$AssetState;
         int var6 = var4.getState().ordinal();
         switch(var5[var6]) {
         case 1:
            var1 = var4;
            break;
         case 2:
            var2 = true;
         }
      }

      if(var1 != null) {
         if(var2) {
            this.mStore.deleteAssetVersion(var1);
         } else {
            MemoryAssetStore var7 = this.mStore;
            String var8 = this.mPackage;
            var7.deleteAsset(var8);
         }
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               StoredLocalAsset var5 = (StoredLocalAsset)var1;
               boolean var6 = this.mDeleted;
               boolean var7 = var5.mDeleted;
               if(var6 != var7) {
                  var2 = false;
                  return var2;
               } else {
                  label80: {
                     if(this.mAccount != null) {
                        String var8 = this.mAccount;
                        String var9 = var5.mAccount;
                        if(var8.equals(var9)) {
                           break label80;
                        }
                     } else if(var5.mAccount == null) {
                        break label80;
                     }

                     var2 = false;
                     return var2;
                  }

                  AutoUpdateState var10 = this.mAutoUpdateState;
                  AutoUpdateState var11 = var5.mAutoUpdateState;
                  if(var10 != var11) {
                     var2 = false;
                  } else {
                     label81: {
                        if(this.mPackage != null) {
                           String var12 = this.mPackage;
                           String var13 = var5.mPackage;
                           if(!var12.equals(var13)) {
                              break label81;
                           }
                        } else if(var5.mPackage != null) {
                           break label81;
                        }

                        label82: {
                           if(this.mStore != null) {
                              MemoryAssetStore var14 = this.mStore;
                              MemoryAssetStore var15 = var5.mStore;
                              if(var14.equals(var15)) {
                                 break label82;
                              }
                           } else if(var5.mStore == null) {
                              break label82;
                           }

                           var2 = false;
                           return var2;
                        }

                        if(this.mWriter != null) {
                           Writer var16 = this.mWriter;
                           Writer var17 = var5.mWriter;
                           if(var16.equals(var17)) {
                              return var2;
                           }
                        } else if(var5.mWriter == null) {
                           return var2;
                        }

                        var2 = false;
                        return var2;
                     }

                     var2 = false;
                  }

                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   protected StoredLocalAssetVersion findMatchingVersion() {
      Collection var1 = this.getAssetVersions();
      StoredLocalAsset.VersionFilter var2 = TRANSIENT_FILTER;
      StoredLocalAssetVersion var3 = this.getHighest(var1, var2);
      StoredLocalAsset.VersionFilter var4 = INSTALLED_FILTER;
      StoredLocalAssetVersion var5 = this.getHighest(var1, var4);
      StoredLocalAsset.VersionFilter var6 = NEITHER_TRANSIENT_NOR_INSTALLED_FILTER;
      StoredLocalAssetVersion var7 = this.getHighest(var1, var6);
      if(var3 != null) {
         if(var5 != null) {
            int var8 = var3.getVersionCode();
            int var9 = var5.getVersionCode();
            if(var8 <= var9) {
               var3 = var5;
            }
         }
      } else if(var5 != null) {
         var3 = var5;
      } else {
         var3 = var7;
      }

      return var3;
   }

   public String getAccount() {
      return this.mAccount;
   }

   public String getAssetId() {
      return this.findMatchingVersion().getAssetId();
   }

   public AutoUpdateState getAutoUpdateState() {
      this.safetyCheck();
      return this.mAutoUpdateState;
   }

   public Uri getContentUri() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      Uri var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getContentUri %s", var2);
         var4 = null;
      } else {
         var4 = var1.getContentUri();
      }

      return var4;
   }

   public long getDownloadPendingTime() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      long var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getDownloadPendingTime %s", var2);
         var4 = 0L;
      } else {
         var4 = var1.getDownloadPendingTime();
      }

      return var4;
   }

   public long getDownloadTime() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      long var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getDownloadTime %s", var2);
         var4 = 0L;
      } else {
         var4 = var1.getDownloadTime();
      }

      return var4;
   }

   public String getExternalReferrer() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      String var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getReferrer %s", var2);
         var4 = null;
      } else {
         var4 = var1.getReferrer();
      }

      return var4;
   }

   public long getInstallTime() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      long var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getInstallTime %s", var2);
         var4 = 0L;
      } else {
         var4 = var1.getInstallTime();
      }

      return var4;
   }

   public Obb getObb(boolean var1) {
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      Obb var5;
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not getObb %s", var3);
         var5 = null;
      } else {
         var5 = var2.getObb(var1);
      }

      return var5;
   }

   public String getPackage() {
      return this.mPackage;
   }

   public Long getRefundPeriodEndTime() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      Long var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getRefundPeriodEndTime %s", var2);
         var4 = Long.valueOf(0L);
      } else {
         var4 = var1.getRefundPeriodEndTime();
      }

      return var4;
   }

   public String getSignature() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      String var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getSignature %s", var2);
         var4 = null;
      } else {
         var4 = var1.getSignature();
      }

      return var4;
   }

   public long getSize() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      long var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getSize %s", var2);
         var4 = 0L;
      } else {
         var4 = var1.getSize();
      }

      return var4;
   }

   public String getSource() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      String var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getSource %s", var2);
         var4 = null;
      } else {
         var4 = var1.getSource();
      }

      return var4;
   }

   public AssetState getState() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      AssetState var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getState();
      }

      return var2;
   }

   public long getUninstallTime() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      long var4;
      if(var1 == null) {
         Object[] var2 = new Object[1];
         String var3 = this.getPackage();
         var2[0] = var3;
         FinskyLog.wtf("Could not getUninstallTime %s", var2);
         var4 = 0L;
      } else {
         var4 = var1.getUninstallTime();
      }

      return var4;
   }

   public int getVersionCode() {
      int var1 = 0;
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not getVersionCode %s", var3);
      } else {
         var1 = var2.getVersionCode();
      }

      return var1;
   }

   public boolean hasEverBeenInstalled() {
      Collection var1 = this.getAssetVersions();
      StoredLocalAsset.7 var2 = new StoredLocalAsset.7();
      boolean var3;
      if(this.getHighest(var1, var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public int hashCode() {
      byte var1 = 0;
      int var2;
      if(this.mWriter != null) {
         var2 = this.mWriter.hashCode();
      } else {
         var2 = 0;
      }

      int var3 = var2 * 31;
      int var4;
      if(this.mStore != null) {
         var4 = this.mStore.hashCode();
      } else {
         var4 = 0;
      }

      int var5 = (var3 + var4) * 31;
      int var6;
      if(this.mPackage != null) {
         var6 = this.mPackage.hashCode();
      } else {
         var6 = 0;
      }

      int var7 = (var5 + var6) * 31;
      int var8;
      if(this.mAutoUpdateState != null) {
         var8 = this.mAutoUpdateState.hashCode();
      } else {
         var8 = 0;
      }

      int var9 = (var7 + var8) * 31;
      int var10;
      if(this.mAccount != null) {
         var10 = this.mAccount.hashCode();
      } else {
         var10 = 0;
      }

      int var11 = (var9 + var10) * 31;
      if(this.mDeleted) {
         var1 = 1;
      }

      return var11 + var1;
   }

   public boolean isConsistent() {
      boolean var1;
      if(this.getAssetVersions().size() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isDownloadingOrInstalling() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      boolean var2;
      if(var1 != null && var1.getState().isDownloadingOrInstalling()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isForwardLocked() {
      byte var1 = 0;
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not isForwardLocked %s", var3);
      } else {
         var1 = var2.isForwardLocked();
      }

      return (boolean)var1;
   }

   public boolean isInstallable() {
      boolean var1 = true;
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not get isInstallable %s", var3);
      } else if(var2.getState().isNotInstallable()) {
         var1 = false;
      }

      return var1;
   }

   public boolean isInstalled() {
      Collection var1 = this.getAssetVersions();
      StoredLocalAsset.VersionFilter var2 = INSTALLED_FILTER;
      boolean var3;
      if(this.getHighest(var1, var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isTransient() {
      byte var1 = 0;
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not isTransient %s", var3);
      } else {
         var1 = var2.getState().isTransient();
      }

      return (boolean)var1;
   }

   public boolean isUninstallable() {
      byte var1 = 0;
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not get isUninstallable %s", var3);
      } else {
         var1 = var2.getState().isUninstallable();
      }

      return (boolean)var1;
   }

   public boolean isUpdate() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      Collection var2 = this.getAssetVersions();
      StoredLocalAsset.6 var3 = new StoredLocalAsset.6(var1);
      boolean var4;
      if(this.getHighest(var2, var3) != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   protected void markDeleted() {
      this.mDeleted = (boolean)1;
   }

   public void resetDownloadPendingState() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      var1.resetDownloadPendingState();
      var1.writeThrough();
   }

   public void resetInstalledState() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      var1.resetInstalledState();
      var1.writeThrough();
   }

   public void resetInstallingState() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      var1.resetInstallingState();
      var1.writeThrough();
   }

   public void resetUninstalledState() {
      StoredLocalAssetVersion var1 = this.findMatchingVersion();
      var1.resetUninstalledState();
      var1.writeThrough();
   }

   public void setAccount(String var1) {
      this.safetyCheck();
      this.mAccount = var1;
      this.writeThrough();
   }

   public void setAutoUpdateState(AutoUpdateState var1) {
      this.safetyCheck();
      this.mAutoUpdateState = var1;
      this.writeThrough();
   }

   public void setExternalReferrer(String var1) {
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getExternalReferrer();
         var3[0] = var4;
         FinskyLog.wtf("Could not setReferrer %s", var3);
      } else {
         var2.setReferrer(var1);
         var2.writeThrough();
      }
   }

   public void setObb(boolean var1, Obb var2) {
      StoredLocalAssetVersion var3 = this.findMatchingVersion();
      if(var3 == null) {
         Object[] var4 = new Object[1];
         String var5 = this.getPackage();
         var4[0] = var5;
         FinskyLog.wtf("Could not setObb %s", var4);
      } else {
         var3.setObb(var1, var2);
         var3.writeThrough();
      }
   }

   public void setRefundPeriodEndTime(Long var1) {
      StoredLocalAssetVersion var2 = this.findMatchingVersion();
      if(var2 == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getPackage();
         var3[0] = var4;
         FinskyLog.wtf("Could not setRefundPeriodEndTime %s", var3);
      } else {
         var2.setRefundPeriodEndTime(var1);
         var2.writeThrough();
      }
   }

   public void setStateCancelPending() {
      AssetState var1 = AssetState.DOWNLOAD_CANCEL_PENDING;
      this.setMatchingVersionState(var1);
   }

   public void setStateDownloadCancelled() {
      AssetState var1 = AssetState.DOWNLOAD_CANCELLED;
      this.setMatchingVersionState(var1);
   }

   public void setStateDownloadDeclined() {
      AssetState var1 = AssetState.DOWNLOAD_DECLINED;
      this.setMatchingVersionState(var1);
   }

   public void setStateDownloadFailed() {
      AssetState var1 = AssetState.DOWNLOAD_FAILED;
      this.setMatchingVersionState(var1);
   }

   public void setStateDownloadPending(long var1, int var3, String var4, String var5, String var6) {
      StoredLocalAssetVersion var7 = this.getByVersionCode(var3);
      if(var7 != null) {
         MemoryAssetStore var8 = this.mStore;
         String var9 = var7.getAssetId();
         var8.deleteAssetVersion(var9);
      }

      this.insertNewAssetVersion(var1, var3, var4, var5, var6);
   }

   public void setStateDownloading(long param1, Uri param3, long param4, String param6, boolean param7, Long param8, Obb param9, Obb param10) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public void setStateInstallFailed() {
      AssetState var1 = AssetState.INSTALL_FAILED;
      this.setMatchingVersionState(var1);
   }

   public void setStateInstalled(long var1) {
      StoredLocalAssetVersion var3 = this.findMatchingVersion();
      if(var3 == null) {
         Object[] var4 = new Object[1];
         String var5 = this.getPackage();
         var4[0] = var5;
         FinskyLog.wtf("Could not setStateInstalled %s", var4);
      } else {
         AssetState var6 = AssetState.INSTALLED;
         var3.setState(var6);
         var3.setInstallTime(var1);
         var3.writeThrough();
      }
   }

   public void setStateInstalling() {
      try {
         AssetState var1 = AssetState.INSTALLING;
         this.setMatchingVersionState(var1);
      } catch (IllegalArgumentException var14) {
         StringBuilder var3 = (new StringBuilder()).append("Caught IAE in setStateInstalling for asset: ");
         String var4 = this.toString();
         String var5 = var3.append(var4).toString();
         Object[] var6 = new Object[0];
         FinskyLog.e(var5, var6);
         Collection var7 = this.getAssetVersions();
         StoredLocalAssetVersion var8 = this.findMatchingVersion();
         String var9;
         if("Matching version: [" + var8 != null) {
            var9 = var8.toString();
         } else {
            var9 = "]";
         }

         Object[] var10 = new Object[0];
         FinskyLog.e(var9, var10);
         Iterator var11 = var7.iterator();

         while(var11.hasNext()) {
            StoredLocalAssetVersion var12 = (StoredLocalAssetVersion)var11.next();
            if(!var12.equals(var8)) {
               if("Other version: [" + var12 != null) {
                  var9 = var12.toString();
               } else {
                  var9 = "]";
               }

               Object[] var13 = new Object[0];
               FinskyLog.e(var9, var13);
            }
         }

         throw var14;
      }
   }

   public void setStateUninstallFailed() {
      AssetState var1 = AssetState.UNINSTALL_FAILED;
      this.setMatchingVersionState(var1);
   }

   public void setStateUninstalled(long var1) {
      Collection var3 = this.getAssetVersions();
      StoredLocalAsset.VersionFilter var4 = INSTALLED_OR_UNINSTALLING_FILTER;
      StoredLocalAssetVersion var5 = this.getHighest(var3, var4);
      if(var5 == null) {
         Object[] var6 = new Object[1];
         String var7 = this.getPackage();
         var6[0] = var7;
         FinskyLog.wtf("Could not setStateUninstalled %s", var6);
      } else {
         AssetState var8 = var5.getState();
         AssetState var9 = AssetState.INSTALLED;
         if(var8.equals(var9)) {
            AssetState var10 = AssetState.UNINSTALLING;
            var5.setState(var10);
         }

         AssetState var11 = AssetState.UNINSTALLED;
         var5.setState(var11);
         var5.setUninstallTime(var1);
         var5.writeThrough();
      }
   }

   public void setStateUninstalling() {
      Collection var1 = this.getAssetVersions();
      StoredLocalAsset.VersionFilter var2 = INSTALLED_FILTER;
      StoredLocalAssetVersion var3 = this.getHighest(var1, var2);
      if(var3 == null) {
         Object[] var4 = new Object[1];
         String var5 = this.getPackage();
         var4[0] = var5;
         FinskyLog.wtf("Could not setStateUninstalling %s", var4);
      } else {
         AssetState var6 = AssetState.UNINSTALLING;
         var3.setState(var6);
         var3.writeThrough();
      }
   }

   public LocalAssetRecord toRecord() {
      String var1 = this.mPackage;
      String var2 = this.mAccount;
      AutoUpdateState var3 = this.mAutoUpdateState;
      return new LocalAssetRecord(var1, var2, var3);
   }

   public void writeThrough() {
      this.safetyCheck();
      Writer var1 = this.mWriter;
      LocalAssetRecord var2 = this.toRecord();
      var1.insert(var2);
      MemoryAssetStore var3 = this.mStore;
      AssetState var4 = this.getState();
      var3.notifyAssetChanged(this, var4);
   }

   class 6 implements StoredLocalAsset.VersionFilter {

      // $FF: synthetic field
      final StoredLocalAssetVersion val$currentVersion;


      6(StoredLocalAssetVersion var2) {
         this.val$currentVersion = var2;
      }

      public boolean filter(StoredLocalAssetVersion var1) {
         AssetState var2 = var1.getState();
         int var3 = var1.getVersionCode();
         int var4 = this.val$currentVersion.getVersionCode();
         boolean var7;
         if(var3 < var4) {
            label22: {
               AssetState var5 = AssetState.INSTALLED;
               if(var2 != var5) {
                  AssetState var6 = AssetState.UNINSTALLED;
                  if(var2 != var6) {
                     break label22;
                  }
               }

               var7 = true;
               return var7;
            }
         }

         var7 = false;
         return var7;
      }
   }

   static class 5 implements StoredLocalAsset.VersionFilter {

      5() {}

      public boolean filter(StoredLocalAssetVersion var1) {
         AssetState var2 = var1.getState();
         AssetState var3 = AssetState.UNINSTALLING;
         boolean var6;
         if(var2 != var3) {
            AssetState var4 = var1.getState();
            AssetState var5 = AssetState.INSTALLED;
            if(var4 != var5) {
               var6 = false;
               return var6;
            }
         }

         var6 = true;
         return var6;
      }
   }

   static class 4 implements StoredLocalAsset.VersionFilter {

      4() {}

      public boolean filter(StoredLocalAssetVersion var1) {
         AssetState var2 = var1.getState();
         AssetState var3 = AssetState.INSTALLED;
         boolean var5;
         if(var2 != var3) {
            AssetState var4 = AssetState.UNINSTALLED;
            if(var2 != var4) {
               var5 = false;
               return var5;
            }
         }

         var5 = true;
         return var5;
      }
   }

   static class 3 implements StoredLocalAsset.VersionFilter {

      3() {}

      public boolean filter(StoredLocalAssetVersion var1) {
         AssetState var2 = var1.getState();
         boolean var4;
         if(!var2.isTransient()) {
            AssetState var3 = AssetState.INSTALLED;
            if(var2 != var3) {
               var4 = true;
               return var4;
            }
         }

         var4 = false;
         return var4;
      }
   }

   static class 2 implements StoredLocalAsset.VersionFilter {

      2() {}

      public boolean filter(StoredLocalAssetVersion var1) {
         AssetState var2 = var1.getState();
         AssetState var3 = AssetState.INSTALLED;
         boolean var4;
         if(var2 == var3) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   static class 1 implements StoredLocalAsset.VersionFilter {

      1() {}

      public boolean filter(StoredLocalAssetVersion var1) {
         return var1.getState().isTransient();
      }
   }

   private interface VersionFilter {

      boolean filter(StoredLocalAssetVersion var1);
   }

   class 7 implements StoredLocalAsset.VersionFilter {

      7() {}

      public boolean filter(StoredLocalAssetVersion var1) {
         AssetState var2 = var1.getState();
         AssetState var3 = AssetState.INSTALLED;
         boolean var5;
         if(var2 != var3) {
            AssetState var4 = AssetState.UNINSTALLED;
            if(var2 != var4) {
               var5 = false;
               return var5;
            }
         }

         var5 = true;
         return var5;
      }
   }

   // $FF: synthetic class
   static class 8 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$AssetState = new int[AssetState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var1 = AssetState.INSTALLING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var3 = AssetState.INSTALLED.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
