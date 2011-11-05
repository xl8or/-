package com.android.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.view.View;
import android.widget.ImageView;

public class ProviderPreference extends Preference {

   private String mAccountType;
   private Drawable mProviderIcon;
   private ImageView mProviderIconView;
   private CharSequence mProviderName;


   public ProviderPreference(Context var1, String var2, Drawable var3, CharSequence var4) {
      super(var1);
      this.mAccountType = var2;
      this.mProviderIcon = var3;
      this.mProviderName = var4;
      this.setLayoutResource(2130903045);
      this.setPersistent((boolean)0);
      CharSequence var5 = this.mProviderName;
      this.setTitle(var5);
   }

   public int compareTo(Preference var1) {
      int var2;
      if(!(var1 instanceof ProviderPreference)) {
         var2 = 1;
      } else {
         String var3 = this.mAccountType;
         int var4 = this.returnAccountTypeValue(var3);
         String var5 = ((ProviderPreference)var1).mAccountType;
         int var6 = this.returnAccountTypeValue(var5);
         if(var4 == 25 && var6 == 25) {
            String var7 = this.mAccountType;
            String var8 = ((ProviderPreference)var1).mAccountType;
            var2 = var7.compareTo(var8);
         } else if(var4 == var6) {
            var2 = 0;
         } else if(var4 > var6) {
            var2 = 1;
         } else {
            var2 = -1;
         }
      }

      return var2;
   }

   public String getAccountType() {
      return this.mAccountType;
   }

   protected void onBindView(View var1) {
      super.onBindView(var1);
      ImageView var2 = (ImageView)var1.findViewById(2131099648);
      this.mProviderIconView = var2;
      ImageView var3 = this.mProviderIconView;
      Drawable var4 = this.mProviderIcon;
      var3.setImageDrawable(var4);
      CharSequence var5 = this.mProviderName;
      this.setTitle(var5);
   }

   protected int returnAccountTypeValue(String var1) {
      byte var2;
      if(var1.equals("com.seven.Z7.work")) {
         var2 = 1;
      } else if(var1.equals("com.seven.Z7.gmail")) {
         var2 = 2;
      } else if(var1.equals("com.seven.Z7.msn")) {
         var2 = 3;
      } else if(var1.equals("com.seven.Z7.yahoo")) {
         var2 = 4;
      } else if(var1.equals("com.seven.Z7.aol")) {
         var2 = 5;
      } else if(var1.equals("com.sec.android.app.snsaccountfacebook.account_type")) {
         var2 = 6;
      } else if(var1.equals("com.sec.android.app.snsaccountmyspace.account_type")) {
         var2 = 8;
      } else if(var1.equals("com.sec.android.app.snsaccounttwitter.account_type")) {
         var2 = 7;
      } else if(var1.equals("com.sec.android.app.snsaccountbebo.account_type")) {
         var2 = 9;
      } else if(var1.equals("com.sec.android.app.snsaccountfriendster.account_type")) {
         var2 = 10;
      } else if(var1.equals("com.sec.android.app.snsaccountskyrock.account_type")) {
         var2 = 11;
      } else if(var1.equals("com.sec.android.app.snsaccountkaixin.account_type")) {
         var2 = 12;
      } else if(var1.equals("com.sec.android.app.snsaccountrenren.account_type")) {
         var2 = 13;
      } else if(var1.equals("com.sec.android.app.snsaccountlinkedin.account_type")) {
         var2 = 14;
      } else if(var1.equals("com.sec.android.app.snsaccountvz.account_type")) {
         var2 = 15;
      } else if(var1.equals("com.sec.android.app.snsaccountplurk.account_type")) {
         var2 = 16;
      } else if(var1.equals("com.sec.android.app.snsaccountlastfm.account_type")) {
         var2 = 17;
      } else if(var1.equals("com.sec.android.app.snsaccountorkut.account_type")) {
         var2 = 18;
      } else if(var1.equals("com.sec.android.app.snsaccountmixi.account_type")) {
         var2 = 19;
      } else if(var1.equals("com.sec.android.app.snsaccountme2day.account_type")) {
         var2 = 20;
      } else if(var1.equals("com.sec.android.app.snsaccountqzone.account_type")) {
         var2 = 21;
      } else if(var1.equals("com.seven.Z7")) {
         var2 = 22;
      } else if(var1.equals("com.android.exchange")) {
         var2 = 23;
      } else if(var1.equals("com.google")) {
         var2 = 24;
      } else {
         var2 = 25;
      }

      return var2;
   }
}
