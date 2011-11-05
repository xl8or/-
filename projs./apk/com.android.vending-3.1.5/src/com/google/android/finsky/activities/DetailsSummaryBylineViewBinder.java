package com.google.android.finsky.activities;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.finsky.activities.ContentFilterActivity;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.remoting.protos.BookInfo;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.FinskyLog;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class DetailsSummaryBylineViewBinder {

   private static DecimalFormat mRatingFormatter;
   private Context mContext;
   private Document mDoc;
   private View mLayout;
   private NumberFormat mNumberFormatInstance;


   public DetailsSummaryBylineViewBinder() {}

   private void bindContentDescription() {
      long var1;
      if(this.mDoc.hasRating()) {
         var1 = this.mDoc.getRatingCount();
      } else {
         var1 = 0L;
      }

      String var6;
      if(this.mDoc.hasRating()) {
         DecimalFormat var3 = mRatingFormatter;
         double var4 = (double)this.mDoc.getStarRating();
         var6 = var3.format(var4);
      } else {
         var6 = "0";
      }

      DeviceDoc.AppDetails var7 = this.mDoc.getAppDetails();
      if(var7 != null) {
         String var8;
         if(var7.hasNumDownloads()) {
            var8 = var7.getNumDownloads();
         } else {
            var8 = "0";
         }

         View var9 = this.mLayout;
         Context var10 = this.mContext;
         Object[] var11 = new Object[4];
         Long var12 = Long.valueOf(var1);
         var11[0] = var12;
         var11[1] = var6;
         var11[2] = var8;
         Context var13 = this.mContext;
         int var14 = this.mDoc.getNormalizedContentRating();
         String var15 = ContentFilterActivity.getLabel(var13, var14);
         var11[3] = var15;
         String var16 = var10.getString(2131231086, var11);
         var9.setContentDescription(var16);
      } else {
         BookInfo.BookDetails var17 = this.mDoc.getBookDetails();
         if(var17 != null) {
            if(var17.hasPublicationDate()) {
               if(var17.hasPublisher()) {
                  if(var17.hasNumberOfPages()) {
                     try {
                        View var18 = this.mLayout;
                        Context var19 = this.mContext;
                        Object[] var20 = new Object[5];
                        Long var21 = Long.valueOf(var1);
                        var20[0] = var21;
                        var20[1] = var6;
                        String var22 = var17.getPublisher();
                        var20[2] = var22;
                        String var23 = DateUtils.formatIso8601Date(var17.getPublicationDate());
                        var20[3] = var23;
                        Integer var24 = Integer.valueOf(var17.getNumberOfPages());
                        var20[4] = var24;
                        String var25 = var19.getString(2131231087, var20);
                        var18.setContentDescription(var25);
                     } catch (ParseException var38) {
                        ;
                     }
                  }
               }
            }
         } else {
            DeviceDoc.VideoDetails var27 = this.mDoc.getVideoDetails();
            if(var27 != null) {
               if(var27.hasReleaseDate()) {
                  if(var27.hasDuration()) {
                     View var28 = this.mLayout;
                     Context var29 = this.mContext;
                     int var30 = 2131231088;
                     Object[] var31 = new Object[5];
                     Long var32 = Long.valueOf(var1);
                     var31[0] = var32;
                     var31[1] = var6;
                     byte var33 = 2;
                     String var34;
                     if(var27.hasContentRating()) {
                        var34 = var27.getContentRating();
                     } else {
                        var34 = this.mContext.getString(2131230952);
                     }

                     var31[var33] = var34;
                     String var35 = var27.getReleaseDate();
                     var31[3] = var35;
                     String var36 = var27.getDuration();
                     var31[4] = var36;
                     String var37 = var29.getString(var30, var31);
                     var28.setContentDescription(var37);
                  }
               }
            }
         }
      }
   }

   private void bindItemRating() {
      RatingBar var1 = (RatingBar)this.mLayout.findViewById(2131755181);
      TextView var2 = (TextView)this.mLayout.findViewById(2131755182);
      if(this.mDoc.hasRating() && this.mDoc.getRatingCount() > 0L) {
         var1.setVisibility(0);
         var2.setVisibility(0);
         float var3 = this.mDoc.getStarRating();
         var1.setRating(var3);
         NumberFormat var4 = this.mNumberFormatInstance;
         long var5 = this.mDoc.getRatingCount();
         String var7 = var4.format(var5);
         var2.setText(var7);
      } else {
         var1.setVisibility(4);
         var2.setVisibility(4);
      }
   }

   private void bindItemTextInfo() {
      TextView var1 = (TextView)this.mLayout.findViewById(2131755172);
      TextView var2 = (TextView)this.mLayout.findViewById(2131755173);
      TextView var3 = (TextView)this.mLayout.findViewById(2131755174);
      DeviceDoc.AppDetails var4 = this.mDoc.getAppDetails();
      if(var4 != null) {
         if(var4.hasUploadDate()) {
            String var5 = var4.getUploadDate();
            var1.setText(var5);
         }

         if(var4.hasNumDownloads()) {
            Context var6 = this.mContext;
            Object[] var7 = new Object[1];
            String var8 = var4.getNumDownloads();
            var7[0] = var8;
            String var9 = var6.getString(2131230949, var7);
            var2.setText(var9);
         }

         if(var4.hasInstallationSize()) {
            Context var10 = this.mContext;
            Object[] var11 = new Object[1];
            Context var12 = this.mContext;
            long var13 = var4.getInstallationSize();
            String var15 = Formatter.formatFileSize(var12, var13);
            var11[0] = var15;
            String var16 = var10.getString(2131231076, var11);
            var3.setText(var16);
         }
      }

      BookInfo.BookDetails var17 = this.mDoc.getBookDetails();
      if(var17 != null) {
         if(var17.hasPublicationDate()) {
            try {
               String var18 = DateUtils.formatIso8601Date(var17.getPublicationDate());
               var1.setText(var18);
            } catch (ParseException var35) {
               String var32 = "Cannot parse ISO 8601 date " + var35;
               Object[] var33 = new Object[0];
               FinskyLog.e(var32, var33);
            }
         }

         if(var17.hasPublisher()) {
            String var19 = var17.getPublisher();
            var2.setText(var19);
         }

         if(var17.hasNumberOfPages()) {
            Context var20 = this.mContext;
            Object[] var21 = new Object[1];
            Integer var22 = Integer.valueOf(var17.getNumberOfPages());
            var21[0] = var22;
            String var23 = var20.getString(2131230950, var21);
            var3.setText(var23);
         }
      }

      DeviceDoc.VideoDetails var24 = this.mDoc.getVideoDetails();
      if(var24 != null) {
         if(var24.hasContentRating()) {
            Context var25 = this.mContext;
            Object[] var26 = new Object[1];
            String var27 = var24.getContentRating();
            var26[0] = var27;
            String var28 = var25.getString(2131230951, var26);
            var1.setText(var28);
         } else {
            String var34 = this.mContext.getString(2131230952);
            var1.setText(var34);
         }

         if(var24.hasReleaseDate()) {
            String var29 = var24.getReleaseDate();
            var2.setText(var29);
         }

         if(var24.hasDuration()) {
            String var30 = var24.getDuration();
            var3.setText(var30);
         }
      }
   }

   public void bind(View var1, Document var2) {
      this.mLayout = var1;
      this.mDoc = var2;
      this.bindItemRating();
      this.bindItemTextInfo();
      this.bindContentDescription();
   }

   public void init(Context var1) {
      this.mContext = var1;
      NumberFormat var2 = NumberFormat.getNumberInstance();
      this.mNumberFormatInstance = var2;
      mRatingFormatter = new DecimalFormat("#.0");
   }

   public void onDestroyView() {}

   public void refresh() {
      View var1 = this.mLayout;
      Document var2 = this.mDoc;
      this.bind(var1, var2);
   }
}
