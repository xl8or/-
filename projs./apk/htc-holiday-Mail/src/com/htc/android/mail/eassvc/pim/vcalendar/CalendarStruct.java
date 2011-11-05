package com.htc.android.mail.eassvc.pim.vcalendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarStruct {

   public List<CalendarStruct.EventStruct> eventList;
   public String timezone;


   public CalendarStruct() {}

   public void addEventList(CalendarStruct.EventStruct var1) {
      if(this.eventList == null) {
         ArrayList var2 = new ArrayList();
         this.eventList = var2;
      }

      this.eventList.add(var1);
   }

   public static class EventStruct {

      public String description;
      public String dtend;
      public String dtstart;
      public String duration;
      public String event_location;
      public String exdate;
      public String exrule;
      public boolean has_alarm;
      public String last_date;
      public String rdate;
      public ArrayList<CalendarStruct.EventStruct.reminderInfo> reminderList;
      public String rrule;
      public String status;
      public String title;
      public String uid;


      public EventStruct() {}

      public void setReminders(ArrayList<CalendarStruct.EventStruct.reminderInfo> var1) {
         if(this.reminderList == null) {
            ArrayList var2 = new ArrayList();
            this.reminderList = var2;
         }

         int var3 = 0;

         while(true) {
            int var4 = var1.size();
            if(var3 >= var4) {
               return;
            }

            ArrayList var5 = this.reminderList;
            Object var6 = var1.get(var3);
            var5.add(var6);
            ++var3;
         }
      }

      public static class reminderInfo {

         public final int method;
         public final long minutes;


         public reminderInfo(long var1, int var3) {
            this.minutes = var1;
            this.method = var3;
         }

         public String toString() {
            StringBuffer var1 = new StringBuffer(20);
            StringBuffer var2 = var1.append(" :re:minutes=");
            long var3 = this.minutes;
            var2.append(var3);
            StringBuffer var6 = var1.append(", :re:method=");
            int var7 = this.method;
            var6.append(var7);
            return var1.toString();
         }
      }
   }
}
