package com.google.wireless.gdata2.calendar.serializer.xml;

import android.text.TextUtils;
import com.google.wireless.gdata2.calendar.data.EventEntry;
import com.google.wireless.gdata2.calendar.data.Reminder;
import com.google.wireless.gdata2.calendar.data.When;
import com.google.wireless.gdata2.calendar.data.Who;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlSerializer;

public class XmlEventEntryGDataSerializer extends XmlEntryGDataSerializer {

   private static final EventEntry EMPTY_EVENT_ENTRY = new EventEntry();
   private static final String FIELD_EXTENDED_PROPERTY = "gd:extendedProperty";
   private static final String FIELD_REMINDER = "gd:reminder";
   private static final String FIELD_WHEN = "gd:when";
   public static final String NAMESPACE_GCAL = "gCal";
   public static final String NAMESPACE_GCAL_URI = "http://schemas.google.com/gCal/2005";
   private boolean serializeAttendees;
   private boolean serializeExtendedProperties;
   private boolean serializeReminders;
   private boolean serializeWhens;


   static {
      EMPTY_EVENT_ENTRY.setStatus((byte)-1);
      EMPTY_EVENT_ENTRY.setVisibility((byte)-1);
      EMPTY_EVENT_ENTRY.setTransparency((byte)-1);
   }

   public XmlEventEntryGDataSerializer(XmlParserFactory var1, EventEntry var2) {
      EventEntry var3 = EMPTY_EVENT_ENTRY;
      super(var1, var2, var3);
      this.serializeAttendees = (boolean)0;
      this.serializeReminders = (boolean)0;
      this.serializeWhens = (boolean)0;
      this.serializeExtendedProperties = (boolean)0;
      this.serializeAttendees = (boolean)1;
      this.serializeExtendedProperties = (boolean)1;
      if(var2.getRecurrence() != null) {
         this.serializeReminders = (boolean)1;
      } else {
         this.serializeWhens = (boolean)1;
      }
   }

   public XmlEventEntryGDataSerializer(XmlParserFactory var1, EventEntry var2, EventEntry var3) {
      if(var3 == null) {
         var3 = EMPTY_EVENT_ENTRY;
      }

      super(var1, var2, var3);
      this.serializeAttendees = (boolean)0;
      this.serializeReminders = (boolean)0;
      this.serializeWhens = (boolean)0;
      this.serializeExtendedProperties = (boolean)0;
      EventEntry var4 = (EventEntry)this.oldEntry;
      String var5 = var2.getContent();
      String var6 = var4.getContent();
      if(!equals(var5, var6)) {
         var4 = EMPTY_EVENT_ENTRY;
         this.oldEntry = var4;
      }

      ArrayList var8 = new ArrayList();
      Set var9 = var2.getAttendees();
      Set var10 = ((EventEntry)this.oldEntry).getAttendees();
      if(!var9.equals(var10)) {
         boolean var11 = var8.add("gd:who");
         this.serializeAttendees = (boolean)1;
      }

      Set var12 = var2.getReminders();
      Set var13 = var4.getReminders();
      String var14 = var2.getRecurrence();
      if(var14 != null) {
         String var15 = var4.getRecurrence();
         if(!var14.equals(var15) || !var12.equals(var13)) {
            boolean var16 = var8.add("gd:reminder");
            boolean var17 = var8.add("gd:when");
            this.serializeReminders = (boolean)1;
         }
      } else {
         List var22 = var2.getWhens();
         List var23 = var4.getWhens();
         if(!var22.equals(var23) || !var12.equals(var13)) {
            boolean var24 = var8.add("gd:reminder");
            boolean var25 = var8.add("gd:when");
            this.serializeWhens = (boolean)1;
         }
      }

      Map var18 = var2.getExtendedProperties();
      Map var19 = var4.getExtendedProperties();
      if(!var18.equals(var19)) {
         boolean var20 = var8.add("gd:extendedProperty");
         this.serializeExtendedProperties = (boolean)1;
      }

      if(this.isPartial()) {
         String var21 = TextUtils.join(",", var8);
         var2.setFields(var21);
      }
   }

