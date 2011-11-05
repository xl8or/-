package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.ContactsElement;
import com.google.wireless.gdata2.data.StringUtils;

public class WebSite extends ContactsElement {

   public static final byte TYPE_BLOG = 2;
   public static final byte TYPE_FTP = 7;
   public static final byte TYPE_HOME = 4;
   public static final byte TYPE_HOMEPAGE = 1;
   public static final byte TYPE_OTHER = 6;
   public static final byte TYPE_PROFILE = 3;
   public static final byte TYPE_WORK = 5;
   private String href;
   private String linksTo;


   public WebSite() {}

   public WebSite(String var1, String var2, byte var3, String var4, boolean var5) {
      super(var3, var4, var5);
      this.href = var1;
      String var6 = this.linksTo;
      this.linksTo = var6;
   }

   public String getHRef() {
      return this.href;
   }

   public String getLinksTo() {
      return this.linksTo;
   }

   public void setHRef(String var1) {
      this.href = var1;
   }

   public void setLinksTo(String var1) {
      this.linksTo = var1;
   }

   public void toString(StringBuffer var1) {
      StringBuffer var2 = var1.append("WebSite");
      super.toString(var1);
      if(!StringUtils.isEmpty(this.href)) {
         StringBuffer var3 = var1.append(" href:");
         String var4 = this.href;
         var3.append(var4);
      }

      if(this.linksTo != null) {
         StringBuffer var6 = var1.append(" linksTo:");
         String var7 = this.linksTo;
         var6.append(var7);
      }
   }
}
