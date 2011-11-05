package com.android.exchange;

import android.accounts.AccountManagerCallback;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.android.email.Account;
import com.android.email.AccountBackupRestore;
import com.android.email.Email;
import com.android.email.Preferences;
import com.android.email.SecurityPolicy;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.mail.store.ExchangeStore;
import com.android.email.provider.EmailContent;
import com.android.exchange.SyncManager;

public class ReSync {

   private final String TAG = "ReSync";
   private EmailContent.Account mAccount = null;
   private long mAccountId = 0L;
   private Context mContext = null;


   public ReSync(long var1, EmailContent.Account var3, Context var4, ContentResolver var5) {
      this.mAccountId = var1;
      this.mAccount = var3;
      this.mContext = var4;
   }

   public void startReSync() {
      ReSync.ResyncTask var1 = new ReSync.ResyncTask();
      Void[] var2 = new Void[0];
      var1.execute(var2);
   }

   private class ResyncTask extends AsyncTask<Void, Integer, Integer> {

      ProgressDialog mProgressDialog;


      public ResyncTask() {}

      protected Integer doInBackground(Void ... var1) {
         SyncManager.stopAccountSyncsForReSync(ReSync.this.mAccountId);
         StringBuilder var2 = (new StringBuilder()).append("ReSync started for Account Id: ");
         long var3 = ReSync.this.mAccountId;
         String var5 = var2.append(var3).toString();
         int var6 = Log.d("ReSync", var5);
         StringBuilder var7 = (new StringBuilder()).append("mAccount.mEmailAddress = ");
         String var8 = ReSync.this.mAccount.mEmailAddress;
         String var9 = var7.append(var8).toString();
         SyncManager.log("ReSync", var9);
         Account[] var10 = Preferences.getPreferences(ReSync.this.mContext).getAccounts();
         Integer var11;
         if(var10.length == 0) {
            var11 = Integer.valueOf(1);
         } else {
            Account[] var12 = var10;
            int var13 = var10.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               Account var17 = var12[var14];
               String var18 = var17.getEmail();
               String var19 = ReSync.this.mAccount.mEmailAddress;
               if(var18.equals(var19)) {
                  int var20 = var17.getBackupFlags();
                  byte var21;
                  if((var20 & 2) != 0) {
                     var21 = 1;
                  } else {
                     var21 = 0;
                  }

                  byte var22;
                  if((var20 & 8) != 0) {
                     var22 = 1;
                  } else {
                     var22 = 0;
                  }

                  byte var23;
                  if((var20 & 9) != 0) {
                     var23 = 1;
                  } else {
                     var23 = 0;
                  }

                  try {
                     EmailContent.Account var24 = ReSync.this.mAccount;
                     Context var25 = ReSync.this.mContext;
                     String var26 = var24.getStoreUri(var25);
                     Context var27 = ReSync.this.mContext;
                     Store.getInstance(var26, var27, (Store.PersistentDataCallbacks)null).delete();
                     EmailContent.Account var28 = ReSync.this.mAccount;
                     Context var29 = ReSync.this.mContext;
                     Store.removeInstance(var28.getStoreUri(var29));
                     Uri var30 = EmailContent.Account.CONTENT_URI;
                     long var31 = ReSync.this.mAccountId;
                     Uri var33 = ContentUris.withAppendedId(var30, var31);
                     ContentResolver var34 = ReSync.this.mContext.getContentResolver();
                     Object var36 = null;
                     Object var37 = null;
                     var34.delete(var33, (String)var36, (String[])var37);
                     SecurityPolicy.getInstance(ReSync.this.mContext).reducePolicies();
                     SyncManager var39 = SyncManager.INSTANCE;
                     Context var40 = ReSync.this.mContext;
                     EmailContent.Account var41 = ReSync.this.mAccount;
                     var39.releaseSyncHolds(var40, 5, var41);
                     ReSync.this.mAccount.mId = 65535L;
                     ReSync.this.mAccount.mSyncKey = "0";
                     ReSync.this.mAccount.mSecuritySyncKey = null;
                     EmailContent.Account var42 = ReSync.this.mAccount;
                     Context var43 = ReSync.this.mContext;
                     var42.save(var43);
                     Context var45 = ReSync.this.mContext;
                     EmailContent.Account var46 = ReSync.this.mAccount;
                     ExchangeStore.addSystemAccount(var45, var46, (boolean)var21, (boolean)var22, (boolean)var23, (boolean)1, (AccountManagerCallback)null);
                     AccountBackupRestore.backupAccounts(ReSync.this.mContext);
                     Email.setNotifyUiAccountsChanged((boolean)1);
                  } catch (MessagingException var48) {
                     var48.printStackTrace();
                     SyncManager.log("Unable to perform resync");
                  }
                  break;
               }
            }

            var11 = Integer.valueOf(1);
         }

         return var11;
      }

      protected void onPostExecute(Integer var1) {
         super.onPostExecute(var1);
         this.mProgressDialog.dismiss();
         Toast.makeText(ReSync.this.mContext, 2131167184, 0).show();
      }

      protected void onPreExecute() {
         super.onPreExecute();
         Context var1 = ReSync.this.mContext;
         String var2 = ReSync.this.mContext.getResources().getString(2131167179);
         String var3 = ReSync.this.mContext.getResources().getString(2131167182);
         ProgressDialog var4 = ProgressDialog.show(var1, var2, var3);
         this.mProgressDialog = var4;
      }

      protected void onProgressUpdate(Integer ... var1) {
         super.onProgressUpdate(var1);
         ProgressDialog var2 = this.mProgressDialog;
         int var3 = var1[0].intValue();
         var2.setProgress(var3);
      }
   }
}
