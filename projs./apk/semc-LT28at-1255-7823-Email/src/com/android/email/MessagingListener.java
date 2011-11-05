package com.android.email;

import android.content.Context;

public class MessagingListener {

   public MessagingListener() {}

   public void checkMailFinished(Context var1, long var2, long var4, long var6) {}

   public void checkMailStarted(Context var1, long var2, long var4) {}

   public void controllerCommandCompleted(boolean var1) {}

   public void listFoldersFailed(long var1, String var3) {}

   public void listFoldersFinished(long var1) {}

   public void listFoldersStarted(long var1) {}

   public void loadAttachmentFailed(long var1, long var3, long var5, String var7) {}

   public void loadAttachmentFinished(long var1, long var3, long var5) {}

   public void loadAttachmentStarted(long var1, long var3, long var5, boolean var7) {}

   public void loadMessageForViewFailed(long var1, String var3) {}

   public void loadMessageForViewFinished(long var1) {}

   public void loadMessageForViewStarted(long var1) {}

   public void messageUidChanged(long var1, long var3, String var5, String var6) {}

   public void sendPendingMessagesCompleted(long var1) {}

   public void sendPendingMessagesFailed(long var1, long var3, Exception var5) {}

   public void sendPendingMessagesStarted(long var1, long var3) {}

   public void synchronizeMailboxFailed(long var1, long var3, Exception var5) {}

   public void synchronizeMailboxFinished(long var1, long var3, int var5, int var6) {}

   public void synchronizeMailboxStarted(long var1, long var3) {}
}
