// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import org.codehaus.jackson.*;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.util.ByteArrayBuilder;
import org.codehaus.jackson.util.TextBuffer;

// Referenced classes of package org.codehaus.jackson.impl:
//            JsonReadContext

public abstract class JsonParserBase extends JsonParser
{

    protected JsonParserBase(IOContext iocontext, int i)
    {
        _inputPtr = 0;
        _inputEnd = 0;
        _currInputProcessed = 0L;
        _currInputRow = 1;
        _currInputRowStart = 0;
        _tokenInputTotal = 0L;
        _tokenInputRow = 1;
        _tokenInputCol = 0;
        _tokenIncomplete = false;
        _nameCopyBuffer = null;
        _nameCopied = false;
        _byteArrayBuilder = null;
        _ioContext = iocontext;
        _features = i;
        _textBuffer = iocontext.constructTextBuffer();
        _parsingContext = JsonReadContext.createRootContext(_tokenInputRow, _tokenInputCol);
    }

    protected static final String _getCharDesc(int i)
    {
        char c = (char)i;
        String s;
        if(Character.isISOControl(c))
            s = (new StringBuilder()).append("(CTRL-CHAR, code ").append(i).append(")").toString();
        else
        if(i > 255)
            s = (new StringBuilder()).append("'").append(c).append("' (code ").append(i).append(" / 0x").append(Integer.toHexString(i)).append(")").toString();
        else
            s = (new StringBuilder()).append("'").append(c).append("' (code ").append(i).append(")").toString();
        return s;
    }

    protected abstract void _closeInput()
        throws IOException;

    protected final JsonParseException _constructError(String s)
    {
        return new JsonParseException(s, getCurrentLocation());
    }

    protected final JsonParseException _constructError(String s, Throwable throwable)
    {
        return new JsonParseException(s, getCurrentLocation(), throwable);
    }

    protected abstract byte[] _decodeBase64(Base64Variant base64variant)
        throws IOException, JsonParseException;

    protected abstract void _finishString()
        throws IOException, JsonParseException;

    public ByteArrayBuilder _getByteArrayBuilder()
    {
        if(_byteArrayBuilder == null)
            _byteArrayBuilder = new ByteArrayBuilder();
        else
            _byteArrayBuilder.reset();
        return _byteArrayBuilder;
    }

    protected void _handleEOF()
        throws JsonParseException
    {
        if(!_parsingContext.inRoot())
            _reportInvalidEOF((new StringBuilder()).append(": expected close marker for ").append(_parsingContext.getTypeDesc()).append(" (from ").append(_parsingContext.getStartLocation(_ioContext.getSourceReference())).append(")").toString());
    }

    protected void _releaseBuffers()
        throws IOException
    {
        _textBuffer.releaseBuffers();
        char ac[] = _nameCopyBuffer;
        if(ac != null)
        {
            _nameCopyBuffer = null;
            _ioContext.releaseNameCopyBuffer(ac);
        }
    }

    protected final void _reportError(String s)
        throws JsonParseException
    {
        throw _constructError(s);
    }

    protected void _reportInvalidEOF()
        throws JsonParseException
    {
        _reportInvalidEOF((new StringBuilder()).append(" in ").append(_currToken).toString());
    }

    protected void _reportInvalidEOF(String s)
        throws JsonParseException
    {
        _reportError((new StringBuilder()).append("Unexpected end-of-input").append(s).toString());
    }

    protected void _reportMismatchedEndMarker(int i, char c)
        throws JsonParseException
    {
        String s = (new StringBuilder()).append("").append(_parsingContext.getStartLocation(_ioContext.getSourceReference())).toString();
        _reportError((new StringBuilder()).append("Unexpected close marker '").append((char)i).append("': expected '").append(c).append("' (for ").append(_parsingContext.getTypeDesc()).append(" starting at ").append(s).append(")").toString());
    }

    protected void _reportUnexpectedChar(int i, String s)
        throws JsonParseException
    {
        String s1 = (new StringBuilder()).append("Unexpected character (").append(_getCharDesc(i)).append(")").toString();
        if(s != null)
            s1 = (new StringBuilder()).append(s1).append(": ").append(s).toString();
        _reportError(s1);
    }

    protected final void _throwInternal()
    {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }

    protected void _throwInvalidSpace(int i)
        throws JsonParseException
    {
        char c = (char)i;
        _reportError((new StringBuilder()).append("Illegal character (").append(_getCharDesc(c)).append("): only regular white space (\\r, \\n, \\t) is allowed between tokens").toString());
    }

    protected void _throwUnquotedSpace(int i, String s)
        throws JsonParseException
    {
        char c = (char)i;
        _reportError((new StringBuilder()).append("Illegal unquoted character (").append(_getCharDesc(c)).append("): has to be escaped using backslash to be included in ").append(s).toString());
    }

