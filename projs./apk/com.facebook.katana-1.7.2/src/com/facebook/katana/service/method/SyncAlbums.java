// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyncAlbums.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import com.facebook.katana.util.Factory;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, AlbumSyncModel, FqlGetAlbums, ApiMethodListener

public class SyncAlbums extends ApiMethod
{
    protected class SyncAlbumsListener
        implements ApiMethodListener
    {

        public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            if(mLastBatch)
                mListener.onOperationComplete(apimethod, i, s, exception);
        }

        public void onOperationProgress(ApiMethod apimethod, long l, long l1)
        {
            mListener.onOperationProgress(apimethod, l, l1);
        }

        public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            SyncAlbums syncalbums = SyncAlbums.this;
            boolean flag;
            List list;
            long l;
            if(mAllQueriesSuccess && i == 200)
                flag = true;
            else
                flag = false;
            syncalbums.mAllQueriesSuccess = flag;
            list = ((FqlGetAlbums)apimethod).mAlbums;
            l = mAlbums.size();
            if(list == null || list.size() == 0 || l >= 70L || !mAllQueriesSuccess)
            {
                mLastBatch = true;
                if(mAllQueriesSuccess)
                    SyncAlbums.syncAlbums(mContext.getContentResolver(), mUserId, mAlbums, true);
                mListener.onProcessComplete(apimethod, i, s, exception);
            } else
            {
                SyncAlbums.syncAlbums(mContext.getContentResolver(), mUserId, list, false);
                mAlbums.addAll(list);
                long l1 = -1L;
                if((long)mAlbums.size() < 70L)
                    l1 = 20L;
                (new FqlGetAlbums(mContext, mIntent, mSessionKey, mUserId, mAlbumIds, this, mAlbums.size(), l1)).start();
            }
        }

        private boolean mLastBatch;
        final SyncAlbums this$0;

        protected SyncAlbumsListener()
        {
            this$0 = SyncAlbums.this;
            super();
        }
    }


    public SyncAlbums(Context context, Intent intent, String s, long l, String as[], ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", null, com.facebook.katana.Constants.URL.getApiUrl(context), null);
        mAlbums = new ArrayList();
        mContext = context;
        mIntent = intent;
        mSessionKey = s;
        mUserId = l;
        mAlbumIds = as;
        mListener = apimethodlistener;
        mAllQueriesSuccess = true;
    }

    protected static Factory localAlbumsCursorFactory(final ContentResolver resolver, final long userId, final List albums)
    {
        return new Factory() {

            public Cursor make()
            {
                Cursor cursor;
                if(SyncAlbums.updatingSpecificAlbums(userId))
                    cursor = AlbumSyncModel.cursorForAlbums(resolver, albums);
                else
                    cursor = AlbumSyncModel.cursorForAlbumsForUser(resolver, userId);
                return cursor;
            }

            public volatile Object make()
            {
                return make();
            }

            final List val$albums;
            final ContentResolver val$resolver;
            final long val$userId;

            
            {
                userId = l;
                resolver = contentresolver;
                albums = list;
                super();
            }
        }
;
    }

    protected static boolean shouldUseBatch(long l, String as[])
    {
        boolean flag;
        if(as == null || as.length == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected static void syncAlbums(ContentResolver contentresolver, long l, List list, boolean flag)
    {
        AlbumSyncModel.doSync(contentresolver, list, localAlbumsCursorFactory(contentresolver, l, list), true, true, flag, false, l);
    }

    private static boolean updatingSpecificAlbums(long l)
    {
        boolean flag;
        if(-1L == l)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void start()
    {
        long l = -1L;
        if(shouldUseBatch(mUserId, mAlbumIds))
            l = 10L;
        (new FqlGetAlbums(mContext, mReqIntent, mSessionKey, mUserId, mAlbumIds, new SyncAlbumsListener(), 0L, l)).start();
    }

    private static final long ALBUM_BATCH_SIZE = 20L;
    private static final long FIRST_BATCH_SIZE = 10L;
    private static final long MAX_ALBUM_BEFORE_USING_BATCH = 70L;
    private final String mAlbumIds[];
    private List mAlbums;
    private boolean mAllQueriesSuccess;
    private final Context mContext;
    private final Intent mIntent;
    private final ApiMethodListener mListener;
    private final String mSessionKey;
    private final long mUserId;



/*
    static boolean access$002(SyncAlbums syncalbums, boolean flag)
    {
        syncalbums.mAllQueriesSuccess = flag;
        return flag;
    }

*/








}
