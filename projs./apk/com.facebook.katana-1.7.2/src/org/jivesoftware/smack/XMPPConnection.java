// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMPPConnection.java

package org.jivesoftware.smack;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.*;
import java.security.*;
import java.util.Collection;
import java.util.Iterator;
import javax.net.SocketFactory;
import javax.net.ssl.*;
import org.apache.harmony.javax.security.auth.callback.*;
import org.jivesoftware.smack.debugger.SmackDebugger;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack:
//            Connection, ConnectionConfiguration, XMPPException, PacketWriter, 
//            PacketReader, ConnectionCreationListener, SmackConfiguration, Roster, 
//            SASLAuthentication, NonSASLAuthentication, ServerTrustManager, PacketInterceptor, 
//            PacketListener, RosterStorage

public class XMPPConnection extends Connection
{

    public XMPPConnection(String s)
    {
        super(new ConnectionConfiguration(s));
        connectionID = null;
        user = null;
        connected = false;
        authenticated = false;
        wasAuthenticated = false;
        anonymous = false;
        usingTLS = false;
        roster = null;
        config.setCompressionEnabled(false);
        config.setSASLAuthenticationEnabled(true);
        config.setDebuggerEnabled(DEBUG_ENABLED);
    }

    public XMPPConnection(String s, CallbackHandler callbackhandler)
    {
        super(new ConnectionConfiguration(s));
        connectionID = null;
        user = null;
        connected = false;
        authenticated = false;
        wasAuthenticated = false;
        anonymous = false;
        usingTLS = false;
        roster = null;
        config.setCompressionEnabled(false);
        config.setSASLAuthenticationEnabled(true);
        config.setDebuggerEnabled(DEBUG_ENABLED);
        config.setCallbackHandler(callbackhandler);
    }

    public XMPPConnection(ConnectionConfiguration connectionconfiguration)
    {
        super(connectionconfiguration);
        connectionID = null;
        user = null;
        connected = false;
        authenticated = false;
        wasAuthenticated = false;
        anonymous = false;
        usingTLS = false;
        roster = null;
    }

    public XMPPConnection(ConnectionConfiguration connectionconfiguration, CallbackHandler callbackhandler)
    {
        super(connectionconfiguration);
        connectionID = null;
        user = null;
        connected = false;
        authenticated = false;
        wasAuthenticated = false;
        anonymous = false;
        usingTLS = false;
        roster = null;
        connectionconfiguration.setCallbackHandler(callbackhandler);
    }

