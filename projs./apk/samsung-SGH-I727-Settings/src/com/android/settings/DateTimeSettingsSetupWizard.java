package com.android.settings;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.settings.DateTimeSettings;

public class DateTimeSettingsSetupWizard extends DateTimeSettings implements OnClickListener {

   private View mNextButton;


   public DateTimeSettingsSetupWizard() {}

   public void onClick(View var1) {
      this.setResult(-1);
      this.finish();
   }

   protected void onCreate(Bundle var1) {
      boolean var2 = this.requestWindowFeature(1);
      super.onCreate(var1);
      this.setContentView(2130903069);
      View var3 = this.findViewById(2131427369);
      this.mNextButton = var3;
      this.mNextButton.setOnClickListener(this);
   }
}
