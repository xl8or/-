package com.android.exchange;

import android.content.Context;
import android.util.Log;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;

public class EasFolderCreateSvc extends EasSyncService {

   public String TAG = "EasFolderCreateService";
   private final String mNewFolderName;
   private final long mParentFolderID;


   public EasFolderCreateSvc(Context var1, EmailContent.Account var2, String var3, long var4) {
      super(var1, var2);
      int var6 = Log.i("Mahskyript", "EasFolderCreateSvc.EasFolderCreateSvc");
      this.mNewFolderName = var3;
      this.mParentFolderID = var4;
      long var7 = var2.mId;
      long var9 = EmailContent.Mailbox.findMailboxOfType(var1, var7, 68);
      this.mMailboxId = var9;
      long var11 = this.mMailboxId;
      EmailContent.Mailbox var13 = EmailContent.Mailbox.restoreMailboxWithId(var1, var11);
      this.mMailbox = var13;
      int var14 = Log.i("Mahskyript", "EasFolderCreateSvc.EasFolderCreateSvc exit");
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
