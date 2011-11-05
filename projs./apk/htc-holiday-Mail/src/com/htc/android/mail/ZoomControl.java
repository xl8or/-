package com.htc.android.mail;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.animation.AlphaAnimation;
import android.widget.ZoomControls;

public class ZoomControl extends ZoomControls {

   private static final long ZOOM_CONTROLS_TIMEOUT = ViewConfiguration.getZoomControlsTimeout();
   Handler mPrivateHandler;
   Runnable mZoomControlRunnable;
   ZoomControls mZoomControls;


   public ZoomControl(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ZoomControl(Context var1, AttributeSet var2) {
      super(var1, var2);
      ZoomControl.1 var3 = new ZoomControl.1();
      this.mZoomControlRunnable = var3;
      Handler var4 = new Handler();
      this.mPrivateHandler = var4;
   }

   private void fade(int var1, float var2, float var3) {
      AlphaAnimation var4 = new AlphaAnimation(var2, var3);
      var4.setDuration(500L);
      this.startAnimation(var4);
      this.setVisibility(var1);
   }

   public void hide() {
      this.fade(8, 1.0F, 0.0F);
   }

   public void show() {
      this.fade(0, 0.0F, 1.0F);
   }

   public void showControlsFromMove() {
      if(this.getVisibility() == 0) {
         Handler var1 = this.mPrivateHandler;
         Runnable var2 = this.mZoomControlRunnable;
         var1.removeCallbacks(var2);
      } else {
         this.show();
      }

      Handler var3 = this.mPrivateHandler;
      Runnable var4 = this.mZoomControlRunnable;
      long var5 = ZOOM_CONTROLS_TIMEOUT;
      var3.postDelayed(var4, var5);
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         if(!ZoomControl.this.hasFocus()) {
            ZoomControl.this.hide();
         } else {
            Handler var1 = ZoomControl.this.mPrivateHandler;
            Runnable var2 = ZoomControl.this.mZoomControlRunnable;
            var1.removeCallbacks(var2);
            Handler var3 = ZoomControl.this.mPrivateHandler;
            Runnable var4 = ZoomControl.this.mZoomControlRunnable;
            long var5 = ZoomControl.ZOOM_CONTROLS_TIMEOUT;
            var3.postDelayed(var4, var5);
         }
      }
   }
}
