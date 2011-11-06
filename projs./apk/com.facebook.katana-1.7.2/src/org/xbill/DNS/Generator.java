// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Generator.java

package org.xbill.DNS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            DClass, TextParseException, Type, Name, 
//            Record, Options

public class Generator
{

    public Generator(long l, long l1, long l2, String s, 
            int i, int j, long l3, String s1, Name name)
    {
        if(l < 0L || l1 < 0L || l > l1 || l2 <= 0L)
            throw new IllegalArgumentException("invalid range specification");
        if(!supportedType(i))
        {
            throw new IllegalArgumentException("unsupported type");
        } else
        {
            DClass.check(j);
            start = l;
            end = l1;
            step = l2;
            namePattern = s;
            type = i;
            dclass = j;
            ttl = l3;
            rdataPattern = s1;
            origin = name;
            current = l;
            return;
        }
    }

    private String substitute(String s, long l)
        throws IOException
    {
        byte abyte0[];
        StringBuffer stringbuffer;
        boolean flag;
        int i;
        abyte0 = s.getBytes();
        stringbuffer = new StringBuffer();
        flag = false;
        i = 0;
_L2:
        char c;
        long l1;
        int j;
        boolean flag1;
        long l2;
        long l3;
        if(i >= abyte0.length)
            break; /* Loop/switch isn't completed */
        c = (char)(0xff & abyte0[i]);
        long l4;
        int k;
        int i1;
        long l7;
        char c1;
        int i4;
        int k4;
        int i5;
        if(flag)
        {
            stringbuffer.append(c);
            flag = false;
        } else
        if(c == '\\')
        {
            if(i + 1 == abyte0.length)
                throw new TextParseException("invalid escape character");
            flag = true;
        } else
        if(c == '$')
        {
label0:
            {
                if(i + 1 >= abyte0.length || abyte0[i + 1] != 36)
                    break label0;
                i++;
                stringbuffer.append((char)(0xff & abyte0[i]));
            }
        } else
        {
            stringbuffer.append(c);
        }
_L19:
        i++;
        if(true) goto _L2; else goto _L1
        if(i + 1 >= abyte0.length || abyte0[i + 1] != 123)
            break MISSING_BLOCK_LABEL_899;
        int j1 = i + 1;
        String s1;
        String s2;
        int k1;
        int i2;
        boolean flag2;
        long l5;
        int j2;
        int k2;
        int i3;
        long l6;
        int j3;
        int k3;
        char c2;
        int j4;
        char c3;
        if(j1 + 1 < abyte0.length && abyte0[j1 + 1] == 45)
        {
            i5 = j1 + 1;
            k1 = c;
            i2 = i5;
            flag2 = true;
            l5 = 0L;
        } else
        {
            k1 = c;
            i2 = j1;
            flag2 = false;
            l5 = 0L;
        }
        if(i2 + 1 >= abyte0.length) goto _L4; else goto _L3
_L3:
        j4 = i2 + 1;
        c3 = (char)(0xff & abyte0[j4]);
        if(c3 == ',') goto _L6; else goto _L5
_L5:
        if(c3 != '}') goto _L8; else goto _L7
_L7:
        i2 = j4;
        k1 = c3;
_L4:
        if(flag2)
            l5 = -l5;
        if(k1 != 44) goto _L10; else goto _L9
_L9:
        j2 = k1;
        k2 = i2;
        l2 = 0L;
_L18:
        if(k2 + 1 >= abyte0.length) goto _L12; else goto _L11
_L11:
        k3 = k2 + 1;
        c2 = (char)(0xff & abyte0[k3]);
        if(c2 == ',') goto _L14; else goto _L13
_L13:
        if(c2 != '}') goto _L16; else goto _L15
_L15:
        k2 = k3;
        j2 = c2;
_L12:
        if(j2 == ',')
        {
            if(k2 + 1 == abyte0.length)
                throw new TextParseException("invalid base");
            j3 = k2 + 1;
            c1 = (char)(0xff & abyte0[j3]);
            if(c1 == 'o')
            {
                l6 = 8L;
                i3 = j3;
                flag1 = false;
            } else
            if(c1 == 'x')
            {
                l6 = 16L;
                i3 = j3;
                flag1 = false;
            } else
            {
label1:
                {
                    if(c1 != 'X')
                        break label1;
                    l6 = 16L;
                    i3 = j3;
                    flag1 = true;
                }
            }
        } else
        {
            flag1 = false;
            i3 = k2;
            l6 = 10L;
        }
          goto _L17
_L8:
        if(c3 < '0' || c3 > '9')
            throw new TextParseException("invalid offset");
        k4 = c3 - 48;
        l5 = l5 * 10L + (long)k4;
        i2 = j4;
        k1 = k4;
        break MISSING_BLOCK_LABEL_219;
_L16:
        if(c2 < '0' || c2 > '9')
            throw new TextParseException("invalid width");
        i4 = c2 - 48;
        l2 = l2 * 10L + (long)i4;
        k2 = k3;
        j2 = i4;
          goto _L18
_L17:
        if(i3 + 1 == abyte0.length || abyte0[i3 + 1] != 125)
            throw new TextParseException("invalid modifiers");
        j = i3 + 1;
        l7 = l6;
        l3 = l5;
        l1 = l7;
_L20:
        l4 = l3 + l;
        if(l4 < 0L)
            throw new TextParseException("invalid offset expansion");
        if(l1 == 8L)
            s1 = Long.toOctalString(l4);
        else
        if(l1 == 16L)
            s1 = Long.toHexString(l4);
        else
            s1 = Long.toString(l4);
        if(flag1)
            s2 = s1.toUpperCase();
        else
            s2 = s1;
        if(l2 != 0L && l2 > (long)s2.length())
        {
            k = (int)l2 - s2.length();
            do
            {
                i1 = k + -1;
                if(k <= 0)
                    break;
                stringbuffer.append('0');
                k = i1;
            } while(true);
        }
        stringbuffer.append(s2);
        i = j;
          goto _L19
_L1:
        return stringbuffer.toString();
        if(c1 != 'd')
            throw new TextParseException("invalid base");
        l6 = 10L;
        i3 = j3;
        flag1 = false;
          goto _L17
_L14:
        k2 = k3;
        j2 = c2;
          goto _L12
_L10:
        j2 = k1;
        k2 = i2;
        l2 = 0L;
          goto _L12
_L6:
        i2 = j4;
        k1 = c3;
          goto _L4
        l1 = 10L;
        j = i;
        flag1 = false;
        l2 = 0L;
        l3 = 0L;
          goto _L20
    }

