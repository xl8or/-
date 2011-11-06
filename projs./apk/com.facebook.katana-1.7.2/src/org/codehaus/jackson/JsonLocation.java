// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson;


public class JsonLocation
{

    public JsonLocation(Object obj, long l, int i, int j)
    {
        _totalChars = l;
        _lineNr = i;
        _columnNr = j;
        _sourceRef = obj;
    }

    public long getByteOffset()
    {
        return -1L;
    }

    public long getCharOffset()
    {
        return _totalChars;
    }

    public int getColumnNr()
    {
        return _columnNr;
    }

    public int getLineNr()
    {
        return _lineNr;
    }

    public Object getSourceRef()
    {
        return _sourceRef;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(80);
        stringbuilder.append("[Source: ");
        if(_sourceRef == null)
            stringbuilder.append("UNKNOWN");
        else
            stringbuilder.append(_sourceRef.toString());
        stringbuilder.append("; line: ");
        stringbuilder.append(_lineNr);
        stringbuilder.append(", column: ");
        stringbuilder.append(_columnNr);
        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    final int _columnNr;
    final int _lineNr;
    final Object _sourceRef;
    final long _totalChars;
}
