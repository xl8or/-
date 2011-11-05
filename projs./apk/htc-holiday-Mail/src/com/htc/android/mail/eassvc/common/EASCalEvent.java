package com.htc.android.mail.eassvc.common;

import com.htc.android.mail.eassvc.calendar.Event;
import com.htc.android.mail.eassvc.common.EASCalException;
import java.util.ArrayList;
import java.util.Iterator;

public class EASCalEvent {

   public ArrayList<Event.Attendee> Attendees;
   public String categories;
   public String clientID;
   public String description;
   public String duration;
   public String exDate;
   public String exRule;
   public String freeBusyStatus;
   public boolean hasReminder = 0;
   public long hashValue;
   public boolean isAllDay;
   public String location;
   public String meetingStatus;
   public String organizerEmail;
   public String organizerName;
   public String rDate;
   public String rRule;
   public int recurDayOfMonth;
   public int recurDayOfWeek;
   public ArrayList<EASCalException> recurExceptions;
   public int recurInterval;
   public int recurMonthOfYear;
   public int recurOccurrences;
   public int recurType = -1;
   public int recurWeekOfMonth;
   public long reminderMinsBefore;
   public int selfAttendeeStatus = 0;
   public String serverID;
   public String strDTEnd;
   public String strDTStamp;
   public String strDTStart;
   public String strRecurUntil;
   public String summary;
   public String timezone;
   public String type;
   public String uid;


   public EASCalEvent() {}

   public String getDataString() {
      StringBuffer var1 = new StringBuffer(1024);
      StringBuffer var2 = var1.append("::clientID=");
      String var3 = this.clientID;
      var2.append(var3);
      StringBuffer var5 = var1.append(", ::serverID=");
      String var6 = this.serverID;
      var5.append(var6);
      StringBuffer var8 = var1.append(", ::uid=");
      String var9 = this.uid;
      var8.append(var9);
      StringBuffer var11 = var1.append(", ::categories=");
      String var12 = this.categories;
      var11.append(var12);
      StringBuffer var14 = var1.append(", ::type=");
      String var15 = this.type;
      var14.append(var15);
      StringBuffer var17 = var1.append(", ::strDTStamp=");
      String var18 = this.strDTStamp;
      var17.append(var18);
      StringBuffer var20 = var1.append(", ::strDTStart=");
      String var21 = this.strDTStart;
      var20.append(var21);
      StringBuffer var23 = var1.append(", ::timezone=");
      String var24 = this.timezone;
      var23.append(var24);
      StringBuffer var26 = var1.append(", ::description=");
      String var27 = this.description;
      var26.append(var27);
      StringBuffer var29 = var1.append(", ::summary=");
      String var30 = this.summary;
      var29.append(var30);
      StringBuffer var32 = var1.append(", ::duration=");
      String var33 = this.duration;
      var32.append(var33);
      StringBuffer var35 = var1.append(", ::rRule=");
      String var36 = this.rRule;
      var35.append(var36);
      StringBuffer var38 = var1.append(", ::rDate=");
      String var39 = this.rDate;
      var38.append(var39);
      StringBuffer var41 = var1.append(", ::exRule=");
      String var42 = this.exRule;
      var41.append(var42);
      StringBuffer var44 = var1.append(", ::exDate=");
      String var45 = this.exDate;
      var44.append(var45);
      StringBuffer var47 = var1.append(", ::isAllDay=");
      boolean var48 = this.isAllDay;
      var47.append(var48);
      StringBuffer var50 = var1.append(", ::freeBusyStatus=");
      String var51 = this.freeBusyStatus;
      var50.append(var51);
      StringBuffer var53 = var1.append(", ::organizerName=");
      String var54 = this.organizerName;
      var53.append(var54);
      StringBuffer var56 = var1.append(", ::organizerEmail=");
      String var57 = this.organizerEmail;
      var56.append(var57);
      StringBuffer var59 = var1.append(", ::reminderMinsBefore=");
      long var60 = this.reminderMinsBefore;
      var59.append(var60);
      StringBuffer var63 = var1.append(", ::selfAttendeeStatus=");
      int var64 = this.selfAttendeeStatus;
      var63.append(var64);
      StringBuffer var66 = var1.append(", ::recurType=");
      int var67 = this.recurType;
      var66.append(var67);
      StringBuffer var69 = var1.append(", ::recurOccurrences=");
      int var70 = this.recurOccurrences;
      var69.append(var70);
      StringBuffer var72 = var1.append(", ::recurInterval=");
      int var73 = this.recurInterval;
      var72.append(var73);
      StringBuffer var75 = var1.append(", ::recurDayOfWeek=");
      int var76 = this.recurDayOfWeek;
      var75.append(var76);
      StringBuffer var78 = var1.append(", ::recurDayOfMonth=");
      int var79 = this.recurDayOfMonth;
      var78.append(var79);
      StringBuffer var81 = var1.append(", ::recurWeekOfMonth=");
      int var82 = this.recurWeekOfMonth;
      var81.append(var82);
      StringBuffer var84 = var1.append(", ::recurMonthOfYear=");
      int var85 = this.recurMonthOfYear;
      var84.append(var85);
      StringBuffer var87 = var1.append(", ::strRecurUntil=");
      String var88 = this.strRecurUntil;
      var87.append(var88);
      StringBuffer var90 = var1.append(", ::meetingStatus=");
      String var91 = this.meetingStatus;
      var90.append(var91);
      if(this.recurExceptions == null) {
         StringBuffer var93 = var1.append(", ::recurExceptions=null");
      } else {
         StringBuffer var95 = var1.append(", ::recurExceptions=[[[");

         StringBuffer var100;
         StringBuffer var98;
         String var99;
         for(Iterator var96 = this.recurExceptions.iterator(); var96.hasNext(); var100 = var98.append(var99).append("}")) {
            EASCalException var97 = (EASCalException)var96.next();
            var98 = var1.append("{");
            var99 = var97.toString();
         }

         StringBuffer var101 = var1.append("]]]");
      }

      if(this.Attendees == null) {
         StringBuffer var94 = var1.append(", ::Attendees=null");
      } else {
         StringBuffer var102 = var1.append(", ::Attendees=[[[");

         String var106;
         StringBuffer var107;
         StringBuffer var105;
         for(Iterator var103 = this.Attendees.iterator(); var103.hasNext(); var107 = var105.append(var106).append("}")) {
            Event.Attendee var104 = (Event.Attendee)var103.next();
            var105 = var1.append("{");
            var106 = var104.toString();
         }

         StringBuffer var108 = var1.append("]]]");
      }

      return var1.toString();
   }
}
