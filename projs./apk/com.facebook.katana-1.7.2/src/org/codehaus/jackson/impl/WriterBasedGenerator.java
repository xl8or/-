// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.*;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.io.NumberOutput;
import org.codehaus.jackson.util.CharTypes;

// Referenced classes of package org.codehaus.jackson.impl:
//            JsonGeneratorBase, JsonWriteContext

public final class WriterBasedGenerator extends JsonGeneratorBase
{

    public WriterBasedGenerator(IOContext iocontext, int i, ObjectCodec objectcodec, Writer writer)
    {
        super(i, objectcodec);
        _outputHead = 0;
        _outputTail = 0;
        _ioContext = iocontext;
        _writer = writer;
        _outputBuffer = iocontext.allocConcatBuffer();
        _outputEnd = _outputBuffer.length;
    }

    private void _appendSingleEscape(int i, char ac[], int j)
    {
        if(i < 0)
        {
            int k = -(i + 1);
            ac[j] = '\\';
            int l = j + 1;
            ac[l] = 'u';
            int i1 = l + 1;
            ac[i1] = '0';
            int j1 = i1 + 1;
            ac[j1] = '0';
            int k1 = j1 + 1;
            ac[k1] = HEX_CHARS[k >> 4];
            ac[k1 + 1] = HEX_CHARS[k & 0xf];
        } else
        {
            ac[j] = '\\';
            ac[j + 1] = (char)i;
        }
    }

    private void _writeLongString(String s)
        throws IOException, JsonGenerationException
    {
        _flushBuffer();
        int i = s.length();
        int j = 0;
        do
        {
            int k = _outputEnd;
            if(j + k > i)
                k = i - j;
            s.getChars(j, j + k, _outputBuffer, 0);
            _writeSegment(k);
            j += k;
        } while(j < i);
    }

    private final void _writeSegment(int i)
        throws IOException, JsonGenerationException
    {
        int ai[];
        int j;
        int k;
        ai = CharTypes.getOutputEscapes();
        j = ai.length;
        k = 0;
_L8:
        if(k >= i) goto _L2; else goto _L1
_L1:
        int l = k;
_L6:
        char c;
        c = _outputBuffer[l];
        continue; /* Loop/switch isn't completed */
_L5:
        int i1 = l - k;
        if(i1 <= 0) goto _L4; else goto _L3
_L3:
        _writer.write(_outputBuffer, k, i1);
        if(l < i) goto _L4; else goto _L2
_L2:
        return;
        if((c >= j || ai[c] == 0) && ++l < i) goto _L6; else goto _L5
_L4:
        int j1 = ai[_outputBuffer[l]];
        int k1 = l + 1;
        byte byte0;
        if(j1 < 0)
            byte0 = 6;
        else
            byte0 = 2;
        if(byte0 > _outputTail)
        {
            _writeSingleEscape(j1);
            k = k1;
        } else
        {
            int l1 = k1 - byte0;
            _appendSingleEscape(j1, _outputBuffer, l1);
            k = l1;
        }
        if(true) goto _L8; else goto _L7
_L7:
    }

    private void _writeSingleEscape(int i)
        throws IOException
    {
        char ac[] = _entityBuffer;
        if(ac == null)
        {
            ac = new char[6];
            ac[0] = '\\';
            ac[2] = '0';
            ac[3] = '0';
        }
        if(i < 0)
        {
            int j = -(i + 1);
            ac[1] = 'u';
            ac[4] = HEX_CHARS[j >> 4];
            ac[5] = HEX_CHARS[j & 0xf];
            _writer.write(ac, 0, 6);
        } else
        {
            ac[1] = (char)i;
            _writer.write(ac, 0, 2);
        }
    }

