package org.jivesoftware.smack;

import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.PasswordCallback;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.UserAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.Authentication;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.XMPPError;

class NonSASLAuthentication implements UserAuthentication {

   private Connection connection;


   public NonSASLAuthentication(Connection var1) {
      this.connection = var1;
   }

   public String authenticate(String var1, String var2, String var3) throws XMPPException {
      Authentication var4 = new Authentication();
      IQ.Type var5 = IQ.Type.GET;
      var4.setType(var5);
      var4.setUsername(var1);
      Connection var6 = this.connection;
      String var7 = var4.getPacketID();
      PacketIDFilter var8 = new PacketIDFilter(var7);
      PacketCollector var9 = var6.createPacketCollector(var8);
      this.connection.sendPacket(var4);
      long var10 = (long)SmackConfiguration.getPacketReplyTimeout();
      IQ var12 = (IQ)var9.nextResult(var10);
      if(var12 == null) {
         throw new XMPPException("No response from the server.");
      } else {
         IQ.Type var13 = var12.getType();
         IQ.Type var14 = IQ.Type.ERROR;
         if(var13 == var14) {
            XMPPError var15 = var12.getError();
            throw new XMPPException(var15);
         } else {
            Authentication var16 = (Authentication)var12;
            var9.cancel();
            Authentication var17 = new Authentication();
            var17.setUsername(var1);
            if(var16.getDigest() != null) {
               String var18 = this.connection.getConnectionID();
               var17.setDigest(var18, var2);
            } else {
               if(var16.getPassword() == null) {
                  throw new XMPPException("Server does not support compatible authentication mechanism.");
               }

               var17.setPassword(var2);
            }

            var17.setResource(var3);
            Connection var19 = this.connection;
            String var20 = var17.getPacketID();
            PacketIDFilter var21 = new PacketIDFilter(var20);
            PacketCollector var22 = var19.createPacketCollector(var21);
            this.connection.sendPacket(var17);
            long var23 = (long)SmackConfiguration.getPacketReplyTimeout();
            IQ var25 = (IQ)var22.nextResult(var23);
            if(var25 == null) {
               throw new XMPPException("Authentication failed.");
            } else {
               IQ.Type var26 = var25.getType();
               IQ.Type var27 = IQ.Type.ERROR;
               if(var26 == var27) {
                  XMPPError var28 = var25.getError();
                  throw new XMPPException(var28);
               } else {
                  var22.cancel();
                  return var25.getTo();
               }
            }
         }
      }
   }

   public String authenticate(String var1, String var2, CallbackHandler var3) throws XMPPException {
      PasswordCallback var4 = new PasswordCallback("Password: ", (boolean)0);

      try {
         Callback[] var5 = new Callback[]{var4};
         var3.handle(var5);
         String var6 = String.valueOf(var4.getPassword());
         String var7 = this.authenticate(var1, var6, var2);
         return var7;
      } catch (Exception var9) {
         throw new XMPPException("Unable to determine password.", var9);
      }
   }

   public String authenticateAnonymously() throws XMPPException {
      Authentication var1 = new Authentication();
      Connection var2 = this.connection;
      String var3 = var1.getPacketID();
      PacketIDFilter var4 = new PacketIDFilter(var3);
      PacketCollector var5 = var2.createPacketCollector(var4);
      this.connection.sendPacket(var1);
      long var6 = (long)SmackConfiguration.getPacketReplyTimeout();
      IQ var8 = (IQ)var5.nextResult(var6);
      if(var8 == null) {
         throw new XMPPException("Anonymous login failed.");
      } else {
         IQ.Type var9 = var8.getType();
         IQ.Type var10 = IQ.Type.ERROR;
         if(var9 == var10) {
            XMPPError var11 = var8.getError();
            throw new XMPPException(var11);
         } else {
            var5.cancel();
            String var16;
            if(var8.getTo() != null) {
               var16 = var8.getTo();
            } else {
               StringBuilder var12 = new StringBuilder();
               String var13 = this.connection.getServiceName();
               StringBuilder var14 = var12.append(var13).append("/");
               String var15 = ((Authentication)var8).getResource();
               var16 = var14.append(var15).toString();
            }

            return var16;
         }
      }
   }
}
