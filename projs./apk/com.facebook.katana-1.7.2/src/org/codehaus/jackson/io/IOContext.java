package org.codehaus.jackson.io;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.util.BufferRecycler;
import org.codehaus.jackson.util.TextBuffer;

public final class IOContext {

   final BufferRecycler _bufferRecycler;
   protected char[] _concatBuffer = null;
   protected JsonEncoding _encoding;
   protected final boolean _managedResource;
   protected char[] _nameCopyBuffer = null;
   protected byte[] _readIOBuffer = null;
   final Object _sourceRef;
   protected char[] _tokenBuffer = null;
   protected byte[] _writeIOBuffer = null;


   public IOContext(BufferRecycler var1, Object var2, boolean var3) {
      this._bufferRecycler = var1;
      this._sourceRef = var2;
      this._managedResource = var3;
   }

   public char[] allocConcatBuffer() {
      if(this._concatBuffer != null) {
         throw new IllegalStateException("Trying to call allocConcatBuffer() second time");
      } else {
         BufferRecycler var1 = this._bufferRecycler;
         BufferRecycler.CharBufferType var2 = BufferRecycler.CharBufferType.CONCAT_BUFFER;
         char[] var3 = var1.allocCharBuffer(var2);
         this._concatBuffer = var3;
         return this._concatBuffer;
      }
   }

   public char[] allocNameCopyBuffer(int var1) {
      if(this._nameCopyBuffer != null) {
         throw new IllegalStateException("Trying to call allocNameCopyBuffer() second time");
      } else {
         BufferRecycler var2 = this._bufferRecycler;
         BufferRecycler.CharBufferType var3 = BufferRecycler.CharBufferType.NAME_COPY_BUFFER;
         char[] var4 = var2.allocCharBuffer(var3, var1);
         this._nameCopyBuffer = var4;
         return this._nameCopyBuffer;
      }
   }

   public byte[] allocReadIOBuffer() {
      if(this._readIOBuffer != null) {
         throw new IllegalStateException("Trying to call allocReadIOBuffer() second time");
      } else {
         BufferRecycler var1 = this._bufferRecycler;
         BufferRecycler.ByteBufferType var2 = BufferRecycler.ByteBufferType.READ_IO_BUFFER;
         byte[] var3 = var1.allocByteBuffer(var2);
         this._readIOBuffer = var3;
         return this._readIOBuffer;
      }
   }

   public char[] allocTokenBuffer() {
      if(this._tokenBuffer != null) {
         throw new IllegalStateException("Trying to call allocTokenBuffer() second time");
      } else {
         BufferRecycler var1 = this._bufferRecycler;
         BufferRecycler.CharBufferType var2 = BufferRecycler.CharBufferType.TOKEN_BUFFER;
         char[] var3 = var1.allocCharBuffer(var2);
         this._tokenBuffer = var3;
         return this._tokenBuffer;
      }
   }

   public byte[] allocWriteIOBuffer() {
      if(this._writeIOBuffer != null) {
         throw new IllegalStateException("Trying to call allocWriteIOBuffer() second time");
      } else {
         BufferRecycler var1 = this._bufferRecycler;
         BufferRecycler.ByteBufferType var2 = BufferRecycler.ByteBufferType.WRITE_IO_BUFFER;
         byte[] var3 = var1.allocByteBuffer(var2);
         this._writeIOBuffer = var3;
         return this._writeIOBuffer;
      }
   }

   public TextBuffer constructTextBuffer() {
      BufferRecycler var1 = this._bufferRecycler;
      return new TextBuffer(var1);
   }

   public JsonEncoding getEncoding() {
      return this._encoding;
   }

   public Object getSourceReference() {
      return this._sourceRef;
   }

   public boolean isResourceManaged() {
      return this._managedResource;
   }

   public void releaseConcatBuffer(char[] var1) {
      if(var1 != null) {
         char[] var2 = this._concatBuffer;
         if(var1 != var2) {
            throw new IllegalArgumentException("Trying to release buffer not owned by the context");
         } else {
            this._concatBuffer = null;
            BufferRecycler var3 = this._bufferRecycler;
            BufferRecycler.CharBufferType var4 = BufferRecycler.CharBufferType.CONCAT_BUFFER;
            var3.releaseCharBuffer(var4, var1);
         }
      }
   }

   public void releaseNameCopyBuffer(char[] var1) {
      if(var1 != null) {
         char[] var2 = this._nameCopyBuffer;
         if(var1 != var2) {
            throw new IllegalArgumentException("Trying to release buffer not owned by the context");
         } else {
            this._nameCopyBuffer = null;
            BufferRecycler var3 = this._bufferRecycler;
            BufferRecycler.CharBufferType var4 = BufferRecycler.CharBufferType.NAME_COPY_BUFFER;
            var3.releaseCharBuffer(var4, var1);
         }
      }
   }

   public void releaseReadIOBuffer(byte[] var1) {
      if(var1 != null) {
         byte[] var2 = this._readIOBuffer;
         if(var1 != var2) {
            throw new IllegalArgumentException("Trying to release buffer not owned by the context");
         } else {
            this._readIOBuffer = null;
            BufferRecycler var3 = this._bufferRecycler;
            BufferRecycler.ByteBufferType var4 = BufferRecycler.ByteBufferType.READ_IO_BUFFER;
            var3.releaseByteBuffer(var4, var1);
         }
      }
   }

   public void releaseTokenBuffer(char[] var1) {
      if(var1 != null) {
         char[] var2 = this._tokenBuffer;
         if(var1 != var2) {
            throw new IllegalArgumentException("Trying to release buffer not owned by the context");
         } else {
            this._tokenBuffer = null;
            BufferRecycler var3 = this._bufferRecycler;
            BufferRecycler.CharBufferType var4 = BufferRecycler.CharBufferType.TOKEN_BUFFER;
            var3.releaseCharBuffer(var4, var1);
         }
      }
   }

   public void releaseWriteIOBuffer(byte[] var1) {
      if(var1 != null) {
         byte[] var2 = this._writeIOBuffer;
         if(var1 != var2) {
            throw new IllegalArgumentException("Trying to release buffer not owned by the context");
         } else {
            this._writeIOBuffer = null;
            BufferRecycler var3 = this._bufferRecycler;
            BufferRecycler.ByteBufferType var4 = BufferRecycler.ByteBufferType.WRITE_IO_BUFFER;
            var3.releaseByteBuffer(var4, var1);
         }
      }
   }

   public void setEncoding(JsonEncoding var1) {
      this._encoding = var1;
   }
}
