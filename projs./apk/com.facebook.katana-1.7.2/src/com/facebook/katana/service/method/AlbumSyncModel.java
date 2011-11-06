// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbumSyncModel.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.Factory;
import com.facebook.katana.util.Log;
import java.util.*;

public class AlbumSyncModel
{
    public static class Result
    {

        public final boolean done;
        public final Collection missingCovers;

        Result(Collection collection)
        {
            done = false;
            missingCovers = collection;
        }

        Result(boolean flag)
        {
            done = flag;
            missingCovers = null;
        }
    }

    private static interface AlbumsQuery
    {

        public static final int INDEX_ALBUM_ID = 0;
        public static final int INDEX_COVER_PHOTO_URL = 2;
        public static final int INDEX_MODIFIED = 1;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[3];
            as[0] = "aid";
            as[1] = "modified";
            as[2] = "cover_url";
        }
    }


    public AlbumSyncModel()
    {
    }

    private static void D(String s)
    {
        if(DEBUG)
            Log.d("AlbumSyncModel", s);
    }

    public static void assignCoversToAlbums(Collection collection, Collection collection1)
    {
        HashMap hashmap = new HashMap();
        FacebookPhoto facebookphoto1;
        for(Iterator iterator = collection.iterator(); iterator.hasNext(); hashmap.put(facebookphoto1.getPhotoId(), facebookphoto1))
            facebookphoto1 = (FacebookPhoto)iterator.next();

        Iterator iterator1 = collection1.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            FacebookAlbum facebookalbum = (FacebookAlbum)iterator1.next();
            FacebookPhoto facebookphoto = (FacebookPhoto)hashmap.get(facebookalbum.getCoverPhotoId());
            if(facebookphoto != null)
                facebookalbum.setCoverPhotoUrl(facebookphoto.getSrcSmall());
        } while(true);
    }

    private static void commit(ContentResolver contentresolver, Collection collection, Collection collection1, Collection collection2, long l)
    {
        Uri uri;
        if(-1L != l)
            uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, Long.toString(l));
        else
            uri = null;
        if(collection.size() > 0)
        {
            ContentValues acontentvalues[] = new ContentValues[collection.size()];
            Iterator iterator3 = collection.iterator();
            int i = 0;
            FacebookAlbum facebookalbum1;
            ContentValues contentvalues1;
            for(; iterator3.hasNext(); contentvalues1.put("object_id", Long.valueOf(facebookalbum1.getObjectId())))
            {
                facebookalbum1 = (FacebookAlbum)iterator3.next();
                contentvalues1 = new ContentValues();
                acontentvalues[i] = contentvalues1;
                i++;
                contentvalues1.put("aid", facebookalbum1.getAlbumId());
                contentvalues1.put("cover_pid", facebookalbum1.getCoverPhotoId());
                contentvalues1.put("cover_url", facebookalbum1.getCoverPhotoUrl());
                contentvalues1.put("owner", Long.valueOf(facebookalbum1.getOwner()));
                contentvalues1.put("name", facebookalbum1.getName());
                contentvalues1.put("created", Long.valueOf(facebookalbum1.getCreated()));
                contentvalues1.put("modified", Long.valueOf(facebookalbum1.getModified()));
                contentvalues1.put("description", facebookalbum1.getDescription());
                contentvalues1.put("location", facebookalbum1.getLocation());
                contentvalues1.put("size", Integer.valueOf(facebookalbum1.getSize()));
                contentvalues1.put("visibility", facebookalbum1.getVisibility());
                contentvalues1.put("type", facebookalbum1.getType());
            }

            contentresolver.bulkInsert(PhotosProvider.ALBUMS_CONTENT_URI, acontentvalues);
        }
        if(collection1.size() > 0)
        {
            ContentValues contentvalues = new ContentValues();
            Uri uri1;
            String as[];
            for(Iterator iterator = collection1.iterator(); iterator.hasNext(); contentresolver.update(uri1, contentvalues, "aid IN(?)", as))
            {
                FacebookAlbum facebookalbum = (FacebookAlbum)iterator.next();
                contentvalues.clear();
                contentvalues.put("cover_pid", facebookalbum.getCoverPhotoId());
                contentvalues.put("cover_url", facebookalbum.getCoverPhotoUrl());
                contentvalues.put("name", facebookalbum.getName());
                contentvalues.put("created", Long.valueOf(facebookalbum.getCreated()));
                contentvalues.put("modified", Long.valueOf(facebookalbum.getModified()));
                contentvalues.put("description", facebookalbum.getDescription());
                contentvalues.put("location", facebookalbum.getLocation());
                contentvalues.put("size", Integer.valueOf(facebookalbum.getSize()));
                contentvalues.put("visibility", facebookalbum.getVisibility());
                contentvalues.put("type", facebookalbum.getType());
                if(facebookalbum.hasCoverChanged())
                    contentvalues.put("thumbnail", (byte[])null);
                uri1 = uri;
                if(uri1 == null)
                    uri1 = ContentUris.withAppendedId(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, facebookalbum.getOwner());
                as = new String[1];
                as[0] = facebookalbum.getAlbumId();
            }

        }
        if(collection2.size() > 0)
        {
            StringBuilder stringbuilder = new StringBuilder(128);
            stringbuilder.append("aid").append(" IN(");
            Iterator iterator1 = collection2.iterator();
            boolean flag = true;
            while(iterator1.hasNext()) 
            {
                String s1 = (String)iterator1.next();
                if(flag)
                    flag = false;
                else
                    stringbuilder.append(',');
                stringbuilder.append('\'').append(s1).append('\'');
            }
            stringbuilder.append(')');
            if(!$assertionsDisabled && uri == null)
                throw new AssertionError();
            contentresolver.delete(uri, stringbuilder.toString(), null);
            StringBuilder stringbuilder1 = new StringBuilder(128);
            stringbuilder1.append("aid").append(" IN(");
            Iterator iterator2 = collection2.iterator();
            boolean flag1 = true;
            while(iterator2.hasNext()) 
            {
                String s = (String)iterator2.next();
                if(flag1)
                    flag1 = false;
                else
                    stringbuilder1.append(',');
                stringbuilder1.append('\'').append(s).append('\'');
            }
            stringbuilder1.append(')');
            contentresolver.delete(PhotosProvider.PHOTOS_CONTENT_URI, stringbuilder1.toString(), null);
        }
    }

    public static Cursor cursorForAlbums(ContentResolver contentresolver, List list)
    {
        StringBuilder stringbuilder = new StringBuilder(128);
        stringbuilder.append("aid").append(" IN(");
        boolean flag = true;
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) 
        {
            FacebookAlbum facebookalbum = (FacebookAlbum)iterator.next();
            if(flag)
                flag = false;
            else
                stringbuilder.append(',');
            stringbuilder.append('\'');
            stringbuilder.append(facebookalbum.getAlbumId());
            stringbuilder.append('\'');
        }
        stringbuilder.append(')');
        return contentresolver.query(PhotosProvider.ALBUMS_CONTENT_URI, AlbumsQuery.PROJECTION, stringbuilder.toString(), null, null);
    }

    public static Cursor cursorForAlbumsForUser(ContentResolver contentresolver, long l)
    {
        return contentresolver.query(Uri.withAppendedPath(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, (new StringBuilder()).append("").append(l).toString()), AlbumsQuery.PROJECTION, null, null, null);
    }

    /**
     * @deprecated Method doSync is deprecated
     */

    public static Result doSync(ContentResolver contentresolver, List list, Factory factory, boolean flag, boolean flag1, boolean flag2, boolean flag3, long l)
    {
        com/facebook/katana/service/method/AlbumSyncModel;
        JVM INSTR monitorenter ;
        ArrayList arraylist;
        ArrayList arraylist1;
        ArrayList arraylist2;
        ArrayList arraylist3;
        HashMap hashmap;
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        arraylist2 = new ArrayList();
        arraylist3 = new ArrayList();
        D((new StringBuilder()).append("received ").append(list.size()).append(" albums").toString());
        hashmap = new HashMap();
        FacebookAlbum facebookalbum2;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); hashmap.put(facebookalbum2.getAlbumId(), facebookalbum2))
            facebookalbum2 = (FacebookAlbum)iterator.next();

        break MISSING_BLOCK_LABEL_138;
        Exception exception;
        exception;
        throw exception;
        Cursor cursor = (Cursor)factory.make();
        if(!cursor.moveToFirst()) goto _L2; else goto _L1
