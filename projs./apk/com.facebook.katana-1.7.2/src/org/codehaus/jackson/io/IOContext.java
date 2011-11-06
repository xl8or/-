// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.io;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.util.BufferRecycler;
import org.codehaus.jackson.util.TextBuffer;

public final class IOContext
{

    public IOContext(BufferRecycler bufferrecycler, Object obj, boolean flag)
    {
        _readIOBuffer = null;
        _writeIOBuffer = null;
        _tokenBuffer = null;
        _concatBuffer = null;
        _nameCopyBuffer = null;
        _bufferRecycler = bufferrecycler;
        _sourceRef = obj;
        _managedResource = flag;
    }

    public char[] allocConcatBuffer()
    {
        if(_concatBuffer != null)
        {
            throw new IllegalStateException("Trying to call allocConcatBuffer() second time");
        } else
        {
            _concatBuffer = _bufferRecycler.allocCharBuffer(org.codehaus.jackson.util.BufferRecycler.CharBufferType.CONCAT_BUFFER);
            return _concatBuffer;
        }
    }

    public char[] allocNameCopyBuffer(int i)
    {
        if(_nameCopyBuffer != null)
        {
            throw new IllegalStateException("Trying to call allocNameCopyBuffer() second time");
        } else
        {
            _nameCopyBuffer = _bufferRecycler.allocCharBuffer(org.codehaus.jackson.util.BufferRecycler.CharBufferType.NAME_COPY_BUFFER, i);
            return _nameCopyBuffer;
        }
    }

    public byte[] allocReadIOBuffer()
    {
        if(_readIOBuffer != null)
        {
            throw new IllegalStateException("Trying to call allocReadIOBuffer() second time");
        } else
        {
            _readIOBuffer = _bufferRecycler.allocByteBuffer(org.codehaus.jackson.util.BufferRecycler.ByteBufferType.READ_IO_BUFFER);
            return _readIOBuffer;
        }
    }

    public char[] allocTokenBuffer()
    {
        if(_tokenBuffer != null)
        {
            throw new IllegalStateException("Trying to call allocTokenBuffer() second time");
        } else
        {
            _tokenBuffer = _bufferRecycler.allocCharBuffer(org.codehaus.jackson.util.BufferRecycler.CharBufferType.TOKEN_BUFFER);
            return _tokenBuffer;
        }
    }

    public byte[] allocWriteIOBuffer()
    {
        if(_writeIOBuffer != null)
        {
            throw new IllegalStateException("Trying to call allocWriteIOBuffer() second time");
        } else
        {
            _writeIOBuffer = _bufferRecycler.allocByteBuffer(org.codehaus.jackson.util.BufferRecycler.ByteBufferType.WRITE_IO_BUFFER);
            return _writeIOBuffer;
        }
    }

    public TextBuffer constructTextBuffer()
    {
        return new TextBuffer(_bufferRecycler);
    }

    public JsonEncoding getEncoding()
    {
        return _encoding;
    }

    public Object getSourceReference()
    {
        return _sourceRef;
    }

    public boolean isResourceManaged()
    {
        return _managedResource;
    }

    public void releaseConcatBuffer(char ac[])
    {
        if(ac != null)
        {
            if(ac != _concatBuffer)
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            _concatBuffer = null;
            _bufferRecycler.releaseCharBuffer(org.codehaus.jackson.util.BufferRecycler.CharBufferType.CONCAT_BUFFER, ac);
        }
    }

    public void releaseNameCopyBuffer(char ac[])
    {
        if(ac != null)
        {
            if(ac != _nameCopyBuffer)
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            _nameCopyBuffer = null;
            _bufferRecycler.releaseCharBuffer(org.codehaus.jackson.util.BufferRecycler.CharBufferType.NAME_COPY_BUFFER, ac);
        }
    }

    public void releaseReadIOBuffer(byte abyte0[])
    {
        if(abyte0 != null)
        {
            if(abyte0 != _readIOBuffer)
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            _readIOBuffer = null;
            _bufferRecycler.releaseByteBuffer(org.codehaus.jackson.util.BufferRecycler.ByteBufferType.READ_IO_BUFFER, abyte0);
        }
    }

    public void releaseTokenBuffer(char ac[])
    {
        if(ac != null)
        {
            if(ac != _tokenBuffer)
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            _tokenBuffer = null;
            _bufferRecycler.releaseCharBuffer(org.codehaus.jackson.util.BufferRecycler.CharBufferType.TOKEN_BUFFER, ac);
        }
    }

    public void releaseWriteIOBuffer(byte abyte0[])
    {
        if(abyte0 != null)
        {
            if(abyte0 != _writeIOBuffer)
                throw new IllegalArgumentException("Trying to release buffer not owned by the context");
            _writeIOBuffer = null;
            _bufferRecycler.releaseByteBuffer(org.codehaus.jackson.util.BufferRecycler.ByteBufferType.WRITE_IO_BUFFER, abyte0);
        }
    }

    public void setEncoding(JsonEncoding jsonencoding)
    {
        _encoding = jsonencoding;
    }

    final BufferRecycler _bufferRecycler;
    protected char _concatBuffer[];
    protected JsonEncoding _encoding;
    protected final boolean _managedResource;
    protected char _nameCopyBuffer[];
    protected byte _readIOBuffer[];
    final Object _sourceRef;
    protected char _tokenBuffer[];
    protected byte _writeIOBuffer[];
}
