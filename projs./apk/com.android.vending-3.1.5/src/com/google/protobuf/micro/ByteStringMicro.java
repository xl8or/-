package com.google.protobuf.micro;

import java.io.UnsupportedEncodingException;

public final class ByteStringMicro {

   public static final ByteStringMicro EMPTY;
   private final byte[] bytes;
   private volatile int hash = 0;


   static {
      byte[] var0 = new byte[0];
      EMPTY = new ByteStringMicro(var0);
   }

   private ByteStringMicro(byte[] var1) {
      this.bytes = var1;
   }

   public static ByteStringMicro copyFrom(String var0, String var1) throws UnsupportedEncodingException {
      byte[] var2 = var0.getBytes(var1);
      return new ByteStringMicro(var2);
   }

   public static ByteStringMicro copyFrom(byte[] var0) {
      int var1 = var0.length;
      return copyFrom(var0, 0, var1);
   }

   public static ByteStringMicro copyFrom(byte[] var0, int var1, int var2) {
      byte[] var3 = new byte[var2];
      System.arraycopy(var0, var1, var3, 0, var2);
      return new ByteStringMicro(var3);
   }

   public static ByteStringMicro copyFromUtf8(String var0) {
      try {
         byte[] var1 = var0.getBytes("UTF-8");
         ByteStringMicro var2 = new ByteStringMicro(var1);
         return var2;
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException("UTF-8 not supported?");
      }
   }

   public byte byteAt(int var1) {
      return this.bytes[var1];
   }

   public void copyTo(byte[] var1, int var2) {
      byte[] var3 = this.bytes;
      int var4 = this.bytes.length;
      System.arraycopy(var3, 0, var1, var2, var4);
   }

   public void copyTo(byte[] var1, int var2, int var3, int var4) {
      System.arraycopy(this.bytes, var2, var1, var3, var4);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(var1 != this) {
         if(!(var1 instanceof ByteStringMicro)) {
            var2 = false;
         } else {
            ByteStringMicro var3 = (ByteStringMicro)var1;
            int var4 = this.bytes.length;
            int var5 = var3.bytes.length;
            if(var4 != var5) {
               var2 = false;
            } else {
               byte[] var6 = this.bytes;
               byte[] var7 = var3.bytes;

               for(int var8 = 0; var8 < var4; ++var8) {
                  byte var9 = var6[var8];
                  byte var10 = var7[var8];
                  if(var9 != var10) {
                     var2 = false;
                     break;
                  }
               }
            }
         }
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this.hash;
      if(var1 == 0) {
         byte[] var2 = this.bytes;
         int var3 = this.bytes.length;
         var1 = var3;

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var1 * 31;
            byte var6 = var2[var4];
            int var10000 = var5 + var6;
         }

         if(var1 == 0) {
            ;
         }

         this.hash = var1;
      }

      return var1;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.bytes.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int size() {
      return this.bytes.length;
   }

   public byte[] toByteArray() {
      int var1 = this.bytes.length;
      byte[] var2 = new byte[var1];
      System.arraycopy(this.bytes, 0, var2, 0, var1);
      return var2;
   }

   public String toString(String var1) throws UnsupportedEncodingException {
      byte[] var2 = this.bytes;
      return new String(var2, var1);
   }

   public String toStringUtf8() {
      try {
         byte[] var1 = this.bytes;
         String var2 = new String(var1, "UTF-8");
         return var2;
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException("UTF-8 not supported?");
      }
   }
}
