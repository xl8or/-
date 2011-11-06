// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhotoWithTag.java

package com.facebook.katana.model;

import com.facebook.katana.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.model:
//            FacebookPhotoTagBase

public class FacebookPhotoWithTag extends FacebookPhotoTagBase
{

    public FacebookPhotoWithTag(long l)
    {
        subject = l;
    }

    public JSONObject toJSON()
    {
        JSONObject jsonobject1 = (new JSONObject()).put("tag_uid", Long.toString(subject));
        JSONObject jsonobject = jsonobject1;
_L2:
        return jsonobject;
        JSONException jsonexception;
        jsonexception;
        Log.e("", "inconceivable JSON exception", jsonexception);
        jsonobject = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public final long subject;
}
