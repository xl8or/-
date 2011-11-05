package com.android.email.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.email.activity.MessageList;
import com.android.email.activity.UpgradeAccounts;

public class FolderMessageList extends Activity {

   public FolderMessageList() {}

   private void openInbox() {
      if(!UpgradeAccounts.doBulkUpgradeIfNecessary(this)) {
         Uri var1 = this.getIntent().getData();
         if(var1 != null) {
            String var2 = var1.getScheme();
            if("content".equals(var2)) {
               String var3 = var1.getAuthority();
               if("accounts".equals(var3)) {
                  String var4 = var1.getPath();
                  if(var4.length() > 0) {
                     var4 = var4.substring(1);
                  }

                  if(!TextUtils.isEmpty(var4)) {
                     MessageList.actionOpenAccountInboxUuid(this, var4);
                  }
               }
            }
         }
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      FolderMessageList.1 var2 = new FolderMessageList.1();
      Void[] var3 = new Void[0];
      var2.execute(var3);
   }

   class 1 extends AsyncTask<Void, Void, Void> {

      1() {}

      protected Void doInBackground(Void ... var1) {
         FolderMessageList.this.openInbox();
         return null;
      }

      protected void onPostExecute(Void var1) {
         FolderMessageList.this.finish();
      }
   }
}
