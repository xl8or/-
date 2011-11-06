// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Chat.java

package org.jivesoftware.smack;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jivesoftware.smack.packet.Message;

// Referenced classes of package org.jivesoftware.smack:
//            ChatManager, MessageListener, XMPPException, PacketCollector

public class Chat
{

    Chat(ChatManager chatmanager, String s, String s1)
    {
        chatManager = chatmanager;
        participant = s;
        threadID = s1;
    }

    public void addMessageListener(MessageListener messagelistener)
    {
        if(messagelistener != null)
            listeners.add(messagelistener);
    }

    public PacketCollector createCollector()
    {
        return chatManager.createPacketCollector(this);
    }

    void deliver(Message message)
    {
        message.setThread(threadID);
        for(Iterator iterator = listeners.iterator(); iterator.hasNext(); ((MessageListener)iterator.next()).processMessage(this, message));
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if((obj instanceof Chat) && threadID.equals(((Chat)obj).getThreadID()) && participant.equals(((Chat)obj).getParticipant()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Collection getListeners()
    {
        return Collections.unmodifiableCollection(listeners);
    }

    public String getParticipant()
    {
        return participant;
    }

    public String getThreadID()
    {
        return threadID;
    }

    public void removeMessageListener(MessageListener messagelistener)
    {
        listeners.remove(messagelistener);
    }

    public void sendMessage(String s)
        throws XMPPException
    {
        Message message = new Message(participant, org.jivesoftware.smack.packet.Message.Type.chat);
        message.setThread(threadID);
        message.setBody(s);
        chatManager.sendMessage(this, message);
    }

    public void sendMessage(Message message)
        throws XMPPException
    {
        message.setTo(participant);
        message.setType(org.jivesoftware.smack.packet.Message.Type.chat);
        message.setThread(threadID);
        chatManager.sendMessage(this, message);
    }

    private ChatManager chatManager;
    private final Set listeners = new CopyOnWriteArraySet();
    private String participant;
    private String threadID;
}
