// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientSaslFactory.java

package org.apache.qpid.management.common.sasl;

import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.*;

// Referenced classes of package org.apache.qpid.management.common.sasl:
//            PlainSaslClient

public class ClientSaslFactory
    implements SaslClientFactory
{

    public ClientSaslFactory()
    {
    }

    public SaslClient createSaslClient(String as[], String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        int i = 0;
_L3:
        if(i >= as.length)
            break MISSING_BLOCK_LABEL_43;
        if(!as[i].equals("PLAIN")) goto _L2; else goto _L1
_L1:
        PlainSaslClient plainsaslclient = new PlainSaslClient(s, callbackhandler);
_L4:
        return plainsaslclient;
_L2:
        i++;
          goto _L3
        plainsaslclient = null;
          goto _L4
    }

    public String[] getMechanismNames(Map map)
    {
        String as[] = new String[1];
        as[0] = "PLAIN";
        return as;
    }
}
