package com.htc.android.mail.eassvc.mail;

import android.content.ContentValues;
import android.webkit.MimeTypeMap;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.util.EASLog;
import java.util.ArrayList;

public class MailItem {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   public static String TAG = "MailItem";
   public String mAccount;
   public String mAllDayEvent;
   public String mBcc;
   public String mCategory;
   public String mCc;
   public String mDate;
   public String mDownloadTotalSize;
   public String mDtStamp;
   public String mEndTime;
   public String mFrom;
   public String mFromemail;
   public String mGlobalObjId;
   public String mGroup;
   public String mHeaders;
   public String mImportance;
   public String mIncattachment;
   public String mInstanceType;
   public String mIntDBusyStatus;
   public String mInternalDate;
   public String mLocation;
   public String mMailbox;
   public String mMailbox_Id;
   public String mMessage;
   public String mMessageClass;
   public int mMessageClassInt;
   public String mMessageSize;
   public String mMessages_Flags;
   public String mMimetype;
   public String mNativeBodyType;
   public String mOrganizer;
   public String mParts_Flags;
   public String mPreview;
   public long mPsuedoGroupId;
   public String mRead;
   public String mReadTotalsize;
   public String mReadsize;
   public String mRecurrenceId;
   public String mRecurrence_DayOfMonth;
   public String mRecurrence_DayOfWeek;
   public String mRecurrence_Interval;
   public String mRecurrence_MonthOfYear;
   public String mRecurrence_Occurrences;
   public String mRecurrence_Type;
   public String mRecurrence_Until;
   public String mRecurrence_WeekOfMonth;
   public String mReference;
   public String mReminder;
   public String mReplyTo;
   public String mResponseRequested;
   public String mSensitivity;
   public String mStartTime;
   public String mSubjType;
   public String mSubject;
   public String mTag;
   public String mText;
   public String mThreadindex;
   public String mThreadtopic;
   public String mTimezone;
   public String mTo;
   public String mUid;
   public ArrayList<MailItem.pimAttachInfo> mailAttach;


   public MailItem() {
      ArrayList var1 = new ArrayList();
      this.mailAttach = var1;
      this.mSubject = "";
      this.mSubjType = "";
      this.mDate = "0";
      this.mInternalDate = "0";
      this.mRead = "0";
      this.mReadsize = "0";
      this.mReadTotalsize = "0";
      this.mDownloadTotalSize = "0";
      this.mMessageSize = "0";
      this.mIncattachment = "0";
      this.mTag = "0";
      this.mPsuedoGroupId = 0L;
      this.mImportance = "1";
      this.mMessageClassInt = 0;
      this.mAllDayEvent = "0";
      this.mResponseRequested = "0";
      this.mSensitivity = "0";
      String var2 = Integer.toString(-1);
      this.mRecurrence_Type = var2;
   }

   public static String getFileExtension(String param0) {
      // $FF: Couldn't be decompiled
   }

   public int getAttachmentSize() {
      return this.mailAttach.size();
   }

