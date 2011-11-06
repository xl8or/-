// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DNSInput.java

package org.xbill.DNS;


// Referenced classes of package org.xbill.DNS:
//            WireParseException

public class DNSInput
{

    public DNSInput(byte abyte0[])
    {
        array = abyte0;
        pos = 0;
        end = array.length;
        saved_pos = -1;
        saved_end = -1;
    }

    private void require(int i)
        throws WireParseException
    {
        if(i > remaining())
            throw new WireParseException("end of input");
        else
            return;
    }

    public void clearActive()
    {
        end = array.length;
    }

    public int current()
    {
        return pos;
    }

    public void jump(int i)
    {
        if(i >= array.length)
        {
            throw new IllegalArgumentException("cannot jump past end of input");
        } else
        {
            pos = i;
            end = array.length;
            return;
        }
    }

    public void readByteArray(byte abyte0[], int i, int j)
        throws WireParseException
    {
        require(j);
        System.arraycopy(array, pos, abyte0, i, j);
        pos = j + pos;
    }

    public byte[] readByteArray()
    {
        int i = remaining();
        byte abyte0[] = new byte[i];
        System.arraycopy(array, pos, abyte0, 0, i);
        pos = i + pos;
        return abyte0;
    }

    public byte[] readByteArray(int i)
        throws WireParseException
    {
        require(i);
        byte abyte0[] = new byte[i];
        System.arraycopy(array, pos, abyte0, 0, i);
        pos = i + pos;
        return abyte0;
    }

    public byte[] readCountedString()
        throws WireParseException
    {
        require(1);
        byte abyte0[] = array;
        int i = pos;
        pos = i + 1;
        return readByteArray(0xff & abyte0[i]);
    }

    public int readU16()
        throws WireParseException
    {
        require(2);
        byte abyte0[] = array;
        int i = pos;
        pos = i + 1;
        int j = 0xff & abyte0[i];
        byte abyte1[] = array;
        int k = pos;
        pos = k + 1;
        return (0xff & abyte1[k]) + (j << 8);
    }

    public long readU32()
        throws WireParseException
    {
        require(4);
        byte abyte0[] = array;
        int i = pos;
        pos = i + 1;
        int j = 0xff & abyte0[i];
        byte abyte1[] = array;
        int k = pos;
        pos = k + 1;
        int l = 0xff & abyte1[k];
        byte abyte2[] = array;
        int i1 = pos;
        pos = i1 + 1;
        int j1 = 0xff & abyte2[i1];
        byte abyte3[] = array;
        int k1 = pos;
        pos = k1 + 1;
        int l1 = 0xff & abyte3[k1];
        return ((long)j << 24) + (long)(l << 16) + (long)(j1 << 8) + (long)l1;
    }

    public int readU8()
        throws WireParseException
    {
        require(1);
        byte abyte0[] = array;
        int i = pos;
        pos = i + 1;
        return 0xff & abyte0[i];
    }

    public int remaining()
    {
        return end - pos;
    }

    public void restore()
    {
        if(saved_pos < 0)
        {
            throw new IllegalStateException("no previous state");
        } else
        {
            pos = saved_pos;
            end = saved_end;
            saved_pos = -1;
            saved_end = -1;
            return;
        }
    }

    public void save()
    {
        saved_pos = pos;
        saved_end = end;
    }

    public void setActive(int i)
    {
        if(i > array.length - pos)
        {
            throw new IllegalArgumentException("cannot set active region past end of input");
        } else
        {
            end = i + pos;
            return;
        }
    }

    private byte array[];
    private int end;
    private int pos;
    private int saved_end;
    private int saved_pos;
}
