// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.sym;

import org.codehaus.jackson.util.InternCache;

// Referenced classes of package org.codehaus.jackson.sym:
//            Name1, Name2, Name3, NameN, 
//            Name

public final class BytesToNameCanonicalizer
{
    static final class Bucket
    {

        public Name find(int i, int j, int k)
        {
            if(mName.hashCode() != i || !mName.equals(j, k)) goto _L2; else goto _L1
_L1:
            Name name = mName;
_L4:
            return name;
_L2:
            Bucket bucket = mNext;
            do
            {
                if(bucket == null)
                    break;
                Name name1 = bucket.mName;
                if(name1.hashCode() == i && name1.equals(j, k))
                {
                    name = name1;
                    continue; /* Loop/switch isn't completed */
                }
                bucket = bucket.mNext;
            } while(true);
            name = null;
            if(true) goto _L4; else goto _L3
_L3:
        }

        public Name find(int i, int ai[], int j)
        {
            if(mName.hashCode() != i || !mName.equals(ai, j)) goto _L2; else goto _L1
_L1:
            Name name = mName;
_L4:
            return name;
_L2:
            Bucket bucket = mNext;
            do
            {
                if(bucket == null)
                    break;
                Name name1 = bucket.mName;
                if(name1.hashCode() == i && name1.equals(ai, j))
                {
                    name = name1;
                    continue; /* Loop/switch isn't completed */
                }
                bucket = bucket.mNext;
            } while(true);
            name = null;
            if(true) goto _L4; else goto _L3
_L3:
        }

        public int length()
        {
            Bucket bucket = mNext;
            int i = 1;
            for(Bucket bucket1 = bucket; bucket1 != null; bucket1 = bucket1.mNext)
                i++;

            return i;
        }

        final Name mName;
        final Bucket mNext;

        Bucket(Name name, Bucket bucket)
        {
            mName = name;
            mNext = bucket;
        }
    }


    private BytesToNameCanonicalizer(int i)
    {
        _parent = null;
        int j;
        if(i < 16)
            j = 16;
        else
        if((i & i - 1) != 0)
        {
            j = 16;
            while(j < i) 
                j += j;
        } else
        {
            j = i;
        }
        initTables(j);
    }

    private BytesToNameCanonicalizer(BytesToNameCanonicalizer bytestonamecanonicalizer)
    {
        _parent = bytestonamecanonicalizer;
        _count = bytestonamecanonicalizer._count;
        _mainHashMask = bytestonamecanonicalizer._mainHashMask;
        _mainHash = bytestonamecanonicalizer._mainHash;
        _mainNames = bytestonamecanonicalizer._mainNames;
        _collList = bytestonamecanonicalizer._collList;
        _collCount = bytestonamecanonicalizer._collCount;
        _collEnd = bytestonamecanonicalizer._collEnd;
        _needRehash = false;
        _mainHashShared = true;
        _mainNamesShared = true;
        _collListShared = true;
    }

    private void _addSymbol(int i, Name name)
    {
        int k1;
        if(_mainHashShared)
            unshareMain();
        if(_needRehash)
            rehash();
        _count = 1 + _count;
        int j = i & _mainHashMask;
        int j1;
        if(_mainNames[j] == null)
        {
            _mainHash[j] = i << 8;
            if(_mainNamesShared)
                unshareNames();
            _mainNames[j] = name;
        } else
        {
            if(_collListShared)
                unshareCollision();
            _collCount = 1 + _collCount;
            int k = _mainHash[j];
            int l = k & 0xff;
            int i1;
            if(l == 0)
            {
                int l1;
                if(_collEnd <= 254)
                {
                    l1 = _collEnd;
                    _collEnd = 1 + _collEnd;
                    if(l1 >= _collList.length)
                        expandCollision();
                } else
                {
                    l1 = findBestBucket();
                }
                _mainHash[j] = k & 0xffffff00 | l1 + 1;
                i1 = l1;
            } else
            {
                i1 = l + -1;
            }
            _collList[i1] = new Bucket(name, _collList[i1]);
        }
        j1 = _mainHash.length;
        if(_count <= j1 >> 1) goto _L2; else goto _L1
_L1:
        k1 = j1 >> 2;
        if(_count <= j1 - k1) goto _L4; else goto _L3
_L3:
        _needRehash = true;
_L2:
        return;
_L4:
        if(_collCount >= k1)
            _needRehash = true;
        if(true) goto _L2; else goto _L5
_L5:
    }

