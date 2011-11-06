// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMPPError.java

package org.jivesoftware.smack.packet;

import java.util.*;

// Referenced classes of package org.jivesoftware.smack.packet:
//            PacketExtension

public class XMPPError
{
    private static class ErrorSpecification
    {

        private static Map errorSpecifications()
        {
            HashMap hashmap = new HashMap(22);
            hashmap.put(Condition.interna_server_error, new ErrorSpecification(Condition.interna_server_error, Type.WAIT, 500));
            hashmap.put(Condition.forbidden, new ErrorSpecification(Condition.forbidden, Type.AUTH, 403));
            hashmap.put(Condition.bad_request, new ErrorSpecification(Condition.bad_request, Type.MODIFY, 400));
            hashmap.put(Condition.item_not_found, new ErrorSpecification(Condition.item_not_found, Type.CANCEL, 404));
            hashmap.put(Condition.conflict, new ErrorSpecification(Condition.conflict, Type.CANCEL, 409));
            hashmap.put(Condition.feature_not_implemented, new ErrorSpecification(Condition.feature_not_implemented, Type.CANCEL, 501));
            hashmap.put(Condition.gone, new ErrorSpecification(Condition.gone, Type.MODIFY, 302));
            hashmap.put(Condition.jid_malformed, new ErrorSpecification(Condition.jid_malformed, Type.MODIFY, 400));
            hashmap.put(Condition.no_acceptable, new ErrorSpecification(Condition.no_acceptable, Type.MODIFY, 406));
            hashmap.put(Condition.not_allowed, new ErrorSpecification(Condition.not_allowed, Type.CANCEL, 405));
            hashmap.put(Condition.not_authorized, new ErrorSpecification(Condition.not_authorized, Type.AUTH, 401));
            hashmap.put(Condition.payment_required, new ErrorSpecification(Condition.payment_required, Type.AUTH, 402));
            hashmap.put(Condition.recipient_unavailable, new ErrorSpecification(Condition.recipient_unavailable, Type.WAIT, 404));
            hashmap.put(Condition.redirect, new ErrorSpecification(Condition.redirect, Type.MODIFY, 302));
            hashmap.put(Condition.registration_required, new ErrorSpecification(Condition.registration_required, Type.AUTH, 407));
            hashmap.put(Condition.remote_server_not_found, new ErrorSpecification(Condition.remote_server_not_found, Type.CANCEL, 404));
            hashmap.put(Condition.remote_server_timeout, new ErrorSpecification(Condition.remote_server_timeout, Type.WAIT, 504));
            hashmap.put(Condition.remote_server_error, new ErrorSpecification(Condition.remote_server_error, Type.CANCEL, 502));
            hashmap.put(Condition.resource_constraint, new ErrorSpecification(Condition.resource_constraint, Type.WAIT, 500));
            hashmap.put(Condition.service_unavailable, new ErrorSpecification(Condition.service_unavailable, Type.CANCEL, 503));
            hashmap.put(Condition.subscription_required, new ErrorSpecification(Condition.subscription_required, Type.AUTH, 407));
            hashmap.put(Condition.undefined_condition, new ErrorSpecification(Condition.undefined_condition, Type.WAIT, 500));
            hashmap.put(Condition.unexpected_request, new ErrorSpecification(Condition.unexpected_request, Type.WAIT, 400));
            hashmap.put(Condition.request_timeout, new ErrorSpecification(Condition.request_timeout, Type.CANCEL, 408));
            return hashmap;
        }

        protected static ErrorSpecification specFor(Condition condition1)
        {
            return (ErrorSpecification)instances.get(condition1);
        }

        protected int getCode()
        {
            return code;
        }

        protected Condition getCondition()
        {
            return condition;
        }

        protected Type getType()
        {
            return type;
        }

        private static Map instances = errorSpecifications();
        private int code;
        private Condition condition;
        private Type type;


        private ErrorSpecification(Condition condition1, Type type1, int i)
        {
            code = i;
            type = type1;
            condition = condition1;
        }
    }

    public static class Condition
    {

        public String toString()
        {
            return value;
        }

        public static final Condition bad_request = new Condition("bad-request");
        public static final Condition conflict = new Condition("conflict");
        public static final Condition feature_not_implemented = new Condition("feature-not-implemented");
        public static final Condition forbidden = new Condition("forbidden");
        public static final Condition gone = new Condition("gone");
        public static final Condition interna_server_error = new Condition("internal-server-error");
        public static final Condition item_not_found = new Condition("item-not-found");
        public static final Condition jid_malformed = new Condition("jid-malformed");
        public static final Condition no_acceptable = new Condition("not-acceptable");
        public static final Condition not_allowed = new Condition("not-allowed");
        public static final Condition not_authorized = new Condition("not-authorized");
        public static final Condition payment_required = new Condition("payment-required");
        public static final Condition recipient_unavailable = new Condition("recipient-unavailable");
        public static final Condition redirect = new Condition("redirect");
        public static final Condition registration_required = new Condition("registration-required");
        public static final Condition remote_server_error = new Condition("remote-server-error");
        public static final Condition remote_server_not_found = new Condition("remote-server-not-found");
        public static final Condition remote_server_timeout = new Condition("remote-server-timeout");
        public static final Condition request_timeout = new Condition("request-timeout");
        public static final Condition resource_constraint = new Condition("resource-constraint");
        public static final Condition service_unavailable = new Condition("service-unavailable");
        public static final Condition subscription_required = new Condition("subscription-required");
        public static final Condition undefined_condition = new Condition("undefined-condition");
        public static final Condition unexpected_request = new Condition("unexpected-request");
        private String value;



