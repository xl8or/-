package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.CalendarLink;
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
import com.google.wireless.gdata2.contacts.data.UserDefinedField;
import com.google.wireless.gdata2.contacts.data.WebSite;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.ExtendedProperty;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import java.util.Enumeration;
import java.util.Vector;

public class ContactEntry extends Entry {

   public static final String GENDER_FEMALE = "female";
   public static final String GENDER_MALE = "male";
   public static final byte TYPE_PRIORITY_HIGH = 1;
   public static final byte TYPE_PRIORITY_LOW = 3;
   public static final byte TYPE_PRIORITY_NORMAL = 2;
   public static final byte TYPE_SENSITIVITY_CONFIDENTIAL = 1;
   public static final byte TYPE_SENSITIVITY_NORMAL = 2;
   public static final byte TYPE_SENSITIVITY_PERSONAL = 3;
   public static final byte TYPE_SENSITIVITY_PRIVATE = 4;
   private String billingInformation;
   private String birthday;
   private final Vector calendarLinks;
   private String directoryServer;
   private final Vector emailAddresses;
   private final Vector events;
   private final Vector extendedProperties;
   private final Vector externalIds;
   private String gender;
   private final Vector groups;
   private final Vector hobbies;
   private final Vector imAddresses;
   private String initials;
   private final Vector jots;
   private final Vector languages;
   private String linkPhotoEtag;
   private String linkPhotoHref;
   private String linkPhotoType;
   private String maidenName;
   private String mileage;
   private Name name;
   private String nickname;
   private String occupation;
   private final Vector organizations;
   private final Vector phoneNumbers;
   private final Vector postalAddresses;
   private byte priority;
   private final Vector relations;
   private byte sensitivity;
   private String shortName;
   private final Vector sipAddresses;
   private String subject;
   private final Vector userDefinedFields;
   private final Vector webSites;


   public ContactEntry() {
      Vector var1 = new Vector();
      this.emailAddresses = var1;
      Vector var2 = new Vector();
      this.imAddresses = var2;
      Vector var3 = new Vector();
      this.phoneNumbers = var3;
      Vector var4 = new Vector();
      this.postalAddresses = var4;
      Vector var5 = new Vector();
      this.organizations = var5;
      Vector var6 = new Vector();
      this.extendedProperties = var6;
      Vector var7 = new Vector();
      this.groups = var7;
      Vector var8 = new Vector();
      this.calendarLinks = var8;
      Vector var9 = new Vector();
      this.events = var9;
      Vector var10 = new Vector();
      this.externalIds = var10;
      Vector var11 = new Vector();
      this.hobbies = var11;
      Vector var12 = new Vector();
      this.jots = var12;
      Vector var13 = new Vector();
      this.languages = var13;
      Vector var14 = new Vector();
      this.relations = var14;
      Vector var15 = new Vector();
      this.userDefinedFields = var15;
      Vector var16 = new Vector();
      this.webSites = var16;
      Vector var17 = new Vector();
      this.sipAddresses = var17;
      this.priority = -1;
      this.sensitivity = -1;
   }

   public void addCalendarLink(CalendarLink var1) {
      this.calendarLinks.addElement(var1);
   }

   public void addEmailAddress(EmailAddress var1) {
      this.emailAddresses.addElement(var1);
   }

   public void addEvent(Event var1) {
      this.events.addElement(var1);
   }

   public void addExtendedProperty(ExtendedProperty var1) {
      this.extendedProperties.addElement(var1);
   }

   public void addExternalId(ExternalId var1) {
      this.externalIds.addElement(var1);
   }

   public void addGroup(GroupMembershipInfo var1) {
      this.groups.addElement(var1);
   }

   public void addHobby(String var1) {
      this.hobbies.addElement(var1);
   }

   public void addImAddress(ImAddress var1) {
      this.imAddresses.addElement(var1);
   }

   public void addJot(Jot var1) {
      this.jots.addElement(var1);
   }

   public void addLanguage(Language var1) {
      this.languages.addElement(var1);
   }

   public void addOrganization(Organization var1) {
      this.organizations.addElement(var1);
   }

