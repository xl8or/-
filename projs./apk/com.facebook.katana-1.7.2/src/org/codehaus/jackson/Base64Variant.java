// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;

import java.util.Arrays;

public final class Base64Variant
{

    public Base64Variant(String s, String s1, boolean flag, char c, int i)
    {
        _asciiToBase64 = new int[128];
        _base64ToAsciiC = new char[64];
        _base64ToAsciiB = new byte[64];
        _name = s;
        _usesPadding = flag;
        _paddingChar = c;
        _maxLineLength = i;
        int j = s1.length();
        if(j != 64)
            throw new IllegalArgumentException((new StringBuilder()).append("Base64Alphabet length must be exactly 64 (was ").append(j).append(")").toString());
        s1.getChars(0, j, _base64ToAsciiC, 0);
        Arrays.fill(_asciiToBase64, -1);
        for(int k = 0; k < j; k++)
        {
            char c1 = _base64ToAsciiC[k];
            _base64ToAsciiB[k] = (byte)c1;
            _asciiToBase64[c1] = k;
        }

        if(flag)
            _asciiToBase64[c] = -2;
    }

    public Base64Variant(Base64Variant base64variant, String s, int i)
    {
        this(base64variant, s, base64variant._usesPadding, base64variant._paddingChar, i);
    }

    public Base64Variant(Base64Variant base64variant, String s, boolean flag, char c, int i)
    {
        _asciiToBase64 = new int[128];
        _base64ToAsciiC = new char[64];
        _base64ToAsciiB = new byte[64];
        _name = s;
        byte abyte0[] = base64variant._base64ToAsciiB;
        System.arraycopy(abyte0, 0, _base64ToAsciiB, 0, abyte0.length);
        char ac[] = base64variant._base64ToAsciiC;
        System.arraycopy(ac, 0, _base64ToAsciiC, 0, ac.length);
        int ai[] = base64variant._asciiToBase64;
        System.arraycopy(ai, 0, _asciiToBase64, 0, ai.length);
        _usesPadding = flag;
        _paddingChar = c;
        _maxLineLength = i;
    }

    public int decodeBase64Byte(byte byte0)
    {
        int i;
        if(byte0 <= 127)
            i = _asciiToBase64[byte0];
        else
            i = -1;
        return i;
    }

    public int decodeBase64Char(char c)
    {
        int i;
        if(c <= '\177')
            i = _asciiToBase64[c];
        else
            i = -1;
        return i;
    }

    public int decodeBase64Char(int i)
    {
        int j;
        if(i <= 127)
            j = _asciiToBase64[i];
        else
            j = -1;
        return j;
    }

    public byte encodeBase64BitsAsByte(int i)
    {
        return _base64ToAsciiB[i];
    }

    public char encodeBase64BitsAsChar(int i)
    {
        return _base64ToAsciiC[i];
    }

    public int encodeBase64Chunk(int i, byte abyte0[], int j)
    {
        int k = j + 1;
        abyte0[j] = _base64ToAsciiB[0x3f & i >> 18];
        int l = k + 1;
        abyte0[k] = _base64ToAsciiB[0x3f & i >> 12];
        int i1 = l + 1;
        abyte0[l] = _base64ToAsciiB[0x3f & i >> 6];
        int j1 = i1 + 1;
        abyte0[i1] = _base64ToAsciiB[i & 0x3f];
        return j1;
    }

    public int encodeBase64Chunk(int i, char ac[], int j)
    {
        int k = j + 1;
        ac[j] = _base64ToAsciiC[0x3f & i >> 18];
        int l = k + 1;
        ac[k] = _base64ToAsciiC[0x3f & i >> 12];
        int i1 = l + 1;
        ac[l] = _base64ToAsciiC[0x3f & i >> 6];
        int j1 = i1 + 1;
        ac[i1] = _base64ToAsciiC[i & 0x3f];
        return j1;
    }

    public void encodeBase64Chunk(StringBuilder stringbuilder, int i)
    {
        stringbuilder.append(_base64ToAsciiC[0x3f & i >> 18]);
        stringbuilder.append(_base64ToAsciiC[0x3f & i >> 12]);
        stringbuilder.append(_base64ToAsciiC[0x3f & i >> 6]);
        stringbuilder.append(_base64ToAsciiC[i & 0x3f]);
    }

    public int encodeBase64Partial(int i, int j, byte abyte0[], int k)
    {
        int l = k + 1;
        abyte0[k] = _base64ToAsciiB[0x3f & i >> 18];
        int i1 = l + 1;
        abyte0[l] = _base64ToAsciiB[0x3f & i >> 12];
        int j1;
        if(_usesPadding)
        {
            byte byte0 = (byte)_paddingChar;
            int k1 = i1 + 1;
            byte byte1;
            int l1;
            if(j == 2)
                byte1 = _base64ToAsciiB[0x3f & i >> 6];
            else
                byte1 = byte0;
            abyte0[i1] = byte1;
            l1 = k1 + 1;
            abyte0[k1] = byte0;
            j1 = l1;
        } else
        if(j == 2)
        {
            j1 = i1 + 1;
            abyte0[i1] = _base64ToAsciiB[0x3f & i >> 6];
        } else
        {
            j1 = i1;
        }
        return j1;
    }

    public int encodeBase64Partial(int i, int j, char ac[], int k)
    {
        int l = k + 1;
        ac[k] = _base64ToAsciiC[0x3f & i >> 18];
        int i1 = l + 1;
        ac[l] = _base64ToAsciiC[0x3f & i >> 12];
        int j1;
        if(_usesPadding)
        {
            int k1 = i1 + 1;
            char c;
            int l1;
            if(j == 2)
                c = _base64ToAsciiC[0x3f & i >> 6];
            else
                c = _paddingChar;
            ac[i1] = c;
            l1 = k1 + 1;
            ac[k1] = _paddingChar;
            j1 = l1;
        } else
        if(j == 2)
        {
            j1 = i1 + 1;
            ac[i1] = _base64ToAsciiC[0x3f & i >> 6];
        } else
        {
            j1 = i1;
        }
        return j1;
    }

    public void encodeBase64Partial(StringBuilder stringbuilder, int i, int j)
    {
        stringbuilder.append(_base64ToAsciiC[0x3f & i >> 18]);
        stringbuilder.append(_base64ToAsciiC[0x3f & i >> 12]);
        if(!_usesPadding) goto _L2; else goto _L1
_L1:
        char c;
        if(j == 2)
            c = _base64ToAsciiC[0x3f & i >> 6];
        else
            c = _paddingChar;
        stringbuilder.append(c);
        stringbuilder.append(_paddingChar);
_L4:
        return;
_L2:
        if(j == 2)
            stringbuilder.append(_base64ToAsciiC[0x3f & i >> 6]);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getMaxLineLength()
    {
        return _maxLineLength;
    }

    public String getName()
    {
        return _name;
    }

    public byte getPaddingByte()
    {
        return (byte)_paddingChar;
    }

    public char getPaddingChar()
    {
        return _paddingChar;
    }

    public String toString()
    {
        return _name;
    }

    public boolean usesPadding()
    {
        return _usesPadding;
    }

    public boolean usesPaddingChar(char c)
    {
        boolean flag;
        if(c == _paddingChar)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean usesPaddingChar(int i)
    {
        boolean flag;
        if(i == _paddingChar)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    static final char PADDING_CHAR_NONE;
    private final int _asciiToBase64[];
    private final byte _base64ToAsciiB[];
    private final char _base64ToAsciiC[];
    final int _maxLineLength;
    final String _name;
    final char _paddingChar;
    final boolean _usesPadding;
}