        public Condition(String s)
        {
            value = s;
        }
    }

    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(org/jivesoftware/smack/packet/XMPPError$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type AUTH;
        public static final Type CANCEL;
        public static final Type CONTINUE;
        public static final Type MODIFY;
        public static final Type WAIT;

        static 
        {
            WAIT = new Type("WAIT", 0);
            CANCEL = new Type("CANCEL", 1);
            MODIFY = new Type("MODIFY", 2);
            AUTH = new Type("AUTH", 3);
            CONTINUE = new Type("CONTINUE", 4);
            Type atype[] = new Type[5];
            atype[0] = WAIT;
            atype[1] = CANCEL;
            atype[2] = MODIFY;
            atype[3] = AUTH;
            atype[4] = CONTINUE;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    public XMPPError(int i)
    {
        applicationExtensions = null;
        code = i;
        message = null;
    }

    public XMPPError(int i, String s)
    {
        applicationExtensions = null;
        code = i;
        message = s;
    }

    public XMPPError(int i, Type type1, String s, String s1, List list)
    {
        applicationExtensions = null;
        code = i;
        type = type1;
        condition = s;
        message = s1;
        applicationExtensions = list;
    }

    public XMPPError(Condition condition1)
    {
        applicationExtensions = null;
        init(condition1);
        message = null;
    }

    public XMPPError(Condition condition1, String s)
    {
        applicationExtensions = null;
        init(condition1);
        message = s;
    }

    private void init(Condition condition1)
    {
        ErrorSpecification errorspecification = ErrorSpecification.specFor(condition1);
        condition = condition1.value;
        if(errorspecification != null)
        {
            type = errorspecification.getType();
            code = errorspecification.getCode();
        }
    }

    /**
     * @deprecated Method addExtension is deprecated
     */

    public void addExtension(PacketExtension packetextension)
    {
        this;
        JVM INSTR monitorenter ;
        if(applicationExtensions == null)
            applicationExtensions = new ArrayList();
        applicationExtensions.add(packetextension);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public int getCode()
    {
        return code;
    }

    public String getCondition()
    {
        return condition;
    }

    /**
     * @deprecated Method getExtension is deprecated
     */

    public PacketExtension getExtension(String s, String s1)
    {
        this;
        JVM INSTR monitorenter ;
        List list = applicationExtensions;
        if(list != null && s != null && s1 != null) goto _L2; else goto _L1
_L1:
        PacketExtension packetextension = null;
_L5:
        this;
        JVM INSTR monitorexit ;
        return packetextension;
_L2:
        Iterator iterator = applicationExtensions.iterator();
_L3:
        boolean flag;
        do
        {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_97;
            packetextension = (PacketExtension)iterator.next();
        } while(!s.equals(packetextension.getElementName()));
        flag = s1.equals(packetextension.getNamespace());
        if(flag)
            continue; /* Loop/switch isn't completed */
          goto _L3
        packetextension = null;
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getExtensions is deprecated
     */

    public List getExtensions()
    {
        this;
        JVM INSTR monitorenter ;
        if(applicationExtensions != null) goto _L2; else goto _L1
_L1:
        List list2 = Collections.emptyList();
        List list1 = list2;
_L4:
        this;
        JVM INSTR monitorexit ;
        return list1;
_L2:
        List list = Collections.unmodifiableList(applicationExtensions);
        list1 = list;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public String getMessage()
    {
        return message;
    }

    public Type getType()
    {
        return type;
    }

    /**
     * @deprecated Method setExtension is deprecated
     */

    public void setExtension(List list)
    {
        this;
        JVM INSTR monitorenter ;
        applicationExtensions = list;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        if(condition != null)
            stringbuilder.append(condition);
        stringbuilder.append("(").append(code).append(")");
        if(message != null)
            stringbuilder.append(" ").append(message);
        return stringbuilder.toString();
    }

    public String toXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<error code=\"").append(code).append("\"");
        if(type != null)
        {
            stringbuilder.append(" type=\"");
            stringbuilder.append(type.name());
            stringbuilder.append("\"");
        }
        stringbuilder.append(">");
        if(condition != null)
        {
            stringbuilder.append("<").append(condition);
            stringbuilder.append(" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\"/>");
        }
        if(message != null)
        {
            stringbuilder.append("<text xml:lang=\"en\" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\">");
            stringbuilder.append(message);
            stringbuilder.append("</text>");
        }
        for(Iterator iterator = getExtensions().iterator(); iterator.hasNext(); stringbuilder.append(((PacketExtension)iterator.next()).toXML()));
        stringbuilder.append("</error>");
        return stringbuilder.toString();
    }

    private List applicationExtensions;
    private int code;
    private String condition;
    private String message;
    private Type type;
}