   public ContentValues getMessagesCV() {
      ContentValues var1;
      if(this.mAccount == null) {
         EASLog.e(TAG, "get messages content value: account is null");
         var1 = null;
      } else {
         ContentValues var2 = new ContentValues();
         String var3 = this.mUid;
         var2.put("_uid", var3);
         String var4 = "_from";
         String var5 = this.mFrom;
         String var6;
         if("".equals(var5)) {
            var6 = null;
         } else {
            var6 = this.mFrom;
         }

         var2.put(var4, var6);
         String var7 = this.mFromemail;
         var2.put("_fromEmail", var7);
         String var8 = this.mReplyTo;
         var2.put("_replyTo", var8);
         String var9 = this.mSubject;
         var2.put("_subject", var9);
         String var10 = this.mSubjType;
         var2.put("_subjtype", var10);
         String var11 = this.mTo;
         var2.put("_to", var11);
         String var12 = this.mCc;
         var2.put("_cc", var12);
         String var13 = this.mBcc;
         var2.put("_bcc", var13);
         String var14 = this.mThreadindex;
         var2.put("_threadindex", var14);
         String var15 = this.mThreadtopic;
         var2.put("_threadtopic", var15);
         String var16 = this.mHeaders;
         var2.put("_headers", var16);
         String var17 = this.mDate;
         var2.put("_date", var17);
         if(this.mInternalDate != null && !this.mInternalDate.equals("0")) {
            String var61 = this.mInternalDate;
            var2.put("_internaldate", var61);
         } else {
            String var18 = this.mDate;
            var2.put("_internaldate", var18);
         }

         String var19 = this.mPreview;
         var2.put("_preview", var19);
         String var20 = this.mMessages_Flags;
         var2.put("_flags", var20);
         String var21 = this.mRead;
         var2.put("_read", var21);
         String var22 = this.mReadsize;
         var2.put("_readsize", var22);
         String var23 = this.mReadTotalsize;
         var2.put("_readtotalsize", var23);
         String var24 = this.mDownloadTotalSize;
         var2.put("_downloadtotalsize", var24);
         String var25 = this.mMessageSize;
         var2.put("_messagesize", var25);
         String var26 = this.mIncattachment;
         var2.put("_incAttachment", var26);
         String var27 = this.mAccount;
         var2.put("_account", var27);
         String var28 = this.mMailbox;
         var2.put("_mailbox", var28);
         String var29 = this.mTag;
         var2.put("_tag", var29);
         String var30 = this.mMailbox_Id;
         var2.put("_mailboxId", var30);
         String var31 = this.mReference;
         var2.put("_references", var31);
         String var32 = this.mGroup;
         var2.put("_group", var32);
         Long var33 = Long.valueOf(this.mPsuedoGroupId);
         var2.put("_groupPseudo", var33);
         String var34 = this.mMessageClass;
         var2.put("_messageClass", var34);
         Integer var35 = Integer.valueOf(this.mMessageClassInt);
         var2.put("_messageClassInt", var35);
         String var36 = this.mAllDayEvent;
         var2.put("_allDayEvent", var36);
         String var37 = this.mStartTime;
         var2.put("_startTime", var37);
         String var38 = this.mDtStamp;
         var2.put("_dtstamp", var38);
         String var39 = this.mEndTime;
         var2.put("_endTime", var39);
         String var40 = this.mInstanceType;
         var2.put("_instanceType", var40);
         String var41 = this.mLocation;
         var2.put("_location", var41);
         String var42 = this.mOrganizer;
         var2.put("_organizer", var42);
         String var43 = this.mRecurrenceId;
         var2.put("_recurrenceId", var43);
         String var44 = this.mReminder;
         var2.put("_reminder", var44);
         String var45 = this.mResponseRequested;
         var2.put("_responseRequested", var45);
         String var46 = this.mSensitivity;
         var2.put("_sensitivity", var46);
         String var47 = this.mIntDBusyStatus;
         var2.put("_IntdBusyStatus", var47);
         String var48 = this.mTimezone;
         var2.put("_timezone", var48);
         String var49 = this.mGlobalObjId;
         var2.put("_globalObjId", var49);
         String var50 = this.mCategory;
         var2.put("_category", var50);
         String var51 = this.mRecurrence_Type;
         var2.put("_recurrence_type", var51);
         String var52 = this.mRecurrence_Occurrences;
         var2.put("_recurrence_occurrences", var52);
         String var53 = this.mRecurrence_Interval;
         var2.put("_recurrence_interval", var53);
         String var54 = this.mRecurrence_DayOfWeek;
         var2.put("_recurrence_dayofweek", var54);
         String var55 = this.mRecurrence_DayOfMonth;
         var2.put("_recurrence_dayofmonth", var55);
         String var56 = this.mRecurrence_WeekOfMonth;
         var2.put("_recurrence_weekofmonth", var56);
         String var57 = this.mRecurrence_MonthOfYear;
         var2.put("_recurrence_monthofyear", var57);
         String var58 = this.mMessages_Flags;
         var2.put("_flags", var58);
         String var59 = this.mRecurrence_Until;
         var2.put("_recurrence_until", var59);

         try {
            Integer var60 = Integer.valueOf(Integer.parseInt(this.mImportance));
            var2.put("_importance", var60);
         } catch (NumberFormatException var67) {
            var2.put("_importance", "1");
         }

         if(this.mMimetype != null && this.mMimetype.equals("text/html")) {
            var2.put("_text", "");
         } else if(this.mMimetype != null && this.mMimetype.equals("text/plain")) {
            if(this.mText == null) {
               if(DEBUG) {
                  String var63 = TAG;
                  long var64 = Long.valueOf(this.mAccount).longValue();
                  EASLog.d(var63, var64, "!! getEasmessagesCV(): add empty plain text body");
               }

               var2.put("_text", "");
            } else {
               String var66 = this.mText;
               var2.put("_text", var66);
            }
         }

         var1 = var2;
      }

      return var1;
   }

