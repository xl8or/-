package org.jivesoftware.smack;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

public class PacketCollector {

   private static final int MAX_PACKETS = 65536;
   private boolean cancelled = 0;
   private Connection conection;
   private PacketFilter packetFilter;
   private LinkedBlockingQueue<Packet> resultQueue;


   protected PacketCollector(Connection var1, PacketFilter var2) {
      this.conection = var1;
      this.packetFilter = var2;
      LinkedBlockingQueue var3 = new LinkedBlockingQueue(65536);
      this.resultQueue = var3;
   }

   public void cancel() {
      if(!this.cancelled) {
         this.conection.removePacketCollector(this);
         this.cancelled = (boolean)1;
      }
   }

   public PacketFilter getPacketFilter() {
      return this.packetFilter;
   }

   public Packet nextResult() {
      while(true) {
         try {
            Packet var3 = (Packet)this.resultQueue.take();
            return var3;
         } catch (InterruptedException var2) {
            ;
         }
      }
   }

   public Packet nextResult(long var1) {
      long var3 = System.currentTimeMillis() + var1;

      Packet var7;
      while(true) {
         Packet var10;
         try {
            LinkedBlockingQueue var5 = this.resultQueue;
            TimeUnit var6 = TimeUnit.MILLISECONDS;
            var10 = (Packet)var5.poll(var1, var6);
         } catch (InterruptedException var9) {
            if(System.currentTimeMillis() < var3) {
               continue;
            }

            var7 = null;
            break;
         }

         var7 = var10;
         break;
      }

      return var7;
   }

   public Packet pollResult() {
      return (Packet)this.resultQueue.poll();
   }

   protected void processPacket(Packet var1) {
      synchronized(this){}
      if(var1 != null) {
         try {
            if(this.packetFilter == null || this.packetFilter.accept(var1)) {
               while(!this.resultQueue.offer(var1)) {
                  Object var2 = this.resultQueue.poll();
               }
            }
         } finally {
            ;
         }
      }

   }
}
