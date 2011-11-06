// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Authentication.java

package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.packet:
//            IQ

public class Authentication extends IQ
{

    public Authentication()
    {
        username = null;
        password = null;
        digest = null;
        resource = null;
        setType(IQ.Type.SET);
    }

    public String getChildElementXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<query xmlns=\"jabber:iq:auth\">");
        if(username != null)
            if(username.equals(""))
                stringbuilder.append("<username/>");
            else
                stringbuilder.append("<username>").append(username).append("</username>");
        if(digest != null)
            if(digest.equals(""))
                stringbuilder.append("<digest/>");
            else
                stringbuilder.append("<digest>").append(digest).append("</digest>");
        if(password != null && digest == null)
            if(password.equals(""))
                stringbuilder.append("<password/>");
            else
                stringbuilder.append("<password>").append(StringUtils.escapeForXML(password)).append("</password>");
        if(resource != null)
            if(resource.equals(""))
                stringbuilder.append("<resource/>");
            else
                stringbuilder.append("<resource>").append(resource).append("</resource>");
        stringbuilder.append("</query>");
        return stringbuilder.toString();
    }

    public String getDigest()
    {
        return digest;
    }

    public String getPassword()
    {
        return password;
    }

    public String getResource()
    {
        return resource;
    }

    public String getUsername()
    {
        return username;
    }

    public void setDigest(String s)
    {
        digest = s;
    }

    public void setDigest(String s, String s1)
    {
        digest = StringUtils.hash((new StringBuilder()).append(s).append(s1).toString());
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void setResource(String s)
    {
        resource = s;
    }

    public void setUsername(String s)
    {
        username = s;
    }

    private String digest;
    private String password;
    private String resource;
    private String username;
}
