package org.acra.sender;

import android.content.Context;
import android.content.Intent;
import org.acra.ACRA;
import org.acra.CrashReportData;
import org.acra.ReportField;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

public class EmailIntentSender implements ReportSender {

   Context mContext = null;


   public EmailIntentSender(Context var1) {
      this.mContext = var1;
   }

   private String buildBody(CrashReportData var1) {
      StringBuilder var2 = new StringBuilder();
      ReportField[] var3 = ACRA.getConfig().customReportContent();
      if(var3.length == 0) {
         var3 = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
      }

      ReportField[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ReportField var7 = var4[var6];
         String var8 = var7.toString();
         StringBuilder var9 = var2.append(var8).append("=");
         String var10 = (String)var1.get(var7);
         var2.append(var10);
         StringBuilder var12 = var2.append('\n');
      }

      return var2.toString();
   }

   public void send(CrashReportData var1) throws ReportSenderException {
      Intent var2 = new Intent("android.intent.action.SEND");
      Intent var3 = var2.addFlags(268435456);
      Intent var4 = var2.setType("text/plain");
      StringBuilder var5 = new StringBuilder();
      ReportField var6 = ReportField.PACKAGE_NAME;
      String var7 = (String)var1.get(var6);
      String var8 = var5.append(var7).append(" Crash Report").toString();
      String var9 = this.buildBody(var1);
      var2.putExtra("android.intent.extra.SUBJECT", var8);
      var2.putExtra("android.intent.extra.TEXT", var9);
      String[] var12 = new String[1];
      String var13 = ACRA.getConfig().mailTo();
      var12[0] = var13;
      var2.putExtra("android.intent.extra.EMAIL", var12);
      this.mContext.startActivity(var2);
   }
}
