// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CRAMMD5HashedSaslClientFactory.java

package org.apache.qpid.management.common.sasl;

import de.measite.smack.Sasl;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.*;

public class CRAMMD5HashedSaslClientFactory
    implements SaslClientFactory
{

    public CRAMMD5HashedSaslClientFactory()
    {
    }

    public SaslClient createSaslClient(String as[], String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        int i = 0;
_L3:
        if(i >= as.length)
            break MISSING_BLOCK_LABEL_73;
        if(!as[i].equals("CRAM-MD5-HASHED")) goto _L2; else goto _L1
_L1:
        SaslClient saslclient;
        if(callbackhandler == null)
            throw new SaslException("CallbackHandler must not be null");
        String as1[] = new String[1];
        as1[0] = "CRAM-MD5";
        saslclient = Sasl.createSaslClient(as1, s, s1, s2, map, callbackhandler);
_L4:
        return saslclient;
_L2:
        i++;
          goto _L3
        saslclient = null;
          goto _L4
    }

    public String[] getMechanismNames(Map map)
    {
        String as[] = new String[1];
        as[0] = "CRAM-MD5-HASHED";
        return as;
    }

    public static final String MECHANISM = "CRAM-MD5-HASHED";
}
