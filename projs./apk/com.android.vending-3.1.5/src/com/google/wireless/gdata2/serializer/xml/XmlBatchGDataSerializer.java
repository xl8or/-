package com.google.wireless.gdata2.serializer.xml;

import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlNametable;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import com.google.wireless.gdata2.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlBatchGDataSerializer implements GDataSerializer {

   private final Enumeration batch;
   private final GDataParserFactory gdataFactory;
   private final XmlParserFactory xmlFactory;


   public XmlBatchGDataSerializer(GDataParserFactory var1, XmlParserFactory var2, Enumeration var3) {
      this.gdataFactory = var1;
      this.xmlFactory = var2;
      this.batch = var3;
   }

   private static void declareNamespaces(XmlSerializer var0) throws IOException {
      var0.setPrefix("", "http://www.w3.org/2005/Atom");
      var0.setPrefix("gd", "http://schemas.google.com/g/2005");
      var0.setPrefix("batch", "http://schemas.google.com/gdata/batch");
   }

   public String getContentType() {
      return "application/atom+xml";
   }

   public boolean isPartial() {
      return false;
   }

   public void serialize(OutputStream var1, int var2) throws IOException, ParseException {
      XmlSerializer var3;
      try {
         var3 = this.xmlFactory.createSerializer();
      } catch (XmlPullParserException var18) {
         throw new ParseException("Unable to create XmlSerializer.", var18);
      }

      XmlSerializer var4 = var3;
      String var5 = XmlNametable.UTF8;
      var3.setOutput(var1, var5);
      String var6 = XmlNametable.UTF8;
      Boolean var7 = Boolean.FALSE;
      var3.startDocument(var6, var7);
      declareNamespaces(var3);

      boolean var8;
      XmlEntryGDataSerializer var10;
      for(var8 = true; this.batch.hasMoreElements(); var10.serialize(var1, 3)) {
         Entry var9 = (Entry)this.batch.nextElement();
         var10 = (XmlEntryGDataSerializer)this.gdataFactory.createSerializer(var9);
         if(var8) {
            var8 = false;
            String var11 = XmlNametable.FEED;
            var4.startTag("http://www.w3.org/2005/Atom", var11);
            var10.declareExtraEntryNamespaces(var4);
         }
      }

      if(var8) {
         String var14 = XmlNametable.FEED;
         var4.startTag("http://www.w3.org/2005/Atom", var14);
      }

      String var16 = XmlNametable.FEED;
      var4.endTag("http://www.w3.org/2005/Atom", var16);
      var4.endDocument();
      var4.flush();
   }
}
