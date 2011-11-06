// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhotoTagBase.java

package com.facebook.katana.model;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class FacebookPhotoTagBase
{

    public FacebookPhotoTagBase()
    {
    }

    public static String encode(List list)
    {
        JSONArray jsonarray = new JSONArray();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookPhotoTagBase facebookphototagbase = (FacebookPhotoTagBase)iterator.next();
            if(facebookphototagbase.toJSON() != null)
                jsonarray.put(facebookphototagbase.toJSON());
        } while(true);
        return jsonarray.toString();
    }

    public abstract JSONObject toJSON();
}
