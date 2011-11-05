package com.broadcom.bt.app.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController.AlertParams;

public class AirplaneErrorActivity extends AlertActivity implements OnClickListener {

   private String mErrorContent;


   public AirplaneErrorActivity() {}

   private View createView() {
      View var1 = this.getLayoutInflater().inflate(2130903064, (ViewGroup)null);
      TextView var2 = (TextView)var1.findViewById(2131427382);
      String var3 = this.mErrorContent;
      var2.setText(var3);
      return var1;
   }

   public void onClick(DialogInterface var1, int var2) {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      int var3 = var2.getIntExtra("title", 0);
      int var4 = var2.getIntExtra("content", 0);
      String var5 = this.getString(var3);
      String var6 = this.getString(var4);
      this.mErrorContent = var6;
      AlertParams var7 = this.mAlertParams;
      var7.mIconId = 17301543;
      var7.mTitle = var5;
      View var8 = this.createView();
      var7.mView = var8;
      String var9 = this.getString(2131232585);
      var7.mPositiveButtonText = var9;
      var7.mPositiveButtonListener = this;
      this.setupAlert();
   }
}
