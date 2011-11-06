// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.sym;

import org.codehaus.jackson.util.InternCache;

public final class CharsToNameCanonicalizer
{
    static final class Bucket
    {

        public String find(char ac[], int i, int j)
        {
            String s1;
            Bucket bucket1;
            String s = _symbol;
            Bucket bucket = mNext;
            s1 = s;
            bucket1 = bucket;
_L4:
            String s2;
            if(s1.length() != j)
                break MISSING_BLOCK_LABEL_73;
            for(int k = 0; s1.charAt(k) == ac[i + k] && ++k < j;);
            if(k != j)
                break MISSING_BLOCK_LABEL_73;
            s2 = s1;
_L2:
            return s2;
label0:
            {
                if(bucket1 != null)
                    break label0;
                s2 = null;
            }
            if(true) goto _L2; else goto _L1
_L1:
            s1 = bucket1.getSymbol();
            bucket1 = bucket1.getNext();
            if(true) goto _L4; else goto _L3
_L3:
        }

        public Bucket getNext()
        {
            return mNext;
        }

        public String getSymbol()
        {
            return _symbol;
        }

        private final String _symbol;
        private final Bucket mNext;

        public Bucket(String s, Bucket bucket)
        {
            _symbol = s;
            mNext = bucket;
        }
    }


    public CharsToNameCanonicalizer(int i)
    {
        _dirty = true;
        if(i < 1)
            throw new IllegalArgumentException((new StringBuilder()).append("Can not use negative/zero initial size: ").append(i).toString());
        int j;
        for(j = 4; j < i; j += j);
        initTables(j);
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charstonamecanonicalizer, String as[], Bucket abucket[], int i)
    {
        _parent = charstonamecanonicalizer;
        _symbols = as;
        _buckets = abucket;
        _size = i;
        int j = as.length;
        _sizeThreshold = j - (j >> 2);
        _indexMask = j - 1;
        _dirty = false;
    }

    public static int calcHash(String s)
    {
        char c = s.charAt(0);
        int i = s.length();
        int j = c;
        for(int k = 1; k < i; k++)
            j = j * 31 + s.charAt(k);

        return j;
    }

    public static int calcHash(char ac[], int i, int j)
    {
        int k = ac[0];
        for(int l = 1; l < j; l++)
            k = k * 31 + ac[l];

        return k;
    }

    private void copyArrays()
    {
        String as[] = _symbols;
        int i = as.length;
        _symbols = new String[i];
        System.arraycopy(as, 0, _symbols, 0, i);
        Bucket abucket[] = _buckets;
        int j = abucket.length;
        _buckets = new Bucket[j];
        System.arraycopy(abucket, 0, _buckets, 0, j);
    }

    public static CharsToNameCanonicalizer createRoot()
    {
        return sBootstrapSymbolTable.makeOrphan();
    }

    private void initTables(int i)
    {
        _symbols = new String[i];
        _buckets = new Bucket[i >> 1];
        _indexMask = i - 1;
        _size = 0;
        _sizeThreshold = i - (i >> 2);
    }

    private CharsToNameCanonicalizer makeOrphan()
    {
        return new CharsToNameCanonicalizer(null, _symbols, _buckets, _size);
    }

    /**
     * @deprecated Method mergeChild is deprecated
     */

    private void mergeChild(CharsToNameCanonicalizer charstonamecanonicalizer)
    {
        this;
        JVM INSTR monitorenter ;
        if(charstonamecanonicalizer.size() <= 6000) goto _L2; else goto _L1
_L1:
        initTables(64);
_L5:
        _dirty = false;
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(charstonamecanonicalizer.size() <= size()) goto _L4; else goto _L3
_L3:
        _symbols = charstonamecanonicalizer._symbols;
        _buckets = charstonamecanonicalizer._buckets;
        _size = charstonamecanonicalizer._size;
        _sizeThreshold = charstonamecanonicalizer._sizeThreshold;
        _indexMask = charstonamecanonicalizer._indexMask;
          goto _L5
        Exception exception;
        exception;
        throw exception;
    }

