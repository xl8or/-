// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractReferenceMap.java

package org.jivesoftware.smack.util.collections;

import java.io.*;
import java.lang.ref.*;
import java.util.*;

// Referenced classes of package org.jivesoftware.smack.util.collections:
//            AbstractHashedMap, MapIterator, DefaultMapEntry

public abstract class AbstractReferenceMap extends AbstractHashedMap
{
    static class WeakRef extends WeakReference
    {

        public int hashCode()
        {
            return hash;
        }

        private int hash;

        public WeakRef(int i, Object obj, ReferenceQueue referencequeue)
        {
            super(obj, referencequeue);
            hash = i;
        }
    }

    static class SoftRef extends SoftReference
    {

        public int hashCode()
        {
            return hash;
        }

        private int hash;

        public SoftRef(int i, Object obj, ReferenceQueue referencequeue)
        {
            super(obj, referencequeue);
            hash = i;
        }
    }

    static class ReferenceMapIterator extends ReferenceIteratorBase
        implements MapIterator
    {

        public Object getKey()
        {
            ReferenceEntry referenceentry = currentEntry();
            if(referenceentry == null)
                throw new IllegalStateException("getKey() can only be called after next() and before remove()");
            else
                return referenceentry.getKey();
        }

        public Object getValue()
        {
            ReferenceEntry referenceentry = currentEntry();
            if(referenceentry == null)
                throw new IllegalStateException("getValue() can only be called after next() and before remove()");
            else
                return referenceentry.getValue();
        }

        public Object next()
        {
            return nextEntry().getKey();
        }

        public Object setValue(Object obj)
        {
            ReferenceEntry referenceentry = currentEntry();
            if(referenceentry == null)
                throw new IllegalStateException("setValue() can only be called after next() and before remove()");
            else
                return referenceentry.setValue(obj);
        }

        protected ReferenceMapIterator(AbstractReferenceMap abstractreferencemap)
        {
            super(abstractreferencemap);
        }
    }

    static class ReferenceValuesIterator extends ReferenceIteratorBase
        implements Iterator
    {

        public Object next()
        {
            return nextEntry().getValue();
        }

        ReferenceValuesIterator(AbstractReferenceMap abstractreferencemap)
        {
            super(abstractreferencemap);
        }
    }

    static class ReferenceKeySetIterator extends ReferenceIteratorBase
        implements Iterator
    {

        public Object next()
        {
            return nextEntry().getKey();
        }

        ReferenceKeySetIterator(AbstractReferenceMap abstractreferencemap)
        {
            super(abstractreferencemap);
        }
    }

    static class ReferenceEntrySetIterator extends ReferenceIteratorBase
        implements Iterator
    {

        public volatile Object next()
        {
            return next();
        }

        public ReferenceEntry next()
        {
            return superNext();
        }

        public ReferenceEntrySetIterator(AbstractReferenceMap abstractreferencemap)
        {
            super(abstractreferencemap);
        }
    }

    static class ReferenceIteratorBase
    {

        private void checkMod()
        {
            if(parent.modCount != expectedModCount)
                throw new ConcurrentModificationException();
            else
                return;
        }

