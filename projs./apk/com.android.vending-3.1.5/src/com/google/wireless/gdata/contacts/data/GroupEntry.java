package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;

public class GroupEntry extends Entry {

   private String systemGroup = null;


   public GroupEntry() {}

   public void clear() {
      super.clear();
      this.systemGroup = null;
   }

   public String getSystemGroup() {
      return this.systemGroup;
   }

   public void setSystemGroup(String var1) {
      this.systemGroup = var1;
   }

   protected void toString(StringBuffer var1) {
      super.toString(var1);
      StringBuffer var2 = var1.append("\n");
      StringBuffer var3 = var1.append("GroupEntry:");
      if(!StringUtils.isEmpty(this.systemGroup)) {
         StringBuffer var4 = var1.append(" systemGroup:");
         String var5 = this.systemGroup;
         StringBuffer var6 = var4.append(var5).append("\n");
      }
   }
}
