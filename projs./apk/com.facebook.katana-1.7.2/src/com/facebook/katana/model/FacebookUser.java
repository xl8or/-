// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookUser.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookUser extends JMCachingDictDestination
    implements Parcelable
{

    protected FacebookUser()
    {
        mUserId = -1L;
        mFirstName = null;
        mLastName = null;
        mDisplayName = null;
        mImageUrl = null;
    }

    public FacebookUser(long l, String s, String s1, String s2, String s3)
    {
        mUserId = l;
        mFirstName = s;
        mLastName = s1;
        mDisplayName = s2;
        mImageUrl = s3;
    }

    public FacebookUser(Parcel parcel)
    {
        mUserId = parcel.readLong();
        mFirstName = parcel.readString();
        mLastName = parcel.readString();
        mDisplayName = parcel.readString();
        mImageUrl = parcel.readString();
    }

    public static FacebookUser newInstance(Class class1, String s)
    {
        FacebookUser facebookuser;
        FacebookUser facebookuser1;
        try
        {
            facebookuser1 = (FacebookUser)class1.newInstance();
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            facebookuser = null;
            continue; /* Loop/switch isn't completed */
        }
        catch(InstantiationException instantiationexception)
        {
            facebookuser = null;
            continue; /* Loop/switch isn't completed */
        }
        facebookuser1.setString("mDisplayName", s);
        facebookuser = facebookuser1;
_L2:
        return facebookuser;
        JMException jmexception;
        jmexception;
        facebookuser = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static FacebookUser parseFromJSON(Class class1, JsonParser jsonparser)
        throws JsonParseException, IOException, JMException
    {
        Object obj = JMParser.parseJsonResponse(jsonparser, JMAutogen.generateJMParser(class1));
        FacebookUser facebookuser;
        if(obj instanceof FacebookUser)
            facebookuser = (FacebookUser)obj;
        else
            facebookuser = null;
        return facebookuser;
    }

    public int describeContents()
    {
        return 0;
    }

    public String getDisplayName()
    {
        String s;
        if(mDisplayName == null)
        {
            Log.e("FacebookUser", "display name was requested, but is null");
            s = "";
        } else
        {
            s = mDisplayName;
        }
        return s;
    }

    public JSONObject toJSONObject()
    {
        JSONObject jsonobject;
        jsonobject = new JSONObject();
        jsonobject.put("uid", mUserId);
        jsonobject.put("first_name", mFirstName);
        jsonobject.put("last_name", mLastName);
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

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mUserId);
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mDisplayName);
        parcel.writeString(mImageUrl);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookUser createFromParcel(Parcel parcel)
        {
            return new FacebookUser(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookUser[] newArray(int i)
        {
            return new FacebookUser[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public static final long INVALID_ID = -1L;
    public static final String TAG = "FacebookUser";
    public final String mDisplayName;
    public final String mFirstName;
    public final String mImageUrl;
    public final String mLastName;
    public final long mUserId;

}
