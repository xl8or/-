// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApiMethodListener.java

package com.facebook.katana.service.method;


// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod

public interface ApiMethodListener
{

    public abstract void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception);

    public abstract void onOperationProgress(ApiMethod apimethod, long l, long l1);

    public abstract void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception);
}
