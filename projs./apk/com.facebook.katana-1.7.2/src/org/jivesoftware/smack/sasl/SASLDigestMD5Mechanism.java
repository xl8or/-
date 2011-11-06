// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SASLDigestMD5Mechanism.java

package org.jivesoftware.smack.sasl;

import org.jivesoftware.smack.SASLAuthentication;

// Referenced classes of package org.jivesoftware.smack.sasl:
//            SASLMechanism

public class SASLDigestMD5Mechanism extends SASLMechanism
{

    public SASLDigestMD5Mechanism(SASLAuthentication saslauthentication)
    {
        super(saslauthentication);
    }

    protected String getName()
    {
        return "DIGEST-MD5";
    }
}
