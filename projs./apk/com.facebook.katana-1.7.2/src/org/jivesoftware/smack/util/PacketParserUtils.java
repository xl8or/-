// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketParserUtils.java

package org.jivesoftware.smack.util;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.provider.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

// Referenced classes of package org.jivesoftware.smack.util:
//            StringUtils

public class PacketParserUtils
{

    public PacketParserUtils()
    {
    }

    private static Object decode(Class class1, String s)
        throws Exception
    {
        Object obj;
        if(class1.getName().equals("java.lang.String"))
            obj = s;
        else
        if(class1.getName().equals("boolean"))
            obj = Boolean.valueOf(s);
        else
        if(class1.getName().equals("int"))
            obj = Integer.valueOf(s);
        else
        if(class1.getName().equals("long"))
            obj = Long.valueOf(s);
        else
        if(class1.getName().equals("float"))
            obj = Float.valueOf(s);
        else
        if(class1.getName().equals("double"))
            obj = Double.valueOf(s);
        else
        if(class1.getName().equals("java.lang.Class"))
            obj = Class.forName(s);
        else
            obj = null;
        return obj;
    }

    private static String getLanguageAttribute(XmlPullParser xmlpullparser)
    {
        int i = 0;
_L3:
        String s1;
        if(i >= xmlpullparser.getAttributeCount())
            break MISSING_BLOCK_LABEL_69;
        s1 = xmlpullparser.getAttributeName(i);
        if(!"xml:lang".equals(s1) && (!"lang".equals(s1) || !"xml".equals(xmlpullparser.getAttributePrefix(i)))) goto _L2; else goto _L1
_L1:
        String s = xmlpullparser.getAttributeValue(i);
_L4:
        return s;
_L2:
        i++;
          goto _L3
        s = null;
          goto _L4
    }

