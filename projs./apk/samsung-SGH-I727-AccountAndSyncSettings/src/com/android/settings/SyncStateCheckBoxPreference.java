package com.android.settings;

import android.accounts.Account;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SyncStateCheckBoxPreference extends CheckBoxPreference {

   private Account mAccount;
   private String mAuthority;
   private boolean mFailed = 0;
   private boolean mIsActive = 0;
   private boolean mIsPending = 0;
   private boolean mOneTimeSyncMode = 0;


   public SyncStateCheckBoxPreference(Context var1, Account var2, String var3) {
      super(var1, (AttributeSet)null);
      this.mAccount = var2;
      this.mAuthority = var3;
      this.setWidgetLayoutResource(2130903044);
   }

   public SyncStateCheckBoxPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.setWidgetLayoutResource(2130903044);
      this.mAccount = null;
      this.mAuthority = null;
   }

   public Account getAccount() {
      return this.mAccount;
   }

   public String getAuthority() {
      return this.mAuthority;
   }

   public boolean isOneTimeSyncMode() {
      return this.mOneTimeSyncMode;
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
         SyncStateCheckBoxPreference.1 var7 = new SyncStateCheckBoxPreference.1(var6);
         var2.post(var7);
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
      View var13 = var1.findViewById(16908289);
      if(this.mOneTimeSyncMode) {
         var13.setVisibility(8);
         TextView var14 = (TextView)var1.findViewById(16908304);
         Context var15 = this.getContext();
         Object[] var16 = new Object[1];
         CharSequence var17 = this.getSummary();
         var16[0] = var17;
         String var18 = var15.getString(2131034125, var16);
         var14.setText(var18);
      } else {
         var13.setVisibility(0);
      }
   }

   protected void onClick() {
      if(!this.mOneTimeSyncMode) {
         super.onClick();
      }
   }

   public void setActive(boolean var1) {
      this.mIsActive = var1;
      this.notifyChanged();
   }

   public void setFailed(boolean var1) {
      this.mFailed = var1;
      this.notifyChanged();
   }

   public void setOneTimeSyncMode(boolean var1) {
      this.mOneTimeSyncMode = var1;
      this.notifyChanged();
   }

   public void setPending(boolean var1) {
      this.mIsPending = var1;
      this.notifyChanged();
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final AnimationDrawable val$anim;


      1(AnimationDrawable var2) {
         this.val$anim = var2;
      }

      public void run() {
         this.val$anim.start();
      }
   }
}
