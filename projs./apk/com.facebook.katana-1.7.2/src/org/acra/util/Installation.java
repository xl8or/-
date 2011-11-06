// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Installation.java

package org.acra.util;

import android.content.Context;
import java.io.*;
import java.util.UUID;

public class Installation
{

    public Installation()
    {
    }

    /**
     * @deprecated Method id is deprecated
     */

    public static String id(Context context)
    {
        org/acra/util/Installation;
        JVM INSTR monitorenter ;
        File file;
        if(sID != null)
            break MISSING_BLOCK_LABEL_41;
        file = new File(context.getFilesDir(), "ACRA-INSTALLATION");
        if(!file.exists())
            writeInstallationFile(file);
        sID = readInstallationFile(file);
        String s = sID;
        org/acra/util/Installation;
        JVM INSTR monitorexit ;
        return s;
        Exception exception1;
        exception1;
        throw new RuntimeException(exception1);
        Exception exception;
        exception;
        org/acra/util/Installation;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static String readInstallationFile(File file)
        throws IOException
    {
        RandomAccessFile randomaccessfile;
        byte abyte0[];
        randomaccessfile = new RandomAccessFile(file, "r");
        abyte0 = new byte[(int)randomaccessfile.length()];
        randomaccessfile.readFully(abyte0);
        randomaccessfile.close();
        return new String(abyte0);
        Exception exception;
        exception;
        randomaccessfile.close();
        throw exception;
    }

    private static void writeInstallationFile(File file)
        throws IOException
    {
        FileOutputStream fileoutputstream = new FileOutputStream(file);
        fileoutputstream.write(UUID.randomUUID().toString().getBytes());
        fileoutputstream.close();
        return;
        Exception exception;
        exception;
        fileoutputstream.close();
        throw exception;
    }

    private static final String INSTALLATION = "ACRA-INSTALLATION";
    private static String sID = null;

}
