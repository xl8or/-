// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FBChatAuthMechanism.java

package com.facebook.katana.service.xmpp;

import android.os.Bundle;
import com.facebook.katana.util.URLQueryBuilder;
import java.io.IOException;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;

// Referenced classes of package com.facebook.katana.service.xmpp:
//            FacebookXmppPacket

public class FBChatAuthMechanism extends SASLMechanism
{

    public FBChatAuthMechanism(SASLAuthentication saslauthentication)
    {
        super(saslauthentication);
    }

    public void authenticate(String s, String s1, String s2)
        throws IOException, XMPPException
    {
        if(s == null || s2 == null)
        {
            throw new IllegalArgumentException("Invalid parameters!");
        } else
        {
            mSessionSecret = s2;
            mSessionKey = s;
            getSASLAuthentication().send(new FacebookXmppPacket(FacebookXmppPacket.PacketType.AUTH));
            return;
        }
    }

    public void challengeReceived(String s)
        throws IOException
    {
        String s1 = "";
        if(s != null)
        {
            String s2 = new String(Base64.decode(s));
            Long long1 = Long.valueOf(System.currentTimeMillis() / 1000L);
            Bundle bundle = URLQueryBuilder.parseQueryString(s2);
            bundle.putString("api_key", Long.toString(0xa67c8e50L));
            bundle.putString("call_id", Long.toString(long1.longValue()));
            bundle.putString("session_key", mSessionKey);
            bundle.putString("v", "1.0");
            s1 = Base64.encodeBytes(URLQueryBuilder.buildSignedQueryString(bundle, mSessionSecret).toString().getBytes(), 8);
        }
        getSASLAuthentication().send(new FacebookXmppPacket(FacebookXmppPacket.PacketType.AUTH_RESPONSE, s1));
    }

    protected String getName()
    {
        return "X-FACEBOOK-PLATFORM";
    }

    public static final String FB_AUTH_MECHANISM = "X-FACEBOOK-PLATFORM";
    private String mSessionKey;
    private String mSessionSecret;
}
