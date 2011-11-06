// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupMemberListActivity.java

package com.facebook.katana.activity.profilelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.ui.SectionedListView;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListActivity, ProfileListDynamicAdapter

public class GroupMemberListActivity extends ProfileListActivity
    implements TabProgressSource
{
    public class GroupMemberListListener extends ProfileListActivity.ProfileListListener
    {

        public void onGetGroupsMembersComplete(AppSession appsession, String s, int i, String s1, Exception exception, Map map)
        {
            if(i == 200)
            {
                logStepDataReceived();
                ArrayList arraylist = new ArrayList();
                for(Iterator iterator = map.values().iterator(); iterator.hasNext(); arraylist.add((FacebookProfile)iterator.next()));
                ((ProfileListDynamicAdapter)mAdapter).updateProfileList(arraylist);
            }
            showProgress(false);
        }

        final GroupMemberListActivity this$0;

        public GroupMemberListListener()
        {
            this$0 = GroupMemberListActivity.this;
            super(GroupMemberListActivity.this);
        }
    }


    public GroupMemberListActivity()
    {
        mGroupId = -1L;
    }

    private void setupEmptyView()
    {
        TextView textview = (TextView)findViewById(0x7f0e0056);
        TextView textview1 = (TextView)findViewById(0x7f0e0058);
        textview.setText(0x7f0a015a);
        textview1.setText(0x7f0a008c);
    }

    private void showProgress(boolean flag)
    {
        if(mProgressListener != null)
            mProgressListener.onShowProgress(flag);
        mShowingProgress = flag;
    }

    public void onCreate(Bundle bundle)
    {
        mHasFatTitleHeader = true;
        super.onCreate(bundle);
        setContentView(0x7f030030);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            setupListHeaders();
            setupFatTitleHeader();
            mAdapter = new ProfileListDynamicAdapter(this, mAppSession.getUserImagesCache());
            ((SectionedListView)getListView()).setSectionedListAdapter(mAdapter);
            setupEmptyView();
            mAppSessionListener = new GroupMemberListListener();
        }
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        FacebookProfile facebookprofile = (FacebookProfile)mAdapter.getItem(getCursorPosition(i));
        Intent intent = ProfileTabHostActivity.intentForProfile(this, facebookprofile.mId);
        intent.putExtra("extra_user_display_name", facebookprofile.mDisplayName);
        intent.putExtra("extra_image_url", facebookprofile.mImageUrl);
        intent.putExtra("extra_user_type", 0);
        startActivity(intent);
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
            mGroupId = getIntent().getLongExtra("group_id", -1L);
            FqlGetProfile.RequestGroupMembers(this, mGroupId);
            logStepDataRequested();
            showProgress(true);
        }
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
            mProgressListener.onShowProgress(mShowingProgress);
    }

    public static final String EXTRA_GROUP_ID = "group_id";
    protected long mGroupId;
    protected TabProgressListener mProgressListener;
    protected boolean mShowingProgress;


}
