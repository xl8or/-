package com.google.android.finsky.download;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.finsky.download.DownloadManagerConstants;

public class DownloadRequest {

   private final int SYSTEM_UID = 1000;
   ContentValues mContentValues;


   public DownloadRequest(Uri var1, String var2, String var3, String var4, String var5, String var6, Uri var7) {
      ContentValues var8 = new ContentValues();
      this.mContentValues = var8;
      ContentValues var9 = this.mContentValues;
      Integer var10 = Integer.valueOf(1000);
      var9.put("otheruid", var10);
      ContentValues var11 = this.mContentValues;
      String var12 = var1.toString();
      var11.put("uri", var12);
      if(var7 != null) {
         ContentValues var13 = this.mContentValues;
         Integer var14 = Integer.valueOf(DownloadManagerConstants.getFileDestinationConstant());
         var13.put("destination", var14);
         ContentValues var15 = this.mContentValues;
         String var16 = var7.toString();
         var15.put("hint", var16);
      } else {
         ContentValues var21 = this.mContentValues;
         Integer var22 = Integer.valueOf(2);
         var21.put("destination", var22);
      }

      this.mContentValues.put("notificationpackage", var3);
      this.mContentValues.put("notificationclass", var4);
      if(var5 != null && var6 != null) {
         ContentValues var17 = this.mContentValues;
         String var18 = var5 + "=" + var6;
         var17.put("cookiedata", var18);
      }

      if(TextUtils.isEmpty(var2)) {
         ContentValues var19 = this.mContentValues;
         Integer var20 = Integer.valueOf(2);
         var19.put("visibility", var20);
      } else {
         ContentValues var23 = this.mContentValues;
         Integer var24 = Integer.valueOf(0);
         var23.put("visibility", var24);
         this.mContentValues.put("title", var2);
      }
   }

   ContentValues toContentValues() {
      return this.mContentValues;
   }
}
