package org.codehaus.jackson.util;


public final class BufferRecycler {

   protected final byte[][] mByteBuffers;
   protected final char[][] mCharBuffers;


   public BufferRecycler() {
      byte[] var1 = new byte[BufferRecycler.ByteBufferType.values().length];
      this.mByteBuffers = (byte[][])var1;
      char[] var2 = new char[BufferRecycler.CharBufferType.values().length];
      this.mCharBuffers = (char[][])var2;
   }

   private byte[] balloc(int var1) {
      return new byte[var1];
   }

   private char[] calloc(int var1) {
      return new char[var1];
   }

   public byte[] allocByteBuffer(BufferRecycler.ByteBufferType var1) {
      int var2 = var1.ordinal();
      byte[] var3 = this.mByteBuffers[var2];
      byte[] var5;
      if(var3 == null) {
         int var4 = var1.size;
         var5 = this.balloc(var4);
      } else {
         this.mByteBuffers[var2] = (byte[])false;
         var5 = var3;
      }

      return var5;
   }

   public char[] allocCharBuffer(BufferRecycler.CharBufferType var1) {
      return this.allocCharBuffer(var1, 0);
   }

   public char[] allocCharBuffer(BufferRecycler.CharBufferType var1, int var2) {
      int var3;
      if(var1.size > var2) {
         var3 = var1.size;
      } else {
         var3 = var2;
      }

      int var4 = var1.ordinal();
      char[] var5 = this.mCharBuffers[var4];
      char[] var6;
      if(var5 != null && var5.length >= var3) {
         this.mCharBuffers[var4] = (char[])false;
         var6 = var5;
      } else {
         var6 = this.calloc(var3);
      }

      return var6;
   }

   public void releaseByteBuffer(BufferRecycler.ByteBufferType var1, byte[] var2) {
      byte[][] var3 = this.mByteBuffers;
      int var4 = var1.ordinal();
      var3[var4] = var2;
   }

   public void releaseCharBuffer(BufferRecycler.CharBufferType var1, char[] var2) {
      char[][] var3 = this.mCharBuffers;
      int var4 = var1.ordinal();
      var3[var4] = var2;
   }

   public static enum CharBufferType {

      // $FF: synthetic field
      private static final BufferRecycler.CharBufferType[] $VALUES;
      CONCAT_BUFFER("CONCAT_BUFFER", 1, 2000),
      NAME_COPY_BUFFER("NAME_COPY_BUFFER", 3, 200),
      TEXT_BUFFER("TEXT_BUFFER", 2, 200),
      TOKEN_BUFFER("TOKEN_BUFFER", 0, 2000);
      private final int size;


      static {
         BufferRecycler.CharBufferType[] var0 = new BufferRecycler.CharBufferType[4];
         BufferRecycler.CharBufferType var1 = TOKEN_BUFFER;
         var0[0] = var1;
         BufferRecycler.CharBufferType var2 = CONCAT_BUFFER;
         var0[1] = var2;
         BufferRecycler.CharBufferType var3 = TEXT_BUFFER;
         var0[2] = var3;
         BufferRecycler.CharBufferType var4 = NAME_COPY_BUFFER;
         var0[3] = var4;
         $VALUES = var0;
      }

      private CharBufferType(String var1, int var2, int var3) {
         this.size = var3;
      }
   }

   public static enum ByteBufferType {

      // $FF: synthetic field
      private static final BufferRecycler.ByteBufferType[] $VALUES;
      READ_IO_BUFFER("READ_IO_BUFFER", 0, 4000),
      WRITE_IO_BUFFER("WRITE_IO_BUFFER", 1, 4000);
      private final int size;


      static {
         BufferRecycler.ByteBufferType[] var0 = new BufferRecycler.ByteBufferType[2];
         BufferRecycler.ByteBufferType var1 = READ_IO_BUFFER;
         var0[0] = var1;
         BufferRecycler.ByteBufferType var2 = WRITE_IO_BUFFER;
         var0[1] = var2;
         $VALUES = var0;
      }

      private ByteBufferType(String var1, int var2, int var3) {
         this.size = var3;
      }
   }
}