   public void addPhoneNumber(PhoneNumber var1) {
      this.phoneNumbers.addElement(var1);
   }

   public void addPostalAddress(StructuredPostalAddress var1) {
      this.postalAddresses.addElement(var1);
   }

   public void addRelation(Relation var1) {
      this.relations.addElement(var1);
   }

   public void addSipAddress(SipAddress var1) {
      this.sipAddresses.addElement(var1);
   }

   public void addUserDefinedField(UserDefinedField var1) {
      this.userDefinedFields.addElement(var1);
   }

   public void addWebSite(WebSite var1) {
      this.webSites.addElement(var1);
   }

   public void clear() {
      super.clear();
      this.linkPhotoHref = null;
      this.linkPhotoType = null;
      this.linkPhotoEtag = null;
      this.directoryServer = null;
      this.gender = null;
      this.initials = null;
      this.maidenName = null;
      this.mileage = null;
      this.nickname = null;
      this.occupation = null;
      this.priority = -1;
      this.sensitivity = -1;
      this.shortName = null;
      this.subject = null;
      this.birthday = null;
      this.billingInformation = null;
      this.name = null;
      this.emailAddresses.removeAllElements();
      this.imAddresses.removeAllElements();
      this.phoneNumbers.removeAllElements();
      this.postalAddresses.removeAllElements();
      this.organizations.removeAllElements();
      this.extendedProperties.removeAllElements();
      this.groups.removeAllElements();
      this.calendarLinks.removeAllElements();
      this.events.removeAllElements();
      this.externalIds.removeAllElements();
      this.hobbies.removeAllElements();
      this.jots.removeAllElements();
      this.languages.removeAllElements();
      this.relations.removeAllElements();
      this.userDefinedFields.removeAllElements();
      this.webSites.removeAllElements();
      this.sipAddresses.removeAllElements();
   }

   public String getBillingInformation() {
      return this.billingInformation;
   }

   public String getBirthday() {
      return this.birthday;
   }

   public Vector getCalendarLinks() {
      return this.calendarLinks;
   }

   public String getDirectoryServer() {
      return this.directoryServer;
   }

   public Vector getEmailAddresses() {
      return this.emailAddresses;
   }

   public Vector getEvents() {
      return this.events;
   }

   public Vector getExtendedProperties() {
      return this.extendedProperties;
   }

   public Vector getExternalIds() {
      return this.externalIds;
   }

   public String getGender() {
      return this.gender;
   }

   public Vector getGroups() {
      return this.groups;
   }

   public Vector getHobbies() {
      return this.hobbies;
   }

   public Vector getImAddresses() {
      return this.imAddresses;
   }

   public String getInitials() {
      return this.initials;
   }

   public Vector getJots() {
      return this.jots;
   }

   public Vector getLanguages() {
      return this.languages;
   }

   public String getLinkPhotoETag() {
      return this.linkPhotoEtag;
   }

   public String getLinkPhotoHref() {
      return this.linkPhotoHref;
   }

   public String getLinkPhotoType() {
      return this.linkPhotoType;
   }

   public String getMaidenName() {
      return this.maidenName;
   }

   public String getMileage() {
      return this.mileage;
   }

   public Name getName() {
      return this.name;
   }

   public String getNickname() {
      return this.nickname;
   }

   public String getOccupation() {
      return this.occupation;
   }

   public Vector getOrganizations() {
      return this.organizations;
   }

   public Vector getPhoneNumbers() {
      return this.phoneNumbers;
   }

   public Vector getPostalAddresses() {
      return this.postalAddresses;
   }

   public byte getPriority() {
      return this.priority;
   }

   public Vector getRelations() {
      return this.relations;
   }

   public byte getSensitivity() {
      return this.sensitivity;
   }

   public String getShortName() {
      return this.shortName;
   }

   public Vector getSipAddresses() {
      return this.sipAddresses;
   }

   public String getSubject() {
      return this.subject;
   }

   public Vector getUserDefinedFields() {
      return this.userDefinedFields;
   }

   public Vector getWebSites() {
      return this.webSites;
   }

