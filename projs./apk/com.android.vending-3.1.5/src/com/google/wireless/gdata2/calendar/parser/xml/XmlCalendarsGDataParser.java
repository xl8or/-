package com.google.wireless.gdata2.calendar.parser.xml;

import com.google.wireless.gdata2.calendar.data.CalendarEntry;
import com.google.wireless.gdata2.calendar.data.CalendarsFeed;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.data.XmlUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlCalendarsGDataParser extends XmlGDataParser {

   private static String EVENT_FEED_SCHEMA = "http://schemas.google.com/gCal/2005#eventFeed";
   private static String GCAL_NAMESPACE = "http://schemas.google.com/gCal/2005";
   private static final String TYPE_APPLICATION_ATOM_XML = "application/atom+xml";


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
         String var5 = GCAL_NAMESPACE;
         if(XmlUtils.matchNameSpaceUri(var2, var5)) {
            if("accesslevel".equals(var4)) {
               String var6 = var2.getAttributeValue((String)null, "value");
               byte var7 = 1;
               if("none".equals(var6)) {
                  var7 = 0;
               } else if("read".equals(var6)) {
                  var7 = 1;
               } else if("freebusy".equals(var6)) {
                  var7 = 2;
               } else if("contributor".equals(var6)) {
                  var7 = 3;
               } else if("editor".equals(var6)) {
                  var7 = 3;
               } else if("owner".equals(var6)) {
                  var7 = 4;
               } else if("root".equals(var6)) {
                  var7 = 5;
               }

               var3.setAccessLevel(var7);
            } else if("color".equals(var4)) {
               String var8 = var2.getAttributeValue((String)null, "value");
               var3.setColor(var8);
            } else if("hidden".equals(var4)) {
               String var9 = var2.getAttributeValue((String)null, "value");
               byte var10 = 0;
               if("false".equals(var9)) {
                  var10 = 0;
               } else if("true".equals(var9)) {
                  var10 = 1;
               }

               var3.setHidden((boolean)var10);
               if(var10 != 0) {
                  var3.setSelected((boolean)0);
               }
            } else if("selected".equals(var4)) {
               String var11 = var2.getAttributeValue((String)null, "value");
               byte var12 = 0;
               if("false".equals(var11)) {
                  var12 = 0;
               } else if("true".equals(var11)) {
                  var12 = 1;
               }

               var3.setSelected((boolean)var12);
            } else if("timezone".equals(var4)) {
               String var13 = var2.getAttributeValue((String)null, "value");
               var3.setTimezone(var13);
            } else if("overridename".equals(var4)) {
               String var14 = var2.getAttributeValue((String)null, "value");
               var3.setOverrideName(var14);
            }
         }
      }
   }

   protected void handleExtraLinkInEntry(String var1, String var2, String var3, Entry var4) throws XmlPullParserException, IOException {
      if("application/atom+xml".equals(var2)) {
         CalendarEntry var5 = (CalendarEntry)var4;
         if("self".equals(var1)) {
            var5.setSelfUri(var3);
         } else if("edit".equals(var1)) {
            var5.setEditUri(var3);
         } else if(EVENT_FEED_SCHEMA.equals(var1)) {
            var5.setEventsUri(var3);
         }
      }
   }
}
