package com.htc.android.mail.meeting;


public class iCalendarBase {

   public static final String ACCEPT = "ACCEPTED";
   public static final String ACTION = "ACTION";
   public static final String ATTENDEE = "ATTENDEE";
   public static final String BEGINT = "BEGIN";
   private static final String CALENDAR_ATTENDEE = "ATTENDEE;ROLE=REQ-PARTICIPANT";
   public static final String CALENDAR_ATTENDEE_ACCEPT = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=ACCEPTED";
   public static final String CALENDAR_ATTENDEE_CANCEL = "ATTENDEE;ROLE=REQ-PARTICIPANT";
   public static final String CALENDAR_ATTENDEE_DECLINE = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=DECLINED";
   public static final String CALENDAR_ATTENDEE_INVITE = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE";
   public static final String CALENDAR_ATTENDEE_TENTATIVE = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=TENTATIVE";
   public static final int CHAR_MAX_BYTES_IN_UTF8 = 4;
   public static final String CLASS = "CLASS";
   public static final String CRLF = "\r\n";
   public static final String DECLINE = "DECLINED";
   public static final String DESCRIPTION = "DESCRIPTION";
   public static final String DTEND = "DTEND";
   public static final String DTSTAMP = "DTSTAMP";
   public static final String DTSTART = "DTSTART";
   public static final String END = "END";
   public static final String LOCATION = "LOCATION";
   public static final String MAILTO = "MAILTO";
   public static final int MAX_LINE_LENGTH = 75;
   public static final String METHOD = "METHOD";
   public static final String MS_ALL_DAY = "X-MICROSOFT-CDO-ALLDAYEVENT";
   public static final String MS_IMPORTANCE = "X-MICROSOFT-CDO-IMPORTANCE";
   public static final String MS_OLK_ORIGINALEND = "X-MS-OLK-ORIGINALEND";
   public static final String MS_OLK_ORIGINALSTART = "X-MS-OLK-ORIGINALSTART";
   public static final String ORGANIZER = "ORGANIZER";
   public static final String PRIORITY = "PRIORITY";
   public static final int PRIORITY_HIGH = 2;
   public static final int PRIORITY_LOW = 0;
   public static final int PRIORITY_NORMAL = 1;
   public static final String PRODID = "PRODID";
   public static final String RECURRENCE_ID = "RECURRENCE-ID";
   public static final String RRULE = "RRULE";
   public static final String SEQUENCE = "SEQUENCE";
   public static final String STATUS = "STATUS";
   public static final String SUMMARY = "SUMMARY";
   public static final String TENTATICE = "TENTATIVE";
   public static final String TRANSP = "TRANSP";
   public static final String TRIGGER = "TRIGGER";
   public static final String UID = "UID";
   public static final String VERSION = "VERSION";
   public static final String value_ACTION_AUDIO = "AUDIO";
   public static final String value_ACTION_DISPLAY = "DISPLAY";
   public static final String value_ACTION_EMAIL = "EMAIL";
   public static final String value_ACTION_PROCEDURE = "PROCEDURE";
   public static final String value_CLASS_CONFIDENTIAL = "CONFIDENTIAL";
   public static final String value_CLASS_PRIVATE = "PRIVATE";
   public static final String value_CLASS_PUBLIC = "PUBLIC";
   public static final String value_METHOD_COUNTER = "COUNTER";
   public static final String value_METHOD_REPLY = "REPLY";
   public static final String value_METHOD_REQUEST = "REQUEST";
   public static final String value_PRIORITY_NORMAL = "5";
   public static final String value_PRODID = "-//HTC Corporation//Email 1.0 MIMEDIR//EN";
   public static final String value_SEQUENCE = "0";
   public static final String value_STATUS_CANCELLED = "CANCELLED";
   public static final String value_STATUS_CONFIRMED = "CONFIRMED";
   public static final String value_STATUS_TENTATIVE = "TENTATIVE";
   public static final String value_TRANSP_OPAQUE = "OPAQUE";
   public static final String value_TRANSP_TRANSPARENT = "TRANSPARENT";
   public static final String value_VALARM = "VALARM";
   public static final String value_VCALENDAR = "VCALENDAR";
   public static final String value_VERSION = "2.0";
   public static final String value_VEVENT = "VEVENT";


   public iCalendarBase() {}

   public static String escapeTextValue(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.length();
         StringBuilder var3 = new StringBuilder(var2);
         int var4 = 0;

         while(true) {
            int var5 = var0.length();
            if(var4 >= var5) {
               var1 = var3.toString();
               break;
            }

            char var6 = var0.charAt(var4);
            if(var6 == 10) {
               StringBuilder var7 = var3.append("\\n");
            } else if(var6 != 13) {
               if(var6 != 44 && var6 != 59 && var6 != 92) {
                  var3.append(var6);
               } else {
                  StringBuilder var8 = var3.append('\\');
                  var3.append(var6);
               }
            }

            ++var4;
         }
      }

      return var1;
   }

   public static int getCalendarAttendeeStatus(int var0) {
      byte var1 = 0;
      switch(var0) {
      case 1:
         var1 = 1;
         break;
      case 2:
         var1 = 4;
         break;
      case 3:
         var1 = 2;
      case 4:
      case 5:
      default:
         break;
      case 6:
         var1 = 3;
      }

      return var1;
   }

   public static String getMethod(int var0) {
      String var1;
      switch(var0) {
      case 4:
      case 6:
         var1 = "REQUEST";
         break;
      case 5:
         var1 = "COUNTER";
         break;
      default:
         var1 = "REPLY";
      }

      return var1;
   }

   public static String getResponseString(int var0) {
      String var1 = null;
      switch(var0) {
      case 1:
         var1 = "ACCEPTED";
         break;
      case 2:
      case 5:
         var1 = "TENTATIVE";
         break;
      case 3:
         var1 = "DECLINED";
      case 4:
      }

      return var1;
   }

   public static boolean isNeedEscape(String var0) {
      boolean var1;
      if(!"CALSCALE".equals(var0) && !"METHOD".equals(var0) && !"PRODID".equals(var0) && !"VERSION".equals(var0) && !"CATEGORIES".equals(var0) && !"CLASS".equals(var0) && !"COMMENT".equals(var0) && !"DESCRIPTION".equals(var0) && !"LOCATION".equals(var0) && !"RESOURCES".equals(var0) && !"STATUS".equals(var0) && !"SUMMARY".equals(var0) && !"TRANSP".equals(var0) && !"TZID".equals(var0) && !"TZNAME".equals(var0) && !"CONTACT".equals(var0) && !"RELATED-TO".equals(var0) && !"UID".equals(var0) && !"ACTION".equals(var0) && !"REQUEST-STATUS".equals(var0) && !"X-LIC-LOCATION".equals(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static String quoteParamValue(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         StringBuilder var2 = (new StringBuilder()).append("\"");
         String var3 = var0.replace("\"", "\'");
         var1 = var2.append(var3).append("\"").toString();
      }

      return var1;
   }
}
