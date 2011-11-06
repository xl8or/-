// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.impl;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.util.TextBuffer;

// Referenced classes of package org.codehaus.jackson.impl:
//            ReaderBasedParserBase

public abstract class ReaderBasedNumericParser extends ReaderBasedParserBase
{

    public ReaderBasedNumericParser(IOContext iocontext, int i, Reader reader)
    {
        super(iocontext, i, reader);
    }

    private final JsonToken parseNumberText2(boolean flag)
        throws IOException, JsonParseException
    {
        char ac[] = _textBuffer.emptyAndGetCurrentSegment();
        int i;
        char ac1[];
        int j;
        char ac2[];
        int k;
        char c;
        int l;
        boolean flag1;
        char ac3[];
        boolean flag2;
        int i1;
        int j1;
        int k1;
        int l1;
        char c1;
        int i2;
        int j2;
        char c2;
        char c3;
        int k2;
        char ac4[];
        int l2;
        int i3;
        boolean flag3;
        int j3;
        int k3;
        char ac5[];
        int l3;
        char ac6[];
        int i4;
        char ac7[];
        int j4;
        int k4;
        int l4;
        int i5;
        char ac8[];
        int j5;
        boolean flag4;
        int k5;
        boolean flag5;
        int l5;
        if(flag)
        {
            i = 0 + 1;
            ac[0] = '-';
        } else
        {
            i = 0;
        }
        ac1 = ac;
        j = 0;
        if(_inputPtr >= _inputEnd && !loadMore())
        {
            flag1 = true;
            l = 0;
        } else
        {
label0:
            {
                ac2 = _inputBuffer;
                k = _inputPtr;
                _inputPtr = k + 1;
                c = ac2[k];
                if(c < '0')
                    break MISSING_BLOCK_LABEL_833;
                if(c <= '9')
                    break label0;
                l = c;
                flag1 = false;
            }
        }
_L11:
        if(j == 0)
            reportInvalidNumber((new StringBuilder()).append("Missing integer part (next char ").append(_getCharDesc(l)).append(")").toString());
        if(l != 46)
            break MISSING_BLOCK_LABEL_812;
        k4 = i + 1;
        ac1[i] = l;
        k1 = 0;
        l4 = l;
        j1 = k4;
        ac3 = ac1;
        i5 = l4;
_L5:
        if(_inputPtr >= _inputEnd && !loadMore())
        {
            i1 = i5;
            flag2 = true;
        } else
        {
            ac8 = _inputBuffer;
            j5 = _inputPtr;
            _inputPtr = j5 + 1;
            i5 = ac8[j5];
            if(i5 >= 48)
            {
label1:
                {
                    if(i5 <= 57)
                        break label1;
                    flag5 = flag1;
                    i1 = i5;
                    flag2 = flag5;
                }
            } else
            {
                flag4 = flag1;
                i1 = i5;
                flag2 = flag4;
            }
        }
        if(k1 == 0)
            reportUnexpectedNumberChar(i1, "Decimal point not followed by a digit");
_L9:
        if(i1 != 101 && i1 != 69)
            break MISSING_BLOCK_LABEL_787;
        if(j1 >= ac3.length)
        {
            ac3 = _textBuffer.finishCurrentSegment();
            j1 = 0;
        }
        l1 = j1 + 1;
        ac3[j1] = i1;
        if(_inputPtr < _inputEnd)
        {
            ac7 = _inputBuffer;
            j4 = _inputPtr;
            _inputPtr = j4 + 1;
            c1 = ac7[j4];
        } else
        {
            c1 = getNextChar("expected a digit for number exponent");
        }
        if(c1 == '-' || c1 == '+')
        {
            if(l1 >= ac3.length)
            {
                ac3 = _textBuffer.finishCurrentSegment();
                i2 = 0;
            } else
            {
                i2 = l1;
            }
            j2 = i2 + 1;
            ac3[i2] = c1;
            if(_inputPtr < _inputEnd)
            {
                ac6 = _inputBuffer;
                i4 = _inputPtr;
                _inputPtr = i4 + 1;
                c2 = ac6[i4];
            } else
            {
                c2 = getNextChar("expected a digit for number exponent");
            }
            c3 = c2;
            k2 = 0;
            ac4 = ac3;
            l2 = j2;
        } else
        {
            c3 = c1;
            k2 = 0;
            ac4 = ac3;
            l2 = l1;
        }
_L6:
        if(c3 > '9' || c3 < '0') goto _L2; else goto _L1
_L1:
        k2++;
        if(l2 >= ac4.length)
        {
            ac4 = _textBuffer.finishCurrentSegment();
            l2 = 0;
        }
        k3 = l2 + 1;
        ac4[l2] = c3;
        if(_inputPtr < _inputEnd || loadMore()) goto _L4; else goto _L3
_L3:
        j3 = k2;
        l2 = k3;
        flag3 = true;
_L7:
        if(j3 == 0)
            reportUnexpectedNumberChar(c3, "Exponent indicator not followed by a digit");
        j1 = l2;
_L8:
        if(!flag3)
            _inputPtr = _inputPtr - 1;
        _textBuffer.setCurrentLength(j1);
        return reset(flag, j, k1, j3);
        if(++j == 2 && ac1[i - 1] == '0')
            reportInvalidNumber("Leading zeroes not allowed");
        if(i >= ac1.length)
        {
            ac1 = _textBuffer.finishCurrentSegment();
            i = 0;
        }
        l5 = i + 1;
        ac1[i] = c;
        i = l5;
        break MISSING_BLOCK_LABEL_27;
        k1++;
        if(j1 >= ac3.length)
        {
            ac3 = _textBuffer.finishCurrentSegment();
            j1 = 0;
        }
        k5 = j1 + 1;
        ac3[j1] = i5;
        j1 = k5;
          goto _L5
_L4:
        ac5 = _inputBuffer;
        l3 = _inputPtr;
        _inputPtr = l3 + 1;
        c3 = ac5[l3];
        l2 = k3;
          goto _L6
_L2:
        i3 = k2;
        flag3 = flag2;
        j3 = i3;
          goto _L7
        flag3 = flag2;
        j3 = 0;
          goto _L8
        ac3 = ac1;
        flag2 = flag1;
        i1 = l;
        j1 = i;
        k1 = 0;
          goto _L9
        l = c;
        flag1 = false;
        if(true) goto _L11; else goto _L10
_L10:
    }

