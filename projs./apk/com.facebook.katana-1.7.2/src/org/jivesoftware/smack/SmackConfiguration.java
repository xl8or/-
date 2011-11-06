// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmackConfiguration.java

package org.jivesoftware.smack;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public final class SmackConfiguration
{

    private SmackConfiguration()
    {
    }

    public static void addSaslMech(String s)
    {
        if(!defaultMechs.contains(s))
            defaultMechs.add(s);
    }

    public static void addSaslMechs(Collection collection)
    {
        for(Iterator iterator = collection.iterator(); iterator.hasNext(); addSaslMech((String)iterator.next()));
    }

    private static ClassLoader[] getClassLoaders()
    {
        int i = 0;
        ClassLoader aclassloader[] = new ClassLoader[2];
        aclassloader[i] = org/jivesoftware/smack/SmackConfiguration.getClassLoader();
        aclassloader[1] = Thread.currentThread().getContextClassLoader();
        ArrayList arraylist = new ArrayList();
        for(int j = aclassloader.length; i < j; i++)
        {
            ClassLoader classloader = aclassloader[i];
            if(classloader != null)
                arraylist.add(classloader);
        }

        return (ClassLoader[])arraylist.toArray(new ClassLoader[arraylist.size()]);
    }

    public static int getKeepAliveInterval()
    {
        return keepAliveInterval;
    }

    public static int getPacketReplyTimeout()
    {
        if(packetReplyTimeout <= 0)
            packetReplyTimeout = 5000;
        return packetReplyTimeout;
    }

    public static List getSaslMechs()
    {
        return defaultMechs;
    }

    public static String getVersion()
    {
        return "3.1.0";
    }

    private static void parseClassToLoad(XmlPullParser xmlpullparser)
        throws Exception
    {
        String s = xmlpullparser.nextText();
        Class.forName(s);
_L1:
        return;
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        System.err.println((new StringBuilder()).append("Error! A startup class specified in smack-config.xml could not be loaded: ").append(s).toString());
          goto _L1
    }

    private static int parseIntProperty(XmlPullParser xmlpullparser, int i)
        throws Exception
    {
        int k = Integer.parseInt(xmlpullparser.nextText());
        int j = k;
_L2:
        return j;
        NumberFormatException numberformatexception;
        numberformatexception;
        numberformatexception.printStackTrace();
        j = i;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static void removeSaslMech(String s)
    {
        if(defaultMechs.contains(s))
            defaultMechs.remove(s);
    }

    public static void removeSaslMechs(Collection collection)
    {
        for(Iterator iterator = collection.iterator(); iterator.hasNext(); removeSaslMech((String)iterator.next()));
    }

    public static void setKeepAliveInterval(int i)
    {
        keepAliveInterval = i;
    }

    public static void setPacketReplyTimeout(int i)
    {
        if(i <= 0)
        {
            throw new IllegalArgumentException();
        } else
        {
            packetReplyTimeout = i;
            return;
        }
    }

    private static final String SMACK_VERSION = "3.1.0";
    private static Vector defaultMechs;
    private static int keepAliveInterval;
    private static int packetReplyTimeout;

    static 
    {
        packetReplyTimeout = 5000;
        keepAliveInterval = 30000;
        defaultMechs = new Vector();
        ClassLoader aclassloader[];
        int i;
        int j;
        aclassloader = getClassLoaders();
        i = aclassloader.length;
        j = 0;
_L17:
        if(j >= i) goto _L2; else goto _L1
_L1:
        Enumeration enumeration = aclassloader[j].getResources("META-INF/smack-config.xml");
_L11:
        if(!enumeration.hasMoreElements()) goto _L4; else goto _L3
_L3:
        URL url = (URL)enumeration.nextElement();
        InputStream inputstream = null;
        InputStream inputstream2 = url.openStream();
        XmlPullParser xmlpullparser;
        int k;
        xmlpullparser = XmlPullParserFactory.newInstance().newPullParser();
        xmlpullparser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        xmlpullparser.setInput(inputstream2, "UTF-8");
        k = xmlpullparser.getEventType();
_L10:
        if(k != 2) goto _L6; else goto _L5
_L5:
        if(!xmlpullparser.getName().equals("className")) goto _L8; else goto _L7
_L7:
        parseClassToLoad(xmlpullparser);
_L6:
        int l = xmlpullparser.next();
        k = l;
        if(k != 1) goto _L10; else goto _L9
_L9:
        try
        {
            inputstream2.close();
        }
        catch(Exception exception7) { }
          goto _L11
_L8:
        if(!xmlpullparser.getName().equals("packetReplyTimeout")) goto _L13; else goto _L12
_L12:
        packetReplyTimeout = parseIntProperty(xmlpullparser, packetReplyTimeout);
          goto _L6
        Exception exception6;
        exception6;
        Exception exception1;
        inputstream = inputstream2;
        exception1 = exception6;
_L19:
        exception1.printStackTrace();
        try
        {
            inputstream.close();
        }
        catch(Exception exception4) { }
          goto _L11
_L13:
        if(!xmlpullparser.getName().equals("keepAliveInterval")) goto _L15; else goto _L14
_L14:
        keepAliveInterval = parseIntProperty(xmlpullparser, keepAliveInterval);
          goto _L6
        Exception exception5;
        exception5;
        Exception exception2;
        InputStream inputstream1;
        inputstream1 = inputstream2;
        exception2 = exception5;
_L18:
        try
        {
            inputstream1.close();
        }
        catch(Exception exception3) { }
        try
        {
            throw exception2;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
_L2:
        return;
_L15:
        if(!xmlpullparser.getName().equals("mechName")) goto _L6; else goto _L16
_L16:
        defaultMechs.add(xmlpullparser.nextText());
          goto _L6
_L4:
        j++;
          goto _L17
        exception2;
        inputstream1 = inputstream;
          goto _L18
        exception2;
        inputstream1 = inputstream;
          goto _L18
        exception1;
          goto _L19
    }
}
