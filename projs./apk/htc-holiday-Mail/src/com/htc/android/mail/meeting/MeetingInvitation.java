package com.htc.android.mail.meeting;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.Time;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Rfc2822;
import com.htc.android.mail.ll;
import com.htc.android.mail.database.ExchangeUtil;
import com.htc.android.mail.meeting.MeetingRequest;
import com.htc.android.mail.meeting.MeetingUtil;
import com.htc.android.mail.meeting.iCalendarCreator;
import com.htc.android.mail.server.ExchangeServer;
import java.util.UUID;

public class MeetingInvitation {

   public static final int CMD_ACCEPT = 1;
   public static final int CMD_DECLINE = 3;
   public static final int CMD_FORWARD_MEETING = 4;
   public static final int CMD_INVITATION = 6;
   public static final int CMD_PROPOSE_NEW_TIME = 5;
   public static final int CMD_TENTATICE = 2;
   public static final int CMD_UNKNOWN = 0;
   private static boolean DEBUG = Mail.MAIL_DEBUG;
   public static int RETRY_COUNT = 1;
   private static final String TAG = "MeetingInvitation";
   private String iCalendar;
   private int mCommand;
   private Context mContext;
   private MeetingRequest mRequest;


   public MeetingInvitation(Context var1) {
      this.mContext = var1;
      this.mRequest = null;
      this.iCalendar = null;
      this.mCommand = 0;
   }

   public long create_CalendarEvent(Account param1) {
      // $FF: Couldn't be decompiled
   }

   public String create_iCalendar() {
      if(DEBUG) {
         StringBuilder var1 = (new StringBuilder()).append("- create_iCalendar: ");
         int var2 = this.mCommand;
         String var3 = var1.append(var2).toString();
         ll.d("MeetingInvitation", var3);
      }

      String var4;
      if(this.mCommand != 0 && this.mRequest != null) {
         switch(this.mCommand) {
         case 5:
         case 6:
            int var8 = this.mCommand;
            MeetingRequest var9 = this.mRequest;
            Context var10 = this.mContext;
            MeetingUtil.buildMeetingInfo(var8, var9, var10);
            MeetingRequest var12 = this.mRequest;
            StringBuilder var13 = new StringBuilder();
            String var14 = this.mRequest.meetingInfo;
            StringBuilder var15 = var13.append(var14);
            String var16 = this.mRequest.description;
            String var17 = var15.append(var16).toString();
            var12.description = var17;
         default:
            int var5 = this.mCommand;
            MeetingRequest var6 = this.mRequest;
            String var7 = (new iCalendarCreator(var5, var6)).create_iCalendar();
            this.iCalendar = var7;
            var4 = this.iCalendar;
         }
      } else {
         if(DEBUG) {
            ll.e("MeetingInvitation", "composeVCalendar error: parameter is null");
         }

         var4 = null;
      }

      return var4;
   }

   public long create_meetingRespInTracking(Account var1, long var2) {
      if(DEBUG) {
         StringBuilder var4 = (new StringBuilder()).append("> create_MeetingRespInTracking: ");
         int var5 = this.mCommand;
         String var6 = var4.append(var5).toString();
         ll.d("MeetingInvitation", var6);
      }

      long var7;
      if(this.mRequest != null && var1 != null) {
         if(TextUtils.isEmpty(this.mRequest.uid)) {
            ll.e("MeetingInvitation", "createMeetingRespInTracking error #2: parameter is null");
            var7 = 0L;
         } else {
            int var9 = this.mCommand;
            if(var9 == 5) {
               var9 = 2;
            }

            ContentValues var10 = new ContentValues();
            ContentResolver var11 = this.mContext.getContentResolver();
            String var12 = this.mRequest.uid;
            String var13 = ExchangeUtil.getMailboxSvrIdByMailSvrId(var1, var12, var11);
            var10.put("_message", "0");
            var10.put("_collectionId", var13);
            String var14 = this.mRequest.uid;
            var10.put("_uid", var14);
            String var15 = Long.toString(var2);
            var10.put("_calendarEventId", var15);
            Integer var16 = Integer.valueOf(var9);
            var10.put("_meetingResp", var16);
            String var17 = Long.toString(var1.id);
            var10.put("_accountId", var17);
            Uri var18 = MailProvider.sEASTracking;
            long var19 = ContentUris.parseId(var11.insert(var18, var10));
            if(DEBUG) {
               String var21 = "< create_MeetingRespInTracking: " + var19;
               ll.d("MeetingInvitation", var21);
            }

            var7 = var19;
         }
      } else {
         if(DEBUG) {
            ll.e("MeetingInvitation", "createMeetingRespInTracking error #1: parameter is null");
         }

         var7 = 0L;
      }

      return var7;
   }

