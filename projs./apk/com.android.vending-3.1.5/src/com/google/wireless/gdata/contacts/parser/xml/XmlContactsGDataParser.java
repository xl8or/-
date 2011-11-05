package com.google.wireless.gdata.contacts.parser.xml;

import com.google.wireless.gdata.contacts.data.ContactEntry;
import com.google.wireless.gdata.contacts.data.ContactsElement;
import com.google.wireless.gdata.contacts.data.ContactsFeed;
import com.google.wireless.gdata.contacts.data.EmailAddress;
import com.google.wireless.gdata.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata.contacts.data.ImAddress;
import com.google.wireless.gdata.contacts.data.Organization;
import com.google.wireless.gdata.contacts.data.PhoneNumber;
import com.google.wireless.gdata.contacts.data.PostalAddress;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.ExtendedProperty;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlContactsGDataParser extends XmlGDataParser {

   private static final String GD_NAMESPACE = "http://schemas.google.com/g/2005#";
   public static final String IM_PROTOCOL_AIM = "http://schemas.google.com/g/2005#AIM";
   public static final String IM_PROTOCOL_GOOGLE_TALK = "http://schemas.google.com/g/2005#GOOGLE_TALK";
   public static final String IM_PROTOCOL_ICQ = "http://schemas.google.com/g/2005#ICQ";
   public static final String IM_PROTOCOL_JABBER = "http://schemas.google.com/g/2005#JABBER";
   public static final String IM_PROTOCOL_MSN = "http://schemas.google.com/g/2005#MSN";
   public static final String IM_PROTOCOL_QQ = "http://schemas.google.com/g/2005#QQ";
   public static final String IM_PROTOCOL_SKYPE = "http://schemas.google.com/g/2005#SKYPE";
   private static final Hashtable IM_PROTOCOL_STRING_TO_TYPE_MAP;
   public static final Hashtable IM_PROTOCOL_TYPE_TO_STRING_MAP;
   public static final String IM_PROTOCOL_YAHOO = "http://schemas.google.com/g/2005#YAHOO";
   public static final String LINK_REL_EDIT_PHOTO = "http://schemas.google.com/contacts/2008/rel#edit-photo";
   public static final String LINK_REL_PHOTO = "http://schemas.google.com/contacts/2008/rel#photo";
   public static final String NAMESPACE_CONTACTS = "gContact";
   public static final String NAMESPACE_CONTACTS_URI = "http://schemas.google.com/contact/2008";
   private static final Hashtable REL_TO_TYPE_EMAIL;
   private static final Hashtable REL_TO_TYPE_IM;
   private static final Hashtable REL_TO_TYPE_ORGANIZATION;
   private static final Hashtable REL_TO_TYPE_PHONE;
   private static final Hashtable REL_TO_TYPE_POSTAL;
   public static final String TYPESTRING_HOME = "http://schemas.google.com/g/2005#home";
   public static final String TYPESTRING_HOME_FAX = "http://schemas.google.com/g/2005#home_fax";
   public static final String TYPESTRING_MOBILE = "http://schemas.google.com/g/2005#mobile";
   public static final String TYPESTRING_OTHER = "http://schemas.google.com/g/2005#other";
   public static final String TYPESTRING_PAGER = "http://schemas.google.com/g/2005#pager";
   public static final String TYPESTRING_WORK = "http://schemas.google.com/g/2005#work";
   public static final String TYPESTRING_WORK_FAX = "http://schemas.google.com/g/2005#work_fax";
   public static final Hashtable TYPE_TO_REL_EMAIL;
   public static final Hashtable TYPE_TO_REL_IM;
   public static final Hashtable TYPE_TO_REL_ORGANIZATION;
   public static final Hashtable TYPE_TO_REL_PHONE;
   public static final Hashtable TYPE_TO_REL_POSTAL;


   static {
      Hashtable var0 = new Hashtable();
      Byte var1 = new Byte((byte)1);
      var0.put("http://schemas.google.com/g/2005#home", var1);
      Byte var3 = new Byte((byte)2);
      var0.put("http://schemas.google.com/g/2005#work", var3);
      Byte var5 = new Byte((byte)3);
      var0.put("http://schemas.google.com/g/2005#other", var5);
      Byte var7 = Byte.valueOf((byte)4);
      var0.put("http://schemas.google.com/g/2005#primary", var7);
      REL_TO_TYPE_EMAIL = var0;
      TYPE_TO_REL_EMAIL = swapMap(var0);
      Hashtable var9 = new Hashtable();
      Byte var10 = new Byte((byte)2);
      var9.put("http://schemas.google.com/g/2005#home", var10);
      Byte var12 = new Byte((byte)1);
      var9.put("http://schemas.google.com/g/2005#mobile", var12);
      Byte var14 = new Byte((byte)6);
      var9.put("http://schemas.google.com/g/2005#pager", var14);
      Byte var16 = new Byte((byte)3);
      var9.put("http://schemas.google.com/g/2005#work", var16);
      Byte var18 = new Byte((byte)5);
      var9.put("http://schemas.google.com/g/2005#home_fax", var18);
      Byte var20 = new Byte((byte)4);
      var9.put("http://schemas.google.com/g/2005#work_fax", var20);
      Byte var22 = new Byte((byte)7);
      var9.put("http://schemas.google.com/g/2005#other", var22);
      REL_TO_TYPE_PHONE = var9;
      TYPE_TO_REL_PHONE = swapMap(var9);
      Hashtable var24 = new Hashtable();
      Byte var25 = new Byte((byte)1);
      var24.put("http://schemas.google.com/g/2005#home", var25);
      Byte var27 = new Byte((byte)2);
      var24.put("http://schemas.google.com/g/2005#work", var27);
      Byte var29 = new Byte((byte)3);
      var24.put("http://schemas.google.com/g/2005#other", var29);
      REL_TO_TYPE_POSTAL = var24;
      TYPE_TO_REL_POSTAL = swapMap(var24);
      Hashtable var31 = new Hashtable();
      Byte var32 = new Byte((byte)1);
      var31.put("http://schemas.google.com/g/2005#home", var32);
      Byte var34 = new Byte((byte)2);
      var31.put("http://schemas.google.com/g/2005#work", var34);
      Byte var36 = new Byte((byte)3);
      var31.put("http://schemas.google.com/g/2005#other", var36);
      REL_TO_TYPE_IM = var31;
      TYPE_TO_REL_IM = swapMap(var31);
      Hashtable var38 = new Hashtable();
      Byte var39 = new Byte((byte)1);
      var38.put("http://schemas.google.com/g/2005#work", var39);
      Byte var41 = new Byte((byte)2);
      var38.put("http://schemas.google.com/g/2005#other", var41);
      REL_TO_TYPE_ORGANIZATION = var38;
      TYPE_TO_REL_ORGANIZATION = swapMap(var38);
      Hashtable var43 = new Hashtable();
      Byte var44 = new Byte((byte)2);
      var43.put("http://schemas.google.com/g/2005#AIM", var44);
      Byte var46 = new Byte((byte)3);
      var43.put("http://schemas.google.com/g/2005#MSN", var46);
      Byte var48 = new Byte((byte)4);
      var43.put("http://schemas.google.com/g/2005#YAHOO", var48);
      Byte var50 = new Byte((byte)5);
      var43.put("http://schemas.google.com/g/2005#SKYPE", var50);
      Byte var52 = new Byte((byte)6);
      var43.put("http://schemas.google.com/g/2005#QQ", var52);
      Byte var54 = new Byte((byte)7);
      var43.put("http://schemas.google.com/g/2005#GOOGLE_TALK", var54);
      Byte var56 = new Byte((byte)8);
      var43.put("http://schemas.google.com/g/2005#ICQ", var56);
      Byte var58 = new Byte((byte)9);
      var43.put("http://schemas.google.com/g/2005#JABBER", var58);
      IM_PROTOCOL_STRING_TO_TYPE_MAP = var43;
      IM_PROTOCOL_TYPE_TO_STRING_MAP = swapMap(var43);
   }

   public XmlContactsGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      super(var1, var2);
   }

   private static void handleOrganizationSubElement(Organization var0, XmlPullParser var1) throws XmlPullParserException, IOException {
      int var2 = var1.getDepth();

      while(true) {
         String var3 = XmlUtils.nextDirectChildTag(var1, var2);
         if(var3 == null) {
            return;
         }

         if("orgName".equals(var3)) {
            String var4 = XmlUtils.extractChildText(var1);
            var0.setName(var4);
         } else if("orgTitle".equals(var3)) {
            String var5 = XmlUtils.extractChildText(var1);
            var0.setTitle(var5);
         }
      }
   }

   private static void parseContactsElement(ContactsElement var0, XmlPullParser var1, Hashtable var2) throws XmlPullParserException {
      String var3 = var1.getAttributeValue((String)null, "rel");
      String var4 = var1.getAttributeValue((String)null, "label");
      if(var4 == null && var3 == null || var4 != null && var3 != null) {
         var3 = "http://schemas.google.com/g/2005#other";
      }

      if(var3 != null) {
         String var5 = var3.toLowerCase();
         Object var6 = var2.get(var5);
         if(var6 == null) {
            String var7 = "unknown rel, " + var3;
            throw new XmlPullParserException(var7);
         }

         byte var8 = ((Byte)var6).byteValue();
         var0.setType(var8);
      }

      var0.setLabel(var4);
      String var9 = var1.getAttributeValue((String)null, "primary");
      boolean var10 = "true".equals(var9);
      var0.setIsPrimary(var10);
   }

   private void parseExtendedProperty(ExtendedProperty var1) throws IOException, XmlPullParserException {
      XmlPullParser var2 = this.getParser();
      String var3 = var2.getAttributeValue((String)null, "name");
      var1.setName(var3);
      String var4 = var2.getAttributeValue((String)null, "value");
      var1.setValue(var4);
      String var5 = XmlUtils.extractFirstChildTextIgnoreRest(var2);
      var1.setXmlBlob(var5);
   }

   private static Hashtable swapMap(Hashtable var0) {
      Hashtable var1 = new Hashtable();
      Enumeration var2 = var0.keys();

      while(var2.hasMoreElements()) {
         Object var3 = var2.nextElement();
         Object var4 = var0.get(var3);
         if(var1.containsKey(var4)) {
            String var5 = "value " + var4 + " was already encountered";
            throw new IllegalArgumentException(var5);
         }

         var1.put(var4, var3);
      }

      return var1;
   }

   protected Entry createEntry() {
      return new ContactEntry();
   }

   protected Feed createFeed() {
      return new ContactsFeed();
   }

   protected void handleExtraElementInEntry(Entry var1) throws XmlPullParserException, IOException {
      XmlPullParser var2 = this.getParser();
      if(!(var1 instanceof ContactEntry)) {
         throw new IllegalArgumentException("Expected ContactEntry!");
      } else {
         ContactEntry var3 = (ContactEntry)var1;
         String var4 = var2.getName();
         if("email".equals(var4)) {
            EmailAddress var5 = new EmailAddress();
            Hashtable var6 = REL_TO_TYPE_EMAIL;
            parseContactsElement(var5, var2, var6);
            if(var5.getType() == 4) {
               var5.setType((byte)3);
               var5.setIsPrimary((boolean)1);
               var5.setLabel((String)null);
            }

            String var7 = var2.getAttributeValue((String)null, "address");
            var5.setAddress(var7);
            var3.addEmailAddress(var5);
         } else if("deleted".equals(var4)) {
            var3.setDeleted((boolean)1);
         } else if("im".equals(var4)) {
            ImAddress var8 = new ImAddress();
            Hashtable var9 = REL_TO_TYPE_IM;
            parseContactsElement(var8, var2, var9);
            String var10 = var2.getAttributeValue((String)null, "address");
            var8.setAddress(var10);
            String var11 = var2.getAttributeValue((String)null, "label");
            var8.setLabel(var11);
            String var12 = var2.getAttributeValue((String)null, "protocol");
            if(var12 == null) {
               var8.setProtocolPredefined((byte)10);
               var8.setProtocolCustom((String)null);
            } else {
               Byte var13 = (Byte)IM_PROTOCOL_STRING_TO_TYPE_MAP.get(var12);
               if(var13 == null) {
                  var8.setProtocolPredefined((byte)1);
                  var8.setProtocolCustom(var12);
               } else {
                  byte var14 = var13.byteValue();
                  var8.setProtocolPredefined(var14);
                  var8.setProtocolCustom((String)null);
               }
            }

            var3.addImAddress(var8);
         } else if("postalAddress".equals(var4)) {
            PostalAddress var15 = new PostalAddress();
            Hashtable var16 = REL_TO_TYPE_POSTAL;
            parseContactsElement(var15, var2, var16);
            String var17 = XmlUtils.extractChildText(var2);
            var15.setValue(var17);
            var3.addPostalAddress(var15);
         } else if("phoneNumber".equals(var4)) {
            PhoneNumber var18 = new PhoneNumber();
            Hashtable var19 = REL_TO_TYPE_PHONE;
            parseContactsElement(var18, var2, var19);
            String var20 = XmlUtils.extractChildText(var2);
            var18.setPhoneNumber(var20);
            var3.addPhoneNumber(var18);
         } else if("organization".equals(var4)) {
            Organization var21 = new Organization();
            Hashtable var22 = REL_TO_TYPE_ORGANIZATION;
            parseContactsElement(var21, var2, var22);
            handleOrganizationSubElement(var21, var2);
            var3.addOrganization(var21);
         } else if("extendedProperty".equals(var4)) {
            ExtendedProperty var23 = new ExtendedProperty();
            this.parseExtendedProperty(var23);
            var3.addExtendedProperty(var23);
         } else if("groupMembershipInfo".equals(var4)) {
            GroupMembershipInfo var24 = new GroupMembershipInfo();
            String var25 = var2.getAttributeValue((String)null, "href");
            var24.setGroup(var25);
            String var26 = "deleted";
            String var27 = var2.getAttributeValue((String)null, var26);
            boolean var28 = "true".equals(var27);
            var24.setDeleted(var28);
            var3.addGroup(var24);
         } else if("yomiName".equals(var4)) {
            String var29 = XmlUtils.extractChildText(var2);
            var3.setYomiName(var29);
         }
      }
   }

   protected void handleExtraLinkInEntry(String var1, String var2, String var3, Entry var4) throws XmlPullParserException, IOException {
      if("http://schemas.google.com/contacts/2008/rel#photo".equals(var1)) {
         ((ContactEntry)var4).setLinkPhoto(var3, var2);
      } else if("http://schemas.google.com/contacts/2008/rel#edit-photo".equals(var1)) {
         ((ContactEntry)var4).setLinkEditPhoto(var3, var2);
      }
   }
}
