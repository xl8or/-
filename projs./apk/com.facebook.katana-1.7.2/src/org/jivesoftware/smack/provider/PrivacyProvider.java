package org.jivesoftware.smack.provider;

import java.util.ArrayList;
import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Privacy;
import org.jivesoftware.smack.packet.PrivacyItem;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

public class PrivacyProvider implements IQProvider {

   public PrivacyProvider() {}

   public IQ parseIQ(XmlPullParser var1) throws Exception {
      Privacy var2 = new Privacy();
      String var3 = var1.getName();
      String var4 = var1.getNamespace();
      DefaultPacketExtension var5 = new DefaultPacketExtension(var3, var4);
      var2.addExtension(var5);
      boolean var6 = false;

      while(!var6) {
         int var7 = var1.next();
         if(var7 == 2) {
            String var8;
            if(var1.getName().equals("active")) {
               var8 = var1.getAttributeValue("", "name");
               if(var8 == null) {
                  var2.setDeclineActiveList((boolean)1);
               } else {
                  var2.setActiveName(var8);
               }
            } else if(var1.getName().equals("default")) {
               var8 = var1.getAttributeValue("", "name");
               if(var8 == null) {
                  var2.setDeclineDefaultList((boolean)1);
               } else {
                  var2.setDefaultName(var8);
               }
            } else if(var1.getName().equals("list")) {
               this.parseList(var1, var2);
            }
         } else if(var7 == 3 && var1.getName().equals("query")) {
            var6 = true;
         }
      }

      return var2;
   }

   public PrivacyItem parseItem(XmlPullParser var1) throws Exception {
      String var2 = var1.getAttributeValue("", "action");
      String var3 = var1.getAttributeValue("", "order");
      String var4 = var1.getAttributeValue("", "type");
      byte var5;
      if("allow".equalsIgnoreCase(var2)) {
         var5 = 1;
      } else if("deny".equalsIgnoreCase(var2)) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      int var6 = Integer.parseInt(var3);
      PrivacyItem var7 = new PrivacyItem(var4, (boolean)var5, var6);
      String var8 = var1.getAttributeValue("", "value");
      var7.setValue(var8);
      boolean var9 = false;

      while(!var9) {
         int var10 = var1.next();
         if(var10 == 2) {
            if(var1.getName().equals("iq")) {
               var7.setFilterIQ((boolean)1);
            }

            if(var1.getName().equals("message")) {
               var7.setFilterMessage((boolean)1);
            }

            if(var1.getName().equals("presence-in")) {
               var7.setFilterPresence_in((boolean)1);
            }

            if(var1.getName().equals("presence-out")) {
               var7.setFilterPresence_out((boolean)1);
            }
         } else if(var10 == 3 && var1.getName().equals("item")) {
            boolean var11 = true;
         }
      }

      return var7;
   }

   public void parseList(XmlPullParser var1, Privacy var2) throws Exception {
      boolean var3 = false;
      String var4 = var1.getAttributeValue("", "name");
      ArrayList var5 = new ArrayList();

      while(!var3) {
         int var6 = var1.next();
         if(var6 == 2) {
            if(var1.getName().equals("item")) {
               PrivacyItem var7 = this.parseItem(var1);
               var5.add(var7);
            }
         } else if(var6 == 3 && var1.getName().equals("list")) {
            var3 = true;
         }
      }

      var2.setPrivacyList(var4, var5);
   }
}
