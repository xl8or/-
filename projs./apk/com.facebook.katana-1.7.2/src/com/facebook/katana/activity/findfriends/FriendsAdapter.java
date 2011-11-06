// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendsAdapter.java

package com.facebook.katana.activity.findfriends;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.model.FacebookPhonebookContactUser;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.findfriends:
//            BaseAdapter

public class FriendsAdapter extends BaseAdapter
{

    public FriendsAdapter(Context context, ArrayList arraylist, ProfileImagesCache profileimagescache)
    {
        super(context);
        mAllContacts = arraylist;
        mUserImagesCache = profileimagescache;
        setAllContacts(arraylist);
    }

    protected String getActionTakenString()
    {
        return mContext.getString(0x7f0a0290);
    }

    protected String getContactAddress(FacebookPhonebookContact facebookphonebookcontact)
    {
        return "";
    }

    protected long getContactId(FacebookPhonebookContact facebookphonebookcontact)
    {
        return facebookphonebookcontact.userId;
    }

    protected String getSelectButtonText()
    {
        return mContext.getString(0x7f0a0021);
    }

    protected void setupPic(View view, FacebookPhonebookContact facebookphonebookcontact, boolean flag)
    {
        FacebookPhonebookContactUser facebookphonebookcontactuser = (FacebookPhonebookContactUser)facebookphonebookcontact;
        ViewHolder viewholder;
        String s;
        if(flag)
        {
            viewholder = new ViewHolder(view, 0x7f0e0033);
            mViewHolders.add(viewholder);
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        viewholder.setItemId(Long.valueOf(facebookphonebookcontactuser.userId));
        s = facebookphonebookcontactuser.imageUrl;
        if(s != null && s.length() != 0)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, facebookphonebookcontactuser.userId, s);
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
    }

    protected final ProfileImagesCache mUserImagesCache;
}
