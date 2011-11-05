package com.google.wireless.gdata2.calendar.parser.xml;

import com.google.wireless.gdata2.calendar.data.CalendarEntry;
import com.google.wireless.gdata2.calendar.data.EventEntry;
import com.google.wireless.gdata2.calendar.parser.xml.XmlCalendarsGDataParser;
import com.google.wireless.gdata2.calendar.parser.xml.XmlEventsGDataParser;
import com.google.wireless.gdata2.calendar.serializer.xml.XmlCalendarEntryGDataSerializer;
import com.google.wireless.gdata2.calendar.serializer.xml.XmlEventEntryGDataSerializer;
import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import com.google.wireless.gdata2.serializer.xml.XmlBatchGDataSerializer;
import java.io.InputStream;
import java.util.Enumeration;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlCalendarGDataParserFactory implements GDataParserFactory {

   private final XmlParserFactory xmlFactory;


   public XmlCalendarGDataParserFactory(XmlParserFactory var1) {
      this.xmlFactory = var1;
   }

   public GDataParser createCalendarsFeedParser(InputStream var1) throws ParseException {
      XmlPullParser var2;
      try {
         var2 = this.xmlFactory.createParser();
      } catch (XmlPullParserException var5) {
         throw new ParseException("Could not create XmlPullParser", var5);
      }

      return new XmlCalendarsGDataParser(var1, var2);
   }

   public GDataParser createParser(InputStream var1) throws ParseException {
      XmlPullParser var2;
      try {
         var2 = this.xmlFactory.createParser();
      } catch (XmlPullParserException var5) {
         throw new ParseException("Could not create XmlPullParser", var5);
      }

      return new XmlEventsGDataParser(var1, var2);
   }

   public GDataParser createParser(Class var1, InputStream var2) throws ParseException {
      GDataParser var3;
      if(var1 == CalendarEntry.class) {
         var3 = this.createCalendarsFeedParser(var2);
      } else {
         if(var1 != EventEntry.class) {
            StringBuilder var4 = (new StringBuilder()).append("Unknown entry class \'");
            String var5 = var1.getName();
            String var6 = var4.append(var5).append("\' specified.").toString();
            throw new IllegalArgumentException(var6);
         }

         var3 = this.createParser(var2);
      }

      return var3;
   }

   public GDataSerializer createSerializer(Entry var1) {
      Object var4;
      if(var1 instanceof EventEntry) {
         EventEntry var2 = (EventEntry)var1;
         XmlParserFactory var3 = this.xmlFactory;
         var4 = new XmlEventEntryGDataSerializer(var3, var2);
      } else {
         if(!(var1 instanceof CalendarEntry)) {
            StringBuilder var7 = (new StringBuilder()).append("Unexpected Entry class: ");
            Class var8 = var1.getClass();
            String var9 = var7.append(var8).toString();
            throw new IllegalArgumentException(var9);
         }

         CalendarEntry var5 = (CalendarEntry)var1;
         XmlParserFactory var6 = this.xmlFactory;
         var4 = new XmlCalendarEntryGDataSerializer(var6, var5);
      }

      return (GDataSerializer)var4;
   }

   public GDataSerializer createSerializer(Entry var1, Entry var2) {
      Object var6;
      if(var1 instanceof EventEntry) {
         EventEntry var3 = (EventEntry)var1;
         EventEntry var4 = (EventEntry)var2;
         XmlParserFactory var5 = this.xmlFactory;
         var6 = new XmlEventEntryGDataSerializer(var5, var3, var4);
      } else {
         if(!(var1 instanceof CalendarEntry)) {
            StringBuilder var9 = (new StringBuilder()).append("Unexpected Entry class: ");
            Class var10 = var1.getClass();
            String var11 = var9.append(var10).toString();
            throw new IllegalArgumentException(var11);
         }

         CalendarEntry var7 = (CalendarEntry)var1;
         XmlParserFactory var8 = this.xmlFactory;
         var6 = new XmlCalendarEntryGDataSerializer(var8, var7);
      }

      return (GDataSerializer)var6;
   }

   public GDataSerializer createSerializer(Enumeration var1) {
      XmlParserFactory var2 = this.xmlFactory;
      return new XmlBatchGDataSerializer(this, var2, var1);
   }
}