_L1:
        String s2;
        FacebookAlbum facebookalbum1;
        s2 = cursor.getString(0);
        facebookalbum1 = (FacebookAlbum)hashmap.get(s2);
        if(facebookalbum1 != null) goto _L4; else goto _L3
_L3:
        if(flag2)
            arraylist2.add(s2);
_L9:
        hashmap.remove(s2);
        if(cursor.moveToNext()) goto _L1; else goto _L2
_L2:
        cursor.close();
        if(flag)
            arraylist.addAll(hashmap.values());
        D((new StringBuilder()).append("found ").append(arraylist.size()).append(" albums to add, ").append(arraylist1.size()).append(" albums to update, and ").append(arraylist2.size()).append(" albums to delete").toString());
        if(!flag3) goto _L6; else goto _L5
_L5:
        boolean flag4;
        String s3;
        String s4;
        for(Iterator iterator1 = arraylist.iterator(); iterator1.hasNext();)
        {
            String s1 = ((FacebookAlbum)iterator1.next()).getCoverPhotoId();
            if(s1 != null)
                arraylist3.add(s1);
        }

        Iterator iterator2 = arraylist1.iterator();
        do
        {
            if(!iterator2.hasNext())
                break;
            FacebookAlbum facebookalbum = (FacebookAlbum)iterator2.next();
            if(facebookalbum.hasCoverChanged())
            {
                String s = facebookalbum.getCoverPhotoId();
                if(s != null)
                    arraylist3.add(s);
            }
        } while(true);
          goto _L7
