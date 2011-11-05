package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.finsky.utils.CorpusMetadata;

public class CustomRadioGroup extends RadioGroup {

   private int mStripTabUnderline;
   private int mStripTabUnderlineSelected;
   private Paint mTabUnderlinePaint;
   private Paint mTabUnderlineSelectedPaint;


   public CustomRadioGroup(Context var1) {
      super(var1);
      this.setWillNotDraw((boolean)0);
   }

   public CustomRadioGroup(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.setWillNotDraw((boolean)0);
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      if(this.mTabUnderlinePaint != null) {
         int var2 = this.getHeight();
         int var3 = this.mStripTabUnderline / 2;
         int var4 = var2 - var3;
         float var5 = (float)var4;
         float var6 = (float)this.getWidth();
         float var7 = (float)var4;
         Paint var8 = this.mTabUnderlinePaint;
         var1.drawLine(0.0F, var5, var6, var7, var8);
         int var9 = 0;

         while(true) {
            int var10 = this.getChildCount();
            if(var9 >= var10) {
               return;
            }

            RadioButton var11 = (RadioButton)this.getChildAt(var9);
            if(var11.isChecked()) {
               int var12 = this.getHeight();
               int var13 = this.mStripTabUnderlineSelected / 2;
               int var14 = var12 - var13;
               float var15 = (float)var11.getLeft();
               float var16 = (float)var14;
               float var17 = (float)var11.getRight();
               float var18 = (float)var14;
               Paint var19 = this.mTabUnderlineSelectedPaint;
               var1.drawLine(var15, var16, var17, var18, var19);
               return;
            }

            ++var9;
         }
      }
   }

   public void setBackendId(int var1) {
      Context var2 = this.getContext();
      int var3 = var2.getResources().getDimensionPixelSize(2131427394);
      this.mStripTabUnderline = var3;
      int var4 = var2.getResources().getDimensionPixelSize(2131427395);
      this.mStripTabUnderlineSelected = var4;
      Paint var5 = new Paint();
      this.mTabUnderlinePaint = var5;
      this.mTabUnderlinePaint.setAntiAlias((boolean)1);
      Paint var6 = this.mTabUnderlinePaint;
      float var7 = (float)this.mStripTabUnderline;
      var6.setStrokeWidth(var7);
      Paint var8 = new Paint();
      this.mTabUnderlineSelectedPaint = var8;
      this.mTabUnderlineSelectedPaint.setAntiAlias((boolean)1);
      Paint var9 = this.mTabUnderlineSelectedPaint;
      float var10 = (float)this.mStripTabUnderlineSelected;
      var9.setStrokeWidth(var10);
      int var11 = CorpusMetadata.getBackendForegroundColor(this.getContext(), var1);
      this.mTabUnderlinePaint.setColor(var11);
      this.mTabUnderlineSelectedPaint.setColor(var11);
   }
}
