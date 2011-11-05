package com.android.email.activity.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.android.email.VendorPolicyLoader;
import com.android.email.activity.setup.AccountSettingsUtils;
import com.android.email.activity.setup.AccountSetupExchange;
import com.android.email.activity.setup.AccountSetupIncoming;
import com.android.email.mail.Store;
import com.android.email.provider.EmailContent;
import com.sonyericsson.email.utils.AccountProvider;
import com.sonyericsson.email.utils.customization.AccountData;
import java.net.URI;
import java.net.URISyntaxException;

public class AccountSetupAccountType extends Activity implements OnClickListener {

   private static final String EXTRA_ACCOUNT = "account";
   private static final String EXTRA_ALLOW_AUTODISCOVER = "allowAutoDiscover";
   private static final String EXTRA_EAS_FLOW = "easFlow";
   private static final String EXTRA_EXIT_AFTER_SETUP = "exitAfterSetup";
   private static final String EXTRA_MAKE_DEFAULT = "makeDefault";
   private EmailContent.Account mAccount;
   private boolean mAllowAutoDiscover;
   private boolean mExitAfterSetup;
   private boolean mMakeDefault;


   public AccountSetupAccountType() {}

   public static void actionSelectAccountType(Activity var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      Intent var6 = new Intent(var0, AccountSetupAccountType.class);
      var6.putExtra("account", var1);
      var6.putExtra("makeDefault", var2);
      var6.putExtra("easFlow", var3);
      var6.putExtra("allowAutoDiscover", var4);
      var6.putExtra("exitAfterSetup", var5);
      var0.startActivity(var6);
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

   private void onExchange() {
      try {
         String var1 = this.mAccount.getStoreUri(this);
         URI var2 = new URI(var1);
         String var3 = var2.getUserInfo();
         String var4 = var2.getHost();
         int var5 = var2.getPort();
         URI var6 = new URI("eas+ssl+", var3, var4, var5, (String)null, (String)null, (String)null);
         EmailContent.Account var7 = this.mAccount;
         String var8 = var6.toString();
         var7.setStoreUri(this, var8);
         EmailContent.Account var9 = this.mAccount;
         String var10 = var6.toString();
         var9.setSenderUri(this, var10);
      } catch (URISyntaxException var22) {
         throw new Error(var22);
      }

      this.mAccount.setDeletePolicy(2);
      AccountData var11 = AccountProvider.getSettingsFromDefaultUX(this.getApplicationContext());
      int var12 = var11.getEasCheckIntervalSeconds();
      int[] var13 = var11.getEasCheckIntervalList();
      int var14 = AccountSettingsUtils.getCheckIntervalMinutes(var12, var13);
      this.mAccount.setSyncInterval(var14);
      this.mAccount.setSyncLookback(1);
      EmailContent.Account var15 = this.mAccount;
      boolean var16 = this.mMakeDefault;
      boolean var17 = this.mAllowAutoDiscover;
      boolean var18 = this.mExitAfterSetup;
      byte var20 = 1;
      AccountSetupExchange.actionIncomingSettings(this, var15, var16, (boolean)var20, var17, var18);
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
      } catch (URISyntaxException var13) {
         throw new Error(var13);
      }

      this.mAccount.setDeletePolicy(2);
      EmailContent.Account var9 = this.mAccount;
      boolean var10 = this.mMakeDefault;
      boolean var11 = this.mExitAfterSetup;
      AccountSetupIncoming.actionIncomingSettings(this, var9, var10, var11);
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
      } catch (URISyntaxException var13) {
         throw new Error(var13);
      }

      EmailContent.Account var9 = this.mAccount;
      boolean var10 = this.mMakeDefault;
      boolean var11 = this.mExitAfterSetup;
      AccountSetupIncoming.actionIncomingSettings(this, var9, var10, var11);
      this.finish();
   }

   boolean checkAccountInstanceLimit(Store.StoreInfo param1) {
      // $FF: Couldn't be decompiled
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131558410:
         this.onPop();
         return;
      case 2131558411:
         this.onImap();
         return;
      case 2131558412:
         this.onExchange();
         return;
      default:
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
      boolean var7 = var2.getBooleanExtra("exitAfterSetup", (boolean)0);
      this.mExitAfterSetup = var7;
      if(var5) {
         this.onExchange();
      } else {
         this.setContentView(2130903042);
         ((Button)this.findViewById(2131558410)).setOnClickListener(this);
         ((Button)this.findViewById(2131558411)).setOnClickListener(this);
         Button var8 = (Button)this.findViewById(2131558412);
         var8.setOnClickListener(this);
         if(this.isExchangeAvailable()) {
            var8.setVisibility(0);
            if(VendorPolicyLoader.getInstance(this).useAlternateExchangeStrings()) {
               var8.setText(2131165340);
            }
         }
      }
   }
}
