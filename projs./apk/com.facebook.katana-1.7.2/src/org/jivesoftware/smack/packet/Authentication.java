package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.StringUtils;

public class Authentication extends IQ {

   private String digest = null;
   private String password = null;
   private String resource = null;
   private String username = null;


   public Authentication() {
      IQ.Type var1 = IQ.Type.SET;
      this.setType(var1);
   }

   public String getChildElementXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<query xmlns=\"jabber:iq:auth\">");
      if(this.username != null) {
         if(this.username.equals("")) {
            StringBuilder var3 = var1.append("<username/>");
         } else {
            StringBuilder var8 = var1.append("<username>");
            String var9 = this.username;
            StringBuilder var10 = var8.append(var9).append("</username>");
         }
      }

      if(this.digest != null) {
         if(this.digest.equals("")) {
            StringBuilder var4 = var1.append("<digest/>");
         } else {
            StringBuilder var11 = var1.append("<digest>");
            String var12 = this.digest;
            StringBuilder var13 = var11.append(var12).append("</digest>");
         }
      }

      if(this.password != null && this.digest == null) {
         if(this.password.equals("")) {
            StringBuilder var5 = var1.append("<password/>");
         } else {
            StringBuilder var14 = var1.append("<password>");
            String var15 = StringUtils.escapeForXML(this.password);
            StringBuilder var16 = var14.append(var15).append("</password>");
         }
      }

      if(this.resource != null) {
         if(this.resource.equals("")) {
            StringBuilder var6 = var1.append("<resource/>");
         } else {
            StringBuilder var17 = var1.append("<resource>");
            String var18 = this.resource;
            StringBuilder var19 = var17.append(var18).append("</resource>");
         }
      }

      StringBuilder var7 = var1.append("</query>");
      return var1.toString();
   }

   public String getDigest() {
      return this.digest;
   }

   public String getPassword() {
      return this.password;
   }

   public String getResource() {
      return this.resource;
   }

   public String getUsername() {
      return this.username;
   }

   public void setDigest(String var1) {
      this.digest = var1;
   }

   public void setDigest(String var1, String var2) {
      String var3 = StringUtils.hash(var1 + var2);
      this.digest = var3;
   }

   public void setPassword(String var1) {
      this.password = var1;
   }

   public void setResource(String var1) {
      this.resource = var1;
   }

   public void setUsername(String var1) {
      this.username = var1;
   }
}
