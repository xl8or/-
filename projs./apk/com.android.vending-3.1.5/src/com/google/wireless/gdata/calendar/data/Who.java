package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.StringUtils;

public class Who {

   public static final byte RELATIONSHIP_ATTENDEE = 1;
   public static final byte RELATIONSHIP_NONE = 0;
   public static final byte RELATIONSHIP_ORGANIZER = 2;
   public static final byte RELATIONSHIP_PERFORMER = 3;
   public static final byte RELATIONSHIP_SPEAKER = 4;
   public static final byte STATUS_ACCEPTED = 1;
   public static final byte STATUS_DECLINED = 2;
   public static final byte STATUS_INVITED = 3;
   public static final byte STATUS_NONE = 0;
   public static final byte STATUS_TENTATIVE = 4;
   public static final byte TYPE_NONE = 0;
   public static final byte TYPE_OPTIONAL = 1;
   public static final byte TYPE_REQUIRED = 2;
   private String email;
   private byte relationship = 0;
   private byte status = 0;
   private byte type = 0;
   private String value;


   public Who() {}

   public String getEmail() {
      return this.email;
   }

   public byte getRelationship() {
      return this.relationship;
   }

   public byte getStatus() {
      return this.status;
   }

   public byte getType() {
      return this.type;
   }

   public String getValue() {
      return this.value;
   }

   public void setEmail(String var1) {
      this.email = var1;
   }

   public void setRelationship(byte var1) {
      this.relationship = var1;
   }

   public void setStatus(byte var1) {
      this.status = var1;
   }

   public void setType(byte var1) {
      this.type = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   protected void toString(StringBuffer var1) {
      if(!StringUtils.isEmpty(this.email)) {
         StringBuilder var2 = (new StringBuilder()).append("EMAIL: ");
         String var3 = this.email;
         String var4 = var2.append(var3).append("\n").toString();
         var1.append(var4);
      }

      if(!StringUtils.isEmpty(this.value)) {
         StringBuilder var6 = (new StringBuilder()).append("VALUE: ");
         String var7 = this.value;
         String var8 = var6.append(var7).append("\n").toString();
         var1.append(var8);
      }

      StringBuilder var10 = (new StringBuilder()).append("RELATIONSHIP: ");
      byte var11 = this.relationship;
      String var12 = var10.append(var11).append("\n").toString();
      var1.append(var12);
      StringBuilder var14 = (new StringBuilder()).append("TYPE: ");
      byte var15 = this.type;
      String var16 = var14.append(var15).append("\n").toString();
      var1.append(var16);
      StringBuilder var18 = (new StringBuilder()).append("STATUS: ");
      byte var19 = this.status;
      String var20 = var18.append(var19).append("\n").toString();
      var1.append(var20);
   }
}
