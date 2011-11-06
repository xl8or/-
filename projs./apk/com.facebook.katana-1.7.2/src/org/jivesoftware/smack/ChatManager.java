// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatManager.java

package org.jivesoftware.smack;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.ThreadFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smack.util.collections.ReferenceMap;

// Referenced classes of package org.jivesoftware.smack:
//            Connection, Chat, ChatManagerListener, PacketInterceptor, 
//            MessageListener, PacketCollector, PacketListener

public class ChatManager
{

    ChatManager(Connection connection1)
    {
        threadChats = new ReferenceMap(0, 2);
        jidChats = new ReferenceMap(0, 2);
        chatManagerListeners = new CopyOnWriteArraySet();
        interceptors = new WeakHashMap();
        connection = connection1;
        PacketFilter packetfilter = new PacketFilter() {

            public boolean accept(Packet packet)
            {
                boolean flag;
                if(!(packet instanceof Message))
                {
                    flag = false;
                } else
                {
                    org.jivesoftware.smack.packet.Message.Type type = ((Message)packet).getType();
                    if(type != org.jivesoftware.smack.packet.Message.Type.groupchat && type != org.jivesoftware.smack.packet.Message.Type.headline)
                        flag = true;
                    else
                        flag = false;
                }
                return flag;
            }

            final ChatManager this$0;

            
            {
                this$0 = ChatManager.this;
                super();
            }
        }
;
        connection1.addPacketListener(new PacketListener() {

            public void processPacket(Packet packet)
            {
                Message message = (Message)packet;
                if(message.getThread() != null) goto _L2; else goto _L1
_L1:
                Chat chat = getUserChat(message.getFrom());
_L4:
                if(chat == null)
                    chat = createChat(message);
                deliverMessage(chat, message);
                return;
_L2:
                chat = getThreadChat(message.getThread());
                if(chat == null)
                    chat = getUserChat(message.getFrom());
                if(true) goto _L4; else goto _L3
_L3:
            }

            final ChatManager this$0;

            
            {
                this$0 = ChatManager.this;
                super();
            }
        }
, packetfilter);
    }

    private Chat createChat(String s, String s1, boolean flag)
    {
        Chat chat = new Chat(this, s, s1);
        threadChats.put(s1, chat);
        jidChats.put(s, chat);
        for(Iterator iterator = chatManagerListeners.iterator(); iterator.hasNext(); ((ChatManagerListener)iterator.next()).chatCreated(chat, flag));
        return chat;
    }

    private Chat createChat(Message message)
    {
        String s = message.getThread();
        if(s == null)
            s = nextID();
        return createChat(message.getFrom(), s, false);
    }

    private void deliverMessage(Chat chat, Message message)
    {
        chat.deliver(message);
    }

    private Chat getUserChat(String s)
    {
        return (Chat)jidChats.get(s);
    }

    /**
     * @deprecated Method nextID is deprecated
     */

    private static String nextID()
    {
        org/jivesoftware/smack/ChatManager;
        JVM INSTR monitorenter ;
        String s;
        StringBuilder stringbuilder = (new StringBuilder()).append(prefix);
        long l = id;
        id = 1L + l;
        s = stringbuilder.append(Long.toString(l)).toString();
        org/jivesoftware/smack/ChatManager;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    public void addChatListener(ChatManagerListener chatmanagerlistener)
    {
        chatManagerListeners.add(chatmanagerlistener);
    }

    public void addOutgoingMessageInterceptor(PacketInterceptor packetinterceptor)
    {
        addOutgoingMessageInterceptor(packetinterceptor, null);
    }

    public void addOutgoingMessageInterceptor(PacketInterceptor packetinterceptor, PacketFilter packetfilter)
    {
        if(packetinterceptor != null)
            interceptors.put(packetinterceptor, packetfilter);
    }

    public Chat createChat(String s, String s1, MessageListener messagelistener)
    {
        String s2;
        if(s1 == null)
            s2 = nextID();
        else
            s2 = s1;
        if((Chat)threadChats.get(s2) != null)
        {
            throw new IllegalArgumentException("ThreadID is already used");
        } else
        {
            Chat chat = createChat(s, s2, true);
            chat.addMessageListener(messagelistener);
            return chat;
        }
    }

    public Chat createChat(String s, MessageListener messagelistener)
    {
        String s1;
        do
            s1 = nextID();
        while(threadChats.get(s1) != null);
        return createChat(s, s1, messagelistener);
    }

    PacketCollector createPacketCollector(Chat chat)
    {
        Connection connection1 = connection;
        PacketFilter apacketfilter[] = new PacketFilter[2];
        apacketfilter[0] = new ThreadFilter(chat.getThreadID());
        apacketfilter[1] = new FromContainsFilter(chat.getParticipant());
        return connection1.createPacketCollector(new AndFilter(apacketfilter));
    }

    public Collection getChatListeners()
    {
        return Collections.unmodifiableCollection(chatManagerListeners);
    }

    public Chat getThreadChat(String s)
    {
        return (Chat)threadChats.get(s);
    }

    public void removeChatListener(ChatManagerListener chatmanagerlistener)
    {
        chatManagerListeners.remove(chatmanagerlistener);
    }

    void sendMessage(Chat chat, Message message)
    {
        Iterator iterator = interceptors.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            PacketFilter packetfilter = (PacketFilter)entry.getValue();
            if(packetfilter != null && packetfilter.accept(message))
                ((PacketInterceptor)entry.getKey()).interceptPacket(message);
        } while(true);
        if(message.getFrom() == null)
            message.setFrom(connection.getUser());
        connection.sendPacket(message);
    }

    private static long id = 0L;
    private static String prefix = StringUtils.randomString(5);
    private Set chatManagerListeners;
    private Connection connection;
    private Map interceptors;
    private Map jidChats;
    private Map threadChats;




}
