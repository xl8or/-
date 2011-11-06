// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodyQName.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            QName

public final class BodyQName
{

    private BodyQName(QName qname1)
    {
        qname = qname1;
    }

    public static BodyQName create(String s, String s1)
    {
        return createWithPrefix(s, s1, null);
    }

    static BodyQName createBOSH(String s)
    {
        return createWithPrefix("http://jabber.org/protocol/httpbind", s, null);
    }

    public static BodyQName createWithPrefix(String s, String s1, String s2)
    {
        if(s == null || s.length() == 0)
            throw new IllegalArgumentException("URI is required and may not be null/empty");
        if(s1 == null || s1.length() == 0)
            throw new IllegalArgumentException("Local arg is required and may not be null/empty");
        BodyQName bodyqname;
        if(s2 == null || s2.length() == 0)
            bodyqname = new BodyQName(new QName(s, s1));
        else
            bodyqname = new BodyQName(new QName(s, s1, s2));
        return bodyqname;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj instanceof BodyQName)
        {
            BodyQName bodyqname = (BodyQName)obj;
            flag = qname.equals(bodyqname.qname);
        } else
        {
            flag = false;
        }
        return flag;
    }

    boolean equalsQName(QName qname1)
    {
        return qname.equals(qname1);
    }

    public String getLocalPart()
    {
        return qname.getLocalPart();
    }

    public String getNamespaceURI()
    {
        return qname.getNamespaceURI();
    }

    public String getPrefix()
    {
        return qname.getPrefix();
    }

    public int hashCode()
    {
        return qname.hashCode();
    }

    static final String BOSH_NS_URI = "http://jabber.org/protocol/httpbind";
    private final QName qname;
}
