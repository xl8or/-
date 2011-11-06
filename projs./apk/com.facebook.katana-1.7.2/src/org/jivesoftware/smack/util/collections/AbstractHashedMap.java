// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractHashedMap.java

package org.jivesoftware.smack.util.collections;

import java.io.*;
import java.util.*;

// Referenced classes of package org.jivesoftware.smack.util.collections:
//            IterableMap, EmptyIterator, MapIterator, EmptyMapIterator, 
//            KeyValue

public class AbstractHashedMap extends AbstractMap
    implements IterableMap
{
    protected static abstract class HashIterator
    {

        protected HashEntry currentEntry()
        {
            return last;
        }

        public boolean hasNext()
        {
            boolean flag;
            if(next != null)
                flag = true;
            else
                flag = false;
            return flag;
        }

        protected HashEntry nextEntry()
        {
            if(parent.modCount != expectedModCount)
                throw new ConcurrentModificationException();
            HashEntry hashentry = next;
            if(hashentry == null)
                throw new NoSuchElementException("No next() entry in the iteration");
            HashEntry ahashentry[] = parent.data;
            int i = hashIndex;
            HashEntry hashentry1 = hashentry.next;
            int j = i;
            HashEntry hashentry2;
            HashEntry hashentry3;
            for(hashentry2 = hashentry1; hashentry2 == null && j > 0; hashentry2 = hashentry3)
            {
                int k = j + -1;
                hashentry3 = ahashentry[k];
                j = k;
            }

            next = hashentry2;
            hashIndex = j;
            last = hashentry;
            return hashentry;
        }

        public void remove()
        {
            if(last == null)
                throw new IllegalStateException("remove() can only be called once after next()");
            if(parent.modCount != expectedModCount)
            {
                throw new ConcurrentModificationException();
            } else
            {
                parent.remove(last.getKey());
                last = null;
                expectedModCount = parent.modCount;
                return;
            }
        }

        public String toString()
        {
            String s;
            if(last != null)
                s = (new StringBuilder()).append("Iterator[").append(last.getKey()).append("=").append(last.getValue()).append("]").toString();
            else
                s = "Iterator[]";
            return s;
        }

        protected int expectedModCount;
        protected int hashIndex;
        protected HashEntry last;
        protected HashEntry next;
        protected final AbstractHashedMap parent;

        protected HashIterator(AbstractHashedMap abstracthashedmap)
        {
            parent = abstracthashedmap;
            HashEntry ahashentry[] = abstracthashedmap.data;
            int i = ahashentry.length;
            HashEntry hashentry;
            HashEntry hashentry1;
            for(hashentry = null; i > 0 && hashentry == null; hashentry = hashentry1)
            {
                int j = i + -1;
                hashentry1 = ahashentry[j];
                i = j;
            }

            next = hashentry;
            hashIndex = i;
            expectedModCount = abstracthashedmap.modCount;
        }
    }

    protected static class HashEntry
        implements java.util.Map.Entry, KeyValue
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
                if((getKey() != null ? getKey().equals(entry.getKey()) : entry.getKey() == null) && (getValue() != null ? getValue().equals(entry.getValue()) : entry.getValue() == null))
                    flag = true;
                else
                    flag = false;
            }
            return flag;
        }

        public Object getKey()
        {
            return key;
        }

        public Object getValue()
        {
            return value;
        }

        public int hashCode()
        {
            int i;
            int j;
            if(getKey() == null)
                i = 0;
            else
                i = getKey().hashCode();
            if(getValue() == null)
                j = 0;
            else
                j = getValue().hashCode();
            return i ^ j;
        }

        public void setKey(Object obj)
        {
            key = obj;
        }

        public Object setValue(Object obj)
        {
            Object obj1 = value;
            value = obj;
            return obj1;
        }

        public String toString()
        {
            return (new StringBuilder()).append(getKey()).append('=').append(getValue()).toString();
        }

        protected int hashCode;
        private Object key;
        protected HashEntry next;
        private Object value;



/*
        static Object access$002(HashEntry hashentry, Object obj)
        {
            hashentry.key = obj;
            return obj;
        }

*/