    protected final void _wrapError(String s, Throwable throwable)
        throws JsonParseException
    {
        throw _constructError(s, throwable);
    }

    public void close()
        throws IOException
    {
        _closed = true;
        _closeInput();
        _releaseBuffers();
    }

    public final byte[] getBinaryValue(Base64Variant base64variant)
        throws IOException, JsonParseException
    {
        if(_currToken != JsonToken.VALUE_STRING)
            _reportError((new StringBuilder()).append("Current token (").append(_currToken).append(") not VALUE_STRING, can not access as binary").toString());
        if(_tokenIncomplete)
        {
            try
            {
                _binaryValue = _decodeBase64(base64variant);
            }
            catch(IllegalArgumentException illegalargumentexception)
            {
                throw _constructError((new StringBuilder()).append("Failed to decode VALUE_STRING as base64 (").append(base64variant).append("): ").append(illegalargumentexception.getMessage()).toString());
            }
            _tokenIncomplete = false;
        }
        return _binaryValue;
    }

    public JsonLocation getCurrentLocation()
    {
        int i = 1 + (_inputPtr - _currInputRowStart);
        return new JsonLocation(_ioContext.getSourceReference(), (_currInputProcessed + (long)_inputPtr) - 1L, _currInputRow, i);
    }

    public String getCurrentName()
        throws IOException, JsonParseException
    {
        return _parsingContext.getCurrentName();
    }

    public volatile JsonStreamContext getParsingContext()
    {
        return getParsingContext();
    }

    public JsonReadContext getParsingContext()
    {
        return _parsingContext;
    }

