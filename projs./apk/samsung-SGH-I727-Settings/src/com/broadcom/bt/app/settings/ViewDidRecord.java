package com.broadcom.bt.app.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController.AlertParams;

public class ViewDidRecord extends AlertActivity {

   public ViewDidRecord() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      AlertParams var2 = this.mAlertParams;
      var2.mIconId = 17301659;
      Intent var3 = this.getIntent();
      String var4 = var3.getStringExtra("title");
      var2.mTitle = var4;
      View var5 = this.getLayoutInflater().inflate(2130903075, (ViewGroup)null);
      var2.mView = var5;
      TextView var6 = (TextView)var2.mView.findViewById(2131427382);
      String var7 = var3.getStringExtra("content");
      var6.setText(var7);
      String var8 = this.getString(2131232585);
      var2.mPositiveButtonText = var8;
      this.setupAlert();
   }
}
