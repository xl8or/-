// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AndroidDebugger.java

package de.measite.smack;

import android.util.Log;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.*;

public class AndroidDebugger
    implements SmackDebugger
{

    public AndroidDebugger(Connection connection1, Writer writer1, Reader reader1)
    {
        dateFormatter = new SimpleDateFormat("hh:mm:ss aaa");
        connection = null;
        listener = null;
        connListener = null;
        connection = connection1;
        writer = writer1;
        reader = reader1;
        createDebug();
    }

    private void createDebug()
    {
        ObservableReader observablereader = new ObservableReader(reader);
        readerListener = new ReaderListener() {

            public void read(String s)
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" RCV  (").append(connection.hashCode()).append("): ").append(s).toString());
            }

            final AndroidDebugger this$0;

            
            {
                this$0 = AndroidDebugger.this;
                super();
            }
        }
;
        observablereader.addReaderListener(readerListener);
        ObservableWriter observablewriter = new ObservableWriter(writer);
        writerListener = new WriterListener() {

            public void write(String s)
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" SENT (").append(connection.hashCode()).append("): ").append(s).toString());
            }

            final AndroidDebugger this$0;

            
            {
                this$0 = AndroidDebugger.this;
                super();
            }
        }
;
        observablewriter.addWriterListener(writerListener);
        reader = observablereader;
        writer = observablewriter;
        listener = new PacketListener() {

            public void processPacket(Packet packet)
            {
                if(AndroidDebugger.printInterpreted)
                    Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" RCV PKT (").append(connection.hashCode()).append("): ").append(packet.toXML()).toString());
            }

            final AndroidDebugger this$0;

            
            {
                this$0 = AndroidDebugger.this;
                super();
            }
        }
;
        connListener = new ConnectionListener() {

            public void connectionClosed()
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" Connection closed (").append(connection.hashCode()).append(")").toString());
            }

            public void connectionClosedOnError(Exception exception)
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" Connection closed due to an exception (").append(connection.hashCode()).append(")").toString());
                exception.printStackTrace();
            }

            public void reconnectingIn(int i)
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" Connection (").append(connection.hashCode()).append(") will reconnect in ").append(i).toString());
            }

            public void reconnectionFailed(Exception exception)
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" Reconnection failed due to an exception (").append(connection.hashCode()).append(")").toString());
                exception.printStackTrace();
            }

            public void reconnectionSuccessful()
            {
                Log.d("SMACK", (new StringBuilder()).append(dateFormatter.format(new Date())).append(" Connection reconnected (").append(connection.hashCode()).append(")").toString());
            }

            final AndroidDebugger this$0;

            
            {
                this$0 = AndroidDebugger.this;
                super();
            }
        }
;
    }

    public Reader getReader()
    {
        return reader;
    }

    public PacketListener getReaderListener()
    {
        return listener;
    }

    public Writer getWriter()
    {
        return writer;
    }

    public PacketListener getWriterListener()
    {
        return null;
    }

    public Reader newConnectionReader(Reader reader1)
    {
        ((ObservableReader)reader).removeReaderListener(readerListener);
        ObservableReader observablereader = new ObservableReader(reader1);
        observablereader.addReaderListener(readerListener);
        reader = observablereader;
        return reader;
    }

    public Writer newConnectionWriter(Writer writer1)
    {
        ((ObservableWriter)writer).removeWriterListener(writerListener);
        ObservableWriter observablewriter = new ObservableWriter(writer1);
        observablewriter.addWriterListener(writerListener);
        writer = observablewriter;
        return writer;
    }

    public void userHasLogged(String s)
    {
        boolean flag = "".equals(StringUtils.parseName(s));
        StringBuilder stringbuilder = (new StringBuilder()).append("User logged (").append(connection.hashCode()).append("): ");
        String s1;
        String s2;
        if(flag)
            s1 = "";
        else
            s1 = StringUtils.parseBareAddress(s);
        s2 = stringbuilder.append(s1).append("@").append(connection.getServiceName()).append(":").append(connection.getPort()).toString();
        Log.d("SMACK", (new StringBuilder()).append(s2).append("/").append(StringUtils.parseResource(s)).toString());
        connection.addConnectionListener(connListener);
    }

    public static boolean printInterpreted = false;
    private ConnectionListener connListener;
    private Connection connection;
    private SimpleDateFormat dateFormatter;
    private PacketListener listener;
    private Reader reader;
    private ReaderListener readerListener;
    private Writer writer;
    private WriterListener writerListener;



}
