package com.android.settings;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;

public class AccountPreference extends Preference {

   public static final int SYNC_DISABLED = 1;
   public static final int SYNC_ENABLED = 0;
   public static final int SYNC_ERROR = 2;
   private static final String TAG = "AccountPreference";
   private Account mAccount;
   private CharSequence mAccountLabel;
   private ArrayList<String> mAuthorities;
   private Drawable mProviderIcon;
   private ImageView mProviderIconView;
   private int mStatus;
   private ImageView mSyncStatusIcon;


   public AccountPreference(Context var1, Account var2, Drawable var3, ArrayList<String> var4, CharSequence var5) {
      super(var1);
      this.mAccount = var2;
      this.mAuthorities = var4;
      this.mProviderIcon = var3;
      this.mAccountLabel = var5;
      this.setLayoutResource(2130903040);
      String var6 = this.mAccount.name;
      this.setTitle(var6);
      this.setSummary("");
      Intent var7 = new Intent("android.settings.ACCOUNT_SYNC_SETTINGS");
      Account var8 = this.mAccount;
      var7.putExtra("account", var8);
      this.setIntent(var7);
      this.setPersistent((boolean)0);
      CharSequence var10 = this.mAccountLabel;
      this.setSummary(var10);
   }

   private int getSyncStatusIcon(int var1) {
      int var2;
      switch(var1) {
      case 0:
         var2 = 2130837513;
         break;
      case 1:
         var2 = 2130837514;
         break;
      case 2:
         var2 = 2130837515;
         break;
      default:
         var2 = 2130837515;
         String var3 = "Unknown sync status: " + var1;
         int var4 = Log.e("AccountPreference", var3);
      }

      return var2;
   }

   public int compareTo(Preference var1) {
      int var2;
      if(!(var1 instanceof AccountPreference)) {
         var2 = 1;
      } else {
         String var3 = this.mAccount.name;
         String var4 = ((AccountPreference)var1).mAccount.name;
         var2 = var3.compareTo(var4);
      }

      return var2;
   }

   public Account getAccount() {
      return this.mAccount;
   }

   public ArrayList<String> getAuthorities() {
      return this.mAuthorities;
   }

   protected void onBindView(View var1) {
      super.onBindView(var1);
      CharSequence var2 = this.mAccountLabel;
      this.setSummary(var2);
      ImageView var3 = (ImageView)var1.findViewById(2131099648);
      this.mProviderIconView = var3;
      ImageView var4 = this.mProviderIconView;
      Drawable var5 = this.mProviderIcon;
      var4.setImageDrawable(var5);
      ImageView var6 = (ImageView)var1.findViewById(2131099649);
      this.mSyncStatusIcon = var6;
      ImageView var7 = this.mSyncStatusIcon;
      int var8 = this.mStatus;
      int var9 = this.getSyncStatusIcon(var8);
      var7.setImageResource(var9);
   }

   public void setProviderIcon(Drawable var1) {
      this.mProviderIcon = var1;
      if(this.mProviderIconView != null) {
         this.mProviderIconView.setImageDrawable(var1);
      }
   }

   public void setSyncStatus(int var1) {
      this.mStatus = var1;
      if(this.mSyncStatusIcon != null) {
         ImageView var2 = this.mSyncStatusIcon;
         int var3 = this.getSyncStatusIcon(var1);
         var2.setImageResource(var3);
      }
   }
}
