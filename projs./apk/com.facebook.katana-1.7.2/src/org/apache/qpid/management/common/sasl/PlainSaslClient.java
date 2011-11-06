// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlainSaslClient.java

package org.apache.qpid.management.common.sasl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.harmony.javax.security.auth.callback.*;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;

public class PlainSaslClient
    implements SaslClient
{

    public PlainSaslClient(String s, CallbackHandler callbackhandler)
        throws SaslException
    {
        completed = false;
        cbh = callbackhandler;
        Object aobj[] = getUserInfo();
        authorizationID = s;
        authenticationID = (String)aobj[0];
        password = (byte[])(byte[])aobj[1];
        if(authenticationID == null || password == null)
            throw new SaslException("PLAIN: authenticationID and password must be specified");
        else
            return;
    }

    private void clearPassword()
    {
        if(password != null)
        {
            for(int i = 0; i < password.length; i++)
                password[i] = 0;

            password = null;
        }
    }

    private Object[] getUserInfo()
        throws SaslException
    {
        String s;
        byte abyte0[];
        NameCallback namecallback = new NameCallback("PLAIN authentication id: ");
        PasswordCallback passwordcallback = new PasswordCallback("PLAIN password: ", false);
        CallbackHandler callbackhandler = cbh;
        Callback acallback[] = new Callback[2];
        acallback[0] = namecallback;
        acallback[1] = passwordcallback;
        callbackhandler.handle(acallback);
        s = namecallback.getName();
        char ac[] = passwordcallback.getPassword();
        if(ac == null)
            break MISSING_BLOCK_LABEL_114;
        byte abyte1[] = (new String(ac)).getBytes("UTF8");
        passwordcallback.clearPassword();
        abyte0 = abyte1;
_L1:
        Object aobj[];
        aobj = new Object[2];
        aobj[0] = s;
        aobj[1] = abyte0;
        return aobj;
        abyte0 = null;
          goto _L1
        IOException ioexception;
        ioexception;
        throw new SaslException("Cannot get password", ioexception);
        UnsupportedCallbackException unsupportedcallbackexception;
        unsupportedcallbackexception;
        throw new SaslException("Cannot get userid/password", unsupportedcallbackexception);
    }

    public void dispose()
        throws SaslException
    {
        clearPassword();
    }

    public byte[] evaluateChallenge(byte abyte0[])
        throws SaslException
    {
        if(completed)
            throw new IllegalStateException("PLAIN: authentication already completed");
        completed = true;
        if(authorizationID != null) goto _L2; else goto _L1
_L1:
        byte abyte2[] = null;
_L5:
        byte abyte3[];
        int i;
        abyte3 = authenticationID.getBytes("UTF8");
        i = 2 + (password.length + abyte3.length);
        if(abyte2 == null) goto _L4; else goto _L3
_L3:
        int j = abyte2.length;
_L6:
        byte abyte4[];
        int k;
        abyte4 = new byte[i + j];
        if(abyte2 == null)
            break MISSING_BLOCK_LABEL_204;
        System.arraycopy(abyte2, 0, abyte4, 0, abyte2.length);
        k = abyte2.length;
_L7:
        int l = k + 1;
        abyte4[k] = SEPARATOR;
        System.arraycopy(abyte3, 0, abyte4, l, abyte3.length);
        int i1 = l + abyte3.length;
        int j1 = i1 + 1;
        abyte4[i1] = SEPARATOR;
        System.arraycopy(password, 0, abyte4, j1, password.length);
        clearPassword();
        return abyte4;
_L2:
        byte abyte1[] = authorizationID.getBytes("UTF8");
        abyte2 = abyte1;
          goto _L5
_L4:
        j = 0;
          goto _L6
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        throw new SaslException("PLAIN: Cannot get UTF-8 encoding of ids", unsupportedencodingexception);
        k = 0;
          goto _L7
    }

    protected void finalize()
    {
        clearPassword();
    }

    public String getMechanismName()
    {
        return "PLAIN";
    }

    public Object getNegotiatedProperty(String s)
    {
        if(completed)
        {
            String s1;
            if(s.equals("javax.security.sasl.qop"))
                s1 = "auth";
            else
                s1 = null;
            return s1;
        } else
        {
            throw new IllegalStateException("PLAIN: authentication not completed");
        }
    }

    public boolean hasInitialResponse()
    {
        return true;
    }

    public boolean isComplete()
    {
        return completed;
    }

    public byte[] unwrap(byte abyte0[], int i, int j)
        throws SaslException
    {
        if(completed)
            throw new IllegalStateException("PLAIN: this mechanism supports neither integrity nor privacy");
        else
            throw new IllegalStateException("PLAIN: authentication not completed");
    }

    public byte[] wrap(byte abyte0[], int i, int j)
        throws SaslException
    {
        if(completed)
            throw new IllegalStateException("PLAIN: this mechanism supports neither integrity nor privacy");
        else
            throw new IllegalStateException("PLAIN: authentication not completed");
    }

    private static byte SEPARATOR = 0;
    private String authenticationID;
    private String authorizationID;
    private CallbackHandler cbh;
    private boolean completed;
    private byte password[];

}
