// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatSession.java

package com.facebook.katana.binding;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.os.*;
import android.preference.PreferenceManager;
import com.facebook.katana.Constants;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.ChatHistoryManager;
import com.facebook.katana.service.xmpp.*;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.ReentrantCallback;
import java.util.*;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.OrFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.*;

// Referenced classes of package com.facebook.katana.binding:
//            AppSession, ChatNotificationsManager, ChatHibernateKeepalive

public class ChatSession
{
    public static interface FacebookChatListener
    {

        public abstract void onConnectionClosed();

        public abstract void onConnectionEstablished();

        public abstract void onNewChatMessage(FacebookChatMessage facebookchatmessage);

        public abstract void onPresenceChange(FacebookChatUser facebookchatuser, boolean flag);

        public abstract void onShutdown();
    }

    private static final class SERVICE_STATUS extends Enum
    {

        public static SERVICE_STATUS valueOf(String s)
        {
            return (SERVICE_STATUS)Enum.valueOf(com/facebook/katana/binding/ChatSession$SERVICE_STATUS, s);
        }

        public static SERVICE_STATUS[] values()
        {
            return (SERVICE_STATUS[])$VALUES.clone();
        }

        private static final SERVICE_STATUS $VALUES[];
        public static final SERVICE_STATUS HIBERNATING;
        public static final SERVICE_STATUS OFF;
        public static final SERVICE_STATUS ON;

        static 
        {
            OFF = new SERVICE_STATUS("OFF", 0);
            ON = new SERVICE_STATUS("ON", 1);
            HIBERNATING = new SERVICE_STATUS("HIBERNATING", 2);
            SERVICE_STATUS aservice_status[] = new SERVICE_STATUS[3];
            aservice_status[0] = OFF;
            aservice_status[1] = ON;
            aservice_status[2] = HIBERNATING;
            $VALUES = aservice_status;
        }

        private SERVICE_STATUS(String s, int i)
        {
            super(s, i);
        }
    }


    protected ChatSession(Context context)
        throws IllegalStateException
    {
        mState = SERVICE_STATUS.OFF;
        Log.v("ChatSession", "Creating a new chat session");
        mContext = context;
        AppSession appsession = AppSession.getActiveSession(mContext, false);
        if(appsession == null)
            throw new IllegalStateException();
        FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
        if(facebooksessioninfo == null)
            throw new IllegalStateException();
        mUserId = facebooksessioninfo.userId;
        boolean flag;
        if(!Constants.isBetaBuild() && Math.random() > 0.01D)
            flag = true;
        else
            flag = false;
        mConnection = new FacebookChatConnection(facebooksessioninfo.sessionKey, facebooksessioninfo.sessionSecret, flag);
        mOnline = new HashMap();
        mConnection.addConnectionListener(mChatConnectionListener);
        mIsConnectionErrorReported = true;
        mActiveConversations = ChatHistoryManager.getActiveConversations(mContext);
        Long long1;
        for(Iterator iterator = mActiveConversations.keySet().iterator(); iterator.hasNext(); mOnline.put(Long.valueOf(long1.longValue()), new FacebookChatUser(long1.longValue(), com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE)))
            long1 = (Long)iterator.next();

        mNotificationsManager = new ChatNotificationsManager(mContext);
    }

    private void acquireWakeLock()
    {
        if(mWakeLock == null)
        {
            Log.v("WAKE", "Acquiring wake lock");
            mWakeLock = ((PowerManager)mContext.getSystemService("power")).newWakeLock(1, "ChatSession");
            mWakeLock.acquire();
        }
    }

