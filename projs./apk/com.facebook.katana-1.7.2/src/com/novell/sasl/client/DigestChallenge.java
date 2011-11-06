// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DigestChallenge.java

package com.novell.sasl.client;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.harmony.javax.security.sasl.SaslException;

// Referenced classes of package com.novell.sasl.client:
//            DirectiveList, ParsedDirective, TokenParser

class DigestChallenge
{

    DigestChallenge(byte abyte0[])
        throws SaslException
    {
        DirectiveList directivelist;
        m_realms = new ArrayList(5);
        m_nonce = null;
        m_qop = 0;
        m_staleFlag = false;
        m_maxBuf = -1;
        m_characterSet = null;
        m_algorithm = null;
        m_cipherOptions = 0;
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
            String s = parseddirective.getName();
            if(s.equals("realm"))
                handleRealm(parseddirective);
            else
            if(s.equals("nonce"))
                handleNonce(parseddirective);
            else
            if(s.equals("qop"))
                handleQop(parseddirective);
            else
            if(s.equals("maxbuf"))
                handleMaxbuf(parseddirective);
            else
            if(s.equals("charset"))
                handleCharset(parseddirective);
            else
            if(s.equals("algorithm"))
                handleAlgorithm(parseddirective);
            else
            if(s.equals("cipher"))
                handleCipher(parseddirective);
            else
            if(s.equals("stale"))
                handleStale(parseddirective);
        } while(true);
        if(-1 == m_maxBuf)
            m_maxBuf = 0x10000;
        if(m_qop == 0)
        {
            m_qop = 1;
        } else
        {
            if((1 & m_qop) != 1)
                throw new SaslException("Only qop-auth is supported by client");
            if((4 & m_qop) == 4 && (0x1f & m_cipherOptions) == 0)
                throw new SaslException("Invalid cipher options");
            if(m_nonce == null)
                throw new SaslException("Missing nonce directive");
            if(m_staleFlag)
                throw new SaslException("Unexpected stale flag");
            if(m_algorithm == null)
                throw new SaslException("Missing algorithm directive");
        }
    }

    public String getAlgorithm()
    {
        return m_algorithm;
    }

    public String getCharacterSet()
    {
        return m_characterSet;
    }

    public int getCipherOptions()
    {
        return m_cipherOptions;
    }

    public int getMaxBuf()
    {
        return m_maxBuf;
    }

    public String getNonce()
    {
        return m_nonce;
    }

    public int getQop()
    {
        return m_qop;
    }

    public ArrayList getRealms()
    {
        return m_realms;
    }

    public boolean getStaleFlag()
    {
        return m_staleFlag;
    }

    void handleAlgorithm(ParsedDirective parseddirective)
        throws SaslException
    {
        if(m_algorithm != null)
            throw new SaslException("Too many algorithm directives.");
        m_algorithm = parseddirective.getValue();
        if(!"md5-sess".equals(m_algorithm))
            throw new SaslException((new StringBuilder()).append("Invalid algorithm directive value: ").append(m_algorithm).toString());
        else
            return;
    }

    void handleCharset(ParsedDirective parseddirective)
        throws SaslException
    {
        if(m_characterSet != null)
            throw new SaslException("Too many charset directives.");
        m_characterSet = parseddirective.getValue();
        if(!m_characterSet.equals("utf-8"))
            throw new SaslException("Invalid character encoding directive");
        else
            return;
    }

    void handleCipher(ParsedDirective parseddirective)
        throws SaslException
    {
        if(m_cipherOptions != 0)
            throw new SaslException("Too many cipher directives.");
        TokenParser tokenparser = new TokenParser(parseddirective.getValue());
        tokenparser.parseToken();
        String s = tokenparser.parseToken();
        while(s != null) 
        {
            if("3des".equals(s))
                m_cipherOptions = 1 | m_cipherOptions;
            else
            if("des".equals(s))
                m_cipherOptions = 2 | m_cipherOptions;
            else
            if("rc4-40".equals(s))
                m_cipherOptions = 4 | m_cipherOptions;
            else
            if("rc4".equals(s))
                m_cipherOptions = 8 | m_cipherOptions;
            else
            if("rc4-56".equals(s))
                m_cipherOptions = 0x10 | m_cipherOptions;
            else
                m_cipherOptions = 0x20 | m_cipherOptions;
            s = tokenparser.parseToken();
        }
        if(m_cipherOptions == 0)
            m_cipherOptions = 32;
    }

    void handleMaxbuf(ParsedDirective parseddirective)
        throws SaslException
    {
        if(-1 != m_maxBuf)
            throw new SaslException("Too many maxBuf directives.");
        m_maxBuf = Integer.parseInt(parseddirective.getValue());
        if(m_maxBuf == 0)
            throw new SaslException("Max buf value must be greater than zero.");
        else
            return;
    }

    void handleNonce(ParsedDirective parseddirective)
        throws SaslException
    {
        if(m_nonce != null)
        {
            throw new SaslException("Too many nonce values.");
        } else
        {
            m_nonce = parseddirective.getValue();
            return;
        }
    }

    void handleQop(ParsedDirective parseddirective)
        throws SaslException
    {
        if(m_qop != 0)
            throw new SaslException("Too many qop directives.");
        TokenParser tokenparser = new TokenParser(parseddirective.getValue());
        String s = tokenparser.parseToken();
        while(s != null) 
        {
            if(s.equals("auth"))
                m_qop = 1 | m_qop;
            else
            if(s.equals("auth-int"))
                m_qop = 2 | m_qop;
            else
            if(s.equals("auth-conf"))
                m_qop = 4 | m_qop;
            else
                m_qop = 8 | m_qop;
            s = tokenparser.parseToken();
        }
    }

    void handleRealm(ParsedDirective parseddirective)
    {
        m_realms.add(parseddirective.getValue());
    }

    void handleStale(ParsedDirective parseddirective)
        throws SaslException
    {
        if(m_staleFlag)
            throw new SaslException("Too many stale directives.");
        if("true".equals(parseddirective.getValue()))
        {
            m_staleFlag = true;
            return;
        } else
        {
            throw new SaslException((new StringBuilder()).append("Invalid stale directive value: ").append(parseddirective.getValue()).toString());
        }
    }

    private static final int CIPHER_3DES = 1;
    private static final int CIPHER_DES = 2;
    private static final int CIPHER_RC4 = 8;
    private static final int CIPHER_RC4_40 = 4;
    private static final int CIPHER_RC4_56 = 16;
    private static final int CIPHER_RECOGNIZED_MASK = 31;
    private static final int CIPHER_UNRECOGNIZED = 32;
    public static final int QOP_AUTH = 1;
    public static final int QOP_AUTH_CONF = 4;
    public static final int QOP_AUTH_INT = 2;
    public static final int QOP_UNRECOGNIZED = 8;
    private String m_algorithm;
    private String m_characterSet;
    private int m_cipherOptions;
    private int m_maxBuf;
    private String m_nonce;
    private int m_qop;
    private ArrayList m_realms;
    private boolean m_staleFlag;
}
