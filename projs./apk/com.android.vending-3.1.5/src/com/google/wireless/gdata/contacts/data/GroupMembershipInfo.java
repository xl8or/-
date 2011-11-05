package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;

public class GroupMembershipInfo {

   private boolean deleted;
   private String group;


   public GroupMembershipInfo() {}

   public String getGroup() {
      return this.group;
   }

   public boolean isDeleted() {
      return this.deleted;
   }

   public void setDeleted(boolean var1) {
      this.deleted = var1;
   }

   public void setGroup(String var1) {
      this.group = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("GroupMembershipInfo");
      if(this.group != null) {
         StringBuffer var3 = var1.append(" group:");
         String var4 = this.group;
         var3.append(var4);
      }

      StringBuffer var6 = var1.append(" deleted:");
      boolean var7 = this.deleted;
      var6.append(var7);
   }

   public void validate() throws ParseException {
      if(StringUtils.isEmpty(this.group)) {
         throw new ParseException("the group must be present");
      }
   }
}
