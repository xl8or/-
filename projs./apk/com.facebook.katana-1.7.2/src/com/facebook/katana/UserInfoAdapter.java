// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserInfoAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.model.FacebookUserFull;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            ProfileInfoAdapter

public class UserInfoAdapter extends ProfileInfoAdapter
{

    public UserInfoAdapter(Context context, StreamPhotosCache streamphotoscache, boolean flag, boolean flag1)
    {
        super(context, streamphotoscache, flag);
        mLimitedInfo = flag1;
    }

    protected static String formatRelationshipStatusString(Context context, FacebookUserFull facebookuserfull)
    {
        String s = facebookuserfull.mRelationshipStatus;
        String s1;
        if(s != null)
        {
            int ai[] = (int[])relationship_i18n_decoder.get(s);
            if(ai == null)
            {
                s1 = s;
            } else
            {
                FacebookUser facebookuser = facebookuserfull.getSignificantOther();
                if(facebookuser == null)
                {
                    s1 = context.getString(ai[0]);
                } else
                {
                    int i = ai[1];
                    Object aobj[] = new Object[1];
                    aobj[0] = facebookuser.getDisplayName();
                    s1 = context.getString(i, aobj);
                }
            }
        } else
        {
            s1 = null;
        }
        return s1;
    }

    public int getItemViewType(int i)
    {
        ((ProfileInfoAdapter.Item)mItems.get(i)).mType;
        JVM INSTR tableswitch 0 4: default 52
    //                   0 56
    //                   1 52
    //                   2 61
    //                   3 61
    //                   4 66;
           goto _L1 _L2 _L1 _L3 _L3 _L4
_L1:
        byte byte0 = 3;
_L6:
        return byte0;
_L2:
        byte0 = 0;
        continue; /* Loop/switch isn't completed */
_L3:
        byte0 = 1;
        continue; /* Loop/switch isn't completed */
_L4:
        byte0 = 2;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public int getViewTypeCount()
    {
        return 4;
    }

    public void setUserInfo(FacebookUserFull facebookuserfull, boolean flag)
    {
        mItems.clear();
        if(mShowProfilePhoto)
            mItems.add(new ProfileInfoAdapter.Item(0, facebookuserfull.mDisplayName, facebookuserfull.mBlurb, facebookuserfull.mLargePic));
        if(mLimitedInfo && !flag)
        {
            String s2 = facebookuserfull.mDisplayName;
            if(s2 == null || s2.length() == 0)
                s2 = mContext.getString(0x7f0a0067);
            List list1 = mItems;
            Context context = mContext;
            Object aobj[] = new Object[1];
            aobj[0] = s2;
            list1.add(new ProfileInfoAdapter.Item(1, s2, context.getString(0x7f0a0156, aobj)));
        }
        if(facebookuserfull.mCellPhone != null)
            mItems.add(new ProfileInfoAdapter.Item(2, mContext.getString(0x7f0a014b), PhoneNumberUtils.formatNumber(facebookuserfull.mCellPhone)));
        if(facebookuserfull.mOtherPhone != null)
            mItems.add(new ProfileInfoAdapter.Item(2, mContext.getString(0x7f0a015c), PhoneNumberUtils.formatNumber(facebookuserfull.mOtherPhone)));
        if(facebookuserfull.mContactEmail != null)
            mItems.add(new ProfileInfoAdapter.Item(3, mContext.getString(0x7f0a014d), facebookuserfull.mContactEmail));
        List list = facebookuserfull.mAffiliations;
        if(list != null && list.size() > 0)
        {
            StringBuilder stringbuilder = new StringBuilder(64);
            StringBuilder stringbuilder1 = new StringBuilder(64);
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                com.facebook.katana.model.FacebookUserFull.Affiliation affiliation = (com.facebook.katana.model.FacebookUserFull.Affiliation)iterator.next();
                if(affiliation.mType.equals("work"))
                {
                    if(stringbuilder1.length() > 0)
                        stringbuilder1.append(", ");
                    stringbuilder1.append(affiliation.mAffiliationName);
                } else
                {
                    if(stringbuilder.length() > 0)
                        stringbuilder.append("\n");
                    Integer integer = (Integer)affiliationStringMap.get(affiliation.mType);
                    String s1;
                    if(integer != null)
                        s1 = mContext.getString(integer.intValue());
                    else
                        s1 = affiliation.mType;
                    stringbuilder.append(s1).append(": ").append(affiliation.mAffiliationName);
                }
            }

            if(stringbuilder.length() > 0)
                mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0148), stringbuilder.toString()));
            if(stringbuilder1.length() > 0)
                mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0002), stringbuilder1.toString()));
        }
        if(facebookuserfull.mBirthday != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0149), facebookuserfull.mBirthday));
        String s = formatRelationshipStatusString(mContext, facebookuserfull);
        if(s != null)
            if(facebookuserfull.getSignificantOther() == null)
                mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0160), s));
            else
                mItems.add(new ProfileInfoAdapter.Item(4, mContext.getString(0x7f0a0160), s, facebookuserfull.getSignificantOther().mImageUrl, facebookuserfull.getSignificantOther().mUserId));
        if(facebookuserfull.mCurrentLocation != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a014c), facebookuserfull.mCurrentLocation));
        if(facebookuserfull.mHometownLocation != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0151), facebookuserfull.mHometownLocation));
        if(facebookuserfull.mReligion != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0173), facebookuserfull.mReligion));
        if(facebookuserfull.mPoliticalViews != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a015e), facebookuserfull.mPoliticalViews));
        if(facebookuserfull.mActivities != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0144), facebookuserfull.mActivities));
        if(facebookuserfull.mInterests != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0155), facebookuserfull.mInterests));
        if(facebookuserfull.mTv != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0176), facebookuserfull.mTv));
        if(facebookuserfull.mMovies != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0159), facebookuserfull.mMovies));
        if(facebookuserfull.mBooks != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a014a), facebookuserfull.mBooks));
        if(facebookuserfull.mQuotes != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a015f), facebookuserfull.mQuotes));
        if(facebookuserfull.mAboutMe != null)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0143), facebookuserfull.mAboutMe));
        notifyDataSetChanged();
    }

    static Map affiliationStringMap;
    protected static Map relationship_i18n_decoder;
    protected final boolean mLimitedInfo;

    static 
    {
        affiliationStringMap = new HashMap();
        affiliationStringMap.put("college", Integer.valueOf(0x7f0a0000));
        affiliationStringMap.put("high school", Integer.valueOf(0x7f0a0001));
        affiliationStringMap.put("regional", Integer.valueOf(0x7f0a0003));
        affiliationStringMap.put("test", Integer.valueOf(0x7f0a0004));
        affiliationStringMap.put("work", Integer.valueOf(0x7f0a0002));
        relationship_i18n_decoder = new HashMap() {

            public volatile Object get(Object obj)
            {
                return get(obj);
            }

            public int[] get(Object obj)
            {
                return (int[])super.get(obj.toString().toLowerCase());
            }

            public volatile Object put(Object obj, Object obj1)
            {
                return put((String)obj, (int[])obj1);
            }

            public int[] put(String s, int ai11[])
            {
                return (int[])super.put(s.toLowerCase(), ai11);
            }

            private static final long serialVersionUID = 1L;

        }
;
        Map map = relationship_i18n_decoder;
        int ai[] = new int[1];
        ai[0] = 0x7f0a0171;
        map.put("Single", ai);
        Map map1 = relationship_i18n_decoder;
        int ai1[] = new int[2];
        ai1[0] = 0x7f0a0168;
        ai1[1] = 0x7f0a016d;
        map1.put("In a Relationship", ai1);
        Map map2 = relationship_i18n_decoder;
        int ai2[] = new int[2];
        ai2[0] = 0x7f0a016e;
        ai2[1] = 0x7f0a016f;
        map2.put("Married", ai2);
        Map map3 = relationship_i18n_decoder;
        int ai3[] = new int[2];
        ai3[0] = 0x7f0a0166;
        ai3[1] = 0x7f0a0167;
        map3.put("Engaged", ai3);
        Map map4 = relationship_i18n_decoder;
        int ai4[] = new int[2];
        ai4[0] = 0x7f0a0169;
        ai4[1] = 0x7f0a016a;
        map4.put("It's Complicated", ai4);
        Map map5 = relationship_i18n_decoder;
        int ai5[] = new int[2];
        ai5[0] = 0x7f0a016b;
        ai5[1] = 0x7f0a016c;
        map5.put("In an Open Relationship", ai5);
        Map map6 = relationship_i18n_decoder;
        int ai6[] = new int[1];
        ai6[0] = 0x7f0a0172;
        map6.put("widowed", ai6);
        Map map7 = relationship_i18n_decoder;
        int ai7[] = new int[1];
        ai7[0] = 0x7f0a0170;
        map7.put("separated", ai7);
        Map map8 = relationship_i18n_decoder;
        int ai8[] = new int[1];
        ai8[0] = 0x7f0a0163;
        map8.put("divorced", ai8);
        Map map9 = relationship_i18n_decoder;
        int ai9[] = new int[2];
        ai9[0] = 0x7f0a0161;
        ai9[1] = 0x7f0a0162;
        map9.put("In a civil union", ai9);
        Map map10 = relationship_i18n_decoder;
        int ai10[] = new int[2];
        ai10[0] = 0x7f0a0164;
        ai10[1] = 0x7f0a0165;
        map10.put("In a domestic partnership", ai10);
    }
}
