// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatConversationActivity.java

package com.facebook.katana.activity.chat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.*;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.ApplicationUtils;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.chat:
//            BuddyListActivity, ChatMessageAdapter

public class ChatConversationActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{

    public ChatConversationActivity()
    {
        closeConversationOnExit = false;
        mToken = null;
    }

    private void enableSending(boolean flag)
    {
        findViewById(0x7f0e0023).setEnabled(flag);
        findViewById(0x7f0e0025).setEnabled(flag);
        findViewById(0x7f0e0026).setEnabled(flag);
    }

    private void loadBuddyInfo(long l)
    {
        if(l != -1L) goto _L2; else goto _L1
_L1:
        return;
_L2:
        mBuddy = mChatSession.getUser(l);
        if(mBuddy == null)
            mBuddy = new FacebookChatUser(l, com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE);
        if(!mBuddy.infoInitialized)
        {
            String as[] = new String[1];
            as[0] = Long.toString(l);
            Cursor cursor = managedQuery(ConnectionsProvider.FRIENDS_CONTENT_URI, BuddyListActivity.PROJECTION, "user_id = ?", as, null);
            cursor.moveToFirst();
            if(cursor.getCount() > 0)
                mBuddy.setUserInfo(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void loadConversationHistory()
    {
        mMessageAdapter.clear();
        List list = mChatSession.getConversationHistory(mBuddy.mUserId);
        if(list != null)
        {
            Iterator iterator = list.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                FacebookChatMessage facebookchatmessage = (FacebookChatMessage)iterator.next();
                if(facebookchatmessage.mBody != null)
                    mMessageAdapter.add(facebookchatmessage);
            } while(true);
        }
    }

    private void loadUserInfo()
    {
        if(mUser == null || !mUser.infoInitialized)
        {
            FacebookUser facebookuser = mAppSession.getSessionInfo().getProfile();
            mUser = new FacebookChatUser(facebookuser.mUserId, com.facebook.katana.model.FacebookChatUser.Presence.AVAILABLE);
            mUser.setUserInfo(facebookuser.mFirstName, facebookuser.mLastName, facebookuser.mDisplayName, facebookuser.mImageUrl);
        }
    }

    private void setPresenceIcon(boolean flag)
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e0022);
        if(!flag) goto _L2; else goto _L1
_L1:
        imageview.setImageResource(0x7f020043);
_L4:
        return;
_L2:
        class _cls4
        {

            static final int $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type[];
            static final int $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence[];

            static 
            {
                $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence = new int[com.facebook.katana.model.FacebookChatUser.Presence.values().length];
                NoSuchFieldError nosuchfielderror4;
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence[com.facebook.katana.model.FacebookChatUser.Presence.AVAILABLE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence[com.facebook.katana.model.FacebookChatUser.Presence.IDLE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type = new int[com.facebook.katana.model.FacebookChatMessage.Type.values().length];
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type[com.facebook.katana.model.FacebookChatMessage.Type.NORMAL.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type[com.facebook.katana.model.FacebookChatMessage.Type.ACTIVE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                $SwitchMap$com$facebook$katana$model$FacebookChatMessage$Type[com.facebook.katana.model.FacebookChatMessage.Type.COMPOSING.ordinal()] = 3;
_L2:
                return;
                nosuchfielderror4;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        switch(_cls4..SwitchMap.com.facebook.katana.model.FacebookChatUser.Presence[mBuddy.mPresence.ordinal()])
        {
        default:
            imageview.setImageResource(0x7f020042);
            break;

        case 1: // '\001'
            break; /* Loop/switch isn't completed */

        case 2: // '\002'
            break MISSING_BLOCK_LABEL_81;
        }
_L5:
        imageview.setVisibility(0);
        if(true) goto _L4; else goto _L3
_L3:
        imageview.setImageResource(0x7f020040);
          goto _L5
        imageview.setImageResource(0x7f020041);
          goto _L5
    }

    private void setup()
    {
        loadUserInfo();
        ListView listview = (ListView)findViewById(0x7f0e0023);
        mMessageAdapter = new ChatMessageAdapter(this, mUser, mBuddy);
        listview.setAdapter(mMessageAdapter);
        loadConversationHistory();
        if(mMessageAdapter.getCount() > 0)
            listview.setSelection(mMessageAdapter.getCount() - 1);
        if(mBuddy.mPresence == com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE)
            enableSending(false);
    }

    private void showProgress(boolean flag)
    {
        if(flag)
        {
            findViewById(0x7f0e0018).setVisibility(0);
            findViewById(0x7f0e0022).setVisibility(8);
        } else
        {
            findViewById(0x7f0e0018).setVisibility(8);
            findViewById(0x7f0e0022).setVisibility(0);
        }
    }

    public void onClick(View view)
    {
        EditText edittext = (EditText)findViewById(0x7f0e0025);
        if(edittext.getText().length() > 0)
        {
            mChatSession.sendChatMessage(mBuddy.mUserId, edittext.getText().toString());
            mMessageAdapter.add(new FacebookChatMessage(mUser.mUserId, mBuddy.mUserId, edittext.getText().toString(), Long.valueOf(System.currentTimeMillis())));
            edittext.setText("");
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000b);
        ((Button)findViewById(0x7f0e0026)).setOnClickListener(this);
        ((TextView)findViewById(0x7f0e0021)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                facebookOnBackPressed();
            }

            final ChatConversationActivity this$0;

            
            {
                this$0 = ChatConversationActivity.this;
                super();
            }
        }
);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0d0001, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2131624316 2131624317: default 28
    //                   2131624316 50
    //                   2131624317 36;
           goto _L1 _L2 _L3
_L1:
        boolean flag = super.onOptionsItemSelected(menuitem);
_L5:
        return flag;
_L3:
        closeConversationOnExit = true;
        finish();
        flag = true;
        continue; /* Loop/switch isn't completed */
_L2:
        ApplicationUtils.OpenUserProfile(this, mBuddy.mUserId, null);
        flag = true;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void onPause()
    {
        super.onPause();
        mChatSession.getChatNotificationsManager().unMarkConversationAsRunning(mBuddy.mUserId);
        if(!closeConversationOnExit)
            mChatSession.markConversationAsRead(mBuddy.mUserId);
        else
            mChatSession.closeActiveConversation(mBuddy.mUserId);
        mChatSession.removeListener(mChatListener);
        mAppSession.removeListener(mSessionListener);
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mChatSession.addListener(mChatListener);
            mChatSession.connect(false, mToken);
            mToken = null;
            mChatSession.getChatNotificationsManager().markConversationAsRunning(mBuddy.mUserId);
            if(mChatSession.isConnected())
            {
                setup();
            } else
            {
                enableSending(false);
                showProgress(true);
            }
            mAppSession.addListener(mSessionListener);
        }
    }

    protected void onStart()
    {
        super.onStart();
        Intent intent = getIntent();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this, intent);
        } else
        {
            mToken = getIntent().getStringExtra("token");
            mChatSession = ChatSession.getActiveChatSession(this);
            FacebookChatUser facebookchatuser = (FacebookChatUser)intent.getParcelableExtra("buddy");
            mBuddy = facebookchatuser;
            if(facebookchatuser == null)
                loadBuddyInfo(intent.getLongExtra("buddyId", -1L));
            setPresenceIcon(false);
            ((TextView)findViewById(0x7f0e0021)).setText(mBuddy.getDisplayName());
        }
    }

    public static final String EXTRA_BUDDY = "buddy";
    public static final String EXTRA_BUDDY_ID = "buddyId";
    public static final String EXTRA_DISPLAY_NAME = "displayName";
    public static final String EXTRA_TOKEN = "token";
    private boolean closeConversationOnExit;
    private AppSession mAppSession;
    private FacebookChatUser mBuddy;
    private final com.facebook.katana.binding.ChatSession.FacebookChatListener mChatListener = new com.facebook.katana.binding.ChatSession.FacebookChatListener() {

        public void onConnectionClosed()
        {
            enableSending(false);
            showProgress(true);
        }

        public void onConnectionEstablished()
        {
            showProgress(false);
            setup();
            if(mBuddy != null)
            {
                if(mBuddy.mPresence != com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE)
                    enableSending(true);
                setPresenceIcon(false);
            }
        }

        public void onNewChatMessage(FacebookChatMessage facebookchatmessage)
        {
            if(facebookchatmessage.mSenderUid != mBuddy.mUserId) goto _L2; else goto _L1
_L1:
            _cls4..SwitchMap.com.facebook.katana.model.FacebookChatMessage.Type[facebookchatmessage.mMessageType.ordinal()];
            JVM INSTR tableswitch 1 3: default 56
        //                       1 57
        //                       2 76
        //                       3 87;
               goto _L2 _L3 _L4 _L5
_L2:
            return;
_L3:
            setPresenceIcon(false);
            mMessageAdapter.add(facebookchatmessage);
_L4:
            setPresenceIcon(false);
            continue; /* Loop/switch isn't completed */
_L5:
            setPresenceIcon(true);
            if(true) goto _L2; else goto _L6
_L6:
        }

        public void onPresenceChange(FacebookChatUser facebookchatuser, boolean flag)
        {
            if(facebookchatuser != null && facebookchatuser.mUserId == mBuddy.mUserId)
            {
                mBuddy = facebookchatuser;
                setPresenceIcon(false);
                if(mBuddy.mPresence == com.facebook.katana.model.FacebookChatUser.Presence.OFFLINE)
                    enableSending(false);
                else
                    enableSending(true);
            }
        }

        public void onShutdown()
        {
            finish();
        }

        final ChatConversationActivity this$0;

            
            {
                this$0 = ChatConversationActivity.this;
                super();
            }
    }
;
    private ChatSession mChatSession;
    private ChatMessageAdapter mMessageAdapter;
    private final AppSessionListener mSessionListener = new AppSessionListener() {

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200 && mMessageAdapter != null)
                mMessageAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            if(mMessageAdapter != null)
                mMessageAdapter.updateUserImage(profileimage);
        }

        final ChatConversationActivity this$0;

            
            {
                this$0 = ChatConversationActivity.this;
                super();
            }
    }
;
    private String mToken;
    private FacebookChatUser mUser;



/*
    static FacebookChatUser access$002(ChatConversationActivity chatconversationactivity, FacebookChatUser facebookchatuser)
    {
        chatconversationactivity.mBuddy = facebookchatuser;
        return facebookchatuser;
    }

*/





}
