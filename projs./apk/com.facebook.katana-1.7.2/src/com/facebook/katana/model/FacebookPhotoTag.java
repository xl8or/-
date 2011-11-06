// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhotoTag.java

package com.facebook.katana.model;

import com.facebook.katana.util.Log;
import java.io.IOException;
import org.codehaus.jackson.*;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.model:
//            FacebookPhotoTagBase

public class FacebookPhotoTag extends FacebookPhotoTagBase
{

    public FacebookPhotoTag(String s, String s1, long l, double d, double d1, long l1)
    {
        mPhotoId = s;
        mSubject = s1;
        mUserId = l;
        mX = d;
        mY = d1;
        mCreated = l1;
    }

    public FacebookPhotoTag(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        String s = null;
        String s1 = null;
        long l = -1L;
        long l1 = -1L;
        double d = 0D;
        double d1 = 0D;
        JsonToken jsontoken = jsonparser.nextToken();
        while(jsontoken != JsonToken.END_OBJECT) 
        {
            if(jsontoken == JsonToken.VALUE_STRING)
            {
                String s4 = jsonparser.getCurrentName();
                if(s4.equals("pid"))
                    s = jsonparser.getText();
                else
                if(s4.equals("subject"))
                    s1 = jsonparser.getText();
            } else
            if(jsontoken == JsonToken.VALUE_NUMBER_INT)
            {
                String s3 = jsonparser.getCurrentName();
                if(s3.equals("uid"))
                    l = jsonparser.getLongValue();
                else
                if(s3.equals("created"))
                    l1 = jsonparser.getLongValue();
            } else
            if(jsontoken == JsonToken.VALUE_NUMBER_FLOAT)
            {
                String s2 = jsonparser.getCurrentName();
                if(s2.equals("xcoord"))
                    d = jsonparser.getFloatValue();
                else
                if(s2.equals("ycoord"))
                    d1 = jsonparser.getFloatValue();
            }
            jsontoken = jsonparser.nextToken();
        }
        mPhotoId = s;
        mSubject = s1;
        mUserId = l;
        mX = d;
        mY = d1;
        mCreated = l1;
    }

    public long getCreated()
    {
        return mCreated;
    }

    public String getPhotoId()
    {
        return mPhotoId;
    }

    public String getSubject()
    {
        return mSubject;
    }

    public long getUid()
    {
        return mUserId;
    }

    public double getX()
    {
        return mX;
    }

    public double getY()
    {
        return mY;
    }

    public JSONObject toJSON()
    {
        JSONObject jsonobject;
label0:
        {
            JSONObject jsonobject1;
            try
            {
                jsonobject1 = (new JSONObject()).put("x", getX()).put("y", getY());
                if(getSubject() != null)
                    jsonobject1.put("tag_text", getSubject());
                else
                    jsonobject1.put("tag_uid", getUid());
            }
            catch(JSONException jsonexception)
            {
                Log.e("", "inconceivable JSON exception", jsonexception);
                jsonobject = null;
                break label0;
            }
            jsonobject = jsonobject1;
        }
        return jsonobject;
    }

    private final long mCreated;
    private final String mPhotoId;
    private final String mSubject;
    private final long mUserId;
    private final double mX;
    private final double mY;
}
