// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookEvent.java

package com.facebook.katana.model;

import android.content.ContentValues;
import com.facebook.katana.util.jsonmirror.*;
import java.io.*;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.model:
//            FacebookProfile

public class FacebookEvent extends JMCachingDictDestination
{
    public static class RsvpStatus
    {

        public final RsvpStatusEnum status;

        public RsvpStatus(RsvpStatusEnum rsvpstatusenum)
        {
            status = rsvpstatusenum;
        }
    }

    public static final class RsvpStatusEnum extends Enum
    {

        public static RsvpStatusEnum valueOf(String s)
        {
            return (RsvpStatusEnum)Enum.valueOf(com/facebook/katana/model/FacebookEvent$RsvpStatusEnum, s);
        }

        public static RsvpStatusEnum[] values()
        {
            return (RsvpStatusEnum[])$VALUES.clone();
        }

        private static final RsvpStatusEnum $VALUES[];
        public static final RsvpStatusEnum ATTENDING;
        public static final RsvpStatusEnum DECLINED;
        public static final RsvpStatusEnum NOT_REPLIED;
        public static final RsvpStatusEnum UNSURE;

        static 
        {
            ATTENDING = new RsvpStatusEnum("ATTENDING", 0);
            UNSURE = new RsvpStatusEnum("UNSURE", 1);
            DECLINED = new RsvpStatusEnum("DECLINED", 2);
            NOT_REPLIED = new RsvpStatusEnum("NOT_REPLIED", 3);
            RsvpStatusEnum arsvpstatusenum[] = new RsvpStatusEnum[4];
            arsvpstatusenum[0] = ATTENDING;
            arsvpstatusenum[1] = UNSURE;
            arsvpstatusenum[2] = DECLINED;
            arsvpstatusenum[3] = NOT_REPLIED;
            $VALUES = arsvpstatusenum;
        }

        private RsvpStatusEnum(String s, int i)
        {
            super(s, i);
        }
    }


    private FacebookEvent()
    {
        mEventId = -1L;
    }

    public static Map deserializeVenue(byte abyte0[])
        throws IOException
    {
        ObjectInputStream objectinputstream = new ObjectInputStream(new ByteArrayInputStream(abyte0));
        Map map;
        try
        {
            map = (Map)objectinputstream.readObject();
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new IOException(classnotfoundexception.toString());
        }
        return map;
    }

    public static RsvpStatus getRsvpStatus(int i)
    {
        return (RsvpStatus)intStatusMapper.get(new Integer(i));
    }

    public static RsvpStatus getRsvpStatus(String s)
    {
        return (RsvpStatus)stringStatusMapper.get(s);
    }

    public static String getRsvpStatusString(RsvpStatus rsvpstatus)
    {
        return (String)statusStringMapper.get(rsvpstatus);
    }

    public static FacebookEvent parseFromJSON(JsonParser jsonparser)
        throws JsonParseException, IOException, JMException
    {
        Object obj = JMParser.parseJsonResponse(jsonparser, JMAutogen.generateJMParser(com/facebook/katana/model/FacebookEvent));
        FacebookEvent facebookevent;
        if(obj instanceof FacebookEvent)
            facebookevent = (FacebookEvent)obj;
        else
            facebookevent = null;
        return facebookevent;
    }

