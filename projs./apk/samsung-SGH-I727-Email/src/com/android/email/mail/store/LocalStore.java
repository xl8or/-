package com.android.email.mail.store;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.util.Log;
import com.android.email.Email;
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
import com.android.email.mail.internet.MimeMultipart;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.mail.internet.TextBody;
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
   private static final String TAG = "LocalStore >>";
   private File mAttachmentsDir;
   private Context mContext;
   private SQLiteDatabase mDb;
   private String mPath;
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
         if(var7 != null && !var7.exists()) {
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
            StringBuffer var8 = new StringBuffer();
            byte var9 = 0;
            byte var10 = 0;
            byte var11 = 0;
            if(var6 != null) {
               String var12 = Flag.X_DOWNLOADED_FULL.toString();
               if(var6.contains(var12)) {
                  var9 = 1;
               }

               String var15 = Flag.X_DOWNLOADED_PARTIAL.toString();
               if(var6.contains(var15)) {
                  var10 = 1;
               }

               String var18 = Flag.DELETED.toString();
               if(var6.contains(var18)) {
                  var11 = 1;
               }
            }

            Integer var21 = Integer.valueOf(var9);
            String var23 = "flag_downloaded_full";
            var7.put(var23, var21);
            Integer var25 = Integer.valueOf(var10);
            String var27 = "flag_downloaded_partial";
            var7.put(var27, var25);
            Integer var29 = Integer.valueOf(var11);
            String var31 = "flag_deleted";
            var7.put(var31, var29);
            int var33 = var3.getInt(var4);
            SQLiteDatabase var34 = this.mDb;
            String var36 = "id=";
            StringBuffer var37 = var8.append(var36);
            String var39 = var37.append(var33).toString();
            String var41 = "messages";
            Object var44 = null;
            var34.update(var41, var7, var39, (String[])var44);
            int var46 = var8.length();
            byte var48 = 0;
            var8.delete(var48, var46);
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

   public void removeFolder(String var1) {}

   public void removePendingCommand(LocalStore.PendingCommand var1) {
      SQLiteDatabase var2 = this.mDb;
      String[] var3 = new String[1];
      String var4 = Long.toString(var1.mId);
      var3[0] = var4;
      var2.delete("pending_commands", "id = ?", var3);
   }

   public void renameFolder(String var1, String var2) {}

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
      private String mName;
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
         StringBuffer var4 = new StringBuffer();
         Flag[] var5;
         int var6;
         int var7;
         if(var2 != null) {
            var5 = var2;
            var6 = var2.length;

            for(var7 = 0; var7 < var6; ++var7) {
               Flag var8 = var5[var7];
               Flag var9 = Flag.X_STORE_1;
               if(var8 == var9) {
                  StringBuilder var10 = var1.append("store_flag_1 = 1 AND ");
               } else {
                  Flag var11 = Flag.X_STORE_2;
                  if(var8 == var11) {
                     StringBuilder var12 = var1.append("store_flag_2 = 1 AND ");
                  } else {
                     Flag var13 = Flag.X_DOWNLOADED_FULL;
                     if(var8 == var13) {
                        StringBuilder var14 = var1.append("flag_downloaded_full = 1 AND ");
                     } else {
                        Flag var15 = Flag.X_DOWNLOADED_PARTIAL;
                        if(var8 == var15) {
                           StringBuilder var16 = var1.append("flag_downloaded_partial = 1 AND ");
                        } else {
                           Flag var17 = Flag.DELETED;
                           if(var8 != var17) {
                              int var19 = var4.length();
                              var4.delete(0, var19);
                              String var21 = var4.append("Unsupported flag ").append(var8).toString();
                              throw new MessagingException(var21);
                           }

                           StringBuilder var18 = var1.append("flag_deleted = 1 AND ");
                        }
                     }
                  }
               }
            }
         }

         if(var3 != null) {
            var5 = var3;
            var6 = var3.length;

            for(var7 = 0; var7 < var6; ++var7) {
               Flag var22 = var5[var7];
               Flag var23 = Flag.X_STORE_1;
               if(var22 == var23) {
                  StringBuilder var24 = var1.append("store_flag_1 = 0 AND ");
               } else {
                  Flag var25 = Flag.X_STORE_2;
                  if(var22 == var25) {
                     StringBuilder var26 = var1.append("store_flag_2 = 0 AND ");
                  } else {
                     Flag var27 = Flag.X_DOWNLOADED_FULL;
                     if(var22 == var27) {
                        StringBuilder var28 = var1.append("flag_downloaded_full = 0 AND ");
                     } else {
                        Flag var29 = Flag.X_DOWNLOADED_PARTIAL;
                        if(var22 == var29) {
                           StringBuilder var30 = var1.append("flag_downloaded_partial = 0 AND ");
                        } else {
                           Flag var31 = Flag.DELETED;
                           if(var22 != var31) {
                              int var33 = var4.length();
                              var4.delete(0, var33);
                              String var35 = var4.append("Unsupported flag ").append(var22).toString();
                              throw new MessagingException(var35);
                           }

                           StringBuilder var32 = var1.append("flag_deleted = 0 AND ");
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
         Email.loge("LocalStore >>", "saveSttachment ||");
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
               InputStream var93 = var3.getBody().getInputStream();
               File var94 = LocalStore.this.mAttachmentsDir;
               var9 = File.createTempFile("att", (String)null, var94);
               FileOutputStream var95 = new FileOutputStream(var9);
               if(var93 != null) {
                  var8 = IOUtils.copy(var93, (OutputStream)var95);
                  var93.close();
               }

               if(var95 != null) {
                  var95.close();
               }
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
         StringBuilder var25 = (new StringBuilder()).append("saveAttachment || name : ");
         String var27 = var25.append(var24).toString();
         Email.loge("LocalStore >>", var27);
         if(var24 == null) {
            Email.loge("LocalStore >>", "set default name +++++++++++++++++++++++++++++++++++++++++++++++++");
            var24 = "Unknown";
         }

         String var28 = var3.getContentId();
         ContentValues var29;
         if(65535L == 65535L) {
            var29 = new ContentValues();
            Long var30 = Long.valueOf(var1);
            String var32 = "message_id";
            var29.put(var32, var30);
            String var34 = "content_uri";
            String var35;
            if(var7 != null) {
               var35 = var7.toString();
            } else {
               var35 = null;
            }

            var29.put(var34, var35);
            String var40 = "store_data";
            var29.put(var40, var23);
            Integer var42 = Integer.valueOf(var8);
            String var44 = "size";
            var29.put(var44, var42);
            String var47 = "name";
            var29.put(var47, var24);
            String var49 = var3.getMimeType();
            String var51 = "mime_type";
            var29.put(var51, var49);
            String var54 = "content_id";
            var29.put(var54, var28);
            SQLiteDatabase var56 = LocalStore.this.mDb;
            String var57 = "attachments";
            String var58 = "message_id";
            var5 = var56.insert(var57, var58, var29);
         } else {
            var29 = new ContentValues();
            String var98 = "content_uri";
            String var99;
            if(var7 != null) {
               var99 = var7.toString();
            } else {
               var99 = null;
            }

            var29.put(var98, var99);
            Integer var103 = Integer.valueOf(var8);
            String var105 = "size";
            var29.put(var105, var103);
            String var108 = "content_id";
            var29.put(var108, var28);
            Long var110 = Long.valueOf(var1);
            String var112 = "message_id";
            var29.put(var112, var110);
            SQLiteDatabase var114 = LocalStore.this.mDb;
            String[] var115 = new String[1];
            String var116 = Long.toString(65535L);
            var115[0] = var116;
            String var118 = "attachments";
            String var120 = "id = ?";
            var114.update(var118, var29, var120, var115);
         }

         if(var9 != null) {
            File var60 = new File;
            File var61 = LocalStore.this.mAttachmentsDir;
            String var62 = Long.toString(var5);
            var60.<init>(var61, var62);
            boolean var68 = var9.renameTo(var60);
            LocalStore.LocalAttachmentBody var69 = new LocalStore.LocalAttachmentBody;
            Context var70 = LocalStore.this.mContext;
            var69.<init>(var7, var70);
            var3.setBody(var69);
            var29 = new ContentValues();
            String var76 = "content_uri";
            String var77;
            if(var7 != null) {
               var77 = var7.toString();
            } else {
               var77 = null;
            }

            var29.put(var76, var77);
            SQLiteDatabase var81 = LocalStore.this.mDb;
            String[] var82 = new String[1];
            String var83 = Long.toString(var5);
            var82[0] = var83;
            String var85 = "attachments";
            String var87 = "id = ?";
            var81.update(var85, var29, var87, var82);
         }

         if(var3 instanceof LocalStore.LocalAttachmentBodyPart) {
            LocalStore.LocalAttachmentBodyPart var90 = (LocalStore.LocalAttachmentBodyPart)var3;
            var90.setAttachmentId(var5);
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

      public boolean delete(boolean var1) throws MessagingException {
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
         return true;
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

      public void fetch(Message[] var1, FetchProfile var2, Folder.MessageRetrievalListener var3) throws MessagingException {
         Folder.OpenMode var4 = Folder.OpenMode.READ_WRITE;
         this.open(var4);
         FetchProfile.Item var7 = FetchProfile.Item.BODY;
         if(!var2.contains(var7)) {
            FetchProfile.Item var10 = FetchProfile.Item.STRUCTURE;
            if(!var2.contains(var10)) {
               return;
            }
         }

         Message[] var13 = var1;
         int var14 = var1.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            LocalStore.LocalMessage var18 = (LocalStore.LocalMessage)var13[var15];
            Cursor var19 = null;
            String var21 = "Content-Type";
            String var22 = "multipart/mixed";
            var18.setHeader(var21, var22);
            MimeMultipart var23 = new MimeMultipart();
            Object var24 = null;
            String var26 = "mixed";
            var23.setSubType(var26);
            var18.setBody(var23);
            FetchProfile.Item var29 = FetchProfile.Item.BODY;
            if(var2.contains(var29)) {
               boolean var135 = false;

               StringBuffer var32;
               try {
                  var135 = true;
                  var32 = new StringBuffer();
                  var135 = false;
               } finally {
                  if(var135) {
                     if(var19 != null) {
                        var19.close();
                     }

                     if(var24 != null) {
                        int var115 = ((StringBuffer)var24).length();
                        byte var117 = 0;
                        ((StringBuffer)var24).delete(var117, var115);
                     }

                  }
               }

               try {
                  SQLiteDatabase var33 = LocalStore.this.mDb;
                  String var35 = "SELECT html_content, text_content FROM messages ";
                  String var36 = var32.append(var35).append("WHERE id = ?").toString();
                  String[] var37 = new String[1];
                  String var38 = Long.toString(var18.mId);
                  var37[0] = var38;
                  var19 = var33.rawQuery(var36, var37);
                  boolean var39 = var19.moveToNext();
                  byte var41 = 0;
                  String var42 = var19.getString(var41);
                  byte var44 = 1;
                  String var45 = var19.getString(var44);
                  if(var42 != null) {
                     TextBody var46 = new TextBody(var42);
                     MimeBodyPart var49 = new MimeBodyPart(var46, "text/html");
                     var23.addBodyPart(var49);
                  }

                  if(var45 != null) {
                     TextBody var52 = new TextBody(var45);
                     MimeBodyPart var55 = new MimeBodyPart(var52, "text/plain");
                     var23.addBodyPart(var55);
                  }
               } finally {
                  ;
               }

               if(var19 != null) {
                  var19.close();
               }

               if(var32 != null) {
                  int var58 = var32.length();
                  byte var60 = 0;
                  var32.delete(var60, var58);
               }
            } else {
               MimeBodyPart var120 = new MimeBodyPart();
               var120.setHeader("Content-Type", "text/html;\n charset=\"UTF-8\"");
               var23.addBodyPart(var120);
               MimeBodyPart var123 = new MimeBodyPart();
               var123.setHeader("Content-Type", "text/plain;\n charset=\"UTF-8\"");
               var23.addBodyPart(var123);
            }

            try {
               SQLiteDatabase var64 = LocalStore.this.mDb;
               String[] var65 = new String[]{"id", "size", "name", "mime_type", "store_data", "content_uri", "content_id"};
               String[] var66 = new String[1];
               String var67 = Long.toString(var18.mId);
               var66[0] = var67;
               var19 = var64.query("attachments", var65, "message_id = ?", var66, (String)null, (String)null, (String)null);

               while(var19.moveToNext()) {
                  byte var69 = 0;
                  long var70 = var19.getLong(var69);
                  byte var73 = 1;
                  int var74 = var19.getInt(var73);
                  byte var76 = 2;
                  String var77 = var19.getString(var76);
                  byte var79 = 3;
                  String var80 = var19.getString(var79);
                  byte var82 = 4;
                  String var83 = var19.getString(var82);
                  byte var85 = 5;
                  String var86 = var19.getString(var85);
                  byte var88 = 6;
                  String var89 = var19.getString(var88);
                  LocalStore.LocalAttachmentBody var90 = null;
                  if(var86 != null) {
                     Uri var91 = Uri.parse(var86);
                     Context var92 = LocalStore.this.mContext;
                     var90 = new LocalStore.LocalAttachmentBody(var91, var92);
                  }

                  LocalStore.LocalAttachmentBodyPart var93 = new LocalStore.LocalAttachmentBodyPart;
                  LocalStore var94 = LocalStore.this;
                  var93.<init>(var90, var70);
                  Object[] var100 = new Object[]{var80, var77};
                  String var101 = String.format("%s;\n name=\"%s\"", var100);
                  var93.setHeader("Content-Type", var101);
                  var93.setHeader("Content-Transfer-Encoding", "base64");
                  Object[] var102 = new Object[]{var77, null};
                  Integer var103 = Integer.valueOf(var74);
                  var102[1] = var103;
                  String var104 = String.format("attachment;\n filename=\"%s\";\n size=%d", var102);
                  var93.setHeader("Content-Disposition", var104);
                  String var106 = "Content-ID";
                  var93.setHeader(var106, var89);
                  String var109 = "X-Android-Attachment-StoreData";
                  var93.setHeader(var109, var83);
                  var23.addBodyPart(var93);
               }
            } finally {
               if(var19 != null) {
                  var19.close();
               }

            }
         }

      }

      public int getDelimiter() {
         return 0;
      }

      public String getFolderPath() {
         return this.mName;
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

      public Message[] getMessages(int var1, String var2, Folder.MessageRetrievalListener var3) {
         return null;
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

      public String getParentFolderName() {
         return null;
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

      public boolean getSelect() {
         return true;
      }

      public int getUnreadMessageCount() throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public int getVisibleLimit() throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public int hashCode() {
         return super.hashCode();
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

      public void reConnect(boolean var1) {}

      public boolean rename(String var1) throws MessagingException {
         return false;
      }

      public void setDelimiter(int var1) {}

      public void setFlags(Message[] var1, Flag[] var2, boolean var3) throws MessagingException {
         Folder.OpenMode var4 = Folder.OpenMode.READ_WRITE;
         this.open(var4);
         Message[] var5 = var1;
         int var6 = var1.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            var5[var7].setFlags(var2, var3);
         }

      }

      public void setFolderPath(String var1) {}

      public void setParentFolderName(String var1) {}

      public void setPersistentString(String var1, String var2) {
         LocalStore var3 = LocalStore.this;
         long var4 = this.mFolderId;
         LocalStore.access$900(var3, var4, var1, var2);
      }

      public void setPersistentStringAndMessageFlags(String param1, String param2, Flag[] param3, Flag[] param4) throws MessagingException {
         // $FF: Couldn't be decompiled
      }

      public void setSelect(boolean var1) {}

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
         return Long.toString(this.mAttachmentId);
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
         StringBuffer var4 = var1.append('\n');
         String[] var5 = this.arguments;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            StringBuffer var9 = var1.append("  ");
            var1.append(var8);
            StringBuffer var11 = var1.append('\n');
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

      public void writeTo(Context var1, long var2, OutputStream var4) throws IOException, MessagingException {}

      public void writeTo(OutputStream param1) throws IOException, MessagingException {
         // $FF: Couldn't be decompiled
      }
   }
}
