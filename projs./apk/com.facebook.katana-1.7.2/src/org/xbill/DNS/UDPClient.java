// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UDPClient.java

package org.xbill.DNS;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.security.SecureRandom;

// Referenced classes of package org.xbill.DNS:
//            Client

final class UDPClient extends Client
{

    public UDPClient(long l)
        throws IOException
    {
        super(DatagramChannel.open(), l);
        bound = false;
    }

    private void bind_random(InetSocketAddress inetsocketaddress)
        throws IOException
    {
        if(!prng_initializing) goto _L2; else goto _L1
_L1:
        DatagramChannel datagramchannel;
        int i;
        int j;
        InetSocketAddress inetsocketaddress1;
        InetSocketAddress inetsocketaddress2;
        try
        {
            Thread.sleep(2L);
        }
        catch(InterruptedException interruptedexception) { }
        if(!prng_initializing) goto _L2; else goto _L3
_L3:
        return;
_L2:
        datagramchannel = (DatagramChannel)key.channel();
        i = 0;
_L8:
        if(i >= 1024) goto _L3; else goto _L4
_L4:
        j = 1024 + prng.nextInt(64511);
        if(inetsocketaddress == null) goto _L6; else goto _L5
_L5:
        inetsocketaddress2 = new InetSocketAddress(inetsocketaddress.getAddress(), j);
_L7:
        datagramchannel.socket().bind(inetsocketaddress2);
        bound = true;
          goto _L3
_L6:
        inetsocketaddress1 = new InetSocketAddress(j);
        inetsocketaddress2 = inetsocketaddress1;
          goto _L7
        SocketException socketexception;
        socketexception;
        i++;
          goto _L8
    }

    static byte[] sendrecv(SocketAddress socketaddress, SocketAddress socketaddress1, byte abyte0[], int i, long l)
        throws IOException
    {
        UDPClient udpclient = new UDPClient(l);
        byte abyte1[];
        udpclient.bind(socketaddress);
        udpclient.connect(socketaddress1);
        udpclient.send(abyte0);
        abyte1 = udpclient.recv(i);
        udpclient.cleanup();
        return abyte1;
        Exception exception;
        exception;
        udpclient.cleanup();
        throw exception;
    }

    static byte[] sendrecv(SocketAddress socketaddress, byte abyte0[], int i, long l)
        throws IOException
    {
        return sendrecv(null, socketaddress, abyte0, i, l);
    }

    void bind(SocketAddress socketaddress)
        throws IOException
    {
        if(socketaddress != null && (!(socketaddress instanceof InetSocketAddress) || ((InetSocketAddress)socketaddress).getPort() != 0)) goto _L2; else goto _L1
_L1:
        bind_random((InetSocketAddress)socketaddress);
        if(!bound) goto _L2; else goto _L3
_L3:
        return;
_L2:
        if(socketaddress != null)
        {
            ((DatagramChannel)key.channel()).socket().bind(socketaddress);
            bound = true;
        }
        if(true) goto _L3; else goto _L4
_L4:
    }

    void connect(SocketAddress socketaddress)
        throws IOException
    {
        if(!bound)
            bind(null);
        ((DatagramChannel)key.channel()).connect(socketaddress);
    }

    byte[] recv(int i)
        throws IOException
    {
        DatagramChannel datagramchannel;
        byte abyte0[];
        datagramchannel = (DatagramChannel)key.channel();
        abyte0 = new byte[i];
        key.interestOps(1);
        for(; !key.isReadable(); blockUntil(key, endTime));
        break MISSING_BLOCK_LABEL_72;
        Exception exception;
        exception;
        if(key.isValid())
            key.interestOps(0);
        throw exception;
        if(key.isValid())
            key.interestOps(0);
        long l = datagramchannel.read(ByteBuffer.wrap(abyte0));
        if(l <= 0L)
        {
            throw new EOFException();
        } else
        {
            int j = (int)l;
            byte abyte1[] = new byte[j];
            System.arraycopy(abyte0, 0, abyte1, 0, j);
            verboseLog("UDP read", abyte1);
            return abyte1;
        }
    }

    void send(byte abyte0[])
        throws IOException
    {
        DatagramChannel datagramchannel = (DatagramChannel)key.channel();
        verboseLog("UDP write", abyte0);
        datagramchannel.write(ByteBuffer.wrap(abyte0));
    }

    private static final int EPHEMERAL_RANGE = 64511;
    private static final int EPHEMERAL_START = 1024;
    private static final int EPHEMERAL_STOP = 65535;
    private static SecureRandom prng = new SecureRandom();
    private static volatile boolean prng_initializing = true;
    private boolean bound;

    static 
    {
        (new Thread(new Runnable() {

            public void run()
            {
                UDPClient.prng.nextInt();
                UDPClient.prng_initializing = false;
            }

        }
)).start();
    }



/*
    static boolean access$102(boolean flag)
    {
        prng_initializing = flag;
        return flag;
    }

*/
}
