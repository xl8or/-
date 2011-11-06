// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UsernameHashedPasswordCallbackHandler.java

package org.apache.qpid.management.common.sasl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.harmony.javax.security.auth.callback.*;

public class UsernameHashedPasswordCallbackHandler
    implements CallbackHandler
{

    public UsernameHashedPasswordCallbackHandler(String s, String s1)
        throws Exception
    {
        user = s;
        pwchars = getHash(s1);
    }

    private void clearPassword()
    {
        if(pwchars != null)
        {
            for(int i = 0; i < pwchars.length; i++)
                pwchars[i] = '\0';

            pwchars = null;
        }
    }

    public static char[] getHash(String s)
        throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        byte abyte0[] = s.getBytes("utf-8");
        MessageDigest messagedigest = MessageDigest.getInstance("MD5");
        int i = abyte0.length;
        for(int j = 0; j < i; j++)
            messagedigest.update(abyte0[j]);

        byte abyte1[] = messagedigest.digest();
        char ac[] = new char[abyte1.length];
        int k = abyte1.length;
        int l = 0;
        int j1;
        for(int i1 = 0; l < k; i1 = j1)
        {
            byte byte0 = abyte1[l];
            j1 = i1 + 1;
            ac[i1] = (char)byte0;
            l++;
        }

        return ac;
    }

    protected void finalize()
    {
        clearPassword();
    }

    public void handle(Callback acallback[])
        throws IOException, UnsupportedCallbackException
    {
        int i = 0;
        while(i < acallback.length) 
        {
            if(acallback[i] instanceof NameCallback)
                ((NameCallback)acallback[i]).setName(user);
            else
            if(acallback[i] instanceof PasswordCallback)
                ((PasswordCallback)acallback[i]).setPassword(pwchars);
            else
                throw new UnsupportedCallbackException(acallback[i]);
            i++;
        }
    }

    private char pwchars[];
    private String user;
}
