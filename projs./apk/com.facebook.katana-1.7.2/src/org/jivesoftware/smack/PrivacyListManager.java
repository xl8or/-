// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivacyListManager.java

package org.jivesoftware.smack;

import java.util.*;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.IQTypeFilter;
import org.jivesoftware.smack.filter.PacketExtensionFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Privacy;

// Referenced classes of package org.jivesoftware.smack:
//            Connection, XMPPException, SmackConfiguration, PacketCollector, 
//            PrivacyList, PrivacyListListener, ConnectionCreationListener, ConnectionListener, 
//            PacketListener

public class PrivacyListManager
{

    private PrivacyListManager(Connection connection1)
    {
        listeners = new ArrayList();
        PacketFilter apacketfilter[] = new PacketFilter[2];
        apacketfilter[0] = new IQTypeFilter(org.jivesoftware.smack.packet.IQ.Type.SET);
        apacketfilter[1] = new PacketExtensionFilter("query", "jabber:iq:privacy");
        packetFilter = new AndFilter(apacketfilter);
        connection = connection1;
        init();
    }


    public static PrivacyListManager getInstanceFor(Connection connection1)
    {
        return (PrivacyListManager)instances.get(connection1);
    }

    private List getPrivacyListItems(String s)
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setPrivacyList(s, new ArrayList());
        return getRequest(privacy).getPrivacyList(s);
    }

    private Privacy getPrivacyWithListNames()
        throws XMPPException
    {
        return getRequest(new Privacy());
    }

    private Privacy getRequest(Privacy privacy)
        throws XMPPException
    {
        privacy.setType(org.jivesoftware.smack.packet.IQ.Type.GET);
        privacy.setFrom(getUser());
        PacketIDFilter packetidfilter = new PacketIDFilter(privacy.getPacketID());
        PacketCollector packetcollector = connection.createPacketCollector(packetidfilter);
        connection.sendPacket(privacy);
        Privacy privacy1 = (Privacy)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(privacy1 == null)
            throw new XMPPException("No response from server.");
        if(privacy1.getError() != null)
            throw new XMPPException(privacy1.getError());
        else
            return privacy1;
    }

    private String getUser()
    {
        return connection.getUser();
    }

    private void init()
    {
        instances.put(connection, this);
        connection.addConnectionListener(new ConnectionListener() {

            public void connectionClosed()
            {
                PrivacyListManager.instances.remove(connection);
            }

            public void connectionClosedOnError(Exception exception)
            {
            }

            public void reconnectingIn(int i)
            {
            }

            public void reconnectionFailed(Exception exception)
            {
            }

            public void reconnectionSuccessful()
            {
            }

            final PrivacyListManager this$0;

            
            {
                this$0 = PrivacyListManager.this;
                super();
            }
        }
);
        connection.addPacketListener(new PacketListener() {

            public void processPacket(Packet packet)
            {
                if(packet != null && packet.getError() == null) goto _L2; else goto _L1
_L1:
                return;
_L2:
                Privacy privacy = (Privacy)packet;
                List list = listeners;
                list;
                JVM INSTR monitorenter ;
                Iterator iterator = listeners.iterator();
_L7:
                PrivacyListListener privacylistlistener;
                Iterator iterator1;
                if(!iterator.hasNext())
                    break; /* Loop/switch isn't completed */
                privacylistlistener = (PrivacyListListener)iterator.next();
                iterator1 = privacy.getItemLists().entrySet().iterator();
_L5:
                String s;
                List list1;
                do
                {
                    if(!iterator1.hasNext())
                        break; /* Loop/switch isn't completed */
                    java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
                    s = (String)entry.getKey();
                    list1 = (List)entry.getValue();
                    if(!list1.isEmpty())
                        break;
                    privacylistlistener.updatedPrivacyList(s);
                } while(true);
                  goto _L3
                Exception exception;
                exception;
                throw exception;
_L3:
                privacylistlistener.setPrivacyList(s, list1);
                if(true) goto _L5; else goto _L4
_L4:
                if(true) goto _L7; else goto _L6
_L6:
                list;
                JVM INSTR monitorexit ;
                IQ iq = new IQ() {

                    public String getChildElementXML()
                    {
                        return "";
                    }

                    final _cls3 this$1;

                    
                    {
                        this$1 = _cls3.this;
                        super();
                    }
                }
;
                iq.setType(org.jivesoftware.smack.packet.IQ.Type.RESULT);
                iq.setFrom(packet.getFrom());
                iq.setPacketID(packet.getPacketID());
                connection.sendPacket(iq);
                if(true) goto _L1; else goto _L8
_L8:
            }

            final PrivacyListManager this$0;

            
            {
                this$0 = PrivacyListManager.this;
                super();
            }
        }
, packetFilter);
    }

    private Packet setRequest(Privacy privacy)
        throws XMPPException
    {
        privacy.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
        privacy.setFrom(getUser());
        PacketIDFilter packetidfilter = new PacketIDFilter(privacy.getPacketID());
        PacketCollector packetcollector = connection.createPacketCollector(packetidfilter);
        connection.sendPacket(privacy);
        Packet packet = packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(packet == null)
            throw new XMPPException("No response from server.");
        if(packet.getError() != null)
            throw new XMPPException(packet.getError());
        else
            return packet;
    }

    public void addListener(PrivacyListListener privacylistlistener)
    {
        List list = listeners;
        list;
        JVM INSTR monitorenter ;
        listeners.add(privacylistlistener);
        return;
    }

    public void createPrivacyList(String s, List list)
        throws XMPPException
    {
        updatePrivacyList(s, list);
    }

    public void declineActiveList()
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setDeclineActiveList(true);
        setRequest(privacy);
    }

    public void declineDefaultList()
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setDeclineDefaultList(true);
        setRequest(privacy);
    }

    public void deletePrivacyList(String s)
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setPrivacyList(s, new ArrayList());
        setRequest(privacy);
    }

    public PrivacyList getActiveList()
        throws XMPPException
    {
        Privacy privacy = getPrivacyWithListNames();
        String s = privacy.getActiveName();
        boolean flag;
        if(privacy.getActiveName() != null && privacy.getDefaultName() != null && privacy.getActiveName().equals(privacy.getDefaultName()))
            flag = true;
        else
            flag = false;
        return new PrivacyList(true, flag, s, getPrivacyListItems(s));
    }

    public PrivacyList getDefaultList()
        throws XMPPException
    {
        Privacy privacy = getPrivacyWithListNames();
        String s = privacy.getDefaultName();
        boolean flag;
        if(privacy.getActiveName() != null && privacy.getDefaultName() != null && privacy.getActiveName().equals(privacy.getDefaultName()))
            flag = true;
        else
            flag = false;
        return new PrivacyList(flag, true, s, getPrivacyListItems(s));
    }

    public PrivacyList getPrivacyList(String s)
        throws XMPPException
    {
        return new PrivacyList(false, false, s, getPrivacyListItems(s));
    }

    public PrivacyList[] getPrivacyLists()
        throws XMPPException
    {
        Privacy privacy = getPrivacyWithListNames();
        Set set = privacy.getPrivacyListNames();
        PrivacyList aprivacylist[] = new PrivacyList[set.size()];
        int i = 0;
        for(Iterator iterator = set.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            aprivacylist[i] = new PrivacyList(s.equals(privacy.getActiveName()), s.equals(privacy.getDefaultName()), s, getPrivacyListItems(s));
            i++;
        }

        return aprivacylist;
    }

    public void setActiveListName(String s)
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setActiveName(s);
        setRequest(privacy);
    }

    public void setDefaultListName(String s)
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setDefaultName(s);
        setRequest(privacy);
    }

    public void updatePrivacyList(String s, List list)
        throws XMPPException
    {
        Privacy privacy = new Privacy();
        privacy.setPrivacyList(s, list);
        setRequest(privacy);
    }

    private static Map instances = new Hashtable();
    private Connection connection;
    private final List listeners;
    PacketFilter packetFilter;

    static 
    {
        Connection.addConnectionCreationListener(new ConnectionCreationListener() {

            public void connectionCreated(Connection connection1)
            {
                new PrivacyListManager(connection1);
            }

        }
);
    }



}
