package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.CrashReportData;
import org.acra.ReportField;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.HttpUtils;

public class GoogleFormSender implements ReportSender {

   private Uri mFormUri = null;


   public GoogleFormSender(String var1) {
      Uri var2 = Uri.parse("https://spreadsheets.google.com/formResponse?formkey=" + var1 + "&amp;ifq");
      this.mFormUri = var2;
   }

   private Map<String, String> remap(Map<ReportField, String> var1) {
      HashMap var2 = new HashMap();
      int var3 = 0;
      ReportField[] var4 = ACRA.getConfig().customReportContent();
      if(var4.length == 0) {
         var4 = ACRA.DEFAULT_REPORT_FIELDS;
      }

      ReportField[] var5 = var4;
      int var6 = var4.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ReportField var8 = var5[var7];
         int[] var9 = GoogleFormSender.1.$SwitchMap$org$acra$ReportField;
         int var10 = ((ReportField)var8).ordinal();
         switch(var9[var10]) {
         case 1:
            String var14 = "entry." + var3 + ".single";
            StringBuilder var15 = (new StringBuilder()).append("\'");
            String var16 = (String)var1.get(var8);
            String var17 = var15.append(var16).toString();
            var2.put(var14, var17);
            break;
         case 2:
            String var19 = "entry." + var3 + ".single";
            StringBuilder var20 = (new StringBuilder()).append("\'");
            String var21 = (String)var1.get(var8);
            String var22 = var20.append(var21).toString();
            var2.put(var19, var22);
            break;
         default:
            String var11 = "entry." + var3 + ".single";
            Object var12 = var1.get(var8);
            var2.put(var11, var12);
         }

         ++var3;
      }

      return var2;
   }

   public void send(CrashReportData var1) throws ReportSenderException {
      Map var2 = this.remap(var1);
      Object var3 = var2.put("pageNumber", "0");
      Object var4 = var2.put("backupCache", "");
      Object var5 = var2.put("submit", "Envoyer");

      try {
         String var6 = this.mFormUri.toString();
         URL var7 = new URL(var6);
         String var8 = ACRA.LOG_TAG;
         StringBuilder var9 = (new StringBuilder()).append("Sending report ");
         ReportField var10 = ReportField.REPORT_ID;
         String var11 = (String)var1.get(var10);
         String var12 = var9.append(var11).toString();
         Log.d(var8, var12);
         String var14 = ACRA.LOG_TAG;
         String var15 = "Connect to " + var7;
         Log.d(var14, var15);
         HttpUtils.doPost(var2, var7, (String)null, (String)null);
      } catch (IOException var18) {
         throw new ReportSenderException("Error while sending report to Google Form.", var18);
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$org$acra$ReportField = new int[ReportField.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$org$acra$ReportField;
            int var1 = ReportField.APP_VERSION_NAME.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$org$acra$ReportField;
            int var3 = ReportField.ANDROID_VERSION.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
