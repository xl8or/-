// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Zone.java

package org.xbill.DNS;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            ZoneTransferException, ZoneTransferIn, Master, Name, 
//            RRset, Record, SetResponse, SOARecord

public class Zone
    implements Serializable
{
    class ZoneIterator
        implements Iterator
    {

        public boolean hasNext()
        {
            boolean flag;
            if(current != null || wantLastSOA)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public Object next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            if(current != null) goto _L2; else goto _L1
_L1:
            RRset rrset1;
            wantLastSOA = false;
            rrset1 = oneRRset(originNode, 6);
_L4:
            return rrset1;
_L2:
            RRset rrset;
label0:
            {
                RRset arrset[] = current;
                int i = count;
                count = i + 1;
                rrset = arrset[i];
                if(count != current.length)
                    break label0;
                current = null;
                RRset arrset1[];
                do
                {
                    java.util.Map.Entry entry;
                    do
                    {
                        if(!zentries.hasNext())
                            break label0;
                        entry = (java.util.Map.Entry)zentries.next();
                    } while(entry.getKey().equals(origin));
                    arrset1 = allRRsets(entry.getValue());
                } while(arrset1.length == 0);
                current = arrset1;
                count = 0;
            }
            rrset1 = rrset;
            if(true) goto _L4; else goto _L3
_L3:
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        private int count;
        private RRset current[];
        final Zone this$0;
        private boolean wantLastSOA;
        private Iterator zentries;

        ZoneIterator(boolean flag)
        {
            this$0 = Zone.this;
            super();
            Zone.this;
            JVM INSTR monitorenter ;
            zentries = data.entrySet().iterator();
            Zone.this;
            JVM INSTR monitorexit ;
            wantLastSOA = flag;
            RRset arrset[] = allRRsets(originNode);
            current = new RRset[arrset.length];
            int i = 2;
            int j = 0;
            while(j < arrset.length) 
            {
                int k = arrset[j].getType();
                Exception exception;
                if(k == 6)
                    current[0] = arrset[j];
                else
                if(k == 2)
                {
                    current[1] = arrset[j];
                } else
                {
                    RRset arrset1[] = current;
                    int l = i + 1;
                    arrset1[i] = arrset[j];
                    i = l;
                }
                j++;
            }
            break MISSING_BLOCK_LABEL_158;
            exception;
            Zone.this;
            JVM INSTR monitorexit ;
            throw exception;
        }
    }


    public Zone(Name name, int i, String s)
        throws IOException, ZoneTransferException
    {
        dclass = 1;
        ZoneTransferIn zonetransferin = ZoneTransferIn.newAXFR(name, s, null);
        zonetransferin.setDClass(i);
        fromXFR(zonetransferin);
    }

    public Zone(Name name, String s)
        throws IOException
    {
        dclass = 1;
        data = new TreeMap();
        if(name == null)
            throw new IllegalArgumentException("no zone name specified");
        Master master = new Master(s, name);
        origin = name;
        do
        {
            Record record = master.nextRecord();
            if(record != null)
            {
                maybeAddRecord(record);
            } else
            {
                validate();
                return;
            }
        } while(true);
    }

    public Zone(Name name, Record arecord[])
        throws IOException
    {
        dclass = 1;
        data = new TreeMap();
        if(name == null)
            throw new IllegalArgumentException("no zone name specified");
        origin = name;
        for(int i = 0; i < arecord.length; i++)
            maybeAddRecord(arecord[i]);

        validate();
    }

    public Zone(ZoneTransferIn zonetransferin)
        throws IOException, ZoneTransferException
    {
        dclass = 1;
        fromXFR(zonetransferin);
    }

    /**
     * @deprecated Method addRRset is deprecated
     */

    private void addRRset(Name name, RRset rrset)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj;
        if(!hasWild && name.isWild())
            hasWild = true;
        obj = data.get(name);
        if(obj != null) goto _L2; else goto _L1
_L1:
        data.put(name, rrset);
_L3:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        int i;
        List list;
        int j;
        i = rrset.getType();
        if(!(obj instanceof List))
            break MISSING_BLOCK_LABEL_146;
        list = (List)obj;
        j = 0;
_L4:
        if(j >= list.size())
            break MISSING_BLOCK_LABEL_134;
        if(((RRset)list.get(j)).getType() != i)
            break MISSING_BLOCK_LABEL_128;
        list.set(j, rrset);
          goto _L3
        Exception exception;
        exception;
        throw exception;
        j++;
          goto _L4
        list.add(rrset);
          goto _L3
        RRset rrset1 = (RRset)obj;
        if(rrset1.getType() == i)
        {
            data.put(name, rrset);
        } else
        {
            LinkedList linkedlist = new LinkedList();
            linkedlist.add(rrset1);
            linkedlist.add(rrset);
            data.put(name, linkedlist);
        }
          goto _L3
    }

    /**
     * @deprecated Method allRRsets is deprecated
     */

    private RRset[] allRRsets(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        RRset arrset[];
        if(!(obj instanceof List))
            break MISSING_BLOCK_LABEL_45;
        List list = (List)obj;
        arrset = (RRset[])(RRset[])list.toArray(new RRset[list.size()]);
_L1:
        this;
        JVM INSTR monitorexit ;
        return arrset;
        RRset rrset = (RRset)obj;
        arrset = new RRset[1];
        arrset[0] = rrset;
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method exactName is deprecated
     */

    private Object exactName(Name name)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = data.get(name);
        this;
        JVM INSTR monitorexit ;
        return obj;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method findRRset is deprecated
     */

    private RRset findRRset(Name name, int i)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = exactName(name);
        if(obj != null) goto _L2; else goto _L1
_L1:
        RRset rrset1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return rrset1;
_L2:
        RRset rrset = oneRRset(obj, i);
        rrset1 = rrset;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    private void fromXFR(ZoneTransferIn zonetransferin)
        throws IOException, ZoneTransferException
    {
        data = new TreeMap();
        origin = zonetransferin.getName();
        for(Iterator iterator1 = zonetransferin.run().iterator(); iterator1.hasNext(); maybeAddRecord((Record)iterator1.next()));
        if(!zonetransferin.isAXFR())
        {
            throw new IllegalArgumentException("zones can only be created from AXFRs");
        } else
        {
            validate();
            return;
        }
    }

    /**
     * @deprecated Method lookup is deprecated
     */

    private SetResponse lookup(Name name, int i)
    {
        this;
        JVM INSTR monitorenter ;
        if(name.subdomain(origin)) goto _L2; else goto _L1
_L1:
        SetResponse setresponse2 = SetResponse.ofType(1);
        SetResponse setresponse1 = setresponse2;
_L9:
        this;
        JVM INSTR monitorexit ;
        return setresponse1;
_L2:
        int j;
        int k;
        int l;
        j = name.labels();
        k = origin.labels();
        l = k;
          goto _L3
_L16:
        boolean flag;
        if(!flag) goto _L5; else goto _L4
_L4:
        Name name1 = origin;
_L8:
        Object obj1 = exactName(name1);
        if(obj1 != null) goto _L7; else goto _L6
_L18:
        name1 = new Name(name, j - l);
          goto _L8
        Exception exception;
        exception;
        throw exception;
_L7:
        if(flag)
            break MISSING_BLOCK_LABEL_134;
        RRset rrset4 = oneRRset(obj1, 2);
        if(rrset4 == null)
            break MISSING_BLOCK_LABEL_134;
        setresponse1 = new SetResponse(3, rrset4);
          goto _L9
        boolean flag1;
        if(flag1 && i == 255)
        {
            setresponse1 = new SetResponse(6);
            RRset arrset[] = allRRsets(obj1);
            int j1 = 0;
            while(j1 < arrset.length) 
            {
                setresponse1.addRRset(arrset[j1]);
                j1++;
            }
        } else
        if(flag1)
        {
            RRset rrset2 = oneRRset(obj1, i);
            if(rrset2 != null)
            {
                setresponse1 = new SetResponse(6);
                setresponse1.addRRset(rrset2);
            } else
            {
                RRset rrset3 = oneRRset(obj1, 5);
                if(rrset3 == null)
                    break MISSING_BLOCK_LABEL_291;
                setresponse1 = new SetResponse(4, rrset3);
            }
        } else
        {
            RRset rrset1 = oneRRset(obj1, 39);
            if(rrset1 == null)
                break MISSING_BLOCK_LABEL_291;
            setresponse1 = new SetResponse(5, rrset1);
        }
          goto _L9
        if(!flag1) goto _L6; else goto _L10
_L10:
        setresponse1 = SetResponse.ofType(2);
          goto _L9
_L15:
        if(!hasWild) goto _L12; else goto _L11
_L11:
        int i1 = 0;
_L19:
        if(i1 >= j - k) goto _L12; else goto _L13
_L13:
        Object obj = exactName(name.wild(i1 + 1));
        if(obj == null)
            break MISSING_BLOCK_LABEL_454;
        RRset rrset = oneRRset(obj, i);
        if(rrset == null)
            break MISSING_BLOCK_LABEL_454;
        setresponse1 = new SetResponse(6);
        setresponse1.addRRset(rrset);
          goto _L9
_L12:
        SetResponse setresponse = SetResponse.ofType(1);
        setresponse1 = setresponse;
          goto _L9
_L3:
        if(l > j) goto _L15; else goto _L14
_L14:
        if(l == k)
            flag = true;
        else
            flag = false;
        if(l == j)
            flag1 = true;
        else
            flag1 = false;
          goto _L16
_L6:
        l++;
          goto _L3
_L5:
        if(!flag1) goto _L18; else goto _L17
_L17:
        name1 = name;
          goto _L8
        i1++;
          goto _L19
    }

    private final void maybeAddRecord(Record record)
        throws IOException
    {
        int i = record.getType();
        Name name = record.getName();
        if(i == 6 && !name.equals(origin))
            throw new IOException((new StringBuilder()).append("SOA owner ").append(name).append(" does not match zone origin ").append(origin).toString());
        if(name.subdomain(origin))
            addRecord(record);
    }

    private void nodeToString(StringBuffer stringbuffer, Object obj)
    {
        RRset arrset[] = allRRsets(obj);
        for(int i = 0; i < arrset.length; i++)
        {
            RRset rrset = arrset[i];
            for(Iterator iterator1 = rrset.rrs(); iterator1.hasNext(); stringbuffer.append((new StringBuilder()).append(iterator1.next()).append("\n").toString()));
            for(Iterator iterator2 = rrset.sigs(); iterator2.hasNext(); stringbuffer.append((new StringBuilder()).append(iterator2.next()).append("\n").toString()));
        }

    }

    /**
     * @deprecated Method oneRRset is deprecated
     */

    private RRset oneRRset(Object obj, int i)
    {
        this;
        JVM INSTR monitorenter ;
        if(i != 255)
            break MISSING_BLOCK_LABEL_27;
        throw new IllegalArgumentException("oneRRset(ANY)");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(!(obj instanceof List)) goto _L2; else goto _L1
_L1:
        List list;
        int k;
        list = (List)obj;
        k = 0;
_L5:
        RRset rrset1;
        int l;
        if(k >= list.size())
            break MISSING_BLOCK_LABEL_116;
        rrset1 = (RRset)list.get(k);
        l = rrset1.getType();
        if(l != i) goto _L4; else goto _L3
_L3:
        this;
        JVM INSTR monitorexit ;
        return rrset1;
_L4:
        k++;
          goto _L5
_L2:
        RRset rrset;
        int j;
        rrset = (RRset)obj;
        j = rrset.getType();
        if(j != i)
            break MISSING_BLOCK_LABEL_116;
        rrset1 = rrset;
          goto _L3
        rrset1 = null;
          goto _L3
    }

    /**
     * @deprecated Method removeRRset is deprecated
     */

    private void removeRRset(Name name, int i)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = data.get(name);
        if(obj != null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(!(obj instanceof List)) goto _L4; else goto _L3
_L3:
        List list;
        int j;
        list = (List)obj;
        j = 0;
_L5:
        if(j < list.size())
        {
            if(((RRset)list.get(j)).getType() != i)
                break MISSING_BLOCK_LABEL_110;
            list.remove(j);
            if(list.size() == 0)
                data.remove(name);
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
        j++;
          goto _L5
_L4:
        if(((RRset)obj).getType() != i) goto _L1; else goto _L6
_L6:
        data.remove(name);
          goto _L1
    }

    private void validate()
        throws IOException
    {
        originNode = exactName(origin);
        if(originNode == null)
            throw new IOException((new StringBuilder()).append(origin).append(": no data specified").toString());
        RRset rrset = oneRRset(originNode, 6);
        if(rrset == null || rrset.size() != 1)
            throw new IOException((new StringBuilder()).append(origin).append(": exactly 1 SOA must be specified").toString());
        SOA = (SOARecord)rrset.rrs().next();
        NS = oneRRset(originNode, 2);
        if(NS == null)
            throw new IOException((new StringBuilder()).append(origin).append(": no NS set specified").toString());
        else
            return;
    }

    public Iterator AXFR()
    {
        return new ZoneIterator(true);
    }

    public void addRRset(RRset rrset)
    {
        addRRset(rrset.getName(), rrset);
    }

    public void addRecord(Record record)
    {
        Name name = record.getName();
        int i = record.getRRsetType();
        this;
        JVM INSTR monitorenter ;
        RRset rrset = findRRset(name, i);
        if(rrset == null)
            addRRset(name, new RRset(record));
        else
            rrset.addRR(record);
        return;
    }

    public RRset findExactMatch(Name name, int i)
    {
        Object obj = exactName(name);
        RRset rrset;
        if(obj == null)
            rrset = null;
        else
            rrset = oneRRset(obj, i);
        return rrset;
    }

    public SetResponse findRecords(Name name, int i)
    {
        return lookup(name, i);
    }

    public int getDClass()
    {
        return dclass;
    }

    public RRset getNS()
    {
        return NS;
    }

    public Name getOrigin()
    {
        return origin;
    }

    public SOARecord getSOA()
    {
        return SOA;
    }

    public Iterator iterator()
    {
        return new ZoneIterator(false);
    }

    public void removeRecord(Record record)
    {
        Name name = record.getName();
        int i = record.getRRsetType();
        this;
        JVM INSTR monitorenter ;
        RRset rrset = findRRset(name, i);
        if(rrset != null) goto _L2; else goto _L1
_L2:
        if(rrset.size() != 1 || !rrset.first().equals(record)) goto _L4; else goto _L3
_L3:
        removeRRset(name, i);
_L5:
        this;
        JVM INSTR monitorexit ;
        break; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L4:
        rrset.deleteRR(record);
        if(true) goto _L5; else goto _L1
_L1:
    }

    /**
     * @deprecated Method toMasterFile is deprecated
     */

    public String toMasterFile()
    {
        this;
        JVM INSTR monitorenter ;
        StringBuffer stringbuffer;
        Iterator iterator1 = data.entrySet().iterator();
        stringbuffer = new StringBuffer();
        nodeToString(stringbuffer, originNode);
        do
        {
            if(!iterator1.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
            if(!origin.equals(entry.getKey()))
                nodeToString(stringbuffer, entry.getValue());
        } while(true);
        break MISSING_BLOCK_LABEL_91;
        Exception exception;
        exception;
        throw exception;
        String s = stringbuffer.toString();
        this;
        JVM INSTR monitorexit ;
        return s;
    }

    public String toString()
    {
        return toMasterFile();
    }

    public static final int PRIMARY = 1;
    public static final int SECONDARY = 2;
    private static final long serialVersionUID = 0x63075ce2L;
    private RRset NS;
    private SOARecord SOA;
    private Map data;
    private int dclass;
    private boolean hasWild;
    private Name origin;
    private Object originNode;





}
