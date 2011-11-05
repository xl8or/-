package com.android.email.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.android.email.provider.EmailContent;
import com.android.email.service.MailService;
import com.android.internal.telephony.IccUtils;

public class EmailPushReceiver extends BroadcastReceiver {

   public static final int EMAIL_PUSH_RECEIVER_WBXML_HEADER_LENGTH = 7;
   public static final String EMAIL_PUSH_RECEIVER_WBXML_SUFFIX_COM = ".com";
   public static final String EMAIL_PUSH_RECEIVER_WBXML_SUFFIX_EDU = ".edu";
   public static final String EMAIL_PUSH_RECEIVER_WBXML_SUFFIX_NET = ".net";
   public static final String EMAIL_PUSH_RECEIVER_WBXML_SUFFIX_ORG = ".org";
   public static final int EMAIL_PUSH_RECEIVER_WBXML_TOKEN_COM = 133;
   public static final int EMAIL_PUSH_RECEIVER_WBXML_TOKEN_EDU = 134;
   public static final int EMAIL_PUSH_RECEIVER_WBXML_TOKEN_NET = 135;
   public static final int EMAIL_PUSH_RECEIVER_WBXML_TOKEN_ORG = 136;
   private static final String LOG_TAG = "EmailPushReceiver";
   int[] WAP_Push_Wbxml_header;


   public EmailPushReceiver() {
      int[] var1 = new int[]{3, 13, 106, 0, 133, 7, 3};
      this.WAP_Push_Wbxml_header = var1;
   }

   public void onReceive(Context var1, Intent var2) {
      if(var2.getAction().equals("android.provider.Telephony.WAP_PUSH_RECEIVED")) {
         Bundle var3 = var2.getExtras();
         byte[] var4 = var3.getByteArray("data");
         long var5 = var3.getLong("applicationId");
         StringBuilder var7 = (new StringBuilder()).append("onReceive: user data ");
         String var8 = IccUtils.bytesToHexString(var4);
         String var9 = var7.append(var8).toString();
         int var10 = Log.d("EmailPushReceiver", var9);
         this.searchAccountAndUpdate(var1, var4);
      }
   }

   public void searchAccountAndUpdate(Context var1, byte[] var2) {
      StringBuilder var3 = new StringBuilder();

      int var4;
      for(var4 = 0; var4 < 7; ++var4) {
         byte var5 = var2[var4];
         byte var6 = (byte)this.WAP_Push_Wbxml_header[var4];
         if(var5 != var6) {
            StringBuilder var7 = (new StringBuilder()).append("user data header is wrong : user data is");
            byte var8 = var2[var4];
            String var9 = var7.append(var8).append("at i ").append(var4).toString();
            int var10 = Log.w("EmailPushReceiver", var9);
            return;
         }
      }

      var4 = 7;

      while(true) {
         int var11 = var2.length;
         if(var4 >= var11 || var2[var4] == 0) {
            int var12 = var4 + 1;
            switch(var2[var12] & 255) {
            case 133:
               StringBuilder var22 = var3.append(".com");
               break;
            case 134:
               StringBuilder var23 = var3.append(".edu");
               break;
            case 135:
               StringBuilder var24 = var3.append(".net");
               break;
            case 136:
               StringBuilder var25 = var3.append(".org");
            }

            StringBuilder var13 = (new StringBuilder()).append("searchAccountAndUpdate: email address ");
            String var14 = var3.toString();
            String var15 = var13.append(var14).toString();
            int var16 = Log.d("EmailPushReceiver", var15);
            String var17 = var3.toString();
            EmailContent.Account var18 = EmailContent.Account.restoreAccountWithEmailAddress(var1, var17);
            if(var18 == null) {
               int var19 = Log.w("EmailPushReceiver", "searchAccountAndUpdate: account is null");
               return;
            } else {
               long var26 = var18.mId;
               MailService.actionSyncOneAccount(var1, var26);
               return;
            }
         }

         char var20 = (char)var2[var4];
         var3.append(var20);
         ++var4;
      }
   }
}
