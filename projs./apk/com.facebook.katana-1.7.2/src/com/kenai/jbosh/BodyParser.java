// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodyParser.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            BOSHException, BodyParserResults

interface BodyParser
{

    public abstract BodyParserResults parse(String s)
        throws BOSHException;
}
