package org.codehaus.jackson.map.util;


public abstract class PrimitiveArrayBuilder<T extends Object> {

   static final int INITIAL_CHUNK_SIZE = 12;
   static final int MAX_CHUNK_SIZE = 262144;
   static final int SMALL_CHUNK_SIZE = 16384;
   PrimitiveArrayBuilder.Node<T> _bufferHead;
   PrimitiveArrayBuilder.Node<T> _bufferTail;
   int _bufferedEntryCount;
   T _freeBuffer;


   protected PrimitiveArrayBuilder() {}

   protected abstract T _constructArray(int var1);

   protected void _reset() {
      if(this._bufferTail != null) {
         Object var1 = this._bufferTail.getData();
         this._freeBuffer = var1;
      }

      this._bufferTail = null;
      this._bufferHead = null;
      this._bufferedEntryCount = 0;
   }

   public final T appendCompletedChunk(T var1, int var2) {
      PrimitiveArrayBuilder.Node var3 = new PrimitiveArrayBuilder.Node(var1, var2);
      if(this._bufferHead == null) {
         this._bufferTail = var3;
         this._bufferHead = var3;
      } else {
         this._bufferTail.linkNext(var3);
         this._bufferTail = var3;
      }

      int var4 = this._bufferedEntryCount + var2;
      this._bufferedEntryCount = var4;
      int var5;
      if(var2 < 16384) {
         var5 = var2 + var2;
      } else {
         var5 = (var2 >> 2) + var2;
      }

      return this._constructArray(var5);
   }

   public T completeAndClearBuffer(T var1, int var2) {
      int var3 = this._bufferedEntryCount + var2;
      Object var4 = this._constructArray(var3);
      PrimitiveArrayBuilder.Node var5 = this._bufferHead;

      int var6;
      for(var6 = 0; var5 != null; var5 = var5.next()) {
         var6 = var5.copyData(var4, var6);
      }

      System.arraycopy(var1, 0, var4, var6, var2);
      int var7 = var6 + var2;
      if(var7 != var3) {
         String var8 = "Should have gotten " + var3 + " entries, got " + var7;
         throw new IllegalStateException(var8);
      } else {
         return var4;
      }
   }

   public T resetAndStart() {
      this._reset();
      Object var1;
      if(this._freeBuffer == null) {
         var1 = this._constructArray(12);
      } else {
         var1 = this._freeBuffer;
      }

      return var1;
   }

   static final class Node<T extends Object> {

      final T _data;
      final int _dataLength;
      PrimitiveArrayBuilder.Node<T> _next;


      public Node(T var1, int var2) {
         this._data = var1;
         this._dataLength = var2;
      }

      public int copyData(T var1, int var2) {
         Object var3 = this._data;
         int var4 = this._dataLength;
         System.arraycopy(var3, 0, var1, var2, var4);
         return this._dataLength + var2;
      }

      public T getData() {
         return this._data;
      }

      public void linkNext(PrimitiveArrayBuilder.Node<T> var1) {
         if(this._next != null) {
            throw new IllegalStateException();
         } else {
            this._next = var1;
         }
      }

      public PrimitiveArrayBuilder.Node<T> next() {
         return this._next;
      }
   }
}
