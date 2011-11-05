package com.htc.android.mail.pim.vcalendar;

import com.htc.android.mail.pim.vcalendar.CalendarStruct;
import com.htc.android.mail.pim.vcalendar.VCalException;
import java.io.UnsupportedEncodingException;

public class VCalComposer {

   private static final String AND_ICAL_SYNC_PROD_ID = "-//HTC//AND PIM Sync Client//";
   public static final int VERSION_VCAL10_INT = 1;
   public static final int VERSION_VCAL20_INT = 2;
   public static final String VERSION_VCALENDAR10 = "vcalendar1.0";
   public static final String VERSION_VCALENDAR20 = "vcalendar2.0";
   private static String mNewLine = "\r\n";
   private String mVersion = null;


   public VCalComposer() {}

   private String buildEventStr(CalendarStruct.EventStruct var1) {
      StringBuilder var2 = new StringBuilder();

      try {
         StringBuilder var3 = var2.append("BEGIN:VEVENT");
         String var4 = mNewLine;
         var3.append(var4);
         if(!var1.isAllday) {
            StringBuilder var6 = var2.append("X-OBJECTTYPE:APPOINTMENT");
            String var7 = mNewLine;
            var6.append(var7);
         } else {
            StringBuilder var106 = var2.append("X-OBJECTTYPE:EVENT");
            String var107 = mNewLine;
            var106.append(var107);
         }

         String var9 = var1.last_update_time;
         if(!this.isNull(var9)) {
            StringBuilder var10 = var2.append("LAST-MODIFIED:");
            String var11 = var1.last_update_time;
            StringBuilder var12 = var10.append(var11);
            String var13 = mNewLine;
            var12.append(var13);
         }

         String var15 = var1.uid;
         if(!this.isNull(var15)) {
            StringBuilder var16 = var2.append("UID:");
            String var17 = var1.uid;
            StringBuilder var18 = var16.append(var17);
            String var19 = mNewLine;
            var18.append(var19);
         }

         String var21 = var1.description;
         if(!this.isNull(var21)) {
            StringBuilder var22 = var2.append("DESCRIPTION;CHARSET=UTF-8:");
            String var23 = var1.description;
            StringBuilder var24 = var22.append(var23);
            String var25 = mNewLine;
            var24.append(var25);
         }

         String var27 = var1.dtend;
         if(!this.isNull(var27)) {
            StringBuilder var28 = var2.append("DTEND:");
            String var29 = var1.dtend;
            StringBuilder var30 = var28.append(var29);
            String var31 = mNewLine;
            var30.append(var31);
         }

         String var33 = var1.dtstart;
         if(!this.isNull(var33)) {
            StringBuilder var34 = var2.append("DTSTART:");
            String var35 = var1.dtstart;
            StringBuilder var36 = var34.append(var35);
            String var37 = mNewLine;
            var36.append(var37);
         }

         String var39 = var1.alarm;
         if(!this.isNull(var39)) {
            StringBuilder var40 = var2.append("AALARM:");
            String var41 = var1.alarm;
            StringBuilder var42 = var40.append(var41);
            String var43 = mNewLine;
            var42.append(var43);
         }

         String var45 = var1.duration;
         if(!this.isNull(var45)) {
            StringBuilder var46 = var2.append("DURATION:");
            String var47 = var1.duration;
            StringBuilder var48 = var46.append(var47);
            String var49 = mNewLine;
            var48.append(var49);
         }

         String var51 = var1.event_location;
         if(!this.isNull(var51)) {
            StringBuilder var52 = var2.append("LOCATION;CHARSET=UTF-8:");
            byte[] var53 = var1.event_location.getBytes("UTF-8");
            String var54 = new String(var53, "UTF-8");
            String var55 = this.foldingString(var54);
            StringBuilder var56 = var52.append(var55);
            String var57 = mNewLine;
            var56.append(var57);
         }

         String var59 = var1.last_date;
         if(!this.isNull(var59)) {
            StringBuilder var60 = var2.append("COMPLETED:");
            String var61 = var1.last_date;
            StringBuilder var62 = var60.append(var61);
            String var63 = mNewLine;
            var62.append(var63);
         }

         String var65 = var1.rrule;
         if(!this.isNull(var65)) {
            StringBuilder var66 = var2.append("RRULE:");
            String var67 = var1.rrule;
            StringBuilder var68 = var66.append(var67);
            String var69 = mNewLine;
            var68.append(var69);
         }

         String var71 = var1.rdate;
         if(!this.isNull(var71)) {
            StringBuilder var72 = var2.append("RDATE:");
            String var73 = var1.rdate;
            StringBuilder var74 = var72.append(var73);
            String var75 = mNewLine;
            var74.append(var75);
         }

         String var77 = var1.exrule;
         if(!this.isNull(var77)) {
            StringBuilder var78 = var2.append("EXRULE:");
            String var79 = var1.exrule;
            StringBuilder var80 = var78.append(var79);
            String var81 = mNewLine;
            var80.append(var81);
         }

         String var83 = var1.exdate;
         if(!this.isNull(var83)) {
            String var84 = var1.exdate.replace(",", ";");
            var1.exdate = var84;
            StringBuilder var85 = var2.append("EXDATE:");
            String var86 = var1.exdate;
            StringBuilder var87 = var85.append(var86);
            String var88 = mNewLine;
            var87.append(var88);
         }

         String var90 = var1.title;
         if(!this.isNull(var90)) {
            StringBuilder var91 = var2.append("SUMMARY;CHARSET=UTF-8:");
            byte[] var92 = var1.title.getBytes("UTF-8");
            String var93 = new String(var92, "UTF-8");
            String var94 = this.foldingString(var93);
            StringBuilder var95 = var91.append(var94);
            String var96 = mNewLine;
            var95.append(var96);
         }

         String var98 = var1.status;
         if(!this.isNull(var98)) {
            String var99 = "TENTATIVE";
            switch(Integer.parseInt(var1.status)) {
            case 0:
               var99 = "TENTATIVE";
               break;
            case 1:
               var99 = "CONFIRMED";
               break;
            case 2:
               var99 = "CANCELLED";
            }

            StringBuilder var100 = var2.append("STATUS:").append(var99);
            String var101 = mNewLine;
            var100.append(var101);
         }

         if(var1.has_alarm && var1.reminderList != null && var1.reminderList.size() > 0) {
            int var103 = 0;

            while(true) {
               int var104 = var1.reminderList.size();
               if(var103 >= var104) {
                  break;
               }

               CalendarStruct.EventStruct.reminderInfo var105 = (CalendarStruct.EventStruct.reminderInfo)var1.reminderList.get(var103);
               if(this.mVersion.equals("vcalendar1.0")) {
                  switch(var105.method) {
                  case 0:
                  case 1:
                  case 2:
                  }
               } else {
                  StringBuilder var109 = var2.append("BEGIN:VALARM");
                  String var110 = mNewLine;
                  StringBuilder var111 = var109.append(var110).append("ACTION:DISPLAY");
                  String var112 = mNewLine;
                  StringBuilder var113 = var111.append(var112).append("DESCRIPTION:ReminderMSG");
                  String var114 = mNewLine;
                  StringBuilder var115 = var113.append(var114).append("TRIGGER:PT");
                  long var116 = var105.minutes;
                  StringBuilder var118 = var115.append(var116).append("M");
                  String var119 = mNewLine;
                  StringBuilder var120 = var118.append(var119).append("END:VALARM");
                  String var121 = mNewLine;
                  var120.append(var121);
               }

               ++var103;
            }
         }

         StringBuilder var123 = var2.append("END:VEVENT");
         String var124 = mNewLine;
         var123.append(var124);
      } catch (UnsupportedEncodingException var126) {
         var126.printStackTrace();
      }

      return var2.toString();
   }

