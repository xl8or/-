package org.jivesoftware.smack;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Packet;

class PacketWriter {

   private XMPPConnection connection;
   private boolean done;
   private Thread keepAliveThread;
   private long lastActive;
   private final BlockingQueue<Packet> queue;
   private Writer writer;
   private Thread writerThread;


   protected PacketWriter(XMPPConnection var1) {
      long var2 = System.currentTimeMillis();
      this.lastActive = var2;
      ArrayBlockingQueue var4 = new ArrayBlockingQueue(500, (boolean)1);
      this.queue = var4;
      this.connection = var1;
      this.init();
   }

   // $FF: synthetic method
   static boolean access$100(PacketWriter var0) {
      return var0.done;
   }

   // $FF: synthetic method
   static Thread access$200(PacketWriter var0) {
      return var0.keepAliveThread;
   }

   // $FF: synthetic method
   static Writer access$300(PacketWriter var0) {
      return var0.writer;
   }

   // $FF: synthetic method
   static long access$400(PacketWriter var0) {
      return var0.lastActive;
   }

   private Packet nextPacket() {
      // $FF: Couldn't be decompiled
   }

   private void writePackets(Thread param1) {
      // $FF: Couldn't be decompiled
   }

   void cleanup() {
      this.connection.interceptors.clear();
      this.connection.sendListeners.clear();
   }

   protected void init() {
      Writer var1 = this.connection.writer;
      this.writer = var1;
      this.done = (boolean)0;
      PacketWriter.1 var2 = new PacketWriter.1();
      this.writerThread = var2;
      Thread var3 = this.writerThread;
      StringBuilder var4 = (new StringBuilder()).append("Smack Packet Writer (");
      int var5 = this.connection.connectionCounterValue;
      String var6 = var4.append(var5).append(")").toString();
      var3.setName(var6);
      this.writerThread.setDaemon((boolean)1);
   }

   void openStream() throws IOException {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<stream:stream");
      StringBuilder var3 = var1.append(" to=\"");
      String var4 = this.connection.getServiceName();
      StringBuilder var5 = var3.append(var4).append("\"");
      StringBuilder var6 = var1.append(" xmlns=\"jabber:client\"");
      StringBuilder var7 = var1.append(" xmlns:stream=\"http://etherx.jabber.org/streams\"");
      StringBuilder var8 = var1.append(" version=\"1.0\">");
      Writer var9 = this.writer;
      String var10 = var1.toString();
      var9.write(var10);
      this.writer.flush();
   }

   public void sendPacket(Packet param1) {
      // $FF: Couldn't be decompiled
   }

   void setWriter(Writer var1) {
      this.writer = var1;
   }

   public void shutdown() {
      this.done = (boolean)1;
      BlockingQueue var1 = this.queue;
      synchronized(var1) {
         this.queue.notifyAll();
      }
   }

   void startKeepAliveProcess() {
      int var1 = SmackConfiguration.getKeepAliveInterval();
      if(var1 > 0) {
         PacketWriter.KeepAliveTask var2 = new PacketWriter.KeepAliveTask(var1);
         Thread var3 = new Thread(var2);
         this.keepAliveThread = var3;
         Thread var4 = this.keepAliveThread;
         var2.setThread(var4);
         this.keepAliveThread.setDaemon((boolean)1);
         Thread var5 = this.keepAliveThread;
         StringBuilder var6 = (new StringBuilder()).append("Smack Keep Alive (");
         int var7 = this.connection.connectionCounterValue;
         String var8 = var6.append(var7).append(")").toString();
         var5.setName(var8);
         this.keepAliveThread.start();
      }
   }

   public void startup() {
      this.writerThread.start();
   }

   class 1 extends Thread {

      1() {}

      public void run() {
         PacketWriter.this.writePackets(this);
      }
   }

   private class KeepAliveTask implements Runnable {

      private int delay;
      private Thread thread;


      public KeepAliveTask(int var2) {
         this.delay = var2;
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }

      protected void setThread(Thread var1) {
         this.thread = var1;
      }
   }
}
