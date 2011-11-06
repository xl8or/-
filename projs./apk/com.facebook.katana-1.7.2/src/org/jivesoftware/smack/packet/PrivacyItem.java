// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivacyItem.java

package org.jivesoftware.smack.packet;


public class PrivacyItem
{
    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(org/jivesoftware/smack/packet/PrivacyItem$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type group;
        public static final Type jid;
        public static final Type subscription;

        static 
        {
            group = new Type("group", 0);
            jid = new Type("jid", 1);
            subscription = new Type("subscription", 2);
            Type atype[] = new Type[3];
            atype[0] = group;
            atype[1] = jid;
            atype[2] = subscription;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }

    public static class PrivacyRule
    {

        protected static PrivacyRule fromString(String s)
        {
            PrivacyRule privacyrule;
            if(s == null)
            {
                privacyrule = null;
            } else
            {
                privacyrule = new PrivacyRule();
                privacyrule.setType(Type.valueOf(s.toLowerCase()));
            }
            return privacyrule;
        }

        private void setSuscriptionValue(String s)
        {
            if(s != null);
            String s1;
            if("both".equalsIgnoreCase(s))
                s1 = "both";
            else
            if("to".equalsIgnoreCase(s))
                s1 = "to";
            else
            if("from".equalsIgnoreCase(s))
                s1 = "from";
            else
            if("none".equalsIgnoreCase(s))
                s1 = "none";
            else
                s1 = null;
            value = s1;
        }

        private void setType(Type type1)
        {
            type = type1;
        }

        public Type getType()
        {
            return type;
        }

        public String getValue()
        {
            return value;
        }

        public boolean isSuscription()
        {
            boolean flag;
            if(getType() == Type.subscription)
                flag = true;
            else
                flag = false;
            return flag;
        }

        protected void setValue(String s)
        {
            if(isSuscription())
                setSuscriptionValue(s);
            else
                value = s;
        }

        public static final String SUBSCRIPTION_BOTH = "both";
        public static final String SUBSCRIPTION_FROM = "from";
        public static final String SUBSCRIPTION_NONE = "none";
        public static final String SUBSCRIPTION_TO = "to";
        private Type type;
        private String value;

        public PrivacyRule()
        {
        }
    }


    public PrivacyItem(String s, boolean flag, int i)
    {
        filterIQ = false;
        filterMessage = false;
        filterPresence_in = false;
        filterPresence_out = false;
        setRule(PrivacyRule.fromString(s));
        setAllow(flag);
        setOrder(i);
    }

    private PrivacyRule getRule()
    {
        return rule;
    }

    private void setAllow(boolean flag)
    {
        allow = flag;
    }

    private void setOrder(int i)
    {
        order = i;
    }

    private void setRule(PrivacyRule privacyrule)
    {
        rule = privacyrule;
    }

    public int getOrder()
    {
        return order;
    }

    public Type getType()
    {
        Type type;
        if(getRule() == null)
            type = null;
        else
            type = getRule().getType();
        return type;
    }

    public String getValue()
    {
        String s;
        if(getRule() == null)
            s = null;
        else
            s = getRule().getValue();
        return s;
    }

    public boolean isAllow()
    {
        return allow;
    }

    public boolean isFilterEverything()
    {
        boolean flag;
        if(!isFilterIQ() && !isFilterMessage() && !isFilterPresence_in() && !isFilterPresence_out())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isFilterIQ()
    {
        return filterIQ;
    }

    public boolean isFilterMessage()
    {
        return filterMessage;
    }

    public boolean isFilterPresence_in()
    {
        return filterPresence_in;
    }

    public boolean isFilterPresence_out()
    {
        return filterPresence_out;
    }

    public void setFilterIQ(boolean flag)
    {
        filterIQ = flag;
    }

    public void setFilterMessage(boolean flag)
    {
        filterMessage = flag;
    }

    public void setFilterPresence_in(boolean flag)
    {
        filterPresence_in = flag;
    }

    public void setFilterPresence_out(boolean flag)
    {
        filterPresence_out = flag;
    }

    public void setValue(String s)
    {
        if(getRule() != null || s != null)
            getRule().setValue(s);
    }

    public String toXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<item");
        if(isAllow())
            stringbuilder.append(" action=\"allow\"");
        else
            stringbuilder.append(" action=\"deny\"");
        stringbuilder.append(" order=\"").append(getOrder()).append("\"");
        if(getType() != null)
            stringbuilder.append(" type=\"").append(getType()).append("\"");
        if(getValue() != null)
            stringbuilder.append(" value=\"").append(getValue()).append("\"");
        if(isFilterEverything())
        {
            stringbuilder.append("/>");
        } else
        {
            stringbuilder.append(">");
            if(isFilterIQ())
                stringbuilder.append("<iq/>");
            if(isFilterMessage())
                stringbuilder.append("<message/>");
            if(isFilterPresence_in())
                stringbuilder.append("<presence-in/>");
            if(isFilterPresence_out())
                stringbuilder.append("<presence-out/>");
            stringbuilder.append("</item>");
        }
        return stringbuilder.toString();
    }

    private boolean allow;
    private boolean filterIQ;
    private boolean filterMessage;
    private boolean filterPresence_in;
    private boolean filterPresence_out;
    private int order;
    private PrivacyRule rule;
}
