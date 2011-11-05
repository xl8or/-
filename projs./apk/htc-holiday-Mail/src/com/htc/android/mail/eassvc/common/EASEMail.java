package com.htc.android.mail.eassvc.common;

import java.util.ArrayList;

public class EASEMail {

   public String AllDayEvent;
   public String Att0Id;
   public String AttMethod;
   public String AttName;
   public String AttRemoved;
   public String AttSize;
   public String Attachment;
   public ArrayList<String> AttachmentDisplayName;
   public String Attachments;
   public String Body;
   public String BodySize;
   public String BodyTruncated;
   public String CC;
   public String Category;
   public String ClientId;
   public String ClientName;
   public String CompleteTime;
   public String ContentClass;
   public String DTStamp;
   public String Data;
   public String DateReceived;
   public String DisplayTo;
   public String EndTime;
   public String EstimatedDataSize;
   public String Flag;
   public String FlagStatus;
   public String FlagType;
   public String From;
   public String GlobalObjId;
   public String Importance;
   public String InstanceType;
   public String IntDBusyStatus;
   public String InternetCPID;
   public String Location;
   public String MIMEData;
   public String MIMESize;
   public String MIMETruncated;
   public String MessageClass;
   public String NaviteBodyType;
   public String Organizer;
   public String Read;
   public String RecurrenceId;
   public String Recurrence_DayOfMonth;
   public String Recurrence_DayOfWeek;
   public String Recurrence_Interval;
   public String Recurrence_MonthOfYear;
   public String Recurrence_Occurrences;
   public String Recurrence_Type;
   public String Recurrence_Until;
   public String Recurrence_WeekOfMonth;
   public String Reminder;
   public String ReplyTo;
   public String ResponseRequested;
   public String Sensitivity;
   public String ServerID;
   public String StartTime;
   public String Subject;
   public String ThreadTopic;
   public String TimeZone;
   public String To;
   public String Truncated;
   public String Type;
   public boolean isMimeData;
   public ArrayList<EASEMail.AttachInfo> mailAttachment;


   public EASEMail() {
      ArrayList var1 = new ArrayList();
      this.AttachmentDisplayName = var1;
      ArrayList var2 = new ArrayList();
      this.mailAttachment = var2;
      this.isMimeData = (boolean)0;
      this.CC = "";
      this.AllDayEvent = "0";
      this.ResponseRequested = "0";
      this.Sensitivity = "0";
      String var3 = Integer.toString(-1);
      this.Recurrence_Type = var3;
   }

   public static class AttachInfo {

      public String AttachmentContentId = null;
      public String AttachmentDisplayName = null;
      public String AttachmentEstimatedDataSize = null;
      public String AttachmentFilePath = null;
      public String AttachmentFileRef = null;
      public String AttachmentIsInLine = null;
      public String AttachmentMethod = null;
      public long id = 65535L;


      public AttachInfo() {}
   }
}
