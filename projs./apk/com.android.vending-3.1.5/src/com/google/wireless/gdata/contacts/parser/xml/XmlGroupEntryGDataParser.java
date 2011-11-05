package com.google.wireless.gdata.contacts.parser.xml;

import com.google.wireless.gdata.contacts.data.GroupEntry;
import com.google.wireless.gdata.contacts.data.GroupsFeed;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;

public class XmlGroupEntryGDataParser extends XmlGDataParser {

   public XmlGroupEntryGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      super(var1, var2);
   }

   protected Entry createEntry() {
      return new GroupEntry();
   }

   protected Feed createFeed() {
      return new GroupsFeed();
   }

   protected void handleExtraElementInEntry(Entry var1) {
      XmlPullParser var2 = this.getParser();
      if(!(var1 instanceof GroupEntry)) {
         throw new IllegalArgumentException("Expected GroupEntry!");
      } else {
         GroupEntry var3 = (GroupEntry)var1;
         String var4 = var2.getName();
         if("systemGroup".equals(var4)) {
            String var5 = var2.getAttributeValue((String)null, "id");
            if(StringUtils.isEmpty(var5)) {
               var5 = null;
            }

            var3.setSystemGroup(var5);
         }
      }
   }
}