    public static boolean supportedType(int i)
    {
        Type.check(i);
        boolean flag;
        if(i == 12 || i == 5 || i == 39 || i == 1 || i == 28 || i == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Record[] expand()
        throws IOException
    {
        ArrayList arraylist = new ArrayList();
        for(long l = start; l < end; l += step)
        {
            Name name = Name.fromString(substitute(namePattern, current), origin);
            String s = substitute(rdataPattern, current);
            arraylist.add(Record.fromString(name, type, dclass, ttl, s, origin));
        }

        return (Record[])(Record[])arraylist.toArray(new Record[arraylist.size()]);
    }

    public Record nextRecord()
        throws IOException
    {
        Record record;
        if(current > end)
        {
            record = null;
        } else
        {
            Name name = Name.fromString(substitute(namePattern, current), origin);
            String s = substitute(rdataPattern, current);
            current = current + step;
            record = Record.fromString(name, type, dclass, ttl, s, origin);
        }
        return record;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("$GENERATE ");
        stringbuffer.append((new StringBuilder()).append(start).append("-").append(end).toString());
        if(step > 1L)
            stringbuffer.append((new StringBuilder()).append("/").append(step).toString());
        stringbuffer.append(" ");
        stringbuffer.append((new StringBuilder()).append(namePattern).append(" ").toString());
        stringbuffer.append((new StringBuilder()).append(ttl).append(" ").toString());
        if(dclass != 1 || !Options.check("noPrintIN"))
            stringbuffer.append((new StringBuilder()).append(DClass.string(dclass)).append(" ").toString());
        stringbuffer.append((new StringBuilder()).append(Type.string(type)).append(" ").toString());
        stringbuffer.append((new StringBuilder()).append(rdataPattern).append(" ").toString());
        return stringbuffer.toString();
    }

    private long current;
    public final int dclass;
    public long end;
    public final String namePattern;
    public final Name origin;
    public final String rdataPattern;
    public long start;
    public long step;
    public final long ttl;
    public final int type;
}
