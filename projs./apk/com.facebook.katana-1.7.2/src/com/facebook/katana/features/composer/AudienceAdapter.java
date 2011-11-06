// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AudienceAdapter.java

package com.facebook.katana.features.composer;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.facebook.katana.model.*;
import com.facebook.katana.service.method.AudienceSettings;
import com.facebook.katana.ui.SectionedListAdapter;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.features.composer:
//            AudienceOption

public class AudienceAdapter extends SectionedListAdapter
{
    public class FriendListAudienceOption
        implements AudienceOption
    {

        public FriendList getFriendList()
        {
            return mFriendList;
        }

        public int getIcon()
        {
            return 0x7f02000f;
        }

        public String getLabel()
        {
            return mFriendList.name;
        }

        public JSONObject getPrivacyObject()
        {
            JSONObject jsonobject1 = (new JSONObject()).put("value", "CUSTOM").put("friends", "SOME_FRIENDS").put("allow", mFriendList.flid);
            JSONObject jsonobject = jsonobject1;
_L2:
            return jsonobject;
            JSONException jsonexception;
            jsonexception;
            jsonobject = null;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public AudienceOption.Type getType()
        {
            return AudienceOption.Type.FRIEND_LIST;
        }

        final FriendList mFriendList;
        final AudienceAdapter this$0;

        public FriendListAudienceOption(FriendList friendlist)
        {
            this$0 = AudienceAdapter.this;
            super();
            mFriendList = friendlist;
        }
    }

    public class GroupAudienceOption
        implements AudienceOption
    {

        public FacebookGroup getGroup()
        {
            return mGroup;
        }

        public int getIcon()
        {
            return 0x7f02000d;
        }

        public String getLabel()
        {
            return mGroup.mDisplayName;
        }

        public AudienceOption.Type getType()
        {
            return AudienceOption.Type.GROUP;
        }

        final FacebookGroup mGroup;
        final AudienceAdapter this$0;

        public GroupAudienceOption(FacebookGroup facebookgroup)
        {
            this$0 = AudienceAdapter.this;
            super();
            mGroup = facebookgroup;
        }
    }

    public class PrivacySettingAudienceOption
        implements AudienceOption
    {

        public int getIcon()
        {
            int i;
            if(mSetting.value.equals("EVERYONE"))
                i = 0x7f02000a;
            else
            if(mSetting.value.equals("ALL_FRIENDS"))
                i = 0x7f02000c;
            else
            if(mSetting.value.equals("SELF"))
                i = 0x7f020010;
            else
            if(mSetting.value.equals("FRIENDS_OF_FRIENDS"))
                i = 0x7f02000b;
            else
            if(mSetting.value.equals("CUSTOM"))
                i = 0x7f020009;
            else
                i = -1;
            return i;
        }

        public String getLabel()
        {
            String s;
            if(mSetting.value.equals("EVERYONE"))
                s = mContext.getString(0x7f0a013c);
            else
            if(mSetting.value.equals("ALL_FRIENDS"))
                s = mContext.getString(0x7f0a013f);
            else
            if(mSetting.value.equals("SELF"))
                s = mContext.getString(0x7f0a0140);
            else
            if(mSetting.value.equals("FRIENDS_OF_FRIENDS"))
                s = mContext.getString(0x7f0a013d);
            else
            if(mSetting.value.equals("CUSTOM"))
                s = mSetting.description;
            else
                s = null;
            return s;
        }

        public PrivacySetting getPrivacySetting()
        {
            return mSetting;
        }

        public String getPrivacyValue()
        {
            return mSetting.value;
        }

        public AudienceOption.Type getType()
        {
            return AudienceOption.Type.PRIVACY_SETTING;
        }

        PrivacySetting mSetting;
        final AudienceAdapter this$0;

        public PrivacySettingAudienceOption(PrivacySetting privacysetting)
        {
            this$0 = AudienceAdapter.this;
            super();
            mSetting = privacysetting;
        }
    }


    public AudienceAdapter(Context context, boolean flag)
    {
        hasReceivedNewOptions = false;
        hasManuallySetAudience = false;
        AudienceOption.Type atype[] = new AudienceOption.Type[3];
        atype[0] = AudienceOption.Type.PRIVACY_SETTING;
        atype[1] = AudienceOption.Type.FRIEND_LIST;
        atype[2] = AudienceOption.Type.GROUP;
        mSections = atype;
        mContext = context;
        mIsMinor = flag;
        mInflater = LayoutInflater.from(context);
        ArrayList arraylist = new ArrayList();
        if(flag)
            arraylist.add(new PrivacySettingAudienceOption(new PrivacySetting("FRIENDS_OF_FRIENDS")));
        else
            arraylist.add(new PrivacySettingAudienceOption(new PrivacySetting("EVERYONE")));
        arraylist.add(new PrivacySettingAudienceOption(new PrivacySetting("ALL_FRIENDS")));
        mAudienceOptions = new HashMap();
        mAudienceOptions.put(AudienceOption.Type.PRIVACY_SETTING, arraylist);
    }

    public AudienceOption getChild(int i, int j)
    {
        return (AudienceOption)((List)mAudienceOptions.get(mSections[i])).get(j);
    }

    public volatile Object getChild(int i, int j)
    {
        return getChild(i, j);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        if(view == null)
            view1 = mInflater.inflate(0x7f030007, null);
        LinearLayout linearlayout = (LinearLayout)view1;
        TextView textview = (TextView)linearlayout.findViewById(0x7f0e0013);
        ImageView imageview = (ImageView)linearlayout.findViewById(0x7f0e0012);
        View view2 = linearlayout.findViewById(0x7f0e0014);
        AudienceOption audienceoption = (AudienceOption)((List)mAudienceOptions.get(mSections[i])).get(j);
        textview.setText(audienceoption.getLabel());
        imageview.setImageResource(audienceoption.getIcon());
        boolean flag1 = false;
        int k;
        if(mCurrentlySelectedPosition != null)
            if(mCurrentlySelectedPosition[0] == i && mCurrentlySelectedPosition[1] == j)
                flag1 = true;
            else
                flag1 = false;
        if(flag1)
            k = 0;
        else
            k = 8;
        view2.setVisibility(k);
        return linearlayout;
    }

    public int getChildViewType(int i, int j)
    {
        return 1;
    }

    public int getChildrenCount(int i)
    {
        List list = (List)mAudienceOptions.get(mSections[i]);
        int j;
        if(list != null)
            j = list.size();
        else
            j = 0;
        return j;
    }

    public int[] getCurrentlySelectedPosition()
    {
        return mCurrentlySelectedPosition;
    }

    public PrivacySetting getDefaultPrivacy()
    {
        return mDefaultPrivacy;
    }

    public int getIconForSelectedOption()
    {
        int i;
        if(mCurrentlySelectedPosition == null)
            i = -1;
        else
            i = getChild(mCurrentlySelectedPosition[0], mCurrentlySelectedPosition[1]).getIcon();
        return i;
    }

    public Object getSection(int i)
    {
        return mAudienceOptions.get(mSections[i]);
    }

    public int getSectionCount()
    {
        return mAudienceOptions.size();
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        TextView textview;
        String s;
        view1 = view;
        if(view == null)
            view1 = mInflater.inflate(0x7f03002e, null);
        textview = (TextView)view1;
        s = new String();
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type[];

            static 
            {
                $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type = new int[AudienceOption.Type.values().length];
                NoSuchFieldError nosuchfielderror2;
                try
                {
                    $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type[AudienceOption.Type.PRIVACY_SETTING.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type[AudienceOption.Type.GROUP.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                $SwitchMap$com$facebook$katana$features$composer$AudienceOption$Type[AudienceOption.Type.FRIEND_LIST.ordinal()] = 3;
_L2:
                return;
                nosuchfielderror2;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.com.facebook.katana.features.composer.AudienceOption.Type[mSections[i].ordinal()];
        JVM INSTR tableswitch 1 3: default 76
    //                   1 86
    //                   2 100
    //                   3 114;
           goto _L1 _L2 _L3 _L4
_L1:
        textview.setText(s);
        return view1;
_L2:
        s = mContext.getString(0x7f0a0030);
        continue; /* Loop/switch isn't completed */
_L3:
        s = mContext.getString(0x7f0a0032);
        continue; /* Loop/switch isn't completed */
_L4:
        s = mContext.getString(0x7f0a0031);
        if(true) goto _L1; else goto _L5
_L5:
    }

    public int getSectionHeaderViewType(int i)
    {
        return 0;
    }

    public AudienceSettings getServerAudienceSettings()
    {
        return mServerAudienceSettings;
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean isCurrentlySelectedAudienceGroup()
    {
        boolean flag;
        if(mCurrentlySelectedPosition != null && mSections[mCurrentlySelectedPosition[0]] == AudienceOption.Type.GROUP)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEmpty()
    {
        return false;
    }

    public boolean isEnabled(int i, int j)
    {
        return true;
    }

    public void setCurrentlySelectedPosition(int ai[])
    {
        mCurrentlySelectedPosition = ai;
    }

    public void setServerAudienceSettings(AudienceSettings audiencesettings)
    {
        mServerAudienceSettings = audiencesettings;
    }

    public void updateDefaultSetting(PrivacySetting privacysetting)
    {
        mDefaultPrivacy = privacysetting;
        if(privacysetting != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s = privacysetting.value;
        if(mIsMinor && s.equals("EVERYONE"))
            s = "FRIENDS_OF_FRIENDS";
        if("CUSTOM".equals(privacysetting.value) && "SOME_FRIENDS".equals(privacysetting.friends))
        {
            List list1 = (List)mAudienceOptions.get(AudienceOption.Type.FRIEND_LIST);
            int l = Arrays.asList(mSections).indexOf(AudienceOption.Type.FRIEND_LIST);
            int i1 = -1;
            int j1 = -1;
            Iterator iterator2 = list1.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AudienceOption audienceoption1 = (AudienceOption)iterator2.next();
                j1++;
                if(((FriendListAudienceOption)audienceoption1).getFriendList().flid != Long.valueOf(privacysetting.allow).longValue())
                    continue;
                i1 = j1;
                break;
            } while(true);
            if(l >= 0 && i1 >= 0)
            {
                int ai1[] = new int[2];
                ai1[0] = l;
                ai1[1] = i1;
                mCurrentlySelectedPosition = ai1;
                notifyDataSetChanged();
                continue; /* Loop/switch isn't completed */
            }
        }
        List list = (List)mAudienceOptions.get(AudienceOption.Type.PRIVACY_SETTING);
        boolean flag = false;
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            if(!((PrivacySettingAudienceOption)(AudienceOption)iterator.next()).getPrivacyValue().equals(s))
                continue;
            flag = true;
            break;
        } while(true);
        if(!flag)
        {
            PrivacySettingAudienceOption privacysettingaudienceoption = new PrivacySettingAudienceOption(privacysetting);
            list.add(privacysettingaudienceoption);
        }
        if(mCurrentlySelectedPosition != null && hasManuallySetAudience)
            continue; /* Loop/switch isn't completed */
        int i = Arrays.asList(mSections).indexOf(AudienceOption.Type.PRIVACY_SETTING);
        int j = -1;
        int k = -1;
        Iterator iterator1 = ((List)mAudienceOptions.get(AudienceOption.Type.PRIVACY_SETTING)).iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            AudienceOption audienceoption = (AudienceOption)iterator1.next();
            k++;
            if(!((PrivacySettingAudienceOption)audienceoption).getPrivacyValue().equals(s))
                continue;
            j = k;
            break;
        } while(true);
        if(i >= 0 && j >= 0)
        {
            int ai[] = new int[2];
            ai[0] = i;
            ai[1] = j;
            mCurrentlySelectedPosition = ai;
            notifyDataSetChanged();
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void updateFriendLists(List list)
    {
        if(list != null && list.size() != 0)
        {
            ArrayList arraylist = new ArrayList();
            for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(new FriendListAudienceOption((FriendList)iterator.next())));
            mAudienceOptions.put(AudienceOption.Type.FRIEND_LIST, arraylist);
        }
    }

    public void updateGroups(List list)
    {
        if(list != null && list.size() != 0)
        {
            ArrayList arraylist = new ArrayList();
            for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(new GroupAudienceOption((FacebookGroup)iterator.next())));
            mAudienceOptions.put(AudienceOption.Type.GROUP, arraylist);
        }
    }

    public boolean hasManuallySetAudience;
    public boolean hasReceivedNewOptions;
    private Map mAudienceOptions;
    private final Context mContext;
    private int mCurrentlySelectedPosition[];
    private PrivacySetting mDefaultPrivacy;
    private final LayoutInflater mInflater;
    private final boolean mIsMinor;
    private AudienceOption.Type mSections[];
    private AudienceSettings mServerAudienceSettings;

}
