// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageListener.java

package org.jivesoftware.smack;

import org.jivesoftware.smack.packet.Message;

// Referenced classes of package org.jivesoftware.smack:
//            Chat

public interface MessageListener
{

    public abstract void processMessage(Chat chat, Message message);
}
