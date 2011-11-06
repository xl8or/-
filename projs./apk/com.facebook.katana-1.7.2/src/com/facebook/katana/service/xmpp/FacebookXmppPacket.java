// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookXmppPacket.java

package com.facebook.katana.service.xmpp;

import org.jivesoftware.smack.packet.Packet;

public class FacebookXmppPacket extends Packet
{
    public static final class PacketType extends Enum
    {

        public static PacketType valueOf(String s)
        {
            return (PacketType)Enum.valueOf(com/facebook/katana/service/xmpp/FacebookXmppPacket$PacketType, s);
        }

        public static PacketType[] values()
        {
            return (PacketType[])$VALUES.clone();
        }

        private static final PacketType $VALUES[];
        public static final PacketType AUTH;
        public static final PacketType AUTH_RESPONSE;
        public static final PacketType CONNECT_TEST;
        public static final PacketType HIBERNATE;
        public static final PacketType RETRIEVE;

        static 
        {
            AUTH = new PacketType("AUTH", 0);
            AUTH_RESPONSE = new PacketType("AUTH_RESPONSE", 1);
            HIBERNATE = new PacketType("HIBERNATE", 2);
            RETRIEVE = new PacketType("RETRIEVE", 3);
            CONNECT_TEST = new PacketType("CONNECT_TEST", 4);
            PacketType apackettype[] = new PacketType[5];
            apackettype[0] = AUTH;
            apackettype[1] = AUTH_RESPONSE;
            apackettype[2] = HIBERNATE;
            apackettype[3] = RETRIEVE;
            apackettype[4] = CONNECT_TEST;
            $VALUES = apackettype;
        }

        private PacketType(String s, int i)
        {
            super(s, i);
        }
    }


    public FacebookXmppPacket(PacketType packettype)
    {
        this(packettype, null);
    }

    public FacebookXmppPacket(PacketType packettype, String s)
    {
        argument = s;
        packetType = packettype;
    }

    public String toXML()
    {
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType[];

            static 
            {
                $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType = new int[PacketType.values().length];
                NoSuchFieldError nosuchfielderror2;
                try
                {
                    $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType[PacketType.AUTH.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType[PacketType.HIBERNATE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                $SwitchMap$com$facebook$katana$service$xmpp$FacebookXmppPacket$PacketType[PacketType.CONNECT_TEST.ordinal()] = 3;
_L2:
                return;
                nosuchfielderror2;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.com.facebook.katana.service.xmpp.FacebookXmppPacket.PacketType[packetType.ordinal()];
        JVM INSTR tableswitch 1 3: default 36
    //                   1 59
    //                   2 59
    //                   3 59;
           goto _L1 _L2 _L2 _L2
_L1:
        String s = xmlStrings[packetType.ordinal()].replaceFirst("%1", argument);
_L4:
        return s;
_L2:
        s = xmlStrings[packetType.ordinal()];
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static String xmlStrings[];
    private final String argument;
    private final PacketType packetType;

    static 
    {
        String as[] = new String[5];
        as[0] = "<auth mechanism=\"X-FACEBOOK-PLATFORM\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"/>";
        as[1] = "<response xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">%1</response>";
        as[2] = "<iq type=\"set\"><hibernate xmlns=\"http://www.facebook.com/xmpp/suspend\"/></iq>";
        as[3] = "<iq type=\"get\"><retrieve xmlns=\"http://www.facebook.com/xmpp/channel\">%1</retrieve></iq>";
        as[4] = "<iq type=\"get\"><livetest/></iq>";
        xmlStrings = as;
    }
}
