package com.google.wireless.gdata.serializer.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.GDataSerializer;
import java.io.IOException;
import java.io.OutputStream;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlEntryGDataSerializer implements GDataSerializer {

   private final Entry entry;
   private final XmlParserFactory factory;


   public XmlEntryGDataSerializer(XmlParserFactory var1, Entry var2) {
      this.factory = var1;
      this.entry = var2;
   }

   private final void declareEntryNamespaces(XmlSerializer var1) throws IOException {
      var1.setPrefix("", "http://www.w3.org/2005/Atom");
      var1.setPrefix("gd", "http://schemas.google.com/g/2005");
      this.declareExtraEntryNamespaces(var1);
   }

   private static void serializeAuthor(XmlSerializer var0, String var1, String var2) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         if(!StringUtils.isEmpty(var2)) {
            XmlSerializer var3 = var0.startTag((String)null, "author");
            XmlSerializer var4 = var0.startTag((String)null, "name");
            var0.text(var1);
            XmlSerializer var6 = var0.endTag((String)null, "name");
            XmlSerializer var7 = var0.startTag((String)null, "email");
            var0.text(var2);
            XmlSerializer var9 = var0.endTag((String)null, "email");
            XmlSerializer var10 = var0.endTag((String)null, "author");
         }
      }
   }

   private static void serializeCategory(XmlSerializer var0, String var1, String var2) throws IOException {
      if(!StringUtils.isEmpty(var1) || !StringUtils.isEmpty(var2)) {
         XmlSerializer var3 = var0.startTag((String)null, "category");
         if(!StringUtils.isEmpty(var1)) {
            var0.attribute((String)null, "term", var1);
         }

         if(!StringUtils.isEmpty(var2)) {
            var0.attribute((String)null, "scheme", var2);
         }

         XmlSerializer var6 = var0.endTag((String)null, "category");
      }
   }

   private static void serializeContent(XmlSerializer var0, String var1) throws IOException {
      if(var1 != null) {
         XmlSerializer var2 = var0.startTag((String)null, "content");
         XmlSerializer var3 = var0.attribute((String)null, "type", "text");
         var0.text(var1);
         XmlSerializer var5 = var0.endTag((String)null, "content");
      }
   }

   private final void serializeEntryContents(XmlSerializer var1, int var2) throws ParseException, IOException {
      if(var2 != 1) {
         String var3 = this.entry.getId();
         serializeId(var1, var3);
      }

      String var4 = this.entry.getTitle();
      serializeTitle(var1, var4);
      if(var2 != 1) {
         String var5 = this.entry.getEditUri();
         serializeLink(var1, "edit", var5, (String)null);
         String var6 = this.entry.getHtmlUri();
         serializeLink(var1, "alternate", var6, "text/html");
      }

      String var7 = this.entry.getSummary();
      serializeSummary(var1, var7);
      String var8 = this.entry.getContent();
      serializeContent(var1, var8);
      String var9 = this.entry.getAuthor();
      String var10 = this.entry.getEmail();
      serializeAuthor(var1, var9, var10);
      String var11 = this.entry.getCategory();
      String var12 = this.entry.getCategoryScheme();
      serializeCategory(var1, var11, var12);
      if(var2 == 0) {
         String var13 = this.entry.getPublicationDate();
         serializePublicationDate(var1, var13);
      }

      if(var2 != 1) {
         String var14 = this.entry.getUpdateDate();
         serializeUpdateDate(var1, var14);
      }

      this.serializeExtraEntryContents(var1, var2);
   }

   private static void serializeId(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag((String)null, "id");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag((String)null, "id");
      }
   }

   public static void serializeLink(XmlSerializer var0, String var1, String var2, String var3) throws IOException {
      if(!StringUtils.isEmpty(var2)) {
         XmlSerializer var4 = var0.startTag((String)null, "link");
         var0.attribute((String)null, "rel", var1);
         var0.attribute((String)null, "href", var2);
         if(!StringUtils.isEmpty(var3)) {
            var0.attribute((String)null, "type", var3);
         }

         XmlSerializer var8 = var0.endTag((String)null, "link");
      }
   }

   private static void serializePublicationDate(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag((String)null, "published");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag((String)null, "published");
      }
   }

   private static void serializeSummary(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag((String)null, "summary");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag((String)null, "summary");
      }
   }

   private static void serializeTitle(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag((String)null, "title");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag((String)null, "title");
      }
   }

   private static void serializeUpdateDate(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag((String)null, "updated");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag((String)null, "updated");
      }
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {}

   public String getContentType() {
      return "application/atom+xml";
   }

   protected Entry getEntry() {
      return this.entry;
   }

   public void serialize(OutputStream var1, int var2) throws IOException, ParseException {
      XmlSerializer var3;
      try {
         var3 = this.factory.createSerializer();
      } catch (XmlPullParserException var9) {
         throw new ParseException("Unable to create XmlSerializer.", var9);
      }

      var3.setOutput(var1, "UTF-8");
      Boolean var5 = Boolean.FALSE;
      var3.startDocument("UTF-8", var5);
      this.declareEntryNamespaces(var3);
      XmlSerializer var6 = var3.startTag("http://www.w3.org/2005/Atom", "entry");
      this.serializeEntryContents(var3, var2);
      XmlSerializer var7 = var3.endTag("http://www.w3.org/2005/Atom", "entry");
      var3.endDocument();
      var3.flush();
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws ParseException, IOException {}
}
