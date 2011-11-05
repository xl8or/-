package com.android.email.activity.bubblelayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import com.android.email.activity.bubblelayout.BubbleData;

public class BubbleButton extends Button {

   private static final int BUTTON_HEIGHT = 43;
   public static final int BUTTON_MARGIN_BOTTOM = 7;
   public static final int BUTTON_MARGIN_LEFT = 0;
   public static final int BUTTON_MARGIN_RIGHT = 10;
   public static final int BUTTON_MARGIN_TOP = 0;
   private static final int BUTTON_MAX_WIDTH = 240;
   private static final int BUTTON_PADDING_LEFT = 10;
   private static final int BUTTON_PADDING_RIGHT = 10;
   private Context mContext;
   private BubbleData mData;
   private int mExpectedWidth;


   public BubbleButton(Context var1, BubbleData var2) {
      super(var1);
      this.mContext = var1;
      this.mData = var2;
      this.initButton();
   }

   private void initButton() {
      Resources var1 = this.mContext.getResources();
      String var2 = this.mData.getName();
      if(TextUtils.isEmpty(var2)) {
         String var3 = this.mData.getAddress();
         this.setText(var3);
      } else {
         this.setText(var2);
      }

      TruncateAt var4 = TruncateAt.END;
      this.setEllipsize(var4);
      this.setBackgroundResource(2130838007);
      float var5 = (float)var1.getInteger(2131230871);
      this.setTextSize(0, var5);
      int var6 = Color.rgb(0, 0, 0);
      this.setTextColor(var6);
      int var7 = var1.getInteger(2131230866);
      this.setHeight(var7);
      int var8 = var1.getInteger(2131230864);
      int var9 = var1.getInteger(2131230865);
      this.setPadding(var8, -1, var9, 0);
      this.setSingleLine((boolean)1);
      int var10 = var1.getInteger(2131230866);
      MarginLayoutParams var11 = new MarginLayoutParams(-1, var10);
      int var12 = var1.getInteger(2131230863);
      this.setMaxWidth(var12);
      int var13 = var1.getInteger(2131230869);
      int var14 = var1.getInteger(2131230867);
      int var15 = var1.getInteger(2131230870);
      int var16 = var1.getInteger(2131230868);
      var11.setMargins(var13, var14, var15, var16);
      LayoutParams var17 = new LayoutParams(var11);
      this.setLayoutParams(var17);
   }

   public BubbleData getBubbleData() {
      return this.mData;
   }

   public int getExpectedWidth() {
      Resources var1 = this.mContext.getResources();
      int var2 = this.getWidth();
      this.mExpectedWidth = var2;
      if(this.mExpectedWidth == 0) {
         int var3 = this.mExpectedWidth;
         TextPaint var4 = this.getPaint();
         String var5 = this.getText().toString();
         int var6 = (int)var4.measureText(var5);
         int var7 = var1.getInteger(2131230864);
         int var8 = var6 + var7;
         int var9 = var1.getInteger(2131230865);
         int var10 = var8 + var9;
         int var11 = var3 + var10;
         this.mExpectedWidth = var11;
      }

      int var12 = this.mExpectedWidth;
      int var13 = var1.getInteger(2131230863);
      if(var12 > var13) {
         int var14 = var1.getInteger(2131230863);
         this.mExpectedWidth = var14;
      }

      int var15 = this.mExpectedWidth;
      int var16 = var1.getInteger(2131230869);
      int var17 = var1.getInteger(2131230870);
      int var18 = var16 + var17;
      int var19 = var15 + var18;
      this.mExpectedWidth = var19;
      return this.mExpectedWidth;
   }

   public void refreshButton() {
      Resources var1 = this.mContext.getResources();
      int var2 = this.getExpectedWidth();
      int var3 = var1.getInteger(2131230869);
      int var4 = var2 - var3;
      int var5 = var1.getInteger(2131230870);
      int var6 = var4 - var5;
      this.mData.refreshContactInfo();
      String var7 = this.mData.getName();
      if(TextUtils.isEmpty(var7)) {
         String var8 = this.mData.getAddress();
         this.setText(var8);
      } else {
         this.setText(var7);
      }

      this.setMaxWidth(var6);
   }
}
