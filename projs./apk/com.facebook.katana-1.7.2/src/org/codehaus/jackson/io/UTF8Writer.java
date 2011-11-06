// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;

import java.io.*;

// Referenced classes of package org.codehaus.jackson.io:
//            IOContext

public final class UTF8Writer extends Writer
{

    public UTF8Writer(IOContext iocontext, OutputStream outputstream)
    {
        mSurrogate = 0;
        mContext = iocontext;
        mOut = outputstream;
        mOutBuffer = iocontext.allocWriteIOBuffer();
        mOutBufferLast = mOutBuffer.length - 4;
        mOutPtr = 0;
    }

    private int convertSurrogate(int i)
        throws IOException
    {
        int j = mSurrogate;
        mSurrogate = 0;
        if(i < 56320 || i > 57343)
            throw new IOException((new StringBuilder()).append("Broken surrogate pair: first char 0x").append(Integer.toHexString(j)).append(", second 0x").append(Integer.toHexString(i)).append("; illegal combination").toString());
        else
            return 0x10000 + (j - 55296 << 10) + (i - 56320);
    }

    private void throwIllegal(int i)
        throws IOException
    {
        if(i > 0x10ffff)
            throw new IOException((new StringBuilder()).append("Illegal character point (0x").append(Integer.toHexString(i)).append(") to output; max is 0x10FFFF as per RFC 4627").toString());
        if(i >= 55296)
        {
            if(i <= 56319)
                throw new IOException((new StringBuilder()).append("Unmatched first part of surrogate pair (0x").append(Integer.toHexString(i)).append(")").toString());
            else
                throw new IOException((new StringBuilder()).append("Unmatched second part of surrogate pair (0x").append(Integer.toHexString(i)).append(")").toString());
        } else
        {
            throw new IOException((new StringBuilder()).append("Illegal character point (0x").append(Integer.toHexString(i)).append(") to output").toString());
        }
    }

    public Writer append(char c)
        throws IOException
    {
        write(c);
        return this;
    }

    public volatile Appendable append(char c)
        throws IOException
    {
        return append(c);
    }

    public void close()
        throws IOException
    {
        if(mOut != null)
        {
            if(mOutPtr > 0)
            {
                mOut.write(mOutBuffer, 0, mOutPtr);
                mOutPtr = 0;
            }
            OutputStream outputstream = mOut;
            mOut = null;
            byte abyte0[] = mOutBuffer;
            if(abyte0 != null)
            {
                mOutBuffer = null;
                mContext.releaseWriteIOBuffer(abyte0);
            }
            outputstream.close();
            int i = mSurrogate;
            mSurrogate = 0;
            if(i > 0)
                throwIllegal(i);
        }
    }

    public void flush()
        throws IOException
    {
        if(mOutPtr > 0)
        {
            mOut.write(mOutBuffer, 0, mOutPtr);
            mOutPtr = 0;
        }
        mOut.flush();
    }

