package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;
import com.facebook.katana.UserTask;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.EclairKeyHandler;
import com.facebook.katana.util.PlatformUtils;

public class SyncContactsSetupActivity extends BaseFacebookActivity implements OnClickListener {

   private boolean mAddAccountMode;
   private int mCheckedId;


   public SyncContactsSetupActivity() {}

   private void checkRadioButton(int var1) {
      RadioButton var2 = (RadioButton)this.findViewById(2131624247);
      byte var3;
      if(var1 == 2131624247) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      var2.setChecked((boolean)var3);
      RadioButton var4 = (RadioButton)this.findViewById(2131624250);
      byte var5;
      if(var1 == 2131624250) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      var4.setChecked((boolean)var5);
      RadioButton var6 = (RadioButton)this.findViewById(2131624253);
      byte var7;
      if(var1 == 2131624253) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var6.setChecked((boolean)var7);
      this.mCheckedId = var1;
   }

   private boolean onBackKeyPressed() {
      return true;
   }

   private void saveSettings(AppSession var1) {
      byte var2 = 0;
      byte var3 = 0;
      switch(this.mCheckedId) {
      case 2131624247:
         var2 = 1;
         var3 = 1;
         break;
      case 2131624250:
         var2 = 1;
      case 2131624253:
      }

      (new SyncContactsSetupActivity.AddAccountUserTask(var1, (boolean)var2, (boolean)var3)).execute();
   }

   private void setupFatTitleBar() {
      ((TextView)this.findViewById(2131624049)).setText(2131362282);
      ((TextView)this.findViewById(2131624050)).setText(2131362281);
   }

   private void startDefaultActivity() {
      Intent var1 = (Intent)this.getIntent().getParcelableExtra("com.facebook.katana.continuation_intent");
      ApplicationUtils.startDefaultActivity(this, var1);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131624245:
         UserValuesManager.setContactsSyncSetupDisplayed(this, (boolean)1);
         if(PlatformUtils.platformStorageSupported(this)) {
            AppSession var2 = AppSession.getActiveSession(this, (boolean)0);
            if(var2 != null) {
               this.saveSettings(var2);
            }

            if(!this.mAddAccountMode) {
               this.startDefaultActivity();
            }
         } else {
            this.startDefaultActivity();
         }

         this.finish();
         return;
      case 2131624246:
         this.checkRadioButton(2131624247);
         return;
      case 2131624247:
      case 2131624248:
      case 2131624250:
      case 2131624251:
      default:
         return;
      case 2131624249:
         this.checkRadioButton(2131624250);
         return;
      case 2131624252:
         this.checkRadioButton(2131624253);
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903165);
      boolean var2 = this.getIntent().getBooleanExtra("add_account", (boolean)0);
      this.mAddAccountMode = var2;
      this.setupFatTitleBar();
      this.checkRadioButton(2131624250);
      this.findViewById(2131624245).setOnClickListener(this);
      this.findViewById(2131624246).setOnClickListener(this);
      this.findViewById(2131624249).setOnClickListener(this);
      this.findViewById(2131624252).setOnClickListener(this);
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 4) {
         if(PlatformUtils.isEclairOrLater()) {
            if(EclairKeyHandler.onKeyDown(var2)) {
               var3 = 1;
               return (boolean)var3;
            }
         } else if(this.onBackKeyPressed()) {
            var3 = 1;
            return (boolean)var3;
         }
      }

      var3 = super.onKeyDown(var1, var2);
      return (boolean)var3;
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 4 && PlatformUtils.isEclairOrLater() && EclairKeyHandler.onKeyUp(var2) && this.onBackKeyPressed()) {
         var3 = 1;
      } else {
         var3 = super.onKeyUp(var1, var2);
      }

      return (boolean)var3;
   }

   private class AddAccountUserTask extends UserTask {

      private final AppSession mAppSession;
      private final boolean mShowUngroupedContacts;
      private final boolean mSyncFriends;
      private final String mUsername;


      public AddAccountUserTask(AppSession var2, boolean var3, boolean var4) {
         Handler var5 = new Handler();
         super(var5);
         this.mAppSession = var2;
         String var6 = var2.getSessionInfo().username;
         this.mUsername = var6;
         this.mSyncFriends = var3;
         this.mShowUngroupedContacts = var4;
      }

      protected void doInBackground() {
         SyncContactsSetupActivity var1 = SyncContactsSetupActivity.this;
         String var2 = this.mUsername;
         boolean var3 = this.mSyncFriends;
         boolean var4 = this.mShowUngroupedContacts;
         FacebookAuthenticationService.storeSessionInfo(var1, var2, var3, var4);
         if(SyncContactsSetupActivity.this.mAddAccountMode) {
            Intent var5 = SyncContactsSetupActivity.this.getIntent();
            String var6 = this.mUsername;
            FacebookAuthenticationService.addAccountComplete(var5, var6);
         }
      }

      protected void onPostExecute() {
         AppSession var1 = this.mAppSession;
         SyncContactsSetupActivity var2 = SyncContactsSetupActivity.this;
         var1.syncFriends(var2);
      }
   }
}
