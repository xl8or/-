package com.android.exchange;

import android.content.Context;
import android.os.RemoteException;
import com.android.email.mail.MessagingException;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import java.io.File;
import java.io.IOException;

public class EasOutboxService extends EasSyncService {

   public static final String[] BODY_SOURCE_PROJECTION;
   public static final String MAILBOX_KEY_AND_NOT_SEND_FAILED = "mailboxKey=? and (syncServerId is null or syncServerId!=1)";
   public static final int SEND_FAILED = 1;
   public static final int SEND_MAIL_TIMEOUT = 900000;
   public static final String WHERE_MESSAGE_KEY = "messageKey=?";


   static {
      String[] var0 = new String[]{"sourceMessageKey"};
      BODY_SOURCE_PROJECTION = var0;
   }

   public EasOutboxService(Context var1, EmailContent.Mailbox var2) {
      super(var1, var2);
   }

   private void sendCallback(long var1, String var3, int var4) {
      try {
         IEmailServiceCallback var5 = SyncManager.callback();
         var5.sendMessageStatus(var1, var3, var4, 0);
      } catch (RemoteException var11) {
         ;
      }
   }

   public static void sendMessage(Context var0, long var1, EmailContent.Message var3) {
      EmailContent.Mailbox var4 = EmailContent.Mailbox.restoreMailboxOfType(var0, var1, 4);
      if(var4 != null) {
         long var5 = var4.mId;
         var3.mMailboxKey = var5;
         var3.mAccountKey = var1;
         var3.save(var0);
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   int sendMessage(File param1, long param2) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }
}
