package com.google.wireless.gdata2.contacts.serializer.xml;

import com.google.wireless.gdata2.contacts.data.CalendarLink;
import com.google.wireless.gdata2.contacts.data.ContactEntry;
import com.google.wireless.gdata2.contacts.data.ContactsElement;
import com.google.wireless.gdata2.contacts.data.EmailAddress;
import com.google.wireless.gdata2.contacts.data.Event;
import com.google.wireless.gdata2.contacts.data.ExternalId;
import com.google.wireless.gdata2.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata2.contacts.data.ImAddress;
import com.google.wireless.gdata2.contacts.data.Jot;
import com.google.wireless.gdata2.contacts.data.Language;
import com.google.wireless.gdata2.contacts.data.Name;
import com.google.wireless.gdata2.contacts.data.Organization;
import com.google.wireless.gdata2.contacts.data.PhoneNumber;
import com.google.wireless.gdata2.contacts.data.Relation;
import com.google.wireless.gdata2.contacts.data.SipAddress;
import com.google.wireless.gdata2.contacts.data.StructuredPostalAddress;
import com.google.wireless.gdata2.contacts.data.TypedElement;
import com.google.wireless.gdata2.contacts.data.UserDefinedField;
import com.google.wireless.gdata2.contacts.data.WebSite;
import com.google.wireless.gdata2.contacts.parser.xml.XmlContactsGDataParser;
import com.google.wireless.gdata2.contacts.parser.xml.XmlNametable;
import com.google.wireless.gdata2.data.ExtendedProperty;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.xml.XmlEntryGDataSerializer;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlSerializer;

public class XmlContactEntryGDataSerializer extends XmlEntryGDataSerializer {

   public XmlContactEntryGDataSerializer(XmlParserFactory var1, ContactEntry var2) {
      super(var1, var2);
   }

   private static void serialize(XmlSerializer var0, CalendarLink var1) throws IOException, ParseException {
      String var2 = var1.getHRef();
      if(shouldSerialize(var1, var2)) {
         String var3 = XmlNametable.GC_CALENDARLINK;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_CALENDARLINK;
         serializeContactsElement(var0, var1, var5);
         if(!StringUtils.isEmpty(var2)) {
            String var6 = XmlNametable.HREF;
            var0.attribute((String)null, var6, var2);
         }

         String var8 = XmlNametable.GC_CALENDARLINK;
         var0.endTag("http://schemas.google.com/contact/2008", var8);
      }
   }