    public static final int calcHash(int i)
    {
        int j = i ^ i >>> 16;
        return j ^ j >>> 8;
    }

    public static final int calcHash(int i, int j)
    {
        int k = j + i * 31;
        int l = k ^ k >>> 16;
        return l ^ l >>> 8;
    }

    public static final int calcHash(int ai[], int i)
    {
        int j = ai[0];
        for(int k = 1; k < i; k++)
            j = j * 31 + ai[k];

        int l = j ^ j >>> 16;
        return l ^ l >>> 8;
    }

    public static Name constructName(int i, String s, int j, int k)
    {
        String s1 = InternCache.instance.intern(s);
        Object obj;
        if(k == 0)
            obj = new Name1(s1, i, j);
        else
            obj = new Name2(s1, i, j, k);
        return ((Name) (obj));
    }

    public static Name constructName(int i, String s, int ai[], int j)
    {
        int k;
        String s1;
        k = 0;
        s1 = InternCache.instance.intern(s);
        if(j >= 4) goto _L2; else goto _L1
_L1:
        j;
        JVM INSTR tableswitch 1 3: default 44
    //                   1 70
    //                   2 89
    //                   3 111;
           goto _L2 _L3 _L4 _L5
_L2:
        int ai1[];
        ai1 = new int[j];
        for(; k < j; k++)
            ai1[k] = ai[k];

        break; /* Loop/switch isn't completed */
_L3:
        Object obj = new Name1(s1, i, ai[k]);
_L7:
        return ((Name) (obj));
_L4:
        obj = new Name2(s1, i, ai[k], ai[1]);
        continue; /* Loop/switch isn't completed */
_L5:
        obj = new Name3(s1, i, ai[k], ai[1], ai[2]);
        continue; /* Loop/switch isn't completed */
        obj = new NameN(s1, i, ai1, j);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static BytesToNameCanonicalizer createRoot()
    {
        return new BytesToNameCanonicalizer(64);
    }

    private void expandCollision()
    {
        Bucket abucket[] = _collList;
        int i = abucket.length;
        _collList = new Bucket[i + i];
        System.arraycopy(abucket, 0, _collList, 0, i);
    }

    private int findBestBucket()
    {
        Bucket abucket[];
        int i;
        int j;
        int k;
        int l;
        abucket = _collList;
        i = -1;
        j = _collEnd;
        k = 0x7fffffff;
        l = 0;
_L5:
        if(l >= j) goto _L2; else goto _L1
_L1:
        int j1;
        j1 = abucket[l].length();
        if(j1 >= k)
            continue; /* Loop/switch isn't completed */
        if(j1 != 1) goto _L4; else goto _L3
_L3:
        int i1 = l;
_L6:
        return i1;
_L4:
        i = l;
        k = j1;
        l++;
          goto _L5
_L2:
        i1 = i;
          goto _L6
    }

    public static Name getEmptyName()
    {
        return Name1.getEmptyName();
    }

    private void initTables(int i)
    {
        _count = 0;
        _mainHash = new int[i];
        _mainNames = new Name[i];
        _mainHashShared = false;
        _mainNamesShared = false;
        _mainHashMask = i - 1;
        _collListShared = true;
        _collList = null;
        _collEnd = 0;
        _needRehash = false;
    }

    private void markAsShared()
    {
        _mainHashShared = true;
        _mainNamesShared = true;
        _collListShared = true;
    }

    /**
     * @deprecated Method mergeChild is deprecated
     */

    private void mergeChild(BytesToNameCanonicalizer bytestonamecanonicalizer)
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        int j;
        i = bytestonamecanonicalizer._count;
        j = _count;
        if(i > j) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(bytestonamecanonicalizer.size() <= 6000)
            break MISSING_BLOCK_LABEL_46;
        initTables(64);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        _count = bytestonamecanonicalizer._count;
        _mainHash = bytestonamecanonicalizer._mainHash;
        _mainNames = bytestonamecanonicalizer._mainNames;
        _mainHashShared = true;
        _mainNamesShared = true;
        _mainHashMask = bytestonamecanonicalizer._mainHashMask;
        _collList = bytestonamecanonicalizer._collList;
        _collCount = bytestonamecanonicalizer._collCount;
        _collEnd = bytestonamecanonicalizer._collEnd;
          goto _L1
    }