    protected final JsonToken parseNumberText(int i)
        throws IOException, JsonParseException
    {
        boolean flag;
        int j;
        int k;
        int l;
        if(i == 45)
            flag = true;
        else
            flag = false;
        j = _inputPtr;
        k = j - 1;
        l = _inputEnd;
        if(!flag) goto _L2; else goto _L1
_L1:
        if(j < _inputEnd) goto _L4; else goto _L3
_L3:
        int i1;
        int k1;
        char c;
        int l1;
        char c1;
        int i2;
        int j2;
        char c2;
        int k2;
        char c3;
        int l2;
        int i3;
        int j1;
        char ac[];
        char ac1[];
        char ac2[];
        int j3;
        int k3;
        JsonToken jsontoken;
        char ac3[];
        int l3;
        int i4;
        int j4;
        char ac4[];
        int k4;
        char c4;
        char ac5[];
        int l4;
        char c5;
        if(flag)
            i4 = k + 1;
        else
            i4 = k;
        _inputPtr = i4;
        jsontoken = parseNumberText2(flag);
_L23:
        return jsontoken;
_L4:
        ac5 = _inputBuffer;
        l4 = j + 1;
        c5 = ac5[j];
        if(c5 > '9' || c5 < '0')
            reportUnexpectedNumberChar(c5, "expected digit (0-9) to follow minus sign, for valid numeric value");
        j = l4;
_L2:
        i1 = j;
        j1 = 1;
_L21:
        if(i1 >= _inputEnd) goto _L3; else goto _L5
_L5:
        ac = _inputBuffer;
        k1 = i1 + 1;
        c = ac[i1];
        if(c >= '0' && c <= '9') goto _L7; else goto _L6
_L6:
        if(c != '.')
            break MISSING_BLOCK_LABEL_510;
        i2 = 0;
        j4 = k1;
_L22:
        if(j4 >= l) goto _L3; else goto _L8
_L8:
        ac4 = _inputBuffer;
        k4 = j4 + 1;
        c4 = ac4[j4];
        if(c4 >= '0' && c4 <= '9') goto _L10; else goto _L9
_L9:
        if(i2 == 0)
            reportUnexpectedNumberChar(c4, "Decimal point not followed by a digit");
        c1 = c4;
        l1 = k4;
_L25:
        if(c1 != 'e' && c1 != 'E') goto _L12; else goto _L11
_L11:
        if(l1 >= l) goto _L3; else goto _L13
_L13:
        ac1 = _inputBuffer;
        j2 = l1 + 1;
        c2 = ac1[l1];
        if(c2 != '-' && c2 != '+') goto _L15; else goto _L14
_L14:
        if(j2 >= l) goto _L3; else goto _L16
_L16:
        ac2 = _inputBuffer;
        k2 = j2 + 1;
        c3 = ac2[j2];
        l2 = 0;
_L20:
        if(c3 > '9' || c3 < '0') goto _L18; else goto _L17
_L17:
        l2++;
        if(k2 >= l) goto _L3; else goto _L19
_L19:
        ac3 = _inputBuffer;
        l3 = k2 + 1;
        c3 = ac3[k2];
        k2 = l3;
          goto _L20
_L7:
        if(++j1 == 2 && _inputBuffer[k1 - 2] == '0')
        {
            reportInvalidNumber("Leading zeroes not allowed");
            i1 = k1;
        } else
        {
            i1 = k1;
        }
          goto _L21
_L10:
        i2++;
        j4 = k4;
          goto _L22
_L18:
        if(l2 == 0)
            reportUnexpectedNumberChar(c3, "Exponent indicator not followed by a digit");
        i3 = l2;
        l1 = k2;
_L24:
        j3 = l1 + -1;
        _inputPtr = j3;
        k3 = j3 - k;
        _textBuffer.resetWithShared(_inputBuffer, k, k3);
        jsontoken = reset(flag, j1, i2, i3);
          goto _L23
_L15:
        k2 = j2;
        c3 = c2;
        l2 = 0;
          goto _L20
_L12:
        i3 = 0;
          goto _L24
        l1 = k1;
        c1 = c;
        i2 = 0;
          goto _L25
    }
}
