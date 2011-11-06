// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.node;

import java.io.IOException;
import java.util.Arrays;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.SerializerProvider;

// Referenced classes of package org.codehaus.jackson.node:
//            ValueNode

public final class BinaryNode extends ValueNode
{

    public BinaryNode(byte abyte0[])
    {
        _data = abyte0;
    }

    public BinaryNode(byte abyte0[], int i, int j)
    {
        if(i == 0 && j == abyte0.length)
        {
            _data = abyte0;
        } else
        {
            _data = new byte[j];
            System.arraycopy(abyte0, i, _data, 0, j);
        }
    }

    protected static String _asBase64(boolean flag, byte abyte0[])
    {
        int i = abyte0.length;
        StringBuilder stringbuilder = new StringBuilder(_outputLength(i));
        if(flag)
            stringbuilder.append('"');
        Base64Variant base64variant = Base64Variants.getDefaultVariant();
        int j = base64variant.getMaxLineLength() >> 2;
        int k = i - 3;
        int l = j;
        int i1;
        int i3;
        for(i1 = 0; i1 <= k; i1 = i3)
        {
            int i2 = i1 + 1;
            int j2 = abyte0[i1] << 8;
            int k2 = i2 + 1;
            int l2 = (j2 | 0xff & abyte0[i2]) << 8;
            i3 = k2 + 1;
            base64variant.encodeBase64Chunk(stringbuilder, l2 | 0xff & abyte0[k2]);
            int j3 = l + -1;
            if(j3 <= 0)
            {
                stringbuilder.append('\\');
                stringbuilder.append('n');
                j3 = base64variant.getMaxLineLength() >> 2;
            }
            l = j3;
        }

        int j1 = i - i1;
        if(j1 > 0)
        {
            int k1 = i1 + 1;
            int l1 = abyte0[i1] << 16;
            if(j1 == 2)
            {
                int _tmp = k1 + 1;
                l1 |= (0xff & abyte0[k1]) << 8;
            }
            base64variant.encodeBase64Partial(stringbuilder, l1, j1);
        }
        if(flag)
            stringbuilder.append('"');
        return stringbuilder.toString();
    }

    private static int _outputLength(int i)
    {
        return i + (i >> 2) + (i >> 3);
    }

    public static BinaryNode valueOf(byte abyte0[])
    {
        BinaryNode binarynode;
        if(abyte0 == null)
            binarynode = null;
        else
        if(abyte0.length == 0)
            binarynode = EMPTY_BINARY_NODE;
        else
            binarynode = new BinaryNode(abyte0);
        return binarynode;
    }

    public static BinaryNode valueOf(byte abyte0[], int i, int j)
    {
        BinaryNode binarynode;
        if(abyte0 == null)
            binarynode = null;
        else
        if(j == 0)
            binarynode = EMPTY_BINARY_NODE;
        else
            binarynode = new BinaryNode(abyte0, i, j);
        return binarynode;
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
            flag = false;
        else
            flag = Arrays.equals(((BinaryNode)obj)._data, _data);
        return flag;
    }

    public byte[] getBinaryValue()
    {
        return _data;
    }

    public String getValueAsText()
    {
        return _asBase64(false, _data);
    }

    public int hashCode()
    {
        int i;
        if(_data == null)
            i = -1;
        else
            i = _data.length;
        return i;
    }

    public boolean isBinary()
    {
        return true;
    }

    public final void serialize(JsonGenerator jsongenerator, SerializerProvider serializerprovider)
        throws IOException, JsonProcessingException
    {
        jsongenerator.writeBinary(_data);
    }

    public String toString()
    {
        return _asBase64(true, _data);
    }

    static final BinaryNode EMPTY_BINARY_NODE = new BinaryNode(new byte[0]);
    final byte _data[];

}