/*
        static Object access$102(HashEntry hashentry, Object obj)
        {
            hashentry.value = obj;
            return obj;
        }

*/

        protected HashEntry(HashEntry hashentry, int i, Object obj, Object obj1)
        {
            next = hashentry;
            hashCode = i;
            key = obj;
            value = obj1;
        }
    }

    protected static class ValuesIterator extends HashIterator
        implements Iterator
    {

        public Object next()
        {
            return super.nextEntry().getValue();
        }

        protected ValuesIterator(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }

    protected static class Values extends AbstractCollection
    {

        public void clear()
        {
            parent.clear();
        }

        public boolean contains(Object obj)
        {
            return parent.containsValue(obj);
        }

        public Iterator iterator()
        {
            return parent.createValuesIterator();
        }

        public int size()
        {
            return parent.size();
        }

        protected final AbstractHashedMap parent;

        protected Values(AbstractHashedMap abstracthashedmap)
        {
            parent = abstracthashedmap;
        }
    }

    protected static class KeySetIterator extends HashIterator
        implements Iterator
    {

        public Object next()
        {
            return super.nextEntry().getKey();
        }

        protected KeySetIterator(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }

    protected static class KeySet extends AbstractSet
    {

        public void clear()
        {
            parent.clear();
        }

        public boolean contains(Object obj)
        {
            return parent.containsKey(obj);
        }

        public Iterator iterator()
        {
            return parent.createKeySetIterator();
        }

        public boolean remove(Object obj)
        {
            boolean flag = parent.containsKey(obj);
            parent.remove(obj);
            return flag;
        }

        public int size()
        {
            return parent.size();
        }

        protected final AbstractHashedMap parent;

        protected KeySet(AbstractHashedMap abstracthashedmap)
        {
            parent = abstracthashedmap;
        }
    }

    protected static class EntrySetIterator extends HashIterator
        implements Iterator
    {

        public volatile Object next()
        {
            return next();
        }

        public HashEntry next()
        {
            return super.nextEntry();
        }

        protected EntrySetIterator(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }

    protected static class EntrySet extends AbstractSet
    {

        public void clear()
        {
            parent.clear();
        }

        public boolean contains(java.util.Map.Entry entry)
        {
            HashEntry hashentry = parent.getEntry(entry.getKey());
            boolean flag;
            if(hashentry != null && hashentry.equals(entry))
                flag = true;
            else
                flag = false;
            return flag;
        }

        public Iterator iterator()
        {
            return parent.createEntrySetIterator();
        }

        public boolean remove(Object obj)
        {
            boolean flag;
            if(!(obj instanceof java.util.Map.Entry))
                flag = false;
            else
            if(!contains(obj))
            {
                flag = false;
            } else
            {
                Object obj1 = ((java.util.Map.Entry)obj).getKey();
                parent.remove(obj1);
                flag = true;
            }
            return flag;
        }

        public int size()
        {
            return parent.size();
        }

        protected final AbstractHashedMap parent;

        protected EntrySet(AbstractHashedMap abstracthashedmap)
        {
            parent = abstracthashedmap;
        }
    }

    protected static class HashMapIterator extends HashIterator
        implements MapIterator
    {

        public Object getKey()
        {
            HashEntry hashentry = currentEntry();
            if(hashentry == null)
                throw new IllegalStateException("getKey() can only be called after next() and before remove()");
            else
                return hashentry.getKey();
        }

        public Object getValue()
        {
            HashEntry hashentry = currentEntry();
            if(hashentry == null)
                throw new IllegalStateException("getValue() can only be called after next() and before remove()");
            else
                return hashentry.getValue();
        }

        public Object next()
        {
            return super.nextEntry().getKey();
        }

        public Object setValue(Object obj)
        {
            HashEntry hashentry = currentEntry();
            if(hashentry == null)
                throw new IllegalStateException("setValue() can only be called after next() and before remove()");
            else
                return hashentry.setValue(obj);
        }

        protected HashMapIterator(AbstractHashedMap abstracthashedmap)
        {
            super(abstracthashedmap);
        }
    }


    protected AbstractHashedMap()
    {
    }

    protected AbstractHashedMap(int i)
    {
        this(i, 0.75F);
    }

    protected AbstractHashedMap(int i, float f)
    {
        if(i < 1)
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        if(f <= 0F || Float.isNaN(f))
        {
            throw new IllegalArgumentException("Load factor must be greater than 0");
        } else
        {
            loadFactor = f;
            threshold = calculateThreshold(i, f);
            data = new HashEntry[calculateNewCapacity(i)];
            init();
            return;
        }
    }

    protected AbstractHashedMap(int i, float f, int j)
    {
        loadFactor = f;
        data = new HashEntry[i];
        threshold = j;
        init();
    }

    protected AbstractHashedMap(Map map)
    {
        this(Math.max(2 * map.size(), 16), 0.75F);
        putAll(map);
    }

    protected void addEntry(HashEntry hashentry, int i)
    {
        data[i] = hashentry;
    }

    protected void addMapping(int i, int j, Object obj, Object obj1)
    {
        modCount = 1 + modCount;
        addEntry(createEntry(data[i], j, obj, obj1), i);
        size = 1 + size;
        checkCapacity();
    }

    protected int calculateNewCapacity(int i)
    {
        int j = 1;
        if(i <= 0x40000000) goto _L2; else goto _L1
_L1:
        j = 0x40000000;
_L4:
        return j;
_L2:
        for(; j < i; j <<= 1);
        if(j > 0x40000000)
            j = 0x40000000;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected int calculateThreshold(int i, float f)
    {
        return (int)(f * (float)i);
    }

    protected void checkCapacity()
    {
        if(size >= threshold)
        {
            int i = 2 * data.length;
            if(i <= 0x40000000)
                ensureCapacity(i);
        }
    }

    public void clear()
    {
        modCount = 1 + modCount;
        HashEntry ahashentry[] = data;
        for(int i = ahashentry.length - 1; i >= 0; i--)
            ahashentry[i] = null;

        size = 0;
    }

    protected Object clone()
    {
        AbstractHashedMap abstracthashedmap;
        try
        {
            abstracthashedmap = (AbstractHashedMap)super.clone();
            abstracthashedmap.data = new HashEntry[data.length];
            abstracthashedmap.entrySet = null;
            abstracthashedmap.keySet = null;
            abstracthashedmap.values = null;
            abstracthashedmap.modCount = 0;
            abstracthashedmap.size = 0;
            abstracthashedmap.init();
            abstracthashedmap.putAll(this);
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            abstracthashedmap = null;
        }
        return abstracthashedmap;
    }

    public boolean containsKey(Object obj)
    {
        HashEntry hashentry;
        boolean flag;
        Object obj1;
        int i;
        if(obj == null)
            obj1 = NULL;
        else
            obj1 = obj;
        i = hash(obj1);
        hashentry = data[hashIndex(i, data.length)];
_L3:
        if(hashentry == null)
            break MISSING_BLOCK_LABEL_79;
        if(hashentry.hashCode != i || !isEqualKey(obj, hashentry.getKey())) goto _L2; else goto _L1
_L1:
        flag = true;
_L4:
        return flag;
_L2:
        hashentry = hashentry.next;
          goto _L3
        flag = false;
          goto _L4
    }

    public boolean containsValue(Object obj)
    {
        if(obj != null) goto _L2; else goto _L1
_L1:
        int k;
        int l;
        k = data.length;
        l = 0;
_L8:
        HashEntry hashentry1;
        if(l >= k)
            break MISSING_BLOCK_LABEL_130;
        hashentry1 = data[l];
_L7:
        if(hashentry1 == null) goto _L4; else goto _L3
_L3:
        if(hashentry1.getValue() != null) goto _L6; else goto _L5
_L5:
        boolean flag = true;
_L9:
        return flag;
_L6:
        hashentry1 = hashentry1.next;
          goto _L7
_L4:
        l++;
          goto _L8
_L2:
        int i;
        int j;
        i = data.length;
        j = 0;
_L11:
        HashEntry hashentry;
        if(j >= i)
            break MISSING_BLOCK_LABEL_130;
        hashentry = data[j];
_L10:
label0:
        {
            if(hashentry == null)
                break MISSING_BLOCK_LABEL_123;
            if(!isEqualValue(obj, hashentry.getValue()))
                break label0;
            flag = true;
        }
          goto _L9
        hashentry = hashentry.next;
          goto _L10
        j++;
          goto _L11
        flag = false;
          goto _L9
    }

    protected HashEntry createEntry(HashEntry hashentry, int i, Object obj, Object obj1)
    {
        return new HashEntry(hashentry, i, obj, obj1);
    }

    protected Iterator createEntrySetIterator()
    {
        Object obj;
        if(size() == 0)
            obj = EmptyIterator.INSTANCE;
        else
            obj = new EntrySetIterator(this);
        return ((Iterator) (obj));
    }

    protected Iterator createKeySetIterator()
    {
        Object obj;
        if(size() == 0)
            obj = EmptyIterator.INSTANCE;
        else
            obj = new KeySetIterator(this);
        return ((Iterator) (obj));
    }

    protected Iterator createValuesIterator()
    {
        Object obj;
        if(size() == 0)
            obj = EmptyIterator.INSTANCE;
        else
            obj = new ValuesIterator(this);
        return ((Iterator) (obj));
    }

    protected void destroyEntry(HashEntry hashentry)
    {
        hashentry.next = null;
        hashentry.key = null;
        hashentry.value = null;
    }

    protected void doReadObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        loadFactor = objectinputstream.readFloat();
        int i = objectinputstream.readInt();
        int j = objectinputstream.readInt();
        init();
        data = new HashEntry[i];
        for(int k = 0; k < j; k++)
            put(objectinputstream.readObject(), objectinputstream.readObject());

        threshold = calculateThreshold(data.length, loadFactor);
    }

    protected void doWriteObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        objectoutputstream.writeFloat(loadFactor);
        objectoutputstream.writeInt(data.length);
        objectoutputstream.writeInt(size);
        for(MapIterator mapiterator = mapIterator(); mapiterator.hasNext(); objectoutputstream.writeObject(mapiterator.getValue()))
            objectoutputstream.writeObject(mapiterator.next());

    }

    protected void ensureCapacity(int i)
    {
        int j;
        j = data.length;
        if(i > j)
        {
label0:
            {
                if(size != 0)
                    break label0;
                threshold = calculateThreshold(i, loadFactor);
                data = new HashEntry[i];
            }
        }
_L4:
        return;
        HashEntry ahashentry[];
        HashEntry ahashentry1[];
        int k;
        ahashentry = data;
        ahashentry1 = new HashEntry[i];
        modCount = 1 + modCount;
        k = j - 1;
_L3:
        if(k < 0) goto _L2; else goto _L1
_L1:
        HashEntry hashentry;
        hashentry = ahashentry[k];
        if(hashentry == null)
            continue; /* Loop/switch isn't completed */
        ahashentry[k] = null;
_L5:
        HashEntry hashentry1;
        hashentry1 = hashentry.next;
        int l = hashIndex(hashentry.hashCode, i);
        hashentry.next = ahashentry1[l];
        ahashentry1[l] = hashentry;
        if(hashentry1 != null)
            break MISSING_BLOCK_LABEL_159;
        k--;
          goto _L3
_L2:
        threshold = calculateThreshold(i, loadFactor);
        data = ahashentry1;
          goto _L4
        hashentry = hashentry1;
          goto _L5
    }

    protected int entryHashCode(HashEntry hashentry)
    {
        return hashentry.hashCode;
    }

    protected Object entryKey(HashEntry hashentry)
    {
        return hashentry.key;
    }

    protected HashEntry entryNext(HashEntry hashentry)
    {
        return hashentry.next;
    }

    public Set entrySet()
    {
        if(entrySet == null)
            entrySet = new EntrySet(this);
        return entrySet;
    }

    protected Object entryValue(HashEntry hashentry)
    {
        return hashentry.value;
    }

    public boolean equals(Object obj)
    {
        if(obj != this) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L4:
        return flag;
_L2:
label0:
        {
            if(!(obj instanceof Map))
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            Map map = (Map)obj;
            if(map.size() != size())
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            MapIterator mapiterator = mapIterator();
            do
            {
                boolean flag1;
                try
                {
                    if(!mapiterator.hasNext())
                        break;
                    Object obj1 = mapiterator.next();
                    Object obj2 = mapiterator.getValue();
                    if(obj2 == null)
                    {
                        if(map.get(obj1) != null || !map.containsKey(obj1))
                            break label0;
                        continue;
                    }
                    flag1 = obj2.equals(map.get(obj1));
                }
                catch(ClassCastException classcastexception)
                {
                    flag = false;
                    continue; /* Loop/switch isn't completed */
                }
                catch(NullPointerException nullpointerexception)
                {
                    flag = false;
                    continue; /* Loop/switch isn't completed */
                }
                if(!flag1)
                {
                    flag = false;
                    continue; /* Loop/switch isn't completed */
                }
            } while(true);
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public Object get(Object obj)
    {
        HashEntry hashentry;
        Object obj2;
        Object obj1;
        int i;
        if(obj == null)
            obj1 = NULL;
        else
            obj1 = obj;
        i = hash(obj1);
        hashentry = data[hashIndex(i, data.length)];
_L3:
        if(hashentry == null)
            break MISSING_BLOCK_LABEL_83;
        if(hashentry.hashCode != i || !isEqualKey(obj, hashentry.key)) goto _L2; else goto _L1
_L1:
        obj2 = hashentry.getValue();
_L4:
        return obj2;
_L2:
        hashentry = hashentry.next;
          goto _L3
        obj2 = null;
          goto _L4
    }

    protected HashEntry getEntry(Object obj)
    {
        HashEntry hashentry;
        HashEntry hashentry1;
        Object obj1;
        int i;
        if(obj == null)
            obj1 = NULL;
        else
            obj1 = obj;
        i = hash(obj1);
        hashentry = data[hashIndex(i, data.length)];
_L3:
        if(hashentry == null)
            break MISSING_BLOCK_LABEL_80;
        if(hashentry.hashCode != i || !isEqualKey(obj, hashentry.getKey())) goto _L2; else goto _L1
_L1:
        hashentry1 = hashentry;
_L4:
        return hashentry1;
_L2:
        hashentry = hashentry.next;
          goto _L3
        hashentry1 = null;
          goto _L4
    }

    protected int hash(Object obj)
    {
        int i = obj.hashCode();
        int j = i + (-1 ^ i << 9);
        int k = j ^ j >>> 14;
        int l = k + (k << 4);
        return l ^ l >>> 10;
    }

    public int hashCode()
    {
        int i = 0;
        for(Iterator iterator = createEntrySetIterator(); iterator.hasNext();)
            i += iterator.next().hashCode();

        return i;
    }

    protected int hashIndex(int i, int j)
    {
        return i & j - 1;
    }

    protected void init()
    {
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(size == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected boolean isEqualKey(Object obj, Object obj1)
    {
        boolean flag;
        if(obj == obj1 || obj != null && obj.equals(obj1))
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected boolean isEqualValue(Object obj, Object obj1)
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
            keySet = new KeySet(this);
        return keySet;
    }

    public MapIterator mapIterator()
    {
        Object obj;
        if(size == 0)
            obj = EmptyMapIterator.INSTANCE;
        else
            obj = new HashMapIterator(this);
        return ((MapIterator) (obj));
    }

    public Object put(Object obj, Object obj1)
    {
        int i;
        int j;
        HashEntry hashentry;
        Object obj3;
        Object obj2;
        if(obj == null)
            obj2 = NULL;
        else
            obj2 = obj;
        i = hash(obj2);
        j = hashIndex(i, data.length);
        hashentry = data[j];
_L3:
        if(hashentry == null)
            break MISSING_BLOCK_LABEL_97;
        if(hashentry.hashCode != i || !isEqualKey(obj, hashentry.getKey())) goto _L2; else goto _L1
_L1:
        obj3 = hashentry.getValue();
        updateEntry(hashentry, obj1);
_L4:
        return obj3;
_L2:
        hashentry = hashentry.next;
          goto _L3
        addMapping(j, i, obj, obj1);
        obj3 = null;
          goto _L4
    }

    public void putAll(Map map)
    {
        int i = map.size();
        if(i != 0)
        {
            ensureCapacity(calculateNewCapacity((int)(1F + (float)(i + size) / loadFactor)));
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()) 
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Object remove(Object obj)
    {
        HashEntry hashentry;
        HashEntry hashentry1;
        Object obj2;
        Object obj1;
        int i;
        int j;
        if(obj == null)
            obj1 = NULL;
        else
            obj1 = obj;
        i = hash(obj1);
        j = hashIndex(i, data.length);
        hashentry = data[j];
        hashentry1 = null;
_L3:
        if(hashentry == null)
            break MISSING_BLOCK_LABEL_112;
        if(hashentry.hashCode != i || !isEqualKey(obj, hashentry.getKey())) goto _L2; else goto _L1
_L1:
        obj2 = hashentry.getValue();
        removeMapping(hashentry, j, hashentry1);
_L4:
        return obj2;
_L2:
        HashEntry hashentry2 = hashentry.next;
        HashEntry hashentry3 = hashentry;
        hashentry = hashentry2;
        hashentry1 = hashentry3;
          goto _L3
        obj2 = null;
          goto _L4
    }

    protected void removeEntry(HashEntry hashentry, int i, HashEntry hashentry1)
    {
        if(hashentry1 == null)
            data[i] = hashentry.next;
        else
            hashentry1.next = hashentry.next;
    }

    protected void removeMapping(HashEntry hashentry, int i, HashEntry hashentry1)
    {
        modCount = 1 + modCount;
        removeEntry(hashentry, i, hashentry1);
        size = size - 1;
        destroyEntry(hashentry);
    }

    protected void reuseEntry(HashEntry hashentry, int i, int j, Object obj, Object obj1)
    {
        hashentry.next = data[i];
        hashentry.hashCode = j;
        hashentry.key = obj;
        hashentry.value = obj1;
    }

    public int size()
    {
        return size;
    }

    public String toString()
    {
        String s;
        if(size() == 0)
        {
            s = "{}";
        } else
        {
            StringBuilder stringbuilder = new StringBuilder(32 * size());
            stringbuilder.append('{');
            MapIterator mapiterator = mapIterator();
            boolean flag = mapiterator.hasNext();
            do
            {
                if(!flag)
                    break;
                Object obj = mapiterator.next();
                Object obj1 = mapiterator.getValue();
                if(obj == this)
                    obj = "(this Map)";
                StringBuilder stringbuilder1 = stringbuilder.append(obj).append('=');
                if(obj1 == this)
                    obj1 = "(this Map)";
                stringbuilder1.append(obj1);
                flag = mapiterator.hasNext();
                if(flag)
                    stringbuilder.append(',').append(' ');
            } while(true);
            stringbuilder.append('}');
            s = stringbuilder.toString();
        }
        return s;
    }

    protected void updateEntry(HashEntry hashentry, Object obj)
    {
        hashentry.setValue(obj);
    }

    public Collection values()
    {
        if(values == null)
            values = new Values(this);
        return values;
    }

    protected static final int DEFAULT_CAPACITY = 16;
    protected static final float DEFAULT_LOAD_FACTOR = 0.75F;
    protected static final int DEFAULT_THRESHOLD = 12;
    protected static final String GETKEY_INVALID = "getKey() can only be called after next() and before remove()";
    protected static final String GETVALUE_INVALID = "getValue() can only be called after next() and before remove()";
    protected static final int MAXIMUM_CAPACITY = 0x40000000;
    protected static final String NO_NEXT_ENTRY = "No next() entry in the iteration";
    protected static final String NO_PREVIOUS_ENTRY = "No previous() entry in the iteration";
    protected static final Object NULL = new Object();
    protected static final String REMOVE_INVALID = "remove() can only be called once after next()";
    protected static final String SETVALUE_INVALID = "setValue() can only be called after next() and before remove()";
    protected transient HashEntry data[];
    protected transient EntrySet entrySet;
    protected transient KeySet keySet;
    protected transient float loadFactor;
    protected transient int modCount;
    protected transient int size;
    protected transient int threshold;
    protected transient Values values;

}
