package com.facebook.katana.binding;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ChatHibernateKeepalive;
import com.facebook.katana.binding.ChatNotificationsManager;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.provider.ChatHistoryManager;
import com.facebook.katana.service.xmpp.FacebookChatConnection;
import com.facebook.katana.service.xmpp.FacebookChatConnectionListener;
import com.facebook.katana.service.xmpp.FacebookXmppPacket;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.ReentrantCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class ChatSession {

   private static final String FBHOST = "facebook.com";
   private static final int POST_HIBERNATION_WAIT_TIME_MS = 120000;
   public static final String PREF_CHAT_AUTOCONNECT = "chat_autoconnect";
   private static final String TAG = "ChatSession";
   private static volatile ChatSession mActiveSession = null;
   private Map<Long, FacebookChatUser.UnreadConversation> mActiveConversations;
   private final FacebookChatConnectionListener mChatConnectionListener;
   private FacebookChatConnection mConnection;
   private Context mContext;
   private final HashMap<Long, List<FacebookChatMessage>> mConversationHistory;
   private final Runnable mDisconnectionRunnable;
   private final Runnable mHibernationRunnable;
   private boolean mIsConnectionErrorReported;
   private final ReentrantCallback<ChatSession.FacebookChatListener> mListeners;
   private final List<FacebookChatMessage> mMessageHolder;
   private final ChatNotificationsManager mNotificationsManager;
   private Map<Long, FacebookChatUser> mOnline;
   private final PacketListener mPacketListener;
   private volatile ChatSession.SERVICE_STATUS mState;
   private final long mUserId;
   private WakeLock mWakeLock;


   protected ChatSession(Context var1) throws IllegalStateException {
      ReentrantCallback var2 = new ReentrantCallback();
      this.mListeners = var2;
      ArrayList var3 = new ArrayList();
      this.mMessageHolder = var3;
      HashMap var4 = new HashMap();
      this.mConversationHistory = var4;
      ChatSession.SERVICE_STATUS var5 = ChatSession.SERVICE_STATUS.OFF;
      this.mState = var5;
      ChatSession.1 var6 = new ChatSession.1();
      this.mPacketListener = var6;
      ChatSession.2 var7 = new ChatSession.2();
      this.mChatConnectionListener = var7;
      ChatSession.3 var8 = new ChatSession.3();
      this.mDisconnectionRunnable = var8;
      ChatSession.4 var9 = new ChatSession.4();
      this.mHibernationRunnable = var9;
      Log.v("ChatSession", "Creating a new chat session");
      this.mContext = var1;
      AppSession var10 = AppSession.getActiveSession(this.mContext, (boolean)0);
      if(var10 == null) {
         throw new IllegalStateException();
      } else {
         FacebookSessionInfo var11 = var10.getSessionInfo();
         if(var11 == null) {
            throw new IllegalStateException();
         } else {
            long var12 = var11.userId;
            this.mUserId = var12;
            byte var14;
            if(!Constants.isBetaBuild() && Math.random() > 0.01D) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            String var15 = var11.sessionKey;
            String var16 = var11.sessionSecret;
            FacebookChatConnection var17 = new FacebookChatConnection(var15, var16, (boolean)var14);
            this.mConnection = var17;
            HashMap var18 = new HashMap();
            this.mOnline = var18;
            FacebookChatConnection var19 = this.mConnection;
            FacebookChatConnectionListener var20 = this.mChatConnectionListener;
            var19.addConnectionListener(var20);
            this.mIsConnectionErrorReported = (boolean)1;
            Map var21 = ChatHistoryManager.getActiveConversations(this.mContext);
            this.mActiveConversations = var21;
            Iterator var22 = this.mActiveConversations.keySet().iterator();

            while(var22.hasNext()) {
               Long var23 = (Long)var22.next();
               Map var24 = this.mOnline;
               Long var25 = Long.valueOf(var23.longValue());
               long var26 = var23.longValue();
               FacebookChatUser.Presence var28 = FacebookChatUser.Presence.OFFLINE;
               FacebookChatUser var29 = new FacebookChatUser(var26, var28);
               var24.put(var25, var29);
            }

            Context var31 = this.mContext;
            ChatNotificationsManager var32 = new ChatNotificationsManager(var31);
            this.mNotificationsManager = var32;
         }
      }
   }

   private void acquireWakeLock() {
      if(this.mWakeLock == null) {
         Log.v("WAKE", "Acquiring wake lock");
         WakeLock var1 = ((PowerManager)this.mContext.getSystemService("power")).newWakeLock(1, "ChatSession");
         this.mWakeLock = var1;
         this.mWakeLock.acquire();
      }
   }

   private void announceConnectionFailure() {
      Iterator var1 = this.mListeners.getListeners().iterator();

      while(var1.hasNext()) {
         ((ChatSession.FacebookChatListener)var1.next()).onConnectionClosed();
      }

   }

   private void announceConnectionSuccess() {
      if(this.mIsConnectionErrorReported) {
         Iterator var1 = this.mListeners.getListeners().iterator();

         while(var1.hasNext()) {
            ((ChatSession.FacebookChatListener)var1.next()).onConnectionEstablished();
         }

         this.mIsConnectionErrorReported = (boolean)0;
      }
   }

   private void announceShutdown() {
      Iterator var1 = this.mListeners.getListeners().iterator();

      while(var1.hasNext()) {
         ((ChatSession.FacebookChatListener)var1.next()).onShutdown();
      }

   }

   public static ChatSession getActiveChatSession(Context var0) {
      if(mActiveSession == null) {
         try {
            mActiveSession = new ChatSession(var0);
         } catch (IllegalStateException var5) {
            ;
         }

         if(mActiveSession != null) {
            AlarmManager var1 = (AlarmManager)var0.getSystemService("alarm");
            Intent var2 = new Intent(var0, ChatHibernateKeepalive.class);
            PendingIntent var3 = PendingIntent.getBroadcast(var0, 0, var2, 0);
            var1.cancel(var3);
         }
      }

      return mActiveSession;
   }

   private boolean getConnectionPreference(Context var1) {
      return PreferenceManager.getDefaultSharedPreferences(var1).getBoolean("chat_autoconnect", (boolean)0);
   }

   private void handlePresenceChanged(Presence var1) {
      byte var2 = 0;
      long var3 = FacebookChatUser.getUid(var1.getFrom());
      FacebookChatUser.Presence var5;
      if(var1.isAway()) {
         var5 = FacebookChatUser.Presence.IDLE;
      } else if(var1.isAvailable()) {
         var5 = FacebookChatUser.Presence.AVAILABLE;
      } else {
         var5 = FacebookChatUser.Presence.OFFLINE;
      }

      FacebookChatUser.Presence var6 = FacebookChatUser.Presence.OFFLINE;
      FacebookChatUser var11;
      if(var5.equals(var6)) {
         Map var7 = this.mActiveConversations;
         Long var8 = Long.valueOf(var3);
         if(var7.get(var8) != null) {
            Map var9 = this.mOnline;
            Long var10 = Long.valueOf(var3);
            var11 = (FacebookChatUser)var9.get(var10);
         } else {
            Map var13 = this.mOnline;
            Long var14 = Long.valueOf(var3);
            var11 = (FacebookChatUser)var13.remove(var14);
         }
      } else {
         Map var15 = this.mOnline;
         Long var16 = Long.valueOf(var3);
         if(var15.containsKey(var16)) {
            Map var17 = this.mOnline;
            Long var18 = Long.valueOf(var3);
            var11 = (FacebookChatUser)var17.get(var18);
            var11.setPresence(var5);
         } else {
            var11 = new FacebookChatUser(var3, var5);
            Map var19 = this.mOnline;
            Long var20 = Long.valueOf(var3);
            var19.put(var20, var11);
            var2 = 1;
         }
      }

      Iterator var12 = this.mListeners.getListeners().iterator();

      while(var12.hasNext()) {
         ((ChatSession.FacebookChatListener)var12.next()).onPresenceChange(var11, (boolean)var2);
      }

   }

   private void releaseWakeLock() {
      if(this.mWakeLock != null) {
         Log.v("WAKE", "Releasing wake lock");
         this.mWakeLock.release();
         this.mWakeLock = null;
      }
   }

   private void saveChatMessage(FacebookChatMessage var1, boolean var2) {
      this.mMessageHolder.add(var1);
      long var4;
      if(var2) {
         var4 = var1.mRecipientUid;
      } else {
         var4 = var1.mSenderUid;
      }

      boolean var6 = this.getConversationHistory(var4).add(var1);
   }

   private void setConnectionPreference(Context var1, boolean var2) {
      Editor var3 = PreferenceManager.getDefaultSharedPreferences(var1).edit();
      var3.putBoolean("chat_autoconnect", var2);
      boolean var5 = var3.commit();
   }

   public static void shutdown(boolean var0) {
      if(mActiveSession != null) {
         if(var0) {
            mActiveSession.hibernate();
         } else {
            mActiveSession.disconnect((boolean)1);
         }
      }
   }

   public void addListener(ChatSession.FacebookChatListener var1) {
      this.mListeners.addListener(var1);
   }

   public void closeActiveConversation(long var1) {
      Map var3 = this.mActiveConversations;
      Long var4 = Long.valueOf(var1);
      var3.remove(var4);
   }

   public void connect(boolean var1, String var2) {
      Handler var3 = this.mConnection.getWorkerHandler();
      Runnable var4 = this.mHibernationRunnable;
      var3.removeCallbacks(var4);
      Runnable var5 = this.mDisconnectionRunnable;
      var3.removeCallbacks(var5);
      ChatSession.SERVICE_STATUS var6 = this.mState;
      ChatSession.SERVICE_STATUS var7 = ChatSession.SERVICE_STATUS.ON;
      if(var6 == var7 && this.mConnection.isConnected()) {
         if(var2 != null) {
            FacebookChatConnection var8 = this.mConnection;
            FacebookXmppPacket.PacketType var9 = FacebookXmppPacket.PacketType.RETRIEVE;
            FacebookXmppPacket var10 = new FacebookXmppPacket(var9, var2);
            var8.sendPacket(var10);
         }
      } else {
         if(var1) {
            Context var11 = this.mContext;
            if(!this.getConnectionPreference(var11)) {
               return;
            }
         }

         if(!this.mConnection.isConnected()) {
            this.mConnection.connect();
            Context var12 = this.mContext;
            this.setConnectionPreference(var12, (boolean)1);
         }

         this.acquireWakeLock();
         ChatSession.SERVICE_STATUS var13 = ChatSession.SERVICE_STATUS.ON;
         this.mState = var13;
         if(var2 != null) {
            FacebookChatConnection var14 = this.mConnection;
            FacebookXmppPacket.PacketType var15 = FacebookXmppPacket.PacketType.RETRIEVE;
            FacebookXmppPacket var16 = new FacebookXmppPacket(var15, var2);
            var14.sendPacket(var16);
         }
      }
   }

   public void disconnect(boolean var1) {
      ChatSession.SERVICE_STATUS var2 = ChatSession.SERVICE_STATUS.OFF;
      this.mState = var2;
      mActiveSession = null;
      if(this.mConnection != null) {
         FacebookChatConnection var3 = this.mConnection;
         FacebookChatConnectionListener var4 = this.mChatConnectionListener;
         var3.removeConnectionListener(var4);
         FacebookChatConnection var5 = this.mConnection;
         PacketListener var6 = this.mPacketListener;
         var5.removePacketListener(var6);
         this.mConnection.disconnect();
         this.mConnection = null;
      }

      if(this.mOnline != null) {
         this.mOnline.clear();
         this.mOnline = null;
      }

      if(var1) {
         Context var7 = this.mContext;
         this.setConnectionPreference(var7, (boolean)0);
      }

      Context var8 = this.mContext;
      long var9 = this.mUserId;
      List var11 = this.mMessageHolder;
      ChatHistoryManager.flushMessageHistory(var8, var9, var11);
      this.mConversationHistory.clear();
      this.mMessageHolder.clear();
      this.announceShutdown();
      if(this.mActiveConversations != null) {
         Context var12 = this.mContext;
         Map var13 = this.mActiveConversations;
         ChatHistoryManager.updateActiveConversations(var12, var13);
         this.mActiveConversations = null;
      }

      ChatHistoryManager.performHistoryCleanup(this.mContext);
      this.mContext = null;
      this.mListeners.clear();
      this.releaseWakeLock();
   }

   public Map<Long, FacebookChatUser.UnreadConversation> getActiveConversations() {
      return Collections.unmodifiableMap(this.mActiveConversations);
   }

   public ChatNotificationsManager getChatNotificationsManager() {
      return this.mNotificationsManager;
   }

   public List<FacebookChatMessage> getConversationHistory(long var1) {
      HashMap var3 = this.mConversationHistory;
      Long var4 = Long.valueOf(var1);
      if(var3.get(var4) == null) {
         Context var5 = this.mContext;
         long var6 = this.mUserId;
         List var8 = ChatHistoryManager.getConversationHistory(var5, var1, var6);
         HashMap var9 = this.mConversationHistory;
         Long var10 = Long.valueOf(var1);
         var9.put(var10, var8);
      }

      HashMap var12 = this.mConversationHistory;
      Long var13 = Long.valueOf(var1);
      return (List)var12.get(var13);
   }

   public Map<Long, FacebookChatUser> getOnlineUsers() {
      return Collections.unmodifiableMap(this.mOnline);
   }

   public FacebookChatUser getUser(long var1) {
      Map var3 = this.mOnline;
      Long var4 = Long.valueOf(var1);
      return (FacebookChatUser)var3.get(var4);
   }

   public void hibernate() {
      ChatSession.SERVICE_STATUS var1 = this.mState;
      ChatSession.SERVICE_STATUS var2 = ChatSession.SERVICE_STATUS.ON;
      if(var1 == var2) {
         Iterator var3 = this.mListeners.getListeners().iterator();

         while(var3.hasNext()) {
            ((ChatSession.FacebookChatListener)var3.next()).onShutdown();
         }

         ChatSession.SERVICE_STATUS var4 = ChatSession.SERVICE_STATUS.HIBERNATING;
         this.mState = var4;
         Handler var5 = this.mConnection.getWorkerHandler();
         Runnable var6 = this.mHibernationRunnable;
         var5.postDelayed(var6, 120000L);
         Runnable var8 = this.mDisconnectionRunnable;
         var5.postDelayed(var8, 125000L);
      }
   }

   public boolean isConnected() {
      ChatSession.SERVICE_STATUS var1 = this.mState;
      ChatSession.SERVICE_STATUS var2 = ChatSession.SERVICE_STATUS.ON;
      boolean var3;
      if(var1 == var2 && this.mConnection.isConnected()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void markConversationAsRead(long var1) {
      Map var3 = this.mActiveConversations;
      Long var4 = Long.valueOf(var1);
      if(var3.containsKey(var4)) {
         Map var5 = this.mActiveConversations;
         Long var6 = Long.valueOf(var1);
         ((FacebookChatUser.UnreadConversation)var5.get(var6)).clear();
      }
   }

   public void removeListener(ChatSession.FacebookChatListener var1) {
      this.mListeners.removeListener(var1);
   }

   public void sendChatMessage(long var1, String var3) {
      Map var4 = this.mActiveConversations;
      Long var5 = Long.valueOf(var1);
      if(!var4.containsKey(var5)) {
         Map var6 = this.mActiveConversations;
         Long var7 = Long.valueOf(var1);
         FacebookChatUser.UnreadConversation var8 = new FacebookChatUser.UnreadConversation((String)null, 0);
         var6.put(var7, var8);
      }

      String var10 = FacebookChatUser.getJid(Long.valueOf(var1));
      Message var11 = new Message();
      var11.setBody(var3);
      var11.setTo(var10);
      Message.Type var12 = Message.Type.chat;
      var11.setType(var12);
      this.mConnection.sendPacket(var11);
      long var13 = this.mUserId;
      Long var15 = Long.valueOf(System.currentTimeMillis());
      FacebookChatMessage var19 = new FacebookChatMessage(var13, var1, var3, var15);
      this.saveChatMessage(var19, (boolean)1);
   }

   private static enum SERVICE_STATUS {

      // $FF: synthetic field
      private static final ChatSession.SERVICE_STATUS[] $VALUES;
      HIBERNATING("HIBERNATING", 2),
      OFF("OFF", 0),
      ON("ON", 1);


      static {
         ChatSession.SERVICE_STATUS[] var0 = new ChatSession.SERVICE_STATUS[3];
         ChatSession.SERVICE_STATUS var1 = OFF;
         var0[0] = var1;
         ChatSession.SERVICE_STATUS var2 = ON;
         var0[1] = var2;
         ChatSession.SERVICE_STATUS var3 = HIBERNATING;
         var0[2] = var3;
         $VALUES = var0;
      }

      private SERVICE_STATUS(String var1, int var2) {}
   }

   public interface FacebookChatListener {

      void onConnectionClosed();

      void onConnectionEstablished();

      void onNewChatMessage(FacebookChatMessage var1);

      void onPresenceChange(FacebookChatUser var1, boolean var2);

      void onShutdown();
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         ChatSession.this.disconnect((boolean)0);
      }
   }

   class 2 implements FacebookChatConnectionListener {

      2() {}

      public void onConnectionEstablished() {
         PacketTypeFilter var1 = new PacketTypeFilter(Presence.class);
         PacketTypeFilter var2 = new PacketTypeFilter(Message.class);
         OrFilter var3 = new OrFilter(var1, var2);
         FacebookChatConnection var4 = ChatSession.this.mConnection;
         PacketListener var5 = ChatSession.this.mPacketListener;
         var4.addPacketListener(var5, var3);
         ChatSession.this.announceConnectionSuccess();
      }

      public void onConnectionPaused() {
         ChatSession.this.announceConnectionFailure();
      }

      public void onConnectionStopped() {
         ChatSession.this.mOnline.clear();
         ChatSession.this.announceConnectionFailure();
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         if(ChatSession.this.mConnection.isConnected()) {
            FacebookChatConnection var1 = ChatSession.this.mConnection;
            FacebookXmppPacket.PacketType var2 = FacebookXmppPacket.PacketType.HIBERNATE;
            FacebookXmppPacket var3 = new FacebookXmppPacket(var2);
            var1.sendPacket(var3);
            AlarmManager var4 = (AlarmManager)ChatSession.this.mContext.getSystemService("alarm");
            Context var5 = ChatSession.this.mContext;
            Intent var6 = new Intent(var5, ChatHibernateKeepalive.class);
            PendingIntent var7 = PendingIntent.getBroadcast(ChatSession.this.mContext, 0, var6, 0);
            long var8 = SystemClock.elapsedRealtime() + 10000L;
            var4.setInexactRepeating(2, var8, 3600000L, var7);
         }
      }
   }

   class 1 implements PacketListener {

      1() {}

      public void processPacket(Packet var1) {
         if(var1 instanceof Presence) {
            ChatSession var2 = ChatSession.this;
            Presence var3 = (Presence)var1;
            var2.handlePresenceChanged(var3);
         } else if(!var1.getFrom().equals("chat.facebook.com")) {
            if(!var1.getFrom().equals("facebook.com")) {
               Message var4 = (Message)var1;
               FacebookChatMessage var5 = new FacebookChatMessage(var4);
               FacebookChatMessage.Type var6 = var5.mMessageType;
               FacebookChatMessage.Type var7 = FacebookChatMessage.Type.NORMAL;
               if(var6 == var7) {
                  long var8 = FacebookChatUser.getUid(var1.getFrom());
                  Map var10 = ChatSession.this.mActiveConversations;
                  Long var11 = Long.valueOf(var8);
                  if(var10.containsKey(var11)) {
                     Map var12 = ChatSession.this.mActiveConversations;
                     Long var13 = Long.valueOf(var8);
                     FacebookChatUser.UnreadConversation var14 = (FacebookChatUser.UnreadConversation)var12.get(var13);
                     String var15 = var5.mBody;
                     var14.addMessage(var15);
                  } else {
                     Map var19 = ChatSession.this.mActiveConversations;
                     Long var20 = Long.valueOf(var8);
                     String var21 = var5.mBody;
                     FacebookChatUser.UnreadConversation var22 = new FacebookChatUser.UnreadConversation(var21, 1);
                     var19.put(var20, var22);
                     Map var24 = ChatSession.this.mOnline;
                     Long var25 = Long.valueOf(var8);
                     if(var24.get(var25) != null) {
                        Map var26 = ChatSession.this.mOnline;
                        Long var27 = Long.valueOf(var8);
                        FacebookChatUser.Presence var28 = FacebookChatUser.Presence.AVAILABLE;
                        FacebookChatUser var29 = new FacebookChatUser(var8, var28);
                        var26.put(var27, var29);
                     }
                  }

                  ChatSession.this.saveChatMessage(var5, (boolean)0);
                  ChatNotificationsManager var16 = ChatSession.this.mNotificationsManager;
                  String var17 = var5.mBody;
                  var16.displayNotification(var8, var17, (String)null);
               }

               Iterator var18 = ChatSession.this.mListeners.getListeners().iterator();

               while(var18.hasNext()) {
                  ((ChatSession.FacebookChatListener)var18.next()).onNewChatMessage(var5);
               }

            }
         }
      }
   }
}
