package com.htc.android.mail.easclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class ScheduleSetUpItem extends LinearLayout {

   private Context mContex;
   private RadioButton radioBtn;
   private TextView txt_1x1;
   private TextView txt_2x1;


   public ScheduleSetUpItem(Context var1) {
      super(var1);
      this.mContext = var1;
      View var2 = LayoutInflater.from(this.mContext).inflate(34144337, this);
      TextView var3 = (TextView)this.findViewById(33685520);
      this.txt_1x1 = var3;
      TextView var4 = (TextView)this.findViewById(33685524);
      this.txt_2x1 = var4;
      RadioButton var5 = (RadioButton)this.findViewById(33685756);
      this.radioBtn = var5;
   }

   public void setCheck(boolean var1) {
      this.radioBtn.setChecked(var1);
   }

   public void setText(String var1, String var2) {
      if(var1 == null) {
         this.txt_1x1.setText("");
      } else {
         this.txt_1x1.setText(var1);
      }

      if(var2 == null) {
         this.txt_2x1.setText("");
      } else {
         this.txt_2x1.setText(var2);
      }
   }
}
