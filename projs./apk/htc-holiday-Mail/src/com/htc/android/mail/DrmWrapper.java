package com.htc.android.mail;

import android.drm.mobile1.DrmException;
import android.drm.mobile1.DrmRawContent;
import android.drm.mobile1.DrmRights;
import android.drm.mobile1.DrmRightsManager;
import android.net.Uri;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DrmWrapper {

   private static final boolean DEBUG = false;
   private static final boolean LOCAL_LOGV = false;
   private static final String LOG_TAG = "DrmWrapper";
   private final byte[] mData;
   private final Uri mDataUri;
   private byte[] mDecryptedData;
   private final DrmRawContent mDrmObject;
   private DrmRights mRight;


   public DrmWrapper(String var1, Uri var2, byte[] var3) throws DrmException, IOException {
      if(var1 != null && var3 != null) {
         this.mDataUri = var2;
         this.mData = var3;
         ByteArrayInputStream var4 = new ByteArrayInputStream(var3);
         int var5 = var4.available();
         DrmRawContent var6 = new DrmRawContent(var4, var5, var1);
         this.mDrmObject = var6;
         if(!this.isRightsInstalled()) {
            this.installRights(var3);
         }
      } else {
         throw new IllegalArgumentException("Content-Type or data may not be null.");
      }
   }

   private int getPermission() {
      String var1 = this.mDrmObject.getContentType();
      return 2;
   }

   public static boolean isDrmObject(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else if(!var0.equalsIgnoreCase("application/vnd.oma.drm.content") && !var0.equalsIgnoreCase("application/vnd.oma.drm.message")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean consumeRights() {
      byte var1;
      if(this.mRight == null) {
         var1 = 0;
      } else {
         DrmRights var2 = this.mRight;
         int var3 = this.getPermission();
         var1 = var2.consumeRights(var3);
      }

      return (boolean)var1;
   }

   public String getContentType() {
      return this.mDrmObject.getContentType();
   }

   public byte[] getDecryptedData() throws IOException {
      if(this.mDecryptedData == null && this.mRight != null) {
         DrmRawContent var1 = this.mDrmObject;
         DrmRights var2 = this.mRight;
         InputStream var3 = var1.getContentInputStream(var2);
         boolean var22 = false;

         try {
            var22 = true;
            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            byte[] var5 = new byte[256];

            while(true) {
               int var6 = var3.read(var5);
               if(var6 <= 0) {
                  byte[] var8 = var4.toByteArray();
                  this.mDecryptedData = var8;
                  var22 = false;
                  break;
               }

               var4.write(var5, 0, var6);
            }
         } finally {
            if(var22) {
               try {
                  var3.close();
               } catch (IOException var23) {
                  String var14 = var23.getMessage();
                  Log.e("DrmWrapper", var14, var23);
               }

            }
         }

         try {
            var3.close();
         } catch (IOException var24) {
            String var17 = var24.getMessage();
            Log.e("DrmWrapper", var17, var24);
         }
      }

      byte[] var12;
      if(this.mDecryptedData != null) {
         byte[] var9 = new byte[this.mDecryptedData.length];
         byte[] var10 = this.mDecryptedData;
         int var11 = this.mDecryptedData.length;
         System.arraycopy(var10, 0, var9, 0, var11);
         var12 = var9;
      } else {
         var12 = null;
      }

      return var12;
   }

   public byte[] getOriginalData() {
      return this.mData;
   }

   public Uri getOriginalUri() {
      return this.mDataUri;
   }

   public String getRightsAddress() {
      String var1;
      if(this.mDrmObject == null) {
         var1 = null;
      } else {
         var1 = this.mDrmObject.getRightsAddress();
      }

      return var1;
   }

   public void installRights(byte[] var1) throws DrmException, IOException {
      if(var1 == null) {
         throw new DrmException("Right data may not be null.");
      } else {
         ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
         DrmRightsManager var3 = DrmRightsManager.getInstance();
         int var4 = var1.length;
         DrmRights var5 = var3.installRights(var2, var4, "application/vnd.oma.drm.message");
         this.mRight = var5;
      }
   }

   public boolean isAllowedToForward() {
      int var1 = this.mDrmObject.getRawType();
      boolean var2;
      if(3 != var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean isRightsInstalled() {
      boolean var1;
      if(this.mRight != null) {
         var1 = true;
      } else {
         DrmRightsManager var2 = DrmRightsManager.getInstance();
         DrmRawContent var3 = this.mDrmObject;
         DrmRights var4 = var2.queryRights(var3);
         this.mRight = var4;
         if(this.mRight != null) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }
}
