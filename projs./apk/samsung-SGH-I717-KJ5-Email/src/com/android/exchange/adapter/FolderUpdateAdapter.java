package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
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

public class FolderUpdateAdapter extends AbstractCommandAdapter {

   public final int CODE_FOLDER_CREATE_ACCESS_DENIED = 7;
   public final int CODE_FOLDER_UPDATE_FOLDER_NOT_EXISTS = 4;
   public final int CODE_FOLDER_UPDATE_INVALID_SYNC_KEY = 9;
   public final int CODE_FOLDER_UPDATE_MALFORMED_REQUEST = 10;
   public final int CODE_FOLDER_UPDATE_NAME_EXISTS = 2;
   public final int CODE_FOLDER_UPDATE_PARENT_FOLDER_NOT_FOUND = 5;
   public final int CODE_FOLDER_UPDATE_SERVER_ERROR = 6;
   public final int CODE_FOLDER_UPDATE_SUCCESS = 1;
   public final int CODE_FOLDER_UPDATE_SYSTEM_FOLDER = 3;
   public final int CODE_FOLDER_UPDATE_TIME_OUT = 8;
   public final int CODE_FOLDER_UPDATE_UNKNOWN_ERROR = 11;
   private final String[] FOLDER_UPDATE_PROJECTION;
   private final int INDEX_DST_MAILBOX_ID;
   private final int INDEX_ID;
   private final int INDEX_NEW_DISPLAY_NAME;
   private final int INDEX_SERVER_ID;
   private final int INDEX_SYNC_KEY;
   private int mCommandType;
   private String mNewFolderName;
   private String mParentServerId;
   AbstractCommandAdapter.FolderCommandResponse mResponse;
   private String mServerId;
   private int mUpdatedMailboxId;


