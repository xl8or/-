package com.htc.android.mail.easclient;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.htc.preference.HtcCheckBoxPreference;

public class SyncStateCheckBoxPreference extends HtcCheckBoxPreference {

   private boolean mFailed = 0;
   private Handler mHandler;
   private boolean mIsActive = 0;
   private boolean mIsPending = 0;


   public SyncStateCheckBoxPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      Handler var3 = new Handler();
      this.mHandler = var3;
      this.setWidgetLayoutResource(2130903081);
   }

   public boolean isActive() {
      return this.mIsActive;
   }

   public void onBindView(View var1) {
      super.onBindView(var1);
      ImageView var2 = (ImageView)var1.findViewById(16842753);
      View var3 = var1.findViewById(16842754);
      View var4 = var1.findViewById(16842752);
      byte var5;
      if(this.mIsActive) {
         var5 = 0;
      } else {
         var5 = 8;
      }

      var2.setVisibility(var5);
      AnimationDrawable var6 = (AnimationDrawable)var2.getDrawable();
      boolean var9;
      byte var10;
      if(this.mIsActive) {
         SyncStateCheckBoxPreference.StartAnimation var7 = new SyncStateCheckBoxPreference.StartAnimation(var6);
         this.mHandler.postDelayed(var7, 100L);
         var9 = false;
         var10 = 0;
      } else {
         var6.stop();
         if(this.mIsPending) {
            var9 = true;
            var10 = 0;
         } else {
            var9 = false;
            var10 = this.mFailed;
         }
      }

      byte var11;
      if(var10 != 0) {
         var11 = 0;
      } else {
         var11 = 8;
      }

      var4.setVisibility(var11);
      byte var12;
      if(var9 && !this.mIsActive) {
         var12 = 0;
      } else {
         var12 = 8;
      }

      var3.setVisibility(var12);
   }

   public void setActive(boolean var1) {
      this.mIsActive = var1;
      this.notifyChanged();
   }

   public void setFailed(boolean var1) {
      this.mFailed = var1;
      this.notifyChanged();
   }

   public void setPending(boolean var1) {
      this.mIsPending = var1;
      this.notifyChanged();
   }

   class StartAnimation implements Runnable {

      AnimationDrawable mAnimation;


      StartAnimation(AnimationDrawable var2) {
         this.mAnimation = var2;
      }

      public void run() {
         this.mAnimation.start();
      }
   }
}
