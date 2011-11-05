package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.LocalAssetVersionRecord;
import com.google.android.finsky.local.MemoryAssetStore;
import com.google.android.finsky.local.Writer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;

class StoredLocalAssetVersion {

   private final String mAssetId;
   private Uri mContentUri;
   private volatile boolean mDeleted = 0;
   private long mDownloadPendingTime;
   private long mDownloadTime;
   private boolean mForwardLocked;
   private long mInstallTime;
   private Obb mMainObb;
   private final String mPackageName;
   private Obb mPatchObb;
   private AssetState mPreviousState = null;
   private String mReferrer;
   private Long mRefundPeriodEndTime;
   private String mSignature;
   private long mSize;
   private String mSource;
   private AssetState mState;
   private final MemoryAssetStore mStore;
   private long mUninstallTime;
   private final int mVersionCode;
   private final Writer mWriter;


   protected StoredLocalAssetVersion(LocalAssetVersionRecord var1, Writer var2, MemoryAssetStore var3) {
      this.mStore = var3;
      this.mWriter = var2;
      String var4 = var1.getPackageName();
      this.mPackageName = var4;
      int var5 = var1.getVersionCode();
      this.mVersionCode = var5;
      String var6 = var1.getAssetId();
      this.mAssetId = var6;
      AssetState var7 = var1.getState();
      this.mState = var7;
      long var8 = var1.getSize();
      this.mSize = var8;
      long var10 = var1.getDownloadPendingTime();
      this.mDownloadPendingTime = var10;
      long var12 = var1.getDownloadTime();
      this.mDownloadTime = var12;
      long var14 = var1.getInstallTime();
      this.mInstallTime = var14;
      long var16 = var1.getUninstallTime();
      this.mUninstallTime = var16;
      String var18 = var1.getSignature();
      this.mSignature = var18;
      Uri var19 = var1.getContentUri();
      this.mContentUri = var19;
      boolean var20 = var1.isForwardLocked();
      this.mForwardLocked = var20;
      Long var21 = var1.getRefundPeriodEndTime();
      this.mRefundPeriodEndTime = var21;
      String var22 = var1.getReferrer();
      this.mReferrer = var22;
      String var23 = var1.getSource();
      this.mSource = var23;
      Obb var24 = var1.getMainObb();
      this.mMainObb = var24;
      Obb var25 = var1.getPatchObb();
      this.mPatchObb = var25;
   }

