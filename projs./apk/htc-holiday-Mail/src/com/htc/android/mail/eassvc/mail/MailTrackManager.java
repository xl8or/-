package com.htc.android.mail.eassvc.mail;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.util.EASLog;
import java.util.ArrayList;

public class MailTrackManager {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "MailTrackManager";


   public MailTrackManager() {}

   public static void addSendMeetingMailRecord(long var0, long var2, Context var4) {
      if(DEBUG) {
         String var5 = "addSendMeetingMailRecord: " + var2;
         EASLog.d("MailTrackManager", var0, var5);
      }

      ContentValues var6 = new ContentValues();
      Long var7 = Long.valueOf(var0);
      var6.put("_accountId", var7);
      Integer var8 = Integer.valueOf(0);
      var6.put("_message", var8);
      Integer var9 = Integer.valueOf(0);
      var6.put("_modify", var9);
      Integer var10 = Integer.valueOf(0);
      var6.put("_delete", var10);
      Integer var11 = Integer.valueOf(0);
      var6.put("_move", var11);
      Integer var12 = Integer.valueOf(6);
      var6.put("_meetingResp", var12);
      Long var13 = Long.valueOf(var2);
      var6.put("_calendarEventId", var13);
      ContentResolver var14 = var4.getContentResolver();
      Uri var15 = EASSyncCommon.EASTRACKING_URI;
      var14.insert(var15, var6);
   }

   public static void delEmptyFolderCommand(long var0, String var2, Context var3) {
      ContentResolver var4 = var3.getContentResolver();
      Object[] var5 = new Object[2];
      Long var6 = Long.valueOf(var0);
      var5[0] = var6;
      var5[1] = var2;
      String var7 = String.format("_accountId=%d AND _collectionId=\'%s\' AND _delete=2", var5);
      Uri var8 = EASSyncCommon.EASTRACKING_URI;
      int var9 = var4.delete(var8, var7, (String[])null);
      if(DEBUG) {
         String var10 = "delEmptyFolderCommand: " + var9;
         EASLog.d("MailTrackManager", var10);
      }
   }

   public static int deleteAccountCommand(long var0, Context var2) {
      Object[] var3 = new Object[1];
      Long var4 = Long.valueOf(var0);
      var3[0] = var4;
      String var5 = String.format("_accountId=%d", var3);
      ContentResolver var6 = var2.getContentResolver();
      Uri var7 = EASSyncCommon.EASTRACKING_URI;
      return var6.delete(var7, var5, (String[])null);
   }

   public static void deleteMeetingMailRecord(long var0, long var2, Context var4) {
      Object[] var5 = new Object[3];
      Long var6 = Long.valueOf(var0);
      var5[0] = var6;
      Integer var7 = Integer.valueOf(6);
      var5[1] = var7;
      Long var8 = Long.valueOf(var2);
      var5[2] = var8;
      String var9 = String.format("_accountId=%d AND _meetingResp=%d AND _calendarEventId=%d", var5);
      ContentResolver var10 = var4.getContentResolver();
      Uri var11 = EASSyncCommon.EASTRACKING_URI;
      int var12 = var10.delete(var11, var9, (String[])null);
      if(DEBUG) {
         String var13 = "deleteMeetingMailRecord: " + var2 + ", " + var12;
         EASLog.d("MailTrackManager", var0, var13);
      }
   }

   public static int deleteMeetingResp(long var0, String var2, String var3, int var4, Context var5) {
      Object[] var6 = new Object[]{var3, var2, null, null};
      Integer var7 = Integer.valueOf(var4);
      var6[2] = var7;
      Long var8 = Long.valueOf(var0);
      var6[3] = var8;
      String var9 = String.format("_uid=\'%s\' AND _collectionId=\'%s\' AND _meetingResp=%d AND _accountId=%d", var6);
      ContentResolver var10 = var5.getContentResolver();
      Uri var11 = EASSyncCommon.EASTRACKING_URI;
      return var10.delete(var11, var9, (String[])null);
   }

   public static ArrayList<String> getDelList(long param0, String param2, Context param3) {
      // $FF: Couldn't be decompiled
   }

   public static boolean getEmptyFolderCommand(long param0, String param2, Context param3) {
      // $FF: Couldn't be decompiled
   }

   public static ArrayList<EASSyncCommon.EASMeetingResp> getMeetingInvitation(long param0, Context param2) {
      // $FF: Couldn't be decompiled
   }

   public static ArrayList<EASSyncCommon.EASMeetingResp> getMeetingResp(long param0, Context param2) {
      // $FF: Couldn't be decompiled
   }

   public static long getMeetingRespCalendarEventId(long param0, String param2, String param3, int param4, Context param5) {
      // $FF: Couldn't be decompiled
   }

   public static ArrayList<EASSyncCommon.EASUpdInfo> getUpdList(long param0, String param2, Context param3) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isMeetingRecordExist(long param0, long param2, Context param4) {
      // $FF: Couldn't be decompiled
   }
}
