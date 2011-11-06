// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookChatUser.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookChatUser extends FacebookUser
    implements Comparable, Parcelable
{
    public static class UnreadConversation
    {

        public void addMessage(String s)
        {
            mMessage = s;
            mUnreadCount = 1 + mUnreadCount;
        }

        public void clear()
        {
            mMessage = null;
            mUnreadCount = 0;
        }

        public String mMessage;
        public int mUnreadCount;

        public UnreadConversation(String s, int i)
        {
            mMessage = s;
            mUnreadCount = i;
        }
    }

    public static final class Presence extends Enum
    {

        public static Presence valueOf(String s)
        {
            return (Presence)Enum.valueOf(com/facebook/katana/model/FacebookChatUser$Presence, s);
        }

        public static Presence[] values()
        {
            return (Presence[])$VALUES.clone();
        }

        private static final Presence $VALUES[];
        public static final Presence AVAILABLE;
        public static final Presence IDLE;
        public static final Presence OFFLINE;

        static 
        {
            AVAILABLE = new Presence("AVAILABLE", 0);
            IDLE = new Presence("IDLE", 1);
            OFFLINE = new Presence("OFFLINE", 2);
            Presence apresence[] = new Presence[3];
            apresence[0] = AVAILABLE;
            apresence[1] = IDLE;
            apresence[2] = OFFLINE;
            $VALUES = apresence;
        }

        private Presence(String s, int i)
        {
            super(s, i);
        }
    }


    public FacebookChatUser(long l, Presence presence)
    {
        mPresence = Presence.OFFLINE;
        infoInitialized = false;
        try
        {
            setLong("mUserId", l);
        }
        catch(Exception exception) { }
        mPresence = presence;
    }

    public FacebookChatUser(Parcel parcel)
    {
        mPresence = Presence.OFFLINE;
        infoInitialized = false;
        setLong("mUserId", parcel.readLong());
        setString("mFirstName", parcel.readString());
        setString("mLastName", parcel.readString());
        setString("mDisplayName", parcel.readString());
        setString("mImageUrl", parcel.readString());
        mPresence = (Presence)parcel.readValue(null);
        infoInitialized = true;
_L2:
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public FacebookChatUser(Presence presence, long l, String s, String s1, String s2, String s3)
    {
        super(l, s, s1, s2, s3);
        mPresence = Presence.OFFLINE;
        infoInitialized = false;
        mPresence = presence;
        infoInitialized = true;
    }

    public static String getJid(Long long1)
    {
        return (new StringBuilder()).append("-").append(long1).append("@chat.facebook.com").toString();
    }

    public static long getUid(String s)
    {
        return Long.parseLong(s.split("@")[0].substring(1));
    }

    public int compareTo(Object obj)
    {
        int i;
        if(obj == null)
            i = 1;
        else
        if(mDisplayName == null)
        {
            i = -1;
        } else
        {
            if(!(obj instanceof FacebookUser))
                throw new ClassCastException();
            FacebookUser facebookuser = (FacebookUser)obj;
            if(facebookuser.mDisplayName == null)
                i = 1;
            else
                i = mDisplayName.compareTo(facebookuser.mDisplayName);
        }
        return i;
    }

    public int describeContents()
    {
        return 0;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(this == obj)
            flag = true;
        else
        if(obj == null)
            flag = false;
        else
        if(getClass() != obj.getClass())
            flag = false;
        else
        if(((FacebookChatUser)obj).mUserId == mUserId)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int hashCode()
    {
        return (int)(mUserId ^ mUserId >>> 32);
    }

    public void setPresence(Presence presence)
    {
        mPresence = presence;
    }

    public void setUserInfo(String s, String s1, String s2, String s3)
    {
        setString("mFirstName", s);
        setString("mLastName", s1);
        setString("mDisplayName", s2);
        setString("mImageUrl", s3);
        infoInitialized = true;
_L2:
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String toString()
    {
        return (new StringBuilder()).append("Chat User [").append(mUserId).append(": ").append(mDisplayName).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mUserId);
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mDisplayName);
        parcel.writeString(mImageUrl);
        parcel.writeValue(mPresence);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookChatUser createFromParcel(Parcel parcel)
        {
            return new FacebookChatUser(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookChatUser[] newArray(int i)
        {
            return new FacebookChatUser[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public boolean infoInitialized;
    public Presence mPresence;

}
