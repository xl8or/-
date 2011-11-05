package com.android.settings;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;

public class ProgressCategory extends PreferenceCategory {

   private boolean mProgress = 0;


   public ProgressCategory(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.setLayoutResource(2130903115);
   }

   public void onBindView(View var1) {
      super.onBindView(var1);
      View var2 = var1.findViewById(2131427572);
      View var3 = var1.findViewById(2131427571);
      byte var4;
      if(this.mProgress) {
         var4 = 0;
      } else {
         var4 = 4;
      }

      var2.setVisibility(var4);
      var3.setVisibility(var4);
   }

   public void setProgress(boolean var1) {
      this.mProgress = var1;
      this.notifyChanged();
   }
}
