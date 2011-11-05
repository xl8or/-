package org.apache.james.mime4j.decoder;

import java.util.Iterator;
import org.apache.james.mime4j.decoder.UnboundedFifoByteBuffer;

public class ByteQueue {

   private UnboundedFifoByteBuffer buf;
   private int initialCapacity = -1;


   public ByteQueue() {
      UnboundedFifoByteBuffer var1 = new UnboundedFifoByteBuffer();
      this.buf = var1;
   }

   public ByteQueue(int var1) {
      UnboundedFifoByteBuffer var2 = new UnboundedFifoByteBuffer(var1);
      this.buf = var2;
      this.initialCapacity = var1;
   }

   public void clear() {
      if(this.initialCapacity != -1) {
         int var1 = this.initialCapacity;
         UnboundedFifoByteBuffer var2 = new UnboundedFifoByteBuffer(var1);
         this.buf = var2;
      } else {
         UnboundedFifoByteBuffer var3 = new UnboundedFifoByteBuffer();
         this.buf = var3;
      }
   }

   public int count() {
      return this.buf.size();
   }

   public byte dequeue() {
      return this.buf.remove();
   }

   public void enqueue(byte var1) {
      this.buf.add(var1);
   }

   public Iterator iterator() {
      return this.buf.iterator();
   }
}
