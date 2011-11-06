// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Roster.java

package org.jivesoftware.smack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack:
//            Connection, ConnectionConfiguration, RosterListener, RosterEntry, 
//            RosterGroup, XMPPException, SmackConfiguration, PacketCollector, 
//            RosterStorage, PacketListener, ConnectionListener

public class Roster
{
    private class RosterPacketListener
        implements PacketListener
    {

        public void processPacket(Packet packet)
        {
            ArrayList arraylist = new ArrayList();
            ArrayList arraylist1 = new ArrayList();
            ArrayList arraylist2 = new ArrayList();
            RosterPacket rosterpacket = (RosterPacket)packet;
            ArrayList arraylist3 = new ArrayList();
            for(Iterator iterator = rosterpacket.getRosterItems().iterator(); iterator.hasNext(); arraylist3.add((org.jivesoftware.smack.packet.RosterPacket.Item)iterator.next()));
            String s;
            if(rosterpacket.getVersion() == null)
            {
                persistentStorage = null;
                s = null;
            } else
            {
                s = rosterpacket.getVersion();
            }
            if(persistentStorage != null && !rosterInitialized)
            {
                for(Iterator iterator3 = persistentStorage.getEntries().iterator(); iterator3.hasNext(); arraylist3.add((org.jivesoftware.smack.packet.RosterPacket.Item)iterator3.next()));
            }
            org.jivesoftware.smack.packet.RosterPacket.Item item1;
            for(Iterator iterator1 = arraylist3.iterator(); iterator1.hasNext(); insertRosterItem(item1, arraylist, arraylist1, arraylist2))
                item1 = (org.jivesoftware.smack.packet.RosterPacket.Item)iterator1.next();

            if(persistentStorage != null)
            {
                for(Iterator iterator2 = rosterpacket.getRosterItems().iterator(); iterator2.hasNext();)
                {
                    org.jivesoftware.smack.packet.RosterPacket.Item item = (org.jivesoftware.smack.packet.RosterPacket.Item)iterator2.next();
                    if(item.getItemType().equals(org.jivesoftware.smack.packet.RosterPacket.ItemType.remove))
                        persistentStorage.removeEntry(item.getUser());
                    else
                        persistentStorage.addEntry(item, s);
                }

            }
            synchronized(Roster.this)
            {
                rosterInitialized = true;
                notifyAll();
            }
            fireRosterChangedEvent(arraylist, arraylist1, arraylist2);
            return;
            exception;
            roster;
            JVM INSTR monitorexit ;
            throw exception;
        }

        final Roster this$0;

        private RosterPacketListener()
        {
            this$0 = Roster.this;
            super();
        }

    }

    private class RosterResultListener
        implements PacketListener
    {

        public void processPacket(Packet packet)
        {
            if(packet instanceof IQ)
            {
                IQ iq = (IQ)packet;
                if(iq.getType().equals(org.jivesoftware.smack.packet.IQ.Type.RESULT) && iq.getExtensions().isEmpty())
                {
                    ArrayList arraylist = new ArrayList();
                    ArrayList arraylist1 = new ArrayList();
                    ArrayList arraylist2 = new ArrayList();
                    if(persistentStorage != null)
                    {
                        org.jivesoftware.smack.packet.RosterPacket.Item item;
                        for(Iterator iterator = persistentStorage.getEntries().iterator(); iterator.hasNext(); insertRosterItem(item, arraylist, arraylist1, arraylist2))
                            item = (org.jivesoftware.smack.packet.RosterPacket.Item)iterator.next();

                        synchronized(Roster.this)
                        {
                            rosterInitialized = true;
                            notifyAll();
                        }
                        fireRosterChangedEvent(arraylist, arraylist1, arraylist2);
                    }
                }
            }
            connection.removePacketListener(this);
            return;
            exception;
            roster;
            JVM INSTR monitorexit ;
            throw exception;
        }

        final Roster this$0;

        private RosterResultListener()
        {
            this$0 = Roster.this;
            super();
        }

    }