   public int getCommand() {
      return this.mCommand;
   }

   public MeetingRequest getMeetingRequest() {
      return this.mRequest;
   }

   public String get_iCalendar() {
      return this.iCalendar;
   }

   public void notSendMeetingResponse(int var1, long var2, Account var4, Mailbox var5) {
      if(DEBUG) {
         String var6 = "> notSendMeetingResponse: " + var1 + "," + var2;
         ll.d("MeetingInvitation", var6);
      }

      if(var2 > 0L && var4 != null) {
         Context var7 = this.mContext;
         MeetingRequest var8 = MeetingUtil.getMeetingData(var1, var4, var2, var7);
         if(var8 == null) {
            ll.e("MeetingInvitation", "notSendMeetingResponse error: request is null");
         } else {
            this.setCommand(var1);
            this.setRequest(var8);
            if(var1 == 1 || var1 == 2) {
               String var9 = var4.emailAddress;
               this.update_CalendarEvent(var9);
            }

            this.create_meetingRespInTracking(var4, 0L);
            Context var14 = this.mContext;
            ExchangeServer var15 = new ExchangeServer(var14, (Account)null);

            try {
               long var16 = var4.id;
               String var18 = var5.serverId;
               String var19 = var8.uid;
               var15.meetingResp(var16, var18, var19, var1);
            } catch (Exception var21) {
               var21.printStackTrace();
            }

            if(DEBUG) {
               ll.d("MeetingInvitation", "< notSendMeetingResponse");
            }
         }
      } else {
         ll.e("MeetingInvitation", "notSendMeetingResponse error: parameter is null");
      }
   }

   public long saveReplyMeetingMail(long var1, Account var3, MeetingRequest var4) {
      if(DEBUG) {
         ll.d("MeetingInvitation", "> saveMeetingMail");
      }

      long var5;
      if(var1 > 0L && var3 != null && var4 != null) {
         ContentResolver var7 = this.mContext.getContentResolver();
         ContentValues var8 = new ContentValues();
         Integer var9 = Integer.valueOf(MeetingUtil.meetingCmd2SmartCommand(this.mCommand));
         String var11 = "_smartCommand";
         var8.put(var11, var9);
         String var13 = var3.name;
         String var15 = "_from";
         var8.put(var15, var13);
         String var17 = var3.emailAddress;
         String var19 = "_fromEmail";
         var8.put(var19, var17);
         String var21 = var4.subject;
         String var23 = "_subject";
         var8.put(var23, var21);
         Long var25 = Long.valueOf(var3.id);
         String var27 = "_account";
         var8.put(var27, var25);
         String var30 = "_read";
         String var31 = "0";
         var8.put(var30, var31);
         Long var32 = Long.valueOf(System.currentTimeMillis());
         String var34 = "_date";
         var8.put(var34, var32);
         Long var36 = Long.valueOf(System.currentTimeMillis());
         String var38 = "_internaldate";
         var8.put(var38, var36);
         Long var40 = Long.valueOf(var3.getMailboxs().getOutMailbox().id);
         String var42 = "_mailboxId";
         var8.put(var42, var40);
         Integer var44 = Integer.valueOf('\uffff');
         String var46 = "_notaddTrack";
         var8.put(var46, var44);
         String var48 = var4.messageClass;
         String var50 = "_messageClass";
         var8.put(var50, var48);
         Boolean var52 = Boolean.valueOf(var4.allDayEvent);
         String var54 = "_allDayEvent";
         var8.put(var54, var52);
         String var57 = "_responseRequested";
         String var58 = "0";
         var8.put(var57, var58);
         String var60 = "_sensitivity";
         String var61 = "0";
         var8.put(var60, var61);
         String var63 = "_synckey";
         String var64 = "0";
         var8.put(var63, var64);
         String var65 = var4.location;
         String var67 = "_location";
         var8.put(var67, var65);
         String var69 = var4.globalObjId;
         String var71 = "_globalObjId";
         var8.put(var71, var69);
         Long var73 = Long.valueOf(var1);
         String var75 = "_refMsgId";
         var8.put(var75, var73);
         String var77 = MeetingRequest.Person.createString(var4.attendeeList);
         String var78 = MeetingRequest.Person.createString(var4.organizer);
         String var80 = "_to";
         var8.put(var80, var78);
         String var83 = "_organizer";
         var8.put(var83, var78);
         Time var85 = var4.getCloneStartTime();
         Time var86 = var4.getCloneEndTime();
         Time var87 = var4.getDTStampTime();
         String var88 = MeetingUtil.TimeToStringRFC3339(var85);
         String var89 = MeetingUtil.TimeToStringRFC3339(var86);
         String var90 = MeetingUtil.TimeToStringRFC3339(var87);
         String var92 = "_startTime";
         var8.put(var92, var88);
         String var95 = "_endTime";
         var8.put(var95, var89);
         String var98 = "_dtstamp";
         var8.put(var98, var90);
         Integer var100 = Integer.valueOf(RETRY_COUNT);
         String var102 = "_retryCount";
         var8.put(var102, var100);
         Uri var104 = MailProvider.sEASMessagesURI;
         long var108 = ContentUris.parseId(var7.insert(var104, var8));
         ContentValues var110 = new ContentValues();
         String var112 = "_mimetype";
         String var113 = "VCALENDAR";
         var110.put(var112, var113);
         String var114 = this.iCalendar;
         String var116 = "_text";
         var110.put(var116, var114);
         String var118 = UUID.randomUUID().toString();
         String var120 = "_uuid";
         var110.put(var120, var118);
         Integer var122 = Integer.valueOf(Rfc2822.CONTENTTYPE_MIXED);
         String var124 = "_contenttype";
         var110.put(var124, var122);
         String var126 = Long.toString(var108);
         String var128 = "_message";
         var110.put(var128, var126);
         String var130 = Long.toString(var3.id);
         String var132 = "_account";
         var110.put(var132, var130);
         Uri var134 = MailProvider.sEASPartsURI;
         Uri var138 = var7.insert(var134, var110);
         if(DEBUG) {
            StringBuilder var139 = (new StringBuilder()).append("< saveMeetingMail: ");
            String var142 = var139.append(var108).toString();
            ll.d("MeetingInvitation", var142);
         }

         var5 = var108;
      } else {
         ll.e("MeetingInvitation", "saveMeetingMail error: parameter is null");
         var5 = 0L;
      }

      return var5;
   }

