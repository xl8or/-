package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.LocalAssetVersion;
import com.google.android.finsky.local.PersistentAssetStore;
import com.google.android.finsky.local.Writable;
import com.google.android.finsky.local.Writer;
import com.google.android.finsky.utils.FinskyLog;

class LocalAssetVersionRecord implements Writable, LocalAssetVersion {

   private final String mAssetId;
   private final Uri mContentUri;
   private final long mDownloadPendingTime;
   private final long mDownloadTime;
   private final boolean mForwardLocked;
   private final long mInstallTime;
   private final Obb mMainObb;
   private final String mPackageName;
   private final Obb mPatchObb;
   private final String mReferrer;
   private final Long mRefundPeriodEndTime;
   private final String mSignature;
   private final long mSize;
   private final String mSource;
   private final AssetState mState;
   private final long mUninstallTime;
   private final int mVersionCode;


   public LocalAssetVersionRecord(LocalAssetVersionRecord var1) {
      String var2 = var1.mPackageName;
      int var3 = var1.mVersionCode;
      String var4 = var1.mAssetId;
      AssetState var5 = var1.mState;
      long var6 = var1.mSize;
      long var8 = var1.mDownloadPendingTime;
      long var10 = var1.mDownloadTime;
      long var12 = var1.mInstallTime;
      long var14 = var1.mUninstallTime;
      String var16 = var1.mSignature;
      Uri var17 = var1.mContentUri;
      boolean var18 = var1.mForwardLocked;
      Long var19 = var1.mRefundPeriodEndTime;
      String var20 = var1.mReferrer;
      String var21 = var1.mSource;
      Obb var22 = var1.mMainObb;
      Obb var23 = var1.mPatchObb;
      this(var2, var3, var4, var5, var6, var8, var10, var12, var14, var16, var17, var18, var19, var20, var21, var22, var23);
   }

