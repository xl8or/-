package com.android.email.mail;

import android.content.Context;
import com.android.email.GroupMessagingListener;
import com.android.email.mail.MessagingException;
import com.android.email.provider.EmailContent;

public interface StoreSynchronizer {

   StoreSynchronizer.SyncResults SynchronizeMessagesSynchronous(EmailContent.Account var1, EmailContent.Mailbox var2, GroupMessagingListener var3, Context var4) throws MessagingException;

   public static class SyncResults {

      public int mNewMessages;
      public int mTotalMessages;


      public SyncResults(int var1, int var2) {
         this.mTotalMessages = var1;
         this.mNewMessages = var2;
      }
   }
}
