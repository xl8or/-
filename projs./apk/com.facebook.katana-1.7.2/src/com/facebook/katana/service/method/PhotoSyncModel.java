// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoSyncModel.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            Utils

public class PhotoSyncModel
{
    private static interface PhotoQuery
    {

        public static final int INDEX_ALBUM_ID = 0;
        public static final int INDEX_CAPTION = 2;
        public static final int INDEX_PHOTO_ID = 1;
        public static final int INDEX_POSITION = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "aid";
            as[1] = "pid";
            as[2] = "caption";
            as[3] = "position";
        }
    }


    public PhotoSyncModel()
    {
    }

    private static void D(String s)
    {
        if(DEBUG)
            Log.d("PhotoSyncModel", s);
    }

    private static String buildDeleteSelection(Collection collection)
    {
        boolean flag = true;
        StringBuffer stringbuffer = new StringBuffer(256);
        stringbuffer.append("pid").append(" IN(");
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()) 
        {
            String s = (String)iterator.next();
            if(flag)
                flag = false;
            else
                stringbuffer.append(',');
            stringbuffer.append('\'').append(s).append('\'');
        }
        stringbuffer.append(")");
        return stringbuffer.toString();
    }

    public static Factory cursorFactoryForPhotos(final Context finalContext, final Collection finalPhotos)
    {
        return new Factory() {

            public Cursor make()
            {
                return PhotoSyncModel.cursorForPhotos(finalContext, finalPhotos);
            }

            public volatile Object make()
            {
                return make();
            }

            final Context val$finalContext;
            final Collection val$finalPhotos;

            
            {
                finalContext = context;
                finalPhotos = collection;
                super();
            }
        }
;
    }

    public static Factory cursorFactoryForPhotosForAlbum(final Context finalContext, final String finalAlbumId)
    {
        return new Factory() {

            public Cursor make()
            {
                return PhotoSyncModel.cursorForPhotosForAlbum(finalContext, finalAlbumId);
            }

            public volatile Object make()
            {
                return make();
            }

            final String val$finalAlbumId;
            final Context val$finalContext;

            
            {
                finalContext = context;
                finalAlbumId = s;
                super();
            }
        }
;
    }

    public static Cursor cursorForPhotos(Context context, Collection collection)
    {
        ContentResolver contentresolver = context.getContentResolver();
        StringBuilder stringbuilder = new StringBuilder(128);
        stringbuilder.append("pid").append(" IN(");
        boolean flag = true;
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()) 
        {
            FacebookPhoto facebookphoto = (FacebookPhoto)iterator.next();
            if(flag)
                flag = false;
            else
                stringbuilder.append(',');
            stringbuilder.append('\'');
            stringbuilder.append(facebookphoto.getPhotoId());
            stringbuilder.append('\'');
        }
        stringbuilder.append(')');
        return contentresolver.query(PhotosProvider.PHOTOS_CONTENT_URI, PhotoQuery.PROJECTION, stringbuilder.toString(), null, null);
    }

    public static Cursor cursorForPhotosForAlbum(Context context, String s)
    {
        Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, s);
        return context.getContentResolver().query(uri, PhotoQuery.PROJECTION, null, null, null);
    }

    /**
     * @deprecated Method doSync is deprecated
     */

    public static void doSync(Context context, Collection collection, Factory factory, boolean flag, boolean flag1, boolean flag2, String s)
    {
        com/facebook/katana/service/method/PhotoSyncModel;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        ArrayList arraylist1;
        ArrayList arraylist2;
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        arraylist2 = new ArrayList();
        if(s == null) goto _L2; else goto _L1
_L1:
        boolean flag3 = true;
_L4:
        boolean flag4;
        HashMap hashmap;
        flag4 = flag2 & flag3;
        D((new StringBuilder()).append("merging ").append(collection.size()).append(" photos").toString());
        hashmap = new HashMap();
        FacebookPhoto facebookphoto3;
        for(Iterator iterator = collection.iterator(); iterator.hasNext(); hashmap.put(facebookphoto3.getPhotoId(), facebookphoto3))
            facebookphoto3 = (FacebookPhoto)iterator.next();

        break MISSING_BLOCK_LABEL_148;
        Exception exception;
        exception;
        throw exception;
_L2:
        flag3 = false;
        if(true) goto _L4; else goto _L3
_L3:
        Cursor cursor = (Cursor)factory.make();
        if(cursor.moveToFirst())
            do
            {
                String s1 = cursor.getString(1);
                FacebookPhoto facebookphoto2 = (FacebookPhoto)hashmap.get(s1);
                Iterator iterator3;
                if(facebookphoto2 == null)
                {
                    if(flag4)
                        arraylist2.add(s1);
                } else
                if(!photosIdentical(facebookphoto2, cursor) && flag1)
                    arraylist1.add(facebookphoto2);
                hashmap.remove(s1);
            } while(cursor.moveToNext());
        cursor.close();
        if(flag)
            for(iterator3 = hashmap.values().iterator(); iterator3.hasNext(); arraylist.add((FacebookPhoto)iterator3.next()));
        if(arraylist.size() > 0 || arraylist1.size() > 0 || arraylist2.size() > 0)
            D((new StringBuilder()).append("adding ").append(arraylist.size()).append(" photos, updating ").append(arraylist1.size()).append(", and deleting ").append(arraylist2.size()).toString());
        ContentResolver contentresolver = context.getContentResolver();
        if(arraylist.size() > 0)
        {
            ContentValues acontentvalues[] = new ContentValues[arraylist.size()];
            Iterator iterator2 = arraylist.iterator();
            int i = 0;
            FacebookPhoto facebookphoto1;
            ContentValues contentvalues1;
            for(; iterator2.hasNext(); contentvalues1.put("position", Long.valueOf(facebookphoto1.position)))
            {
                facebookphoto1 = (FacebookPhoto)iterator2.next();
                contentvalues1 = new ContentValues();
                acontentvalues[i] = contentvalues1;
                i++;
                contentvalues1.put("pid", facebookphoto1.getPhotoId());
                contentvalues1.put("aid", facebookphoto1.getAlbumId());
                contentvalues1.put("owner", Long.valueOf(facebookphoto1.getOwnerId()));
                contentvalues1.put("src", facebookphoto1.getSrc());
                contentvalues1.put("src_big", facebookphoto1.getSrcBig());
                contentvalues1.put("src_small", facebookphoto1.getSrcSmall());
                contentvalues1.put("caption", facebookphoto1.getCaption());
                contentvalues1.put("created", Long.valueOf(facebookphoto1.getCreated()));
            }

            contentresolver.bulkInsert(PhotosProvider.PHOTOS_CONTENT_URI, acontentvalues);
        }
        if(arraylist1.size() > 0)
        {
            ContentValues contentvalues = new ContentValues();
            FacebookPhoto facebookphoto;
            for(Iterator iterator1 = arraylist1.iterator(); iterator1.hasNext(); contentresolver.update(Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, facebookphoto.getPhotoId()), contentvalues, null, null))
            {
                facebookphoto = (FacebookPhoto)iterator1.next();
                contentvalues.clear();
                contentvalues.put("aid", facebookphoto.getAlbumId());
                contentvalues.put("owner", Long.valueOf(facebookphoto.getOwnerId()));
                contentvalues.put("src", facebookphoto.getSrc());
                contentvalues.put("src_big", facebookphoto.getSrcBig());
                contentvalues.put("src_small", facebookphoto.getSrcSmall());
                contentvalues.put("caption", facebookphoto.getCaption());
                contentvalues.put("created", Long.valueOf(facebookphoto.getCreated()));
                contentvalues.put("position", Long.valueOf(facebookphoto.position));
            }

        }
        if(arraylist2.size() > 0)
        {
            if(!$assertionsDisabled && s == null)
                throw new AssertionError();
            contentresolver.delete(Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, s), buildDeleteSelection(arraylist2), null);
        }
        if(flag && flag1 && flag4)
            Utils.setAlbumsSize(context, s, collection.size());
        com/facebook/katana/service/method/PhotoSyncModel;
        JVM INSTR monitorexit ;
    }

    private static boolean photosIdentical(FacebookPhoto facebookphoto, Cursor cursor)
    {
        String s = cursor.getString(2);
        String s1 = cursor.getString(0);
        long l = cursor.getLong(3);
        boolean flag;
        if(StringUtils.saneStringEquals(facebookphoto.getCaption(), s) && StringUtils.saneStringEquals(facebookphoto.getAlbumId(), s1) && facebookphoto.position == l)
            flag = true;
        else
            flag = false;
        return flag;
    }

    static final boolean $assertionsDisabled;
    private static boolean DEBUG = false;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/PhotoSyncModel.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
