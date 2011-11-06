// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InvitesAdapter.java

package com.facebook.katana.activity.findfriends;

import android.content.Context;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.util.GrowthUtils;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.findfriends:
//            BaseAdapter

public class InvitesAdapter extends BaseAdapter
{

    public InvitesAdapter(Context context, ArrayList arraylist, Spanned spanned)
    {
        super(context);
        mAllContacts = arraylist;
        mLegalDisclaimer = spanned;
        mShowLegalBar = GrowthUtils.shouldShowLegalBar(mContext);
        setAllContacts(arraylist);
    }

    protected String getActionTakenString()
    {
        return mContext.getString(0x7f0a0291);
    }

    protected long getContactId(FacebookPhonebookContact facebookphonebookcontact)
    {
        return facebookphonebookcontact.recordId;
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        if(!mShowLegalBar)
            view1 = super.getSectionHeaderView(i, view, viewgroup);
        else
        if(i == 0)
        {
            if(!mLegalSettingSaved)
            {
                mLegalSettingSaved = true;
                GrowthUtils.setLegalBarShown(mContext);
            }
            View view2 = view;
            if(view2 == null || !(view2 instanceof LinearLayout))
                view2 = mInflater.inflate(0x7f030025, null);
            TextView textview = (TextView)view2.findViewById(0x7f0e007a);
            textview.setText(mLegalDisclaimer);
            textview.setMovementMethod(LinkMovementMethod.getInstance());
            ((TextView)view2.findViewById(0x7f0e0065)).setText(((BaseAdapter.FirstLetterFriendSection)mSections.get(i)).toString());
            view1 = view2;
        } else
        if(view == null || (view instanceof TextView))
            view1 = super.getSectionHeaderView(i, view, viewgroup);
        else
            view1 = super.getSectionHeaderView(i, null, null);
        return view1;
    }

    protected String getSelectButtonText()
    {
        return mContext.getString(0x7f0a028b);
    }

    protected void setupPic(View view, FacebookPhonebookContact facebookphonebookcontact, boolean flag)
    {
        view.findViewById(0x7f0e0033).setVisibility(8);
    }

    private Spanned mLegalDisclaimer;
    private boolean mLegalSettingSaved;
    private boolean mShowLegalBar;
}
