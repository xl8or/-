package com.google.wireless.gdata.subscribedfeeds.parser.xml;

import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.GDataSerializer;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsEntry;
import com.google.wireless.gdata.subscribedfeeds.parser.xml.XmlSubscribedFeedsGDataParser;
import com.google.wireless.gdata.subscribedfeeds.serializer.xml.XmlSubscribedFeedsEntryGDataSerializer;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlSubscribedFeedsGDataParserFactory implements GDataParserFactory {

   private final XmlParserFactory xmlFactory;


   public XmlSubscribedFeedsGDataParserFactory(XmlParserFactory var1) {
      this.xmlFactory = var1;
   }

   public GDataParser createParser(InputStream var1) throws ParseException {
      XmlPullParser var2;
      try {
         var2 = this.xmlFactory.createParser();
      } catch (XmlPullParserException var5) {
         throw new ParseException("Could not create XmlPullParser", var5);
      }

      return new XmlSubscribedFeedsGDataParser(var1, var2);
   }

   public GDataParser createParser(Class var1, InputStream var2) throws ParseException {
      if(var1 != SubscribedFeedsEntry.class) {
         throw new IllegalArgumentException("SubscribedFeeds supports only a single feed type");
      } else {
         return this.createParser(var2);
      }
   }

   public GDataSerializer createSerializer(Entry var1) {
      if(!(var1 instanceof SubscribedFeedsEntry)) {
         throw new IllegalArgumentException("Expected SubscribedFeedsEntry!");
      } else {
         SubscribedFeedsEntry var2 = (SubscribedFeedsEntry)var1;
         XmlParserFactory var3 = this.xmlFactory;
         return new XmlSubscribedFeedsEntryGDataSerializer(var3, var2);
      }
   }
}
