// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GZIPCodec.java

package com.kenai.jbosh;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

final class GZIPCodec
{

    private GZIPCodec()
    {
    }

    public static byte[] decode(byte abyte0[])
        throws IOException
    {
        ByteArrayInputStream bytearrayinputstream;
        ByteArrayOutputStream bytearrayoutputstream;
        GZIPInputStream gzipinputstream;
        bytearrayinputstream = new ByteArrayInputStream(abyte0);
        bytearrayoutputstream = new ByteArrayOutputStream();
        gzipinputstream = null;
        GZIPInputStream gzipinputstream1 = new GZIPInputStream(bytearrayinputstream);
        byte abyte2[];
        byte abyte1[] = new byte[512];
        int i;
        do
        {
            i = gzipinputstream1.read(abyte1);
            if(i > 0)
                bytearrayoutputstream.write(abyte1, 0, i);
        } while(i >= 0);
        abyte2 = bytearrayoutputstream.toByteArray();
        gzipinputstream1.close();
        bytearrayoutputstream.close();
        return abyte2;
        Exception exception;
        exception;
_L2:
        gzipinputstream.close();
        bytearrayoutputstream.close();
        throw exception;
        exception;
        gzipinputstream = gzipinputstream1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static byte[] encode(byte abyte0[])
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        GZIPOutputStream gzipoutputstream = new GZIPOutputStream(bytearrayoutputstream);
        byte abyte1[];
        gzipoutputstream.write(abyte0);
        gzipoutputstream.close();
        bytearrayoutputstream.close();
        abyte1 = bytearrayoutputstream.toByteArray();
        gzipoutputstream.close();
        bytearrayoutputstream.close();
        return abyte1;
        Exception exception1;
        exception1;
        Exception exception;
        gzipoutputstream = null;
        exception = exception1;
_L2:
        gzipoutputstream.close();
        bytearrayoutputstream.close();
        throw exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static String getID()
    {
        return "gzip";
    }

    private static final int BUFFER_SIZE = 512;
}
