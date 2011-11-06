// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Packet.java

package org.jivesoftware.smack.packet;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.packet:
//            PacketExtension, XMPPError

public abstract class Packet
{

    public Packet()
    {
        xmlns = DEFAULT_XML_NS;
        packetID = null;
        to = null;
        from = null;
        error = null;
    }

    public static String getDefaultLanguage()
    {
        return DEFAULT_LANGUAGE;
    }

    /**
     * @deprecated Method nextID is deprecated
     */

    public static String nextID()
    {
        org/jivesoftware/smack/packet/Packet;
        JVM INSTR monitorenter ;
        String s;
        StringBuilder stringbuilder = (new StringBuilder()).append(prefix);
        long l = id;
        id = 1L + l;
        s = stringbuilder.append(Long.toString(l)).toString();
        org/jivesoftware/smack/packet/Packet;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    public static void setDefaultXmlns(String s)
    {
        DEFAULT_XML_NS = s;
    }

    public void addExtension(PacketExtension packetextension)
    {
        packetExtensions.add(packetextension);
    }

    /**
     * @deprecated Method deleteProperty is deprecated
     */

    public void deleteProperty(String s)
    {
        this;
        JVM INSTR monitorenter ;
        Map map = properties;
        if(map != null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        properties.remove(s);
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(this == obj)
            flag = true;
        else
        if(obj == null || getClass() != obj.getClass())
        {
            flag = false;
        } else
        {
            Packet packet = (Packet)obj;
            if(error == null ? packet.error != null : !error.equals(packet.error))
                flag = false;
            else
            if(from == null ? packet.from != null : !from.equals(packet.from))
                flag = false;
            else
            if(!packetExtensions.equals(packet.packetExtensions))
                flag = false;
            else
            if(packetID == null ? packet.packetID != null : !packetID.equals(packet.packetID))
                flag = false;
            else
            if(properties == null ? packet.properties != null : !properties.equals(packet.properties))
                flag = false;
            else
            if(to == null ? packet.to != null : !to.equals(packet.to))
                flag = false;
            else
            if(xmlns == null ? packet.xmlns != null : !xmlns.equals(packet.xmlns))
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    public XMPPError getError()
    {
        return error;
    }

    public PacketExtension getExtension(String s)
    {
        return getExtension(null, s);
    }

    public PacketExtension getExtension(String s, String s1)
    {
        if(s1 != null) goto _L2; else goto _L1
_L1:
        PacketExtension packetextension = null;
_L4:
        return packetextension;
_L2:
        for(Iterator iterator = packetExtensions.iterator(); iterator.hasNext();)
        {
            PacketExtension packetextension1 = (PacketExtension)iterator.next();
            if((s == null || s.equals(packetextension1.getElementName())) && s1.equals(packetextension1.getNamespace()))
            {
                packetextension = packetextension1;
                continue; /* Loop/switch isn't completed */
            }
        }

        packetextension = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    /**
     * @deprecated Method getExtensions is deprecated
     */

    public Collection getExtensions()
    {
        this;
        JVM INSTR monitorenter ;
        if(packetExtensions != null) goto _L2; else goto _L1
_L1:
        List list2 = Collections.emptyList();
        List list1 = list2;
_L4:
        this;
        JVM INSTR monitorexit ;
        return list1;
_L2:
        List list = Collections.unmodifiableList(new ArrayList(packetExtensions));
        list1 = list;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getExtensionsXML is deprecated
     */

    protected String getExtensionsXML()
    {
        this;
        JVM INSTR monitorenter ;
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder();
        for(Iterator iterator = getExtensions().iterator(); iterator.hasNext(); stringbuilder.append(((PacketExtension)iterator.next()).toXML()));
        break MISSING_BLOCK_LABEL_56;
        Exception exception;
        exception;
        throw exception;
        if(properties == null || properties.isEmpty()) goto _L2; else goto _L1
_L1:
        Iterator iterator1;
        stringbuilder.append("<properties xmlns=\"http://www.jivesoftware.com/xmlns/xmpp/properties\">");
        iterator1 = getPropertyNames().iterator();
_L7:
        if(!iterator1.hasNext()) goto _L4; else goto _L3
_L3:
        Object obj;
        String s1 = (String)iterator1.next();
        obj = getProperty(s1);
        stringbuilder.append("<property>");
        stringbuilder.append("<name>").append(StringUtils.escapeForXML(s1)).append("</name>");
        stringbuilder.append("<value type=\"");
        if(!(obj instanceof Integer)) goto _L6; else goto _L5
_L5:
        stringbuilder.append("integer\">").append(obj).append("</value>");
_L10:
        stringbuilder.append("</property>");
          goto _L7
_L6:
        if(!(obj instanceof Long)) goto _L9; else goto _L8
_L8:
        stringbuilder.append("long\">").append(obj).append("</value>");
          goto _L10
_L9:
        if(!(obj instanceof Float)) goto _L12; else goto _L11
_L11:
        stringbuilder.append("float\">").append(obj).append("</value>");
          goto _L10
_L12:
        if(!(obj instanceof Double)) goto _L14; else goto _L13
_L13:
        stringbuilder.append("double\">").append(obj).append("</value>");
          goto _L10
_L14:
        if(!(obj instanceof Boolean)) goto _L16; else goto _L15
_L15:
        stringbuilder.append("boolean\">").append(obj).append("</value>");
          goto _L10
_L16:
        if(!(obj instanceof String)) goto _L18; else goto _L17
_L17:
        stringbuilder.append("string\">");
        stringbuilder.append(StringUtils.escapeForXML((String)obj));
        stringbuilder.append("</value>");
          goto _L10
_L18:
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
        objectoutputstream.writeObject(obj);
        stringbuilder.append("java-object\">");
        stringbuilder.append(StringUtils.encodeBase64(bytearrayoutputstream.toByteArray())).append("</value>");
        if(objectoutputstream == null) goto _L20; else goto _L19
_L19:
        String s;
        ObjectOutputStream objectoutputstream1;
        ByteArrayOutputStream bytearrayoutputstream1;
        Exception exception2;
        Exception exception3;
        ByteArrayOutputStream bytearrayoutputstream2;
        try
        {
            objectoutputstream.close();
        }
        catch(Exception exception10) { }
_L20:
        if(bytearrayoutputstream == null) goto _L10; else goto _L21
_L21:
        try
        {
            bytearrayoutputstream.close();
        }
        catch(Exception exception9) { }
          goto _L10
        exception2;
        objectoutputstream1 = null;
        bytearrayoutputstream1 = null;
_L27:
        exception2.printStackTrace();
        if(objectoutputstream1 == null) goto _L23; else goto _L22
_L22:
        try
        {
            objectoutputstream1.close();
        }
        catch(Exception exception7) { }
_L23:
        if(bytearrayoutputstream1 == null) goto _L10; else goto _L24
_L24:
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception6) { }
          goto _L10
        exception3;
        objectoutputstream1 = null;
        bytearrayoutputstream2 = null;
_L26:
        if(objectoutputstream1 == null)
            break MISSING_BLOCK_LABEL_484;
        try
        {
            objectoutputstream1.close();
        }
        catch(Exception exception5) { }
        if(bytearrayoutputstream2 == null)
            break MISSING_BLOCK_LABEL_494;
        try
        {
            bytearrayoutputstream2.close();
        }
        catch(Exception exception4) { }
        throw exception3;
_L4:
        stringbuilder.append("</properties>");
_L2:
        s = stringbuilder.toString();
        this;
        JVM INSTR monitorexit ;
        return s;
        Exception exception12;
        exception12;
        bytearrayoutputstream2 = bytearrayoutputstream;
        exception3 = exception12;
        objectoutputstream1 = null;
        continue; /* Loop/switch isn't completed */
        Exception exception8;
        exception8;
        bytearrayoutputstream2 = bytearrayoutputstream;
        exception3 = exception8;
        objectoutputstream1 = objectoutputstream;
        continue; /* Loop/switch isn't completed */
        exception3;
        bytearrayoutputstream2 = bytearrayoutputstream1;
        if(true) goto _L26; else goto _L25
_L25:
        Exception exception11;
        exception11;
        bytearrayoutputstream1 = bytearrayoutputstream;
        exception2 = exception11;
        objectoutputstream1 = null;
          goto _L27
        Exception exception1;
        exception1;
        objectoutputstream1 = objectoutputstream;
        bytearrayoutputstream1 = bytearrayoutputstream;
        exception2 = exception1;
          goto _L27
    }

    public String getFrom()
    {
        return from;
    }

    public String getPacketID()
    {
        String s;
        if("ID_NOT_AVAILABLE".equals(packetID))
        {
            s = null;
        } else
        {
            if(packetID == null)
                packetID = nextID();
            s = packetID;
        }
        return s;
    }

    /**
     * @deprecated Method getProperty is deprecated
     */

    public Object getProperty(String s)
    {
        this;
        JVM INSTR monitorenter ;
        Map map = properties;
        if(map != null) goto _L2; else goto _L1
_L1:
        Object obj1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return obj1;
_L2:
        Object obj = properties.get(s);
        obj1 = obj;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getPropertyNames is deprecated
     */

    public Collection getPropertyNames()
    {
        this;
        JVM INSTR monitorenter ;
        if(properties != null) goto _L2; else goto _L1
_L1:
        java.util.Set set2 = Collections.emptySet();
        java.util.Set set1 = set2;
_L4:
        this;
        JVM INSTR monitorexit ;
        return set1;
_L2:
        java.util.Set set = Collections.unmodifiableSet(new HashSet(properties.keySet()));
        set1 = set;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public String getTo()
    {
        return to;
    }

    public String getXmlns()
    {
        return xmlns;
    }

    public int hashCode()
    {
        int i;
        int j;
        int k;
        int l;
        int i1;
        int j1;
        int k1;
        int l1;
        int i2;
        if(xmlns != null)
            i = xmlns.hashCode();
        else
            i = 0;
        j = i * 31;
        if(packetID != null)
            k = packetID.hashCode();
        else
            k = 0;
        l = 31 * (j + k);
        if(to != null)
            i1 = to.hashCode();
        else
            i1 = 0;
        j1 = 31 * (l + i1);
        if(from != null)
            k1 = from.hashCode();
        else
            k1 = 0;
        l1 = 31 * (31 * (31 * (j1 + k1) + packetExtensions.hashCode()) + properties.hashCode());
        if(error != null)
            i2 = error.hashCode();
        else
            i2 = 0;
        return l1 + i2;
    }

    public void removeExtension(PacketExtension packetextension)
    {
        packetExtensions.remove(packetextension);
    }

    public void setError(XMPPError xmpperror)
    {
        error = xmpperror;
    }

    public void setFrom(String s)
    {
        from = s;
    }

    public void setPacketID(String s)
    {
        packetID = s;
    }

    /**
     * @deprecated Method setProperty is deprecated
     */

    public void setProperty(String s, Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        if(!(obj instanceof Serializable))
            throw new IllegalArgumentException("Value must be serialiazble");
        break MISSING_BLOCK_LABEL_25;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        properties.put(s, obj);
        this;
        JVM INSTR monitorexit ;
    }

    public void setTo(String s)
    {
        to = s;
    }

    public abstract String toXML();

    protected static final String DEFAULT_LANGUAGE = Locale.getDefault().getLanguage().toLowerCase();
    private static String DEFAULT_XML_NS = null;
    public static final String ID_NOT_AVAILABLE = "ID_NOT_AVAILABLE";
    public static final DateFormat XEP_0082_UTC_FORMAT;
    private static long id = 0L;
    private static String prefix = (new StringBuilder()).append(StringUtils.randomString(5)).append("-").toString();
    private XMPPError error;
    private String from;
    private final List packetExtensions = new CopyOnWriteArrayList();
    private String packetID;
    private final Map properties = new HashMap();
    private String to;
    private String xmlns;

    static 
    {
        XEP_0082_UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        XEP_0082_UTC_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
