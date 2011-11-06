// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.util.TextBuffer;

// Referenced classes of package org.codehaus.jackson.impl:
//            StreamBasedParserBase

public abstract class Utf8NumericParser extends StreamBasedParserBase
{

    public Utf8NumericParser(IOContext iocontext, int i, InputStream inputstream, byte abyte0[], int j, int k, boolean flag)
    {
        super(iocontext, i, inputstream, abyte0, j, k, flag);
    }

    protected final JsonToken parseNumberText(int i)
        throws IOException, JsonParseException
    {
        char ac[] = _textBuffer.emptyAndGetCurrentSegment();
        int j = 0;
        boolean flag;
        int k;
        int l;
        char ac1[];
        int i1;
        int j1;
        char ac2[];
        int k1;
        int l1;
        boolean flag1;
        int i2;
        char ac3[];
        int j2;
        boolean flag2;
        int k2;
        int l2;
        byte abyte0[];
        int i3;
        int j3;
        char ac4[];
        int k3;
        int l3;
        byte abyte1[];
        int i4;
        int j4;
        int k4;
        int l4;
        int i5;
        boolean flag3;
        int k5;
        int l5;
        int j6;
        int k6;
        int j7;
        if(i == 45)
            flag = true;
        else
            flag = false;
        int j5;
        byte abyte2[];
        int i6;
        byte abyte3[];
        int l6;
        int i7;
        int k7;
        byte abyte4[];
        int l7;
        if(flag)
        {
            int i8 = j + 1;
            ac[j] = '-';
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            byte abyte5[] = _inputBuffer;
            int j8 = _inputPtr;
            _inputPtr = j8 + 1;
            k = 0xff & abyte5[j8];
            j = i8;
        } else
        {
            k = i;
        }
        l = k;
        ac1 = ac;
        i1 = 0;
        if(l < 48)
            break MISSING_BLOCK_LABEL_918;
        if(l > 57)
        {
            j1 = l;
            ac2 = ac1;
            k1 = j;
            l1 = i1;
            flag1 = false;
        } else
        {
label0:
            {
                if(++i1 == 2 && ac1[j - 1] == '0')
                    reportInvalidNumber("Leading zeroes not allowed");
                if(j >= ac1.length)
                {
                    ac1 = _textBuffer.finishCurrentSegment();
                    j = 0;
                }
                k7 = j + 1;
                ac1[j] = (char)l;
                if(_inputPtr < _inputEnd || loadMore())
                    break label0;
                j1 = 0;
                l1 = i1;
                flag1 = true;
                ac2 = ac1;
                k1 = k7;
            }
        }
_L16:
        if(l1 == 0)
            reportInvalidNumber((new StringBuilder()).append("Missing integer part (next char ").append(_getCharDesc(j1)).append(")").toString());
        if(j1 != 46)
            break MISSING_BLOCK_LABEL_896;
        j6 = k1 + 1;
        ac2[k1] = (char)j1;
        k6 = 0;
        i2 = j1;
        ac3 = ac2;
        j2 = j6;
_L11:
        if(_inputPtr < _inputEnd || loadMore()) goto _L2; else goto _L1
_L1:
        flag1 = true;
_L10:
        if(k6 == 0)
            reportUnexpectedNumberChar(i2, "Decimal point not followed by a digit");
        j7 = k6;
        flag2 = flag1;
        k2 = j7;
_L14:
        if(i2 != 101 && i2 != 69) goto _L4; else goto _L3
_L3:
        if(j2 >= ac3.length)
        {
            ac3 = _textBuffer.finishCurrentSegment();
            j2 = 0;
        }
        l2 = j2 + 1;
        ac3[j2] = (char)i2;
        if(_inputPtr >= _inputEnd)
            loadMoreGuaranteed();
        abyte0 = _inputBuffer;
        i3 = _inputPtr;
        _inputPtr = i3 + 1;
        j3 = 0xff & abyte0[i3];
        if(j3 == 45 || j3 == 43)
        {
            if(l2 >= ac3.length)
            {
                ac4 = _textBuffer.finishCurrentSegment();
                k3 = 0;
            } else
            {
                ac4 = ac3;
                k3 = l2;
            }
            l3 = k3 + 1;
            ac4[k3] = (char)j3;
            if(_inputPtr >= _inputEnd)
                loadMoreGuaranteed();
            abyte1 = _inputBuffer;
            i4 = _inputPtr;
            _inputPtr = i4 + 1;
            j4 = 0xff & abyte1[i4];
            k4 = l3;
            l4 = j4;
            i5 = 0;
        } else
        {
            ac4 = ac3;
            k4 = l2;
            l4 = j3;
            i5 = 0;
        }
        if(l4 > 57 || l4 < 48) goto _L6; else goto _L5
_L5:
        i5++;
        if(k4 >= ac4.length)
        {
            ac4 = _textBuffer.finishCurrentSegment();
            k4 = 0;
        }
        l5 = k4 + 1;
        ac4[k4] = (char)l4;
        if(_inputPtr < _inputEnd || loadMore()) goto _L8; else goto _L7
_L7:
        k4 = l5;
        k5 = i5;
        flag3 = true;
_L12:
        if(k5 == 0)
            reportUnexpectedNumberChar(l4, "Exponent indicator not followed by a digit");
_L13:
        if(!flag3)
            _inputPtr = _inputPtr - 1;
        _textBuffer.setCurrentLength(k4);
        return reset(flag, l1, k2, k5);
        abyte4 = _inputBuffer;
        l7 = _inputPtr;
        _inputPtr = l7 + 1;
        l = 0xff & abyte4[l7];
        j = k7;
        break MISSING_BLOCK_LABEL_93;
_L2:
        abyte3 = _inputBuffer;
        l6 = _inputPtr;
        _inputPtr = l6 + 1;
        i2 = 0xff & abyte3[l6];
        if(i2 < 48 || i2 > 57) goto _L10; else goto _L9
_L9:
        k6++;
        if(j2 >= ac3.length)
        {
            ac3 = _textBuffer.finishCurrentSegment();
            j2 = 0;
        }
        i7 = j2 + 1;
        ac3[j2] = (char)i2;
        j2 = i7;
          goto _L11
_L8:
        abyte2 = _inputBuffer;
        i6 = _inputPtr;
        _inputPtr = i6 + 1;
        l4 = 0xff & abyte2[i6];
        k4 = l5;
        break MISSING_BLOCK_LABEL_443;
_L6:
        j5 = i5;
        flag3 = flag2;
        k5 = j5;
          goto _L12
_L4:
        k4 = j2;
        flag3 = flag2;
        k5 = 0;
          goto _L13
        i2 = j1;
        ac3 = ac2;
        j2 = k1;
        flag2 = flag1;
        k2 = 0;
          goto _L14
        j1 = l;
        ac2 = ac1;
        k1 = j;
        l1 = i1;
        flag1 = false;
        if(true) goto _L16; else goto _L15
_L15:
    }
}
