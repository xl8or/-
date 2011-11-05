package com.google.wireless.gdata2.contacts.parser.xml;

import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.contacts.data.ContactEntry;
import com.google.wireless.gdata2.contacts.data.GroupEntry;
import com.google.wireless.gdata2.contacts.parser.xml.XmlContactsGDataParser;
import com.google.wireless.gdata2.contacts.parser.xml.XmlGroupEntryGDataParser;
import com.google.wireless.gdata2.contacts.serializer.xml.XmlContactEntryGDataSerializer;
import com.google.wireless.gdata2.contacts.serializer.xml.XmlGroupEntryGDataSerializer;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.MediaEntry;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlMediaEntryGDataParser;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import com.google.wireless.gdata2.serializer.xml.XmlBatchGDataSerializer;
import java.io.InputStream;
import java.util.Enumeration;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlContactsGDataParserFactory implements GDataParserFactory {

   private final XmlParserFactory xmlFactory;


   public XmlContactsGDataParserFactory(XmlParserFactory var1) {
      this.xmlFactory = var1;
   }

   public GDataParser createGroupEntryFeedParser(InputStream var1) throws ParseException {
      XmlPullParser var2;
      try {
         var2 = this.xmlFactory.createParser();
      } catch (XmlPullParserException var5) {
         throw new ParseException("Could not create XmlPullParser", var5);
      }

      return new XmlGroupEntryGDataParser(var1, var2);
   }

   public GDataParser createMediaEntryFeedParser(InputStream var1) throws ParseException {
      XmlPullParser var2;
      try {
         var2 = this.xmlFactory.createParser();
      } catch (XmlPullParserException var5) {
         throw new ParseException("Could not create XmlPullParser", var5);
      }

      return new XmlMediaEntryGDataParser(var1, var2);
   }

   public GDataParser createParser(InputStream var1) throws ParseException {
      XmlPullParser var2;
      try {
         var2 = this.xmlFactory.createParser();
      } catch (XmlPullParserException var5) {
         throw new ParseException("Could not create XmlPullParser", var5);
      }

      return new XmlContactsGDataParser(var1, var2);
   }

   public GDataParser createParser(Class var1, InputStream var2) throws ParseException {
      GDataParser var3;
      if(var1 == ContactEntry.class) {
         var3 = this.createParser(var2);
      } else if(var1 == GroupEntry.class) {
         var3 = this.createGroupEntryFeedParser(var2);
      } else {
         if(var1 != MediaEntry.class) {
            StringBuilder var4 = (new StringBuilder()).append("unexpected feed type, ");
            String var5 = var1.getName();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }

         var3 = this.createMediaEntryFeedParser(var2);
      }

      return var3;
   }

   public GDataSerializer createSerializer(Entry var1) {
      Object var4;
      if(var1 instanceof ContactEntry) {
         ContactEntry var2 = (ContactEntry)var1;
         XmlParserFactory var3 = this.xmlFactory;
         var4 = new XmlContactEntryGDataSerializer(var3, var2);
      } else {
         if(!(var1 instanceof GroupEntry)) {
            StringBuilder var7 = (new StringBuilder()).append("unexpected entry type, ");
            String var8 = var1.getClass().toString();
            String var9 = var7.append(var8).toString();
            throw new IllegalArgumentException(var9);
         }

         GroupEntry var5 = (GroupEntry)var1;
         XmlParserFactory var6 = this.xmlFactory;
         var4 = new XmlGroupEntryGDataSerializer(var6, var5);
      }

      return (GDataSerializer)var4;
   }

   public GDataSerializer createSerializer(Entry var1, Entry var2) {
      return this.createSerializer(var1);
   }

   public GDataSerializer createSerializer(Enumeration var1) {
      XmlParserFactory var2 = this.xmlFactory;
      return new XmlBatchGDataSerializer(this, var2, var1);
   }
}
