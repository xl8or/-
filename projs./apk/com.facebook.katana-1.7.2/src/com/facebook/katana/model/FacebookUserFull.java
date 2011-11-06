// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookUserFull.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.types.JMString;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookUserFull extends FacebookUser
{
    public static class JMTrimWhiteAndNLString extends JMString
    {

        public String formatString(String s)
        {
            String s1 = FacebookUserFull.trimWhite(s, false);
            String s2;
            if(s1.length() == 0)
                s2 = null;
            else
                s2 = s1;
            return s2;
        }

        public JMTrimWhiteAndNLString()
        {
        }
    }

    public static class JMTrimWhiteString extends JMString
    {

        public String formatString(String s)
        {
            String s1 = FacebookUserFull.trimWhite(s, true);
            String s2;
            if(s1.length() == 0)
                s2 = null;
            else
                s2 = s1;
            return s2;
        }

        public JMTrimWhiteString()
        {
        }
    }

    public static class Affiliation extends JMCachingDictDestination
    {

        public final String mAffiliationName = null;
        public final String mStatus = null;
        public final String mType = null;

        private Affiliation()
        {
        }
    }


    private FacebookUserFull()
    {
    }

    protected static String parse_location(Map map)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Object obj = map.get("city");
        Object obj1 = map.get("state");
        String s;
        if(obj != null && obj1 != null)
        {
            stringbuilder.append(obj).append(", ").append(obj1);
            s = stringbuilder.toString();
        } else
        if(obj != null)
            s = obj.toString();
        else
        if(obj1 != null)
            s = obj1.toString();
        else
            s = null;
        return s;
    }

    protected static String trimWhite(String s, boolean flag)
    {
        String s1;
        StringBuffer stringbuffer;
        int i;
        if(flag)
            s1 = s.replaceAll("\n", ", ");
        else
            s1 = s.replaceAll("[\r|\013|\f]", "");
        stringbuffer = new StringBuffer(128);
        i = 0;
label0:
        do
        {
label1:
            {
                if(i < s1.length())
                {
                    char c1 = s1.charAt(i);
                    if(c1 == ' ' || c1 == ',' || c1 == '\n')
                        break label1;
                    stringbuffer.append(s1.subSequence(i, s1.length()));
                }
                int j = stringbuffer.length() - 1;
                do
                {
                    if(j < 0)
                        break;
                    char c = stringbuffer.charAt(j);
                    if(c != ' ' && c != ',' && c != '\n')
                        break;
                    stringbuffer.deleteCharAt(j);
                    j--;
                } while(true);
                break label0;
            }
            i++;
        } while(true);
        return stringbuffer.toString();
    }

    public FacebookUser getSignificantOther()
    {
        return mSignificantOther;
    }

    protected void postprocess()
        throws JMException
    {
        if(mCurrentLocation_internal != null)
            setString("mCurrentLocation", parse_location(mCurrentLocation_internal));
        if(mHometownLocation_internal != null)
            setString("mHometownLocation", parse_location(mHometownLocation_internal));
    }

    public void setSignificantOther(FacebookUser facebookuser)
    {
        mSignificantOther = facebookuser;
    }

    public final String mAboutMe = null;
    public final String mActivities = null;
    public final List mAffiliations = null;
    public final String mBirthday = null;
    public final String mBlurb = null;
    public final String mBooks = null;
    public final String mCellPhone = null;
    public final String mContactEmail = null;
    public final String mCurrentLocation = null;
    private final Map mCurrentLocation_internal = null;
    public final String mHometownLocation = null;
    private final Map mHometownLocation_internal = null;
    public final String mInterests = null;
    public final String mLargePic = null;
    public final String mMovies = null;
    public final String mMusic = null;
    public final String mOtherPhone = null;
    public final String mPoliticalViews = null;
    public final String mQuotes = null;
    public final String mRelationshipStatus = null;
    public final String mReligion = null;
    protected FacebookUser mSignificantOther;
    public final long mSignificantOtherId = -1L;
    public final String mTv = null;
    public final String mUrl = null;
}
