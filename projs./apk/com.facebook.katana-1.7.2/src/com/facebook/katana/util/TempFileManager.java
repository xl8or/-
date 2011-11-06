// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempFileManager.java

package com.facebook.katana.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import java.io.*;

// Referenced classes of package com.facebook.katana.util:
//            FileUtils, Log

public class TempFileManager
{

    public TempFileManager()
    {
    }

    public static Uri addImage(String s)
    {
        if(isExternalStorageMounted()) goto _L2; else goto _L1
_L1:
        Uri uri = null;
_L4:
        return uri;
_L2:
        File file = getDirectory();
        File file1;
        Uri uri1;
        try
        {
            file1 = File.createTempFile(".facebook_", ".jpg", file);
        }
        catch(IOException ioexception)
        {
            Log.d(TAG, "Cannot create temp file", ioexception);
            uri = null;
            continue; /* Loop/switch isn't completed */
        }
        FileUtils.copy(new FileInputStream(s), new FileOutputStream(file1));
        uri1 = Uri.fromFile(file1);
        uri = uri1;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        Log.d(TAG, "Error: ", exception);
        uri = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void cleanup()
    {
        if(isExternalStorageMounted())
        {
            AsyncTask asynctask = new AsyncTask() {

                protected transient Object doInBackground(File afile1[])
                {
                    long l = System.currentTimeMillis();
                    int i = afile1.length;
                    int j = 0;
                    while(j < i) 
                    {
                        File file = afile1[j];
                        if(!file.isDirectory())
                        {
                            Log.e(TempFileManager.TAG, (new StringBuilder()).append("Attempted to clean a non-directory ").append(file.toString()).toString());
                        } else
                        {
                            File afile2[] = file.listFiles();
                            int k = 0;
                            while(k < afile2.length) 
                            {
                                if(l - afile2[k].lastModified() > 60000L)
                                    afile2[k].delete();
                                k++;
                            }
                        }
                        j++;
                    }
                    return null;
                }

                protected volatile Object doInBackground(Object aobj[])
                {
                    return doInBackground((File[])aobj);
                }

            }
;
            File afile[] = new File[1];
            afile[0] = getDirectory();
            asynctask.execute(afile);
        }
    }

    private static File getDirectory()
    {
        File file2;
        if(!isExternalStorageMounted())
        {
            file2 = null;
        } else
        {
            File file = Environment.getExternalStorageDirectory();
            File file1 = new File((new StringBuilder()).append(file.toString()).append("/Android/data/com.facebook.katana/cache/").toString());
            file1.mkdirs();
            file2 = file1;
        }
        return file2;
    }

    private static boolean isExternalStorageMounted()
    {
        return Environment.getExternalStorageState().equals("mounted");
    }

    private static final String IMG_SUFFIX = ".jpg";
    private static final String PATH = "/Android/data/com.facebook.katana/cache/";
    private static final String PREFIX = ".facebook_";
    private static final String TAG = com/facebook/katana/util/TempFileManager.getName();
    private static final long TTL = 60000L;


}
