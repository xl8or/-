// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StreamPhotosCache.java

package com.facebook.katana.binding;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.provider.PhotosProvider;
import java.io.File;
import java.util.*;

// Referenced classes of package com.facebook.katana.binding:
//            StreamPhoto, WorkerThread

public class StreamPhotosCache
{
    protected static interface PhotosContainerListener
    {

        public abstract void onPhotoDecoded(Bitmap bitmap, String s);

        public abstract void onPhotoRequested(Context context, String s, int i);
    }

    private static interface StreamPhotoQuery
    {

        public static final int INDEX_FILENAME = 2;
        public static final int INDEX_ID = 0;
        public static final int INDEX_URL = 1;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[3];
            as[0] = "_id";
            as[1] = "url";
            as[2] = "filename";
        }
    }


    protected StreamPhotosCache(PhotosContainerListener photoscontainerlistener)
    {
        mListener = photoscontainerlistener;
    }

    public static Map getPhotos(Context context)
    {
        HashMap hashmap = new HashMap();
        Cursor cursor = context.getContentResolver().query(PhotosProvider.STREAM_PHOTOS_CONTENT_URI, StreamPhotoQuery.PROJECTION, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                do
                {
                    Uri uri = Uri.withAppendedPath(PhotosProvider.STREAM_PHOTOS_CONTENT_URI, (new StringBuilder()).append("").append(cursor.getInt(0)).toString());
                    String s = cursor.getString(2);
                    File file = new File(s);
                    if(file.exists())
                    {
                        String s1 = cursor.getString(1);
                        hashmap.put(s1, new StreamPhoto(uri, s1, s, file.length(), null));
                    } else
                    {
                        context.getContentResolver().delete(uri, null, null);
                    }
                } while(cursor.moveToNext());
            cursor.close();
        }
        return hashmap;
    }

    private void makeRoom(Context context, long l)
    {
        mCacheList.addAll(mPhotos.values());
        Collections.sort(mCacheList, new Comparator() {

            public int compare(StreamPhoto streamphoto1, StreamPhoto streamphoto2)
            {
                int i;
                if(streamphoto1.getUsageCount() > streamphoto2.getUsageCount())
                    i = 1;
                else
                if(streamphoto1.getUsageCount() == streamphoto2.getUsageCount())
                    i = 0;
                else
                    i = -1;
                return i;
            }

            public volatile int compare(Object obj, Object obj1)
            {
                return compare((StreamPhoto)obj, (StreamPhoto)obj1);
            }

            final StreamPhotosCache this$0;

            
            {
                this$0 = StreamPhotosCache.this;
                super();
            }
        }
);
        Iterator iterator = mCacheList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            StreamPhoto streamphoto = (StreamPhoto)iterator.next();
            mPhotos.remove(streamphoto.getUrl());
            context.getContentResolver().delete(streamphoto.getContentUri(), null, null);
            mCacheSize = mCacheSize - streamphoto.getLength();
        } while(mCacheSize > l || (long)mPhotos.size() >= 100L);
        mCacheList.clear();
    }

    protected void close()
    {
        mPhotos.clear();
        mPendingDownload.clear();
        mDecoderThread = null;
    }

    public void decode(final StreamPhoto photo, final PhotosContainerListener listener)
    {
        mDecoderThread.getThreadHandler().post(new Runnable() {

            public void run()
            {
                photo.setBitmap(BitmapFactory.decodeFile(photo.getFilename()));
                if(photo.getBitmap() != null)
                    mDecoderThread.getHandler().post(new Runnable() {

                        public void run()
                        {
                            listener.onPhotoDecoded(photo.getBitmap(), photo.getUrl());
                        }

                        final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                    }
                    }
);
            }

            final StreamPhotosCache this$0;
            final PhotosContainerListener val$listener;
            final StreamPhoto val$photo;

            
            {
                this$0 = StreamPhotosCache.this;
                photo = streamphoto;
                listener = photoscontainerlistener;
                super();
            }
        }
);
    }

    public Bitmap get(Context context, String s)
    {
        return get(context, s, 1);
    }

    public Bitmap get(Context context, String s, int i)
    {
        Bitmap bitmap;
        StreamPhoto streamphoto;
        bitmap = null;
        streamphoto = (StreamPhoto)mPhotos.get(s);
        if(streamphoto == null) goto _L2; else goto _L1
_L1:
        streamphoto.incrUsageCount();
        bitmap = streamphoto.getBitmap();
        if(bitmap == null)
            decode(streamphoto, mListener);
_L4:
        return bitmap;
_L2:
        if(!mPendingDownload.containsKey(s))
        {
            mListener.onPhotoRequested(context, s, i);
            mPendingDownload.put(s, s);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected StreamPhoto onDownloadComplete(Context context, int i, String s, StreamPhoto streamphoto)
    {
        if(i == 200)
        {
            long l = streamphoto.getLength();
            if(l < 0x7a120L)
            {
                if(l + mCacheSize > 0x7a120L || (long)mPhotos.size() >= 100L)
                    makeRoom(context, 0x7a120L - l);
                mPhotos.put(streamphoto.getUrl(), streamphoto);
                mCacheSize = l + mCacheSize;
            }
        }
        mPendingDownload.remove(s);
        return streamphoto;
    }

    protected void open(Context context, WorkerThread workerthread)
    {
        mPhotos.putAll(getPhotos(context));
        for(Iterator iterator = mPhotos.values().iterator(); iterator.hasNext();)
            mCacheSize = mCacheSize + ((StreamPhoto)iterator.next()).getLength();

        mDecoderThread = workerthread;
    }

    private static final long MAX_CACHE_ENTRIES = 100L;
    private static final long MAX_CACHE_SIZE = 0x7a120L;
    public static final int TYPE_STREAM_PHOTO = 1;
    public static final int TYPE_STREAM_PROFILE_PHOTO = 2;
    private final List mCacheList = new ArrayList();
    private long mCacheSize;
    private WorkerThread mDecoderThread;
    private final PhotosContainerListener mListener;
    private final Map mPendingDownload = new HashMap();
    private final Map mPhotos = new HashMap();

}
