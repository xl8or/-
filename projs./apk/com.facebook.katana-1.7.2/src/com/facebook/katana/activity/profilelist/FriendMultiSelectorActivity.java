// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendMultiSelectorActivity.java

package com.facebook.katana.activity.profilelist;

import android.content.*;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.*;
import java.util.Set;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListActivity, SelectableProfileListNaiveCursorAdapter

public class FriendMultiSelectorActivity extends ProfileListActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    protected class FriendMultiSelectorAppSessionListener extends ProfileListActivity.ProfileListListener
    {

        public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            showProgress(2, false);
            if(i == 200)
            {
                SelectableProfileListNaiveCursorAdapter selectableprofilelistnaivecursoradapter = (SelectableProfileListNaiveCursorAdapter)mAdapter;
                Cursor cursor = selectableprofilelistnaivecursoradapter.getCursor();
                if(cursor != null)
                {
                    cursor.requery();
                    selectableprofilelistnaivecursoradapter.refreshData(cursor);
                }
            } else
            {
                String s2 = StringUtils.getErrorString(FriendMultiSelectorActivity.this, getString(0x7f0a006e), i, s1, exception);
                Toaster.toast(FriendMultiSelectorActivity.this, s2);
            }
        }

        final FriendMultiSelectorActivity this$0;

        protected FriendMultiSelectorAppSessionListener()
        {
            this$0 = FriendMultiSelectorActivity.this;
            super(FriendMultiSelectorActivity.this);
        }
    }

    private final class QueryHandler extends AsyncQueryHandler
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
            ((SelectableProfileListNaiveCursorAdapter)mAdapter).refreshData(cursor);
            if(!mAppSession.isFriendsSyncPending())
            {
                if(mAdapter.getCount() == 0)
                {
                    mAppSession.syncFriends(FriendMultiSelectorActivity.this);
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
        final FriendMultiSelectorActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = FriendMultiSelectorActivity.this;
            super(context.getContentResolver());
        }
    }


    public FriendMultiSelectorActivity()
    {
    }

    private void setupEmptyView()
    {
        TextView textview = (TextView)findViewById(0x7f0e0056);
        TextView textview1 = (TextView)findViewById(0x7f0e0058);
        textview.setText(0x7f0a0070);
        textview1.setText(0x7f0a006f);
    }

    private void setupViews()
    {
        setContentView(0x7f030072);
        ((SectionedListView)getListView()).setSectionedListAdapter(mAdapter);
        setupEmptyView();
        setPrimaryActionFace(-1, getString(0x7f0a004b));
        hideSearchButton();
        getListView().setOnItemClickListener(this);
        mTextBox = (TextView)findViewById(0x7f0e0019);
        mTextBox.setText(mCurrentQuery);
        mTextBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable)
            {
                int i;
                if(editable.length() > 0)
                    i = 0x108005a;
                else
                    i = 0x7f0200dc;
                mSearchIcon.setImageResource(i);
            }

            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
            {
            }

            public void onTextChanged(CharSequence charsequence, int i, int j, int k)
            {
                String s = charsequence.toString().trim();
                mCurrentQuery = s;
                ((SelectableProfileListNaiveCursorAdapter)mAdapter).mFilter.filter(mCurrentQuery);
                ((SectionedListView)getListView()).setFastScrollEnabled(false);
            }

            final FriendMultiSelectorActivity this$0;

            
            {
                this$0 = FriendMultiSelectorActivity.this;
                super();
            }
        }
);
        mSearchIcon = (ImageView)findViewById(0x7f0e0126);
        mSearchIcon.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(mTextBox.length() > 0)
                    mTextBox.setText("");
            }

            final FriendMultiSelectorActivity this$0;

            
            {
                this$0 = FriendMultiSelectorActivity.this;
                super();
            }
        }
);
    }

    private void showProgress(int i, boolean flag)
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
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        setupViews();
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mSelectedUids = IntentUtils.primitiveToSet(getIntent().getLongArrayExtra("profiles"));
            mAdapter = new SelectableProfileListNaiveCursorAdapter(this, mAppSession.getUserImagesCache(), null, mSelectedUids);
            mQueryHandler = new QueryHandler(this);
            mAppSessionListener = new FriendMultiSelectorAppSessionListener();
            setupViews();
            mTextBox.requestFocus();
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        ((SelectableProfileListNaiveCursorAdapter)mAdapter).toggle(i, view);
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
        if(((SelectableProfileListNaiveCursorAdapter)mAdapter).getCursor() == null)
        {
            showProgress(1, true);
            mQueryHandler.startQuery(1, null, ConnectionsProvider.FRIENDS_CONTENT_URI, ProfileListNaiveCursorAdapter.FriendsQuery.PROJECTION, "display_name IS NOT NULL AND LENGTH(display_name) > 0", null, null);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("profiles", IntentUtils.setToPrimitive(mSelectedUids));
        setResult(-1, intent);
        finish();
    }

    public static final String EXTRA_SELECTED_PROFILES = "profiles";
    public static final int PROGRESS_FLAG_FRIENDS_QUERY = 1;
    public static final int PROGRESS_FLAG_FRIENDS_SYNC = 2;
    protected String mCurrentQuery;
    protected int mProgress;
    protected QueryHandler mQueryHandler;
    protected ImageView mSearchIcon;
    protected Set mSelectedUids;
    protected TextView mTextBox;

}
