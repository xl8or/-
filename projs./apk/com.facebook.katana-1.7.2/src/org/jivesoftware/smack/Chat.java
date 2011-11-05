package org.jivesoftware.smack;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Chat {

   private ChatManager chatManager;
   private final Set<MessageListener> listeners;
   private String participant;
   private String threadID;


   Chat(ChatManager var1, String var2, String var3) {
      CopyOnWriteArraySet var4 = new CopyOnWriteArraySet();
      this.listeners = var4;
      this.chatManager = var1;
      this.participant = var2;
      this.threadID = var3;
   }

   public void addMessageListener(MessageListener var1) {
      if(var1 != null) {
         this.listeners.add(var1);
      }
   }

   public PacketCollector createCollector() {
      return this.chatManager.createPacketCollector(this);
   }

   void deliver(Message var1) {
      String var2 = this.threadID;
      var1.setThread(var2);
      Iterator var3 = this.listeners.iterator();

      while(var3.hasNext()) {
         ((MessageListener)var3.next()).processMessage(this, var1);
      }

   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof Chat) {
         String var2 = this.threadID;
         String var3 = ((Chat)var1).getThreadID();
         if(var2.equals(var3)) {
            String var4 = this.participant;
            String var5 = ((Chat)var1).getParticipant();
            if(var4.equals(var5)) {
               var6 = true;
               return var6;
            }
         }
      }

      var6 = false;
      return var6;
   }

   public Collection<MessageListener> getListeners() {
      return Collections.unmodifiableCollection(this.listeners);
   }

   public String getParticipant() {
      return this.participant;
   }

   public String getThreadID() {
      return this.threadID;
   }

   public void removeMessageListener(MessageListener var1) {
      this.listeners.remove(var1);
   }

   public void sendMessage(String var1) throws XMPPException {
      String var2 = this.participant;
      Message.Type var3 = Message.Type.chat;
      Message var4 = new Message(var2, var3);
      String var5 = this.threadID;
      var4.setThread(var5);
      var4.setBody(var1);
      this.chatManager.sendMessage(this, var4);
   }

   public void sendMessage(Message var1) throws XMPPException {
      String var2 = this.participant;
      var1.setTo(var2);
      Message.Type var3 = Message.Type.chat;
      var1.setType(var3);
      String var4 = this.threadID;
      var1.setThread(var4);
      this.chatManager.sendMessage(this, var1);
   }
}
