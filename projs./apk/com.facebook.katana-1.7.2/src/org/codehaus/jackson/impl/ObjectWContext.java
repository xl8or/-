// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;


// Referenced classes of package org.codehaus.jackson.impl:
//            JsonWriteContext

final class ObjectWContext extends JsonWriteContext
{

    public ObjectWContext(JsonWriteContext jsonwritecontext)
    {
        super(2, jsonwritecontext);
        _currentName = null;
        _expectValue = false;
    }

    protected void appendDesc(StringBuilder stringbuilder)
    {
        stringbuilder.append('{');
        if(_currentName != null)
        {
            stringbuilder.append('"');
            stringbuilder.append(_currentName);
            stringbuilder.append('"');
        } else
        {
            stringbuilder.append('?');
        }
        stringbuilder.append(']');
    }

    public String getCurrentName()
    {
        return _currentName;
    }

    public int writeFieldName(String s)
    {
        byte byte0;
        if(_currentName != null)
        {
            byte0 = 4;
        } else
        {
            _currentName = s;
            if(_index < 0)
                byte0 = 0;
            else
                byte0 = 1;
        }
        return byte0;
    }

    public int writeValue()
    {
        byte byte0;
        if(_currentName == null)
        {
            byte0 = 5;
        } else
        {
            _currentName = null;
            _index = 1 + _index;
            byte0 = 2;
        }
        return byte0;
    }

    protected String _currentName;
    protected boolean _expectValue;
}