   private String foldingString(String var1) {
      String var2;
      if(var1 == null) {
         var2 = var1;
      } else {
         var2 = var1.replace("\\", "\\\\").replace(";", "\\;").replace(",", "\\,").replace("\"", "\\\"");
      }

      return var2;
   }

   private boolean isNull(String var1) {
      boolean var2;
      if(var1 != null && !var1.trim().equals("") && !var1.equals("null")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public String createVCal(CalendarStruct var1, int var2) throws VCalException {
      StringBuilder var3 = new StringBuilder();
      if(var2 != 1 && var2 != 2) {
         throw new VCalException("version not match 1.0 or 2.0.");
      } else {
         if(var2 == 1) {
            this.mVersion = "vcalendar1.0";
         } else {
            this.mVersion = "vcalendar2.0";
         }

         StringBuilder var4 = var3.append("BEGIN:VCALENDAR");
         String var5 = mNewLine;
         var4.append(var5);
         int var7 = 0;

         while(true) {
            int var8 = var1.eventList.size();
            if(var7 >= var8) {
               StringBuilder var12 = var3.append("END:VCALENDAR");
               String var13 = mNewLine;
               var12.append(var13);
               return var3.toString();
            }

            CalendarStruct.EventStruct var9 = (CalendarStruct.EventStruct)var1.eventList.get(var7);
            String var10 = this.buildEventStr(var9);
            var3.append(var10);
            ++var7;
         }
      }
   }
}
