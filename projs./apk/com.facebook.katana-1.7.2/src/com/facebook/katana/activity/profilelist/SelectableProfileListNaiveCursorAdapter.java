// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SelectableProfileListNaiveCursorAdapter.java

package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.database.Cursor;
import android.view.*;
import android.widget.CheckBox;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.Set;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListNaiveCursorAdapter

public class SelectableProfileListNaiveCursorAdapter extends ProfileListNaiveCursorAdapter
{

    public SelectableProfileListNaiveCursorAdapter(Context context, ProfileImagesCache profileimagescache, Cursor cursor, Set set)
    {
        super(context, profileimagescache, cursor);
        mTagged = set;
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        View view1 = super.getChildView(i, j, flag, view, viewgroup);
        FacebookProfile facebookprofile = (FacebookProfile)getChild(i, j);
        ((CheckBox)view1.findViewById(0x7f0e002f)).setChecked(mTagged.contains(Long.valueOf(facebookprofile.mId)));
        return view1;
    }

    protected View inflateChildView(FacebookProfile facebookprofile)
    {
        View view = super.inflateChildView(facebookprofile);
        ((ViewStub)view.findViewById(0x7f0e010a)).inflate();
        return view;
    }

    void toggle(int i, View view)
    {
        FacebookProfile facebookprofile = (FacebookProfile)getItem(i);
        CheckBox checkbox = (CheckBox)view.findViewById(0x7f0e002f);
        if(mTagged.contains(Long.valueOf(facebookprofile.mId)))
        {
            mTagged.remove(Long.valueOf(facebookprofile.mId));
            checkbox.setChecked(false);
        } else
        {
            mTagged.add(Long.valueOf(facebookprofile.mId));
            checkbox.setChecked(true);
        }
    }

    protected final Set mTagged;
}