    private static Authentication parseAuthentication(XmlPullParser xmlpullparser)
        throws Exception
    {
        Authentication authentication = new Authentication();
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("username"))
                    authentication.setUsername(xmlpullparser.nextText());
                else
                if(xmlpullparser.getName().equals("password"))
                    authentication.setPassword(xmlpullparser.nextText());
                else
                if(xmlpullparser.getName().equals("digest"))
                    authentication.setDigest(xmlpullparser.nextText());
                else
                if(xmlpullparser.getName().equals("resource"))
                    authentication.setResource(xmlpullparser.nextText());
            } else
            if(i == 3 && xmlpullparser.getName().equals("query"))
                flag = true;
        } while(true);
        return authentication;
    }

    public static Collection parseCompressionMethods(XmlPullParser xmlpullparser)
        throws IOException, XmlPullParserException
    {
        ArrayList arraylist = new ArrayList();
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("method"))
                    arraylist.add(xmlpullparser.nextText());
            } else
            if(i == 3 && xmlpullparser.getName().equals("compression"))
                flag = true;
        } while(true);
        return arraylist;
    }

    private static String parseContent(XmlPullParser xmlpullparser)
        throws XmlPullParserException, IOException
    {
        String s = "";
        for(int i = xmlpullparser.getDepth(); xmlpullparser.next() != 3 || xmlpullparser.getDepth() != i;)
            s = (new StringBuilder()).append(s).append(xmlpullparser.getText()).toString();

        return s;
    }

    public static XMPPError parseError(XmlPullParser xmlpullparser)
        throws Exception
    {
        ArrayList arraylist = new ArrayList();
        String s = null;
        String s1 = "-1";
        int i = 0;
        while(i < xmlpullparser.getAttributeCount()) 
        {
            boolean flag;
            String s2;
            String s3;
            org.jivesoftware.smack.packet.XMPPError.Type type;
            org.jivesoftware.smack.packet.XMPPError.Type type1;
            IllegalArgumentException illegalargumentexception;
            org.jivesoftware.smack.packet.XMPPError.Type type2;
            int j;
            String s4;
            String s5;
            String s6;
            String s7;
            String s8;
            String s9;
            if(xmlpullparser.getAttributeName(i).equals("code"))
                s9 = xmlpullparser.getAttributeValue("", "code");
            else
                s9 = s1;
            if(xmlpullparser.getAttributeName(i).equals("type"))
                s = xmlpullparser.getAttributeValue("", "type");
            i++;
            s1 = s9;
        }
        flag = false;
        s2 = null;
        s3 = null;
        while(!flag) 
        {
            j = xmlpullparser.next();
            if(j == 2)
            {
                if(xmlpullparser.getName().equals("text"))
                {
                    s7 = xmlpullparser.nextText();
                    s8 = s2;
                    s5 = s7;
                    s4 = s8;
                } else
                {
                    s4 = xmlpullparser.getName();
                    s6 = xmlpullparser.getNamespace();
                    if(!"urn:ietf:params:xml:ns:xmpp-stanzas".equals(s6))
                    {
                        arraylist.add(parsePacketExtension(s4, s6, xmlpullparser));
                        s4 = s2;
                    }
                    s5 = s3;
                }
            } else
            if(j == 3 && xmlpullparser.getName().equals("error"))
            {
                flag = true;
                s4 = s2;
                s5 = s3;
            } else
            {
                s4 = s2;
                s5 = s3;
            }
            s3 = s5;
            s2 = s4;
        }
        type = org.jivesoftware.smack.packet.XMPPError.Type.CANCEL;
        if(s == null)
            break MISSING_BLOCK_LABEL_275;
        type2 = org.jivesoftware.smack.packet.XMPPError.Type.valueOf(s.toUpperCase());
        type = type2;
        type1 = type;
_L2:
        return new XMPPError(Integer.parseInt(s1), type1, s2, s3, arraylist);
        illegalargumentexception;
        illegalargumentexception.printStackTrace();
        type1 = type;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static IQ parseIQ(XmlPullParser xmlpullparser, Connection connection)
        throws Exception
    {
        String s;
        String s1;
        String s2;
        org.jivesoftware.smack.packet.IQ.Type type;
        boolean flag;
        XMPPError xmpperror;
        Object obj;
        s = xmlpullparser.getAttributeValue("", "id");
        s1 = xmlpullparser.getAttributeValue("", "to");
        s2 = xmlpullparser.getAttributeValue("", "from");
        type = org.jivesoftware.smack.packet.IQ.Type.fromString(xmlpullparser.getAttributeValue("", "type"));
        flag = false;
        xmpperror = null;
        obj = null;
_L5:
        if(flag) goto _L2; else goto _L1
_L1:
        int i = xmlpullparser.next();
        if(i != 2) goto _L4; else goto _L3
_L3:
        boolean flag1;
        XMPPError xmpperror1;
        Object obj2;
        XMPPError xmpperror2;
        String s3 = xmlpullparser.getName();
        String s4 = xmlpullparser.getNamespace();
        boolean flag2;
        if(s3.equals("error"))
        {
            xmpperror2 = parseError(xmlpullparser);
            obj2 = obj;
        } else
        if(s3.equals("query") && s4.equals("jabber:iq:auth"))
        {
            Authentication authentication = parseAuthentication(xmlpullparser);
            XMPPError xmpperror8 = xmpperror;
            obj2 = authentication;
            xmpperror2 = xmpperror8;
        } else
        if(s3.equals("query") && s4.equals("jabber:iq:roster"))
        {
            RosterPacket rosterpacket = parseRoster(xmlpullparser);
            XMPPError xmpperror7 = xmpperror;
            obj2 = rosterpacket;
            xmpperror2 = xmpperror7;
        } else
        if(s3.equals("query") && s4.equals("jabber:iq:register"))
        {
            Registration registration = parseRegistration(xmlpullparser);
            XMPPError xmpperror6 = xmpperror;
            obj2 = registration;
            xmpperror2 = xmpperror6;
        } else
        if(s3.equals("bind") && s4.equals("urn:ietf:params:xml:ns:xmpp-bind"))
        {
            Bind bind = parseResourceBinding(xmlpullparser);
            XMPPError xmpperror5 = xmpperror;
            obj2 = bind;
            xmpperror2 = xmpperror5;
        } else
        {
            Object obj3 = ProviderManager.getInstance().getIQProvider(s3, s4);
            if(obj3 == null)
                break MISSING_BLOCK_LABEL_584;
            if(obj3 instanceof IQProvider)
            {
                IQ iq2 = ((IQProvider)obj3).parseIQ(xmlpullparser);
                XMPPError xmpperror4 = xmpperror;
                obj2 = iq2;
                xmpperror2 = xmpperror4;
            } else
            {
                if(!(obj3 instanceof Class))
                    break MISSING_BLOCK_LABEL_584;
                IQ iq1 = (IQ)parseWithIntrospection(s3, (Class)obj3, xmlpullparser);
                XMPPError xmpperror3 = xmpperror;
                obj2 = iq1;
                xmpperror2 = xmpperror3;
            }
        }
_L13:
        flag2 = flag;
        xmpperror1 = xmpperror2;
        flag1 = flag2;
_L6:
        obj = obj2;
        xmpperror = xmpperror1;
        flag = flag1;
          goto _L5
_L4:
        Object obj1;
        if(i == 3 && xmlpullparser.getName().equals("iq"))
        {
            flag1 = true;
            xmpperror1 = xmpperror;
            obj2 = obj;
        } else
        {
            flag1 = flag;
            xmpperror1 = xmpperror;
            obj2 = obj;
        }
          goto _L6
_L2:
        if(obj != null) goto _L8; else goto _L7
_L7:
        if(org.jivesoftware.smack.packet.IQ.Type.GET != type && org.jivesoftware.smack.packet.IQ.Type.SET != type) goto _L10; else goto _L9
_L9:
        IQ iq = new IQ() {

            public String getChildElementXML()
            {
                return null;
            }

        }
;
        iq.setPacketID(s);
        iq.setTo(s2);
        iq.setFrom(s1);
        iq.setType(org.jivesoftware.smack.packet.IQ.Type.ERROR);
        iq.setError(new XMPPError(org.jivesoftware.smack.packet.XMPPError.Condition.feature_not_implemented));
        connection.sendPacket(iq);
        obj1 = null;
_L11:
        return ((IQ) (obj1));
_L10:
        obj1 = new IQ() {

            public String getChildElementXML()
            {
                return null;
            }

        }
;
_L12:
        ((IQ) (obj1)).setPacketID(s);
        ((IQ) (obj1)).setTo(s1);
        ((IQ) (obj1)).setFrom(s2);
        ((IQ) (obj1)).setType(type);
        ((IQ) (obj1)).setError(xmpperror);
        if(true) goto _L11; else goto _L8
_L8:
        obj1 = obj;
          goto _L12
        xmpperror2 = xmpperror;
        obj2 = obj;
          goto _L13
    }

    public static Collection parseMechanisms(XmlPullParser xmlpullparser)
        throws Exception
    {
        ArrayList arraylist = new ArrayList();
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("mechanism"))
                    arraylist.add(xmlpullparser.nextText());
            } else
            if(i == 3 && xmlpullparser.getName().equals("mechanisms"))
                flag = true;
        } while(true);
        return arraylist;
    }

    public static Packet parseMessage(XmlPullParser xmlpullparser)
        throws Exception
    {
        Message message = new Message();
        String s = xmlpullparser.getAttributeValue("", "id");
        if(s == null)
            s = "ID_NOT_AVAILABLE";
        message.setPacketID(s);
        message.setTo(xmlpullparser.getAttributeValue("", "to"));
        message.setFrom(xmlpullparser.getAttributeValue("", "from"));
        message.setType(org.jivesoftware.smack.packet.Message.Type.fromString(xmlpullparser.getAttributeValue("", "type")));
        String s1 = getLanguageAttribute(xmlpullparser);
        String s2;
        boolean flag;
        Map map;
        if(s1 != null && !"".equals(s1.trim()))
            message.setLanguage(s1);
        else
            s1 = Packet.getDefaultLanguage();
        s2 = null;
        flag = false;
        map = null;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                String s4 = xmlpullparser.getName();
                String s5 = xmlpullparser.getNamespace();
                if(s4.equals("subject"))
                {
                    String s8 = getLanguageAttribute(xmlpullparser);
                    if(s8 == null)
                        s8 = s1;
                    String s9 = parseContent(xmlpullparser);
                    if(message.getSubject(s8) == null)
                        message.addSubject(s8, s9);
                } else
                if(s4.equals("body"))
                {
                    String s6 = getLanguageAttribute(xmlpullparser);
                    if(s6 == null)
                        s6 = s1;
                    String s7 = parseContent(xmlpullparser);
                    if(message.getBody(s6) == null)
                        message.addBody(s6, s7);
                } else
                if(s4.equals("thread"))
                {
                    if(s2 == null)
                        s2 = xmlpullparser.nextText();
                } else
                if(s4.equals("error"))
                    message.setError(parseError(xmlpullparser));
                else
                if(s4.equals("properties") && s5.equals("http://www.jivesoftware.com/xmlns/xmpp/properties"))
                    map = parseProperties(xmlpullparser);
                else
                    message.addExtension(parsePacketExtension(s4, s5, xmlpullparser));
            } else
            if(i == 3 && xmlpullparser.getName().equals("message"))
                flag = true;
        } while(true);
        message.setThread(s2);
        if(map != null)
        {
            String s3;
            for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); message.setProperty(s3, map.get(s3)))
                s3 = (String)iterator.next();

        }
        return message;
    }

    public static PacketExtension parsePacketExtension(String s, String s1, XmlPullParser xmlpullparser)
        throws Exception
    {
        Object obj = ProviderManager.getInstance().getExtensionProvider(s, s1);
        if(obj == null) goto _L2; else goto _L1
_L1:
        if(!(obj instanceof PacketExtensionProvider)) goto _L4; else goto _L3
_L3:
        Object obj1 = ((PacketExtensionProvider)obj).parseExtension(xmlpullparser);
_L5:
        return ((PacketExtension) (obj1));
_L4:
        if(!(obj instanceof Class))
            break; /* Loop/switch isn't completed */
        obj1 = (PacketExtension)parseWithIntrospection(s, (Class)obj, xmlpullparser);
        if(true) goto _L5; else goto _L2
_L2:
        obj1 = new DefaultPacketExtension(s, s1);
        boolean flag = false;
        while(!flag) 
        {
            int i = xmlpullparser.next();
            if(i == 2)
            {
                String s2 = xmlpullparser.getName();
                if(xmlpullparser.isEmptyElementTag())
                    ((DefaultPacketExtension) (obj1)).setValue(s2, "");
                else
                if(xmlpullparser.next() == 4)
                    ((DefaultPacketExtension) (obj1)).setValue(s2, xmlpullparser.getText());
            } else
            if(i == 3 && xmlpullparser.getName().equals(s))
                flag = true;
        }
        if(true) goto _L5; else goto _L6
_L6:
    }

    public static Presence parsePresence(XmlPullParser xmlpullparser)
        throws Exception
    {
        org.jivesoftware.smack.packet.Presence.Type type;
        String s;
        type = org.jivesoftware.smack.packet.Presence.Type.available;
        s = xmlpullparser.getAttributeValue("", "type");
        if(s == null || s.equals(""))
            break MISSING_BLOCK_LABEL_37;
        org.jivesoftware.smack.packet.Presence.Type type1 = org.jivesoftware.smack.packet.Presence.Type.valueOf(s);
        type = type1;
_L1:
        Presence presence = new Presence(type);
        presence.setTo(xmlpullparser.getAttributeValue("", "to"));
        presence.setFrom(xmlpullparser.getAttributeValue("", "from"));
        String s1 = xmlpullparser.getAttributeValue("", "id");
        String s2;
        String s3;
        boolean flag;
        if(s1 == null)
            s2 = "ID_NOT_AVAILABLE";
        else
            s2 = s1;
        presence.setPacketID(s2);
        s3 = getLanguageAttribute(xmlpullparser);
        if(s3 != null && !"".equals(s3.trim()))
            presence.setLanguage(s3);
        if(s1 == null)
            s1 = "ID_NOT_AVAILABLE";
        presence.setPacketID(s1);
        flag = false;
        while(!flag) 
        {
            int i = xmlpullparser.next();
            boolean flag1;
            if(i == 2)
            {
                String s4 = xmlpullparser.getName();
                String s5 = xmlpullparser.getNamespace();
                IllegalArgumentException illegalargumentexception2;
                if(s4.equals("status"))
                    presence.setStatus(xmlpullparser.nextText());
                else
                if(s4.equals("priority"))
                    try
                    {
                        presence.setPriority(Integer.parseInt(xmlpullparser.nextText()));
                    }
                    catch(NumberFormatException numberformatexception) { }
                    catch(IllegalArgumentException illegalargumentexception1)
                    {
                        presence.setPriority(0);
                    }
                else
                if(s4.equals("show"))
                {
                    String s7 = xmlpullparser.nextText();
                    try
                    {
                        presence.setMode(org.jivesoftware.smack.packet.Presence.Mode.valueOf(s7));
                    }
                    catch(IllegalArgumentException illegalargumentexception)
                    {
                        System.err.println((new StringBuilder()).append("Found invalid presence mode ").append(s7).toString());
                    }
                } else
                if(s4.equals("error"))
                    presence.setError(parseError(xmlpullparser));
                else
                if(s4.equals("properties") && s5.equals("http://www.jivesoftware.com/xmlns/xmpp/properties"))
                {
                    Map map = parseProperties(xmlpullparser);
                    Iterator iterator = map.keySet().iterator();
                    while(iterator.hasNext()) 
                    {
                        String s6 = (String)iterator.next();
                        presence.setProperty(s6, map.get(s6));
                    }
                } else
                {
                    presence.addExtension(parsePacketExtension(s4, s5, xmlpullparser));
                }
                flag1 = flag;
            } else
            if(i == 3 && xmlpullparser.getName().equals("presence"))
                flag1 = true;
            else
                flag1 = flag;
            flag = flag1;
        }
        break MISSING_BLOCK_LABEL_505;
        illegalargumentexception2;
        System.err.println((new StringBuilder()).append("Found invalid presence type ").append(s).toString());
          goto _L1
        return presence;
    }

    public static Map parseProperties(XmlPullParser xmlpullparser)
        throws Exception
    {
        HashMap hashmap = new HashMap();
_L2:
        int i;
        String s;
        String s1;
        String s2;
        boolean flag;
        Object obj;
        i = xmlpullparser.next();
        if(i != 2 || !xmlpullparser.getName().equals("property"))
            continue; /* Loop/switch isn't completed */
        s = null;
        s1 = null;
        s2 = null;
        flag = false;
        obj = null;
_L5:
        if(flag) goto _L2; else goto _L1
_L1:
        int j = xmlpullparser.next();
        if(j != 2) goto _L4; else goto _L3
_L3:
        String s3 = xmlpullparser.getName();
        if(s3.equals("name"))
            s2 = xmlpullparser.nextText();
        else
        if(s3.equals("value"))
        {
            String s4 = xmlpullparser.getAttributeValue("", "type");
            String s5 = xmlpullparser.nextText();
            s1 = s4;
            s = s5;
        }
          goto _L5
_L4:
        if(j != 3 || !xmlpullparser.getName().equals("property")) goto _L5; else goto _L6
_L6:
        if("integer".equals(s1))
            obj = Integer.valueOf(s);
        else
        if("long".equals(s1))
            obj = Long.valueOf(s);
        else
        if("float".equals(s1))
            obj = Float.valueOf(s);
        else
        if("double".equals(s1))
        {
            obj = Double.valueOf(s);
        } else
        {
label0:
            {
                if(!"boolean".equals(s1))
                    break label0;
                obj = Boolean.valueOf(s);
            }
        }
_L9:
        if(s2 != null && obj != null)
            hashmap.put(s2, obj);
        flag = true;
          goto _L5
        if(!"string".equals(s1)) goto _L8; else goto _L7
_L7:
        obj = s;
          goto _L9
_L8:
        if(!"java-object".equals(s1)) goto _L9; else goto _L10
_L10:
        Object obj1 = (new ObjectInputStream(new ByteArrayInputStream(StringUtils.decodeBase64(s)))).readObject();
        obj = obj1;
          goto _L9
        Exception exception;
        exception;
        exception.printStackTrace();
          goto _L9
        if(i != 3 || !xmlpullparser.getName().equals("properties")) goto _L2; else goto _L11
_L11:
        return hashmap;
    }

    private static Registration parseRegistration(XmlPullParser xmlpullparser)
        throws Exception
    {
        Registration registration = new Registration();
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getNamespace().equals("jabber:iq:register"))
                {
                    String s = xmlpullparser.getName();
                    if(xmlpullparser.next() == 4)
                    {
                        String s1 = xmlpullparser.getText();
                        if(s.equals("instructions"))
                            registration.setInstructions(s1);
                        else
                            registration.addAttribute(s, s1);
                    } else
                    if(s.equals("registered"))
                        registration.setRegistered(true);
                    else
                        registration.addRequiredField(s);
                } else
                {
                    registration.addExtension(parsePacketExtension(xmlpullparser.getName(), xmlpullparser.getNamespace(), xmlpullparser));
                }
            } else
            if(i == 3 && xmlpullparser.getName().equals("query"))
                flag = true;
        } while(true);
        return registration;
    }

    private static Bind parseResourceBinding(XmlPullParser xmlpullparser)
        throws IOException, XmlPullParserException
    {
        Bind bind = new Bind();
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("resource"))
                    bind.setResource(xmlpullparser.nextText());
                else
                if(xmlpullparser.getName().equals("jid"))
                    bind.setJid(xmlpullparser.nextText());
            } else
            if(i == 3 && xmlpullparser.getName().equals("bind"))
                flag = true;
        } while(true);
        return bind;
    }

    private static RosterPacket parseRoster(XmlPullParser xmlpullparser)
        throws Exception
    {
        RosterPacket rosterpacket = new RosterPacket();
        boolean flag = false;
        org.jivesoftware.smack.packet.RosterPacket.Item item = null;
        do
        {
            if(flag)
                break;
            if(xmlpullparser.getEventType() == 2 && xmlpullparser.getName().equals("query"))
                rosterpacket.setVersion(xmlpullparser.getAttributeValue(null, "ver"));
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("item"))
                {
                    org.jivesoftware.smack.packet.RosterPacket.Item item1 = new org.jivesoftware.smack.packet.RosterPacket.Item(xmlpullparser.getAttributeValue("", "jid"), xmlpullparser.getAttributeValue("", "name"));
                    item1.setItemStatus(org.jivesoftware.smack.packet.RosterPacket.ItemStatus.fromString(xmlpullparser.getAttributeValue("", "ask")));
                    String s = xmlpullparser.getAttributeValue("", "subscription");
                    String s1;
                    if(s == null)
                        s = "none";
                    item1.setItemType(org.jivesoftware.smack.packet.RosterPacket.ItemType.valueOf(s));
                    item = item1;
                }
                if(xmlpullparser.getName().equals("group") && item != null)
                {
                    s1 = xmlpullparser.nextText();
                    if(s1 != null && s1.trim().length() > 0)
                        item.addGroupName(s1);
                }
            } else
            if(i == 3)
            {
                if(xmlpullparser.getName().equals("item"))
                    rosterpacket.addRosterItem(item);
                if(xmlpullparser.getName().equals("query"))
                    flag = true;
            }
        } while(true);
        return rosterpacket;
    }

    public static org.jivesoftware.smack.sasl.SASLMechanism.Failure parseSASLFailure(XmlPullParser xmlpullparser)
        throws Exception
    {
        String s = null;
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(!xmlpullparser.getName().equals("failure"))
                    s = xmlpullparser.getName();
            } else
            if(i == 3 && xmlpullparser.getName().equals("failure"))
                flag = true;
        } while(true);
        return new org.jivesoftware.smack.sasl.SASLMechanism.Failure(s);
    }

    public static StreamError parseStreamError(XmlPullParser xmlpullparser)
        throws IOException, XmlPullParserException
    {
        StreamError streamerror = null;
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
                streamerror = new StreamError(xmlpullparser.getName());
            else
            if(i == 3 && xmlpullparser.getName().equals("error"))
                flag = true;
        } while(true);
        return streamerror;
    }

    public static Object parseWithIntrospection(String s, Class class1, XmlPullParser xmlpullparser)
        throws Exception
    {
        Object obj = class1.newInstance();
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                String s1 = xmlpullparser.getName();
                String s2 = xmlpullparser.nextText();
                Class class2 = obj.getClass().getMethod((new StringBuilder()).append("get").append(Character.toUpperCase(s1.charAt(0))).append(s1.substring(1)).toString(), new Class[0]).getReturnType();
                Object obj1 = decode(class2, s2);
                Class class3 = obj.getClass();
                String s3 = (new StringBuilder()).append("set").append(Character.toUpperCase(s1.charAt(0))).append(s1.substring(1)).toString();
                Class aclass[] = new Class[1];
                aclass[0] = class2;
                Method method = class3.getMethod(s3, aclass);
                Object aobj[] = new Object[1];
                aobj[0] = obj1;
                method.invoke(obj, aobj);
            } else
            if(i == 3 && xmlpullparser.getName().equals(s))
                flag = true;
        } while(true);
        return obj;
    }

    private static final String PROPERTIES_NAMESPACE = "http://www.jivesoftware.com/xmlns/xmpp/properties";
}
