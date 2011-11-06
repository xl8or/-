// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package org.codehaus.jackson.io:
//            IOContext

public final class MergedStream extends InputStream
{

    public MergedStream(IOContext iocontext, InputStream inputstream, byte abyte0[], int i, int j)
    {
        _context = iocontext;
        _in = inputstream;
        _buffer = abyte0;
        _ptr = i;
        _end = j;
    }

    private void freeMergedBuffer()
    {
        byte abyte0[] = _buffer;
        if(abyte0 != null)
        {
            _buffer = null;
            _context.releaseReadIOBuffer(abyte0);
        }
    }

    public int available()
        throws IOException
    {
        int i;
        if(_buffer != null)
            i = _end - _ptr;
        else
            i = _in.available();
        return i;
    }

    public void close()
        throws IOException
    {
        freeMergedBuffer();
        _in.close();
    }

    public void mark(int i)
    {
        if(_buffer == null)
            _in.mark(i);
    }

    public boolean markSupported()
    {
        boolean flag;
        if(_buffer == null && _in.markSupported())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int read()
        throws IOException
    {
        int i;
        if(_buffer != null)
        {
            byte abyte0[] = _buffer;
            int j = _ptr;
            _ptr = j + 1;
            i = 0xff & abyte0[j];
            if(_ptr >= _end)
                freeMergedBuffer();
        } else
        {
            i = _in.read();
        }
        return i;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        if(_buffer != null)
        {
            int k = _end - _ptr;
            if(j <= k)
                k = j;
            System.arraycopy(_buffer, _ptr, abyte0, i, k);
            _ptr = k + _ptr;
            if(_ptr >= _end)
                freeMergedBuffer();
        } else
        {
            k = _in.read(abyte0, i, j);
        }
        return k;
    }

    public void reset()
        throws IOException
    {
        if(_buffer == null)
            _in.reset();
    }

    public long skip(long l)
        throws IOException
    {
        if(_buffer == null) goto _L2; else goto _L1
_L1:
        int i = _end - _ptr;
        if((long)i <= l) goto _L4; else goto _L3
_L3:
        long l1;
        _ptr = _ptr + (int)l;
        l1 = i;
_L5:
        return l1;
_L4:
        long l2;
        freeMergedBuffer();
        long l3 = 0L + (long)i;
        long l4 = l - (long)i;
        l1 = l3;
        l2 = l4;
_L6:
        if(l2 > 0L)
            l1 += _in.skip(l2);
        if(true) goto _L5; else goto _L2
_L2:
        l1 = 0L;
        l2 = l;
          goto _L6
    }

    byte _buffer[];
    protected final IOContext _context;
    final int _end;
    final InputStream _in;
    int _ptr;
}
