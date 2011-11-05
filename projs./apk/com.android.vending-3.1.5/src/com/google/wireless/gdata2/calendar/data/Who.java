package com.google.wireless.gdata2.calendar.data;

import com.google.wireless.gdata2.data.StringUtils;

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

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               Who var5 = (Who)var1;
               byte var6 = this.relationship;
               byte var7 = var5.relationship;
               if(var6 != var7) {
                  var2 = false;
                  return var2;
               } else {
                  byte var8 = this.status;
                  byte var9 = var5.status;
                  if(var8 != var9) {
                     var2 = false;
                     return var2;
                  } else {
                     byte var10 = this.type;
                     byte var11 = var5.type;
                     if(var10 != var11) {
                        var2 = false;
                     } else {
                        label55: {
                           if(this.email != null) {
                              String var12 = this.email;
                              String var13 = var5.email;
                              if(var12.equals(var13)) {
                                 break label55;
                              }
                           } else if(var5.email == null) {
                              break label55;
                           }

                           var2 = false;
                           return var2;
                        }

                        if(this.value != null) {
                           String var14 = this.value;
                           String var15 = var5.value;
                           if(var14.equals(var15)) {
                              return var2;
                           }
                        } else if(var5.value == null) {
                           return var2;
                        }

                        var2 = false;
                     }

                     return var2;
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

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

   public int hashCode() {
      int var1 = 0;
      int var2;
      if(this.email != null) {
         var2 = this.email.hashCode();
      } else {
         var2 = 0;
      }

      int var3 = var2 * 31;
      if(this.value != null) {
         var1 = this.value.hashCode();
      }

      int var4 = (var3 + var1) * 31;
      byte var5 = this.relationship;
      int var6 = (var4 + var5) * 31;
      byte var7 = this.type;
      int var8 = (var6 + var7) * 31;
      byte var9 = this.status;
      return var8 + var9;
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
         StringBuffer var2 = var1.append("EMAIL: ");
         String var3 = this.email;
         StringBuffer var4 = var2.append(var3).append("\n");
      }

      if(!StringUtils.isEmpty(this.value)) {
         StringBuffer var5 = var1.append("VALUE: ");
         String var6 = this.value;
         StringBuffer var7 = var5.append(var6).append("\n");
      }

      StringBuffer var8 = var1.append("RELATIONSHIP: ");
      byte var9 = this.relationship;
      StringBuffer var10 = var8.append(var9).append("\n");
      StringBuffer var11 = var1.append("TYPE: ");
      byte var12 = this.type;
      StringBuffer var13 = var11.append(var12).append("\n");
      StringBuffer var14 = var1.append("STATUS: ");
      byte var15 = this.status;
      StringBuffer var16 = var14.append(var15).append("\n");
   }
}
