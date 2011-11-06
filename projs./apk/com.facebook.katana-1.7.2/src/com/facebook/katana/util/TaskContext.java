// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TaskContext.java

package com.facebook.katana.util;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskContext
    implements Parcelable
{

    public TaskContext()
    {
    }

    private TaskContext(Parcel parcel)
    {
        boolean flag;
        boolean flag1;
        if(parcel.readByte() == 1)
            flag = true;
        else
            flag = false;
        sentRequest = flag;
        if(parcel.readByte() == 1)
            flag1 = true;
        else
            flag1 = false;
        receivedResponse = flag1;
    }


    public void clear()
    {
        sentRequest = false;
        receivedResponse = false;
    }

    public int describeContents()
    {
        return 0;
    }

    public void reset()
    {
        if(sentRequest && !receivedResponse)
            sentRequest = false;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        int j;
        int k;
        if(sentRequest)
            j = 1;
        else
            j = 0;
        parcel.writeByte((byte)j);
        if(receivedResponse)
            k = 1;
        else
            k = 0;
        parcel.writeByte((byte)k);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public TaskContext createFromParcel(Parcel parcel)
        {
            return new TaskContext(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public TaskContext[] newArray(int i)
        {
            return new TaskContext[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public boolean receivedResponse;
    public boolean sentRequest;

}
