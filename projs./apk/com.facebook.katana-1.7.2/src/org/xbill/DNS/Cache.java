// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cache.java

package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            Master, SetResponse, RRset, Record, 
//            NameTooLongException, Message, Header, Options, 
//            Name, CNAMERecord, DNAMERecord, SOARecord, 
//            Type

public class Cache
{
    private static class CacheMap extends LinkedHashMap
    {

        int getMaxSize()
        {
            return maxsize;
        }

        protected boolean removeEldestEntry(java.util.Map.Entry entry)
        {
            boolean flag;
            if(maxsize >= 0 && size() > maxsize)
                flag = true;
            else
                flag = false;
            return flag;
        }

        void setMaxSize(int i)
        {
            maxsize = i;
        }

        private int maxsize;

        CacheMap(int i)
        {
            super(16, 0.75F, true);
            maxsize = -1;
            maxsize = i;
        }
    }

    private static class NegativeElement
        implements Element
    {

        public final int compareCredibility(int i)
        {
            return credibility - i;
        }

        public final boolean expired()
        {
            boolean flag;
            if((int)(System.currentTimeMillis() / 1000L) >= expire)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public int getType()
        {
            return type;
        }

        public String toString()
        {
            StringBuffer stringbuffer = new StringBuffer();
            if(type == 0)
                stringbuffer.append((new StringBuilder()).append("NXDOMAIN ").append(name).toString());
            else
                stringbuffer.append((new StringBuilder()).append("NXRRSET ").append(name).append(" ").append(Type.string(type)).toString());
            stringbuffer.append(" cl = ");
            stringbuffer.append(credibility);
            return stringbuffer.toString();
        }

        int credibility;
        int expire;
        Name name;
        int type;

        public NegativeElement(Name name1, int i, SOARecord soarecord, int j, long l)
        {
            name = name1;
            type = i;
            long l1 = 0L;
            if(soarecord != null)
                l1 = soarecord.getMinimum();
            credibility = j;
            expire = Cache.limitExpire(l1, l);
        }
    }

    private static class CacheRRset extends RRset
        implements Element
    {

        public final int compareCredibility(int i)
        {
            return credibility - i;
        }

        public final boolean expired()
        {
            boolean flag;
            if((int)(System.currentTimeMillis() / 1000L) >= expire)
                flag = true;
            else
                flag = false;
            return flag;
        }

        public String toString()
        {
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append(super.toString());
            stringbuffer.append(" cl = ");
            stringbuffer.append(credibility);
            return stringbuffer.toString();
        }

        private static final long serialVersionUID = 0x55793de0L;
        int credibility;
        int expire;

        public CacheRRset(RRset rrset, int i, long l)
        {
            super(rrset);
            credibility = i;
            expire = Cache.limitExpire(rrset.getTTL(), l);
        }

        public CacheRRset(Record record, int i, long l)
        {
            credibility = i;
            expire = Cache.limitExpire(record.getTTL(), l);
            addRR(record);
        }
    }

    private static interface Element
    {

        public abstract int compareCredibility(int i);

        public abstract boolean expired();

        public abstract int getType();
    }


    public Cache()
    {
        this(1);
    }

    public Cache(int i)
    {
        maxncache = -1;
        maxcache = -1;
        dclass = i;
        data = new CacheMap(50000);
    }

    public Cache(String s)
        throws IOException
    {
        maxncache = -1;
        maxcache = -1;
        data = new CacheMap(50000);
        Master master = new Master(s);
        do
        {
            Record record = master.nextRecord();
            if(record != null)
                addRecord(record, 0, master);
            else
                return;
        } while(true);
    }

    /**
     * @deprecated Method addElement is deprecated
     */

    private void addElement(Name name, Element element)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = data.get(name);
        if(obj != null) goto _L2; else goto _L1
_L1:
        data.put(name, element);
_L3:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        int i;
        List list;
        int j;
        i = element.getType();
        if(!(obj instanceof List))
            break MISSING_BLOCK_LABEL_127;
        list = (List)obj;
        j = 0;
_L4:
        if(j >= list.size())
            break MISSING_BLOCK_LABEL_115;
        if(((Element)list.get(j)).getType() != i)
            break MISSING_BLOCK_LABEL_109;
        list.set(j, element);
          goto _L3
        Exception exception;
        exception;
        throw exception;
        j++;
          goto _L4
        list.add(element);
          goto _L3
        Element element1 = (Element)obj;
        if(element1.getType() == i)
        {
            data.put(name, element);
        } else
        {
            LinkedList linkedlist = new LinkedList();
            linkedlist.add(element1);
            linkedlist.add(element);
            data.put(name, linkedlist);
        }
          goto _L3
    }

