// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.io.IOContext;

// Referenced classes of package org.codehaus.jackson.impl:
//            JsonNumericParserBase

public abstract class ReaderBasedParserBase extends JsonNumericParserBase
{

    protected ReaderBasedParserBase(IOContext iocontext, int i, Reader reader)
    {
        super(iocontext, i);
        _reader = reader;
        _inputBuffer = iocontext.allocTokenBuffer();
    }

    protected void _closeInput()
        throws IOException
    {
        if(_reader != null)
        {
            if(_ioContext.isResourceManaged() || isFeatureEnabled(org.codehaus.jackson.JsonParser.Feature.AUTO_CLOSE_SOURCE))
                _reader.close();
            _reader = null;
        }
    }

    protected void _releaseBuffers()
        throws IOException
    {
        super._releaseBuffers();
        char ac[] = _inputBuffer;
        if(ac != null)
        {
            _inputBuffer = null;
            _ioContext.releaseTokenBuffer(ac);
        }
    }

    protected char getNextChar(String s)
        throws IOException, JsonParseException
    {
        if(_inputPtr >= _inputEnd && !loadMore())
            _reportInvalidEOF(s);
        char ac[] = _inputBuffer;
        int i = _inputPtr;
        _inputPtr = i + 1;
        return ac[i];
    }

    protected final boolean loadMore()
        throws IOException
    {
        _currInputProcessed = _currInputProcessed + (long)_inputEnd;
        _currInputRowStart = _currInputRowStart - _inputEnd;
        if(_reader == null) goto _L2; else goto _L1
_L1:
        int i = _reader.read(_inputBuffer, 0, _inputBuffer.length);
        if(i <= 0) goto _L4; else goto _L3
_L3:
        boolean flag;
        _inputPtr = 0;
        _inputEnd = i;
        flag = true;
_L6:
        return flag;
_L4:
        _closeInput();
        if(i == 0)
            throw new IOException((new StringBuilder()).append("Reader returned 0 characters when trying to read ").append(_inputEnd).toString());
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected char _inputBuffer[];
    protected Reader _reader;
}
