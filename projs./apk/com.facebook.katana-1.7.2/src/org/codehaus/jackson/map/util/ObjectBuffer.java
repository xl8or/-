package org.codehaus.jackson.map.util;

import java.lang.reflect.Array;

public final class ObjectBuffer {

   static final int INITIAL_CHUNK_SIZE = 12;
   static final int MAX_CHUNK_SIZE = 262144;
   static final int SMALL_CHUNK_SIZE = 16384;
   ObjectBuffer.Node _bufferHead;
   ObjectBuffer.Node _bufferTail;
   int _bufferedEntryCount;
   Object[] _freeBuffer;


   public ObjectBuffer() {}

   protected final void _copyTo(Object var1, int var2, Object[] var3, int var4) {
      ObjectBuffer.Node var5 = this._bufferHead;

      int var6;
      for(var6 = 0; var5 != null; var5 = var5.next()) {
         Object[] var7 = var5.getData();
         int var8 = var7.length;
         System.arraycopy(var7, 0, var1, var6, var8);
         var6 += var8;
      }

      System.arraycopy(var3, 0, var1, var6, var4);
      int var9 = var6 + var4;
      if(var9 != var2) {
         String var10 = "Should have gotten " + var2 + " entries, got " + var9;
         throw new IllegalStateException(var10);
      }
   }

   protected void _reset() {
      if(this._bufferTail != null) {
         Object[] var1 = this._bufferTail.getData();
         this._freeBuffer = var1;
      }

      this._bufferTail = null;
      this._bufferHead = null;
      this._bufferedEntryCount = 0;
   }

   public Object[] appendCompletedChunk(Object[] var1) {
      ObjectBuffer.Node var2 = new ObjectBuffer.Node(var1);
      if(this._bufferHead == null) {
         this._bufferTail = var2;
         this._bufferHead = var2;
      } else {
         this._bufferTail.linkNext(var2);
         this._bufferTail = var2;
      }

      int var3 = var1.length;
      int var4 = this._bufferedEntryCount + var3;
      this._bufferedEntryCount = var4;
      int var5;
      if(var3 < 16384) {
         var5 = var3 + var3;
      } else {
         int var6 = var3 >> 2;
         var5 = var3 + var6;
      }

      return new Object[var5];
   }

   public int bufferedSize() {
      return this._bufferedEntryCount;
   }

   public Object[] completeAndClearBuffer(Object[] var1, int var2) {
      int var3 = this._bufferedEntryCount + var2;
      Object[] var4 = new Object[var3];
      this._copyTo(var4, var3, var1, var2);
      return var4;
   }

   public <T extends Object> T[] completeAndClearBuffer(Object[] var1, int var2, Class<T> var3) {
      int var4 = this._bufferedEntryCount;
      int var5 = var2 + var4;
      Object[] var6 = (Object[])((Object[])Array.newInstance(var3, var5));
      this._copyTo(var6, var5, var1, var2);
      this._reset();
      return var6;
   }

   public int initialCapacity() {
      int var1;
      if(this._freeBuffer == null) {
         var1 = 0;
      } else {
         var1 = this._freeBuffer.length;
      }

      return var1;
   }

   public Object[] resetAndStart() {
      this._reset();
      Object[] var1;
      if(this._freeBuffer == null) {
         var1 = new Object[12];
      } else {
         var1 = this._freeBuffer;
      }

      return var1;
   }

   static final class Node {

      final Object[] _data;
      ObjectBuffer.Node _next;


      public Node(Object[] var1) {
         this._data = var1;
      }

      public Object[] getData() {
         return this._data;
      }

      public void linkNext(ObjectBuffer.Node var1) {
         if(this._next != null) {
            throw new IllegalStateException();
         } else {
            this._next = var1;
         }
      }

      public ObjectBuffer.Node next() {
         return this._next;
      }
   }
}