    private void connectUsingConfiguration(ConnectionConfiguration connectionconfiguration)
        throws XMPPException
    {
        String s;
        int i;
        s = connectionconfiguration.getHost();
        i = connectionconfiguration.getPort();
        if(connectionconfiguration.getSocketFactory() != null) goto _L2; else goto _L1
_L1:
        socket = new Socket(s, i);
_L4:
        initConnection();
        return;
_L2:
        try
        {
            socket = connectionconfiguration.getSocketFactory().createSocket(s, i);
        }
        catch(UnknownHostException unknownhostexception)
        {
            String s2 = (new StringBuilder()).append("Could not connect to ").append(s).append(":").append(i).append(".").toString();
            throw new XMPPException(s2, new XMPPError(org.jivesoftware.smack.packet.XMPPError.Condition.remote_server_timeout, s2), unknownhostexception);
        }
        catch(IOException ioexception)
        {
            String s1 = (new StringBuilder()).append("XMPPError connecting to ").append(s).append(":").append(i).append(".").toString();
            throw new XMPPException(s1, new XMPPError(org.jivesoftware.smack.packet.XMPPError.Condition.remote_server_error, s1), ioexception);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private boolean hasAvailableCompressionMethod(String s)
    {
        boolean flag;
        if(compressionMethods != null && compressionMethods.contains(s))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void initConnection()
        throws XMPPException
    {
        boolean flag;
        XMPPException xmppexception;
        Iterator iterator;
        if(packetReader == null || packetWriter == null)
            flag = true;
        else
            flag = false;
        if(!flag)
            usingCompression = false;
        initReaderAndWriter();
        if(!flag)
            break MISSING_BLOCK_LABEL_297;
        packetWriter = new PacketWriter(this);
        packetReader = new PacketReader(this);
        if(config.isDebuggerEnabled())
        {
            addPacketListener(debugger.getReaderListener(), null);
            if(debugger.getWriterListener() != null)
                addPacketSendingListener(debugger.getWriterListener(), null);
        }
_L1:
        packetWriter.startup();
        packetReader.startup();
        connected = true;
        packetWriter.startKeepAliveProcess();
        if(flag)
        {
            for(iterator = getConnectionCreationListeners().iterator(); iterator.hasNext(); ((ConnectionCreationListener)iterator.next()).connectionCreated(this));
            break MISSING_BLOCK_LABEL_328;
        }
        break MISSING_BLOCK_LABEL_314;
        xmppexception;
        if(packetWriter != null)
        {
            Exception exception;
            Throwable throwable;
            Throwable throwable1;
            Throwable throwable2;
            try
            {
                packetWriter.shutdown();
            }
            catch(Throwable throwable3) { }
            packetWriter = null;
        }
        if(packetReader != null)
        {
            try
            {
                packetReader.shutdown();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable2) { }
            packetReader = null;
        }
        if(reader != null)
        {
            try
            {
                reader.close();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable1) { }
            reader = null;
        }
        if(writer != null)
        {
            try
            {
                writer.close();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable) { }
            writer = null;
        }
        if(socket != null)
        {
            try
            {
                socket.close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception) { }
            socket = null;
        }
        setWasAuthenticated(authenticated);
        authenticated = false;
        connected = false;
        throw xmppexception;
        packetWriter.init();
        packetReader.init();
          goto _L1
        if(!wasAuthenticated)
            packetReader.notifyReconnection();
    }

    private void initReaderAndWriter()
        throws XMPPException
    {
        if(usingCompression) goto _L2; else goto _L1
_L1:
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
_L4:
        initDebugger();
        return;
_L2:
        Class class1 = Class.forName("com.jcraft.jzlib.ZOutputStream");
        Class aclass[] = new Class[2];
        aclass[0] = java/io/OutputStream;
        aclass[1] = Integer.TYPE;
        Constructor constructor = class1.getConstructor(aclass);
        Object aobj[] = new Object[2];
        aobj[0] = socket.getOutputStream();
        aobj[1] = Integer.valueOf(9);
        Object obj = constructor.newInstance(aobj);
        Class aclass1[] = new Class[1];
        aclass1[0] = Integer.TYPE;
        Method method = class1.getMethod("setFlushMode", aclass1);
        Object aobj1[] = new Object[1];
        aobj1[0] = Integer.valueOf(2);
        method.invoke(obj, aobj1);
        writer = new BufferedWriter(new OutputStreamWriter((OutputStream)obj, "UTF-8"));
        Class class2 = Class.forName("com.jcraft.jzlib.ZInputStream");
        Class aclass2[] = new Class[1];
        aclass2[0] = java/io/InputStream;
        Constructor constructor1 = class2.getConstructor(aclass2);
        Object aobj2[] = new Object[1];
        aobj2[0] = socket.getInputStream();
        Object obj1 = constructor1.newInstance(aobj2);
        Class aclass3[] = new Class[1];
        aclass3[0] = Integer.TYPE;
        Method method1 = class2.getMethod("setFlushMode", aclass3);
        Object aobj3[] = new Object[1];
        aobj3[0] = Integer.valueOf(2);
        method1.invoke(obj1, aobj3);
        reader = new BufferedReader(new InputStreamReader((InputStream)obj1, "UTF-8"));
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        try
        {
            exception.printStackTrace();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        }
        catch(IOException ioexception)
        {
            throw new XMPPException("XMPPError establishing connection with server.", new XMPPError(org.jivesoftware.smack.packet.XMPPError.Condition.remote_server_error, "XMPPError establishing connection with server."), ioexception);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void requestStreamCompression()
    {
        writer.write("<compress xmlns='http://jabber.org/protocol/compress'>");
        writer.write("<method>zlib</method></compress>");
        writer.flush();
_L1:
        return;
        IOException ioexception;
        ioexception;
        packetReader.notifyConnectionError(ioexception);
          goto _L1
    }

    private void setWasAuthenticated(boolean flag)
    {
        if(!wasAuthenticated)
            wasAuthenticated = flag;
    }

    private boolean useCompression()
    {
        boolean flag;
        if(authenticated)
            throw new IllegalStateException("Compression should be negotiated before authentication.");
        try
        {
            Class.forName("com.jcraft.jzlib.ZOutputStream");
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new IllegalStateException("Cannot use compression. Add smackx.jar to the classpath");
        }
        if(!hasAvailableCompressionMethod("zlib")) goto _L2; else goto _L1
_L1:
        requestStreamCompression();
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        try
        {
            wait(5 * SmackConfiguration.getPacketReplyTimeout());
        }
        catch(InterruptedException interruptedexception) { }
        this;
        JVM INSTR monitorexit ;
        flag = usingCompression;
_L4:
        return flag;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void addPacketWriterInterceptor(PacketInterceptor packetinterceptor, PacketFilter packetfilter)
    {
        addPacketInterceptor(packetinterceptor, packetfilter);
    }

    public void addPacketWriterListener(PacketListener packetlistener, PacketFilter packetfilter)
    {
        addPacketSendingListener(packetlistener, packetfilter);
    }

    public void connect()
        throws XMPPException
    {
        connectUsingConfiguration(config);
        if(connected && wasAuthenticated)
            try
            {
                if(isAnonymous())
                    loginAnonymously();
                else
                    login(config.getUsername(), config.getPassword(), config.getResource());
                packetReader.notifyReconnection();
            }
            catch(XMPPException xmppexception)
            {
                xmppexception.printStackTrace();
            }
    }

    public void disconnect(Presence presence)
    {
        if(packetReader != null && packetWriter != null)
        {
            shutdown(presence);
            if(roster != null)
            {
                roster.cleanup();
                roster = null;
            }
            wasAuthenticated = false;
            packetWriter.cleanup();
            packetWriter = null;
            packetReader.cleanup();
            packetReader = null;
        }
    }

    public String getConnectionID()
    {
        String s;
        if(!isConnected())
            s = null;
        else
            s = connectionID;
        return s;
    }

    public Roster getRoster()
    {
        if(roster != null) goto _L2; else goto _L1
_L1:
        Roster roster1 = null;
_L6:
        return roster1;
_L2:
        if(roster.rosterInitialized) goto _L4; else goto _L3
_L3:
        Roster roster2 = roster;
        roster2;
        JVM INSTR monitorenter ;
        long l2;
        long l3;
        long l = SmackConfiguration.getPacketReplyTimeout();
        long l1 = System.currentTimeMillis();
        l2 = l;
        l3 = l1;
_L7:
        if(!roster.rosterInitialized && l2 > 0L) goto _L5; else goto _L4
_L4:
        roster1 = roster;
          goto _L6
_L5:
        roster.wait(l2);
        long l4 = System.currentTimeMillis();
        l2 -= l4 - l3;
        l3 = l4;
          goto _L7
        Exception exception;
        exception;
        try
        {
            throw exception;
        }
        catch(InterruptedException interruptedexception) { }
          goto _L4
    }

    public String getUser()
    {
        String s;
        if(!isAuthenticated())
            s = null;
        else
            s = user;
        return s;
    }

    public boolean isAnonymous()
    {
        return anonymous;
    }

    public boolean isAuthenticated()
    {
        return authenticated;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public boolean isSecureConnection()
    {
        return isUsingTLS();
    }

    public boolean isUsingCompression()
    {
        return usingCompression;
    }

    public boolean isUsingTLS()
    {
        return usingTLS;
    }

    /**
     * @deprecated Method login is deprecated
     */

    public void login(String s, String s1, String s2)
        throws XMPPException
    {
        this;
        JVM INSTR monitorenter ;
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        break MISSING_BLOCK_LABEL_27;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        String s3;
        if(authenticated)
            throw new IllegalStateException("Already logged in to server.");
        s3 = s.toLowerCase().trim();
        if(!config.isSASLAuthenticationEnabled() || !saslAuthentication.hasNonAnonymousAuthentication()) goto _L2; else goto _L1
_L1:
        if(s1 == null) goto _L4; else goto _L3
_L3:
        String s4 = saslAuthentication.authenticate(s3, s1, s2);
_L7:
        if(s4 == null) goto _L6; else goto _L5
_L5:
        user = s4;
        config.setServiceName(StringUtils.parseServer(s4));
_L8:
        if(config.isCompressionEnabled())
            useCompression();
        if(roster == null)
        {
            if(rosterStorage != null)
                break MISSING_BLOCK_LABEL_365;
            roster = new Roster(this);
        }
_L9:
        if(config.isRosterLoadedAtLogin())
            roster.reload();
        if(config.isSendPresence())
            packetWriter.sendPacket(new Presence(org.jivesoftware.smack.packet.Presence.Type.available));
        authenticated = true;
        anonymous = false;
        config.setLoginInfo(s3, s1, s2);
        if(config.isDebuggerEnabled() && debugger != null)
            debugger.userHasLogged(user);
        this;
        JVM INSTR monitorexit ;
        return;
_L4:
        s4 = saslAuthentication.authenticate(s3, s2, config.getCallbackHandler());
          goto _L7
_L2:
        s4 = (new NonSASLAuthentication(this)).authenticate(s3, s1, s2);
          goto _L7
_L6:
        user = (new StringBuilder()).append(s3).append("@").append(getServiceName()).toString();
        if(s2 != null)
            user = (new StringBuilder()).append(user).append("/").append(s2).toString();
          goto _L8
        roster = new Roster(this, rosterStorage);
          goto _L9
    }

    /**
     * @deprecated Method loginAnonymously is deprecated
     */

    public void loginAnonymously()
        throws XMPPException
    {
        this;
        JVM INSTR monitorenter ;
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        break MISSING_BLOCK_LABEL_25;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(authenticated)
            throw new IllegalStateException("Already logged in to server.");
        if(!config.isSASLAuthenticationEnabled() || !saslAuthentication.hasAnonymousAuthentication()) goto _L2; else goto _L1
_L1:
        String s1 = saslAuthentication.authenticateAnonymously();
_L3:
        user = s1;
        config.setServiceName(StringUtils.parseServer(s1));
        if(config.isCompressionEnabled())
            useCompression();
        roster = null;
        packetWriter.sendPacket(new Presence(org.jivesoftware.smack.packet.Presence.Type.available));
        authenticated = true;
        anonymous = true;
        if(config.isDebuggerEnabled() && debugger != null)
            debugger.userHasLogged(user);
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        String s = (new NonSASLAuthentication(this)).authenticateAnonymously();
        s1 = s;
          goto _L3
    }

    void proceedTLSReceived()
        throws Exception
    {
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        if(config.getCallbackHandler() != null) goto _L2; else goto _L1
_L1:
        javax.net.ssl.KeyManager akeymanager[] = null;
_L8:
        TrustManager atrustmanager[] = new TrustManager[1];
        atrustmanager[0] = new ServerTrustManager(getServiceName(), config);
        sslcontext.init(akeymanager, atrustmanager, new SecureRandom());
        Socket socket1 = socket;
        socket = sslcontext.getSocketFactory().createSocket(socket1, socket1.getInetAddress().getHostName(), socket1.getPort(), true);
        socket.setSoTimeout(0);
        socket.setKeepAlive(true);
        initReaderAndWriter();
        ((SSLSocket)socket).startHandshake();
        usingTLS = true;
        packetWriter.setWriter(writer);
        packetWriter.openStream();
        return;
_L2:
        if(!config.getKeystoreType().equals("NONE")) goto _L4; else goto _L3
_L3:
        PasswordCallback passwordcallback1;
        KeyStore keystore1;
        passwordcallback1 = null;
        keystore1 = null;
_L5:
        KeyManagerFactory keymanagerfactory;
        keymanagerfactory = KeyManagerFactory.getInstance("SunX509");
        if(passwordcallback1 != null)
            break MISSING_BLOCK_LABEL_542;
        keymanagerfactory.init(keystore1, null);
_L6:
        javax.net.ssl.KeyManager akeymanager1[] = keymanagerfactory.getKeyManagers();
        akeymanager = akeymanager1;
        continue; /* Loop/switch isn't completed */
_L4:
        if(!config.getKeystoreType().equals("PKCS11"))
            break MISSING_BLOCK_LABEL_404;
        KeyStore keystore3;
        PasswordCallback passwordcallback2;
        Class class1 = Class.forName("sun.security.pkcs11.SunPKCS11");
        Class aclass[] = new Class[1];
        aclass[0] = java/io/InputStream;
        Constructor constructor = class1.getConstructor(aclass);
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream((new StringBuilder()).append("name = SmartCard\nlibrary = ").append(config.getPKCS11Library()).toString().getBytes());
        Object aobj[] = new Object[1];
        aobj[0] = bytearrayinputstream;
        Provider provider = (Provider)constructor.newInstance(aobj);
        Security.addProvider(provider);
        keystore3 = KeyStore.getInstance("PKCS11", provider);
        passwordcallback2 = new PasswordCallback("PKCS11 Password: ", false);
        CallbackHandler callbackhandler1 = config.getCallbackHandler();
        Callback acallback1[] = new Callback[1];
        acallback1[0] = passwordcallback2;
        callbackhandler1.handle(acallback1);
        keystore3.load(null, passwordcallback2.getPassword());
        keystore1 = keystore3;
        passwordcallback1 = passwordcallback2;
          goto _L5
        Exception exception1;
        exception1;
        passwordcallback1 = null;
        keystore1 = null;
          goto _L5
label0:
        {
            if(!config.getKeystoreType().equals("Apple"))
                break label0;
            KeyStore keystore2 = KeyStore.getInstance("KeychainStore", "Apple");
            keystore2.load(null, null);
            keystore1 = keystore2;
            passwordcallback1 = null;
        }
          goto _L5
        KeyStore keystore = KeyStore.getInstance(config.getKeystoreType());
        PasswordCallback passwordcallback;
        passwordcallback = new PasswordCallback("Keystore Password: ", false);
        CallbackHandler callbackhandler = config.getCallbackHandler();
        Callback acallback[] = new Callback[1];
        acallback[0] = passwordcallback;
        callbackhandler.handle(acallback);
        keystore.load(new FileInputStream(config.getKeystorePath()), passwordcallback.getPassword());
        keystore1 = keystore;
        passwordcallback1 = passwordcallback;
          goto _L5
        Exception exception;
        exception;
        passwordcallback1 = null;
        keystore1 = null;
          goto _L5
        keymanagerfactory.init(keystore1, passwordcallback1.getPassword());
        passwordcallback1.clearPassword();
          goto _L6
        NullPointerException nullpointerexception;
        nullpointerexception;
        akeymanager = null;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void removePacketWriterInterceptor(PacketInterceptor packetinterceptor)
    {
        removePacketInterceptor(packetinterceptor);
    }

    public void removePacketWriterListener(PacketListener packetlistener)
    {
        removePacketSendingListener(packetlistener);
    }

    public void sendPacket(Packet packet)
    {
        if(!isConnected())
            throw new IllegalStateException("Not connected to server.");
        if(packet == null)
        {
            throw new NullPointerException("Packet is null.");
        } else
        {
            packetWriter.sendPacket(packet);
            return;
        }
    }

    void setAvailableCompressionMethods(Collection collection)
    {
        compressionMethods = collection;
    }

    public void setRosterStorage(RosterStorage rosterstorage)
        throws IllegalStateException
    {
        if(roster != null)
        {
            throw new IllegalStateException("Roster is already initialized");
        } else
        {
            rosterStorage = rosterstorage;
            return;
        }
    }

    protected void shutdown(Presence presence)
    {
        if(packetWriter != null)
            packetWriter.sendPacket(presence);
        setWasAuthenticated(authenticated);
        authenticated = false;
        connected = false;
        if(packetReader != null)
            packetReader.shutdown();
        if(packetWriter != null)
            packetWriter.shutdown();
        try
        {
            Thread.sleep(150L);
        }
        catch(Exception exception) { }
        if(reader != null)
        {
            Exception exception1;
            Throwable throwable;
            try
            {
                reader.close();
            }
            catch(Throwable throwable1) { }
            reader = null;
        }
        if(writer != null)
        {
            try
            {
                writer.close();
            }
            // Misplaced declaration of an exception variable
            catch(Throwable throwable) { }
            writer = null;
        }
        try
        {
            socket.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        saslAuthentication.init();
    }

    void startStreamCompression()
        throws Exception
    {
        usingCompression = true;
        initReaderAndWriter();
        packetWriter.setWriter(writer);
        packetWriter.openStream();
        this;
        JVM INSTR monitorenter ;
        notify();
        return;
    }

    void startTLSReceived(boolean flag)
    {
        if(!flag || config.getSecurityMode() != ConnectionConfiguration.SecurityMode.disabled) goto _L2; else goto _L1
_L1:
        packetReader.notifyConnectionError(new IllegalStateException("TLS required by server but not allowed by connection configuration"));
_L4:
        return;
_L2:
        if(config.getSecurityMode() != ConnectionConfiguration.SecurityMode.disabled)
            try
            {
                writer.write("<starttls xmlns=\"urn:ietf:params:xml:ns:xmpp-tls\"/>");
                writer.flush();
            }
            catch(IOException ioexception)
            {
                packetReader.notifyConnectionError(ioexception);
            }
        if(true) goto _L4; else goto _L3
_L3:
    }

    void streamCompressionDenied()
    {
        this;
        JVM INSTR monitorenter ;
        notify();
        return;
    }

    private boolean anonymous;
    private boolean authenticated;
    private Collection compressionMethods;
    private boolean connected;
    String connectionID;
    PacketReader packetReader;
    PacketWriter packetWriter;
    Roster roster;
    protected Socket socket;
    private String user;
    private boolean usingCompression;
    private boolean usingTLS;
    private boolean wasAuthenticated;
}
