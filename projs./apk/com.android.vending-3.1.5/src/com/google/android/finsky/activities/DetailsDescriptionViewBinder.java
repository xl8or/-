package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import com.google.android.finsky.activities.ContentFilterActivity;
import com.google.android.finsky.activities.DetailsTextViewBinder;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.BookInfo;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.CorpusMetadata;

public class DetailsDescriptionViewBinder extends DetailsTextViewBinder {

   public DetailsDescriptionViewBinder() {}

   private String getExtraSummary(Document var1) {
      DeviceDoc.AppDetails var2 = var1.getAppDetails();
      StringBuilder var3;
      String var30;
      if(var2 != null) {
         var3 = new StringBuilder();
         if(var2.hasVersionString()) {
            Context var4 = this.mContext;
            Object[] var5 = new Object[1];
            String var6 = var2.getVersionString();
            var5[0] = var6;
            String var7 = var4.getString(2131230948, var5);
            var3.append(var7);
         }

         if(var2.hasUploadDate()) {
            if(var3.length() > 0) {
               StringBuilder var9 = var3.append("\n");
            }

            Context var10 = this.mContext;
            Object[] var11 = new Object[1];
            String var12 = var2.getUploadDate();
            var11[0] = var12;
            String var13 = var10.getString(2131231075, var11);
            var3.append(var13);
         }

         if(var2.hasInstallationSize()) {
            if(var3.length() > 0) {
               StringBuilder var15 = var3.append("\n");
            }

            Context var16 = this.mContext;
            Object[] var17 = new Object[1];
            Context var18 = this.mContext;
            long var19 = var2.getInstallationSize();
            String var21 = Formatter.formatFileSize(var18, var19);
            var17[0] = var21;
            String var22 = var16.getString(2131231076, var17);
            var3.append(var22);
         }

         if(var3.length() > 0) {
            StringBuilder var24 = var3.append("\n");
         }

         int var25 = var1.getNormalizedContentRating();
         String var26 = this.mContext.getString(2131230947);
         StringBuilder var27 = var3.append(var26).append(" ");
         String var28 = ContentFilterActivity.getLabel(this.mContext, var25);
         var27.append(var28);
         var30 = var3.toString();
      } else {
         BookInfo.BookDetails var31 = var1.getBookDetails();
         if(var31 != null) {
            var3 = new StringBuilder();
            if(var31.hasIsbn()) {
               String var32 = var31.getIsbn();
               var3.append(var32);
            }

            var30 = var3.toString();
         } else {
            var30 = null;
         }
      }

      return var30;
   }

   public void bind(View var1, Document var2, Bundle var3) {
      int var4 = CorpusMetadata.getDescriptionHeaderStringId(var2.getBackend());
      CharSequence var5 = var2.getDescription();
      String var6 = this.getExtraSummary(var2);
      super.bind(var1, var2, var4, var5, var6, var3);
   }
}
