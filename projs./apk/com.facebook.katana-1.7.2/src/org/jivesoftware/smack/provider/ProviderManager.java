// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProviderManager.java

package org.jivesoftware.smack.provider;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.PacketExtension;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

// Referenced classes of package org.jivesoftware.smack.provider:
//            PacketExtensionProvider, IQProvider

public class ProviderManager
{

    private ProviderManager()
    {
        extensionProviders = new ConcurrentHashMap();
        iqProviders = new ConcurrentHashMap();
        initialize();
    }

    private ClassLoader[] getClassLoaders()
    {
        int i = 0;
        ClassLoader aclassloader[] = new ClassLoader[2];
        aclassloader[i] = org/jivesoftware/smack/provider/ProviderManager.getClassLoader();
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

    /**
     * @deprecated Method getInstance is deprecated
     */

    public static ProviderManager getInstance()
    {
        org/jivesoftware/smack/provider/ProviderManager;
        JVM INSTR monitorenter ;
        ProviderManager providermanager;
        if(instance == null)
            instance = new ProviderManager();
        providermanager = instance;
        org/jivesoftware/smack/provider/ProviderManager;
        JVM INSTR monitorexit ;
        return providermanager;
        Exception exception;
        exception;
        throw exception;
    }

    private String getProviderKey(String s, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<").append(s).append("/><").append(s1).append("/>");
        return stringbuilder.toString();
    }

    /**
     * @deprecated Method setInstance is deprecated
     */

    public static void setInstance(ProviderManager providermanager)
    {
        org/jivesoftware/smack/provider/ProviderManager;
        JVM INSTR monitorenter ;
        if(instance != null)
            throw new IllegalStateException("ProviderManager singleton already set");
        break MISSING_BLOCK_LABEL_25;
        Exception exception;
        exception;
        org/jivesoftware/smack/provider/ProviderManager;
        JVM INSTR monitorexit ;
        throw exception;
        instance = providermanager;
        org/jivesoftware/smack/provider/ProviderManager;
        JVM INSTR monitorexit ;
    }

    public void addExtensionProvider(String s, String s1, Object obj)
    {
        if(!(obj instanceof PacketExtensionProvider) && !(obj instanceof Class))
        {
            throw new IllegalArgumentException("Provider must be a PacketExtensionProvider or a Class instance.");
        } else
        {
            String s2 = getProviderKey(s, s1);
            extensionProviders.put(s2, obj);
            return;
        }
    }

    public void addIQProvider(String s, String s1, Object obj)
    {
        if(!(obj instanceof IQProvider) && (!(obj instanceof Class) || !org/jivesoftware/smack/packet/IQ.isAssignableFrom((Class)obj)))
        {
            throw new IllegalArgumentException("Provider must be an IQProvider or a Class instance.");
        } else
        {
            String s2 = getProviderKey(s, s1);
            iqProviders.put(s2, obj);
            return;
        }
    }

    public Object getExtensionProvider(String s, String s1)
    {
        String s2 = getProviderKey(s, s1);
        return extensionProviders.get(s2);
    }

    public Collection getExtensionProviders()
    {
        return Collections.unmodifiableCollection(extensionProviders.values());
    }

    public Object getIQProvider(String s, String s1)
    {
        String s2 = getProviderKey(s, s1);
        return iqProviders.get(s2);
    }

    public Collection getIQProviders()
    {
        return Collections.unmodifiableCollection(iqProviders.values());
    }

    protected void initialize()
    {
        ClassLoader aclassloader[];
        int i;
        int j;
        aclassloader = getClassLoaders();
        i = aclassloader.length;
        j = 0;
_L21:
        if(j >= i) goto _L2; else goto _L1
_L1:
        Enumeration enumeration = aclassloader[j].getResources("META-INF/smack.providers");
_L14:
        if(!enumeration.hasMoreElements()) goto _L4; else goto _L3
_L3:
        URL url = (URL)enumeration.nextElement();
        InputStream inputstream1 = url.openStream();
        XmlPullParser xmlpullparser;
        int k;
        xmlpullparser = XmlPullParserFactory.newInstance().newPullParser();
        xmlpullparser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        xmlpullparser.setInput(inputstream1, "UTF-8");
        k = xmlpullparser.getEventType();
_L13:
        if(k != 2) goto _L6; else goto _L5
_L5:
        if(!xmlpullparser.getName().equals("iqProvider")) goto _L8; else goto _L7
_L7:
        String s6;
        String s7;
        boolean flag1;
        xmlpullparser.next();
        xmlpullparser.next();
        String s4 = xmlpullparser.nextText();
        xmlpullparser.next();
        xmlpullparser.next();
        String s5 = xmlpullparser.nextText();
        xmlpullparser.next();
        xmlpullparser.next();
        s6 = xmlpullparser.nextText();
        s7 = getProviderKey(s4, s5);
        flag1 = iqProviders.containsKey(s7);
        if(flag1) goto _L6; else goto _L9
_L9:
        Class class2 = Class.forName(s6);
        if(!org/jivesoftware/smack/provider/IQProvider.isAssignableFrom(class2)) goto _L11; else goto _L10
_L10:
        iqProviders.put(s7, class2.newInstance());
_L6:
        int l = xmlpullparser.next();
        k = l;
        if(k != 1) goto _L13; else goto _L12
_L12:
        try
        {
            inputstream1.close();
        }
        catch(Exception exception4) { }
          goto _L14
_L11:
        if(!org/jivesoftware/smack/packet/IQ.isAssignableFrom(class2)) goto _L6; else goto _L15
_L15:
        iqProviders.put(s7, class2);
          goto _L6
        ClassNotFoundException classnotfoundexception1;
        classnotfoundexception1;
        classnotfoundexception1.printStackTrace();
          goto _L6
        Exception exception3;
        exception3;
        Exception exception1;
        InputStream inputstream;
        inputstream = inputstream1;
        exception1 = exception3;
_L22:
        String s;
        String s1;
        String s2;
        String s3;
        boolean flag;
        ClassNotFoundException classnotfoundexception;
        Class class1;
        try
        {
            inputstream.close();
        }
        catch(Exception exception2) { }
        try
        {
            throw exception1;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
_L2:
        return;
_L8:
        if(!xmlpullparser.getName().equals("extensionProvider")) goto _L6; else goto _L16
_L16:
        xmlpullparser.next();
        xmlpullparser.next();
        s = xmlpullparser.nextText();
        xmlpullparser.next();
        xmlpullparser.next();
        s1 = xmlpullparser.nextText();
        xmlpullparser.next();
        xmlpullparser.next();
        s2 = xmlpullparser.nextText();
        s3 = getProviderKey(s, s1);
        flag = extensionProviders.containsKey(s3);
        if(flag) goto _L6; else goto _L17
_L17:
        class1 = Class.forName(s2);
        if(!org/jivesoftware/smack/provider/PacketExtensionProvider.isAssignableFrom(class1)) goto _L19; else goto _L18
_L18:
        extensionProviders.put(s3, class1.newInstance());
          goto _L6
        classnotfoundexception;
        classnotfoundexception.printStackTrace();
          goto _L6
_L19:
        if(!org/jivesoftware/smack/packet/PacketExtension.isAssignableFrom(class1)) goto _L6; else goto _L20
_L20:
        extensionProviders.put(s3, class1);
          goto _L6
_L4:
        j++;
          goto _L21
        exception1;
        inputstream = null;
          goto _L22
    }

    public void removeExtensionProvider(String s, String s1)
    {
        String s2 = getProviderKey(s, s1);
        extensionProviders.remove(s2);
    }

    public void removeIQProvider(String s, String s1)
    {
        String s2 = getProviderKey(s, s1);
        iqProviders.remove(s2);
    }

    private static ProviderManager instance;
    private Map extensionProviders;
    private Map iqProviders;
}
