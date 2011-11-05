package org.apache.commons.io.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ByteArrayOutputStream extends OutputStream {

   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   private List buffers;
   private int count;
   private byte[] currentBuffer;
   private int currentBufferIndex;
   private int filledBufferSum;


   public ByteArrayOutputStream() {
      this(1024);
   }

   public ByteArrayOutputStream(int var1) {
      ArrayList var2 = new ArrayList();
      this.buffers = var2;
      if(var1 < 0) {
         String var3 = "Negative initial size: " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         this.needNewBuffer(var1);
      }
   }

   private byte[] getBuffer(int var1) {
      return (byte[])((byte[])this.buffers.get(var1));
   }

   private void needNewBuffer(int var1) {
      int var2 = this.currentBufferIndex;
      int var3 = this.buffers.size() - 1;
      if(var2 < var3) {
         int var4 = this.filledBufferSum;
         int var5 = this.currentBuffer.length;
         int var6 = var4 + var5;
         this.filledBufferSum = var6;
         int var7 = this.currentBufferIndex + 1;
         this.currentBufferIndex = var7;
         int var8 = this.currentBufferIndex;
         byte[] var9 = this.getBuffer(var8);
         this.currentBuffer = var9;
      } else {
         int var10;
         if(this.currentBuffer == null) {
            var10 = var1;
            this.filledBufferSum = 0;
         } else {
            int var16 = this.currentBuffer.length << 1;
            int var17 = this.filledBufferSum;
            int var18 = var1 - var17;
            var10 = Math.max(var16, var18);
            int var19 = this.filledBufferSum;
            int var20 = this.currentBuffer.length;
            int var21 = var19 + var20;
            this.filledBufferSum = var21;
         }

         int var11 = this.currentBufferIndex + 1;
         this.currentBufferIndex = var11;
         byte[] var12 = new byte[var10];
         this.currentBuffer = var12;
         List var13 = this.buffers;
         byte[] var14 = this.currentBuffer;
         var13.add(var14);
      }
   }

   public void close() throws IOException {}

   public void reset() {
      synchronized(this){}

      try {
         this.count = 0;
         this.filledBufferSum = 0;
         this.currentBufferIndex = 0;
         int var1 = this.currentBufferIndex;
         byte[] var2 = this.getBuffer(var1);
         this.currentBuffer = var2;
      } finally {
         ;
      }

   }

   public int size() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.count;
      } finally {
         ;
      }

      return var1;
   }

   public byte[] toByteArray() {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      byte[] var1 = this.toByteArray();
      return new String(var1);
   }

   public String toString(String var1) throws UnsupportedEncodingException {
      byte[] var2 = this.toByteArray();
      return new String(var2, var1);
   }

   public int write(InputStream param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void write(int var1) {
      synchronized(this){}

      try {
         int var2 = this.count;
         int var3 = this.filledBufferSum;
         int var4 = var2 - var3;
         int var5 = this.currentBuffer.length;
         if(var4 == var5) {
            int var6 = this.count + 1;
            this.needNewBuffer(var6);
            var4 = 0;
         }

         byte[] var7 = this.currentBuffer;
         byte var8 = (byte)var1;
         var7[var4] = var8;
         int var9 = this.count + 1;
         this.count = var9;
      } finally {
         ;
      }

   }

   public void write(byte[] var1, int var2, int var3) {
      if(var2 >= 0) {
         int var4 = var1.length;
         if(var2 <= var4 && var3 >= 0) {
            int var5 = var2 + var3;
            int var6 = var1.length;
            if(var5 <= var6 && var2 + var3 >= 0) {
               if(var3 == 0) {
                  return;
               }

               synchronized(this) {
                  int var7 = this.count + var3;
                  int var8 = var3;
                  int var9 = this.count;
                  int var10 = this.filledBufferSum;
                  int var11 = var9 - var10;

                  while(var8 > 0) {
                     int var12 = this.currentBuffer.length - var11;
                     int var13 = Math.min(var8, var12);
                     int var14 = var2 + var3 - var8;
                     byte[] var15 = this.currentBuffer;
                     System.arraycopy(var1, var14, var15, var11, var13);
                     var8 -= var13;
                     if(var8 > 0) {
                        this.needNewBuffer(var7);
                        var11 = 0;
                     }
                  }

                  this.count = var7;
                  return;
               }
            }
         }
      }

      throw new IndexOutOfBoundsException();
   }

   public void writeTo(OutputStream param1) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
