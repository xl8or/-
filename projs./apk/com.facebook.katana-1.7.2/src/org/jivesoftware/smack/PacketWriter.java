// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PacketWriter.java

package org.jivesoftware.smack;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jivesoftware.smack.packet.Packet;

// Referenced classes of package org.jivesoftware.smack:
//            XMPPConnection, PacketReader, SmackConfiguration

class PacketWriter
{
    private class KeepAliveTask
        implements Runnable
    {

        public void run()
        {
            Writer writer1;
            Exception exception;
            long l;
            int i;
            try
            {
                Thread.sleep(15000L);
            }
            catch(InterruptedException interruptedexception) { }
            if(done || keepAliveThread != thread)
                break; /* Loop/switch isn't completed */
            writer1 = writer;
            writer1;
            JVM INSTR monitorenter ;
            l = System.currentTimeMillis() - lastActive;
            i = delay;
            if(l < (long)i)
                break MISSING_BLOCK_LABEL_90;
            try
            {
                writer.write(" ");
                writer.flush();
            }
            catch(Exception exception1) { }
            writer1;
            JVM INSTR monitorexit ;
            try
            {
                Thread.sleep(delay);
            }
            catch(InterruptedException interruptedexception1) { }
            if(true) goto _L2; else goto _L1
_L2:
            break MISSING_BLOCK_LABEL_6;
            exception;
            writer1;
            JVM INSTR monitorexit ;
            throw exception;
_L1:
        }

        protected void setThread(Thread thread1)
        {
            thread = thread1;
        }

        private int delay;
        final PacketWriter this$0;
        private Thread thread;

        public KeepAliveTask(int i)
        {
            this$0 = PacketWriter.this;
            super();
            delay = i;
        }
    }


    protected PacketWriter(XMPPConnection xmppconnection)
    {
        lastActive = System.currentTimeMillis();
        connection = xmppconnection;
        init();
    }

    private Packet nextPacket()
    {
        Packet packet = null;
_L2:
        if(done)
            break; /* Loop/switch isn't completed */
        packet = (Packet)queue.poll();
        if(packet != null)
            break; /* Loop/switch isn't completed */
        BlockingQueue blockingqueue = queue;
        blockingqueue;
        JVM INSTR monitorenter ;
        queue.wait();
        continue; /* Loop/switch isn't completed */
        InterruptedException interruptedexception;
        interruptedexception;
        if(true) goto _L2; else goto _L1
_L1:
        return packet;
    }

    private void writePackets(Thread thread)
    {
        openStream();
_L4:
        if(done || writerThread != thread) goto _L2; else goto _L1
_L1:
        Packet packet1 = nextPacket();
        if(packet1 == null) goto _L4; else goto _L3
_L3:
        Writer writer2 = writer;
        writer2;
        JVM INSTR monitorenter ;
        IOException ioexception;
        writer.write(packet1.toXML());
        writer.flush();
        lastActive = System.currentTimeMillis();
          goto _L4
_L8:
        return;
_L2:
        Writer writer1 = writer;
        writer1;
        JVM INSTR monitorenter ;
        Packet packet;
        for(; !queue.isEmpty(); writer.write(packet.toXML()))
            packet = (Packet)queue.remove();

          goto _L5
        Exception exception6;
        exception6;
        throw exception6;
        Exception exception;
        exception;
        exception.printStackTrace();
_L6:
        queue.clear();
        writer.write("</stream:stream>");
        writer.flush();
        try
        {
            writer.close();
        }
        catch(Exception exception5) { }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            if(!done)
            {
                done = true;
                connection.packetReader.notifyConnectionError(ioexception);
            }
        }
        continue; /* Loop/switch isn't completed */
_L5:
        writer.flush();
        writer1;
        JVM INSTR monitorexit ;
          goto _L6
        Exception exception3;
        exception3;
        writer.close();
        continue; /* Loop/switch isn't completed */
        Exception exception4;
        exception4;
        if(true) goto _L8; else goto _L7
_L7:
        Exception exception1;
        exception1;
        try
        {
            writer.close();
        }
        catch(Exception exception2) { }
        throw exception1;
    }

    void cleanup()
    {
        connection.interceptors.clear();
        connection.sendListeners.clear();
    }

    protected void init()
    {
        writer = connection.writer;
        done = false;
        writerThread = new Thread() {

            public void run()
            {
                writePackets(this);
            }

            final PacketWriter this$0;

            
            {
                this$0 = PacketWriter.this;
                super();
            }
        }
;
        writerThread.setName((new StringBuilder()).append("Smack Packet Writer (").append(connection.connectionCounterValue).append(")").toString());
        writerThread.setDaemon(true);
    }

    void openStream()
        throws IOException
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<stream:stream");
        stringbuilder.append(" to=\"").append(connection.getServiceName()).append("\"");
        stringbuilder.append(" xmlns=\"jabber:client\"");
        stringbuilder.append(" xmlns:stream=\"http://etherx.jabber.org/streams\"");
        stringbuilder.append(" version=\"1.0\">");
        writer.write(stringbuilder.toString());
        writer.flush();
    }

    public void sendPacket(Packet packet)
    {
        if(done)
            break MISSING_BLOCK_LABEL_49;
        connection.firePacketInterceptors(packet);
        queue.put(packet);
        synchronized(queue)
        {
            queue.notifyAll();
        }
        connection.firePacketSendingListeners(packet);
_L2:
        return;
        InterruptedException interruptedexception;
        interruptedexception;
        interruptedexception.printStackTrace();
        if(true) goto _L2; else goto _L1
_L1:
        exception;
        blockingqueue;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void setWriter(Writer writer1)
    {
        writer = writer1;
    }

    public void shutdown()
    {
        done = true;
        BlockingQueue blockingqueue = queue;
        blockingqueue;
        JVM INSTR monitorenter ;
        queue.notifyAll();
        return;
    }

    void startKeepAliveProcess()
    {
        int i = SmackConfiguration.getKeepAliveInterval();
        if(i > 0)
        {
            KeepAliveTask keepalivetask = new KeepAliveTask(i);
            keepAliveThread = new Thread(keepalivetask);
            keepalivetask.setThread(keepAliveThread);
            keepAliveThread.setDaemon(true);
            keepAliveThread.setName((new StringBuilder()).append("Smack Keep Alive (").append(connection.connectionCounterValue).append(")").toString());
            keepAliveThread.start();
        }
    }

    public void startup()
    {
        writerThread.start();
    }

    private XMPPConnection connection;
    private boolean done;
    private Thread keepAliveThread;
    private long lastActive;
    private final BlockingQueue queue = new ArrayBlockingQueue(500, true);
    private Writer writer;
    private Thread writerThread;





}
