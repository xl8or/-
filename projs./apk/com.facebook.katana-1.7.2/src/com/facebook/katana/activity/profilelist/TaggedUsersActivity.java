// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaggedUsersActivity.java

package com.facebook.katana.activity.profilelist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.ui.SectionedListView;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListActivity, ProfileListDynamicAdapter

public class TaggedUsersActivity extends ProfileListActivity
{

    public TaggedUsersActivity()
    {
    }

    private void setupEmptyView()
    {
        TextView textview = (TextView)findViewById(0x7f0e0056);
        TextView textview1 = (TextView)findViewById(0x7f0e0058);
        textview.setText(0x7f0a0070);
        textview1.setText(0x7f0a006f);
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
            mProfiles = getIntent().getParcelableArrayListExtra("profiles");
            mAdapter = new ProfileListDynamicAdapter(this, mAppSession.getUserImagesCache());
            ((ProfileListDynamicAdapter)mAdapter).updateProfileList(mProfiles);
            ((SectionedListView)getListView()).setSectionedListAdapter(mAdapter);
            setupEmptyView();
            mAppSessionListener = new ProfileListActivity.ProfileListListener(this);
        }
    }

    public static final String EXTRA_PROFILES = "profiles";
    protected List mProfiles;
}
