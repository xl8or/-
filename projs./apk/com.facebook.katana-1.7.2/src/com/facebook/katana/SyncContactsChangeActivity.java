package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.RemoveRawContactsService;
import com.facebook.katana.UserTask;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.platform.FacebookAuthenticationService;

public class SyncContactsChangeActivity extends BaseFacebookActivity implements OnClickListener {

   private AppSession mAppSession;
   private int mCheckedId;
   private boolean mShowUngroupedContacts;
   private boolean mSyncContacts;


   public SyncContactsChangeActivity() {}

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

   private void saveSettings() {
      byte var1 = 0;
      byte var2 = 0;
      switch(this.mCheckedId) {
      case 2131624247:
         var1 = 1;
         var2 = 1;
         break;
      case 2131624250:
         var1 = 1;
      case 2131624253:
      }

      String var3 = this.mAppSession.getSessionInfo().username;
      FacebookAuthenticationService.storeSessionInfo(this, var3, (boolean)var1, (boolean)var2);
      if(this.mSyncContacts && var1 == 0) {
         Intent var4 = new Intent(this, RemoveRawContactsService.class);
         this.startService(var4);
      } else if(!this.mSyncContacts) {
         if(var1 != 0) {
            String var6 = this.mAppSession.syncFriends(this);
         }
      }
   }

   private void setupFatTitleBar() {
      ((TextView)this.findViewById(2131624049)).setText(2131362282);
      ((TextView)this.findViewById(2131624050)).setText(2131362281);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131624100:
         this.saveSettings();
         this.finish();
         return;
      case 2131624243:
         this.finish();
         return;
      case 2131624246:
         this.checkRadioButton(2131624247);
         return;
      case 2131624249:
         this.checkRadioButton(2131624250);
         return;
      case 2131624252:
         this.checkRadioButton(2131624253);
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903164);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         this.setupFatTitleBar();
         ((Button)this.findViewById(2131624100)).setOnClickListener(this);
         ((Button)this.findViewById(2131624243)).setOnClickListener(this);
         this.findViewById(2131624246).setOnClickListener(this);
         this.findViewById(2131624249).setOnClickListener(this);
         this.findViewById(2131624252).setOnClickListener(this);
         this.findViewById(2131624256).setVisibility(8);
         (new SyncContactsChangeActivity.ReadAccountUserTask()).execute();
      }
   }

   private class ReadAccountUserTask extends UserTask {

      private final String mUsername;


      public ReadAccountUserTask() {
         Handler var2 = new Handler();
         super(var2);
         String var3 = SyncContactsChangeActivity.this.mAppSession.getSessionInfo().username;
         this.mUsername = var3;
      }

      protected void doInBackground() {
         SyncContactsChangeActivity var1 = SyncContactsChangeActivity.this;
         SyncContactsChangeActivity var2 = SyncContactsChangeActivity.this;
         String var3 = this.mUsername;
         boolean var4 = FacebookAuthenticationService.isSyncEnabled(var2, var3);
         var1.mSyncContacts = var4;
         SyncContactsChangeActivity var6 = SyncContactsChangeActivity.this;
         SyncContactsChangeActivity var7 = SyncContactsChangeActivity.this;
         String var8 = this.mUsername;
         boolean var9 = FacebookAuthenticationService.doesShowUngroupedContacts(var7, var8);
         var6.mShowUngroupedContacts = var9;
      }

      protected void onPostExecute() {
         if(!SyncContactsChangeActivity.this.isFinishing()) {
            int var1;
            if(SyncContactsChangeActivity.this.mSyncContacts) {
               if(SyncContactsChangeActivity.this.mShowUngroupedContacts) {
                  var1 = 2131624247;
               } else {
                  var1 = 2131624250;
               }
            } else {
               var1 = 2131624253;
            }

            SyncContactsChangeActivity.this.checkRadioButton(var1);
            if(var1 != 2131624253) {
               TextView var2 = (TextView)SyncContactsChangeActivity.this.findViewById(2131624254);
               String var3 = SyncContactsChangeActivity.this.getString(2131362273);
               var2.setText(var3);
               TextView var4 = (TextView)SyncContactsChangeActivity.this.findViewById(2131624255);
               String var5 = SyncContactsChangeActivity.this.getString(2131362274);
               var4.setText(var5);
            }
         }
      }
   }
}
