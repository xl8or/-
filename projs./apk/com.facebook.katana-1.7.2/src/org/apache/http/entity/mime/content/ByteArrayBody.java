// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ByteArrayBody.java

package org.apache.http.entity.mime.content;

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package org.apache.http.entity.mime.content:
//            AbstractContentBody

public class ByteArrayBody extends AbstractContentBody
{

    public ByteArrayBody(byte abyte0[], String s)
    {
        this(abyte0, "application/octet-stream", s);
    }

    public ByteArrayBody(byte abyte0[], String s, String s1)
    {
        super(s);
        if(abyte0 == null)
        {
            throw new IllegalArgumentException("byte[] may not be null");
        } else
        {
            data = abyte0;
            filename = s1;
            return;
        }
    }

    public String getCharset()
    {
        return null;
    }

    public long getContentLength()
    {
        return (long)data.length;
    }

    public String getFilename()
    {
        return filename;
    }

    public String getTransferEncoding()
    {
        return "binary";
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        outputstream.write(data);
    }

    private final byte data[];
    private final String filename;
}
