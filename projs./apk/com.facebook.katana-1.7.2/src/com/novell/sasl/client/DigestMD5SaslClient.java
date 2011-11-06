// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DigestMD5SaslClient.java

package com.novell.sasl.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.*;
import org.apache.harmony.javax.security.sasl.*;

// Referenced classes of package com.novell.sasl.client:
//            DigestChallenge, ResponseAuth

public class DigestMD5SaslClient
    implements SaslClient
{

    private DigestMD5SaslClient(String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
    {
        m_authorizationId = "";
        m_protocol = "";
        m_serverName = "";
        m_qopValue = "";
        m_HA1 = null;
        m_clientNonce = "";
        m_realm = "";
        m_name = "";
        m_authorizationId = s;
        m_protocol = s1;
        m_serverName = s2;
        m_props = map;
        m_cbh = callbackhandler;
        m_state = 0;
    }

    private String createDigestResponse(byte abyte0[])
        throws SaslException
    {
        StringBuffer stringbuffer = new StringBuffer(512);
        m_dc = new DigestChallenge(abyte0);
        m_digestURI = (new StringBuilder()).append(m_protocol).append("/").append(m_serverName).toString();
        Callback acallback[];
        if((1 & m_dc.getQop()) == 1)
        {
            m_qopValue = "auth";
            acallback = new Callback[3];
            ArrayList arraylist = m_dc.getRealms();
            int i = arraylist.size();
            if(i == 0)
                acallback[0] = new RealmCallback("Realm");
            else
            if(i == 1)
                acallback[0] = new RealmCallback("Realm", (String)arraylist.get(0));
            else
                acallback[0] = new RealmChoiceCallback("Realm", (String[])(String[])arraylist.toArray(new String[i]), 0, false);
            acallback[1] = new PasswordCallback("Password", false);
            if(m_authorizationId == null || m_authorizationId.length() == 0)
                acallback[2] = new NameCallback("Name");
            else
                acallback[2] = new NameCallback("Name", m_authorizationId);
            try
            {
                m_cbh.handle(acallback);
            }
            catch(UnsupportedCallbackException unsupportedcallbackexception)
            {
                throw new SaslException("Handler does not support necessary callbacks", unsupportedcallbackexception);
            }
            catch(IOException ioexception)
            {
                throw new SaslException("IO exception in CallbackHandler.", ioexception);
            }
            if(i > 1)
            {
                int ai[] = ((RealmChoiceCallback)acallback[0]).getSelectedIndexes();
                if(ai.length > 0)
                    m_realm = ((RealmChoiceCallback)acallback[0]).getChoices()[ai[0]];
                else
                    m_realm = ((RealmChoiceCallback)acallback[0]).getChoices()[0];
            } else
            {
                m_realm = ((RealmCallback)acallback[0]).getText();
            }
            m_clientNonce = getClientNonce();
            m_name = ((NameCallback)acallback[2]).getName();
            if(m_name == null)
                m_name = ((NameCallback)acallback[2]).getDefaultName();
            if(m_name == null)
                throw new SaslException("No user name was specified.");
        } else
        {
            throw new SaslException("Client only supports qop of 'auth'");
        }
        m_HA1 = DigestCalcHA1(m_dc.getAlgorithm(), m_name, m_realm, new String(((PasswordCallback)acallback[1]).getPassword()), m_dc.getNonce(), m_clientNonce);
        char ac[] = DigestCalcResponse(m_HA1, m_dc.getNonce(), "00000001", m_clientNonce, m_qopValue, "AUTHENTICATE", m_digestURI, true);
        stringbuffer.append("username=\"");
        stringbuffer.append(m_authorizationId);
        if(m_realm.length() != 0)
        {
            stringbuffer.append("\",realm=\"");
            stringbuffer.append(m_realm);
        }
        stringbuffer.append("\",cnonce=\"");
        stringbuffer.append(m_clientNonce);
        stringbuffer.append("\",nc=");
        stringbuffer.append("00000001");
        stringbuffer.append(",qop=");
        stringbuffer.append(m_qopValue);
        stringbuffer.append(",digest-uri=\"");
        stringbuffer.append(m_digestURI);
        stringbuffer.append("\",response=");
        stringbuffer.append(ac);
        stringbuffer.append(",charset=utf-8,nonce=\"");
        stringbuffer.append(m_dc.getNonce());
        stringbuffer.append("\"");
        return stringbuffer.toString();
    }

    public static SaslClient getClient(String s, String s1, String s2, Map map, CallbackHandler callbackhandler)
    {
        String s3 = (String)map.get("javax.security.sasl.qop");
        String _tmp = (String)map.get("javax.security.sasl.strength");
        String s4 = (String)map.get("javax.security.sasl.server.authentication");
        Object obj;
        if(s3 != null && !"auth".equals(s3))
            obj = null;
        else
        if(s4 != null && !"false".equals(s4))
            obj = null;
        else
        if(callbackhandler == null)
            obj = null;
        else
            obj = new DigestMD5SaslClient(s, s1, s2, map, callbackhandler);
        return ((SaslClient) (obj));
    }

    private static char getHexChar(byte byte0)
    {
        byte0;
        JVM INSTR tableswitch 0 15: default 80
    //                   0 85
    //                   1 91
    //                   2 97
    //                   3 103
    //                   4 109
    //                   5 115
    //                   6 121
    //                   7 127
    //                   8 133
    //                   9 139
    //                   10 145
    //                   11 151
    //                   12 157
    //                   13 163
    //                   14 169
    //                   15 175;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17
_L1:
        char c = 'Z';
_L19:
        return c;
_L2:
        c = '0';
        continue; /* Loop/switch isn't completed */
_L3:
        c = '1';
        continue; /* Loop/switch isn't completed */
_L4:
        c = '2';
        continue; /* Loop/switch isn't completed */
_L5:
        c = '3';
        continue; /* Loop/switch isn't completed */
_L6:
        c = '4';
        continue; /* Loop/switch isn't completed */
_L7:
        c = '5';
        continue; /* Loop/switch isn't completed */
_L8:
        c = '6';
        continue; /* Loop/switch isn't completed */
_L9:
        c = '7';
        continue; /* Loop/switch isn't completed */
_L10:
        c = '8';
        continue; /* Loop/switch isn't completed */
_L11:
        c = '9';
        continue; /* Loop/switch isn't completed */
_L12:
        c = 'a';
        continue; /* Loop/switch isn't completed */
_L13:
        c = 'b';
        continue; /* Loop/switch isn't completed */
_L14:
        c = 'c';
        continue; /* Loop/switch isn't completed */
_L15:
        c = 'd';
        continue; /* Loop/switch isn't completed */
_L16:
        c = 'e';
        continue; /* Loop/switch isn't completed */
_L17:
        c = 'f';
        if(true) goto _L19; else goto _L18
_L18:
    }

    char[] DigestCalcHA1(String s, String s1, String s2, String s3, String s4, String s5)
        throws SaslException
    {
        MessageDigest messagedigest;
        byte abyte0[];
        messagedigest = MessageDigest.getInstance("MD5");
        messagedigest.update(s1.getBytes("UTF-8"));
        messagedigest.update(":".getBytes("UTF-8"));
        messagedigest.update(s2.getBytes("UTF-8"));
        messagedigest.update(":".getBytes("UTF-8"));
        messagedigest.update(s3.getBytes("UTF-8"));
        abyte0 = messagedigest.digest();
        if(!"md5-sess".equals(s)) goto _L2; else goto _L1
_L1:
        byte abyte2[];
        messagedigest.update(abyte0);
        messagedigest.update(":".getBytes("UTF-8"));
        messagedigest.update(s4.getBytes("UTF-8"));
        messagedigest.update(":".getBytes("UTF-8"));
        messagedigest.update(s5.getBytes("UTF-8"));
        abyte2 = messagedigest.digest();
        byte abyte1[] = abyte2;
_L4:
        return convertToHex(abyte1);
        NoSuchAlgorithmException nosuchalgorithmexception;
        nosuchalgorithmexception;
        throw new SaslException("No provider found for MD5 hash", nosuchalgorithmexception);
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        throw new SaslException("UTF-8 encoding not supported by platform.", unsupportedencodingexception);
_L2:
        abyte1 = abyte0;
        if(true) goto _L4; else goto _L3
_L3:
    }

    char[] DigestCalcResponse(char ac[], String s, String s1, String s2, String s3, String s4, String s5, 
            boolean flag)
        throws SaslException
    {
        byte abyte0[];
        try
        {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            if(flag)
                messagedigest.update(s4.getBytes("UTF-8"));
            messagedigest.update(":".getBytes("UTF-8"));
            messagedigest.update(s5.getBytes("UTF-8"));
            if("auth-int".equals(s3))
            {
                messagedigest.update(":".getBytes("UTF-8"));
                messagedigest.update("00000000000000000000000000000000".getBytes("UTF-8"));
            }
            char ac1[] = convertToHex(messagedigest.digest());
            messagedigest.update((new String(ac)).getBytes("UTF-8"));
            messagedigest.update(":".getBytes("UTF-8"));
            messagedigest.update(s.getBytes("UTF-8"));
            messagedigest.update(":".getBytes("UTF-8"));
            if(s3.length() > 0)
            {
                messagedigest.update(s1.getBytes("UTF-8"));
                messagedigest.update(":".getBytes("UTF-8"));
                messagedigest.update(s2.getBytes("UTF-8"));
                messagedigest.update(":".getBytes("UTF-8"));
                messagedigest.update(s3.getBytes("UTF-8"));
                messagedigest.update(":".getBytes("UTF-8"));
            }
            messagedigest.update((new String(ac1)).getBytes("UTF-8"));
            abyte0 = messagedigest.digest();
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            throw new SaslException("No provider found for MD5 hash", nosuchalgorithmexception);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new SaslException("UTF-8 encoding not supported by platform.", unsupportedencodingexception);
        }
        return convertToHex(abyte0);
    }

    boolean checkServerResponseAuth(byte abyte0[])
        throws SaslException
    {
        ResponseAuth responseauth = new ResponseAuth(abyte0);
        return (new String(DigestCalcResponse(m_HA1, m_dc.getNonce(), "00000001", m_clientNonce, m_qopValue, "AUTHENTICATE", m_digestURI, false))).equals(responseauth.getResponseValue());
    }

    char[] convertToHex(byte abyte0[])
    {
        char ac[] = new char[32];
        for(int i = 0; i < 16; i++)
        {
            ac[i * 2] = getHexChar((byte)((0xf0 & abyte0[i]) >> 4));
            ac[1 + i * 2] = getHexChar((byte)(0xf & abyte0[i]));
        }

        return ac;
    }

    public void dispose()
        throws SaslException
    {
        if(m_state != 4)
            m_state = 4;
    }

    public byte[] evaluateChallenge(byte abyte0[])
        throws SaslException
    {
        byte abyte1[] = null;
        m_state;
        JVM INSTR tableswitch 0 4: default 40
    //                   0 51
    //                   1 99
    //                   2 131
    //                   3 131
    //                   4 142;
           goto _L1 _L2 _L3 _L4 _L4 _L5
_L5:
        break MISSING_BLOCK_LABEL_142;
_L1:
        throw new SaslException("Unknown client state.");
_L2:
        if(abyte0.length == 0)
            throw new SaslException("response = byte[0]");
        try
        {
            abyte1 = createDigestResponse(abyte0).getBytes("UTF-8");
            m_state = 1;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new SaslException("UTF-8 encoding not suppported by platform", unsupportedencodingexception);
        }
_L6:
        return abyte1;
_L3:
        if(checkServerResponseAuth(abyte0))
        {
            m_state = 2;
        } else
        {
            m_state = 3;
            throw new SaslException("Could not validate response-auth value from server");
        }
        if(true) goto _L6; else goto _L4
_L4:
        throw new SaslException("Authentication sequence is complete");
        throw new SaslException("Client has been disposed");
    }

    String getClientNonce()
        throws SaslException
    {
        byte abyte0[] = new byte[32];
        char ac[] = new char[64];
        String s;
        try
        {
            SecureRandom.getInstance("SHA1PRNG").nextBytes(abyte0);
            for(int i = 0; i < 32; i++)
            {
                ac[i * 2] = getHexChar((byte)(0xf & abyte0[i]));
                ac[1 + i * 2] = getHexChar((byte)((0xf0 & abyte0[i]) >> 4));
            }

            s = new String(ac);
        }
        catch(NoSuchAlgorithmException nosuchalgorithmexception)
        {
            throw new SaslException("No random number generator available", nosuchalgorithmexception);
        }
        return s;
    }

    public String getMechanismName()
    {
        return "DIGEST-MD5";
    }

    public Object getNegotiatedProperty(String s)
    {
        if(m_state != 2)
            throw new IllegalStateException("getNegotiatedProperty: authentication exchange not complete.");
        String s1;
        if("javax.security.sasl.qop".equals(s))
            s1 = "auth";
        else
            s1 = null;
        return s1;
    }

    public boolean hasInitialResponse()
    {
        return false;
    }

    public boolean isComplete()
    {
        boolean flag;
        if(m_state == 2 || m_state == 3 || m_state == 4)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public byte[] unwrap(byte abyte0[], int i, int j)
        throws SaslException
    {
        throw new IllegalStateException("unwrap: QOP has neither integrity nor privacy>");
    }

    public byte[] wrap(byte abyte0[], int i, int j)
        throws SaslException
    {
        throw new IllegalStateException("wrap: QOP has neither integrity nor privacy>");
    }

    private static final String DIGEST_METHOD = "AUTHENTICATE";
    private static final int NONCE_BYTE_COUNT = 32;
    private static final int NONCE_HEX_COUNT = 64;
    private static final int STATE_DIGEST_RESPONSE_SENT = 1;
    private static final int STATE_DISPOSED = 4;
    private static final int STATE_INITIAL = 0;
    private static final int STATE_INVALID_SERVER_RESPONSE = 3;
    private static final int STATE_VALID_SERVER_RESPONSE = 2;
    private char m_HA1[];
    private String m_authorizationId;
    private CallbackHandler m_cbh;
    private String m_clientNonce;
    private DigestChallenge m_dc;
    private String m_digestURI;
    private String m_name;
    private Map m_props;
    private String m_protocol;
    private String m_qopValue;
    private String m_realm;
    private String m_serverName;
    private int m_state;
}
