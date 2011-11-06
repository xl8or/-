// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QName.java

package com.kenai.jbosh;

import java.io.*;

public class QName
    implements Serializable
{

    public QName(String s)
    {
        this(emptyString, s, emptyString);
    }

    public QName(String s, String s1)
    {
        this(s, s1, emptyString);
    }

    public QName(String s, String s1, String s2)
    {
        String s3;
        if(s == null)
            s3 = emptyString;
        else
            s3 = s.intern();
        namespaceURI = s3;
        if(s1 == null)
            throw new IllegalArgumentException("invalid QName local part");
        localPart = s1.intern();
        if(s2 == null)
        {
            throw new IllegalArgumentException("invalid QName prefix");
        } else
        {
            prefix = s2.intern();
            return;
        }
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        namespaceURI = namespaceURI.intern();
        localPart = localPart.intern();
        prefix = prefix.intern();
    }

    public static QName valueOf(String s)
    {
        if(s == null || s.equals(""))
            throw new IllegalArgumentException("invalid QName literal");
        QName qname;
        if(s.charAt(0) == '{')
        {
            int i = s.indexOf('}');
            if(i == -1)
                throw new IllegalArgumentException("invalid QName literal");
            if(i == s.length() - 1)
                throw new IllegalArgumentException("invalid QName literal");
            qname = new QName(s.substring(1, i), s.substring(i + 1));
        } else
        {
            qname = new QName(s);
        }
        return qname;
    }

    public final boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
        if(!(obj instanceof QName))
            flag = false;
        else
        if(namespaceURI == ((QName)obj).namespaceURI && localPart == ((QName)obj).localPart)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String getLocalPart()
    {
        return localPart;
    }

    public String getNamespaceURI()
    {
        return namespaceURI;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public final int hashCode()
    {
        return namespaceURI.hashCode() ^ localPart.hashCode();
    }

    public String toString()
    {
        String s;
        if(namespaceURI == emptyString)
            s = localPart;
        else
            s = (new StringBuilder()).append('{').append(namespaceURI).append('}').append(localPart).toString();
        return s;
    }

    private static final String emptyString = "".intern();
    private String localPart;
    private String namespaceURI;
    private String prefix;

}