    public String getText()
        throws IOException, JsonParseException
    {
        if(_currToken == null) goto _L2; else goto _L1
_L1:
        class _cls1
        {

            static final int $SwitchMap$org$codehaus$jackson$JsonToken[];

            static 
            {
                $SwitchMap$org$codehaus$jackson$JsonToken = new int[JsonToken.values().length];
                NoSuchFieldError nosuchfielderror7;
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.START_OBJECT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.START_ARRAY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.END_OBJECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.END_ARRAY.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_STRING.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                $SwitchMap$org$codehaus$jackson$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 8;
_L2:
                return;
                nosuchfielderror7;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.org.codehaus.jackson.JsonToken[_currToken.ordinal()];
        JVM INSTR tableswitch 5 8: default 48
    //                   5 58
    //                   6 69
    //                   7 85
    //                   8 85;
           goto _L3 _L4 _L5 _L6 _L6
_L3:
        String s = _currToken.asString();
_L8:
        return s;
_L4:
        s = _parsingContext.getCurrentName();
        continue; /* Loop/switch isn't completed */
_L5:
        if(_tokenIncomplete)
        {
            _tokenIncomplete = false;
            _finishString();
        }
_L6:
        s = _textBuffer.contentsAsString();
        continue; /* Loop/switch isn't completed */
_L2:
        s = null;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public char[] getTextCharacters()
        throws IOException, JsonParseException
    {
        if(_currToken == null) goto _L2; else goto _L1
_L1:
        _cls1..SwitchMap.org.codehaus.jackson.JsonToken[_currToken.ordinal()];
        JVM INSTR tableswitch 5 8: default 48
    //                   5 58
    //                   6 140
    //                   7 156
    //                   8 156;
           goto _L3 _L4 _L5 _L6 _L6
_L3:
        char ac[] = _currToken.asCharArray();
_L13:
        return ac;
_L4:
        if(_nameCopied) goto _L8; else goto _L7
_L7:
        String s;
        int i;
        s = _parsingContext.getCurrentName();
        i = s.length();
        if(_nameCopyBuffer != null) goto _L10; else goto _L9
_L9:
        _nameCopyBuffer = _ioContext.allocNameCopyBuffer(i);
_L11:
        s.getChars(0, i, _nameCopyBuffer, 0);
        _nameCopied = true;
_L8:
        ac = _nameCopyBuffer;
        continue; /* Loop/switch isn't completed */
_L10:
        if(_nameCopyBuffer.length < i)
            _nameCopyBuffer = new char[i];
        if(true) goto _L11; else goto _L5
_L5:
        if(_tokenIncomplete)
        {
            _tokenIncomplete = false;
            _finishString();
        }
_L6:
        ac = _textBuffer.getTextBuffer();
        continue; /* Loop/switch isn't completed */
_L2:
        ac = null;
        if(true) goto _L13; else goto _L12
_L12:
    }

    public int getTextLength()
        throws IOException, JsonParseException
    {
        if(_currToken == null) goto _L2; else goto _L1
_L1:
        _cls1..SwitchMap.org.codehaus.jackson.JsonToken[_currToken.ordinal()];
        JVM INSTR tableswitch 5 8: default 48
    //                   5 59
    //                   6 73
    //                   7 89
    //                   8 89;
           goto _L3 _L4 _L5 _L6 _L6
_L3:
        int i = _currToken.asCharArray().length;
_L8:
        return i;
_L4:
        i = _parsingContext.getCurrentName().length();
        continue; /* Loop/switch isn't completed */
_L5:
        if(_tokenIncomplete)
        {
            _tokenIncomplete = false;
            _finishString();
        }
_L6:
        i = _textBuffer.size();
        continue; /* Loop/switch isn't completed */
_L2:
        i = 0;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public int getTextOffset()
        throws IOException, JsonParseException
    {
        if(_currToken == null) goto _L2; else goto _L1
_L1:
        _cls1..SwitchMap.org.codehaus.jackson.JsonToken[_currToken.ordinal()];
        JVM INSTR tableswitch 5 8: default 48
    //                   5 52
    //                   6 57
    //                   7 73
    //                   8 73;
           goto _L2 _L3 _L4 _L5 _L5
_L2:
        int i = 0;
_L7:
        return i;
_L3:
        i = 0;
        continue; /* Loop/switch isn't completed */
_L4:
        if(_tokenIncomplete)
        {
            _tokenIncomplete = false;
            _finishString();
        }
_L5:
        i = _textBuffer.getTextOffset();
        if(true) goto _L7; else goto _L6
_L6:
    }

    public final long getTokenCharacterOffset()
    {
        return _tokenInputTotal;
    }

    public final int getTokenColumnNr()
    {
        return 1 + _tokenInputCol;
    }

    public final int getTokenLineNr()
    {
        return _tokenInputRow;
    }

    public JsonLocation getTokenLocation()
    {
        return new JsonLocation(_ioContext.getSourceReference(), getTokenCharacterOffset(), getTokenLineNr(), getTokenColumnNr());
    }

    public boolean isClosed()
    {
        return _closed;
    }

    protected abstract boolean loadMore()
        throws IOException;

    protected final void loadMoreGuaranteed()
        throws IOException
    {
        if(!loadMore())
            _reportInvalidEOF();
    }

    public abstract JsonToken nextToken()
        throws IOException, JsonParseException;

    public final JsonToken nextValue()
        throws IOException, JsonParseException
    {
        JsonToken jsontoken = nextToken();
        if(jsontoken == JsonToken.FIELD_NAME)
            jsontoken = nextToken();
        return jsontoken;
    }

    public void skipChildren()
        throws IOException, JsonParseException
    {
        if(_currToken == JsonToken.START_OBJECT || _currToken == JsonToken.START_ARRAY)
        {
            int i = 1;
label0:
            do
                do
                {
                    JsonToken jsontoken = nextToken();
                    if(jsontoken == null)
                    {
                        _handleEOF();
                        break label0;
                    }
                    switch(_cls1..SwitchMap.org.codehaus.jackson.JsonToken[jsontoken.ordinal()])
                    {
                    default:
                        break;

                    case 3: // '\003'
                    case 4: // '\004'
                        continue label0;

                    case 1: // '\001'
                    case 2: // '\002'
                        i++;
                        break;
                    }
                } while(true);
            while(--i != 0);
        }
    }

    static final int INT_ASTERISK = 42;
    static final int INT_BACKSLASH = 92;
    static final int INT_COLON = 58;
    static final int INT_COMMA = 44;
    static final int INT_CR = 13;
    static final int INT_LBRACKET = 91;
    static final int INT_LCURLY = 123;
    static final int INT_LF = 10;
    static final int INT_QUOTE = 34;
    static final int INT_RBRACKET = 93;
    static final int INT_RCURLY = 125;
    static final int INT_SLASH = 47;
    static final int INT_SPACE = 32;
    static final int INT_TAB = 9;
    static final int INT_b = 98;
    static final int INT_f = 102;
    static final int INT_n = 110;
    static final int INT_r = 114;
    static final int INT_t = 116;
    static final int INT_u = 117;
    protected byte _binaryValue[];
    ByteArrayBuilder _byteArrayBuilder;
    protected boolean _closed;
    protected long _currInputProcessed;
    protected int _currInputRow;
    protected int _currInputRowStart;
    protected int _inputEnd;
    protected int _inputPtr;
    protected final IOContext _ioContext;
    protected boolean _nameCopied;
    protected char _nameCopyBuffer[];
    protected JsonToken _nextToken;
    protected JsonReadContext _parsingContext;
    protected final TextBuffer _textBuffer;
    protected boolean _tokenIncomplete;
    protected int _tokenInputCol;
    protected int _tokenInputRow;
    protected long _tokenInputTotal;
}