   public void setBillingInformation(String var1) {
      this.billingInformation = var1;
   }

   public void setBirthday(String var1) {
      this.birthday = var1;
   }

   public void setDirectoryServer(String var1) {
      this.directoryServer = var1;
   }

   public void setGender(String var1) {
      this.gender = var1;
   }

   public void setInitials(String var1) {
      this.initials = var1;
   }

   public void setLinkPhoto(String var1, String var2, String var3) {
      this.linkPhotoHref = var1;
      this.linkPhotoType = var2;
      this.linkPhotoEtag = var3;
   }

   public void setMaidenName(String var1) {
      this.maidenName = var1;
   }

   public void setMileage(String var1) {
      this.mileage = var1;
   }

   public void setName(Name var1) {
      this.name = var1;
   }

   public void setNickname(String var1) {
      this.nickname = var1;
   }

   public void setOccupation(String var1) {
      this.occupation = var1;
   }

   public void setPriority(byte var1) {
      this.priority = var1;
   }

   public void setSensitivity(byte var1) {
      this.sensitivity = var1;
   }

   public void setShortName(String var1) {
      this.shortName = var1;
   }

   public void setSubject(String var1) {
      this.subject = var1;
   }

   protected void toString(StringBuffer var1) {
      super.toString(var1);
      StringBuffer var2 = var1.append("\n");
      StringBuffer var3 = var1.append("ContactEntry:");
      if(!StringUtils.isEmpty(this.linkPhotoHref)) {
         StringBuffer var4 = var1.append(" linkPhotoHref:");
         String var5 = this.linkPhotoHref;
         var4.append(var5);
      }

      if(!StringUtils.isEmpty(this.linkPhotoType)) {
         StringBuffer var7 = var1.append(" linkPhotoType:");
         String var8 = this.linkPhotoType;
         var7.append(var8);
      }

      if(!StringUtils.isEmpty(this.linkPhotoEtag)) {
         StringBuffer var10 = var1.append(" linkPhotoEtag:");
         String var11 = this.linkPhotoEtag;
         var10.append(var11);
      }

      if(!StringUtils.isEmpty(this.directoryServer)) {
         StringBuffer var13 = var1.append(" directoryServer:");
         String var14 = this.directoryServer;
         var13.append(var14);
      }

      if(!StringUtils.isEmpty(this.gender)) {
         StringBuffer var16 = var1.append(" gender:");
         String var17 = this.gender;
         var16.append(var17);
      }

      if(!StringUtils.isEmpty(this.initials)) {
         StringBuffer var19 = var1.append(" initials:");
         String var20 = this.initials;
         var19.append(var20);
      }

      if(!StringUtils.isEmpty(this.maidenName)) {
         StringBuffer var22 = var1.append(" maidenName:");
         String var23 = this.maidenName;
         var22.append(var23);
      }

      if(!StringUtils.isEmpty(this.mileage)) {
         StringBuffer var25 = var1.append(" mileage:");
         String var26 = this.mileage;
         var25.append(var26);
      }

      if(!StringUtils.isEmpty(this.nickname)) {
         StringBuffer var28 = var1.append(" nickname:");
         String var29 = this.nickname;
         var28.append(var29);
      }

      if(!StringUtils.isEmpty(this.occupation)) {
         StringBuffer var31 = var1.append(" occupaton:");
         String var32 = this.occupation;
         var31.append(var32);
      }

      StringBuffer var34 = var1.append(" priority:");
      byte var35 = this.priority;
      var34.append(var35);
      StringBuffer var37 = var1.append(" sensitivity:");
      byte var38 = this.sensitivity;
      var37.append(var38);
      if(!StringUtils.isEmpty(this.shortName)) {
         StringBuffer var40 = var1.append(" shortName:");
         String var41 = this.shortName;
         var40.append(var41);
      }

      if(!StringUtils.isEmpty(this.subject)) {
         StringBuffer var43 = var1.append(" subject:");
         String var44 = this.subject;
         var43.append(var44);
      }

      if(!StringUtils.isEmpty(this.birthday)) {
         StringBuffer var46 = var1.append(" birthday:");
         String var47 = this.birthday;
         var46.append(var47);
      }

      if(!StringUtils.isEmpty(this.billingInformation)) {
         StringBuffer var49 = var1.append(" billingInformation:");
         String var50 = this.billingInformation;
         var49.append(var50);
      }

      StringBuffer var52 = var1.append("\n");
      if(this.name != null) {
         this.name.toString(var1);
         StringBuffer var53 = var1.append("\n");
      }

      StringBuffer var56;
      for(Enumeration var54 = this.emailAddresses.elements(); var54.hasMoreElements(); var56 = var1.append("\n")) {
         StringBuffer var55 = var1.append("  ");
         ((EmailAddress)var54.nextElement()).toString(var1);
      }

      StringBuffer var59;
      for(Enumeration var57 = this.imAddresses.elements(); var57.hasMoreElements(); var59 = var1.append("\n")) {
         StringBuffer var58 = var1.append("  ");
         ((ImAddress)var57.nextElement()).toString(var1);
      }

      StringBuffer var62;
      for(Enumeration var60 = this.postalAddresses.elements(); var60.hasMoreElements(); var62 = var1.append("\n")) {
         StringBuffer var61 = var1.append("  ");
         ((StructuredPostalAddress)var60.nextElement()).toString(var1);
      }

      StringBuffer var65;
      for(Enumeration var63 = this.phoneNumbers.elements(); var63.hasMoreElements(); var65 = var1.append("\n")) {
         StringBuffer var64 = var1.append("  ");
         ((PhoneNumber)var63.nextElement()).toString(var1);
      }

      StringBuffer var68;
      for(Enumeration var66 = this.organizations.elements(); var66.hasMoreElements(); var68 = var1.append("\n")) {
         StringBuffer var67 = var1.append("  ");
         ((Organization)var66.nextElement()).toString(var1);
      }

      StringBuffer var71;
      for(Enumeration var69 = this.extendedProperties.elements(); var69.hasMoreElements(); var71 = var1.append("\n")) {
         StringBuffer var70 = var1.append("  ");
         ((ExtendedProperty)var69.nextElement()).toString(var1);
      }

      StringBuffer var74;
      for(Enumeration var72 = this.groups.elements(); var72.hasMoreElements(); var74 = var1.append("\n")) {
         StringBuffer var73 = var1.append("  ");
         ((GroupMembershipInfo)var72.nextElement()).toString(var1);
      }

      StringBuffer var77;
      for(Enumeration var75 = this.calendarLinks.elements(); var75.hasMoreElements(); var77 = var1.append("\n")) {
         StringBuffer var76 = var1.append("  ");
         ((CalendarLink)var75.nextElement()).toString(var1);
      }

      StringBuffer var80;
      for(Enumeration var78 = this.events.elements(); var78.hasMoreElements(); var80 = var1.append("\n")) {
         StringBuffer var79 = var1.append("  ");
         ((Event)var78.nextElement()).toString(var1);
      }

      StringBuffer var83;
      for(Enumeration var81 = this.externalIds.elements(); var81.hasMoreElements(); var83 = var1.append("\n")) {
         StringBuffer var82 = var1.append("  ");
         ((ExternalId)var81.nextElement()).toString(var1);
      }

      StringBuffer var88;
      for(Enumeration var84 = this.hobbies.elements(); var84.hasMoreElements(); var88 = var1.append("\n")) {
         StringBuffer var85 = var1.append("  ");
         String var86 = (String)var84.nextElement();
         var1.append(var86);
      }

      StringBuffer var93;
      for(Enumeration var89 = this.jots.elements(); var89.hasMoreElements(); var93 = var1.append("\n")) {
         StringBuffer var90 = var1.append("  ");
         Jot var91 = (Jot)var89.nextElement();
         var1.append(var91);
      }

      StringBuffer var96;
      for(Enumeration var94 = this.languages.elements(); var94.hasMoreElements(); var96 = var1.append("\n")) {
         StringBuffer var95 = var1.append("  ");
         ((Language)var94.nextElement()).toString(var1);
      }

      StringBuffer var99;
      for(Enumeration var97 = this.relations.elements(); var97.hasMoreElements(); var99 = var1.append("\n")) {
         StringBuffer var98 = var1.append("  ");
         ((Relation)var97.nextElement()).toString(var1);
      }

      StringBuffer var102;
      for(Enumeration var100 = this.userDefinedFields.elements(); var100.hasMoreElements(); var102 = var1.append("\n")) {
         StringBuffer var101 = var1.append("  ");
         ((UserDefinedField)var100.nextElement()).toString(var1);
      }

      StringBuffer var105;
      for(Enumeration var103 = this.webSites.elements(); var103.hasMoreElements(); var105 = var1.append("\n")) {
         StringBuffer var104 = var1.append("  ");
         ((WebSite)var103.nextElement()).toString(var1);
      }

      StringBuffer var108;
      for(Enumeration var106 = this.sipAddresses.elements(); var106.hasMoreElements(); var108 = var1.append("\n")) {
         StringBuffer var107 = var1.append("  ");
         ((SipAddress)var106.nextElement()).toString(var1);
      }

   }

