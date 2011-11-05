package com.google.wireless.gdata.subscribedfeeds.serializer.xml;

import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import com.google.wireless.gdata.subscribedfeeds.data.FeedUrl;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsEntry;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

public class XmlSubscribedFeedsEntryGDataSerializer extends XmlEntryGDataSerializer {

   public static final String NAMESPACE_GSYNC = "gsync";
   public static final String NAMESPACE_GSYNC_URI = "http://schemas.google.com/gsync/data";


   public XmlSubscribedFeedsEntryGDataSerializer(XmlParserFactory var1, SubscribedFeedsEntry var2) {
      super(var1, var2);
   }

   private static void serializeClientToken(XmlSerializer var0, String var1) throws IOException {
      if(StringUtils.isEmpty(var1)) {
         var1 = "";
      }

      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gsync/data", "clientToken");
      var0.text(var1);
      XmlSerializer var4 = var0.endTag("http://schemas.google.com/gsync/data", "clientToken");
   }

   private static void serializeFeedUrl(XmlSerializer var0, FeedUrl var1) throws IOException {
      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gsync/data", "feedurl");
      String var3 = var1.getFeed();
      var0.attribute((String)null, "value", var3);
      String var5 = var1.getService();
      var0.attribute((String)null, "service", var5);
      String var7 = var1.getAuthToken();
      var0.attribute((String)null, "authtoken", var7);
      XmlSerializer var9 = var0.endTag("http://schemas.google.com/gsync/data", "feedurl");
   }

   private static void serializeRoutingInfo(XmlSerializer var0, String var1) throws IOException {
      if(StringUtils.isEmpty(var1)) {
         var1 = "";
      }

      XmlSerializer var2 = var0.startTag("http://schemas.google.com/gsync/data", "routingInfo");
      var0.text(var1);
      XmlSerializer var4 = var0.endTag("http://schemas.google.com/gsync/data", "routingInfo");
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {
      var1.setPrefix("gsync", "http://schemas.google.com/gsync/data");
   }

   protected SubscribedFeedsEntry getSubscribedFeedsEntry() {
      return (SubscribedFeedsEntry)this.getEntry();
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws IOException {
      SubscribedFeedsEntry var3 = this.getSubscribedFeedsEntry();
      FeedUrl var4 = var3.getSubscribedFeed();
      serializeFeedUrl(var1, var4);
      String var5 = var3.getClientToken();
      serializeClientToken(var1, var5);
      String var6 = var3.getRoutingInfo();
      serializeRoutingInfo(var1, var6);
   }
}
