// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileUtils.java

package com.facebook.katana.util;

import android.content.Context;
import java.io.*;

// Referenced classes of package com.facebook.katana.util:
//            StringUtils

public class FileUtils
{

    public FileUtils()
    {
    }

    public static String buildFilename(Context context)
    {
        return (new StringBuilder()).append(context.getFilesDir().getAbsolutePath()).append("/").append(StringUtils.randomString(4)).toString();
    }

    public static void copy(InputStream inputstream, OutputStream outputstream)
        throws IOException
    {
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.read(abyte0);
            if(i <= 0)
                break;
            outputstream.write(abyte0, 0, i);
        } while(true);
        break MISSING_BLOCK_LABEL_40;
        Exception exception;
        exception;
        inputstream.close();
        outputstream.close();
        throw exception;
        inputstream.close();
        outputstream.close();
        return;
    }

    public static void deleteFilesInDirectory(String s)
    {
        File file = new File(s);
        if(file.exists())
        {
            File afile[] = file.listFiles();
            if(afile != null)
            {
                for(int i = 0; i < afile.length; i++)
                    afile[i].delete();

            }
        }
    }

    public static byte[] getBytesFromFile(File file)
        throws IOException
    {
        FileInputStream fileinputstream = new FileInputStream(file);
        long l = file.length();
        if(l <= 0x7fffffffL);
        byte abyte0[] = new byte[(int)l];
        int i = 0;
        do
        {
            if(i >= abyte0.length)
                break;
            int j = fileinputstream.read(abyte0, i, abyte0.length - i);
            if(j < 0)
                break;
            i += j;
        } while(true);
        if(i < abyte0.length)
        {
            throw new IOException((new StringBuilder()).append("Could not completely read file ").append(file.getName()).toString());
        } else
        {
            fileinputstream.close();
            return abyte0;
        }
    }
}