   public void validate() throws ParseException {
      super.validate();
      if(this.gender != null) {
         String var1 = this.gender;
         if(!"female".equals(var1)) {
            String var2 = this.gender;
            if(!"male".equals(var2)) {
               Object[] var3 = new Object[3];
               String var4 = this.gender;
               var3[0] = var4;
               var3[1] = "female";
               var3[2] = "male";
               String var5 = String.format("invalid gender \"%s\", must be one of \"%s\" or \"%s\"", var3);
               throw new ParseException(var5);
            }
         }
      }

      Enumeration var6 = this.emailAddresses.elements();

      while(var6.hasMoreElements()) {
         ((EmailAddress)var6.nextElement()).validate();
      }

      Enumeration var7 = this.imAddresses.elements();

      while(var7.hasMoreElements()) {
         ((ImAddress)var7.nextElement()).validate();
      }

      Enumeration var8 = this.postalAddresses.elements();

      while(var8.hasMoreElements()) {
         ((StructuredPostalAddress)var8.nextElement()).validate();
      }

      Enumeration var9 = this.phoneNumbers.elements();

      while(var9.hasMoreElements()) {
         ((PhoneNumber)var9.nextElement()).validate();
      }

      Enumeration var10 = this.organizations.elements();

      while(var10.hasMoreElements()) {
         ((Organization)var10.nextElement()).validate();
      }

      Enumeration var11 = this.extendedProperties.elements();

      while(var11.hasMoreElements()) {
         ((ExtendedProperty)var11.nextElement()).validate();
      }

      Enumeration var12 = this.groups.elements();

      while(var12.hasMoreElements()) {
         ((GroupMembershipInfo)var12.nextElement()).validate();
      }

      Enumeration var13 = this.calendarLinks.elements();

      while(var13.hasMoreElements()) {
         ((CalendarLink)var13.nextElement()).validate();
      }

      Enumeration var14 = this.events.elements();

      while(var14.hasMoreElements()) {
         ((Event)var14.nextElement()).validate();
      }

      Enumeration var15 = this.externalIds.elements();

      while(var15.hasMoreElements()) {
         ((ExternalId)var15.nextElement()).validate();
      }

      Enumeration var16 = this.languages.elements();

      while(var16.hasMoreElements()) {
         ((Language)var16.nextElement()).validate();
      }

      Enumeration var17 = this.relations.elements();

      while(var17.hasMoreElements()) {
         ((Relation)var17.nextElement()).validate();
      }

      Enumeration var18 = this.userDefinedFields.elements();

      while(var18.hasMoreElements()) {
         ((UserDefinedField)var18.nextElement()).validate();
      }

      Enumeration var19 = this.webSites.elements();

      while(var19.hasMoreElements()) {
         ((WebSite)var19.nextElement()).validate();
      }

      Enumeration var20 = this.sipAddresses.elements();

      while(var20.hasMoreElements()) {
         ((SipAddress)var20.nextElement()).validate();
      }

   }
}
