package org.jivesoftware.smack.packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.smack.packet.IQ;

public class Registration extends IQ {

   private Map<String, String> attributes;
   private String instructions = null;
   private boolean registered;
   private boolean remove;
   private List<String> requiredFields;


   public Registration() {
      HashMap var1 = new HashMap();
      this.attributes = var1;
      ArrayList var2 = new ArrayList();
      this.requiredFields = var2;
      this.registered = (boolean)0;
      this.remove = (boolean)0;
   }

   public void addAttribute(String var1, String var2) {
      this.attributes.put(var1, var2);
   }

   public void addRequiredField(String var1) {
      this.requiredFields.add(var1);
   }

   public Map<String, String> getAttributes() {
      return this.attributes;
   }

   public String getChildElementXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<query xmlns=\"jabber:iq:register\">");
      if(this.instructions != null && !this.remove) {
         StringBuilder var3 = var1.append("<instructions>");
         String var4 = this.instructions;
         StringBuilder var5 = var3.append(var4).append("</instructions>");
      }

      String var7;
      StringBuilder var11;
      if(this.attributes != null && this.attributes.size() > 0 && !this.remove) {
         for(Iterator var6 = this.attributes.keySet().iterator(); var6.hasNext(); var11 = var1.append("</").append(var7).append(">")) {
            var7 = (String)var6.next();
            String var8 = (String)this.attributes.get(var7);
            StringBuilder var9 = var1.append("<").append(var7).append(">");
            var1.append(var8);
         }
      } else if(this.remove) {
         StringBuilder var12 = var1.append("</remove>");
      }

      String var13 = this.getExtensionsXML();
      var1.append(var13);
      StringBuilder var15 = var1.append("</query>");
      return var1.toString();
   }

   public String getField(String var1) {
      return (String)this.attributes.get(var1);
   }

   public List<String> getFieldNames() {
      Set var1 = this.attributes.keySet();
      return new ArrayList(var1);
   }

   public String getInstructions() {
      return this.instructions;
   }

   public List<String> getRequiredFields() {
      return this.requiredFields;
   }

   public boolean isRegistered() {
      return this.registered;
   }

   public void setInstructions(String var1) {
      this.instructions = var1;
   }

   public void setPassword(String var1) {
      this.attributes.put("password", var1);
   }

   public void setRegistered(boolean var1) {
      this.registered = var1;
   }

   public void setRemove(boolean var1) {
      this.remove = var1;
   }

   public void setUsername(String var1) {
      this.attributes.put("username", var1);
   }
}
