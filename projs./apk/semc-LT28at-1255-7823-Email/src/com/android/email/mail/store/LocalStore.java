package com.android.email.mail.store;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.util.Base64OutputStream;
import android.util.Log;
import com.android.email.Utility;
import com.android.email.mail.Body;
import com.android.email.mail.FetchProfile;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Part;
import com.android.email.mail.Store;
import com.android.email.mail.internet.MimeBodyPart;
import com.android.email.mail.internet.MimeMessage;
import com.android.email.mail.internet.MimeUtility;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

public class LocalStore extends Store implements Store.PersistentDataCallbacks {

   private static final int DB_VERSION = 24;
   private static final Flag[] PERMANENT_FLAGS;
   private final File mAttachmentsDir;
   private final Context mContext;
   private SQLiteDatabase mDb;
   private final String mPath;
   private int mVisibleLimitDefault = -1;


   static {
      Flag[] var0 = new Flag[3];
      Flag var1 = Flag.DELETED;
      var0[0] = var1;
      Flag var2 = Flag.X_DESTROYED;
      var0[1] = var2;
      Flag var3 = Flag.SEEN;
      var0[2] = var3;
      PERMANENT_FLAGS = var0;
   }

   private LocalStore(String var1, Context var2) throws MessagingException {
      this.mContext = var2;

      URI var3;
      try {
         var3 = new URI(var1);
      } catch (Exception var20) {
         throw new MessagingException("Invalid uri for LocalStore");
      }

      if(!var3.getScheme().equals("local")) {
         throw new MessagingException("Invalid scheme");
      } else {
         String var5 = var3.getPath();
         this.mPath = var5;
         String var6 = this.mPath;
         File var7 = (new File(var6)).getParentFile();
         if(!var7.exists()) {
            boolean var8 = var7.mkdirs();
         }

         SQLiteDatabase var9 = SQLiteDatabase.openOrCreateDatabase(this.mPath, (CursorFactory)null);
         this.mDb = var9;
         int var10 = this.mDb.getVersion();
         if(var10 != 24) {
            if(var10 < 18) {
               this.mDb.execSQL("DROP TABLE IF EXISTS folders");
               this.mDb.execSQL("CREATE TABLE folders (id INTEGER PRIMARY KEY, name TEXT, last_updated INTEGER, unread_count INTEGER, visible_limit INTEGER)");
               this.mDb.execSQL("DROP TABLE IF EXISTS messages");
               this.mDb.execSQL("CREATE TABLE messages (id INTEGER PRIMARY KEY, folder_id INTEGER, uid TEXT, subject TEXT, date INTEGER, flags TEXT, sender_list TEXT, to_list TEXT, cc_list TEXT, bcc_list TEXT, reply_to_list TEXT, html_content TEXT, text_content TEXT, attachment_count INTEGER, internal_date INTEGER, message_id TEXT, store_flag_1 INTEGER, store_flag_2 INTEGER, flag_downloaded_full INTEGER,flag_downloaded_partial INTEGER, flag_deleted INTEGER, x_headers TEXT)");
               this.mDb.execSQL("DROP TABLE IF EXISTS attachments");
               this.mDb.execSQL("CREATE TABLE attachments (id INTEGER PRIMARY KEY, message_id INTEGER,store_data TEXT, content_uri TEXT, size INTEGER, name TEXT,mime_type TEXT, content_id TEXT)");
               this.mDb.execSQL("DROP TABLE IF EXISTS pending_commands");
               this.mDb.execSQL("CREATE TABLE pending_commands (id INTEGER PRIMARY KEY, command TEXT, arguments TEXT)");
               this.addRemoteStoreDataTable();
               this.addFolderDeleteTrigger();
               this.mDb.execSQL("DROP TRIGGER IF EXISTS delete_message");
               this.mDb.execSQL("CREATE TRIGGER delete_message BEFORE DELETE ON messages BEGIN DELETE FROM attachments WHERE old.id = message_id; END;");
               this.mDb.setVersion(24);
            } else {
               if(var10 < 19) {
                  this.mDb.execSQL("ALTER TABLE messages ADD COLUMN message_id TEXT;");
                  this.mDb.setVersion(19);
               }

               if(var10 < 20) {
                  this.mDb.execSQL("ALTER TABLE attachments ADD COLUMN content_id TEXT;");
                  this.mDb.setVersion(20);
               }

               if(var10 < 21) {
                  this.addRemoteStoreDataTable();
                  this.addFolderDeleteTrigger();
                  this.mDb.setVersion(21);
               }

               if(var10 < 22) {
                  this.mDb.execSQL("ALTER TABLE messages ADD COLUMN store_flag_1 INTEGER;");
                  this.mDb.execSQL("ALTER TABLE messages ADD COLUMN store_flag_2 INTEGER;");
                  this.mDb.setVersion(22);
               }

               if(var10 < 23) {
                  this.mDb.beginTransaction();

                  try {
                     this.mDb.execSQL("ALTER TABLE messages ADD COLUMN flag_downloaded_full INTEGER;");
                     this.mDb.execSQL("ALTER TABLE messages ADD COLUMN flag_downloaded_partial INTEGER;");
                     this.mDb.execSQL("ALTER TABLE messages ADD COLUMN flag_deleted INTEGER;");
                     this.migrateMessageFlags();
                     this.mDb.setVersion(23);
                     this.mDb.setTransactionSuccessful();
                  } finally {
                     this.mDb.endTransaction();
                  }
               }

               if(var10 < 24) {
                  this.mDb.execSQL("ALTER TABLE messages ADD COLUMN x_headers TEXT;");
                  this.mDb.setVersion(24);
               }
            }

            if(this.mDb.getVersion() != 24) {
               throw new Error("Database upgrade failed!");
            }
         }

         StringBuilder var12 = new StringBuilder();
         String var13 = this.mPath;
         String var14 = var12.append(var13).append("_att").toString();
         File var15 = new File(var14);
         this.mAttachmentsDir = var15;
         if(!this.mAttachmentsDir.exists()) {
            boolean var16 = this.mAttachmentsDir.mkdirs();
         }
      }
   }

