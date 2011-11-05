package com.google.wireless.gdata.contacts.serializer.xml;

import com.google.wireless.gdata.contacts.data.ContactEntry;
import com.google.wireless.gdata.contacts.data.ContactsElement;
import com.google.wireless.gdata.contacts.data.EmailAddress;
import com.google.wireless.gdata.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata.contacts.data.ImAddress;
import com.google.wireless.gdata.contacts.data.Organization;
import com.google.wireless.gdata.contacts.data.PhoneNumber;
import com.google.wireless.gdata.contacts.data.PostalAddress;
import com.google.wireless.gdata.contacts.parser.xml.XmlContactsGDataParser;
import com.google.wireless.gdata.data.ExtendedProperty;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlSerializer;

public class XmlContactEntryGDataSerializer extends XmlEntryGDataSerializer {

   public XmlContactEntryGDataSerializer(XmlParserFactory var1, ContactEntry var2) {
      super(var1, var2);
   }

   private static void serialize(XmlSerializer var0, EmailAddress var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getAddress())) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "email");
         Hashtable var3 = XmlContactsGDataParser.TYPE_TO_REL_EMAIL;
         serializeContactsElement(var0, var1, var3);
         String var4 = var1.getAddress();
         var0.attribute((String)null, "address", var4);
         XmlSerializer var6 = var0.endTag("http://schemas.google.com/g/2005", "email");
      }
   }

   private static void serialize(XmlSerializer var0, GroupMembershipInfo var1) throws IOException, ParseException {
      String var2 = var1.getGroup();
      boolean var3 = var1.isDeleted();
      if(StringUtils.isEmptyOrWhitespace(var2)) {
         throw new ParseException("the group must not be empty");
      } else {
         XmlSerializer var4 = var0.startTag("http://schemas.google.com/contact/2008", "groupMembershipInfo");
         var0.attribute((String)null, "href", var2);
         String var6 = "deleted";
         String var7;
         if(var3) {
            var7 = "true";
         } else {
            var7 = "false";
         }

         var0.attribute((String)null, var6, var7);
         XmlSerializer var9 = var0.endTag("http://schemas.google.com/contact/2008", "groupMembershipInfo");
      }
   }

   private static void serialize(XmlSerializer var0, ImAddress var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getAddress())) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "im");
         Hashtable var3 = XmlContactsGDataParser.TYPE_TO_REL_IM;
         serializeContactsElement(var0, var1, var3);
         String var4 = var1.getAddress();
         var0.attribute((String)null, "address", var4);
         switch(var1.getProtocolPredefined()) {
         case 1:
            String var12 = var1.getProtocolCustom();
            if(var12 == null) {
               throw new IllegalArgumentException("the protocol is custom, but the custom string is null");
            }

            var0.attribute((String)null, "protocol", var12);
         case 10:
            break;
         default:
            Hashtable var6 = XmlContactsGDataParser.IM_PROTOCOL_TYPE_TO_STRING_MAP;
            byte var7 = var1.getProtocolPredefined();
            Byte var8 = new Byte(var7);
            String var9 = (String)var6.get(var8);
            var0.attribute((String)null, "protocol", var9);
         }

         XmlSerializer var11 = var0.endTag("http://schemas.google.com/g/2005", "im");
      }
   }

   private static void serialize(XmlSerializer var0, Organization var1) throws IOException, ParseException {
      String var2 = var1.getName();
      String var3 = var1.getTitle();
      if(!StringUtils.isEmptyOrWhitespace(var2) || !StringUtils.isEmptyOrWhitespace(var3)) {
         XmlSerializer var4 = var0.startTag("http://schemas.google.com/g/2005", "organization");
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_ORGANIZATION;
         serializeContactsElement(var0, var1, var5);
         if(!StringUtils.isEmpty(var2)) {
            XmlSerializer var6 = var0.startTag("http://schemas.google.com/g/2005", "orgName");
            var0.text(var2);
            XmlSerializer var8 = var0.endTag("http://schemas.google.com/g/2005", "orgName");
         }

         if(!StringUtils.isEmpty(var3)) {
            XmlSerializer var9 = var0.startTag("http://schemas.google.com/g/2005", "orgTitle");
            var0.text(var3);
            XmlSerializer var11 = var0.endTag("http://schemas.google.com/g/2005", "orgTitle");
         }

         XmlSerializer var12 = var0.endTag("http://schemas.google.com/g/2005", "organization");
      }
   }

   private static void serialize(XmlSerializer var0, PhoneNumber var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getPhoneNumber())) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "phoneNumber");
         Hashtable var3 = XmlContactsGDataParser.TYPE_TO_REL_PHONE;
         serializeContactsElement(var0, var1, var3);
         String var4 = var1.getPhoneNumber();
         var0.text(var4);
         XmlSerializer var6 = var0.endTag("http://schemas.google.com/g/2005", "phoneNumber");
      }
   }

   private static void serialize(XmlSerializer var0, PostalAddress var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getValue())) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/g/2005", "postalAddress");
         Hashtable var3 = XmlContactsGDataParser.TYPE_TO_REL_POSTAL;
         serializeContactsElement(var0, var1, var3);
         String var4 = var1.getValue();
         if(var4 != null) {
            var0.text(var4);
         }

         XmlSerializer var6 = var0.endTag("http://schemas.google.com/g/2005", "postalAddress");
      }
   }

   private static void serialize(XmlSerializer var0, ExtendedProperty var1) throws IOException, ParseException {
      String var2 = var1.getName();
      String var3 = var1.getValue();
      String var4 = var1.getXmlBlob();
      XmlSerializer var5 = var0.startTag("http://schemas.google.com/g/2005", "extendedProperty");
      if(!StringUtils.isEmpty(var2)) {
         var0.attribute((String)null, "name", var2);
      }

      if(!StringUtils.isEmpty(var3)) {
         var0.attribute((String)null, "value", var3);
      }

      if(!StringUtils.isEmpty(var4)) {
         serializeBlob(var0, var4);
      }

      XmlSerializer var8 = var0.endTag("http://schemas.google.com/g/2005", "extendedProperty");
   }

   private static void serializeBlob(XmlSerializer var0, String var1) throws IOException, ParseException {
      var0.text(var1);
   }

   private static void serializeContactsElement(XmlSerializer var0, ContactsElement var1, Hashtable var2) throws IOException, ParseException {
      String var3 = var1.getLabel();
      boolean var4;
      if(var1.getType() != -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      if((var3 != null || var4) && (var3 == null || !var4)) {
         if(var3 != null) {
            var0.attribute((String)null, "label", var3);
         }

         if(var4) {
            byte var6 = var1.getType();
            Byte var7 = new Byte(var6);
            String var8 = (String)var2.get(var7);
            var0.attribute((String)null, "rel", var8);
         }

         if(var1.isPrimary()) {
            XmlSerializer var10 = var0.attribute((String)null, "primary", "true");
         }
      } else {
         throw new ParseException("exactly one of label or rel must be set");
      }
   }

   private static void serializeYomiName(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         XmlSerializer var2 = var0.startTag("http://schemas.google.com/contact/2008", "yomiName");
         var0.text(var1);
         XmlSerializer var4 = var0.endTag("http://schemas.google.com/contact/2008", "yomiName");
      }
   }

   protected void declareExtraEntryNamespaces(XmlSerializer var1) throws IOException {
      super.declareExtraEntryNamespaces(var1);
      var1.setPrefix("gContact", "http://schemas.google.com/contact/2008");
   }

   protected ContactEntry getContactEntry() {
      return (ContactEntry)this.getEntry();
   }

   protected void serializeExtraEntryContents(XmlSerializer var1, int var2) throws ParseException, IOException {
      ContactEntry var3 = this.getContactEntry();
      var3.validate();
      String var4 = var3.getLinkEditPhotoHref();
      String var5 = var3.getLinkEditPhotoType();
      serializeLink(var1, "http://schemas.google.com/contacts/2008/rel#edit-photo", var4, var5);
      String var6 = var3.getLinkPhotoHref();
      String var7 = var3.getLinkPhotoType();
      serializeLink(var1, "http://schemas.google.com/contacts/2008/rel#photo", var6, var7);
      Enumeration var8 = var3.getEmailAddresses().elements();

      while(var8.hasMoreElements()) {
         EmailAddress var9 = (EmailAddress)var8.nextElement();
         serialize(var1, var9);
      }

      Enumeration var10 = var3.getImAddresses().elements();

      while(var10.hasMoreElements()) {
         ImAddress var11 = (ImAddress)var10.nextElement();
         serialize(var1, var11);
      }

      Enumeration var12 = var3.getPhoneNumbers().elements();

      while(var12.hasMoreElements()) {
         PhoneNumber var13 = (PhoneNumber)var12.nextElement();
         serialize(var1, var13);
      }

      Enumeration var14 = var3.getPostalAddresses().elements();

      while(var14.hasMoreElements()) {
         PostalAddress var15 = (PostalAddress)var14.nextElement();
         serialize(var1, var15);
      }

      Enumeration var16 = var3.getOrganizations().elements();

      while(var16.hasMoreElements()) {
         Organization var17 = (Organization)var16.nextElement();
         serialize(var1, var17);
      }

      Enumeration var18 = var3.getExtendedProperties().elements();

      while(var18.hasMoreElements()) {
         ExtendedProperty var19 = (ExtendedProperty)var18.nextElement();
         serialize(var1, var19);
      }

      Enumeration var20 = var3.getGroups().elements();

      while(var20.hasMoreElements()) {
         GroupMembershipInfo var21 = (GroupMembershipInfo)var20.nextElement();
         serialize(var1, var21);
      }

      String var22 = var3.getYomiName();
      serializeYomiName(var1, var22);
   }
}
