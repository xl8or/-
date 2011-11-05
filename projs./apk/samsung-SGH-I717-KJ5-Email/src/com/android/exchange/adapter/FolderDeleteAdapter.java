package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.AbstractCommandAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Parser;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;

public class FolderDeleteAdapter extends AbstractCommandAdapter {

   public final int CODE_FOLDER_CREATE_ACCESS_DENIED = 7;
   public final int CODE_FOLDER_DELETE_FOLDER_NOT_EXISTS = 4;
   public final int CODE_FOLDER_DELETE_INVALID_SYNC_KEY = 9;
   public final int CODE_FOLDER_DELETE_MALFORMED_REQUEST = 10;
   public final int CODE_FOLDER_DELETE_SERVER_ERROR = 6;
   public final int CODE_FOLDER_DELETE_SUCCESS = 1;
   public final int CODE_FOLDER_DELETE_SYSTEM_FOLDER = 3;
   public final int CODE_FOLDER_DELETE_TIME_OUT = 8;
   public final int CODE_FOLDER_DELETE_UNKNOWN_ERROR = 11;
   private final String[] FOLDER_DELETE_PROJECTION;
   private int INDEX_MAILBOX_FLAGCHANGED;
   private int INDEX_MAILBOX_ID;
   private int INDEX_SERVER_ID;
   private int INDEX_SYNC_KEY;
   private final String WHERE_PARENT_SERVER_ID_AND_ACCOUNT;
   private final String WHERE_SERVER_ID_AND_ACCOUNT;
   AbstractCommandAdapter.FolderCommandResponse mResponse;
   private String mServerId;
   private int thisMailboxChanged;
   private long thisMailboxId;


