// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BuddyListActivity.java

package com.facebook.katana.activity.chat;

import android.content.*;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.Toaster;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.chat:
//            BuddyListSectionedAdapter, ChatConversationActivity

public class BuddyListActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener, android.view.View.OnClickListener
{
    private final class QueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(cursor != null && mBuddiesCache != null)
                if(isFinishing())
                {
                    cursor.close();
                } else
                {
                    if(obj != null)
                        mDisplayCache.clear();
                    cursor.moveToFirst();
                    for(; !cursor.isAfterLast(); cursor.moveToNext())
                    {
                        FacebookChatUser facebookchatuser = (FacebookChatUser)mBuddiesCache.get(Long.valueOf(cursor.getLong(0)));
                        if(facebookchatuser == null)
                            continue;
                        if(!facebookchatuser.infoInitialized)
                            facebookchatuser.setUserInfo(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                        mDisplayCache.add(facebookchatuser);
                    }

                    updateDisplay();
                    cursor.close();
                }
        }

        public static final int QUERY_FRIENDS_TOKEN = 1;
        final BuddyListActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = BuddyListActivity.this;
            super(context.getContentResolver());
        }
    }


    public BuddyListActivity()
    {
        mCurrentQuery = null;
        mToken = null;
        numConnFailures = 0;
    }

    private void loadBuddyList(String s, boolean flag)
    {
        if(mChatSession.isConnected())
        {
            if(mAdapter == null)
            {
                BuddyListSectionedAdapter buddylistsectionedadapter = new BuddyListSectionedAdapter(this, mImageCache);
                mAdapter = buddylistsectionedadapter;
                mListView.setSectionedListAdapter(mAdapter);
            }
            mBuddiesCache = mChatSession.getOnlineUsers();
            if(mBuddiesCache.isEmpty())
                showProgress(false);
            ArrayList arraylist = new ArrayList();
            StringBuilder stringbuilder = new StringBuilder("user_id");
            stringbuilder.append(" IN (");
            boolean flag1 = true;
            boolean flag2;
            Iterator iterator;
            if(mDisplayCache.size() > 0 && !flag)
                flag2 = true;
            else
                flag2 = false;
            iterator = mBuddiesCache.values().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                FacebookChatUser facebookchatuser = (FacebookChatUser)iterator.next();
                if(!flag2 || !facebookchatuser.infoInitialized)
                {
                    arraylist.add(Long.toString(facebookchatuser.mUserId));
                    if(!flag1)
                        stringbuilder.append(", ");
                    stringbuilder.append("?");
                    flag1 = false;
                }
            } while(true);
            for(Iterator iterator1 = mChatSession.getActiveConversations().keySet().iterator(); iterator1.hasNext();)
            {
                arraylist.add(Long.toString(((Long)iterator1.next()).longValue()));
                if(!flag1)
                    stringbuilder.append(", ");
                stringbuilder.append("?");
                flag1 = false;
            }

            stringbuilder.append(")");
            if(arraylist.size() != 0)
            {
                if(s != null)
                    stringbuilder.append((new StringBuilder()).append(" AND (first_name LIKE ").append(DatabaseUtils.sqlEscapeString((new StringBuilder()).append(s).append("%").toString())).append(" OR ").append("last_name").append(" LIKE ").append(DatabaseUtils.sqlEscapeString((new StringBuilder()).append(s).append("%").toString())).append(" OR ").append("display_name").append(" LIKE ").append(DatabaseUtils.sqlEscapeString((new StringBuilder()).append(s).append("%").toString())).append(")").toString());
                mQueryHandler.startQuery(1, s, ConnectionsProvider.FRIENDS_CONTENT_URI, PROJECTION, stringbuilder.toString(), (String[])arraylist.toArray(new String[0]), null);
            }
        }
    }

    private void modifyBuddy(FacebookChatUser facebookchatuser)
    {
        if(mChatSession.isConnected() && facebookchatuser != null && !facebookchatuser.infoInitialized)
        {
            if(mAdapter == null)
            {
                mAdapter = new BuddyListSectionedAdapter(this, mImageCache);
                mListView.setSectionedListAdapter(mAdapter);
            }
            mBuddiesCache = mChatSession.getOnlineUsers();
            StringBuilder stringbuilder = new StringBuilder("user_id");
            stringbuilder.append("=?");
            String as[] = new String[1];
            for(int i = 0; i < as.length; i++)
                as[i] = String.valueOf(facebookchatuser.mUserId);

            mQueryHandler.startQuery(1, null, ConnectionsProvider.FRIENDS_CONTENT_URI, PROJECTION, stringbuilder.toString(), as, null);
        }
    }

    private void showProgress(boolean flag)
    {
        if(flag)
        {
            findViewById(0x7f0e0018).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(0);
            findViewById(0x7f0e0056).setVisibility(8);
            getListView().setVisibility(8);
        } else
        {
            findViewById(0x7f0e0018).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(8);
            findViewById(0x7f0e0056).setVisibility(0);
            getListView().setVisibility(0);
        }
    }

    private void updateDisplay()
    {
        int i = mListView.getFirstVisiblePosition();
        if(mAdapter != null)
        {
            if(mDisplayCache.size() > 0)
                showProgress(false);
            BuddyListSectionedAdapter buddylistsectionedadapter = mAdapter;
            SortedSet sortedset = mDisplayCache;
            Map map = mChatSession.getActiveConversations();
            boolean flag;
            if(mCurrentQuery != null)
                flag = true;
            else
                flag = false;
            buddylistsectionedadapter.redraw(sortedset, map, flag);
            if(i > 0)
                mListView.setSelection(i);
        }
    }

    public void onClick(View view)
    {
        facebookOnBackPressed();
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030009);
        getListView().setOnItemClickListener(this);
        ((TextView)findViewById(0x7f0e0017)).setOnClickListener(this);
        mBuddiesCache = null;
        mCurrentQuery = null;
        mQueryHandler = new QueryHandler(this);
        mListView = (SectionedListView)getListView();
        mTextBox = (TextView)findViewById(0x7f0e0019);
        mToken = getIntent().getStringExtra("token");
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a0206);
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a0029);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0d0000, menu);
        return true;
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        FacebookChatUser facebookchatuser = (FacebookChatUser)mAdapter.getItem(i);
        Intent intent = new Intent(this, com/facebook/katana/activity/chat/ChatConversationActivity);
        intent.putExtra("buddy", facebookchatuser);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2131624315 2131624315: default 24
    //                   2131624315 26;
           goto _L1 _L2
