// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Master.java

package org.xbill.DNS;

import java.io.*;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            Name, RelativeNameException, Tokenizer, TextParseException, 
//            Generator, DClass, TTL, Type, 
//            Record, SOARecord

public class Master
{

    Master(File file1, Name name, long l)
        throws IOException
    {
        last = null;
        included = null;
        if(name != null && !name.isAbsolute())
        {
            throw new RelativeNameException(name);
        } else
        {
            file = file1;
            st = new Tokenizer(file1);
            origin = name;
            defaultTTL = l;
            return;
        }
    }

    public Master(InputStream inputstream)
    {
        Master(inputstream, null, -1L);
    }

    public Master(InputStream inputstream, Name name)
    {
        Master(inputstream, name, -1L);
    }

    public Master(InputStream inputstream, Name name, long l)
    {
        last = null;
        included = null;
        if(name != null && !name.isAbsolute())
        {
            throw new RelativeNameException(name);
        } else
        {
            st = new Tokenizer(inputstream);
            origin = name;
            defaultTTL = l;
            return;
        }
    }

    public Master(String s)
        throws IOException
    {
        Master(new File(s), null, -1L);
    }

    public Master(String s, Name name)
        throws IOException
    {
        Master(new File(s), name, -1L);
    }

    public Master(String s, Name name, long l)
        throws IOException
    {
        Master(new File(s), name, l);
    }

    private void endGenerate()
        throws IOException
    {
        st.getEOL();
        generator = null;
    }

    private Record nextGenerated()
        throws IOException
    {
        Record record;
        try
        {
            record = generator.nextRecord();
        }
        catch(Tokenizer.TokenizerException tokenizerexception)
        {
            throw st.exception((new StringBuilder()).append("Parsing $GENERATE: ").append(tokenizerexception.getBaseMessage()).toString());
        }
        catch(TextParseException textparseexception)
        {
            throw st.exception((new StringBuilder()).append("Parsing $GENERATE: ").append(textparseexception.getMessage()).toString());
        }
        return record;
    }

    private Name parseName(String s, Name name)
        throws TextParseException
    {
        Name name1;
        try
        {
            name1 = Name.fromString(s, name);
        }
        catch(TextParseException textparseexception)
        {
            throw st.exception(textparseexception.getMessage());
        }
        return name1;
    }

    private void parseTTLClassAndType()
        throws IOException
    {
        String s;
        String s1;
        boolean flag = false;
        s = st.getString();
        int i = DClass.value(s);
        currentDClass = i;
        if(i >= 0)
        {
            s = st.getString();
            flag = true;
        }
        currentTTL = -1L;
        int j;
        int k;
        String s2;
        try
        {
            currentTTL = TTL.parseTTL(s);
            s2 = st.getString();
        }
        catch(NumberFormatException numberformatexception)
        {
            if(defaultTTL >= 0L)
                currentTTL = defaultTTL;
            else
            if(last != null)
                currentTTL = last.getTTL();
            continue; /* Loop/switch isn't completed */
        }
        s = s2;
_L8:
        if(flag) goto _L2; else goto _L1
_L1:
        k = DClass.value(s);
        currentDClass = k;
        if(k < 0) goto _L4; else goto _L3
_L3:
        s1 = st.getString();
_L6:
        j = Type.value(s1);
        currentType = j;
        if(j < 0)
            throw st.exception((new StringBuilder()).append("Invalid type '").append(s1).append("'").toString());
        break; /* Loop/switch isn't completed */
_L4:
        currentDClass = 1;
_L2:
        s1 = s;
        if(true) goto _L6; else goto _L5
_L5:
        if(currentTTL < 0L)
        {
            if(currentType != 6)
                throw st.exception("missing TTL");
            needSOATTL = true;
            currentTTL = 0L;
        }
        return;
        if(true) goto _L8; else goto _L7
_L7:
    }

