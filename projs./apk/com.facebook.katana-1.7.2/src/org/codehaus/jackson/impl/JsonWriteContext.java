// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import org.codehaus.jackson.JsonStreamContext;

// Referenced classes of package org.codehaus.jackson.impl:
//            RootWContext, ArrayWContext, ObjectWContext

public abstract class JsonWriteContext extends JsonStreamContext
{

    protected JsonWriteContext(int i, JsonWriteContext jsonwritecontext)
    {
        super(i);
        _childArray = null;
        _childObject = null;
        _parent = jsonwritecontext;
    }

    public static JsonWriteContext createRootContext()
    {
        return new RootWContext();
    }

    protected abstract void appendDesc(StringBuilder stringbuilder);

    public final JsonWriteContext createChildArrayContext()
    {
        Object obj = _childArray;
        if(obj == null)
        {
            obj = new ArrayWContext(this);
            _childArray = ((JsonWriteContext) (obj));
        } else
        {
            obj._index = -1;
        }
        return ((JsonWriteContext) (obj));
    }

    public final JsonWriteContext createChildObjectContext()
    {
        Object obj = _childObject;
        if(obj == null)
        {
            obj = new ObjectWContext(this);
            _childObject = ((JsonWriteContext) (obj));
        } else
        {
            obj._index = -1;
        }
        return ((JsonWriteContext) (obj));
    }

    public volatile JsonStreamContext getParent()
    {
        return getParent();
    }

    public final JsonWriteContext getParent()
    {
        return _parent;
    }

    public final String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(64);
        appendDesc(stringbuilder);
        return stringbuilder.toString();
    }

    public abstract int writeFieldName(String s);

    public abstract int writeValue();

    public static final int STATUS_EXPECT_NAME = 5;
    public static final int STATUS_EXPECT_VALUE = 4;
    public static final int STATUS_OK_AFTER_COLON = 2;
    public static final int STATUS_OK_AFTER_COMMA = 1;
    public static final int STATUS_OK_AFTER_SPACE = 3;
    public static final int STATUS_OK_AS_IS;
    JsonWriteContext _childArray;
    JsonWriteContext _childObject;
    protected final JsonWriteContext _parent;
}
