package com.google.wireless.gdata.subscribedfeeds.parser.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import com.google.wireless.gdata.subscribedfeeds.data.FeedUrl;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsEntry;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsFeed;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlSubscribedFeedsGDataParser extends XmlGDataParser {

   public XmlSubscribedFeedsGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      super(var1, var2);
   }

   protected Entry createEntry() {
      return new SubscribedFeedsEntry();
   }

   protected Feed createFeed() {
      return new SubscribedFeedsFeed();
   }

   protected void handleExtraElementInEntry(Entry var1) throws XmlPullParserException, IOException {
      XmlPullParser var2 = this.getParser();
      if(!(var1 instanceof SubscribedFeedsEntry)) {
         throw new IllegalArgumentException("Expected SubscribedFeedsEntry!");
      } else {
         SubscribedFeedsEntry var3 = (SubscribedFeedsEntry)var1;
         String var4 = var2.getName();
         if("feedurl".equals(var4)) {
            FeedUrl var5 = new FeedUrl();
            String var6 = var2.getAttributeValue((String)null, "value");
            var5.setFeed(var6);
            String var7 = var2.getAttributeValue((String)null, "service");
            var5.setService(var7);
            String var8 = var2.getAttributeValue((String)null, "authtoken");
            var5.setAuthToken(var8);
            var3.setSubscribedFeed(var5);
         }

         if("routingInfo".equals(var4)) {
            String var9 = XmlUtils.extractChildText(var2);
            var3.setRoutingInfo(var9);
         }

         if("clientToken".equals(var4)) {
            String var10 = XmlUtils.extractChildText(var2);
            var3.setClientToken(var10);
         }
      }
   }
}
