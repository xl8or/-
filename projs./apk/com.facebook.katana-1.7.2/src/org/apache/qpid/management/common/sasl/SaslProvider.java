// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaslProvider.java

package org.apache.qpid.management.common.sasl;

import java.security.Provider;

public class SaslProvider extends Provider
{

    public SaslProvider()
    {
        super("SaslClientFactory", 1D, "SASL PLAIN CLIENT MECHANISM");
        put("SaslClientFactory.PLAIN", "ClientSaslFactory");
    }

    private static final long serialVersionUID = 0xe82066ceL;
}