   private void serializeAttendees(XmlSerializer var1) throws IOException, ParseException {
      if(this.serializeAttendees) {
         Iterator var2 = ((EventEntry)this.entry).getAttendees().iterator();

         while(var2.hasNext()) {
            Who var3 = (Who)var2.next();
            serializeWho(var1, var3);
         }

      }
   }

   private static void serializeExtendedProperty(XmlSerializer var0, String var1, String var2) throws IOException {
      XmlSerializer var3 = var0.startTag("http://schemas.google.com/g/2005", "extendedProperty");
      var0.attribute((String)null, "name", var1);
      var0.attribute((String)null, "value", var2);
      XmlSerializer var6 = var0.endTag("http://schemas.google.com/g/2005", "extendedProperty");
   }

   private void serializeOriginalEvent(XmlSerializer var1) throws IOException {
      String var2 = ((EventEntry)this.entry).getOriginalEventId();
      String var3 = ((EventEntry)this.entry).getOriginalEventStartTime();
      String var4 = ((EventEntry)this.oldEntry).getOriginalEventId();
      String var5 = ((EventEntry)this.oldEntry).getOriginalEventStartTime();
      if(!StringUtils.isEmpty(var2)) {
         if(!StringUtils.isEmpty(var3)) {
            if(!var2.equals(var4) || !var3.equals(var5)) {
               XmlSerializer var6 = var1.startTag("http://schemas.google.com/g/2005", "originalEvent");
               int var7 = var2.lastIndexOf(47);
               if(var7 != -1) {
                  int var8 = var7 + 1;
                  String var9 = var2.substring(var8);
                  if(!StringUtils.isEmpty(var9)) {
                     var1.attribute((String)null, "id", var9);
                  }
               }

               var1.attribute((String)null, "href", var2);
               XmlSerializer var12 = var1.startTag("http://schemas.google.com/g/2005", "when");
               var1.attribute((String)null, "startTime", var3);
               XmlSerializer var14 = var1.endTag("http://schemas.google.com/g/2005", "when");
               XmlSerializer var15 = var1.endTag("http://schemas.google.com/g/2005", "originalEvent");
            }
         }
      }
   }

   private void serializeQuickAdd(XmlSerializer var1) throws IOException {
      boolean var2 = ((EventEntry)this.entry).isQuickAdd();
      boolean var3 = ((EventEntry)this.oldEntry).isQuickAdd();
      if(var2 != var3) {
         XmlSerializer var4 = var1.startTag("gCal", "quickadd");
         XmlSerializer var5 = var1.attribute((String)null, "value", "true");
         XmlSerializer var6 = var1.endTag("gCal", "quickadd");
      }
   }

