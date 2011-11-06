// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResponseAuth.java

package com.novell.sasl.client;

import java.util.Iterator;
import org.apache.harmony.javax.security.sasl.SaslException;

// Referenced classes of package com.novell.sasl.client:
//            DirectiveList, ParsedDirective

class ResponseAuth
{

    ResponseAuth(byte abyte0[])
        throws SaslException
    {
        DirectiveList directivelist;
        m_responseValue = null;
        directivelist = new DirectiveList(abyte0);
        directivelist.parseDirectives();
        checkSemantics(directivelist);
_L2:
        return;
        SaslException saslexception;
        saslexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    void checkSemantics(DirectiveList directivelist)
        throws SaslException
    {
        Iterator iterator = directivelist.getIterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ParsedDirective parseddirective = (ParsedDirective)iterator.next();
            if(parseddirective.getName().equals("rspauth"))
                m_responseValue = parseddirective.getValue();
        } while(true);
        if(m_responseValue == null)
            throw new SaslException("Missing response-auth directive.");
        else
            return;
    }

    public String getResponseValue()
    {
        return m_responseValue;
    }

    private String m_responseValue;
}
