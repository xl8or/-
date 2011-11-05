package com.digc.seven;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import com.android.email.Email;
import com.android.email.combined.AccountFacade;
import com.android.email.service.DoExternalRequest;
import com.digc.seven.SevenMessageManager;
import java.util.Iterator;

public class SevenBroadCastReceiver extends BroadcastReceiver {

   private static String SEVEN_ACTION;
   private final String TAG = "SevenBroadCastReceiver";


   public SevenBroadCastReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.i("SevenBroadCastReceiver", "==============================================");
      int var4 = Log.i("SevenBroadCastReceiver", "\tSevenBroadCastReceiver.onReceive() ");
      StringBuilder var5 = (new StringBuilder()).append("\tintent.getAction() :  ");
      String var6 = var2.getAction();
      String var7 = var5.append(var6).toString();
      int var8 = Log.i("SevenBroadCastReceiver", var7);
      int var9 = Log.i("SevenBroadCastReceiver", "==============================================");
      SEVEN_ACTION = var2.getAction();
      if(SEVEN_ACTION != null) {
         if(SEVEN_ACTION.equals("com.seven.Z7.EMAIL_RECEIVED")) {
            StringBuilder var10 = (new StringBuilder()).append("SEVEN_ACTION = ");
            String var11 = SEVEN_ACTION;
            String var12 = var10.append(var11).append(" :EVENT_EMAIL_RECEIVED").toString();
            int var13 = Log.d("SevenBroadCastReceiver", var12);
            StringBuilder var14 = (new StringBuilder()).append("EXTRA_MESSAGE_ID = ");
            int var15 = var2.getIntExtra("message_id", -1);
            String var16 = var14.append(var15).toString();
            int var17 = Log.d("SevenBroadCastReceiver", var16);
            StringBuilder var18 = (new StringBuilder()).append("EXTRA_IS_READ = ");
            boolean var19 = var2.getBooleanExtra("is_read", (boolean)0);
            String var20 = var18.append(var19).toString();
            int var21 = Log.d("SevenBroadCastReceiver", var20);
            StringBuilder var22 = (new StringBuilder()).append("EXTRA_DATE = ");
            long var23 = var2.getLongExtra("date", 65535L);
            String var25 = var22.append(var23).toString();
            int var26 = Log.d("SevenBroadCastReceiver", var25);
            StringBuilder var27 = (new StringBuilder()).append("EXTRA_IS_INCOMING = ");
            boolean var28 = var2.getBooleanExtra("is_incoming", (boolean)0);
            String var29 = var27.append(var28).toString();
            int var30 = Log.d("SevenBroadCastReceiver", var29);
            StringBuilder var31 = (new StringBuilder()).append("EXTRA_EMAIL = ");
            String var32 = var2.getStringExtra("email");
            String var33 = var31.append(var32).toString();
            int var34 = Log.d("SevenBroadCastReceiver", var33);
            StringBuilder var35 = (new StringBuilder()).append("EXTRA_SUBJECT = ");
            String var36 = var2.getStringExtra("subject");
            String var37 = var35.append(var36).toString();
            int var38 = Log.d("SevenBroadCastReceiver", var37);
            StringBuilder var39 = (new StringBuilder()).append("EXTRA_MESSAGE = ");
            String var40 = var2.getStringExtra("message");
            String var41 = var39.append(var40).toString();
            int var42 = Log.d("SevenBroadCastReceiver", var41);
            StringBuilder var43 = (new StringBuilder()).append("EXTRA_ACCOUNT_ID = ");
            int var44 = var2.getIntExtra("account_id", -1);
            String var45 = var43.append(var44).toString();
            int var46 = Log.d("SevenBroadCastReceiver", var45);
            SevenMessageManager.makeMessage(var2, var1);
            long var47 = (long)var2.getIntExtra("message_id", -1);
            DoExternalRequest.sendBroadcastSevenMessageAdd(var1, var47);
            int var49 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_EMAIL_RECEIVED BroadCast----------------------");
         } else if(SEVEN_ACTION.equals("com.seven.Z7.EMAIL_SENT")) {
            StringBuilder var50 = (new StringBuilder()).append("SEVEN_ACTION = ");
            String var51 = SEVEN_ACTION;
            String var52 = var50.append(var51).append(" :EVENT_EMAIL_SENT").toString();
            int var53 = Log.d("SevenBroadCastReceiver", var52);
            StringBuilder var54 = (new StringBuilder()).append("EXTRA_MESSAGE_ID = ");
            int var55 = var2.getIntExtra("message_id", -1);
            String var56 = var54.append(var55).toString();
            int var57 = Log.d("SevenBroadCastReceiver", var56);
            StringBuilder var58 = (new StringBuilder()).append("EXTRA_IS_READ = ");
            boolean var59 = var2.getBooleanExtra("is_read", (boolean)0);
            String var60 = var58.append(var59).toString();
            int var61 = Log.d("SevenBroadCastReceiver", var60);
            StringBuilder var62 = (new StringBuilder()).append("EXTRA_DATE = ");
            long var63 = var2.getLongExtra("date", 65535L);
            String var65 = var62.append(var63).toString();
            int var66 = Log.d("SevenBroadCastReceiver", var65);
            StringBuilder var67 = (new StringBuilder()).append("EXTRA_IS_INCOMING = ");
            boolean var68 = var2.getBooleanExtra("is_incoming", (boolean)0);
            String var69 = var67.append(var68).toString();
            int var70 = Log.d("SevenBroadCastReceiver", var69);
            StringBuilder var71 = (new StringBuilder()).append("EXTRA_EMAIL = ");
            String var72 = var2.getStringExtra("email");
            String var73 = var71.append(var72).toString();
            int var74 = Log.d("SevenBroadCastReceiver", var73);
            StringBuilder var75 = (new StringBuilder()).append("EXTRA_SUBJECT = ");
            String var76 = var2.getStringExtra("subject");
            String var77 = var75.append(var76).toString();
            int var78 = Log.d("SevenBroadCastReceiver", var77);
            StringBuilder var79 = (new StringBuilder()).append("EXTRA_MESSAGE = ");
            String var80 = var2.getStringExtra("message");
            String var81 = var79.append(var80).toString();
            int var82 = Log.d("SevenBroadCastReceiver", var81);
            StringBuilder var83 = (new StringBuilder()).append("EXTRA_ACCOUNT_ID = ");
            String var84 = var2.getStringExtra("account_id");
            String var85 = var83.append(var84).toString();
            int var86 = Log.d("SevenBroadCastReceiver", var85);
            int var87 = var2.getIntExtra("message_id", -1);
            SevenMessageManager.basicMoveToSent(var1, var87);
            int var88 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_EMAIL_SENT BroadCast----------------------");
         } else if(SEVEN_ACTION.equals("com.seven.Z7.EMAIL_REMOVED")) {
            String var89 = SEVEN_ACTION;
            SevenMessageManager.syncUpdateMessage(var2, var1, var89);
            int var90 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_EMAIL_REMOVED BroadCast----------------------");
         } else if(SEVEN_ACTION.equals("com.seven.Z7.EMAIL_UPDATED")) {
            String var91 = SEVEN_ACTION;
            SevenMessageManager.syncUpdateMessage(var2, var1, var91);
            int var92 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_EMAIL_UPDATED BroadCast----------------------");
         } else if(SEVEN_ACTION.equals("com.seven.Z7.FOLDER_ADDED")) {
            String var93 = SEVEN_ACTION;
            SevenMessageManager.syncFolder(var2, var1, var93);
            int var94 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_FOLDER_ADDED BroadCast----------------------");
         } else if(SEVEN_ACTION.equals("com.seven.Z7.FOLDER_REMOVED")) {
            String var95 = SEVEN_ACTION;
            SevenMessageManager.syncFolder(var2, var1, var95);
            int var96 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_FOLDER_REMOVED BroadCast----------------------");
         } else if(SEVEN_ACTION.equals("com.seven.Z7.ACCOUNT_REMOVED")) {
            if(var2.hasExtra("isp_type")) {
               StringBuilder var97 = (new StringBuilder()).append("SEVEN_ACTION = ");
               String var98 = SEVEN_ACTION;
               String var99 = var97.append(var98).append(" : EVENT_ACCOUNT_REMOVED from IM.").toString();
               int var100 = Log.d("SevenBroadCastReceiver", var99);
            } else {
               int var101 = Log.d("SevenBroadCastReceiver", "--------------------End EVENT_ACCOUNT_REMOVED BroadCast----------------------");
               AccountFacade var102 = Email.getAccountFacade();
               int var103 = var2.getIntExtra("account_id", -1);
               var102.removeAccount(var103);
            }
         } else if(SEVEN_ACTION.equals("com.seven.Z7.ACCOUNT_ADDED")) {
            if(var2.hasExtra("isp_type")) {
               StringBuilder var104 = (new StringBuilder()).append("SEVEN_ACTION = ");
               String var105 = SEVEN_ACTION;
               String var106 = var104.append(var105).append(" : ACCOUNT_ADDED from IM.").toString();
               int var107 = Log.d("SevenBroadCastReceiver", var106);
            } else {
               StringBuilder var108 = (new StringBuilder()).append("SEVEN_ACTION = ");
               String var109 = SEVEN_ACTION;
               String var110 = var108.append(var109).append(" : ACCOUNT_ADDED").toString();
               int var111 = Log.d("SevenBroadCastReceiver", var110);
               AccountFacade var112 = Email.getAccountFacade();
               int var113 = var2.getIntExtra("account_id", -1);
               String var114 = var2.getStringExtra("account_name");
               var112.addedAccount(var113, var114);
               int var115 = Log.d("SevenBroadCastReceiver", "--------------------End ACCOUNT_ADD BroadCast----------------------");
            }
         } else if(SEVEN_ACTION.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
            if(var2.getDataString().equals("package:com.seven.Z7")) {
               StringBuilder var116 = (new StringBuilder()).append("SEVEN_ACTION = ");
               String var117 = SEVEN_ACTION;
               String var118 = var116.append(var117).toString();
               int var119 = Log.d("SevenBroadCastReceiver", var118);
               int var120 = Log.d("SevenBroadCastReceiver", "SEVEN_ACTION = package:com.seven.Z7 ");
               Email.deleteSevenAccountForEmail(var1);
               int var121 = Log.d("SevenBroadCastReceiver", "data clear success");
               ((ActivityManager)var1.getSystemService("activity")).killBackgroundProcesses("com.android.email");
               Process.killProcess(Process.myPid());
            }
         } else if(SEVEN_ACTION.equals("android.intent.action.PACKAGE_REMOVED")) {
            StringBuilder var122 = (new StringBuilder()).append("getBooleanExtra = ");
            boolean var123 = var2.getBooleanExtra("android.intent.extra.DATA_REMOVED", (boolean)0);
            String var124 = var122.append(var123).toString();
            int var125 = Log.d("SevenBroadCastReceiver", var124);
            if(var2.getDataString().equals("package:com.seven.Z7")) {
               if(var2.getBooleanExtra("android.intent.extra.DATA_REMOVED", (boolean)0)) {
                  StringBuilder var126 = (new StringBuilder()).append("SEVEN_ACTION = ");
                  String var127 = SEVEN_ACTION;
                  String var128 = var126.append(var127).toString();
                  int var129 = Log.d("SevenBroadCastReceiver", var128);
                  int var130 = Log.d("SevenBroadCastReceiver", "SEVEN_ACTION = package:com.seven.Z7 ");
                  Email.deleteSevenAccountForEmail(var1);
                  int var131 = Log.d("SevenBroadCastReceiver", "data clear success");
               }

               ((ActivityManager)var1.getSystemService("activity")).killBackgroundProcesses("com.android.email");
               Process.killProcess(Process.myPid());
            }
         } else if(!SEVEN_ACTION.equals("com.seven.Z7.RANGE_EMAIL_REMOVED")) {
            StringBuilder var143 = (new StringBuilder()).append("SEVEN_ACTION = ");
            String var144 = SEVEN_ACTION;
            String var145 = var143.append(var144).append(" :Not Used Action Event").toString();
            int var146 = Log.d("SevenBroadCastReceiver", var145);
         } else {
            StringBuilder var132 = (new StringBuilder()).append("SEVEN_ACTION = ");
            String var133 = SEVEN_ACTION;
            String var134 = var132.append(var133).append(" :EVENT_RANGE_EMAIL_REMOVED ").toString();
            int var135 = Log.d("SevenBroadCastReceiver", var134);

            int var141;
            String var140;
            for(Iterator var136 = var2.getExtras().keySet().iterator(); var136.hasNext(); var141 = Log.d("SevenBroadCastReceiver", var140)) {
               String var137 = (String)var136.next();
               StringBuilder var138 = (new StringBuilder()).append("key = ").append(var137).append(" value= ");
               Object var139 = var2.getExtra(var137);
               var140 = var138.append(var139).toString();
            }

            SevenMessageManager.syncMessageTimeLimit(var2, var1);
            int var142 = Log.d("SevenBroadCastReceiver", "--------------------End TimeLimitMessage BroadCast----------------------");
         }
      }
   }
}
