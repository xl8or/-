// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.jackson.*;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.util.*;

// Referenced classes of package org.codehaus.jackson.impl:
//            ReaderBasedNumericParser, JsonReadContext

public class ReaderBasedParser extends ReaderBasedNumericParser
{

    public ReaderBasedParser(IOContext iocontext, int i, Reader reader, ObjectCodec objectcodec, CharsToNameCanonicalizer charstonamecanonicalizer)
    {
        super(iocontext, i, reader);
        _objectCodec = objectcodec;
        _symbols = charstonamecanonicalizer;
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
        char c;
        _reportError((new StringBuilder()).append("Unrecognized token '").append(stringbuilder.toString()).append("': was expecting 'null', 'true' or 'false'").toString());
        return;
_L2:
        if(!Character.isJavaIdentifierPart(c = _inputBuffer[_inputPtr])) goto _L1; else goto _L3
_L3:
        _inputPtr = 1 + _inputPtr;
        stringbuilder.append(c);
        if(true) goto _L5; else goto _L4
_L4:
    }

    private final void _skipCComment()
        throws IOException, JsonParseException
    {
_L8:
        if(_inputPtr >= _inputEnd && !loadMore()) goto _L2; else goto _L1
_L1:
        char c;
        char ac[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        c = ac[i];
        if(c > '*')
            continue; /* Loop/switch isn't completed */
        if(c != '*') goto _L4; else goto _L3
_L3:
        if(_inputPtr < _inputEnd || loadMore()) goto _L5; else goto _L2
_L2:
        _reportInvalidEOF(" in a comment");
_L6:
        return;
_L5:
        if(_inputBuffer[_inputPtr] != '/')
            continue; /* Loop/switch isn't completed */
        _inputPtr = 1 + _inputPtr;
        if(true) goto _L6; else goto _L4
_L4:
        if(c < ' ')
            if(c == '\n')
                _skipLF();
            else
            if(c == '\r')
                _skipCR();
            else
            if(c != '\t')
                _throwInvalidSpace(c);
        if(true) goto _L8; else goto _L7
_L7:
    }

    private final void _skipComment()
        throws IOException, JsonParseException
    {
        if(!isFeatureEnabled(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS))
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(" in a comment");
        char ac[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        char c = ac[i];
        if(c == '/')
            _skipCppComment();
        else
        if(c == '*')
            _skipCComment();
        else
            _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
    }

    private final void _skipCppComment()
        throws IOException, JsonParseException
    {
_L7:
        if(_inputPtr >= _inputEnd && !loadMore()) goto _L2; else goto _L1
_L1:
        char c;
        char ac[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        c = ac[i];
        if(c >= ' ')
            continue; /* Loop/switch isn't completed */
        if(c != '\n') goto _L4; else goto _L3
_L3:
        _skipLF();
_L2:
        return;
_L4:
        if(c != '\r')
            break; /* Loop/switch isn't completed */
        _skipCR();
        if(true) goto _L2; else goto _L5
_L5:
        if(c != '\t')
            _throwInvalidSpace(c);
        if(true) goto _L7; else goto _L6
_L6:
    }

    private final int _skipWS()
        throws IOException, JsonParseException
    {
        do
        {
            if(_inputPtr >= _inputEnd && !loadMore())
                break;
            char ac[] = _inputBuffer;
            int i = _inputPtr;
            _inputPtr = i + 1;
            char c = ac[i];
            if(c > ' ')
            {
                if(c != '/')
                    return c;
                _skipComment();
            } else
            if(c != ' ')
                if(c == '\n')
                    _skipLF();
                else
                if(c == '\r')
                    _skipCR();
                else
                if(c != '\t')
                    _throwInvalidSpace(c);
        } while(true);
        throw _constructError((new StringBuilder()).append("Unexpected end-of-input within/between ").append(_parsingContext.getTypeDesc()).append(" entries").toString());
    }

    private final int _skipWSOrEnd()
        throws IOException, JsonParseException
    {
_L5:
        char c;
        if(_inputPtr >= _inputEnd && !loadMore())
            break MISSING_BLOCK_LABEL_106;
        char ac[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        c = ac[i];
        if(c <= ' ') goto _L2; else goto _L1
_L1:
        if(c == '/') goto _L4; else goto _L3
_L3:
        return c;
_L4:
        _skipComment();
          goto _L5
_L2:
        if(c != ' ')
            if(c == '\n')
                _skipLF();
            else
            if(c == '\r')
                _skipCR();
            else
            if(c != '\t')
                _throwInvalidSpace(c);
          goto _L5
        _handleEOF();
        c = '\uFFFF';
          goto _L3
    }

    private void handleFieldName2(int i, int j)
        throws IOException, JsonParseException
    {
        int l;
        int i1;
        char ac1[];
        _textBuffer.resetWithShared(_inputBuffer, i, _inputPtr - i);
        char ac[] = _textBuffer.getCurrentSegment();
        int k = _textBuffer.getCurrentSegmentSize();
        l = j;
        i1 = k;
        ac1 = ac;
_L3:
        char c;
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(": was expecting closing quote for name");
        char ac2[] = _inputBuffer;
        int j1 = _inputPtr;
        _inputPtr = j1 + 1;
        c = ac2[j1];
        if(c > '\\')
            break MISSING_BLOCK_LABEL_250;
        if(c != '\\') goto _L2; else goto _L1
_L1:
        char c1 = _decodeEscaped();
_L4:
        l = c + l * 31;
        int k1 = i1 + 1;
        ac1[i1] = c1;
        TextBuffer textbuffer;
        char ac3[];
        int l1;
        int i2;
        if(k1 >= ac1.length)
        {
            ac1 = _textBuffer.finishCurrentSegment();
            i1 = 0;
        } else
        {
            i1 = k1;
        }
        if(true) goto _L3; else goto _L2
_L2:
        if(c <= '"')
        {
            if(c == '"')
            {
                _textBuffer.setCurrentLength(i1);
                textbuffer = _textBuffer;
                ac3 = textbuffer.getTextBuffer();
                l1 = textbuffer.getTextOffset();
                i2 = textbuffer.size();
                _parsingContext.setCurrentName(_symbols.findSymbol(ac3, l1, i2, l));
                return;
            }
            if(c < ' ')
                _throwUnquotedSpace(c, "name");
        }
        c1 = c;
          goto _L4
    }

    protected byte[] _decodeBase64(Base64Variant base64variant)
        throws IOException, JsonParseException
    {
        ByteArrayBuilder bytearraybuilder = _getByteArrayBuilder();
        do
        {
            char c;
            do
            {
                if(_inputPtr >= _inputEnd)
                    loadMoreGuaranteed();
                char ac[] = _inputBuffer;
                int i = _inputPtr;
                _inputPtr = i + 1;
                c = ac[i];
            } while(c <= ' ');
            int j = base64variant.decodeBase64Char(c);
            if(j < 0)
                if(c == '"')
                    return bytearraybuilder.toByteArray();
                else
                    throw reportInvalidChar(base64variant, c, 0);
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            char ac1[] = _inputBuffer;
            int k = _inputPtr;
            _inputPtr = k + 1;
            char c1 = ac1[k];
            int l = base64variant.decodeBase64Char(c1);
            if(l < 0)
                throw reportInvalidChar(base64variant, c1, 1);
            int i1 = l | j << 6;
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            char ac2[] = _inputBuffer;
            int j1 = _inputPtr;
            _inputPtr = j1 + 1;
            char c2 = ac2[j1];
            int k1 = base64variant.decodeBase64Char(c2);
            if(k1 < 0)
            {
                if(k1 != -2)
                    throw reportInvalidChar(base64variant, c2, 2);
                if(_inputPtr >= _inputEnd)
                    loadMoreGuaranteed();
                char ac4[] = _inputBuffer;
                int k2 = _inputPtr;
                _inputPtr = k2 + 1;
                char c4 = ac4[k2];
                if(!base64variant.usesPaddingChar(c4))
                    throw reportInvalidChar(base64variant, c4, 3, (new StringBuilder()).append("expected padding character '").append(base64variant.getPaddingChar()).append("'").toString());
                bytearraybuilder.append(i1 >> 4);
            } else
            {
                int l1 = k1 | i1 << 6;
                if(_inputPtr >= _inputEnd)
                    loadMoreGuaranteed();
                char ac3[] = _inputBuffer;
                int i2 = _inputPtr;
                _inputPtr = i2 + 1;
                char c3 = ac3[i2];
                int j2 = base64variant.decodeBase64Char(c3);
                if(j2 < 0)
                {
                    if(j2 != -2)
                        throw reportInvalidChar(base64variant, c3, 3);
                    bytearraybuilder.appendTwoBytes(l1 >> 2);
                } else
                {
                    bytearraybuilder.appendThreeBytes(j2 | l1 << 6);
                }
            }
        } while(true);
    }

    protected final char _decodeEscaped()
        throws IOException, JsonParseException
    {
        char c;
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(" in character escape sequence");
        char ac[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        c = ac[i];
        c;
        JVM INSTR lookupswitch 9: default 128
    //                   34: 258
    //                   47: 258
    //                   92: 258
    //                   98: 255
    //                   102: 272
    //                   110: 266
    //                   114: 278
    //                   116: 260
    //                   117: 155;
           goto _L1 _L2 _L2 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L2:
        break; /* Loop/switch isn't completed */
_L1:
        _reportError((new StringBuilder()).append("Unrecognized character escape ").append(_getCharDesc(c)).toString());
_L8:
        int j = 0;
        int k = 0;
        for(; j < 4; j++)
        {
            if(_inputPtr >= _inputEnd && !loadMore())
                _reportInvalidEOF(" in character escape sequence");
            char ac1[] = _inputBuffer;
            int l = _inputPtr;
            _inputPtr = l + 1;
            char c1 = ac1[l];
            int i1 = CharTypes.charToHex(c1);
            if(i1 < 0)
                _reportUnexpectedChar(c1, "expected a hex-digit for character escape sequence");
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
_L9:
        if(true) goto _L11; else goto _L10
_L10:
    }

    protected void _finishString()
        throws IOException, JsonParseException
    {
        int i;
        int j;
        i = _inputPtr;
        j = _inputEnd;
        if(i >= j) goto _L2; else goto _L1
_L1:
        int ai[];
        int k;
        ai = CharTypes.getInputCodeLatin1();
        k = ai.length;
_L3:
        char c = _inputBuffer[i];
        if(c >= k || ai[c] == 0)
            continue; /* Loop/switch isn't completed */
        if(c != '"')
            break; /* Loop/switch isn't completed */
        _textBuffer.resetWithShared(_inputBuffer, _inputPtr, i - _inputPtr);
        _inputPtr = i + 1;
_L4:
        return;
        if(++i < j) goto _L3; else goto _L2
_L2:
        _textBuffer.resetWithCopy(_inputBuffer, _inputPtr, i - _inputPtr);
        _inputPtr = i;
        _finishString2();
          goto _L4
    }

    protected void _finishString2()
        throws IOException, JsonParseException
    {
        char ac1[];
        int j;
        char ac[] = _textBuffer.getCurrentSegment();
        int i = _textBuffer.getCurrentSegmentSize();
        ac1 = ac;
        j = i;
_L2:
        char c;
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(": was expecting closing quote for a string value");
        char ac2[] = _inputBuffer;
        int k = _inputPtr;
        _inputPtr = k + 1;
        c = ac2[k];
        if(c <= '\\')
        {
            if(c != '\\')
                break; /* Loop/switch isn't completed */
            c = _decodeEscaped();
        }
_L4:
        if(j >= ac1.length)
        {
            ac1 = _textBuffer.finishCurrentSegment();
            j = 0;
        }
        int l = j + 1;
        ac1[j] = c;
        j = l;
        if(true) goto _L2; else goto _L1
_L1:
        if(c > '"') goto _L4; else goto _L3
_L3:
        if(c == '"')
        {
            _textBuffer.setCurrentLength(j);
            return;
        }
        if(c < ' ')
            _throwUnquotedSpace(c, "string value");
          goto _L4
    }

    protected void _handleFieldName(int i)
        throws IOException, JsonParseException
    {
        int j;
        int k;
        int l;
        int i1;
        int ai[];
        int k1;
        if(i != 34)
            _reportUnexpectedChar(i, "was expecting double-quote to start field name");
        j = _inputPtr;
        k = _inputEnd;
        if(j >= k)
            break MISSING_BLOCK_LABEL_164;
        ai = CharTypes.getInputCodeLatin1();
        k1 = ai.length;
        l = j;
        i1 = 0;
_L4:
        char c = _inputBuffer[l];
        if(c >= k1 || ai[c] == 0) goto _L2; else goto _L1
_L1:
        if(c != '"')
            break; /* Loop/switch isn't completed */
        int l1 = _inputPtr;
        _inputPtr = l + 1;
        String s = _symbols.findSymbol(_inputBuffer, l1, l - l1, i1);
        _parsingContext.setCurrentName(s);
_L5:
        return;
_L2:
        i1 = c + i1 * 31;
        if(++l < k) goto _L4; else goto _L3
_L3:
        int j1 = _inputPtr;
        _inputPtr = l;
        handleFieldName2(j1, i1);
          goto _L5
        l = j;
        i1 = 0;
          goto _L3
    }

    protected void _matchToken(JsonToken jsontoken)
        throws IOException, JsonParseException
    {
        String s = jsontoken.asString();
        int i = 1;
        for(int j = s.length(); i < j; i++)
        {
            if(_inputPtr >= _inputEnd && !loadMore())
                _reportInvalidEOF(" in a value");
            if(_inputBuffer[_inputPtr] != s.charAt(i))
                _reportInvalidToken(s.substring(0, i));
            _inputPtr = 1 + _inputPtr;
        }

    }

    protected final void _skipCR()
        throws IOException
    {
        if((_inputPtr < _inputEnd || loadMore()) && _inputBuffer[_inputPtr] == '\n')
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
        _tokenIncomplete = false;
        int i = _inputPtr;
        int j = _inputEnd;
        char ac[] = _inputBuffer;
        int k = i;
        int l = j;
        do
        {
            if(k >= l)
            {
                _inputPtr = k;
                if(!loadMore())
                    _reportInvalidEOF(": was expecting closing quote for a string value");
                int l1 = _inputPtr;
                int i2 = _inputEnd;
                k = l1;
                l = i2;
            }
            int i1 = k + 1;
            char c = ac[k];
            if(c <= '\\')
            {
                if(c == '\\')
                {
                    _inputPtr = i1;
                    _decodeEscaped();
                    int j1 = _inputPtr;
                    int k1 = _inputEnd;
                    k = j1;
                    l = k1;
                    continue;
                }
                if(c <= '"')
                {
                    if(c == '"')
                    {
                        _inputPtr = i1;
                        return;
                    }
                    if(c < ' ')
                    {
                        _inputPtr = i1;
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            k = i1;
        } while(true);
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
            _handleFieldName(i);
            _currToken = JsonToken.FIELD_NAME;
            int j = _skipWS();
            if(j != 58)
                _reportUnexpectedChar(j, "was expecting a colon to separate field name and value");
            i = _skipWS();
        }
        i;
        JVM INSTR lookupswitch 19: default 460
    //                   34: 487
    //                   45: 609
    //                   48: 609
    //                   49: 609
    //                   50: 609
    //                   51: 609
    //                   52: 609
    //                   53: 609
    //                   54: 609
    //                   55: 609
    //                   56: 609
    //                   57: 609
    //                   91: 499
    //                   93: 559
    //                   102: 581
    //                   110: 595
    //                   116: 567
    //                   123: 529
    //                   125: 559;
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

    protected IllegalArgumentException reportInvalidChar(Base64Variant base64variant, char c, int i)
        throws IllegalArgumentException
    {
        return reportInvalidChar(base64variant, c, i, null);
    }

    protected IllegalArgumentException reportInvalidChar(Base64Variant base64variant, char c, int i, String s)
        throws IllegalArgumentException
    {
        String s1;
        if(c <= ' ')
            s1 = (new StringBuilder()).append("Illegal white space character (code 0x").append(Integer.toHexString(c)).append(") as character #").append(i + 1).append(" of 4-char base64 unit: can only used between units").toString();
        else
        if(base64variant.usesPaddingChar(c))
            s1 = (new StringBuilder()).append("Unexpected padding character ('").append(base64variant.getPaddingChar()).append("') as character #").append(i + 1).append(" of 4-char base64 unit: padding only legal as 3rd or 4th character").toString();
        else
        if(!Character.isDefined(c) || Character.isISOControl(c))
            s1 = (new StringBuilder()).append("Illegal character (code 0x").append(Integer.toHexString(c)).append(") in base64 content").toString();
        else
            s1 = (new StringBuilder()).append("Illegal character '").append(c).append("' (code 0x").append(Integer.toHexString(c)).append(") in base64 content").toString();
        if(s != null)
            s1 = (new StringBuilder()).append(s1).append(": ").append(s).toString();
        return new IllegalArgumentException(s1);
    }

    final ObjectCodec _objectCodec;
    protected final CharsToNameCanonicalizer _symbols;
}
