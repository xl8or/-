// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FormBodyPart.java

package org.apache.http.entity.mime;

import org.apache.http.entity.mime.content.ContentBody;

// Referenced classes of package org.apache.http.entity.mime:
//            Header, MinimalField

public class FormBodyPart
{

    public FormBodyPart(String s, ContentBody contentbody)
    {
        if(s == null)
            throw new IllegalArgumentException("Name may not be null");
        if(contentbody == null)
        {
            throw new IllegalArgumentException("Body may not be null");
        } else
        {
            name = s;
            body = contentbody;
            header = new Header();
            generateContentDisp(contentbody);
            generateContentType(contentbody);
            generateTransferEncoding(contentbody);
            return;
        }
    }

    public void addField(String s, String s1)
    {
        if(s == null)
        {
            throw new IllegalArgumentException("Field name may not be null");
        } else
        {
            header.addField(new MinimalField(s, s1));
            return;
        }
    }

    protected void generateContentDisp(ContentBody contentbody)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("form-data; name=\"");
        stringbuilder.append(getName());
        stringbuilder.append("\"");
        if(contentbody.getFilename() != null)
        {
            stringbuilder.append("; filename=\"");
            stringbuilder.append(contentbody.getFilename());
            stringbuilder.append("\"");
        }
        addField("Content-Disposition", stringbuilder.toString());
    }

    protected void generateContentType(ContentBody contentbody)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(contentbody.getMimeType());
        if(contentbody.getCharset() != null)
        {
            stringbuilder.append("; charset=");
            stringbuilder.append(contentbody.getCharset());
        }
        addField("Content-Type", stringbuilder.toString());
    }

    protected void generateTransferEncoding(ContentBody contentbody)
    {
        addField("Content-Transfer-Encoding", contentbody.getTransferEncoding());
    }

    public ContentBody getBody()
    {
        return body;
    }

    public Header getHeader()
    {
        return header;
    }

    public String getName()
    {
        return name;
    }

    private final ContentBody body;
    private final Header header;
    private final String name;
}
