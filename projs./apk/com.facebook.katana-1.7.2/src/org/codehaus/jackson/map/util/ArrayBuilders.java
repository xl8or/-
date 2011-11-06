// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.util;


// Referenced classes of package org.codehaus.jackson.map.util:
//            PrimitiveArrayBuilder

public final class ArrayBuilders
{
    public static final class DoubleBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final double[] _constructArray(int i)
        {
            return new double[i];
        }

        public DoubleBuilder()
        {
        }
    }

    public static final class FloatBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final float[] _constructArray(int i)
        {
            return new float[i];
        }

        public FloatBuilder()
        {
        }
    }

    public static final class LongBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final long[] _constructArray(int i)
        {
            return new long[i];
        }

        public LongBuilder()
        {
        }
    }

    public static final class IntBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final int[] _constructArray(int i)
        {
            return new int[i];
        }

        public IntBuilder()
        {
        }
    }

    public static final class ShortBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final short[] _constructArray(int i)
        {
            return new short[i];
        }

        public ShortBuilder()
        {
        }
    }

    public static final class ByteBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final byte[] _constructArray(int i)
        {
            return new byte[i];
        }

        public ByteBuilder()
        {
        }
    }

    public static final class BooleanBuilder extends PrimitiveArrayBuilder
    {

        public volatile Object _constructArray(int i)
        {
            return _constructArray(i);
        }

        public final boolean[] _constructArray(int i)
        {
            return new boolean[i];
        }

        public BooleanBuilder()
        {
        }
    }


    public ArrayBuilders()
    {
        _booleanBuilder = null;
        _byteBuilder = null;
        _shortBuilder = null;
        _intBuilder = null;
        _longBuilder = null;
        _floatBuilder = null;
        _doubleBuilder = null;
    }

    public BooleanBuilder getBooleanBuilder()
    {
        if(_booleanBuilder == null)
            _booleanBuilder = new BooleanBuilder();
        return _booleanBuilder;
    }

    public ByteBuilder getByteBuilder()
    {
        if(_byteBuilder == null)
            _byteBuilder = new ByteBuilder();
        return _byteBuilder;
    }

    public DoubleBuilder getDoubleBuilder()
    {
        if(_doubleBuilder == null)
            _doubleBuilder = new DoubleBuilder();
        return _doubleBuilder;
    }

    public FloatBuilder getFloatBuilder()
    {
        if(_floatBuilder == null)
            _floatBuilder = new FloatBuilder();
        return _floatBuilder;
    }

    public IntBuilder getIntBuilder()
    {
        if(_intBuilder == null)
            _intBuilder = new IntBuilder();
        return _intBuilder;
    }

    public LongBuilder getLongBuilder()
    {
        if(_longBuilder == null)
            _longBuilder = new LongBuilder();
        return _longBuilder;
    }

    public ShortBuilder getShortBuilder()
    {
        if(_shortBuilder == null)
            _shortBuilder = new ShortBuilder();
        return _shortBuilder;
    }

    BooleanBuilder _booleanBuilder;
    ByteBuilder _byteBuilder;
    DoubleBuilder _doubleBuilder;
    FloatBuilder _floatBuilder;
    IntBuilder _intBuilder;
    LongBuilder _longBuilder;
    ShortBuilder _shortBuilder;
}