   // $FF: synthetic method
   static String access$800(LocalStore var0, long var1, String var3, String var4) {
      return var0.getPersistentString(var1, var3, var4);
   }

   // $FF: synthetic method
   static void access$900(LocalStore var0, long var1, String var3, String var4) {
      var0.setPersistentString(var1, var3, var4);
   }

   private void addFolderDeleteTrigger() {
      this.mDb.execSQL("DROP TRIGGER IF EXISTS delete_folder");
      this.mDb.execSQL("CREATE TRIGGER delete_folder BEFORE DELETE ON folders BEGIN DELETE FROM messages WHERE old.id = folder_id; DELETE FROM remote_store_data WHERE old.id = folder_id; END;");
   }

   private void addRemoteStoreDataTable() {
      this.mDb.execSQL("DROP TABLE IF EXISTS remote_store_data");
      this.mDb.execSQL("CREATE TABLE remote_store_data (id INTEGER PRIMARY KEY, folder_id INTEGER, data_key TEXT, data TEXT, UNIQUE (folder_id, data_key) ON CONFLICT REPLACE)");
   }

   private String getPersistentString(long param1, String param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   private void migrateMessageFlags() {
      SQLiteDatabase var1 = this.mDb;
      String[] var2 = new String[]{"id", "flags"};
      Cursor var3 = var1.query("messages", var2, (String)null, (String[])null, (String)null, (String)null, (String)null);

      try {
         int var4 = var3.getColumnIndexOrThrow("id");
         int var5 = var3.getColumnIndexOrThrow("flags");

         while(var3.moveToNext()) {
            String var6 = var3.getString(var5);
            ContentValues var7 = new ContentValues();
            byte var8 = 0;
            byte var9 = 0;
            byte var10 = 0;
            if(var6 != null) {
               String var11 = Flag.X_DOWNLOADED_FULL.toString();
               if(var6.contains(var11)) {
                  var8 = 1;
               }

               String var14 = Flag.X_DOWNLOADED_PARTIAL.toString();
               if(var6.contains(var14)) {
                  var9 = 1;
               }

               String var17 = Flag.DELETED.toString();
               if(var6.contains(var17)) {
                  var10 = 1;
               }
            }

            Integer var20 = Integer.valueOf(var8);
            String var22 = "flag_downloaded_full";
            var7.put(var22, var20);
            Integer var24 = Integer.valueOf(var9);
            String var26 = "flag_downloaded_partial";
            var7.put(var26, var24);
            Integer var28 = Integer.valueOf(var10);
            String var30 = "flag_deleted";
            var7.put(var30, var28);
            int var32 = var3.getInt(var4);
            SQLiteDatabase var33 = this.mDb;
            StringBuilder var34 = (new StringBuilder()).append("id=");
            String var36 = var34.append(var32).toString();
            String var38 = "messages";
            Object var41 = null;
            var33.update(var38, var7, var36, (String[])var41);
         }
      } finally {
         var3.close();
      }

   }

   public static LocalStore newInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      return new LocalStore(var0, var1);
   }

