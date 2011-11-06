// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageUtils.java

package com.facebook.katana.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.*;
import android.net.Uri;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.katana.version.SDK5;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils
{
    public static class Dimension
    {

        public int getHeight()
        {
            return height;
        }

        public int getWidth()
        {
            return width;
        }

        public void setHeight(int i)
        {
            height = i;
        }

        public void setWidth(int i)
        {
            width = i;
        }

        private int height;
        private int width;

        Dimension(int i, int j)
        {
            width = i;
            height = j;
        }
    }


    public ImageUtils()
    {
    }

    public static Bitmap cropBitmapCenter(Bitmap bitmap, int i, int j)
    {
        Bitmap bitmap1 = Bitmap.createBitmap(i, j, android.graphics.Bitmap.Config.ARGB_8888);
        (new Canvas(bitmap1)).drawBitmap(bitmap, new Rect((bitmap.getWidth() - i) / 2, (bitmap.getHeight() - j) / 2, (i + bitmap.getWidth()) / 2, (j + bitmap.getHeight()) / 2), new Rect(0, 0, i, j), null);
        return bitmap1;
    }

    public static Bitmap decodeByteArray(byte abyte0[], int i, int j)
    {
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(abyte0, i, j);
        Bitmap bitmap = bitmap1;
_L2:
        return bitmap;
        OutOfMemoryError outofmemoryerror;
        outofmemoryerror;
        bitmap = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static Bitmap decodeFile(String s, android.graphics.BitmapFactory.Options options)
    {
        Bitmap bitmap1 = BitmapFactory.decodeFile(s, options);
        Bitmap bitmap = bitmap1;
_L2:
        return bitmap;
        OutOfMemoryError outofmemoryerror;
        outofmemoryerror;
        bitmap = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static Dimension fit(int i, int j, int k, int l)
    {
        Dimension dimension;
        if(k <= 0 || l <= 0)
        {
            dimension = new Dimension(0, 0);
        } else
        {
            float f = Math.max(Math.max(1F, (float)i / (float)k), Math.max(1F, (float)j / (float)l));
            dimension = new Dimension((int)((float)i / f), (int)((float)j / f));
        }
        return dimension;
    }

    public static void formatPhotoStreamImageView(Context context, LinearLayout linearlayout, Bitmap bitmap)
    {
        if(linearlayout != null)
            if(bitmap == null)
            {
                linearlayout.setVisibility(8);
            } else
            {
                linearlayout.setVisibility(0);
                ImageView imageview = (ImageView)linearlayout.findViewById(0x7f0e002d);
                imageview.setImageBitmap(bitmap);
                int i = (int)TypedValue.applyDimension(1, STREAM_PHOTO_PADDING, context.getResources().getDisplayMetrics());
                linearlayout.setPadding(i, i, i, i);
                int j = (int)TypedValue.applyDimension(1, STREAM_PHOTO_SIZE, context.getResources().getDisplayMetrics());
                Dimension dimension = fit(bitmap.getWidth(), bitmap.getHeight(), j, j);
                imageview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(dimension.getWidth() + i * 2, dimension.getHeight() + i * 2));
            }
    }

    public static int getOrientation(Context context, Uri uri)
    {
        int i;
        if(uri.getScheme().equals("content") && uri.getHost().equals("media") && uri.getPath().startsWith(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath()))
        {
            ContentResolver contentresolver = context.getContentResolver();
            String as[] = new String[1];
            as[0] = "orientation";
            Cursor cursor = contentresolver.query(uri, as, null, null, null);
            if(cursor.getCount() != 1)
            {
                i = -1;
            } else
            {
                cursor.moveToFirst();
                i = cursor.getInt(0);
            }
        } else
        if(uri.getScheme().equals("file") && Integer.parseInt(android.os.Build.VERSION.SDK) >= 5)
            i = SDK5.getJpegExifOrientation(uri.getPath());
        else
            i = -1;
        return i;
    }

    public static Bitmap resizeBitmap(String s, int i, int j)
    {
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(s, options);
        int k = options.outWidth;
        int l = options.outHeight;
        Bitmap bitmap;
        if(k > i || l > j)
        {
            float f = (float)k / (float)i;
            float f1 = (float)l / (float)j;
            float f2;
            if(f > f1)
                f2 = i;
            else
                f2 = (float)k / f1;
            if((float)k / f2 > 1F)
            {
                android.graphics.BitmapFactory.Options options1 = new android.graphics.BitmapFactory.Options();
                options1.inSampleSize = k / (int)f2;
                bitmap = decodeFile(s, options1);
            } else
            {
                bitmap = BitmapFactory.decodeFile(s);
            }
        } else
        {
            bitmap = BitmapFactory.decodeFile(s);
        }
        return bitmap;
    }

    public static Bitmap resizeBitmapAndFrame(String s, int i, int j, int k)
    {
        float f3;
        float f4;
        Bitmap bitmap1;
        Bitmap bitmap2;
        Canvas canvas;
        int l = i + -3;
        int i1 = j + -3;
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(s, options);
        int j1 = options.outWidth;
        int k1 = options.outHeight;
        if(j1 > l || k1 > i1)
        {
            float f = (float)j1 / (float)l;
            float f1 = (float)k1 / (float)i1;
            Bitmap bitmap;
            Bitmap bitmap3;
            if(f > f1)
            {
                f4 = l;
                f3 = (float)k1 / f;
            } else
            {
                float f2 = (float)j1 / f1;
                f3 = i1;
                f4 = f2;
            }
            if((float)j1 / f4 > 1F)
            {
                android.graphics.BitmapFactory.Options options1 = new android.graphics.BitmapFactory.Options();
                options1.inSampleSize = j1 / (int)f4;
                bitmap = decodeFile(s, options1);
            } else
            {
                bitmap = BitmapFactory.decodeFile(s);
            }
            bitmap1 = bitmap;
        } else
        {
            f4 = j1;
            f3 = k1;
            bitmap1 = BitmapFactory.decodeFile(s);
        }
        if(bitmap1 == null)
            break MISSING_BLOCK_LABEL_493;
        bitmap3 = Bitmap.createBitmap(3 + (int)f4, 3 + (int)f3, android.graphics.Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap3);
        canvas.drawColor(-1);
        k;
        JVM INSTR tableswitch 0 1: default 208
    //                   0 314
    //                   1 415;
           goto _L1 _L2 _L3
_L1:
        canvas.drawBitmap(bitmap1, new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight()), new Rect(3, 3, 1 + (int)f4, (int)f3), mResizePaint);
_L4:
        bitmap1.recycle();
        bitmap2 = bitmap3;
_L5:
        return bitmap2;
_L2:
        canvas.drawBitmap(bitmap1, new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight()), new Rect(3, 3, 1 + (int)f4, (int)f3), mResizePaint);
        canvas.drawRect(0F, 0F, (3F + f4) - 1F, (3F + f3) - 1F, mOutStrokePaint);
        Paint paint = mInStrokePaint;
        canvas.drawRect(2F, 2F, f4, f3, paint);
          goto _L4
_L3:
        canvas.drawBitmap(bitmap1, new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight()), new Rect(3, 3, 1 + (int)f4, (int)f3), mResizePaint);
        canvas.drawRect(1F, 0F, (f4 + 3F) - 1F, (f3 + 3F) - 1F, mSolidStrokePaint);
          goto _L4
        bitmap2 = null;
          goto _L5
    }

    public static Bitmap resizeToSquareBitmap(Bitmap bitmap, int i)
    {
        Bitmap bitmap1 = Bitmap.createBitmap(i, i, android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        int j;
        int k;
        if(bitmap.getWidth() > bitmap.getHeight())
        {
            j = i;
            k = (i * bitmap.getHeight()) / bitmap.getWidth();
        } else
        {
            j = (i * bitmap.getWidth()) / bitmap.getHeight();
            k = i;
        }
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect((i - j) / 2, (i - k) / 2, (i + j) / 2, (i + k) / 2), mResizePaint);
        return bitmap1;
    }

    public static Bitmap scaleImage(Context context, Uri uri, int i, int j)
        throws IOException
    {
        InputStream inputstream = context.getContentResolver().openInputStream(uri);
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputstream, null, options);
        inputstream.close();
        int k = getOrientation(context, uri);
        int l;
        int i1;
        InputStream inputstream1;
        Bitmap bitmap;
        if(k == 90 || k == 270)
        {
            l = options.outHeight;
            i1 = options.outWidth;
        } else
        {
            l = options.outWidth;
            i1 = options.outHeight;
        }
        inputstream1 = context.getContentResolver().openInputStream(uri);
        if(l > i || i1 > j)
        {
            float f = Math.max((float)l / (float)i, (float)i1 / (float)j);
            android.graphics.BitmapFactory.Options options1 = new android.graphics.BitmapFactory.Options();
            options1.inSampleSize = (int)f;
            bitmap = BitmapFactory.decodeStream(inputstream1, null, options1);
        } else
        {
            bitmap = BitmapFactory.decodeStream(inputstream1);
        }
        inputstream1.close();
        if(k > 0)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(k);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    public static final int FRAME_HOLLOW = 0;
    public static final int FRAME_SOLID = 1;
    public static final int PHOTO_BORDER = 3;
    public static float STREAM_PHOTO_PADDING = 7F;
    public static float STREAM_PHOTO_SIZE = 50F;
    private static final Paint mInStrokePaint;
    private static final Paint mOutStrokePaint;
    private static final Paint mResizePaint = new Paint(2);
    private static final Paint mSolidStrokePaint;

    static 
    {
        mOutStrokePaint = new Paint();
        mOutStrokePaint.setStrokeWidth(1F);
        mOutStrokePaint.setStyle(android.graphics.Paint.Style.STROKE);
        mOutStrokePaint.setColor(0xffe0e0e0);
        mInStrokePaint = new Paint();
        mInStrokePaint.setStrokeWidth(1F);
        mInStrokePaint.setStyle(android.graphics.Paint.Style.STROKE);
        mInStrokePaint.setColor(0xfff0f0f0);
        mSolidStrokePaint = new Paint();
        mSolidStrokePaint.setStrokeWidth(3F);
        mSolidStrokePaint.setStyle(android.graphics.Paint.Style.STROKE);
        mSolidStrokePaint.setColor(0xfff0f0f0);
    }
}
