// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GeoRegion.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Iterator;
import java.util.List;

public class GeoRegion extends JMCachingDictDestination
{
    public static class ImplicitLocation
        implements Parcelable
    {

        public int describeContents()
        {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i)
        {
            parcel.writeString(label);
            parcel.writeLong(pageId.longValue());
        }

        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

            public ImplicitLocation createFromParcel(Parcel parcel)
            {
                return new ImplicitLocation(parcel);
            }

            public volatile Object createFromParcel(Parcel parcel)
            {
                return createFromParcel(parcel);
            }

            public ImplicitLocation[] newArray(int i)
            {
                return new ImplicitLocation[i];
            }

            public volatile Object[] newArray(int i)
            {
                return newArray(i);
            }

        }
;
        public String label;
        public Long pageId;


        public ImplicitLocation(Parcel parcel)
        {
            label = parcel.readString();
            pageId = Long.valueOf(parcel.readLong());
        }

        public ImplicitLocation(String s, Long long1)
        {
            label = s;
            pageId = long1;
        }
    }

    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(com/facebook/katana/model/GeoRegion$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type city;
        public static final Type state;

        static 
        {
            city = new Type("city", 0);
            state = new Type("state", 1);
            Type atype[] = new Type[2];
            atype[0] = city;
            atype[1] = state;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    private GeoRegion()
    {
    }

    public static ImplicitLocation createImplicitLocation(List list)
    {
        if(list != null) goto _L2; else goto _L1
_L1:
        ImplicitLocation implicitlocation = null;
_L5:
        return implicitlocation;
_L2:
        String s;
        String s1;
        Long long1;
        String s2;
        s = null;
        s1 = null;
        long1 = null;
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            GeoRegion georegion = (GeoRegion)iterator.next();
            if(georegion.type.equals(Type.city.toString()))
            {
                s = georegion.abbrName;
                long1 = Long.valueOf(georegion.id);
            } else
            if(georegion.type.equals(Type.state.toString()))
                s1 = georegion.abbrName;
        } while(true);
        s2 = null;
        if(s == null) goto _L4; else goto _L3
_L3:
        s2 = s;
_L6:
        if(s2 != null)
            implicitlocation = new ImplicitLocation(s2, long1);
        else
            implicitlocation = null;
        if(true) goto _L5; else goto _L4
_L4:
        if(s1 != null)
            s2 = s1;
          goto _L6
    }

    public static final long INVALID_ID = -1L;
    public final String abbrName = null;
    public final long id = -1L;
    public final String type = null;
}
