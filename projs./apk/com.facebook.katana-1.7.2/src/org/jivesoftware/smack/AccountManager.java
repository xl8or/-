package org.jivesoftware.smack;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public class AccountManager {

   private boolean accountCreationSupported = 0;
   private Connection connection;
   private Registration info = null;


   public AccountManager(Connection var1) {
      this.connection = var1;
   }

   private void getRegistrationInfo() throws XMPPException {
      synchronized(this){}

      try {
         Registration var1 = new Registration();
         String var2 = this.connection.getServiceName();
         var1.setTo(var2);
         PacketFilter[] var3 = new PacketFilter[2];
         String var4 = var1.getPacketID();
         PacketIDFilter var5 = new PacketIDFilter(var4);
         var3[0] = var5;
         PacketTypeFilter var6 = new PacketTypeFilter(IQ.class);
         var3[1] = var6;
         AndFilter var7 = new AndFilter(var3);
         PacketCollector var8 = this.connection.createPacketCollector(var7);
         this.connection.sendPacket(var1);
         long var9 = (long)SmackConfiguration.getPacketReplyTimeout();
         IQ var11 = (IQ)var8.nextResult(var9);
         var8.cancel();
         if(var11 == null) {
            throw new XMPPException("No response from server.");
         }

         IQ.Type var13 = var11.getType();
         IQ.Type var14 = IQ.Type.ERROR;
         if(var13 == var14) {
            XMPPError var15 = var11.getError();
            throw new XMPPException(var15);
         }

         Registration var16 = (Registration)var11;
         this.info = var16;
      } finally {
         ;
      }

   }

   public void changePassword(String var1) throws XMPPException {
      Registration var2 = new Registration();
      IQ.Type var3 = IQ.Type.SET;
      var2.setType(var3);
      String var4 = this.connection.getServiceName();
      var2.setTo(var4);
      String var5 = StringUtils.parseName(this.connection.getUser());
      var2.setUsername(var5);
      var2.setPassword(var1);
      PacketFilter[] var6 = new PacketFilter[2];
      String var7 = var2.getPacketID();
      PacketIDFilter var8 = new PacketIDFilter(var7);
      var6[0] = var8;
      PacketTypeFilter var9 = new PacketTypeFilter(IQ.class);
      var6[1] = var9;
      AndFilter var10 = new AndFilter(var6);
      PacketCollector var11 = this.connection.createPacketCollector(var10);
      this.connection.sendPacket(var2);
      long var12 = (long)SmackConfiguration.getPacketReplyTimeout();
      IQ var14 = (IQ)var11.nextResult(var12);
      var11.cancel();
      if(var14 == null) {
         throw new XMPPException("No response from server.");
      } else {
         IQ.Type var15 = var14.getType();
         IQ.Type var16 = IQ.Type.ERROR;
         if(var15 == var16) {
            XMPPError var17 = var14.getError();
            throw new XMPPException(var17);
         }
      }
   }

   public void createAccount(String var1, String var2) throws XMPPException {
      if(!this.supportsAccountCreation()) {
         throw new XMPPException("Server does not support account creation.");
      } else {
         HashMap var3 = new HashMap();
         Iterator var4 = this.getAccountAttributes().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            var3.put(var5, "");
         }

         this.createAccount(var1, var2, var3);
      }
   }

   public void createAccount(String var1, String var2, Map<String, String> var3) throws XMPPException {
      if(!this.supportsAccountCreation()) {
         throw new XMPPException("Server does not support account creation.");
      } else {
         Registration var4 = new Registration();
         IQ.Type var5 = IQ.Type.SET;
         var4.setType(var5);
         String var6 = this.connection.getServiceName();
         var4.setTo(var6);
         var4.setUsername(var1);
         var4.setPassword(var2);
         Iterator var7 = var3.keySet().iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            String var9 = (String)var3.get(var8);
            var4.addAttribute(var8, var9);
         }

         PacketFilter[] var10 = new PacketFilter[2];
         String var11 = var4.getPacketID();
         PacketIDFilter var12 = new PacketIDFilter(var11);
         var10[0] = var12;
         PacketTypeFilter var13 = new PacketTypeFilter(IQ.class);
         var10[1] = var13;
         AndFilter var14 = new AndFilter(var10);
         PacketCollector var15 = this.connection.createPacketCollector(var14);
         this.connection.sendPacket(var4);
         long var16 = (long)SmackConfiguration.getPacketReplyTimeout();
         IQ var18 = (IQ)var15.nextResult(var16);
         var15.cancel();
         if(var18 == null) {
            throw new XMPPException("No response from server.");
         } else {
            IQ.Type var19 = var18.getType();
            IQ.Type var20 = IQ.Type.ERROR;
            if(var19 == var20) {
               XMPPError var21 = var18.getError();
               throw new XMPPException(var21);
            }
         }
      }
   }

   public void deleteAccount() throws XMPPException {
      if(!this.connection.isAuthenticated()) {
         throw new IllegalStateException("Must be logged in to delete a account.");
      } else {
         Registration var1 = new Registration();
         IQ.Type var2 = IQ.Type.SET;
         var1.setType(var2);
         String var3 = this.connection.getServiceName();
         var1.setTo(var3);
         var1.setRemove((boolean)1);
         PacketFilter[] var4 = new PacketFilter[2];
         String var5 = var1.getPacketID();
         PacketIDFilter var6 = new PacketIDFilter(var5);
         var4[0] = var6;
         PacketTypeFilter var7 = new PacketTypeFilter(IQ.class);
         var4[1] = var7;
         AndFilter var8 = new AndFilter(var4);
         PacketCollector var9 = this.connection.createPacketCollector(var8);
         this.connection.sendPacket(var1);
         long var10 = (long)SmackConfiguration.getPacketReplyTimeout();
         IQ var12 = (IQ)var9.nextResult(var10);
         var9.cancel();
         if(var12 == null) {
            throw new XMPPException("No response from server.");
         } else {
            IQ.Type var13 = var12.getType();
            IQ.Type var14 = IQ.Type.ERROR;
            if(var13 == var14) {
               XMPPError var15 = var12.getError();
               throw new XMPPException(var15);
            }
         }
      }
   }

   public String getAccountAttribute(String var1) {
      String var2;
      String var4;
      try {
         if(this.info == null) {
            this.getRegistrationInfo();
         }

         var4 = (String)this.info.getAttributes().get(var1);
      } catch (XMPPException var3) {
         var3.printStackTrace();
         var2 = null;
         return var2;
      }

      var2 = var4;
      return var2;
   }

   public Collection<String> getAccountAttributes() {
      Set var2;
      Set var4;
      label27: {
         try {
            if(this.info == null) {
               this.getRegistrationInfo();
            }

            List var1 = this.info.getRequiredFields();
            if(var1.size() > 0) {
               var2 = Collections.unmodifiableSet(new HashSet(var1));
               break label27;
            }
         } catch (XMPPException var3) {
            var3.printStackTrace();
         }

         var4 = Collections.emptySet();
         return var4;
      }

      var4 = var2;
      return var4;
   }

   public String getAccountInstructions() {
      String var1;
      String var2;
      try {
         if(this.info == null) {
            this.getRegistrationInfo();
         }

         var1 = this.info.getInstructions();
      } catch (XMPPException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   void setSupportsAccountCreation(boolean var1) {
      this.accountCreationSupported = var1;
   }

   public boolean supportsAccountCreation() {
      // $FF: Couldn't be decompiled
   }
}
