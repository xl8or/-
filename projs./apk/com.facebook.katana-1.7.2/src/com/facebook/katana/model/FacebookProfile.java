// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookProfile.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookProfile extends JMCachingDictDestination
    implements Parcelable
{

    public FacebookProfile()
    {
        mId = -1L;
        mDisplayName = null;
        mImageUrl = null;
        mType = 0;
    }

    public FacebookProfile(long l, String s, String s1, int i)
    {
        mId = l;
        mDisplayName = s;
        mImageUrl = s1;
        mType = i;
    }

    protected FacebookProfile(Parcel parcel)
    {
        mId = parcel.readLong();
        mDisplayName = parcel.readString();
        mImageUrl = parcel.readString();
        mType = parcel.readInt();
    }

    public FacebookProfile(FacebookUser facebookuser)
    {
        mId = facebookuser.mUserId;
        mDisplayName = facebookuser.mDisplayName;
        mImageUrl = facebookuser.mImageUrl;
        mType = 0;
    }

    public static FacebookProfile parseJson(JsonParser jsonparser)
        throws JsonParseException, IOException, JMException
    {
        return (FacebookProfile)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookProfile);
    }

    public int describeContents()
    {
        return 0;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if((obj instanceof FacebookProfile) && mId == ((FacebookProfile)obj).mId)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int hashCode()
    {
        return (int)mId;
    }

    protected void postprocess()
        throws JMException
    {
        if(mTypeString != null && mTypeString.equals("page"))
            setLong("mType", 1L);
        mTypeString = null;
    }

    public JSONObject toJSONObject()
    {
        JSONObject jsonobject;
        jsonobject = new JSONObject();
        jsonobject.put("id", mId);
        jsonobject.put("name", mDisplayName);
        jsonobject.put("pic_square", mImageUrl);
        JSONObject jsonobject1 = jsonobject;
_L2:
        return jsonobject1;
        JSONException jsonexception;
        jsonexception;
        jsonobject1 = new JSONObject();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("FacebookProfile(").append(mDisplayName).append(" (id=").append(mId).append("))");
        return stringbuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mId);
        parcel.writeString(mDisplayName);
        parcel.writeString(mImageUrl);
        parcel.writeInt(mType);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookProfile createFromParcel(Parcel parcel)
        {
            return new FacebookProfile(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookProfile[] newArray(int i)
        {
            return new FacebookProfile[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public static final long INVALID_ID = -1L;
    public static final int TYPE_EVENT = 4;
    public static final int TYPE_GROUP = 3;
    public static final int TYPE_PAGE = 1;
    public static final int TYPE_PLACE_PAGE = 2;
    public static final int TYPE_USER;
    public final String mDisplayName;
    public final long mId;
    public final String mImageUrl;
    public final int mType;
    private String mTypeString;

}
