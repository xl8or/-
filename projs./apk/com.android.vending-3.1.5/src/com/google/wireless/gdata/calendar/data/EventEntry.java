package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.calendar.data.Reminder;
import com.google.wireless.gdata.calendar.data.When;
import com.google.wireless.gdata.calendar.data.Who;
import com.google.wireless.gdata.data.Entry;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class EventEntry extends Entry {

   public static final byte STATUS_CANCELED = 2;
   public static final byte STATUS_CONFIRMED = 1;
   public static final byte STATUS_TENTATIVE = 0;
   public static final byte TRANSPARENCY_OPAQUE = 0;
   public static final byte TRANSPARENCY_TRANSPARENT = 1;
   public static final byte VISIBILITY_CONFIDENTIAL = 1;
   public static final byte VISIBILITY_DEFAULT = 0;
   public static final byte VISIBILITY_PRIVATE = 2;
   public static final byte VISIBILITY_PUBLIC = 3;
   private Vector attendees;
   private String commentsUri;
   private Hashtable extendedProperties;
   private boolean guestsCanInviteOthers;
   private boolean guestsCanModify;
   private boolean guestsCanSeeGuests;
   private String organizer;
   private String originalEventId;
   private String originalEventStartTime;
   private boolean quickAdd;
   private String recurrence = null;
   private Vector reminders;
   private boolean sendEventNotifications;
   private byte status = 0;
   private byte transparency = 0;
   private byte visibility = 0;
   private Vector whens;
   private String where;


   public EventEntry() {
      Vector var1 = new Vector();
      this.attendees = var1;
      this.sendEventNotifications = (boolean)0;
      this.guestsCanModify = (boolean)0;
      this.guestsCanInviteOthers = (boolean)1;
      this.guestsCanSeeGuests = (boolean)1;
      this.organizer = null;
      Vector var2 = new Vector();
      this.whens = var2;
      this.reminders = null;
      this.originalEventId = null;
      this.originalEventStartTime = null;
      this.where = null;
      this.commentsUri = null;
      this.extendedProperties = null;
      this.quickAdd = (boolean)0;
   }

   public void addAttendee(Who var1) {
      this.attendees.add(var1);
   }

   public void addExtendedProperty(String var1, String var2) {
      if(this.extendedProperties == null) {
         Hashtable var3 = new Hashtable();
         this.extendedProperties = var3;
      }

      this.extendedProperties.put(var1, var2);
   }

   public void addReminder(Reminder var1) {
      if(this.reminders == null) {
         Vector var2 = new Vector();
         this.reminders = var2;
      }

      this.reminders.add(var1);
   }

   public void addWhen(When var1) {
      this.whens.add(var1);
   }

   public void clear() {
      super.clear();
      this.status = 0;
      this.recurrence = null;
      this.visibility = 0;
      this.transparency = 0;
      this.sendEventNotifications = (boolean)0;
      this.guestsCanModify = (boolean)0;
      this.guestsCanInviteOthers = (boolean)1;
      this.guestsCanSeeGuests = (boolean)1;
      this.organizer = null;
      this.attendees.removeAllElements();
      this.whens.removeAllElements();
      this.reminders = null;
      this.originalEventId = null;
      this.originalEventStartTime = null;
      this.where = null;
      this.commentsUri = null;
      this.extendedProperties = null;
      this.quickAdd = (boolean)0;
   }

   public void clearAttendees() {
      this.attendees.clear();
   }

   public void clearExtendedProperties() {
      this.extendedProperties = null;
   }

   public void clearReminders() {
      this.reminders = null;
   }

   public void clearWhens() {
      this.whens.clear();
   }

   public Vector getAttendees() {
      return this.attendees;
   }

   public String getCommentsUri() {
      return this.commentsUri;
   }

   public Hashtable getExtendedProperties() {
      return this.extendedProperties;
   }

   public String getExtendedProperty(String var1) {
      String var2;
      if(this.extendedProperties == null) {
         var2 = null;
      } else {
         var2 = null;
         if(this.extendedProperties.containsKey(var1)) {
            var2 = (String)this.extendedProperties.get(var1);
         }
      }

      return var2;
   }

   public When getFirstWhen() {
      When var1;
      if(this.whens.isEmpty()) {
         var1 = null;
      } else {
         var1 = (When)this.whens.elementAt(0);
      }

      return var1;
   }

   public boolean getGuestsCanInviteOthers() {
      return this.guestsCanInviteOthers;
   }

   public boolean getGuestsCanModify() {
      return this.guestsCanModify;
   }

   public boolean getGuestsCanSeeGuests() {
      return this.guestsCanSeeGuests;
   }

   public String getOrganizer() {
      return this.organizer;
   }

   public String getOriginalEventId() {
      return this.originalEventId;
   }

   public String getOriginalEventStartTime() {
      return this.originalEventStartTime;
   }

   public String getRecurrence() {
      return this.recurrence;
   }

   public Vector getReminders() {
      return this.reminders;
   }

   public boolean getSendEventNotifications() {
      return this.sendEventNotifications;
   }

   public byte getStatus() {
      return this.status;
   }

   public byte getTransparency() {
      return this.transparency;
   }

   public byte getVisibility() {
      return this.visibility;
   }

   public Vector getWhens() {
      return this.whens;
   }

   public String getWhere() {
      return this.where;
   }

   public boolean isQuickAdd() {
      return this.quickAdd;
   }

   public void setCommentsUri(String var1) {
      this.commentsUri = var1;
   }

   public void setGuestsCanInviteOthers(boolean var1) {
      this.guestsCanInviteOthers = var1;
   }

   public void setGuestsCanModify(boolean var1) {
      this.guestsCanModify = var1;
   }

   public void setGuestsCanSeeGuests(boolean var1) {
      this.guestsCanSeeGuests = var1;
   }

   public void setOrganizer(String var1) {
      this.organizer = var1;
   }

   public void setOriginalEventId(String var1) {
      this.originalEventId = var1;
   }

   public void setOriginalEventStartTime(String var1) {
      this.originalEventStartTime = var1;
   }

   public void setQuickAdd(boolean var1) {
      this.quickAdd = var1;
   }

   public void setRecurrence(String var1) {
      this.recurrence = var1;
   }

   public void setSendEventNotifications(boolean var1) {
      this.sendEventNotifications = var1;
   }

   public void setStatus(byte var1) {
      this.status = var1;
   }

   public void setTransparency(byte var1) {
      this.transparency = var1;
   }

   public void setVisibility(byte var1) {
      this.visibility = var1;
   }

   public void setWhere(String var1) {
      this.where = var1;
   }

   public void toString(StringBuffer var1) {
      super.toString(var1);
      StringBuilder var2 = (new StringBuilder()).append("STATUS: ");
      byte var3 = this.status;
      String var4 = var2.append(var3).append("\n").toString();
      var1.append(var4);
      String var6 = this.recurrence;
      this.appendIfNotNull(var1, "RECURRENCE", var6);
      StringBuilder var7 = (new StringBuilder()).append("VISIBILITY: ");
      byte var8 = this.visibility;
      String var9 = var7.append(var8).append("\n").toString();
      var1.append(var9);
      StringBuilder var11 = (new StringBuilder()).append("TRANSPARENCY: ");
      byte var12 = this.transparency;
      String var13 = var11.append(var12).append("\n").toString();
      var1.append(var13);
      String var15 = this.originalEventId;
      this.appendIfNotNull(var1, "ORIGINAL_EVENT_ID", var15);
      String var16 = this.originalEventStartTime;
      this.appendIfNotNull(var1, "ORIGINAL_START_TIME", var16);
      StringBuilder var17 = (new StringBuilder()).append("QUICK_ADD: ");
      String var18;
      if(this.quickAdd) {
         var18 = "true";
      } else {
         var18 = "false";
      }

      String var19 = var17.append(var18).toString();
      var1.append(var19);
      StringBuilder var21 = (new StringBuilder()).append("SEND_EVENT_NOTIFICATIONS: ");
      String var22;
      if(this.sendEventNotifications) {
         var22 = "true";
      } else {
         var22 = "false";
      }

      String var23 = var21.append(var22).toString();
      var1.append(var23);
      StringBuilder var25 = (new StringBuilder()).append("GUESTS_CAN_MODIFY: ");
      String var26;
      if(this.guestsCanModify) {
         var26 = "true";
      } else {
         var26 = "false";
      }

      String var27 = var25.append(var26).toString();
      var1.append(var27);
      StringBuilder var29 = (new StringBuilder()).append("GUESTS_CAN_INVITE_OTHERS: ");
      String var30;
      if(this.guestsCanInviteOthers) {
         var30 = "true";
      } else {
         var30 = "false";
      }

      String var31 = var29.append(var30).toString();
      var1.append(var31);
      StringBuilder var33 = (new StringBuilder()).append("GUESTS_CAN_SEE_GUESTS: ");
      String var34;
      if(this.guestsCanSeeGuests) {
         var34 = "true";
      } else {
         var34 = "false";
      }

      String var35 = var33.append(var34).toString();
      var1.append(var35);
      String var37 = this.organizer;
      this.appendIfNotNull(var1, "ORGANIZER", var37);
      Enumeration var38 = this.attendees.elements();

      while(var38.hasMoreElements()) {
         ((Who)var38.nextElement()).toString(var1);
      }

      Enumeration var39 = this.whens.elements();

      while(var39.hasMoreElements()) {
         ((When)var39.nextElement()).toString(var1);
      }

      if(this.reminders != null) {
         Enumeration var40 = this.reminders.elements();

         while(var40.hasMoreElements()) {
            ((Reminder)var40.nextElement()).toString(var1);
         }
      }

      String var41 = this.where;
      this.appendIfNotNull(var1, "WHERE", var41);
      String var42 = this.commentsUri;
      this.appendIfNotNull(var1, "COMMENTS", var42);
      if(this.extendedProperties != null) {
         StringBuffer var49;
         for(Enumeration var43 = this.extendedProperties.keys(); var43.hasMoreElements(); var49 = var1.append('\n')) {
            String var44 = (String)var43.nextElement();
            String var45 = (String)this.extendedProperties.get(var44);
            var1.append(var44);
            StringBuffer var47 = var1.append(':');
            var1.append(var45);
         }

      }
   }
}
