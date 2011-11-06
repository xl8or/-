// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.jackson.JsonNode;

// Referenced classes of package org.codehaus.jackson.node:
//            BaseJsonNode, JsonNodeFactory, POJONode, ArrayNode, 
//            BinaryNode, BooleanNode, NullNode, NumericNode, 
//            ObjectNode, TextNode

public abstract class ContainerNode extends BaseJsonNode
{
    protected static class NoStringsIterator
        implements Iterator
    {

        public static NoStringsIterator instance()
        {
            return instance;
        }

        public boolean hasNext()
        {
            return false;
        }

        public volatile Object next()
        {
            return next();
        }

        public String next()
        {
            throw new NoSuchElementException();
        }

        public void remove()
        {
            throw new IllegalStateException();
        }

        static final NoStringsIterator instance = new NoStringsIterator();


        private NoStringsIterator()
        {
        }
    }

    protected static class NoNodesIterator
        implements Iterator
    {

        public static NoNodesIterator instance()
        {
            return instance;
        }

        public boolean hasNext()
        {
            return false;
        }

        public volatile Object next()
        {
            return next();
        }

        public JsonNode next()
        {
            throw new NoSuchElementException();
        }

        public void remove()
        {
            throw new IllegalStateException();
        }

        static final NoNodesIterator instance = new NoNodesIterator();


        private NoNodesIterator()
        {
        }
    }


    protected ContainerNode(JsonNodeFactory jsonnodefactory)
    {
        _nodeFactory = jsonnodefactory;
    }

    public final POJONode POJONode(Object obj)
    {
        return _nodeFactory.POJONode(obj);
    }

    public final ArrayNode arrayNode()
    {
        return _nodeFactory.arrayNode();
    }

    public final BinaryNode binaryNode(byte abyte0[])
    {
        return _nodeFactory.binaryNode(abyte0);
    }

    public final BinaryNode binaryNode(byte abyte0[], int i, int j)
    {
        return _nodeFactory.binaryNode(abyte0, i, j);
    }

    public final BooleanNode booleanNode(boolean flag)
    {
        return _nodeFactory.booleanNode(flag);
    }

    public abstract JsonNode get(int i);

    public abstract JsonNode get(String s);

    public String getValueAsText()
    {
        return null;
    }

    public boolean isContainerNode()
    {
        return true;
    }

    public final NullNode nullNode()
    {
        return _nodeFactory.nullNode();
    }

    public final NumericNode numberNode(byte byte0)
    {
        return _nodeFactory.numberNode(byte0);
    }

    public final NumericNode numberNode(double d)
    {
        return _nodeFactory.numberNode(d);
    }

    public final NumericNode numberNode(float f)
    {
        return _nodeFactory.numberNode(f);
    }

    public final NumericNode numberNode(int i)
    {
        return _nodeFactory.numberNode(i);
    }

    public final NumericNode numberNode(long l)
    {
        return _nodeFactory.numberNode(l);
    }

    public final NumericNode numberNode(BigDecimal bigdecimal)
    {
        return _nodeFactory.numberNode(bigdecimal);
    }

    public final NumericNode numberNode(short word0)
    {
        return _nodeFactory.numberNode(word0);
    }

    public final ObjectNode objectNode()
    {
        return _nodeFactory.objectNode();
    }

    public abstract int size();

    public final TextNode textNode(String s)
    {
        return _nodeFactory.textNode(s);
    }

    JsonNodeFactory _nodeFactory;
}
