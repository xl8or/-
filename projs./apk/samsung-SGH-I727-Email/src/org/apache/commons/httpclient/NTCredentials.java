package org.apache.commons.httpclient;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.util.LangUtils;

public class NTCredentials extends UsernamePasswordCredentials {

   private String domain;
   private String host;


   public NTCredentials() {}

   public NTCredentials(String var1, String var2, String var3, String var4) {
      super(var1, var2);
      if(var4 == null) {
         throw new IllegalArgumentException("Domain may not be null");
      } else {
         this.domain = var4;
         if(var3 == null) {
            throw new IllegalArgumentException("Host may not be null");
         } else {
            this.host = var3;
         }
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(this == var1) {
         var2 = true;
      } else if(super.equals(var1) && var1 instanceof NTCredentials) {
         NTCredentials var3 = (NTCredentials)var1;
         String var4 = this.domain;
         String var5 = var3.domain;
         if(LangUtils.equals(var4, var5)) {
            String var6 = this.host;
            String var7 = var3.host;
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

   public String getDomain() {
      return this.domain;
   }

   public String getHost() {
      return this.host;
   }

   public int hashCode() {
      int var1 = super.hashCode();
      String var2 = this.host;
      int var3 = LangUtils.hashCode(var1, var2);
      String var4 = this.domain;
      return LangUtils.hashCode(var3, var4);
   }

   public void setDomain(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Domain may not be null");
      } else {
         this.domain = var1;
      }
   }

   public void setHost(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Host may not be null");
      } else {
         this.host = var1;
      }
   }

   public String toString() {
      String var1 = super.toString();
      StringBuffer var2 = new StringBuffer(var1);
      StringBuffer var3 = var2.append("@");
      String var4 = this.host;
      var2.append(var4);
      StringBuffer var6 = var2.append(".");
      String var7 = this.domain;
      var2.append(var7);
      return var2.toString();
   }
}
