package com.android.email;

import android.content.Context;
import com.android.email.mail.MessagingException;
import java.util.ArrayList;

public class MessagingListener {

   public static final int FOLDER_CMD_CREATE = 1;
   public static final int FOLDER_CMD_DELETE = 2;
   public static final int FOLDER_CMD_LIST = 0;
   public static final int FOLDER_CMD_MAX = 6;
   public static final int FOLDER_CMD_PUSH = 5;
   public static final int FOLDER_CMD_RENAME = 3;
   public static final int FOLDER_CMD_UPDATE = 4;


   public MessagingListener() {}

   public void Attachment_StatusStart(long var1, long var3, long var5, long var7) {}

   public void checkMailFinished(Context var1, long var2, long var4, long var6) {}

   public void checkMailStarted(Context var1, long var2, long var4) {}

   public void controllerCommandCompleted(boolean var1) {}

   public void foldersCommandFinished(long var1, int var3, String var4, MessagingException var5) {}

   public void foldersCommandStarted(long var1, int var3, String var4) {}

   public void loadAttachmentFailed(long var1, long var3, long var5, String var7) {}

   public void loadAttachmentFinished(long var1, long var3, long var5, boolean var7) {}

   public void loadAttachmentStarted(long var1, long var3, long var5, boolean var7) {}

   public void loadMessageForViewFailed(long var1, String var3) {}

   public void loadMessageForViewFinished(long var1) {}

   public void loadMessageForViewStarted(long var1) {}

   public void messageUidChanged(long var1, long var3, String var5, String var6) {}

   public void movemessageToOtherAccountCallback(boolean var1, long var2, long var4, long var6, long var8, long var10, long var12, int var14, int var15) {}

   public void sendPendingMessagesCompleted(long var1) {}

   public void sendPendingMessagesFailed(long var1, long var3, Exception var5) {}

   public void sendPendingMessagesStarted(long var1, long var3) {}

   public void synchronizeMailboxFailed(long var1, long var3, Exception var5) {}

   public void synchronizeMailboxFinished(long var1, long var3, int var5, int var6) {}

   public void synchronizeMailboxFinishedEx(long var1, long var3, int var5, int var6, ArrayList<Long> var7) {}

   public void synchronizeMailboxStarted(long var1, long var3) {}
}
