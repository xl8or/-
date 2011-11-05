package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.CrashReportData;
import org.acra.ReportField;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.HttpUtils;

public class HttpPostSender implements ReportSender {

   private Uri mFormUri = null;
   private Map<ReportField, String> mMapping = null;


   public HttpPostSender(String var1, Map<ReportField, String> var2) {
      Uri var3 = Uri.parse(var1);
      this.mFormUri = var3;
      this.mMapping = var2;
   }

   private Map<String, String> remap(Map<ReportField, String> var1) {
      int var2 = var1.size();
      HashMap var3 = new HashMap(var2);
      ReportField[] var4 = ACRA.getConfig().customReportContent();
      if(var4.length == 0) {
         var4 = ACRA.DEFAULT_REPORT_FIELDS;
      }

      ReportField[] var5 = var4;
      int var6 = var4.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ReportField var8 = var5[var7];
         if(this.mMapping != null && this.mMapping.get(var8) != null) {
            Object var12 = this.mMapping.get(var8);
            Object var13 = var1.get(var8);
            var3.put(var12, var13);
         } else {
            String var9 = var8.toString();
            Object var10 = var1.get(var8);
            var3.put(var9, var10);
         }
      }

      return var3;
   }

   public void send(CrashReportData var1) throws ReportSenderException {
      try {
         Map var2 = this.remap(var1);
         String var3 = this.mFormUri.toString();
         URL var4 = new URL(var3);
         String var5 = ACRA.LOG_TAG;
         StringBuilder var6 = (new StringBuilder()).append("Connect to ");
         String var7 = var4.toString();
         String var8 = var6.append(var7).toString();
         Log.d(var5, var8);
         String var10 = ACRA.getConfig().formUriBasicAuthLogin();
         String var11 = ACRA.getConfig().formUriBasicAuthPassword();
         HttpUtils.doPost(var2, var4, var10, var11);
      } catch (Exception var13) {
         throw new ReportSenderException("Error while sending report to Http Post Form.", var13);
      }
   }
}
