// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Registration.java

package org.jivesoftware.smack.packet;

import java.util.*;

// Referenced classes of package org.jivesoftware.smack.packet:
//            IQ

public class Registration extends IQ
{

    public Registration()
    {
        instructions = null;
        attributes = new HashMap();
        requiredFields = new ArrayList();
        registered = false;
        remove = false;
    }

    public void addAttribute(String s, String s1)
    {
        attributes.put(s, s1);
    }

    public void addRequiredField(String s)
    {
        requiredFields.add(s);
    }

    public Map getAttributes()
    {
        return attributes;
    }

    public String getChildElementXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<query xmlns=\"jabber:iq:register\">");
        if(instructions != null && !remove)
            stringbuilder.append("<instructions>").append(instructions).append("</instructions>");
        if(attributes != null && attributes.size() > 0 && !remove)
        {
            String s;
            for(Iterator iterator = attributes.keySet().iterator(); iterator.hasNext(); stringbuilder.append("</").append(s).append(">"))
            {
                s = (String)iterator.next();
                String s1 = (String)attributes.get(s);
                stringbuilder.append("<").append(s).append(">");
                stringbuilder.append(s1);
            }

        } else
        if(remove)
            stringbuilder.append("</remove>");
        stringbuilder.append(getExtensionsXML());
        stringbuilder.append("</query>");
        return stringbuilder.toString();
    }

    public String getField(String s)
    {
        return (String)attributes.get(s);
    }

    public List getFieldNames()
    {
        return new ArrayList(attributes.keySet());
    }

    public String getInstructions()
    {
        return instructions;
    }

    public List getRequiredFields()
    {
        return requiredFields;
    }

    public boolean isRegistered()
    {
        return registered;
    }

    public void setInstructions(String s)
    {
        instructions = s;
    }

    public void setPassword(String s)
    {
        attributes.put("password", s);
    }

    public void setRegistered(boolean flag)
    {
        registered = flag;
    }

    public void setRemove(boolean flag)
    {
        remove = flag;
    }

    public void setUsername(String s)
    {
        attributes.put("username", s);
    }

    private Map attributes;
    private String instructions;
    private boolean registered;
    private boolean remove;
    private List requiredFields;
}
