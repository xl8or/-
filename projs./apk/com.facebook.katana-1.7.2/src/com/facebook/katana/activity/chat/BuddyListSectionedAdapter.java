// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BuddyListSectionedAdapter.java

package com.facebook.katana.activity.chat;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.ui.SectionedListAdapter;
import java.util.*;

public class BuddyListSectionedAdapter extends SectionedListAdapter
{

    public BuddyListSectionedAdapter(Context context, ProfileImagesCache profileimagescache)
    {
        mQueryView = false;
        mContext = context;
        mUserImagesCache = profileimagescache;
        for(int i = 0; i < 3; i++)
            sectionChildren[i] = new ArrayList();

        sectionData[0] = context.getString(0x7f0a0025);
        sectionData[1] = context.getString(0x7f0a0026);
        sectionData[2] = context.getString(0x7f0a0027);
    }

    public Object getChild(int i, int j)
    {
        int k = ((Integer)activeSections.get(i)).intValue();
        return sectionChildren[k].get(j);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        View view1;
        Long long1;
        ImageView imageview;
        int k = ((Integer)activeSections.get(i)).intValue();
        ViewHolder viewholder;
        FacebookChatUser facebookchatuser;
        String s;
        if(view == null || view.getTag() == null)
        {
            view1 = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f03000a, null);
        } else
        {
            view1 = view;
            mViewHolders.remove(view.getTag());
        }
        viewholder = new ViewHolder(view1, 0x7f0e001a);
        view1.setTag(viewholder);
        mViewHolders.add(viewholder);
        facebookchatuser = (FacebookChatUser)sectionChildren[k].get(j);
        long1 = Long.valueOf(facebookchatuser.mUserId);
        viewholder.setItemId(long1);
        s = facebookchatuser.mImageUrl;
        if(s != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, long1.longValue(), s);
            class _cls1
            {

                static final int $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence[];

                static 
                {
                    $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence = new int[com.facebook.katana.model.FacebookChatUser.Presence.values().length];
                    NoSuchFieldError nosuchfielderror1;
                    try
                    {
                        $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence[com.facebook.katana.model.FacebookChatUser.Presence.AVAILABLE.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    $SwitchMap$com$facebook$katana$model$FacebookChatUser$Presence[com.facebook.katana.model.FacebookChatUser.Presence.IDLE.ordinal()] = 2;
_L2:
                    return;
                    nosuchfielderror1;
                    if(true) goto _L2; else goto _L1
_L1:
                }
            }

            String s1;
            TextView textview;
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        s1 = facebookchatuser.getDisplayName();
        if(s1 == null)
            s1 = mContext.getString(0x7f0a0067);
        ((TextView)view1.findViewById(0x7f0e001c)).setText(s1);
        imageview = (ImageView)view1.findViewById(0x7f0e0020);
        _cls1..SwitchMap.com.facebook.katana.model.FacebookChatUser.Presence[facebookchatuser.mPresence.ordinal()];
        JVM INSTR tableswitch 1 2: default 248
    //                   1 473
    //                   2 483;
           goto _L1 _L2 _L3
_L3:
        break MISSING_BLOCK_LABEL_483;
_L1:
        imageview.setImageResource(0x7f020045);
_L4:
        if(mConversations.containsKey(long1) && ((com.facebook.katana.model.FacebookChatUser.UnreadConversation)mConversations.get(long1)).mUnreadCount > 0)
        {
            ((TextView)view1.findViewById(0x7f0e001e)).setText((new StringBuilder()).append(((com.facebook.katana.model.FacebookChatUser.UnreadConversation)mConversations.get(long1)).mUnreadCount).append("").toString());
            ((TextView)view1.findViewById(0x7f0e001e)).setVisibility(0);
            textview = (TextView)view1.findViewById(0x7f0e001f);
            String s2;
            if(((com.facebook.katana.model.FacebookChatUser.UnreadConversation)mConversations.get(long1)).mMessage == null)
                s2 = "";
            else
                s2 = ((com.facebook.katana.model.FacebookChatUser.UnreadConversation)mConversations.get(long1)).mMessage;
            textview.setText(s2);
            ((TextView)view1.findViewById(0x7f0e001f)).setVisibility(0);
            ((LinearLayout)view1.findViewById(0x7f0e001d)).setVisibility(0);
        } else
        {
            ((TextView)view1.findViewById(0x7f0e001e)).setVisibility(8);
            ((TextView)view1.findViewById(0x7f0e001f)).setVisibility(8);
            ((LinearLayout)view1.findViewById(0x7f0e001d)).setVisibility(8);
        }
        return view1;
_L2:
        imageview.setImageResource(0x7f02003e);
          goto _L4
        imageview.setImageResource(0x7f020044);
          goto _L4
    }

