package com.android.email.bubblebutton;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class LinearLayoutCreated extends LinearLayout {

   private Context mContext;
   private LinearLayout mLinearLayout;


   public LinearLayoutCreated(Context var1) {
      super(var1);
      this.mContext = var1;
   }

   public LinearLayout getLinearLayout() {
      return this.mLinearLayout;
   }

   public void setLinearLayout(int var1) {
      Context var2 = this.mContext;
      LinearLayout var3 = new LinearLayout(var2);
      this.mLinearLayout = var3;
      LinearLayout var4 = this.mLinearLayout;
      LayoutParams var5 = new LayoutParams(-1, -1);
      var4.setLayoutParams(var5);
      this.mLinearLayout.setId(var1);
   }
}