   public ContentValues getPartsAttachCV(int var1) {
      ContentValues var2;
      if(this.mAccount == null) {
         EASLog.e(TAG, "get parts attach: account is null");
         var2 = null;
      } else {
         MailItem.pimAttachInfo var3 = (MailItem.pimAttachInfo)this.mailAttach.get(var1);
         if(var3 == null) {
            String var4 = TAG;
            long var5 = Long.valueOf(this.mAccount).longValue();
            EASLog.e(var4, var5, "getEaspartsAttachCV(): attach is null");
            var2 = null;
         } else {
            IndexOutOfBoundsException var26;
            label77: {
               Exception var19;
               label62: {
                  String var8;
                  ContentValues var9;
                  try {
                     String var7 = getFileExtension(var3.mFilename);
                     var8 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var7);
                     if(var8 == null || var8.length() <= 0) {
                        var8 = "application/octet-stream";
                     }

                     var9 = new ContentValues();
                  } catch (IndexOutOfBoundsException var24) {
                     var26 = var24;
                     break label77;
                  } catch (Exception var25) {
                     var19 = var25;
                     break label62;
                  }

                  try {
                     var9.put("_mimetype", var8);
                     String var10 = this.mAccount;
                     var9.put("_account", var10);
                     String var11 = this.mParts_Flags;
                     var9.put("_flags", var11);
                     String var12 = var3.mFilename;
                     var9.put("_filename", var12);
                     String var13 = var3.mFileSize;
                     var9.put("_filesize", var13);
                     String var14 = var3.mContentId;
                     var9.put("_contentid", var14);
                     String var15 = var3.mContentId;
                     var9.put("_cid", var15);
                     String var16 = var3.mFilereference;
                     var9.put("_filereference", var16);
                     if(var3.mIsInLine != null) {
                        String var17 = var3.mIsInLine;
                        var9.put("_inline", var17);
                     } else {
                        var9.put("_inline", "0");
                     }

                     if(var3.mIsInLine != null && var3.mIsInLine.equals("1") && this.mMessageClass != null && !this.mMessageClass.equalsIgnoreCase("IPM.Schedule.Meeting.Request")) {
                        var9.put("_contenttype", "2");
                     } else {
                        var9.put("_contenttype", "1");
                     }

                     if(var3.mFilepath != null) {
                        String var18 = var3.mFilepath;
                        var9.put("_filepath", var18);
                     }

                     var2 = var9;
                     return var2;
                  } catch (IndexOutOfBoundsException var22) {
                     var26 = var22;
                     break label77;
                  } catch (Exception var23) {
                     var19 = var23;
                  }
               }

               var19.printStackTrace();
               var2 = null;
               return var2;
            }

            var26.printStackTrace();
            var2 = null;
         }
      }

      return var2;
   }

   public ContentValues getPartsBodyCV() {
      ContentValues var1;
      if(this.mAccount == null) {
         EASLog.e(TAG, "get parts mail body: account is null");
         var1 = null;
      } else {
         ContentValues var2 = new ContentValues();
         String var3 = this.mMimetype;
         var2.put("_mimetype", var3);
         String var4 = this.mNativeBodyType;
         var2.put("_nativeType", var4);
         String var5 = this.mText;
         var2.put("_text", var5);
         String var6 = this.mAccount;
         var2.put("_account", var6);
         String var7 = this.mParts_Flags;
         var2.put("_flags", var7);
         var1 = var2;
      }

      return var1;
   }

   public static class pimAttachInfo {

      String mContentId = null;
      String mFileSize = null;
      String mFilename = null;
      String mFilepath = null;
      String mFilereference = null;
      String mIsInLine = null;
      String mMimeType = null;


      public pimAttachInfo() {}
   }
}
