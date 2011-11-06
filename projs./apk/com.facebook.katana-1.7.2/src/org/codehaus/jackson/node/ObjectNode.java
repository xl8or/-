// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            ContainerNode, MissingNode, BaseJsonNode, TextNode, 
//            JsonNodeFactory, ArrayNode

public class ObjectNode extends ContainerNode
{
    protected static class NoFieldsIterator
        implements Iterator
    {

        public boolean hasNext()
        {
            return false;
        }

        public volatile Object next()
        {
            return next();
        }

        public java.util.Map.Entry next()
        {
            throw new NoSuchElementException();
        }

        public void remove()
        {
            throw new IllegalStateException();
        }

        static final NoFieldsIterator instance = new NoFieldsIterator();


        private NoFieldsIterator()
        {
        }
    }


    public ObjectNode(JsonNodeFactory jsonnodefactory)
    {
        super(jsonnodefactory);
        _children = null;
    }

    private final JsonNode _put(String s, JsonNode jsonnode)
    {
        if(_children == null)
            _children = new LinkedHashMap();
        return (JsonNode)_children.put(s, jsonnode);
    }

    public boolean equals(Object obj)
    {
        if(obj != this) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
label0:
        {
            if(obj == null)
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            if(obj.getClass() != getClass())
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            ObjectNode objectnode = (ObjectNode)obj;
            if(objectnode.size() != size())
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            if(_children == null)
                break label0;
            Iterator iterator = _children.entrySet().iterator();
            JsonNode jsonnode;
            JsonNode jsonnode1;
            do
            {
                if(!iterator.hasNext())
                    break label0;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                String s = (String)entry.getKey();
                jsonnode = (JsonNode)entry.getValue();
                jsonnode1 = objectnode.get(s);
            } while(jsonnode1 != null && jsonnode1.equals(jsonnode));
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public JsonNode get(int i)
    {
        return null;
    }

    public JsonNode get(String s)
    {
        JsonNode jsonnode;
        if(_children != null)
            jsonnode = (JsonNode)_children.get(s);
        else
            jsonnode = null;
        return jsonnode;
    }

    public Iterator getElements()
    {
        Object obj;
        if(_children == null)
            obj = ContainerNode.NoNodesIterator.instance();
        else
            obj = _children.values().iterator();
        return ((Iterator) (obj));
    }

    public Iterator getFieldNames()
    {
        Object obj;
        if(_children == null)
            obj = ContainerNode.NoStringsIterator.instance();
        else
            obj = _children.keySet().iterator();
        return ((Iterator) (obj));
    }

    public Iterator getFields()
    {
        Object obj;
        if(_children == null)
            obj = NoFieldsIterator.instance;
        else
            obj = _children.entrySet().iterator();
        return ((Iterator) (obj));
    }

    public int hashCode()
    {
        int i;
        if(_children == null)
            i = -1;
        else
            i = _children.hashCode();
        return i;
    }

    public boolean isObject()
    {
        return true;
    }

    public JsonNode path(int i)
    {
        return MissingNode.getInstance();
    }

    public JsonNode path(String s)
    {
        if(_children == null) goto _L2; else goto _L1
_L1:
        JsonNode jsonnode = (JsonNode)_children.get(s);
        if(jsonnode == null) goto _L2; else goto _L3
_L3:
        Object obj = jsonnode;
_L5:
        return ((JsonNode) (obj));
_L2:
        obj = MissingNode.getInstance();
        if(true) goto _L5; else goto _L4
_L4:
    }

    public JsonNode put(String s, JsonNode jsonnode)
    {
        Object obj;
        if(jsonnode == null)
            obj = nullNode();
        else
            obj = jsonnode;
        return _put(s, ((JsonNode) (obj)));
    }

    public void put(String s, double d)
    {
        _put(s, numberNode(d));
    }

    public void put(String s, float f)
    {
        _put(s, numberNode(f));
    }

    public void put(String s, int i)
    {
        _put(s, numberNode(i));
    }

    public void put(String s, long l)
    {
        _put(s, numberNode(l));
    }

    public void put(String s, String s1)
    {
        _put(s, textNode(s1));
    }

    public void put(String s, BigDecimal bigdecimal)
    {
        _put(s, numberNode(bigdecimal));
    }

    public void put(String s, boolean flag)
    {
        _put(s, booleanNode(flag));
    }

    public void put(String s, byte abyte0[])
    {
        _put(s, binaryNode(abyte0));
    }

    public ArrayNode putArray(String s)
    {
        ArrayNode arraynode = arrayNode();
        _put(s, arraynode);
        return arraynode;
    }

    public void putNull(String s)
    {
        _put(s, nullNode());
    }

    public ObjectNode putObject(String s)
    {
        ObjectNode objectnode = objectNode();
        _put(s, objectnode);
        return objectnode;
    }

    public void putPOJO(String s, Object obj)
    {
        _put(s, POJONode(obj));
    }

    public JsonNode remove(String s)
    {
        JsonNode jsonnode;
        if(_children != null)
            jsonnode = (JsonNode)_children.remove(s);
        else
            jsonnode = null;
        return jsonnode;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        jsongenerator.writeStartObject();
        if(_children != null)
        {
            java.util.Map.Entry entry;
            for(Iterator iterator = _children.entrySet().iterator(); iterator.hasNext(); ((BaseJsonNode)entry.getValue()).serialize(jsongenerator, serializerprovider))
            {
                entry = (java.util.Map.Entry)iterator.next();
                jsongenerator.writeFieldName((String)entry.getKey());
            }

        }
        jsongenerator.writeEndObject();
    }

    public int size()
    {
        int i;
        if(_children == null)
            i = 0;
        else
            i = _children.size();
        return i;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(32 + (size() << 4));
        stringbuilder.append("{");
        if(_children != null)
        {
            int i = 0;
            for(Iterator iterator = _children.entrySet().iterator(); iterator.hasNext();)
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                if(i > 0)
                    stringbuilder.append(",");
                int j = i + 1;
                TextNode.appendQuoted(stringbuilder, (String)entry.getKey());
                stringbuilder.append(':');
                stringbuilder.append(((JsonNode)entry.getValue()).toString());
                i = j;
            }

        }
        stringbuilder.append("}");
        return stringbuilder.toString();
    }

    LinkedHashMap _children;
}