    /**
     * @deprecated Method allElements is deprecated
     */

    private Element[] allElements(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        Element aelement[];
        if(!(obj instanceof List))
            break MISSING_BLOCK_LABEL_45;
        List list = (List)obj;
        aelement = (Element[])(Element[])list.toArray(new Element[list.size()]);
_L1:
        this;
        JVM INSTR monitorexit ;
        return aelement;
        Element element = (Element)obj;
        aelement = new Element[1];
        aelement[0] = element;
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
     * @deprecated Method findElement is deprecated
     */

    private Element findElement(Name name, int i, int j)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = exactName(name);
        if(obj != null) goto _L2; else goto _L1
_L1:
        Element element1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return element1;
_L2:
        Element element = oneElement(name, obj, i, j);
        element1 = element;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    private RRset[] findRecords(Name name, int i, int j)
    {
        SetResponse setresponse = lookupRecords(name, i, j);
        RRset arrset[];
        if(setresponse.isSuccessful())
            arrset = setresponse.answers();
        else
            arrset = null;
        return arrset;
    }

    private final int getCred(int i, boolean flag)
    {
        byte byte0;
        if(i == 1)
        {
            if(flag)
                byte0 = 4;
            else
                byte0 = 3;
        } else
        if(i == 2)
        {
            if(flag)
                byte0 = 4;
            else
                byte0 = 3;
        } else
        if(i == 3)
            byte0 = 1;
        else
            throw new IllegalArgumentException("getCred: invalid section");
        return byte0;
    }

    private static int limitExpire(long l, long l1)
    {
        long l2;
        long l3;
        int i;
        if(l1 >= 0L && l1 < l)
            l2 = l1;
        else
            l2 = l;
        l3 = l2 + System.currentTimeMillis() / 1000L;
        if(l3 < 0L || l3 > 0x7fffffffL)
            i = 0x7fffffff;
        else
            i = (int)l3;
        return i;
    }

    private static void markAdditional(RRset rrset, Set set)
    {
        if(rrset.first().getAdditionalName() != null)
        {
            Iterator iterator = rrset.rrs();
            while(iterator.hasNext()) 
            {
                Name name = ((Record)iterator.next()).getAdditionalName();
                if(name != null)
                    set.add(name);
            }
        }
    }

    /**
     * @deprecated Method oneElement is deprecated
     */

    private Element oneElement(Name name, Object obj, int i, int j)
    {
        this;
        JVM INSTR monitorenter ;
        if(i != 255)
            break MISSING_BLOCK_LABEL_26;
        throw new IllegalArgumentException("oneElement(ANY)");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(!(obj instanceof List)) goto _L2; else goto _L1
_L1:
        List list;
        int l;
        list = (List)obj;
        l = 0;
_L7:
        Element element1;
        int i1;
        if(l >= list.size())
            break MISSING_BLOCK_LABEL_170;
        element1 = (Element)list.get(l);
        i1 = element1.getType();
        if(i1 != i) goto _L4; else goto _L3
_L3:
        if(element1 != null) goto _L6; else goto _L5
_L5:
        element1 = null;
_L10:
        this;
        JVM INSTR monitorexit ;
        return element1;
_L4:
        l++;
          goto _L7
_L2:
        Element element = (Element)obj;
        if(element.getType() != i)
            break MISSING_BLOCK_LABEL_170;
        element1 = element;
          goto _L3
_L6:
        if(!element1.expired()) goto _L9; else goto _L8
_L8:
        removeElement(name, i);
        element1 = null;
          goto _L10
_L9:
        int k = element1.compareCredibility(j);
        if(k < 0)
            element1 = null;
          goto _L10
        element1 = null;
          goto _L3
    }

    /**
     * @deprecated Method removeElement is deprecated
     */

    private void removeElement(Name name, int i)
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
            if(((Element)list.get(j)).getType() != i)
                break MISSING_BLOCK_LABEL_108;
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
        if(((Element)obj).getType() != i) goto _L1; else goto _L6
_L6:
        data.remove(name);
          goto _L1
    }

