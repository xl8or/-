package com.facebook.katana.service.xmpp;

import android.os.Bundle;
import com.facebook.katana.service.xmpp.FacebookXmppPacket;
import com.facebook.katana.util.URLQueryBuilder;
import java.io.IOException;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;

public class FBChatAuthMechanism extends SASLMechanism {

   public static final String FB_AUTH_MECHANISM = "X-FACEBOOK-PLATFORM";
   private String mSessionKey;
   private String mSessionSecret;


   public FBChatAuthMechanism(SASLAuthentication var1) {
      super(var1);
   }

   public void authenticate(String var1, String var2, String var3) throws IOException, XMPPException {
      if(var1 != null && var3 != null) {
         this.mSessionSecret = var3;
         this.mSessionKey = var1;
         SASLAuthentication var4 = this.getSASLAuthentication();
         FacebookXmppPacket.PacketType var5 = FacebookXmppPacket.PacketType.AUTH;
         FacebookXmppPacket var6 = new FacebookXmppPacket(var5);
         var4.send(var6);
      } else {
         throw new IllegalArgumentException("Invalid parameters!");
      }
   }

   public void challengeReceived(String var1) throws IOException {
      String var2 = "";
      if(var1 != null) {
         byte[] var3 = Base64.decode(var1);
         String var4 = new String(var3);
         Long var5 = Long.valueOf(System.currentTimeMillis() / 1000L);
         Bundle var6 = URLQueryBuilder.parseQueryString(var4);
         String var7 = Long.toString(350685531728L);
         var6.putString("api_key", var7);
         String var8 = Long.toString(var5.longValue());
         var6.putString("call_id", var8);
         String var9 = this.mSessionKey;
         var6.putString("session_key", var9);
         var6.putString("v", "1.0");
         String var10 = this.mSessionSecret;
         var2 = Base64.encodeBytes(URLQueryBuilder.buildSignedQueryString(var6, var10).toString().getBytes(), 8);
      }

      SASLAuthentication var11 = this.getSASLAuthentication();
      FacebookXmppPacket.PacketType var12 = FacebookXmppPacket.PacketType.AUTH_RESPONSE;
      FacebookXmppPacket var13 = new FacebookXmppPacket(var12, var2);
      var11.send(var13);
   }

   protected String getName() {
      return "X-FACEBOOK-PLATFORM";
   }
}
