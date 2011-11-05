package com.facebook.katana.service.xmpp;

import org.jivesoftware.smack.packet.Packet;

public class FacebookXmppPacket extends Packet {

   public static String[] xmlStrings;
   private final String argument;
   private final FacebookXmppPacket.PacketType packetType;


   static {
      String[] var0 = new String[]{"<auth mechanism=\"X-FACEBOOK-PLATFORM\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"/>", "<response xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">%1</response>", "<iq type=\"set\"><hibernate xmlns=\"http://www.facebook.com/xmpp/suspend\"/></iq>", "<iq type=\"get\"><retrieve xmlns=\"http://www.facebook.com/xmpp/channel\">%1</retrieve></iq>", "<iq type=\"get\"><livetest/></iq>"};
      xmlStrings = var0;
   }

   public FacebookXmppPacket(FacebookXmppPacket.PacketType var1) {
      this(var1, (String)null);
   }

   public FacebookXmppPacket(FacebookXmppPacket.PacketType var1, String var2) {
      this.argument = var2;
      this.packetType = var1;
   }

   public String toXML() {
      int[] var1 = FacebookXmppPacket.1.$SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType;
      int var2 = this.packetType.ordinal();
      String var7;
      switch(var1[var2]) {
      case 1:
      case 2:
      case 3:
         String[] var8 = xmlStrings;
         int var9 = this.packetType.ordinal();
         var7 = var8[var9];
         break;
      default:
         String[] var3 = xmlStrings;
         int var4 = this.packetType.ordinal();
         String var5 = var3[var4];
         String var6 = this.argument;
         var7 = var5.replaceFirst("%1", var6);
      }

      return var7;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType = new int[FacebookXmppPacket.PacketType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType;
            int var1 = FacebookXmppPacket.PacketType.AUTH.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType;
            int var3 = FacebookXmppPacket.PacketType.HIBERNATE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType;
            int var5 = FacebookXmppPacket.PacketType.CONNECT_TEST.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   public static enum PacketType {

      // $FF: synthetic field
      private static final FacebookXmppPacket.PacketType[] $VALUES;
      AUTH("AUTH", 0),
      AUTH_RESPONSE("AUTH_RESPONSE", 1),
      CONNECT_TEST("CONNECT_TEST", 4),
      HIBERNATE("HIBERNATE", 2),
      RETRIEVE("RETRIEVE", 3);


      static {
         FacebookXmppPacket.PacketType[] var0 = new FacebookXmppPacket.PacketType[5];
         FacebookXmppPacket.PacketType var1 = AUTH;
         var0[0] = var1;
         FacebookXmppPacket.PacketType var2 = AUTH_RESPONSE;
         var0[1] = var2;
         FacebookXmppPacket.PacketType var3 = HIBERNATE;
         var0[2] = var3;
         FacebookXmppPacket.PacketType var4 = RETRIEVE;
         var0[3] = var4;
         FacebookXmppPacket.PacketType var5 = CONNECT_TEST;
         var0[4] = var5;
         $VALUES = var0;
      }

      private PacketType(String var1, int var2) {}
   }
}
