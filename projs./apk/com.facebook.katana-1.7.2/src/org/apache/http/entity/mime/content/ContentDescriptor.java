// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContentDescriptor.java

package org.apache.http.entity.mime.content;


public interface ContentDescriptor
{

    public abstract String getCharset();

    public abstract long getContentLength();

    public abstract String getMediaType();

    public abstract String getMimeType();

    public abstract String getSubType();

    public abstract String getTransferEncoding();
}
