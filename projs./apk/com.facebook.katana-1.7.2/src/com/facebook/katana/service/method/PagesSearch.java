// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PagesSearch.java

package com.facebook.katana.service.method;

import android.content.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.PagesProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGetPages, ApiMethodListener

public class PagesSearch extends FqlGetPages
{
    public class DeleteThread extends Thread
    {

        public void run()
        {
            saveSearchResults(new HashMap());
            onComplete(200, null, null);
        }

        final PagesSearch this$0;

        public DeleteThread()
        {
            this$0 = PagesSearch.this;
            super();
        }
    }


    public PagesSearch(Context context, Intent intent, String s, String s1, int i, int j, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, apimethodlistener, buildQuery(s1, i, j), com/facebook/katana/model/FacebookPage);
        mStart = 0;
        mTotal = 0;
        mStart = i;
        mName = s1;
        mLastReqTimeMillis = System.currentTimeMillis();
        mReqTimeMillis = mLastReqTimeMillis;
    }

    public static String RequestPagesSearch(Context context, String s, int i, int j)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new PagesSearch(context, null, appsession.getSessionInfo().sessionKey, s, i, j, null), 1001, 1020, null);
    }

    private static String buildQuery(String s, int i, int j)
    {
        StringBuilder stringbuilder = new StringBuilder("contains(");
        StringUtils.appendEscapedFQLString(stringbuilder, s);
        stringbuilder.append(") ").append(" AND is_community_page!='true' ").append("LIMIT ").append(i).append(",").append(j);
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
        long l;
        long l1;
        l = mReqTimeMillis;
        l1 = mLastReqTimeMillis;
        if(l >= l1) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        ContentResolver contentresolver;
        contentresolver = mContext.getContentResolver();
        if(mStart == 0)
            contentresolver.delete(PagesProvider.SEARCH_RESULTS_CONTENT_URI, null, null);
        if(map.size() == 0) goto _L1; else goto _L3
_L3:
        ContentValues acontentvalues[];
        int i = 0;
        acontentvalues = new ContentValues[map.size()];
        FacebookPage facebookpage;
        ContentValues contentvalues;
        for(Iterator iterator = map.values().iterator(); iterator.hasNext(); contentvalues.put("pic", facebookpage.mPicSmall))
        {
            facebookpage = (FacebookPage)iterator.next();
            contentvalues = new ContentValues();
            acontentvalues[i] = contentvalues;
            i++;
            contentvalues.put("page_id", Long.valueOf(facebookpage.mPageId));
            contentvalues.put("display_name", facebookpage.mDisplayName);
        }

        break MISSING_BLOCK_LABEL_173;
        Exception exception;
        exception;
        throw exception;
        contentresolver.bulkInsert(PagesProvider.SEARCH_RESULTS_CONTENT_URI, acontentvalues);
          goto _L1
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPagesSearchComplete(appsession, s, i, s1, exception, mStart, mTotal));
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
        saveSearchResults(getPages());
    }

    public void start()
    {
        if(isValidNameQuery(mName))
            super.start();
        else
            (new DeleteThread()).start();
    }

    private static long mLastReqTimeMillis = -1L;
    private String mName;
    private final long mReqTimeMillis;
    private int mStart;
    private int mTotal;


}
