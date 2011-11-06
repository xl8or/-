// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IQ.java

package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.packet:
//            Packet, XMPPError

public abstract class IQ extends Packet
{
    public static class Type
    {

        public static Type fromString(String s)
        {
            Type type1;
            if(s == null)
            {
                type1 = null;
            } else
            {
                String s1 = s.toLowerCase();
                if(GET.toString().equals(s1))
                    type1 = GET;
                else
                if(SET.toString().equals(s1))
                    type1 = SET;
                else
                if(ERROR.toString().equals(s1))
                    type1 = ERROR;
                else
                if(RESULT.toString().equals(s1))
                    type1 = RESULT;
                else
                    type1 = null;
            }
            return type1;
        }

        public String toString()
        {
            return value;
        }

        public static final Type ERROR = new Type("error");
        public static final Type GET = new Type("get");
        public static final Type RESULT = new Type("result");
        public static final Type SET = new Type("set");
        private String value;


        private Type(String s)
        {
            value = s;
        }
    }


    public IQ()
    {
        type = Type.GET;
    }

    public static IQ createErrorResponse(final IQ request, XMPPError xmpperror)
    {
        if(request.getType() != Type.GET && request.getType() != Type.SET)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("IQ must be of type 'set' or 'get'. Original IQ: ").append(request.toXML()).toString());
        } else
        {
            IQ iq = new IQ() {

                public String getChildElementXML()
                {
                    return request.getChildElementXML();
                }

                final IQ val$request;

            
            {
                request = iq;
                super();
            }
            }
;
            iq.setType(Type.ERROR);
            iq.setPacketID(request.getPacketID());
            iq.setFrom(request.getTo());
            iq.setTo(request.getFrom());
            iq.setError(xmpperror);
            return iq;
        }
    }

    public static IQ createResultIQ(IQ iq)
    {
        if(iq.getType() != Type.GET && iq.getType() != Type.SET)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("IQ must be of type 'set' or 'get'. Original IQ: ").append(iq.toXML()).toString());
        } else
        {
            IQ iq1 = new IQ() {

                public String getChildElementXML()
                {
                    return null;
                }

            }
;
            iq1.setType(Type.RESULT);
            iq1.setPacketID(iq.getPacketID());
            iq1.setFrom(iq.getTo());
            iq1.setTo(iq.getFrom());
            return iq1;
        }
    }

    public abstract String getChildElementXML();

    public Type getType()
    {
        return type;
    }

    public void setType(Type type1)
    {
        if(type1 == null)
            type = Type.GET;
        else
            type = type1;
    }

    public String toXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<iq ");
        if(getPacketID() != null)
            stringbuilder.append((new StringBuilder()).append("id=\"").append(getPacketID()).append("\" ").toString());
        if(getTo() != null)
            stringbuilder.append("to=\"").append(StringUtils.escapeForXML(getTo())).append("\" ");
        if(getFrom() != null)
            stringbuilder.append("from=\"").append(StringUtils.escapeForXML(getFrom())).append("\" ");
        String s;
        XMPPError xmpperror;
        if(type == null)
            stringbuilder.append("type=\"get\">");
        else
            stringbuilder.append("type=\"").append(getType()).append("\">");
        s = getChildElementXML();
        if(s != null)
            stringbuilder.append(s);
        xmpperror = getError();
        if(xmpperror != null)
            stringbuilder.append(xmpperror.toXML());
        stringbuilder.append("</iq>");
        return stringbuilder.toString();
    }

    private Type type;
}