    private class PresencePacketListener
        implements PacketListener
    {

        public void processPacket(Packet packet)
        {
            Presence presence;
            String s;
            String s1;
            presence = (Presence)packet;
            s = presence.getFrom();
            s1 = getPresenceMapKey(s);
            if(presence.getType() != org.jivesoftware.smack.packet.Presence.Type.available) goto _L2; else goto _L1
_L1:
            Object obj2;
            if(presenceMap.get(s1) == null)
            {
                obj2 = new ConcurrentHashMap();
                presenceMap.put(s1, obj2);
            } else
            {
                obj2 = (Map)presenceMap.get(s1);
            }
            ((Map) (obj2)).remove("");
            ((Map) (obj2)).put(StringUtils.parseResource(s), presence);
            if((RosterEntry)entries.get(s1) != null)
                fireRosterPresenceEvent(presence);
_L9:
            return;
_L2:
            if(presence.getType() != org.jivesoftware.smack.packet.Presence.Type.unavailable) goto _L4; else goto _L3
_L3:
            if(!"".equals(StringUtils.parseResource(s))) goto _L6; else goto _L5
_L5:
            Object obj1;
            if(presenceMap.get(s1) == null)
            {
                obj1 = new ConcurrentHashMap();
                presenceMap.put(s1, obj1);
            } else
            {
                obj1 = (Map)presenceMap.get(s1);
            }
            ((Map) (obj1)).put("", presence);
_L7:
            if((RosterEntry)entries.get(s1) != null)
                fireRosterPresenceEvent(presence);
            continue; /* Loop/switch isn't completed */
_L6:
            if(presenceMap.get(s1) != null)
                ((Map)presenceMap.get(s1)).put(StringUtils.parseResource(s), presence);
            if(true) goto _L7; else goto _L4
_L4:
            if(presence.getType() == org.jivesoftware.smack.packet.Presence.Type.subscribe)
            {
                if(subscriptionMode == SubscriptionMode.accept_all)
                {
                    Presence presence2 = new Presence(org.jivesoftware.smack.packet.Presence.Type.subscribed);
                    presence2.setTo(presence.getFrom());
                    connection.sendPacket(presence2);
                } else
                if(subscriptionMode == SubscriptionMode.reject_all)
                {
                    Presence presence3 = new Presence(org.jivesoftware.smack.packet.Presence.Type.unsubscribed);
                    presence3.setTo(presence.getFrom());
                    connection.sendPacket(presence3);
                }
            } else
            if(presence.getType() == org.jivesoftware.smack.packet.Presence.Type.unsubscribe)
            {
                if(subscriptionMode != SubscriptionMode.manual)
                {
                    Presence presence1 = new Presence(org.jivesoftware.smack.packet.Presence.Type.unsubscribed);
                    presence1.setTo(presence.getFrom());
                    connection.sendPacket(presence1);
                }
            } else
            if(presence.getType() == org.jivesoftware.smack.packet.Presence.Type.error && "".equals(StringUtils.parseResource(s)))
            {
                Object obj;
                if(!presenceMap.containsKey(s1))
                {
                    obj = new ConcurrentHashMap();
                    presenceMap.put(s1, obj);
                } else
                {
                    obj = (Map)presenceMap.get(s1);
                    ((Map) (obj)).clear();
                }
                ((Map) (obj)).put("", presence);
                if((RosterEntry)entries.get(s1) != null)
                    fireRosterPresenceEvent(presence);
            }
            if(true) goto _L9; else goto _L8
_L8:
        }

        final Roster this$0;

        private PresencePacketListener()
        {
            this$0 = Roster.this;
            super();
        }

    }

    public static final class SubscriptionMode extends Enum
    {

        public static SubscriptionMode valueOf(String s)
        {
            return (SubscriptionMode)Enum.valueOf(org/jivesoftware/smack/Roster$SubscriptionMode, s);
        }

        public static SubscriptionMode[] values()
        {
            return (SubscriptionMode[])$VALUES.clone();
        }

        private static final SubscriptionMode $VALUES[];
        public static final SubscriptionMode accept_all;
        public static final SubscriptionMode manual;
        public static final SubscriptionMode reject_all;

