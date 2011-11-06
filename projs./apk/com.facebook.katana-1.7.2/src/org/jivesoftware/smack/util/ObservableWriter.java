// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObservableWriter.java

package org.jivesoftware.smack.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.jivesoftware.smack.util:
//            WriterListener

public class ObservableWriter extends Writer
{

    public ObservableWriter(Writer writer)
    {
        wrappedWriter = null;
        listeners = new ArrayList();
        wrappedWriter = writer;
    }

    private void notifyListeners(String s)
    {
        WriterListener awriterlistener[];
        synchronized(listeners)
        {
            awriterlistener = new WriterListener[listeners.size()];
            listeners.toArray(awriterlistener);
        }
        for(int i = 0; i < awriterlistener.length; i++)
            awriterlistener[i].write(s);

        break MISSING_BLOCK_LABEL_68;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void addWriterListener(WriterListener writerlistener)
    {
        if(writerlistener != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        List list = listeners;
        list;
        JVM INSTR monitorenter ;
        if(!listeners.contains(writerlistener))
            listeners.add(writerlistener);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void close()
        throws IOException
    {
        wrappedWriter.close();
    }

    public void flush()
        throws IOException
    {
        wrappedWriter.flush();
    }

    public void removeWriterListener(WriterListener writerlistener)
    {
        List list = listeners;
        list;
        JVM INSTR monitorenter ;
        listeners.remove(writerlistener);
        return;
    }

    public void write(int i)
        throws IOException
    {
        wrappedWriter.write(i);
    }

    public void write(String s)
        throws IOException
    {
        wrappedWriter.write(s);
        notifyListeners(s);
    }

    public void write(String s, int i, int j)
        throws IOException
    {
        wrappedWriter.write(s, i, j);
        notifyListeners(s.substring(i, i + j));
    }

    public void write(char ac[])
        throws IOException
    {
        wrappedWriter.write(ac);
        notifyListeners(new String(ac));
    }

    public void write(char ac[], int i, int j)
        throws IOException
    {
        wrappedWriter.write(ac, i, j);
        notifyListeners(new String(ac, i, j));
    }

    List listeners;
    Writer wrappedWriter;
}
