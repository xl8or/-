package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.contacts.data.ContactsElement;

public class ImAddress extends ContactsElement {

   public static final byte PROTOCOL_AIM = 2;
   public static final byte PROTOCOL_CUSTOM = 1;
   public static final byte PROTOCOL_GOOGLE_TALK = 7;
   public static final byte PROTOCOL_ICQ = 8;
   public static final byte PROTOCOL_JABBER = 9;
   public static final byte PROTOCOL_MSN = 3;
   public static final byte PROTOCOL_NONE = 10;
   public static final byte PROTOCOL_QQ = 6;
   public static final byte PROTOCOL_SKYPE = 5;
   public static final byte PROTOCOL_YAHOO = 4;
   public static final byte TYPE_HOME = 1;
   public static final byte TYPE_OTHER = 3;
   public static final byte TYPE_WORK = 2;
   private String address;
   private String protocolCustom;
   private byte protocolPredefined;


   public ImAddress() {}

   public String getAddress() {
      return this.address;
   }

   public String getProtocolCustom() {
      return this.protocolCustom;
   }

   public byte getProtocolPredefined() {
      return this.protocolPredefined;
   }

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void setProtocolCustom(String var1) {
      this.protocolCustom = var1;
   }

   public void setProtocolPredefined(byte var1) {
      this.protocolPredefined = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("ImAddress");
      super.toString(var1);
      StringBuffer var3 = var1.append(" protocolPredefined:");
      byte var4 = this.protocolPredefined;
      var3.append(var4);
      if(this.protocolCustom != null) {
         StringBuffer var6 = var1.append(" protocolCustom:");
         String var7 = this.protocolCustom;
         var6.append(var7);
      }

      if(this.address != null) {
         StringBuffer var9 = var1.append(" address:");
         String var10 = this.address;
         var9.append(var10);
      }
   }
}
