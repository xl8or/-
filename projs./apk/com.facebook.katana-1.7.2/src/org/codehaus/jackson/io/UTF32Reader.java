// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;

import java.io.*;

// Referenced classes of package org.codehaus.jackson.io:
//            BaseReader, IOContext

public final class UTF32Reader extends BaseReader
{

    public UTF32Reader(IOContext iocontext, InputStream inputstream, byte abyte0[], int i, int j, boolean flag)
    {
        super(iocontext, inputstream, abyte0, i, j);
        mSurrogate = '\0';
        mCharCount = 0;
        mByteCount = 0;
        mBigEndian = flag;
    }

    private boolean loadMore(int i)
        throws IOException
    {
        mByteCount = mByteCount + (mLength - i);
        if(i <= 0) goto _L2; else goto _L1
_L1:
        if(mPtr > 0)
        {
            for(int l = 0; l < i; l++)
                mBuffer[l] = mBuffer[l + mPtr];

            mPtr = 0;
        }
_L7:
        int k;
        for(mLength = i; mLength < 4; mLength = k + mLength)
        {
            k = mIn.read(mBuffer, mLength, mBuffer.length - mLength);
            if(k < 1)
            {
                if(k < 0)
                {
                    freeBuffers();
                    reportUnexpectedEOF(mLength, 4);
                }
                reportStrangeStream();
            }
        }

        break MISSING_BLOCK_LABEL_198;
_L2:
        int j;
        mPtr = 0;
        j = mIn.read(mBuffer);
        if(j >= 1) goto _L4; else goto _L3
_L3:
        mLength = 0;
        if(j >= 0) goto _L6; else goto _L5
_L5:
        boolean flag;
        freeBuffers();
        flag = false;
_L8:
        return flag;
_L6:
        reportStrangeStream();
_L4:
        mLength = j;
          goto _L7
        flag = true;
          goto _L8
    }

    private void reportInvalid(int i, int j, String s)
        throws IOException
    {
        int k = (mByteCount + mPtr) - 1;
        int l = j + mCharCount;
        throw new CharConversionException((new StringBuilder()).append("Invalid UTF-32 character 0x").append(Integer.toHexString(i)).append(s).append(" at char #").append(l).append(", byte #").append(k).append(")").toString());
    }

    private void reportUnexpectedEOF(int i, int j)
        throws IOException
    {
        int k = i + mByteCount;
        int l = mCharCount;
        throw new CharConversionException((new StringBuilder()).append("Unexpected EOF in the middle of a 4-byte UTF-32 char: got ").append(i).append(", needed ").append(j).append(", at char #").append(l).append(", byte #").append(k).append(")").toString());
    }

    public volatile void close()
        throws IOException
    {
        super.close();
    }

    public volatile int read()
        throws IOException
    {
        return super.read();
    }

    public int read(char ac[], int i, int j)
        throws IOException
    {
        int k1;
        if(mBuffer == null)
        {
            k1 = -1;
        } else
        {
label0:
            {
                if(j >= 1)
                    break label0;
                k1 = j;
            }
        }
_L9:
        return k1;
        int k;
        if(i < 0 || i + j > ac.length)
            reportBounds(ac, i, j);
        k = j + i;
        if(mSurrogate == 0) goto _L2; else goto _L1
_L1:
        int i1;
        i1 = i + 1;
        ac[i] = mSurrogate;
        mSurrogate = '\0';
_L11:
        if(i1 >= k) goto _L4; else goto _L3
_L3:
        int j1;
        int i2;
        int l2;
        int k3;
        int l1 = mPtr;
        int l;
        int j3;
        if(mBigEndian)
            i2 = mBuffer[l1] << 24 | (0xff & mBuffer[l1 + 1]) << 16 | (0xff & mBuffer[l1 + 2]) << 8 | 0xff & mBuffer[l1 + 3];
        else
            i2 = 0xff & mBuffer[l1] | (0xff & mBuffer[l1 + 1]) << 8 | (0xff & mBuffer[l1 + 2]) << 16 | mBuffer[l1 + 3] << 24;
        mPtr = 4 + mPtr;
        if(i2 <= 65535) goto _L6; else goto _L5
_L5:
        if(i2 > 0x10ffff)
            reportInvalid(i2, i1 - i, (new StringBuilder()).append("(above ").append(Integer.toHexString(0x10ffff)).append(") ").toString());
        j3 = i2 - 0x10000;
        k3 = i1 + 1;
        ac[i1] = (char)(55296 + (j3 >> 10));
        l2 = 0xdc00 | j3 & 0x3ff;
        if(k3 < k) goto _L8; else goto _L7
_L7:
        mSurrogate = (char)l2;
        j1 = k3;
_L10:
        k1 = j1 - i;
        mCharCount = k1 + mCharCount;
          goto _L9
_L2:
        l = mLength - mPtr;
        if(l >= 4 || loadMore(l))
            break MISSING_BLOCK_LABEL_450;
        k1 = -1;
          goto _L9
_L8:
        int k2 = k3;
_L12:
        int i3;
label1:
        {
            i3 = k2 + 1;
            ac[k2] = (char)l2;
            if(mPtr < mLength)
                break label1;
            j1 = i3;
        }
          goto _L10
        i1 = i3;
          goto _L11
_L6:
        int j2 = i2;
        k2 = i1;
        l2 = j2;
          goto _L12
_L4:
        j1 = i1;
          goto _L10
        i1 = i;
          goto _L11
    }

    final boolean mBigEndian;
    int mByteCount;
    int mCharCount;
    char mSurrogate;
}