    public int getChildViewType(int i, int j)
    {
        return 1;
    }

    public int getChildrenCount(int i)
    {
        int j = ((Integer)activeSections.get(i)).intValue();
        return sectionChildren[j].size();
    }

    public Object getSection(int i)
    {
        int j = ((Integer)activeSections.get(i)).intValue();
        return sectionData[j];
    }

    public int getSectionCount()
    {
        return activeSections.size();
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        int j = ((Integer)activeSections.get(i)).intValue();
        View view1;
        if(view == null || view.getTag() == null)
        {
            view1 = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030008, null);
        } else
        {
            view1 = view;
            mViewHolders.remove(view.getTag());
        }
        if(sectionData[j].length() == 0)
            view1.setVisibility(8);
        else
            ((TextView)view1).setText(sectionData[j]);
        return view1;
    }

    public int getSectionHeaderViewType(int i)
    {
        return 0;
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(sectionChildren.length == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled(int i, int j)
    {
        boolean flag;
        if(j != -1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void redraw(Collection collection, Map map, boolean flag)
    {
        mConversations = map;
        mQueryView = flag;
        activeSections.clear();
        for(int i = 0; i < 3; i++)
            sectionChildren[i].clear();

        Iterator iterator = collection.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookChatUser facebookchatuser = (FacebookChatUser)iterator.next();
            if(facebookchatuser != null)
            {
                long l = facebookchatuser.mUserId;
                boolean flag1 = false;
                if(mConversations.containsKey(Long.valueOf(l)))
                {
                    sectionChildren[0].add(facebookchatuser);
                    flag1 = true;
                }
                if(!mQueryView || !flag1)
                    if(facebookchatuser.mPresence == com.facebook.katana.model.FacebookChatUser.Presence.AVAILABLE)
                        sectionChildren[1].add(facebookchatuser);
                    else
                    if(facebookchatuser.mPresence == com.facebook.katana.model.FacebookChatUser.Presence.IDLE)
                        sectionChildren[2].add(facebookchatuser);
            }
        } while(true);
        if(sectionChildren[0].size() > 0)
            activeSections.add(Integer.valueOf(0));
        if(sectionChildren[1].size() > 0)
            activeSections.add(Integer.valueOf(1));
        if(sectionChildren[2].size() > 0)
            activeSections.add(Integer.valueOf(2));
        notifyDataSetChanged();
    }

    public void updatePresenceIcon(Long long1, int i)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            Long long2 = (Long)viewholder.getItemId();
            if(long2 != null && long2.equals(long1))
                viewholder.mImageView.setImageResource(i);
        } while(true);
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

    private static final int ACTIVE_SECTION_INDEX = 0;
    private static final int AVAILABLE_SECTION_INDEX = 1;
    private static final int IDLE_SECTION_INDEX = 2;
    private static final int SECTION_NUMBER = 3;
    private final List activeSections = new ArrayList(3);
    private final Context mContext;
    private Map mConversations;
    private boolean mQueryView;
    private final ProfileImagesCache mUserImagesCache;
    private final List mViewHolders = new ArrayList();
    private final ArrayList sectionChildren[] = new ArrayList[3];
    private final String sectionData[] = new String[3];
}