   private void setPersistentString(long var1, String var3, String var4) {
      ContentValues var5 = new ContentValues();
      String var6 = Long.toString(var1);
      var5.put("folder_id", var6);
      var5.put("data_key", var3);
      var5.put("data", var4);
      this.mDb.insert("remote_store_data", (String)null, var5);
   }

   public void addPendingCommand(LocalStore.PendingCommand var1) {
      int var2 = 0;

      try {
         while(true) {
            int var3 = var1.arguments.length;
            if(var2 >= var3) {
               ContentValues var6 = new ContentValues();
               String var7 = var1.command;
               var6.put("command", var7);
               String var8 = Utility.combine(var1.arguments, ',');
               var6.put("arguments", var8);
               this.mDb.insert("pending_commands", "command", var6);
               return;
            }

            String[] var4 = var1.arguments;
            String var5 = URLEncoder.encode(var1.arguments[var2], "UTF-8");
            var4[var2] = var5;
            ++var2;
         }
      } catch (UnsupportedEncodingException var12) {
         throw new Error("Aparently UTF-8 has been lost to the annals of history.");
      }
   }

   public void checkSettings() throws MessagingException {}

   public void close() {
      try {
         this.mDb.close();
         this.mDb = null;
      } catch (Exception var4) {
         String var2 = "Caught exception while closing localstore db: " + var4;
         int var3 = Log.d("Email", var2);
      }
   }

   public void delete() {
      try {
         this.mDb.close();
      } catch (Exception var13) {
         ;
      }

      try {
         File[] var1 = this.mAttachmentsDir.listFiles();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            File var4 = var1[var3];
            if(var4.exists()) {
               boolean var5 = var4.delete();
            }
         }

         if(this.mAttachmentsDir.exists()) {
            boolean var6 = this.mAttachmentsDir.delete();
         }
      } catch (Exception var14) {
         ;
      }

