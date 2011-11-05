package com.htc.android.mail.eassvc.common;


public class EASCalException {

   public String busyStatus;
   public String categories;
   public String creationTime;
   public String description;
   public String dtStamp;
   public String endTime;
   public String exceptionStartTime;
   public boolean hasReminder = 0;
   public boolean isAllDay;
   public boolean isDeleted = 0;
   public String location;
   public long reminderMinsBefore;
   public String sensitivity;
   public String startTime;
   public String summary;


   public EASCalException() {}

   public String toString() {
      StringBuffer var1 = new StringBuffer(270);
      StringBuffer var2 = var1.append("EASCalException@");
      int var3 = this.hashCode();
      StringBuffer var4 = var2.append(var3).append(" ");
      StringBuffer var5 = var1.append(" :ex:exceptionStartTime=");
      String var6 = this.exceptionStartTime;
      var5.append(var6);
      StringBuffer var8 = var1.append(", :ex:isDeleted=");
      boolean var9 = this.isDeleted;
      var8.append(var9);
      StringBuffer var11 = var1.append(", :ex:startTime=");
      String var12 = this.startTime;
      var11.append(var12);
      StringBuffer var14 = var1.append(", :ex:endTime=");
      String var15 = this.endTime;
      var14.append(var15);
      StringBuffer var17 = var1.append(", :ex:isAllDay=");
      boolean var18 = this.isAllDay;
      var17.append(var18);
      StringBuffer var20 = var1.append(", :ex:creationTime=");
      String var21 = this.creationTime;
      var20.append(var21);
      StringBuffer var23 = var1.append(", :ex:dtStamp=");
      String var24 = this.dtStamp;
      var23.append(var24);
      StringBuffer var26 = var1.append(", :ex:summary=");
      String var27 = this.summary;
      var26.append(var27);
      StringBuffer var29 = var1.append(", :ex:description=");
      String var30 = this.description;
      var29.append(var30);
      StringBuffer var32 = var1.append(", :ex:busyStatus=");
      String var33 = this.busyStatus;
      var32.append(var33);
      StringBuffer var35 = var1.append(", :ex:categories=");
      String var36 = this.categories;
      var35.append(var36);
      StringBuffer var38 = var1.append(", :ex:location=");
      String var39 = this.location;
      var38.append(var39);
      StringBuffer var41 = var1.append(", :ex:sensitivity=");
      String var42 = this.sensitivity;
      var41.append(var42);
      StringBuffer var44 = var1.append(", :ex:hasReminder=");
      boolean var45 = this.hasReminder;
      var44.append(var45);
      StringBuffer var47 = var1.append(", :ex:reminderMinsBefore=");
      long var48 = this.reminderMinsBefore;
      var47.append(var48);
      return var1.toString();
   }
}
