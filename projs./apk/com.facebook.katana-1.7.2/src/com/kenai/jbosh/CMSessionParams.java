// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CMSessionParams.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            BOSHException, Attributes, AbstractBody, AttrAck, 
//            AttrSessionID, AttrWait, AttrVersion, AttrPolling, 
//            AttrInactivity, AttrRequests, AttrHold, AttrAccept, 
//            AttrMaxPause, AttrCharsets, BodyQName

final class CMSessionParams
{

    private CMSessionParams(AttrSessionID attrsessionid, AttrWait attrwait, AttrVersion attrversion, AttrPolling attrpolling, AttrInactivity attrinactivity, AttrRequests attrrequests, AttrHold attrhold, 
            AttrAccept attraccept, AttrMaxPause attrmaxpause, AttrAck attrack, AttrCharsets attrcharsets, boolean flag)
    {
        sid = attrsessionid;
        wait = attrwait;
        ver = attrversion;
        polling = attrpolling;
        inactivity = attrinactivity;
        requests = attrrequests;
        hold = attrhold;
        accept = attraccept;
        maxPause = attrmaxpause;
        ack = attrack;
        charsets = attrcharsets;
        ackingRequests = flag;
    }

    static CMSessionParams fromSessionInit(AbstractBody abstractbody, AbstractBody abstractbody1)
        throws BOSHException
    {
        AttrAck attrack = AttrAck.createFromString(abstractbody1.getAttribute(Attributes.ACK));
        String s = abstractbody.getAttribute(Attributes.RID);
        boolean flag;
        if(attrack != null && ((String)attrack.getValue()).equals(s))
            flag = true;
        else
            flag = false;
        return new CMSessionParams(AttrSessionID.createFromString(getRequiredAttribute(abstractbody1, Attributes.SID)), AttrWait.createFromString(getRequiredAttribute(abstractbody1, Attributes.WAIT)), AttrVersion.createFromString(abstractbody1.getAttribute(Attributes.VER)), AttrPolling.createFromString(abstractbody1.getAttribute(Attributes.POLLING)), AttrInactivity.createFromString(abstractbody1.getAttribute(Attributes.INACTIVITY)), AttrRequests.createFromString(abstractbody1.getAttribute(Attributes.REQUESTS)), AttrHold.createFromString(abstractbody1.getAttribute(Attributes.HOLD)), AttrAccept.createFromString(abstractbody1.getAttribute(Attributes.ACCEPT)), AttrMaxPause.createFromString(abstractbody1.getAttribute(Attributes.MAXPAUSE)), attrack, AttrCharsets.createFromString(abstractbody1.getAttribute(Attributes.CHARSETS)), flag);
    }

    private static String getRequiredAttribute(AbstractBody abstractbody, BodyQName bodyqname)
        throws BOSHException
    {
        String s = abstractbody.getAttribute(bodyqname);
        if(s == null)
            throw new BOSHException((new StringBuilder()).append("Connection Manager session creation response did not include required '").append(bodyqname.getLocalPart()).append("' attribute").toString());
        else
            return s;
    }

    AttrAccept getAccept()
    {
        return accept;
    }

    AttrAck getAck()
    {
        return ack;
    }

    AttrCharsets getCharsets()
    {
        return charsets;
    }

    AttrHold getHold()
    {
        return hold;
    }

    AttrInactivity getInactivityPeriod()
    {
        return inactivity;
    }

    AttrMaxPause getMaxPause()
    {
        return maxPause;
    }

    AttrPolling getPollingInterval()
    {
        return polling;
    }

    AttrRequests getRequests()
    {
        return requests;
    }

    AttrSessionID getSessionID()
    {
        return sid;
    }

    AttrVersion getVersion()
    {
        return ver;
    }

    AttrWait getWait()
    {
        return wait;
    }

    boolean isAckingRequests()
    {
        return ackingRequests;
    }

    private final AttrAccept accept;
    private final AttrAck ack;
    private final boolean ackingRequests;
    private final AttrCharsets charsets;
    private final AttrHold hold;
    private final AttrInactivity inactivity;
    private final AttrMaxPause maxPause;
    private final AttrPolling polling;
    private final AttrRequests requests;
    private final AttrSessionID sid;
    private final AttrVersion ver;
    private final AttrWait wait;
}
