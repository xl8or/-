package com.android.email.mail;

import com.android.email.mail.FetchProfile;
import com.android.email.mail.Flag;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;

public abstract class Folder {

   public Folder() {}

   public abstract void appendMessages(Message[] var1) throws MessagingException;

   public abstract boolean canCreate(Folder.FolderType var1);

   public abstract void close(boolean var1) throws MessagingException;

   public abstract void copyMessages(Message[] var1, Folder var2, Folder.MessageUpdateCallbacks var3) throws MessagingException;

   public abstract boolean create(Folder.FolderType var1) throws MessagingException;

   public abstract Message createMessage(String var1) throws MessagingException;

   public abstract void delete(boolean var1) throws MessagingException;

   public abstract boolean exists() throws MessagingException;

   public abstract Message[] expunge() throws MessagingException;

   public abstract void fetch(Message[] var1, FetchProfile var2, Folder.MessageRetrievalListener var3) throws MessagingException;

   public abstract Message getMessage(String var1) throws MessagingException;

   public abstract int getMessageCount() throws MessagingException;

   public abstract Message[] getMessages(int var1, int var2, Folder.MessageRetrievalListener var3) throws MessagingException;

   public abstract Message[] getMessages(Folder.MessageRetrievalListener var1) throws MessagingException;

   public Message[] getMessages(Flag[] var1, Flag[] var2, Folder.MessageRetrievalListener var3) throws MessagingException {
      throw new MessagingException("Not implemented");
   }

   public abstract Message[] getMessages(String[] var1, Folder.MessageRetrievalListener var2) throws MessagingException;

   public abstract Folder.OpenMode getMode() throws MessagingException;

   public abstract String getName();

   public abstract Flag[] getPermanentFlags() throws MessagingException;

   public Folder.FolderRole getRole() {
      return Folder.FolderRole.UNKNOWN;
   }

   public abstract int getUnreadMessageCount() throws MessagingException;

   public abstract boolean isOpen();

   public void localFolderSetupComplete(Folder var1) throws MessagingException {}

   public abstract void open(Folder.OpenMode var1, Folder.PersistentDataCallbacks var2) throws MessagingException;

   public abstract void setFlags(Message[] var1, Flag[] var2, boolean var3) throws MessagingException;

   public String toString() {
      return this.getName();
   }

   public static enum FolderType {

      // $FF: synthetic field
      private static final Folder.FolderType[] $VALUES;
      HOLDS_FOLDERS("HOLDS_FOLDERS", 0),
      HOLDS_MESSAGES("HOLDS_MESSAGES", 1);


      static {
         Folder.FolderType[] var0 = new Folder.FolderType[2];
         Folder.FolderType var1 = HOLDS_FOLDERS;
         var0[0] = var1;
         Folder.FolderType var2 = HOLDS_MESSAGES;
         var0[1] = var2;
         $VALUES = var0;
      }

      private FolderType(String var1, int var2) {}
   }

   public interface MessageRetrievalListener {

      void messageRetrieved(Message var1);
   }

   public interface MessageUpdateCallbacks {

      void onMessageNotFound(Message var1) throws MessagingException;

      void onMessageUidChange(Message var1, String var2) throws MessagingException;
   }

   public static enum OpenMode {

      // $FF: synthetic field
      private static final Folder.OpenMode[] $VALUES;
      READ_ONLY("READ_ONLY", 1),
      READ_WRITE("READ_WRITE", 0);


      static {
         Folder.OpenMode[] var0 = new Folder.OpenMode[2];
         Folder.OpenMode var1 = READ_WRITE;
         var0[0] = var1;
         Folder.OpenMode var2 = READ_ONLY;
         var0[1] = var2;
         $VALUES = var0;
      }

      private OpenMode(String var1, int var2) {}
   }

   public interface PersistentDataCallbacks {

      String getPersistentString(String var1, String var2);

      void setPersistentString(String var1, String var2);

      void setPersistentStringAndMessageFlags(String var1, String var2, Flag[] var3, Flag[] var4) throws MessagingException;
   }

   public static enum FolderRole {

      // $FF: synthetic field
      private static final Folder.FolderRole[] $VALUES;
      DRAFTS("DRAFTS", 3),
      INBOX("INBOX", 0),
      OTHER("OTHER", 5),
      OUTBOX("OUTBOX", 4),
      SENT("SENT", 2),
      TRASH("TRASH", 1),
      UNKNOWN("UNKNOWN", 6);


      static {
         Folder.FolderRole[] var0 = new Folder.FolderRole[7];
         Folder.FolderRole var1 = INBOX;
         var0[0] = var1;
         Folder.FolderRole var2 = TRASH;
         var0[1] = var2;
         Folder.FolderRole var3 = SENT;
         var0[2] = var3;
         Folder.FolderRole var4 = DRAFTS;
         var0[3] = var4;
         Folder.FolderRole var5 = OUTBOX;
         var0[4] = var5;
         Folder.FolderRole var6 = OTHER;
         var0[5] = var6;
         Folder.FolderRole var7 = UNKNOWN;
         var0[6] = var7;
         $VALUES = var0;
      }

      private FolderRole(String var1, int var2) {}
   }
}
