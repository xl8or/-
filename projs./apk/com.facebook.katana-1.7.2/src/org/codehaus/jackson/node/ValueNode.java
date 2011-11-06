// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import org.codehaus.jackson.JsonNode;

// Referenced classes of package org.codehaus.jackson.node:
//            BaseJsonNode, MissingNode

public abstract class ValueNode extends BaseJsonNode
{

    protected ValueNode()
    {
    }

    public boolean isValueNode()
    {
        return true;
    }

    public JsonNode path(int i)
    {
        return MissingNode.getInstance();
    }

    public JsonNode path(String s)
    {
        return MissingNode.getInstance();
    }

    public String toString()
    {
        return getValueAsText();
    }
}
