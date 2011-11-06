// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodyParserResults.java

package com.kenai.jbosh;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.kenai.jbosh:
//            BodyQName

final class BodyParserResults
{

    BodyParserResults()
    {
    }

    void addBodyAttributeValue(BodyQName bodyqname, String s)
    {
        attrs.put(bodyqname, s);
    }

    Map getAttributes()
    {
        return attrs;
    }

    private final Map attrs = new HashMap();
}
