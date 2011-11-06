// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyncPhotoComments.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, PhotosGetComments, PhotosCanComment, BatchRun, 
//            ApiMethodListener, FqlGetProfile

public class SyncPhotoComments extends ApiMethod
{
    private class GetUsersApiMethodListener
        implements ApiMethodListener
    {

        public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            mListener.onOperationComplete(SyncPhotoComments.this, i, s, exception);
        }

        public void onOperationProgress(ApiMethod apimethod, long l, long l1)
        {
        }

        public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            if(i == 200)
            {
                Map map = ((FqlGetProfile)apimethod).getProfiles();
                mAllProfiles.putAll(map);
                FacebookPhotoComment facebookphotocomment;
                for(Iterator iterator = mComments.iterator(); iterator.hasNext(); facebookphotocomment.setFromProfile((FacebookProfile)mAllProfiles.get(Long.valueOf(facebookphotocomment.getFromProfileId()))))
                    facebookphotocomment = (FacebookPhotoComment)iterator.next();

            }
        }

        final SyncPhotoComments this$0;

        private GetUsersApiMethodListener()
        {
            this$0 = SyncPhotoComments.this;
            super();
        }

    }

    private class BatchListener
        implements ApiMethodListener
    {

        public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            if(mNeedProfiles != null && mNeedProfiles.size() > 0)
                (new FqlGetProfile(mContext, getIntent(), mSessionKey, new GetUsersApiMethodListener(), mNeedProfiles)).start();
            else
                mListener.onOperationComplete(SyncPhotoComments.this, i, s, exception);
        }

        public void onOperationProgress(ApiMethod apimethod, long l, long l1)
        {
        }

        public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            if(i == 200)
            {
                BatchRun batchrun = (BatchRun)apimethod;
                PhotosGetComments photosgetcomments = (PhotosGetComments)batchrun.getMethods().get(0);
                mComments = photosgetcomments.getComments();
                mNeedProfiles = requestProfiles();
                mCanComment = ((PhotosCanComment)batchrun.getMethods().get(1)).getCanComment();
            }
        }

        private Map mNeedProfiles;
        final SyncPhotoComments this$0;

        private BatchListener()
        {
            this$0 = SyncPhotoComments.this;
            super();
        }

    }

    private static interface ConnectionsQuery
    {

        public static final int INDEX_USER_CONNECTION_TYPE = 3;
        public static final int INDEX_USER_DISPLAY_NAME = 1;
        public static final int INDEX_USER_ID = 0;
        public static final int INDEX_USER_IMAGE_URL = 2;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "user_id";
            as[1] = "display_name";
            as[2] = "user_image_url";
            as[3] = "connection_type";
        }
    }


    public SyncPhotoComments(Context context, Intent intent, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", null, com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mSessionKey = s;
        mPhotoId = s1;
    }

    public boolean getCanComment()
    {
        return mCanComment;
    }

    public List getComments()
    {
        List list;
        if(mComments == null)
            list = Collections.emptyList();
        else
            list = mComments;
        return list;
    }

    public Map requestProfiles()
    {
        HashMap hashmap;
        hashmap = new HashMap();
        for(Iterator iterator = mComments.iterator(); iterator.hasNext(); hashmap.put(Long.valueOf(((FacebookPhotoComment)iterator.next()).getFromProfileId()), null));
        if(hashmap.size() != 0) goto _L2; else goto _L1
_L1:
        return hashmap;
_L2:
        StringBuffer stringbuffer = new StringBuffer(256);
        stringbuffer.append("user_id").append(" IN(");
        boolean flag = true;
        Iterator iterator1 = hashmap.keySet().iterator();
        while(iterator1.hasNext()) 
        {
            Long long2 = (Long)iterator1.next();
            if(flag)
                flag = false;
            else
                stringbuffer.append(",");
            stringbuffer.append(long2);
        }
        stringbuffer.append(")");
        Cursor cursor = mContext.getContentResolver().query(ConnectionsProvider.CONNECTIONS_CONTENT_URI, ConnectionsQuery.PROJECTION, stringbuffer.toString(), null, null);
        if(cursor.moveToFirst())
            do
            {
                Long long1 = Long.valueOf(cursor.getLong(0));
                hashmap.remove(long1);
                boolean flag1 = false;
                if(cursor.getInt(3) == com.facebook.katana.provider.ConnectionsProvider.ConnectionType.USER.ordinal())
                    flag1 = true;
                long l = long1.longValue();
                String s = cursor.getString(1);
                String s1 = cursor.getString(2);
                Iterator iterator2;
                FacebookPhotoComment facebookphotocomment;
                int i;
                FacebookProfile facebookprofile;
                if(flag1)
                    i = 0;
                else
                    i = 1;
                facebookprofile = new FacebookProfile(l, s, s1, i);
                mAllProfiles.put(long1, facebookprofile);
            } while(cursor.moveToNext());
        cursor.close();
        if(hashmap.size() == 0)
        {
            iterator2 = mComments.iterator();
            while(iterator2.hasNext()) 
            {
                facebookphotocomment = (FacebookPhotoComment)iterator2.next();
                facebookphotocomment.setFromProfile((FacebookProfile)mAllProfiles.get(Long.valueOf(facebookphotocomment.getFromProfileId())));
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void start()
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(new PhotosGetComments(mContext, mReqIntent, mSessionKey, mPhotoId, null));
        arraylist.add(new PhotosCanComment(mContext, mReqIntent, mSessionKey, mPhotoId, null));
        (new BatchRun(mContext, mReqIntent, mSessionKey, arraylist, new BatchListener())).start();
    }

    private final Map mAllProfiles = new HashMap();
    private boolean mCanComment;
    private List mComments;
    private final String mPhotoId;
    private final String mSessionKey;



/*
    static List access$102(SyncPhotoComments syncphotocomments, List list)
    {
        syncphotocomments.mComments = list;
        return list;
    }

*/


/*
    static boolean access$202(SyncPhotoComments syncphotocomments, boolean flag)
    {
        syncphotocomments.mCanComment = flag;
        return flag;
    }

*/


}
