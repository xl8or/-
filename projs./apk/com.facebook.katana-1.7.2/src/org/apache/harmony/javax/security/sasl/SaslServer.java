// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaslServer.java

package org.apache.harmony.javax.security.sasl;


// Referenced classes of package org.apache.harmony.javax.security.sasl:
//            SaslException

public interface SaslServer
{

    public abstract void dispose()
        throws SaslException;

    public abstract byte[] evaluateResponse(byte abyte0[])
        throws SaslException;

    public abstract String getAuthorizationID();

    public abstract String getMechanismName();

    public abstract Object getNegotiatedProperty(String s);

    public abstract boolean isComplete();

    public abstract byte[] unwrap(byte abyte0[], int i, int j)
        throws SaslException;

    public abstract byte[] wrap(byte abyte0[], int i, int j)
        throws SaslException;
}
