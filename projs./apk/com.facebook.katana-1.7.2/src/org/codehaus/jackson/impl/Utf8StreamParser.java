// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.*;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import org.codehaus.jackson.sym.Name;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.util.*;

// Referenced classes of package org.codehaus.jackson.impl:
//            Utf8NumericParser, JsonReadContext

public final class Utf8StreamParser extends Utf8NumericParser
{

    public Utf8StreamParser(IOContext iocontext, int i, InputStream inputstream, ObjectCodec objectcodec, BytesToNameCanonicalizer bytestonamecanonicalizer, byte abyte0[], int j, 
            int k, boolean flag)
    {
        super(iocontext, i, inputstream, abyte0, j, k, flag);
        _quadBuffer = new int[32];
        _objectCodec = objectcodec;
        _symbols = bytestonamecanonicalizer;
    }

    private final int _decodeUtf8_2(int i)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte0[] = _inputBuffer;
        int j = _inputPtr;
        _inputPtr = j + 1;
        byte byte0 = abyte0[j];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        return (i & 0x1f) << 6 | byte0 & 0x3f;
    }

    private final int _decodeUtf8_3(int i)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        int j = i & 0xf;
        byte abyte0[] = _inputBuffer;
        int k = _inputPtr;
        _inputPtr = k + 1;
        byte byte0 = abyte0[k];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        int l = j << 6 | byte0 & 0x3f;
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte1[] = _inputBuffer;
        int i1 = _inputPtr;
        _inputPtr = i1 + 1;
        byte byte1 = abyte1[i1];
        if((byte1 & 0xc0) != 128)
            _reportInvalidOther(byte1 & 0xff, _inputPtr);
        return l << 6 | byte1 & 0x3f;
    }

    private final int _decodeUtf8_3fast(int i)
        throws IOException, JsonParseException
    {
        int j = i & 0xf;
        byte abyte0[] = _inputBuffer;
        int k = _inputPtr;
        _inputPtr = k + 1;
        byte byte0 = abyte0[k];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        int l = j << 6 | byte0 & 0x3f;
        byte abyte1[] = _inputBuffer;
        int i1 = _inputPtr;
        _inputPtr = i1 + 1;
        byte byte1 = abyte1[i1];
        if((byte1 & 0xc0) != 128)
            _reportInvalidOther(byte1 & 0xff, _inputPtr);
        return l << 6 | byte1 & 0x3f;
    }

    private final int _decodeUtf8_4(int i)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte0[] = _inputBuffer;
        int j = _inputPtr;
        _inputPtr = j + 1;
        byte byte0 = abyte0[j];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        int k = (i & 7) << 6 | byte0 & 0x3f;
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte1[] = _inputBuffer;
        int l = _inputPtr;
        _inputPtr = l + 1;
        byte byte1 = abyte1[l];
        if((byte1 & 0xc0) != 128)
            _reportInvalidOther(byte1 & 0xff, _inputPtr);
        int i1 = k << 6 | byte1 & 0x3f;
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte2[] = _inputBuffer;
        int j1 = _inputPtr;
        _inputPtr = j1 + 1;
        byte byte2 = abyte2[j1];
        if((byte2 & 0xc0) != 128)
            _reportInvalidOther(byte2 & 0xff, _inputPtr);
        return (i1 << 6 | byte2 & 0x3f) - 0x10000;
    }

    private final JsonToken _nextAfterName()
    {
        JsonToken jsontoken;
        _nameCopied = false;
        jsontoken = _nextToken;
        _nextToken = null;
        if(jsontoken != JsonToken.START_ARRAY) goto _L2; else goto _L1
_L1:
        _parsingContext = _parsingContext.createChildArrayContext(_tokenInputRow, _tokenInputCol);
_L4:
        _currToken = jsontoken;
        return jsontoken;
_L2:
        if(jsontoken == JsonToken.START_OBJECT)
            _parsingContext = _parsingContext.createChildObjectContext(_tokenInputRow, _tokenInputCol);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void _reportInvalidToken(String s)
        throws IOException, JsonParseException
    {
        StringBuilder stringbuilder = new StringBuilder(s);
_L5:
        if(_inputPtr < _inputEnd || loadMore()) goto _L2; else goto _L1
_L1:
        _reportError((new StringBuilder()).append("Unrecognized token '").append(stringbuilder.toString()).append("': was expecting 'null', 'true' or 'false'").toString());
        return;
_L2:
        char c;
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        c = (char)_decodeCharForError(abyte0[i]);
        if(!Character.isJavaIdentifierPart(c)) goto _L1; else goto _L3
_L3:
        _inputPtr = 1 + _inputPtr;
        stringbuilder.append(c);
        if(true) goto _L5; else goto _L4
_L4:
    }

    private final void _skipCComment()
        throws IOException, JsonParseException
    {
        int ai[] = CharTypes.getInputCodeComment();
_L4:
        if(_inputPtr >= _inputEnd && !loadMore()) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        j = 0xff & abyte0[i];
        k = ai[j];
        if(k == 0) goto _L4; else goto _L3
_L3:
        k;
        JVM INSTR lookupswitch 3: default 96
    //                   10: 130
    //                   13: 137
    //                   42: 105;
           goto _L5 _L6 _L7 _L8
_L5:
        _reportInvalidChar(j);
          goto _L4
_L8:
        if(_inputBuffer[_inputPtr] != 47) goto _L4; else goto _L9
_L9:
        _inputPtr = 1 + _inputPtr;
_L10:
        return;
_L6:
        _skipLF();
          goto _L4
_L7:
        _skipCR();
          goto _L4
_L2:
        _reportInvalidEOF(" in a comment");
          goto _L10
    }

    private final void _skipComment()
        throws IOException, JsonParseException
    {
        if(!isFeatureEnabled(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS))
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(" in a comment");
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        int j = 0xff & abyte0[i];
        if(j == 47)
            _skipCppComment();
        else
        if(j == 42)
            _skipCComment();
        else
            _reportUnexpectedChar(j, "was expecting either '*' or '/' for a comment");
    }

    private final void _skipCppComment()
        throws IOException, JsonParseException
    {
        int ai[] = CharTypes.getInputCodeComment();
_L4:
        if(_inputPtr >= _inputEnd && !loadMore()) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        j = 0xff & abyte0[i];
        k = ai[j];
        if(k == 0) goto _L4; else goto _L3
_L3:
        k;
        JVM INSTR lookupswitch 3: default 96
    //                   10: 105
    //                   13: 110
    //                   42: 4;
           goto _L5 _L6 _L7 _L4
_L5:
        _reportInvalidChar(j);
          goto _L4
_L6:
        _skipLF();
_L2:
        return;
_L7:
        _skipCR();
        if(true) goto _L2; else goto _L8
_L8:
    }

    private final void _skipUtf8_2(int i)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte0[] = _inputBuffer;
        int j = _inputPtr;
        _inputPtr = j + 1;
        byte byte0 = abyte0[j];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
    }

    private final void _skipUtf8_3(int i)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte0[] = _inputBuffer;
        int j = _inputPtr;
        _inputPtr = j + 1;
        byte byte0 = abyte0[j];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte1[] = _inputBuffer;
        int k = _inputPtr;
        _inputPtr = k + 1;
        byte byte1 = abyte1[k];
        if((byte1 & 0xc0) != 128)
            _reportInvalidOther(byte1 & 0xff, _inputPtr);
    }

    private final void _skipUtf8_4(int i)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte0[] = _inputBuffer;
        int j = _inputPtr;
        _inputPtr = j + 1;
        byte byte0 = abyte0[j];
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        if((byte0 & 0xc0) != 128)
            _reportInvalidOther(byte0 & 0xff, _inputPtr);
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte1[] = _inputBuffer;
        int k = _inputPtr;
        _inputPtr = k + 1;
        byte byte1 = abyte1[k];
        if((byte1 & 0xc0) != 128)
            _reportInvalidOther(byte1 & 0xff, _inputPtr);
    }

    private final int _skipWS()
        throws IOException, JsonParseException
    {
        do
        {
            if(_inputPtr >= _inputEnd && !loadMore())
                break;
            byte abyte0[] = _inputBuffer;
            int i = _inputPtr;
            _inputPtr = i + 1;
            int j = 0xff & abyte0[i];
            if(j > 32)
            {
                if(j != 47)
                    return j;
                _skipComment();
            } else
            if(j != 32)
                if(j == 10)
                    _skipLF();
                else
                if(j == 13)
                    _skipCR();
                else
                if(j != 9)
                    _throwInvalidSpace(j);
        } while(true);
        throw _constructError((new StringBuilder()).append("Unexpected end-of-input within/between ").append(_parsingContext.getTypeDesc()).append(" entries").toString());
    }

    private final int _skipWSOrEnd()
        throws IOException, JsonParseException
    {
_L5:
        int j;
        if(_inputPtr >= _inputEnd && !loadMore())
            break MISSING_BLOCK_LABEL_110;
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        j = 0xff & abyte0[i];
        if(j <= 32) goto _L2; else goto _L1
_L1:
        if(j == 47) goto _L4; else goto _L3
_L3:
        return j;
_L4:
        _skipComment();
          goto _L5
_L2:
        if(j != 32)
            if(j == 10)
                _skipLF();
            else
            if(j == 13)
                _skipCR();
            else
            if(j != 9)
                _throwInvalidSpace(j);
          goto _L5
        _handleEOF();
        j = -1;
          goto _L3
    }

    private final Name addName(int ai[], int i, int j)
        throws JsonParseException
    {
        int l;
        int i1;
        char ac1[];
        int k1;
        int l1;
        char ac2[];
        int i2;
        int j2;
        int k2;
        int l3;
        int i4;
        int k4;
        int k = j + ((i << 2) - 4);
        char ac[];
        int j1;
        int l2;
        int k3;
        int l4;
        int i5;
        int j5;
        int k5;
        int l5;
        int i6;
        if(j < 4)
        {
            l = ai[i - 1];
            ai[i - 1] = l << (4 - j << 3);
        } else
        {
            l = 0;
        }
        ac = _textBuffer.emptyAndGetCurrentSegment();
        i1 = 0;
        ac1 = ac;
        j1 = 0;
        if(j1 >= k) goto _L2; else goto _L1
_L1:
        k1 = 0xff & ai[j1 >> 2] >> (3 - (j1 & 3) << 3);
        l1 = j1 + 1;
        if(k1 <= 127)
            break MISSING_BLOCK_LABEL_574;
        int i3;
        int j3;
        if((k1 & 0xe0) == 192)
        {
            j3 = k1 & 0x1f;
            i3 = 1;
        } else
        if((k1 & 0xf0) == 224)
        {
            j3 = k1 & 0xf;
            i3 = 2;
        } else
        if((k1 & 0xf8) == 240)
        {
            j3 = k1 & 7;
            i3 = 3;
        } else
        {
            _reportInvalidInitial(k1);
            i3 = 1;
            j3 = i3;
        }
        if(l1 + i3 > k)
            _reportInvalidEOF(" in field name");
        k3 = ai[l1 >> 2] >> (3 - (l1 & 3) << 3);
        l3 = l1 + 1;
        if((k3 & 0xc0) != 128)
            _reportInvalidOther(k3);
        i4 = j3 << 6 | k3 & 0x3f;
        if(i3 <= 1) goto _L4; else goto _L3
_L3:
        j5 = ai[l3 >> 2] >> (3 - (l3 & 3) << 3);
        l3++;
        if((j5 & 0xc0) != 128)
            _reportInvalidOther(j5);
        i4 = i4 << 6 | j5 & 0x3f;
        if(i3 <= 2) goto _L4; else goto _L5
_L5:
        k5 = ai[l3 >> 2] >> (3 - (l3 & 3) << 3);
        l5 = l3 + 1;
        if((k5 & 0xc0) != 128)
            _reportInvalidOther(k5 & 0xff);
        i6 = i4 << 6 | k5 & 0x3f;
        k4 = l5;
        k2 = i6;
_L6:
        if(i3 > 2)
        {
            l4 = k2 - 0x10000;
            if(i1 >= ac1.length)
                ac1 = _textBuffer.expandCurrentSegment();
            i5 = i1 + 1;
            ac1[i1] = (char)(55296 + (l4 >> 10));
            k2 = 0xdc00 | l4 & 0x3ff;
            j2 = k4;
            ac2 = ac1;
            i2 = i5;
        } else
        {
            ac2 = ac1;
            i2 = i1;
            j2 = k4;
        }
_L7:
        if(i2 >= ac2.length)
            ac2 = _textBuffer.expandCurrentSegment();
        l2 = i2 + 1;
        ac2[i2] = (char)k2;
        j1 = j2;
        ac1 = ac2;
        i1 = l2;
        break MISSING_BLOCK_LABEL_53;
_L2:
        String s = new String(ac1, 0, i1);
        if(j < 4)
            ai[i - 1] = l;
        return _symbols.addName(s, ai, i);
_L4:
        int j4 = i4;
        k4 = l3;
        k2 = j4;
          goto _L6
        ac2 = ac1;
        i2 = i1;
        j2 = l1;
        k2 = k1;
          goto _L7
    }

    private final Name findName(int i, int j)
        throws JsonParseException
    {
        Name name = _symbols.findName(i);
        if(name == null)
        {
            _quadBuffer[0] = i;
            name = addName(_quadBuffer, 1, j);
        }
        return name;
    }

    private final Name findName(int i, int j, int k)
        throws JsonParseException
    {
        Name name = _symbols.findName(i, j);
        if(name == null)
        {
            _quadBuffer[0] = i;
            _quadBuffer[1] = j;
            name = addName(_quadBuffer, 2, k);
        }
        return name;
    }

    private final Name findName(int ai[], int i, int j, int k)
        throws JsonParseException
    {
        int ai1[];
        int l;
        Name name;
        Name name1;
        if(i >= ai.length)
        {
            ai1 = growArrayBy(ai, ai.length);
            _quadBuffer = ai1;
        } else
        {
            ai1 = ai;
        }
        l = i + 1;
        ai1[i] = j;
        name = _symbols.findName(ai1, l);
        if(name == null)
            name1 = addName(ai1, l, k);
        else
            name1 = name;
        return name1;
    }

    public static int[] growArrayBy(int ai[], int i)
    {
        int ai2[];
        if(ai == null)
        {
            ai2 = new int[i];
        } else
        {
            int j = ai.length;
            int ai1[] = new int[j + i];
            System.arraycopy(ai, 0, ai1, 0, j);
            ai2 = ai1;
        }
        return ai2;
    }

    private int nextByte()
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        return 0xff & abyte0[i];
    }

    private final Name parseFieldName(int i, int j, int k)
        throws IOException, JsonParseException
    {
        return parseEscapedFieldName(_quadBuffer, 0, i, j, k);
    }

    private final Name parseFieldName(int i, int j, int k, int l)
        throws IOException, JsonParseException
    {
        _quadBuffer[0] = i;
        return parseEscapedFieldName(_quadBuffer, 1, j, k, l);
    }

    protected byte[] _decodeBase64(Base64Variant base64variant)
        throws IOException, JsonParseException
    {
        ByteArrayBuilder bytearraybuilder = _getByteArrayBuilder();
        do
        {
            int j;
            do
            {
                if(_inputPtr >= _inputEnd)
                    loadMoreGuaranteed();
                byte abyte0[] = _inputBuffer;
                int i = _inputPtr;
                _inputPtr = i + 1;
                j = 0xff & abyte0[i];
            } while(j <= 32);
            int k = base64variant.decodeBase64Char(j);
            if(k < 0)
                if(j == 34)
                    return bytearraybuilder.toByteArray();
                else
                    throw reportInvalidChar(base64variant, j, 0);
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            byte abyte1[] = _inputBuffer;
            int l = _inputPtr;
            _inputPtr = l + 1;
            int i1 = 0xff & abyte1[l];
            int j1 = base64variant.decodeBase64Char(i1);
            if(j1 < 0)
                throw reportInvalidChar(base64variant, i1, 1);
            int k1 = j1 | k << 6;
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            byte abyte2[] = _inputBuffer;
            int l1 = _inputPtr;
            _inputPtr = l1 + 1;
            int i2 = 0xff & abyte2[l1];
            int j2 = base64variant.decodeBase64Char(i2);
            if(j2 < 0)
            {
                if(j2 != -2)
                    throw reportInvalidChar(base64variant, i2, 2);
                if(_inputPtr >= _inputEnd)
                    loadMoreGuaranteed();
                byte abyte4[] = _inputBuffer;
                int k3 = _inputPtr;
                _inputPtr = k3 + 1;
                int l3 = 0xff & abyte4[k3];
                if(!base64variant.usesPaddingChar(l3))
                    throw reportInvalidChar(base64variant, l3, 3, (new StringBuilder()).append("expected padding character '").append(base64variant.getPaddingChar()).append("'").toString());
                bytearraybuilder.append(k1 >> 4);
            } else
            {
                int k2 = j2 | k1 << 6;
                if(_inputPtr >= _inputEnd)
                    loadMoreGuaranteed();
                byte abyte3[] = _inputBuffer;
                int l2 = _inputPtr;
                _inputPtr = l2 + 1;
                int i3 = 0xff & abyte3[l2];
                int j3 = base64variant.decodeBase64Char(i3);
                if(j3 < 0)
                {
                    if(j3 != -2)
                        throw reportInvalidChar(base64variant, i3, 3);
                    bytearraybuilder.appendTwoBytes(k2 >> 2);
                } else
                {
                    bytearraybuilder.appendThreeBytes(j3 | k2 << 6);
                }
            }
        } while(true);
    }

    protected int _decodeCharForError(int i)
        throws IOException, JsonParseException
    {
        if(i >= 0) goto _L2; else goto _L1
_L1:
        int j;
        int j1;
        int k;
        int l;
        int i1;
        int k1;
        int l1;
        if((i & 0xe0) == 192)
        {
            l = i & 0x1f;
            k = 1;
        } else
        if((i & 0xf0) == 224)
        {
            l = i & 0xf;
            k = 2;
        } else
        if((i & 0xf8) == 240)
        {
            l = i & 7;
            k = 3;
        } else
        {
            _reportInvalidInitial(i & 0xff);
            k = 1;
            l = i;
        }
        i1 = nextByte();
        if((i1 & 0xc0) != 128)
            _reportInvalidOther(i1 & 0xff);
        j1 = l << 6 | i1 & 0x3f;
        if(k <= 1) goto _L4; else goto _L3
_L3:
        k1 = nextByte();
        if((k1 & 0xc0) != 128)
            _reportInvalidOther(k1 & 0xff);
        j1 = j1 << 6 | k1 & 0x3f;
        if(k <= 2) goto _L4; else goto _L5
_L5:
        l1 = nextByte();
        if((l1 & 0xc0) != 128)
            _reportInvalidOther(l1 & 0xff);
        j = j1 << 6 | l1 & 0x3f;
_L7:
        return j;
_L4:
        j = j1;
        continue; /* Loop/switch isn't completed */
_L2:
        j = i;
        if(true) goto _L7; else goto _L6
_L6:
    }

    protected final char _decodeEscaped()
        throws IOException, JsonParseException
    {
        byte byte0;
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(" in character escape sequence");
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        byte0 = abyte0[i];
        byte0;
        JVM INSTR lookupswitch 9: default 128
    //                   34: 294
    //                   47: 294
    //                   92: 294
    //                   98: 259
    //                   102: 280
    //                   110: 273
    //                   114: 287
    //                   116: 266
    //                   117: 159;
           goto _L1 _L2 _L2 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
        _reportError((new StringBuilder()).append("Unrecognized character escape \\ followed by ").append(_getCharDesc(_decodeCharForError(byte0))).toString());
_L8:
        int j = 0;
        int k = 0;
        char c;
        for(; j < 4; j++)
        {
            if(_inputPtr >= _inputEnd && !loadMore())
                _reportInvalidEOF(" in character escape sequence");
            byte abyte1[] = _inputBuffer;
            int l = _inputPtr;
            _inputPtr = l + 1;
            byte byte1 = abyte1[l];
            int i1 = CharTypes.charToHex(byte1);
            if(i1 < 0)
                _reportUnexpectedChar(byte1, "expected a hex-digit for character escape sequence");
            k = i1 | k << 4;
        }

        c = (char)k;
          goto _L9
_L3:
        c = '\b';
_L11:
        return c;
_L7:
        c = '\t';
        continue; /* Loop/switch isn't completed */
_L5:
        c = '\n';
        continue; /* Loop/switch isn't completed */
_L4:
        c = '\f';
        continue; /* Loop/switch isn't completed */
_L6:
        c = '\r';
        continue; /* Loop/switch isn't completed */
_L2:
        c = (char)byte0;
        continue; /* Loop/switch isn't completed */
_L9:
        if(true) goto _L11; else goto _L10
_L10:
    }

    protected void _finishString()
        throws IOException, JsonParseException
    {
        char ac[];
        int ai[];
        byte abyte0[];
        int i;
        ac = _textBuffer.emptyAndGetCurrentSegment();
        ai = CharTypes.getInputCodeUtf8();
        abyte0 = _inputBuffer;
        i = 0;
_L6:
        do
        {
label0:
            {
                int j = _inputPtr;
                if(j >= _inputEnd)
                {
                    loadMoreGuaranteed();
                    j = _inputPtr;
                }
                if(i >= ac.length)
                {
                    ac = _textBuffer.finishCurrentSegment();
                    i = 0;
                }
                int k = _inputEnd;
                int l = j + (ac.length - i);
                int i1;
                int j1;
                int l1;
                int j2;
                int k2;
                int l2;
                int i3;
                if(l < k)
                {
                    i1 = i;
                    j1 = l;
                } else
                {
                    i1 = i;
                    j1 = k;
                }
                do
                {
                    if(j >= j1)
                        break;
                    int k1 = j + 1;
                    l1 = 0xff & abyte0[j];
                    if(ai[l1] != 0)
                    {
                        _inputPtr = k1;
                        if(l1 == 34)
                        {
                            _textBuffer.setCurrentLength(i1);
                            return;
                        }
                        break label0;
                    }
                    int i2 = i1 + 1;
                    ac[i1] = (char)l1;
                    j = k1;
                    i1 = i2;
                } while(true);
                _inputPtr = j;
                i = i1;
            }
        } while(true);
        ai[l1];
        JVM INSTR tableswitch 1 4: default 220
    //                   1 288
    //                   2 301
    //                   3 316
    //                   4 359;
           goto _L1 _L2 _L3 _L4 _L5
_L5:
        break MISSING_BLOCK_LABEL_359;
_L1:
        if(l1 < 32)
            _throwUnquotedSpace(l1, "string value");
        _reportInvalidChar(l1);
        l2 = l1;
        k2 = i1;
_L7:
        if(k2 >= ac.length)
        {
            ac = _textBuffer.finishCurrentSegment();
            k2 = 0;
        }
        i3 = k2 + 1;
        ac[k2] = (char)l2;
        i = i3;
          goto _L6
_L2:
        l2 = _decodeEscaped();
        k2 = i1;
          goto _L7
_L3:
        l2 = _decodeUtf8_2(l1);
        k2 = i1;
          goto _L7
_L4:
        if(_inputEnd - _inputPtr >= 2)
        {
            l2 = _decodeUtf8_3fast(l1);
            k2 = i1;
        } else
        {
            l2 = _decodeUtf8_3(l1);
            k2 = i1;
        }
          goto _L7
        j2 = _decodeUtf8_4(l1);
        k2 = i1 + 1;
        ac[i1] = (char)(0xd800 | j2 >> 10);
        if(k2 >= ac.length)
        {
            ac = _textBuffer.finishCurrentSegment();
            k2 = 0;
        }
        l2 = 0xdc00 | j2 & 0x3ff;
          goto _L7
    }

    protected void _matchToken(JsonToken jsontoken)
        throws IOException, JsonParseException
    {
        byte abyte0[] = jsontoken.asByteArray();
        int i = 1;
        for(int j = abyte0.length; i < j; i++)
        {
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            if(abyte0[i] != _inputBuffer[_inputPtr])
                _reportInvalidToken(jsontoken.asString().substring(0, i));
            _inputPtr = 1 + _inputPtr;
        }

    }

    protected final Name _parseFieldName(int i)
        throws IOException, JsonParseException
    {
        if(i != 34)
            _reportUnexpectedChar(i, "was expecting double-quote to start field name");
        Name name;
        if(_inputEnd - _inputPtr < 9)
        {
            name = slowParseFieldName();
        } else
        {
            int ai[] = CharTypes.getInputCodeLatin1();
            byte abyte0[] = _inputBuffer;
            int j = _inputPtr;
            _inputPtr = j + 1;
            int k = 0xff & abyte0[j];
            if(ai[k] != 0)
            {
                if(k == 34)
                    name = BytesToNameCanonicalizer.getEmptyName();
                else
                    name = parseFieldName(0, k, 0);
            } else
            {
                byte abyte1[] = _inputBuffer;
                int l = _inputPtr;
                _inputPtr = l + 1;
                int i1 = 0xff & abyte1[l];
                if(ai[i1] != 0)
                {
                    if(i1 == 34)
                        name = findName(k, 1);
                    else
                        name = parseFieldName(k, i1, 1);
                } else
                {
                    int j1 = i1 | k << 8;
                    byte abyte2[] = _inputBuffer;
                    int k1 = _inputPtr;
                    _inputPtr = k1 + 1;
                    int l1 = 0xff & abyte2[k1];
                    if(ai[l1] != 0)
                    {
                        if(l1 == 34)
                            name = findName(j1, 2);
                        else
                            name = parseFieldName(j1, l1, 2);
                    } else
                    {
                        int i2 = l1 | j1 << 8;
                        byte abyte3[] = _inputBuffer;
                        int j2 = _inputPtr;
                        _inputPtr = j2 + 1;
                        int k2 = 0xff & abyte3[j2];
                        if(ai[k2] != 0)
                        {
                            if(k2 == 34)
                                name = findName(i2, 3);
                            else
                                name = parseFieldName(i2, k2, 3);
                        } else
                        {
                            int l2 = k2 | i2 << 8;
                            byte abyte4[] = _inputBuffer;
                            int i3 = _inputPtr;
                            _inputPtr = i3 + 1;
                            int j3 = 0xff & abyte4[i3];
                            if(ai[j3] != 0)
                            {
                                if(j3 == 34)
                                    name = findName(l2, 4);
                                else
                                    name = parseFieldName(l2, j3, 4);
                            } else
                            {
                                name = parseMediumFieldName(l2, j3);
                            }
                        }
                    }
                }
            }
        }
        return name;
    }

    protected void _reportInvalidChar(int i)
        throws JsonParseException
    {
        if(i < 32)
            _throwInvalidSpace(i);
        _reportInvalidInitial(i);
    }

    protected void _reportInvalidInitial(int i)
        throws JsonParseException
    {
        _reportError((new StringBuilder()).append("Invalid UTF-8 start byte 0x").append(Integer.toHexString(i)).toString());
    }

    protected void _reportInvalidOther(int i)
        throws JsonParseException
    {
        _reportError((new StringBuilder()).append("Invalid UTF-8 middle byte 0x").append(Integer.toHexString(i)).toString());
    }

    protected void _reportInvalidOther(int i, int j)
        throws JsonParseException
    {
        _inputPtr = j;
        _reportInvalidOther(i);
    }

    protected final void _skipCR()
        throws IOException
    {
        if((_inputPtr < _inputEnd || loadMore()) && _inputBuffer[_inputPtr] == 10)
            _inputPtr = 1 + _inputPtr;
        _currInputRow = 1 + _currInputRow;
        _currInputRowStart = _inputPtr;
    }

    protected final void _skipLF()
        throws IOException
    {
        _currInputRow = 1 + _currInputRow;
        _currInputRowStart = _inputPtr;
    }

    protected void _skipString()
        throws IOException, JsonParseException
    {
        int ai[];
        byte abyte0[];
        _tokenIncomplete = false;
        ai = CharTypes.getInputCodeUtf8();
        abyte0 = _inputBuffer;
_L2:
        do
        {
label0:
            {
                int i = _inputPtr;
                int j = _inputEnd;
                int k;
                int l;
                int i1;
                int j1;
                if(i >= j)
                {
                    loadMoreGuaranteed();
                    int k1 = _inputPtr;
                    int l1 = _inputEnd;
                    k = k1;
                    l = l1;
                } else
                {
                    k = i;
                    l = j;
                }
                for(; k < l; k = i1)
                {
                    i1 = k + 1;
                    j1 = 0xff & abyte0[k];
                    if(ai[j1] == 0)
                        break MISSING_BLOCK_LABEL_204;
                    _inputPtr = i1;
                    if(j1 == 34)
                        return;
                    break label0;
                }

                _inputPtr = k;
            }
        } while(true);
        switch(ai[j1])
        {
        default:
            if(j1 < 32)
                _throwUnquotedSpace(j1, "string value");
            _reportInvalidChar(j1);
            break;

        case 1: // '\001'
            _decodeEscaped();
            break;

        case 2: // '\002'
            _skipUtf8_2(j1);
            break;

        case 3: // '\003'
            _skipUtf8_3(j1);
            break;

        case 4: // '\004'
            _skipUtf8_4(j1);
            break;
        }
        continue; /* Loop/switch isn't completed */
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void close()
        throws IOException
    {
        super.close();
        _symbols.release();
    }

    public JsonToken nextToken()
        throws IOException, JsonParseException
    {
        if(_currToken != JsonToken.FIELD_NAME) goto _L2; else goto _L1
_L1:
        JsonToken jsontoken = _nextAfterName();
_L19:
        return jsontoken;
_L2:
        int i;
        if(_tokenIncomplete)
            _skipString();
        i = _skipWSOrEnd();
        if(i >= 0) goto _L4; else goto _L3
_L3:
        close();
        _currToken = null;
        jsontoken = null;
          goto _L5
_L4:
        _tokenInputTotal = (_currInputProcessed + (long)_inputPtr) - 1L;
        _tokenInputRow = _currInputRow;
        _tokenInputCol = _inputPtr - _currInputRowStart - 1;
        _binaryValue = null;
        if(i != 93) goto _L7; else goto _L6
_L6:
        if(!_parsingContext.inArray())
            _reportMismatchedEndMarker(i, ']');
        _parsingContext = _parsingContext.getParent();
        jsontoken = JsonToken.END_ARRAY;
        _currToken = jsontoken;
          goto _L5
_L7:
        if(i != 125) goto _L9; else goto _L8
_L8:
        if(!_parsingContext.inObject())
            _reportMismatchedEndMarker(i, '}');
        _parsingContext = _parsingContext.getParent();
        jsontoken = JsonToken.END_OBJECT;
        _currToken = jsontoken;
          goto _L5
_L9:
        boolean flag;
        if(_parsingContext.expectComma())
        {
            if(i != 44)
                _reportUnexpectedChar(i, (new StringBuilder()).append("was expecting comma to separate ").append(_parsingContext.getTypeDesc()).append(" entries").toString());
            i = _skipWS();
        }
        flag = _parsingContext.inObject();
        if(flag)
        {
            Name name = _parseFieldName(i);
            _parsingContext.setCurrentName(name.getName());
            int j = _skipWS();
            if(j != 58)
                _reportUnexpectedChar(j, "was expecting a colon to separate field name and value");
            _currToken = JsonToken.FIELD_NAME;
            i = _skipWS();
        }
        i;
        JVM INSTR lookupswitch 19: default 476
    //                   34: 503
    //                   45: 625
    //                   48: 625
    //                   49: 625
    //                   50: 625
    //                   51: 625
    //                   52: 625
    //                   53: 625
    //                   54: 625
    //                   55: 625
    //                   56: 625
    //                   57: 625
    //                   91: 515
    //                   93: 575
    //                   102: 597
    //                   110: 611
    //                   116: 583
    //                   123: 545
    //                   125: 575;
           goto _L10 _L11 _L12 _L12 _L12 _L12 _L12 _L12 _L12 _L12 _L12 _L12 _L12 _L13 _L14 _L15 _L16 _L17 _L18 _L14
_L10:
        _reportUnexpectedChar(i, "expected a valid value (number, String, array, object, 'true', 'false' or 'null')");
        jsontoken = null;
_L20:
        if(flag)
        {
            _nextToken = jsontoken;
            jsontoken = _currToken;
        } else
        {
            _currToken = jsontoken;
        }
_L5:
        if(true) goto _L19; else goto _L11
_L11:
        _tokenIncomplete = true;
        jsontoken = JsonToken.VALUE_STRING;
          goto _L20
_L13:
        if(!flag)
            _parsingContext = _parsingContext.createChildArrayContext(_tokenInputRow, _tokenInputCol);
        jsontoken = JsonToken.START_ARRAY;
          goto _L20
_L18:
        if(!flag)
            _parsingContext = _parsingContext.createChildObjectContext(_tokenInputRow, _tokenInputCol);
        jsontoken = JsonToken.START_OBJECT;
          goto _L20
_L14:
        _reportUnexpectedChar(i, "expected a value");
_L17:
        _matchToken(JsonToken.VALUE_TRUE);
        jsontoken = JsonToken.VALUE_TRUE;
          goto _L20
_L15:
        _matchToken(JsonToken.VALUE_FALSE);
        jsontoken = JsonToken.VALUE_FALSE;
          goto _L20
_L16:
        _matchToken(JsonToken.VALUE_NULL);
        jsontoken = JsonToken.VALUE_NULL;
          goto _L20
_L12:
        jsontoken = parseNumberText(i);
          goto _L20
    }

    protected Name parseEscapedFieldName(int ai[], int i, int j, int k, int l)
        throws IOException, JsonParseException
    {
        int ai1[] = CharTypes.getInputCodeLatin1();
        int i1 = l;
        int j1 = k;
        int k1 = j;
        int l1 = i;
        int ai2[] = ai;
        do
        {
            if(ai1[j1] != 0)
            {
                if(j1 == 34)
                {
                    int i4;
                    int ai7[];
                    if(i1 > 0)
                    {
                        int i2;
                        int ai4[];
                        int j2;
                        byte abyte0[];
                        int k2;
                        int l2;
                        int i3;
                        int j3;
                        int k3;
                        int l3;
                        Name name;
                        Name name1;
                        int ai8[];
                        int j4;
                        if(l1 >= ai2.length)
                        {
                            ai8 = growArrayBy(ai2, ai2.length);
                            _quadBuffer = ai8;
                        } else
                        {
                            ai8 = ai2;
                        }
                        j4 = l1 + 1;
                        ai8[l1] = k1;
                        ai7 = ai8;
                        i4 = j4;
                    } else
                    {
                        i4 = l1;
                        ai7 = ai2;
                    }
                    name = _symbols.findName(ai7, i4);
                    if(name == null)
                        name1 = addName(ai7, i4, i1);
                    else
                        name1 = name;
                    return name1;
                }
                if(j1 != 92)
                    _throwUnquotedSpace(j1, "name");
                j1 = _decodeEscaped();
                if(j1 > 127)
                {
                    if(i1 >= 4)
                    {
                        int ai3[];
                        int ai5[];
                        int ai6[];
                        if(l1 >= ai2.length)
                        {
                            ai6 = growArrayBy(ai2, ai2.length);
                            _quadBuffer = ai6;
                        } else
                        {
                            ai6 = ai2;
                        }
                        l3 = l1 + 1;
                        ai6[l1] = k1;
                        k1 = 0;
                        l1 = l3;
                        ai2 = ai6;
                        i1 = 0;
                    }
                    if(j1 < 2048)
                    {
                        k1 = k1 << 8 | (0xc0 | j1 >> 6);
                        i1++;
                    } else
                    {
                        i3 = k1 << 8 | (0xe0 | j1 >> 12);
                        j3 = i1 + 1;
                        if(j3 >= 4)
                        {
                            if(l1 >= ai2.length)
                            {
                                ai5 = growArrayBy(ai2, ai2.length);
                                _quadBuffer = ai5;
                            } else
                            {
                                ai5 = ai2;
                            }
                            k3 = l1 + 1;
                            ai5[l1] = i3;
                            i3 = 0;
                            l1 = k3;
                            ai2 = ai5;
                            j3 = 0;
                        }
                        k1 = i3 << 8 | (0x80 | 0x3f & j1 >> 6);
                        i1 = j3 + 1;
                    }
                    j1 = 0x80 | j1 & 0x3f;
                }
            }
            if(i1 < 4)
            {
                i1++;
                j1 |= k1 << 8;
                j2 = l1;
                ai4 = ai2;
            } else
            {
                if(l1 >= ai2.length)
                {
                    ai3 = growArrayBy(ai2, ai2.length);
                    _quadBuffer = ai3;
                } else
                {
                    ai3 = ai2;
                }
                i2 = l1 + 1;
                ai3[l1] = k1;
                ai4 = ai3;
                i1 = 1;
                j2 = i2;
            }
            if(_inputPtr >= _inputEnd && !loadMore())
                _reportInvalidEOF(" in field name");
            abyte0 = _inputBuffer;
            k2 = _inputPtr;
            _inputPtr = k2 + 1;
            l2 = 0xff & abyte0[k2];
            ai2 = ai4;
            l1 = j2;
            k1 = j1;
            j1 = l2;
        } while(true);
    }

    protected Name parseLongFieldName(int i)
        throws IOException, JsonParseException
    {
        int ai[];
        int j;
        int k;
        ai = CharTypes.getInputCodeLatin1();
        j = 2;
        k = i;
_L6:
        if(_inputEnd - _inputPtr >= 4) goto _L2; else goto _L1
_L1:
        Name name = parseEscapedFieldName(_quadBuffer, j, 0, k, 0);
_L4:
        return name;
_L2:
        int l2;
        byte abyte0[] = _inputBuffer;
        int l = _inputPtr;
        _inputPtr = l + 1;
        int i1 = 0xff & abyte0[l];
        if(ai[i1] != 0)
        {
            if(i1 == 34)
                name = findName(_quadBuffer, j, k, 1);
            else
                name = parseEscapedFieldName(_quadBuffer, j, k, i1, 1);
            continue; /* Loop/switch isn't completed */
        }
        int j1 = i1 | k << 8;
        byte abyte1[] = _inputBuffer;
        int k1 = _inputPtr;
        _inputPtr = k1 + 1;
        int l1 = 0xff & abyte1[k1];
        if(ai[l1] != 0)
        {
            if(l1 == 34)
                name = findName(_quadBuffer, j, j1, 2);
            else
                name = parseEscapedFieldName(_quadBuffer, j, j1, l1, 2);
            continue; /* Loop/switch isn't completed */
        }
        int i2 = l1 | j1 << 8;
        byte abyte2[] = _inputBuffer;
        int j2 = _inputPtr;
        _inputPtr = j2 + 1;
        int k2 = 0xff & abyte2[j2];
        if(ai[k2] != 0)
        {
            if(k2 == 34)
                name = findName(_quadBuffer, j, i2, 3);
            else
                name = parseEscapedFieldName(_quadBuffer, j, i2, k2, 3);
            continue; /* Loop/switch isn't completed */
        }
        l2 = k2 | i2 << 8;
        byte abyte3[] = _inputBuffer;
        int i3 = _inputPtr;
        _inputPtr = i3 + 1;
        k = 0xff & abyte3[i3];
        if(ai[k] == 0)
            break; /* Loop/switch isn't completed */
        if(k == 34)
            name = findName(_quadBuffer, j, l2, 4);
        else
            name = parseEscapedFieldName(_quadBuffer, j, l2, k, 4);
        if(true) goto _L4; else goto _L3
_L3:
        if(j >= _quadBuffer.length)
            _quadBuffer = growArrayBy(_quadBuffer, j);
        int ai1[] = _quadBuffer;
        int j3 = j + 1;
        ai1[j] = l2;
        j = j3;
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected Name parseMediumFieldName(int i, int j)
        throws IOException, JsonParseException
    {
        int ai[] = CharTypes.getInputCodeLatin1();
        byte abyte0[] = _inputBuffer;
        int k = _inputPtr;
        _inputPtr = k + 1;
        int l = 0xff & abyte0[k];
        Name name;
        if(ai[l] != 0)
        {
            if(l == 34)
                name = findName(i, j, 1);
            else
                name = parseFieldName(i, j, l, 1);
        } else
        {
            int i1 = l | j << 8;
            byte abyte1[] = _inputBuffer;
            int j1 = _inputPtr;
            _inputPtr = j1 + 1;
            int k1 = 0xff & abyte1[j1];
            if(ai[k1] != 0)
            {
                if(k1 == 34)
                    name = findName(i, i1, 2);
                else
                    name = parseFieldName(i, i1, k1, 2);
            } else
            {
                int l1 = k1 | i1 << 8;
                byte abyte2[] = _inputBuffer;
                int i2 = _inputPtr;
                _inputPtr = i2 + 1;
                int j2 = 0xff & abyte2[i2];
                if(ai[j2] != 0)
                {
                    if(j2 == 34)
                        name = findName(i, l1, 3);
                    else
                        name = parseFieldName(i, l1, j2, 3);
                } else
                {
                    int k2 = j2 | l1 << 8;
                    byte abyte3[] = _inputBuffer;
                    int l2 = _inputPtr;
                    _inputPtr = l2 + 1;
                    int i3 = 0xff & abyte3[l2];
                    if(ai[i3] != 0)
                    {
                        if(i3 == 34)
                            name = findName(i, k2, 4);
                        else
                            name = parseFieldName(i, k2, i3, 4);
                    } else
                    {
                        _quadBuffer[0] = i;
                        _quadBuffer[1] = k2;
                        name = parseLongFieldName(i3);
                    }
                }
            }
        }
        return name;
    }

    public final Object readValueAs(Class class1)
        throws IOException, JsonProcessingException
    {
        if(_objectCodec == null)
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize Json into regular Java objects");
        else
            return _objectCodec.readValue(this, class1);
    }

    public final Object readValueAs(TypeReference typereference)
        throws IOException, JsonProcessingException
    {
        if(_objectCodec == null)
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize Json into regular Java objects");
        else
            return _objectCodec.readValue(this, typereference);
    }

    public final JsonNode readValueAsTree()
        throws IOException, JsonProcessingException
    {
        if(_objectCodec == null)
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize Json into JsonNode tree");
        else
            return _objectCodec.readTree(this);
    }

    protected IllegalArgumentException reportInvalidChar(Base64Variant base64variant, int i, int j)
        throws IllegalArgumentException
    {
        return reportInvalidChar(base64variant, i, j, null);
    }

    protected IllegalArgumentException reportInvalidChar(Base64Variant base64variant, int i, int j, String s)
        throws IllegalArgumentException
    {
        String s1;
        if(i <= 32)
            s1 = (new StringBuilder()).append("Illegal white space character (code 0x").append(Integer.toHexString(i)).append(") as character #").append(j + 1).append(" of 4-char base64 unit: can only used between units").toString();
        else
        if(base64variant.usesPaddingChar(i))
            s1 = (new StringBuilder()).append("Unexpected padding character ('").append(base64variant.getPaddingChar()).append("') as character #").append(j + 1).append(" of 4-char base64 unit: padding only legal as 3rd or 4th character").toString();
        else
        if(!Character.isDefined(i) || Character.isISOControl(i))
            s1 = (new StringBuilder()).append("Illegal character (code 0x").append(Integer.toHexString(i)).append(") in base64 content").toString();
        else
            s1 = (new StringBuilder()).append("Illegal character '").append((char)i).append("' (code 0x").append(Integer.toHexString(i)).append(") in base64 content").toString();
        if(s != null)
            s1 = (new StringBuilder()).append(s1).append(": ").append(s).toString();
        return new IllegalArgumentException(s1);
    }

    protected Name slowParseFieldName()
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(": was expecting closing quote for name");
        byte abyte0[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        int j = 0xff & abyte0[i];
        Name name;
        if(j == 34)
            name = BytesToNameCanonicalizer.getEmptyName();
        else
            name = parseEscapedFieldName(_quadBuffer, 0, 0, j, 0);
        return name;
    }

    static final byte BYTE_LF = 10;
    final ObjectCodec _objectCodec;
    protected int _quadBuffer[];
    protected final BytesToNameCanonicalizer _symbols;
}
