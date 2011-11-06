// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatMessageAdapter.java

package com.facebook.katana.activity.chat;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import java.util.*;

public class ChatMessageAdapter extends ArrayAdapter
{

    public ChatMessageAdapter(Context context, FacebookChatUser facebookchatuser, FacebookChatUser facebookchatuser1)
    {
        super(context, 0x7f03000c, new ArrayList());
        lastTag = 0xfffe2b40L;
        mContext = context;
        me = facebookchatuser;
        recepient = facebookchatuser1;
    }

    public void add(FacebookChatMessage facebookchatmessage)
    {
        if(facebookchatmessage.mTimestamp.longValue() - lastTag > 0x1d4c0L)
        {
            lastTag = facebookchatmessage.mTimestamp.longValue();
            timeTag.add(Boolean.valueOf(true));
        } else
        {
            timeTag.add(Boolean.valueOf(false));
        }
        super.add(facebookchatmessage);
    }

    public volatile void add(Object obj)
    {
        add((FacebookChatMessage)obj);
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        if(view == null)
        {
            view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f03000c, null);
            ViewHolder viewholder1 = new ViewHolder(view, 0x7f0e002a);
            view.setTag(viewholder1);
            mViewHolders.add(viewholder1);
        }
        FacebookChatMessage facebookchatmessage = (FacebookChatMessage)getItem(i);
        long l = facebookchatmessage.mSenderUid;
        ViewHolder viewholder = (ViewHolder)view.getTag();
        TextView textview;
        FacebookChatUser facebookchatuser;
        android.graphics.Bitmap bitmap;
        if(((Boolean)timeTag.get(i)).booleanValue())
        {
            long l1 = facebookchatmessage.mTimestamp.longValue();
            ((TextView)view.findViewById(0x7f0e0028)).setText(DateUtils.formatDateTime(mContext, l1, 2577));
            view.findViewById(0x7f0e0027).setVisibility(0);
        } else
        {
            view.findViewById(0x7f0e0027).setVisibility(8);
        }
        viewholder.setItemId(Long.valueOf(l));
        textview = (TextView)view.findViewById(0x7f0e002b);
        if(l == me.mUserId)
        {
            facebookchatuser = me;
            textview.setBackgroundResource(0x7f02003f);
        } else
        {
            facebookchatuser = recepient;
            textview.setBackgroundResource(0x7f02004b);
        }
        textview.setText(facebookchatmessage.mBody);
        bitmap = AppSession.getActiveSession(mContext, false).getUserImagesCache().get(mContext, facebookchatuser.mUserId, facebookchatuser.mImageUrl);
        if(bitmap == null)
            viewholder.mImageView.setImageResource(0x7f0200f3);
        else
            viewholder.mImageView.setImageBitmap(bitmap);
        viewholder.mImageView.setTag(Long.valueOf(l));
        return view;
    }

    public void updateUserImage(ProfileImage profileimage)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            Long long1 = (Long)viewholder.getItemId();
            if(long1 != null && long1.equals(Long.valueOf(profileimage.id)))
                viewholder.mImageView.setImageBitmap(profileimage.getBitmap());
        } while(true);
    }

    private static final int TIME_PERIOD = 0x1d4c0;
    private long lastTag;
    private final Context mContext;
    private final List mViewHolders = new ArrayList();
    private final FacebookChatUser me;
    private final FacebookChatUser recepient;
    private final ArrayList timeTag = new ArrayList();
}