_L4:
        if(facebookalbum1.getModified() == cursor.getLong(1)) goto _L9; else goto _L8
_L8:
        s3 = facebookalbum1.getCoverPhotoUrl();
        s4 = cursor.getString(2);
        if(s3 != null || s4 != null) goto _L11; else goto _L10
_L10:
        facebookalbum1.setCoverChanged(false);
_L16:
        arraylist1.add(facebookalbum1);
          goto _L9
_L11:
        if(s3 == null || s4 == null) goto _L13; else goto _L12
_L12:
        if(s3.equals(s4)) goto _L15; else goto _L14
_L14:
        flag4 = true;
_L20:
        facebookalbum1.setCoverChanged(flag4);
          goto _L16
_L13:
        facebookalbum1.setCoverChanged(true);
          goto _L16
_L7:
        if(arraylist3.size() != 0) goto _L18; else goto _L17
_L17:
        Result result;
        D("no covers missing");
        commit(contentresolver, arraylist, arraylist1, arraylist2, l);
        result = new Result(true);
_L19:
        com/facebook/katana/service/method/AlbumSyncModel;
        JVM INSTR monitorexit ;
        return result;
_L18:
        D((new StringBuilder()).append("missing ").append(arraylist3.size()).append(" covers").toString());
        result = new Result(arraylist3);
        continue; /* Loop/switch isn't completed */
_L6:
        commit(contentresolver, arraylist, arraylist1, arraylist2, l);
        result = new Result(true);
        if(true) goto _L19; else goto _L15
_L15:
        flag4 = false;
          goto _L20
    }

    static final boolean $assertionsDisabled;
    private static boolean DEBUG = false;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/AlbumSyncModel.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
