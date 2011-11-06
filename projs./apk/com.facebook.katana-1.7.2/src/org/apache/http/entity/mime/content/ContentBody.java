// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContentBody.java

package org.apache.http.entity.mime.content;

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package org.apache.http.entity.mime.content:
//            ContentDescriptor

public interface ContentBody
    extends ContentDescriptor
{

    public abstract String getFilename();

    public abstract void writeTo(OutputStream outputstream)
        throws IOException;
}
