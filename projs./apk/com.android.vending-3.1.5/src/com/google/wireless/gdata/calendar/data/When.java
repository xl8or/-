package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.StringUtils;

public class When {

   private final String endTime;
   private final String startTime;


   public When(String var1, String var2) {
      this.startTime = var1;
      this.endTime = var2;
   }

   public String getEndTime() {
      return this.endTime;
   }

   public String getStartTime() {
      return this.startTime;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      if(!StringUtils.isEmpty(this.startTime)) {
         StringBuilder var2 = (new StringBuilder()).append("START TIME: ");
         String var3 = this.startTime;
         String var4 = var2.append(var3).append("\n").toString();
         var1.append(var4);
      }

      if(!StringUtils.isEmpty(this.endTime)) {
         StringBuilder var6 = (new StringBuilder()).append("END TIME: ");
         String var7 = this.endTime;
         String var8 = var6.append(var7).append("\n").toString();
         var1.append(var8);
      }
   }
}
