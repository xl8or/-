// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.util;

import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder
{

    public ByteArrayBuilder()
    {
        _pastBlocks = new LinkedList();
        _currBlock = new byte[500];
    }

    private void _allocMoreAndAppend(byte byte0)
    {
        _pastLen = _pastLen + _currBlock.length;
        int i = Math.max(_pastLen >> 1, 1000);
        if(i > 0x40000)
            i = 0x40000;
        _pastBlocks.add(_currBlock);
        _currBlock = new byte[i];
        _currBlockPtr = 0;
        byte abyte0[] = _currBlock;
        int j = _currBlockPtr;
        _currBlockPtr = j + 1;
        abyte0[j] = byte0;
    }

    public void append(int i)
    {
        byte byte0 = (byte)i;
        if(_currBlockPtr < _currBlock.length)
        {
            byte abyte0[] = _currBlock;
            int j = _currBlockPtr;
            _currBlockPtr = j + 1;
            abyte0[j] = byte0;
        } else
        {
            _allocMoreAndAppend(byte0);
        }
    }

    public void appendThreeBytes(int i)
    {
        if(2 + _currBlockPtr < _currBlock.length)
        {
            byte abyte0[] = _currBlock;
            int j = _currBlockPtr;
            _currBlockPtr = j + 1;
            abyte0[j] = (byte)(i >> 16);
            byte abyte1[] = _currBlock;
            int k = _currBlockPtr;
            _currBlockPtr = k + 1;
            abyte1[k] = (byte)(i >> 8);
            byte abyte2[] = _currBlock;
            int l = _currBlockPtr;
            _currBlockPtr = l + 1;
            abyte2[l] = (byte)i;
        } else
        {
            append(i >> 16);
            append(i >> 8);
            append(i);
        }
    }

    public void appendTwoBytes(int i)
    {
        if(1 + _currBlockPtr < _currBlock.length)
        {
            byte abyte0[] = _currBlock;
            int j = _currBlockPtr;
            _currBlockPtr = j + 1;
            abyte0[j] = (byte)(i >> 8);
            byte abyte1[] = _currBlock;
            int k = _currBlockPtr;
            _currBlockPtr = k + 1;
            abyte1[k] = (byte)i;
        } else
        {
            append(i >> 8);
            append(i);
        }
    }

    public void reset()
    {
        _pastLen = 0;
        _currBlockPtr = 0;
        if(!_pastBlocks.isEmpty())
        {
            _currBlock = (byte[])_pastBlocks.getLast();
            _pastBlocks.clear();
        }
    }

    public byte[] toByteArray()
    {
        int i = _pastLen + _currBlockPtr;
        byte abyte1[];
        if(i == 0)
        {
            abyte1 = NO_BYTES;
        } else
        {
            byte abyte0[] = new byte[i];
            Iterator iterator = _pastBlocks.iterator();
            int j;
            int l;
            for(j = 0; iterator.hasNext(); j += l)
            {
                byte abyte2[] = (byte[])iterator.next();
                l = abyte2.length;
                System.arraycopy(abyte2, 0, abyte0, j, l);
            }

            System.arraycopy(_currBlock, 0, abyte0, j, _currBlockPtr);
            int k = j + _currBlockPtr;
            if(k != i)
                throw new RuntimeException((new StringBuilder()).append("Internal error: total len assumed to be ").append(i).append(", copied ").append(k).append(" bytes").toString());
            if(!_pastBlocks.isEmpty())
                reset();
            abyte1 = abyte0;
        }
        return abyte1;
    }

    static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
    private static final int INITIAL_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 0x40000;
    private static final byte NO_BYTES[] = new byte[0];
    private byte _currBlock[];
    private int _currBlockPtr;
    private LinkedList _pastBlocks;
    private int _pastLen;

}
