package com.sonyericsson.email.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.android.email.activity.setup.AccountSetupBasics;
import com.sonyericsson.email.ui.BrandedAccountsListAdapter;
import com.sonyericsson.email.utils.customization.AccountData;
import com.sonyericsson.email.utils.customization.Customization;
import com.sonyericsson.email.utils.customization.CustomizationFactory;

public class BrandedAccountsList extends ListActivity implements OnItemClickListener {

   private static final String EXTRA_EXIT = "exit";
   private static final String EXTRA_EXIT_AFTER_SETUP = "exitAfterSetup";
   private BrandedAccountsListAdapter mAdapter;
   private boolean mExitAfterSetup;


   public BrandedAccountsList() {}

   public static void actionExit(Activity var0) {
      Intent var1 = new Intent(var0, BrandedAccountsList.class);
      Intent var2 = var1.putExtra("exit", (boolean)1);
      Intent var3 = var1.setFlags(67108864);
      var0.startActivity(var1);
   }

   public static void actionSetupAccount(Activity var0) {
      actionSetupAccount(var0, (boolean)0);
   }

   public static void actionSetupAccount(Activity var0, boolean var1) {
      Intent var2 = new Intent(var0, BrandedAccountsList.class);
      var2.putExtra("exitAfterSetup", var1);
      Intent var4 = var2.setFlags(67108864);
      var0.startActivity(var2);
   }

   public static boolean checkBrandAccountSettings(Context var0) {
      boolean var1;
      if(!CustomizationFactory.getInstance().getCustomization(var0).hasBrandedAccountData(var0) && !CustomizationFactory.getInstance().getCustomization(var0).hasPreconfAccountData(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(this.getIntent().getBooleanExtra("exit", (boolean)0)) {
         this.finish();
      } else {
         this.setContentView(2130903054);
         this.getListView().setOnItemClickListener(this);
         boolean var2 = this.getIntent().getBooleanExtra("exitAfterSetup", (boolean)0);
         this.mExitAfterSetup = var2;
         BrandedAccountsListAdapter var3 = new BrandedAccountsListAdapter(this);
         this.mAdapter = var3;
         BrandedAccountsListAdapter var4 = this.mAdapter;
         this.setListAdapter(var4);
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      int var6 = this.mAdapter.getCount();
      Customization var7 = CustomizationFactory.getInstance().getCustomization(this);
      if(var4 == 0L && var7.hasPreconfAccountData(this)) {
         AccountData var8 = var7.getPreconfAccountData(this);
         if(var8 != null) {
            BrandedAccountsListAdapter var9 = this.mAdapter;
            int var10 = (int)var4;
            Bitmap var11 = var9.getBitmap(var10);
            String var12 = var8.getEmailAddress();
            String var13 = var8.getIncomingPassword();
            boolean var14 = this.mExitAfterSetup;
            AccountSetupBasics.actionNewAccountWithCredentials(this, var11, var12, var13, (boolean)0, var14, (boolean)1);
         }
      } else {
         long var15 = (long)(var6 - 2);
         if(var4 == var15) {
            boolean var17 = this.mExitAfterSetup;
            Intent var18 = AccountSetupBasics.actionSetupExchangeIntent(this, (Bitmap)null, var17);
            this.startActivity(var18);
         } else {
            long var19 = (long)(var6 - 1);
            if(var4 == var19) {
               boolean var21 = this.mExitAfterSetup;
               AccountSetupBasics.actionNewAccount(this, var21);
            } else {
               BrandedAccountsListAdapter var22 = this.mAdapter;
               int var23 = (int)var4;
               Bitmap var24 = var22.getBitmap(var23);
               BrandedAccountsListAdapter var25 = this.mAdapter;
               int var26 = (int)var4;
               String var27 = var25.getDomain(var26);
               boolean var28 = this.mExitAfterSetup;
               AccountSetupBasics.actionNewAccountWithCredentials(this, var24, var27, (String)null, (boolean)0, var28, (boolean)0);
            }
         }
      }
   }
}