        private boolean nextNull()
        {
            boolean flag;
            if(nextKey == null || nextValue == null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        protected ReferenceEntry currentEntry()
        {
            checkMod();
            return previous;
        }

        public boolean hasNext()
        {
            checkMod();
_L3:
            ReferenceEntry referenceentry1;
            if(!nextNull())
                break MISSING_BLOCK_LABEL_136;
            ReferenceEntry referenceentry = entry;
            int i = index;
            referenceentry1 = referenceentry;
            int j;
            int k;
            for(j = i; referenceentry1 == null && j > 0; j = k)
            {
                k = j + -1;
                referenceentry1 = (ReferenceEntry)parent.data[k];
            }

            entry = referenceentry1;
            index = j;
            if(referenceentry1 != null) goto _L2; else goto _L1
_L1:
            boolean flag;
            currentKey = null;
            currentValue = null;
            flag = false;
_L4:
            return flag;
_L2:
            nextKey = referenceentry1.getKey();
            nextValue = referenceentry1.getValue();
            if(nextNull())
                entry = entry.next();
              goto _L3
            flag = true;
              goto _L4
        }

        protected ReferenceEntry nextEntry()
        {
            checkMod();
            if(nextNull() && !hasNext())
            {
                throw new NoSuchElementException();
            } else
            {
                previous = entry;
                entry = entry.next();
                currentKey = nextKey;
                currentValue = nextValue;
                nextKey = null;
                nextValue = null;
                return previous;
            }
        }

        public void remove()
        {
            checkMod();
            if(previous == null)
            {
                throw new IllegalStateException();
            } else
            {
                parent.remove(currentKey);
                previous = null;
                currentKey = null;
                currentValue = null;
                expectedModCount = parent.modCount;
                return;
            }
        }

        public ReferenceEntry superNext()
        {
            return nextEntry();
        }

        Object currentKey;
        Object currentValue;
        ReferenceEntry entry;
        int expectedModCount;
        int index;
        Object nextKey;
        Object nextValue;
        final AbstractReferenceMap parent;
        ReferenceEntry previous;

        public ReferenceIteratorBase(AbstractReferenceMap abstractreferencemap)
        {
            parent = abstractreferencemap;
            int i;
            if(abstractreferencemap.size() != 0)
                i = abstractreferencemap.data.length;
            else
                i = 0;
            index = i;
            expectedModCount = abstractreferencemap.modCount;
        }
    }

    protected static class ReferenceEntry extends AbstractHashedMap.HashEntry
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(obj == this)
                flag = true;
            else
            if(!(obj instanceof java.util.Map.Entry))
            {
                flag = false;
            } else
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                Object obj1 = entry.getKey();
                Object obj2 = entry.getValue();
                if(obj1 == null || obj2 == null)
                    flag = false;
                else
                if(parent.isEqualKey(obj1, getKey()) && parent.isEqualValue(obj2, getValue()))
                    flag = true;
                else
                    flag = false;
            }
            return flag;
        }

        public Object getKey()
        {
            Object obj;
            if(parent.keyType > 0)
                obj = refKey.get();
            else
                obj = super.getKey();
            return obj;
        }

        public Object getValue()
        {
            Object obj;
            if(parent.valueType > 0)
                obj = refValue.get();
            else
                obj = super.getValue();
            return obj;
        }

        public int hashCode()
        {
            return parent.hashEntry(getKey(), getValue());
        }

        protected ReferenceEntry next()
        {
            return (ReferenceEntry)next;
        }

        boolean purge(Reference reference)
        {
            boolean flag;
            boolean flag1;
            if(parent.keyType > 0 && refKey == reference)
                flag = true;
            else
                flag = false;
            if(flag || parent.valueType > 0 && refValue == reference)
                flag1 = true;
            else
                flag1 = false;
            if(!flag1) goto _L2; else goto _L1
_L1:
            if(parent.keyType > 0)
                refKey.clear();
            if(parent.valueType <= 0) goto _L4; else goto _L3
_L3:
            refValue.clear();
_L2:
            return flag1;
_L4:
            if(parent.purgeValues)
                setValue(null);
            if(true) goto _L2; else goto _L5
_L5:
        }

        public Object setValue(Object obj)
        {
            Object obj1 = getValue();
            if(parent.valueType > 0)
            {
                refValue.clear();
                refValue = toReference(parent.valueType, obj, hashCode);
            } else
            {
                super.setValue(obj);
            }
            return obj1;
        }

        protected Reference toReference(int i, Object obj, int j)
        {
            i;
            JVM INSTR tableswitch 1 2: default 24
        //                       1 34
        //                       2 55;
               goto _L1 _L2 _L3
_L1:
            throw new Error("Attempt to create hard reference in ReferenceMap!");
_L2:
            Object obj1 = new SoftRef(j, obj, parent.queue);
_L5:
            return ((Reference) (obj1));
_L3:
            obj1 = new WeakRef(j, obj, parent.queue);
            if(true) goto _L5; else goto _L4
_L4:
        }

        protected final AbstractReferenceMap parent;
        protected Reference refKey;
        protected Reference refValue;

