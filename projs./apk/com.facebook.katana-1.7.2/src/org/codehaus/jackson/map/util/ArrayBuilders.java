package org.codehaus.jackson.map.util;

import org.codehaus.jackson.map.util.PrimitiveArrayBuilder;

public final class ArrayBuilders {

   ArrayBuilders.BooleanBuilder _booleanBuilder = null;
   ArrayBuilders.ByteBuilder _byteBuilder = null;
   ArrayBuilders.DoubleBuilder _doubleBuilder = null;
   ArrayBuilders.FloatBuilder _floatBuilder = null;
   ArrayBuilders.IntBuilder _intBuilder = null;
   ArrayBuilders.LongBuilder _longBuilder = null;
   ArrayBuilders.ShortBuilder _shortBuilder = null;


   public ArrayBuilders() {}

   public ArrayBuilders.BooleanBuilder getBooleanBuilder() {
      if(this._booleanBuilder == null) {
         ArrayBuilders.BooleanBuilder var1 = new ArrayBuilders.BooleanBuilder();
         this._booleanBuilder = var1;
      }

      return this._booleanBuilder;
   }

   public ArrayBuilders.ByteBuilder getByteBuilder() {
      if(this._byteBuilder == null) {
         ArrayBuilders.ByteBuilder var1 = new ArrayBuilders.ByteBuilder();
         this._byteBuilder = var1;
      }

      return this._byteBuilder;
   }

   public ArrayBuilders.DoubleBuilder getDoubleBuilder() {
      if(this._doubleBuilder == null) {
         ArrayBuilders.DoubleBuilder var1 = new ArrayBuilders.DoubleBuilder();
         this._doubleBuilder = var1;
      }

      return this._doubleBuilder;
   }

   public ArrayBuilders.FloatBuilder getFloatBuilder() {
      if(this._floatBuilder == null) {
         ArrayBuilders.FloatBuilder var1 = new ArrayBuilders.FloatBuilder();
         this._floatBuilder = var1;
      }

      return this._floatBuilder;
   }

   public ArrayBuilders.IntBuilder getIntBuilder() {
      if(this._intBuilder == null) {
         ArrayBuilders.IntBuilder var1 = new ArrayBuilders.IntBuilder();
         this._intBuilder = var1;
      }

      return this._intBuilder;
   }

   public ArrayBuilders.LongBuilder getLongBuilder() {
      if(this._longBuilder == null) {
         ArrayBuilders.LongBuilder var1 = new ArrayBuilders.LongBuilder();
         this._longBuilder = var1;
      }

      return this._longBuilder;
   }

   public ArrayBuilders.ShortBuilder getShortBuilder() {
      if(this._shortBuilder == null) {
         ArrayBuilders.ShortBuilder var1 = new ArrayBuilders.ShortBuilder();
         this._shortBuilder = var1;
      }

      return this._shortBuilder;
   }

   public static final class FloatBuilder extends PrimitiveArrayBuilder<float[]> {

      public FloatBuilder() {}

      public final float[] _constructArray(int var1) {
         return new float[var1];
      }
   }

   public static final class DoubleBuilder extends PrimitiveArrayBuilder<double[]> {

      public DoubleBuilder() {}

      public final double[] _constructArray(int var1) {
         return new double[var1];
      }
   }

   public static final class ByteBuilder extends PrimitiveArrayBuilder<byte[]> {

      public ByteBuilder() {}

      public final byte[] _constructArray(int var1) {
         return new byte[var1];
      }
   }

   public static final class IntBuilder extends PrimitiveArrayBuilder<int[]> {

      public IntBuilder() {}

      public final int[] _constructArray(int var1) {
         return new int[var1];
      }
   }

   public static final class LongBuilder extends PrimitiveArrayBuilder<long[]> {

      public LongBuilder() {}

      public final long[] _constructArray(int var1) {
         return (long[])var1;
      }
   }

   public static final class ShortBuilder extends PrimitiveArrayBuilder<short[]> {

      public ShortBuilder() {}

      public final short[] _constructArray(int var1) {
         return (short[])var1;
      }
   }

   public static final class BooleanBuilder extends PrimitiveArrayBuilder<boolean[]> {

      public BooleanBuilder() {}

      public final boolean[] _constructArray(int var1) {
         return new boolean[var1];
      }
   }
}