_L1:
        return true;
_L2:
        ChatSession.shutdown(false);
        finish();
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected void onPause()
    {
        super.onPause();
        mAppSession.removeListener(mSessionListener);
        mChatSession.removeListener(mChatListener);
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
            mAppSession.addListener(mSessionListener);
            mChatSession = ChatSession.getActiveChatSession(this);
            mChatSession.addListener(mChatListener);
            mChatSession.connect(false, mToken);
            mImageCache = mAppSession.getUserImagesCache();
            mChatSession.getChatNotificationsManager().clear();
            if(mChatSession.isConnected())
                loadBuddyList(mCurrentQuery, true);
            else
                showProgress(true);
            mToken = null;
            getWindow().setSoftInputMode(3);
            mTextBox.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable editable)
                {
                }

                public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
                {
                }

                public void onTextChanged(CharSequence charsequence, int i, int j, int k)
                {
                    if(!TextUtils.isEmpty(charsequence))
                        mCurrentQuery = charsequence.toString();
                    else
                        mCurrentQuery = null;
                    loadBuddyList(mCurrentQuery, true);
                }

                final BuddyListActivity this$0;

            
            {
                this$0 = BuddyListActivity.this;
                super();
            }
            }
);
        }
    }

    public static final int INDEX_USER_DISPLAY_NAME = 3;
    public static final int INDEX_USER_FIRST_NAME = 1;
    public static final int INDEX_USER_ID = 0;
    public static final int INDEX_USER_IMAGE_URL = 4;
    public static final int INDEX_USER_LAST_NAME = 2;
    public static final String PROJECTION[];
    private BuddyListSectionedAdapter mAdapter;
    private AppSession mAppSession;
    private Map mBuddiesCache;
    private final com.facebook.katana.binding.ChatSession.FacebookChatListener mChatListener = new com.facebook.katana.binding.ChatSession.FacebookChatListener() ;
    private ChatSession mChatSession;
    private String mCurrentQuery;
    private final SortedSet mDisplayCache = new TreeSet();
    private ProfileImagesCache mImageCache;
    private SectionedListView mListView;
    private QueryHandler mQueryHandler;
    private final AppSessionListener mSessionListener = new AppSessionListener() {

        public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            loadBuddyList(mCurrentQuery, false);
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(mAdapter != null && i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            if(mAdapter != null)
                mAdapter.updateUserImage(profileimage);
        }

        final BuddyListActivity this$0;

            
            {
                this$0 = BuddyListActivity.this;
                super();
            }
    }
;
    private TextView mTextBox;
    private String mToken;
    private int numConnFailures;

    static 
    {
        String as[] = new String[5];
        as[0] = "user_id";
        as[1] = "first_name";
        as[2] = "last_name";
        as[3] = "display_name";
        as[4] = "user_image_url";
        PROJECTION = as;
    }



/*
    static String access$002(BuddyListActivity buddylistactivity, String s)
    {
        buddylistactivity.mCurrentQuery = s;
        return s;
    }

*/




/*
    static int access$202(BuddyListActivity buddylistactivity, int i)
    {
        buddylistactivity.numConnFailures = i;
        return i;
    }

*/


/*
    static int access$208(BuddyListActivity buddylistactivity)
    {
        int i = buddylistactivity.numConnFailures;
        buddylistactivity.numConnFailures = i + 1;
        return i;
    }

*/






}
