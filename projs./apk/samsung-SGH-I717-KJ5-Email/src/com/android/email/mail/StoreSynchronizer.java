package com.android.email.mail;

import android.content.Context;
import com.android.email.GroupMessagingListener;
import com.android.email.mail.MessagingException;
import com.android.email.provider.EmailContent;
import java.util.ArrayList;

public interface StoreSynchronizer {

   StoreSynchronizer.SyncResults SynchronizeMessagesSynchronous(EmailContent.Account var1, EmailContent.Mailbox var2, GroupMessagingListener var3, Context var4) throws MessagingException;

   public static class SyncResults {

      public ArrayList<Long> mNewMessageIds;
      public int mNewMessages;
      public int mTotalMessages;


      public SyncResults(int var1, int var2) {
         ArrayList var3 = new ArrayList();
         this.mNewMessageIds = var3;
         this.mTotalMessages = var1;
         this.mNewMessages = var2;
         this.mNewMessageIds.clear();
      }

      public void addMessageId(Long var1) {
         this.mNewMessageIds.add(var1);
      }
   }
}
