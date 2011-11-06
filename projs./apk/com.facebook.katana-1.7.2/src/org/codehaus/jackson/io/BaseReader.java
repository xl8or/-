// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;

import java.io.*;

// Referenced classes of package org.codehaus.jackson.io:
//            IOContext

abstract class BaseReader extends Reader
{

    protected BaseReader(IOContext iocontext, InputStream inputstream, byte abyte0[], int i, int j)
    {
        mTmpBuf = null;
        mContext = iocontext;
        mIn = inputstream;
        mBuffer = abyte0;
        mPtr = i;
        mLength = j;
    }

    public void close()
        throws IOException
    {
        InputStream inputstream = mIn;
        if(inputstream != null)
        {
            mIn = null;
            freeBuffers();
            inputstream.close();
        }
    }

    public final void freeBuffers()
    {
        byte abyte0[] = mBuffer;
        if(abyte0 != null)
        {
            mBuffer = null;
            mContext.releaseReadIOBuffer(abyte0);
        }
    }

    public int read()
        throws IOException
    {
        if(mTmpBuf == null)
            mTmpBuf = new char[1];
        char c;
        if(read(mTmpBuf, 0, 1) < 1)
            c = '\uFFFF';
        else
            c = mTmpBuf[0];
        return c;
    }

    protected void reportBounds(char ac[], int i, int j)
        throws IOException
    {
        throw new ArrayIndexOutOfBoundsException((new StringBuilder()).append("read(buf,").append(i).append(",").append(j).append("), cbuf[").append(ac.length).append("]").toString());
    }

    protected void reportStrangeStream()
        throws IOException
    {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }

    protected static final int LAST_VALID_UNICODE_CHAR = 0x10ffff;
    protected static final char NULL_BYTE;
    protected static final char NULL_CHAR;
    protected byte mBuffer[];
    protected final IOContext mContext;
    protected InputStream mIn;
    protected int mLength;
    protected int mPtr;
    char mTmpBuf[];
}
