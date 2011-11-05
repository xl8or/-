package com.android.settings.fuelgauge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settings.fuelgauge.BatterySipper;
import com.android.settings.fuelgauge.PercentageBar;

public class PowerGaugePreference extends Preference {

   private PercentageBar mGauge;
   private Drawable mIcon;
   private BatterySipper mInfo;
   private double mPercent;
   private double mValue;


   public PowerGaugePreference(Context var1, Drawable var2, BatterySipper var3) {
      super(var1);
      this.setLayoutResource(2130903114);
      this.mIcon = var2;
      PercentageBar var4 = new PercentageBar();
      this.mGauge = var4;
      PercentageBar var5 = this.mGauge;
      Drawable var6 = var1.getResources().getDrawable(2130837504);
      var5.bar = var6;
      this.mInfo = var3;
   }

   BatterySipper getInfo() {
      return this.mInfo;
   }

   protected void onBindView(View var1) {
      super.onBindView(var1);
      ImageView var2 = (ImageView)var1.findViewById(2131427568);
      if(this.mIcon == null) {
         Drawable var3 = this.getContext().getResources().getDrawable(17301651);
         this.mIcon = var3;
      }

      Drawable var4 = this.mIcon;
      var2.setImageDrawable(var4);
      ImageView var5 = (ImageView)var1.findViewById(2131427570);
      PercentageBar var6 = this.mGauge;
      var5.setImageDrawable(var6);
      TextView var7 = (TextView)var1.findViewById(2131427569);
      StringBuilder var8 = new StringBuilder();
      int var9 = (int)Math.ceil(this.mPercent);
      String var10 = var8.append(var9).append("%").toString();
      var7.setText(var10);
   }

   void setGaugeValue(double var1) {
      this.mValue = var1;
      PercentageBar var3 = this.mGauge;
      double var4 = this.mValue;
      var3.percent = var4;
   }

   void setIcon(Drawable var1) {
      this.mIcon = var1;
      this.notifyChanged();
   }

   void setPercent(double var1) {
      this.mPercent = var1;
   }
}
