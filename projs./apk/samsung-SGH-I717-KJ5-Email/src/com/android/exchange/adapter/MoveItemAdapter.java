package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
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

public class MoveItemAdapter extends AbstractCommandAdapter {

   private final int CODE_MOVE_ITEM_INVALID_COLLECTION_DST = 2;
   private final int CODE_MOVE_ITEM_INVALID_COLLECTION_SRC = 1;
   private final int CODE_MOVE_ITEM_ITEM_LOCKED = 6;
   private final int CODE_MOVE_ITEM_SERVER_ERROR = 5;
   private final int CODE_MOVE_ITEM_SRC_DST_SAME = 4;
   private final int CODE_MOVE_ITEM_SUCCESS = 3;
   private final int INDEX_DST_MAILBOX;
   private final int INDEX_SERVER_ID;
   private String[] MOVE_ITEMS_PROJECTION;
   private long mDstMailboxId;
   private MoveItemAdapter.MoveItemParser parser;


   public MoveItemAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
      String[] var3 = new String[]{"syncServerId", "dstMailboxKey"};
      this.MOVE_ITEMS_PROJECTION = var3;
      this.INDEX_SERVER_ID = 0;
      this.INDEX_DST_MAILBOX = 1;
      this.parser = null;
      this.mDstMailboxId = 0L;
   }

   // $FF: synthetic method
   static long access$000(MoveItemAdapter var0) {
      return var0.mDstMailboxId;
   }

   public void callback(int var1) {
      try {
         IEmailServiceCallback var2 = SyncManager.callback();
         long var3 = this.mMailbox.mId;
         var2.moveItemStatus(var3, var1);
      } catch (RemoteException var6) {
         ;
      }
   }

   public void cleanup() {
      if(this.parser != null) {
         this.parser.wipe();
      }
   }

   public String getCollectionName() {
      return null;
   }

   public String getCommandName() {
      return "MoveItems";
   }

   public boolean hasChangedItems() {
      int var1 = 0;
      ContentResolver var2 = this.mContext.getContentResolver();
      Uri var3 = EmailContent.Message.CONTENT_URI;
      String[] var4 = this.MOVE_ITEMS_PROJECTION;
      StringBuilder var5 = (new StringBuilder()).append("mailboxKey=");
      long var6 = this.mMailbox.mId;
      String var8 = var5.append(var6).append(" AND ").append("flagMoved").append("=1").toString();
      Object var9 = null;
      Cursor var10 = var2.query(var3, var4, var8, (String[])null, (String)var9);
      if(var10 != null) {
         boolean var15 = false;

         int var11;
         try {
            var15 = true;
            var11 = var10.getCount();
            var15 = false;
         } finally {
            if(var15) {
               var10.close();
            }
         }

         var1 = var11;
         var10.close();
      }

      boolean var12;
      if(var1 > 0) {
         var12 = true;
      } else {
         var12 = false;
      }

      return var12;
   }

   public boolean isSyncable() {
      return true;
   }

   public boolean parse(InputStream var1) throws IOException, DeviceAccessException {
      MoveItemAdapter.MoveItemParser var2 = new MoveItemAdapter.MoveItemParser(var1, this);
      this.parser = var2;
      return this.parser.parse();
   }

   public boolean sendLocalChanges(Serializer param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setDestinationMailBox(long var1) {
      this.mDstMailboxId = var1;
   }

   public class MoveItemParser extends AbstractSyncParser {

      String mDstMsgId = null;
      String mSrcMsgId = null;
      int mStatus;


      public MoveItemParser(InputStream var2, MoveItemAdapter var3) throws IOException {
         super(var2, var3);
      }

      public void commandsParser() throws IOException {}

      public void commit() throws IOException {
         // $FF: Couldn't be decompiled
      }

      public void itemOperationsResponsesParser() throws IOException {}

      public void moveResponseParser() throws IOException {}

      public boolean parse() throws IOException, DeviceAccessException {
         if(this.nextTag(0) != 325) {
            throw new Parser.EasParserException();
         } else {
            while(this.nextTag(0) != 3) {
               if(this.tag == 330) {
                  boolean var1 = this.parseMoveItemResponse();
               }
            }

            MoveItemAdapter.this.callback(0);
            return false;
         }
      }

      public boolean parseMoveItemResponse() throws IOException, DeviceAccessException {
         while(this.nextTag(330) != 3) {
            if(this.tag == 327) {
               String var1 = this.getValue();
               this.mSrcMsgId = var1;
            } else if(this.tag == 331) {
               int var2 = this.getValueInt();
               this.mStatus = var2;
               String[] var3 = new String[4];
               var3[0] = "MoveItem status:";
               StringBuilder var4 = (new StringBuilder()).append("");
               int var5 = this.mStatus;
               String var6 = var4.append(var5).toString();
               var3[1] = var6;
               var3[2] = " srcMsgId: ";
               StringBuilder var7 = (new StringBuilder()).append("");
               String var8 = this.mSrcMsgId;
               String var9 = var7.append(var8).toString();
               var3[3] = var9;
               this.userLog(var3);
               switch(this.mStatus) {
               case 1:
               case 2:
               case 4:
               case 5:
               case 6:
                  throw new IOException("Move Item: Server error.");
               case 129:
                  throw new DeviceAccessException(262145, 2131166819);
               }
            } else if(this.tag == 332) {
               String var10 = this.getValue();
               this.mDstMsgId = var10;
               this.commit();
            }
         }

         return false;
      }

      public void responsesParser() throws IOException {}

      public void wipe() {
         if(this.mSrcMsgId != null) {
            ContentValues var1 = new ContentValues();
            var1.put("flagMoved", "0");
            var1.put("dstMailboxKey", "0");
            Object var2 = this.mService.getSynchronizer();
            synchronized(var2) {
               if(!this.mService.isStopped()) {
                  ContentResolver var3 = this.mContentResolver;
                  Uri var4 = EmailContent.Message.CONTENT_URI;
                  StringBuilder var5 = (new StringBuilder()).append("syncServerId=\'");
                  String var6 = this.mSrcMsgId;
                  String var7 = var5.append(var6).append("\'").toString();
                  var3.update(var4, var1, var7, (String[])null);
               }

            }
         }
      }
   }
}
