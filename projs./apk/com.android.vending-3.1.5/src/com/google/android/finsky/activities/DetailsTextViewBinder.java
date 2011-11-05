package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.DetailsViewBinder;
import com.google.android.finsky.layout.DetailsTextLayout;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.ExpandableUtils;

public class DetailsTextViewBinder extends DetailsViewBinder {

   public static final int DEFAULT_MAX_LINES = 6;
   private static final int EXPANDED_MAX_LINES = Integer.MAX_VALUE;
   private TextView mContentView;
   private int mExpansionState;
   private TextView mExtraContentView;
   private int mFullHeight;
   private int mTruncatedHeight;


   public DetailsTextViewBinder() {}

   private void collapseContent() {
      View var1 = (View)this.mLayout.getParent();
      int var2 = this.mFullHeight;
      int var3 = this.mTruncatedHeight;
      int var4 = var2 - var3;
      int var5 = this.mExtraContentView.getHeight();

      int var6;
      for(var6 = var4 + var5; !(var1 instanceof ScrollView); var1 = (View)var1.getParent()) {
         ;
      }

      int var7 = -var6;
      var1.scrollBy(0, var7);
      this.showMoreButton();
      this.mContentView.setMaxLines(6);
      this.mExtraContentView.setVisibility(8);
      this.mExpansionState = 1;
   }

   private void configureContent() {
      if(this.mExpansionState == 2) {
         this.expandContent();
      } else {
         this.collapseContent();
      }

      DetailsTextViewBinder.2 var1 = new DetailsTextViewBinder.2();
      this.setButtonClickListener(2131755138, var1);
      DetailsTextViewBinder.3 var2 = new DetailsTextViewBinder.3();
      this.setButtonClickListener(2131755139, var2);
   }

   private void expandContent() {
      this.showLessButton();
      this.mContentView.setMaxLines(Integer.MAX_VALUE);
      if(!TextUtils.isEmpty(this.mExtraContentView.getText())) {
         this.mExtraContentView.setVisibility(0);
      }

      this.mExpansionState = 2;
   }

   private void showLessButton() {
      this.setButtonVisibility(2131755138, 8, 0);
      this.setButtonVisibility(2131755139, 0, 2131230971);
   }

   private void showMoreButton() {
      this.setButtonVisibility(2131755138, 0, 2131230970);
      this.setButtonVisibility(2131755139, 8, 0);
   }

   public void bind(View var1, Document var2, int var3, CharSequence var4, CharSequence var5, Bundle var6) {
      super.bind(var1, var2, 2131755140, var3);
      if(TextUtils.isEmpty(var4)) {
         this.mLayout.setVisibility(8);
      } else {
         this.mLayout.setVisibility(0);
         TextView var7 = (TextView)this.mLayout.findViewById(2131755188);
         this.mContentView = var7;
         TextView var8 = (TextView)this.mLayout.findViewById(2131755189);
         this.mExtraContentView = var8;
         TextView var9 = this.mContentView;
         MovementMethod var10 = LinkMovementMethod.getInstance();
         var9.setMovementMethod(var10);
         CharSequence var11 = this.mContentView.getText();
         if(var11 != null) {
            String var12 = var4.toString();
            String var13 = var11.toString();
            if(var12.equals(var13)) {
               return;
            }
         }

         this.mContentView.setText(var4);
         if(!TextUtils.isEmpty(var5)) {
            this.mExtraContentView.setText(var5);
         } else {
            this.mExtraContentView.setVisibility(8);
         }

         this.mFullHeight = -1;
         this.mTruncatedHeight = -1;
         this.setButtonVisibility(2131755138, 8, 0);
         this.setButtonVisibility(2131755139, 8, 0);
         String var14 = Integer.toString(this.mLayout.getId());
         int var15 = ExpandableUtils.getSavedExpansionState(var6, var14);
         this.mExpansionState = var15;
         DetailsTextLayout var16 = (DetailsTextLayout)this.mLayout.findViewById(2131755187);
         DetailsTextViewBinder.1 var17 = new DetailsTextViewBinder.1(var16);
         var16.setMetricsListener(var17);
      }
   }

   public void init(Context var1) {
      super.init(var1, (DfeApi)null, (NavigationManager)null);
   }

   public void saveInstanceState(Bundle var1) {
      String var2 = Integer.toString(this.mLayout.getId());
      int var3 = this.mExpansionState;
      ExpandableUtils.saveExpansionState(var1, var2, var3);
   }

   class 1 implements DetailsTextLayout.MetricsListener {

      // $FF: synthetic field
      final DetailsTextLayout val$contentLayout;


      1(DetailsTextLayout var2) {
         this.val$contentLayout = var2;
      }

      public void metricsAvailable(int var1, int var2) {
         DetailsTextViewBinder.this.mFullHeight = var1;
         DetailsTextViewBinder.this.mTruncatedHeight = var2;
         int var5 = DetailsTextViewBinder.this.mFullHeight;
         int var6 = DetailsTextViewBinder.this.mTruncatedHeight;
         if(var5 <= var6) {
            if(!TextUtils.isEmpty(DetailsTextViewBinder.this.mExtraContentView.getText())) {
               DetailsTextViewBinder.this.mExtraContentView.setVisibility(0);
            }

            DetailsTextViewBinder.this.mLayout.findViewById(2131755137).setVisibility(8);
         } else {
            DetailsTextViewBinder.this.configureContent();
         }

         this.val$contentLayout.setMetricsListener((DetailsTextLayout.MetricsListener)null);
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         DetailsTextViewBinder.this.expandContent();
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         DetailsTextViewBinder.this.collapseContent();
      }
   }
}
