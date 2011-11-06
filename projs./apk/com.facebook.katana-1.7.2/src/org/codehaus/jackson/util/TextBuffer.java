// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.util;

import java.math.BigDecimal;
import java.util.ArrayList;

// Referenced classes of package org.codehaus.jackson.util:
//            BufferRecycler

public final class TextBuffer
{

    public TextBuffer(BufferRecycler bufferrecycler)
    {
        _hasSegments = false;
        _allocator = bufferrecycler;
    }

    private final char[] allocBuffer(int i)
    {
        return _allocator.allocCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, i);
    }

    private char[] buildResultArray()
    {
        char ac1[];
        if(_resultString != null)
            ac1 = _resultString.toCharArray();
        else
        if(_inputStart >= 0)
        {
            if(_inputLen < 1)
            {
                ac1 = NO_CHARS;
            } else
            {
                ac1 = new char[_inputLen];
                System.arraycopy(_inputBuffer, _inputStart, ac1, 0, _inputLen);
            }
        } else
        {
            int i = size();
            if(i < 1)
            {
                ac1 = NO_CHARS;
            } else
            {
                char ac[] = new char[i];
                int j;
                if(_segments != null)
                {
                    int k = _segments.size();
                    int l = 0;
                    int i1;
                    int k1;
                    for(i1 = 0; l < k; i1 = k1)
                    {
                        char ac2[] = (char[])(char[])_segments.get(l);
                        int j1 = ac2.length;
                        System.arraycopy(ac2, 0, ac, i1, j1);
                        k1 = i1 + j1;
                        l++;
                    }

                    j = i1;
                } else
                {
                    j = 0;
                }
                System.arraycopy(_currentSegment, 0, ac, j, _currentSize);
                ac1 = ac;
            }
        }
        return ac1;
    }

    private final void clearSegments()
    {
        _hasSegments = false;
        _currentSegment = (char[])_segments.get(_segments.size() - 1);
        _segments.clear();
        _segmentSize = 0;
        _currentSize = 0;
    }

    private void expand(int i)
    {
        if(_segments == null)
            _segments = new ArrayList();
        char ac[] = _currentSegment;
        _hasSegments = true;
        _segments.add(ac);
        _segmentSize = _segmentSize + ac.length;
        int j = ac.length;
        int k = j >> 1;
        if(k < i)
            k = i;
        char ac1[] = new char[j + k];
        _currentSize = 0;
        _currentSegment = ac1;
    }

    private void unshare(int i)
    {
        int j = _inputLen;
        _inputLen = 0;
        char ac[] = _inputBuffer;
        _inputBuffer = null;
        int k = _inputStart;
        _inputStart = -1;
        int l = j + i;
        if(_currentSegment == null || l > _currentSegment.length)
            _currentSegment = allocBuffer(l);
        if(j > 0)
            System.arraycopy(ac, k, _currentSegment, 0, j);
        _segmentSize = 0;
        _currentSize = j;
    }

    public void append(char ac[], int i, int j)
    {
        if(_inputStart >= 0)
            unshare(j);
        _resultString = null;
        _resultArray = null;
        char ac1[] = _currentSegment;
        int k = ac1.length - _currentSize;
        if(k >= j)
        {
            System.arraycopy(ac, i, ac1, _currentSize, j);
            _currentSize = j + _currentSize;
        } else
        {
            int l;
            int i1;
            if(k > 0)
            {
                System.arraycopy(ac, i, ac1, _currentSize, k);
                int j1 = i + k;
                int k1 = j - k;
                i1 = j1;
                l = k1;
            } else
            {
                l = j;
                i1 = i;
            }
            expand(l);
            System.arraycopy(ac, i1, _currentSegment, 0, l);
            _currentSize = l;
        }
    }

    public char[] contentsAsArray()
    {
        char ac[] = _resultArray;
        if(ac == null)
        {
            ac = buildResultArray();
            _resultArray = ac;
        }
        return ac;
    }

    public BigDecimal contentsAsDecimal()
        throws NumberFormatException
    {
        BigDecimal bigdecimal;
        if(_resultArray != null)
            bigdecimal = new BigDecimal(_resultArray);
        else
        if(_inputStart >= 0)
            bigdecimal = new BigDecimal(_inputBuffer, _inputStart, _inputLen);
        else
        if(_segmentSize == 0)
            bigdecimal = new BigDecimal(_currentSegment, 0, _currentSize);
        else
            bigdecimal = new BigDecimal(contentsAsArray());
        return bigdecimal;
    }

    public double contentsAsDouble()
        throws NumberFormatException
    {
        return Double.parseDouble(contentsAsString());
    }

    public String contentsAsString()
    {
        if(_resultString != null) goto _L2; else goto _L1
_L1:
        if(_resultArray == null) goto _L4; else goto _L3
_L3:
        _resultString = new String(_resultArray);
_L2:
        String s = _resultString;
_L6:
        return s;
_L4:
        if(_inputStart < 0)
            break; /* Loop/switch isn't completed */
        if(_inputLen < 1)
        {
            s = "";
            _resultString = s;
        } else
        {
            _resultString = new String(_inputBuffer, _inputStart, _inputLen);
            continue; /* Loop/switch isn't completed */
        }
        if(true) goto _L6; else goto _L5
_L5:
        int i = _segmentSize;
        int j = _currentSize;
        if(i == 0)
        {
            String s1;
            if(j == 0)
                s1 = "";
            else
                s1 = new String(_currentSegment, 0, j);
            _resultString = s1;
        } else
        {
            StringBuilder stringbuilder = new StringBuilder(i + j);
            if(_segments != null)
            {
                int k = _segments.size();
                for(int l = 0; l < k; l++)
                {
                    char ac[] = (char[])_segments.get(l);
                    stringbuilder.append(ac, 0, ac.length);
                }

            }
            stringbuilder.append(_currentSegment, 0, _currentSize);
            _resultString = stringbuilder.toString();
        }
        if(true) goto _L2; else goto _L7
_L7:
    }

    public char[] emptyAndGetCurrentSegment()
    {
        resetWithEmpty();
        char ac[] = _currentSegment;
        if(ac == null)
        {
            ac = allocBuffer(0);
            _currentSegment = ac;
        }
        return ac;
    }

    public void ensureNotShared()
    {
        if(_inputStart >= 0)
            unshare(16);
    }

    public char[] expandCurrentSegment()
    {
        char ac[] = _currentSegment;
        int i = ac.length;
        _currentSegment = new char[i + i];
        System.arraycopy(ac, 0, _currentSegment, 0, i);
        return _currentSegment;
    }

    public char[] finishCurrentSegment()
    {
        if(_segments == null)
            _segments = new ArrayList();
        _hasSegments = true;
        _segments.add(_currentSegment);
        int i = _currentSegment.length;
        _segmentSize = i + _segmentSize;
        char ac[] = new char[i + (i >> 1)];
        _currentSize = 0;
        _currentSegment = ac;
        return ac;
    }

    public char[] getCurrentSegment()
    {
        if(_inputStart < 0) goto _L2; else goto _L1
_L1:
        unshare(1);
_L4:
        return _currentSegment;
_L2:
        char ac[] = _currentSegment;
        if(ac == null)
            _currentSegment = allocBuffer(0);
        else
        if(_currentSize >= ac.length)
            expand(1);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getCurrentSegmentSize()
    {
        return _currentSize;
    }

    public char[] getTextBuffer()
    {
        char ac[];
        if(_inputStart >= 0)
            ac = _inputBuffer;
        else
        if(!_hasSegments)
            ac = _currentSegment;
        else
            ac = contentsAsArray();
        return ac;
    }

    public int getTextOffset()
    {
        int i;
        if(_inputStart >= 0)
            i = _inputStart;
        else
            i = 0;
        return i;
    }

    public void releaseBuffers()
    {
        if(_allocator != null && _currentSegment != null)
        {
            resetWithEmpty();
            char ac[] = _currentSegment;
            _currentSegment = null;
            _allocator.releaseCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, ac);
        }
    }

    public void resetWithCopy(char ac[], int i, int j)
    {
        _inputBuffer = null;
        _inputStart = -1;
        _inputLen = 0;
        _resultString = null;
        _resultArray = null;
        if(!_hasSegments) goto _L2; else goto _L1
_L1:
        clearSegments();
_L4:
        _segmentSize = 0;
        _currentSize = 0;
        append(ac, i, j);
        return;
_L2:
        if(_currentSegment == null)
            _currentSegment = allocBuffer(j);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void resetWithEmpty()
    {
        _inputBuffer = null;
        _inputStart = -1;
        _inputLen = 0;
        _resultString = null;
        _resultArray = null;
        if(_hasSegments)
            clearSegments();
        _currentSize = 0;
    }

    public void resetWithShared(char ac[], int i, int j)
    {
        _resultString = null;
        _resultArray = null;
        _inputBuffer = ac;
        _inputStart = i;
        _inputLen = j;
        if(_hasSegments)
            clearSegments();
    }

    public void setCurrentLength(int i)
    {
        _currentSize = i;
    }

    public int size()
    {
        int i;
        if(_inputStart >= 0)
            i = _inputLen;
        else
            i = _segmentSize + _currentSize;
        return i;
    }

    public String toString()
    {
        return contentsAsString();
    }

    static final char NO_CHARS[] = new char[0];
    private final BufferRecycler _allocator;
    private char _currentSegment[];
    private int _currentSize;
    private boolean _hasSegments;
    private char _inputBuffer[];
    private int _inputLen;
    private int _inputStart;
    private char _resultArray[];
    private String _resultString;
    private int _segmentSize;
    private ArrayList _segments;

}
