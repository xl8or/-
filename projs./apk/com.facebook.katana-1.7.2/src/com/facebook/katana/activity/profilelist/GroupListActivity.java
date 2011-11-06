// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupListActivity.java

package com.facebook.katana.activity.profilelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetGroups;
import com.facebook.katana.ui.SectionedListView;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListActivity, GroupListAdapter

public class GroupListActivity extends ProfileListActivity
{
    public class GroupsListListener extends ProfileListActivity.ProfileListListener
    {

        public void onGetGroupsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            if(i == 200)
                ((GroupListAdapter)mAdapter).updateProfileList(list);
            showProgress(false);
        }

        final GroupListActivity this$0;

        public GroupsListListener()
        {
            this$0 = GroupListActivity.this;
            super(GroupListActivity.this);
        }
    }


    public GroupListActivity()
    {
    }

    private void setupEmptyView()
    {
        TextView textview = (TextView)findViewById(0x7f0e0056);
        TextView textview1 = (TextView)findViewById(0x7f0e0058);
        textview.setText(0x7f0a007b);
        textview1.setText(0x7f0a007a);
    }

    private void showProgress(boolean flag)
    {
        if(flag)
            findViewById(0x7f0e00f1).setVisibility(0);
        else
            findViewById(0x7f0e00f1).setVisibility(8);
        setListLoading(flag);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030071);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mAdapter = new GroupListAdapter(this, mAppSession.getUserImagesCache());
            ((SectionedListView)getListView()).setSectionedListAdapter(mAdapter);
            setupEmptyView();
            mAppSessionListener = new GroupsListListener();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        return true;
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        FacebookProfile facebookprofile = (FacebookProfile)mAdapter.getItem(i);
        Intent intent = ProfileTabHostActivity.intentForProfile(this, facebookprofile.mId);
        intent.putExtra("extra_user_display_name", facebookprofile.mDisplayName);
        intent.putExtra("extra_image_url", facebookprofile.mImageUrl);
        intent.putExtra("extra_user_type", 3);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 2: default 24
    //                   2 32;
           goto _L1 _L2
_L1:
        boolean flag = super.onOptionsItemSelected(menuitem);
_L4:
        return flag;
_L2:
        FqlGetGroups.RequestGroups(this);
        showProgress(true);
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
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
            FqlGetGroups.RequestGroups(this);
            showProgress(true);
        }
    }

    private static final int REFRESH_ID = 2;

}
