// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Presence.java

package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.packet:
//            Packet, XMPPError

public class Presence extends Packet
{
    public static final class Mode extends Enum
    {

        public static Mode valueOf(String s)
        {
            return (Mode)Enum.valueOf(org/jivesoftware/smack/packet/Presence$Mode, s);
        }

        public static Mode[] values()
        {
            return (Mode[])$VALUES.clone();
        }

        private static final Mode $VALUES[];
        public static final Mode available;
        public static final Mode away;
        public static final Mode chat;
        public static final Mode dnd;
        public static final Mode xa;

        static 
        {
            chat = new Mode("chat", 0);
            available = new Mode("available", 1);
            away = new Mode("away", 2);
            xa = new Mode("xa", 3);
            dnd = new Mode("dnd", 4);
            Mode amode[] = new Mode[5];
            amode[0] = chat;
            amode[1] = available;
            amode[2] = away;
            amode[3] = xa;
            amode[4] = dnd;
            $VALUES = amode;
        }

        private Mode(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(org/jivesoftware/smack/packet/Presence$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type available;
        public static final Type error;
        public static final Type subscribe;
        public static final Type subscribed;
        public static final Type unavailable;
        public static final Type unsubscribe;
        public static final Type unsubscribed;

        static 
        {
            available = new Type("available", 0);
            unavailable = new Type("unavailable", 1);
            subscribe = new Type("subscribe", 2);
            subscribed = new Type("subscribed", 3);
            unsubscribe = new Type("unsubscribe", 4);
            unsubscribed = new Type("unsubscribed", 5);
            error = new Type("error", 6);
            Type atype[] = new Type[7];
            atype[0] = available;
            atype[1] = unavailable;
            atype[2] = subscribe;
            atype[3] = subscribed;
            atype[4] = unsubscribe;
            atype[5] = unsubscribed;
            atype[6] = error;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    public Presence(Type type1)
    {
        type = Type.available;
        status = null;
        priority = 0x80000000;
        mode = null;
        setType(type1);
    }

    public Presence(Type type1, String s, int i, Mode mode1)
    {
        type = Type.available;
        status = null;
        priority = 0x80000000;
        mode = null;
        setType(type1);
        setStatus(s);
        setPriority(i);
        setMode(mode1);
    }

    private String getLanguage()
    {
        return language;
    }

    public Mode getMode()
    {
        return mode;
    }

    public int getPriority()
    {
        return priority;
    }

    public String getStatus()
    {
        return status;
    }

    public Type getType()
    {
        return type;
    }

    public boolean isAvailable()
    {
        boolean flag;
        if(type == Type.available)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isAway()
    {
        boolean flag;
        if(type == Type.available && (mode == Mode.away || mode == Mode.xa || mode == Mode.dnd))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void setLanguage(String s)
    {
        language = s;
    }

    public void setMode(Mode mode1)
    {
        mode = mode1;
    }

    public void setPriority(int i)
    {
        if(i < -128 || i > 128)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Priority value ").append(i).append(" is not valid. Valid range is -128 through 128.").toString());
        } else
        {
            priority = i;
            return;
        }
    }

    public void setStatus(String s)
    {
        status = s;
    }

    public void setType(Type type1)
    {
        if(type1 == null)
        {
            throw new NullPointerException("Type cannot be null");
        } else
        {
            type = type1;
            return;
        }
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(type);
        if(mode != null)
            stringbuilder.append(": ").append(mode);
        if(getStatus() != null)
            stringbuilder.append(" (").append(getStatus()).append(")");
        return stringbuilder.toString();
    }

    public String toXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<presence");
        if(getXmlns() != null)
            stringbuilder.append(" xmlns=\"").append(getXmlns()).append("\"");
        if(language != null)
            stringbuilder.append(" xml:lang=\"").append(getLanguage()).append("\"");
        if(getPacketID() != null)
            stringbuilder.append(" id=\"").append(getPacketID()).append("\"");
        if(getTo() != null)
            stringbuilder.append(" to=\"").append(StringUtils.escapeForXML(getTo())).append("\"");
        if(getFrom() != null)
            stringbuilder.append(" from=\"").append(StringUtils.escapeForXML(getFrom())).append("\"");
        if(type != Type.available)
            stringbuilder.append(" type=\"").append(type).append("\"");
        stringbuilder.append(">");
        if(status != null)
            stringbuilder.append("<status>").append(StringUtils.escapeForXML(status)).append("</status>");
        if(priority != 0x80000000)
            stringbuilder.append("<priority>").append(priority).append("</priority>");
        if(mode != null && mode != Mode.available)
            stringbuilder.append("<show>").append(mode).append("</show>");
        stringbuilder.append(getExtensionsXML());
        XMPPError xmpperror = getError();
        if(xmpperror != null)
            stringbuilder.append(xmpperror.toXML());
        stringbuilder.append("</presence>");
        return stringbuilder.toString();
    }

    private String language;
    private Mode mode;
    private int priority;
    private String status;
    private Type type;
}
