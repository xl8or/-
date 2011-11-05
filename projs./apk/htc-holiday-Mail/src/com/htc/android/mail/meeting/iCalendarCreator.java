package com.htc.android.mail.meeting;

import android.text.TextUtils;
import android.text.format.Time;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import com.htc.android.mail.meeting.MeetingRequest;
import com.htc.android.mail.meeting.MeetingUtil;
import com.htc.android.mail.meeting.iCalendarBase;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;

public class iCalendarCreator extends iCalendarBase {

   private static boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "iCalendarCreator";
   private static final Charset UTF_8;
   private static final int sCurrentYear = (new GregorianCalendar()).get(1);
   private static final String[] sDayTokens;
   private static final TimeZone sGmtTimeZone = TimeZone.getTimeZone("GMT");
   private int mCmd;
   private final ByteArrayOutputStream mOut;
   private MeetingRequest mRequest;


   static {
      String[] var0 = new String[]{"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
      sDayTokens = var0;
      UTF_8 = Charset.forName("UTF-8");
   }

   public iCalendarCreator(int var1, MeetingRequest var2) {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.mOut = var3;
      this.mCmd = var1;
      this.mRequest = var2;
   }

   static GregorianCalendar findTransitionDate(TimeZone var0, long var1, long var3, boolean var5) {
      long var6 = var3;

      while(var3 - var1 > 60000L) {
         long var8 = (var1 + var3) / 2L + 1L;
         Date var10 = new Date(var8);
         if(var0.inDaylightTime(var10) != var5) {
            var3 = var8;
         } else {
            var1 = var8;
         }
      }

      GregorianCalendar var13;
      if(var3 == var6) {
         var13 = null;
      } else {
         GregorianCalendar var12 = new GregorianCalendar(var0);
         var12.setTimeInMillis(var1);
         var13 = var12;
      }

      return var13;
   }

   static int getTrueTransitionHour(GregorianCalendar var0) {
      int var1 = var0.get(11) + 1;
      if(var1 == 24) {
         var1 = 0;
      }

      return var1;
   }

   static int getTrueTransitionMinute(GregorianCalendar var0) {
      int var1 = var0.get(12);
      if(var1 == 59) {
         var1 = 0;
      }

      return var1;
   }

   static iCalendarCreator.RRule inferRRuleFromCalendars(GregorianCalendar[] var0) {
      GregorianCalendar var1 = var0[0];
      iCalendarCreator.RRule var21;
      if(var1 == null) {
         var21 = null;
      } else {
         int var2 = var1.get(2);
         int var3 = var1.get(5);
         int var4 = var1.get(7);
         int var5 = var1.get(8);
         int var6 = var1.getActualMaximum(8);
         int var7 = var5;
         int var8 = 1;
         boolean var9 = false;
         boolean var10 = false;

         while(true) {
            int var11 = var0.length;
            if(var8 >= var11) {
               if(var10) {
                  int var19 = var2 + 1;
                  var21 = new iCalendarCreator.RRule(var19, var3);
               } else {
                  int var20 = var2 + 1;
                  var21 = new iCalendarCreator.RRule(var20, var4, var7);
               }
               break;
            }

            var1 = var0[var8];
            if(var1 == null) {
               var21 = null;
               break;
            }

            if(var1.get(2) != var2) {
               var21 = null;
               break;
            }

            int var12 = var1.get(7);
            int var15;
            boolean var17;
            boolean var16;
            if(var4 == var12) {
               if(var10) {
                  var21 = null;
                  break;
               }

               var9 = true;
               int var13 = var1.get(8);
               if(var7 != var13) {
                  label66: {
                     if(var7 < 0 || var7 == var6) {
                        int var14 = var1.getActualMaximum(8);
                        if(var13 == var14) {
                           var15 = -1;
                           var16 = var10;
                           var17 = var9;
                           break label66;
                        }
                     }

                     var21 = null;
                     break;
                  }
               } else {
                  var16 = var10;
                  var15 = var7;
                  var17 = var9;
               }
            } else {
               int var18 = var1.get(5);
               if(var3 != var18) {
                  var21 = null;
                  break;
               }

               if(var9) {
                  var21 = null;
                  break;
               }

               var16 = true;
               var17 = var9;
               var15 = var7;
            }

            ++var8;
            var7 = var15;
            var9 = var17;
            var10 = var16;
         }
      }

      return var21;
   }

   private static boolean isFirstUtf8Byte(byte var0) {
      boolean var1;
      if((var0 & 192) != 128) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void timeZoneToVTimezone(TimeZone var1) throws IOException {
      int var2 = (int)((long)var1.getRawOffset() / 60000L);
      String var3 = utcOffsetString(var2);
      this.writeTag("BEGIN", "VTIMEZONE");
      String var4 = var1.getID();
      this.writeTag("TZID", var4);
      String var5 = var1.getDisplayName();
      this.writeTag("X-LIC-LOCATION", var5);
      if(!var1.useDaylightTime()) {
         this.writeNoDST(var1, var3);
      } else {
         byte var6 = 3;
         GregorianCalendar[] var7 = new GregorianCalendar[var6];
         GregorianCalendar[] var8 = new GregorianCalendar[var6];
         if(!this.getDSTCalendars(var1, var7, var8)) {
            this.writeNoDST(var1, var3);
         } else {
            iCalendarCreator.RRule var9 = inferRRuleFromCalendars(var7);
            iCalendarCreator.RRule var10 = inferRRuleFromCalendars(var8);
            String var11 = utcOffsetString((int)((long)var1.getDSTSavings() / 60000L) + var2);
            boolean var12;
            if(var9 != null && var10 != null) {
               var12 = true;
            } else {
               var12 = false;
            }

            this.writeTag("BEGIN", "DAYLIGHT");
            this.writeTag("TZOFFSETFROM", var3);
            this.writeTag("TZOFFSETTO", var11);
            String var13 = transitionMillisToVCalendarTime(var7[0].getTimeInMillis(), var1, (boolean)1);
            this.writeTag("DTSTART", var13);
            if(var12) {
               String var14 = var9.toString();
               this.writeTag("RRULE", var14);
            } else {
               for(int var17 = 1; var17 < var6; ++var17) {
                  String var18 = transitionMillisToVCalendarTime(var7[var17].getTimeInMillis(), var1, (boolean)1);
                  this.writeTag("RDATE", var18);
               }
            }

            this.writeTag("END", "DAYLIGHT");
            this.writeTag("BEGIN", "STANDARD");
            this.writeTag("TZOFFSETFROM", var11);
            this.writeTag("TZOFFSETTO", var3);
            String var15 = transitionMillisToVCalendarTime(var8[0].getTimeInMillis(), var1, (boolean)0);
            this.writeTag("DTSTART", var15);
            if(var12) {
               String var16 = var10.toString();
               this.writeTag("RRULE", var16);
            } else {
               for(int var19 = 1; var19 < var6; ++var19) {
                  String var20 = transitionMillisToVCalendarTime(var8[var19].getTimeInMillis(), var1, (boolean)1);
                  this.writeTag("RDATE", var20);
               }
            }

            this.writeTag("END", "STANDARD");
            this.writeTag("END", "VTIMEZONE");
         }
      }
   }

   private static byte[] toUtf8(String var0) {
      Charset var1 = UTF_8;
      CharBuffer var2 = CharBuffer.wrap(var0);
      ByteBuffer var3 = var1.encode(var2);
      byte[] var4 = new byte[var3.limit()];
      var3.get(var4);
      return var4;
   }

   static String transitionMillisToVCalendarTime(long var0, TimeZone var2, boolean var3) {
      StringBuilder var4 = new StringBuilder();
      GregorianCalendar var5 = new GregorianCalendar(var2);
      var5.setTimeInMillis(var0);
      int var6 = var5.get(1);
      var4.append(var6);
      String var8 = MeetingUtil.formatTwo(var5.get(2) + 1);
      var4.append(var8);
      String var10 = MeetingUtil.formatTwo(var5.get(5));
      var4.append(var10);
      StringBuilder var12 = var4.append('T');
      String var13 = MeetingUtil.formatTwo(getTrueTransitionHour(var5));
      var4.append(var13);
      String var15 = MeetingUtil.formatTwo(getTrueTransitionMinute(var5));
      var4.append(var15);
      String var17 = MeetingUtil.formatTwo(0);
      var4.append(var17);
      return var4.toString();
   }

   static String utcOffsetString(int var0) {
      StringBuilder var1 = new StringBuilder();
      int var2 = var0 / 60;
      if(var2 < 0) {
         StringBuilder var3 = var1.append('-');
         var2 = 0 - var2;
      } else {
         StringBuilder var9 = var1.append('+');
      }

      int var4 = var0 % 60;
      if(var2 < 10) {
         StringBuilder var5 = var1.append('0');
      }

      var1.append(var2);
      if(var4 < 10) {
         StringBuilder var7 = var1.append('0');
      }

      var1.append(var4);
      return var1.toString();
   }

   private void writeLine(String var1) {
      if(!TextUtils.isEmpty(var1)) {
         int var2 = 0;
         byte[] var3 = toUtf8(var1);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte var6 = var3[var5];
            if(var2 > 71 && isFirstUtf8Byte(var6)) {
               this.mOut.write(13);
               this.mOut.write(10);
               this.mOut.write(9);
               var2 = 1;
            }

            this.mOut.write(var6);
            ++var2;
         }

         this.mOut.write(13);
         this.mOut.write(10);
      }
   }

   private void writeNoDST(TimeZone var1, String var2) throws IOException {
      this.writeTag("BEGIN", "STANDARD");
      this.writeTag("TZOFFSETFROM", var2);
      this.writeTag("TZOFFSETTO", var2);
      TimeZone var3 = sGmtTimeZone;
      TimeZone var4 = sGmtTimeZone;
      String var5 = MeetingUtil.millisToEasDateTime(0L, var3, (boolean)1, var4);
      this.writeTag("DTSTART", var5);
      this.writeTag("END", "STANDARD");
      this.writeTag("END", "VTIMEZONE");
   }

   private void writeTag(String var1, String var2) {
      if(!TextUtils.isEmpty(var2)) {
         if(iCalendarBase.isNeedEscape(var2)) {
            var2 = iCalendarBase.escapeTextValue(var2);
         }

         String var3 = var1 + ":" + var2;
         this.writeLine(var3);
      }
   }

   public String create_iCalendar() {
      String var1;
      if(this.mRequest == null) {
         var1 = null;
      } else {
         if(DEBUG) {
            StringBuilder var2 = (new StringBuilder()).append("create iCalendar: ");
            int var3 = this.mCmd;
            String var4 = var2.append(var3).toString();
            ll.d("iCalendarCreator", var4);
         }

         String var5 = iCalendarBase.getMethod(this.mCmd);
         String var6 = "";
         TimeZone var7 = sGmtTimeZone;
         if(this.mRequest.allDayEvent) {
            var6 = ";VALUE=DATE";
         }

         String var9 = "BEGIN";
         String var10 = "VCALENDAR";
         this.writeTag(var9, var10);
         String var12 = "METHOD";
         this.writeTag(var12, var5);
         String var15 = "PRODID";
         String var16 = "-//HTC Corporation//Email 1.0 MIMEDIR//EN";
         this.writeTag(var15, var16);
         String var18 = "VERSION";
         String var19 = "2.0";
         this.writeTag(var18, var19);
         int var20 = this.mCmd;
         byte var21 = 6;
         if(var20 == var21 && !this.mRequest.allDayEvent && !TextUtils.isEmpty(this.mRequest.rrule)) {
            try {
               this.timeZoneToVTimezone(var7);
            } catch (Exception var224) {
               var224.printStackTrace();
            }

            StringBuilder var24 = (new StringBuilder()).append(";TZID=");
            String var25 = var7.getID();
            var6 = var24.append(var25).toString();
         }

         String var27 = "BEGIN";
         String var28 = "VEVENT";
         this.writeTag(var27, var28);
         if(!TextUtils.isEmpty(this.mRequest.globalObjId)) {
            String var29 = this.mRequest.globalObjId;
            String var31 = "UID";
            this.writeTag(var31, var29);
         }

         Time var33 = this.mRequest.getDTStampTime();
         if(var33 == null) {
            var33 = new Time;
            String var35 = "UTC";
            var33.<init>(var35);
            var33.setToNow();
         }

         String var36 = MeetingUtil.TimeToString1(var33);
         String var38 = "DTSTAMP";
         this.writeTag(var38, var36);
         Time var40 = this.mRequest.getCloneStartTime();
         byte var45;
         if(var40 != null) {
            byte var42 = 0;
            long var43 = var40.toMillis((boolean)var42);
            if(!this.mRequest.allDayEvent) {
               var45 = 1;
            } else {
               var45 = 0;
            }

            TimeZone var46 = sGmtTimeZone;
            String var52 = MeetingUtil.millisToEasDateTime(var43, var7, (boolean)var45, var46);
            StringBuilder var53 = (new StringBuilder()).append("DTSTART");
            String var55 = var53.append(var6).toString();
            this.writeTag(var55, var52);
         }

         if(this.mRequest.isException && this.mRequest.originalInstanceTime > 0L) {
            StringBuilder var59 = (new StringBuilder()).append("RECURRENCE-ID");
            String var61 = var59.append(var6).toString();
            long var62 = this.mRequest.originalInstanceTime;
            byte var64;
            if(!this.mRequest.allDayEvent) {
               var64 = 1;
            } else {
               var64 = 0;
            }

            TimeZone var65 = sGmtTimeZone;
            String var71 = MeetingUtil.millisToEasDateTime(var62, var7, (boolean)var64, var65);
            this.writeTag(var61, var71);
         }

         Time var75 = this.mRequest.getCloneEndTime();
         if(var75 != null) {
            byte var77 = 0;
            long var78 = var75.toMillis((boolean)var77);
            if(!this.mRequest.allDayEvent) {
               var45 = 1;
            } else {
               var45 = 0;
            }

            TimeZone var80 = sGmtTimeZone;
            String var86 = MeetingUtil.millisToEasDateTime(var78, var7, (boolean)var45, var80);
            StringBuilder var87 = (new StringBuilder()).append("DTEND");
            String var89 = var87.append(var6).toString();
            this.writeTag(var89, var86);
         }

         int var93 = this.mCmd;
         byte var94 = 6;
         String var96;
         if(var93 != var94 && this.mRequest.organizer != null) {
            MeetingRequest.Person var95 = this.mRequest.organizer;
            var96 = "ORGANIZER";
            if(!TextUtils.isEmpty(var95.name)) {
               StringBuilder var97 = new StringBuilder();
               StringBuilder var99 = var97.append(var96).append(";CN=");
               String var100 = iCalendarBase.quoteParamValue(var95.name);
               var96 = var99.append(var100).toString();
            }

            StringBuilder var101 = (new StringBuilder()).append("MAILTO:");
            String var102 = var95.addr;
            String var103 = var101.append(var102).toString();
            this.writeTag(var96, var103);
         }

         if(this.mRequest.attendeeList != null && this.mRequest.attendeeList.size() > 0) {
            int var107 = this.mCmd;
            byte var108 = 6;
            Iterator var109;
            if(var107 == var108) {
               var109 = this.mRequest.attendeeList.iterator();

               while(var109.hasNext()) {
                  MeetingRequest.Person var110 = (MeetingRequest.Person)var109.next();
                  var96 = "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE";
                  if(!TextUtils.isEmpty(var110.name)) {
                     StringBuilder var111 = new StringBuilder();
                     StringBuilder var113 = var111.append(var96).append(";CN=");
                     String var114 = iCalendarBase.quoteParamValue(var110.name);
                     String var115 = var113.append(var114).toString();
                  }

                  StringBuilder var116 = (new StringBuilder()).append("MAILTO:");
                  String var117 = var110.addr;
                  String var118 = var116.append(var117).toString();
                  this.writeTag(var96, var118);
               }
            } else {
               label152: {
                  int var122 = this.mCmd;
                  byte var123 = 1;
                  if(var122 != var123) {
                     int var124 = this.mCmd;
                     byte var125 = 3;
                     if(var124 != var125) {
                        int var126 = this.mCmd;
                        byte var127 = 2;
                        if(var126 != var127) {
                           int var128 = this.mCmd;
                           byte var129 = 5;
                           if(var128 != var129) {
                              int var142 = this.mCmd;
                              byte var143 = 4;
                              if(var142 == var143) {
                                 ;
                              }
                              break label152;
                           }
                        }
                     }
                  }

                  var109 = this.mRequest.attendeeList.iterator();

                  while(var109.hasNext()) {
                     MeetingRequest.Person var130 = (MeetingRequest.Person)var109.next();
                     String var131 = getResponseString(this.mCmd);
                     String var132 = var130.getName();
                     StringBuilder var133 = (new StringBuilder()).append("ATTENDEE;PARTSTAT=");
                     StringBuilder var135 = var133.append(var131).append(";CN=");
                     String var137 = var135.append(var132).append(":MAILTO").toString();
                     String var138 = var130.addr;
                     this.writeTag(var137, var138);
                  }
               }
            }
         }

         int var144 = this.mCmd;
         byte var145 = 6;
         if(var144 == var145 && !TextUtils.isEmpty(this.mRequest.rrule)) {
            String var146 = this.mRequest.rrule;
            String var148 = "RRULE";
            this.writeTag(var148, var146);
         }

         String var150 = "SUMMARY";
         String var151;
         if(this.mRequest.summary == null) {
            var151 = "";
         } else {
            var151 = this.mRequest.summary;
         }

         this.writeTag(var150, var151);
         String var155 = this.mRequest.location;
         String var157 = "LOCATION";
         this.writeTag(var157, var155);
         String var159 = this.mRequest.description;
         String var161 = "DESCRIPTION";
         this.writeTag(var161, var159);
         String var164 = "CLASS";
         String var165 = "PUBLIC";
         this.writeTag(var164, var165);
         String var167 = "TRANSP";
         String var168 = "OPAQUE";
         this.writeTag(var167, var168);
         String var170 = "SEQUENCE";
         String var171 = "0";
         this.writeTag(var170, var171);
         String var173 = "PRIORITY";
         String var174 = "5";
         this.writeTag(var173, var174);
         String var175 = Integer.toString(this.mRequest.importance);
         String var177 = "X-MICROSOFT-CDO-IMPORTANCE";
         this.writeTag(var177, var175);
         String var179 = "REPLY";
         if(var179.equals(var5)) {
            String var182 = "STATUS";
            String var183 = "CONFIRMED";
            this.writeTag(var182, var183);
         }

         String var184 = "REQUEST";
         if(var184.equals(var5)) {
            String var186 = "X-MICROSOFT-CDO-ALLDAYEVENT";
            byte var187 = this.mRequest.allDayEvent;
            byte var188 = 1;
            String var189;
            if(var187 == var188) {
               var189 = "TRUE";
            } else {
               var189 = "FALSE";
            }

            this.writeTag(var186, var189);
         }

         int var193 = this.mCmd;
         byte var194 = 5;
         if(var193 == var194) {
            String var195 = MeetingUtil.RFC3339ToString1(this.mRequest.originalStartTime);
            String var196 = MeetingUtil.RFC3339ToString1(this.mRequest.originalEndTime);
            String var198 = "X-MS-OLK-ORIGINALSTART";
            this.writeTag(var198, var195);
            String var201 = "X-MS-OLK-ORIGINALEND";
            this.writeTag(var201, var196);
         }

         if(this.mRequest.reminder > 0) {
            String var204 = "BEGIN";
            String var205 = "VALARM";
            this.writeTag(var204, var205);
            StringBuilder var206 = (new StringBuilder()).append("-PT");
            int var207 = this.mRequest.reminder;
            String var208 = var206.append(var207).append("M").toString();
            String var210 = "TRIGGER";
            this.writeTag(var210, var208);
            String var213 = "ACTION";
            String var214 = "AUDIO";
            this.writeTag(var213, var214);
            String var216 = "END";
            String var217 = "VALARM";
            this.writeTag(var216, var217);
         }

         String var219 = "END";
         String var220 = "VEVENT";
         this.writeTag(var219, var220);
         String var222 = "END";
         String var223 = "VCALENDAR";
         this.writeTag(var222, var223);
         var1 = this.get_vCalendar();
      }

      return var1;
   }

   boolean getDSTCalendars(TimeZone var1, GregorianCalendar[] var2, GregorianCalendar[] var3) {
      int var4 = var2.length;
      int var5 = var3.length;
      boolean var7;
      if(var5 != var4) {
         var7 = false;
      } else {
         int var8 = 0;

         while(true) {
            if(var8 >= var4) {
               var7 = true;
               break;
            }

            GregorianCalendar var11 = new GregorianCalendar(var1);
            int var14 = sCurrentYear + var8;
            var11.set(var14, 0, 1, 0, 0, 0);
            long var15 = var11.getTimeInMillis();
            long var17 = 31536000000L + var15 + 21600000L;
            Date var19 = new Date(var15);
            boolean var22 = var1.inDaylightTime(var19);
            GregorianCalendar var23 = findTransitionDate(var1, var15, var17, var22);
            if(var23 == null) {
               var7 = false;
               break;
            }

            if(var22) {
               var3[var8] = var23;
            } else {
               var2[var8] = var23;
            }

            byte var24;
            if(!var22) {
               var24 = 1;
            } else {
               var24 = 0;
            }

            var23 = findTransitionDate(var1, var15, var17, (boolean)var24);
            if(var23 == null) {
               var7 = false;
               break;
            }

            if(var22) {
               var2[var8] = var23;
            } else {
               var3[var8] = var23;
            }

            ++var8;
         }
      }

      return var7;
   }

   public String get_vCalendar() {
      String var4;
      if(this.mOut != null) {
         String var1;
         try {
            this.mOut.flush();
            var1 = this.mOut.toString();
         } catch (IOException var5) {
            var5.printStackTrace();
            var4 = null;
            return var4;
         }

         if(DEBUG) {
            String var3 = "get_vCalendar: \n" + var1;
            ll.d("iCalendarCreator", var3);
         }

         var4 = var1;
      } else {
         var4 = null;
      }

      return var4;
   }

   static class RRule {

      static final int RRULE_DATE = 2;
      static final int RRULE_DAY_WEEK = 1;
      static final int RRULE_NONE;
      int date;
      int dayOfWeek;
      int month;
      int type;
      int week;


      RRule(int var1, int var2) {
         this.type = 2;
         this.month = var1;
         this.date = var2;
      }

      RRule(int var1, int var2, int var3) {
         this.type = 1;
         this.month = var1;
         this.dayOfWeek = var2;
         this.week = var3;
      }

      public String toString() {
         String var9;
         if(this.type == 1) {
            StringBuilder var1 = (new StringBuilder()).append("FREQ=YEARLY;BYMONTH=");
            int var2 = this.month;
            StringBuilder var3 = var1.append(var2).append(";BYDAY=");
            int var4 = this.week;
            StringBuilder var5 = var3.append(var4);
            String[] var6 = iCalendarCreator.sDayTokens;
            int var7 = this.dayOfWeek - 1;
            String var8 = var6[var7];
            var9 = var5.append(var8).toString();
         } else {
            StringBuilder var10 = (new StringBuilder()).append("FREQ=YEARLY;BYMONTH=");
            int var11 = this.month;
            StringBuilder var12 = var10.append(var11).append(";BYMONTHDAY=");
            int var13 = this.date;
            var9 = var12.append(var13).toString();
         }

         return var9;
      }
   }
}
