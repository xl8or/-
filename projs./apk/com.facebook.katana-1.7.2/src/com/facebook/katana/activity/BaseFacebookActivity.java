package com.facebook.katana.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.activity.FacebookActivity;
import com.facebook.katana.activity.FacebookActivityDelegate;

public class BaseFacebookActivity extends Activity implements FacebookActivity {

   public static final String INTENT_WITHIN_TAB = "within_tab";
   private FacebookActivityDelegate mDelegate;


   public BaseFacebookActivity() {
      FacebookActivityDelegate var1 = new FacebookActivityDelegate(this);
      this.mDelegate = var1;
   }

   public boolean facebookOnBackPressed() {
      this.finish();
      return true;
   }

   public void finish() {
      this.mDelegate.finish();
      super.finish();
   }

   public long getActivityId() {
      return this.mDelegate.getActivityId();
   }

   protected String getTag() {
      return this.mDelegate.getTag();
   }

   protected void hideSearchButton() {
      this.mDelegate.hideSearchButton();
   }

   public boolean isOnTop() {
      return this.mDelegate.isOnTop();
   }

   protected void launchComposer(Uri var1, Bundle var2, Integer var3, long var4) {
      FacebookActivityDelegate var6 = this.mDelegate;
      var6.launchComposer(var1, var2, var3, var4);
   }

   protected void logStepDataReceived() {
      this.mDelegate.logStepDataReceived();
   }

   protected void logStepDataRequested() {
      this.mDelegate.logStepDataRequested();
   }

   public void onContentChanged() {
      super.onContentChanged();
      this.mDelegate.onContentChanged();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mDelegate.onCreate(var1);
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      Boolean var3 = this.mDelegate.onKeyDown(var1, var2);
      boolean var4;
      if(var3 != null) {
         var4 = var3.booleanValue();
      } else {
         var4 = super.onKeyDown(var1, var2);
      }

      return var4;
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      Boolean var3 = this.mDelegate.onKeyUp(var1, var2);
      boolean var4;
      if(var3 != null) {
         var4 = var3.booleanValue();
      } else {
         var4 = super.onKeyUp(var1, var2);
      }

      return var4;
   }

   protected void onPause() {
      this.mDelegate.onPause();
      super.onPause();
   }

   protected void onResume() {
      super.onResume();
      this.mDelegate.onResume();
   }

   public boolean onSearchRequested() {
      return this.mDelegate.onSearchRequested();
   }

   protected void setPrimaryActionFace(int var1, String var2) {
      this.mDelegate.setPrimaryActionFace(var1, var2);
   }

   protected void setPrimaryActionIcon(int var1) {
      this.mDelegate.setPrimaryActionIcon(var1);
   }

   public void setTransitioningToBackground(boolean var1) {
      this.mDelegate.setTransitioningToBackground(var1);
   }

   public void startActivity(Intent var1) {
      this.mDelegate.startActivity(var1);
      super.startActivity(var1);
   }

   public void startActivityForResult(Intent var1, int var2) {
      this.mDelegate.startActivityForResult(var1, var2);
      super.startActivityForResult(var1, var2);
   }

   public void titleBarClickHandler(View var1) {
      Intent var2 = IntentUriHandler.getIntentForUri(this, "fb://root");
      Intent var3 = var2.setFlags(67108864);
      this.startActivity(var2);
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      this.mDelegate.titleBarPrimaryActionClickHandler(var1);
   }

   public void titleBarSearchClickHandler(View var1) {
      this.mDelegate.titleBarSearchClickHandler(var1);
   }
}
