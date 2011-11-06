// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendsActivity.java

package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.List;

// Referenced classes of package com.facebook.katana:
//            UsersTabProgressSource, LoginActivity, TabProgressListener, FriendsAdapter

public class FriendsActivity extends BaseFacebookListActivity
    implements UsersTabProgressSource, android.widget.AdapterView.OnItemClickListener, android.widget.AbsListView.OnScrollListener
{
    public static class EveryoneSection extends com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section
    {

        public String toString()
        {
            return "*";
        }

        public EveryoneSection(Context context, int i, int j)
        {
            super(context.getString(0x7f0a006b), i, j);
        }
    }

    protected class FriendsAndUserSearchAdapter extends FriendsAdapter
    {

        public Object getChild(int i, int j)
        {
            com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section section = (com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section)mSections.get(i);
            int k = j + section.getCursorStartPosition();
            mCursor.moveToPosition(k);
            FacebookProfile facebookprofile1;
            if(mCursor.getCount() < 1)
            {
                facebookprofile1 = null;
            } else
            {
                FacebookProfile facebookprofile;
                if(section instanceof EveryoneSection)
                    facebookprofile = new FacebookProfile(mCursor.getLong(1), mCursor.getString(2), mCursor.getString(3), 0);
                else
                    facebookprofile = new FacebookProfile(mCursor.getLong(1), mCursor.getString(2), mCursor.getString(3), 0);
                facebookprofile1 = facebookprofile;
            }
            return facebookprofile1;
        }

        /**
         * @deprecated Method manageEveryoneSection is deprecated
         */

        public void manageEveryoneSection(Cursor cursor)
        {
            this;
            JVM INSTR monitorenter ;
            if(mSections != null && mSections.size() > 0 && ((com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section)mSections.get(mSections.size() - 1) instanceof EveryoneSection))
                mSections.remove(mSections.size() - 1);
            userSearchCursor = cursor;
            if(showEveryoneSection && cursor != null && cursor.getCount() != 0) goto _L2; else goto _L1
_L1:
            mCursor = friendCursor;
            notifyDataSetChanged();
_L5:
            this;
            JVM INSTR monitorexit ;
            return;
_L2:
            if(friendCursor != null) goto _L4; else goto _L3
_L3:
            int j = 0;
_L6:
            Cursor acursor[] = new Cursor[2];
            acursor[0] = friendCursor;
            acursor[1] = cursor;
            mCursor = new MergeCursor(acursor);
            mSections.add(new EveryoneSection(FriendsActivity.this, j, cursor.getCount()));
            notifyDataSetChanged();
              goto _L5
            Exception exception;
            exception;
            throw exception;
_L4:
            int i = friendCursor.getCount();
            j = i;
              goto _L6
        }

        /**
         * @deprecated Method refreshData is deprecated
         */

        public void refreshData(Cursor cursor)
        {
            this;
            JVM INSTR monitorenter ;
            friendCursor = cursor;
            super.refreshData(cursor);
            manageEveryoneSection(userSearchCursor);
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

        public void setEveryoneSectionEnabled(boolean flag)
        {
            showEveryoneSection = flag;
            manageEveryoneSection(userSearchCursor);
        }

        Cursor friendCursor;
        boolean showEveryoneSection;
        final FriendsActivity this$0;
        Cursor userSearchCursor;

        public FriendsAndUserSearchAdapter(Context context, ProfileImagesCache profileimagescache, Cursor cursor)
        {
            this$0 = FriendsActivity.this;
            super(context, profileimagescache, cursor);
            friendCursor = cursor;
            showEveryoneSection = false;
        }
    }

    private class FriendsAppSessionListener extends AppSessionListener
    {

        public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            showProgress(2, false);
            if(i == 200)
            {
                logStepDataReceived();
                Cursor cursor = mFriendsAndEveryoneAdapter.friendCursor;
                if(cursor != null)
                    cursor.requery();
                mFriendsAndEveryoneAdapter.refreshData(cursor);
            } else
            {
                String s2 = StringUtils.getErrorString(FriendsActivity.this, getString(0x7f0a006e), i, s1, exception);
                Toaster.toast(FriendsActivity.this, s2);
            }
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mFriendsAndEveryoneAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mFriendsAndEveryoneAdapter.updateUserImage(profileimage);
        }

        public void onUsersSearchComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, int k)
        {
            if(mCurrentUserSearchQueryId != null && s.equals(mCurrentUserSearchQueryId))
            {
                showProgress(4, false);
                if(i == 200)
                {
                    logStepDataReceived();
                    Cursor cursor = mFriendsAndEveryoneAdapter.userSearchCursor;
                    if(cursor != null)
                        cursor.requery();
                    mFriendsAndEveryoneAdapter.setEveryoneSectionEnabled(true);
                    mFriendsAndEveryoneAdapter.manageEveryoneSection(cursor);
                } else
                {
                    String s2 = StringUtils.getErrorString(FriendsActivity.this, getString(0x7f0a0072), i, s1, exception);
                    Toaster.toast(FriendsActivity.this, s2);
                }
            }
        }

        final FriendsActivity this$0;

        private FriendsAppSessionListener()
        {
            this$0 = FriendsActivity.this;
            super();
        }

    }

    protected final class UserSearchQueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(isFinishing())
            {
                cursor.close();
            } else
            {
                showProgress(3, false);
                startManagingCursor(cursor);
                mFriendsAndEveryoneAdapter.setEveryoneSectionEnabled(true);
                mFriendsAndEveryoneAdapter.manageEveryoneSection(cursor);
            }
        }

        public static final int DELETE_SEARCH_TOKEN = 1;
        public static final int QUERY_SEARCH_TOKEN = 1;
        final FriendsActivity this$0;

        public UserSearchQueryHandler(Context context)
        {
            this$0 = FriendsActivity.this;
            super(context.getContentResolver());
        }
    }

    private final class FriendsQueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(!isFinishing()) goto _L2; else goto _L1
