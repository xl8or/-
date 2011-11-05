package org.apache.commons.httpclient;

import java.io.Serializable;
import org.apache.commons.httpclient.util.LangUtils;

public class NameValuePair implements Serializable {

   private String name;
   private String value;


   public NameValuePair() {
      this((String)null, (String)null);
   }

   public NameValuePair(String var1, String var2) {
      this.name = null;
      this.value = null;
      this.name = var1;
      this.value = var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(this == var1) {
         var2 = true;
      } else if(var1 instanceof NameValuePair) {
         NameValuePair var3 = (NameValuePair)var1;
         String var4 = this.name;
         String var5 = var3.name;
         if(LangUtils.equals(var4, var5)) {
            String var6 = this.value;
            String var7 = var3.value;
            if(LangUtils.equals(var6, var7)) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public int hashCode() {
      String var1 = this.name;
      int var2 = LangUtils.hashCode(17, var1);
      String var3 = this.value;
      return LangUtils.hashCode(var2, var3);
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("name=");
      String var2 = this.name;
      StringBuilder var3 = var1.append(var2).append(", ").append("value=");
      String var4 = this.value;
      return var3.append(var4).toString();
   }
}
