// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sasl.java

package org.apache.harmony.javax.security.sasl;

import java.security.Provider;
import java.security.Security;
import java.util.*;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;

// Referenced classes of package org.apache.harmony.javax.security.sasl:
//            SaslException, SaslClientFactory, SaslServerFactory, SaslClient, 
//            SaslServer

public class Sasl
{

    private Sasl()
    {
    }

    public static SaslClient createSaslClient(String as[], String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        Collection collection;
        if(as == null)
            throw new NullPointerException("auth.33");
        collection = findFactories("SaslClientFactory");
        if(!collection.isEmpty()) goto _L2; else goto _L1
_L1:
        SaslClient saslclient = null;
_L7:
        return saslclient;
_L2:
        Iterator iterator;
        iterator = collection.iterator();
        break MISSING_BLOCK_LABEL_46;
        boolean flag = flag1;
_L8:
        if(!flag) goto _L4; else goto _L3
_L3:
        saslclient = saslclientfactory.createSaslClient(as, s, s1, s2, map, callbackhandler);
        if(saslclient == null) goto _L4; else goto _L5
_L5:
        continue; /* Loop/switch isn't completed */
_L4:
label0:
        {
            SaslClientFactory saslclientfactory;
            boolean flag1;
            if(iterator.hasNext())
            {
                saslclientfactory = (SaslClientFactory)iterator.next();
                String as1[] = saslclientfactory.getMechanismNames(null);
                if(as1 == null)
                    break label0;
                int i = 0;
                flag1 = false;
                do
                {
label1:
                    {
                        if(i >= as1.length)
                            break label1;
                        int j = 0;
                        do
                        {
label2:
                            {
                                if(j < as.length)
                                {
                                    if(!as1[i].equals(as[j]))
                                        break label2;
                                    flag1 = true;
                                }
                                i++;
                            }
                            if(true)
                                break;
                            j++;
                        } while(true);
                    }
                } while(true);
            }
            saslclient = null;
        }
        if(true) goto _L7; else goto _L6
_L6:
        flag = false;
          goto _L8
    }

    public static SaslServer createSaslServer(String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        Collection collection;
        if(s == null)
            throw new NullPointerException("auth.32");
        collection = findFactories("SaslServerFactory");
        if(!collection.isEmpty()) goto _L2; else goto _L1
_L1:
        SaslServer saslserver = null;
_L9:
        return saslserver;
_L2:
        Iterator iterator = collection.iterator();
_L8:
        if(!iterator.hasNext()) goto _L4; else goto _L3
_L3:
        SaslServerFactory saslserverfactory;
        String as[];
        int i;
        saslserverfactory = (SaslServerFactory)iterator.next();
        as = saslserverfactory.getMechanismNames(null);
        if(as == null)
            break MISSING_BLOCK_LABEL_149;
        i = 0;
_L10:
        if(i >= as.length)
            break MISSING_BLOCK_LABEL_149;
        if(!as[i].equals(s)) goto _L6; else goto _L5
_L5:
        boolean flag = true;
_L11:
        if(!flag) goto _L8; else goto _L7
_L7:
        saslserver = saslserverfactory.createSaslServer(s, s1, s2, map, callbackhandler);
        if(saslserver == null) goto _L8; else goto _L9
_L6:
        i++;
          goto _L10
_L4:
        saslserver = null;
          goto _L9
        flag = false;
          goto _L11
    }

    private static Collection findFactories(String s)
    {
        HashSet hashset = new HashSet();
        Provider aprovider[] = Security.getProviders();
        HashSet hashset1;
        if(aprovider == null || aprovider.length == 0)
        {
            hashset1 = hashset;
        } else
        {
            HashSet hashset2 = new HashSet();
label0:
            for(int i = 0; i < aprovider.length; i++)
            {
                String s1 = aprovider[i].getName();
                Enumeration enumeration = aprovider[i].keys();
                do
                {
                    if(!enumeration.hasMoreElements())
                        continue label0;
                    String s2 = (String)enumeration.nextElement();
                    if(s2.startsWith(s))
                    {
                        String s3 = aprovider[i].getProperty(s2);
                        try
                        {
                            if(hashset2.add(s1.concat(s3)))
                                hashset.add(newInstance(s3, aprovider[i]));
                        }
                        catch(SaslException saslexception)
                        {
                            saslexception.printStackTrace();
                        }
                    }
                } while(true);
            }

            hashset1 = hashset;
        }
        return hashset1;
    }

    public static Enumeration getSaslClientFactories()
    {
        return Collections.enumeration(findFactories("SaslClientFactory"));
    }

    public static Enumeration getSaslServerFactories()
    {
        return Collections.enumeration(findFactories("SaslServerFactory"));
    }

    private static Object newInstance(String s, Provider provider)
        throws SaslException
    {
        ClassLoader classloader = provider.getClass().getClassLoader();
        if(classloader == null)
            classloader = ClassLoader.getSystemClassLoader();
        Object obj;
        try
        {
            obj = Class.forName(s, true, classloader).newInstance();
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new SaslException((new StringBuilder()).append("auth.31").append(s).toString(), illegalaccessexception);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new SaslException((new StringBuilder()).append("auth.31").append(s).toString(), classnotfoundexception);
        }
        catch(InstantiationException instantiationexception)
        {
            throw new SaslException((new StringBuilder()).append("auth.31").append(s).toString(), instantiationexception);
        }
        return obj;
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
