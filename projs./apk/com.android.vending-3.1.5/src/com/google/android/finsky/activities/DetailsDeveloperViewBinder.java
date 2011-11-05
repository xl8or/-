package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.utils.CorpusMetadata;
import com.google.android.finsky.utils.IntentUtils;

public class DetailsDeveloperViewBinder extends DetailsViewBinder {

   private String mDeveloperEmail;
   private String mDeveloperWebsite;
   private int mRows;


   public DetailsDeveloperViewBinder() {}

   public void bind(View var1, Document var2) {
      super.bind(var1, var2, 2131755140, 2131230973);
      this.mRows = 0;
      if(this.mDoc.getAppDetails() != null) {
         String var3 = this.mDoc.getAppDetails().getDeveloperWebsite();
         this.mDeveloperWebsite = var3;
         String var4 = this.mDoc.getAppDetails().getDeveloperEmail();
         this.mDeveloperEmail = var4;
      }

      if(!TextUtils.isEmpty(this.mDeveloperWebsite)) {
         int var5 = this.mRows + 1;
         this.mRows = var5;
      }

      if(!TextUtils.isEmpty(this.mDeveloperEmail)) {
         int var6 = this.mRows + 1;
         this.mRows = var6;
      }

      if(this.mRows == 0) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
         this.populateContent();
         var1.findViewById(2131755137).setVisibility(8);
      }
   }

   protected void populateContent() {
      LinearLayout var1 = (LinearLayout)this.mLayout.findViewById(2131755143);
      var1.removeAllViews();
      if(!TextUtils.isEmpty(this.mDeveloperWebsite)) {
         View var2 = this.mInflater.inflate(2130968662, var1, (boolean)0);
         TextView var3 = (TextView)var2.findViewById(2131755227);
         TextView var4 = (TextView)var2.findViewById(2131755228);
         var3.setText(2131230978);
         String var5 = this.mDeveloperWebsite;
         var4.setText(var5);
         Drawable var6 = this.mContext.getResources().getDrawable(2130837552);
         int var7 = var6.getIntrinsicWidth();
         int var8 = var6.getIntrinsicHeight();
         var6.setBounds(0, 0, var7, var8);
         var4.setCompoundDrawables(var6, (Drawable)null, (Drawable)null, (Drawable)null);
         DetailsDeveloperViewBinder.1 var9 = new DetailsDeveloperViewBinder.1();
         var2.setOnClickListener(var9);
         Context var10 = this.mContext;
         int var11 = this.mDoc.getBackend();
         Drawable var12 = CorpusMetadata.getBucketEntryBackground(var10, var11);
         var2.setBackgroundDrawable(var12);
         var1.addView(var2);
      }

      if(!TextUtils.isEmpty(this.mDeveloperEmail)) {
         View var13 = this.mInflater.inflate(2130968662, var1, (boolean)0);
         TextView var14 = (TextView)var13.findViewById(2131755227);
         TextView var15 = (TextView)var13.findViewById(2131755228);
         var14.setText(2131230979);
         Drawable var16 = this.mContext.getResources().getDrawable(2130837551);
         int var17 = var16.getIntrinsicWidth();
         int var18 = var16.getIntrinsicHeight();
         var16.setBounds(0, 0, var17, var18);
         var15.setCompoundDrawables(var16, (Drawable)null, (Drawable)null, (Drawable)null);
         String var19 = this.mDeveloperEmail;
         var15.setText(var19);
         DetailsDeveloperViewBinder.2 var20 = new DetailsDeveloperViewBinder.2();
         var13.setOnClickListener(var20);
         Context var21 = this.mContext;
         int var22 = this.mDoc.getBackend();
         Drawable var23 = CorpusMetadata.getBucketEntryBackground(var21, var22);
         var13.setBackgroundDrawable(var23);
         var1.addView(var13);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         Context var2 = DetailsDeveloperViewBinder.this.mContext;
         Intent var3 = IntentUtils.createViewIntentForUrl(Uri.parse(DetailsDeveloperViewBinder.this.mDeveloperWebsite));
         var2.startActivity(var3);
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         StringBuilder var2 = (new StringBuilder()).append("mailto:");
         String var3 = DetailsDeveloperViewBinder.this.mDeveloperEmail;
         Intent var4 = IntentUtils.createSendIntentForUrl(Uri.parse(var2.append(var3).toString()));
         String var5 = DetailsDeveloperViewBinder.this.mDoc.getTitle();
         var4.putExtra("android.intent.extra.SUBJECT", var5);
         DetailsDeveloperViewBinder.this.mContext.startActivity(var4);
      }
   }
}