        static 
        {
            accept_all = new SubscriptionMode("accept_all", 0);
            reject_all = new SubscriptionMode("reject_all", 1);
            manual = new SubscriptionMode("manual", 2);
            SubscriptionMode asubscriptionmode[] = new SubscriptionMode[3];
            asubscriptionmode[0] = accept_all;
            asubscriptionmode[1] = reject_all;
            asubscriptionmode[2] = manual;
            $VALUES = asubscriptionmode;
        }

        private SubscriptionMode(String s, int i)
        {
            super(s, i);
        }
    }


    Roster(Connection connection1)
    {
        rosterInitialized = false;
        subscriptionMode = getDefaultSubscriptionMode();
        connection = connection1;
        if(!connection1.getConfiguration().isRosterVersioningAvailable())
            persistentStorage = null;
        groups = new ConcurrentHashMap();
        unfiledEntries = new CopyOnWriteArrayList();
        entries = new ConcurrentHashMap();
        rosterListeners = new CopyOnWriteArrayList();
        presenceMap = new ConcurrentHashMap();
        PacketTypeFilter packettypefilter = new PacketTypeFilter(org/jivesoftware/smack/packet/RosterPacket);
        connection1.addPacketListener(new RosterPacketListener(), packettypefilter);
        PacketTypeFilter packettypefilter1 = new PacketTypeFilter(org/jivesoftware/smack/packet/Presence);
        presencePacketListener = new PresencePacketListener();
        connection1.addPacketListener(presencePacketListener, packettypefilter1);
        connection1.addConnectionListener(new ConnectionListener() {

            public void connectionClosed()
            {
                setOfflinePresences();
            }

            public void connectionClosedOnError(Exception exception)
            {
                setOfflinePresences();
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

            final Roster this$0;

            
            {
                this$0 = Roster.this;
                super();
            }
        }
);
    }

    Roster(Connection connection1, RosterStorage rosterstorage)
    {
        this(connection1);
        persistentStorage = rosterstorage;
    }

    private void fireRosterChangedEvent(Collection collection, Collection collection1, Collection collection2)
    {
        Iterator iterator = rosterListeners.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RosterListener rosterlistener = (RosterListener)iterator.next();
            if(!collection.isEmpty())
                rosterlistener.entriesAdded(collection);
            if(!collection1.isEmpty())
                rosterlistener.entriesUpdated(collection1);
            if(!collection2.isEmpty())
                rosterlistener.entriesDeleted(collection2);
        } while(true);
    }

    private void fireRosterPresenceEvent(Presence presence)
    {
        for(Iterator iterator = rosterListeners.iterator(); iterator.hasNext(); ((RosterListener)iterator.next()).presenceChanged(presence));
    }

    public static SubscriptionMode getDefaultSubscriptionMode()
    {
        return defaultSubscriptionMode;
    }

    private String getPresenceMapKey(String s)
    {
        String s2;
        if(s == null)
        {
            s2 = null;
        } else
        {
            String s1;
            if(!contains(s))
                s1 = StringUtils.parseBareAddress(s);
            else
                s1 = s;
            s2 = s1.toLowerCase();
        }
        return s2;
    }

