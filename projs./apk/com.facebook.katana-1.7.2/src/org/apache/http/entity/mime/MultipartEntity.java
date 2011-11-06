// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultipartEntity.java

package org.apache.http.entity.mime;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.message.BasicHeader;

// Referenced classes of package org.apache.http.entity.mime:
//            HttpMultipartMode, HttpMultipart, FormBodyPart

public class MultipartEntity
    implements HttpEntity
{

    public MultipartEntity()
    {
        this(HttpMultipartMode.STRICT, null, null);
    }

    public MultipartEntity(HttpMultipartMode httpmultipartmode)
    {
        this(httpmultipartmode, null, null);
    }

    public MultipartEntity(HttpMultipartMode httpmultipartmode, String s, Charset charset)
    {
        if(s == null)
            s = generateBoundary();
        if(httpmultipartmode == null)
            httpmultipartmode = HttpMultipartMode.STRICT;
        multipart = new HttpMultipart("form-data", charset, s, httpmultipartmode);
        contentType = new BasicHeader("Content-Type", generateContentType(s, charset));
        dirty = true;
    }

    public void addPart(String s, ContentBody contentbody)
    {
        addPart(new FormBodyPart(s, contentbody));
    }

    public void addPart(FormBodyPart formbodypart)
    {
        multipart.addBodyPart(formbodypart);
        dirty = true;
    }

    public void consumeContent()
        throws IOException, UnsupportedOperationException
    {
        if(isStreaming())
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        else
            return;
    }

    protected String generateBoundary()
    {
        StringBuilder stringbuilder = new StringBuilder();
        Random random = new Random();
        int i = 30 + random.nextInt(11);
        for(int j = 0; j < i; j++)
            stringbuilder.append(MULTIPART_CHARS[random.nextInt(MULTIPART_CHARS.length)]);

        return stringbuilder.toString();
    }

    protected String generateContentType(String s, Charset charset)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("multipart/form-data; boundary=");
        stringbuilder.append(s);
        if(charset != null)
        {
            stringbuilder.append("; charset=");
            stringbuilder.append(charset.name());
        }
        return stringbuilder.toString();
    }

    public InputStream getContent()
        throws IOException, UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
    }

    public Header getContentEncoding()
    {
        return null;
    }

    public long getContentLength()
    {
        if(dirty)
        {
            length = multipart.getTotalLength();
            dirty = false;
        }
        return length;
    }

    public Header getContentType()
    {
        return contentType;
    }

    public boolean isChunked()
    {
        boolean flag;
        if(!isRepeatable())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isRepeatable()
    {
        Iterator iterator = multipart.getBodyParts().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(((FormBodyPart)iterator.next()).getBody().getContentLength() >= 0L) goto _L4; else goto _L3
_L3:
        boolean flag = false;
_L6:
        return flag;
_L2:
        flag = true;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isStreaming()
    {
        boolean flag;
        if(!isRepeatable())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        multipart.writeTo(outputstream);
    }

    private static final char MULTIPART_CHARS[] = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final Header contentType;
    private volatile boolean dirty;
    private long length;
    private final HttpMultipart multipart;

}
