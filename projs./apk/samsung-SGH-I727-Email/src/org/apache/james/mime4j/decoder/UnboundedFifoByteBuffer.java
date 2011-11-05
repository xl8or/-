package org.apache.james.mime4j.decoder;

import java.util.Iterator;
import java.util.NoSuchElementException;

class UnboundedFifoByteBuffer {

   protected byte[] buffer;
   protected int head;
   protected int tail;


   public UnboundedFifoByteBuffer() {
      this(32);
   }

   public UnboundedFifoByteBuffer(int var1) {
      if(var1 <= 0) {
         throw new IllegalArgumentException("The size must be greater than 0");
      } else {
         byte[] var2 = new byte[var1 + 1];
         this.buffer = var2;
         this.head = 0;
         this.tail = 0;
      }
   }

   private int decrement(int var1) {
      int var2 = var1 + -1;
      if(var2 < 0) {
         var2 = this.buffer.length - 1;
      }

      return var2;
   }

   private int increment(int var1) {
      int var2 = var1 + 1;
      int var3 = this.buffer.length;
      if(var2 >= var3) {
         var2 = 0;
      }

      return var2;
   }

   public boolean add(byte var1) {
      int var2 = this.size() + 1;
      int var3 = this.buffer.length;
      if(var2 >= var3) {
         byte[] var4 = new byte[(this.buffer.length - 1) * 2 + 1];
         int var5 = 0;
         int var6 = this.head;

         while(true) {
            int var7 = this.tail;
            if(var6 == var7) {
               this.buffer = var4;
               this.head = 0;
               this.tail = var5;
               break;
            }

            byte var8 = this.buffer[var6];
            var4[var5] = var8;
            this.buffer[var6] = 0;
            ++var5;
            ++var6;
            int var9 = this.buffer.length;
            if(var6 == var9) {
               var6 = 0;
            }
         }
      }

      byte[] var10 = this.buffer;
      int var11 = this.tail;
      var10[var11] = var1;
      int var12 = this.tail + 1;
      this.tail = var12;
      int var13 = this.tail;
      int var14 = this.buffer.length;
      if(var13 >= var14) {
         this.tail = 0;
      }

      return true;
   }

   public byte get() {
      if(this.isEmpty()) {
         throw new IllegalStateException("The buffer is already empty");
      } else {
         byte[] var1 = this.buffer;
         int var2 = this.head;
         return var1[var2];
      }
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Iterator iterator() {
      return new UnboundedFifoByteBuffer.1();
   }

   public byte remove() {
      if(this.isEmpty()) {
         throw new IllegalStateException("The buffer is already empty");
      } else {
         byte[] var1 = this.buffer;
         int var2 = this.head;
         byte var3 = var1[var2];
         int var4 = this.head + 1;
         this.head = var4;
         int var5 = this.head;
         int var6 = this.buffer.length;
         if(var5 >= var6) {
            this.head = 0;
         }

         return var3;
      }
   }

   public int size() {
      int var1 = this.tail;
      int var2 = this.head;
      int var7;
      if(var1 < var2) {
         int var3 = this.buffer.length;
         int var4 = this.head;
         int var5 = var3 - var4;
         int var6 = this.tail;
         var7 = var5 + var6;
      } else {
         int var8 = this.tail;
         int var9 = this.head;
         var7 = var8 - var9;
      }

      return var7;
   }

   class 1 implements Iterator {

      private int index;
      private int lastReturnedIndex;


      1() {
         int var2 = UnboundedFifoByteBuffer.this.head;
         this.index = var2;
         this.lastReturnedIndex = -1;
      }

      public boolean hasNext() {
         int var1 = this.index;
         int var2 = UnboundedFifoByteBuffer.this.tail;
         boolean var3;
         if(var1 != var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public Object next() {
         if(!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            int var1 = this.index;
            this.lastReturnedIndex = var1;
            UnboundedFifoByteBuffer var2 = UnboundedFifoByteBuffer.this;
            int var3 = this.index;
            int var4 = var2.increment(var3);
            this.index = var4;
            byte[] var5 = UnboundedFifoByteBuffer.this.buffer;
            int var6 = this.lastReturnedIndex;
            byte var7 = var5[var6];
            return new Byte(var7);
         }
      }

      public void remove() {
         if(this.lastReturnedIndex == -1) {
            throw new IllegalStateException();
         } else {
            int var1 = this.lastReturnedIndex;
            int var2 = UnboundedFifoByteBuffer.this.head;
            if(var1 == var2) {
               byte var3 = UnboundedFifoByteBuffer.this.remove();
               this.lastReturnedIndex = -1;
            } else {
               int var4 = this.lastReturnedIndex + 1;

               while(true) {
                  int var5 = UnboundedFifoByteBuffer.this.tail;
                  if(var4 == var5) {
                     this.lastReturnedIndex = -1;
                     UnboundedFifoByteBuffer var13 = UnboundedFifoByteBuffer.this;
                     UnboundedFifoByteBuffer var14 = UnboundedFifoByteBuffer.this;
                     int var15 = UnboundedFifoByteBuffer.this.tail;
                     int var16 = var14.decrement(var15);
                     var13.tail = var16;
                     byte[] var17 = UnboundedFifoByteBuffer.this.buffer;
                     int var18 = UnboundedFifoByteBuffer.this.tail;
                     var17[var18] = 0;
                     UnboundedFifoByteBuffer var19 = UnboundedFifoByteBuffer.this;
                     int var20 = this.index;
                     int var21 = var19.decrement(var20);
                     this.index = var21;
                     return;
                  }

                  int var6 = UnboundedFifoByteBuffer.this.buffer.length;
                  if(var4 >= var6) {
                     byte[] var7 = UnboundedFifoByteBuffer.this.buffer;
                     int var8 = var4 - 1;
                     byte var9 = UnboundedFifoByteBuffer.this.buffer[0];
                     var7[var8] = var9;
                     var4 = 0;
                  } else {
                     byte[] var10 = UnboundedFifoByteBuffer.this.buffer;
                     int var11 = var4 - 1;
                     byte var12 = UnboundedFifoByteBuffer.this.buffer[var4];
                     var10[var11] = var12;
                     ++var4;
                  }
               }
            }
         }
      }
   }
}
