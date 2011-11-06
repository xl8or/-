// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObservableReader.java

package org.jivesoftware.smack.util;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.jivesoftware.smack.util:
//            ReaderListener

public class ObservableReader extends Reader
{

    public ObservableReader(Reader reader)
    {
        wrappedReader = null;
        listeners = new ArrayList();
        wrappedReader = reader;
    }

    public void addReaderListener(ReaderListener readerlistener)
    {
        if(readerlistener != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        List list = listeners;
        list;
        JVM INSTR monitorenter ;
        if(!listeners.contains(readerlistener))
            listeners.add(readerlistener);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void close()
        throws IOException
    {
        wrappedReader.close();
    }

    public void mark(int i)
        throws IOException
    {
        wrappedReader.mark(i);
    }

    public boolean markSupported()
    {
        return wrappedReader.markSupported();
    }

    public int read()
        throws IOException
    {
        return wrappedReader.read();
    }

    public int read(char ac[])
        throws IOException
    {
        return wrappedReader.read(ac);
    }

    public int read(char ac[], int i, int j)
        throws IOException
    {
        int k;
        k = wrappedReader.read(ac, i, j);
        if(k > 0)
        {
            String s = new String(ac, i, k);
            ReaderListener areaderlistener[];
            synchronized(listeners)
            {
                areaderlistener = new ReaderListener[listeners.size()];
                listeners.toArray(areaderlistener);
            }
            for(int l = 0; l < areaderlistener.length; l++)
                areaderlistener[l].read(s);

        }
        break MISSING_BLOCK_LABEL_105;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
        return k;
    }

    public boolean ready()
        throws IOException
    {
        return wrappedReader.ready();
    }

    public void removeReaderListener(ReaderListener readerlistener)
    {
        List list = listeners;
        list;
        JVM INSTR monitorenter ;
        listeners.remove(readerlistener);
        return;
    }

    public void reset()
        throws IOException
    {
        wrappedReader.reset();
    }

    public long skip(long l)
        throws IOException
    {
        return wrappedReader.skip(l);
    }

    List listeners;
    Reader wrappedReader;
}
