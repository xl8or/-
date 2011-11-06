// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TCPClient.java

package org.xbill.DNS;

import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

// Referenced classes of package org.xbill.DNS:
//            Client

final class TCPClient extends Client
{

    public TCPClient(long l)
        throws IOException
    {
        Client(SocketChannel.open(), l);
    }

    private byte[] _recv(int i)
        throws IOException
    {
        SocketChannel socketchannel;
        byte abyte0[];
        ByteBuffer bytebuffer;
        int j;
        socketchannel = (SocketChannel)key.channel();
        abyte0 = new byte[i];
        bytebuffer = ByteBuffer.wrap(abyte0);
        key.interestOps(1);
        j = 0;
_L2:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        long l;
        if(!key.isReadable())
            break MISSING_BLOCK_LABEL_132;
        l = socketchannel.read(bytebuffer);
        if(l < 0L)
            throw new EOFException();
        break MISSING_BLOCK_LABEL_99;
        Exception exception;
        exception;
        if(key.isValid())
            key.interestOps(0);
        throw exception;
        j += (int)l;
        if(j >= i)
            continue; /* Loop/switch isn't completed */
        if(System.currentTimeMillis() > endTime)
            throw new SocketTimeoutException();
        continue; /* Loop/switch isn't completed */
        blockUntil(key, endTime);
        if(true) goto _L2; else goto _L1
_L1:
        if(key.isValid())
            key.interestOps(0);
        return abyte0;
    }

    static byte[] sendrecv(SocketAddress socketaddress, SocketAddress socketaddress1, byte abyte0[], long l)
        throws IOException
    {
        TCPClient tcpclient;
        tcpclient = new TCPClient(l);
        if(socketaddress == null)
            break MISSING_BLOCK_LABEL_20;
        tcpclient.bind(socketaddress);
        byte abyte1[];
        tcpclient.connect(socketaddress1);
        tcpclient.send(abyte0);
        abyte1 = tcpclient.recv();
        tcpclient.cleanup();
        return abyte1;
        Exception exception;
        exception;
        tcpclient.cleanup();
        throw exception;
    }

    static byte[] sendrecv(SocketAddress socketaddress, byte abyte0[], long l)
        throws IOException
    {
        return sendrecv(null, socketaddress, abyte0, l);
    }

    void bind(SocketAddress socketaddress)
        throws IOException
    {
        ((SocketChannel)key.channel()).socket().bind(socketaddress);
    }

    void connect(SocketAddress socketaddress)
        throws IOException
    {
        SocketChannel socketchannel = (SocketChannel)key.channel();
        if(!socketchannel.connect(socketaddress)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        key.interestOps(8);
        do
        {
            if(socketchannel.finishConnect())
                break MISSING_BLOCK_LABEL_85;
            if(!key.isConnectable())
                blockUntil(key, endTime);
        } while(true);
        Exception exception;
        exception;
        if(key.isValid())
            key.interestOps(0);
        throw exception;
        if(key.isValid())
            key.interestOps(0);
        if(true) goto _L1; else goto _L3
_L3:
    }

    byte[] recv()
        throws IOException
    {
        byte abyte0[] = _recv(2);
        byte abyte1[] = _recv(((0xff & abyte0[0]) << 8) + (0xff & abyte0[1]));
        verboseLog("TCP read", abyte1);
        return abyte1;
    }

    void send(byte abyte0[])
        throws IOException
    {
        SocketChannel socketchannel;
        ByteBuffer abytebuffer[];
        int i;
        socketchannel = (SocketChannel)key.channel();
        verboseLog("TCP write", abyte0);
        byte abyte1[] = new byte[2];
        abyte1[0] = (byte)(abyte0.length >>> 8);
        abyte1[1] = (byte)(0xff & abyte0.length);
        abytebuffer = new ByteBuffer[2];
        abytebuffer[0] = ByteBuffer.wrap(abyte1);
        abytebuffer[1] = ByteBuffer.wrap(abyte0);
        key.interestOps(4);
        i = 0;
_L2:
        long l;
        if(i >= 2 + abyte0.length)
            break; /* Loop/switch isn't completed */
        if(!key.isWritable())
            break MISSING_BLOCK_LABEL_178;
        l = socketchannel.write(abytebuffer);
        if(l < 0L)
            throw new EOFException();
        break MISSING_BLOCK_LABEL_142;
        Exception exception;
        exception;
        if(key.isValid())
            key.interestOps(0);
        throw exception;
        i += (int)l;
        if(i < 2 + abyte0.length && System.currentTimeMillis() > endTime)
            throw new SocketTimeoutException();
        continue; /* Loop/switch isn't completed */
        blockUntil(key, endTime);
        if(true) goto _L2; else goto _L1
_L1:
        if(key.isValid())
            key.interestOps(0);
        return;
    }
}
