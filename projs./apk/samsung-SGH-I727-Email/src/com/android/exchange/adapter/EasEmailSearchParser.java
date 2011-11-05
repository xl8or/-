package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.ParserUtility;
import com.android.exchange.provider.EmailResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class EasEmailSearchParser extends AbstractSyncParser {

   public static final int SEARCH_ERR_CODE_BAD_CONNECTION_ID = 11;
   public static final int SEARCH_ERR_CODE_CONTENT_INDEX = 9;
   public static final int SEARCH_ERR_CODE_CPLX_QUERY = 8;
   public static final int SEARCH_ERR_CODE_END_RANGE = 12;
   public static final int SEARCH_ERR_CODE_PROTOCOL_VIOLATION = 2;
   public static final int SEARCH_ERR_CODE_SERVER_ERR = 3;
   public static final int SEARCH_ERR_CODE_SUCCESS = 1;
   public static final int SEARCH_ERR_CODE_TIMED_OUT = 10;
   private static final String TAG = EasEmailSearchParser.class.getSimpleName();
   public static final int UNKNOWN_VALUE = 255;
   private long mFoldId;
   private ArrayList<EmailContent.Message> msgs;
   private EmailResult res;


   public EasEmailSearchParser(InputStream var1, AbstractSyncAdapter var2) throws IOException {
      super(var1, var2);
      ArrayList var3 = new ArrayList(0);
      this.msgs = var3;
      EmailResult var4 = new EmailResult();
      this.res = var4;
   }

   private void parsePropertiesTag(EmailContent.Message var1) throws IOException {
      ParserUtility.addMessageData(this, var1, 975);
      this.msgs.add(var1);
   }

   private void parseResultTag() throws IOException {
      EmailContent.Message var1 = new EmailContent.Message();
      long var2 = this.mAccount.mId;
      var1.mAccountKey = var2;
      Context var4 = this.mContext;
      long var5 = this.mAccount.mId;
      long var7 = EmailContent.Mailbox.findMailboxOfType(var4, var5, 8);
      var1.mMailboxKey = var7;
      var1.mFlagLoaded = 1;

      while(this.nextTag(974) != 3) {
         if(this.tag == 984) {
            String var9 = this.getValue();
            var1.mServerId = var9;
         } else if(this.tag == 975) {
            this.parsePropertiesTag(var1);
         } else {
            this.skipTag();
         }
      }

   }

   public void commandsParser() throws IOException {}

   public void commit() throws IOException {
      int var1 = 0;
      ArrayList var2 = new ArrayList();

      EmailContent.Message var4;
      for(Iterator var3 = this.msgs.iterator(); var3.hasNext(); var4.addSaveOps(var2)) {
         var4 = (EmailContent.Message)var3.next();
         if(!var4.mFlagRead) {
            ++var1;
         }
      }

      Object var6 = this.mService.getSynchronizer();
      synchronized(var6) {
         if(this.mService.isStopped()) {
            return;
         }

         try {
            this.mContentResolver.applyBatch("com.android.email.provider", var2);
            String[] var8 = new String[3];
            String var9 = this.mMailbox.mDisplayName;
            var8[0] = var9;
            var8[1] = " SyncKey saved as: ";
            String var10 = this.mMailbox.mSyncKey;
            var8[2] = var10;
            this.userLog(var8);
         } catch (RemoteException var22) {
            int var19 = Log.e(TAG, "Failed at mContentResolver.applyBatch.", var22);
         } catch (OperationApplicationException var23) {
            int var21 = Log.e(TAG, "Failed at mContentResolver.applyBatch.", var23);
         }
      }

      if(var1 > 0) {
         ContentValues var11 = new ContentValues();
         var11.put("field", "newMessageCount");
         Integer var12 = Integer.valueOf(var1);
         var11.put("add", var12);
         Uri var13 = EmailContent.Account.ADD_TO_FIELD_URI;
         long var14 = this.mAccount.mId;
         Uri var16 = ContentUris.withAppendedId(var13, var14);
         this.mContentResolver.update(var16, var11, (String)null, (String[])null);
      }
   }

   public void moveResponseParser() throws IOException {}

   public boolean parse() throws IOException, DeviceAccessException {
      // $FF: Couldn't be decompiled
   }

   public EmailResult parse_email_response() throws IOException, DeviceAccessException {
      boolean var1 = this.parse();
      return this.res;
   }

   public void responsesParser() throws IOException {}

   public void setFoldId(long var1) {
      this.mFoldId = var1;
   }

   public void wipe() {
      String var1 = TAG;
      StringBuilder var2 = (new StringBuilder()).append("fzhang mMailbox ID = ");
      long var3 = this.mMailbox.mId;
      String var5 = var2.append(var3).toString();
      Log.e(var1, var5);
      ContentResolver var7 = this.mContentResolver;
      Uri var8 = EmailContent.Message.CONTENT_URI;
      StringBuilder var9 = (new StringBuilder()).append("mailboxKey=");
      long var10 = this.mFoldId;
      String var12 = var9.append(var10).toString();
      var7.delete(var8, var12, (String[])null);
      ContentResolver var14 = this.mContentResolver;
      Uri var15 = EmailContent.Message.UPDATED_CONTENT_URI;
      StringBuilder var16 = (new StringBuilder()).append("mailboxKey=");
      long var17 = this.mFoldId;
      String var19 = var16.append(var17).toString();
      var14.delete(var15, var19, (String[])null);
   }
}
