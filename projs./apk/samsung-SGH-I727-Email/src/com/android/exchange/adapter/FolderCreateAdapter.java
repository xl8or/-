package com.android.exchange.adapter;

import android.content.Context;
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

public class FolderCreateAdapter extends AbstractCommandAdapter {

   public final int CODE_FOLDER_CREATE_ACCESS_DENIED = 7;
   public final int CODE_FOLDER_CREATE_INVALID_SYNC_KEY = 9;
   public final int CODE_FOLDER_CREATE_MALFORMED_REQUEST = 10;
   public final int CODE_FOLDER_CREATE_NAME_EXISTS = 2;
   public final int CODE_FOLDER_CREATE_PARENT_FOLDER_NOT_FOUND = 5;
   public final int CODE_FOLDER_CREATE_SERVER_ERROR = 6;
   public final int CODE_FOLDER_CREATE_SUCCESS = 1;
   public final int CODE_FOLDER_CREATE_SYSTEM_FOLDER = 3;
   public final int CODE_FOLDER_CREATE_TIME_OUT = 8;
   public final int CODE_FOLDER_CREATE_UNKNOWN_CODE = 12;
   public final int CODE_FOLDER_CREATE_UNKNOWN_ERROR = 11;
   private String mNewFolderName = null;
   private long mParentFolderId = 65535L;
   AbstractCommandAdapter.FolderCommandResponse mResponse = null;


   public FolderCreateAdapter(EmailContent.Mailbox var1, EasSyncService var2, String var3, long var4) {
      super(var1, var2);
      int var6 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateAdapter");
      this.mNewFolderName = var3;
      this.mParentFolderId = var4;
   }

   // $FF: synthetic method
   static long access$000(FolderCreateAdapter var0) {
      return var0.mParentFolderId;
   }

   // $FF: synthetic method
   static long access$002(FolderCreateAdapter var0, long var1) {
      var0.mParentFolderId = var1;
      return var1;
   }

   // $FF: synthetic method
   static String access$100(FolderCreateAdapter var0) {
      return var0.mNewFolderName;
   }

   // $FF: synthetic method
   static String access$102(FolderCreateAdapter var0, String var1) {
      var0.mNewFolderName = var1;
      return var1;
   }

   public void callback(int var1) {
      int var2 = Log.i("Mahskyript", "FolderCreateAdapter.callback");

      try {
         IEmailServiceCallback var3 = SyncManager.callback();
         long var4 = this.mMailbox.mId;
         var3.folderCommandStatus(4, var4, var1);
      } catch (RemoteException var7) {
         ;
      }
   }

   public void cleanup() {
      int var1 = Log.i("Mahskyript", "FolderCreateAdapter.cleanup");
   }

   public boolean commit() throws IOException {
      int var1 = Log.i("Mahskyript", "FolderCreateAdapter.commit");
      return false;
   }

   public String getCollectionName() {
      int var1 = Log.i("Mahskyript", "FolderCreateAdapter.getCollectionName");
      return null;
   }

   public String getCommandName() {
      int var1 = Log.i("Mahskyript", "FolderCreateAdapter.getCommandName");
      return "FolderCreate";
   }

