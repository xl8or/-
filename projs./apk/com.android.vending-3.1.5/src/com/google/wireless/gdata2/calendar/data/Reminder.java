package com.google.wireless.gdata2.calendar.data;


public class Reminder {

   public static final byte METHOD_ALERT = 3;
   public static final byte METHOD_DEFAULT = 0;
   public static final byte METHOD_EMAIL = 1;
   public static final byte METHOD_SMS = 2;
   public static final int MINUTES_DEFAULT = 255;
   private byte method = 0;
   private int minutes = -1;


   public Reminder() {}

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               Reminder var5 = (Reminder)var1;
               byte var6 = this.method;
               byte var7 = var5.method;
               if(var6 != var7) {
                  var2 = false;
               } else {
                  int var8 = this.minutes;
                  int var9 = var5.minutes;
                  if(var8 != var9) {
                     var2 = false;
                     return var2;
                  }
               }

               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public byte getMethod() {
      return this.method;
   }

   public int getMinutes() {
      return this.minutes;
   }

   public int hashCode() {
      int var1 = this.minutes * 31;
      byte var2 = this.method;
      return var1 + var2;
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
      StringBuffer var2 = var1.append("REMINDER MINUTES: ");
      int var3 = this.minutes;
      var2.append(var3);
      StringBuffer var5 = var1.append("\n");
      StringBuffer var6 = var1.append("REMINDER METHOD: ");
      byte var7 = this.method;
      var6.append(var7);
      StringBuffer var9 = var1.append("\n");
   }
}
