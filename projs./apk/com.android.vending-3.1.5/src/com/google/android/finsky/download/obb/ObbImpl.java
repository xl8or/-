package com.google.android.finsky.download.obb;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.finsky.download.Storage;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.FinskyLog;
import java.io.File;

class ObbImpl implements Obb {

   private final String TEMP_OBB_FILE_PREFIX = "temp.";
   private AssetStore mAssetStore;
   private String mContentUri = "";
   private String mFileName;
   private boolean mIsPatch;
   private String mPackageName;
   private long mSize;
   private ObbState mState;
   private String mUrl;
   private int mVersionCode;


   ObbImpl(AssetStore var1, boolean var2, String var3, int var4, String var5, long var6, ObbState var8) {
      this.mIsPatch = var2;
      this.mVersionCode = var4;
      this.mUrl = var5;
      this.mSize = var6;
      this.mPackageName = var3;
      String var9 = this.generateFileName();
      this.mFileName = var9;
      this.mState = var8;
      this.mAssetStore = var1;
   }

   private String generateFileName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.getTypeString();
      StringBuilder var3 = var1.append(var2).append(".");
      int var4 = this.mVersionCode;
      StringBuilder var5 = var3.append(var4).append(".");
      String var6 = this.mPackageName;
      StringBuilder var7 = var5.append(var6).append(".obb");
      return var1.toString();
   }

   private String getTypeString() {
      String var1;
      if(this.mIsPatch) {
         var1 = "patch";
      } else {
         var1 = "main";
      }

      return var1;
   }

   private boolean isDownloaded() {
      boolean var1 = false;
      File var2 = this.getFile();
      if(var2 != null && var2.exists()) {
         long var3 = var2.length();
         long var5 = this.mSize;
         if(var3 == var5) {
            var1 = true;
         }
      }

      return var1;
   }

   private void reset() {
      this.mVersionCode = -1;
      this.mUrl = "";
      this.mSize = 65535L;
      this.mFileName = "";
   }

   private boolean shouldDownload() {
      boolean var1;
      if(Storage.externalStorageAvailable() && !this.isDownloaded()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void delete() {
      File[] var1 = new File[2];
      File var2 = this.getFile();
      var1[0] = var2;
      File var3 = this.getTempFile();
      var1[1] = var3;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File var6 = var1[var5];
         if(var6 != null && var6.exists()) {
            boolean var7 = var6.delete();
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
               ObbImpl var5;
               label101: {
                  var5 = (ObbImpl)var1;
                  if(this.mFileName != null) {
                     String var6 = this.mFileName;
                     String var7 = var5.mFileName;
                     if(var6.equals(var7)) {
                        break label101;
                     }
                  } else if(var5.mFileName == null) {
                     break label101;
                  }

                  var2 = false;
                  return var2;
               }

               boolean var8 = this.mIsPatch;
               boolean var9 = var5.mIsPatch;
               if(var8 != var9) {
                  var2 = false;
                  return var2;
               } else {
                  int var10 = this.mVersionCode;
                  int var11 = var5.mVersionCode;
                  if(var10 != var11) {
                     var2 = false;
                  } else {
                     label102: {
                        if(this.mUrl != null) {
                           String var12 = this.mUrl;
                           String var13 = var5.mUrl;
                           if(!var12.equals(var13)) {
                              break label102;
                           }
                        } else if(var5.mUrl != null) {
                           break label102;
                        }

                        long var14 = this.mSize;
                        long var16 = var5.mSize;
                        if(var14 != var16) {
                           var2 = false;
                           return var2;
                        } else {
                           label103: {
                              if(this.mState != null) {
                                 ObbState var18 = this.mState;
                                 ObbState var19 = var5.mState;
                                 if(var18.equals(var19)) {
                                    break label103;
                                 }
                              } else if(var5.mState == null) {
                                 break label103;
                              }

                              var2 = false;
                              return var2;
                           }

                           label104: {
                              if(this.mPackageName != null) {
                                 String var20 = this.mPackageName;
                                 String var21 = var5.mPackageName;
                                 if(var20.equals(var21)) {
                                    break label104;
                                 }
                              } else if(var5.mPackageName == null) {
                                 break label104;
                              }

                              var2 = false;
                              return var2;
                           }

                           AssetStore var22 = this.mAssetStore;
                           AssetStore var23 = var5.mAssetStore;
                           if(var22 != var23) {
                              var2 = false;
                           } else {
                              if(this.mContentUri != null) {
                                 String var24 = this.mContentUri;
                                 String var25 = var5.mContentUri;
                                 if(var24.equals(var25)) {
                                    return var2;
                                 }
                              } else if(var5.mContentUri == null) {
                                 return var2;
                              }

                              var2 = false;
                           }

                           return var2;
                        }
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

   public boolean finalizeTempFile() {
      File var1 = this.getFile();
      byte var2;
      if(this.getTempFile().renameTo(var1)) {
         var2 = 1;
      } else {
         Object[] var3 = new Object[0];
         FinskyLog.w("Could not rename using expected temp file, using Cursor to get temp file path.", var3);
         String var4 = Storage.getFileUriForContentUri(Uri.parse(this.mContentUri)).getPath();
         var2 = (new File(var4)).renameTo(var1);
      }

      return (boolean)var2;
   }

   public String getContentUri() {
      return this.mContentUri;
   }

   public File getFile() {
      File var1;
      if(Storage.externalStorageAvailable() && this.mSize > 0L) {
         File var2 = ObbFactory.getParentDirectory(this.mPackageName);
         if(!var2.exists()) {
            boolean var3 = var2.mkdirs();
         }

         String var4 = this.mFileName;
         var1 = new File(var2, var4);
      } else {
         var1 = null;
      }

      return var1;
   }

   public long getSize() {
      return this.mSize;
   }

   public ObbState getState() {
      return this.mState;
   }

   public File getTempFile() {
      File var1 = this.getFile();
      File var2 = var1.getParentFile();
      StringBuilder var3 = (new StringBuilder()).append("temp.");
      String var4 = var1.getName();
      String var5 = var3.append(var4).toString();
      return new File(var2, var5);
   }

   public String getUrl() {
      return this.mUrl;
   }

   public int getVersionCode() {
      return this.mVersionCode;
   }

   public boolean isPatch() {
      return this.mIsPatch;
   }

   public void setContentUri(String var1) {
      if(TextUtils.isEmpty(var1)) {
         var1 = "";
      }

      this.mContentUri = var1;
   }

   public void setState(ObbState var1) {
      ObbState var2 = ObbState.NOT_APPLICABLE;
      if(var1.equals(var2)) {
         this.reset();
      }

      this.mState = var1;
      AssetStore var3 = this.mAssetStore;
      String var4 = this.mPackageName;
      LocalAsset var5 = var3.getAsset(var4);
      if(var5 != null) {
         boolean var6 = this.mIsPatch;
         var5.setObb(var6, this);
      }
   }

   public void syncStateWithStorage() {
      ObbState var1 = this.mState;
      ObbState var2 = ObbState.NOT_APPLICABLE;
      if(!var1.equals(var2)) {
         if(this.shouldDownload()) {
            ObbState var3 = ObbState.NOT_ON_STORAGE;
            this.setState(var3);
            this.delete();
         } else {
            ObbState var4 = ObbState.DOWNLOADED;
            this.setState(var4);
         }
      }
   }
}