        public ReferenceEntry(AbstractReferenceMap abstractreferencemap, ReferenceEntry referenceentry, int i, Object obj, Object obj1)
        {
            super(referenceentry, i, null, null);
            parent = abstractreferencemap;
            if(abstractreferencemap.keyType != 0)
                refKey = toReference(abstractreferencemap.keyType, obj, i);
            else
                setKey(obj);
            if(abstractreferencemap.valueType != 0)
                refValue = toReference(abstractreferencemap.valueType, obj1, i);
            else
                setValue(obj1);
        }
    }

    static class ReferenceValues extends AbstractHashedMap.Values
    {

        public Object[] toArray()
        {
            return toArray(new Object[0]);
        }

        public Object[] toArray(Object aobj[])
        {
            ArrayList arraylist = new ArrayList(parent.size());
            for(Iterator iterator = iterator(); iterator.hasNext(); arraylist.add(iterator.next()));
            return arraylist.toArray(aobj);
        }

        protected ReferenceValues(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }

    static class ReferenceKeySet extends AbstractHashedMap.KeySet
    {

        public Object[] toArray()
        {
            return toArray(new Object[0]);
        }

        public Object[] toArray(Object aobj[])
        {
            ArrayList arraylist = new ArrayList(parent.size());
            for(Iterator iterator = iterator(); iterator.hasNext(); arraylist.add(iterator.next()));
            return arraylist.toArray(aobj);
        }

        protected ReferenceKeySet(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }

    static class ReferenceEntrySet extends AbstractHashedMap.EntrySet
    {

        public Object[] toArray()
        {
            return toArray(new Object[0]);
        }

        public Object[] toArray(Object aobj[])
        {
            ArrayList arraylist = new ArrayList();
            java.util.Map.Entry entry;
            for(Iterator iterator = iterator(); iterator.hasNext(); arraylist.add(new DefaultMapEntry(entry.getKey(), entry.getValue())))
                entry = (java.util.Map.Entry)iterator.next();

            return arraylist.toArray(aobj);
        }

        protected ReferenceEntrySet(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }


    protected AbstractReferenceMap()
    {
    }

    protected AbstractReferenceMap(int i, int j, int k, float f, boolean flag)
    {
        super(k, f);
        verify("keyType", i);
        verify("valueType", j);
        keyType = i;
        valueType = j;
        purgeValues = flag;
    }

    private static void verify(String s, int i)
    {
        if(i < 0 || i > 2)
            throw new IllegalArgumentException((new StringBuilder()).append(s).append(" must be HARD, SOFT, WEAK.").toString());
        else
            return;
    }

    public void clear()
    {
        super.clear();
        while(queue.poll() != null) ;
    }

    public boolean containsKey(Object obj)
    {
        purgeBeforeRead();
        AbstractHashedMap.HashEntry hashentry = getEntry(obj);
        boolean flag;
        if(hashentry == null)
            flag = false;
        else
        if(hashentry.getValue() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean containsValue(Object obj)
    {
        purgeBeforeRead();
        boolean flag;
        if(obj == null)
            flag = false;
        else
            flag = super.containsValue(obj);
        return flag;
    }

    public AbstractHashedMap.HashEntry createEntry(AbstractHashedMap.HashEntry hashentry, int i, Object obj, Object obj1)
    {
        return new ReferenceEntry(this, (ReferenceEntry)hashentry, i, obj, obj1);
    }

    protected Iterator createEntrySetIterator()
    {
        return new ReferenceEntrySetIterator(this);
    }

    protected Iterator createKeySetIterator()
    {
        return new ReferenceKeySetIterator(this);
    }

    protected Iterator createValuesIterator()
    {
        return new ReferenceValuesIterator(this);
    }

    protected void doReadObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        keyType = objectinputstream.readInt();
        valueType = objectinputstream.readInt();
        purgeValues = objectinputstream.readBoolean();
        loadFactor = objectinputstream.readFloat();
        int i = objectinputstream.readInt();
        init();
        data = new AbstractHashedMap.HashEntry[i];
        do
        {
            Object obj = objectinputstream.readObject();
            if(obj == null)
            {
                threshold = calculateThreshold(data.length, loadFactor);
                return;
            }
            put(obj, objectinputstream.readObject());
        } while(true);
    }

    protected void doWriteObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        objectoutputstream.writeInt(keyType);
        objectoutputstream.writeInt(valueType);
        objectoutputstream.writeBoolean(purgeValues);
        objectoutputstream.writeFloat(loadFactor);
        objectoutputstream.writeInt(data.length);
        for(MapIterator mapiterator = mapIterator(); mapiterator.hasNext(); objectoutputstream.writeObject(mapiterator.getValue()))
            objectoutputstream.writeObject(mapiterator.next());

        objectoutputstream.writeObject(null);
    }

    public Set entrySet()
    {
        if(entrySet == null)
            entrySet = new ReferenceEntrySet(this);
        return entrySet;
    }

    public Object get(Object obj)
    {
        purgeBeforeRead();
        AbstractHashedMap.HashEntry hashentry = getEntry(obj);
        Object obj1;
        if(hashentry == null)
            obj1 = null;
        else
            obj1 = hashentry.getValue();
        return obj1;
    }

    protected AbstractHashedMap.HashEntry getEntry(Object obj)
    {
        AbstractHashedMap.HashEntry hashentry;
        if(obj == null)
            hashentry = null;
        else
            hashentry = super.getEntry(obj);
        return hashentry;
    }

    protected int hashEntry(Object obj, Object obj1)
    {
        int i = 0;
        int j;
        if(obj == null)
            j = i;
        else
            j = obj.hashCode();
        if(obj1 != null)
            i = obj1.hashCode();
        return j ^ i;
    }

    protected void init()
    {
        queue = new ReferenceQueue();
    }

    public boolean isEmpty()
    {
        purgeBeforeRead();
        return super.isEmpty();
    }

    protected boolean isEqualKey(Object obj, Object obj1)
    {
        boolean flag;
        if(obj == obj1 || obj.equals(obj1))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Set keySet()
    {
        if(keySet == null)
            keySet = new ReferenceKeySet(this);
        return keySet;
    }

    public MapIterator mapIterator()
    {
        return new ReferenceMapIterator(this);
    }

    protected void purge()
    {
        for(Reference reference = queue.poll(); reference != null; reference = queue.poll())
            purge(reference);

    }

    protected void purge(Reference reference)
    {
        int i = hashIndex(reference.hashCode(), data.length);
        AbstractHashedMap.HashEntry hashentry = data[i];
        AbstractHashedMap.HashEntry hashentry1 = null;
        do
        {
label0:
            {
                if(hashentry != null)
                {
                    if(!((ReferenceEntry)hashentry).purge(reference))
                        break label0;
                    if(hashentry1 == null)
                        data[i] = hashentry.next;
                    else
                        hashentry1.next = hashentry.next;
                    size = size - 1;
                }
                return;
            }
            AbstractHashedMap.HashEntry hashentry2 = hashentry.next;
            hashentry1 = hashentry;
            hashentry = hashentry2;
        } while(true);
    }

    protected void purgeBeforeRead()
    {
        purge();
    }

    protected void purgeBeforeWrite()
    {
        purge();
    }

    public Object put(Object obj, Object obj1)
    {
        if(obj == null)
            throw new NullPointerException("null keys not allowed");
        if(obj1 == null)
        {
            throw new NullPointerException("null values not allowed");
        } else
        {
            purgeBeforeWrite();
            return super.put(obj, obj1);
        }
    }

    public Object remove(Object obj)
    {
        Object obj1;
        if(obj == null)
        {
            obj1 = null;
        } else
        {
            purgeBeforeWrite();
            obj1 = super.remove(obj);
        }
        return obj1;
    }

    public int size()
    {
        purgeBeforeRead();
        return super.size();
    }

    public Collection values()
    {
        if(values == null)
            values = new ReferenceValues(this);
        return values;
    }

    public static final int HARD = 0;
    public static final int SOFT = 1;
    public static final int WEAK = 2;
    protected int keyType;
    protected boolean purgeValues;
    private transient ReferenceQueue queue;
    protected int valueType;

}
