// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SASLAuthentication.java

package org.jivesoftware.smack;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.Bind;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Session;
import org.jivesoftware.smack.sasl.SASLAnonymous;
import org.jivesoftware.smack.sasl.SASLCramMD5Mechanism;
import org.jivesoftware.smack.sasl.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.sasl.SASLExternalMechanism;
import org.jivesoftware.smack.sasl.SASLGSSAPIMechanism;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.SASLPlainMechanism;

// Referenced classes of package org.jivesoftware.smack:
//            UserAuthentication, XMPPException, Connection, SmackConfiguration, 
//            PacketCollector, NonSASLAuthentication

public class SASLAuthentication
    implements UserAuthentication
{

    SASLAuthentication(Connection connection1)
    {
        serverMechanisms = new ArrayList();
        currentMechanism = null;
        connection = connection1;
        init();
    }

    private String bindResourceAndEstablishSession(String s)
        throws XMPPException
    {
        this;
        JVM INSTR monitorenter ;
        long l = 30000L + System.currentTimeMillis();
_L1:
        long l1;
        if(resourceBinded)
            break MISSING_BLOCK_LABEL_49;
        l1 = System.currentTimeMillis();
        if(l1 >= l)
            break MISSING_BLOCK_LABEL_49;
        try
        {
            wait(Math.abs(System.currentTimeMillis() - l));
        }
        catch(InterruptedException interruptedexception) { }
          goto _L1
        this;
        JVM INSTR monitorexit ;
        if(!resourceBinded)
            throw new XMPPException("Resource binding not offered by server");
        break MISSING_BLOCK_LABEL_73;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        Bind bind = new Bind();
        bind.setResource(s);
        PacketCollector packetcollector = connection.createPacketCollector(new PacketIDFilter(bind.getPacketID()));
        connection.sendPacket(bind);
        Bind bind1 = (Bind)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(bind1 == null)
            throw new XMPPException("No response from the server.");
        if(bind1.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
            throw new XMPPException(bind1.getError());
        String s1 = bind1.getJid();
        if(sessionSupported)
        {
            Session session = new Session();
            PacketCollector packetcollector1 = connection.createPacketCollector(new PacketIDFilter(session.getPacketID()));
            connection.sendPacket(session);
            IQ iq = (IQ)packetcollector1.nextResult(SmackConfiguration.getPacketReplyTimeout());
            packetcollector1.cancel();
            if(iq == null)
                throw new XMPPException("No response from the server.");
            if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
                throw new XMPPException(iq.getError());
            else
                return s1;
        } else
        {
            throw new XMPPException("Session establishment not offered by server");
        }
    }

    public static List getRegisterSASLMechanisms()
    {
        ArrayList arraylist = new ArrayList();
        String s;
        for(Iterator iterator = mechanismsPreferences.iterator(); iterator.hasNext(); arraylist.add(implementedMechanisms.get(s)))
            s = (String)iterator.next();

        return arraylist;
    }

    public static void registerSASLMechanism(String s, Class class1)
    {
        implementedMechanisms.put(s, class1);
    }

    public static void supportSASLMechanism(String s)
    {
        mechanismsPreferences.add(0, s);
    }

    public static void supportSASLMechanism(String s, int i)
    {
        mechanismsPreferences.add(i, s);
    }

    public static void unregisterSASLMechanism(String s)
    {
        implementedMechanisms.remove(s);
        mechanismsPreferences.remove(s);
    }

    public static void unsupportSASLMechanism(String s)
    {
        mechanismsPreferences.remove(s);
    }

    public String authenticate(String s, String s1, String s2)
        throws XMPPException
    {
        String s3;
        s3 = null;
        Iterator iterator = mechanismsPreferences.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s6 = (String)iterator.next();
            if(!implementedMechanisms.containsKey(s6) || !serverMechanisms.contains(s6))
                continue;
            s3 = s6;
            break;
        } while(true);
        if(s3 == null) goto _L2; else goto _L1
_L1:
        Class class1 = (Class)implementedMechanisms.get(s3);
        Class aclass[] = new Class[1];
        aclass[0] = org/jivesoftware/smack/SASLAuthentication;
        Constructor constructor = class1.getConstructor(aclass);
        Object aobj[] = new Object[1];
        aobj[0] = this;
        currentMechanism = (SASLMechanism)constructor.newInstance(aobj);
        currentMechanism.authenticate(s, connection.getServiceName(), s1);
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        if(saslNegotiated)
            break MISSING_BLOCK_LABEL_175;
        flag = saslFailed;
        if(flag)
            break MISSING_BLOCK_LABEL_175;
        String s4;
        XMPPException xmppexception;
        Exception exception1;
        String s5;
        try
        {
            wait(30000L);
        }
        catch(InterruptedException interruptedexception) { }
        this;
        JVM INSTR monitorexit ;
        if(!saslFailed) goto _L4; else goto _L3
_L3:
        if(errorCondition != null)
            throw new XMPPException((new StringBuilder()).append("SASL authentication ").append(s3).append(" failed: ").append(errorCondition).toString());
          goto _L5
        exception1;
        this;
        JVM INSTR monitorexit ;
        try
        {
            throw exception1;
        }
        // Misplaced declaration of an exception variable
        catch(XMPPException xmppexception)
        {
            throw xmppexception;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        s4 = (new NonSASLAuthentication(connection)).authenticate(s, s1, s2);
_L7:
        return s4;
_L5:
        throw new XMPPException((new StringBuilder()).append("SASL authentication failed using mechanism ").append(s3).toString());
_L4:
        if(saslNegotiated)
        {
            s4 = bindResourceAndEstablishSession(s2);
            continue; /* Loop/switch isn't completed */
        }
        s5 = (new NonSASLAuthentication(connection)).authenticate(s, s1, s2);
        s4 = s5;
        continue; /* Loop/switch isn't completed */
_L2:
        s4 = (new NonSASLAuthentication(connection)).authenticate(s, s1, s2);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public String authenticate(String s, String s1, CallbackHandler callbackhandler)
        throws XMPPException
    {
        String s2;
        s2 = null;
        Iterator iterator = mechanismsPreferences.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s4 = (String)iterator.next();
            if(!implementedMechanisms.containsKey(s4) || !serverMechanisms.contains(s4))
                continue;
            s2 = s4;
            break;
        } while(true);
        if(s2 == null)
            break MISSING_BLOCK_LABEL_337;
        Class class1 = (Class)implementedMechanisms.get(s2);
        Class aclass[] = new Class[1];
        aclass[0] = org/jivesoftware/smack/SASLAuthentication;
        Constructor constructor = class1.getConstructor(aclass);
        Object aobj[] = new Object[1];
        aobj[0] = this;
        currentMechanism = (SASLMechanism)constructor.newInstance(aobj);
        currentMechanism.authenticate(s, connection.getHost(), callbackhandler);
        this;
        JVM INSTR monitorenter ;
        long l = System.currentTimeMillis();
_L1:
        long l1;
        if(saslNegotiated || saslFailed)
            break MISSING_BLOCK_LABEL_203;
        l1 = System.currentTimeMillis();
        if(l1 >= l)
            break MISSING_BLOCK_LABEL_203;
        try
        {
            wait(Math.abs(System.currentTimeMillis() - l));
        }
        catch(InterruptedException interruptedexception) { }
          goto _L1
        this;
        JVM INSTR monitorexit ;
        if(!saslFailed) goto _L3; else goto _L2
_L2:
        XMPPException xmppexception;
        if(errorCondition != null)
            throw new XMPPException((new StringBuilder()).append("SASL authentication ").append(s2).append(" failed: ").append(errorCondition).toString());
          goto _L4
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        try
        {
            throw exception1;
        }
        // Misplaced declaration of an exception variable
        catch(XMPPException xmppexception)
        {
            throw xmppexception;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
_L6:
        throw new XMPPException("SASL authentication failed");
_L4:
        throw new XMPPException((new StringBuilder()).append("SASL authentication failed using mechanism ").append(s2).toString());
_L3:
        if(!saslNegotiated) goto _L6; else goto _L5
_L5:
        String s3 = bindResourceAndEstablishSession(s1);
        return s3;
        throw new XMPPException("SASL Authentication failed. No known authentication mechanisims.");
    }

    public String authenticateAnonymously()
        throws XMPPException
    {
        currentMechanism = new SASLAnonymous(this);
        currentMechanism.authenticate(null, null, "");
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        if(saslNegotiated)
            break MISSING_BLOCK_LABEL_51;
        flag = saslFailed;
        if(flag)
            break MISSING_BLOCK_LABEL_51;
        IOException ioexception;
        String s;
        Exception exception;
        String s1;
        try
        {
            wait(5000L);
        }
        catch(InterruptedException interruptedexception) { }
        this;
        JVM INSTR monitorexit ;
        if(!saslFailed) goto _L2; else goto _L1
_L1:
        if(errorCondition != null)
            throw new XMPPException((new StringBuilder()).append("SASL authentication failed: ").append(errorCondition).toString());
          goto _L3
        ioexception;
        s = (new NonSASLAuthentication(connection)).authenticateAnonymously();
_L5:
        return s;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L3:
        throw new XMPPException("SASL authentication failed");
_L2:
        if(saslNegotiated)
        {
            s = bindResourceAndEstablishSession(null);
            continue; /* Loop/switch isn't completed */
        }
        s1 = (new NonSASLAuthentication(connection)).authenticateAnonymously();
        s = s1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    void authenticated()
    {
        this;
        JVM INSTR monitorenter ;
        saslNegotiated = true;
        notify();
        return;
    }

    void authenticationFailed()
    {
        authenticationFailed(null);
    }

    void authenticationFailed(String s)
    {
        this;
        JVM INSTR monitorenter ;
        saslFailed = true;
        errorCondition = s;
        notify();
        return;
    }

    void bindingRequired()
    {
        this;
        JVM INSTR monitorenter ;
        resourceBinded = true;
        notify();
        return;
    }

    void challengeReceived(String s)
        throws IOException
    {
        currentMechanism.challengeReceived(s);
    }

    public boolean hasAnonymousAuthentication()
    {
        return serverMechanisms.contains("ANONYMOUS");
    }

    public boolean hasNonAnonymousAuthentication()
    {
        boolean flag;
        if(!serverMechanisms.isEmpty() && (serverMechanisms.size() != 1 || !hasAnonymousAuthentication()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected void init()
    {
        saslNegotiated = false;
        saslFailed = false;
        resourceBinded = false;
        sessionSupported = false;
    }

    public boolean isAuthenticated()
    {
        return saslNegotiated;
    }

    public void send(Packet packet)
    {
        connection.sendPacket(packet);
    }

    void sessionsSupported()
    {
        sessionSupported = true;
    }

    void setAvailableSASLMethods(Collection collection)
    {
        serverMechanisms = collection;
    }

    private static Map implementedMechanisms = new HashMap();
    private static List mechanismsPreferences = new ArrayList();
    private Connection connection;
    private SASLMechanism currentMechanism;
    private String errorCondition;
    private boolean resourceBinded;
    private boolean saslFailed;
    private boolean saslNegotiated;
    private Collection serverMechanisms;
    private boolean sessionSupported;

    static 
    {
        registerSASLMechanism("EXTERNAL", org/jivesoftware/smack/sasl/SASLExternalMechanism);
        registerSASLMechanism("GSSAPI", org/jivesoftware/smack/sasl/SASLGSSAPIMechanism);
        registerSASLMechanism("DIGEST-MD5", org/jivesoftware/smack/sasl/SASLDigestMD5Mechanism);
        registerSASLMechanism("CRAM-MD5", org/jivesoftware/smack/sasl/SASLCramMD5Mechanism);
        registerSASLMechanism("PLAIN", org/jivesoftware/smack/sasl/SASLPlainMechanism);
        registerSASLMechanism("ANONYMOUS", org/jivesoftware/smack/sasl/SASLAnonymous);
        supportSASLMechanism("DIGEST-MD5", 0);
        supportSASLMechanism("PLAIN", 1);
        supportSASLMechanism("ANONYMOUS", 2);
    }
}