_L1:
            cursor.close();
_L4:
            return;
_L2:
            showProgress(1, false);
            startManagingCursor(cursor);
            mFriendsAndEveryoneAdapter.refreshData(cursor);
            if(!mAppSession.isFriendsSyncPending())
            {
                if(mFriendsAndEveryoneAdapter.getCount() == 0)
                {
                    mAppSession.syncFriends(FriendsActivity.this);
                    logStepDataRequested();
                    showProgress(2, true);
                }
            } else
            {
                showProgress(2, true);
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        public static final int QUERY_FRIENDS_TOKEN = 1;
        final FriendsActivity this$0;

        public FriendsQueryHandler(Context context)
        {
            this$0 = FriendsActivity.this;
            super(context.getContentResolver());
        }
    }


    public FriendsActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03002c);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(bundle != null)
                mCurrentQuery = bundle.getString("query");
            else
                mCurrentQuery = "";
            mFriendsAndEveryoneAdapter = new FriendsAndUserSearchAdapter(this, mAppSession.getUserImagesCache(), null);
            ((SectionedListView)getListView()).setSectionedListAdapter(mFriendsAndEveryoneAdapter);
            setupEmptyView();
            mFriendsQueryHandler = new FriendsQueryHandler(this);
            mUserSearchQueryHandler = new UserSearchQueryHandler(this);
            mAppSessionListener = new FriendsAppSessionListener();
            getListView().setOnItemClickListener(this);
            getListView().setOnScrollListener(this);
            mUserSearchQueryHandler.startDelete(1, null, ConnectionsProvider.USER_SEARCH_CONTENT_URI, null, null);
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        FacebookProfile facebookprofile = (FacebookProfile)mFriendsAndEveryoneAdapter.getItem(i);
        ApplicationUtils.OpenUserProfile(this, facebookprofile.mId, facebookprofile);
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        mAppSession.addListener(mAppSessionListener);
        if(mFriendsAndEveryoneAdapter.friendCursor == null)
        {
            showProgress(1, true);
            mFriendsQueryHandler.startQuery(1, null, ConnectionsProvider.FRIENDS_CONTENT_URI, com.facebook.katana.activity.profilelist.ProfileListNaiveCursorAdapter.FriendsQuery.PROJECTION, "display_name IS NOT NULL AND LENGTH(display_name) > 0", null, null);
        } else
        {
            mFriendsAndEveryoneAdapter.refreshData(mFriendsAndEveryoneAdapter.friendCursor);
        }
        if(mFriendsAndEveryoneAdapter.userSearchCursor == null)
        {
            showProgress(3, true);
            mUserSearchQueryHandler.startQuery(1, null, ConnectionsProvider.USER_SEARCH_CONTENT_URI, UserSearchResultsAdapter.SearchResultsQuery.PROJECTION, null, null, null);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onScroll(AbsListView abslistview, int i, int j, int k)
    {
        int l = getListView().getLastVisiblePosition();
        if(mFriendsAndEveryoneAdapter.userSearchCursor != null)
        {
            int i1 = mFriendsAndEveryoneAdapter.getCursor().getCount();
            if(l > 0 && l == i1 - 1 && mCurrentQuery != null && mCurrentQuery.length() > 0)
            {
                showProgress(4, true);
                int j1;
                if(mFriendsAndEveryoneAdapter.userSearchCursor != null)
                    j1 = mFriendsAndEveryoneAdapter.userSearchCursor.getCount();
                else
                    j1 = 0;
                mCurrentUserSearchQueryId = performSearchRequest(mCurrentQuery, j1, 20);
            }
        }
    }

    public void onScrollStateChanged(AbsListView abslistview, int i)
    {
    }

    protected String performSearchRequest(String s, int i, int j)
    {
        String s1;
        if(s.equals(mCurrentQuery) && mLastQueryStart >= i && mLastQueryLimit == j)
        {
            s1 = mCurrentUserSearchQueryId;
        } else
        {
            mCurrentQuery = s;
            mLastQueryStart = i;
            mLastQueryLimit = j;
            s1 = mAppSession.usersSearch(this, s, i, j);
        }
        return s1;
    }

    public void search(String s)
    {
        if(!s.equals(mCurrentQuery))
        {
            mFriendsAndEveryoneAdapter.mFilter.filter(s);
            mFriendsAndEveryoneAdapter.setEveryoneSectionEnabled(false);
            if(s.length() == 0)
            {
                mCurrentQuery = "";
                mCurrentUserSearchQueryId = null;
            } else
            {
                ((SectionedListView)getListView()).setFastScrollEnabled(false);
                showProgress(4, true);
                mCurrentUserSearchQueryId = performSearchRequest(s, 0, 20);
            }
        }
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
        {
            TabProgressListener tabprogresslistener1 = mProgressListener;
            boolean flag;
            if(mProgress != 0)
                flag = true;
            else
                flag = false;
            tabprogresslistener1.onShowProgress(flag);
        }
    }

    protected void setupEmptyView()
    {
        TextView textview = (TextView)findViewById(0x7f0e0056);
        TextView textview1 = (TextView)findViewById(0x7f0e0058);
        textview.setText(0x7f0a0070);
        textview1.setText(0x7f0a006f);
    }

    protected void showProgress(int i, boolean flag)
    {
        boolean flag1;
        if(flag)
            mProgress = i | mProgress;
        else
            mProgress = mProgress & ~i;
        if(mProgress != 0)
            flag1 = true;
        else
            flag1 = false;
        if(flag1)
        {
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
        if(mProgressListener != null)
            mProgressListener.onShowProgress(flag1);
    }

    public static final int PROGRESS_FLAG_FRIENDS_QUERY = 1;
    public static final int PROGRESS_FLAG_FRIENDS_SYNC = 2;
    public static final int PROGRESS_FLAG_SEARCH = 4;
    public static final int PROGRESS_FLAG_SEARCH_QUERY = 3;
    protected static final int RESULT_BATCH_SIZE = 20;
    private static final String SAVED_STATE_LAST_QUERY = "query";
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mCurrentQuery;
    protected String mCurrentUserSearchQueryId;
    private FriendsAndUserSearchAdapter mFriendsAndEveryoneAdapter;
    private FriendsQueryHandler mFriendsQueryHandler;
    private int mLastQueryLimit;
    private int mLastQueryStart;
    private int mProgress;
    private TabProgressListener mProgressListener;
    private UserSearchQueryHandler mUserSearchQueryHandler;





}
