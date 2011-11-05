package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.Entry;

public class CalendarEntry extends Entry {

   public static final byte ACCESS_EDITOR = 3;
   public static final byte ACCESS_FREEBUSY = 2;
   public static final byte ACCESS_NONE = 0;
   public static final byte ACCESS_OWNER = 4;
   public static final byte ACCESS_READ = 1;
   public static final byte ACCESS_ROOT = 5;
   private byte accessLevel = 1;
   private String alternateLink = null;
   private String color = null;
   private boolean hidden = 0;
   private String overrideName = null;
   private boolean selected = 1;
   private String timezone = null;


   public CalendarEntry() {}

   public void clear() {
      super.clear();
      this.accessLevel = 1;
      this.alternateLink = null;
      this.color = null;
      this.hidden = (boolean)0;
      this.selected = (boolean)1;
      this.timezone = null;
      this.overrideName = null;
   }

   public byte getAccessLevel() {
      return this.accessLevel;
   }

   public String getAlternateLink() {
      return this.alternateLink;
   }

   public String getColor() {
      return this.color;
   }

   public String getOverrideName() {
      return this.overrideName;
   }

   public String getTimezone() {
      return this.timezone;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setAccessLevel(byte var1) {
      this.accessLevel = var1;
   }

   public void setAlternateLink(String var1) {
      this.alternateLink = var1;
   }

   public void setColor(String var1) {
      this.color = var1;
   }

   public void setHidden(boolean var1) {
      this.hidden = var1;
   }

   public void setOverrideName(String var1) {
      this.overrideName = var1;
   }

   public void setSelected(boolean var1) {
      this.selected = var1;
   }

   public void setTimezone(String var1) {
      this.timezone = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("ACCESS LEVEL: ");
      byte var3 = this.accessLevel;
      var1.append(var3);
      StringBuffer var5 = var1.append('\n');
      String var6 = this.alternateLink;
      this.appendIfNotNull(var1, "ALTERNATE LINK", var6);
      String var7 = this.color;
      this.appendIfNotNull(var1, "COLOR", var7);
      StringBuffer var8 = var1.append("HIDDEN: ");
      boolean var9 = this.hidden;
      var1.append(var9);
      StringBuffer var11 = var1.append('\n');
      StringBuffer var12 = var1.append("SELECTED: ");
      boolean var13 = this.selected;
      var1.append(var13);
      StringBuffer var15 = var1.append('\n');
      String var16 = this.timezone;
      this.appendIfNotNull(var1, "TIMEZONE", var16);
      String var17 = this.overrideName;
      this.appendIfNotNull(var1, "OVERRIDE NAME", var17);
   }
}