   public FolderDeleteAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
      String[] var3 = new String[]{"syncKey", "serverId", "_id", "flagChanged"};
      this.FOLDER_DELETE_PROJECTION = var3;
      this.WHERE_SERVER_ID_AND_ACCOUNT = "serverId=? and accountKey=?";
      this.WHERE_PARENT_SERVER_ID_AND_ACCOUNT = "parentServerId=? and accountKey=?";
      this.INDEX_SYNC_KEY = 0;
      this.INDEX_SERVER_ID = 1;
      this.INDEX_MAILBOX_ID = 2;
      this.INDEX_MAILBOX_FLAGCHANGED = 3;
      this.mResponse = null;
      this.mServerId = null;
      this.thisMailboxId = 65535L;
      this.thisMailboxChanged = 0;
      int var4 = Log.i("Mahskyript", "FolderDeleteAdapter.FolderDeleteAdapter");
   }

   // $FF: synthetic method
   static int access$272(FolderDeleteAdapter var0, int var1) {
      int var2 = var0.thisMailboxChanged & var1;
      var0.thisMailboxChanged = var2;
      return var2;
   }

   public void callback(int var1) {
      int var2 = Log.i("Mahskyript", "FolderDeleteAdapter.callback ");

      try {
         IEmailServiceCallback var3 = SyncManager.callback();
         long var4 = this.mMailbox.mId;
         var3.folderCommandStatus(1, var4, var1);
      } catch (RemoteException var7) {
         ;
      }
   }

   public void cleanup() {}

   public boolean commit() throws IOException {
      return false;
   }

   public String getCollectionName() {
      return null;
   }

   public String getCommandName() {
      int var1 = Log.i("Mahskyript", "FolderDeleteAdapter.getCommandName: FolderDelete");
      return "FolderDelete";
   }

   public boolean hasChangedItems() {
      int var1 = 0;
      ContentResolver var2 = this.mContext.getContentResolver();
      Uri var3 = EmailContent.Mailbox.CONTENT_URI;
      String[] var4 = this.FOLDER_DELETE_PROJECTION;
      StringBuilder var5 = (new StringBuilder()).append("accountKey=");
      long var6 = this.mMailbox.mAccountKey;
      String var8 = var5.append(var6).append(" AND ").append("flagChanged").append("&").append(1).append("=").append(1).toString();
      Object var9 = null;
      Cursor var10 = var2.query(var3, var4, var8, (String[])null, (String)var9);
      if(var10 != null) {
         boolean var17 = false;

         int var11;
         try {
            var17 = true;
            var11 = var10.getCount();
            var17 = false;
         } finally {
            if(var17) {
               var10.close();
            }
         }

         var1 = var11;
         var10.close();
      }

      String var12 = "FolderDeleteAdapter.hasChangedItems count: " + var1;
      int var13 = Log.i("Mahskyript", var12);
      boolean var14;
      if(var1 > 0) {
         var14 = true;
      } else {
         var14 = false;
      }

      return var14;
   }

   public boolean isSyncable() {
      int var1 = Log.i("Mahskyript", "FolderDeleteAdapter.isSyncable");
      return false;
   }

   public boolean parse(InputStream var1) throws IOException, DeviceAccessException {
      return (new FolderDeleteAdapter.FolderDeleteParser(var1, this)).parse();
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      ContentResolver var2 = this.mContext.getContentResolver();
      Uri var3 = EmailContent.Mailbox.CONTENT_URI;
      String[] var4 = this.FOLDER_DELETE_PROJECTION;
      StringBuilder var5 = (new StringBuilder()).append("accountKey=");
      long var6 = this.mMailbox.mAccountKey;
      String var8 = var5.append(var6).append(" AND ").append("flagChanged").append("&").append(1).append("=").append(1).toString();
      Object var9 = null;
      Cursor var10 = var2.query(var3, var4, var8, (String[])null, (String)var9);
      boolean var27;
      if(var10 != null) {
         label47: {
            try {
               if(!var10.moveToNext()) {
                  break label47;
               }

               int var11 = this.INDEX_SERVER_ID;
               String var12 = var10.getString(var11);
               this.mServerId = var12;
               int var13 = this.INDEX_MAILBOX_ID;
               long var14 = var10.getLong(var13);
               this.thisMailboxId = var14;
               int var16 = this.INDEX_MAILBOX_FLAGCHANGED;
               int var17 = var10.getInt(var16);
               this.thisMailboxChanged = var17;
               Serializer var18 = var1.start(468);
               String var19 = this.mAccount.mSyncKey;
               Serializer var20 = var18.data(466, var19);
               String var21 = this.mServerId;
               Serializer var22 = var20.data(456, var21).end();
               StringBuilder var23 = (new StringBuilder()).append("FolderDeleteAdapter.sendLocalChanges. Deleting item serverId:");
               String var24 = this.mServerId;
               String var25 = var23.append(var24).toString();
               int var26 = Log.i("Mahskyript", var25);
            } finally {
               var10.close();
            }

            var27 = true;
            return var27;
         }
      }

      int var29 = Log.i("Mahskyript", "FolderDeleteAdapter.sendLocalChanges Nothing to delete");
      var27 = false;
      return var27;
   }

   public class FolderDeleteParser extends AbstractSyncParser {

      public FolderDeleteParser(InputStream var2, AbstractCommandAdapter var3) throws IOException {
         super(var2, var3);
         int var4 = Log.i("Mahskyript", "FolderDeleteAdapter.FolderDeleteParser.FolderDeleteParser");
      }

      public void commandsParser() throws IOException {}

      public void commit() throws IOException {
         // $FF: Couldn't be decompiled
      }

      public void itemOperationsResponsesParser() throws IOException {}

      public void moveResponseParser() throws IOException {
         int var1 = Log.i("Mahskyript", "FolderDeleteAdapter.FolderDeleteParser.moveResponseParser");
      }

      public boolean parse() throws IOException, DeviceAccessException {
         int var1 = Log.i("Mahskyript", "FolderDeleteAdapter.FolderDeleteParser.parse ");
         FolderDeleteAdapter var2 = FolderDeleteAdapter.this;
         FolderDeleteAdapter var3 = FolderDeleteAdapter.this;
         AbstractCommandAdapter.FolderCommandResponse var4 = var3.new FolderCommandResponse();
         var2.mResponse = var4;
         if(this.nextTag(0) != 468) {
            throw new Parser.EasParserException();
         } else {
            while(this.nextTag(0) != 3) {
               if(this.tag == 466) {
                  AbstractCommandAdapter.FolderCommandResponse var5 = FolderDeleteAdapter.this.mResponse;
                  String var6 = this.getValue();
                  var5.mSyncKey = var6;
               } else if(this.tag == 460) {
                  AbstractCommandAdapter.FolderCommandResponse var7 = FolderDeleteAdapter.this.mResponse;
                  int var8 = this.getValueInt();
                  var7.mStatus = var8;
                  switch(FolderDeleteAdapter.this.mResponse.mStatus) {
                  case 1:
                     break;
                  case 3:
                     FolderDeleteAdapter.this.callback(35);
                     break;
                  case 4:
                     FolderDeleteAdapter.this.callback(36);
                     break;
                  case 6:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                     FolderDeleteAdapter.this.callback(34);
                     break;
                  case 129:
                     FolderDeleteAdapter.this.callback(3);
                     throw new DeviceAccessException(262145, 2131166819);
                  default:
                     throw new IOException("Unknown error.");
                  }
               }
            }

            if(FolderDeleteAdapter.this.mResponse.mStatus == 1) {
               this.commit();
            } else if(FolderDeleteAdapter.this.mResponse.mStatus == 4) {
               if(FolderDeleteAdapter.this.mServerId != null) {
                  StringBuilder var10 = (new StringBuilder()).append("Folder does not exist. Deleting mailbox serverId:");
                  String var11 = FolderDeleteAdapter.this.mServerId;
                  String var12 = var10.append(var11).toString();
                  int var13 = Log.d("FolderDeleteAdapter", var12);
                  ContentResolver var14 = this.mContentResolver;
                  Uri var15 = EmailContent.Mailbox.CONTENT_URI;
                  StringBuilder var16 = (new StringBuilder()).append("accountKey=");
                  long var17 = this.mMailbox.mAccountKey;
                  StringBuilder var19 = var16.append(var17).append(" AND ").append("serverId").append("=");
                  String var20 = FolderDeleteAdapter.this.mServerId;
                  String var21 = var19.append(var20).toString();
                  var14.delete(var15, var21, (String[])null);
               }
            } else {
               this.wipe();
            }

            int var9 = Log.i("Mahskyript", "FolderDeleteAdapter.FolderDeleteParser.parse exit");
            return false;
         }
      }

      public void responsesParser() throws IOException {}

      public void wipe() {
         if(FolderDeleteAdapter.this.thisMailboxId != 65535L) {
            if(this.mContentResolver != null) {
               StringBuilder var1 = (new StringBuilder()).append("wipe(). Marking this mailbox:");
               long var2 = FolderDeleteAdapter.this.thisMailboxId;
               String var4 = var1.append(var2).append(" as not deleted").toString();
               int var5 = Log.i("FolderDeleteAdapter", var4);
               int var6 = FolderDeleteAdapter.access$272(FolderDeleteAdapter.this, -1);
               ContentValues var7 = new ContentValues();
               Integer var8 = Integer.valueOf(FolderDeleteAdapter.this.thisMailboxChanged);
               var7.put("flagChanged", var8);
               ContentResolver var9 = this.mContentResolver;
               Uri var10 = EmailContent.Mailbox.CONTENT_URI;
               StringBuilder var11 = (new StringBuilder()).append("_id=");
               long var12 = FolderDeleteAdapter.this.thisMailboxId;
               String var14 = var11.append(var12).toString();
               var9.update(var10, var7, var14, (String[])null);
            }
         }
      }
   }
}
