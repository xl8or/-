// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UsersSearch.java

package com.facebook.katana.service.method;

import android.content.*;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGetUsersProfile, ApiMethodListener

public class UsersSearch extends FqlGetUsersProfile
{
    public class DeleteThread extends Thread
    {

        public void run()
        {
            saveSearchResults(new HashMap());
            onComplete(200, null, null);
        }

        final UsersSearch this$0;

        public DeleteThread()
        {
            this$0 = UsersSearch.this;
            super();
        }
    }


    public UsersSearch(Context context, Intent intent, int i, String s, String s1, int j, int k, 
            ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, apimethodlistener, buildQuery(s1, j, k), com/facebook/katana/model/FacebookUser);
        mStart = 0;
        mTotal = 0;
        mStart = j;
        mName = s1;
        mReqId = i;
        mLastReqId = i;
    }

    private static String buildQuery(String s, int i, int j)
    {
        StringBuilder stringbuilder = new StringBuilder("contains(");
        StringUtils.appendEscapedFQLString(stringbuilder, s);
        stringbuilder.append(") ").append("LIMIT ").append(i).append(",").append(j);
        return stringbuilder.toString();
    }

    private static boolean isValidNameQuery(String s)
    {
        boolean flag;
        if(s == null || s.trim().length() == 0)
            flag = false;
        else
            flag = true;
        return flag;
    }

    /**
     * @deprecated Method saveSearchResults is deprecated
     */

    private void saveSearchResults(Map map)
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        int j;
        i = mReqId;
        j = mLastReqId;
        if(i >= j) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        ContentResolver contentresolver;
        contentresolver = mContext.getContentResolver();
        if(mStart == 0)
            contentresolver.delete(ConnectionsProvider.USER_SEARCH_CONTENT_URI, null, null);
        if(map.size() == 0) goto _L1; else goto _L3
_L3:
        ContentValues acontentvalues[];
        int k = 0;
        acontentvalues = new ContentValues[map.size()];
        FacebookUser facebookuser;
        ContentValues contentvalues;
        for(Iterator iterator = map.values().iterator(); iterator.hasNext(); contentvalues.put("user_image_url", facebookuser.mImageUrl))
        {
            facebookuser = (FacebookUser)iterator.next();
            contentvalues = new ContentValues();
            acontentvalues[k] = contentvalues;
            k++;
            contentvalues.put("user_id", Long.valueOf(facebookuser.mUserId));
            contentvalues.put("display_name", facebookuser.getDisplayName());
        }

        break MISSING_BLOCK_LABEL_172;
        Exception exception;
        exception;
        throw exception;
        contentresolver.bulkInsert(ConnectionsProvider.USER_SEARCH_CONTENT_URI, acontentvalues);
          goto _L1
    }

    public int getStartResults()
    {
        return mStart;
    }

    public int getTotalResults()
    {
        return mTotal;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws JsonParseException, IOException, FacebookApiException, JMException
    {
        super.parseJSON(jsonparser);
        saveSearchResults(getUsers());
    }

    public void start()
    {
        if(isValidNameQuery(mName))
            super.start();
        else
            (new DeleteThread()).start();
    }

    private static int mLastReqId = -1;
    private String mName;
    private final int mReqId;
    private int mStart;
    private int mTotal;


}
