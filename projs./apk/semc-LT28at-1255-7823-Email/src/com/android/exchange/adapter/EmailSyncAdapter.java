package com.android.exchange.adapter;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.webkit.MimeTypeMap;
import com.android.email.Utility;
import com.android.email.mail.Address;
import com.android.email.mail.PackedString;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Serializer;
import com.android.exchange.utility.CalendarUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class EmailSyncAdapter extends AbstractSyncAdapter {

   private static final int MESSAGE_ID_SUBJECT_ID_COLUMN = 0;
   private static final String[] MESSAGE_ID_SUBJECT_PROJECTION;
   private static final int MESSAGE_ID_SUBJECT_SUBJECT_COLUMN = 1;
   private static final int UPDATES_FLAG_COLUMN = 3;
   private static final int UPDATES_MAILBOX_KEY_COLUMN = 1;
   private static final String[] UPDATES_PROJECTION;
   private static final int UPDATES_READ_COLUMN = 0;
   private static final int UPDATES_SERVER_ID_COLUMN = 2;
   private static final String WHERE_BODY_SOURCE_MESSAGE_KEY = "sourceMessageKey=?";
   String[] mBindArgument;
   String[] mBindArguments;
   ArrayList<Long> mDeletedIdList;
   boolean mIsLooping;
   ArrayList<Long> mUpdatedIdList;


   static {
      String[] var0 = new String[]{"flagRead", "mailboxKey", "syncServerId", "flagFavorite"};
      UPDATES_PROJECTION = var0;
      String[] var1 = new String[]{"_id", "subject"};
      MESSAGE_ID_SUBJECT_PROJECTION = var1;
   }

   public EmailSyncAdapter(EmailContent.Mailbox var1, EasSyncService var2) {
      super(var1, var2);
      String[] var3 = new String[2];
      this.mBindArguments = var3;
      String[] var4 = new String[1];
      this.mBindArgument = var4;
      ArrayList var5 = new ArrayList();
      this.mDeletedIdList = var5;
      ArrayList var6 = new ArrayList();
      this.mUpdatedIdList = var6;
      this.mIsLooping = (boolean)0;
   }

   // $FF: synthetic method
   static void access$100(EmailSyncAdapter var0, ArrayList var1) {
      var0.addCleanupOps(var1);
   }

   private void addCleanupOps(ArrayList<ContentProviderOperation> var1) {
      Iterator var2 = this.mDeletedIdList.iterator();

      while(var2.hasNext()) {
         Long var3 = (Long)var2.next();
         Uri var4 = EmailContent.Message.DELETED_CONTENT_URI;
         long var5 = var3.longValue();
         ContentProviderOperation var7 = ContentProviderOperation.newDelete(ContentUris.withAppendedId(var4, var5)).build();
         var1.add(var7);
      }

      Iterator var9 = this.mUpdatedIdList.iterator();

      while(var9.hasNext()) {
         Long var10 = (Long)var9.next();
         Uri var11 = EmailContent.Message.UPDATED_CONTENT_URI;
         long var12 = var10.longValue();
         ContentProviderOperation var14 = ContentProviderOperation.newDelete(ContentUris.withAppendedId(var11, var12)).build();
         var1.add(var14);
      }

   }

   private String formatTwo(int var1) {
      String var4;
      if(var1 < 10) {
         StringBuilder var2 = (new StringBuilder()).append("0");
         char var3 = (char)(var1 + 48);
         var4 = var2.append(var3).toString();
      } else {
         var4 = Integer.toString(var1);
      }

      return var4;
   }

   private boolean messageReferenced(ContentResolver var1, long var2) {
      String[] var4 = this.mBindArgument;
      String var5 = Long.toString(var2);
      var4[0] = var5;
      Uri var6 = EmailContent.Body.CONTENT_URI;
      String[] var7 = EmailContent.Body.ID_PROJECTION;
      String[] var8 = this.mBindArgument;
      Cursor var9 = var1.query(var6, var7, "sourceMessageKey=?", var8, (String)null);
      boolean var14 = false;

      boolean var10;
      try {
         var14 = true;
         var10 = var9.moveToFirst();
         var14 = false;
      } finally {
         if(var14) {
            var9.close();
         }
      }

      var9.close();
      return var10;
   }

   public void cleanup() {
      if(!this.mDeletedIdList.isEmpty() || !this.mUpdatedIdList.isEmpty()) {
         ArrayList var1 = new ArrayList();
         this.addCleanupOps(var1);

         try {
            ContentProviderResult[] var2 = this.mContext.getContentResolver().applyBatch("com.android.email.provider", var1);
         } catch (RemoteException var5) {
            ;
         } catch (OperationApplicationException var6) {
            ;
         }
      }
   }

   public String formatDateTime(Calendar var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = var1.get(1);
      var2.append(var3);
      StringBuilder var5 = var2.append('-');
      int var6 = var1.get(2) + 1;
      String var7 = this.formatTwo(var6);
      var2.append(var7);
      StringBuilder var9 = var2.append('-');
      int var10 = var1.get(5);
      String var11 = this.formatTwo(var10);
      var2.append(var11);
      StringBuilder var13 = var2.append('T');
      int var14 = var1.get(11);
      String var15 = this.formatTwo(var14);
      var2.append(var15);
      StringBuilder var17 = var2.append(':');
      int var18 = var1.get(12);
      String var19 = this.formatTwo(var18);
      var2.append(var19);
      StringBuilder var21 = var2.append(':');
      int var22 = var1.get(13);
      String var23 = this.formatTwo(var22);
      var2.append(var23);
      StringBuilder var25 = var2.append(".000Z");
      return var2.toString();
   }

   public String getCollectionName() {
      return "Email";
   }

   public boolean isLooping() {
      return this.mIsLooping;
   }

   public boolean isSyncable() {
      return true;
   }

   public boolean parse(InputStream var1) throws IOException {
      EmailSyncAdapter.EasEmailSyncParser var2 = new EmailSyncAdapter.EasEmailSyncParser(var1, this);
      boolean var3 = var2.parse();
      boolean var4 = var2.isLooping();
      this.mIsLooping = var4;
      return var3;
   }

   boolean sendDeletedItems(Serializer param1, ArrayList<Long> param2, boolean param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public boolean sendLocalChanges(Serializer param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public class EasEmailSyncParser extends AbstractSyncParser {

      private static final String WHERE_SERVER_ID_AND_MAILBOX_KEY = "syncServerId=? and mailboxKey=?";
      ArrayList<EmailSyncAdapter.EasEmailSyncParser.ServerChange> changedEmails;
      ArrayList<Long> deletedEmails;
      private String mMailboxIdAsString;
      ArrayList<EmailContent.Message> newEmails;


      public EasEmailSyncParser(InputStream var2, EmailSyncAdapter var3) throws IOException {
         super(var2, var3);
         ArrayList var4 = new ArrayList();
         this.newEmails = var4;
         ArrayList var5 = new ArrayList();
         this.deletedEmails = var5;
         ArrayList var6 = new ArrayList();
         this.changedEmails = var6;
         String var7 = Long.toString(this.mMailbox.mId);
         this.mMailboxIdAsString = var7;
      }

      private void addParser(ArrayList<EmailContent.Message> var1) throws IOException {
         EmailContent.Message var2 = new EmailContent.Message();
         long var3 = this.mAccount.mId;
         var2.mAccountKey = var3;
         long var5 = this.mMailbox.mId;
         var2.mMailboxKey = var5;
         var2.mFlagLoaded = 1;

         while(this.nextTag(7) != 3) {
            switch(this.tag) {
            case 13:
               String var7 = this.getValue();
               var2.mServerId = var7;
               break;
            case 29:
               this.addData(var2);
               break;
            default:
               this.skipTag();
            }
         }

         var1.add(var2);
      }

      private void attachmentParser(ArrayList<EmailContent.Attachment> var1, EmailContent.Message var2) throws IOException {
         String var3 = null;
         String var4 = null;
         String var5 = null;
         String var6 = null;

         while(this.nextTag(133) != 3) {
            switch(this.tag) {
            case 135:
            case 1105:
               var5 = this.getValue();
               break;
            case 136:
            case 1100:
               var4 = this.getValue();
               break;
            case 144:
            case 1104:
               var3 = this.getValue();
               break;
            case 1107:
               var6 = this.getValue();
               break;
            default:
               this.skipTag();
            }
         }

         if(var3 != null) {
            if(var4 != null) {
               if(var5 != null) {
                  EmailContent.Attachment var7 = new EmailContent.Attachment();
                  var7.mEncoding = "base64";
                  long var8 = Long.parseLong(var4);
                  var7.mSize = var8;
                  var7.mFileName = var3;
                  var7.mLocation = var5;
                  String var10 = this.getMimeTypeFromFileName(var3);
                  var7.mMimeType = var10;
                  var7.mContentId = var6;
                  var1.add(var7);
                  var2.mFlagAttachment = (boolean)1;
               }
            }
         }
      }

      private void attachmentsParser(ArrayList<EmailContent.Attachment> var1, EmailContent.Message var2) throws IOException {
         while(this.nextTag(134) != 3) {
            switch(this.tag) {
            case 133:
            case 1103:
               this.attachmentParser(var1, var2);
               break;
            default:
               this.skipTag();
            }
         }

      }

      private void bodyParser(EmailContent.Message var1) throws IOException {
         String var2 = "1";
         String var3 = "";

         while(this.nextTag(140) != 3) {
            switch(this.tag) {
            case 1094:
               var2 = this.getValue();
               break;
            case 1099:
               var3 = this.getValue();
               break;
            default:
               this.skipTag();
            }
         }

         if(var2.equals("2")) {
            var1.mHtml = var3;
         } else {
            var1.mText = var3;
         }
      }

      private void changeApplicationDataParser(ArrayList<EmailSyncAdapter.EasEmailSyncParser.ServerChange> var1, Boolean var2, Boolean var3, long var4) throws IOException {
         Boolean var6 = null;
         Boolean var7 = null;

         while(this.nextTag(29) != 3) {
            switch(this.tag) {
            case 149:
               byte var8;
               if(this.getValueInt() == 1) {
                  var8 = 1;
               } else {
                  var8 = 0;
               }

               var6 = Boolean.valueOf((boolean)var8);
               break;
            case 186:
               var7 = this.flagParser();
               break;
            default:
               this.skipTag();
            }
         }

         if(var6 == null || var2.equals(var6)) {
            if(var7 == null) {
               return;
            }

            if(var3.equals(var7)) {
               return;
            }
         }

         EmailSyncAdapter.EasEmailSyncParser.ServerChange var12 = new EmailSyncAdapter.EasEmailSyncParser.ServerChange(var4, var6, var7);
         var1.add(var12);
      }

      private Boolean flagParser() throws IOException {
         Boolean var1 = Boolean.valueOf((boolean)0);

         while(this.nextTag(186) != 3) {
            switch(this.tag) {
            case 187:
               byte var2;
               if(this.getValueInt() == 2) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               var1 = Boolean.valueOf((boolean)var2);
               break;
            default:
               this.skipTag();
            }
         }

         return var1;
      }

      private Cursor getServerIdCursor(String var1, String[] var2) {
         EmailSyncAdapter.this.mBindArguments[0] = var1;
         String[] var3 = EmailSyncAdapter.this.mBindArguments;
         String var4 = this.mMailboxIdAsString;
         var3[1] = var4;
         ContentResolver var5 = this.mContentResolver;
         Uri var6 = EmailContent.Message.CONTENT_URI;
         String[] var7 = EmailSyncAdapter.this.mBindArguments;
         return var5.query(var6, var2, "syncServerId=? and mailboxKey=?", var7, (String)null);
      }

      private int getUnreadMailsCountByAccount(long param1) {
         // $FF: Couldn't be decompiled
      }

      private void meetingRequestParser(EmailContent.Message var1) throws IOException {
         PackedString.Builder var2 = new PackedString.Builder();

         while(this.nextTag(162) != 3) {
            switch(this.tag) {
            case 155:
               this.nullParser();
               break;
            case 157:
               String var3 = this.getValue();
               var2.put("DTSTAMP", var3);
               break;
            case 158:
               String var5 = this.getValue();
               var2.put("DTEND", var5);
               break;
            case 161:
               String var7 = this.getValue();
               var2.put("LOC", var7);
               break;
            case 163:
               String var6 = this.getValue();
               var2.put("ORGMAIL", var6);
               break;
            case 167:
               this.recurrencesParser();
               break;
            case 177:
               String var4 = this.getValue();
               var2.put("DTSTART", var4);
               break;
            case 180:
               String var8 = CalendarUtilities.getUidFromGlobalObjId(this.getValue());
               var2.put("UID", var8);
               break;
            default:
               this.skipTag();
            }
         }

         if(var1.mSubject != null) {
            String var9 = var1.mSubject;
            var2.put("TITLE", var9);
         }

         String var10 = var2.toString();
         var1.mMeetingInfo = var10;
      }

      private void nullParser() throws IOException {
         while(this.nextTag(155) != 3) {
            this.skipTag();
         }

      }

      private void recurrencesParser() throws IOException {
         while(this.nextTag(167) != 3) {
            switch(this.tag) {
            case 168:
               this.nullParser();
               break;
            default:
               this.skipTag();
            }
         }

      }

      public void addData(EmailContent.Message var1) throws IOException {
         ArrayList var2 = new ArrayList();

         while(this.nextTag(29) != 3) {
            switch(this.tag) {
            case 134:
            case 1102:
               this.attachmentsParser(var2, var1);
               break;
            case 140:
               String var24 = this.getValue();
               var1.mText = var24;
               break;
            case 143:
               long var19 = Utility.parseEmailDateTimeToMillis(this.getValue());
               var1.mTimeStamp = var19;
               break;
            case 147:
               String var25 = this.getValue();
               if(var25.equals("IPM.Schedule.Meeting.Request")) {
                  int var26 = var1.mFlags | 4;
                  var1.mFlags = var26;
               } else if(var25.equals("IPM.Schedule.Meeting.Canceled")) {
                  int var27 = var1.mFlags | 8;
                  var1.mFlags = var27;
               }
               break;
            case 148:
               String var21 = this.getValue();
               var1.mSubject = var21;
               break;
            case 149:
               byte var22;
               if(this.getValueInt() == 1) {
                  var22 = 1;
               } else {
                  var22 = 0;
               }

               var1.mFlagRead = (boolean)var22;
               break;
            case 150:
               String var3 = this.getValue();
               boolean[] var4 = new boolean[]{true};
               Address[] var5 = Address.parse(var3, var4);
               if(this.mMailbox.mType == 5 && var5 != null && var5.length > 0) {
                  String var6 = var5[0].toFriendly();
                  var1.mDisplayName = var6;
               }

               String var7 = Address.pack(var5);
               var1.mTo = var7;
               break;
            case 151:
               String var13 = this.getValue();
               boolean[] var14 = new boolean[]{true};
               String var15 = Address.pack(Address.parse(var13, var14));
               var1.mCc = var15;
               break;
            case 152:
               String var8 = this.getValue();
               boolean[] var9 = new boolean[]{true};
               Address[] var10 = Address.parse(var8, var9);
               if(this.mMailbox.mType != 5 && var10 != null && var10.length > 0) {
                  String var11 = var10[0].toFriendly();
                  var1.mDisplayName = var11;
               }

               String var12 = Address.pack(var10);
               var1.mFrom = var12;
               break;
            case 153:
               String var16 = this.getValue();
               boolean[] var17 = new boolean[]{true};
               String var18 = Address.pack(Address.parse(var16, var17));
               var1.mReplyTo = var18;
               break;
            case 162:
               this.meetingRequestParser(var1);
               break;
            case 186:
               boolean var23 = this.flagParser().booleanValue();
               var1.mFlagFavorite = var23;
               break;
            case 1098:
               this.bodyParser(var1);
               break;
            default:
               this.skipTag();
            }
         }

         if(var2.size() > 0) {
            var1.mAttachments = var2;
         }
      }

      void changeParser(ArrayList<EmailSyncAdapter.EasEmailSyncParser.ServerChange> param1) throws IOException {
         // $FF: Couldn't be decompiled
      }

      public void commandsParser() throws IOException {
         while(this.nextTag(22) != 3) {
            if(this.tag == 7) {
               ArrayList var1 = this.newEmails;
               this.addParser(var1);
               EmailSyncAdapter.this.incrementChangeCount();
            } else if(this.tag != 9 && this.tag != 33) {
               if(this.tag == 8) {
                  ArrayList var4 = this.changedEmails;
                  this.changeParser(var4);
                  EmailSyncAdapter.this.incrementChangeCount();
               } else {
                  this.skipTag();
               }
            } else {
               ArrayList var2 = this.deletedEmails;
               int var3 = this.tag;
               this.deleteParser(var2, var3);
               EmailSyncAdapter.this.incrementChangeCount();
            }
         }

      }

      public void commit() {
         // $FF: Couldn't be decompiled
      }

      void deleteParser(ArrayList<Long> var1, int var2) throws IOException {
         while(this.nextTag(var2) != 3) {
            switch(this.tag) {
            case 13:
               String var3 = this.getValue();
               String[] var4 = EmailSyncAdapter.MESSAGE_ID_SUBJECT_PROJECTION;
               Cursor var5 = this.getServerIdCursor(var3, var4);

               try {
                  if(!var5.moveToFirst()) {
                     break;
                  }

                  Long var6 = Long.valueOf(var5.getLong(0));
                  var1.add(var6);
                  if(Eas.USER_LOG) {
                     String[] var8 = new String[]{"Deleting ", null};
                     StringBuilder var9 = (new StringBuilder()).append(var3).append(", ");
                     String var10 = var5.getString(1);
                     String var11 = var9.append(var10).toString();
                     var8[1] = var11;
                     this.userLog(var8);
                  }
                  break;
               } finally {
                  var5.close();
               }
            default:
               this.skipTag();
            }
         }

      }

      public String getMimeTypeFromFileName(String var1) {
         int var2 = var1.lastIndexOf(46);
         String var3 = null;
         if(var2 > 0) {
            int var4 = var1.length() - 1;
            if(var2 < var4) {
               int var5 = var2 + 1;
               var3 = var1.substring(var5).toLowerCase();
            }
         }

         String var6;
         if(var3 == null) {
            var6 = "application/octet-stream";
         } else {
            var6 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
            if(var6 == null) {
               var6 = "application/" + var3;
            }
         }

         return var6;
      }

      public void responsesParser() {}

      public void wipe() {
         ContentResolver var1 = this.mContentResolver;
         Uri var2 = EmailContent.Message.CONTENT_URI;
         StringBuilder var3 = (new StringBuilder()).append("mailboxKey=");
         long var4 = this.mMailbox.mId;
         String var6 = var3.append(var4).toString();
         var1.delete(var2, var6, (String[])null);
         ContentResolver var8 = this.mContentResolver;
         Uri var9 = EmailContent.Message.DELETED_CONTENT_URI;
         StringBuilder var10 = (new StringBuilder()).append("mailboxKey=");
         long var11 = this.mMailbox.mId;
         String var13 = var10.append(var11).toString();
         var8.delete(var9, var13, (String[])null);
         ContentResolver var15 = this.mContentResolver;
         Uri var16 = EmailContent.Message.UPDATED_CONTENT_URI;
         StringBuilder var17 = (new StringBuilder()).append("mailboxKey=");
         long var18 = this.mMailbox.mId;
         String var20 = var17.append(var18).toString();
         var15.delete(var16, var20, (String[])null);
      }

      class ServerChange {

         Boolean flag;
         long id;
         Boolean read;


         ServerChange(long var2, Boolean var4, Boolean var5) {
            this.id = var2;
            this.read = var4;
            this.flag = var5;
         }
      }
   }
}
