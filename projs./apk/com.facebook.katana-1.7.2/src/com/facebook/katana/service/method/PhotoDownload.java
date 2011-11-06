// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoDownload.java

package com.facebook.katana.service.method;

import android.content.*;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import com.facebook.katana.binding.StreamPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.FileUtils;
import com.facebook.katana.util.ImageUtils;
import java.io.*;
import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiLogging, HttpOperation, ApiMethodListener

public class PhotoDownload extends ApiMethod
    implements HttpOperation.HttpOperationListener
{

    public PhotoDownload(Context context, Intent intent, long l, String s, String s1, String s2, 
            int i, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", null, s2, apimethodlistener);
        mOwnerId = l;
        mAlbumId = s;
        mPhotoId = s1;
        mType = i;
        mFilename = FileUtils.buildFilename(context);
    }

    public static StreamPhoto insertProfilePhoto(Context context, String s, String s1)
        throws IOException
    {
        Bitmap bitmap = BitmapFactory.decodeFile(s1);
        if(bitmap != null)
        {
            Bitmap bitmap1 = ImageUtils.resizeToSquareBitmap(bitmap, (int)(50F * context.getResources().getDisplayMetrics().density));
            bitmap.recycle();
            String s2 = FileUtils.buildFilename(context);
            FileOutputStream fileoutputstream = new FileOutputStream(s2);
            bitmap1.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("url", s);
            contentvalues.put("filename", s2);
            return new StreamPhoto(context.getContentResolver().insert(PhotosProvider.STREAM_PHOTOS_CONTENT_URI, contentvalues), s, s2, (new File(s2)).length(), bitmap1);
        } else
        {
            throw new IOException("Cannot decode bitmap");
        }
    }

    public static StreamPhoto insertStreamPhoto(Context context, String s, String s1)
        throws IOException
    {
        Bitmap bitmap = ImageUtils.resizeBitmap(s1, 150, 150);
        if(bitmap != null)
        {
            String s2 = FileUtils.buildFilename(context);
            FileOutputStream fileoutputstream = new FileOutputStream(s2);
            ContentValues contentvalues;
            if(bitmap.getConfig() == android.graphics.Bitmap.Config.RGB_565)
                bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 80, fileoutputstream);
            else
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 80, fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
            contentvalues = new ContentValues();
            contentvalues.put("url", s);
            contentvalues.put("filename", s2);
            return new StreamPhoto(context.getContentResolver().insert(PhotosProvider.STREAM_PHOTOS_CONTENT_URI, contentvalues), s, s2, (new File(s2)).length(), bitmap);
        } else
        {
            throw new IOException("Cannot decode bitmap");
        }
    }

    private static void updateAlbumThumbnail(Context context, long l, String s, String s1)
        throws IOException
    {
        Bitmap bitmap = BitmapFactory.decodeFile(s1);
        if(bitmap != null)
        {
            Bitmap bitmap1 = ImageUtils.cropBitmapCenter(bitmap, 56, 56);
            bitmap.recycle();
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(4 * (bitmap1.getWidth() * bitmap1.getHeight()));
            bitmap1.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream);
            bytearrayoutputstream.flush();
            bytearrayoutputstream.close();
            bitmap1.recycle();
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("thumbnail", bytearrayoutputstream.toByteArray());
            Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_OWNER_CONTENT_URI, (new StringBuilder()).append("").append(l).toString());
            ContentResolver contentresolver = context.getContentResolver();
            String as[] = new String[1];
            as[0] = s;
            contentresolver.update(uri, contentvalues, "aid IN(?)", as);
            return;
        } else
        {
            throw new IOException("Cannot decode bitmap");
        }
    }

    private static void updatePhotoFilename(Context context, String s, String s1, String s2)
        throws NullPointerException
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("filename", s2);
        Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, s);
        ContentResolver contentresolver = context.getContentResolver();
        String as[] = new String[1];
        as[0] = s1;
        contentresolver.update(uri, contentvalues, "pid IN(?)", as);
    }

    public static void updatePhotoThumbnail(Context context, String s, String s1, String s2)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(2048);
        FileInputStream fileinputstream = new FileInputStream(s2);
        byte abyte0[] = new byte[1024];
        do
        {
            int i = fileinputstream.read(abyte0);
            if(i > 0)
            {
                bytearrayoutputstream.write(abyte0, 0, i);
            } else
            {
                fileinputstream.close();
                ContentValues contentvalues = new ContentValues();
                contentvalues.put("thumbnail", bytearrayoutputstream.toByteArray());
                Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, s);
                ContentResolver contentresolver = context.getContentResolver();
                String as[] = new String[1];
                as[0] = s1;
                contentresolver.update(uri, contentvalues, "pid IN(?)", as);
                return;
            }
        } while(true);
    }

    public Bitmap getBitmap()
    {
        return mBitmap;
    }

    public StreamPhoto getPhoto()
    {
        return mPhoto;
    }

    public void onHttpOperationComplete(HttpOperation httpoperation, int i, String s, OutputStream outputstream, Exception exception)
    {
        long l = 0L;
        if(i != 200) goto _L2; else goto _L1
_L1:
        File file;
        file = new File(mFilename);
        l = file.length();
        mType;
        JVM INSTR tableswitch 72 77: default 76
    //                   72 190
    //                   73 140
    //                   74 240
    //                   75 290
    //                   76 388
    //                   77 340;
           goto _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L3:
        final int fErrorCode = i;
        final String fReasonPhrase = s;
        final Exception fex = exception;
        if(ApiLogging.reportAndCheckTrx(i))
            ApiLogging.logTransferResponse(mContext, httpoperation.mHttpMethod.getURI().toString(), httpoperation.calculateTimeElapsed(), l, fErrorCode);
        mHandler.post(new Runnable() {

            public void run()
            {
                if(mHttpOp != null)
                {
                    mHttpOp = null;
                    mListener.onOperationComplete(PhotoDownload.this, fErrorCode, fReasonPhrase, fex);
                }
            }

            final PhotoDownload this$0;
            final int val$fErrorCode;
            final String val$fReasonPhrase;
            final Exception val$fex;

            
            {
                this$0 = PhotoDownload.this;
                fErrorCode = i;
                fReasonPhrase = s;
                fex = exception;
                super();
            }
        }
);
        return;
_L5:
        try
        {
            mPhoto = insertStreamPhoto(mContext, mBaseUrl, mFilename);
        }
        catch(IOException ioexception3)
        {
            i = 0;
            s = null;
            exception = ioexception3;
        }
        (new File(mFilename)).delete();
        continue; /* Loop/switch isn't completed */
_L4:
        try
        {
            mPhoto = insertProfilePhoto(mContext, mBaseUrl, mFilename);
        }
        catch(IOException ioexception2)
        {
            i = 0;
            s = null;
            exception = ioexception2;
        }
        (new File(mFilename)).delete();
        continue; /* Loop/switch isn't completed */
_L6:
        try
        {
            updateAlbumThumbnail(mContext, mOwnerId, mAlbumId, mFilename);
        }
        catch(IOException ioexception1)
        {
            i = 0;
            s = null;
            exception = ioexception1;
        }
        (new File(mFilename)).delete();
        continue; /* Loop/switch isn't completed */
_L7:
        try
        {
            updatePhotoThumbnail(mContext, mAlbumId, mPhotoId, mFilename);
        }
        catch(IOException ioexception)
        {
            i = 0;
            s = null;
            exception = ioexception;
        }
        (new File(mFilename)).delete();
        continue; /* Loop/switch isn't completed */
_L9:
        try
        {
            mBitmap = BitmapFactory.decodeFile(mFilename);
        }
        catch(NullPointerException nullpointerexception1)
        {
            (new File(mFilename)).delete();
            i = 0;
            s = null;
            exception = nullpointerexception1;
        }
        file.delete();
        continue; /* Loop/switch isn't completed */
_L8:
        try
        {
            updatePhotoFilename(mContext, mAlbumId, mPhotoId, mFilename);
        }
        catch(NullPointerException nullpointerexception)
        {
            (new File(mFilename)).delete();
            i = 0;
            s = null;
            exception = nullpointerexception;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        (new File(mFilename)).delete();
        if(true) goto _L3; else goto _L10
_L10:
    }

    public void onHttpOperationProgress(HttpOperation httpoperation, long l, long l1)
    {
    }

    public void start()
    {
        mHttpOp = new HttpOperation(mContext, mHttpMethod, mBaseUrl, new FileOutputStream(mFilename), this, false);
        mHttpOp.start();
_L1:
        return;
        Exception exception;
        exception;
        exception.printStackTrace();
        mListener.onOperationComplete(this, 0, null, exception);
          goto _L1
    }

    private final String mAlbumId;
    private Bitmap mBitmap;
    private final String mFilename;
    private final long mOwnerId;
    private StreamPhoto mPhoto;
    private final String mPhotoId;
    private final int mType;
}
