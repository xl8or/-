package com.android.settings;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.settings.AccountSyncSettings;

public class AccountSyncSettingsInAddAccount extends AccountSyncSettings implements OnClickListener {

   private View mFinishArea;
   private View mFinishButton;


   public AccountSyncSettingsInAddAccount() {}

   public void onClick(View var1) {
      this.finish();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mRemoveAccountArea.setVisibility(8);
      View var2 = this.findViewById(2131099655);
      this.mFinishArea = var2;
      this.mFinishArea.setVisibility(0);
      View var3 = this.findViewById(2131099656);
      this.mFinishButton = var3;
      this.mFinishButton.setOnClickListener(this);
   }
}
