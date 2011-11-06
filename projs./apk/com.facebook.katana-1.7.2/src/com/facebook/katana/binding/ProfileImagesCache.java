// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileImagesCache.java

package com.facebook.katana.binding;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.ImageUtils;
import com.facebook.katana.util.Log;
import java.util.*;

// Referenced classes of package com.facebook.katana.binding:
//            ProfileImage, WorkerThread

public class ProfileImagesCache
{
    protected static interface ProfileImagesContainerListener
    {

        public abstract void onProfileImageDownload(Context context, long l, String s);

        public abstract void onProfileImageLoaded(Context context, ProfileImage profileimage);
    }

    private static interface ImagesURLQuery
    {

        public static final int INDEX_URL;
        public static final String PROJECTION_URL[] = as;

        
        {
            String as[] = new String[1];
            as[0] = "user_image_url";
        }
    }

    private static interface ImagesQuery
    {

        public static final int INDEX_IMAGE;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[1];
            as[0] = "user_image";
        }
    }


    protected ProfileImagesCache(ProfileImagesContainerListener profileimagescontainerlistener)
    {
        mListener = profileimagescontainerlistener;
    }

    private static Bitmap getProfileImageBitmap(Context context, long l)
    {
        Bitmap bitmap = null;
        Uri uri = Uri.withAppendedPath(ConnectionsProvider.CONNECTION_ID_CONTENT_URI, String.valueOf(l));
        Cursor cursor = context.getContentResolver().query(uri, ImagesQuery.PROJECTION, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                byte abyte0[] = cursor.getBlob(0);
                if(abyte0 != null)
                    bitmap = ImageUtils.decodeByteArray(abyte0, 0, abyte0.length);
            }
            cursor.close();
        }
        return bitmap;
    }

    private static String getProfileImageURL(Context context, long l)
    {
        String s = null;
        Uri uri = Uri.withAppendedPath(ConnectionsProvider.CONNECTION_ID_CONTENT_URI, String.valueOf(l));
        Cursor cursor = context.getContentResolver().query(uri, ImagesURLQuery.PROJECTION_URL, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                s = cursor.getString(0);
            cursor.close();
        }
        return s;
    }

    private void load(final Context context, final ProfileImage profileImage, final ProfileImagesContainerListener listener)
    {
        final long profileId = profileImage.id;
        if(!mLoadPendingMap.containsKey(Long.valueOf(profileId)))
        {
            mLoadPendingMap.put(Long.valueOf(profileId), profileImage);
            mLoaderThread.getThreadHandler().post(new Runnable() {

                public void run()
                {
                    Bitmap bitmap = ProfileImagesCache.getProfileImageBitmap(context, profileId);
                    profileImage.setBitmap(bitmap);
                    mLoaderThread.getHandler().post(new Runnable() {

                        public void run()
                        {
                            mLoadPendingMap.remove(Long.valueOf(profileId));
                            if(profileImage.getBitmap() == null) goto _L2; else goto _L1
_L1:
                            profileImage.incrUsageCount();
                            if((long)mProfileImages.size() >= 75L)
                                makeRoom();
                            mProfileImages.put(Long.valueOf(profileId), profileImage);
                            listener.onProfileImageLoaded(context, profileImage);
_L4:
                            return;
_L2:
                            if(!mPendingDownload.containsKey(Long.valueOf(profileId)))
                            {
                                mListener.onProfileImageDownload(context, profileId, profileImage.url);
                                mPendingDownload.put(Long.valueOf(profileId), profileImage.url);
                            }
                            if(true) goto _L4; else goto _L3
_L3:
                        }

                        final _cls3 this$1;

                    
                    {
                        this$1 = _cls3.this;
                        super();
                    }
                    }
);
                }

                final ProfileImagesCache this$0;
                final Context val$context;
                final ProfileImagesContainerListener val$listener;
                final long val$profileId;
                final ProfileImage val$profileImage;

            
            {
                this$0 = ProfileImagesCache.this;
                context = context1;
                profileId = l;
                profileImage = profileimage;
                listener = profileimagescontainerlistener;
                super();
            }
            }
);
        }
    }

    private void loadKnownURL(final Context context, final long uid, ProfileImagesContainerListener profileimagescontainerlistener)
    {
        mLoaderThread.getThreadHandler().post(new Runnable() {

            public void run()
            {
                String s = ProfileImagesCache.getProfileImageURL(context, uid);
                if(s == null)
                    Log.i("ProfileImageCache", (new StringBuilder()).append("loadKnownURL: did not get url for uid=").append(uid).toString());
                else
                    load(context, new ProfileImage(uid, s, null), mListener);
            }

            final ProfileImagesCache this$0;
            final Context val$context;
            final long val$uid;

            
            {
                this$0 = ProfileImagesCache.this;
                context = context1;
                uid = l;
                super();
            }
        }
);
    }

    private void makeRoom()
    {
        mCacheList.addAll(mProfileImages.values());
        Collections.sort(mCacheList, new Comparator() {

            public int compare(ProfileImage profileimage1, ProfileImage profileimage2)
            {
                int k;
                if(profileimage1.getUsageCount() > profileimage2.getUsageCount())
                    k = 1;
                else
                if(profileimage1.getUsageCount() == profileimage2.getUsageCount())
                    k = 0;
                else
                    k = -1;
                return k;
            }

            public volatile int compare(Object obj, Object obj1)
            {
                return compare((ProfileImage)obj, (ProfileImage)obj1);
            }

            final ProfileImagesCache this$0;

            
            {
                this$0 = ProfileImagesCache.this;
                super();
            }
        }
);
        int i = Math.min(mCacheList.size(), 5);
        for(int j = 0; j < i; j++)
        {
            ProfileImage profileimage = (ProfileImage)mCacheList.get(j);
            mProfileImages.remove(Long.valueOf(profileimage.id));
        }

        mCacheList.clear();
    }

    protected void close()
    {
        mProfileImages.clear();
        mPendingDownload.clear();
        mLoadPendingMap.clear();
    }

    public Bitmap get(Context context, long l, String s)
    {
        Bitmap bitmap;
        ProfileImage profileimage;
        bitmap = null;
        profileimage = (ProfileImage)mProfileImages.get(Long.valueOf(l));
        if(s == null || s.length() <= 0) goto _L2; else goto _L1
_L1:
        if(profileimage == null) goto _L4; else goto _L3
_L3:
        if(!profileimage.url.equals(s)) goto _L6; else goto _L5
_L5:
        bitmap = profileimage.getBitmap();
        if(bitmap == null)
            load(context, profileimage, mListener);
        else
            profileimage.incrUsageCount();
_L8:
        return bitmap;
_L6:
        if(!mPendingDownload.containsKey(Long.valueOf(l)))
        {
            mListener.onProfileImageDownload(context, l, s);
            mPendingDownload.put(Long.valueOf(l), s);
        }
        continue; /* Loop/switch isn't completed */
_L4:
        load(context, new ProfileImage(l, s, null), mListener);
        continue; /* Loop/switch isn't completed */
_L2:
        if(profileimage != null)
            mProfileImages.remove(Long.valueOf(l));
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void get(Context context, Map map)
    {
        mPendingDownload.putAll(map);
        Long long1;
        for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); mListener.onProfileImageDownload(context, long1.longValue(), (String)map.get(long1)))
            long1 = (Long)iterator.next();

    }

    public Bitmap getWithoutURL(Context context, long l)
    {
        Bitmap bitmap = null;
        ProfileImage profileimage = (ProfileImage)mProfileImages.get(Long.valueOf(l));
        if(profileimage != null)
        {
            bitmap = profileimage.getBitmap();
            if(bitmap == null)
                load(context, profileimage, mListener);
            else
                profileimage.incrUsageCount();
        } else
        {
            loadKnownURL(context, l, mListener);
        }
        return bitmap;
    }

    protected ProfileImage onDownloadComplete(Context context, int i, long l, ProfileImage profileimage)
    {
        if(i == 200)
        {
            if((long)mProfileImages.size() >= 75L)
                makeRoom();
            mProfileImages.put(Long.valueOf(l), profileimage);
        }
        mPendingDownload.remove(Long.valueOf(l));
        return profileimage;
    }

    protected void open(Context context, WorkerThread workerthread)
    {
        mLoaderThread = workerthread;
    }

    private static final long MAX_CACHE_ENTRIES = 75L;
    private final List mCacheList = new ArrayList();
    private final ProfileImagesContainerListener mListener;
    private final Map mLoadPendingMap = new HashMap();
    private WorkerThread mLoaderThread;
    private final Map mPendingDownload = new HashMap();
    private final Map mProfileImages = new HashMap();









}
