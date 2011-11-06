// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;


// Referenced classes of package org.codehaus.jackson.impl:
//            JsonWriteContext

final class RootWContext extends JsonWriteContext
{

    public RootWContext()
    {
        super(0, null);
    }

    protected void appendDesc(StringBuilder stringbuilder)
    {
        stringbuilder.append("/");
    }

    public String getCurrentName()
    {
        return null;
    }

    public int writeFieldName(String s)
    {
        return 4;
    }

    public int writeValue()
    {
        _index = 1 + _index;
        int i;
        if(_index == 0)
            i = 0;
        else
            i = 3;
        return i;
    }
}
