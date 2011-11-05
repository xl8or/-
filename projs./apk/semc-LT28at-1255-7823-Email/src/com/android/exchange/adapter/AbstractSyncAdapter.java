package com.android.exchange.adapter;

import android.accounts.Account;
import android.content.Context;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractSyncAdapter {

   public static final int DAYS = 86400000;
   public static final int HOURS = 3600000;
   public static final int MINUTES = 60000;
   public static final int SECONDS = 1000;
   public static final int WEEKS = 604800000;
   public EmailContent.Account mAccount;
   public final Account mAccountManagerAccount;
   public Context mContext;
   public EmailContent.Mailbox mMailbox;
   public EasSyncService mService;


   public AbstractSyncAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      this.mMailbox = var1;
      this.mService = var2;
      Context var3 = var2.mContext;
      this.mContext = var3;
      EmailContent.Account var4 = var2.mAccount;
      this.mAccount = var4;
      String var5 = this.mAccount.mEmailAddress;
      Account var6 = new Account(var5, "com.android.exchange");
      this.mAccountManagerAccount = var6;
   }

   public abstract void cleanup();

   public abstract String getCollectionName();

   public String getSyncKey() throws IOException {
      if(this.mMailbox.mSyncKey == null) {
         String[] var1 = new String[]{"Reset SyncKey to 0"};
         this.userLog(var1);
         this.mMailbox.mSyncKey = "0";
      }

      return this.mMailbox.mSyncKey;
   }

   public void incrementChangeCount() {
      EasSyncService var1 = this.mService;
      int var2 = var1.mChangeCount + 1;
      var1.mChangeCount = var2;
   }

   public boolean isLooping() {
      return false;
   }

   public abstract boolean isSyncable();

   public abstract boolean parse(InputStream var1) throws IOException;

   public abstract boolean sendLocalChanges(Serializer var1) throws IOException;

   public void setSyncKey(String var1, boolean var2) throws IOException {
      this.mMailbox.mSyncKey = var1;
   }

   public void userLog(String ... var1) {
      this.mService.userLog(var1);
   }
}