    public static void parseRsvpStatus(JsonParser jsonparser, Map map)
        throws JsonParseException, IOException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        Long long1 = null;
        RsvpStatus rsvpstatus = null;
        if(!$assertionsDisabled && jsontoken != JsonToken.START_OBJECT)
            throw new AssertionError();
        JsonToken jsontoken1 = jsonparser.nextToken();
        while(jsontoken1 != JsonToken.END_OBJECT) 
        {
            if(jsontoken1 == JsonToken.VALUE_NUMBER_INT || jsontoken1 == JsonToken.VALUE_STRING)
            {
                String s = jsonparser.getCurrentName();
                if(s.equals("eid") || s.equals("uid"))
                {
                    if(jsontoken1 == JsonToken.VALUE_NUMBER_INT)
                        long1 = new Long(jsonparser.getLongValue());
                    else
                        long1 = new Long(jsonparser.getText());
                } else
                if(s.equals("rsvp_status"))
                {
                    if(!$assertionsDisabled && jsontoken1 != JsonToken.VALUE_STRING)
                        throw new AssertionError();
                    String s1 = jsonparser.getText();
                    rsvpstatus = (RsvpStatus)stringStatusMapper.get(s1);
                }
            } else
            if(jsontoken1 == JsonToken.START_ARRAY || jsontoken1 == JsonToken.START_OBJECT)
                jsonparser.skipChildren();
            jsontoken1 = jsonparser.nextToken();
        }
        if(long1 != null)
            map.put(long1, rsvpstatus);
    }

    public static byte[] serializeVenue(Map map)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        (new ObjectOutputStream(bytearrayoutputstream)).writeObject(map);
        return bytearrayoutputstream.toByteArray();
    }

    public FacebookProfile getCreator()
    {
        return mCreator;
    }

    public long getCreatorId()
    {
        return mCreatorId;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public long getEndTime()
    {
        return mEndTime;
    }

    public long getEventId()
    {
        return mEventId;
    }

    public String getEventSubtype()
    {
        return mEventSubtype;
    }

    public String getEventType()
    {
        return mEventType;
    }

    public boolean getHideGuestList()
    {
        return mHideGuestList;
    }

    public String getHost()
    {
        return mHost;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }

    public String getLocation()
    {
        return mLocation;
    }

    public String getMediumImageUrl()
    {
        return mMediumImageUrl;
    }

    public String getName()
    {
        return mName;
    }

    public RsvpStatus getRsvpStatus()
    {
        return mRsvpStatus;
    }

    public long getStartTime()
    {
        return mStartTime;
    }

    public String getTagline()
    {
        return mTagline;
    }

    public Map getVenue()
    {
        return Collections.unmodifiableMap(mVenue);
    }

    public void setCreator(FacebookProfile facebookprofile)
    {
        mCreator = facebookprofile;
    }

    public void setRsvpStatus(RsvpStatus rsvpstatus)
    {
        mRsvpStatus = rsvpstatus;
    }

    public void writeContentValues(ContentValues contentvalues)
        throws IOException
    {
        byte abyte0[] = serializeVenue(mVenue);
        contentvalues.put("event_id", Long.valueOf(mEventId));
        contentvalues.put("event_name", mName);
        contentvalues.put("tagline", mTagline);
        contentvalues.put("image_url", mImageUrl);
        contentvalues.put("medium_image_url", mMediumImageUrl);
        contentvalues.put("host", mHost);
        contentvalues.put("description", mDescription);
        contentvalues.put("event_type", mEventType);
        contentvalues.put("event_subtype", mEventSubtype);
        contentvalues.put("start_time", Long.valueOf(mStartTime));
        contentvalues.put("end_time", Long.valueOf(mEndTime));
        if(mCreator != null)
        {
            contentvalues.put("creator_id", Long.valueOf(mCreator.mId));
            contentvalues.put("display_name", mCreator.mDisplayName);
            contentvalues.put("creator_image_url", mCreator.mImageUrl);
        }
        contentvalues.put("location", mLocation);
        contentvalues.put("venue", abyte0);
        int i;
        if(mHideGuestList)
            i = 1;
        else
            i = 0;
        contentvalues.put("hide_guest_list", Integer.valueOf(i));
        contentvalues.put("rsvp_status", Integer.valueOf(mRsvpStatus.status.ordinal()));
    }

    static final boolean $assertionsDisabled = false;
    public static final long INVALID_ID = -1L;
    public static final String KEY_VENUE_CITY = "city";
    public static final String KEY_VENUE_COUNTRY = "country";
    public static final String KEY_VENUE_STATE = "state";
    public static final String KEY_VENUE_STREET = "street";
    static Map intStatusMapper;
    static Map statusStringMapper;
    static Map stringStatusMapper;
    protected FacebookProfile mCreator;
    protected long mCreatorId;
    protected String mDescription;
    protected long mEndTime;
    protected long mEventId;
    protected String mEventSubtype;
    protected String mEventType;
    protected boolean mHideGuestList;
    protected String mHost;
    protected String mImageUrl;
    protected String mLocation;
    protected String mMediumImageUrl;
    protected String mName;
    protected RsvpStatus mRsvpStatus;
    protected long mStartTime;
    protected String mTagline;
    protected Map mVenue;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/model/FacebookEvent.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
        intStatusMapper = new HashMap();
        stringStatusMapper = new HashMap();
        statusStringMapper = new HashMap();
        stringStatusMapper.put("attending", new RsvpStatus(RsvpStatusEnum.ATTENDING));
        stringStatusMapper.put("unsure", new RsvpStatus(RsvpStatusEnum.UNSURE));
        stringStatusMapper.put("declined", new RsvpStatus(RsvpStatusEnum.DECLINED));
        stringStatusMapper.put("not_replied", new RsvpStatus(RsvpStatusEnum.NOT_REPLIED));
        java.util.Map.Entry entry;
        for(Iterator iterator = stringStatusMapper.entrySet().iterator(); iterator.hasNext(); statusStringMapper.put(entry.getValue(), entry.getKey()))
        {
            entry = (java.util.Map.Entry)iterator.next();
            intStatusMapper.put(new Integer(((RsvpStatus)entry.getValue()).status.ordinal()), entry.getValue());
        }

    }
}
