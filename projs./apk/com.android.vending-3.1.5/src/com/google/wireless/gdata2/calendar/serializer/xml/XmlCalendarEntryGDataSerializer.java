package com.google.wireless.gdata2.calendar.serializer.xml;

import com.google.wireless.gdata2.calendar.data.CalendarEntry;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

public class XmlCalendarEntryGDataSerializer extends XmlEntryGDataSerializer {

   public static final String NAMESPACE_GCAL = "gCal";
   public static final String NAMESPACE_GCAL_URI = "http://schemas.google.com/gCal/2005";


   public XmlCalendarEntryGDataSerializer(XmlParserFactory var1, Entry var2) {
      super(var1, var2);
   }

   private static void serializeAccessLevel(XmlSerializer var0, byte var1) throws IOException {
      String var2;
      switch(var1) {
      case 0:
         var2 = "none";
         break;
      case 1:
         var2 = "read";
         break;
      case 2:
         var2 = "freebusy";
         break;
      case 3:
         var2 = "editor";
         break;
      case 4:
         var2 = "owner";
         break;
      case 5:
         var2 = "root";
         break;
      default:
         var2 = "none";
      }

      serializeStringValue(var0, "http://schemas.google.com/gCal/2005", "accesslevel", var2);
   }

   private static void serializeColor(XmlSerializer var0, String var1) throws IOException {
      serializeStringValue(var0, "http://schemas.google.com/gCal/2005", "color", var1);
   }

   private static void serializeHidden(XmlSerializer var0, boolean var1) throws IOException {
      String var2 = Boolean.toString(var1);
      serializeStringValue(var0, "http://schemas.google.com/gCal/2005", "hidden", var2);
   }

   private void serializeOverrideName(XmlSerializer var1, String var2) throws IOException {
      serializeStringValue(var1, "http://schemas.google.com/gCal/2005", "overridename", var2);
   }

   private static void serializeSelected(XmlSerializer var0, boolean var1) throws IOException {
      String var2 = Boolean.toString(var1);
      serializeStringValue(var0, "http://schemas.google.com/gCal/2005", "selected", var2);
   }

   private static void serializeStringValue(XmlSerializer var0, String var1, String var2, String var3) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         if(!StringUtils.isEmpty(var2)) {
            if(!StringUtils.isEmpty(var3)) {
               var0.startTag(var1, var2);
               var0.attribute((String)null, "value", var3);
               var0.endTag(var1, var2);
            }
         }
      }
   }

   private static void serializeTimezone(XmlSerializer var0, String var1) throws IOException {
      serializeStringValue(var0, "http://schemas.google.com/gCal/2005", "timezone", var1);
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {
      var1.setPrefix("gCal", "http://schemas.google.com/gCal/2005");
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws IOException, ParseException {
      CalendarEntry var3 = (CalendarEntry)this.getEntry();
      String var4 = var3.getTimezone();
      serializeTimezone(var1, var4);
      boolean var5 = var3.isHidden();
      serializeHidden(var1, var5);
      String var6 = var3.getColor();
      serializeColor(var1, var6);
      boolean var7 = var3.isSelected();
      serializeSelected(var1, var7);
      byte var8 = var3.getAccessLevel();
      serializeAccessLevel(var1, var8);
      String var9 = var3.getOverrideName();
      this.serializeOverrideName(var1, var9);
   }
}
