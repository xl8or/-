// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;


// Referenced classes of package org.codehaus.jackson.impl:
//            JsonWriteContext

final class ArrayWContext extends JsonWriteContext
{

    public ArrayWContext(JsonWriteContext jsonwritecontext)
    {
        super(1, jsonwritecontext);
    }

    protected void appendDesc(StringBuilder stringbuilder)
    {
        stringbuilder.append('[');
        stringbuilder.append(getCurrentIndex());
        stringbuilder.append(']');
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
        int i = _index;
        _index = 1 + _index;
        int j;
        if(i < 0)
            j = 0;
        else
            j = 1;
        return j;
    }
}
