package com.google.wireless.gdata.calendar.serializer.xml;

import com.google.wireless.gdata.calendar.data.EventEntry;
import com.google.wireless.gdata.calendar.data.Reminder;
import com.google.wireless.gdata.calendar.data.When;
import com.google.wireless.gdata.calendar.data.Who;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlSerializer;

public class XmlEventEntryGDataSerializer extends XmlEntryGDataSerializer {

   public static final String NAMESPACE_GCAL = "gCal";
   public static final String NAMESPACE_GCAL_URI = "http://schemas.google.com/gCal/2005";


   public XmlEventEntryGDataSerializer(XmlParserFactory var1, EventEntry var2) {
      super(var1, var2);
   }

   private static void serializeCommentsUri(XmlSerializer var0, String var1) throws IOException {
      if(var1 != null) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "feedLink");
         var0.attribute((String)null, "href", var1);
         XmlSerializer var4 = var0.endTag("http://schemas.google.com/g/2005", "feedLink");
      }
   }

   private static void serializeEventStatus(XmlSerializer var0, byte var1) throws IOException {
      String var2;
      switch(var1) {
      case 0:
         var2 = "http://schemas.google.com/g/2005#event.tentative";
         break;
      case 1:
         var2 = "http://schemas.google.com/g/2005#event.confirmed";
         break;
      case 2:
         var2 = "http://schemas.google.com/g/2005#event.canceled";
         break;
      default:
         var2 = "http://schemas.google.com/g/2005#event.tentative";
      }

      XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "eventStatus");
      var0.attribute((String)null, "value", var2);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/g/2005", "eventStatus");
   }

   private static void serializeExtendedProperty(XmlSerializer var0, String var1, String var2) throws IOException {
      XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "extendedProperty");
      var0.attribute((String)null, "name", var1);
      var0.attribute((String)null, "value", var2);
      XmlSerializer var6 = var0.endTag("http://schemas.google.com/g/2005", "extendedProperty");
   }

   private static void serializeGuestsCanInviteOthers(XmlSerializer var0, boolean var1) throws IOException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gCal/2005", "guestsCanInviteOthers");
      String var3;
      if(var1) {
         var3 = "true";
      } else {
         var3 = "false";
      }

      var0.attribute((String)null, "value", var3);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/gCal/2005", "guestsCanInviteOthers");
   }

   private static void serializeGuestsCanModify(XmlSerializer var0, boolean var1) throws IOException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gCal/2005", "guestsCanModify");
      String var3;
      if(var1) {
         var3 = "true";
      } else {
         var3 = "false";
      }

      var0.attribute((String)null, "value", var3);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/gCal/2005", "guestsCanModify");
   }

   private static void serializeGuestsCanSeeGuests(XmlSerializer var0, boolean var1) throws IOException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gCal/2005", "guestsCanSeeGuests");
      String var3;
      if(var1) {
         var3 = "true";
      } else {
         var3 = "false";
      }

      var0.attribute((String)null, "value", var3);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/gCal/2005", "guestsCanSeeGuests");
   }

   private static void serializeOriginalEvent(XmlSerializer var0, String var1, String var2) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         if(!StringUtils.isEmpty(var2)) {
            XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "originalEvent");
            int var4 = var1.lastIndexOf("/");
            if(var4 != -1) {
               int var5 = var4 + 1;
               String var6 = var1.substring(var5);
               if(!StringUtils.isEmpty(var6)) {
                  var0.attribute((String)null, "id", var6);
               }
            }

            var0.attribute((String)null, "href", var1);
            XmlSerializer var9 = var0.startTag("http://schemas.google.com/g/2005", "when");
            var0.attribute((String)null, "startTime", var2);
            XmlSerializer var11 = var0.endTag("http://schemas.google.com/g/2005", "when");
            XmlSerializer var12 = var0.endTag("http://schemas.google.com/g/2005", "originalEvent");
         }
      }
   }

   private static void serializeQuickAdd(XmlSerializer var0, boolean var1) throws IOException {
      if(var1) {
         XmlSerializer var2 = var0.startTag("gCal", "quickadd");
         XmlSerializer var3 = var0.attribute((String)null, "value", "true");
         XmlSerializer var4 = var0.endTag("gCal", "quickadd");
      }
   }

   private static void serializeRecurrence(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "recurrence");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag("http://schemas.google.com/g/2005", "recurrence");
      }
   }

   private static void serializeReminder(XmlSerializer var0, Reminder var1) throws IOException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "reminder");
      byte var3 = var1.getMethod();
      String var4 = null;
      switch(var3) {
      case 1:
         var4 = "email";
         break;
      case 2:
         var4 = "sms";
         break;
      case 3:
         var4 = "alert";
      }

      if(var4 != null) {
         var0.attribute((String)null, "method", var4);
      }

      int var6 = var1.getMinutes();
      if(var6 != -1) {
         String var7 = Integer.toString(var6);
         var0.attribute((String)null, "minutes", var7);
      }

      XmlSerializer var9 = var0.endTag("http://schemas.google.com/g/2005", "reminder");
   }

   private static void serializeSendEventNotifications(XmlSerializer var0, boolean var1) throws IOException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gCal/2005", "sendEventNotifications");
      String var3;
      if(var1) {
         var3 = "true";
      } else {
         var3 = "false";
      }

      var0.attribute((String)null, "value", var3);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/gCal/2005", "sendEventNotifications");
   }

   private static void serializeTransparency(XmlSerializer var0, byte var1) throws IOException {
      String var2;
      switch(var1) {
      case 0:
         var2 = "http://schemas.google.com/g/2005#event.opaque";
         break;
      case 1:
         var2 = "http://schemas.google.com/g/2005#event.transparent";
         break;
      default:
         var2 = "http://schemas.google.com/g/2005#event.transparent";
      }

      XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "transparency");
      var0.attribute((String)null, "value", var2);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/g/2005", "transparency");
   }

   private static void serializeVisibility(XmlSerializer var0, byte var1) throws IOException {
      String var2;
      switch(var1) {
      case 0:
         var2 = "http://schemas.google.com/g/2005#event.default";
         break;
      case 1:
         var2 = "http://schemas.google.com/g/2005#event.confidential";
         break;
      case 2:
         var2 = "http://schemas.google.com/g/2005#event.private";
         break;
      case 3:
         var2 = "http://schemas.google.com/g/2005#event.public";
         break;
      default:
         var2 = "http://schemas.google.com/g/2005#event.default";
      }

      XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "visibility");
      var0.attribute((String)null, "value", var2);
      XmlSerializer var5 = var0.endTag("http://schemas.google.com/g/2005", "visibility");
   }

   private static void serializeWhen(XmlSerializer var0, EventEntry var1, When var2) throws IOException {
      String var3 = var2.getStartTime();
      String var4 = var2.getEndTime();
      if(!StringUtils.isEmpty(var2.getStartTime())) {
         XmlSerializer var5 = var0.startTag("http://schemas.google.com/g/2005", "when");
         var0.attribute((String)null, "startTime", var3);
         if(!StringUtils.isEmpty(var4)) {
            var0.attribute((String)null, "endTime", var4);
         }

         if(var1.getReminders() != null) {
            Enumeration var8 = var1.getReminders().elements();

            while(var8.hasMoreElements()) {
               Reminder var9 = (Reminder)var8.nextElement();
               serializeReminder(var0, var9);
            }
         }

         XmlSerializer var10 = var0.endTag("http://schemas.google.com/g/2005", "when");
      }
   }

   private static void serializeWhere(XmlSerializer var0, String var1) throws IOException {
      if(var1 == null) {
         var1 = "";
      }

      XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "where");
      var0.attribute((String)null, "valueString", var1);
      XmlSerializer var4 = var0.endTag("http://schemas.google.com/g/2005", "where");
   }

   private static void serializeWho(XmlSerializer var0, EventEntry var1, Who var2) throws IOException, ParseException {
      XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "who");
      String var4 = var2.getEmail();
      if(!StringUtils.isEmpty(var4)) {
         var0.attribute((String)null, "email", var4);
      }

      String var6 = var2.getValue();
      if(!StringUtils.isEmpty(var6)) {
         var0.attribute((String)null, "valueString", var6);
      }

      String var8 = null;
      switch(var2.getRelationship()) {
      case 0:
         break;
      case 1:
         var8 = "http://schemas.google.com/g/2005#event.attendee";
         break;
      case 2:
         var8 = "http://schemas.google.com/g/2005#event.organizer";
         break;
      case 3:
         var8 = "http://schemas.google.com/g/2005#event.performer";
         break;
      case 4:
         var8 = "http://schemas.google.com/g/2005#event.speaker";
         break;
      default:
         StringBuilder var9 = (new StringBuilder()).append("Unexpected rel: ");
         byte var10 = var2.getRelationship();
         String var11 = var9.append(var10).toString();
         throw new ParseException(var11);
      }

      if(!StringUtils.isEmpty(var8)) {
         var0.attribute((String)null, "rel", var8);
      }

      String var13 = null;
      switch(var2.getStatus()) {
      case 0:
         break;
      case 1:
         var13 = "http://schemas.google.com/g/2005#event.accepted";
         break;
      case 2:
         var13 = "http://schemas.google.com/g/2005#event.declined";
         break;
      case 3:
         var13 = "http://schemas.google.com/g/2005#event.invited";
         break;
      case 4:
         var13 = "http://schemas.google.com/g/2005#event.tentative";
         break;
      default:
         StringBuilder var14 = (new StringBuilder()).append("Unexpected status: ");
         byte var15 = var2.getStatus();
         String var16 = var14.append(var15).toString();
         throw new ParseException(var16);
      }

      if(!StringUtils.isEmpty(var13)) {
         XmlSerializer var17 = var0.startTag("http://schemas.google.com/g/2005", "attendeeStatus");
         var0.attribute((String)null, "value", var13);
         XmlSerializer var19 = var0.endTag("http://schemas.google.com/g/2005", "attendeeStatus");
      }

      String var20 = null;
      switch(var2.getType()) {
      case 0:
         break;
      case 1:
         var20 = "http://schemas.google.com/g/2005#event.optional";
         break;
      case 2:
         var20 = "http://schemas.google.com/g/2005#event.required";
         break;
      default:
         StringBuilder var21 = (new StringBuilder()).append("Unexpected type: ");
         byte var22 = var2.getType();
         String var23 = var21.append(var22).toString();
         throw new ParseException(var23);
      }

      if(!StringUtils.isEmpty(var20)) {
         XmlSerializer var24 = var0.startTag("http://schemas.google.com/g/2005", "attendeeType");
         var0.attribute((String)null, "value", var20);
         XmlSerializer var26 = var0.endTag("http://schemas.google.com/g/2005", "attendeeType");
      }

      XmlSerializer var27 = var0.endTag("http://schemas.google.com/g/2005", "who");
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {
      var1.setPrefix("gCal", "http://schemas.google.com/gCal/2005");
   }

   protected EventEntry getEventEntry() {
      return (EventEntry)this.getEntry();
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws IOException, ParseException {
      EventEntry var3 = this.getEventEntry();
      byte var4 = var3.getStatus();
      serializeEventStatus(var1, var4);
      byte var5 = var3.getTransparency();
      serializeTransparency(var1, var5);
      byte var6 = var3.getVisibility();
      serializeVisibility(var1, var6);
      if(var3.getSendEventNotifications()) {
         XmlSerializer var7 = var1.startTag("http://schemas.google.com/gCal/2005", "sendEventNotifications");
         XmlSerializer var8 = var1.attribute((String)null, "value", "true");
         XmlSerializer var9 = var1.endTag("http://schemas.google.com/gCal/2005", "sendEventNotifications");
      }

      Enumeration var10 = var3.getAttendees().elements();

      while(var10.hasMoreElements()) {
         Who var11 = (Who)var10.nextElement();
         serializeWho(var1, var3, var11);
      }

      String var12 = var3.getRecurrence();
      serializeRecurrence(var1, var12);
      if(var3.getRecurrence() != null) {
         if(var3.getReminders() != null) {
            Enumeration var13 = var3.getReminders().elements();

            while(var13.hasMoreElements()) {
               Reminder var14 = (Reminder)var13.nextElement();
               serializeReminder(var1, var14);
            }
         }
      } else {
         Enumeration var15 = var3.getWhens().elements();

         while(var15.hasMoreElements()) {
            When var16 = (When)var15.nextElement();
            serializeWhen(var1, var3, var16);
         }
      }

      String var17 = var3.getOriginalEventId();
      String var18 = var3.getOriginalEventStartTime();
      serializeOriginalEvent(var1, var17, var18);
      String var19 = var3.getWhere();
      serializeWhere(var1, var19);
      String var20 = var3.getCommentsUri();
      serializeCommentsUri(var1, var20);
      Hashtable var21 = var3.getExtendedProperties();
      if(var21 != null) {
         Enumeration var22 = var21.keys();

         while(var22.hasMoreElements()) {
            String var23 = (String)var22.nextElement();
            String var24 = (String)var21.get(var23);
            serializeExtendedProperty(var1, var23, var24);
         }
      }

      boolean var25 = var3.isQuickAdd();
      serializeQuickAdd(var1, var25);
   }
}
