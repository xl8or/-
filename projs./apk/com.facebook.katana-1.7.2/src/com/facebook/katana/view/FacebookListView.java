package com.facebook.katana.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;
import com.facebook.katana.service.method.PerfLogging;

public class FacebookListView extends ListView {

   private static PerfLogging.Step INVALID_DRAW_STEP = null;
   public String TAG = "FacebookListView";
   public long activityId;
   private PerfLogging.Step mNextDrawStep;
   public String reqId;
   public long startTime;


   public FacebookListView(Context var1) {
      super(var1);
      PerfLogging.Step var2 = INVALID_DRAW_STEP;
      this.mNextDrawStep = var2;
   }

   public FacebookListView(Context var1, AttributeSet var2) {
      super(var1, var2);
      PerfLogging.Step var3 = INVALID_DRAW_STEP;
      this.mNextDrawStep = var3;
   }

   public FacebookListView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      PerfLogging.Step var4 = INVALID_DRAW_STEP;
      this.mNextDrawStep = var4;
   }

   protected void onDraw(Canvas var1) {
      PerfLogging.Step var2 = this.mNextDrawStep;
      PerfLogging.Step var3 = INVALID_DRAW_STEP;
      if(var2 != var3) {
         Context var4 = this.getContext();
         PerfLogging.Step var5 = this.mNextDrawStep;
         String var6 = this.TAG;
         long var7 = this.activityId;
         PerfLogging.logStep(var4, var5, var6, var7);
         PerfLogging.Step var9 = INVALID_DRAW_STEP;
         this.mNextDrawStep = var9;
      }

      super.onDraw(var1);
   }

   public void setNextDrawStep(PerfLogging.Step var1) {
      PerfLogging.Step var2 = PerfLogging.Step.UI_DRAWN_STALE;
      if(var1 != var2) {
         PerfLogging.Step var3 = PerfLogging.Step.UI_DRAWN_FRESH;
         if(var1 != var3) {
            return;
         }
      }

      this.mNextDrawStep = var1;
   }
}
