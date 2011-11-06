// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.io.IOContext;

// Referenced classes of package org.codehaus.jackson.impl:
//            JsonNumericParserBase

public abstract class StreamBasedParserBase extends JsonNumericParserBase
{

    protected StreamBasedParserBase(IOContext iocontext, int i, InputStream inputstream, byte abyte0[], int j, int k, boolean flag)
    {
        super(iocontext, i);
        _inputStream = inputstream;
        _inputBuffer = abyte0;
        _inputPtr = j;
        _inputEnd = k;
        _bufferRecyclable = flag;
    }

    protected void _closeInput()
        throws IOException
    {
        if(_inputStream != null)
        {
            if(_ioContext.isResourceManaged() || isFeatureEnabled(org.codehaus.jackson.JsonParser.Feature.AUTO_CLOSE_SOURCE))
                _inputStream.close();
            _inputStream = null;
        }
    }

    protected void _releaseBuffers()
        throws IOException
    {
        super._releaseBuffers();
        if(_bufferRecyclable)
        {
            byte abyte0[] = _inputBuffer;
            if(abyte0 != null)
            {
                _inputBuffer = null;
                _ioContext.releaseReadIOBuffer(abyte0);
            }
        }
    }

    protected final boolean loadMore()
        throws IOException
    {
        _currInputProcessed = _currInputProcessed + (long)_inputEnd;
        _currInputRowStart = _currInputRowStart - _inputEnd;
        if(_inputStream == null) goto _L2; else goto _L1
_L1:
        int i = _inputStream.read(_inputBuffer, 0, _inputBuffer.length);
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

    protected boolean _bufferRecyclable;
    protected byte _inputBuffer[];
    protected InputStream _inputStream;
}
