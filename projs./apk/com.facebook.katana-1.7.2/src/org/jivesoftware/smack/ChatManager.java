package org.jivesoftware.smack;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.ThreadFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smack.util.collections.ReferenceMap;

public class ChatManager {

   private static long id = 0L;
   private static String prefix = StringUtils.randomString(5);
   private Set<ChatManagerListener> chatManagerListeners;
   private Connection connection;
   private Map<PacketInterceptor, PacketFilter> interceptors;
   private Map<String, Chat> jidChats;
   private Map<String, Chat> threadChats;


   ChatManager(Connection var1) {
      ReferenceMap var2 = new ReferenceMap(0, 2);
      this.threadChats = var2;
      ReferenceMap var3 = new ReferenceMap(0, 2);
      this.jidChats = var3;
      CopyOnWriteArraySet var4 = new CopyOnWriteArraySet();
      this.chatManagerListeners = var4;
      WeakHashMap var5 = new WeakHashMap();
      this.interceptors = var5;
      this.connection = var1;
      ChatManager.1 var6 = new ChatManager.1();
      ChatManager.2 var7 = new ChatManager.2();
      var1.addPacketListener(var7, var6);
   }

   private Chat createChat(String var1, String var2, boolean var3) {
      Chat var4 = new Chat(this, var1, var2);
      this.threadChats.put(var2, var4);
      this.jidChats.put(var1, var4);
      Iterator var7 = this.chatManagerListeners.iterator();

      while(var7.hasNext()) {
         ((ChatManagerListener)var7.next()).chatCreated(var4, var3);
      }

      return var4;
   }

   private Chat createChat(Message var1) {
      String var2 = var1.getThread();
      if(var2 == null) {
         var2 = nextID();
      }

      String var3 = var1.getFrom();
      return this.createChat(var3, var2, (boolean)0);
   }

   private void deliverMessage(Chat var1, Message var2) {
      var1.deliver(var2);
   }

   private Chat getUserChat(String var1) {
      return (Chat)this.jidChats.get(var1);
   }

   private static String nextID() {
      synchronized(ChatManager.class){}
      boolean var10 = false;

      String var6;
      try {
         var10 = true;
         StringBuilder var0 = new StringBuilder();
         String var1 = prefix;
         StringBuilder var2 = var0.append(var1);
         long var3 = id;
         id = 1L + var3;
         String var5 = Long.toString(var3);
         var6 = var2.append(var5).toString();
         var10 = false;
      } finally {
         if(var10) {
            ;
         }
      }

      return var6;
   }

   public void addChatListener(ChatManagerListener var1) {
      this.chatManagerListeners.add(var1);
   }

   public void addOutgoingMessageInterceptor(PacketInterceptor var1) {
      this.addOutgoingMessageInterceptor(var1, (PacketFilter)null);
   }

   public void addOutgoingMessageInterceptor(PacketInterceptor var1, PacketFilter var2) {
      if(var1 != null) {
         this.interceptors.put(var1, var2);
      }
   }

   public Chat createChat(String var1, String var2, MessageListener var3) {
      String var4;
      if(var2 == null) {
         var4 = nextID();
      } else {
         var4 = var2;
      }

      if((Chat)this.threadChats.get(var4) != null) {
         throw new IllegalArgumentException("ThreadID is already used");
      } else {
         Chat var5 = this.createChat(var1, var4, (boolean)1);
         var5.addMessageListener(var3);
         return var5;
      }
   }

   public Chat createChat(String var1, MessageListener var2) {
      String var3;
      do {
         var3 = nextID();
      } while(this.threadChats.get(var3) != null);

      return this.createChat(var1, var3, var2);
   }

   PacketCollector createPacketCollector(Chat var1) {
      Connection var2 = this.connection;
      PacketFilter[] var3 = new PacketFilter[2];
      String var4 = var1.getThreadID();
      ThreadFilter var5 = new ThreadFilter(var4);
      var3[0] = var5;
      String var6 = var1.getParticipant();
      FromContainsFilter var7 = new FromContainsFilter(var6);
      var3[1] = var7;
      AndFilter var8 = new AndFilter(var3);
      return var2.createPacketCollector(var8);
   }

   public Collection<ChatManagerListener> getChatListeners() {
      return Collections.unmodifiableCollection(this.chatManagerListeners);
   }

   public Chat getThreadChat(String var1) {
      return (Chat)this.threadChats.get(var1);
   }

   public void removeChatListener(ChatManagerListener var1) {
      this.chatManagerListeners.remove(var1);
   }

   void sendMessage(Chat var1, Message var2) {
      Iterator var3 = this.interceptors.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         PacketFilter var5 = (PacketFilter)var4.getValue();
         if(var5 != null && var5.accept(var2)) {
            ((PacketInterceptor)var4.getKey()).interceptPacket(var2);
         }
      }

      if(var2.getFrom() == null) {
         String var6 = this.connection.getUser();
         var2.setFrom(var6);
      }

      this.connection.sendPacket(var2);
   }

   class 1 implements PacketFilter {

      1() {}

      public boolean accept(Packet var1) {
         boolean var2;
         if(!(var1 instanceof Message)) {
            var2 = false;
         } else {
            Message.Type var3 = ((Message)var1).getType();
            Message.Type var4 = Message.Type.groupchat;
            if(var3 != var4) {
               Message.Type var5 = Message.Type.headline;
               if(var3 != var5) {
                  var2 = true;
                  return var2;
               }
            }

            var2 = false;
         }

         return var2;
      }
   }

   class 2 implements PacketListener {

      2() {}

      public void processPacket(Packet var1) {
         Message var2 = (Message)var1;
         Chat var5;
         if(var2.getThread() == null) {
            ChatManager var3 = ChatManager.this;
            String var4 = var2.getFrom();
            var5 = var3.getUserChat(var4);
         } else {
            ChatManager var6 = ChatManager.this;
            String var7 = var2.getThread();
            var5 = var6.getThreadChat(var7);
            if(var5 == null) {
               ChatManager var8 = ChatManager.this;
               String var9 = var2.getFrom();
               var5 = var8.getUserChat(var9);
            }
         }

         if(var5 == null) {
            var5 = ChatManager.this.createChat(var2);
         }

         ChatManager.this.deliverMessage(var5, var2);
      }
   }
}
