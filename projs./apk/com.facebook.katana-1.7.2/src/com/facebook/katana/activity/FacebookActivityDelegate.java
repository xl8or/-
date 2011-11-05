package com.facebook.katana.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import com.facebook.katana.ComposerActivity;
import com.facebook.katana.HomeActivity;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.activity.FacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.service.FacebookService;
import com.facebook.katana.service.method.PerfLogging;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.EclairKeyHandler;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FacebookActivityDelegate implements FacebookActivity {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected static Map<Class<?>, String> TAGS;
   private Activity mActivity;
   protected long mActivityId;
   private boolean mOnTop;
   public boolean mTransitioningToBackground = 0;


   static {
      byte var0;
      if(!FacebookActivityDelegate.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      TAGS = Collections.synchronizedMap(new HashMap());
   }

   public FacebookActivityDelegate(Activity var1) {
      this.mActivity = var1;
      if(!$assertionsDisabled) {
         if(!(var1 instanceof FacebookActivity)) {
            throw new AssertionError();
         }
      }
   }

   public boolean facebookOnBackPressed() {
      this.mActivity.finish();
      return true;
   }

   public void finish() {
      if(!(this.mActivity instanceof HomeActivity)) {
         this.mTransitioningToBackground = (boolean)0;
      }
   }

   public long getActivityId() {
      return this.mActivityId;
   }

   protected String getTag() {
      Intent var1 = this.mActivity.getIntent();
      Class var2 = this.mActivity.getClass();
      String var3 = (String)TAGS.get(var2);
      if(var3 == null) {
         String var6;
         if(var1.getBooleanExtra("within_tab", (boolean)0)) {
            StringBuilder var4 = new StringBuilder();
            String var5 = var1.getStringExtra("extra_parent_tag");
            var6 = var4.append(var5).append("|").toString();
         } else {
            var6 = "";
         }

         StringBuilder var7 = (new StringBuilder()).append(var6);
         String var8 = Utils.getClassName(var2);
         var3 = var7.append(var8).toString();
         TAGS.put(var2, var3);
      }

      return var3;
   }

   protected void hideSearchButton() {
      ((ImageButton)this.mActivity.findViewById(2131624040)).setVisibility(8);
   }

   public boolean isOnTop() {
      return this.mOnTop;
   }

   protected void launchComposer(Uri var1, Bundle var2, Integer var3, long var4) {
      Activity var6 = this.mActivity;
      Intent var7 = new Intent(var6, ComposerActivity.class);
      if(var1 != null) {
         var7.setData(var1);
      }

      if(var2 != null) {
         var7.putExtras(var2);
      }

      if(var4 != 65535L) {
         long var10 = AppSession.getActiveSession(this.mActivity, (boolean)0).getSessionInfo().userId;
         if(var4 != var10) {
            var7.putExtra("extra_fixed_audience_id", var4);
         }
      }

      if(var3 != null) {
         Activity var13 = this.mActivity;
         int var14 = var3.intValue();
         var13.startActivityForResult(var7, var14);
      } else {
         this.mActivity.startActivity(var7);
      }
   }

   protected void logStepDataReceived() {
      Activity var1 = this.mActivity;
      PerfLogging.Step var2 = PerfLogging.Step.DATA_RECEIVED;
      String var3 = this.getTag();
      long var4 = this.mActivityId;
      PerfLogging.logStep(var1, var2, var3, var4);
   }

   protected void logStepDataRequested() {
      Activity var1 = this.mActivity;
      PerfLogging.Step var2 = PerfLogging.Step.DATA_REQUESTED;
      String var3 = this.getTag();
      long var4 = this.mActivityId;
      PerfLogging.logStep(var1, var2, var3, var4);
   }

   public void onContentChanged() {
      FacebookActivityDelegate.1 var1 = new FacebookActivityDelegate.1();
      ImageButton var2 = (ImageButton)this.mActivity.findViewById(2131623998);
      Button var3 = (Button)this.mActivity.findViewById(2131623997);
      if(var2 != null) {
         var2.setOnClickListener(var1);
      }

      if(var3 != null) {
         var3.setOnClickListener(var1);
      }

      ImageButton var4 = (ImageButton)this.mActivity.findViewById(2131624260);
      if(var4 != null) {
         FacebookActivityDelegate.2 var5 = new FacebookActivityDelegate.2();
         var4.setOnClickListener(var5);
      }

      ImageButton var6 = (ImageButton)this.mActivity.findViewById(2131624040);
      if(var6 != null) {
         FacebookActivityDelegate.3 var7 = new FacebookActivityDelegate.3();
         var6.setOnClickListener(var7);
      }
   }

   protected void onCreate(Bundle var1) {
      long var2 = (long)Utils.RNG.nextInt();
      this.mActivityId = var2;
      Activity var4 = this.mActivity;
      PerfLogging.Step var5 = PerfLogging.Step.ONCREATE;
      String var6 = this.getTag();
      long var7 = this.mActivityId;
      PerfLogging.logStep(var4, var5, var6, var7);
   }

   public Boolean onKeyDown(int var1, KeyEvent var2) {
      Boolean var3;
      if(var1 == 4) {
         if(PlatformUtils.isEclairOrLater()) {
            if(EclairKeyHandler.onKeyDown(var2)) {
               var3 = Boolean.valueOf((boolean)1);
               return var3;
            }
         } else if(((FacebookActivity)this.mActivity).facebookOnBackPressed()) {
            var3 = Boolean.valueOf((boolean)1);
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public Boolean onKeyUp(int var1, KeyEvent var2) {
      Boolean var3;
      if(var1 == 4 && PlatformUtils.isEclairOrLater() && EclairKeyHandler.onKeyUp(var2) && ((FacebookActivity)this.mActivity).facebookOnBackPressed()) {
         var3 = Boolean.valueOf((boolean)1);
      } else {
         var3 = null;
      }

      return var3;
   }

   protected void onPause() {
      boolean var1 = this.mActivity.getIntent().getBooleanExtra("within_tab", (boolean)0);
      boolean var2 = this.mTransitioningToBackground;
      Activity var3 = this.mActivity;
      FacebookService.pause(var1, var2, var3);
      this.mOnTop = (boolean)0;
   }

   protected void onResume() {
      boolean var1 = this.mActivity.getIntent().getBooleanExtra("within_tab", (boolean)0);
      Activity var2 = this.mActivity;
      boolean var3 = FacebookService.resume(var1, var2);
      this.mTransitioningToBackground = var3;
      Activity var4 = this.mActivity;
      PerfLogging.Step var5 = PerfLogging.Step.ONRESUME;
      String var6 = this.getTag();
      long var7 = this.mActivityId;
      PerfLogging.logStep(var4, var5, var6, var7);
      Activity var9 = this.mActivity;
      String var10 = this.getTag();
      long var11 = this.mActivityId;
      PerfLogging.logPageView(var9, var10, var11);
      this.mOnTop = (boolean)1;
   }

   public boolean onSearchRequested() {
      ApplicationUtils.OpenSearch(this.mActivity);
      return true;
   }

   protected void setListLoading(boolean var1) {
      byte var2;
      if(var1) {
         var2 = 0;
      } else {
         var2 = 8;
      }

      byte var3;
      if(var1) {
         var3 = 8;
      } else {
         var3 = 0;
      }

      this.mActivity.findViewById(2131624023).setVisibility(var2);
      this.mActivity.findViewById(2131624022).setVisibility(var3);
   }

   protected void setPrimaryActionFace(int var1, String var2) {
      ImageButton var3 = (ImageButton)this.mActivity.findViewById(2131623998);
      Button var4 = (Button)this.mActivity.findViewById(2131623997);
      if(var1 < 0) {
         var3.setVisibility(8);
      } else {
         var3.setImageResource(var1);
         var3.setVisibility(0);
      }

      if(var2 == null) {
         var4.setVisibility(8);
      } else {
         var4.setText(var2);
         var4.setVisibility(0);
      }
   }

   protected void setPrimaryActionIcon(int var1) {
      this.setPrimaryActionFace(var1, (String)null);
   }

   public void setTransitioningToBackground(boolean var1) {
      this.mTransitioningToBackground = var1;
   }

   public void startActivity(Intent var1) {
      boolean var2 = FacebookService.processActivityChange(var1);
      this.mTransitioningToBackground = var2;
   }

   public void startActivityForResult(Intent var1, int var2) {
      boolean var3 = FacebookService.processActivityChange(var1);
      this.mTransitioningToBackground = var3;
   }

   public void titleBarClickHandler(View var1) {
      Intent var2 = IntentUriHandler.getIntentForUri(this.mActivity, "fb://root");
      Intent var3 = var2.setFlags(67108864);
      this.mActivity.startActivity(var2);
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      throw new IllegalStateException("titleBarPrimaryActionClickHandler has no operation.");
   }

   public void titleBarSearchClickHandler(View var1) {
      boolean var2 = IntentUriHandler.handleUri(this.mActivity, "fb://search");
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         ((FacebookActivity)FacebookActivityDelegate.this.mActivity).titleBarClickHandler(var1);
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         ((FacebookActivity)FacebookActivityDelegate.this.mActivity).titleBarSearchClickHandler(var1);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         ((FacebookActivity)FacebookActivityDelegate.this.mActivity).titleBarPrimaryActionClickHandler(var1);
      }
   }
}
