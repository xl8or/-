// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cache.java

package org.jivesoftware.smack.util;

import java.io.PrintStream;
import java.util.*;
import org.jivesoftware.smack.util.collections.AbstractMapEntry;

public class Cache
    implements Map
{
    private static class LinkedListNode
    {

        public void remove()
        {
            previous.next = next;
            next.previous = previous;
        }

        public String toString()
        {
            return object.toString();
        }

        public LinkedListNode next;
        public Object object;
        public LinkedListNode previous;
        public long timestamp;

        public LinkedListNode(Object obj, LinkedListNode linkedlistnode, LinkedListNode linkedlistnode1)
        {
            object = obj;
            next = linkedlistnode;
            previous = linkedlistnode1;
        }
    }

    private static class LinkedList
    {

        public LinkedListNode addFirst(Object obj)
        {
            LinkedListNode linkedlistnode = new LinkedListNode(obj, head.next, head);
            linkedlistnode.previous.next = linkedlistnode;
            linkedlistnode.next.previous = linkedlistnode;
            return linkedlistnode;
        }

        public LinkedListNode addFirst(LinkedListNode linkedlistnode)
        {
            linkedlistnode.next = head.next;
            linkedlistnode.previous = head;
            linkedlistnode.previous.next = linkedlistnode;
            linkedlistnode.next.previous = linkedlistnode;
            return linkedlistnode;
        }

        public LinkedListNode addLast(Object obj)
        {
            LinkedListNode linkedlistnode = new LinkedListNode(obj, head, head.previous);
            linkedlistnode.previous.next = linkedlistnode;
            linkedlistnode.next.previous = linkedlistnode;
            return linkedlistnode;
        }

        public void clear()
        {
            for(LinkedListNode linkedlistnode = getLast(); linkedlistnode != null; linkedlistnode = getLast())
                linkedlistnode.remove();

            LinkedListNode linkedlistnode1 = head;
            LinkedListNode linkedlistnode2 = head;
            LinkedListNode linkedlistnode3 = head;
            linkedlistnode2.previous = linkedlistnode3;
            linkedlistnode1.next = linkedlistnode3;
        }

        public LinkedListNode getFirst()
        {
            LinkedListNode linkedlistnode = head.next;
            if(linkedlistnode == head)
                linkedlistnode = null;
            return linkedlistnode;
        }

        public LinkedListNode getLast()
        {
            LinkedListNode linkedlistnode = head.previous;
            if(linkedlistnode == head)
                linkedlistnode = null;
            return linkedlistnode;
        }

        public String toString()
        {
            LinkedListNode linkedlistnode = head.next;
            StringBuilder stringbuilder = new StringBuilder();
            for(; linkedlistnode != head; linkedlistnode = linkedlistnode.next)
                stringbuilder.append(linkedlistnode.toString()).append(", ");

            return stringbuilder.toString();
        }

        private LinkedListNode head;

        public LinkedList()
        {
            head = new LinkedListNode("head", null, null);
            LinkedListNode linkedlistnode = head;
            LinkedListNode linkedlistnode1 = head;
            LinkedListNode linkedlistnode2 = head;
            linkedlistnode1.previous = linkedlistnode2;
            linkedlistnode.next = linkedlistnode2;
        }
    }

    private static class CacheObject
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(this == obj)
                flag = true;
            else
            if(!(obj instanceof CacheObject))
            {
                flag = false;
            } else
            {
                CacheObject cacheobject = (CacheObject)obj;
                flag = object.equals(cacheobject.object);
            }
            return flag;
        }

        public int hashCode()
        {
            return object.hashCode();
        }

        public LinkedListNode ageListNode;
        public LinkedListNode lastAccessedListNode;
        public Object object;
        public int readCount;

        public CacheObject(Object obj)
        {
            readCount = 0;
            object = obj;
        }
    }


    public Cache(int i, long l)
    {
        cacheMisses = 0L;
        if(i == 0)
        {
            throw new IllegalArgumentException("Max cache size cannot be 0.");
        } else
        {
            maxCacheSize = i;
            maxLifetime = l;
            map = new HashMap(103);
            lastAccessedList = new LinkedList();
            ageList = new LinkedList();
            return;
        }
    }

    /**
     * @deprecated Method clear is deprecated
     */

    public void clear()
    {
        this;
        JVM INSTR monitorenter ;
        Object aobj[] = map.keySet().toArray();
        int i = aobj.length;
        for(int j = 0; j < i; j++)
            remove(aobj[j]);

        map.clear();
        lastAccessedList.clear();
        ageList.clear();
        cacheHits = 0L;
        cacheMisses = 0L;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method containsKey is deprecated
     */

    public boolean containsKey(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        deleteExpiredEntries();
        flag = map.containsKey(obj);
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method containsValue is deprecated
     */

    public boolean containsValue(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        deleteExpiredEntries();
        CacheObject cacheobject = new CacheObject(obj);
        flag = map.containsValue(cacheobject);
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method cullCache is deprecated
     */

    protected void cullCache()
    {
        this;
        JVM INSTR monitorenter ;
        int i = maxCacheSize;
        if(i >= 0) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        int j;
        int k;
        if(map.size() <= maxCacheSize)
            continue; /* Loop/switch isn't completed */
        deleteExpiredEntries();
        j = (int)(0.90000000000000002D * (double)maxCacheSize);
        k = map.size();
_L4:
        if(k <= j)
            break; /* Loop/switch isn't completed */
        if(remove(lastAccessedList.getLast().object, true) == null)
        {
            System.err.println((new StringBuilder()).append("Error attempting to cullCache with remove(").append(lastAccessedList.getLast().object.toString()).append(") - ").append("cacheObject not found in cache!").toString());
            lastAccessedList.getLast().remove();
        }
        k--;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L1; else goto _L5
_L5:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method deleteExpiredEntries is deprecated
     */

    protected void deleteExpiredEntries()
    {
        this;
        JVM INSTR monitorenter ;
        long l = maxLifetime;
        if(l > 0L) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        LinkedListNode linkedlistnode;
        return;
_L2:
        if((linkedlistnode = ageList.getLast()) == null) goto _L1; else goto _L3
_L3:
        long l1 = System.currentTimeMillis() - maxLifetime;
_L4:
        LinkedListNode linkedlistnode1;
        if(l1 <= linkedlistnode.timestamp)
            break; /* Loop/switch isn't completed */
        if(remove(linkedlistnode.object, true) == null)
        {
            System.err.println((new StringBuilder()).append("Error attempting to remove(").append(linkedlistnode.object.toString()).append(") - cacheObject not found in cache!").toString());
            linkedlistnode.remove();
        }
        linkedlistnode1 = ageList.getLast();
        linkedlistnode = linkedlistnode1;
        if(linkedlistnode != null) goto _L4; else goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method entrySet is deprecated
     */

    public Set entrySet()
    {
        this;
        JVM INSTR monitorenter ;
        AbstractSet abstractset;
        deleteExpiredEntries();
        abstractset = new AbstractSet() {

            public Iterator iterator()
            {
                return new Iterator() {

                    public boolean hasNext()
                    {
                        return it.hasNext();
                    }

                    public volatile Object next()
                    {
                        return next();
                    }

                    public java.util.Map.Entry next()
                    {
                        java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
                        return new AbstractMapEntry(entry.getKey(), ((CacheObject)entry.getValue()).object) {

                            public Object setValue(Object obj)
                            {
                                throw new UnsupportedOperationException("Cannot set");
                            }

                            final _cls1 this$2;

                        
                        {
                            this$2 = _cls1.this;
                            super(obj, obj1);
                        }
                        }
;
                    }

                    public void remove()
                    {
                        it.remove();
                    }

                    private final Iterator it;
                    final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                        it = set.iterator();
                    }
                }
;
            }

            public int size()
            {
                return set.size();
            }

            private final Set set;
            final Cache this$0;


            
            {
                this$0 = Cache.this;
                super();
                set = map.entrySet();
            }
        }
;
        this;
        JVM INSTR monitorexit ;
        return abstractset;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method get is deprecated
     */

    public Object get(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        CacheObject cacheobject;
        deleteExpiredEntries();
        cacheobject = (CacheObject)map.get(obj);
        if(cacheobject != null) goto _L2; else goto _L1
_L1:
        cacheMisses = 1L + cacheMisses;
        Object obj1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return obj1;
_L2:
        cacheobject.lastAccessedListNode.remove();
        lastAccessedList.addFirst(cacheobject.lastAccessedListNode);
        cacheHits = 1L + cacheHits;
        cacheobject.readCount = 1 + cacheobject.readCount;
        obj1 = cacheobject.object;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public long getCacheHits()
    {
        return cacheHits;
    }

    public long getCacheMisses()
    {
        return cacheMisses;
    }

    public int getMaxCacheSize()
    {
        return maxCacheSize;
    }

    public long getMaxLifetime()
    {
        return maxLifetime;
    }

    /**
     * @deprecated Method isEmpty is deprecated
     */

    public boolean isEmpty()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        deleteExpiredEntries();
        flag = map.isEmpty();
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method keySet is deprecated
     */

    public Set keySet()
    {
        this;
        JVM INSTR monitorenter ;
        Set set;
        deleteExpiredEntries();
        set = Collections.unmodifiableSet(map.keySet());
        this;
        JVM INSTR monitorexit ;
        return set;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method put is deprecated
     */

    public Object put(Object obj, Object obj1)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj2 = null;
        if(map.containsKey(obj))
            obj2 = remove(obj, true);
        CacheObject cacheobject = new CacheObject(obj1);
        map.put(obj, cacheobject);
        cacheobject.lastAccessedListNode = lastAccessedList.addFirst(obj);
        LinkedListNode linkedlistnode = ageList.addFirst(obj);
        linkedlistnode.timestamp = System.currentTimeMillis();
        cacheobject.ageListNode = linkedlistnode;
        cullCache();
        this;
        JVM INSTR monitorexit ;
        return obj2;
        Exception exception;
        exception;
        throw exception;
    }

    public void putAll(Map map1)
    {
        java.util.Map.Entry entry;
        Object obj;
        for(Iterator iterator = map1.entrySet().iterator(); iterator.hasNext(); put(entry.getKey(), obj))
        {
            entry = (java.util.Map.Entry)iterator.next();
            obj = entry.getValue();
            if(obj instanceof CacheObject)
                obj = ((CacheObject)obj).object;
        }

    }

    /**
     * @deprecated Method remove is deprecated
     */

    public Object remove(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj1 = remove(obj, false);
        this;
        JVM INSTR monitorexit ;
        return obj1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method remove is deprecated
     */

    public Object remove(Object obj, boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        CacheObject cacheobject = (CacheObject)map.remove(obj);
        if(cacheobject != null) goto _L2; else goto _L1
_L1:
        Object obj1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return obj1;
_L2:
        cacheobject.lastAccessedListNode.remove();
        cacheobject.ageListNode.remove();
        cacheobject.ageListNode = null;
        cacheobject.lastAccessedListNode = null;
        obj1 = cacheobject.object;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method setMaxCacheSize is deprecated
     */

    public void setMaxCacheSize(int i)
    {
        this;
        JVM INSTR monitorenter ;
        maxCacheSize = i;
        cullCache();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setMaxLifetime(long l)
    {
        maxLifetime = l;
    }

    /**
     * @deprecated Method size is deprecated
     */

    public int size()
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        deleteExpiredEntries();
        i = map.size();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method values is deprecated
     */

    public Collection values()
    {
        this;
        JVM INSTR monitorenter ;
        Collection collection;
        deleteExpiredEntries();
        collection = Collections.unmodifiableCollection(new AbstractCollection() {

            public Iterator iterator()
            {
                return new Iterator() {

                    public boolean hasNext()
                    {
                        return it.hasNext();
                    }

                    public Object next()
                    {
                        return ((CacheObject)it.next()).object;
                    }

                    public void remove()
                    {
                        it.remove();
                    }

                    Iterator it;
                    final _cls1 this$1;

                    
                    {
                        this$1 = _cls1.this;
                        super();
                        it = values.iterator();
                    }
                }
;
            }

            public int size()
            {
                return values.size();
            }

            final Cache this$0;
            Collection values;

            
            {
                this$0 = Cache.this;
                super();
                values = map.values();
            }
        }
);
        this;
        JVM INSTR monitorexit ;
        return collection;
        Exception exception;
        exception;
        throw exception;
    }

    protected LinkedList ageList;
    protected long cacheHits;
    protected long cacheMisses;
    protected LinkedList lastAccessedList;
    protected Map map;
    protected int maxCacheSize;
    protected long maxLifetime;
}
