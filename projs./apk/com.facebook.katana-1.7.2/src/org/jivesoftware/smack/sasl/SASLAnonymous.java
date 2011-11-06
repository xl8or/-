// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SASLAnonymous.java

package org.jivesoftware.smack.sasl;

import java.io.IOException;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.SASLAuthentication;

// Referenced classes of package org.jivesoftware.smack.sasl:
//            SASLMechanism

public class SASLAnonymous extends SASLMechanism
{

    public SASLAnonymous(SASLAuthentication saslauthentication)
    {
        super(saslauthentication);
    }

    protected void authenticate()
        throws IOException
    {
        getSASLAuthentication().send(new SASLMechanism.AuthMechanism(this, getName(), null));
    }

    public void authenticate(String s, String s1, String s2)
        throws IOException
    {
        authenticate();
    }

    public void authenticate(String s, String s1, CallbackHandler callbackhandler)
        throws IOException
    {
        authenticate();
    }

    public void challengeReceived(String s)
        throws IOException
    {
        getSASLAuthentication().send(new SASLMechanism.Response(this));
    }

    protected String getName()
    {
        return "ANONYMOUS";
    }
}
