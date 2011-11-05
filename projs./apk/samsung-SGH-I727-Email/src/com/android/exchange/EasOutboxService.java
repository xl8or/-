package com.android.exchange;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.telephony.SmsManager;
import com.android.email.Controller;
import com.android.email.activity.MessageList;
import com.android.email.mail.MessagingException;
import com.android.email.mail.internet.EmailHtmlUtil;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailServiceCallback;
import com.android.email.service.MailService;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class EasOutboxService extends EasSyncService {

   public static final String[] BODY_HTML_CONTENT_PROJECTION;
   public static final String[] BODY_SOURCE_PROJECTION;
   public static final String MAILBOX_KEY_AND_NOT_SEND_FAILED = "mailboxKey=? and (syncServerId is null or syncServerId!=1)";
   public static final int SEND_FAILED = 1;
   public static final int SEND_MAIL_TIMEOUT = 900000;
   public static final String WHERE_MESSAGE_KEY = "messageKey=?";
   private Controller mController;


   static {
      String[] var0 = new String[]{"sourceMessageKey"};
      BODY_SOURCE_PROJECTION = var0;
      String[] var1 = new String[]{"htmlContent"};
      BODY_HTML_CONTENT_PROJECTION = var1;
   }

   public EasOutboxService(Context var1, EmailContent.Mailbox var2) {
      super(var1, var2);
      Controller var3 = Controller.getInstance(var1);
      this.mController = var3;
   }

   private byte[] converToOpaqueLength(int var1) {
      byte[] var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         byte[] var3 = new byte[5];
         byte var4 = (byte)(var1 & 127);
         var3[4] = var4;
         var1 >>= 7;

         for(int var5 = 3; var1 > 0 && var5 >= 0; var5 += -1) {
            byte var6 = (byte)(var1 & 127 | 128);
            var3[var5] = var6;
            int var7 = var1 >> 7;
         }

         var2 = var3;
      }

      return var2;
   }

   private String convertBodyToHtml(String var1) {
      String var2 = EmailHtmlUtil.escapeCharacterToDisplay(var1);
      return "<pre style=\"white-space:pre-wrap; word-wrap:break-word\">" + var2 + "</pre>";
   }

   private void notifyOutOfMemoryErrorWhileSending(long var1, String var3) {
      Context var5 = this.mContext;
      Intent var8 = MessageList.actionHandleAccountIntent(var5, var1, 65535L, 4);
      StringBuilder var9 = new StringBuilder();
      String var10 = this.mContext.getString(2131167202);
      StringBuilder var11 = var9.append(var10).append(" (\"");
      String var13 = var11.append(var3).append("\")").toString();
      PendingIntent var14 = PendingIntent.getActivity(this.mContext, 0, var8, 134217728);
      String var15 = this.mContext.getString(2131166808);
      long var16 = System.currentTimeMillis();
      Notification var18 = new Notification(2130838098, var15, var16);
      Context var19 = this.mContext;
      String var20 = this.mContext.getString(2131166808);
      var18.setLatestEventInfo(var19, var20, var13, var14);
      String var26 = Environment.getExternalStorageState();
      if(!"mounted".equals(var26) && null.getPath().matches(".*external/.*")) {
         Uri var27 = Uri.parse("content://media/internal/audio/media/28");
         var18.sound = var27;
      } else {
         Object var33 = null;
         var18.sound = (Uri)var33;
      }

      boolean var28;
      if(((AudioManager)this.mContext.getSystemService("audio")).getRingerMode() != 2) {
         var28 = true;
      } else {
         var28 = false;
      }

      if(false || false && var28) {
         var18.haptic = 16;
      }

      int var29 = var18.flags | 1;
      var18.flags = var29;
      int var30 = var18.defaults | 4;
      var18.defaults = var30;
      NotificationManager var31 = (NotificationManager)this.mContext.getSystemService("notification");
      int var32 = MailService.NOTIFICATION_ID_SENDING_FAIL_OUTOFMEMORY;
      var31.notify(var32, var18);
   }

   private void sendCallback(long var1, String var3, int var4) {
      try {
         IEmailServiceCallback var5 = SyncManager.callback();
         var5.sendMessageStatus(var1, var3, var4, 0);
      } catch (RemoteException var11) {
         ;
      }
   }

   public static void sendMessage(Context var0, long var1, EmailContent.Message var3) {
      EmailContent.Mailbox var4 = EmailContent.Mailbox.restoreMailboxOfType(var0, var1, 4);
      if(var4 != null) {
         long var5 = var4.mId;
         var3.mMailboxKey = var5;
         var3.mAccountKey = var1;
         var3.save(var0);
      }
   }

   ArrayList<String> getNumbers(String var1) {
      ArrayList var2 = new ArrayList();
      String[] var3 = var1.split(",");
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5].split("MOBILE:")[1].replace("]", " ");
         String var7 = var6.trim();
         var2.add(var6);
      }

      return var2;
   }

   void relaySms(EmailContent.Message var1, EmailContent.Mailbox var2) {
      Context var3 = this.mContext;
      long var4 = var1.mId;
      EmailContent.Body var6 = EmailContent.Body.restoreBodyWithMessageId(var3, var4);
      if(var6 != null) {
         SmsManager var7 = SmsManager.getDefault();
         String var8 = var1.mTo;
         ArrayList var9 = this.getNumbers(var8);
         if(var6.mTextContent != null) {
            if(var6.mTextContent.length() > 0) {
               String var10 = var6.mTextContent;
               ArrayList var11 = var7.divideMessage(var10);
               Iterator var12 = var9.iterator();

               while(var12.hasNext()) {
                  String var13 = (String)var12.next();
                  Object var14 = null;
                  Object var15 = null;
                  var7.sendMultipartTextMessage(var13, (String)null, var11, (ArrayList)var14, (ArrayList)var15);
                  String[] var16 = new String[1];
                  String var17 = "Sending SMS to: " + var13;
                  var16[0] = var17;
                  this.userLog(var16);
               }

            }
         }
      }
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   int sendMessage(File param1, long param2) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }

   int sendSMS(long param1) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }
}
