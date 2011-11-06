// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SASLMechanism.java

package org.jivesoftware.smack.sasl;

import de.measite.smack.Sasl;
import java.io.IOException;
import java.util.HashMap;
import org.apache.harmony.javax.security.auth.callback.*;
import org.apache.harmony.javax.security.sasl.*;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.Base64;

public abstract class SASLMechanism
    implements CallbackHandler
{
    public static class Failure extends Packet
    {

        public String getCondition()
        {
            return condition;
        }

        public String toXML()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("<failure xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
            if(condition != null && condition.trim().length() > 0)
                stringbuilder.append("<").append(condition).append("/>");
            stringbuilder.append("</failure>");
            return stringbuilder.toString();
        }

        private final String condition;

        public Failure(String s)
        {
            condition = s;
        }
    }

    public static class Success extends Packet
    {

        public String toXML()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("<success xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
            if(data != null && data.trim().length() > 0)
                stringbuilder.append(data);
            stringbuilder.append("</success>");
            return stringbuilder.toString();
        }

        private final String data;

        public Success(String s)
        {
            data = s;
        }
    }

    public class Response extends Packet
    {

        public String toXML()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("<response xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
            if(authenticationText != null)
                stringbuilder.append(authenticationText);
            else
                stringbuilder.append("=");
            stringbuilder.append("</response>");
            return stringbuilder.toString();
        }

        private final String authenticationText;
        final SASLMechanism this$0;

        public Response()
        {
            this$0 = SASLMechanism.this;
            super();
            authenticationText = null;
        }

        public Response(String s)
        {
            this$0 = SASLMechanism.this;
            super();
            if(s == null || s.trim().length() == 0)
                authenticationText = null;
            else
                authenticationText = s;
        }
    }

    public static class Challenge extends Packet
    {

        public String toXML()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("<challenge xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
            if(data != null && data.trim().length() > 0)
                stringbuilder.append(data);
            stringbuilder.append("</challenge>");
            return stringbuilder.toString();
        }

        private final String data;

        public Challenge(String s)
        {
            data = s;
        }
    }

    public class AuthMechanism extends Packet
    {

        public String toXML()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("<auth mechanism=\"").append(name);
            stringbuilder.append("\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
            if(authenticationText != null && authenticationText.trim().length() > 0)
                stringbuilder.append(authenticationText);
            stringbuilder.append("</auth>");
            return stringbuilder.toString();
        }

        private final String authenticationText;
        private final String name;
        final SASLMechanism this$0;

        public AuthMechanism(String s, String s1)
        {
            this$0 = SASLMechanism.this;
            super();
            if(s == null)
            {
                throw new NullPointerException("SASL mechanism name shouldn't be null.");
            } else
            {
                name = s;
                authenticationText = s1;
                return;
            }
        }
    }


    public SASLMechanism(SASLAuthentication saslauthentication)
    {
        saslAuthentication = saslauthentication;
    }

    protected void authenticate()
        throws IOException, XMPPException
    {
label0:
        {
            String s = null;
            String s1;
            try
            {
                if(!sc.hasInitialResponse())
                    break label0;
                s1 = Base64.encodeBytes(sc.evaluateChallenge(new byte[0]), 8);
            }
            catch(SaslException saslexception)
            {
                throw new XMPPException("SASL authentication failed", saslexception);
            }
            s = s1;
        }
        getSASLAuthentication().send(new AuthMechanism(getName(), s));
    }

    public void authenticate(String s, String s1, String s2)
        throws IOException, XMPPException
    {
        authenticationId = s;
        password = s2;
        hostname = s1;
        String as[] = new String[1];
        as[0] = getName();
        sc = Sasl.createSaslClient(as, s, "xmpp", s1, new HashMap(), this);
        authenticate();
    }

    public void authenticate(String s, String s1, CallbackHandler callbackhandler)
        throws IOException, XMPPException
    {
        String as[] = new String[1];
        as[0] = getName();
        sc = Sasl.createSaslClient(as, s, "xmpp", s1, new HashMap(), callbackhandler);
        authenticate();
    }

    public void challengeReceived(String s)
        throws IOException
    {
        byte abyte0[];
        Response response;
        if(s != null)
            abyte0 = sc.evaluateChallenge(Base64.decode(s));
        else
            abyte0 = sc.evaluateChallenge(new byte[0]);
        if(abyte0 == null)
            response = new Response();
        else
            response = new Response(Base64.encodeBytes(abyte0, 8));
        getSASLAuthentication().send(response);
    }

    protected abstract String getName();

    protected SASLAuthentication getSASLAuthentication()
    {
        return saslAuthentication;
    }

    public void handle(Callback acallback[])
        throws IOException, UnsupportedCallbackException
    {
        int i = 0;
_L1:
        if(i >= acallback.length)
            break MISSING_BLOCK_LABEL_109;
        if(acallback[i] instanceof NameCallback)
            ((NameCallback)acallback[i]).setName(authenticationId);
        else
        if(acallback[i] instanceof PasswordCallback)
        {
            ((PasswordCallback)acallback[i]).setPassword(password.toCharArray());
        } else
        {
            if(!(acallback[i] instanceof RealmCallback))
                continue; /* Loop/switch isn't completed */
            ((RealmCallback)acallback[i]).setText(hostname);
        }
_L3:
        i++;
          goto _L1
        if(acallback[i] instanceof RealmChoiceCallback) goto _L3; else goto _L2
_L2:
        throw new UnsupportedCallbackException(acallback[i]);
    }

    protected String authenticationId;
    protected String hostname;
    protected String password;
    private SASLAuthentication saslAuthentication;
    protected SaslClient sc;
}