    private long parseUInt32(String s)
    {
        if(Character.isDigit(s.charAt(0))) goto _L2; else goto _L1
_L1:
        long l = -1L;
_L4:
        return l;
_L2:
        long l1 = Long.parseLong(s);
        l = l1;
        if(l < 0L || l > 0xffffffffL)
            l = -1L;
        continue; /* Loop/switch isn't completed */
        NumberFormatException numberformatexception;
        numberformatexception;
        l = -1L;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void startGenerate()
        throws IOException
    {
        String s = st.getIdentifier();
        int i = s.indexOf("-");
        if(i < 0)
            throw st.exception((new StringBuilder()).append("Invalid $GENERATE range specifier: ").append(s).toString());
        String s1 = s.substring(0, i);
        String s2 = s.substring(i + 1);
        int j = s2.indexOf("/");
        String s3;
        String s4;
        long l;
        long l1;
        long l2;
        String s5;
        String s6;
        if(j >= 0)
        {
            String s7 = s2.substring(j + 1);
            String s8 = s2.substring(0, j);
            s3 = s7;
            s4 = s8;
        } else
        {
            s3 = null;
            s4 = s2;
        }
        l = parseUInt32(s1);
        l1 = parseUInt32(s4);
        if(s3 != null)
            l2 = parseUInt32(s3);
        else
            l2 = 1L;
        if(l < 0L || l1 < 0L || l > l1 || l2 <= 0L)
            throw st.exception((new StringBuilder()).append("Invalid $GENERATE range specifier: ").append(s).toString());
        s5 = st.getIdentifier();
        parseTTLClassAndType();
        if(!Generator.supportedType(currentType))
            throw st.exception((new StringBuilder()).append("$GENERATE does not support ").append(Type.string(currentType)).append(" records").toString());
        s6 = st.getIdentifier();
        st.getEOL();
        st.unget();
        generator = new Generator(l, l1, l2, s5, currentType, currentDClass, currentTTL, s6, origin);
        if(generators == null)
            generators = new ArrayList(1);
        generators.add(generator);
    }

    public Record _nextRecord()
        throws IOException
    {
        if(included == null) goto _L2; else goto _L1
_L1:
        Record record = included.nextRecord();
        if(record == null) goto _L4; else goto _L3
_L3:
        return record;
_L4:
        included = null;
_L2:
        if(generator == null)
            break MISSING_BLOCK_LABEL_46;
        record = nextGenerated();
        if(record != null) goto _L3; else goto _L5
_L5:
        endGenerate();
_L9:
        Tokenizer.Token token = st.get(true, false);
        if(token.type != 2) goto _L7; else goto _L6
_L6:
        Tokenizer.Token token2 = st.get();
        if(token2.type == 1) goto _L9; else goto _L8
_L8:
label0:
        {
            if(token2.type != 0)
                break label0;
            record = null;
        }
          goto _L3
        Name name;
        st.unget();
        if(last == null)
            throw st.exception("no owner");
        name = last.getName();
_L11:
        parseTTLClassAndType();
        last = Record.fromString(name, currentType, currentDClass, currentTTL, st, origin);
        if(needSOATTL)
        {
            long l = ((SOARecord)last).getMinimum();
            last.setTTL(l);
            defaultTTL = l;
            needSOATTL = false;
        }
        record = last;
          goto _L3
_L7:
        if(token.type == 1) goto _L9; else goto _L10
_L10:
label1:
        {
            if(token.type != 0)
                break label1;
            record = null;
        }
          goto _L3
        String s;
        if(token.value.charAt(0) != '$')
            break MISSING_BLOCK_LABEL_527;
        s = token.value;
        if(s.equalsIgnoreCase("$ORIGIN"))
        {
            origin = st.getName(Name.root);
            st.getEOL();
        } else
        {
label2:
            {
                if(!s.equalsIgnoreCase("$TTL"))
                    break label2;
                defaultTTL = st.getTTL();
                st.getEOL();
            }
        }
          goto _L9
label3:
        {
            if(!s.equalsIgnoreCase("$INCLUDE"))
                break label3;
            String s1 = st.getString();
            File file1;
            Name name1;
            Tokenizer.Token token1;
            if(file != null)
                file1 = new File(file.getParent(), s1);
            else
                file1 = new File(s1);
            name1 = origin;
            token1 = st.get();
            if(token1.isString())
            {
                name1 = parseName(token1.value, Name.root);
                st.getEOL();
            }
            included = new Master(file1, name1, defaultTTL);
            record = nextRecord();
        }
          goto _L3
label4:
        {
            if(!s.equalsIgnoreCase("$GENERATE"))
                break MISSING_BLOCK_LABEL_498;
            if(generator != null)
                throw new IllegalStateException("cannot nest $GENERATE");
            startGenerate();
            if(!noExpandGenerate)
                break label4;
            endGenerate();
        }
          goto _L9
        record = nextGenerated();
          goto _L3
        throw st.exception((new StringBuilder()).append("Invalid directive: ").append(s).toString());
        name = parseName(token.value, origin);
        if(last != null && name.equals(last.getName()))
            name = last.getName();
          goto _L11
    }

    public void expandGenerate(boolean flag)
    {
        boolean flag1;
        if(!flag)
            flag1 = true;
        else
            flag1 = false;
        noExpandGenerate = flag1;
    }

    protected void finalize()
    {
        st.close();
    }

    public Iterator generators()
    {
        Iterator iterator;
        if(generators != null)
            iterator = Collections.unmodifiableList(generators).iterator();
        else
            iterator = Collections.EMPTY_LIST.iterator();
        return iterator;
    }

    public Record nextRecord()
        throws IOException
    {
        Record record = _nextRecord();
        if(record == null)
            st.close();
        return record;
        Exception exception;
        exception;
        if(true)
            st.close();
        throw exception;
    }

    private int currentDClass;
    private long currentTTL;
    private int currentType;
    private long defaultTTL;
    private File file;
    private Generator generator;
    private List generators;
    private Master included;
    private Record last;
    private boolean needSOATTL;
    private boolean noExpandGenerate;
    private Name origin;
    private Tokenizer st;
}
