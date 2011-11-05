package com.android.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.android.settings.R;

public class IconPreferenceScreen extends Preference {

   private Drawable mIcon;


   public IconPreferenceScreen(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public IconPreferenceScreen(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.setLayoutResource(2130903113);
      int[] var4 = R.styleable.IconPreferenceScreen;
      Drawable var5 = var1.obtainStyledAttributes(var2, var4, var3, 0).getDrawable(0);
      this.mIcon = var5;
   }

   public void onBindView(View var1) {
      super.onBindView(var1);
      ImageView var2 = (ImageView)var1.findViewById(2131427364);
      if(var2 != null) {
         if(this.mIcon != null) {
            Drawable var3 = this.mIcon;
            var2.setImageDrawable(var3);
         }
      }
   }
}
