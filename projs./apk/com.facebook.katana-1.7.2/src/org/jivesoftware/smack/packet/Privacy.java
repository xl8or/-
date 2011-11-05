package org.jivesoftware.smack.packet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.PrivacyItem;

public class Privacy extends IQ {

   private String activeName;
   private boolean declineActiveList = 0;
   private boolean declineDefaultList = 0;
   private String defaultName;
   private Map<String, List<PrivacyItem>> itemLists;


   public Privacy() {
      HashMap var1 = new HashMap();
      this.itemLists = var1;
   }

   public boolean changeDefaultList(String var1) {
      boolean var2;
      if(this.getItemLists().containsKey(var1)) {
         this.setDefaultName(var1);
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void deleteList(String var1) {
      Object var2 = this.getItemLists().remove(var1);
   }

   public void deletePrivacyList(String var1) {
      Object var2 = this.getItemLists().remove(var1);
      if(this.getDefaultName() != null) {
         String var3 = this.getDefaultName();
         if(var1.equals(var3)) {
            this.setDefaultName((String)null);
         }
      }
   }

   public String getActiveName() {
      return this.activeName;
   }

   public List<PrivacyItem> getActivePrivacyList() {
      List var1;
      if(this.getActiveName() == null) {
         var1 = null;
      } else {
         Map var2 = this.getItemLists();
         String var3 = this.getActiveName();
         var1 = (List)var2.get(var3);
      }

      return var1;
   }

   public String getChildElementXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<query xmlns=\"jabber:iq:privacy\">");
      if(this.isDeclineActiveList()) {
         StringBuilder var3 = var1.append("<active/>");
      } else if(this.getActiveName() != null) {
         StringBuilder var13 = var1.append("<active name=\"");
         String var14 = this.getActiveName();
         StringBuilder var15 = var13.append(var14).append("\"/>");
      }

      if(this.isDeclineDefaultList()) {
         StringBuilder var4 = var1.append("<default/>");
      } else if(this.getDefaultName() != null) {
         StringBuilder var16 = var1.append("<default name=\"");
         String var17 = this.getDefaultName();
         StringBuilder var18 = var16.append(var17).append("\"/>");
      }

      Iterator var5 = this.getItemLists().entrySet().iterator();

      while(var5.hasNext()) {
         Entry var6 = (Entry)var5.next();
         String var7 = (String)var6.getKey();
         List var8 = (List)var6.getValue();
         if(var8.isEmpty()) {
            StringBuilder var9 = var1.append("<list name=\"").append(var7).append("\"/>");
         } else {
            StringBuilder var19 = var1.append("<list name=\"").append(var7).append("\">");
         }

         Iterator var10 = var8.iterator();

         while(var10.hasNext()) {
            String var11 = ((PrivacyItem)var10.next()).toXML();
            var1.append(var11);
         }

         if(!var8.isEmpty()) {
            StringBuilder var20 = var1.append("</list>");
         }
      }

      String var21 = this.getExtensionsXML();
      var1.append(var21);
      StringBuilder var23 = var1.append("</query>");
      return var1.toString();
   }

   public String getDefaultName() {
      return this.defaultName;
   }

   public List<PrivacyItem> getDefaultPrivacyList() {
      List var1;
      if(this.getDefaultName() == null) {
         var1 = null;
      } else {
         Map var2 = this.getItemLists();
         String var3 = this.getDefaultName();
         var1 = (List)var2.get(var3);
      }

      return var1;
   }

   public PrivacyItem getItem(String var1, int var2) {
      Iterator var3 = this.getPrivacyList(var1).iterator();
      PrivacyItem var4 = null;

      while(var4 == null && var3.hasNext()) {
         PrivacyItem var5 = (PrivacyItem)var3.next();
         if(var5.getOrder() == var2) {
            var4 = var5;
         }
      }

      return var4;
   }

   public Map<String, List<PrivacyItem>> getItemLists() {
      return this.itemLists;
   }

   public List<PrivacyItem> getPrivacyList(String var1) {
      return (List)this.getItemLists().get(var1);
   }

   public Set<String> getPrivacyListNames() {
      return this.itemLists.keySet();
   }

   public boolean isDeclineActiveList() {
      return this.declineActiveList;
   }

   public boolean isDeclineDefaultList() {
      return this.declineDefaultList;
   }

   public void setActiveName(String var1) {
      this.activeName = var1;
   }

   public List<PrivacyItem> setActivePrivacyList() {
      String var1 = this.getDefaultName();
      this.setActiveName(var1);
      Map var2 = this.getItemLists();
      String var3 = this.getActiveName();
      return (List)var2.get(var3);
   }

   public void setDeclineActiveList(boolean var1) {
      this.declineActiveList = var1;
   }

   public void setDeclineDefaultList(boolean var1) {
      this.declineDefaultList = var1;
   }

   public void setDefaultName(String var1) {
      this.defaultName = var1;
   }

   public List setPrivacyList(String var1, List<PrivacyItem> var2) {
      this.getItemLists().put(var1, var2);
      return var2;
   }
}
