package com.google.wireless.gdata.calendar.parser.xml;

import com.google.wireless.gdata.calendar.data.CalendarEntry;
import com.google.wireless.gdata.calendar.data.CalendarsFeed;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlCalendarsGDataParser extends XmlGDataParser {

   public XmlCalendarsGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      super(var1, var2);
   }

   protected Entry createEntry() {
      return new CalendarEntry();
   }

   protected Feed createFeed() {
      return new CalendarsFeed();
   }

   protected void handleExtraElementInEntry(Entry var1) throws XmlPullParserException, IOException {
      XmlPullParser var2 = this.getParser();
      if(!(var1 instanceof CalendarEntry)) {
         throw new IllegalArgumentException("Expected CalendarEntry!");
      } else {
         CalendarEntry var3 = (CalendarEntry)var1;
         String var4 = var2.getName();
         if("accesslevel".equals(var4)) {
            String var5 = var2.getAttributeValue((String)null, "value");
            byte var6 = 1;
            if("none".equals(var5)) {
               var6 = 0;
            } else if("read".equals(var5)) {
               var6 = 1;
            } else if("freebusy".equals(var5)) {
               var6 = 2;
            } else if("contributor".equals(var5)) {
               var6 = 3;
            } else if("editor".equals(var5)) {
               var6 = 3;
            } else if("owner".equals(var5)) {
               var6 = 4;
            } else if("root".equals(var5)) {
               var6 = 5;
            }

            var3.setAccessLevel(var6);
         } else if("color".equals(var4)) {
            String var7 = var2.getAttributeValue((String)null, "value");
            var3.setColor(var7);
         } else if("hidden".equals(var4)) {
            String var8 = var2.getAttributeValue((String)null, "value");
            byte var9 = 0;
            if("false".equals(var8)) {
               var9 = 0;
            } else if("true".equals(var8)) {
               var9 = 1;
            }

            var3.setHidden((boolean)var9);
            if(var9 != 0) {
               var3.setSelected((boolean)0);
            }
         } else if("selected".equals(var4)) {
            String var10 = var2.getAttributeValue((String)null, "value");
            byte var11 = 0;
            if("false".equals(var10)) {
               var11 = 0;
            } else if("true".equals(var10)) {
               var11 = 1;
            }

            var3.setSelected((boolean)var11);
         } else if("timezone".equals(var4)) {
            String var12 = var2.getAttributeValue((String)null, "value");
            var3.setTimezone(var12);
         } else if("overridename".equals(var4)) {
            String var13 = var2.getAttributeValue((String)null, "value");
            var3.setOverrideName(var13);
         }
      }
   }

   protected void handleExtraLinkInEntry(String var1, String var2, String var3, Entry var4) throws XmlPullParserException, IOException {
      if("alternate".equals(var1)) {
         if("application/atom+xml".equals(var2)) {
            ((CalendarEntry)var4).setAlternateLink(var3);
         }
      }
   }
}
