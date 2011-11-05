package com.google.wireless.gdata.calendar.parser.xml;

import com.google.wireless.gdata.calendar.data.CalendarEntry;
import com.google.wireless.gdata.calendar.data.EventEntry;
import com.google.wireless.gdata.calendar.parser.xml.XmlCalendarsGDataParser;
import com.google.wireless.gdata.calendar.parser.xml.XmlEventsGDataParser;
import com.google.wireless.gdata.calendar.serializer.xml.XmlEventEntryGDataSerializer;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.GDataSerializer;
import java.io.InputStream;
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
      if(!(var1 instanceof EventEntry)) {
         throw new IllegalArgumentException("Expected EventEntry!");
      } else {
         EventEntry var2 = (EventEntry)var1;
         XmlParserFactory var3 = this.xmlFactory;
         return new XmlEventEntryGDataSerializer(var3, var2);
      }
   }
}
