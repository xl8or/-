package com.android.settings.fuelgauge;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import com.android.internal.os.BatteryStatsImpl;
import com.android.settings.fuelgauge.BatteryHistoryChart;

public class BatteryHistoryDetail extends Activity {

   public static final String EXTRA_STATS = "stats";
   private BatteryStatsImpl mStats;


   public BatteryHistoryDetail() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      byte[] var2 = this.getIntent().getByteArrayExtra("stats");
      Parcel var3 = Parcel.obtain();
      int var4 = var2.length;
      var3.unmarshall(var2, 0, var4);
      var3.setDataPosition(0);
      this.setContentView(2130903107);
      BatteryStatsImpl var5 = (BatteryStatsImpl)BatteryStatsImpl.CREATOR.createFromParcel(var3);
      this.mStats = var5;
      BatteryHistoryChart var6 = (BatteryHistoryChart)this.findViewById(16842754);
      BatteryStatsImpl var7 = this.mStats;
      var6.setStats(var7);
   }
}
