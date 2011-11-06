// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.*;
import org.codehaus.jackson.*;
import org.codehaus.jackson.io.*;
import org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;

// Referenced classes of package org.codehaus.jackson.impl:
//            Utf8StreamParser, ReaderBasedParser

public final class ByteSourceBootstrapper
{

    public ByteSourceBootstrapper(IOContext iocontext, InputStream inputstream)
    {
        _bigEndian = true;
        _bytesPerChar = 0;
        _context = iocontext;
        _in = inputstream;
        _inputBuffer = iocontext.allocReadIOBuffer();
        _inputPtr = 0;
        _inputEnd = 0;
        _inputProcessed = 0;
        _bufferRecyclable = true;
    }

    public ByteSourceBootstrapper(IOContext iocontext, byte abyte0[], int i, int j)
    {
        _bigEndian = true;
        _bytesPerChar = 0;
        _context = iocontext;
        _in = null;
        _inputBuffer = abyte0;
        _inputPtr = i;
        _inputEnd = i + j;
        _inputProcessed = -i;
        _bufferRecyclable = false;
    }

    private boolean checkUTF16(int i)
    {
        boolean flag;
        if((0xff00 & i) == 0)
        {
            _bigEndian = true;
        } else
        {
label0:
            {
                if((i & 0xff) != 0)
                    break label0;
                _bigEndian = false;
            }
        }
        _bytesPerChar = 2;
        flag = true;
_L2:
        return flag;
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private boolean checkUTF32(int i)
        throws IOException
    {
        boolean flag;
        if(i >> 8 == 0)
            _bigEndian = true;
        else
        if((0xffffff & i) == 0)
            _bigEndian = false;
        else
        if((0xff00ffff & i) == 0)
        {
            reportWeirdUCS4("3412");
        } else
        {
label0:
            {
                if((0xffff00ff & i) != 0)
                    break label0;
                reportWeirdUCS4("2143");
            }
        }
        _bytesPerChar = 4;
        flag = true;
_L2:
        return flag;
        flag = false;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private boolean handleBOM(int i)
        throws IOException
    {
        i;
        JVM INSTR lookupswitch 4: default 44
    //                   -16842752: 135
    //                   -131072: 104
    //                   65279: 79
    //                   65534: 129;
           goto _L1 _L2 _L3 _L4 _L5
_L2:
        break MISSING_BLOCK_LABEL_135;
_L1:
        int j = i >>> 16;
        boolean flag;
        if(j == 65279)
        {
            _inputPtr = 2 + _inputPtr;
            _bytesPerChar = 2;
            _bigEndian = true;
            flag = true;
        } else
        if(j == 65534)
        {
            _inputPtr = 2 + _inputPtr;
            _bytesPerChar = 2;
            _bigEndian = false;
            flag = true;
        } else
        if(i >>> 8 == 0xefbbbf)
        {
            _inputPtr = 3 + _inputPtr;
            _bytesPerChar = 1;
            _bigEndian = true;
            flag = true;
        } else
        {
            flag = false;
        }
_L6:
        return flag;
_L4:
        _bigEndian = true;
        _inputPtr = 4 + _inputPtr;
        _bytesPerChar = 4;
        flag = true;
        continue; /* Loop/switch isn't completed */
_L3:
        _inputPtr = 4 + _inputPtr;
        _bytesPerChar = 4;
        _bigEndian = false;
        flag = true;
        if(true) goto _L6; else goto _L5
_L5:
        reportWeirdUCS4("2143");
        reportWeirdUCS4("3412");
          goto _L1
    }

    private void reportWeirdUCS4(String s)
        throws IOException
    {
        throw new CharConversionException((new StringBuilder()).append("Unsupported UCS-4 endianness (").append(s).append(") detected").toString());
    }

    public JsonParser constructParser(int i, ObjectCodec objectcodec, BytesToNameCanonicalizer bytestonamecanonicalizer, CharsToNameCanonicalizer charstonamecanonicalizer)
        throws IOException, JsonParseException
    {
        Object obj;
        if(detectEncoding() == JsonEncoding.UTF8)
            obj = new Utf8StreamParser(_context, i, _in, objectcodec, bytestonamecanonicalizer.makeChild(), _inputBuffer, _inputPtr, _inputEnd, _bufferRecyclable);
        else
            obj = new ReaderBasedParser(_context, i, constructReader(), objectcodec, charstonamecanonicalizer.makeChild());
        return ((JsonParser) (obj));
    }

    public Reader constructReader()
        throws IOException
    {
        JsonEncoding jsonencoding = _context.getEncoding();
        class _cls1
        {

            static final int $SwitchMap$org$codehaus$jackson$JsonEncoding[];

            static 
            {
                $SwitchMap$org$codehaus$jackson$JsonEncoding = new int[JsonEncoding.values().length];
                NoSuchFieldError nosuchfielderror4;
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonEncoding[JsonEncoding.UTF32_BE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonEncoding[JsonEncoding.UTF32_LE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonEncoding[JsonEncoding.UTF16_BE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$org$codehaus$jackson$JsonEncoding[JsonEncoding.UTF16_LE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                $SwitchMap$org$codehaus$jackson$JsonEncoding[JsonEncoding.UTF8.ordinal()] = 5;
_L2:
                return;
                nosuchfielderror4;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.org.codehaus.jackson.JsonEncoding[jsonencoding.ordinal()];
        JVM INSTR tableswitch 1 5: default 52
    //                   1 62
    //                   2 62
    //                   3 104
    //                   4 104
    //                   5 189;
           goto _L1 _L2 _L2 _L3 _L3 _L4
_L1:
        throw new RuntimeException("Internal error");
_L2:
        Object obj1 = new UTF32Reader(_context, _in, _inputBuffer, _inputPtr, _inputEnd, _context.getEncoding().isBigEndian());
_L6:
        return ((Reader) (obj1));
_L3:
        InputStream inputstream;
        Object obj;
        inputstream = _in;
        if(inputstream != null)
            break; /* Loop/switch isn't completed */
        obj = new ByteArrayInputStream(_inputBuffer, _inputPtr, _inputEnd);
_L7:
        obj1 = new InputStreamReader(((InputStream) (obj)), jsonencoding.getJavaName());
        if(true) goto _L6; else goto _L5
_L5:
        if(_inputPtr < _inputEnd)
            obj = new MergedStream(_context, inputstream, _inputBuffer, _inputPtr, _inputEnd);
        else
            obj = inputstream;
          goto _L7
          goto _L6
_L4:
        throw new RuntimeException("Internal error: should be using Utf8StreamParser directly");
    }

    public JsonEncoding detectEncoding()
        throws IOException, JsonParseException
    {
        boolean flag = false;
        if(!ensureLoaded(4)) goto _L2; else goto _L1
_L1:
        int i = _inputBuffer[_inputPtr] << 24 | (0xff & _inputBuffer[1 + _inputPtr]) << 16 | (0xff & _inputBuffer[2 + _inputPtr]) << 8 | 0xff & _inputBuffer[3 + _inputPtr];
        if(!handleBOM(i)) goto _L4; else goto _L3
_L3:
        flag = true;
_L10:
        if(flag) goto _L6; else goto _L5
_L5:
        JsonEncoding jsonencoding = JsonEncoding.UTF8;
_L8:
        _context.setEncoding(jsonencoding);
        return jsonencoding;
_L4:
        if(checkUTF32(i))
            flag = true;
        else
        if(checkUTF16(i >>> 16))
            flag = true;
        continue; /* Loop/switch isn't completed */
_L2:
        if(ensureLoaded(2) && checkUTF16((0xff & _inputBuffer[_inputPtr]) << 8 | 0xff & _inputBuffer[1 + _inputPtr]))
            flag = true;
        continue; /* Loop/switch isn't completed */
_L6:
        if(_bytesPerChar == 2)
        {
            if(_bigEndian)
                jsonencoding = JsonEncoding.UTF16_BE;
            else
                jsonencoding = JsonEncoding.UTF16_LE;
            continue; /* Loop/switch isn't completed */
        }
        if(_bytesPerChar != 4)
            break; /* Loop/switch isn't completed */
        if(_bigEndian)
            jsonencoding = JsonEncoding.UTF32_BE;
        else
            jsonencoding = JsonEncoding.UTF32_LE;
        if(true) goto _L8; else goto _L7
_L7:
        throw new RuntimeException("Internal error");
        if(true) goto _L10; else goto _L9
_L9:
    }

    protected boolean ensureLoaded(int i)
        throws IOException
    {
        int j = _inputEnd - _inputPtr;
_L3:
        boolean flag;
        int k;
        if(j >= i)
            break MISSING_BLOCK_LABEL_85;
        if(_in == null)
            k = -1;
        else
            k = _in.read(_inputBuffer, _inputEnd, _inputBuffer.length - _inputEnd);
        if(k >= 1) goto _L2; else goto _L1
_L1:
        flag = false;
_L4:
        return flag;
_L2:
        _inputEnd = k + _inputEnd;
        j += k;
          goto _L3
        flag = true;
          goto _L4
    }

    boolean _bigEndian;
    private final boolean _bufferRecyclable;
    int _bytesPerChar;
    final IOContext _context;
    final InputStream _in;
    final byte _inputBuffer[];
    private int _inputEnd;
    protected int _inputProcessed;
    private int _inputPtr;
}
