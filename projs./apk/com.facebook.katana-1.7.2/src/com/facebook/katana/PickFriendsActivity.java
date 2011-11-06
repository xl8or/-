// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PickFriendsActivity.java

package com.facebook.katana;

import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;

// Referenced classes of package com.facebook.katana:
//            CheckboxAdapterListener, PickFriendsAdapter, LoginActivity

public class PickFriendsActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener, CheckboxAdapterListener
{
    private class PickFriendsAppSessionListener extends AppSessionListener
    {

        public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            findViewById(0x7f0e00f1).setVisibility(8);
            if(i != 200)
            {
                String s2 = StringUtils.getErrorString(PickFriendsActivity.this, getString(0x7f0a006e), i, s1, exception);
                Toaster.toast(PickFriendsActivity.this, s2);
            }
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        final PickFriendsActivity this$0;

        private PickFriendsAppSessionListener()
        {
            this$0 = PickFriendsActivity.this;
            super();
        }

    }

    private final class QueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(!isFinishing())
            {
                showProgress(false);
                handleQueryComplete(cursor);
            } else
            {
                cursor.close();
            }
        }

        public static final int QUERY_FRIENDS_TOKEN = 1;
        final PickFriendsActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = PickFriendsActivity.this;
            super(context.getContentResolver());
        }
    }


    public PickFriendsActivity()
    {
    }

    private void handleQueryComplete(Cursor cursor)
    {
        startManagingCursor(cursor);
        mAdapter.changeCursor(cursor);
        if(!mAppSession.isFriendsSyncPending())
        {
            if(mAdapter.getCount() == 0)
            {
                mAppSession.syncFriends(this);
                showProgress(true);
            } else
            {
                showProgress(false);
            }
        } else
        {
            showProgress(true);
        }
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a0070);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a006f);
    }

    private void showButtonBar(boolean flag)
    {
        View view;
        ViewStub viewstub = (ViewStub)findViewById(0x7f0e00ea);
        if(viewstub != null)
        {
            viewstub.inflate();
            findViewById(0x7f0e00e0).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    ArrayList arraylist = mAdapter.getMarkedFriends();
                    if(arraylist.size() > 0)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("com.facebook.katana.PickFriendsActivity.result_friends", arraylist);
                        setResult(-1, intent);
                    }
                    finish();
                }

                final PickFriendsActivity this$0;

            
            {
                this$0 = PickFriendsActivity.this;
                super();
            }
            }
);
            findViewById(0x7f0e0055).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    finish();
                }

                final PickFriendsActivity this$0;

            
            {
                this$0 = PickFriendsActivity.this;
                super();
            }
            }
);
        }
        view = findViewById(0x7f0e00eb);
        if(!flag || view.getVisibility() != 8) goto _L2; else goto _L1
_L1:
        view.setAnimation(AnimationUtils.loadAnimation(this, 0x7f040005));
        view.setVisibility(0);
_L4:
        return;
_L2:
        if(!flag && view.getVisibility() == 0)
        {
            view.setAnimation(AnimationUtils.loadAnimation(this, 0x7f040004));
            view.setVisibility(8);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void showProgress(boolean flag)
    {
        if(flag)
        {
            findViewById(0x7f0e00f1).setVisibility(0);
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            findViewById(0x7f0e00f1).setVisibility(8);
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
    }

    private void updateSummaryText()
    {
        int i = mAdapter.getMarkedFriends().size();
        String s;
        if(1 == i)
        {
            s = getString(0x7f0a00de);
        } else
        {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            s = getString(0x7f0a00cc, aobj);
        }
        mRecipientsSummaryTextView.setText(s);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030059);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            hideSearchButton();
            ArrayList arraylist = getIntent().getParcelableArrayListExtra("com.facebook.katana.PickFriendsActivity.initial_friends");
            mAdapter = new PickFriendsAdapter(this, null, mAppSession.getUserImagesCache(), this, arraylist);
            getListView().setAdapter(mAdapter);
            setupEmptyView();
            mQueryHandler = new QueryHandler(this);
            mAppSessionListener = new PickFriendsAppSessionListener();
            getListView().setOnItemClickListener(this);
            mRecipientsSummaryTextView = (TextView)findViewById(0x7f0e00e9);
            updateSummaryText();
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        mAdapter.flipMarked(i);
    }

    public void onMarkChanged(long l, boolean flag, int i)
    {
        boolean flag1;
        if(flag || i > 0)
            flag1 = true;
        else
            flag1 = false;
        showButtonBar(flag1);
        updateSummaryText();
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
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mAppSession.addListener(mAppSessionListener);
            showProgress(true);
            mQueryHandler.startQuery(1, null, ConnectionsProvider.FRIENDS_CONTENT_URI, PickFriendsAdapter.FriendsQuery.PROJECTION, null, null, null);
        }
    }

    public boolean shouldChangeState(long l, boolean flag, int i)
    {
        return true;
    }

    public static final String INITIAL_FRIENDS = "com.facebook.katana.PickFriendsActivity.initial_friends";
    public static final String RESULT_FRIENDS = "com.facebook.katana.PickFriendsActivity.result_friends";
    private PickFriendsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private QueryHandler mQueryHandler;
    private TextView mRecipientsSummaryTextView;



}
