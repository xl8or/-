// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sasl.java

package de.measite.smack;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.apache.harmony.javax.security.sasl.SaslServer;

// Referenced classes of package de.measite.smack:
//            SaslClientFactory

public class Sasl
{

    public Sasl()
    {
    }

    public static SaslClient createSaslClient(String as[], String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        SaslClientFactory saslclientfactory;
        String as1[];
        if(as == null)
            throw new NullPointerException("auth.33");
        saslclientfactory = (SaslClientFactory)getSaslClientFactories().nextElement();
        as1 = saslclientfactory.getMechanismNames(null);
        if(as1 == null) goto _L2; else goto _L1
_L1:
        int i;
        boolean flag1;
        i = 0;
        flag1 = false;
_L6:
        boolean flag;
        if(i < as1.length)
        {
            int j = 0;
            do
            {
label0:
                {
                    if(j < as.length)
                    {
                        if(!as1[i].equals(as[j]))
                            break label0;
                        flag1 = true;
                    }
                    i++;
                    continue; /* Loop/switch isn't completed */
                }
                j++;
            } while(true);
        }
        flag = flag1;
_L4:
        SaslClient saslclient;
        if(flag)
            saslclient = saslclientfactory.createSaslClient(as, s, s1, s2, map, callbackhandler);
        else
            saslclient = null;
        return saslclient;
_L2:
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static SaslServer createSaslServer(String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        return org.apache.harmony.javax.security.sasl.Sasl.createSaslServer(s, s1, s2, map, callbackhandler);
    }

    public static Enumeration getSaslClientFactories()
    {
        Hashtable hashtable = new Hashtable();
        hashtable.put(new SaslClientFactory(), new Object());
        return hashtable.keys();
    }

    public static Enumeration getSaslServerFactories()
    {
        return org.apache.harmony.javax.security.sasl.Sasl.getSaslServerFactories();
    }

    private static final String CLIENTFACTORYSRV = "SaslClientFactory";
    public static final String MAX_BUFFER = "javax.security.sasl.maxbuffer";
    public static final String POLICY_FORWARD_SECRECY = "javax.security.sasl.policy.forward";
    public static final String POLICY_NOACTIVE = "javax.security.sasl.policy.noactive";
    public static final String POLICY_NOANONYMOUS = "javax.security.sasl.policy.noanonymous";
    public static final String POLICY_NODICTIONARY = "javax.security.sasl.policy.nodictionary";
    public static final String POLICY_NOPLAINTEXT = "javax.security.sasl.policy.noplaintext";
    public static final String POLICY_PASS_CREDENTIALS = "javax.security.sasl.policy.credentials";
    public static final String QOP = "javax.security.sasl.qop";
    public static final String RAW_SEND_SIZE = "javax.security.sasl.rawsendsize";
    public static final String REUSE = "javax.security.sasl.reuse";
    private static final String SERVERFACTORYSRV = "SaslServerFactory";
    public static final String SERVER_AUTH = "javax.security.sasl.server.authentication";
    public static final String STRENGTH = "javax.security.sasl.strength";
}