   public boolean hasChangedItems() {
      int var1 = Log.i("Mahskyript", "FolderCreateAdapter.hasChangedItems");
      boolean var2;
      if(this.mNewFolderName != null && this.mParentFolderId != 65535L) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isSyncable() {
      int var1 = Log.i("Mahskyript", "FolderCreateAdapter.isSyncable");
      return false;
   }

   public boolean parse(InputStream var1) throws IOException, DeviceAccessException {
      int var2 = Log.i("Mahskyript", "FolderCreateAdapter.parse");
      return (new FolderCreateAdapter.FolderCreateParser(var1, this)).parse();
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      int var2 = Log.i("Mahskyript", "FolderCreateAdapter.sendLocalChanges");
      Context var3 = this.mContext;
      long var4 = this.mParentFolderId;
      EmailContent.Mailbox var6 = EmailContent.Mailbox.restoreMailboxWithId(var3, var4);
      String var7;
      if(var6.mType != 68) {
         var7 = var6.mServerId;
      } else {
         var7 = "0";
      }

      boolean var16;
      if(var6 != null && this.mNewFolderName != null && this.mAccount != null) {
         Serializer var8 = var1.start(467);
         String var9 = this.mAccount.mSyncKey;
         Serializer var10 = var8.data(466, var9).data(457, var7);
         String var11 = this.mNewFolderName;
         Serializer var12 = var10.data(455, var11);
         String var13 = Integer.toString(12);
         Serializer var14 = var12.data(458, var13).end();
         int var15 = Log.i("Mahskyript", "FolderCreateAdapter.sendLocalChanges exit 1");
         var16 = true;
      } else {
         int var17 = Log.i("Mahskyript", "FolderCreateAdapter.sendLocalChanges exit 2");
         var16 = false;
      }

      return var16;
   }

   public class FolderCreateParser extends AbstractSyncParser {

      public FolderCreateParser(InputStream var2, AbstractCommandAdapter var3) throws IOException {
         super(var2, var3);
         int var4 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.FolderCreateParser");
      }

      public void commandsParser() throws IOException {
         int var1 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.commandsParser");
      }

      public void commit() throws IOException {
         // $FF: Couldn't be decompiled
      }

      public void itemOperationsResponsesParser() throws IOException {
         int var1 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.itemOperationsResponsesParser");
      }

      public void moveResponseParser() throws IOException {
         int var1 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.moveResponseParser");
      }

      public boolean parse() throws IOException, DeviceAccessException {
         int var1 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.parse");
         FolderCreateAdapter var2 = FolderCreateAdapter.this;
         FolderCreateAdapter var3 = FolderCreateAdapter.this;
         AbstractCommandAdapter.FolderCommandResponse var4 = var3.new FolderCommandResponse();
         var2.mResponse = var4;
         if(this.nextTag(0) != 467) {
            throw new Parser.EasParserException();
         } else {
            while(this.nextTag(0) != 3) {
               if(this.tag == 466) {
                  AbstractCommandAdapter.FolderCommandResponse var5 = FolderCreateAdapter.this.mResponse;
                  String var6 = this.getValue();
                  var5.mSyncKey = var6;
               } else if(this.tag == 456) {
                  AbstractCommandAdapter.FolderCommandResponse var7 = FolderCreateAdapter.this.mResponse;
                  String var8 = this.getValue();
                  var7.mServerId = var8;
               } else if(this.tag == 460) {
                  AbstractCommandAdapter.FolderCommandResponse var9 = FolderCreateAdapter.this.mResponse;
                  int var10 = this.getValueInt();
                  var9.mStatus = var10;
                  switch(FolderCreateAdapter.this.mResponse.mStatus) {
                  case 1:
                     break;
                  case 2:
                     FolderCreateAdapter.this.callback(37);
                     break;
                  case 3:
                     FolderCreateAdapter.this.callback(35);
                     break;
                  case 5:
                     FolderCreateAdapter.this.callback(38);
                     break;
                  case 6:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                     FolderCreateAdapter.this.callback(34);
                     break;
                  case 129:
                     FolderCreateAdapter.this.callback(3);
                     throw new DeviceAccessException(262145, 2131166810);
                  default:
                     throw new IOException("Unknown error.");
                  }
               }
            }

            if(FolderCreateAdapter.this.mResponse.mStatus == 1) {
               int var11 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.parse  CODE_FOLDER_CREATE_SUCCESS");
               this.commit();
            }

            int var12 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.parse exit");
            return false;
         }
      }

      public void responsesParser() throws IOException {
         int var1 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.responsesParser");
      }

      public void wipe() {
         int var1 = Log.i("Mahskyript", "FolderCreateAdapter.FolderCreateParser.wipe");
      }
   }
}
