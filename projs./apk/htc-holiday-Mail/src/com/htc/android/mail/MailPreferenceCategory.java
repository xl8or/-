package com.htc.android.mail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.htc.preference.HtcPreferenceCategory;

public class MailPreferenceCategory extends HtcPreferenceCategory {

   private static final String TAG = "MailPreferenceCategory";


   public MailPreferenceCategory(Context var1) {
      super(var1);
   }

   public MailPreferenceCategory(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public MailPreferenceCategory(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onBindView(View var1) {
      super.onBindView(var1);
      TextView var2 = (TextView)var1.findViewById(16908310);
      if(var2 != null) {
         LayoutParams var3 = var2.getLayoutParams();
         var3.width = -1;
         var3.height = -1;
         var2.setLayoutParams(var3);
      }
   }
}