    public void write(int i)
        throws IOException
    {
        if(mSurrogate <= 0) goto _L2; else goto _L1
_L1:
        int j = convertSurrogate(i);
_L6:
        if(mOutPtr >= mOutBufferLast)
        {
            mOut.write(mOutBuffer, 0, mOutPtr);
            mOutPtr = 0;
        }
        if(j < 128)
        {
            byte abyte9[] = mOutBuffer;
            int j3 = mOutPtr;
            mOutPtr = j3 + 1;
            abyte9[j3] = (byte)j;
        } else
        {
            int k = mOutPtr;
            int l1;
            if(j < 2048)
            {
                byte abyte7[] = mOutBuffer;
                int l2 = k + 1;
                abyte7[k] = (byte)(0xc0 | j >> 6);
                byte abyte8[] = mOutBuffer;
                int i3 = l2 + 1;
                abyte8[l2] = (byte)(0x80 | j & 0x3f);
                l1 = i3;
            } else
            if(j <= 65535)
            {
                byte abyte4[] = mOutBuffer;
                int i2 = k + 1;
                abyte4[k] = (byte)(0xe0 | j >> 12);
                byte abyte5[] = mOutBuffer;
                int j2 = i2 + 1;
                abyte5[i2] = (byte)(0x80 | 0x3f & j >> 6);
                byte abyte6[] = mOutBuffer;
                int k2 = j2 + 1;
                abyte6[j2] = (byte)(0x80 | j & 0x3f);
                l1 = k2;
            } else
            {
                if(j > 0x10ffff)
                    throwIllegal(j);
                byte abyte0[] = mOutBuffer;
                int l = k + 1;
                abyte0[k] = (byte)(0xf0 | j >> 18);
                byte abyte1[] = mOutBuffer;
                int i1 = l + 1;
                abyte1[l] = (byte)(0x80 | 0x3f & j >> 12);
                byte abyte2[] = mOutBuffer;
                int j1 = i1 + 1;
                abyte2[i1] = (byte)(0x80 | 0x3f & j >> 6);
                byte abyte3[] = mOutBuffer;
                int k1 = j1 + 1;
                abyte3[j1] = (byte)(0x80 | j & 0x3f);
                l1 = k1;
            }
            mOutPtr = l1;
        }
_L4:
        return;
_L2:
        if(i < 55296 || i > 57343)
            break; /* Loop/switch isn't completed */
        if(i > 56319)
            throwIllegal(i);
        mSurrogate = i;
        if(true) goto _L4; else goto _L3
_L3:
        j = i;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void write(String s)
        throws IOException
    {
        write(s, 0, s.length());
    }

    public void write(String s, int i, int j)
        throws IOException
    {
        if(j >= 2) goto _L2; else goto _L1
_L1:
        if(j == 1)
            write(s.charAt(i));
_L10:
        return;
_L2:
        int k;
        int l;
        int i1;
        byte abyte0[];
        int j1;
        int k1;
        int l1;
        int i2;
        int j2;
        int k2;
        char c;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        int l4;
        int i5;
        int j5;
        int k5;
        int l5;
        int i6;
        int j6;
        int k6;
        int l6;
        int i7;
        char c1;
        int j7;
        if(mSurrogate > 0)
        {
            int k7 = i + 1;
            char c2 = s.charAt(i);
            int l7 = j + -1;
            write(convertSurrogate(c2));
            l = k7;
            k = l7;
        } else
        {
            k = j;
            l = i;
        }
        i1 = mOutPtr;
        abyte0 = mOutBuffer;
        j1 = mOutBufferLast;
        k1 = k + l;
        l1 = l;
        i2 = i1;
        if(l1 >= k1)
            break MISSING_BLOCK_LABEL_616;
        if(i2 >= j1)
        {
            mOut.write(abyte0, 0, i2);
            i2 = 0;
        }
        k2 = l1 + 1;
        c = s.charAt(l1);
        if(c >= '\200') goto _L4; else goto _L3
_L3:
        i6 = i2 + 1;
        abyte0[i2] = (byte)c;
        j6 = k1 - k2;
        k6 = j1 - i6;
        if(j6 > k6)
            j6 = k6;
        l6 = j6 + k2;
        l2 = i6;
_L7:
label0:
        {
            if(k2 < l6)
                break label0;
            i2 = l2;
            l1 = k2;
        }
        break MISSING_BLOCK_LABEL_96;
        i7 = k2 + 1;
        c1 = s.charAt(k2);
        if(c1 < '\200') goto _L6; else goto _L5
_L5:
        i3 = c1;
        k2 = i7;
_L11:
        if(i3 < 2048)
        {
            k5 = l2 + 1;
            abyte0[l2] = (byte)(0xc0 | i3 >> 6);
            l5 = k5 + 1;
            abyte0[k5] = (byte)(0x80 | i3 & 0x3f);
            i2 = l5;
            l1 = k2;
        } else
        {
label1:
            {
                if(i3 >= 55296 && i3 <= 57343)
                    break label1;
                j3 = l2 + 1;
                abyte0[l2] = (byte)(0xe0 | i3 >> 12);
                k3 = j3 + 1;
                abyte0[j3] = (byte)(0x80 | 0x3f & i3 >> 6);
                l3 = k3 + 1;
                abyte0[k3] = (byte)(0x80 | i3 & 0x3f);
                i2 = l3;
                l1 = k2;
            }
        }
        break MISSING_BLOCK_LABEL_96;
_L6:
        j7 = l2 + 1;
        abyte0[l2] = (byte)c1;
        l2 = j7;
        k2 = i7;
          goto _L7
        if(i3 > 56319)
        {
            mOutPtr = l2;
            throwIllegal(i3);
        }
        mSurrogate = i3;
        if(k2 < k1) goto _L9; else goto _L8
_L8:
        j2 = l2;
_L12:
        mOutPtr = j2;
          goto _L10
_L9:
        i4 = k2 + 1;
        j4 = convertSurrogate(s.charAt(k2));
        if(j4 > 0x10ffff)
        {
            mOutPtr = l2;
            throwIllegal(j4);
        }
        k4 = l2 + 1;
        abyte0[l2] = (byte)(0xf0 | j4 >> 18);
        l4 = k4 + 1;
        abyte0[k4] = (byte)(0x80 | 0x3f & j4 >> 12);
        i5 = l4 + 1;
        abyte0[l4] = (byte)(0x80 | 0x3f & j4 >> 6);
        j5 = i5 + 1;
        abyte0[i5] = (byte)(0x80 | j4 & 0x3f);
        l1 = i4;
        i2 = j5;
        break MISSING_BLOCK_LABEL_96;
_L4:
        l2 = i2;
        i3 = c;
          goto _L11
        j2 = i2;
          goto _L12
    }

    public void write(char ac[])
        throws IOException
    {
        write(ac, 0, ac.length);
    }

    public void write(char ac[], int i, int j)
        throws IOException
    {
        if(j >= 2) goto _L2; else goto _L1
_L1:
        if(j == 1)
            write(ac[i]);
_L10:
        return;
_L2:
        int k;
        int l;
        int i1;
        byte abyte0[];
        int j1;
        int k1;
        int l1;
        int i2;
        int j2;
        int k2;
        char c;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        int l4;
        int i5;
        int j5;
        int k5;
        int l5;
        int i6;
        int j6;
        int k6;
        int l6;
        int i7;
        char c1;
        int j7;
        if(mSurrogate > 0)
        {
            int k7 = i + 1;
            char c2 = ac[i];
            int l7 = j + -1;
            write(convertSurrogate(c2));
            l = k7;
            k = l7;
        } else
        {
            k = j;
            l = i;
        }
        i1 = mOutPtr;
        abyte0 = mOutBuffer;
        j1 = mOutBufferLast;
        k1 = k + l;
        l1 = l;
        i2 = i1;
        if(l1 >= k1)
            break MISSING_BLOCK_LABEL_606;
        if(i2 >= j1)
        {
            mOut.write(abyte0, 0, i2);
            i2 = 0;
        }
        k2 = l1 + 1;
        c = ac[l1];
        if(c >= '\200') goto _L4; else goto _L3
_L3:
        i6 = i2 + 1;
        abyte0[i2] = (byte)c;
        j6 = k1 - k2;
        k6 = j1 - i6;
        if(j6 > k6)
            j6 = k6;
        l6 = j6 + k2;
        l2 = i6;
_L7:
label0:
        {
            if(k2 < l6)
                break label0;
            i2 = l2;
            l1 = k2;
        }
        break MISSING_BLOCK_LABEL_92;
        i7 = k2 + 1;
        c1 = ac[k2];
        if(c1 < '\200') goto _L6; else goto _L5
_L5:
        i3 = c1;
        k2 = i7;
_L11:
        if(i3 < 2048)
        {
            k5 = l2 + 1;
            abyte0[l2] = (byte)(0xc0 | i3 >> 6);
            l5 = k5 + 1;
            abyte0[k5] = (byte)(0x80 | i3 & 0x3f);
            i2 = l5;
            l1 = k2;
        } else
        {
label1:
            {
                if(i3 >= 55296 && i3 <= 57343)
                    break label1;
                j3 = l2 + 1;
                abyte0[l2] = (byte)(0xe0 | i3 >> 12);
                k3 = j3 + 1;
                abyte0[j3] = (byte)(0x80 | 0x3f & i3 >> 6);
                l3 = k3 + 1;
                abyte0[k3] = (byte)(0x80 | i3 & 0x3f);
                i2 = l3;
                l1 = k2;
            }
        }
        break MISSING_BLOCK_LABEL_92;
_L6:
        j7 = l2 + 1;
        abyte0[l2] = (byte)c1;
        l2 = j7;
        k2 = i7;
          goto _L7
        if(i3 > 56319)
        {
            mOutPtr = l2;
            throwIllegal(i3);
        }
        mSurrogate = i3;
        if(k2 < k1) goto _L9; else goto _L8
_L8:
        j2 = l2;
_L12:
        mOutPtr = j2;
          goto _L10
_L9:
        i4 = k2 + 1;
        j4 = convertSurrogate(ac[k2]);
        if(j4 > 0x10ffff)
        {
            mOutPtr = l2;
            throwIllegal(j4);
        }
        k4 = l2 + 1;
        abyte0[l2] = (byte)(0xf0 | j4 >> 18);
        l4 = k4 + 1;
        abyte0[k4] = (byte)(0x80 | 0x3f & j4 >> 12);
        i5 = l4 + 1;
        abyte0[l4] = (byte)(0x80 | 0x3f & j4 >> 6);
        j5 = i5 + 1;
        abyte0[i5] = (byte)(0x80 | j4 & 0x3f);
        l1 = i4;
        i2 = j5;
        break MISSING_BLOCK_LABEL_92;
_L4:
        l2 = i2;
        i3 = c;
          goto _L11
        j2 = i2;
          goto _L12
    }

    static final int SURR1_FIRST = 55296;
    static final int SURR1_LAST = 56319;
    static final int SURR2_FIRST = 56320;
    static final int SURR2_LAST = 57343;
    protected final IOContext mContext;
    OutputStream mOut;
    byte mOutBuffer[];
    final int mOutBufferLast;
    int mOutPtr;
    int mSurrogate;
}
