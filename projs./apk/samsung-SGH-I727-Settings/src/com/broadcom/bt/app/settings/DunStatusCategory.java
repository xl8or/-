package com.broadcom.bt.app.settings;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;

public class DunStatusCategory extends PreferenceCategory {

   private boolean mStatus = 0;


   public DunStatusCategory(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.setLayoutResource(2130903057);
   }

   public void onBindView(View var1) {
      super.onBindView(var1);
      View var2 = var1.findViewById(2131427368);
      View var3 = var1.findViewById(2131427367);
      byte var4;
      if(this.mStatus) {
         var4 = 0;
      } else {
         var4 = 4;
      }

      var2.setVisibility(var4);
      var3.setVisibility(var4);
   }

   public void setStatus(boolean var1) {
      this.mStatus = var1;
      this.notifyChanged();
   }
}
