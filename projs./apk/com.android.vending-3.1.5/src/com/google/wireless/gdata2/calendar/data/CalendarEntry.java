package com.google.wireless.gdata2.calendar.data;

import com.google.wireless.gdata2.data.Entry;

public class CalendarEntry extends Entry {

   public static final byte ACCESS_EDITOR = 3;
   public static final byte ACCESS_FREEBUSY = 2;
   public static final byte ACCESS_NONE = 0;
   public static final byte ACCESS_OWNER = 4;
   public static final byte ACCESS_READ = 1;
   public static final byte ACCESS_ROOT = 5;
   private byte accessLevel = 1;
   private String color = null;
   private String eventsUri = null;
   private boolean hidden = 0;
   private String overrideName = null;
   private boolean selected = 1;
   private String selfUri = null;
   private String timezone = null;


   public CalendarEntry() {}

   public void clear() {
      super.clear();
      this.accessLevel = 1;
      this.color = null;
      this.hidden = (boolean)0;
      this.selected = (boolean)1;
      this.timezone = null;
      this.overrideName = null;
      this.selfUri = null;
      this.eventsUri = null;
   }

   public byte getAccessLevel() {
      return this.accessLevel;
   }

   public String getColor() {
      return this.color;
   }

   public String getEventsUri() {
      return this.eventsUri;
   }

   public String getOverrideName() {
      return this.overrideName;
   }

   public String getSelfUri() {
      return this.selfUri;
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

   public void setColor(String var1) {
      this.color = var1;
   }

   public void setEventsUri(String var1) {
      this.eventsUri = var1;
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

   public void setSelfUri(String var1) {
      this.selfUri = var1;
   }

   public void setTimezone(String var1) {
      this.timezone = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("ACCESS LEVEL: ");
      byte var3 = this.accessLevel;
      var1.append(var3);
      StringBuffer var5 = var1.append('\n');
      String var6 = this.selfUri;
      this.appendIfNotNull(var1, "SELF URI", var6);
      String var7 = this.getEditUri();
      this.appendIfNotNull(var1, "EDIT URI", var7);
      String var8 = this.eventsUri;
      this.appendIfNotNull(var1, "EVENTS URI", var8);
      String var9 = this.color;
      this.appendIfNotNull(var1, "COLOR", var9);
      StringBuffer var10 = var1.append("HIDDEN: ");
      boolean var11 = this.hidden;
      var1.append(var11);
      StringBuffer var13 = var1.append('\n');
      StringBuffer var14 = var1.append("SELECTED: ");
      boolean var15 = this.selected;
      var1.append(var15);
      StringBuffer var17 = var1.append('\n');
      String var18 = this.timezone;
      this.appendIfNotNull(var1, "TIMEZONE", var18);
      String var19 = this.overrideName;
      this.appendIfNotNull(var1, "OVERRIDE NAME", var19);
   }
}
