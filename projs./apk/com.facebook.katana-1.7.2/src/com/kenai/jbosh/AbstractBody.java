// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractBody.java

package com.kenai.jbosh;

import java.util.*;

// Referenced classes of package com.kenai.jbosh:
//            BodyQName

public abstract class AbstractBody
{

    AbstractBody()
    {
    }

    static BodyQName getBodyQName()
    {
        return BodyQName.createBOSH("body");
    }

    public final String getAttribute(BodyQName bodyqname)
    {
        return (String)getAttributes().get(bodyqname);
    }

    public final Set getAttributeNames()
    {
        return Collections.unmodifiableSet(getAttributes().keySet());
    }

    public abstract Map getAttributes();

    public abstract String toXML();
}