    private void rehash()
    {
        int i = _symbols.length;
        int j = i + i;
        String as[] = _symbols;
        Bucket abucket[] = _buckets;
        _symbols = new String[j];
        _buckets = new Bucket[j >> 1];
        _indexMask = j - 1;
        _sizeThreshold = _sizeThreshold + _sizeThreshold;
        int k = 0;
        int l = 0;
        while(k < i) 
        {
            String s1 = as[k];
            if(s1 != null)
            {
                l++;
                int k2 = calcHash(s1) & _indexMask;
                if(_symbols[k2] == null)
                {
                    _symbols[k2] = s1;
                } else
                {
                    int l2 = k2 >> 1;
                    _buckets[l2] = new Bucket(s1, _buckets[l2]);
                }
            }
            k++;
        }
        int i1 = i >> 1;
        int j1 = 0;
        int k1;
        int l1;
        for(k1 = l; j1 < i1; k1 = l1)
        {
            Bucket bucket = abucket[j1];
            l1 = k1;
            Bucket bucket1 = bucket;
            while(bucket1 != null) 
            {
                l1++;
                String s = bucket1.getSymbol();
                int i2 = calcHash(s) & _indexMask;
                if(_symbols[i2] == null)
                {
                    _symbols[i2] = s;
                } else
                {
                    int j2 = i2 >> 1;
                    _buckets[j2] = new Bucket(s, _buckets[j2]);
                }
                bucket1 = bucket1.getNext();
            }
            j1++;
        }

        if(k1 != _size)
            throw new Error((new StringBuilder()).append("Internal error on SymbolTable.rehash(): had ").append(_size).append(" entries; now have ").append(k1).append(".").toString());
        else
            return;
    }

    public String findSymbol(char ac[], int i, int j, int k)
    {
        if(j >= 1) goto _L2; else goto _L1
_L1:
        String s3 = "";
_L16:
        return s3;
_L2:
        int l;
        String s;
        l = k & _indexMask;
        s = _symbols[l];
        if(s == null) goto _L4; else goto _L3
_L3:
        if(s.length() != j) goto _L6; else goto _L5
_L5:
        int j1 = 0;
_L11:
        if(s.charAt(j1) == ac[i + j1]) goto _L8; else goto _L7
_L7:
        if(j1 != j) goto _L6; else goto _L9
_L9:
        s3 = s;
          goto _L10
_L8:
        j1++;
        if(j1 < j) goto _L11; else goto _L7
_L6:
        Bucket bucket = _buckets[l >> 1];
        if(bucket == null) goto _L4; else goto _L12
_L12:
        String s4 = bucket.find(ac, i, j);
        if(s4 == null) goto _L4; else goto _L13
_L13:
        s3 = s4;
          goto _L10
_L4:
        if(_dirty) goto _L15; else goto _L14
_L14:
        copyArrays();
        _dirty = true;
_L17:
        _size = 1 + _size;
        String s1 = new String(ac, i, j);
        String s2 = InternCache.instance.intern(s1);
        if(_symbols[l] == null)
        {
            _symbols[l] = s2;
        } else
        {
            int i1 = l >> 1;
            _buckets[i1] = new Bucket(s2, _buckets[i1]);
        }
        s3 = s2;
_L10:
        if(true) goto _L16; else goto _L15
_L15:
        if(_size >= _sizeThreshold)
        {
            rehash();
            l = calcHash(ac, i, j) & _indexMask;
        }
          goto _L17
    }

    /**
     * @deprecated Method makeChild is deprecated
     */

    public CharsToNameCanonicalizer makeChild()
    {
        this;
        JVM INSTR monitorenter ;
        CharsToNameCanonicalizer charstonamecanonicalizer = new CharsToNameCanonicalizer(this, _symbols, _buckets, _size);
        this;
        JVM INSTR monitorexit ;
        return charstonamecanonicalizer;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean maybeDirty()
    {
        return _dirty;
    }

    public void release()
    {
        if(maybeDirty() && _parent != null)
        {
            _parent.mergeChild(this);
            _dirty = false;
        }
    }

    public int size()
    {
        return _size;
    }

    protected static final int DEFAULT_TABLE_SIZE = 64;
    protected static final boolean INTERN_STRINGS = true;
    static final int MAX_SYMBOL_TABLE_SIZE = 6000;
    static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer(64);
    protected Bucket _buckets[];
    protected boolean _dirty;
    protected int _indexMask;
    protected CharsToNameCanonicalizer _parent;
    protected int _size;
    protected int _sizeThreshold;
    protected String _symbols[];

}
