// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendsAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import android.view.*;
import com.facebook.katana.activity.profilelist.ProfileListNaiveCursorAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.PlatformFastTrack;
import com.facebook.katana.util.PlatformUtils;

public class FriendsAdapter extends ProfileListNaiveCursorAdapter
{

    public FriendsAdapter(Context context, ProfileImagesCache profileimagescache, Cursor cursor)
    {
        super(context, profileimagescache, cursor);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        View view1 = super.getChildView(i, j, flag, view, viewgroup);
        if(PlatformUtils.platformStorageSupported(mContext))
        {
            FacebookProfile facebookprofile = (FacebookProfile)getChild(i, j);
            View view2 = view1.findViewById(0x7f0e0033);
            String s = AppSession.getActiveSession(mContext, false).getSessionInfo().username;
            long l = facebookprofile.mId;
            String as[] = new String[1];
            as[0] = "vnd.android.cursor.item/vnd.facebook.profile";
            PlatformFastTrack.prepareBadge(view2, s, l, as);
        }
        return view1;
    }

    protected View inflateChildView(FacebookProfile facebookprofile)
    {
        View view1;
        if(!PlatformUtils.platformStorageSupported(mContext))
        {
            view1 = super.inflateChildView(facebookprofile);
        } else
        {
            View view = mInflater.inflate(0x7f030068, null);
            ((ViewStub)view.findViewById(0x7f0e0109)).inflate();
            view1 = view;
        }
        return view1;
    }
}
