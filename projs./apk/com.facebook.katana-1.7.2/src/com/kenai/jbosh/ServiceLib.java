// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceLib.java

package com.kenai.jbosh;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

final class ServiceLib
{

    private ServiceLib()
    {
    }

    private static Object attemptLoad(Class class1, String s)
    {
        if(LOG.isLoggable(Level.FINEST))
            LOG.finest((new StringBuilder()).append("Attempting service load: ").append(s).toString());
        Class class2 = Class.forName(s);
        if(class1.isAssignableFrom(class2)) goto _L2; else goto _L1
_L1:
        if(LOG.isLoggable(Level.WARNING))
            LOG.warning((new StringBuilder()).append(class2.getName()).append(" is not assignable to ").append(class1.getName()).toString());
          goto _L3
_L2:
        Object obj2 = class1.cast(class2.newInstance());
        Object obj1 = obj2;
          goto _L4
        Object obj;
        obj;
        Level level = Level.FINEST;
_L5:
        LOG.log(level, (new StringBuilder()).append("Could not load ").append(class1.getSimpleName()).append(" instance: ").append(s).toString(), ((Throwable) (obj)));
        obj1 = null;
        break; /* Loop/switch isn't completed */
        obj;
        level = Level.FINEST;
        continue; /* Loop/switch isn't completed */
        obj;
        level = Level.WARNING;
        continue; /* Loop/switch isn't completed */
        obj;
        level = Level.WARNING;
        if(true) goto _L5; else goto _L4
_L3:
        obj1 = null;
_L4:
        return obj1;
    }

    private static void finalClose(Closeable closeable)
    {
        if(closeable == null)
            break MISSING_BLOCK_LABEL_10;
        closeable.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
        LOG.log(Level.FINEST, (new StringBuilder()).append("Could not close: ").append(closeable).toString(), ioexception);
          goto _L1
    }

    static Object loadService(Class class1)
    {
        for(Iterator iterator = loadServicesImplementations(class1).iterator(); iterator.hasNext();)
        {
            Object obj = attemptLoad(class1, (String)iterator.next());
            if(obj != null)
            {
                if(LOG.isLoggable(Level.FINEST))
                    LOG.finest((new StringBuilder()).append("Selected ").append(class1.getSimpleName()).append(" implementation: ").append(obj.getClass().getName()).toString());
                return obj;
            }
        }

        throw new IllegalStateException((new StringBuilder()).append("Could not load ").append(class1.getName()).append(" implementation").toString());
    }

    private static List loadServicesImplementations(Class class1)
    {
        Object obj;
        ArrayList arraylist;
        URL url;
        obj = null;
        arraylist = new ArrayList();
        String s = System.getProperty(class1.getName());
        if(s != null)
            arraylist.add(s);
        url = com/kenai/jbosh/ServiceLib.getClassLoader().getResource((new StringBuilder()).append("META-INF/services/").append(class1.getName()).toString());
        if(url != null) goto _L2; else goto _L1
_L1:
        return arraylist;
_L2:
        java.io.InputStream inputstream = url.openStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        IOException ioexception;
        Object obj1;
        Object obj2;
        Exception exception;
        Object obj3;
        Object obj4;
        Object obj5;
        BufferedReader bufferedreader;
        IOException ioexception3;
        try
        {
            bufferedreader = new BufferedReader(inputstreamreader);
            break MISSING_BLOCK_LABEL_98;
        }
        catch(IOException ioexception2)
        {
            obj2 = inputstreamreader;
            obj1 = obj;
            obj = inputstream;
            ioexception = ioexception2;
        }
          goto _L3
        ioexception1;
        obj = inputstream;
        ioexception = ioexception1;
        obj1 = bufferedreader;
        obj2 = inputstreamreader;
_L3:
        LOG.log(Level.WARNING, (new StringBuilder()).append("Could not load services descriptor: ").append(url.toString()).toString(), ioexception);
        finalClose(((Closeable) (obj1)));
        finalClose(((Closeable) (obj2)));
        finalClose(((Closeable) (obj)));
          goto _L4
        IOException ioexception1;
        do
        {
            String s1 = bufferedreader.readLine();
            if(s1 == null)
                break;
            if(!s1.matches("\\s*(#.*)?"))
                arraylist.add(s1.trim());
        } while(true);
        finalClose(bufferedreader);
        finalClose(inputstreamreader);
        finalClose(inputstream);
_L4:
        if(true) goto _L1; else goto _L3
        exception;
        obj3 = obj;
        obj4 = obj;
        obj5 = obj;
_L6:
        finalClose(((Closeable) (obj3)));
        finalClose(((Closeable) (obj4)));
        finalClose(((Closeable) (obj5)));
        throw exception;
        exception;
        obj3 = obj;
        obj5 = inputstream;
        obj4 = obj;
        continue; /* Loop/switch isn't completed */
        exception;
        obj3 = obj;
        obj5 = inputstream;
        obj4 = inputstreamreader;
        continue; /* Loop/switch isn't completed */
        exception;
        obj3 = bufferedreader;
        obj5 = inputstream;
        obj4 = inputstreamreader;
        continue; /* Loop/switch isn't completed */
        exception;
        obj3 = obj1;
        obj4 = obj2;
        obj5 = obj;
        if(true) goto _L6; else goto _L5
_L5:
        ioexception;
        obj1 = obj;
        obj2 = obj;
          goto _L3
        ioexception3;
        obj2 = obj;
        Object obj6 = obj;
        obj = inputstream;
        ioexception = ioexception3;
        obj1 = obj6;
          goto _L3
    }

    private static final Logger LOG = Logger.getLogger(com/kenai/jbosh/ServiceLib.getName());

}