    private void rehash()
    {
        _needRehash = false;
        _mainNamesShared = false;
        int i = _mainHash.length;
        _mainHash = new int[i + i];
        _mainHashMask = (i + i) - 1;
        Name aname[] = _mainNames;
        _mainNames = new Name[i + i];
        int j = 0;
        int k = 0;
        for(; j < i; j++)
        {
            Name name1 = aname[j];
            if(name1 != null)
            {
                k++;
                int l2 = name1.hashCode();
                int i3 = l2 & _mainHashMask;
                _mainNames[i3] = name1;
                _mainHash[i3] = l2 << 8;
            }
        }

        int l = _collEnd;
        if(l != 0)
        {
            _collCount = 0;
            _collEnd = 0;
            _collListShared = false;
            Bucket abucket[] = _collList;
            _collList = new Bucket[abucket.length];
            for(int i1 = 0; i1 < l;)
            {
                Bucket bucket = abucket[i1];
                int j1 = k;
                Bucket bucket1 = bucket;
                while(bucket1 != null) 
                {
                    j1++;
                    Name name = bucket1.mName;
                    int k1 = name.hashCode();
                    int l1 = k1 & _mainHashMask;
                    int i2 = _mainHash[l1];
                    if(_mainNames[l1] == null)
                    {
                        _mainHash[l1] = k1 << 8;
                        _mainNames[l1] = name;
                    } else
                    {
                        _collCount = 1 + _collCount;
                        int j2 = i2 & 0xff;
                        int k2;
                        if(j2 == 0)
                        {
                            if(_collEnd <= 254)
                            {
                                k2 = _collEnd;
                                _collEnd = 1 + _collEnd;
                                if(k2 >= _collList.length)
                                    expandCollision();
                            } else
                            {
                                k2 = findBestBucket();
                            }
                            _mainHash[l1] = i2 & 0xffffff00 | k2 + 1;
                        } else
                        {
                            k2 = j2 + -1;
                        }
                        _collList[k2] = new Bucket(name, _collList[k2]);
                    }
                    bucket1 = bucket1.mNext;
                }
                i1++;
                k = j1;
            }

            if(k != _count)
                throw new RuntimeException((new StringBuilder()).append("Internal error: count after rehash ").append(k).append("; should be ").append(_count).toString());
        }
    }

    private void unshareCollision()
    {
        Bucket abucket[] = _collList;
        if(abucket == null)
        {
            _collList = new Bucket[32];
        } else
        {
            int i = abucket.length;
            _collList = new Bucket[i];
            System.arraycopy(abucket, 0, _collList, 0, i);
        }
        _collListShared = false;
    }

    private void unshareMain()
    {
        int ai[] = _mainHash;
        int i = _mainHash.length;
        _mainHash = new int[i];
        System.arraycopy(ai, 0, _mainHash, 0, i);
        _mainHashShared = false;
    }

    private void unshareNames()
    {
        Name aname[] = _mainNames;
        int i = aname.length;
        _mainNames = new Name[i];
        System.arraycopy(aname, 0, _mainNames, 0, i);
        _mainNamesShared = false;
    }

    public Name addName(String s, int ai[], int i)
    {
        int j = calcHash(ai, i);
        Name name = constructName(j, s, ai, i);
        _addSymbol(j, name);
        return name;
    }

