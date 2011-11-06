// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            ContainerNode, MissingNode, BaseJsonNode, JsonNodeFactory, 
//            ObjectNode

public final class ArrayNode extends ContainerNode
{

    public ArrayNode(JsonNodeFactory jsonnodefactory)
    {
        super(jsonnodefactory);
    }

    private void _add(JsonNode jsonnode)
    {
        if(_children == null)
            _children = new ArrayList();
        _children.add(jsonnode);
    }

    private void _insert(int i, JsonNode jsonnode)
    {
        if(_children == null)
        {
            _children = new ArrayList();
            _children.add(jsonnode);
        } else
        if(i < 0)
            _children.add(0, jsonnode);
        else
        if(i >= _children.size())
            _children.add(jsonnode);
        else
            _children.add(i, jsonnode);
    }

    private boolean _sameChildren(ArrayList arraylist)
    {
        int i = arraylist.size();
        if(_children.size() == i) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        int j = 0;
        do
        {
            if(j >= i)
                break;
            if(!((JsonNode)_children.get(j)).equals(arraylist.get(j)))
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            j++;
        } while(true);
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public JsonNode _set(int i, JsonNode jsonnode)
    {
        if(_children == null || i < 0 || i >= _children.size())
            throw new IndexOutOfBoundsException((new StringBuilder()).append("Illegal index ").append(i).append(", array size ").append(size()).toString());
        else
            return (JsonNode)_children.set(i, jsonnode);
    }

    public void add(double d)
    {
        _add(numberNode(d));
    }

    public void add(float f)
    {
        _add(numberNode(f));
    }

    public void add(int i)
    {
        _add(numberNode(i));
    }

    public void add(long l)
    {
        _add(numberNode(l));
    }

    public void add(String s)
    {
        _add(textNode(s));
    }

    public void add(BigDecimal bigdecimal)
    {
        _add(numberNode(bigdecimal));
    }

    public void add(JsonNode jsonnode)
    {
        Object obj;
        if(jsonnode == null)
            obj = nullNode();
        else
            obj = jsonnode;
        _add(((JsonNode) (obj)));
    }

    public void add(boolean flag)
    {
        _add(booleanNode(flag));
    }

    public void add(byte abyte0[])
    {
        _add(binaryNode(abyte0));
    }

    public ArrayNode addArray()
    {
        ArrayNode arraynode = arrayNode();
        _add(arraynode);
        return arraynode;
    }

    public void addNull()
    {
        _add(nullNode());
    }

    public ObjectNode addObject()
    {
        ObjectNode objectnode = objectNode();
        _add(objectnode);
        return objectnode;
    }

    public void addPOJO(Object obj)
    {
        _add(POJONode(obj));
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
        if(obj == null)
            flag = false;
        else
        if(obj.getClass() != getClass())
        {
            flag = false;
        } else
        {
            ArrayNode arraynode = (ArrayNode)obj;
            if(_children == null)
            {
                if(arraynode._children == null)
                    flag = true;
                else
                    flag = false;
            } else
            {
                flag = arraynode._sameChildren(_children);
            }
        }
        return flag;
    }

    public JsonNode get(int i)
    {
        JsonNode jsonnode;
        if(i >= 0 && _children != null && i < _children.size())
            jsonnode = (JsonNode)_children.get(i);
        else
            jsonnode = null;
        return jsonnode;
    }

    public JsonNode get(String s)
    {
        return null;
    }

    public Iterator getElements()
    {
        Object obj;
        if(_children == null)
            obj = ContainerNode.NoNodesIterator.instance();
        else
            obj = _children.iterator();
        return ((Iterator) (obj));
    }

    public int hashCode()
    {
        int i;
        if(_children == null)
        {
            i = 1;
        } else
        {
            i = _children.size();
            Iterator iterator = _children.iterator();
            while(iterator.hasNext()) 
            {
                JsonNode jsonnode = (JsonNode)iterator.next();
                if(jsonnode != null)
                    i ^= jsonnode.hashCode();
            }
        }
        return i;
    }

    public void insert(int i, double d)
    {
        _insert(i, numberNode(d));
    }

    public void insert(int i, float f)
    {
        _insert(i, numberNode(f));
    }

    public void insert(int i, int j)
    {
        _insert(i, numberNode(j));
    }

    public void insert(int i, long l)
    {
        _insert(i, numberNode(l));
    }

    public void insert(int i, String s)
    {
        _insert(i, textNode(s));
    }

    public void insert(int i, BigDecimal bigdecimal)
    {
        _insert(i, numberNode(bigdecimal));
    }

    public void insert(int i, JsonNode jsonnode)
    {
        Object obj;
        if(jsonnode == null)
            obj = nullNode();
        else
            obj = jsonnode;
        _insert(i, ((JsonNode) (obj)));
    }

    public void insert(int i, boolean flag)
    {
        _insert(i, booleanNode(flag));
    }

    public void insert(int i, byte abyte0[])
    {
        _insert(i, binaryNode(abyte0));
    }

    public ArrayNode insertArray(int i)
    {
        ArrayNode arraynode = arrayNode();
        _insert(i, arraynode);
        return arraynode;
    }

    public void insertNull(int i)
    {
        _insert(i, nullNode());
    }

    public ObjectNode insertObject(int i)
    {
        ObjectNode objectnode = objectNode();
        _insert(i, objectnode);
        return objectnode;
    }

    public void insertPOJO(int i, Object obj)
    {
        _insert(i, POJONode(obj));
    }

    public boolean isArray()
    {
        return true;
    }

    public JsonNode path(int i)
    {
        Object obj;
        if(i >= 0 && _children != null && i < _children.size())
            obj = (JsonNode)_children.get(i);
        else
            obj = MissingNode.getInstance();
        return ((JsonNode) (obj));
    }

    public JsonNode path(String s)
    {
        return MissingNode.getInstance();
    }

    public JsonNode remove(int i)
    {
        JsonNode jsonnode;
        if(i >= 0 && _children != null && i < _children.size())
            jsonnode = (JsonNode)_children.remove(i);
        else
            jsonnode = null;
        return jsonnode;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        jsongenerator.writeStartArray();
        if(_children != null)
        {
            for(Iterator iterator = _children.iterator(); iterator.hasNext(); ((BaseJsonNode)(JsonNode)iterator.next()).writeTo(jsongenerator));
        }
        jsongenerator.writeEndArray();
    }

    public JsonNode set(int i, JsonNode jsonnode)
    {
        Object obj;
        if(jsonnode == null)
            obj = nullNode();
        else
            obj = jsonnode;
        return _set(i, ((JsonNode) (obj)));
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
        StringBuilder stringbuilder = new StringBuilder(16 + (size() << 4));
        stringbuilder.append('[');
        if(_children != null)
        {
            int i = _children.size();
            for(int j = 0; j < i; j++)
            {
                if(j > 0)
                    stringbuilder.append(',');
                stringbuilder.append(((JsonNode)_children.get(j)).toString());
            }

        }
        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    ArrayList _children;
}
