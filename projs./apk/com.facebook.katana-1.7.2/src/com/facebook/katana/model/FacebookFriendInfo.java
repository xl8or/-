// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookFriendInfo.java

package com.facebook.katana.model;

import com.facebook.katana.util.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookFriendInfo extends FacebookUser
{

    public FacebookFriendInfo()
    {
    }

    public long computeHash()
    {
        Object aobj[] = new Object[10];
        aobj[0] = Long.valueOf(mUserId);
        aobj[1] = mFirstName;
        aobj[2] = mLastName;
        aobj[3] = mImageUrl;
        aobj[4] = mCellPhone;
        aobj[5] = mOtherPhone;
        aobj[6] = mContactEmail;
        aobj[7] = Integer.valueOf(mBirthdayMonth);
        aobj[8] = Integer.valueOf(mBirthdayDay);
        aobj[9] = Integer.valueOf(mBirthdayYear);
        return Utils.hashItemsLong(aobj);
    }

    public boolean hasPhoneNumber()
    {
        boolean flag;
        if(mCellPhone != null || mOtherPhone != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected void postprocess()
        throws JMException
    {
        if(mBirthday != null)
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("M/d/y", Locale.US);
            boolean flag = false;
            Date date = simpledateformat.parse(mBirthday, new ParsePosition(0));
            if(date != null)
                flag = true;
            else
                date = (new SimpleDateFormat("M/d", Locale.US)).parse(mBirthday, new ParsePosition(0));
            if(date != null)
            {
                setLong("mBirthdayMonth", 1 + date.getMonth());
                setLong("mBirthdayDay", date.getDate());
                if(flag)
                    setLong("mBirthdayYear", 1900 + date.getYear());
            }
        }
    }

    private final String mBirthday = null;
    public final int mBirthdayDay = -1;
    public final int mBirthdayMonth = -1;
    public final int mBirthdayYear = -1;
    public final String mCellPhone = null;
    public final String mContactEmail = null;
    public final String mOtherPhone = null;
}
