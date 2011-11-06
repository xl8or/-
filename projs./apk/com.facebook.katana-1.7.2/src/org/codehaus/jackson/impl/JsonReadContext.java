// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.util.CharTypes;

public final class JsonReadContext extends JsonStreamContext
{

    public JsonReadContext(JsonReadContext jsonreadcontext, int i, int j, int k)
    {
        super(i);
        _child = null;
        _parent = jsonreadcontext;
        _lineNr = j;
        _columnNr = k;
    }

    public static JsonReadContext createRootContext(int i, int j)
    {
        return new JsonReadContext(null, 0, i, j);
    }

    public final JsonReadContext createChildArrayContext(int i, int j)
    {
        JsonReadContext jsonreadcontext = _child;
        if(jsonreadcontext == null)
        {
            jsonreadcontext = new JsonReadContext(this, 1, i, j);
            _child = jsonreadcontext;
        } else
        {
            jsonreadcontext.reset(1, i, j);
        }
        return jsonreadcontext;
    }

    public final JsonReadContext createChildObjectContext(int i, int j)
    {
        JsonReadContext jsonreadcontext = _child;
        if(jsonreadcontext == null)
        {
            jsonreadcontext = new JsonReadContext(this, 2, i, j);
            _child = jsonreadcontext;
        } else
        {
            jsonreadcontext.reset(2, i, j);
        }
        return jsonreadcontext;
    }

    public final boolean expectComma()
    {
        int i = 1 + _index;
        _index = i;
        boolean flag;
        if(_type != 0 && i > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public final String getCurrentName()
    {
        return _currentName;
    }

    public volatile JsonStreamContext getParent()
    {
        return getParent();
    }

    public final JsonReadContext getParent()
    {
        return _parent;
    }

    public final JsonLocation getStartLocation(Object obj)
    {
        return new JsonLocation(obj, -1L, _lineNr, _columnNr);
    }

    protected final void reset(int i, int j, int k)
    {
        _type = i;
        _index = -1;
        _lineNr = j;
        _columnNr = k;
        _currentName = null;
    }

    public void setCurrentName(String s)
    {
        _currentName = s;
    }

    public final String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(64);
        _type;
        JVM INSTR tableswitch 0 2: default 40
    //                   0 45
    //                   1 55
    //                   2 81;
           goto _L1 _L2 _L3 _L4
_L1:
        return stringbuilder.toString();
_L2:
        stringbuilder.append("/");
        continue; /* Loop/switch isn't completed */
_L3:
        stringbuilder.append('[');
        stringbuilder.append(getCurrentIndex());
        stringbuilder.append(']');
        continue; /* Loop/switch isn't completed */
_L4:
        stringbuilder.append('{');
        if(_currentName != null)
        {
            stringbuilder.append('"');
            CharTypes.appendQuoted(stringbuilder, _currentName);
            stringbuilder.append('"');
        } else
        {
            stringbuilder.append('?');
        }
        stringbuilder.append(']');
        if(true) goto _L1; else goto _L5
_L5:
    }

    JsonReadContext _child;
    protected int _columnNr;
    protected String _currentName;
    protected int _lineNr;
    protected final JsonReadContext _parent;
}