   private void serializeRecurrence(XmlSerializer var1) throws IOException {
      String var2 = ((EventEntry)this.entry).getRecurrence();
      String var3 = ((EventEntry)this.oldEntry).getRecurrence();
      if(!equals(var2, var3)) {
         XmlSerializer var4 = var1.startTag("http://schemas.google.com/g/2005", "recurrence");
         var1.text(var2);
         XmlSerializer var6 = var1.endTag("http://schemas.google.com/g/2005", "recurrence");
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

   private void serializeStatus(XmlSerializer var1) throws IOException {
      byte var2 = ((EventEntry)this.entry).getStatus();
      byte var3 = ((EventEntry)this.oldEntry).getStatus();
      if(var2 != var3) {
         String var4;
         switch(var2) {
         case 0:
            var4 = "http://schemas.google.com/g/2005#event.tentative";
            break;
         case 1:
            var4 = "http://schemas.google.com/g/2005#event.confirmed";
            break;
         case 2:
            var4 = "http://schemas.google.com/g/2005#event.canceled";
            break;
         default:
            var4 = "http://schemas.google.com/g/2005#event.tentative";
         }

         XmlSerializer var5 = var1.startTag("http://schemas.google.com/g/2005", "eventStatus");
         var1.attribute((String)null, "value", var4);
         XmlSerializer var7 = var1.endTag("http://schemas.google.com/g/2005", "eventStatus");
      }
   }

   private void serializeTransparency(XmlSerializer var1) throws IOException {
      byte var2 = ((EventEntry)this.entry).getTransparency();
      byte var3 = ((EventEntry)this.oldEntry).getTransparency();
      if(var2 != var3) {
         String var4;
         switch(var2) {
         case 0:
            var4 = "http://schemas.google.com/g/2005#event.opaque";
            break;
         case 1:
            var4 = "http://schemas.google.com/g/2005#event.transparent";
            break;
         default:
            var4 = "http://schemas.google.com/g/2005#event.transparent";
         }

         XmlSerializer var5 = var1.startTag("http://schemas.google.com/g/2005", "transparency");
         var1.attribute((String)null, "value", var4);
         XmlSerializer var7 = var1.endTag("http://schemas.google.com/g/2005", "transparency");
      }
   }

   private void serializeVisibility(XmlSerializer var1) throws IOException {
      byte var2 = ((EventEntry)this.entry).getVisibility();
      byte var3 = ((EventEntry)this.oldEntry).getVisibility();
      if(var2 != var3) {
         String var4;
         switch(var2) {
         case 0:
            var4 = "http://schemas.google.com/g/2005#event.default";
            break;
         case 1:
            var4 = "http://schemas.google.com/g/2005#event.confidential";
            break;
         case 2:
            var4 = "http://schemas.google.com/g/2005#event.private";
            break;
         case 3:
            var4 = "http://schemas.google.com/g/2005#event.public";
            break;
         default:
            var4 = "http://schemas.google.com/g/2005#event.default";
         }

         XmlSerializer var5 = var1.startTag("http://schemas.google.com/g/2005", "visibility");
         var1.attribute((String)null, "value", var4);
         XmlSerializer var7 = var1.endTag("http://schemas.google.com/g/2005", "visibility");
      }
   }

   private void serializeWhen(XmlSerializer var1, When var2) throws IOException {
      String var3 = var2.getStartTime();
      String var4 = var2.getEndTime();
      if(!StringUtils.isEmpty(var2.getStartTime())) {
         XmlSerializer var5 = var1.startTag("http://schemas.google.com/g/2005", "when");
         var1.attribute((String)null, "startTime", var3);
         if(!StringUtils.isEmpty(var4)) {
            var1.attribute((String)null, "endTime", var4);
         }

         Iterator var8 = ((EventEntry)this.entry).getReminders().iterator();

         while(var8.hasNext()) {
            Reminder var9 = (Reminder)var8.next();
            serializeReminder(var1, var9);
         }

         XmlSerializer var10 = var1.endTag("http://schemas.google.com/g/2005", "when");
      }
   }

   private void serializeWhere(XmlSerializer var1) throws IOException {
      String var2 = ((EventEntry)this.entry).getWhere();
      if(var2 == null) {
         var2 = "";
      }

      Entry var3 = this.oldEntry;
      EventEntry var4 = EMPTY_EVENT_ENTRY;
      if(var3 != var4) {
         String var5 = ((EventEntry)this.oldEntry).getWhere();
         if(equals(var2, var5)) {
            return;
         }
      }

      XmlSerializer var6 = var1.startTag("http://schemas.google.com/g/2005", "where");
      var1.attribute((String)null, "valueString", var2);
      XmlSerializer var8 = var1.endTag("http://schemas.google.com/g/2005", "where");
   }

   private static void serializeWho(XmlSerializer var0, Who var1) throws IOException, ParseException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "who");
      String var3 = var1.getEmail();
      if(!StringUtils.isEmpty(var3)) {
         var0.attribute((String)null, "email", var3);
      }

      String var5 = var1.getValue();
      if(!StringUtils.isEmpty(var5)) {
         var0.attribute((String)null, "valueString", var5);
      }

      String var7 = null;
      switch(var1.getRelationship()) {
      case 0:
         break;
      case 1:
         var7 = "http://schemas.google.com/g/2005#event.attendee";
         break;
      case 2:
         var7 = "http://schemas.google.com/g/2005#event.organizer";
         break;
      case 3:
         var7 = "http://schemas.google.com/g/2005#event.performer";
         break;
      case 4:
         var7 = "http://schemas.google.com/g/2005#event.speaker";
         break;
      default:
         StringBuilder var8 = (new StringBuilder()).append("Unexpected rel: ");
         byte var9 = var1.getRelationship();
         String var10 = var8.append(var9).toString();
         throw new ParseException(var10);
      }

      if(!StringUtils.isEmpty(var7)) {
         var0.attribute((String)null, "rel", var7);
      }

      String var12 = null;
      switch(var1.getStatus()) {
      case 0:
         break;
      case 1:
         var12 = "http://schemas.google.com/g/2005#event.accepted";
         break;
      case 2:
         var12 = "http://schemas.google.com/g/2005#event.declined";
         break;
      case 3:
         var12 = "http://schemas.google.com/g/2005#event.invited";
         break;
      case 4:
         var12 = "http://schemas.google.com/g/2005#event.tentative";
         break;
      default:
         StringBuilder var13 = (new StringBuilder()).append("Unexpected status: ");
         byte var14 = var1.getStatus();
         String var15 = var13.append(var14).toString();
         throw new ParseException(var15);
      }

      if(!StringUtils.isEmpty(var12)) {
         XmlSerializer var16 = var0.startTag("http://schemas.google.com/g/2005", "attendeeStatus");
         var0.attribute((String)null, "value", var12);
         XmlSerializer var18 = var0.endTag("http://schemas.google.com/g/2005", "attendeeStatus");
      }

      String var19 = null;
      switch(var1.getType()) {
      case 0:
         break;
      case 1:
         var19 = "http://schemas.google.com/g/2005#event.optional";
         break;
      case 2:
         var19 = "http://schemas.google.com/g/2005#event.required";
         break;
      default:
         StringBuilder var20 = (new StringBuilder()).append("Unexpected type: ");
         byte var21 = var1.getType();
         String var22 = var20.append(var21).toString();
         throw new ParseException(var22);
      }

      if(!StringUtils.isEmpty(var19)) {
         XmlSerializer var23 = var0.startTag("http://schemas.google.com/g/2005", "attendeeType");
         var0.attribute((String)null, "value", var19);
         XmlSerializer var25 = var0.endTag("http://schemas.google.com/g/2005", "attendeeType");
      }

      XmlSerializer var26 = var0.endTag("http://schemas.google.com/g/2005", "who");
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {
      var1.setPrefix("gCal", "http://schemas.google.com/gCal/2005");
   }

   public boolean isPartial() {
      Entry var1 = this.oldEntry;
      EventEntry var2 = EMPTY_EVENT_ENTRY;
      boolean var3;
      if(var1 != var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws IOException, ParseException {
      this.serializeStatus(var1);
      this.serializeTransparency(var1);
      this.serializeVisibility(var1);
      if(((EventEntry)this.entry).getSendEventNotifications()) {
         XmlSerializer var3 = var1.startTag("http://schemas.google.com/gCal/2005", "sendEventNotifications");
         XmlSerializer var4 = var1.attribute((String)null, "value", "true");
         XmlSerializer var5 = var1.endTag("http://schemas.google.com/gCal/2005", "sendEventNotifications");
      }

      this.serializeAttendees(var1);
      this.serializeRecurrence(var1);
      Iterator var6;
      if(this.serializeReminders) {
         var6 = ((EventEntry)this.entry).getReminders().iterator();

         while(var6.hasNext()) {
            Reminder var7 = (Reminder)var6.next();
            serializeReminder(var1, var7);
         }
      }

      if(this.serializeWhens) {
         var6 = ((EventEntry)this.entry).getWhens().iterator();

         while(var6.hasNext()) {
            When var8 = (When)var6.next();
            this.serializeWhen(var1, var8);
         }
      }

      this.serializeOriginalEvent(var1);
      this.serializeWhere(var1);
      if(this.serializeExtendedProperties) {
         var6 = ((EventEntry)this.entry).getExtendedProperties().entrySet().iterator();

         while(var6.hasNext()) {
            java.util.Map.Entry var9 = (java.util.Map.Entry)var6.next();
            String var10 = (String)var9.getKey();
            String var11 = (String)var9.getValue();
            serializeExtendedProperty(var1, var10, var11);
         }
      }

      this.serializeQuickAdd(var1);
   }
}