   private void safetyCheck() {
      Utils.ensureOnMainThread();
      if(this.mDeleted) {
         throw new IllegalStateException("Can\'t write to or read from a deleted asset");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               StoredLocalAssetVersion var5 = (StoredLocalAssetVersion)var1;
               boolean var6 = this.mDeleted;
               boolean var7 = var5.mDeleted;
               if(var6 != var7) {
                  var2 = false;
                  return var2;
               } else {
                  long var8 = this.mDownloadPendingTime;
                  long var10 = var5.mDownloadPendingTime;
                  if(var8 != var10) {
                     var2 = false;
                     return var2;
                  } else {
                     long var12 = this.mDownloadTime;
                     long var14 = var5.mDownloadTime;
                     if(var12 != var14) {
                        var2 = false;
                        return var2;
                     } else {
                        boolean var16 = this.mForwardLocked;
                        boolean var17 = var5.mForwardLocked;
                        if(var16 != var17) {
                           var2 = false;
                           return var2;
                        } else {
                           long var18 = this.mInstallTime;
                           long var20 = var5.mInstallTime;
                           if(var18 != var20) {
                              var2 = false;
                              return var2;
                           } else {
                              long var22 = this.mSize;
                              long var24 = var5.mSize;
                              if(var22 != var24) {
                                 var2 = false;
                                 return var2;
                              } else {
                                 long var26 = this.mUninstallTime;
                                 long var28 = var5.mUninstallTime;
                                 if(var26 != var28) {
                                    var2 = false;
                                    return var2;
                                 } else {
                                    int var30 = this.mVersionCode;
                                    int var31 = var5.mVersionCode;
                                    if(var30 != var31) {
                                       var2 = false;
                                    } else {
                                       label231: {
                                          if(this.mAssetId != null) {
                                             String var32 = this.mAssetId;
                                             String var33 = var5.mAssetId;
                                             if(var32.equals(var33)) {
                                                break label231;
                                             }
                                          } else if(var5.mAssetId == null) {
                                             break label231;
                                          }

                                          var2 = false;
                                          return var2;
                                       }

                                       label232: {
                                          if(this.mContentUri != null) {
                                             Uri var34 = this.mContentUri;
                                             Uri var35 = var5.mContentUri;
                                             if(!var34.equals(var35)) {
                                                break label232;
                                             }
                                          } else if(var5.mContentUri != null) {
                                             break label232;
                                          }

                                          label233: {
                                             if(this.mMainObb != null) {
                                                Obb var36 = this.mMainObb;
                                                Obb var37 = var5.mMainObb;
                                                if(!var36.equals(var37)) {
                                                   break label233;
                                                }
                                             } else if(var5.mMainObb != null) {
                                                break label233;
                                             }

                                             label234: {
                                                if(this.mPackageName != null) {
                                                   String var38 = this.mPackageName;
                                                   String var39 = var5.mPackageName;
                                                   if(var38.equals(var39)) {
                                                      break label234;
                                                   }
                                                } else if(var5.mPackageName == null) {
                                                   break label234;
                                                }

                                                var2 = false;
                                                return var2;
                                             }

                                             label235: {
                                                if(this.mPatchObb != null) {
                                                   Obb var40 = this.mPatchObb;
                                                   Obb var41 = var5.mPatchObb;
                                                   if(!var40.equals(var41)) {
                                                      break label235;
                                                   }
                                                } else if(var5.mPatchObb != null) {
                                                   break label235;
                                                }

                                                label236: {
                                                   if(this.mPreviousState != null) {
                                                      AssetState var42 = this.mPreviousState;
                                                      AssetState var43 = var5.mPreviousState;
                                                      if(var42.equals(var43)) {
                                                         break label236;
                                                      }
                                                   } else if(var5.mPreviousState == null) {
                                                      break label236;
                                                   }

                                                   var2 = false;
                                                   return var2;
                                                }

                                                label237: {
                                                   if(this.mReferrer != null) {
                                                      String var44 = this.mReferrer;
                                                      String var45 = var5.mReferrer;
                                                      if(!var44.equals(var45)) {
                                                         break label237;
                                                      }
                                                   } else if(var5.mReferrer != null) {
                                                      break label237;
                                                   }

                                                   label238: {
                                                      if(this.mRefundPeriodEndTime != null) {
                                                         Long var46 = this.mRefundPeriodEndTime;
                                                         Long var47 = var5.mRefundPeriodEndTime;
                                                         if(!var46.equals(var47)) {
                                                            break label238;
                                                         }
                                                      } else if(var5.mRefundPeriodEndTime != null) {
                                                         break label238;
                                                      }

                                                      label239: {
                                                         if(this.mSignature != null) {
                                                            String var48 = this.mSignature;
                                                            String var49 = var5.mSignature;
                                                            if(var48.equals(var49)) {
                                                               break label239;
                                                            }
                                                         } else if(var5.mSignature == null) {
                                                            break label239;
                                                         }

                                                         var2 = false;
                                                         return var2;
                                                      }

                                                      label240: {
                                                         if(this.mSource != null) {
                                                            String var50 = this.mSource;
                                                            String var51 = var5.mSource;
                                                            if(var50.equals(var51)) {
                                                               break label240;
                                                            }
                                                         } else if(var5.mSource == null) {
                                                            break label240;
                                                         }

                                                         var2 = false;
                                                         return var2;
                                                      }

                                                      label241: {
                                                         if(this.mState != null) {
                                                            AssetState var52 = this.mState;
                                                            AssetState var53 = var5.mState;
                                                            if(!var52.equals(var53)) {
                                                               break label241;
                                                            }
                                                         } else if(var5.mState != null) {
                                                            break label241;
                                                         }

                                                         label242: {
                                                            if(this.mStore != null) {
                                                               MemoryAssetStore var54 = this.mStore;
                                                               MemoryAssetStore var55 = var5.mStore;
                                                               if(!var54.equals(var55)) {
                                                                  break label242;
                                                               }
                                                            } else if(var5.mStore != null) {
                                                               break label242;
                                                            }

                                                            if(this.mWriter != null) {
                                                               Writer var56 = this.mWriter;
                                                               Writer var57 = var5.mWriter;
                                                               if(var56.equals(var57)) {
                                                                  return var2;
                                                               }
                                                            } else if(var5.mWriter == null) {
                                                               return var2;
                                                            }

                                                            var2 = false;
                                                            return var2;
                                                         }

                                                         var2 = false;
                                                         return var2;
                                                      }

                                                      var2 = false;
                                                      return var2;
                                                   }

                                                   var2 = false;
                                                   return var2;
                                                }

                                                var2 = false;
                                                return var2;
                                             }

                                             var2 = false;
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
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getAssetId() {
      this.safetyCheck();
      return this.mAssetId;
   }

   public Writer getAsyncWriter() {
      this.safetyCheck();
      return this.mWriter;
   }

   public Uri getContentUri() {
      this.safetyCheck();
      return this.mContentUri;
   }

   public long getDownloadPendingTime() {
      this.safetyCheck();
      return this.mDownloadPendingTime;
   }

   public long getDownloadTime() {
      this.safetyCheck();
      return this.mDownloadTime;
   }

   public long getInstallTime() {
      this.safetyCheck();
      return this.mInstallTime;
   }

   public Obb getObb(boolean var1) {
      Obb var2;
      if(var1) {
         var2 = this.mPatchObb;
      } else {
         var2 = this.mMainObb;
      }

      return var2;
   }

   public String getPackageName() {
      this.safetyCheck();
      return this.mPackageName;
   }

   public String getReferrer() {
      this.safetyCheck();
      return this.mReferrer;
   }

   public Long getRefundPeriodEndTime() {
      this.safetyCheck();
      return this.mRefundPeriodEndTime;
   }

   public String getSignature() {
      this.safetyCheck();
      return this.mSignature;
   }

   public long getSize() {
      this.safetyCheck();
      return this.mSize;
   }

   public String getSource() {
      this.safetyCheck();
      return this.mSource;
   }

   public AssetState getState() {
      this.safetyCheck();
      return this.mState;
   }

   public long getUninstallTime() {
      this.safetyCheck();
      return this.mUninstallTime;
   }

   public int getVersionCode() {
      this.safetyCheck();
      return this.mVersionCode;
   }

   public int hashCode() {
      byte var1 = 1;
      int var2 = 0;
      byte var3;
      if(this.mDeleted) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var3 * 31;
      int var5;
      if(this.mPackageName != null) {
         var5 = this.mPackageName.hashCode();
      } else {
         var5 = 0;
      }

      int var6 = (var4 + var5) * 31;
      int var7 = this.mVersionCode;
      int var8 = (var6 + var7) * 31;
      int var9;
      if(this.mAssetId != null) {
         var9 = this.mAssetId.hashCode();
      } else {
         var9 = 0;
      }

      int var10 = (var8 + var9) * 31;
      int var11;
      if(this.mState != null) {
         var11 = this.mState.hashCode();
      } else {
         var11 = 0;
      }

      int var12 = (var10 + var11) * 31;
      long var13 = this.mSize;
      long var15 = this.mSize >>> 32;
      int var17 = (int)(var13 ^ var15);
      int var18 = (var12 + var17) * 31;
      long var19 = this.mDownloadPendingTime;
      long var21 = this.mDownloadPendingTime >>> 32;
      int var23 = (int)(var19 ^ var21);
      int var24 = (var18 + var23) * 31;
      long var25 = this.mDownloadTime;
      long var27 = this.mDownloadTime >>> 32;
      int var29 = (int)(var25 ^ var27);
      int var30 = (var24 + var29) * 31;
      long var31 = this.mInstallTime;
      long var33 = this.mInstallTime >>> 32;
      int var35 = (int)(var31 ^ var33);
      int var36 = (var30 + var35) * 31;
      long var37 = this.mUninstallTime;
      long var39 = this.mUninstallTime >>> 32;
      int var41 = (int)(var37 ^ var39);
      int var42 = (var36 + var41) * 31;
      int var43;
      if(this.mSignature != null) {
         var43 = this.mSignature.hashCode();
      } else {
         var43 = 0;
      }

      int var44 = (var42 + var43) * 31;
      int var45;
      if(this.mContentUri != null) {
         var45 = this.mContentUri.hashCode();
      } else {
         var45 = 0;
      }

      int var46 = (var44 + var45) * 31;
      if(!this.mForwardLocked) {
         var1 = 0;
      }

      int var47 = (var46 + var1) * 31;
      int var48;
      if(this.mRefundPeriodEndTime != null) {
         var48 = this.mRefundPeriodEndTime.hashCode();
      } else {
         var48 = 0;
      }

      int var49 = (var47 + var48) * 31;
      int var50;
      if(this.mReferrer != null) {
         var50 = this.mReferrer.hashCode();
      } else {
         var50 = 0;
      }

      int var51 = (var49 + var50) * 31;
      if(this.mSource != null) {
         var2 = this.mSource.hashCode();
      }

      return var51 + var2;
   }

   public boolean isForwardLocked() {
      this.safetyCheck();
      return this.mForwardLocked;
   }

   protected void markDeleted() {
      this.mDeleted = (boolean)1;
   }

   public void resetDownloadPendingState() {
      int[] var1 = StoredLocalAssetVersion.1.$SwitchMap$com$google$android$finsky$local$AssetState;
      int var2 = this.getState().ordinal();
      switch(var1[var2]) {
      case 1:
         AssetState var8 = AssetState.DOWNLOAD_CANCELLED;
         this.setState(var8);
         AssetState var9 = AssetState.DOWNLOAD_PENDING;
         this.setState(var9);
      case 2:
         long var10 = System.currentTimeMillis();
         this.setDownloadPendingTime(var10);
         return;
      default:
         StringBuilder var3 = (new StringBuilder()).append("Invalid time to reset ");
         String var4 = this.getPackageName();
         StringBuilder var5 = var3.append(var4).append(" download state. ").append("Current state is ");
         String var6 = this.getState().toString();
         String var7 = var5.append(var6).toString();
         throw new IllegalStateException(var7);
      }
   }

   public void resetInstalledState() {
      AssetState var1 = AssetState.INSTALLED;
      this.mState = var1;
      if(this.mInstallTime == 0L) {
         long var2 = System.currentTimeMillis();
         this.mInstallTime = var2;
      }
   }

   public void resetInstallingState() {
      AssetState var1 = AssetState.INSTALLING;
      this.mState = var1;
   }

   public void resetUninstalledState() {
      AssetState var1 = AssetState.UNINSTALLED;
      this.mState = var1;
      if(this.mUninstallTime == 0L) {
         long var2 = System.currentTimeMillis();
         this.mUninstallTime = var2;
      }
   }

   public void setContentUri(Uri var1) {
      this.safetyCheck();
      this.mContentUri = var1;
   }

   public void setDownloadPendingTime(long var1) {
      this.safetyCheck();
      this.mDownloadPendingTime = var1;
   }

   public void setDownloadTime(long var1) {
      this.safetyCheck();
      this.mDownloadTime = var1;
   }

   public void setForwardLocked(boolean var1) {
      this.safetyCheck();
      this.mForwardLocked = var1;
   }

   public void setInstallTime(long var1) {
      this.safetyCheck();
      this.mInstallTime = var1;
   }

   public void setObb(boolean var1, Obb var2) {
      this.safetyCheck();
      if(var2 == null) {
         String var3 = this.mPackageName;
         var2 = ObbFactory.createEmpty(var1, var3);
         Object[] var4 = new Object[0];
         FinskyLog.wtf("Attempting to set null obb", var4);
      }

      if(var1) {
         this.mPatchObb = var2;
      } else {
         this.mMainObb = var2;
      }
   }

   public void setReferrer(String var1) {
      this.safetyCheck();
      this.mReferrer = var1;
   }

   public void setRefundPeriodEndTime(Long var1) {
      this.safetyCheck();
      this.mRefundPeriodEndTime = var1;
   }

   public void setSignature(String var1) {
      this.safetyCheck();
      this.mSignature = var1;
   }

   public void setSize(long var1) {
      this.safetyCheck();
      this.mSize = var1;
   }

   public void setSource(String var1) {
      this.safetyCheck();
      this.mSource = var1;
   }

   public void setState(AssetState var1) {
      this.safetyCheck();
      if(!this.mState.isValidTransition(var1)) {
         Object[] var2 = new Object[2];
         String var3 = this.mState.name();
         var2[0] = var3;
         String var4 = var1.name();
         var2[1] = var4;
         FinskyLog.wtf("Invalid transition: from %s to %s.", var2);
      }

      AssetState var5 = this.mState;
      this.mPreviousState = var5;
      this.mState = var1;
   }

   public void setUninstallTime(long var1) {
      this.safetyCheck();
      this.mUninstallTime = var1;
   }

   public LocalAssetVersionRecord toRecord() {
      this.safetyCheck();
      String var1 = this.mPackageName;
      int var2 = this.mVersionCode;
      String var3 = this.mAssetId;
      AssetState var4 = this.mState;
      long var5 = this.mSize;
      long var7 = this.mDownloadPendingTime;
      long var9 = this.mDownloadTime;
      long var11 = this.mInstallTime;
      long var13 = this.mUninstallTime;
      String var15 = this.mSignature;
      Uri var16 = this.mContentUri;
      boolean var17 = this.mForwardLocked;
      Long var18 = this.mRefundPeriodEndTime;
      String var19 = this.mReferrer;
      String var20 = this.mSource;
      Obb var21 = this.mMainObb;
      Obb var22 = this.mPatchObb;
      return new LocalAssetVersionRecord(var1, var2, var3, var4, var5, var7, var9, var11, var13, var15, var16, var17, var18, var19, var20, var21, var22);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("StoredLocalAssetVersion{mWriter=");
      Writer var2 = this.mWriter;
      StringBuilder var3 = var1.append(var2).append(", mStore=");
      MemoryAssetStore var4 = this.mStore;
      StringBuilder var5 = var3.append(var4).append(", mDeleted=");
      boolean var6 = this.mDeleted;
      StringBuilder var7 = var5.append(var6).append(", mPackageName=");
      String var8 = this.mPackageName;
      StringBuilder var9 = var7.append(var8).append(", mVersionCode=");
      int var10 = this.mVersionCode;
      StringBuilder var11 = var9.append(var10).append(", mAssetId=");
      String var12 = this.mAssetId;
      StringBuilder var13 = var11.append(var12).append(", mState=");
      AssetState var14 = this.mState;
      StringBuilder var15 = var13.append(var14).append(", mPreviousState=");
      AssetState var16 = this.mPreviousState;
      StringBuilder var17 = var15.append(var16).append(", mSize=");
      long var18 = this.mSize;
      StringBuilder var20 = var17.append(var18).append(", mDownloadPendingTime=");
      long var21 = this.mDownloadPendingTime;
      StringBuilder var23 = var20.append(var21).append(", mDownloadTime=");
      long var24 = this.mDownloadTime;
      StringBuilder var26 = var23.append(var24).append(", mInstallTime=");
      long var27 = this.mInstallTime;
      StringBuilder var29 = var26.append(var27).append(", mUninstallTime=");
      long var30 = this.mUninstallTime;
      StringBuilder var32 = var29.append(var30).append(", mSignature=\'");
      String var33 = this.mSignature;
      StringBuilder var34 = var32.append(var33).append('\'').append(", mContentUri=");
      Uri var35 = this.mContentUri;
      StringBuilder var36 = var34.append(var35).append(", mForwardLocked=");
      boolean var37 = this.mForwardLocked;
      StringBuilder var38 = var36.append(var37).append(", mRefundPeriodEndTime=");
      Long var39 = this.mRefundPeriodEndTime;
      StringBuilder var40 = var38.append(var39).append(", mReferrer=");
      String var41 = this.mReferrer;
      StringBuilder var42 = var40.append(var41).append(", mSource=");
      String var43 = this.mSource;
      StringBuilder var44 = var42.append(var43).append(", mMainObb=");
      Obb var45 = this.mMainObb;
      StringBuilder var46 = var44.append(var45).append(", mPatchObb=");
      Obb var47 = this.mPatchObb;
      return var46.append(var47).append('}').toString();
   }

   public void writeThrough() {
      this.safetyCheck();
      Writer var1 = this.mWriter;
      LocalAssetVersionRecord var2 = this.toRecord();
      var1.insert(var2);
      MemoryAssetStore var3 = this.mStore;
      AssetState var4 = this.mPreviousState;
      var3.notifyAssetVersionChanged(this, var4);
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$AssetState = new int[AssetState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var1 = AssetState.DOWNLOADING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var3 = AssetState.DOWNLOAD_PENDING.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
