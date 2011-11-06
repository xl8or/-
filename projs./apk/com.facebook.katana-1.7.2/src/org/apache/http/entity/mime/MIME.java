// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MIME.java

package org.apache.http.entity.mime;

import java.nio.charset.Charset;

public final class MIME
{

    public MIME()
    {
    }

    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_TRANSFER_ENC = "Content-Transfer-Encoding";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final Charset DEFAULT_CHARSET = Charset.forName("US-ASCII");
    public static final String ENC_8BIT = "8bit";
    public static final String ENC_BINARY = "binary";

}
