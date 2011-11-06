// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComposableBody.java

package com.kenai.jbosh;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package com.kenai.jbosh:
//            AbstractBody, BodyQName, BOSHException, StaticBody

public final class ComposableBody extends AbstractBody
{
    public static final class Builder
    {

        private static Builder fromBody(ComposableBody composablebody)
        {
            Builder builder1 = new Builder();
            builder1.map = composablebody.getAttributes();
            builder1.doMapCopy = true;
            builder1.payloadXML = composablebody.payload;
            return builder1;
        }

        public ComposableBody build()
        {
            if(map == null)
                map = new HashMap();
            if(payloadXML == null)
                payloadXML = "";
            return new ComposableBody(map, payloadXML);
        }

        public Builder setAttribute(BodyQName bodyqname, String s)
        {
            if(map == null)
                map = new HashMap();
            else
            if(doMapCopy)
            {
                map = new HashMap(map);
                doMapCopy = false;
            }
            if(s == null)
                map.remove(bodyqname);
            else
                map.put(bodyqname, s);
            return this;
        }

        public Builder setNamespaceDefinition(String s, String s1)
        {
            return setAttribute(BodyQName.createWithPrefix("http://www.w3.org/XML/1998/namespace", s, "xmlns"), s1);
        }

        public Builder setPayloadXML(String s)
        {
            if(s == null)
            {
                throw new IllegalArgumentException("payload XML argument cannot be null");
            } else
            {
                payloadXML = s;
                return this;
            }
        }

        private boolean doMapCopy;
        private Map map;
        private String payloadXML;


        private Builder()
        {
        }

    }


    private ComposableBody(Map map, String s)
    {
        computed = new AtomicReference();
        attrs = map;
        payload = s;
    }


    public static Builder builder()
    {
        return new Builder();
    }

    private String computeXML()
    {
        BodyQName bodyqname = getBodyQName();
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<");
        stringbuilder.append(bodyqname.getLocalPart());
        for(Iterator iterator = attrs.entrySet().iterator(); iterator.hasNext(); stringbuilder.append("'"))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            stringbuilder.append(" ");
            BodyQName bodyqname1 = (BodyQName)entry.getKey();
            String s = bodyqname1.getPrefix();
            if(s != null && s.length() > 0)
            {
                stringbuilder.append(s);
                stringbuilder.append(":");
            }
            stringbuilder.append(bodyqname1.getLocalPart());
            stringbuilder.append("='");
            stringbuilder.append(escape((String)entry.getValue()));
        }

        stringbuilder.append(" ");
        stringbuilder.append("xmlns");
        stringbuilder.append("='");
        stringbuilder.append(bodyqname.getNamespaceURI());
        stringbuilder.append("'>");
        if(payload != null)
            stringbuilder.append(payload);
        stringbuilder.append("</body>");
        return stringbuilder.toString();
    }

    private String escape(String s)
    {
        return s.replace("'", "&apos;");
    }

    static ComposableBody fromStaticBody(StaticBody staticbody)
        throws BOSHException
    {
        String s = staticbody.toXML();
        Matcher matcher = BOSH_START.matcher(s);
        if(!matcher.find())
            throw new BOSHException((new StringBuilder()).append("Could not locate 'body' element in XML.  The raw XML did not match the pattern: ").append(BOSH_START).toString());
        String s1;
        if(">".equals(matcher.group(1)))
        {
            int i = matcher.end();
            int j = s.lastIndexOf("</");
            if(j < i)
                j = i;
            s1 = s.substring(i, j);
        } else
        {
            s1 = "";
        }
        return new ComposableBody(staticbody.getAttributes(), s1);
    }

    public Map getAttributes()
    {
        return Collections.unmodifiableMap(attrs);
    }

    public String getPayloadXML()
    {
        return payload;
    }

    public Builder rebuild()
    {
        return Builder.fromBody(this);
    }

    public String toXML()
    {
        String s = (String)computed.get();
        if(s == null)
        {
            s = computeXML();
            computed.set(s);
        }
        return s;
    }

    private static final Pattern BOSH_START = Pattern.compile("<body(?:[\t\n\r ][^>]*?)?(/>|>)", 64);
    private final Map attrs;
    private final AtomicReference computed;
    private final String payload;


}