    private void insertRosterItem(org.jivesoftware.smack.packet.RosterPacket.Item item, Collection collection, Collection collection1, Collection collection2)
    {
        RosterEntry rosterentry = new RosterEntry(item.getUser(), item.getName(), item.getItemType(), item.getItemStatus(), this, connection);
        if(!org.jivesoftware.smack.packet.RosterPacket.ItemType.remove.equals(item.getItemType())) goto _L2; else goto _L1
_L1:
        if(entries.containsKey(item.getUser()))
            entries.remove(item.getUser());
        if(unfiledEntries.contains(rosterentry))
            unfiledEntries.remove(rosterentry);
        String s2 = (new StringBuilder()).append(StringUtils.parseName(item.getUser())).append("@").append(StringUtils.parseServer(item.getUser())).toString();
        presenceMap.remove(s2);
        if(collection2 != null)
            collection2.add(item.getUser());
_L8:
        ArrayList arraylist;
        arraylist = new ArrayList();
        Iterator iterator = getGroups().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RosterGroup rostergroup3 = (RosterGroup)iterator.next();
            if(rostergroup3.contains(rosterentry))
                arraylist.add(rostergroup3.getName());
        } while(true);
        break; /* Loop/switch isn't completed */
_L2:
        if(entries.containsKey(item.getUser())) goto _L4; else goto _L3
_L3:
        entries.put(item.getUser(), rosterentry);
        if(collection != null)
            collection.add(item.getUser());
_L6:
        if(item.getGroupNames().isEmpty())
            break; /* Loop/switch isn't completed */
        unfiledEntries.remove(rosterentry);
        continue; /* Loop/switch isn't completed */
_L4:
        entries.put(item.getUser(), rosterentry);
        if(collection1 != null)
            collection1.add(item.getUser());
        if(true) goto _L6; else goto _L5
_L5:
        if(!unfiledEntries.contains(rosterentry))
            unfiledEntries.add(rosterentry);
        if(true) goto _L8; else goto _L7
_L7:
        if(!org.jivesoftware.smack.packet.RosterPacket.ItemType.remove.equals(item.getItemType()))
        {
            ArrayList arraylist1 = new ArrayList();
            RosterGroup rostergroup2;
            for(Iterator iterator1 = item.getGroupNames().iterator(); iterator1.hasNext(); rostergroup2.addEntryLocal(rosterentry))
            {
                String s1 = (String)iterator1.next();
                arraylist1.add(s1);
                rostergroup2 = getGroup(s1);
                if(rostergroup2 == null)
                {
                    rostergroup2 = createGroup(s1);
                    groups.put(s1, rostergroup2);
                }
            }

            for(Iterator iterator2 = arraylist1.iterator(); iterator2.hasNext(); arraylist.remove((String)iterator2.next()));
        }
        Iterator iterator3 = arraylist.iterator();
        do
        {
            if(!iterator3.hasNext())
                break;
            String s = (String)iterator3.next();
            RosterGroup rostergroup1 = getGroup(s);
            rostergroup1.removeEntryLocal(rosterentry);
            if(rostergroup1.getEntryCount() == 0)
                groups.remove(s);
        } while(true);
        Iterator iterator4 = getGroups().iterator();
        do
        {
            if(!iterator4.hasNext())
                break;
            RosterGroup rostergroup = (RosterGroup)iterator4.next();
            if(rostergroup.getEntryCount() == 0)
                groups.remove(rostergroup.getName());
        } while(true);
        return;
    }

    private void insertRosterItems(List list)
    {
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        ArrayList arraylist2 = new ArrayList();
        for(Iterator iterator = list.iterator(); iterator.hasNext(); insertRosterItem((org.jivesoftware.smack.packet.RosterPacket.Item)iterator.next(), arraylist, arraylist1, arraylist2));
        fireRosterChangedEvent(arraylist, arraylist1, arraylist2);
    }

    public static void setDefaultSubscriptionMode(SubscriptionMode subscriptionmode)
    {
        defaultSubscriptionMode = subscriptionmode;
    }

    private void setOfflinePresences()
    {
        Iterator iterator = presenceMap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            Map map = (Map)presenceMap.get(s);
            if(map != null)
            {
                Iterator iterator1 = map.keySet().iterator();
                while(iterator1.hasNext()) 
                {
                    String s1 = (String)iterator1.next();
                    Presence presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
                    presence.setFrom((new StringBuilder()).append(s).append("/").append(s1).toString());
                    presencePacketListener.processPacket(presence);
                }
            }
        } while(true);
    }

    public void addRosterListener(RosterListener rosterlistener)
    {
        if(!rosterListeners.contains(rosterlistener))
            rosterListeners.add(rosterlistener);
    }

    void cleanup()
    {
        rosterListeners.clear();
    }

    public boolean contains(String s)
    {
        boolean flag;
        if(getEntry(s) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void createEntry(String s, String s1, String as[])
        throws XMPPException
    {
        RosterPacket rosterpacket = new RosterPacket();
        rosterpacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
        org.jivesoftware.smack.packet.RosterPacket.Item item = new org.jivesoftware.smack.packet.RosterPacket.Item(s, s1);
        if(as != null)
        {
            int i = as.length;
            for(int j = 0; j < i; j++)
            {
                String s2 = as[j];
                if(s2 != null && s2.trim().length() > 0)
                    item.addGroupName(s2);
            }

        }
        rosterpacket.addRosterItem(item);
        PacketCollector packetcollector = connection.createPacketCollector(new PacketIDFilter(rosterpacket.getPacketID()));
        connection.sendPacket(rosterpacket);
        IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        packetcollector.cancel();
        if(iq == null)
            throw new XMPPException("No response from the server.");
        if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
        {
            throw new XMPPException(iq.getError());
        } else
        {
            Presence presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.subscribe);
            presence.setTo(s);
            connection.sendPacket(presence);
            return;
        }
    }

    public RosterGroup createGroup(String s)
    {
        if(groups.containsKey(s))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Group with name ").append(s).append(" alread exists.").toString());
        } else
        {
            RosterGroup rostergroup = new RosterGroup(s, connection);
            groups.put(s, rostergroup);
            return rostergroup;
        }
    }

    public Collection getEntries()
    {
        HashSet hashset = new HashSet();
        for(Iterator iterator = getGroups().iterator(); iterator.hasNext(); hashset.addAll(((RosterGroup)iterator.next()).getEntries()));
        hashset.addAll(unfiledEntries);
        return Collections.unmodifiableCollection(hashset);
    }

    public RosterEntry getEntry(String s)
    {
        RosterEntry rosterentry;
        if(s == null)
            rosterentry = null;
        else
            rosterentry = (RosterEntry)entries.get(s.toLowerCase());
        return rosterentry;
    }

    public int getEntryCount()
    {
        return getEntries().size();
    }

    public RosterGroup getGroup(String s)
    {
        return (RosterGroup)groups.get(s);
    }

    public int getGroupCount()
    {
        return groups.size();
    }

    public Collection getGroups()
    {
        return Collections.unmodifiableCollection(groups.values());
    }

    public Presence getPresence(String s)
    {
        Map map;
        String s1 = getPresenceMapKey(StringUtils.parseBareAddress(s));
        map = (Map)presenceMap.get(s1);
        if(map != null) goto _L2; else goto _L1
_L1:
        Presence presence;
        presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
        presence.setFrom(s);
_L9:
        return presence;
_L2:
        Iterator iterator;
        Presence presence1;
        iterator = map.keySet().iterator();
        presence1 = null;
_L4:
        Presence presence2;
        do
        {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_192;
            presence2 = (Presence)map.get((String)iterator.next());
        } while(!presence2.isAvailable());
        if(presence1 != null && presence2.getPriority() <= presence1.getPriority())
            break; /* Loop/switch isn't completed */
_L7:
        presence1 = presence2;
        if(true) goto _L4; else goto _L3
_L3:
        if(presence2.getPriority() != presence1.getPriority()) goto _L6; else goto _L5
_L5:
        org.jivesoftware.smack.packet.Presence.Mode mode;
        org.jivesoftware.smack.packet.Presence.Mode mode1;
        mode = presence2.getMode();
        if(mode == null)
            mode = org.jivesoftware.smack.packet.Presence.Mode.available;
        mode1 = presence1.getMode();
        if(mode1 == null)
            mode1 = org.jivesoftware.smack.packet.Presence.Mode.available;
        if(mode.compareTo(mode1) < 0) goto _L7; else goto _L6
_L6:
        presence2 = presence1;
          goto _L7
        if(presence1 == null)
        {
            presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
            presence.setFrom(s);
        } else
        {
            presence = presence1;
        }
        if(true) goto _L9; else goto _L8
_L8:
    }

    public Presence getPresenceResource(String s)
    {
        String s1 = getPresenceMapKey(s);
        String s2 = StringUtils.parseResource(s);
        Map map = (Map)presenceMap.get(s1);
        Presence presence;
        if(map == null)
        {
            presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
            presence.setFrom(s);
        } else
        {
            Presence presence1 = (Presence)map.get(s2);
            if(presence1 == null)
            {
                presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
                presence.setFrom(s);
            } else
            {
                presence = presence1;
            }
        }
        return presence;
    }

    public Iterator getPresences(String s)
    {
        String s1 = getPresenceMapKey(s);
        Map map = (Map)presenceMap.get(s1);
        Iterator iterator;
        if(map == null)
        {
            Presence presence = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
            presence.setFrom(s);
            Presence apresence[] = new Presence[1];
            apresence[0] = presence;
            iterator = Arrays.asList(apresence).iterator();
        } else
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator1 = map.values().iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                Presence presence2 = (Presence)iterator1.next();
                if(presence2.isAvailable())
                    arraylist.add(presence2);
            } while(true);
            if(!arraylist.isEmpty())
            {
                iterator = arraylist.iterator();
            } else
            {
                Presence presence1 = new Presence(org.jivesoftware.smack.packet.Presence.Type.unavailable);
                presence1.setFrom(s);
                Presence apresence1[] = new Presence[1];
                apresence1[0] = presence1;
                iterator = Arrays.asList(apresence1).iterator();
            }
        }
        return iterator;
    }

    public SubscriptionMode getSubscriptionMode()
    {
        return subscriptionMode;
    }

    public Collection getUnfiledEntries()
    {
        return Collections.unmodifiableList(unfiledEntries);
    }

    public int getUnfiledEntryCount()
    {
        return unfiledEntries.size();
    }

    public void reload()
    {
        RosterPacket rosterpacket = new RosterPacket();
        if(persistentStorage != null)
            rosterpacket.setVersion(persistentStorage.getRosterVersion());
        requestPacketId = rosterpacket.getPacketID();
        PacketIDFilter packetidfilter = new PacketIDFilter(requestPacketId);
        connection.addPacketListener(new RosterResultListener(), packetidfilter);
        connection.sendPacket(rosterpacket);
    }

    public void removeEntry(RosterEntry rosterentry)
        throws XMPPException
    {
        if(entries.containsKey(rosterentry.getUser()))
        {
            RosterPacket rosterpacket = new RosterPacket();
            rosterpacket.setType(org.jivesoftware.smack.packet.IQ.Type.SET);
            org.jivesoftware.smack.packet.RosterPacket.Item item = RosterEntry.toRosterItem(rosterentry);
            item.setItemType(org.jivesoftware.smack.packet.RosterPacket.ItemType.remove);
            rosterpacket.addRosterItem(item);
            PacketCollector packetcollector = connection.createPacketCollector(new PacketIDFilter(rosterpacket.getPacketID()));
            connection.sendPacket(rosterpacket);
            IQ iq = (IQ)packetcollector.nextResult(SmackConfiguration.getPacketReplyTimeout());
            packetcollector.cancel();
            if(iq == null)
                throw new XMPPException("No response from the server.");
            if(iq.getType() == org.jivesoftware.smack.packet.IQ.Type.ERROR)
                throw new XMPPException(iq.getError());
        }
    }

    public void removeRosterListener(RosterListener rosterlistener)
    {
        rosterListeners.remove(rosterlistener);
    }

    public void setSubscriptionMode(SubscriptionMode subscriptionmode)
    {
        subscriptionMode = subscriptionmode;
    }

    private static SubscriptionMode defaultSubscriptionMode;
    private Connection connection;
    private final Map entries;
    private final Map groups;
    private RosterStorage persistentStorage;
    private Map presenceMap;
    private PresencePacketListener presencePacketListener;
    private String requestPacketId;
    boolean rosterInitialized;
    private final List rosterListeners;
    private SubscriptionMode subscriptionMode;
    private final List unfiledEntries;

    static 
    {
        defaultSubscriptionMode = SubscriptionMode.accept_all;
    }



/*
    static RosterStorage access$1002(Roster roster, RosterStorage rosterstorage)
    {
        roster.persistentStorage = rosterstorage;
        return rosterstorage;
    }

*/









}
