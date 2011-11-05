package com.android.settings.fuelgauge;

import android.content.Context;
import android.os.BatteryStats;
import android.preference.Preference;
import android.view.View;
import com.android.settings.fuelgauge.BatteryHistoryChart;

public class BatteryHistoryPreference extends Preference {

   private BatteryStats mStats;


   public BatteryHistoryPreference(Context var1, BatteryStats var2) {
      super(var1);
      this.setLayoutResource(2130903107);
      this.mStats = var2;
   }

   BatteryStats getStats() {
      return this.mStats;
   }

   protected void onBindView(View var1) {
      super.onBindView(var1);
      BatteryHistoryChart var2 = (BatteryHistoryChart)var1.findViewById(16842754);
      BatteryStats var3 = this.mStats;
      var2.setStats(var3);
   }
}
