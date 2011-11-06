// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TerminalBindingCondition.java

package com.kenai.jbosh;

import java.util.HashMap;
import java.util.Map;

final class TerminalBindingCondition
{

    private TerminalBindingCondition(String s, String s1)
    {
        cond = s;
        msg = s1;
    }

    private static TerminalBindingCondition create(String s, String s1)
    {
        return createWithCode(s, s1, null);
    }

    private static TerminalBindingCondition createWithCode(String s, String s1, Integer integer)
    {
        if(s == null)
            throw new IllegalArgumentException("condition may not be null");
        if(s1 == null)
            throw new IllegalArgumentException("message may not be null");
        if(COND_TO_INSTANCE.get(s) != null)
            throw new IllegalStateException((new StringBuilder()).append("Multiple definitions of condition: ").append(s).toString());
        TerminalBindingCondition terminalbindingcondition = new TerminalBindingCondition(s, s1);
        COND_TO_INSTANCE.put(s, terminalbindingcondition);
        if(integer != null)
        {
            if(CODE_TO_INSTANCE.get(integer) != null)
                throw new IllegalStateException((new StringBuilder()).append("Multiple definitions of code: ").append(integer).toString());
            CODE_TO_INSTANCE.put(integer, terminalbindingcondition);
        }
        return terminalbindingcondition;
    }

    static TerminalBindingCondition forHTTPResponseCode(int i)
    {
        return (TerminalBindingCondition)CODE_TO_INSTANCE.get(Integer.valueOf(i));
    }

    static TerminalBindingCondition forString(String s)
    {
        return (TerminalBindingCondition)COND_TO_INSTANCE.get(s);
    }

    String getCondition()
    {
        return cond;
    }

    String getMessage()
    {
        return msg;
    }

    static final TerminalBindingCondition BAD_REQUEST = createWithCode("bad-request", "The format of an HTTP header or binding element received from the client is unacceptable (e.g., syntax error).", Integer.valueOf(400));
    private static final Map CODE_TO_INSTANCE = new HashMap();
    private static final Map COND_TO_INSTANCE = new HashMap();
    static final TerminalBindingCondition HOST_GONE = create("host-gone", "The target domain specified in the 'to' attribute or the target host or port specified in the 'route' attribute is no longer serviced by the connection manager.");
    static final TerminalBindingCondition HOST_UNKNOWN = create("host-unknown", "The target domain specified in the 'to' attribute or the target host or port specified in the 'route' attribute is unknown to the connection manager.");
    static final TerminalBindingCondition IMPROPER_ADDRESSING = create("improper-addressing", "The initialization element lacks a 'to' or 'route' attribute (or the attribute has no value) but the connection manager requires one.");
    static final TerminalBindingCondition INTERNAL_SERVER_ERROR = create("internal-server-error", "The connection manager has experienced an internal error that prevents it from servicing the request.");
    static final TerminalBindingCondition ITEM_NOT_FOUND = createWithCode("item-not-found", "(1) 'sid' is not valid, (2) 'stream' is not valid, (3) 'rid' is larger than the upper limit of the expected window, (4) connection manager is unable to resend response, (5) 'key' sequence is invalid.", Integer.valueOf(404));
    static final TerminalBindingCondition OTHER_REQUEST = create("other-request", "Another request being processed at the same time as this request caused the session to terminate.");
    static final TerminalBindingCondition POLICY_VIOLATION = createWithCode("policy-violation", "The client has broken the session rules (polling too frequently, requesting too frequently, sending too many simultaneous requests).", Integer.valueOf(403));
    static final TerminalBindingCondition REMOTE_CONNECTION_FAILED = create("remote-connection-failed", "The connection manager was unable to connect to, or unable to connect securely to, or has lost its connection to, the server.");
    static final TerminalBindingCondition REMOTE_STREAM_ERROR = create("remote-stream-error", "Encapsulated transport protocol error.");
    static final TerminalBindingCondition SEE_OTHER_URI = create("see-other-uri", "The connection manager does not operate at this URI (e.g., the connection manager accepts only SSL or TLS connections at some https: URI rather than the http: URI requested by the client).");
    static final TerminalBindingCondition SYSTEM_SHUTDOWN = create("system-shutdown", "The connection manager is being shut down. All active HTTP sessions are being terminated. No new sessions can be created.");
    static final TerminalBindingCondition UNDEFINED_CONDITION = create("undefined-condition", "Unknown or undefined error condition.");
    private final String cond;
    private final String msg;

}
