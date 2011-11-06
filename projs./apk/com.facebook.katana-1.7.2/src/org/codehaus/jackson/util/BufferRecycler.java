// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.util;


public final class BufferRecycler
{
    public static final class CharBufferType extends Enum
    {

        public static CharBufferType valueOf(String s)
        {
            return (CharBufferType)Enum.valueOf(org/codehaus/jackson/util/BufferRecycler$CharBufferType, s);
        }

        public static CharBufferType[] values()
        {
            return (CharBufferType[])$VALUES.clone();
        }

        private static final CharBufferType $VALUES[];
        public static final CharBufferType CONCAT_BUFFER;
        public static final CharBufferType NAME_COPY_BUFFER;
        public static final CharBufferType TEXT_BUFFER;
        public static final CharBufferType TOKEN_BUFFER;
        private final int size;

        static 
        {
            TOKEN_BUFFER = new CharBufferType("TOKEN_BUFFER", 0, 2000);
            CONCAT_BUFFER = new CharBufferType("CONCAT_BUFFER", 1, 2000);
            TEXT_BUFFER = new CharBufferType("TEXT_BUFFER", 2, 200);
            NAME_COPY_BUFFER = new CharBufferType("NAME_COPY_BUFFER", 3, 200);
            CharBufferType acharbuffertype[] = new CharBufferType[4];
            acharbuffertype[0] = TOKEN_BUFFER;
            acharbuffertype[1] = CONCAT_BUFFER;
            acharbuffertype[2] = TEXT_BUFFER;
            acharbuffertype[3] = NAME_COPY_BUFFER;
            $VALUES = acharbuffertype;
        }


        private CharBufferType(String s, int i, int j)
        {
            super(s, i);
            size = j;
        }
    }

    public static final class ByteBufferType extends Enum
    {

        public static ByteBufferType valueOf(String s)
        {
            return (ByteBufferType)Enum.valueOf(org/codehaus/jackson/util/BufferRecycler$ByteBufferType, s);
        }

        public static ByteBufferType[] values()
        {
            return (ByteBufferType[])$VALUES.clone();
        }

        private static final ByteBufferType $VALUES[];
        public static final ByteBufferType READ_IO_BUFFER;
        public static final ByteBufferType WRITE_IO_BUFFER;
        private final int size;

        static 
        {
            READ_IO_BUFFER = new ByteBufferType("READ_IO_BUFFER", 0, 4000);
            WRITE_IO_BUFFER = new ByteBufferType("WRITE_IO_BUFFER", 1, 4000);
            ByteBufferType abytebuffertype[] = new ByteBufferType[2];
            abytebuffertype[0] = READ_IO_BUFFER;
            abytebuffertype[1] = WRITE_IO_BUFFER;
            $VALUES = abytebuffertype;
        }


        private ByteBufferType(String s, int i, int j)
        {
            super(s, i);
            size = j;
        }
    }


    public BufferRecycler()
    {
    }

    private byte[] balloc(int i)
    {
        return new byte[i];
    }

    private char[] calloc(int i)
    {
        return new char[i];
    }

    public byte[] allocByteBuffer(ByteBufferType bytebuffertype)
    {
        int i = bytebuffertype.ordinal();
        byte abyte0[] = mByteBuffers[i];
        byte abyte1[];
        if(abyte0 == null)
        {
            abyte1 = balloc(bytebuffertype.size);
        } else
        {
            mByteBuffers[i] = null;
            abyte1 = abyte0;
        }
        return abyte1;
    }

    public char[] allocCharBuffer(CharBufferType charbuffertype)
    {
        return allocCharBuffer(charbuffertype, 0);
    }

    public char[] allocCharBuffer(CharBufferType charbuffertype, int i)
    {
        int j;
        int k;
        char ac[];
        char ac1[];
        if(charbuffertype.size > i)
            j = charbuffertype.size;
        else
            j = i;
        k = charbuffertype.ordinal();
        ac = mCharBuffers[k];
        if(ac == null || ac.length < j)
        {
            ac1 = calloc(j);
        } else
        {
            mCharBuffers[k] = null;
            ac1 = ac;
        }
        return ac1;
    }

    public void releaseByteBuffer(ByteBufferType bytebuffertype, byte abyte0[])
    {
        mByteBuffers[bytebuffertype.ordinal()] = abyte0;
    }

    public void releaseCharBuffer(CharBufferType charbuffertype, char ac[])
    {
        mCharBuffers[charbuffertype.ordinal()] = ac;
    }

    protected final byte mByteBuffers[][] = new byte[ByteBufferType.values().length][];
    protected final char mCharBuffers[][] = new char[CharBufferType.values().length][];
}
