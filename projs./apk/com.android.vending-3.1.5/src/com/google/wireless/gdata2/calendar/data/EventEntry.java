package com.google.wireless.gdata2.calendar.data;

import com.google.wireless.gdata2.calendar.data.Reminder;
import com.google.wireless.gdata2.calendar.data.When;
import com.google.wireless.gdata2.calendar.data.Who;
import com.google.wireless.gdata2.data.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventEntry extends Entry {

   public static final byte STATUS_CANCELED = 2;
   public static final byte STATUS_CONFIRMED = 1;
   public static final byte STATUS_EMPTY = -1;
   public static final byte STATUS_TENTATIVE = 0;
   public static final byte TRANSPARENCY_EMPTY = -1;
   public static final byte TRANSPARENCY_OPAQUE = 0;
   public static final byte TRANSPARENCY_TRANSPARENT = 1;
   public static final byte VISIBILITY_CONFIDENTIAL = 1;
   public static final byte VISIBILITY_DEFAULT = 0;
   public static final byte VISIBILITY_EMPTY = -1;
   public static final byte VISIBILITY_PRIVATE = 2;
   public static final byte VISIBILITY_PUBLIC = 3;
   private final Set<Who> attendees;
   private String calendarUrl;
   private String commentsUri;
   private final Map<String, String> extendedProperties;
   private boolean guestsCanInviteOthers;
   private boolean guestsCanModify;
   private boolean guestsCanSeeGuests;
   private String organizer;
   private String originalEventId;
   private String originalEventStartTime;
   private boolean quickAdd;
   private String recurrence = null;
   private final Set<Reminder> reminders;
   private boolean sendEventNotifications;
   private byte status = 0;
   private byte transparency = 0;
   private String uid;
   private byte visibility = 0;
   private final List<When> whens;
   private String where;


   public EventEntry() {
      HashSet var1 = new HashSet();
      this.attendees = var1;
      this.sendEventNotifications = (boolean)0;
      this.guestsCanModify = (boolean)0;
      this.guestsCanInviteOthers = (boolean)1;
      this.guestsCanSeeGuests = (boolean)1;
      this.organizer = null;
      ArrayList var2 = new ArrayList();
      this.whens = var2;
      HashSet var3 = new HashSet();
      this.reminders = var3;
      this.originalEventId = null;
      this.originalEventStartTime = null;
      this.where = null;
      this.commentsUri = null;
      HashMap var4 = new HashMap();
      this.extendedProperties = var4;
      this.quickAdd = (boolean)0;
      this.calendarUrl = null;
      this.uid = null;
   }

   public EventEntry(EventEntry var1) {
      super(var1);
      HashSet var2 = new HashSet();
      this.attendees = var2;
      this.sendEventNotifications = (boolean)0;
      this.guestsCanModify = (boolean)0;
      this.guestsCanInviteOthers = (boolean)1;
      this.guestsCanSeeGuests = (boolean)1;
      this.organizer = null;
      ArrayList var3 = new ArrayList();
      this.whens = var3;
      HashSet var4 = new HashSet();
      this.reminders = var4;
      this.originalEventId = null;
      this.originalEventStartTime = null;
      this.where = null;
      this.commentsUri = null;
      HashMap var5 = new HashMap();
      this.extendedProperties = var5;
      this.quickAdd = (boolean)0;
      this.calendarUrl = null;
      this.uid = null;
      byte var6 = var1.status;
      this.status = var6;
      String var7 = var1.recurrence;
      this.recurrence = var7;
      byte var8 = var1.visibility;
      this.visibility = var8;
      byte var9 = var1.transparency;
      this.transparency = var9;
      Set var10 = this.attendees;
      Set var11 = var1.attendees;
      var10.addAll(var11);
      boolean var13 = var1.sendEventNotifications;
      this.sendEventNotifications = var13;
      boolean var14 = var1.guestsCanModify;
      this.guestsCanModify = var14;
      boolean var15 = var1.guestsCanInviteOthers;
      this.guestsCanInviteOthers = var15;
      boolean var16 = var1.guestsCanSeeGuests;
      this.guestsCanSeeGuests = var16;
      String var17 = var1.organizer;
      this.organizer = var17;
      List var18 = this.whens;
      List var19 = var1.whens;
      var18.addAll(var19);
      Set var21 = this.reminders;
      Set var22 = var1.reminders;
      var21.addAll(var22);
      String var24 = var1.originalEventId;
      this.originalEventId = var24;
      String var25 = var1.originalEventStartTime;
      this.originalEventStartTime = var25;
      String var26 = var1.where;
      this.where = var26;
      String var27 = var1.commentsUri;
      this.commentsUri = var27;
      Map var28 = this.extendedProperties;
      Map var29 = var1.extendedProperties;
      var28.putAll(var29);
      boolean var30 = var1.quickAdd;
      this.quickAdd = var30;
      String var31 = var1.calendarUrl;
      this.calendarUrl = var31;
      String var32 = var1.uid;
      this.uid = var32;
   }

   public void addAttendee(Who var1) {
      this.attendees.add(var1);
   }

   public void addExtendedProperty(String var1, String var2) {
      this.extendedProperties.put(var1, var2);
   }

   public void addReminder(Reminder var1) {
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
      this.attendees.clear();
      this.whens.clear();
      this.originalEventId = null;
      this.originalEventStartTime = null;
      this.where = null;
      this.commentsUri = null;
      this.quickAdd = (boolean)0;
      this.calendarUrl = null;
      this.uid = null;
   }

   public void clearAttendees() {
      this.attendees.clear();
   }

   public void clearExtendedProperties() {
      this.extendedProperties.clear();
      this.quickAdd = (boolean)0;
   }

   public void clearReminders() {
      this.reminders.clear();
   }

   public void clearWhens() {
      this.whens.clear();
   }

   public Set<Who> getAttendees() {
      return this.attendees;
   }

   public String getCalendarUrl() {
      return this.calendarUrl;
   }

   public String getCommentsUri() {
      return this.commentsUri;
   }

   public Map<String, String> getExtendedProperties() {
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
         var1 = (When)this.whens.get(0);
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

   public Set<Reminder> getReminders() {
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

   public String getUid() {
      return this.uid;
   }

   public byte getVisibility() {
      return this.visibility;
   }

   public List<When> getWhens() {
      return this.whens;
   }

   public String getWhere() {
      return this.where;
   }

   public boolean isQuickAdd() {
      return this.quickAdd;
   }

   public void setCalendarUrl(String var1) {
      this.calendarUrl = var1;
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

   public void setUid(String var1) {
      this.uid = var1;
   }

   public void setVisibility(byte var1) {
      this.visibility = var1;
   }

   public void setWhere(String var1) {
      this.where = var1;
   }

   public void toString(StringBuffer var1) {
      super.toString(var1);
      StringBuffer var2 = var1.append("STATUS: ");
      byte var3 = this.status;
      StringBuffer var4 = var2.append(var3).append("\n");
      String var5 = this.recurrence;
      this.appendIfNotNull(var1, "RECURRENCE", var5);
      StringBuffer var6 = var1.append("VISIBILITY: ");
      byte var7 = this.visibility;
      StringBuffer var8 = var6.append(var7).append("\n");
      StringBuffer var9 = var1.append("TRANSPARENCY: ");
      byte var10 = this.transparency;
      StringBuffer var11 = var9.append(var10).append("\n");
      String var12 = this.originalEventId;
      this.appendIfNotNull(var1, "ORIGINAL_EVENT_ID", var12);
      String var13 = this.originalEventStartTime;
      this.appendIfNotNull(var1, "ORIGINAL_START_TIME", var13);
      StringBuffer var14 = var1.append("QUICK_ADD: ");
      String var15;
      if(this.quickAdd) {
         var15 = "true";
      } else {
         var15 = "false";
      }

      StringBuffer var16 = var14.append(var15).append("\n");
      StringBuffer var17 = var1.append("SEND_EVENT_NOTIFICATIONS: ");
      String var18;
      if(this.sendEventNotifications) {
         var18 = "true";
      } else {
         var18 = "false";
      }

      StringBuffer var19 = var17.append(var18).append("\n");
      StringBuffer var20 = var1.append("GUESTS_CAN_MODIFY: ");
      String var21;
      if(this.guestsCanModify) {
         var21 = "true";
      } else {
         var21 = "false";
      }

      StringBuffer var22 = var20.append(var21).append("\n");
      StringBuffer var23 = var1.append("GUESTS_CAN_INVITE_OTHERS: ");
      String var24;
      if(this.guestsCanInviteOthers) {
         var24 = "true";
      } else {
         var24 = "false";
      }

      StringBuffer var25 = var23.append(var24).append("\n");
      StringBuffer var26 = var1.append("GUESTS_CAN_SEE_GUESTS: ");
      String var27;
      if(this.guestsCanSeeGuests) {
         var27 = "true";
      } else {
         var27 = "false";
      }

      StringBuffer var28 = var26.append(var27).append("\n");
      String var29 = this.organizer;
      this.appendIfNotNull(var1, "ORGANIZER", var29);
      Iterator var30 = this.attendees.iterator();

      while(var30.hasNext()) {
         ((Who)var30.next()).toString(var1);
      }

      Iterator var31 = this.whens.iterator();

      while(var31.hasNext()) {
         ((When)var31.next()).toString(var1);
      }

      if(this.reminders != null) {
         var31 = this.reminders.iterator();

         while(var31.hasNext()) {
            ((Reminder)var31.next()).toString(var1);
         }
      }

      String var32 = this.where;
      this.appendIfNotNull(var1, "WHERE", var32);
      String var33 = this.commentsUri;
      this.appendIfNotNull(var1, "COMMENTS", var33);
      StringBuffer var40;
      if(this.extendedProperties != null) {
         for(var31 = this.extendedProperties.entrySet().iterator(); var31.hasNext(); var40 = var1.append('\n')) {
            java.util.Map.Entry var34 = (java.util.Map.Entry)var31.next();
            String var35 = (String)var34.getKey();
            var1.append(var35);
            StringBuffer var37 = var1.append(':');
            String var38 = (String)var34.getValue();
            var1.append(var38);
         }
      }

      String var41 = this.calendarUrl;
      this.appendIfNotNull(var1, "CALENDAR_URL", var41);
   }
}
