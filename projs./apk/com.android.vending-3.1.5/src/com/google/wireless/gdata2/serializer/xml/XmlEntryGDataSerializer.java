package com.google.wireless.gdata2.serializer.xml;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.data.batch.BatchUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlNametable;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import java.io.IOException;
import java.io.OutputStream;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlEntryGDataSerializer implements GDataSerializer {

   private static final Entry EMPTY_ENTRY = new Entry();
   protected final Entry entry;
   private final XmlParserFactory factory;
   protected Entry oldEntry;


   public XmlEntryGDataSerializer(XmlParserFactory var1, Entry var2) {
      this.factory = var1;
      this.entry = var2;
      Entry var3 = EMPTY_ENTRY;
      this.oldEntry = var3;
   }

   public XmlEntryGDataSerializer(XmlParserFactory var1, Entry var2, Entry var3) {
      this.factory = var1;
      this.entry = var2;
      this.oldEntry = var3;
   }

   private void declareEntryNamespaces(XmlSerializer var1) throws IOException {
      var1.setPrefix("", "http://www.w3.org/2005/Atom");
      var1.setPrefix("gd", "http://schemas.google.com/g/2005");
      this.declareExtraEntryNamespaces(var1);
   }

   protected static boolean equals(String var0, String var1) {
      boolean var2;
      if(StringUtils.isEmpty(var0)) {
         var2 = StringUtils.isEmpty(var1);
      } else {
         var2 = var0.equals(var1);
      }

      return var2;
   }

   private void serializeAuthor(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getAuthor();
      String var3 = this.entry.getEmail();
      if(!StringUtils.isEmpty(var2)) {
         if(!StringUtils.isEmpty(var3)) {
            String var4 = this.oldEntry.getAuthor();
            if(var2.equals(var4)) {
               String var5 = this.oldEntry.getEmail();
               if(var3.equals(var5)) {
                  return;
               }
            }

            String var6 = XmlNametable.AUTHOR;
            var1.startTag((String)null, var6);
            String var8 = XmlNametable.NAME;
            var1.startTag((String)null, var8);
            var1.text(var2);
            String var11 = XmlNametable.NAME;
            var1.endTag((String)null, var11);
            String var13 = XmlNametable.EMAIL;
            var1.startTag((String)null, var13);
            var1.text(var3);
            String var16 = XmlNametable.EMAIL;
            var1.endTag((String)null, var16);
            String var18 = XmlNametable.AUTHOR;
            var1.endTag((String)null, var18);
         }
      }
   }

   private void serializeBatchInfo(XmlSerializer var1) throws IOException {
      if(!StringUtils.isEmpty(this.entry.getETag())) {
         String var2 = XmlNametable.ETAG;
         String var3 = this.entry.getETag();
         var1.attribute("http://schemas.google.com/g/2005", var2, var3);
      }

      if(!StringUtils.isEmpty(BatchUtils.getBatchOperation(this.entry))) {
         String var5 = XmlNametable.OPERATION;
         var1.startTag("http://schemas.google.com/gdata/batch", var5);
         String var7 = XmlNametable.TYPE;
         String var8 = BatchUtils.getBatchOperation(this.entry);
         var1.attribute((String)null, var7, var8);
         String var10 = XmlNametable.OPERATION;
         var1.endTag("http://schemas.google.com/gdata/batch", var10);
      }

      if(!StringUtils.isEmpty(BatchUtils.getBatchId(this.entry))) {
         String var12 = XmlNametable.ID;
         var1.startTag("http://schemas.google.com/gdata/batch", var12);
         String var14 = BatchUtils.getBatchId(this.entry);
         var1.text(var14);
         String var16 = XmlNametable.ID;
         var1.endTag("http://schemas.google.com/gdata/batch", var16);
      }
   }

   private void serializeCategory(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getCategory();
      String var3 = this.entry.getCategoryScheme();
      String var4 = this.oldEntry.getCategory();
      if(equals(var2, var4)) {
         String var5 = this.oldEntry.getCategoryScheme();
         if(equals(var3, var5)) {
            return;
         }
      }

      String var6 = XmlNametable.CATEGORY;
      var1.startTag((String)null, var6);
      if(!StringUtils.isEmpty(var2)) {
         String var8 = XmlNametable.TERM;
         var1.attribute((String)null, var8, var2);
      }

      if(!StringUtils.isEmpty(var3)) {
         String var10 = XmlNametable.SCHEME;
         var1.attribute((String)null, var10, var3);
      }

      String var12 = XmlNametable.CATEGORY;
      var1.endTag((String)null, var12);
   }

   private void serializeContent(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getContent();
      String var3 = this.oldEntry.getContent();
      if(!equals(var2, var3)) {
         String var4 = XmlNametable.CONTENT;
         var1.startTag((String)null, var4);
         String var6 = XmlNametable.TYPE;
         String var7 = XmlNametable.TEXT;
         var1.attribute((String)null, var6, var7);
         var1.text(var2);
         String var10 = XmlNametable.CONTENT;
         var1.endTag((String)null, var10);
      }
   }

   private void serializeEntryContents(XmlSerializer var1, int var2) throws ParseException, IOException {
      if(var2 == 3) {
         this.serializeBatchInfo(var1);
      }

      if(var2 != 1 && !this.isPartial()) {
         String var3 = this.entry.getId();
         serializeId(var1, var3);
      }

      this.serializeTitle(var1);
      if(var2 != 1 && !this.isPartial()) {
         String var4 = XmlNametable.EDIT_REL;
         String var5 = this.entry.getEditUri();
         serializeLink(var1, var4, var5, (String)null, (String)null);
         String var6 = XmlNametable.ALTERNATE_REL;
         String var7 = this.entry.getHtmlUri();
         String var8 = XmlNametable.TEXTHTML;
         serializeLink(var1, var6, var7, var8, (String)null);
      }

      this.serializeSummary(var1);
      this.serializeContent(var1);
      this.serializeAuthor(var1);
      this.serializeCategory(var1);
      if(var2 == 0) {
         this.serializePublicationDate(var1);
      }

      if(var2 != 1) {
         this.serializeUpdateDate(var1);
      }

      this.serializeExtraEntryContents(var1, var2);
   }

   private static void serializeId(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         String var2 = XmlNametable.ID;
         var0.startTag((String)null, var2);
         var0.text(var1);
         String var5 = XmlNametable.ID;
         var0.endTag((String)null, var5);
      }
   }

   protected static void serializeLink(XmlSerializer var0, String var1, String var2, String var3, String var4) throws IOException {
      if(!StringUtils.isEmpty(var2)) {
         String var5 = XmlNametable.LINK;
         var0.startTag((String)null, var5);
         String var7 = XmlNametable.REL;
         var0.attribute((String)null, var7, var1);
         String var9 = XmlNametable.HREF;
         var0.attribute((String)null, var9, var2);
         if(!StringUtils.isEmpty(var3)) {
            String var11 = XmlNametable.TYPE;
            var0.attribute((String)null, var11, var3);
         }

         if(!StringUtils.isEmpty(var4)) {
            String var13 = XmlNametable.ETAG;
            var0.attribute((String)null, var13, var4);
         }

         String var15 = XmlNametable.LINK;
         var0.endTag((String)null, var15);
      }
   }

   private void serializePublicationDate(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getPublicationDate();
      if(!StringUtils.isEmpty(var2)) {
         String var3 = XmlNametable.PUBLISHED;
         var1.startTag((String)null, var3);
         var1.text(var2);
         String var6 = XmlNametable.PUBLISHED;
         var1.endTag((String)null, var6);
      }
   }

   private void serializeSummary(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getSummary();
      String var3 = this.oldEntry.getSummary();
      if(!equals(var2, var3)) {
         String var4 = XmlNametable.SUMMARY;
         var1.startTag((String)null, var4);
         var1.text(var2);
         String var7 = XmlNametable.SUMMARY;
         var1.endTag((String)null, var7);
      }
   }

   private void serializeTitle(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getTitle();
      String var3 = this.oldEntry.getTitle();
      if(!equals(var2, var3)) {
         String var4 = XmlNametable.TITLE;
         var1.startTag((String)null, var4);
         var1.text(var2);
         String var7 = XmlNametable.TITLE;
         var1.endTag((String)null, var7);
      }
   }

   private void serializeUpdateDate(XmlSerializer var1) throws IOException {
      String var2 = this.entry.getUpdateDate();
      String var3 = this.oldEntry.getUpdateDate();
      if(!equals(var2, var3)) {
         String var4 = XmlNametable.UPDATED;
         var1.startTag((String)null, var4);
         var1.text(var2);
         String var7 = XmlNametable.UPDATED;
         var1.endTag((String)null, var7);
      }
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {}

   public String getContentType() {
      String var1;
      if(this.isPartial()) {
         var1 = "application/xml";
      } else {
         var1 = "application/atom+xml";
      }

      return var1;
   }

   protected Entry getEntry() {
      return this.entry;
   }

   protected Entry getOldEntry() {
      Entry var1 = this.oldEntry;
      Entry var2 = EMPTY_ENTRY;
      Entry var3;
      if(var1 == var2) {
         var3 = null;
      } else {
         var3 = this.oldEntry;
      }

      return var3;
   }

   public boolean isPartial() {
      Entry var1 = this.oldEntry;
      Entry var2 = EMPTY_ENTRY;
      boolean var3;
      if(var1 != var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void serialize(OutputStream var1, int var2) throws IOException, ParseException {
      XmlSerializer var3;
      try {
         var3 = this.factory.createSerializer();
      } catch (XmlPullParserException var16) {
         throw new ParseException("Unable to create XmlSerializer.", var16);
      }

      String var5 = XmlNametable.UTF8;
      var3.setOutput(var1, var5);
      if(var2 != 3) {
         String var6 = XmlNametable.UTF8;
         Boolean var7 = Boolean.FALSE;
         var3.startDocument(var6, var7);
         this.declareEntryNamespaces(var3);
      }

      String var8 = XmlNametable.ENTRY;
      var3.startTag("http://www.w3.org/2005/Atom", var8);
      String var10 = this.entry.getFields();
      if(!StringUtils.isEmptyOrWhitespace(var10)) {
         String var11 = XmlNametable.FIELDS;
         var3.attribute("http://schemas.google.com/g/2005", var11, var10);
      }

      this.serializeEntryContents(var3, var2);
      String var13 = XmlNametable.ENTRY;
      XmlSerializer var14 = var3.endTag("http://www.w3.org/2005/Atom", var13);
      if(var2 != 3) {
         var3.endDocument();
      }

      var3.flush();
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws ParseException, IOException {}
}