   public long sendNoCommentMeetingResp(int var1, long var2, Account var4) {
      if(DEBUG) {
         String var5 = "> sendNoCommentMeetingResp: " + var1 + "," + var2;
         ll.d("MeetingInvitation", var5);
      }

      long var6;
      if(var2 > 0L && var4 != null) {
         Context var8 = this.mContext;
         MeetingRequest var9 = MeetingUtil.getMeetingData(var1, var4, var2, var8);
         if(var9 == null) {
            ll.e("MeetingInvitation", "sendNoCommentMeetingResp error: request is null");
            var6 = 0L;
         } else {
            this.setCommand(var1);
            this.setRequest(var9);
            if(TextUtils.isEmpty(this.create_iCalendar())) {
               ll.e("MeetingInvitation", "sendNoCommentMeetingResp error: iCalendar is null");
               var6 = 0L;
            } else {
               if(var1 == 1 || var1 == 2) {
                  String var10 = var4.emailAddress;
                  this.update_CalendarEvent(var10);
               }

               long var13 = this.saveReplyMeetingMail(var2, var4, var9);
               this.create_meetingRespInTracking(var4, 0L);
               if(DEBUG) {
                  String var17 = "< sendNoCommentMeetingResp: " + var13;
                  ll.d("MeetingInvitation", var17);
               }

               var6 = var13;
            }
         }
      } else {
         ll.e("MeetingInvitation", "sendNoCommentMeetingResp error: parameter is null");
         var6 = 0L;
      }

      return var6;
   }

   public void setCommand(int var1) {
      if(DEBUG) {
         String var2 = "set command: " + var1;
         ll.d("MeetingInvitation", var2);
      }

      this.mCommand = var1;
   }

   public void setRequest(MeetingRequest var1) {
      if(var1 == null) {
         if(DEBUG) {
            ll.e("MeetingInvitation", "setMeetingRequest error");
         }
      } else {
         this.mRequest = var1;
      }
   }

   public long update_CalendarEvent(String param1) {
      // $FF: Couldn't be decompiled
   }
}