    private void announceConnectionFailure()
    {
        for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((FacebookChatListener)iterator.next()).onConnectionClosed());
    }

    private void announceConnectionSuccess()
    {
        if(mIsConnectionErrorReported)
        {
            for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((FacebookChatListener)iterator.next()).onConnectionEstablished());
            mIsConnectionErrorReported = false;
        }
    }

    private void announceShutdown()
    {
        for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((FacebookChatListener)iterator.next()).onShutdown());
    }

    public static ChatSession getActiveChatSession(Context context)
    {
        if(mActiveSession == null)
        {
            try
            {
                mActiveSession = new ChatSession(context);
            }
            catch(IllegalStateException illegalstateexception) { }
            if(mActiveSession != null)
                ((AlarmManager)context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, 0, new Intent(context, com/facebook/katana/binding/ChatHibernateKeepalive), 0));
        }
        return mActiveSession;
    }

    private boolean getConnectionPreference(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("chat_autoconnect", false);
    }

    private void handlePresenceChanged(Presence presence)
    {
        boolean flag = false;
        long l = FacebookChatUser.getUid(presence.getFrom());
        com.facebook.katana.model.FacebookChatUser.Presence presence1;
        FacebookChatUser facebookchatuser;
        Iterator iterator;
        if(presence.isAway())
            presence1 = com.facebook.katana.model.FacebookChatUser.Presence.IDLE;
        else
        if(presence.isAvailable())
            presence1 = com.facebook.katana.model.FacebookChatUser.Presence.AVAILABLE;
        else
            presence1 = com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE;
        if(presence1.equals(com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE))
        {
            if(mActiveConversations.get(Long.valueOf(l)) != null)
                facebookchatuser = (FacebookChatUser)mOnline.get(Long.valueOf(l));
            else
                facebookchatuser = (FacebookChatUser)mOnline.remove(Long.valueOf(l));
        } else
        if(mOnline.containsKey(Long.valueOf(l)))
        {
            facebookchatuser = (FacebookChatUser)mOnline.get(Long.valueOf(l));
            facebookchatuser.setPresence(presence1);
        } else
        {
            facebookchatuser = new FacebookChatUser(l, presence1);
            mOnline.put(Long.valueOf(l), facebookchatuser);
            flag = true;
        }
        for(iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((FacebookChatListener)iterator.next()).onPresenceChange(facebookchatuser, flag));
    }

    private void releaseWakeLock()
    {
        if(mWakeLock != null)
        {
            Log.v("WAKE", "Releasing wake lock");
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void saveChatMessage(FacebookChatMessage facebookchatmessage, boolean flag)
    {
        mMessageHolder.add(facebookchatmessage);
        long l;
        if(flag)
            l = facebookchatmessage.mRecipientUid;
        else
            l = facebookchatmessage.mSenderUid;
        getConversationHistory(l).add(facebookchatmessage);
    }

    private void setConnectionPreference(Context context, boolean flag)
    {
        android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("chat_autoconnect", flag);
        editor.commit();
    }

    public static void shutdown(boolean flag)
    {
        if(mActiveSession != null)
            if(flag)
                mActiveSession.hibernate();
            else
                mActiveSession.disconnect(true);
    }

    public void addListener(FacebookChatListener facebookchatlistener)
    {
        mListeners.addListener(facebookchatlistener);
    }

    public void closeActiveConversation(long l)
    {
        mActiveConversations.remove(Long.valueOf(l));
    }

    public void connect(boolean flag, String s)
    {
        Handler handler = mConnection.getWorkerHandler();
        handler.removeCallbacks(mHibernationRunnable);
        handler.removeCallbacks(mDisconnectionRunnable);
        if(mState != SERVICE_STATUS.ON || !mConnection.isConnected()) goto _L2; else goto _L1
_L1:
        if(s != null)
            mConnection.sendPacket(new FacebookXmppPacket(com.facebook.katana.service.xmpp.FacebookXmppPacket.PacketType.RETRIEVE, s));
_L4:
        return;
_L2:
        if(!flag || getConnectionPreference(mContext))
        {
            if(!mConnection.isConnected())
            {
                mConnection.connect();
                setConnectionPreference(mContext, true);
            }
            acquireWakeLock();
            mState = SERVICE_STATUS.ON;
            if(s != null)
                mConnection.sendPacket(new FacebookXmppPacket(com.facebook.katana.service.xmpp.FacebookXmppPacket.PacketType.RETRIEVE, s));
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void disconnect(boolean flag)
    {
        mState = SERVICE_STATUS.OFF;
        mActiveSession = null;
        if(mConnection != null)
        {
            mConnection.removeConnectionListener(mChatConnectionListener);
            mConnection.removePacketListener(mPacketListener);
            mConnection.disconnect();
            mConnection = null;
        }
        if(mOnline != null)
        {
            mOnline.clear();
            mOnline = null;
        }
        if(flag)
            setConnectionPreference(mContext, false);
        ChatHistoryManager.flushMessageHistory(mContext, mUserId, mMessageHolder);
        mConversationHistory.clear();
        mMessageHolder.clear();
        announceShutdown();
        if(mActiveConversations != null)
        {
            ChatHistoryManager.updateActiveConversations(mContext, mActiveConversations);
            mActiveConversations = null;
        }
        ChatHistoryManager.performHistoryCleanup(mContext);
        mContext = null;
        mListeners.clear();
        releaseWakeLock();
    }

    public Map getActiveConversations()
    {
        return Collections.unmodifiableMap(mActiveConversations);
    }

    public ChatNotificationsManager getChatNotificationsManager()
    {
        return mNotificationsManager;
    }

    public List getConversationHistory(long l)
    {
        if(mConversationHistory.get(Long.valueOf(l)) == null)
        {
            List list = ChatHistoryManager.getConversationHistory(mContext, l, mUserId);
            mConversationHistory.put(Long.valueOf(l), list);
        }
        return (List)mConversationHistory.get(Long.valueOf(l));
    }

    public Map getOnlineUsers()
    {
        return Collections.unmodifiableMap(mOnline);
    }

    public FacebookChatUser getUser(long l)
    {
        return (FacebookChatUser)mOnline.get(Long.valueOf(l));
    }

    public void hibernate()
    {
        if(mState == SERVICE_STATUS.ON)
        {
            for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((FacebookChatListener)iterator.next()).onShutdown());
            mState = SERVICE_STATUS.HIBERNATING;
            Handler handler = mConnection.getWorkerHandler();
            handler.postDelayed(mHibernationRunnable, 0x1d4c0L);
            handler.postDelayed(mDisconnectionRunnable, 0x1e848L);
        }
    }

    public boolean isConnected()
    {
        boolean flag;
        if(mState == SERVICE_STATUS.ON && mConnection.isConnected())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void markConversationAsRead(long l)
    {
        if(mActiveConversations.containsKey(Long.valueOf(l)))
            ((com.facebook.katana.model.FacebookChatUser.UnreadConversation)mActiveConversations.get(Long.valueOf(l))).clear();
    }

    public void removeListener(FacebookChatListener facebookchatlistener)
    {
        mListeners.removeListener(facebookchatlistener);
    }

    public void sendChatMessage(long l, String s)
    {
        if(!mActiveConversations.containsKey(Long.valueOf(l)))
            mActiveConversations.put(Long.valueOf(l), new com.facebook.katana.model.FacebookChatUser.UnreadConversation(null, 0));
        String s1 = FacebookChatUser.getJid(Long.valueOf(l));
        Message message = new Message();
        message.setBody(s);
        message.setTo(s1);
        message.setType(org.jivesoftware.smack.packet.Message.Type.chat);
        mConnection.sendPacket(message);
        saveChatMessage(new FacebookChatMessage(mUserId, l, s, Long.valueOf(System.currentTimeMillis())), true);
    }

    private static final String FBHOST = "facebook.com";
    private static final int POST_HIBERNATION_WAIT_TIME_MS = 0x1d4c0;
    public static final String PREF_CHAT_AUTOCONNECT = "chat_autoconnect";
    private static final String TAG = "ChatSession";
    private static volatile ChatSession mActiveSession = null;
    private Map mActiveConversations;
    private final FacebookChatConnectionListener mChatConnectionListener = new FacebookChatConnectionListener() {

        public void onConnectionEstablished()
        {
            OrFilter orfilter = new OrFilter(new PacketTypeFilter(org/jivesoftware/smack/packet/Presence), new PacketTypeFilter(org/jivesoftware/smack/packet/Message));
            mConnection.addPacketListener(mPacketListener, orfilter);
            announceConnectionSuccess();
        }

        public void onConnectionPaused()
        {
            announceConnectionFailure();
        }

        public void onConnectionStopped()
        {
            mOnline.clear();
            announceConnectionFailure();
        }

        final ChatSession this$0;

            
            {
                this$0 = ChatSession.this;
                super();
            }
    }
;
    private FacebookChatConnection mConnection;
    private Context mContext;
    private final HashMap mConversationHistory = new HashMap();
    private final Runnable mDisconnectionRunnable = new Runnable() {

        public void run()
        {
            disconnect(false);
        }

        final ChatSession this$0;

            
            {
                this$0 = ChatSession.this;
                super();
            }
    }
;
    private final Runnable mHibernationRunnable = new Runnable() {

        public void run()
        {
            if(mConnection.isConnected())
            {
                mConnection.sendPacket(new FacebookXmppPacket(com.facebook.katana.service.xmpp.FacebookXmppPacket.PacketType.HIBERNATE));
                AlarmManager alarmmanager = (AlarmManager)mContext.getSystemService("alarm");
                Intent intent = new Intent(mContext, com/facebook/katana/binding/ChatHibernateKeepalive);
                PendingIntent pendingintent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
                alarmmanager.setInexactRepeating(2, 10000L + SystemClock.elapsedRealtime(), 0x36ee80L, pendingintent);
            }
        }

        final ChatSession this$0;

            
            {
                this$0 = ChatSession.this;
                super();
            }
    }
;
    private boolean mIsConnectionErrorReported;
    private final ReentrantCallback mListeners = new ReentrantCallback();
    private final List mMessageHolder = new ArrayList();
    private final ChatNotificationsManager mNotificationsManager;
    private Map mOnline;
    private final PacketListener mPacketListener = new PacketListener() {

        public void processPacket(Packet packet)
        {
            if(packet instanceof Presence)
                handlePresenceChanged((Presence)packet);
            else
            if(!packet.getFrom().equals("chat.facebook.com") && !packet.getFrom().equals("facebook.com"))
            {
                FacebookChatMessage facebookchatmessage = new FacebookChatMessage((Message)packet);
                if(facebookchatmessage.mMessageType == com.facebook.katana.model.FacebookChatMessage.Type.NORMAL)
                {
                    long l = FacebookChatUser.getUid(packet.getFrom());
                    Iterator iterator1;
                    if(mActiveConversations.containsKey(Long.valueOf(l)))
                    {
                        ((com.facebook.katana.model.FacebookChatUser.UnreadConversation)mActiveConversations.get(Long.valueOf(l))).addMessage(facebookchatmessage.mBody);
                    } else
                    {
                        mActiveConversations.put(Long.valueOf(l), new com.facebook.katana.model.FacebookChatUser.UnreadConversation(facebookchatmessage.mBody, 1));
                        if(mOnline.get(Long.valueOf(l)) != null)
                            mOnline.put(Long.valueOf(l), new FacebookChatUser(l, com.facebook.katana.model.FacebookChatUser.Presence.AVAILABLE));
                    }
                    saveChatMessage(facebookchatmessage, false);
                    mNotificationsManager.displayNotification(l, facebookchatmessage.mBody, null);
                }
                iterator1 = mListeners.getListeners().iterator();
                while(iterator1.hasNext()) 
                    ((FacebookChatListener)iterator1.next()).onNewChatMessage(facebookchatmessage);
            }
        }

        final ChatSession this$0;

            
            {
                this$0 = ChatSession.this;
                super();
            }
    }
;
    private volatile SERVICE_STATUS mState;
    private final long mUserId;
    private android.os.PowerManager.WakeLock mWakeLock;












}