    /**
     * @deprecated Method removeName is deprecated
     */

    private void removeName(Name name)
    {
        this;
        JVM INSTR monitorenter ;
        data.remove(name);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public SetResponse addMessage(Message message)
    {
        boolean flag;
        Record record;
        int i;
        boolean flag1;
        flag = message.getHeader().getFlag(5);
        record = message.getQuestion();
        i = message.getHeader().getRcode();
        flag1 = Options.check("verbosecache");
        if((i == 0 || i == 3) && record != null) goto _L2; else goto _L1
_L1:
        SetResponse setresponse2 = null;
_L12:
        return setresponse2;
_L2:
        Name name;
        int j;
        int k;
        HashSet hashset;
        RRset arrset[];
        Name name1;
        boolean flag2;
        int l;
        SetResponse setresponse;
        name = record.getName();
        j = record.getType();
        k = record.getDClass();
        hashset = new HashSet();
        arrset = message.getSectionRRsets(1);
        name1 = name;
        flag2 = false;
        l = 0;
        setresponse = null;
_L4:
        int i1 = arrset.length;
        if(l >= i1)
            break MISSING_BLOCK_LABEL_857;
        if(arrset[l].getDClass() == k)
            break; /* Loop/switch isn't completed */
_L5:
        l++;
        if(true) goto _L4; else goto _L3
_L3:
        int l3;
        Name name2;
        int i4;
label0:
        {
            l3 = arrset[l].getType();
            name2 = arrset[l].getName();
            i4 = getCred(1, flag);
            if(l3 != j && j != 255 || !name2.equals(name1))
                break label0;
            addRRset(arrset[l], i4);
            flag2 = true;
            if(name1 == name)
            {
                if(setresponse == null)
                    setresponse = new SetResponse(6);
                RRset rrset2 = arrset[l];
                setresponse.addRRset(rrset2);
            }
            markAdditional(arrset[l], hashset);
        }
          goto _L5
        if(l3 != 5 || !name2.equals(name1)) goto _L7; else goto _L6
_L6:
        SetResponse setresponse1;
        addRRset(arrset[l], i4);
        RRset arrset1[];
        RRset rrset;
        RRset rrset1;
        int j1;
        int k1;
        RRset arrset2[];
        int l1;
        int i2;
        int j2;
        int k2;
        int l2;
        int i3;
        SOARecord soarecord;
        int j3;
        int k3;
        SetResponse setresponse3;
        SetResponse setresponse4;
        DNAMERecord dnamerecord;
        NameTooLongException nametoolongexception;
        Name name3;
        SetResponse setresponse6;
        Name name4;
        SetResponse setresponse7;
        if(name1 == name)
        {
            SetResponse setresponse5 = new SetResponse(4, arrset[l]);
            setresponse6 = setresponse5;
        } else
        {
            setresponse6 = setresponse;
        }
        name4 = ((CNAMERecord)arrset[l].first()).getTarget();
        setresponse7 = setresponse6;
        name1 = name4;
        setresponse = setresponse7;
          goto _L5
_L7:
        if(l3 != 39 || !name1.subdomain(name2)) goto _L5; else goto _L8
_L8:
        addRRset(arrset[l], i4);
        if(name1 == name)
        {
            setresponse3 = new SetResponse(5, arrset[l]);
            setresponse4 = setresponse3;
        } else
        {
            setresponse4 = setresponse;
        }
        dnamerecord = (DNAMERecord)arrset[l].first();
        name3 = name1.fromDNAME(dnamerecord);
        name1 = name3;
        setresponse = setresponse4;
          goto _L5
        nametoolongexception;
        setresponse1 = setresponse4;
_L13:
        arrset1 = message.getSectionRRsets(2);
        rrset = null;
        rrset1 = null;
        j1 = 0;
        do
        {
            k1 = arrset1.length;
            if(j1 >= k1)
                break;
            if(arrset1[j1].getType() == 6 && name1.subdomain(arrset1[j1].getName()))
                rrset1 = arrset1[j1];
            else
            if(arrset1[j1].getType() == 2 && name1.subdomain(arrset1[j1].getName()))
                rrset = arrset1[j1];
            j1++;
        } while(true);
        if(flag2) goto _L10; else goto _L9
_L9:
        if(i == 3)
            l2 = 0;
        else
            l2 = j;
        if(i == 3 || rrset1 != null || rrset == null)
        {
            i3 = getCred(2, flag);
            soarecord = null;
            if(rrset1 != null)
                soarecord = (SOARecord)rrset1.first();
            addNegative(name1, l2, soarecord, i3);
            if(setresponse1 == null)
            {
                if(i == 3)
                    j3 = 1;
                else
                    j3 = 2;
                setresponse2 = SetResponse.ofType(j3);
                break MISSING_BLOCK_LABEL_620;
            }
            break MISSING_BLOCK_LABEL_763;
        }
        k3 = getCred(2, flag);
        addRRset(rrset, k3);
        markAdditional(rrset, hashset);
        if(setresponse1 != null)
            break MISSING_BLOCK_LABEL_763;
        setresponse2 = new SetResponse(3, rrset);
          goto _L11
_L10:
        if(i == 0 && rrset != null)
        {
            k2 = getCred(2, flag);
            addRRset(rrset, k2);
            markAdditional(rrset, hashset);
        }
        setresponse2 = setresponse1;
_L11:
        arrset2 = message.getSectionRRsets(3);
        l1 = 0;
        do
        {
            if(l1 >= arrset2.length)
                break;
            i2 = arrset2[l1].getType();
            if((i2 == 1 || i2 == 28 || i2 == 38) && hashset.contains(arrset2[l1].getName()))
            {
                j2 = getCred(3, flag);
                addRRset(arrset2[l1], j2);
            }
            l1++;
        } while(true);
        if(flag1)
            System.out.println((new StringBuilder()).append("addMessage: ").append(setresponse2).toString());
          goto _L12
        setresponse1 = setresponse;
          goto _L13
    }

    /**
     * @deprecated Method addNegative is deprecated
     */

    public void addNegative(Name name, int i, SOARecord soarecord, int j)
    {
        this;
        JVM INSTR monitorenter ;
        if(soarecord == null)
            break MISSING_BLOCK_LABEL_120;
        long l = soarecord.getTTL();
_L5:
        Element element = findElement(name, i, 0);
        if(l != 0L) goto _L2; else goto _L1
_L1:
        if(element != null && element.compareCredibility(j) <= 0)
            removeElement(name, i);
_L3:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(element == null)
            break MISSING_BLOCK_LABEL_113;
        Element element1;
        if(element.compareCredibility(j) > 0)
            break MISSING_BLOCK_LABEL_113;
        element1 = null;
_L4:
        if(element1 == null)
            addElement(name, new NegativeElement(name, i, soarecord, j, maxncache));
          goto _L3
        Exception exception;
        exception;
        throw exception;
        element1 = element;
          goto _L4
        l = 0L;
          goto _L5
    }

    /**
     * @deprecated Method addRRset is deprecated
     */

    public void addRRset(RRset rrset, int i)
    {
        this;
        JVM INSTR monitorenter ;
        long l;
        Name name;
        int j;
        Element element;
        l = rrset.getTTL();
        name = rrset.getName();
        j = rrset.getType();
        element = findElement(name, j, 0);
        if(l != 0L) goto _L2; else goto _L1
_L1:
        if(element != null && element.compareCredibility(i) <= 0)
            removeElement(name, j);
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(element == null)
            break MISSING_BLOCK_LABEL_139;
        Element element1;
        if(element.compareCredibility(i) > 0)
            break MISSING_BLOCK_LABEL_139;
        element1 = null;
_L8:
        if(element1 != null) goto _L4; else goto _L3
_L3:
        if(!(rrset instanceof CacheRRset)) goto _L6; else goto _L5
_L5:
        CacheRRset cacherrset = (CacheRRset)rrset;
_L7:
        addElement(name, cacherrset);
          goto _L4
        Exception exception;
        exception;
        throw exception;
_L6:
        cacherrset = new CacheRRset(rrset, i, maxcache);
          goto _L7
        element1 = element;
          goto _L8
    }

    /**
     * @deprecated Method addRecord is deprecated
     */

    public void addRecord(Record record, int i, Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        Name name;
        int j;
        boolean flag;
        name = record.getName();
        j = record.getRRsetType();
        flag = Type.isRR(j);
        if(flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        Element element = findElement(name, j, i);
        if(element != null) goto _L4; else goto _L3
_L3:
        addRRset(new CacheRRset(record, i, maxcache), i);
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L4:
        if(element.compareCredibility(i) != 0 || !(element instanceof CacheRRset)) goto _L1; else goto _L5
_L5:
        ((CacheRRset)element).addRR(record);
          goto _L1
    }

    /**
     * @deprecated Method clearCache is deprecated
     */

    public void clearCache()
    {
        this;
        JVM INSTR monitorenter ;
        data.clear();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public RRset[] findAnyRecords(Name name, int i)
    {
        return findRecords(name, i, 2);
    }

    public RRset[] findRecords(Name name, int i)
    {
        return findRecords(name, i, 3);
    }

    public void flushName(Name name)
    {
        removeName(name);
    }

    public void flushSet(Name name, int i)
    {
        removeElement(name, i);
    }

    public int getDClass()
    {
        return dclass;
    }

    public int getMaxCache()
    {
        return maxcache;
    }

    public int getMaxEntries()
    {
        return data.getMaxSize();
    }

    public int getMaxNCache()
    {
        return maxncache;
    }

    public int getSize()
    {
        return data.size();
    }

    /**
     * @deprecated Method lookup is deprecated
     */

    protected SetResponse lookup(Name name, int i, int j)
    {
        this;
        JVM INSTR monitorenter ;
        int k;
        int l;
        k = name.labels();
        l = k;
          goto _L1
_L16:
        boolean flag;
        if(!flag) goto _L3; else goto _L2
_L2:
        Name name1 = Name.root;
_L6:
        Object obj = data.get(name1);
        if(obj != null) goto _L5; else goto _L4
_L18:
        name1 = new Name(name, k - l);
          goto _L6
_L5:
        boolean flag1;
        if(!flag1 || i != 255) goto _L8; else goto _L7
_L7:
        SetResponse setresponse3;
        Element aelement[];
        int i1;
        int j1;
        setresponse3 = new SetResponse(6);
        aelement = allElements(obj);
        i1 = 0;
        j1 = 0;
_L19:
        if(i1 >= aelement.length) goto _L10; else goto _L9
_L9:
        int k1;
        Element element4 = aelement[i1];
        if(element4.expired())
        {
            removeElement(name1, element4.getType());
            k1 = j1;
            break MISSING_BLOCK_LABEL_520;
        }
        if(!(element4 instanceof CacheRRset))
        {
            k1 = j1;
            break MISSING_BLOCK_LABEL_520;
        }
        if(element4.compareCredibility(j) < 0)
        {
            k1 = j1;
            break MISSING_BLOCK_LABEL_520;
        }
        setresponse3.addRRset((CacheRRset)element4);
        k1 = j1 + 1;
        break MISSING_BLOCK_LABEL_520;
_L10:
        if(j1 <= 0) goto _L8; else goto _L11
_L11:
        SetResponse setresponse1 = setresponse3;
_L12:
        this;
        JVM INSTR monitorexit ;
        return setresponse1;
_L8:
        if(!flag1)
            break MISSING_BLOCK_LABEL_336;
        Element element2 = oneElement(name1, obj, i, j);
        if(element2 != null && (element2 instanceof CacheRRset))
        {
            SetResponse setresponse2 = new SetResponse(6);
            setresponse2.addRRset((CacheRRset)element2);
            setresponse1 = setresponse2;
        } else
        {
            if(element2 == null)
                break MISSING_BLOCK_LABEL_293;
            setresponse1 = new SetResponse(2);
        }
          goto _L12
        Exception exception;
        exception;
        throw exception;
        Element element3 = oneElement(name1, obj, 5, j);
        if(element3 == null || !(element3 instanceof CacheRRset))
            break MISSING_BLOCK_LABEL_380;
        setresponse1 = new SetResponse(4, (CacheRRset)element3);
          goto _L12
        Element element = oneElement(name1, obj, 39, j);
        if(element == null || !(element instanceof CacheRRset))
            break MISSING_BLOCK_LABEL_380;
        setresponse1 = new SetResponse(5, (CacheRRset)element);
          goto _L12
        Element element1 = oneElement(name1, obj, 2, j);
        if(element1 == null || !(element1 instanceof CacheRRset))
            break MISSING_BLOCK_LABEL_423;
        setresponse1 = new SetResponse(3, (CacheRRset)element1);
          goto _L12
        if(!flag1 || oneElement(name1, obj, 0, j) == null) goto _L4; else goto _L13
_L13:
        setresponse1 = SetResponse.ofType(1);
          goto _L12
_L15:
        SetResponse setresponse = SetResponse.ofType(0);
        setresponse1 = setresponse;
          goto _L12
_L1:
        if(l < 1) goto _L15; else goto _L14
_L14:
        if(l == 1)
            flag = true;
        else
            flag = false;
        if(l == k)
            flag1 = true;
        else
            flag1 = false;
          goto _L16
_L4:
        l--;
          goto _L1
_L3:
        if(!flag1) goto _L18; else goto _L17
_L17:
        name1 = name;
          goto _L6
        i1++;
        j1 = k1;
          goto _L19
    }

    public SetResponse lookupRecords(Name name, int i, int j)
    {
        return lookup(name, i, j);
    }

    public void setMaxCache(int i)
    {
        maxcache = i;
    }

    public void setMaxEntries(int i)
    {
        data.setMaxSize(i);
    }

    public void setMaxNCache(int i)
    {
        maxncache = i;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        this;
        JVM INSTR monitorenter ;
        for(Iterator iterator = data.values().iterator(); iterator.hasNext();)
        {
            Element aelement[] = allElements(iterator.next());
            int i = 0;
            while(i < aelement.length) 
            {
                stringbuffer.append(aelement[i]);
                stringbuffer.append("\n");
                i++;
            }
        }

        this;
        JVM INSTR monitorexit ;
        return stringbuffer.toString();
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static final int defaultMaxEntries = 50000;
    private CacheMap data;
    private int dclass;
    private int maxcache;
    private int maxncache;

}