   public FolderUpdateAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
      String[] var3 = new String[]{"_id", "syncKey", "serverId", "dstMailboxId", "newDisplayName"};
      this.FOLDER_UPDATE_PROJECTION = var3;
      this.INDEX_ID = 0;
      this.INDEX_SYNC_KEY = 1;
      this.INDEX_SERVER_ID = 2;
      this.INDEX_DST_MAILBOX_ID = 3;
      this.INDEX_NEW_DISPLAY_NAME = 4;
      this.mServerId = null;
      this.mParentServerId = null;
      this.mNewFolderName = null;
      this.mResponse = null;
      int var4 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateAdapter");
   }

   // $FF: synthetic method
   static String access$100(FolderUpdateAdapter var0) {
      return var0.mNewFolderName;
   }

   // $FF: synthetic method
   static String access$200(FolderUpdateAdapter var0) {
      return var0.mParentServerId;
   }

   public void callback(int var1) {
      try {
         IEmailServiceCallback var2 = SyncManager.callback();
         long var3 = this.mMailbox.mId;
         var2.folderCommandStatus(2, var3, var1);
      } catch (RemoteException var6) {
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
      int var1 = Log.i("Mahskyript", "FolderUpdateAdapter.getCommandName: FolderUpdate");
      return "FolderUpdate";
   }

   public boolean hasChangedItems() {
      int var1 = 0;
      ContentResolver var2 = this.mContext.getContentResolver();
      Uri var3 = EmailContent.Mailbox.CONTENT_URI;
      String[] var4 = this.FOLDER_UPDATE_PROJECTION;
      StringBuilder var5 = (new StringBuilder()).append("accountKey=");
      long var6 = this.mMailbox.mAccountKey;
      String var8 = var5.append(var6).append(" AND ").append("flagChanged").append("&").append(2).append("=").append(2).toString();
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

      String var12 = "FolderUpdateAdapter.hasChangedItems count: " + var1;
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
      int var1 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateAdapter.isSyncable");
      return false;
   }

   public boolean parse(InputStream var1) throws IOException, DeviceAccessException {
      return (new FolderUpdateAdapter.FolderUpdateParser(var1, this)).parse();
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      ContentResolver var2 = this.mContext.getContentResolver();
      Uri var3 = EmailContent.Mailbox.CONTENT_URI;
      String[] var4 = this.FOLDER_UPDATE_PROJECTION;
      StringBuilder var5 = (new StringBuilder()).append("accountKey=");
      long var6 = this.mMailbox.mAccountKey;
      String var8 = var5.append(var6).append(" AND ").append("flagChanged").append("&").append(2).append("=").append(2).toString();
      Object var9 = null;
      Cursor var10 = var2.query(var3, var4, var8, (String[])null, (String)var9);
      boolean var33;
      if(var10 != null) {
         boolean var37 = false;

         label116: {
            try {
               var37 = true;
               if(!var10.moveToNext()) {
                  var37 = false;
                  break label116;
               }

               int var11 = var10.getInt(0);
               this.mUpdatedMailboxId = var11;
               String var12 = var10.getString(2);
               this.mServerId = var12;
               String var13 = var10.getString(3);
               this.mParentServerId = var13;
               String var14 = var10.getString(4);
               this.mNewFolderName = var14;
               Context var15 = this.mContext;
               long var16 = (long)this.mUpdatedMailboxId;
               EmailContent.Mailbox var18 = EmailContent.Mailbox.restoreMailboxWithId(var15, var16);
               if(var18 != null && this.mParentServerId == null) {
                  String var19 = var18.mParentServerId;
                  this.mParentServerId = var19;
               }

               if(var18 != null && this.mNewFolderName == null) {
                  String var20 = var18.mDisplayName;
                  this.mNewFolderName = var20;
               }

               String var21;
               if(this.mParentServerId == null) {
                  var21 = "0";
               } else {
                  var21 = this.mParentServerId;
               }

               if(TextUtils.isEmpty(this.mServerId)) {
                  var37 = false;
                  break label116;
               }

               if(TextUtils.isEmpty(this.mParentServerId) && TextUtils.isEmpty(this.mNewFolderName)) {
                  var37 = false;
                  break label116;
               }

               Serializer var22 = var1.start(469);
               String var23 = this.mAccount.mSyncKey;
               Serializer var24 = var22.data(466, var23);
               String var25 = this.mServerId;
               Serializer var26 = var24.data(456, var25).data(457, var21);
               String var27 = this.mNewFolderName;
               Serializer var28 = var26.data(455, var27).end();
               StringBuilder var29 = (new StringBuilder()).append("FolderUpdateAdapter.FolderUpdateAdapter.sendLocalChanges. updatedMailboxId:");
               int var30 = this.mUpdatedMailboxId;
               String var31 = var29.append(var30).toString();
               int var32 = Log.i("Mahskyript", var31);
               var37 = false;
            } finally {
               if(var37) {
                  var10.close();
               }
            }

            var33 = true;
            var10.close();
            return var33;
         }

         var10.close();
      }

      int var35 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateAdapter.sendLocalChanges exit 2");
      var33 = false;
      return var33;
   }

   public class FolderUpdateParser extends AbstractSyncParser {

      public FolderUpdateParser(InputStream var2, AbstractCommandAdapter var3) throws IOException {
         super(var2, var3);
         int var4 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateParser.FolderUpdateParser");
      }

      public void commandsParser() throws IOException {}

      public void commit() throws IOException {
         // $FF: Couldn't be decompiled
      }

      public void itemOperationsResponsesParser() throws IOException {}

      public void moveResponseParser() throws IOException {
         int var1 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateParser.moveResponseParser");
      }

      public boolean parse() throws IOException, DeviceAccessException {
         int var1 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateParser.parse ");
         FolderUpdateAdapter var2 = FolderUpdateAdapter.this;
         FolderUpdateAdapter var3 = FolderUpdateAdapter.this;
         AbstractCommandAdapter.FolderCommandResponse var4 = var3.new FolderCommandResponse();
         var2.mResponse = var4;
         if(this.nextTag(0) != 469) {
            throw new Parser.EasParserException();
         } else {
            while(this.nextTag(0) != 3) {
               if(this.tag == 466) {
                  AbstractCommandAdapter.FolderCommandResponse var5 = FolderUpdateAdapter.this.mResponse;
                  String var6 = this.getValue();
                  var5.mSyncKey = var6;
               } else if(this.tag == 460) {
                  AbstractCommandAdapter.FolderCommandResponse var7 = FolderUpdateAdapter.this.mResponse;
                  int var8 = this.getValueInt();
                  var7.mStatus = var8;
                  switch(FolderUpdateAdapter.this.mResponse.mStatus) {
                  case 1:
                     break;
                  case 2:
                     FolderUpdateAdapter.this.callback(37);
                     break;
                  case 3:
                     FolderUpdateAdapter.this.callback(35);
                     break;
                  case 4:
                     FolderUpdateAdapter.this.callback(36);
                     break;
                  case 5:
                     FolderUpdateAdapter.this.callback(38);
                     break;
                  case 6:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                     FolderUpdateAdapter.this.callback(34);
                     break;
                  case 129:
                     FolderUpdateAdapter.this.callback(3);
                     int var9 = Log.i("DeviceAccessException", "Received in FolderUpdateAdapter");
                     throw new DeviceAccessException(262145, 2131166819);
                  default:
                     throw new IOException("Unknown error.");
                  }
               }
            }

            if(FolderUpdateAdapter.this.mResponse.mStatus == 1) {
               this.commit();
            } else if(FolderUpdateAdapter.this.mResponse.mStatus == 4) {
               if(FolderUpdateAdapter.this.mServerId != null) {
                  StringBuilder var11 = (new StringBuilder()).append("Folder does not exist. Deleting mailbox serverId:");
                  String var12 = FolderUpdateAdapter.this.mServerId;
                  String var13 = var11.append(var12).toString();
                  int var14 = Log.d("FolderDeleteAdapter", var13);
                  ContentResolver var15 = this.mContentResolver;
                  Uri var16 = EmailContent.Mailbox.CONTENT_URI;
                  StringBuilder var17 = (new StringBuilder()).append("accountKey=");
                  long var18 = this.mMailbox.mAccountKey;
                  StringBuilder var20 = var17.append(var18).append(" AND ").append("serverId").append("=");
                  String var21 = FolderUpdateAdapter.this.mServerId;
                  String var22 = var20.append(var21).toString();
                  var15.delete(var16, var22, (String[])null);
               }
            } else {
               this.wipe();
            }

            int var10 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateParser.parse exit");
            return false;
         }
      }

      public void responsesParser() throws IOException {}

      public void wipe() {
         Context var1 = this.mContext;
         long var2 = (long)FolderUpdateAdapter.this.mUpdatedMailboxId;
         EmailContent.Mailbox var4 = EmailContent.Mailbox.restoreMailboxWithId(var1, var2);
         if(var4 != null && this.mContentResolver != null) {
            int var5 = var4.mFlagChanged & -3;
            var4.mFlagChanged = var5;
            var4.mDstServerId = null;
            var4.mNewDisplayName = null;
            ContentResolver var6 = this.mContentResolver;
            Uri var7 = EmailContent.Mailbox.CONTENT_URI;
            ContentValues var8 = var4.toContentValues();
            StringBuilder var9 = (new StringBuilder()).append("_id=");
            long var10 = var4.mId;
            String var12 = var9.append(var10).toString();
            var6.update(var7, var8, var12, (String[])null);
         }

         int var14 = Log.i("Mahskyript", "FolderUpdateAdapter.FolderUpdateParser.wipe finished");
      }
   }
}