   private static void serialize(XmlSerializer var0, EmailAddress var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getAddress())) {
         String var2 = XmlNametable.GD_EMAIL;
         var0.startTag("http://schemas.google.com/g/2005", var2);
         Hashtable var4 = XmlContactsGDataParser.TYPE_TO_REL_EMAIL;
         serializeContactsElement(var0, var1, var4);
         String var5 = XmlNametable.GD_ADDRESS;
         String var6 = var1.getAddress();
         var0.attribute((String)null, var5, var6);
         String var8 = XmlNametable.GD_EMAIL;
         var0.endTag("http://schemas.google.com/g/2005", var8);
      }
   }

   private static void serialize(XmlSerializer var0, Event var1) throws IOException, ParseException {
      String var2 = var1.getStartDate();
      if(shouldSerialize(var1, var2)) {
         String var3 = XmlNametable.GC_EVENT;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_EVENT;
         serializeTypedElement(var0, var1, var5);
         if(!StringUtils.isEmpty(var2)) {
            String var6 = XmlNametable.GD_WHEN;
            var0.startTag("http://schemas.google.com/g/2005", var6);
            String var8 = XmlNametable.STARTTIME;
            var0.attribute((String)null, var8, var2);
            String var10 = XmlNametable.GD_WHEN;
            var0.endTag("http://schemas.google.com/g/2005", var10);
         }

         String var12 = XmlNametable.GC_EVENT;
         var0.endTag("http://schemas.google.com/contact/2008", var12);
      }
   }

   private static void serialize(XmlSerializer var0, ExternalId var1) throws IOException, ParseException {
      String var2 = var1.getValue();
      if(shouldSerialize(var1, var2)) {
         String var3 = XmlNametable.GC_EXTERNALID;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_EXTERNALID;
         serializeTypedElement(var0, var1, var5);
         if(!StringUtils.isEmpty(var2)) {
            String var6 = XmlNametable.VALUE;
            var0.attribute((String)null, var6, var2);
         }

         String var8 = XmlNametable.GC_EXTERNALID;
         var0.endTag("http://schemas.google.com/contact/2008", var8);
      }
   }

   private static void serialize(XmlSerializer var0, GroupMembershipInfo var1) throws IOException, ParseException {
      String var2 = var1.getGroup();
      boolean var3 = var1.isDeleted();
      if(StringUtils.isEmptyOrWhitespace(var2)) {
         throw new ParseException("the group must not be empty");
      } else {
         String var4 = XmlNametable.GC_GMI;
         var0.startTag("http://schemas.google.com/contact/2008", var4);
         String var6 = XmlNametable.HREF;
         var0.attribute((String)null, var6, var2);
         String var8 = XmlNametable.GD_DELETED;
         String var9;
         if(var3) {
            var9 = "true";
         } else {
            var9 = "false";
         }

         var0.attribute((String)null, var8, var9);
         String var11 = XmlNametable.GC_GMI;
         var0.endTag("http://schemas.google.com/contact/2008", var11);
      }
   }

   private static void serialize(XmlSerializer var0, ImAddress var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getAddress())) {
         String var2 = XmlNametable.GD_IM;
         var0.startTag("http://schemas.google.com/g/2005", var2);
         Hashtable var4 = XmlContactsGDataParser.TYPE_TO_REL_IM;
         serializeContactsElement(var0, var1, var4);
         String var5 = XmlNametable.GD_ADDRESS;
         String var6 = var1.getAddress();
         var0.attribute((String)null, var5, var6);
         switch(var1.getProtocolPredefined()) {
         case 1:
            String var16 = var1.getProtocolCustom();
            if(var16 == null) {
               throw new IllegalArgumentException("the protocol is custom, but the custom string is null");
            }

            String var17 = XmlNametable.GD_PROTOCOL;
            var0.attribute((String)null, var17, var16);
         case 11:
            break;
         default:
            Hashtable var8 = XmlContactsGDataParser.IM_PROTOCOL_TYPE_TO_STRING_MAP;
            byte var9 = var1.getProtocolPredefined();
            Byte var10 = new Byte(var9);
            String var11 = (String)var8.get(var10);
            String var12 = XmlNametable.GD_PROTOCOL;
            var0.attribute((String)null, var12, var11);
         }

         String var14 = XmlNametable.GD_IM;
         var0.endTag("http://schemas.google.com/g/2005", var14);
      }
   }

   private static void serialize(XmlSerializer var0, Jot var1) throws IOException {
      String var2 = var1.getLabel();
      if(!StringUtils.isEmptyOrWhitespace(var2)) {
         String var3 = XmlNametable.GC_JOT;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         byte var5 = var1.getType();
         Hashtable var6 = XmlContactsGDataParser.TYPE_TO_REL_JOT;
         serializeRelation(var0, var5, var6);
         var0.text(var2);
         String var8 = XmlNametable.GC_JOT;
         var0.endTag("http://schemas.google.com/contact/2008", var8);
      }
   }

   private static void serialize(XmlSerializer var0, Language var1) throws IOException, ParseException {
      var1.validate();
      String var2 = XmlNametable.GC_LANGUAGE;
      var0.startTag("http://schemas.google.com/contact/2008", var2);
      String var4 = var1.getCode();
      if(!StringUtils.isEmptyOrWhitespace(var4)) {
         String var5 = XmlNametable.CODE;
         var0.attribute((String)null, var5, var4);
      } else {
         String var9 = XmlNametable.LABEL;
         String var10 = var1.getLabel();
         var0.attribute((String)null, var9, var10);
      }

      String var7 = XmlNametable.GC_LANGUAGE;
      var0.endTag("http://schemas.google.com/contact/2008", var7);
   }

   private static void serialize(XmlSerializer var0, Organization var1) throws IOException, ParseException {
      String var2 = XmlNametable.GD_ORGANIZATION;
      var0.startTag("http://schemas.google.com/g/2005", var2);
      Hashtable var4 = XmlContactsGDataParser.TYPE_TO_REL_ORGANIZATION;
      serializeContactsElement(var0, var1, var4);
      String var5 = var1.getName();
      String var6 = XmlNametable.GD_ORG_NAME;
      serializeGDSubelement(var0, var5, var6);
      String var7 = var1.getTitle();
      String var8 = XmlNametable.GD_ORG_TITLE;
      serializeGDSubelement(var0, var7, var8);
      String var9 = var1.getOrgDepartment();
      String var10 = XmlNametable.GD_ORG_DEPARTMENT;
      serializeGDSubelement(var0, var9, var10);
      String var11 = var1.getOrgJobDescription();
      String var12 = XmlNametable.GD_ORG_JOBDESC;
      serializeGDSubelement(var0, var11, var12);
      String var13 = var1.getOrgSymbol();
      String var14 = XmlNametable.GD_ORG_SYMBOL;
      serializeGDSubelement(var0, var13, var14);
      String var15 = var1.getWhere();
      if(!StringUtils.isEmpty(var15)) {
         String var16 = XmlNametable.GD_WHERE;
         var0.startTag("http://schemas.google.com/g/2005", var16);
         String var18 = XmlNametable.VALUESTRING;
         var0.attribute((String)null, var18, var15);
         String var20 = XmlNametable.GD_WHERE;
         var0.endTag("http://schemas.google.com/g/2005", var20);
      }

      String var22 = XmlNametable.GD_ORGANIZATION;
      var0.endTag("http://schemas.google.com/g/2005", var22);
   }

   private static void serialize(XmlSerializer var0, PhoneNumber var1) throws IOException, ParseException {
      if(!StringUtils.isEmptyOrWhitespace(var1.getPhoneNumber())) {
         String var2 = XmlNametable.GD_PHONENUMBER;
         var0.startTag("http://schemas.google.com/g/2005", var2);
         Hashtable var4 = XmlContactsGDataParser.TYPE_TO_REL_PHONE;
         serializeContactsElement(var0, var1, var4);
         String var5 = var1.getPhoneNumber();
         var0.text(var5);
         String var7 = XmlNametable.GD_PHONENUMBER;
         var0.endTag("http://schemas.google.com/g/2005", var7);
      }
   }

   private static void serialize(XmlSerializer var0, Relation var1) throws IOException, ParseException {
      String var2 = var1.getText();
      if(shouldSerialize(var1, var2)) {
         String var3 = XmlNametable.GC_RELATION;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_RELATION;
         serializeTypedElement(var0, var1, var5);
         var0.text(var2);
         String var7 = XmlNametable.GC_RELATION;
         var0.endTag("http://schemas.google.com/contact/2008", var7);
      }
   }

   private static void serialize(XmlSerializer var0, SipAddress var1) throws IOException, ParseException {
      String var2 = var1.getAddress();
      if(shouldSerialize(var1, var2)) {
         String var3 = XmlNametable.GC_SIP;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_SIP;
         serializeContactsElement(var0, var1, var5);
         if(!StringUtils.isEmpty(var2)) {
            String var6 = XmlNametable.GD_ADDRESS;
            var0.attribute((String)null, var6, var2);
         }

         String var8 = XmlNametable.GC_SIP;
         var0.endTag("http://schemas.google.com/contact/2008", var8);
      }
   }

   private static void serialize(XmlSerializer var0, StructuredPostalAddress var1) throws IOException, ParseException {
      String var2 = XmlNametable.GD_SPA;
      var0.startTag("http://schemas.google.com/g/2005", var2);
      Hashtable var4 = XmlContactsGDataParser.TYPE_TO_REL_POSTAL;
      serializeContactsElement(var0, var1, var4);
      String var5 = var1.getStreet();
      String var6 = XmlNametable.GD_SPA_STREET;
      serializeGDSubelement(var0, var5, var6);
      String var7 = var1.getPobox();
      String var8 = XmlNametable.GD_SPA_POBOX;
      serializeGDSubelement(var0, var7, var8);
      String var9 = var1.getNeighborhood();
      String var10 = XmlNametable.GD_SPA_NEIGHBORHOOD;
      serializeGDSubelement(var0, var9, var10);
      String var11 = var1.getCity();
      String var12 = XmlNametable.GD_SPA_CITY;
      serializeGDSubelement(var0, var11, var12);
      String var13 = var1.getRegion();
      String var14 = XmlNametable.GD_SPA_REGION;
      serializeGDSubelement(var0, var13, var14);
      String var15 = var1.getPostcode();
      String var16 = XmlNametable.GD_SPA_POSTCODE;
      serializeGDSubelement(var0, var15, var16);
      String var17 = var1.getCountry();
      String var18 = XmlNametable.GD_SPA_COUNTRY;
      serializeGDSubelement(var0, var17, var18);
      String var19 = var1.getFormattedAddress();
      String var20 = XmlNametable.GD_SPA_FORMATTEDADDRESS;
      serializeGDSubelement(var0, var19, var20);
      String var21 = XmlNametable.GD_SPA;
      var0.endTag("http://schemas.google.com/g/2005", var21);
   }

   private static void serialize(XmlSerializer var0, UserDefinedField var1) throws IOException, ParseException {
      var1.validate();
      String var2 = XmlNametable.GC_UDF;
      var0.startTag("http://schemas.google.com/contact/2008", var2);
      if(!StringUtils.isEmpty(var1.getKey())) {
         String var4 = XmlNametable.KEY;
         String var5 = var1.getKey();
         var0.attribute((String)null, var4, var5);
      }

      if(!StringUtils.isEmpty(var1.getValue())) {
         String var7 = XmlNametable.VALUE;
         String var8 = var1.getValue();
         var0.attribute((String)null, var7, var8);
      }

      String var10 = XmlNametable.GC_UDF;
      var0.endTag("http://schemas.google.com/contact/2008", var10);
   }

   private static void serialize(XmlSerializer var0, WebSite var1) throws IOException, ParseException {
      String var2 = var1.getHRef();
      if(shouldSerialize(var1, var2)) {
         String var3 = XmlNametable.GC_WEBSITE;
         var0.startTag("http://schemas.google.com/contact/2008", var3);
         Hashtable var5 = XmlContactsGDataParser.TYPE_TO_REL_WEBSITE;
         serializeContactsElement(var0, var1, var5);
         if(!StringUtils.isEmpty(var2)) {
            String var6 = XmlNametable.HREF;
            var0.attribute((String)null, var6, var2);
         }

         String var8 = XmlNametable.GC_WEBSITE;
         var0.endTag("http://schemas.google.com/contact/2008", var8);
      }
   }

   private static void serialize(XmlSerializer var0, ExtendedProperty var1) throws IOException {
      String var2 = var1.getName();
      String var3 = var1.getValue();
      String var4 = var1.getXmlBlob();
      String var5 = XmlNametable.GD_EXTENDEDPROPERTY;
      var0.startTag("http://schemas.google.com/g/2005", var5);
      if(!StringUtils.isEmpty(var2)) {
         String var7 = XmlNametable.GD_NAME;
         var0.attribute((String)null, var7, var2);
      }

      if(!StringUtils.isEmpty(var3)) {
         String var9 = XmlNametable.VALUE;
         var0.attribute((String)null, var9, var3);
      }

      if(!StringUtils.isEmpty(var4)) {
         serializeBlob(var0, var4);
      }

      String var11 = XmlNametable.GD_EXTENDEDPROPERTY;
      var0.endTag("http://schemas.google.com/g/2005", var11);
   }

   private static void serializeBirthday(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmptyOrWhitespace(var1)) {
         String var2 = XmlNametable.GC_BIRTHDAY;
         var0.startTag("http://schemas.google.com/contact/2008", var2);
         String var4 = XmlNametable.GD_WHEN;
         var0.attribute((String)null, var4, var1);
         String var6 = XmlNametable.GC_BIRTHDAY;
         var0.endTag("http://schemas.google.com/contact/2008", var6);
      }
   }

   private static void serializeBlob(XmlSerializer var0, String var1) throws IOException {
      var0.text(var1);
   }

   private static void serializeContactsElement(XmlSerializer var0, ContactsElement var1, Hashtable var2) throws IOException, ParseException {
      serializeTypedElement(var0, var1, var2);
      if(var1.isPrimary()) {
         String var3 = XmlNametable.PRIMARY;
         var0.attribute((String)null, var3, "true");
      }
   }

   private static void serializeElement(XmlSerializer var0, byte var1, String var2, Hashtable var3) throws IOException {
      if(var1 != -1) {
         var0.startTag("http://schemas.google.com/contact/2008", var2);
         serializeRelation(var0, var1, var3);
         var0.endTag("http://schemas.google.com/contact/2008", var2);
      }
   }

   private static void serializeElement(XmlSerializer var0, String var1, String var2) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         var0.startTag("http://schemas.google.com/contact/2008", var2);
         var0.text(var1);
         var0.endTag("http://schemas.google.com/contact/2008", var2);
      }
   }

   private static void serializeGDSubelement(XmlSerializer var0, String var1, String var2) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         var0.startTag("http://schemas.google.com/g/2005", var2);
         var0.text(var1);
         var0.endTag("http://schemas.google.com/g/2005", var2);
      }
   }

   private static void serializeGenderElement(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         String var2 = XmlNametable.GC_GENDER;
         var0.startTag("http://schemas.google.com/contact/2008", var2);
         String var4 = XmlNametable.VALUE;
         var0.attribute((String)null, var4, var1);
         String var6 = XmlNametable.GC_GENDER;
         var0.endTag("http://schemas.google.com/contact/2008", var6);
      }
   }

   private static void serializeHobby(XmlSerializer var0, String var1) throws IOException {
      if(!StringUtils.isEmptyOrWhitespace(var1)) {
         String var2 = XmlNametable.GC_HOBBY;
         var0.startTag("http://schemas.google.com/contact/2008", var2);
         var0.text(var1);
         String var5 = XmlNametable.GC_HOBBY;
         var0.endTag("http://schemas.google.com/contact/2008", var5);
      }
   }

   private static void serializeName(XmlSerializer var0, Name var1) throws IOException {
      if(var1 != null) {
         String var2 = XmlNametable.GD_NAME;
         var0.startTag("http://schemas.google.com/g/2005", var2);
         String var4 = var1.getGivenName();
         String var5 = var1.getGivenNameYomi();
         String var6 = XmlNametable.GD_NAME_GIVENNAME;
         serializeNameSubelement(var0, var4, var5, var6);
         String var7 = var1.getAdditionalName();
         String var8 = var1.getAdditionalNameYomi();
         String var9 = XmlNametable.GD_NAME_ADDITIONALNAME;
         serializeNameSubelement(var0, var7, var8, var9);
         String var10 = var1.getFamilyName();
         String var11 = var1.getFamilyNameYomi();
         String var12 = XmlNametable.GD_NAME_FAMILYNAME;
         serializeNameSubelement(var0, var10, var11, var12);
         String var13 = var1.getNamePrefix();
         String var14 = XmlNametable.GD_NAME_PREFIX;
         serializeNameSubelement(var0, var13, (String)null, var14);
         String var15 = var1.getNameSuffix();
         String var16 = XmlNametable.GD_NAME_SUFFIX;
         serializeNameSubelement(var0, var15, (String)null, var16);
         String var17 = var1.getFullName();
         String var18 = XmlNametable.GD_NAME_FULLNAME;
         serializeNameSubelement(var0, var17, (String)null, var18);
         String var19 = XmlNametable.GD_NAME;
         var0.endTag("http://schemas.google.com/g/2005", var19);
      }
   }

   private static void serializeNameSubelement(XmlSerializer var0, String var1, String var2, String var3) throws IOException {
      if(!StringUtils.isEmpty(var1)) {
         var0.startTag("http://schemas.google.com/g/2005", var3);
         if(!StringUtils.isEmpty(var2)) {
            String var5 = XmlNametable.GD_NAME_YOMI;
            var0.attribute((String)null, var5, var2);
         }

         var0.text(var1);
         var0.endTag("http://schemas.google.com/g/2005", var3);
      }
   }

   private static void serializeRelation(XmlSerializer var0, byte var1, Hashtable var2) throws IOException {
      String var3 = XmlNametable.REL;
      Byte var4 = new Byte(var1);
      String var5 = (String)var2.get(var4);
      var0.attribute((String)null, var3, var5);
   }

   private static void serializeTypedElement(XmlSerializer var0, TypedElement var1, Hashtable var2) throws IOException, ParseException {
      String var3 = var1.getLabel();
      byte var4 = var1.getType();
      boolean var5;
      if(var4 != -1) {
         var5 = true;
      } else {
         var5 = false;
      }

      var1.validate();
      if(var3 != null) {
         String var6 = XmlNametable.LABEL;
         var0.attribute((String)null, var6, var3);
      }

      if(var5) {
         serializeRelation(var0, var4, var2);
      }
   }

   private static boolean shouldSerialize(TypedElement var0, String var1) {
      boolean var2 = true;
      if(var0.getType() == -1 && StringUtils.isEmptyOrWhitespace(var0.getLabel()) && StringUtils.isEmptyOrWhitespace(var1)) {
         var2 = false;
      }

      return var2;
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
      String var4 = var3.getLinkPhotoHref();
      String var5 = var3.getLinkPhotoType();
      String var6 = var3.getLinkPhotoETag();
      String var8 = "http://schemas.google.com/contacts/2008/rel#photo";
      serializeLink(var1, var8, var4, var5, var6);
      Enumeration var12 = var3.getEmailAddresses().elements();

      while(var12.hasMoreElements()) {
         EmailAddress var13 = (EmailAddress)var12.nextElement();
         serialize(var1, var13);
      }

      Enumeration var16 = var3.getImAddresses().elements();

      while(var16.hasMoreElements()) {
         ImAddress var17 = (ImAddress)var16.nextElement();
         serialize(var1, var17);
      }

      Enumeration var20 = var3.getPhoneNumbers().elements();

      while(var20.hasMoreElements()) {
         PhoneNumber var21 = (PhoneNumber)var20.nextElement();
         serialize(var1, var21);
      }

      Enumeration var24 = var3.getPostalAddresses().elements();

      while(var24.hasMoreElements()) {
         StructuredPostalAddress var25 = (StructuredPostalAddress)var24.nextElement();
         serialize(var1, var25);
      }

      Enumeration var28 = var3.getOrganizations().elements();

      while(var28.hasMoreElements()) {
         Organization var29 = (Organization)var28.nextElement();
         serialize(var1, var29);
      }

      Enumeration var32 = var3.getExtendedProperties().elements();

      while(var32.hasMoreElements()) {
         ExtendedProperty var33 = (ExtendedProperty)var32.nextElement();
         serialize(var1, var33);
      }

      Enumeration var36 = var3.getGroups().elements();

      while(var36.hasMoreElements()) {
         GroupMembershipInfo var37 = (GroupMembershipInfo)var36.nextElement();
         serialize(var1, var37);
      }

      Enumeration var40 = var3.getCalendarLinks().elements();

      while(var40.hasMoreElements()) {
         CalendarLink var41 = (CalendarLink)var40.nextElement();
         serialize(var1, var41);
      }

      Enumeration var44 = var3.getEvents().elements();

      while(var44.hasMoreElements()) {
         Event var45 = (Event)var44.nextElement();
         serialize(var1, var45);
      }

      Enumeration var48 = var3.getWebSites().elements();

      while(var48.hasMoreElements()) {
         WebSite var49 = (WebSite)var48.nextElement();
         serialize(var1, var49);
      }

      Enumeration var52 = var3.getExternalIds().elements();

      while(var52.hasMoreElements()) {
         ExternalId var53 = (ExternalId)var52.nextElement();
         serialize(var1, var53);
      }

      Enumeration var56 = var3.getHobbies().elements();

      while(var56.hasMoreElements()) {
         String var57 = (String)var56.nextElement();
         serializeHobby(var1, var57);
      }

      Enumeration var60 = var3.getJots().elements();

      while(var60.hasMoreElements()) {
         Jot var61 = (Jot)var60.nextElement();
         serialize(var1, var61);
      }

      Enumeration var64 = var3.getLanguages().elements();

      while(var64.hasMoreElements()) {
         Language var65 = (Language)var64.nextElement();
         serialize(var1, var65);
      }

      Enumeration var68 = var3.getRelations().elements();

      while(var68.hasMoreElements()) {
         Relation var69 = (Relation)var68.nextElement();
         serialize(var1, var69);
      }

      Enumeration var72 = var3.getUserDefinedFields().elements();

      while(var72.hasMoreElements()) {
         UserDefinedField var73 = (UserDefinedField)var72.nextElement();
         serialize(var1, var73);
      }

      Enumeration var76 = var3.getSipAddresses().elements();

      while(var76.hasMoreElements()) {
         SipAddress var77 = (SipAddress)var76.nextElement();
         serialize(var1, var77);
      }

      String var80 = var3.getBirthday();
      serializeBirthday(var1, var80);
      String var83 = var3.getDirectoryServer();
      String var84 = XmlNametable.GC_DIRECTORYSERVER;
      serializeElement(var1, var83, var84);
      String var88 = var3.getGender();
      serializeGenderElement(var1, var88);
      String var91 = var3.getInitials();
      String var92 = XmlNametable.GC_INITIALS;
      serializeElement(var1, var91, var92);
      String var96 = var3.getMaidenName();
      String var97 = XmlNametable.GC_MAIDENNAME;
      serializeElement(var1, var96, var97);
      String var101 = var3.getMileage();
      String var102 = XmlNametable.GC_MILEAGE;
      serializeElement(var1, var101, var102);
      String var106 = var3.getNickname();
      String var107 = XmlNametable.GC_NICKNAME;
      serializeElement(var1, var106, var107);
      String var111 = var3.getOccupation();
      String var112 = XmlNametable.GC_OCCUPATION;
      serializeElement(var1, var111, var112);
      String var116 = var3.getShortName();
      String var117 = XmlNametable.GC_SHORTNAME;
      serializeElement(var1, var116, var117);
      String var121 = var3.getSubject();
      String var122 = XmlNametable.GC_SUBJECT;
      serializeElement(var1, var121, var122);
      String var126 = var3.getBillingInformation();
      String var127 = XmlNametable.GC_BILLINGINFO;
      serializeElement(var1, var126, var127);
      byte var131 = var3.getPriority();
      String var132 = XmlNametable.GC_PRIORITY;
      Hashtable var133 = XmlContactsGDataParser.TYPE_TO_REL_PRIORITY;
      serializeElement(var1, var131, var132, var133);
      byte var138 = var3.getSensitivity();
      String var139 = XmlNametable.GC_SENSITIVITY;
      Hashtable var140 = XmlContactsGDataParser.TYPE_TO_REL_SENSITIVITY;
      serializeElement(var1, var138, var139, var140);
      Name var145 = var3.getName();
      serializeName(var1, var145);
   }
}