    public Name findName(int i)
    {
        int j;
        int k;
        int l;
        j = calcHash(i);
        k = j & _mainHashMask;
        l = _mainHash[k];
        if((j ^ l >> 8) << 8 != 0) goto _L2; else goto _L1
_L1:
        Name name1 = _mainNames[k];
        if(name1 != null) goto _L4; else goto _L3
_L3:
        Name name = null;
_L7:
        return name;
_L4:
        if(name1.equals(i))
        {
            name = name1;
            continue; /* Loop/switch isn't completed */
        }
          goto _L5
_L2:
        if(l == 0)
        {
            name = null;
            continue; /* Loop/switch isn't completed */
        }
_L5:
        int i1 = l & 0xff;
        if(i1 > 0)
        {
            int j1 = i1 + -1;
            Bucket bucket = _collList[j1];
            if(bucket != null)
            {
                name = bucket.find(j, i, 0);
                continue; /* Loop/switch isn't completed */
            }
        }
        name = null;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public Name findName(int i, int j)
    {
        int k;
        int l;
        int i1;
        k = calcHash(i, j);
        l = k & _mainHashMask;
        i1 = _mainHash[l];
        if((k ^ i1 >> 8) << 8 != 0) goto _L2; else goto _L1
_L1:
        Name name1 = _mainNames[l];
        if(name1 != null) goto _L4; else goto _L3
_L3:
        Name name = null;
_L7:
        return name;
_L4:
        if(name1.equals(i, j))
        {
            name = name1;
            continue; /* Loop/switch isn't completed */
        }
          goto _L5
_L2:
        if(i1 == 0)
        {
            name = null;
            continue; /* Loop/switch isn't completed */
        }
_L5:
        int j1 = i1 & 0xff;
        if(j1 > 0)
        {
            int k1 = j1 + -1;
            Bucket bucket = _collList[k1];
            if(bucket != null)
            {
                name = bucket.find(k, i, j);
                continue; /* Loop/switch isn't completed */
            }
        }
        name = null;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public Name findName(int ai[], int i)
    {
        int j;
        int k;
        int l;
        j = calcHash(ai, i);
        k = j & _mainHashMask;
        l = _mainHash[k];
        if((j ^ l >> 8) << 8 != 0) goto _L2; else goto _L1
_L1:
        Name name1 = _mainNames[k];
        if(name1 != null && !name1.equals(ai, i)) goto _L4; else goto _L3
_L3:
        Name name = name1;
_L6:
        return name;
_L2:
        if(l == 0)
        {
            name = null;
            continue; /* Loop/switch isn't completed */
        }
_L4:
        int i1 = l & 0xff;
        if(i1 > 0)
        {
            int j1 = i1 + -1;
            Bucket bucket = _collList[j1];
            if(bucket != null)
            {
                name = bucket.find(j, ai, i);
                continue; /* Loop/switch isn't completed */
            }
        }
        name = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    /**
     * @deprecated Method makeChild is deprecated
     */

    public BytesToNameCanonicalizer makeChild()
    {
        this;
        JVM INSTR monitorenter ;
        BytesToNameCanonicalizer bytestonamecanonicalizer = new BytesToNameCanonicalizer(this);
        this;
        JVM INSTR monitorexit ;
        return bytestonamecanonicalizer;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean maybeDirty()
    {
        boolean flag;
        if(!_mainHashShared)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void release()
    {
        if(maybeDirty() && _parent != null)
        {
            _parent.mergeChild(this);
            markAsShared();
        }
    }

    public int size()
    {
        return _count;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[NameCanonicalizer, size: ");
        stringbuilder.append(_count);
        stringbuilder.append('/');
        stringbuilder.append(_mainHash.length);
        stringbuilder.append(", ");
        stringbuilder.append(_collCount);
        stringbuilder.append(" coll; avg length: ");
        int i = _count;
        for(int j = 0; j < _collEnd;)
        {
            int k = _collList[j].length();
            int l = i;
            for(int i1 = 1; i1 <= k; i1++)
                l += i1;

            j++;
            i = l;
        }

        double d;
        if(_count == 0)
            d = 0D;
        else
            d = (double)i / (double)_count;
        stringbuilder.append(d);
        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    protected static final int DEFAULT_TABLE_SIZE = 64;
    static final int INITIAL_COLLISION_LEN = 32;
    static final int LAST_VALID_BUCKET = 254;
    static final int MAX_TABLE_SIZE = 6000;
    static final int MIN_HASH_SIZE = 16;
    private int _collCount;
    private int _collEnd;
    private Bucket _collList[];
    private boolean _collListShared;
    private int _count;
    private int _mainHash[];
    private int _mainHashMask;
    private boolean _mainHashShared;
    private Name _mainNames[];
    private boolean _mainNamesShared;
    private transient boolean _needRehash;
    final BytesToNameCanonicalizer _parent;
}