    private void _writeString(String s)
        throws IOException, JsonGenerationException
    {
        int i = s.length();
        if(i <= _outputEnd) goto _L2; else goto _L1
_L1:
        _writeLongString(s);
_L4:
        return;
_L2:
        int j;
        int ai[];
        int k;
        if(i + _outputTail > _outputEnd)
            _flushBuffer();
        s.getChars(0, i, _outputBuffer, _outputTail);
        j = i + _outputTail;
        ai = CharTypes.getOutputEscapes();
        k = ai.length;
_L5:
        if(_outputTail >= j) goto _L4; else goto _L3
_L3:
label0:
        {
            char c = _outputBuffer[_outputTail];
            if(c >= k || ai[c] == 0)
                break label0;
            int i1 = _outputTail - _outputHead;
            if(i1 > 0)
                _writer.write(_outputBuffer, _outputHead, i1);
            int j1 = ai[_outputBuffer[_outputTail]];
            _outputTail = 1 + _outputTail;
            int l;
            byte byte0;
            if(j1 < 0)
                byte0 = 6;
            else
                byte0 = 2;
            if(byte0 > _outputTail)
            {
                _outputHead = _outputTail;
                _writeSingleEscape(j1);
            } else
            {
                int k1 = _outputTail - byte0;
                _outputHead = k1;
                _appendSingleEscape(j1, _outputBuffer, k1);
            }
        }
          goto _L5
        l = 1 + _outputTail;
        _outputTail = l;
        if(l < j) goto _L3; else goto _L4
    }

