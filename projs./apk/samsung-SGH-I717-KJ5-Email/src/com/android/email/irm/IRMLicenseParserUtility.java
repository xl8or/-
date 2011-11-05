package com.android.email.irm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.email.Utility;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.adapter.AbstractSyncParser;
import java.io.IOException;

public class IRMLicenseParserUtility {

   private static final String IRM_CONTENT_EXPIRY_MAXDATE = "12082020t6789407z";
   private static final int IRM_EDIT_ALLOWED = 3;
   private static final int IRM_EXPORT_ALLOWED = 6;
   private static final int IRM_EXTRACT_ALLOWED = 5;
   private static final int IRM_FORWARD_ALLOWED = 1;
   private static final int IRM_MODIFY_RECEPIENTS_ALLOWED = 4;
   private static final int IRM_PRINT_ALLOWED = 7;
   private static final int IRM_PROGRAMATIC_ACCESS_ALLOWED = 8;
   private static final int IRM_REPLY_ALLOWED = 0;
   private static final int IRM_REPLY_ALL_ALLOWED = 2;
   public static boolean mRenewLicense = 0;


   public IRMLicenseParserUtility() {}

   public static void parseLicense(EmailContent.Message var0, AbstractSyncParser var1) throws IOException {
      if(Eas.USER_LOG) {
         int var2 = Log.i("IRM", "IRMParserUtility:parse license");
      }

      var0.mIRMLicenseFlag = 0;

      while(var1.nextTag(1544) != 3) {
         switch(var1.tag) {
         case 1545:
            int var10 = var1.getValueInt();
            int var11 = var0.mIRMLicenseFlag;
            int var12 = var10 << 3;
            int var13 = var11 | var12;
            var0.mIRMLicenseFlag = var13;
            break;
         case 1546:
            int var14 = var1.getValueInt();
            int var15 = var0.mIRMLicenseFlag;
            int var16 = var14 << 0;
            int var17 = var15 | var16;
            var0.mIRMLicenseFlag = var17;
            break;
         case 1547:
            int var6 = var1.getValueInt();
            int var7 = var0.mIRMLicenseFlag;
            int var8 = var6 << 2;
            int var9 = var7 | var8;
            var0.mIRMLicenseFlag = var9;
            break;
         case 1548:
            int var18 = var1.getValueInt();
            int var19 = var0.mIRMLicenseFlag;
            int var20 = var18 << 1;
            int var21 = var19 | var20;
            var0.mIRMLicenseFlag = var21;
            break;
         case 1549:
            int var26 = var1.getValueInt();
            int var27 = var0.mIRMLicenseFlag;
            int var28 = var26 << 4;
            int var29 = var27 | var28;
            var0.mIRMLicenseFlag = var29;
            break;
         case 1550:
            int var30 = var1.getValueInt();
            int var31 = var0.mIRMLicenseFlag;
            int var32 = var30 << 5;
            int var33 = var31 | var32;
            var0.mIRMLicenseFlag = var33;
            break;
         case 1551:
            int var35 = var1.getValueInt();
            int var36 = var0.mIRMLicenseFlag;
            int var37 = var35 << 7;
            int var38 = var36 | var37;
            var0.mIRMLicenseFlag = var38;
            break;
         case 1552:
            int var22 = var1.getValueInt();
            int var23 = var0.mIRMLicenseFlag;
            int var24 = var22 << 6;
            int var25 = var23 | var24;
            var0.mIRMLicenseFlag = var25;
            break;
         case 1553:
            int var39 = var1.getValueInt();
            int var40 = var0.mIRMLicenseFlag;
            int var41 = var39 << 8;
            int var42 = var40 | var41;
            var0.mIRMLicenseFlag = var42;
            break;
         case 1554:
            int var3 = var1.getValueInt();
            var0.mIRMOwner = var3;
            break;
         case 1555:
            String var34 = var1.getValue();
            var0.mIRMContentExpiryDate = var34;
            if(var0.mIRMContentExpiryDate.equals("12082020t6789407z")) {
               mRenewLicense = (boolean)0;
            } else {
               mRenewLicense = (boolean)1;
            }
            break;
         case 1556:
            String var4 = var1.getValue();
            var0.mIRMTemplateId = var4;
            break;
         case 1557:
         case 1558:
         default:
            var1.skipTag();
            break;
         case 1559:
            String var5 = var1.getValue();
            var0.mIRMContentOwner = var5;
         }
      }

   }

   public static void renewLicense(String var0, String var1, Context var2) {
      if(Eas.USER_LOG) {
         int var3 = Log.i("IRM", "IRMParserUtility :Inside renew license if IRM");
      }

      Intent var4 = new Intent();
      Intent var5 = var4.setAction("expiry");
      var4.putExtra("MessageId", var1);
      PendingIntent var7 = PendingIntent.getBroadcast(var2, 0, var4, 0);
      AlarmManager var8 = (AlarmManager)var2.getSystemService("alarm");
      long var9 = Utility.parseEmailDateTimeToMillis(var0);
      var8.set(0, var9, var7);
   }
}
