package com.htc.android.mail.meeting;

import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import com.htc.android.mail.Mail;
import com.htc.android.mail.meeting.MeetingRecurrences;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class MeetingRequest implements Serializable {

   private static final long serialVersionUID = 1L;
   public long accountId;
   public boolean allDayEvent;
   public ArrayList<MeetingRequest.Person> attendeeList;
   public int busyStatus;
   public String category;
   public String description;
   private Time dtStamp;
   public String duration;
   private Time endTime;
   public String globalObjId;
   public long id;
   public int importance = 1;
   public int instanceType;
   public boolean isException;
   public String location;
   public String meetingInfo;
   public String messageClass;
   public MeetingRequest.Person organizer;
   public String originalEndTime;
   public long originalInstanceTime;
   public String originalStartTime;
   private Time recurrenceId;
   public MeetingRecurrences recurrences;
   public int reminder;
   public boolean responseRequested;
   public String rrule;
   public int sensitivity = 0;
   private Time startTime;
   public String subject;
   public String summary;
   public String timeZone;
   public String uid;


   public MeetingRequest() {
      Time var1 = new Time("UTC");
      this.startTime = var1;
      Time var2 = new Time("UTC");
      this.endTime = var2;
      Time var3 = new Time("UTC");
      this.dtStamp = var3;
      Time var4 = new Time("UTC");
      this.recurrenceId = var4;
      MeetingRequest.Person var5 = new MeetingRequest.Person();
      this.organizer = var5;
      ArrayList var6 = new ArrayList();
      this.attendeeList = var6;
      MeetingRecurrences var7 = new MeetingRecurrences();
      this.recurrences = var7;
   }

   public Time getCloneEndTime() {
      Time var1 = this.endTime;
      return new Time(var1);
   }

   public Time getCloneStartTime() {
      Time var1 = this.startTime;
      return new Time(var1);
   }

   public Time getDTStampTime() {
      return this.dtStamp;
   }

   public Time getEndTime() {
      return this.endTime;
   }

   public Time getRecurrenceId() {
      return this.recurrenceId;
   }

   public Time getStartTime() {
      return this.startTime;
   }

   public void setDTStampTime(Time var1) {
      this.dtStamp = var1;
   }

   public void setEndTime(Time var1) {
      if(var1 != null) {
         this.endTime = var1;
      }
   }

   public void setRecurrenceId(Time var1) {
      this.recurrenceId = var1;
   }

   public void setStartTime(Time var1) {
      if(var1 != null) {
         this.startTime = var1;
      }
   }

   public static class InstanceType {

      public static final int Exception_of_Recurs = 3;
      public static final int Master_Recurs_Appointment = 1;
      public static final int Single_Appointment = 0;
      public static final int Single_Instance_of_Recurs_Appointment = 2;


      public InstanceType() {}
   }

   public static class BusyStatus {

      public static final int Busy = 0;
      public static final int Free = 1;
      public static final int OutOfOffice = 3;
      public static final int Tentative = 2;


      public BusyStatus() {}
   }

   public static class Person {

      public String addr;
      public String name;


      public Person() {}

      public static String createString(MeetingRequest.Person var0) {
         String var1;
         if(var0 == null) {
            var1 = null;
         } else if(var0.addr == null) {
            if(Mail.MAIL_DEBUG) {
               int var2 = Log.d("MeetingRequest", "createString null");
            }

            var1 = null;
         } else {
            String var3 = var0.name;
            String var4 = var0.addr;
            if(TextUtils.isEmpty(var3)) {
               String[] var5 = var4.split("@");
               if(var5 != null) {
                  var3 = var5[0];
               }
            }

            var1 = "\"" + var3 + "\" <" + var4 + ">";
         }

         return var1;
      }

      public static String createString(ArrayList<MeetingRequest.Person> var0) {
         String var1;
         if(var0 != null && var0.size() > 0) {
            StringBuilder var2 = new StringBuilder();
            Iterator var3 = var0.iterator();

            while(var3.hasNext()) {
               String var4 = createString((MeetingRequest.Person)var3.next());
               if(!TextUtils.isEmpty(var4)) {
                  if(var2 == null) {
                     var2 = new StringBuilder();
                     var2.append(var4);
                  } else {
                     StringBuilder var6 = var2.append(", ");
                     var2.append(var4);
                  }
               }
            }

            if(var2 == null) {
               var1 = null;
            } else {
               var1 = var2.toString();
            }
         } else {
            var1 = null;
         }

         return var1;
      }

      public String getName() {
         String var1;
         if(!TextUtils.isEmpty(this.name)) {
            var1 = this.name;
         } else {
            String[] var2 = this.addr.split("@");
            if(var2 != null) {
               var1 = var2[0];
            } else {
               var1 = "";
            }
         }

         return var1;
      }
   }

   public static class Sensitivity {

      public static final int Confidential = 3;
      public static final int Normal = 0;
      public static final int Personal = 1;
      public static final int Proviate = 2;


      public Sensitivity() {}
   }
}