   LocalAssetVersionRecord(String var1, int var2, String var3, AssetState var4, long var5, long var7, long var9, long var11, long var13, String var15, Uri var16, boolean var17, Long var18, String var19, String var20, Obb var21, Obb var22) {
      this.mPackageName = var1;
      this.mVersionCode = var2;
      this.mAssetId = var3;
      this.mState = var4;
      this.mSize = var5;
      this.mDownloadPendingTime = var7;
      this.mDownloadTime = var9;
      this.mInstallTime = var11;
      this.mUninstallTime = var13;
      this.mSignature = var15;
      this.mContentUri = var16;
      this.mForwardLocked = var17;
      this.mRefundPeriodEndTime = var18;
      this.mReferrer = var19;
      this.mSource = var20;
      if(var21 == null) {
         var21 = ObbFactory.createEmpty((boolean)0, var1);
         Object[] var33 = new Object[0];
         FinskyLog.wtf("Null main OBB in constructor", var33);
      }

      if(var22 == false) {
         var22 = ObbFactory.createEmpty((boolean)1, var1);
         Object[] var34 = new Object[0];
         FinskyLog.wtf("Null patch OBB in constructor", var34);
      }

      this.mMainObb = var21;
      this.mPatchObb = var22;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               LocalAssetVersionRecord var5 = (LocalAssetVersionRecord)var1;
               long var6 = this.mDownloadPendingTime;
               long var8 = var5.mDownloadPendingTime;
               if(var6 != var8) {
                  var2 = false;
                  return var2;
               } else {
                  long var10 = this.mDownloadTime;
                  long var12 = var5.mDownloadTime;
                  if(var10 != var12) {
                     var2 = false;
                     return var2;
                  } else {
                     boolean var14 = this.mForwardLocked;
                     boolean var15 = var5.mForwardLocked;
                     if(var14 != var15) {
                        var2 = false;
                        return var2;
                     } else {
                        long var16 = this.mInstallTime;
                        long var18 = var5.mInstallTime;
                        if(var16 != var18) {
                           var2 = false;
                           return var2;
                        } else {
                           long var20 = this.mSize;
                           long var22 = var5.mSize;
                           if(var20 != var22) {
                              var2 = false;
                              return var2;
                           } else {
                              long var24 = this.mUninstallTime;
                              long var26 = var5.mUninstallTime;
                              if(var24 != var26) {
                                 var2 = false;
                                 return var2;
                              } else {
                                 int var28 = this.mVersionCode;
                                 int var29 = var5.mVersionCode;
                                 if(var28 != var29) {
                                    var2 = false;
                                 } else {
                                    label183: {
                                       if(this.mAssetId != null) {
                                          String var30 = this.mAssetId;
                                          String var31 = var5.mAssetId;
                                          if(!var30.equals(var31)) {
                                             break label183;
                                          }
                                       } else if(var5.mAssetId != null) {
                                          break label183;
                                       }

                                       label184: {
                                          if(this.mContentUri != null) {
                                             Uri var32 = this.mContentUri;
                                             Uri var33 = var5.mContentUri;
                                             if(var32.equals(var33)) {
                                                break label184;
                                             }
                                          } else if(var5.mContentUri == null) {
                                             break label184;
                                          }

                                          var2 = false;
                                          return var2;
                                       }

                                       label185: {
                                          if(this.mMainObb != null) {
                                             Obb var34 = this.mMainObb;
                                             Obb var35 = var5.mMainObb;
                                             if(var34.equals(var35)) {
                                                break label185;
                                             }
                                          } else if(var5.mMainObb == null) {
                                             break label185;
                                          }

                                          var2 = false;
                                          return var2;
                                       }

                                       label186: {
                                          if(this.mPackageName != null) {
                                             String var36 = this.mPackageName;
                                             String var37 = var5.mPackageName;
                                             if(var36.equals(var37)) {
                                                break label186;
                                             }
                                          } else if(var5.mPackageName == null) {
                                             break label186;
                                          }

                                          var2 = false;
                                          return var2;
                                       }

                                       label187: {
                                          if(this.mPatchObb != null) {
                                             Obb var38 = this.mPatchObb;
                                             Obb var39 = var5.mPatchObb;
                                             if(!var38.equals(var39)) {
                                                break label187;
                                             }
                                          } else if(var5.mPatchObb != null) {
                                             break label187;
                                          }

                                          label188: {
                                             if(this.mReferrer != null) {
                                                String var40 = this.mReferrer;
                                                String var41 = var5.mReferrer;
                                                if(!var40.equals(var41)) {
                                                   break label188;
                                                }
                                             } else if(var5.mReferrer != null) {
                                                break label188;
                                             }

                                             label189: {
                                                if(this.mRefundPeriodEndTime != null) {
                                                   Long var42 = this.mRefundPeriodEndTime;
                                                   Long var43 = var5.mRefundPeriodEndTime;
                                                   if(var42.equals(var43)) {
                                                      break label189;
                                                   }
                                                } else if(var5.mRefundPeriodEndTime == null) {
                                                   break label189;
                                                }

                                                var2 = false;
                                                return var2;
                                             }

                                             label190: {
                                                if(this.mSignature != null) {
                                                   String var44 = this.mSignature;
                                                   String var45 = var5.mSignature;
                                                   if(!var44.equals(var45)) {
                                                      break label190;
                                                   }
                                                } else if(var5.mSignature != null) {
                                                   break label190;
                                                }

                                                label191: {
                                                   if(this.mSource != null) {
                                                      String var46 = this.mSource;
                                                      String var47 = var5.mSource;
                                                      if(!var46.equals(var47)) {
                                                         break label191;
                                                      }
                                                   } else if(var5.mSource != null) {
                                                      break label191;
                                                   }

                                                   if(this.mState != null) {
                                                      AssetState var48 = this.mState;
                                                      AssetState var49 = var5.mState;
                                                      if(var48.equals(var49)) {
                                                         return var2;
                                                      }
                                                   } else if(var5.mState == null) {
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

         var2 = false;
      }

      return var2;
   }

   public String getAssetId() {
      return this.mAssetId;
   }

   public Uri getContentUri() {
      return this.mContentUri;
   }

   public long getDownloadPendingTime() {
      return this.mDownloadPendingTime;
   }

   public long getDownloadTime() {
      return this.mDownloadTime;
   }

   public long getInstallTime() {
      return this.mInstallTime;
   }

   public Obb getMainObb() {
      return this.mMainObb;
   }

   public ObbState getMainObbState() {
      return this.mMainObb.getState();
   }

   public String getPackageName() {
      return this.mPackageName;
   }

   public Obb getPatchObb() {
      return this.mPatchObb;
   }

   public ObbState getPatchObbState() {
      return this.mPatchObb.getState();
   }

   public String getReferrer() {
      return this.mReferrer;
   }

   public Long getRefundPeriodEndTime() {
      return this.mRefundPeriodEndTime;
   }

   public String getSignature() {
      return this.mSignature;
   }

   public long getSize() {
      return this.mSize;
   }

   public String getSource() {
      return this.mSource;
   }

   public AssetState getState() {
      return this.mState;
   }

   public long getUninstallTime() {
      return this.mUninstallTime;
   }

   public int getVersionCode() {
      return this.mVersionCode;
   }

   public int hashCode() {
      int var1 = 0;
      int var2;
      if(this.mPackageName != null) {
         var2 = this.mPackageName.hashCode();
      } else {
         var2 = 0;
      }

      int var3 = var2 * 31;
      int var4 = this.mVersionCode;
      int var5 = (var3 + var4) * 31;
      int var6;
      if(this.mAssetId != null) {
         var6 = this.mAssetId.hashCode();
      } else {
         var6 = 0;
      }

      int var7 = (var5 + var6) * 31;
      int var8;
      if(this.mState != null) {
         var8 = this.mState.hashCode();
      } else {
         var8 = 0;
      }

      int var9 = (var7 + var8) * 31;
      long var10 = this.mSize;
      long var12 = this.mSize >>> 32;
      int var14 = (int)(var10 ^ var12);
      int var15 = (var9 + var14) * 31;
      long var16 = this.mDownloadPendingTime;
      long var18 = this.mDownloadPendingTime >>> 32;
      int var20 = (int)(var16 ^ var18);
      int var21 = (var15 + var20) * 31;
      long var22 = this.mDownloadTime;
      long var24 = this.mDownloadTime >>> 32;
      int var26 = (int)(var22 ^ var24);
      int var27 = (var21 + var26) * 31;
      long var28 = this.mInstallTime;
      long var30 = this.mInstallTime >>> 32;
      int var32 = (int)(var28 ^ var30);
      int var33 = (var27 + var32) * 31;
      long var34 = this.mUninstallTime;
      long var36 = this.mUninstallTime >>> 32;
      int var38 = (int)(var34 ^ var36);
      int var39 = (var33 + var38) * 31;
      int var40;
      if(this.mSignature != null) {
         var40 = this.mSignature.hashCode();
      } else {
         var40 = 0;
      }

      int var41 = (var39 + var40) * 31;
      int var42;
      if(this.mContentUri != null) {
         var42 = this.mContentUri.hashCode();
      } else {
         var42 = 0;
      }

      int var43 = (var41 + var42) * 31;
      byte var44;
      if(this.mForwardLocked) {
         var44 = 1;
      } else {
         var44 = 0;
      }

      int var45 = (var43 + var44) * 31;
      int var46;
      if(this.mRefundPeriodEndTime != null) {
         var46 = this.mRefundPeriodEndTime.hashCode();
      } else {
         var46 = 0;
      }

      int var47 = (var45 + var46) * 31;
      int var48;
      if(this.mReferrer != null) {
         var48 = this.mReferrer.hashCode();
      } else {
         var48 = 0;
      }

      int var49 = (var47 + var48) * 31;
      if(this.mSource != null) {
         var1 = this.mSource.hashCode();
      }

      return var49 + var1;
   }

   public boolean isForwardLocked() {
      return this.mForwardLocked;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("LocalAssetVersionRecord{mPackageName=");
      String var2 = this.mPackageName;
      StringBuilder var3 = var1.append(var2).append(", mVersionCode=");
      int var4 = this.mVersionCode;
      StringBuilder var5 = var3.append(var4).append(", mAssetId=");
      String var6 = this.mAssetId;
      StringBuilder var7 = var5.append(var6).append(", mState=");
      AssetState var8 = this.mState;
      StringBuilder var9 = var7.append(var8).append(", mSize=");
      long var10 = this.mSize;
      StringBuilder var12 = var9.append(var10).append(", mDownloadPendingTime=");
      long var13 = this.mDownloadPendingTime;
      StringBuilder var15 = var12.append(var13).append(", mDownloadTime=");
      long var16 = this.mDownloadTime;
      StringBuilder var18 = var15.append(var16).append(", mInstallTime=");
      long var19 = this.mInstallTime;
      StringBuilder var21 = var18.append(var19).append(", mUninstallTime=");
      long var22 = this.mUninstallTime;
      StringBuilder var24 = var21.append(var22).append(", mSignature=\'");
      String var25 = this.mSignature;
      StringBuilder var26 = var24.append(var25).append('\'').append(", mContentUri=");
      Uri var27 = this.mContentUri;
      StringBuilder var28 = var26.append(var27).append(", mForwardLocked=");
      boolean var29 = this.mForwardLocked;
      StringBuilder var30 = var28.append(var29).append(", mRefundPeriodEndTime=");
      Long var31 = this.mRefundPeriodEndTime;
      StringBuilder var32 = var30.append(var31).append(", mReferrer=");
      String var33 = this.mReferrer;
      StringBuilder var34 = var32.append(var33).append(", mSource=");
      String var35 = this.mSource;
      StringBuilder var36 = var34.append(var35).append(", mMainObb=");
      Obb var37 = this.mMainObb;
      StringBuilder var38 = var36.append(var37).append(", mPatchObb=");
      Obb var39 = this.mPatchObb;
      return var38.append(var39).append('}').toString();
   }

   public void write(PersistentAssetStore var1, Writer.Op var2) {
      int[] var3 = LocalAssetVersionRecord.1.$SwitchMap$com$google$android$finsky$local$Writer$Op;
      int var4 = var2.ordinal();
      switch(var3[var4]) {
      case 1:
         var1.insertAssetVersion(this);
         return;
      case 2:
         String var5 = this.getAssetId();
         var1.deleteAssetVersion(var5);
         return;
      default:
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$Writer$Op = new int[Writer.Op.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$Writer$Op;
            int var1 = Writer.Op.INSERT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$local$Writer$Op;
            int var3 = Writer.Op.DELETE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
