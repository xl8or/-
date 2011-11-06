// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaslClientFactory.java

package de.measite.smack;

import com.novell.sasl.client.DigestMD5SaslClient;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.apache.qpid.management.common.sasl.PlainSaslClient;

public class SaslClientFactory
    implements org.apache.harmony.javax.security.sasl.SaslClientFactory
{

    public SaslClientFactory()
    {
    }

    public SaslClient createSaslClient(String as[], String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
        throws SaslException
    {
        int i;
        int j;
        i = as.length;
        j = 0;
_L1:
        Object obj;
        if(j >= i)
            break MISSING_BLOCK_LABEL_77;
        String s3 = as[j];
        if("PLAIN".equals(s3))
        {
            obj = new PlainSaslClient(s, callbackhandler);
        } else
        {
label0:
            {
                if(!"DIGEST-MD5".equals(s3))
                    break label0;
                obj = DigestMD5SaslClient.getClient(s, s1, s2, map, callbackhandler);
            }
        }
_L2:
        return ((SaslClient) (obj));
        j++;
          goto _L1
        obj = null;
          goto _L2
    }

    public String[] getMechanismNames(Map map)
    {
        String as[] = new String[2];
        as[0] = "PLAIN";
        as[1] = "DIGEST-MD5";
        return as;
    }
}
