package com.google.wireless.gdata2.contacts.parser.xml;

import com.google.wireless.gdata2.contacts.data.CalendarLink;
import com.google.wireless.gdata2.contacts.data.ContactEntry;
import com.google.wireless.gdata2.contacts.data.ContactsElement;
import com.google.wireless.gdata2.contacts.data.ContactsFeed;
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
import com.google.wireless.gdata2.contacts.parser.xml.XmlNametable;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.ExtendedProperty;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.data.XmlUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;
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
   public static final String IM_PROTOCOL_NETMEETING = "http://schemas.google.com/g/2005#netmeeting";
   public static final String IM_PROTOCOL_QQ = "http://schemas.google.com/g/2005#QQ";
   public static final String IM_PROTOCOL_SKYPE = "http://schemas.google.com/g/2005#SKYPE";
   private static final Hashtable IM_PROTOCOL_STRING_TO_TYPE_MAP;
   public static final Hashtable IM_PROTOCOL_TYPE_TO_STRING_MAP;
   public static final String IM_PROTOCOL_YAHOO = "http://schemas.google.com/g/2005#YAHOO";
   public static final String LINK_REL_PHOTO = "http://schemas.google.com/contacts/2008/rel#photo";
   public static final String NAMESPACE_CONTACTS = "gContact";
   public static final String NAMESPACE_CONTACTS_URI = "http://schemas.google.com/contact/2008";
   private static final Hashtable REL_TO_TYPE_CALENDARLINK;
   private static final Hashtable REL_TO_TYPE_EMAIL;
   private static final Hashtable REL_TO_TYPE_EVENT;
   private static final Hashtable REL_TO_TYPE_EXTERNALID;
   private static final Hashtable REL_TO_TYPE_IM;
   private static final Hashtable REL_TO_TYPE_JOT;
   private static final Hashtable REL_TO_TYPE_ORGANIZATION;
   private static final Hashtable REL_TO_TYPE_PHONE;
   private static final Hashtable REL_TO_TYPE_POSTAL;
   private static final Hashtable REL_TO_TYPE_PRIORITY;
   private static final Hashtable REL_TO_TYPE_RELATION;
   private static final Hashtable REL_TO_TYPE_SENSITIVITY;
   private static final Hashtable REL_TO_TYPE_SIP;
   private static final Hashtable REL_TO_TYPE_WEBSITE;
   public static final String TYPESTRING_ASSISTANT = "http://schemas.google.com/g/2005#assistant";
   public static final String TYPESTRING_CALENDARLINK_FREEBUSY = "free-busy";
   public static final String TYPESTRING_CALENDARLINK_HOME = "home";
   public static final String TYPESTRING_CALENDARLINK_WORK = "work";
   public static final String TYPESTRING_CALLBACK = "http://schemas.google.com/g/2005#callback";
   public static final String TYPESTRING_CAR = "http://schemas.google.com/g/2005#car";
   public static final String TYPESTRING_COMPANY_MAIN = "http://schemas.google.com/g/2005#company_main";
   public static final String TYPESTRING_EVENT_ANNIVERARY = "anniversary";
   public static final String TYPESTRING_EVENT_OTHER = "other";
   public static final String TYPESTRING_EXTERNALID_ACCOUNT = "account";
   public static final String TYPESTRING_EXTERNALID_CUSTOMER = "customer";
   public static final String TYPESTRING_EXTERNALID_NETWORK = "network";
   public static final String TYPESTRING_EXTERNALID_ORGANIZATION = "organization";
   public static final String TYPESTRING_HOME = "http://schemas.google.com/g/2005#home";
   public static final String TYPESTRING_HOME_FAX = "http://schemas.google.com/g/2005#home_fax";
   public static final String TYPESTRING_ISDN = "http://schemas.google.com/g/2005#isdn";
   public static final String TYPESTRING_JOT_HOME = "home";
   public static final String TYPESTRING_JOT_KEYWORDS = "keywords";
   public static final String TYPESTRING_JOT_OTHER = "other";
   public static final String TYPESTRING_JOT_USER = "user";
   public static final String TYPESTRING_JOT_WORK = "work";
   public static final String TYPESTRING_MAIN = "http://schemas.google.com/g/2005#main";
   public static final String TYPESTRING_MOBILE = "http://schemas.google.com/g/2005#mobile";
   public static final String TYPESTRING_OTHER = "http://schemas.google.com/g/2005#other";
   public static final String TYPESTRING_OTHER_FAX = "http://schemas.google.com/g/2005#other_fax";
   public static final String TYPESTRING_PAGER = "http://schemas.google.com/g/2005#pager";
   public static final String TYPESTRING_PRIORITY_HIGH = "high";
   public static final String TYPESTRING_PRIORITY_LOW = "low";
   public static final String TYPESTRING_PRIORITY_NORMAL = "normal";
   public static final String TYPESTRING_RADIO = "http://schemas.google.com/g/2005#radio";
   public static final String TYPESTRING_RELATION_ASSISTANT = "assistant";
   public static final String TYPESTRING_RELATION_BROTHER = "brother";
   public static final String TYPESTRING_RELATION_CHILD = "child";
   public static final String TYPESTRING_RELATION_DOMESTICPARTNER = "domestic-partner";
   public static final String TYPESTRING_RELATION_FATHER = "father";
   public static final String TYPESTRING_RELATION_FRIEND = "friend";
   public static final String TYPESTRING_RELATION_MANAGER = "manager";
   public static final String TYPESTRING_RELATION_MOTHER = "mother";
   public static final String TYPESTRING_RELATION_PARENT = "parent";
   public static final String TYPESTRING_RELATION_PARTNER = "partner";
   public static final String TYPESTRING_RELATION_REFERREDBY = "referred-by";
   public static final String TYPESTRING_RELATION_RELATIVE = "relative";
   public static final String TYPESTRING_RELATION_SISTER = "sister";
   public static final String TYPESTRING_RELATION_SPOUSE = "spouse";
   public static final String TYPESTRING_SENSITIVITY_CONFIDENTIAL = "confidential";
   public static final String TYPESTRING_SENSITIVITY_NORMAL = "normal";
   public static final String TYPESTRING_SENSITIVITY_PERSONAL = "personal";
   public static final String TYPESTRING_SENSITIVITY_PRIVATE = "private";
   public static final String TYPESTRING_TELEX = "http://schemas.google.com/g/2005#telex";
   public static final String TYPESTRING_TTY_TDD = "http://schemas.google.com/g/2005#tty_tdd";
   public static final String TYPESTRING_WEBSITE_BLOG = "blog";
   public static final String TYPESTRING_WEBSITE_FTP = "ftp";
   public static final String TYPESTRING_WEBSITE_HOME = "home";
   public static final String TYPESTRING_WEBSITE_HOMEPAGE = "home-page";
   public static final String TYPESTRING_WEBSITE_OTHER = "other";
   public static final String TYPESTRING_WEBSITE_PROFILE = "profile";
   public static final String TYPESTRING_WEBSITE_WORK = "work";
   public static final String TYPESTRING_WORK = "http://schemas.google.com/g/2005#work";
   public static final String TYPESTRING_WORK_FAX = "http://schemas.google.com/g/2005#work_fax";
   public static final String TYPESTRING_WORK_MOBILE = "http://schemas.google.com/g/2005#work_mobile";
   public static final String TYPESTRING_WORK_PAGER = "http://schemas.google.com/g/2005#work_pager";
   public static final Hashtable TYPE_TO_REL_CALENDARLINK;
   public static final Hashtable TYPE_TO_REL_EMAIL;
   public static final Hashtable TYPE_TO_REL_EVENT;
   public static final Hashtable TYPE_TO_REL_EXTERNALID;
   public static final Hashtable TYPE_TO_REL_IM;
   public static final Hashtable TYPE_TO_REL_JOT;
   public static final Hashtable TYPE_TO_REL_ORGANIZATION;
   public static final Hashtable TYPE_TO_REL_PHONE;
   public static final Hashtable TYPE_TO_REL_POSTAL;
   public static final Hashtable TYPE_TO_REL_PRIORITY;
   public static final Hashtable TYPE_TO_REL_RELATION;
   public static final Hashtable TYPE_TO_REL_SENSITIVITY;
   public static final Hashtable TYPE_TO_REL_SIP;
   public static final Hashtable TYPE_TO_REL_WEBSITE;


   static {
      Hashtable var0 = new Hashtable();
      Byte var1 = new Byte((byte)1);
      var0.put("http://schemas.google.com/g/2005#home", var1);
      Byte var3 = new Byte((byte)2);
      var0.put("http://schemas.google.com/g/2005#work", var3);
      Byte var5 = new Byte((byte)3);
      var0.put("http://schemas.google.com/g/2005#other", var5);
      REL_TO_TYPE_EMAIL = var0;
      TYPE_TO_REL_EMAIL = swapMap(var0);
      Hashtable var7 = new Hashtable();
      Byte var8 = new Byte((byte)2);
      var7.put("http://schemas.google.com/g/2005#home", var8);
      Byte var10 = new Byte((byte)1);
      var7.put("http://schemas.google.com/g/2005#mobile", var10);
      Byte var12 = new Byte((byte)6);
      var7.put("http://schemas.google.com/g/2005#pager", var12);
      Byte var14 = new Byte((byte)3);
      var7.put("http://schemas.google.com/g/2005#work", var14);
      Byte var16 = new Byte((byte)5);
      var7.put("http://schemas.google.com/g/2005#home_fax", var16);
      Byte var18 = new Byte((byte)4);
      var7.put("http://schemas.google.com/g/2005#work_fax", var18);
      Byte var20 = new Byte((byte)7);
      var7.put("http://schemas.google.com/g/2005#assistant", var20);
      Byte var22 = new Byte((byte)8);
      var7.put("http://schemas.google.com/g/2005#callback", var22);
      Byte var24 = new Byte((byte)9);
      var7.put("http://schemas.google.com/g/2005#car", var24);
      Byte var26 = new Byte((byte)10);
      var7.put("http://schemas.google.com/g/2005#company_main", var26);
      Byte var28 = new Byte((byte)11);
      var7.put("http://schemas.google.com/g/2005#isdn", var28);
      Byte var30 = new Byte((byte)12);
      var7.put("http://schemas.google.com/g/2005#main", var30);
      Byte var32 = new Byte((byte)13);
      var7.put("http://schemas.google.com/g/2005#other_fax", var32);
      Byte var34 = new Byte((byte)14);
      var7.put("http://schemas.google.com/g/2005#radio", var34);
      Byte var36 = new Byte((byte)15);
      var7.put("http://schemas.google.com/g/2005#telex", var36);
      Byte var38 = new Byte((byte)16);
      var7.put("http://schemas.google.com/g/2005#tty_tdd", var38);
      Byte var40 = new Byte((byte)17);
      var7.put("http://schemas.google.com/g/2005#work_mobile", var40);
      Byte var42 = new Byte((byte)18);
      var7.put("http://schemas.google.com/g/2005#work_pager", var42);
      Byte var44 = new Byte((byte)19);
      var7.put("http://schemas.google.com/g/2005#other", var44);
      REL_TO_TYPE_PHONE = var7;
      TYPE_TO_REL_PHONE = swapMap(var7);
      Hashtable var46 = new Hashtable();
      Byte var47 = new Byte((byte)1);
      var46.put("http://schemas.google.com/g/2005#home", var47);
      Byte var49 = new Byte((byte)2);
      var46.put("http://schemas.google.com/g/2005#work", var49);
      Byte var51 = new Byte((byte)3);
      var46.put("http://schemas.google.com/g/2005#other", var51);
      REL_TO_TYPE_POSTAL = var46;
      TYPE_TO_REL_POSTAL = swapMap(var46);
      Hashtable var53 = new Hashtable();
      Byte var54 = new Byte((byte)1);
      var53.put("http://schemas.google.com/g/2005#home", var54);
      Byte var56 = new Byte((byte)2);
      var53.put("http://schemas.google.com/g/2005#work", var56);
      Byte var58 = new Byte((byte)3);
      var53.put("http://schemas.google.com/g/2005#other", var58);
      REL_TO_TYPE_IM = var53;
      TYPE_TO_REL_IM = swapMap(var53);
      Hashtable var60 = new Hashtable();
      Byte var61 = new Byte((byte)1);
      var60.put("http://schemas.google.com/g/2005#work", var61);
      Byte var63 = new Byte((byte)2);
      var60.put("http://schemas.google.com/g/2005#other", var63);
      REL_TO_TYPE_ORGANIZATION = var60;
      TYPE_TO_REL_ORGANIZATION = swapMap(var60);
      Hashtable var65 = new Hashtable();
      Byte var66 = new Byte((byte)2);
      var65.put("http://schemas.google.com/g/2005#AIM", var66);
      Byte var68 = new Byte((byte)3);
      var65.put("http://schemas.google.com/g/2005#MSN", var68);
      Byte var70 = new Byte((byte)4);
      var65.put("http://schemas.google.com/g/2005#YAHOO", var70);
      Byte var72 = new Byte((byte)5);
      var65.put("http://schemas.google.com/g/2005#SKYPE", var72);
      Byte var74 = new Byte((byte)6);
      var65.put("http://schemas.google.com/g/2005#QQ", var74);
      Byte var76 = new Byte((byte)7);
      var65.put("http://schemas.google.com/g/2005#GOOGLE_TALK", var76);
      Byte var78 = new Byte((byte)8);
      var65.put("http://schemas.google.com/g/2005#ICQ", var78);
      Byte var80 = new Byte((byte)9);
      var65.put("http://schemas.google.com/g/2005#JABBER", var80);
      Byte var82 = new Byte((byte)10);
      var65.put("http://schemas.google.com/g/2005#netmeeting", var82);
      IM_PROTOCOL_STRING_TO_TYPE_MAP = var65;
      IM_PROTOCOL_TYPE_TO_STRING_MAP = swapMap(var65);
      Hashtable var84 = new Hashtable();
      Byte var85 = new Byte((byte)1);
      var84.put("home", var85);
      Byte var87 = new Byte((byte)2);
      var84.put("work", var87);
      Byte var89 = new Byte((byte)3);
      var84.put("free-busy", var89);
      REL_TO_TYPE_CALENDARLINK = var84;
      TYPE_TO_REL_CALENDARLINK = swapMap(var84);
      Hashtable var91 = new Hashtable();
      Byte var92 = new Byte((byte)1);
      var91.put("anniversary", var92);
      Byte var94 = new Byte((byte)2);
      var91.put("other", var94);
      REL_TO_TYPE_EVENT = var91;
      TYPE_TO_REL_EVENT = swapMap(var91);
      Hashtable var96 = new Hashtable();
      Byte var97 = new Byte((byte)1);
      var96.put("account", var97);
      Byte var99 = new Byte((byte)2);
      var96.put("customer", var99);
      Byte var101 = new Byte((byte)3);
      var96.put("network", var101);
      Byte var103 = new Byte((byte)4);
      var96.put("organization", var103);
      REL_TO_TYPE_EXTERNALID = var96;
      TYPE_TO_REL_EXTERNALID = swapMap(var96);
      Hashtable var105 = new Hashtable();
      Byte var106 = new Byte((byte)1);
      var105.put("home", var106);
      Byte var108 = new Byte((byte)3);
      var105.put("keywords", var108);
      Byte var110 = new Byte((byte)5);
      var105.put("other", var110);
      Byte var112 = new Byte((byte)4);
      var105.put("user", var112);
      Byte var114 = new Byte((byte)2);
      var105.put("work", var114);
      REL_TO_TYPE_JOT = var105;
      TYPE_TO_REL_JOT = swapMap(var105);
      Hashtable var116 = new Hashtable();
      Byte var117 = new Byte((byte)1);
      var116.put("high", var117);
      Byte var119 = new Byte((byte)2);
      var116.put("normal", var119);
      Byte var121 = new Byte((byte)3);
      var116.put("low", var121);
      REL_TO_TYPE_PRIORITY = var116;
      TYPE_TO_REL_PRIORITY = swapMap(var116);
      Hashtable var123 = new Hashtable();
      Byte var124 = new Byte((byte)1);
      var123.put("assistant", var124);
      Byte var126 = new Byte((byte)2);
      var123.put("brother", var126);
      Byte var128 = new Byte((byte)3);
      var123.put("child", var128);
      Byte var130 = new Byte((byte)4);
      var123.put("domestic-partner", var130);
      Byte var132 = new Byte((byte)5);
      var123.put("father", var132);
      Byte var134 = new Byte((byte)6);
      var123.put("friend", var134);
      Byte var136 = new Byte((byte)7);
      var123.put("manager", var136);
      Byte var138 = new Byte((byte)8);
      var123.put("mother", var138);
      Byte var140 = new Byte((byte)9);
      var123.put("parent", var140);
      Byte var142 = new Byte((byte)10);
      var123.put("partner", var142);
      Byte var144 = new Byte((byte)11);
      var123.put("referred-by", var144);
      Byte var146 = new Byte((byte)12);
      var123.put("relative", var146);
      Byte var148 = new Byte((byte)13);
      var123.put("sister", var148);
      Byte var150 = new Byte((byte)14);
      var123.put("spouse", var150);
      REL_TO_TYPE_RELATION = var123;
      TYPE_TO_REL_RELATION = swapMap(var123);
      Hashtable var152 = new Hashtable();
      Byte var153 = new Byte((byte)1);
      var152.put("confidential", var153);
      Byte var155 = new Byte((byte)2);
      var152.put("normal", var155);
      Byte var157 = new Byte((byte)3);
      var152.put("personal", var157);
      Byte var159 = new Byte((byte)4);
      var152.put("private", var159);
      REL_TO_TYPE_SENSITIVITY = var152;
      TYPE_TO_REL_SENSITIVITY = swapMap(var152);
      Hashtable var161 = new Hashtable();
      Byte var162 = new Byte((byte)2);
      var161.put("blog", var162);
      Byte var164 = new Byte((byte)1);
      var161.put("home-page", var164);
      Byte var166 = new Byte((byte)3);
      var161.put("profile", var166);
      Byte var168 = new Byte((byte)4);
      var161.put("home", var168);
      Byte var170 = new Byte((byte)5);
      var161.put("work", var170);
      Byte var172 = new Byte((byte)6);
      var161.put("other", var172);
      Byte var174 = new Byte((byte)7);
      var161.put("ftp", var174);
      REL_TO_TYPE_WEBSITE = var161;
      TYPE_TO_REL_WEBSITE = swapMap(var161);
      Hashtable var176 = new Hashtable();
      Byte var177 = new Byte((byte)1);
      var176.put("http://schemas.google.com/g/2005#home", var177);
      Byte var179 = new Byte((byte)2);
      var176.put("http://schemas.google.com/g/2005#work", var179);
      Byte var181 = new Byte((byte)3);
      var176.put("http://schemas.google.com/g/2005#other", var181);
      REL_TO_TYPE_SIP = var176;
      TYPE_TO_REL_SIP = swapMap(var176);
   }

   public XmlContactsGDataParser(InputStream var1, XmlPullParser var2) throws ParseException {
      super(var1, var2);
   }

   private static void handleEventSubElement(Event var0, XmlPullParser var1) throws XmlPullParserException, IOException {
      int var2 = var1.getDepth();

      while(true) {
         String var3 = XmlUtils.nextDirectChildTag(var1, var2);
         if(var3 == null) {
            return;
         }

         if(XmlNametable.GD_WHEN.equals(var3)) {
            String var4 = XmlNametable.STARTTIME;
            String var5 = var1.getAttributeValue((String)null, var4);
            var0.setStartDate(var5);
         }
      }
   }

   private static void handleNameSubElement(Name var0, XmlPullParser var1) throws XmlPullParserException, IOException {
      int var2 = var1.getDepth();

      while(true) {
         String var3 = XmlUtils.nextDirectChildTag(var1, var2);
         if(var3 == null) {
            return;
         }

         if(XmlNametable.GD_NAME_GIVENNAME.equals(var3)) {
            String var4 = XmlNametable.GD_NAME_YOMI;
            String var5 = var1.getAttributeValue((String)null, var4);
            var0.setGivenNameYomi(var5);
            String var6 = XmlUtils.extractChildText(var1);
            var0.setGivenName(var6);
         } else if(XmlNametable.GD_NAME_ADDITIONALNAME.equals(var3)) {
            String var7 = XmlNametable.GD_NAME_YOMI;
            String var8 = var1.getAttributeValue((String)null, var7);
            var0.setAdditionalNameYomi(var8);
            String var9 = XmlUtils.extractChildText(var1);
            var0.setAdditionalName(var9);
         } else if(XmlNametable.GD_NAME_FAMILYNAME.equals(var3)) {
            String var10 = XmlNametable.GD_NAME_YOMI;
            String var11 = var1.getAttributeValue((String)null, var10);
            var0.setFamilyNameYomi(var11);
            String var12 = XmlUtils.extractChildText(var1);
            var0.setFamilyName(var12);
         } else if(XmlNametable.GD_NAME_PREFIX.equals(var3)) {
            String var13 = XmlUtils.extractChildText(var1);
            var0.setNamePrefix(var13);
         } else if(XmlNametable.GD_NAME_SUFFIX.equals(var3)) {
            String var14 = XmlUtils.extractChildText(var1);
            var0.setNameSuffix(var14);
         } else if(XmlNametable.GD_NAME_FULLNAME.equals(var3)) {
            String var15 = XmlNametable.GD_NAME_YOMI;
            String var16 = var1.getAttributeValue((String)null, var15);
            var0.setFullNameYomi(var16);
            String var17 = XmlUtils.extractChildText(var1);
            var0.setFullName(var17);
         }
      }
   }

   private static void handleOrganizationSubElement(Organization var0, XmlPullParser var1) throws XmlPullParserException, IOException {
      int var2 = var1.getDepth();

      while(true) {
         String var3 = XmlUtils.nextDirectChildTag(var1, var2);
         if(var3 == null) {
            return;
         }

         if(XmlNametable.GD_ORG_NAME.equals(var3)) {
            String var4 = XmlUtils.extractChildText(var1);
            var0.setName(var4);
         } else if(XmlNametable.GD_ORG_TITLE.equals(var3)) {
            String var5 = XmlUtils.extractChildText(var1);
            var0.setTitle(var5);
         } else if(XmlNametable.GD_ORG_DEPARTMENT.equals(var3)) {
            String var6 = XmlUtils.extractChildText(var1);
            var0.setOrgDepartment(var6);
         } else if(XmlNametable.GD_ORG_JOBDESC.equals(var3)) {
            String var7 = XmlUtils.extractChildText(var1);
            var0.setOrgJobDescription(var7);
         } else if(XmlNametable.GD_ORG_SYMBOL.equals(var3)) {
            String var8 = XmlUtils.extractChildText(var1);
            var0.setOrgSymbol(var8);
         } else if(XmlNametable.GD_WHERE.equals(var3)) {
            String var9 = XmlNametable.VALUESTRING;
            String var10 = var1.getAttributeValue((String)null, var9);
            var0.setWhere(var10);
         }
      }
   }

   private static void handleStructuredPostalAddressSubElement(StructuredPostalAddress var0, XmlPullParser var1) throws XmlPullParserException, IOException {
      int var2 = var1.getDepth();

      while(true) {
         String var3 = XmlUtils.nextDirectChildTag(var1, var2);
         if(var3 == null) {
            return;
         }

         if(XmlNametable.GD_SPA_STREET.equals(var3)) {
            String var4 = XmlUtils.extractChildText(var1);
            var0.setStreet(var4);
         } else if(XmlNametable.GD_SPA_POBOX.equals(var3)) {
            String var5 = XmlUtils.extractChildText(var1);
            var0.setPobox(var5);
         } else if(XmlNametable.GD_SPA_NEIGHBORHOOD.equals(var3)) {
            String var6 = XmlUtils.extractChildText(var1);
            var0.setNeighborhood(var6);
         } else if(XmlNametable.GD_SPA_CITY.equals(var3)) {
            String var7 = XmlUtils.extractChildText(var1);
            var0.setCity(var7);
         } else if(XmlNametable.GD_SPA_REGION.equals(var3)) {
            String var8 = XmlUtils.extractChildText(var1);
            var0.setRegion(var8);
         } else if(XmlNametable.GD_SPA_POSTCODE.equals(var3)) {
            String var9 = XmlUtils.extractChildText(var1);
            var0.setPostcode(var9);
         } else if(XmlNametable.GD_SPA_COUNTRY.equals(var3)) {
            String var10 = XmlUtils.extractChildText(var1);
            var0.setCountry(var10);
         } else if(XmlNametable.GD_SPA_FORMATTEDADDRESS.equals(var3)) {
            String var11 = XmlUtils.extractChildText(var1);
            var0.setFormattedAddress(var11);
         }
      }
   }

   private static void parseContactsElement(ContactsElement var0, XmlPullParser var1, Hashtable var2) throws XmlPullParserException {
      parseTypedElement(var0, var1, var2);
      String var3 = XmlNametable.PRIMARY;
      String var4 = var1.getAttributeValue((String)null, var3);
      boolean var5 = "true".equals(var4);
      var0.setIsPrimary(var5);
   }

   private void parseExtendedProperty(ExtendedProperty var1) throws IOException, XmlPullParserException {
      XmlPullParser var2 = this.getParser();
      String var3 = XmlNametable.GD_NAME;
      String var4 = var2.getAttributeValue((String)null, var3);
      var1.setName(var4);
      String var5 = XmlNametable.VALUE;
      String var6 = var2.getAttributeValue((String)null, var5);
      var1.setValue(var6);
      String var7 = XmlUtils.extractFirstChildTextIgnoreRest(var2);
      var1.setXmlBlob(var7);
   }

   private static void parseTypedElement(TypedElement var0, XmlPullParser var1, Hashtable var2) throws XmlPullParserException {
      String var3 = XmlNametable.REL;
      String var4 = var1.getAttributeValue((String)null, var3);
      String var5 = XmlNametable.LABEL;
      String var6 = var1.getAttributeValue((String)null, var5);
      if(var6 == null && var4 == null || var6 != null && var4 != null) {
         var4 = "http://schemas.google.com/g/2005#other";
      }

      if(var4 != null) {
         byte var7 = relToType(var4, var2);
         var0.setType(var7);
      }

      var0.setLabel(var6);
   }

   private static byte relToType(String var0, Hashtable var1) throws XmlPullParserException {
      byte var5;
      if(var0 != null) {
         String var2 = var0.toLowerCase();
         Object var3 = var1.get(var2);
         if(var3 == null) {
            String var4 = "unknown rel, " + var0;
            throw new XmlPullParserException(var4);
         }

         var5 = ((Byte)var3).byteValue();
      } else {
         var5 = -1;
      }

      return var5;
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
         String var5 = var2.getNamespace();
         if("http://schemas.google.com/g/2005".equals(var5)) {
            if(XmlNametable.GD_EMAIL.equals(var4)) {
               EmailAddress var6 = new EmailAddress();
               Hashtable var7 = REL_TO_TYPE_EMAIL;
               parseContactsElement(var6, var2, var7);
               String var10 = XmlNametable.GD_EMAIL_DISPLAYNAME;
               Object var12 = null;
               String var14 = var2.getAttributeValue((String)var12, var10);
               var6.setDisplayName(var14);
               String var15 = XmlNametable.GD_ADDRESS;
               Object var17 = null;
               String var19 = var2.getAttributeValue((String)var17, var15);
               var6.setAddress(var19);
               String var20 = XmlNametable.GC_LINKSTO;
               Object var22 = null;
               String var24 = var2.getAttributeValue((String)var22, var20);
               var6.setLinksTo(var24);
               var3.addEmailAddress(var6);
            } else if(XmlNametable.GD_DELETED.equals(var4)) {
               byte var25 = 1;
               var3.setDeleted((boolean)var25);
            } else if(XmlNametable.GD_IM.equals(var4)) {
               ImAddress var26 = new ImAddress();
               Hashtable var27 = REL_TO_TYPE_IM;
               parseContactsElement(var26, var2, var27);
               String var30 = XmlNametable.GD_ADDRESS;
               Object var32 = null;
               String var34 = var2.getAttributeValue((String)var32, var30);
               var26.setAddress(var34);
               String var35 = XmlNametable.LABEL;
               Object var37 = null;
               String var39 = var2.getAttributeValue((String)var37, var35);
               var26.setLabel(var39);
               String var40 = XmlNametable.GD_PROTOCOL;
               Object var42 = null;
               String var44 = var2.getAttributeValue((String)var42, var40);
               if(var44 == null) {
                  byte var45 = 11;
                  var26.setProtocolPredefined(var45);
                  Object var46 = null;
                  var26.setProtocolCustom((String)var46);
               } else {
                  Hashtable var47 = IM_PROTOCOL_STRING_TO_TYPE_MAP;
                  Byte var49 = (Byte)var47.get(var44);
                  if(var49 == null) {
                     byte var50 = 1;
                     var26.setProtocolPredefined(var50);
                     var26.setProtocolCustom(var44);
                  } else {
                     byte var52 = var49.byteValue();
                     var26.setProtocolPredefined(var52);
                     Object var53 = null;
                     var26.setProtocolCustom((String)var53);
                  }
               }

               var3.addImAddress(var26);
            } else if(XmlNametable.GD_SPA.equals(var4)) {
               StructuredPostalAddress var54 = new StructuredPostalAddress();
               Hashtable var55 = REL_TO_TYPE_POSTAL;
               parseContactsElement(var54, var2, var55);
               handleStructuredPostalAddressSubElement(var54, var2);
               var3.addPostalAddress(var54);
            } else if(XmlNametable.GD_PHONENUMBER.equals(var4)) {
               PhoneNumber var62 = new PhoneNumber();
               Hashtable var63 = REL_TO_TYPE_PHONE;
               parseContactsElement(var62, var2, var63);
               String var67 = XmlUtils.extractChildText(var2);
               var62.setPhoneNumber(var67);
               String var70 = XmlNametable.GC_LINKSTO;
               Object var72 = null;
               String var74 = var2.getAttributeValue((String)var72, var70);
               var62.setLinksTo(var74);
               var3.addPhoneNumber(var62);
            } else if(XmlNametable.GD_ORGANIZATION.equals(var4)) {
               Organization var78 = new Organization();
               Hashtable var79 = REL_TO_TYPE_ORGANIZATION;
               parseContactsElement(var78, var2, var79);
               handleOrganizationSubElement(var78, var2);
               var3.addOrganization(var78);
            } else if(XmlNametable.GD_EXTENDEDPROPERTY.equals(var4)) {
               ExtendedProperty var84 = new ExtendedProperty();
               this.parseExtendedProperty(var84);
               var3.addExtendedProperty(var84);
            } else if(XmlNametable.GD_NAME.equals(var4)) {
               Name var85 = new Name();
               handleNameSubElement(var85, var2);
               var3.setName(var85);
            }
         } else if("http://schemas.google.com/contact/2008".equals(var5)) {
            if(XmlNametable.GC_GMI.equals(var4)) {
               GroupMembershipInfo var87 = new GroupMembershipInfo();
               String var88 = XmlNametable.HREF;
               Object var90 = null;
               String var92 = var2.getAttributeValue((String)var90, var88);
               var87.setGroup(var92);
               String var93 = XmlNametable.GD_DELETED;
               Object var95 = null;
               String var97 = var2.getAttributeValue((String)var95, var93);
               boolean var98 = "true".equals(var97);
               var87.setDeleted(var98);
               var3.addGroup(var87);
            } else if(XmlNametable.GC_BIRTHDAY.equals(var4)) {
               String var99 = XmlNametable.GD_WHEN;
               Object var101 = null;
               String var103 = var2.getAttributeValue((String)var101, var99);
               var3.setBirthday(var103);
            } else if(XmlNametable.GC_BILLINGINFO.equals(var4)) {
               String var104 = XmlUtils.extractChildText(var2);
               var3.setBillingInformation(var104);
            } else if(XmlNametable.GC_CALENDARLINK.equals(var4)) {
               CalendarLink var105 = new CalendarLink();
               Hashtable var106 = REL_TO_TYPE_CALENDARLINK;
               parseContactsElement(var105, var2, var106);
               String var109 = XmlNametable.HREF;
               Object var111 = null;
               String var113 = var2.getAttributeValue((String)var111, var109);
               var105.setHRef(var113);
               var3.addCalendarLink(var105);
            } else if(XmlNametable.GC_DIRECTORYSERVER.equals(var4)) {
               String var114 = XmlUtils.extractChildText(var2);
               var3.setDirectoryServer(var114);
            } else if("event".equals(var4)) {
               Event var115 = new Event();
               Hashtable var116 = REL_TO_TYPE_EVENT;
               parseTypedElement(var115, var2, var116);
               handleEventSubElement(var115, var2);
               var3.addEvent(var115);
            } else if(XmlNametable.GC_EXTERNALID.equals(var4)) {
               ExternalId var120 = new ExternalId();
               Hashtable var121 = REL_TO_TYPE_EXTERNALID;
               parseTypedElement(var120, var2, var121);
               String var124 = XmlNametable.VALUE;
               Object var126 = null;
               String var128 = var2.getAttributeValue((String)var126, var124);
               var120.setValue(var128);
               var3.addExternalId(var120);
            } else if(XmlNametable.GC_GENDER.equals(var4)) {
               String var129 = XmlNametable.VALUE;
               Object var131 = null;
               String var133 = var2.getAttributeValue((String)var131, var129);
               var3.setGender(var133);
            } else if(XmlNametable.GC_HOBBY.equals(var4)) {
               String var134 = XmlUtils.extractChildText(var2);
               var3.addHobby(var134);
            } else if(XmlNametable.GC_INITIALS.equals(var4)) {
               String var135 = XmlUtils.extractChildText(var2);
               var3.setInitials(var135);
            } else if(XmlNametable.GC_JOT.equals(var4)) {
               Jot var136 = new Jot();
               Hashtable var137 = REL_TO_TYPE_JOT;
               parseTypedElement(var136, var2, var137);
               String var140 = XmlUtils.extractChildText(var2);
               var136.setLabel(var140);
               var3.addJot(var136);
            } else if(XmlNametable.GC_LANGUAGE.equals(var4)) {
               Language var141 = new Language();
               String var142 = XmlNametable.CODE;
               Object var144 = null;
               String var146 = var2.getAttributeValue((String)var144, var142);
               var141.setCode(var146);
               String var147 = XmlNametable.LABEL;
               Object var149 = null;
               String var151 = var2.getAttributeValue((String)var149, var147);
               var141.setLabel(var151);
               var3.addLanguage(var141);
            } else if(XmlNametable.GC_MAIDENNAME.equals(var4)) {
               String var152 = XmlUtils.extractChildText(var2);
               var3.setMaidenName(var152);
            } else if(XmlNametable.GC_MILEAGE.equals(var4)) {
               String var153 = XmlUtils.extractChildText(var2);
               var3.setMileage(var153);
            } else if(XmlNametable.GC_NICKNAME.equals(var4)) {
               String var154 = XmlUtils.extractChildText(var2);
               var3.setNickname(var154);
            } else if(XmlNametable.GC_OCCUPATION.equals(var4)) {
               String var155 = XmlUtils.extractChildText(var2);
               var3.setOccupation(var155);
            } else if(XmlNametable.GC_PRIORITY.equals(var4)) {
               String var156 = XmlNametable.REL;
               Object var158 = null;
               String var160 = var2.getAttributeValue((String)var158, var156);
               Hashtable var161 = REL_TO_TYPE_PRIORITY;
               byte var162 = relToType(var160, var161);
               var3.setPriority(var162);
            } else if(XmlNametable.GC_RELATION.equals(var4)) {
               Relation var163 = new Relation();
               Hashtable var164 = REL_TO_TYPE_RELATION;
               parseTypedElement(var163, var2, var164);
               String var168 = XmlUtils.extractChildText(var2);
               var163.setText(var168);
               var3.addRelation(var163);
            } else if(XmlNametable.GC_SENSITIVITY.equals(var4)) {
               String var172 = XmlNametable.REL;
               Object var174 = null;
               String var176 = var2.getAttributeValue((String)var174, var172);
               Hashtable var177 = REL_TO_TYPE_SENSITIVITY;
               byte var178 = relToType(var176, var177);
               var3.setSensitivity(var178);
            } else if(XmlNametable.GC_SHORTNAME.equals(var4)) {
               String var179 = XmlUtils.extractChildText(var2);
               var3.setShortName(var179);
            } else if(XmlNametable.GC_SUBJECT.equals(var4)) {
               String var180 = XmlUtils.extractChildText(var2);
               var3.setSubject(var180);
            } else if(XmlNametable.GC_UDF.equals(var4)) {
               UserDefinedField var181 = new UserDefinedField();
               String var182 = XmlNametable.KEY;
               Object var184 = null;
               String var186 = var2.getAttributeValue((String)var184, var182);
               var181.setKey(var186);
               String var189 = XmlNametable.VALUE;
               Object var191 = null;
               String var193 = var2.getAttributeValue((String)var191, var189);
               var181.setValue(var193);
               var3.addUserDefinedField(var181);
            } else if(XmlNametable.GC_WEBSITE.equals(var4)) {
               WebSite var197 = new WebSite();
               Hashtable var198 = REL_TO_TYPE_WEBSITE;
               parseContactsElement(var197, var2, var198);
               String var202 = XmlNametable.HREF;
               Object var204 = null;
               String var206 = var2.getAttributeValue((String)var204, var202);
               var197.setHRef(var206);
               String var207 = XmlNametable.GC_LINKSTO;
               Object var209 = null;
               String var211 = var2.getAttributeValue((String)var209, var207);
               var197.setLinksTo(var211);
               var3.addWebSite(var197);
            } else if(XmlNametable.GC_SIP.equals(var4)) {
               SipAddress var213 = new SipAddress();
               Hashtable var214 = REL_TO_TYPE_SIP;
               parseContactsElement(var213, var2, var214);
               String var218 = XmlNametable.GD_ADDRESS;
               Object var220 = null;
               String var222 = var2.getAttributeValue((String)var220, var218);
               var213.setAddress(var222);
               var3.addSipAddress(var213);
            }
         }
      }
   }

   protected void handleExtraLinkInEntry(String var1, String var2, String var3, Entry var4) throws XmlPullParserException, IOException {
      if("http://schemas.google.com/contacts/2008/rel#photo".equals(var1)) {
         ContactEntry var5 = (ContactEntry)var4;
         XmlPullParser var6 = this.getParser();
         String var7 = XmlNametable.ETAG;
         String var8 = var6.getAttributeValue("http://schemas.google.com/g/2005", var7);
         var5.setLinkPhoto(var3, var2, var8);
      }
   }
}
