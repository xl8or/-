package com.google.wireless.gdata.contacts.data;


public class GeoPt {

   private Float elevation;
   private String label;
   private Float latitude;
   private Float longitude;
   private String time;


   public GeoPt() {}

   public Float getElevation() {
      return this.elevation;
   }

   public String getLabel() {
      return this.label;
   }

   public Float getLatitute() {
      return this.latitude;
   }

   public Float getLongitute() {
      return this.longitude;
   }

   public String getTime() {
      return this.time;
   }

   public void setElevation(Float var1) {
      this.elevation = var1;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public void setLatitude(Float var1) {
      this.latitude = var1;
   }

   public void setLongitude(Float var1) {
      this.longitude = var1;
   }

   public void setTime(String var1) {
      this.time = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("GeoPt");
      if(this.latitude != null) {
         StringBuffer var3 = var1.append(" latitude:");
         Float var4 = this.latitude;
         var3.append(var4);
      }

      if(this.longitude != null) {
         StringBuffer var6 = var1.append(" longitude:");
         Float var7 = this.longitude;
         var6.append(var7);
      }

      if(this.elevation != null) {
         StringBuffer var9 = var1.append(" elevation:");
         Float var10 = this.elevation;
         var9.append(var10);
      }

      if(this.time != null) {
         StringBuffer var12 = var1.append(" time:");
         String var13 = this.time;
         var12.append(var13);
      }

      if(this.label != null) {
         StringBuffer var15 = var1.append(" label:");
         String var16 = this.label;
         var15.append(var16);
      }
   }
}
