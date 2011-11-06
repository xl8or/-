// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.schema;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public class JsonSchema
{

    public JsonSchema(ObjectNode objectnode)
    {
        schema = objectnode;
    }

    public static JsonNode getDefaultSchemaNode()
    {
        ObjectNode objectnode = JsonNodeFactory.instance.objectNode();
        objectnode.put("type", "any");
        objectnode.put("optional", true);
        return objectnode;
    }

    public boolean equals(Object obj)
    {
        return schema.equals(obj);
    }

    public ObjectNode getSchemaNode()
    {
        return schema;
    }

    public String toString()
    {
        return schema.toString();
    }

    private final ObjectNode schema;
}
