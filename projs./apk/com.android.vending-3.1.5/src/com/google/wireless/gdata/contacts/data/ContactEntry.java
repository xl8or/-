package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.contacts.data.EmailAddress;
import com.google.wireless.gdata.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata.contacts.data.ImAddress;
import com.google.wireless.gdata.contacts.data.Organization;
import com.google.wireless.gdata.contacts.data.PhoneNumber;
import com.google.wireless.gdata.contacts.data.PostalAddress;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.ExtendedProperty;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import java.util.Enumeration;
import java.util.Vector;

public class ContactEntry extends Entry {

   private final Vector emailAddresses;
   private final Vector extendedProperties;
   private final Vector groups;
   private final Vector imAddresses;
   private String linkEditPhotoHref;
   private String linkEditPhotoType;
   private String linkPhotoHref;
   private String linkPhotoType;
   private final Vector organizations;
   private final Vector phoneNumbers;
   private final Vector postalAddresses;
   private String yomiName;


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
   }

   public void addEmailAddress(EmailAddress var1) {
      this.emailAddresses.addElement(var1);
   }

   public void addExtendedProperty(ExtendedProperty var1) {
      this.extendedProperties.addElement(var1);
   }

   public void addGroup(GroupMembershipInfo var1) {
      this.groups.addElement(var1);
   }

   public void addImAddress(ImAddress var1) {
      this.imAddresses.addElement(var1);
   }

   public void addOrganization(Organization var1) {
      this.organizations.addElement(var1);
   }

   public void addPhoneNumber(PhoneNumber var1) {
      this.phoneNumbers.addElement(var1);
   }

   public void addPostalAddress(PostalAddress var1) {
      this.postalAddresses.addElement(var1);
   }

   public void clear() {
      super.clear();
      this.linkEditPhotoHref = null;
      this.linkEditPhotoType = null;
      this.linkPhotoHref = null;
      this.linkPhotoType = null;
      this.emailAddresses.removeAllElements();
      this.imAddresses.removeAllElements();
      this.phoneNumbers.removeAllElements();
      this.postalAddresses.removeAllElements();
      this.organizations.removeAllElements();
      this.extendedProperties.removeAllElements();
      this.groups.removeAllElements();
      this.yomiName = null;
   }

   public Vector getEmailAddresses() {
      return this.emailAddresses;
   }

   public Vector getExtendedProperties() {
      return this.extendedProperties;
   }

   public Vector getGroups() {
      return this.groups;
   }

   public Vector getImAddresses() {
      return this.imAddresses;
   }

   public String getLinkEditPhotoHref() {
      return this.linkEditPhotoHref;
   }

   public String getLinkEditPhotoType() {
      return this.linkEditPhotoType;
   }

   public String getLinkPhotoHref() {
      return this.linkPhotoHref;
   }

   public String getLinkPhotoType() {
      return this.linkPhotoType;
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

   public String getYomiName() {
      return this.yomiName;
   }

   public void setLinkEditPhoto(String var1, String var2) {
      this.linkEditPhotoHref = var1;
      this.linkEditPhotoType = var2;
   }

   public void setLinkPhoto(String var1, String var2) {
      this.linkPhotoHref = var1;
      this.linkPhotoType = var2;
   }

   public void setYomiName(String var1) {
      this.yomiName = var1;
   }

   protected void toString(StringBuffer var1) {
      super.toString(var1);
      StringBuffer var2 = var1.append("\n");
      StringBuffer var3 = var1.append("ContactEntry:");
      if(!StringUtils.isEmpty(this.linkPhotoHref)) {
         StringBuffer var4 = var1.append(" linkPhotoHref:");
         String var5 = this.linkPhotoHref;
         StringBuffer var6 = var4.append(var5).append("\n");
      }

      if(!StringUtils.isEmpty(this.linkPhotoType)) {
         StringBuffer var7 = var1.append(" linkPhotoType:");
         String var8 = this.linkPhotoType;
         StringBuffer var9 = var7.append(var8).append("\n");
      }

      if(!StringUtils.isEmpty(this.linkEditPhotoHref)) {
         StringBuffer var10 = var1.append(" linkEditPhotoHref:");
         String var11 = this.linkEditPhotoHref;
         StringBuffer var12 = var10.append(var11).append("\n");
      }

      if(!StringUtils.isEmpty(this.linkEditPhotoType)) {
         StringBuffer var13 = var1.append(" linkEditPhotoType:");
         String var14 = this.linkEditPhotoType;
         StringBuffer var15 = var13.append(var14).append("\n");
      }

      StringBuffer var18;
      for(Enumeration var16 = this.emailAddresses.elements(); var16.hasMoreElements(); var18 = var1.append("\n")) {
         StringBuffer var17 = var1.append("  ");
         ((EmailAddress)var16.nextElement()).toString(var1);
      }

      StringBuffer var21;
      for(Enumeration var19 = this.imAddresses.elements(); var19.hasMoreElements(); var21 = var1.append("\n")) {
         StringBuffer var20 = var1.append("  ");
         ((ImAddress)var19.nextElement()).toString(var1);
      }

      StringBuffer var24;
      for(Enumeration var22 = this.postalAddresses.elements(); var22.hasMoreElements(); var24 = var1.append("\n")) {
         StringBuffer var23 = var1.append("  ");
         ((PostalAddress)var22.nextElement()).toString(var1);
      }

      StringBuffer var27;
      for(Enumeration var25 = this.phoneNumbers.elements(); var25.hasMoreElements(); var27 = var1.append("\n")) {
         StringBuffer var26 = var1.append("  ");
         ((PhoneNumber)var25.nextElement()).toString(var1);
      }

      StringBuffer var30;
      for(Enumeration var28 = this.organizations.elements(); var28.hasMoreElements(); var30 = var1.append("\n")) {
         StringBuffer var29 = var1.append("  ");
         ((Organization)var28.nextElement()).toString(var1);
      }

      StringBuffer var33;
      for(Enumeration var31 = this.extendedProperties.elements(); var31.hasMoreElements(); var33 = var1.append("\n")) {
         StringBuffer var32 = var1.append("  ");
         ((ExtendedProperty)var31.nextElement()).toString(var1);
      }

      StringBuffer var36;
      for(Enumeration var34 = this.groups.elements(); var34.hasMoreElements(); var36 = var1.append("\n")) {
         StringBuffer var35 = var1.append("  ");
         ((GroupMembershipInfo)var34.nextElement()).toString(var1);
      }

      if(!StringUtils.isEmpty(this.yomiName)) {
         StringBuffer var37 = var1.append(" yomiName:");
         String var38 = this.yomiName;
         StringBuffer var39 = var37.append(var38).append("\n");
      }
   }

   public void validate() throws ParseException {
      super.validate();
      Enumeration var1 = this.emailAddresses.elements();

      while(var1.hasMoreElements()) {
         ((EmailAddress)var1.nextElement()).validate();
      }

      Enumeration var2 = this.imAddresses.elements();

      while(var2.hasMoreElements()) {
         ((ImAddress)var2.nextElement()).validate();
      }

      Enumeration var3 = this.postalAddresses.elements();

      while(var3.hasMoreElements()) {
         ((PostalAddress)var3.nextElement()).validate();
      }

      Enumeration var4 = this.phoneNumbers.elements();

      while(var4.hasMoreElements()) {
         ((PhoneNumber)var4.nextElement()).validate();
      }

      Enumeration var5 = this.organizations.elements();

      while(var5.hasMoreElements()) {
         ((Organization)var5.nextElement()).validate();
      }

      Enumeration var6 = this.extendedProperties.elements();

      while(var6.hasMoreElements()) {
         ((ExtendedProperty)var6.nextElement()).validate();
      }

      Enumeration var7 = this.groups.elements();

      while(var7.hasMoreElements()) {
         ((GroupMembershipInfo)var7.nextElement()).validate();
      }

   }
}
