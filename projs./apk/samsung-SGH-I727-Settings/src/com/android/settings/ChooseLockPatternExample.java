package com.android.settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.android.settings.ChooseLockPattern;

public class ChooseLockPatternExample extends Activity implements OnClickListener {

   private static final long START_DELAY = 1000L;
   protected static final String TAG = "Settings";
   private AnimationDrawable mAnimation;
   private Handler mHandler;
   private View mImageView;
   private View mNextButton;
   private Runnable mRunnable;
   private View mSkipButton;


   public ChooseLockPatternExample() {
      Handler var1 = new Handler();
      this.mHandler = var1;
      ChooseLockPatternExample.1 var2 = new ChooseLockPatternExample.1();
      this.mRunnable = var2;
   }

   private void initViews() {
      View var1 = this.findViewById(2131427369);
      this.mNextButton = var1;
      this.mNextButton.setOnClickListener(this);
      View var2 = this.findViewById(2131427370);
      this.mSkipButton = var2;
      this.mSkipButton.setOnClickListener(this);
      ImageView var3 = (ImageView)this.findViewById(2131427381);
      this.mImageView = var3;
      this.mImageView.setBackgroundResource(2130837642);
      this.mImageView.setOnClickListener(this);
      AnimationDrawable var4 = (AnimationDrawable)this.mImageView.getBackground();
      this.mAnimation = var4;
   }

   public void onClick(View var1) {
      View var2 = this.mSkipButton;
      if(var1.equals(var2)) {
         this.setResult(1);
         this.finish();
      } else {
         View var3 = this.mNextButton;
         if(var1.equals(var3)) {
            AnimationDrawable var4 = this.mAnimation;
            this.stopAnimation(var4);
            Intent var5 = new Intent(this, ChooseLockPattern.class);
            Intent var6 = var5.addFlags(33554432);
            Intent var7 = var5.putExtra("confirm_credentials", (boolean)0);
            this.startActivity(var5);
            this.finish();
         }
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903062);
      this.initViews();
   }

   protected void onPause() {
      super.onPause();
      AnimationDrawable var1 = this.mAnimation;
      this.stopAnimation(var1);
   }

   protected void onResume() {
      super.onResume();
      Handler var1 = this.mHandler;
      Runnable var2 = this.mRunnable;
      var1.postDelayed(var2, 1000L);
   }

   protected void startAnimation(AnimationDrawable var1) {
      if(var1 != null) {
         if(!var1.isRunning()) {
            var1.run();
         }
      }
   }

   protected void stopAnimation(AnimationDrawable var1) {
      if(var1 != null) {
         if(var1.isRunning()) {
            var1.stop();
         }
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         ChooseLockPatternExample var1 = ChooseLockPatternExample.this;
         AnimationDrawable var2 = ChooseLockPatternExample.this.mAnimation;
         var1.startAnimation(var2);
      }
   }
}
