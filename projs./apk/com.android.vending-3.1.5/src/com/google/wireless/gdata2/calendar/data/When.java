package com.google.wireless.gdata2.calendar.data;

import com.google.wireless.gdata2.data.StringUtils;

public class When {

   private final String endTime;
   private final String startTime;


   public When(String var1, String var2) {
      this.startTime = var1;
      this.endTime = var2;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               When var5;
               label47: {
                  var5 = (When)var1;
                  if(this.endTime != null) {
                     String var6 = this.endTime;
                     String var7 = var5.endTime;
                     if(var6.equals(var7)) {
                        break label47;
                     }
                  } else if(var5.endTime == null) {
                     break label47;
                  }

                  var2 = false;
                  return var2;
               }

               if(this.startTime != null) {
                  String var8 = this.startTime;
                  String var9 = var5.startTime;
                  if(var8.equals(var9)) {
                     return var2;
                  }
               } else if(var5.startTime == null) {
                  return var2;
               }

               var2 = false;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getEndTime() {
      return this.endTime;
   }

   public String getStartTime() {
      return this.startTime;
   }

   public int hashCode() {
      int var1 = 0;
      int var2;
      if(this.startTime != null) {
         var2 = this.startTime.hashCode();
      } else {
         var2 = 0;
      }

      int var3 = var2 * 31;
      if(this.endTime != null) {
         var1 = this.endTime.hashCode();
      }

      return var3 + var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      if(!StringUtils.isEmpty(this.startTime)) {
         StringBuffer var2 = var1.append("START TIME: ");
         String var3 = this.startTime;
         StringBuffer var4 = var2.append(var3).append("\n");
      }

      if(!StringUtils.isEmpty(this.endTime)) {
         StringBuffer var5 = var1.append("END TIME: ");
         String var6 = this.endTime;
         StringBuffer var7 = var5.append(var6).append("\n");
      }
   }
}