      try {
         String var7 = this.mPath;
         boolean var8 = (new File(var7)).delete();
      } catch (Exception var12) {
         ;
      }
   }

   public Folder getFolder(String var1) throws MessagingException {
      return new LocalStore.LocalFolder(var1);
   }

   public ArrayList<LocalStore.PendingCommand> getPendingCommands() {
      // $FF: Couldn't be decompiled
   }

   public Store.PersistentDataCallbacks getPersistentCallbacks() throws MessagingException {
      return this;
   }

   public String getPersistentString(String var1, String var2) {
      return this.getPersistentString(65535L, var1, var2);
   }

   public Folder[] getPersonalNamespaces() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Class<? extends Activity> getSettingActivityClass() {
      return null;
   }

   public int getStoredAttachmentCount() {
      int var1;
      try {
         var1 = this.mAttachmentsDir.listFiles().length;
      } catch (Exception var3) {
         var1 = 0;
      }

      return var1;
   }

   int makeFlagNumeric(Message var1, Flag var2) {
      byte var3;
      if(var1.isSet(var2)) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      return var3;
   }

   String makeFlagsString(Message var1) {
      StringBuilder var2 = null;
      Flag[] var3 = Flag.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Flag var6 = var3[var5];
         Flag var7 = Flag.X_STORE_1;
         if(var6 != var7) {
            Flag var8 = Flag.X_STORE_2;
            if(var6 != var8) {
               Flag var9 = Flag.X_DOWNLOADED_FULL;
               if(var6 != var9) {
                  Flag var10 = Flag.X_DOWNLOADED_PARTIAL;
                  if(var6 != var10) {
                     Flag var11 = Flag.DELETED;
                     if(var6 != var11 && var1.isSet(var6)) {
                        if(var2 == null) {
                           var2 = new StringBuilder();
                        }

                        if(false) {
                           StringBuilder var12 = var2.append(',');
                        }

                        String var13 = var6.toString();
                        var2.append(var13);
                     }
                  }
               }
            }
         }
      }

      String var15;
      if(var2 == null) {
         var15 = null;
      } else {
         var15 = var2.toString();
      }

      return var15;
   }

   public int pruneCachedAttachments() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void removePendingCommand(LocalStore.PendingCommand var1) {
      SQLiteDatabase var2 = this.mDb;
      String[] var3 = new String[1];
      String var4 = Long.toString(var1.mId);
      var3[0] = var4;
      var2.delete("pending_commands", "id = ?", var3);
   }

   public void resetVisibleLimits(int var1) {
      this.mVisibleLimitDefault = var1;
      ContentValues var2 = new ContentValues();
      String var3 = Integer.toString(var1);
      var2.put("visible_limit", var3);
      this.mDb.update("folders", var2, (String)null, (String[])null);
   }

   public void setPersistentString(String var1, String var2) {
      this.setPersistentString(65535L, var1, var2);
   }

   public class LocalFolder extends Folder implements Folder.PersistentDataCallbacks {

      private final String POPULATE_MESSAGE_SELECT_COLUMNS = "subject, sender_list, date, uid, flags, id, to_list, cc_list, bcc_list, reply_to_list, attachment_count, internal_date, message_id, store_flag_1, store_flag_2, flag_downloaded_full, flag_downloaded_partial, flag_deleted, x_headers";
      private long mFolderId = 65535L;
      private final String mName;
      private int mUnreadMessageCount = -1;
      private int mVisibleLimit = -1;


      public LocalFolder(String var2) {
         this.mName = var2;
      }

      // $FF: synthetic method
      static void access$1000(LocalStore.LocalFolder var0, String var1) throws MessagingException {
         var0.deleteAttachments(var1);
      }

      private void buildFlagPredicates(StringBuilder var1, Flag[] var2, Flag[] var3) throws MessagingException {
         Flag[] var4;
         int var5;
         int var6;
         if(var2 != null) {
            var4 = var2;
            var5 = var2.length;

            for(var6 = 0; var6 < var5; ++var6) {
               Flag var7 = var4[var6];
               Flag var8 = Flag.X_STORE_1;
               if(var7 == var8) {
                  StringBuilder var9 = var1.append("store_flag_1 = 1 AND ");
               } else {
                  Flag var10 = Flag.X_STORE_2;
                  if(var7 == var10) {
                     StringBuilder var11 = var1.append("store_flag_2 = 1 AND ");
                  } else {
                     Flag var12 = Flag.X_DOWNLOADED_FULL;
                     if(var7 == var12) {
                        StringBuilder var13 = var1.append("flag_downloaded_full = 1 AND ");
                     } else {
                        Flag var14 = Flag.X_DOWNLOADED_PARTIAL;
                        if(var7 == var14) {
                           StringBuilder var15 = var1.append("flag_downloaded_partial = 1 AND ");
                        } else {
                           Flag var16 = Flag.DELETED;
                           if(var7 != var16) {
                              String var18 = "Unsupported flag " + var7;
                              throw new MessagingException(var18);
                           }

                           StringBuilder var17 = var1.append("flag_deleted = 1 AND ");
                        }
                     }
                  }
               }
            }
         }

         if(var3 != null) {
            var4 = var3;
            var5 = var3.length;

            for(var6 = 0; var6 < var5; ++var6) {
               Flag var19 = var4[var6];
               Flag var20 = Flag.X_STORE_1;
               if(var19 == var20) {
                  StringBuilder var21 = var1.append("store_flag_1 = 0 AND ");
               } else {
                  Flag var22 = Flag.X_STORE_2;
                  if(var19 == var22) {
                     StringBuilder var23 = var1.append("store_flag_2 = 0 AND ");
                  } else {
                     Flag var24 = Flag.X_DOWNLOADED_FULL;
                     if(var19 == var24) {
                        StringBuilder var25 = var1.append("flag_downloaded_full = 0 AND ");
                     } else {
                        Flag var26 = Flag.X_DOWNLOADED_PARTIAL;
                        if(var19 == var26) {
                           StringBuilder var27 = var1.append("flag_downloaded_partial = 0 AND ");
                        } else {
                           Flag var28 = Flag.DELETED;
                           if(var19 != var28) {
                              String var30 = "Unsupported flag " + var19;
                              throw new MessagingException(var30);
                           }

                           StringBuilder var29 = var1.append("flag_deleted = 0 AND ");
                        }
                     }
                  }
               }
            }

         }
      }

      private void deleteAttachments(String param1) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      private void open(Folder.OpenMode var1) throws MessagingException {
         this.open(var1, (Folder.PersistentDataCallbacks)null);
      }

      private void populateMessageFromGetMessageCursor(LocalStore.LocalMessage param1, Cursor param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      private void saveAttachment(long var1, Part var3, boolean var4) throws IOException, MessagingException {
         long var5 = 65535L;
         Uri var7 = null;
         int var8 = '\uffff';
         File var9 = null;
         if(var4 == null && var3 instanceof LocalStore.LocalAttachmentBodyPart) {
            long var10 = ((LocalStore.LocalAttachmentBodyPart)var3).getAttachmentId();
         }

         if(var3.getBody() != null) {
            Body var12 = var3.getBody();
            if(var12 instanceof LocalStore.LocalAttachmentBody) {
               var7 = ((LocalStore.LocalAttachmentBody)var12).getContentUri();
            } else {
               InputStream var90 = var3.getBody().getInputStream();
               File var91 = LocalStore.this.mAttachmentsDir;
               var9 = File.createTempFile("att", (String)null, var91);
               FileOutputStream var92 = new FileOutputStream(var9);
               var8 = IOUtils.copy(var90, (OutputStream)var92);
               var90.close();
               var92.close();
            }
         }

         char var14 = '\uffff';
         if(var8 == var14) {
            String var15 = var3.getDisposition();
            if(var15 != null) {
               String var17 = "size";
               String var18 = MimeUtility.getHeaderParameter(var15, var17);
               if(var18 != null) {
                  var8 = Integer.parseInt(var18);
               }
            }
         }

         char var20 = '\uffff';
         if(var8 == var20) {
            var8 = 0;
         }

         String var22 = "X-Android-Attachment-StoreData";
         String var23 = Utility.combine(var3.getHeader(var22), ',');
         String var24 = MimeUtility.getHeaderParameter(var3.getContentType(), "name");
         String var25 = var3.getContentId();
         ContentValues var26;
         if(65535L == 65535L) {
            var26 = new ContentValues();
            Long var27 = Long.valueOf(var1);
            String var29 = "message_id";
            var26.put(var29, var27);
            String var31 = "content_uri";
            String var32;
            if(var7 != null) {
               var32 = var7.toString();
            } else {
               var32 = null;
            }

            var26.put(var31, var32);
            String var37 = "store_data";
            var26.put(var37, var23);
            Integer var39 = Integer.valueOf(var8);
            String var41 = "size";
            var26.put(var41, var39);
            String var44 = "name";
            var26.put(var44, var24);
            String var46 = var3.getMimeType();
            String var48 = "mime_type";
            var26.put(var48, var46);
            String var51 = "content_id";
            var26.put(var51, var25);
            SQLiteDatabase var53 = LocalStore.this.mDb;
            String var54 = "attachments";
            String var55 = "message_id";
            var5 = var53.insert(var54, var55, var26);
         } else {
            var26 = new ContentValues();
            String var95 = "content_uri";
            String var96;
            if(var7 != null) {
               var96 = var7.toString();
            } else {
               var96 = null;
            }

            var26.put(var95, var96);
            Integer var100 = Integer.valueOf(var8);
            String var102 = "size";
            var26.put(var102, var100);
            String var105 = "content_id";
            var26.put(var105, var25);
            Long var107 = Long.valueOf(var1);
            String var109 = "message_id";
            var26.put(var109, var107);
            SQLiteDatabase var111 = LocalStore.this.mDb;
            String[] var112 = new String[1];
            String var113 = Long.toString(65535L);
            var112[0] = var113;
            String var115 = "attachments";
            String var117 = "id = ?";
            var111.update(var115, var26, var117, var112);
         }

         if(var9 != null) {
            File var57 = new File;
            File var58 = LocalStore.this.mAttachmentsDir;
            String var59 = Long.toString(var5);
            var57.<init>(var58, var59);
            boolean var65 = var9.renameTo(var57);
            LocalStore.LocalAttachmentBody var66 = new LocalStore.LocalAttachmentBody;
            Context var67 = LocalStore.this.mContext;
            var66.<init>(var7, var67);
            var3.setBody(var66);
            var26 = new ContentValues();
            String var73 = "content_uri";
            String var74;
            if(var7 != null) {
               var74 = var7.toString();
            } else {
               var74 = null;
            }

            var26.put(var73, var74);
            SQLiteDatabase var78 = LocalStore.this.mDb;
            String[] var79 = new String[1];
            String var80 = Long.toString(var5);
            var79[0] = var80;
            String var82 = "attachments";
            String var84 = "id = ?";
            var78.update(var82, var26, var84, var79);
         }

         if(var3 instanceof LocalStore.LocalAttachmentBodyPart) {
            LocalStore.LocalAttachmentBodyPart var87 = (LocalStore.LocalAttachmentBodyPart)var3;
            var87.setAttachmentId(var5);
         }
      }

      public void appendMessages(Message[] var1) throws MessagingException {
         this.appendMessages(var1, (boolean)0);
      }

      public void appendMessages(Message[] param1, boolean param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public boolean canCreate(Folder.FolderType var1) {
         return true;
      }

      public void changeUid(LocalStore.LocalMessage var1) throws MessagingException {
         Folder.OpenMode var2 = Folder.OpenMode.READ_WRITE;
         this.open(var2);
         ContentValues var3 = new ContentValues();
         String var4 = var1.getUid();
         var3.put("uid", var4);
         SQLiteDatabase var5 = LocalStore.this.mDb;
         String[] var6 = new String[1];
         String var7 = Long.toString(var1.mId);
         var6[0] = var7;
         var5.update("messages", var3, "id = ?", var6);
      }

      public void close(boolean var1) throws MessagingException {
         if(var1) {
            Message[] var2 = this.expunge();
         }

         this.mFolderId = 65535L;
      }

      public void copyMessages(Message[] var1, Folder var2, Folder.MessageUpdateCallbacks var3) throws MessagingException {
         if(!(var2 instanceof LocalStore.LocalFolder)) {
            throw new MessagingException("copyMessages called with incorrect Folder");
         } else {
            ((LocalStore.LocalFolder)var2).appendMessages(var1, (boolean)1);
         }
      }

      public boolean create(Folder.FolderType var1) throws MessagingException {
         if(this.exists()) {
            StringBuilder var2 = (new StringBuilder()).append("Folder ");
            String var3 = this.mName;
            String var4 = var2.append(var3).append(" already exists.").toString();
            throw new MessagingException(var4);
         } else {
            SQLiteDatabase var5 = LocalStore.this.mDb;
            Object[] var6 = new Object[2];
            String var7 = this.mName;
            var6[0] = var7;
            Integer var8 = Integer.valueOf(LocalStore.this.mVisibleLimitDefault);
            var6[1] = var8;
            var5.execSQL("INSERT INTO folders (name, visible_limit) VALUES (?, ?)", var6);
            return true;
         }
      }

      public Message createMessage(String var1) throws MessagingException {
         LocalStore var2 = LocalStore.this;
         return var2.new LocalMessage(var1, this);
      }

      public void delete(boolean var1) throws MessagingException {
         Folder.OpenMode var2 = Folder.OpenMode.READ_ONLY;
         this.open(var2);
         Message[] var3 = this.getMessages((Folder.MessageRetrievalListener)null);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5].getUid();
            this.deleteAttachments(var6);
         }

         SQLiteDatabase var7 = LocalStore.this.mDb;
         Object[] var8 = new Object[1];
         String var9 = Long.toString(this.mFolderId);
         var8[0] = var9;
         var7.execSQL("DELETE FROM folders WHERE id = ?", var8);
      }

      public boolean equals(Object var1) {
         boolean var4;
         if(var1 instanceof LocalStore.LocalFolder) {
            String var2 = ((LocalStore.LocalFolder)var1).mName;
            String var3 = this.mName;
            var4 = var2.equals(var3);
         } else {
            var4 = super.equals(var1);
         }

         return var4;
      }

      public boolean exists() throws MessagingException {
         return Utility.arrayContains(LocalStore.this.getPersonalNamespaces(), this);
      }

      public Message[] expunge() throws MessagingException {
         Folder.OpenMode var1 = Folder.OpenMode.READ_WRITE;
         this.open(var1);
         ArrayList var2 = new ArrayList();
         Message[] var3 = new Message[0];
         return (Message[])var2.toArray(var3);
      }

      public void fetch(Message[] param1, FetchProfile param2, Folder.MessageRetrievalListener param3) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public long getId() {
         return this.mFolderId;
      }

      public Message getMessage(String param1) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public int getMessageCount() throws MessagingException {
         return this.getMessageCount((Flag[])null, (Flag[])null);
      }

      public int getMessageCount(Flag[] param1, Flag[] param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public Message[] getMessages(int var1, int var2, Folder.MessageRetrievalListener var3) throws MessagingException {
         Folder.OpenMode var4 = Folder.OpenMode.READ_WRITE;
         this.open(var4);
         throw new MessagingException("LocalStore.getMessages(int, int, MessageRetrievalListener) not yet implemented");
      }

      public Message[] getMessages(Folder.MessageRetrievalListener param1) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public Message[] getMessages(Flag[] param1, Flag[] param2, Folder.MessageRetrievalListener param3) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public Message[] getMessages(String[] var1, Folder.MessageRetrievalListener var2) throws MessagingException {
         Folder.OpenMode var3 = Folder.OpenMode.READ_WRITE;
         this.open(var3);
         Message[] var4;
         if(var1 == null) {
            var4 = this.getMessages(var2);
         } else {
            ArrayList var5 = new ArrayList();
            String[] var6 = var1;
            int var7 = var1.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String var9 = var6[var8];
               Message var10 = this.getMessage(var9);
               var5.add(var10);
            }

            Message[] var12 = new Message[0];
            var4 = (Message[])var5.toArray(var12);
         }

         return var4;
      }

      public Folder.OpenMode getMode() throws MessagingException {
         return Folder.OpenMode.READ_WRITE;
      }

      public String getName() {
         return this.mName;
      }

      public Flag[] getPermanentFlags() throws MessagingException {
         return LocalStore.PERMANENT_FLAGS;
      }

      public Folder.PersistentDataCallbacks getPersistentCallbacks() throws MessagingException {
         Folder.OpenMode var1 = Folder.OpenMode.READ_WRITE;
         this.open(var1);
         return this;
      }

      public String getPersistentString(String var1, String var2) {
         LocalStore var3 = LocalStore.this;
         long var4 = this.mFolderId;
         return LocalStore.access$800(var3, var4, var1, var2);
      }

      public int getUnreadMessageCount() throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public int getVisibleLimit() throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public boolean isOpen() {
         boolean var1;
         if(this.mFolderId != 65535L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void open(Folder.OpenMode param1, Folder.PersistentDataCallbacks param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void setFlags(Message[] var1, Flag[] var2, boolean var3) throws MessagingException {
         Folder.OpenMode var4 = Folder.OpenMode.READ_WRITE;
         this.open(var4);
         Message[] var5 = var1;
         int var6 = var1.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            var5[var7].setFlags(var2, var3);
         }

      }

      public void setPersistentString(String var1, String var2) {
         LocalStore var3 = LocalStore.this;
         long var4 = this.mFolderId;
         LocalStore.access$900(var3, var4, var1, var2);
      }

      public void setPersistentStringAndMessageFlags(String param1, String param2, Flag[] param3, Flag[] param4) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void setUnreadMessageCount(int var1) throws MessagingException {
         Folder.OpenMode var2 = Folder.OpenMode.READ_WRITE;
         this.open(var2);
         int var3 = Math.max(0, var1);
         this.mUnreadMessageCount = var3;
         SQLiteDatabase var4 = LocalStore.this.mDb;
         Object[] var5 = new Object[2];
         Integer var6 = Integer.valueOf(this.mUnreadMessageCount);
         var5[0] = var6;
         Long var7 = Long.valueOf(this.mFolderId);
         var5[1] = var7;
         var4.execSQL("UPDATE folders SET unread_count = ? WHERE id = ?", var5);
      }

      public void setVisibleLimit(int var1) throws MessagingException {
         Folder.OpenMode var2 = Folder.OpenMode.READ_WRITE;
         this.open(var2);
         this.mVisibleLimit = var1;
         SQLiteDatabase var3 = LocalStore.this.mDb;
         Object[] var4 = new Object[2];
         Integer var5 = Integer.valueOf(this.mVisibleLimit);
         var4[0] = var5;
         Long var6 = Long.valueOf(this.mFolderId);
         var4[1] = var6;
         var3.execSQL("UPDATE folders SET visible_limit = ? WHERE id = ?", var4);
      }

      public void updateMessage(LocalStore.LocalMessage param1) throws MessagingException {
         // $FF: Couldn't be decompiled
      }
   }

   public class LocalAttachmentBodyPart extends MimeBodyPart {

      private long mAttachmentId = 65535L;


      public LocalAttachmentBodyPart(Body var2, long var3) throws MessagingException {
         super(var2);
         this.mAttachmentId = var3;
      }

      public long getAttachmentId() {
         return this.mAttachmentId;
      }

      public void setAttachmentId(long var1) {
         this.mAttachmentId = var1;
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("");
         long var2 = this.mAttachmentId;
         return var1.append(var2).toString();
      }
   }

   public class LocalMessage extends MimeMessage {

      private int mAttachmentCount;
      private long mId;


      LocalMessage(String var2, Folder var3) throws MessagingException {
         this.mUid = var2;
         this.mFolder = var3;
      }

      // $FF: synthetic method
      static long access$302(LocalStore.LocalMessage var0, long var1) {
         var0.mId = var1;
         return var1;
      }

      // $FF: synthetic method
      static int access$502(LocalStore.LocalMessage var0, int var1) {
         var0.mAttachmentCount = var1;
         return var1;
      }

      public int getAttachmentCount() {
         return this.mAttachmentCount;
      }

      public long getId() {
         return this.mId;
      }

      public void parse(InputStream var1) throws IOException, MessagingException {
         super.parse(var1);
      }

      public void setFlag(Flag param1, boolean param2) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void setFlagInternal(Flag var1, boolean var2) throws MessagingException {
         super.setFlag(var1, var2);
      }
   }

   public static class PendingCommand {

      public String[] arguments;
      public String command;
      private long mId;


      public PendingCommand() {}

      // $FF: synthetic method
      static long access$002(LocalStore.PendingCommand var0, long var1) {
         var0.mId = var1;
         return var1;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         String var2 = this.command;
         var1.append(var2);
         StringBuffer var4 = var1.append("\n");
         String[] var5 = this.arguments;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            StringBuffer var9 = var1.append("  ");
            var1.append(var8);
            StringBuffer var11 = var1.append("\n");
         }

         return var1.toString();
      }
   }

   public static class LocalAttachmentBody implements Body {

      private Context mContext;
      private Uri mUri;


      public LocalAttachmentBody(Uri var1, Context var2) {
         this.mContext = var2;
         this.mUri = var1;
      }

      public Uri getContentUri() {
         return this.mUri;
      }

      public InputStream getInputStream() throws MessagingException {
         InputStream var3;
         Object var4;
         try {
            ContentResolver var1 = this.mContext.getContentResolver();
            Uri var2 = this.mUri;
            var3 = var1.openInputStream(var2);
         } catch (FileNotFoundException var8) {
            byte[] var6 = new byte[0];
            var4 = new ByteArrayInputStream(var6);
            return (InputStream)var4;
         } catch (IOException var9) {
            throw new MessagingException("Invalid attachment.", var9);
         }

         var4 = var3;
         return (InputStream)var4;
      }

      public void writeTo(OutputStream var1) throws IOException, MessagingException {
         InputStream var2 = this.getInputStream();
         Base64OutputStream var3 = new Base64OutputStream(var1, 20);
         IOUtils.copy(var2, (OutputStream)var3);
         var3.close();
      }
   }
}
