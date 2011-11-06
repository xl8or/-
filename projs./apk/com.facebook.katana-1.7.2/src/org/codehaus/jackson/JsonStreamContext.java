// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;


public abstract class JsonStreamContext
{

    public JsonStreamContext(int i)
    {
        _type = i;
        _index = -1;
    }

    public final int getCurrentIndex()
    {
        int i;
        if(_index < 0)
            i = 0;
        else
            i = _index;
        return i;
    }

    public abstract String getCurrentName();

    public final int getEntryCount()
    {
        return 1 + _index;
    }

    public abstract JsonStreamContext getParent();

    public final String getTypeDesc()
    {
        _type;
        JVM INSTR tableswitch 0 2: default 32
    //                   0 37
    //                   1 43
    //                   2 49;
           goto _L1 _L2 _L3 _L4
_L1:
        String s = "?";
_L6:
        return s;
_L2:
        s = "ROOT";
        continue; /* Loop/switch isn't completed */
_L3:
        s = "ARRAY";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "OBJECT";
        if(true) goto _L6; else goto _L5
_L5:
    }

    public final boolean inArray()
    {
        boolean flag;
        if(_type == 1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final boolean inObject()
    {
        boolean flag;
        if(_type == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final boolean inRoot()
    {
        boolean flag;
        if(_type == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected static final int TYPE_ARRAY = 1;
    protected static final int TYPE_OBJECT = 2;
    protected static final int TYPE_ROOT;
    protected int _index;
    protected int _type;
}
