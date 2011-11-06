// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringBody.java

package org.apache.http.entity.mime.content;

import java.io.*;
import java.nio.charset.Charset;

// Referenced classes of package org.apache.http.entity.mime.content:
//            AbstractContentBody

public class StringBody extends AbstractContentBody
{

    public StringBody(String s)
        throws UnsupportedEncodingException
    {
        this(s, "text/plain", null);
    }

    public StringBody(String s, String s1, Charset charset1)
        throws UnsupportedEncodingException
    {
        super(s1);
        if(s == null)
            throw new IllegalArgumentException("Text may not be null");
        if(charset1 == null)
            charset1 = Charset.forName("US-ASCII");
        content = s.getBytes(charset1.name());
        charset = charset1;
    }

    public StringBody(String s, Charset charset1)
        throws UnsupportedEncodingException
    {
        this(s, "text/plain", charset1);
    }

    public static StringBody create(String s)
        throws IllegalArgumentException
    {
        return create(s, null, null);
    }

    public static StringBody create(String s, String s1, Charset charset1)
        throws IllegalArgumentException
    {
        StringBody stringbody;
        try
        {
            stringbody = new StringBody(s, s1, charset1);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Charset ").append(charset1).append(" is not supported").toString(), unsupportedencodingexception);
        }
        return stringbody;
    }

    public static StringBody create(String s, Charset charset1)
        throws IllegalArgumentException
    {
        return create(s, null, charset1);
    }

    public String getCharset()
    {
        return charset.name();
    }

    public long getContentLength()
    {
        return (long)content.length;
    }

    public String getFilename()
    {
        return null;
    }

    public Reader getReader()
    {
        return new InputStreamReader(new ByteArrayInputStream(content), charset);
    }

    public String getTransferEncoding()
    {
        return "8bit";
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        if(outputstream == null)
            throw new IllegalArgumentException("Output stream may not be null");
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(content);
        byte abyte0[] = new byte[4096];
        do
        {
            int i = bytearrayinputstream.read(abyte0);
            if(i != -1)
            {
                outputstream.write(abyte0, 0, i);
            } else
            {
                outputstream.flush();
                return;
            }
        } while(true);
    }

    public void writeTo(OutputStream outputstream, int i)
        throws IOException
    {
        writeTo(outputstream);
    }

    private final Charset charset;
    private final byte content[];
}
