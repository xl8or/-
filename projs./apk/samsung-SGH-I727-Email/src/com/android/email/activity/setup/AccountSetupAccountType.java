package com.android.email.activity.setup;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.email.VendorPolicyLoader;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.activity.setup.AccountSetupExchange;
import com.android.email.activity.setup.AccountSetupIncoming;
import com.android.email.mail.Store;
import com.android.email.provider.EmailContent;
import com.android.exchange.SyncScheduleData;
import com.digc.seven.SevenSyncProvider;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupAccountType extends Activity implements OnClickListener {

   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_ALLOW_AUTODISCOVER = "allowAutoDiscover";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private EmailContent.Account mAccount;
   private boolean mAllowAutoDiscover;
   private int mCurrentOrientation;
   private LinearLayout mHorizontalLayout;
   private boolean mMakeDefault;
   private LinearLayout mVerticalLayout;


   public AccountSetupAccountType() {}

   public static void actionSelectAccountType(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4) {
      Intent var5 = new Intent(var0, AccountSetupAccountType.class);
      var5.putExtra("account", var1);
      var5.putExtra("makeDefault", var2);
      var5.putExtra("easFlow", var3);
      var5.putExtra("allowAutoDiscover", var4);
      var0.startActivity(var5);
   }

   private boolean isExchangeAvailable() {
      boolean var8;
      label19: {
         boolean var7;
         try {
            String var1 = this.mAccount.getStoreUri(this);
            URI var2 = new URI(var1);
            String var3 = var2.getUserInfo();
            String var4 = var2.getHost();
            int var5 = var2.getPort();
            Store.StoreInfo var6 = Store.StoreInfo.getStoreInfo((new URI("eas", var3, var4, var5, (String)null, (String)null, (String)null)).toString(), this);
            if(var6 == null) {
               break label19;
            }

            var7 = this.checkAccountInstanceLimit(var6);
         } catch (URISyntaxException var10) {
            var8 = false;
            return var8;
         }

         if(var7) {
            var8 = true;
            return var8;
         }
      }

      var8 = false;
      return var8;
   }

   private void onExchange(boolean var1) {
      try {
         String var2 = this.mAccount.getStoreUri(this);
         URI var3 = new URI(var2);
         String var4 = var3.getUserInfo();
         String var5 = var3.getHost();
         int var6 = var3.getPort();
         URI var7 = new URI("eas+ssl+", var4, var5, var6, (String)null, (String)null, (String)null);
         EmailContent.Account var8 = this.mAccount;
         String var9 = var7.toString();
         var8.setStoreUri(this, var9);
         EmailContent.Account var10 = this.mAccount;
         String var11 = var7.toString();
         var10.setSenderUri(this, var11);
      } catch (URISyntaxException var20) {
         throw new Error(var20);
      }

      this.mAccount.setDeletePolicy(2);
      this.mAccount.setSyncInterval(-1);
      this.mAccount.setSyncLookback(1);
      byte var12 = -1;
      byte var13 = -1;
      SyncScheduleData var14 = new SyncScheduleData(480, 1020, 62, var12, var13, 0);
      this.mAccount.setSyncScheduleData(var14);
      boolean var15 = this.mAccount.setCalendarSyncLookback(4);
      EmailContent.Account var16 = this.mAccount;
      boolean var17 = this.mMakeDefault;
      boolean var18 = this.mAllowAutoDiscover;
      AccountSetupExchange.actionIncomingSettings(this, var16, var17, var1, var18);
      this.finish();
   }

   private void onImap() {
      try {
         String var1 = this.mAccount.getStoreUri(this);
         URI var2 = new URI(var1);
         String var3 = var2.getUserInfo();
         String var4 = var2.getHost();
         int var5 = var2.getPort();
         URI var6 = new URI("imap", var3, var4, var5, (String)null, (String)null, (String)null);
         EmailContent.Account var7 = this.mAccount;
         String var8 = var6.toString();
         var7.setStoreUri(this, var8);
      } catch (URISyntaxException var12) {
         throw new Error(var12);
      }

      this.mAccount.setDeletePolicy(2);
      EmailContent.Account var9 = this.mAccount;
      boolean var10 = this.mMakeDefault;
      AccountSetupIncoming.actionIncomingSettings(this, var9, var10);
      this.finish();
   }

   private void onPop() {
      try {
         String var1 = this.mAccount.getStoreUri(this);
         URI var2 = new URI(var1);
         String var3 = var2.getUserInfo();
         String var4 = var2.getHost();
         int var5 = var2.getPort();
         URI var6 = new URI("pop3", var3, var4, var5, (String)null, (String)null, (String)null);
         EmailContent.Account var7 = this.mAccount;
         String var8 = var6.toString();
         var7.setStoreUri(this, var8);
      } catch (URISyntaxException var12) {
         throw new Error(var12);
      }

      EmailContent.Account var9 = this.mAccount;
      boolean var10 = this.mMakeDefault;
      AccountSetupIncoming.actionIncomingSettings(this, var9, var10);
      this.finish();
   }

   boolean checkAccountInstanceLimit(Store.StoreInfo param1) {
      // $FF: Couldn't be decompiled
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131361826:
      case 2131361830:
         this.onPop();
         return;
      case 2131361827:
      case 2131361831:
         this.onExchange((boolean)0);
         return;
      case 2131361828:
      case 2131361832:
         this.onImap();
         return;
      case 2131361829:
      default:
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      int var2 = this.getResources().getConfiguration().orientation;
      this.mCurrentOrientation = var2;
      if(this.mCurrentOrientation == 2) {
         this.mVerticalLayout.setVisibility(8);
         this.mHorizontalLayout.setVisibility(0);
      } else {
         this.mHorizontalLayout.setVisibility(8);
         this.mVerticalLayout.setVisibility(0);
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      EmailContent.Account var3 = (EmailContent.Account)var2.getParcelableExtra("account");
      this.mAccount = var3;
      boolean var4 = var2.getBooleanExtra("makeDefault", (boolean)0);
      this.mMakeDefault = var4;
      boolean var5 = var2.getBooleanExtra("easFlow", (boolean)0);
      boolean var6 = var2.getBooleanExtra("allowAutoDiscover", (boolean)1);
      this.mAllowAutoDiscover = var6;
      if(var5) {
         this.onExchange((boolean)1);
      } else {
         this.setContentView(2130903046);
         LinearLayout var7 = (LinearLayout)this.findViewById(2131361825);
         this.mVerticalLayout = var7;
         this.mVerticalLayout.setOnClickListener(this);
         LinearLayout var8 = (LinearLayout)this.findViewById(2131361829);
         this.mHorizontalLayout = var8;
         this.mHorizontalLayout.setOnClickListener(this);
         ((Button)this.findViewById(2131361830)).setOnClickListener(this);
         ((Button)this.findViewById(2131361832)).setOnClickListener(this);
         ((Button)this.findViewById(2131361826)).setOnClickListener(this);
         ((Button)this.findViewById(2131361828)).setOnClickListener(this);
         ((Button)this.findViewById(2131361831)).setOnClickListener(this);
         ((Button)this.findViewById(2131361827)).setOnClickListener(this);
         if(this.isExchangeAvailable()) {
            ((Button)this.findViewById(2131361831)).setVisibility(0);
            ((Button)this.findViewById(2131361827)).setVisibility(0);
            if(VendorPolicyLoader.getInstance(this).useAlternateExchangeStrings()) {
               ((Button)this.findViewById(2131361831)).setText(2131167057);
               ((Button)this.findViewById(2131361827)).setText(2131167057);
            }
         }

         int var9 = this.getResources().getConfiguration().orientation;
         this.mCurrentOrientation = var9;
         if(this.mCurrentOrientation == 2) {
            this.mVerticalLayout.setVisibility(8);
            this.mHorizontalLayout.setVisibility(0);
         } else {
            this.mHorizontalLayout.setVisibility(8);
            this.mVerticalLayout.setVisibility(0);
         }
      }
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      if(AccountSetupCustomer.getInstance().getEmailType() == 1) {
         if(SevenSyncProvider.checkSevenApkVer(this)) {
            ;
         }
      }
   }

   public boolean onSearchRequested() {
      return false;
   }
}
