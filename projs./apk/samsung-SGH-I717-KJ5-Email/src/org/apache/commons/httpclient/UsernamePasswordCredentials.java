package org.apache.commons.httpclient;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.util.LangUtils;

public class UsernamePasswordCredentials implements Credentials {

   private String password;
   private String userName;


   public UsernamePasswordCredentials() {}

   public UsernamePasswordCredentials(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Username:password string may not be null");
      } else {
         int var2 = var1.indexOf(58);
         if(var2 >= 0) {
            String var3 = var1.substring(0, var2);
            this.userName = var3;
            int var4 = var2 + 1;
            String var5 = var1.substring(var4);
            this.password = var5;
         } else {
            this.userName = var1;
         }
      }
   }

   public UsernamePasswordCredentials(String var1, String var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("Username may not be null");
      } else {
         this.userName = var1;
         this.password = var2;
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(this == var1) {
         var2 = true;
      } else {
         Class var3 = this.getClass();
         Class var4 = var1.getClass();
         if(var3.equals(var4)) {
            UsernamePasswordCredentials var5 = (UsernamePasswordCredentials)var1;
            String var6 = this.userName;
            String var7 = var5.userName;
            if(LangUtils.equals(var6, var7)) {
               String var8 = this.password;
               String var9 = var5.password;
               if(LangUtils.equals(var8, var9)) {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getPassword() {
      return this.password;
   }

   public String getUserName() {
      return this.userName;
   }

   public int hashCode() {
      String var1 = this.userName;
      int var2 = LangUtils.hashCode(17, var1);
      String var3 = this.password;
      return LangUtils.hashCode(var2, var3);
   }

   public void setPassword(String var1) {
      this.password = var1;
   }

   public void setUserName(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Username may not be null");
      } else {
         this.userName = var1;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = this.userName;
      var1.append(var2);
      StringBuffer var4 = var1.append(":");
      String var5;
      if(this.password == null) {
         var5 = "null";
      } else {
         var5 = this.password;
      }

      var1.append(var5);
      return var1.toString();
   }
}
