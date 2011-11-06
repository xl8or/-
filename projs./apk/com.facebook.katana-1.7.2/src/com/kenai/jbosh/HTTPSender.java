// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HTTPSender.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            BOSHClientConfig, CMSessionParams, AbstractBody, HTTPResponse

interface HTTPSender
{

    public abstract void destroy();

    public abstract void init(BOSHClientConfig boshclientconfig);

    public abstract HTTPResponse send(CMSessionParams cmsessionparams, AbstractBody abstractbody);
}
