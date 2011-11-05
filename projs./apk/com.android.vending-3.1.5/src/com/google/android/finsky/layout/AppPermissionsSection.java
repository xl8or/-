package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class AppPermissionsSection extends LinearLayout {

   public AppPermissionsSection(Context var1) {
      super(var1);
   }

   public AppPermissionsSection(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      View var6 = this.findViewById(2131755212);
      if(var6.getVisibility() != 8) {
         int var7 = var6.getHeight();
         int var8 = this.getHeight();
         int var9 = this.getPaddingBottom();
         int var10 = var8 - var9;
         int var11 = var6.getLeft();
         int var12 = var10 - var7;
         int var13 = var6.getRight();
         var6.layout(var11, var12, var13, var10);
      }
   }
}