    private void _writeString(char ac[], int i, int j)
        throws IOException, JsonGenerationException
    {
        int k;
        int ai[];
        int l;
        int i1;
        k = j + i;
        ai = CharTypes.getOutputEscapes();
        l = ai.length;
        i1 = i;
_L7:
        if(i1 >= k) goto _L2; else goto _L1
_L1:
        int j1 = i1;
_L5:
        char c;
        c = ac[j1];
        continue; /* Loop/switch isn't completed */
_L4:
        int k1 = j1 - i1;
        if(k1 < 32)
        {
            if(k1 + _outputTail > _outputEnd)
                _flushBuffer();
            if(k1 > 0)
            {
                System.arraycopy(ac, i1, _outputBuffer, _outputTail, k1);
                _outputTail = k1 + _outputTail;
            }
        } else
        {
            _flushBuffer();
            _writer.write(ac, i1, k1);
        }
        if(j1 < k) goto _L3; else goto _L2
_L2:
        return;
        if((c >= l || ai[c] == 0) && ++j1 < k) goto _L5; else goto _L4
_L3:
        int l1 = ai[ac[j1]];
        int i2 = j1 + 1;
        byte byte0;
        if(l1 < 0)
            byte0 = 6;
        else
            byte0 = 2;
        if(byte0 + _outputTail > _outputEnd)
            _flushBuffer();
        _appendSingleEscape(l1, _outputBuffer, _outputTail);
        _outputTail = byte0 + _outputTail;
        i1 = i2;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void writeRawLong(String s)
        throws IOException, JsonGenerationException
    {
        int i = _outputEnd - _outputTail;
        s.getChars(0, i, _outputBuffer, _outputTail);
        _outputTail = i + _outputTail;
        _flushBuffer();
        int j = s.length() - i;
        int k = i;
        int l;
        int i1;
        for(l = j; l > _outputEnd; l -= i1)
        {
            i1 = _outputEnd;
            s.getChars(k, k + i1, _outputBuffer, 0);
            _outputHead = 0;
            _outputTail = i1;
            _flushBuffer();
            k += i1;
        }

        s.getChars(k, k + l, _outputBuffer, 0);
        _outputHead = 0;
        _outputTail = l;
    }

    protected final void _flushBuffer()
        throws IOException
    {
        int i = _outputTail - _outputHead;
        if(i > 0)
        {
            int j = _outputHead;
            _outputHead = 0;
            _outputTail = 0;
            _writer.write(_outputBuffer, j, i);
        }
    }

    protected void _releaseBuffers()
    {
        char ac[] = _outputBuffer;
        if(ac != null)
        {
            _outputBuffer = null;
            _ioContext.releaseConcatBuffer(ac);
        }
    }

    protected final void _verifyPrettyValueWrite(String s, int i)
        throws IOException, JsonGenerationException
    {
        i;
        JVM INSTR tableswitch 0 3: default 32
    //                   0 76
    //                   1 37
    //                   2 50
    //                   3 63;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        _cantHappen();
_L7:
        return;
_L3:
        _cfgPrettyPrinter.writeArrayValueSeparator(this);
        continue; /* Loop/switch isn't completed */
_L4:
        _cfgPrettyPrinter.writeObjectFieldValueSeparator(this);
        continue; /* Loop/switch isn't completed */
_L5:
        _cfgPrettyPrinter.writeRootValueSeparator(this);
        continue; /* Loop/switch isn't completed */
_L2:
        if(_writeContext.inArray())
            _cfgPrettyPrinter.beforeArrayValues(this);
        else
        if(_writeContext.inObject())
            _cfgPrettyPrinter.beforeObjectEntries(this);
        if(true) goto _L7; else goto _L6
_L6:
    }

    protected final void _verifyValueWrite(String s)
        throws IOException, JsonGenerationException
    {
        int i;
        i = _writeContext.writeValue();
        if(i == 5)
            _reportError((new StringBuilder()).append("Can not ").append(s).append(", expecting field name").toString());
        if(_cfgPrettyPrinter != null) goto _L2; else goto _L1
_L1:
        i;
        JVM INSTR tableswitch 1 3: default 76
    //                   1 77
    //                   2 118
    //                   3 124;
           goto _L3 _L4 _L5 _L6
_L3:
        return;
_L4:
        char c = ',';
_L7:
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        _outputBuffer[_outputTail] = c;
        _outputTail = 1 + _outputTail;
        continue; /* Loop/switch isn't completed */
_L5:
        c = ':';
        continue; /* Loop/switch isn't completed */
_L6:
        c = ' ';
        if(true) goto _L7; else goto _L2
_L2:
        _verifyPrettyValueWrite(s, i);
        if(true) goto _L3; else goto _L8
_L8:
    }

    protected void _writeBinary(Base64Variant base64variant, byte abyte0[], int i, int j)
        throws IOException, JsonGenerationException
    {
        int k = j - 3;
        int l = _outputEnd - 6;
        int i1 = base64variant.getMaxLineLength() >> 2;
        int j1;
        int k3;
        for(j1 = i; j1 <= k; j1 = k3)
        {
            if(_outputTail > l)
                _flushBuffer();
            int k2 = j1 + 1;
            int l2 = abyte0[j1] << 8;
            int i3 = k2 + 1;
            int j3 = (l2 | 0xff & abyte0[k2]) << 8;
            k3 = i3 + 1;
            _outputTail = base64variant.encodeBase64Chunk(j3 | 0xff & abyte0[i3], _outputBuffer, _outputTail);
            if(--i1 <= 0)
            {
                char ac[] = _outputBuffer;
                int l3 = _outputTail;
                _outputTail = l3 + 1;
                ac[l3] = '\\';
                char ac1[] = _outputBuffer;
                int i4 = _outputTail;
                _outputTail = i4 + 1;
                ac1[i4] = 'n';
                i1 = base64variant.getMaxLineLength() >> 2;
            }
        }

        int k1 = j - j1;
        if(k1 > 0)
        {
            if(_outputTail > l)
                _flushBuffer();
            int l1 = j1 + 1;
            int i2 = abyte0[j1] << 16;
            int j2;
            if(k1 == 2)
            {
                int _tmp = l1 + 1;
                j2 = i2 | (0xff & abyte0[l1]) << 8;
            } else
            {
                j2 = i2;
            }
            _outputTail = base64variant.encodeBase64Partial(j2, k1, _outputBuffer, _outputTail);
        }
    }

    protected void _writeEndArray()
        throws IOException, JsonGenerationException
    {
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int i = _outputTail;
        _outputTail = i + 1;
        ac[i] = ']';
    }

    protected void _writeEndObject()
        throws IOException, JsonGenerationException
    {
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int i = _outputTail;
        _outputTail = i + 1;
        ac[i] = '}';
    }

    protected void _writeFieldName(String s, boolean flag)
        throws IOException, JsonGenerationException
    {
        if(_cfgPrettyPrinter != null)
        {
            _writePPFieldName(s, flag);
        } else
        {
            if(1 + _outputTail >= _outputEnd)
                _flushBuffer();
            if(flag)
            {
                char ac2[] = _outputBuffer;
                int k = _outputTail;
                _outputTail = k + 1;
                ac2[k] = ',';
            }
            if(!isFeatureEnabled(org.codehaus.jackson.JsonGenerator.Feature.QUOTE_FIELD_NAMES))
            {
                _writeString(s);
            } else
            {
                char ac[] = _outputBuffer;
                int i = _outputTail;
                _outputTail = i + 1;
                ac[i] = '"';
                _writeString(s);
                if(_outputTail >= _outputEnd)
                    _flushBuffer();
                char ac1[] = _outputBuffer;
                int j = _outputTail;
                _outputTail = j + 1;
                ac1[j] = '"';
            }
        }
    }

    protected final void _writePPFieldName(String s, boolean flag)
        throws IOException, JsonGenerationException
    {
        if(flag)
            _cfgPrettyPrinter.writeObjectEntrySeparator(this);
        else
            _cfgPrettyPrinter.beforeObjectEntries(this);
        if(isFeatureEnabled(org.codehaus.jackson.JsonGenerator.Feature.QUOTE_FIELD_NAMES))
        {
            if(_outputTail >= _outputEnd)
                _flushBuffer();
            char ac[] = _outputBuffer;
            int i = _outputTail;
            _outputTail = i + 1;
            ac[i] = '"';
            _writeString(s);
            if(_outputTail >= _outputEnd)
                _flushBuffer();
            char ac1[] = _outputBuffer;
            int j = _outputTail;
            _outputTail = j + 1;
            ac1[j] = '"';
        } else
        {
            _writeString(s);
        }
    }

    protected void _writeStartArray()
        throws IOException, JsonGenerationException
    {
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int i = _outputTail;
        _outputTail = i + 1;
        ac[i] = '[';
    }

    protected void _writeStartObject()
        throws IOException, JsonGenerationException
    {
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int i = _outputTail;
        _outputTail = i + 1;
        ac[i] = '{';
    }

    public void close()
        throws IOException
    {
        super.close();
        if(_outputBuffer != null && isFeatureEnabled(org.codehaus.jackson.JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT))
            do
            {
                JsonWriteContext jsonwritecontext = getOutputContext();
                if(jsonwritecontext.inArray())
                {
                    writeEndArray();
                    continue;
                }
                if(!jsonwritecontext.inObject())
                    break;
                writeEndObject();
            } while(true);
        _flushBuffer();
        if(_ioContext.isResourceManaged() || isFeatureEnabled(org.codehaus.jackson.JsonGenerator.Feature.AUTO_CLOSE_TARGET))
            _writer.close();
        else
            _writer.flush();
        _releaseBuffers();
    }

    public final void flush()
        throws IOException
    {
        _flushBuffer();
        _writer.flush();
    }

    public void writeBinary(Base64Variant base64variant, byte abyte0[], int i, int j)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write binary value");
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int k = _outputTail;
        _outputTail = k + 1;
        ac[k] = '"';
        _writeBinary(base64variant, abyte0, i, i + j);
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac1[] = _outputBuffer;
        int l = _outputTail;
        _outputTail = l + 1;
        ac1[l] = '"';
    }

