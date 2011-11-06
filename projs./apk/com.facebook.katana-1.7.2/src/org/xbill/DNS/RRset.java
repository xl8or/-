// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RRset.java

package org.xbill.DNS;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            Record, RRSIGRecord, DClass, Type, 
//            Name

public class RRset
    implements Serializable
{

    public RRset()
    {
        rrs = new ArrayList(1);
        nsigs = 0;
        position = 0;
    }

    public RRset(RRset rrset)
    {
        rrset;
        JVM INSTR monitorenter ;
        rrs = (List)((ArrayList)rrset.rrs).clone();
        nsigs = rrset.nsigs;
        position = rrset.position;
        return;
    }

    public RRset(Record record)
    {
        RRset();
        safeAddRR(record);
    }

    /**
     * @deprecated Method iterator is deprecated
     */

    private Iterator iterator(boolean flag, boolean flag1)
    {
        int i = 0;
        this;
        JVM INSTR monitorenter ;
        int j = rrs.size();
        if(!flag) goto _L2; else goto _L1
_L1:
        int k = j - nsigs;
_L5:
        if(k != 0) goto _L4; else goto _L3
_L3:
        Iterator iterator2 = Collections.EMPTY_LIST.iterator();
        Iterator iterator1 = iterator2;
_L8:
        this;
        JVM INSTR monitorexit ;
        return iterator1;
_L2:
        k = nsigs;
          goto _L5
_L12:
        ArrayList arraylist = new ArrayList(k);
        if(!flag) goto _L7; else goto _L6
_L6:
        arraylist.addAll(rrs.subList(i, k));
        if(i != 0)
            arraylist.addAll(rrs.subList(0, i));
_L9:
        iterator1 = arraylist.iterator();
          goto _L8
_L13:
        if(position >= k)
            position = 0;
        i = position;
        position = (short)(i + 1);
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L11:
        i = j - nsigs;
        break; /* Loop/switch isn't completed */
_L7:
        arraylist.addAll(rrs.subList(i, j));
          goto _L9
_L4:
        if(!flag) goto _L11; else goto _L10
_L10:
        if(flag1) goto _L13; else goto _L12
    }

    private String iteratorToString(Iterator iterator1)
    {
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            if(!iterator1.hasNext())
                break;
            Record record = (Record)iterator1.next();
            stringbuffer.append("[");
            stringbuffer.append(record.rdataToString());
            stringbuffer.append("]");
            if(iterator1.hasNext())
                stringbuffer.append(" ");
        } while(true);
        return stringbuffer.toString();
    }

    private void safeAddRR(Record record)
    {
        if(!(record instanceof RRSIGRecord))
        {
            if(nsigs == 0)
                rrs.add(record);
            else
                rrs.add(rrs.size() - nsigs, record);
        } else
        {
            rrs.add(record);
            nsigs = 1 + nsigs;
        }
    }

    /**
     * @deprecated Method addRR is deprecated
     */

    public void addRR(Record record)
    {
        this;
        JVM INSTR monitorenter ;
        if(rrs.size() != 0) goto _L2; else goto _L1
_L1:
        safeAddRR(record);
_L5:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        Record record1;
        record1 = first();
        if(!record.sameRRset(record1))
            throw new IllegalArgumentException("record does not match rrset");
        break MISSING_BLOCK_LABEL_50;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(record.getTTL() == record1.getTTL()) goto _L4; else goto _L3
_L3:
        Record record2;
        if(record.getTTL() <= record1.getTTL())
            break MISSING_BLOCK_LABEL_184;
        Record record4 = record.cloneRecord();
        record4.setTTL(record1.getTTL());
        record2 = record4;
_L8:
        if(!rrs.contains(record2))
            safeAddRR(record2);
          goto _L5
_L7:
        int i;
        if(i >= rrs.size()) goto _L4; else goto _L6
_L6:
        Record record3 = ((Record)rrs.get(i)).cloneRecord();
        record3.setTTL(record.getTTL());
        rrs.set(i, record3);
        i++;
          goto _L7
_L4:
        record2 = record;
          goto _L8
        i = 0;
          goto _L7
    }

    /**
     * @deprecated Method clear is deprecated
     */

    public void clear()
    {
        this;
        JVM INSTR monitorenter ;
        rrs.clear();
        position = 0;
        nsigs = 0;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method deleteRR is deprecated
     */

    public void deleteRR(Record record)
    {
        this;
        JVM INSTR monitorenter ;
        if(rrs.remove(record) && (record instanceof RRSIGRecord))
            nsigs = nsigs - 1;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method first is deprecated
     */

    public Record first()
    {
        this;
        JVM INSTR monitorenter ;
        if(rrs.size() == 0)
            throw new IllegalStateException("rrset is empty");
        break MISSING_BLOCK_LABEL_29;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        Record record = (Record)rrs.get(0);
        this;
        JVM INSTR monitorexit ;
        return record;
    }

    public int getDClass()
    {
        return first().getDClass();
    }

    public Name getName()
    {
        return first().getName();
    }

    /**
     * @deprecated Method getTTL is deprecated
     */

    public long getTTL()
    {
        this;
        JVM INSTR monitorenter ;
        long l = first().getTTL();
        this;
        JVM INSTR monitorexit ;
        return l;
        Exception exception;
        exception;
        throw exception;
    }

    public int getType()
    {
        return first().getRRsetType();
    }

    /**
     * @deprecated Method rrs is deprecated
     */

    public Iterator rrs()
    {
        this;
        JVM INSTR monitorenter ;
        Iterator iterator1 = iterator(true, true);
        this;
        JVM INSTR monitorexit ;
        return iterator1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method rrs is deprecated
     */

    public Iterator rrs(boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        Iterator iterator1 = iterator(true, flag);
        this;
        JVM INSTR monitorexit ;
        return iterator1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method sigs is deprecated
     */

    public Iterator sigs()
    {
        this;
        JVM INSTR monitorenter ;
        Iterator iterator1 = iterator(false, false);
        this;
        JVM INSTR monitorexit ;
        return iterator1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method size is deprecated
     */

    public int size()
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        short word0;
        i = rrs.size();
        word0 = nsigs;
        int j = i - word0;
        this;
        JVM INSTR monitorexit ;
        return j;
        Exception exception;
        exception;
        throw exception;
    }

    public String toString()
    {
        String s;
        if(rrs == null)
        {
            s = "{empty}";
        } else
        {
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append("{ ");
            stringbuffer.append((new StringBuilder()).append(getName()).append(" ").toString());
            stringbuffer.append((new StringBuilder()).append(getTTL()).append(" ").toString());
            stringbuffer.append((new StringBuilder()).append(DClass.string(getDClass())).append(" ").toString());
            stringbuffer.append((new StringBuilder()).append(Type.string(getType())).append(" ").toString());
            stringbuffer.append(iteratorToString(iterator(true, false)));
            if(nsigs > 0)
            {
                stringbuffer.append(" sigs: ");
                stringbuffer.append(iteratorToString(iterator(false, false)));
            }
            stringbuffer.append(" }");
            s = stringbuffer.toString();
        }
        return s;
    }

    private static final long serialVersionUID = 0xdc5eb2f1L;
    private short nsigs;
    private short position;
    private List rrs;
}
