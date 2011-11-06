// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ZLIBCodec.java

package com.kenai.jbosh;

import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

final class ZLIBCodec
{

    private ZLIBCodec()
    {
    }

    public static byte[] decode(byte abyte0[])
        throws IOException
    {
        ByteArrayInputStream bytearrayinputstream;
        ByteArrayOutputStream bytearrayoutputstream;
        InflaterInputStream inflaterinputstream;
        bytearrayinputstream = new ByteArrayInputStream(abyte0);
        bytearrayoutputstream = new ByteArrayOutputStream();
        inflaterinputstream = null;
        InflaterInputStream inflaterinputstream1 = new InflaterInputStream(bytearrayinputstream);
        byte abyte2[];
        byte abyte1[] = new byte[512];
        int i;
        do
        {
            i = inflaterinputstream1.read(abyte1);
            if(i > 0)
                bytearrayoutputstream.write(abyte1, 0, i);
        } while(i >= 0);
        abyte2 = bytearrayoutputstream.toByteArray();
        inflaterinputstream1.close();
        bytearrayoutputstream.close();
        return abyte2;
        Exception exception;
        exception;
_L2:
        inflaterinputstream.close();
        bytearrayoutputstream.close();
        throw exception;
        exception;
        inflaterinputstream = inflaterinputstream1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static byte[] encode(byte abyte0[])
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DeflaterOutputStream deflateroutputstream = new DeflaterOutputStream(bytearrayoutputstream);
        byte abyte1[];
        deflateroutputstream.write(abyte0);
        deflateroutputstream.close();
        bytearrayoutputstream.close();
        abyte1 = bytearrayoutputstream.toByteArray();
        deflateroutputstream.close();
        bytearrayoutputstream.close();
        return abyte1;
        Exception exception1;
        exception1;
        Exception exception;
        deflateroutputstream = null;
        exception = exception1;
_L2:
        deflateroutputstream.close();
        bytearrayoutputstream.close();
        throw exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static String getID()
    {
        return "deflate";
    }

    private static final int BUFFER_SIZE = 512;
}
