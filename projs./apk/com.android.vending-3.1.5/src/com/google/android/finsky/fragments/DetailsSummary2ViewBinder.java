package com.google.android.finsky.fragments;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ContentFilterActivity;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.BookInfo;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageInfoCache;
import java.text.ParseException;

public class DetailsSummary2ViewBinder {

   private Context mContext;
   private Document mDoc;
   private View mLayout;


   public DetailsSummary2ViewBinder() {}

   private void bindDetails() {
      TextView var1 = (TextView)this.mLayout.findViewById(2131755172);
      TextView var2 = (TextView)this.mLayout.findViewById(2131755173);
      TextView var3 = (TextView)this.mLayout.findViewById(2131755174);
      DeviceDoc.AppDetails var4 = this.mDoc.getAppDetails();
      if(var4 != null) {
         Context var5 = this.mContext;
         int var6 = this.mDoc.getNormalizedContentRating();
         String var7 = ContentFilterActivity.getLabel(var5, var6);
         Context var8 = this.mContext;
         Object[] var9 = new Object[]{var7};
         String var10 = var8.getString(2131230947, var9);
         var1.setText(var10);
         StringBuilder var11 = new StringBuilder();
         if(var4.hasVersionString()) {
            Context var12 = this.mContext;
            Object[] var13 = new Object[1];
            String var14 = var4.getVersionString();
            var13[0] = var14;
            String var15 = var12.getString(2131230948, var13);
            StringBuilder var16 = var11.append(var15).append("   ");
         }

         if(var4.hasUploadDate()) {
            String var17 = var4.getUploadDate();
            var11.append(var17);
         }

         String var19 = var11.toString();
         var2.setText(var19);
         StringBuilder var20 = new StringBuilder();
         if(var4.hasInstallationSize()) {
            Context var21 = this.mContext;
            long var22 = var4.getInstallationSize();
            String var24 = Formatter.formatFileSize(var21, var22);
            StringBuilder var25 = var20.append(var24).append("   ");
         }

         if(var4.hasNumDownloads()) {
            Context var26 = this.mContext;
            Object[] var27 = new Object[1];
            String var28 = var4.getNumDownloads();
            var27[0] = var28;
            String var29 = var26.getString(2131230949, var27);
            var20.append(var29);
         }

         String var31 = var20.toString();
         var3.setText(var31);
      }

      BookInfo.BookDetails var32 = this.mDoc.getBookDetails();
      if(var32 != null) {
         if(var32.hasPublicationDate()) {
            try {
               String var33 = DateUtils.formatIso8601Date(var32.getPublicationDate());
               var1.setText(var33);
            } catch (ParseException var50) {
               String var47 = "Cannot parse ISO 8601 date " + var50;
               Object[] var48 = new Object[0];
               FinskyLog.e(var47, var48);
            }
         }

         if(var32.hasPublisher()) {
            String var34 = var32.getPublisher();
            var2.setText(var34);
         }

         if(var32.hasNumberOfPages()) {
            Context var35 = this.mContext;
            Object[] var36 = new Object[1];
            Integer var37 = Integer.valueOf(var32.getNumberOfPages());
            var36[0] = var37;
            String var38 = var35.getString(2131230950, var36);
            var3.setText(var38);
         }
      }

      DeviceDoc.VideoDetails var39 = this.mDoc.getVideoDetails();
      if(var39 != null) {
         if(var39.hasContentRating()) {
            Context var40 = this.mContext;
            Object[] var41 = new Object[1];
            String var42 = var39.getContentRating();
            var41[0] = var42;
            String var43 = var40.getString(2131230951, var41);
            var1.setText(var43);
         } else {
            String var49 = this.mContext.getString(2131230952);
            var1.setText(var49);
         }

         if(var39.hasReleaseDate()) {
            String var44 = var39.getReleaseDate();
            var2.setText(var44);
         }

         if(var39.hasDuration()) {
            String var45 = var39.getDuration();
            var3.setText(var45);
         }
      }
   }

   private void setupOwnershipStatus() {
      if(this.mDoc.hasDetails()) {
         ImageView var1 = (ImageView)this.mLayout.findViewById(2131755171);
         PackageInfoCache var2 = FinskyApp.get().getPackageInfoCache();
         if(this.mDoc.isLocallyAvailable(var2)) {
            int var3 = CorpusMetadata.getOwnedIconResource(this.mDoc.getBackend());
            var1.setImageResource(var3);
         } else if(this.mDoc.ownedByUser(var2)) {
            int var4 = CorpusMetadata.getOwnedNotLocalIconResource(this.mDoc.getBackend());
            var1.setImageResource(var4);
         }
      }
   }

   public void bind(View var1, Document var2) {
      this.mLayout = var1;
      this.mDoc = var2;
      this.bindDetails();
      this.setupOwnershipStatus();
   }

   public void init(Context var1, DfeApi var2) {
      this.mContext = var1;
   }

   public void onDestroyView() {}

   public void refresh() {
      View var1 = this.mLayout;
      Document var2 = this.mDoc;
      this.bind(var1, var2);
   }
}