    public void writeBoolean(boolean flag)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write boolean value");
        if(5 + _outputTail >= _outputEnd)
            _flushBuffer();
        int i = _outputTail;
        char ac[] = _outputBuffer;
        int i1;
        if(flag)
        {
            ac[i] = 't';
            int j1 = i + 1;
            ac[j1] = 'r';
            int k1 = j1 + 1;
            ac[k1] = 'u';
            i1 = k1 + 1;
            ac[i1] = 'e';
        } else
        {
            ac[i] = 'f';
            int j = i + 1;
            ac[j] = 'a';
            int k = j + 1;
            ac[k] = 'l';
            int l = k + 1;
            ac[l] = 's';
            i1 = l + 1;
            ac[i1] = 'e';
        }
        _outputTail = i1 + 1;
    }

    public void writeNull()
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write null value");
        if(4 + _outputTail >= _outputEnd)
            _flushBuffer();
        int i = _outputTail;
        char ac[] = _outputBuffer;
        ac[i] = 'n';
        int j = i + 1;
        ac[j] = 'u';
        int k = j + 1;
        ac[k] = 'l';
        int l = k + 1;
        ac[l] = 'l';
        _outputTail = l + 1;
    }

    public void writeNumber(double d)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        writeRaw(String.valueOf(d));
    }

    public void writeNumber(float f)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        writeRaw(String.valueOf(f));
    }

    public void writeNumber(int i)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        if(11 + _outputTail >= _outputEnd)
            _flushBuffer();
        _outputTail = NumberOutput.outputInt(i, _outputBuffer, _outputTail);
    }

    public void writeNumber(long l)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        if(21 + _outputTail >= _outputEnd)
            _flushBuffer();
        _outputTail = NumberOutput.outputLong(l, _outputBuffer, _outputTail);
    }

    public void writeNumber(String s)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        writeRaw(s);
    }

    public void writeNumber(BigDecimal bigdecimal)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        writeRaw(bigdecimal.toString());
    }

    public void writeNumber(BigInteger biginteger)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write number");
        writeRaw(biginteger.toString());
    }

    public final void writeObject(Object obj)
        throws IOException, JsonProcessingException
    {
        if(_objectCodec == null)
        {
            throw new IllegalStateException("No ObjectCodec defined for the generator, can not serialize regular Java objects");
        } else
        {
            _objectCodec.writeValue(this, obj);
            return;
        }
    }

    public void writeRaw(char c)
        throws IOException, JsonGenerationException
    {
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int i = _outputTail;
        _outputTail = i + 1;
        ac[i] = c;
    }

    public void writeRaw(String s)
        throws IOException, JsonGenerationException
    {
        int i = s.length();
        int j = _outputEnd - _outputTail;
        if(j == 0)
        {
            _flushBuffer();
            j = _outputEnd - _outputTail;
        }
        if(j >= i)
        {
            s.getChars(0, i, _outputBuffer, _outputTail);
            _outputTail = i + _outputTail;
        } else
        {
            writeRawLong(s);
        }
    }

    public void writeRaw(String s, int i, int j)
        throws IOException, JsonGenerationException
    {
        int k = _outputEnd - _outputTail;
        if(k < j)
        {
            _flushBuffer();
            k = _outputEnd - _outputTail;
        }
        if(k >= j)
        {
            s.getChars(i, i + j, _outputBuffer, _outputTail);
            _outputTail = j + _outputTail;
        } else
        {
            writeRawLong(s.substring(i, i + j));
        }
    }

    public void writeRaw(char ac[], int i, int j)
        throws IOException, JsonGenerationException
    {
        if(j < 32)
        {
            if(j > _outputEnd - _outputTail)
                _flushBuffer();
            System.arraycopy(ac, i, _outputBuffer, _outputTail, j);
            _outputTail = j + _outputTail;
        } else
        {
            _flushBuffer();
            _writer.write(ac, i, j);
        }
    }

    public void writeRawValue(String s)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write raw value");
        writeRaw(s);
    }

    public void writeRawValue(String s, int i, int j)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write raw value");
        writeRaw(s, i, j);
    }

    public void writeRawValue(char ac[], int i, int j)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write raw value");
        writeRaw(ac, i, j);
    }

    public void writeString(String s)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write text value");
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac[] = _outputBuffer;
        int i = _outputTail;
        _outputTail = i + 1;
        ac[i] = '"';
        _writeString(s);
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac1[] = _outputBuffer;
        int j = _outputTail;
        _outputTail = j + 1;
        ac1[j] = '"';
    }

    public void writeString(char ac[], int i, int j)
        throws IOException, JsonGenerationException
    {
        _verifyValueWrite("write text value");
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac1[] = _outputBuffer;
        int k = _outputTail;
        _outputTail = k + 1;
        ac1[k] = '"';
        _writeString(ac, i, j);
        if(_outputTail >= _outputEnd)
            _flushBuffer();
        char ac2[] = _outputBuffer;
        int l = _outputTail;
        _outputTail = l + 1;
        ac2[l] = '"';
    }

    public final void writeTree(JsonNode jsonnode)
        throws IOException, JsonProcessingException
    {
        if(_objectCodec == null)
        {
            throw new IllegalStateException("No ObjectCodec defined for the generator, can not serialize JsonNode-based trees");
        } else
        {
            _objectCodec.writeTree(this, jsonnode);
            return;
        }
    }

    static final char HEX_CHARS[] = "0123456789ABCDEF".toCharArray();
    static final int SHORT_WRITE = 32;
    protected char _entityBuffer[];
    protected final IOContext _ioContext;
    protected char _outputBuffer[];
    protected int _outputEnd;
    protected int _outputHead;
    protected int _outputTail;
    protected final Writer _writer;

}
