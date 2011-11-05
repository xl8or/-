package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsTextLayout extends LinearLayout {

   private DetailsTextLayout.MetricsListener mMetricsListener;
   private int mPrevWidth;


   public DetailsTextLayout(Context var1) {
      super(var1);
   }

   public DetailsTextLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      if(this.mPrevWidth != var3) {
         TextView var4 = (TextView)this.findViewById(2131755188);
         var4.measure(var1, 0);
         int var5 = var4.getMeasuredHeight();
         var4.setMaxLines(6);
         var4.measure(var1, 0);
         int var6 = var4.getMeasuredHeight();
         if(this.mMetricsListener != null) {
            this.mMetricsListener.metricsAvailable(var5, var6);
         }

         this.mPrevWidth = var3;
      }

      super.onMeasure(var1, var2);
   }

   public void setMetricsListener(DetailsTextLayout.MetricsListener var1) {
      this.mMetricsListener = var1;
   }

   public interface MetricsListener {

      void metricsAvailable(int var1, int var2);
   }
}
