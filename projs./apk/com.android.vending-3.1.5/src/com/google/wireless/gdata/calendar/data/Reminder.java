package com.google.wireless.gdata.calendar.data;


public class Reminder {

   public static final byte METHOD_ALERT = 3;
   public static final byte METHOD_DEFAULT = 0;
   public static final byte METHOD_EMAIL = 1;
   public static final byte METHOD_SMS = 2;
   public static final int MINUTES_DEFAULT = 255;
   private byte method = 0;
   private int minutes = -1;


   public Reminder() {}

   public byte getMethod() {
      return this.method;
   }

   public int getMinutes() {
      return this.minutes;
   }

   public void setMethod(byte var1) {
      this.method = var1;
   }

   public void setMinutes(int var1) {
      this.minutes = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      StringBuilder var2 = (new StringBuilder()).append("REMINDER MINUTES: ");
      int var3 = this.minutes;
      String var4 = var2.append(var3).toString();
      var1.append(var4);
      StringBuffer var6 = var1.append("\n");
      StringBuilder var7 = (new StringBuilder()).append("REMINDER METHOD: ");
      byte var8 = this.method;
      String var9 = var7.append(var8).toString();
      var1.append(var9);
      StringBuffer var11 = var1.append("\n");
   }
}
