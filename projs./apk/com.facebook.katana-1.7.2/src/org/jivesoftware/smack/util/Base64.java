// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base64.java

package org.jivesoftware.smack.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Base64
{
    public static class OutputStream extends FilterOutputStream
    {

        public void close()
            throws IOException
        {
            flushBase64();
            super.close();
            buffer = null;
            out = null;
        }

        public void flushBase64()
            throws IOException
        {
label0:
            {
                if(position > 0)
                {
                    if(!encode)
                        break label0;
                    out.write(Base64.encode3to4(b4, buffer, position, options));
                    position = 0;
                }
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void resumeEncoding()
        {
            suspendEncoding = false;
        }

        public void suspendEncoding()
            throws IOException
        {
            flushBase64();
            suspendEncoding = true;
        }

        public void write(int i)
            throws IOException
        {
            if(!suspendEncoding) goto _L2; else goto _L1
_L1:
            super.out.write(i);
_L4:
            return;
_L2:
            if(encode)
            {
                byte abyte1[] = buffer;
                int l = position;
                position = l + 1;
                abyte1[l] = (byte)i;
                if(position >= bufferLength)
                {
                    out.write(Base64.encode3to4(b4, buffer, bufferLength, options));
                    lineLength = 4 + lineLength;
                    if(breakLines && lineLength >= 76)
                    {
                        out.write(10);
                        lineLength = 0;
                    }
                    position = 0;
                }
                continue; /* Loop/switch isn't completed */
            }
            if(decodabet[i & 0x7f] <= -5)
                break; /* Loop/switch isn't completed */
            byte abyte0[] = buffer;
            int j = position;
            position = j + 1;
            abyte0[j] = (byte)i;
            if(position >= bufferLength)
            {
                int k = Base64.decode4to3(buffer, 0, b4, 0, options);
                out.write(b4, 0, k);
                position = 0;
            }
            if(true) goto _L4; else goto _L3
_L3:
            if(decodabet[i & 0x7f] == -5) goto _L4; else goto _L5
_L5:
            throw new IOException("Invalid character in Base64 data.");
        }

        public void write(byte abyte0[], int i, int j)
            throws IOException
        {
            if(suspendEncoding)
            {
                super.out.write(abyte0, i, j);
            } else
            {
                int k = 0;
                while(k < j) 
                {
                    write(abyte0[i + k]);
                    k++;
                }
            }
        }

        private byte alphabet[];
        private byte b4[];
        private boolean breakLines;
        private byte buffer[];
        private int bufferLength;
        private byte decodabet[];
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream outputstream)
        {
            this(outputstream, 1);
        }

        public OutputStream(java.io.OutputStream outputstream, int i)
        {
            super(outputstream);
            boolean flag;
            boolean flag1;
            int j;
            if((i & 8) != 8)
                flag = true;
            else
                flag = false;
            breakLines = flag;
            if((i & 1) == 1)
                flag1 = true;
            else
                flag1 = false;
            encode = flag1;
            if(encode)
                j = 3;
            else
                j = 4;
            bufferLength = j;
            buffer = new byte[bufferLength];
            position = 0;
            lineLength = 0;
            suspendEncoding = false;
            b4 = new byte[4];
            options = i;
            alphabet = Base64.getAlphabet(i);
            decodabet = Base64.getDecodabet(i);
        }
    }

    public static class InputStream extends FilterInputStream
    {

        public int read()
            throws IOException
        {
            if(position >= 0) goto _L2; else goto _L1
_L1:
            if(!encode) goto _L4; else goto _L3
_L3:
            byte abyte2[];
            int i1;
            int j1;
            abyte2 = new byte[3];
            i1 = 0;
            j1 = 0;
_L6:
            if(i1 >= 3)
                break; /* Loop/switch isn't completed */
            int k1 = in.read();
            if(k1 < 0)
                break MISSING_BLOCK_LABEL_56;
            abyte2[i1] = (byte)k1;
            j1++;
_L7:
            i1++;
            if(true) goto _L6; else goto _L5
            IOException ioexception;
            ioexception;
            if(i1 == 0)
                throw ioexception;
              goto _L7
_L5:
            if(j1 <= 0) goto _L9; else goto _L8
_L8:
            Base64.encode3to4(abyte2, 0, j1, buffer, 0, options);
            position = 0;
            numSigBytes = 4;
_L2:
            if(position < 0) goto _L11; else goto _L10
_L10:
            int j;
            if(position >= numSigBytes)
                j = -1;
            else
            if(encode && breakLines && lineLength >= 76)
            {
                lineLength = 0;
                j = 10;
            } else
            {
                lineLength = 1 + lineLength;
                byte abyte0[] = buffer;
                int i = position;
                position = i + 1;
                byte byte0 = abyte0[i];
                if(position >= bufferLength)
                    position = -1;
                j = byte0 & 0xff;
            }
            return j;
_L9:
            j = -1;
            continue; /* Loop/switch isn't completed */
_L4:
            byte abyte1[] = new byte[4];
            int k = 0;
label0:
            do
            {
                int l;
label1:
                {
                    if(k < 4)
                    {
                        do
                            l = in.read();
                        while(l >= 0 && decodabet[l & 0x7f] <= -5);
                        if(l >= 0)
                            break label1;
                    }
                    if(k != 4)
                        break label0;
                    numSigBytes = Base64.decode4to3(abyte1, 0, buffer, 0, options);
                    position = 0;
                    continue; /* Loop/switch isn't completed */
                }
                abyte1[k] = (byte)l;
                k++;
            } while(true);
            if(k == 0)
                j = -1;
            else
                throw new IOException("Improperly padded Base64 input.");
            if(true) goto _L12; else goto _L11
_L12:
            break MISSING_BLOCK_LABEL_127;
_L11:
            throw new IOException("Error in Base64 code reading stream.");
            if(true) goto _L2; else goto _L13
_L13:
        }

        public int read(byte abyte0[], int i, int j)
            throws IOException
        {
            int k;
label0:
            {
                k = 0;
                do
                {
                    if(k >= j)
                        break label0;
                    int l = read();
                    if(l < 0)
                        break;
                    abyte0[i + k] = (byte)l;
                    k++;
                } while(true);
                if(k == 0)
                    k = -1;
            }
            return k;
        }

        private byte alphabet[];
        private boolean breakLines;
        private byte buffer[];
        private int bufferLength;
        private byte decodabet[];
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream inputstream)
        {
            this(inputstream, 0);
        }

        public InputStream(java.io.InputStream inputstream, int i)
        {
            super(inputstream);
            boolean flag;
            boolean flag1;
            int j;
            if((i & 8) != 8)
                flag = true;
            else
                flag = false;
            breakLines = flag;
            if((i & 1) == 1)
                flag1 = true;
            else
                flag1 = false;
            encode = flag1;
            if(encode)
                j = 4;
            else
                j = 3;
            bufferLength = j;
            buffer = new byte[bufferLength];
            position = -1;
            lineLength = 0;
            options = i;
            alphabet = Base64.getAlphabet(i);
            decodabet = Base64.getDecodabet(i);
        }
    }


    private Base64()
    {
    }

    public static byte[] decode(String s)
    {
        return decode(s, 0);
    }

    public static byte[] decode(String s, int i)
    {
        byte abyte4[] = s.getBytes("UTF-8");
        byte abyte0[] = abyte4;
_L4:
        byte abyte1[] = decode(abyte0, 0, abyte0.length, i);
        if(abyte1 == null || abyte1.length < 4 || 35615 != (0xff & abyte1[0] | 0xff00 & abyte1[1] << 8)) goto _L2; else goto _L1
_L1:
        byte abyte2[] = new byte[2048];
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte1);
        GZIPInputStream gzipinputstream = new GZIPInputStream(bytearrayinputstream);
        do
        {
            int j = gzipinputstream.read(abyte2);
            if(j < 0)
                break;
            bytearrayoutputstream.write(abyte2, 0, j);
        } while(true);
          goto _L3
        IOException ioexception;
        ioexception;
        ByteArrayOutputStream bytearrayoutputstream2;
        GZIPInputStream gzipinputstream2;
        bytearrayoutputstream2 = bytearrayoutputstream;
        gzipinputstream2 = gzipinputstream;
_L7:
        Exception exception;
        ByteArrayOutputStream bytearrayoutputstream1;
        GZIPInputStream gzipinputstream1;
        UnsupportedEncodingException unsupportedencodingexception;
        byte abyte3[];
        Exception exception7;
        Exception exception8;
        Exception exception9;
        try
        {
            bytearrayoutputstream2.close();
        }
        catch(Exception exception4) { }
        try
        {
            gzipinputstream2.close();
        }
        catch(Exception exception5) { }
        try
        {
            bytearrayinputstream.close();
        }
        catch(Exception exception6) { }
_L2:
        return abyte1;
        unsupportedencodingexception;
        abyte0 = s.getBytes();
          goto _L4
_L3:
        abyte3 = bytearrayoutputstream.toByteArray();
        abyte1 = abyte3;
        try
        {
            bytearrayoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception7) { }
        try
        {
            gzipinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception8) { }
        try
        {
            bytearrayinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception9) { }
          goto _L2
        exception;
        bytearrayoutputstream1 = null;
        gzipinputstream1 = null;
        bytearrayinputstream = null;
_L6:
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception1) { }
        try
        {
            gzipinputstream1.close();
        }
        catch(Exception exception2) { }
        try
        {
            bytearrayinputstream.close();
        }
        catch(Exception exception3) { }
        throw exception;
        exception;
        bytearrayoutputstream1 = bytearrayoutputstream;
        bytearrayinputstream = null;
        gzipinputstream1 = null;
        continue; /* Loop/switch isn't completed */
        exception;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipinputstream1 = null;
        continue; /* Loop/switch isn't completed */
        exception;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipinputstream1 = gzipinputstream;
        if(true) goto _L6; else goto _L5
_L5:
        IOException ioexception3;
        ioexception3;
        bytearrayoutputstream2 = null;
        gzipinputstream2 = null;
        bytearrayinputstream = null;
          goto _L7
        IOException ioexception2;
        ioexception2;
        bytearrayoutputstream2 = bytearrayoutputstream;
        bytearrayinputstream = null;
        gzipinputstream2 = null;
          goto _L7
        IOException ioexception1;
        ioexception1;
        bytearrayoutputstream2 = bytearrayoutputstream;
        gzipinputstream2 = null;
          goto _L7
    }

    public static byte[] decode(byte abyte0[], int i, int j, int k)
    {
        byte abyte1[];
        byte abyte2[];
        byte abyte3[];
        int l;
        int i1;
        int j1;
        abyte1 = getDecodabet(k);
        abyte2 = new byte[(j * 3) / 4];
        abyte3 = new byte[4];
        l = i;
        i1 = 0;
        j1 = 0;
_L10:
        byte byte0;
        byte byte1;
        if(l >= i + j)
            break MISSING_BLOCK_LABEL_210;
        byte0 = (byte)(0x7f & abyte0[l]);
        byte1 = abyte1[byte0];
        if(byte1 < -5) goto _L2; else goto _L1
_L1:
        if(byte1 < -1) goto _L4; else goto _L3
_L3:
        int l1;
        l1 = i1 + 1;
        abyte3[i1] = byte0;
        if(l1 <= 3) goto _L6; else goto _L5
_L5:
        int i2 = j1 + decode4to3(abyte3, 0, abyte2, j1, k);
        if(byte0 != 61) goto _L8; else goto _L7
_L7:
        int k1 = i2;
_L11:
        byte abyte5[];
        byte abyte4[] = new byte[k1];
        System.arraycopy(abyte2, 0, abyte4, 0, k1);
        abyte5 = abyte4;
_L9:
        return abyte5;
_L2:
        System.err.println((new StringBuilder()).append("Bad Base64 input character at ").append(l).append(": ").append(abyte0[l]).append("(decimal)").toString());
        abyte5 = null;
        if(true) goto _L9; else goto _L8
_L8:
        j1 = i2;
        i1 = 0;
_L4:
        l++;
          goto _L10
_L6:
        i1 = l1;
          goto _L4
        k1 = j1;
          goto _L11
    }

    private static int decode4to3(byte abyte0[], int i, byte abyte1[], int j, int k)
    {
        byte abyte2[] = getDecodabet(k);
        if(abyte0[i + 2] != 61) goto _L2; else goto _L1
_L1:
        int l;
        abyte1[j] = (byte)(((0xff & abyte2[abyte0[i]]) << 18 | (0xff & abyte2[abyte0[i + 1]]) << 12) >>> 16);
        l = 1;
_L4:
        return l;
_L2:
        if(abyte0[i + 3] == 61)
        {
            int j1 = (0xff & abyte2[abyte0[i]]) << 18 | (0xff & abyte2[abyte0[i + 1]]) << 12 | (0xff & abyte2[abyte0[i + 2]]) << 6;
            abyte1[j] = (byte)(j1 >>> 16);
            abyte1[j + 1] = (byte)(j1 >>> 8);
            l = 2;
            continue; /* Loop/switch isn't completed */
        }
        int i1 = (0xff & abyte2[abyte0[i]]) << 18 | (0xff & abyte2[abyte0[i + 1]]) << 12 | (0xff & abyte2[abyte0[i + 2]]) << 6 | 0xff & abyte2[abyte0[i + 3]];
        abyte1[j] = (byte)(i1 >> 16);
        abyte1[j + 1] = (byte)(i1 >> 8);
        abyte1[j + 2] = (byte)i1;
        l = 3;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        System.out.println((new StringBuilder()).append("").append(abyte0[i]).append(": ").append(abyte2[abyte0[i]]).toString());
        System.out.println((new StringBuilder()).append("").append(abyte0[i + 1]).append(": ").append(abyte2[abyte0[i + 1]]).toString());
        System.out.println((new StringBuilder()).append("").append(abyte0[i + 2]).append(": ").append(abyte2[abyte0[i + 2]]).toString());
        System.out.println((new StringBuilder()).append("").append(abyte0[i + 3]).append(": ").append(abyte2[abyte0[i + 3]]).toString());
        l = -1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void decodeFileToFile(String s, String s1)
    {
        byte abyte0[];
        BufferedOutputStream bufferedoutputstream;
        abyte0 = decodeFromFile(s);
        bufferedoutputstream = null;
        BufferedOutputStream bufferedoutputstream1 = new BufferedOutputStream(new FileOutputStream(s1));
        bufferedoutputstream1.write(abyte0);
        bufferedoutputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        ioexception.printStackTrace();
        try
        {
            bufferedoutputstream.close();
        }
        catch(Exception exception2) { }
          goto _L1
        Exception exception;
        exception;
_L2:
        Exception exception3;
        try
        {
            bufferedoutputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        exception3;
          goto _L1
        exception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L2
        ioexception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L3
    }

    public static byte[] decodeFromFile(String s)
    {
        File file;
        file = new File(s);
        if(file.length() <= 0x7fffffffL)
            break MISSING_BLOCK_LABEL_65;
        System.err.println((new StringBuilder()).append("File is too big for this convenience method (").append(file.length()).append(" bytes).").toString());
        Exception exception;
        InputStream inputstream;
        InputStream inputstream1;
        byte abyte0[];
        InputStream inputstream2;
        byte abyte3[];
        IOException ioexception;
        Exception exception3;
        byte abyte1[];
        byte abyte2[];
        int i;
        int j;
        Exception exception4;
        try
        {
            null.close();
        }
        catch(Exception exception5) { }
        abyte1 = null;
        return abyte1;
        abyte2 = new byte[(int)file.length()];
        inputstream2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
        i = 0;
        Exception exception2;
        try
        {
            do
            {
                j = inputstream2.read(abyte2, i, 4096);
                if(j < 0)
                    break;
                i += j;
            } while(true);
            abyte3 = new byte[i];
        }
        catch(IOException ioexception1)
        {
            inputstream1 = inputstream2;
            abyte0 = null;
            continue; /* Loop/switch isn't completed */
        }
_L1:
        System.arraycopy(abyte2, 0, abyte3, 0, i);
        try
        {
            inputstream2.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception4)
        {
            abyte1 = abyte3;
            break MISSING_BLOCK_LABEL_62;
        }
        abyte1 = abyte3;
        break MISSING_BLOCK_LABEL_62;
        ioexception;
        inputstream1 = null;
        abyte0 = null;
_L4:
        System.err.println((new StringBuilder()).append("Error decoding from file ").append(s).toString());
        try
        {
            inputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3)
        {
            abyte1 = abyte0;
            break MISSING_BLOCK_LABEL_62;
        }
        abyte1 = abyte0;
        break MISSING_BLOCK_LABEL_62;
        exception;
        inputstream = null;
_L2:
        try
        {
            inputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        exception;
        inputstream = inputstream2;
        continue; /* Loop/switch isn't completed */
        exception2;
        inputstream = inputstream1;
        exception = exception2;
        if(true) goto _L2; else goto _L1
        IOException ioexception2;
        ioexception2;
        inputstream1 = inputstream2;
        abyte0 = abyte3;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static boolean decodeToFile(String s, String s1)
    {
        OutputStream outputstream = null;
        OutputStream outputstream1 = new OutputStream(new FileOutputStream(s1), 0);
        outputstream1.write(s.getBytes("UTF-8"));
        Exception exception1;
        boolean flag = true;
        Exception exception;
        Exception exception4;
        IOException ioexception1;
        try
        {
            outputstream1.close();
        }
        catch(Exception exception3) { }
        return flag;
        ioexception1;
_L4:
        try
        {
            outputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception)
        {
            flag = false;
            break MISSING_BLOCK_LABEL_36;
        }
        flag = false;
        break MISSING_BLOCK_LABEL_36;
        exception4;
        outputstream1 = outputstream;
        exception1 = exception4;
_L2:
        try
        {
            outputstream1.close();
        }
        catch(Exception exception2) { }
        throw exception1;
        exception1;
        if(true) goto _L2; else goto _L1
_L1:
        IOException ioexception;
        ioexception;
        outputstream = outputstream1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static Object decodeToObject(String s)
    {
        byte abyte0[] = decode(s);
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
        ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
        Object obj1 = objectinputstream.readObject();
        ByteArrayInputStream bytearrayinputstream1;
        ObjectInputStream objectinputstream1;
        IOException ioexception1;
        Exception exception;
        Exception exception4;
        Object obj;
        ClassNotFoundException classnotfoundexception1;
        Exception exception6;
        Exception exception9;
        try
        {
            bytearrayinputstream.close();
        }
        catch(Exception exception8) { }
        objectinputstream.close();
        obj = obj1;
_L1:
        return obj;
        exception9;
        obj = obj1;
          goto _L1
        ioexception1;
        objectinputstream1 = null;
        bytearrayinputstream1 = null;
_L5:
        ioexception1.printStackTrace();
        try
        {
            bytearrayinputstream1.close();
        }
        catch(Exception exception3) { }
        objectinputstream1.close();
        obj = null;
          goto _L1
        exception4;
        obj = null;
          goto _L1
        classnotfoundexception1;
        objectinputstream1 = null;
        bytearrayinputstream1 = null;
_L4:
        classnotfoundexception1.printStackTrace();
        try
        {
            bytearrayinputstream1.close();
        }
        catch(Exception exception5) { }
        objectinputstream1.close();
        obj = null;
          goto _L1
        exception6;
        obj = null;
          goto _L1
        exception;
        objectinputstream1 = null;
        bytearrayinputstream1 = null;
_L3:
        try
        {
            bytearrayinputstream1.close();
        }
        catch(Exception exception1) { }
        try
        {
            objectinputstream1.close();
        }
        catch(Exception exception2) { }
        throw exception;
        exception;
        bytearrayinputstream1 = bytearrayinputstream;
        objectinputstream1 = null;
        continue; /* Loop/switch isn't completed */
        Exception exception7;
        exception7;
        bytearrayinputstream1 = bytearrayinputstream;
        objectinputstream1 = objectinputstream;
        exception = exception7;
        continue; /* Loop/switch isn't completed */
        exception;
        if(true) goto _L3; else goto _L2
_L2:
        classnotfoundexception1;
        bytearrayinputstream1 = bytearrayinputstream;
        objectinputstream1 = null;
          goto _L4
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        bytearrayinputstream1 = bytearrayinputstream;
        objectinputstream1 = objectinputstream;
        classnotfoundexception1 = classnotfoundexception;
          goto _L4
        ioexception1;
        bytearrayinputstream1 = bytearrayinputstream;
        objectinputstream1 = null;
          goto _L5
        IOException ioexception;
        ioexception;
        bytearrayinputstream1 = bytearrayinputstream;
        objectinputstream1 = objectinputstream;
        ioexception1 = ioexception;
          goto _L5
    }

    private static byte[] encode3to4(byte abyte0[], int i, int j, byte abyte1[], int k, int l)
    {
        byte abyte2[];
        int i2;
        byte abyte3[];
        abyte2 = getAlphabet(l);
        int i1;
        int j1;
        int k1;
        int l1;
        if(j > 0)
            i1 = (abyte0[i] << 24) >>> 8;
        else
            i1 = 0;
        if(j > 1)
            j1 = (abyte0[i + 1] << 24) >>> 16;
        else
            j1 = 0;
        k1 = i1 | j1;
        if(j > 2)
            l1 = (abyte0[i + 2] << 24) >>> 24;
        else
            l1 = 0;
        i2 = k1 | l1;
        j;
        JVM INSTR tableswitch 1 3: default 100
    //                   1 250
    //                   2 190
    //                   3 124;
           goto _L1 _L2 _L3 _L4
_L1:
        abyte3 = abyte1;
_L6:
        return abyte3;
_L4:
        abyte1[k] = abyte2[i2 >>> 18];
        abyte1[k + 1] = abyte2[0x3f & i2 >>> 12];
        abyte1[k + 2] = abyte2[0x3f & i2 >>> 6];
        abyte1[k + 3] = abyte2[i2 & 0x3f];
        abyte3 = abyte1;
        continue; /* Loop/switch isn't completed */
_L3:
        abyte1[k] = abyte2[i2 >>> 18];
        abyte1[k + 1] = abyte2[0x3f & i2 >>> 12];
        abyte1[k + 2] = abyte2[0x3f & i2 >>> 6];
        abyte1[k + 3] = 61;
        abyte3 = abyte1;
        continue; /* Loop/switch isn't completed */
_L2:
        abyte1[k] = abyte2[i2 >>> 18];
        abyte1[k + 1] = abyte2[0x3f & i2 >>> 12];
        abyte1[k + 2] = 61;
        abyte1[k + 3] = 61;
        abyte3 = abyte1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static byte[] encode3to4(byte abyte0[], byte abyte1[], int i, int j)
    {
        encode3to4(abyte1, 0, i, abyte0, 0, j);
        return abyte0;
    }

    public static String encodeBytes(byte abyte0[])
    {
        return encodeBytes(abyte0, 0, abyte0.length, 0);
    }

    public static String encodeBytes(byte abyte0[], int i)
    {
        return encodeBytes(abyte0, 0, abyte0.length, i);
    }

    public static String encodeBytes(byte abyte0[], int i, int j)
    {
        return encodeBytes(abyte0, i, j, 0);
    }

    public static String encodeBytes(byte abyte0[], int i, int j, int k)
    {
        int l = k & 8;
        if((k & 2) != 2) goto _L2; else goto _L1
_L1:
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        OutputStream outputstream = new OutputStream(bytearrayoutputstream, k | 1);
        GZIPOutputStream gzipoutputstream = new GZIPOutputStream(outputstream);
        gzipoutputstream.write(abyte0, i, j);
        gzipoutputstream.close();
        ByteArrayOutputStream bytearrayoutputstream1;
        IOException ioexception1;
        Exception exception;
        boolean flag;
        int i1;
        byte byte0;
        int j1;
        int k1;
        byte abyte1[];
        int l1;
        int i2;
        int k2;
        String s;
        String s1;
        UnsupportedEncodingException unsupportedencodingexception;
        int i3;
        int k3;
        int l3;
        String s2;
        UnsupportedEncodingException unsupportedencodingexception1;
        try
        {
            gzipoutputstream.close();
        }
        catch(Exception exception8) { }
        try
        {
            outputstream.close();
        }
        catch(Exception exception9) { }
        try
        {
            bytearrayoutputstream.close();
        }
        catch(Exception exception10) { }
        s2 = new String(bytearrayoutputstream.toByteArray(), "UTF-8");
        s1 = s2;
_L3:
        return s1;
        ioexception1;
        outputstream = null;
        gzipoutputstream = null;
        bytearrayoutputstream1 = null;
_L5:
        ioexception1.printStackTrace();
        try
        {
            gzipoutputstream.close();
        }
        catch(Exception exception4) { }
        try
        {
            outputstream.close();
        }
        catch(Exception exception5) { }
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception6) { }
        s1 = null;
          goto _L3
        exception;
        outputstream = null;
        gzipoutputstream = null;
        bytearrayoutputstream1 = null;
_L4:
        try
        {
            gzipoutputstream.close();
        }
        catch(Exception exception1) { }
        try
        {
            outputstream.close();
        }
        catch(Exception exception2) { }
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception3) { }
        throw exception;
        unsupportedencodingexception1;
        s1 = new String(bytearrayoutputstream.toByteArray());
          goto _L3
_L2:
        int j2;
        if(l == 0)
            flag = true;
        else
            flag = false;
        i1 = (j * 4) / 3;
        if(j % 3 > 0)
            byte0 = 4;
        else
            byte0 = 0;
        j1 = byte0 + i1;
        if(flag)
            k1 = i1 / 76;
        else
            k1 = 0;
        abyte1 = new byte[k1 + j1];
        l1 = j - 2;
        i2 = 0;
        j2 = 0;
        k2 = 0;
        while(k2 < l1) 
        {
            encode3to4(abyte0, k2 + i, 3, abyte1, j2, k);
            i3 = i2 + 4;
            int l2;
            int j3;
            IOException ioexception;
            Exception exception7;
            IOException ioexception2;
            Exception exception11;
            IOException ioexception3;
            Exception exception12;
            if(flag && i3 == 76)
            {
                abyte1[j2 + 4] = 10;
                j3 = j2 + 1;
                i3 = 0;
            } else
            {
                j3 = j2;
            }
            k3 = k2 + 3;
            l3 = j3 + 4;
            i2 = i3;
            j2 = l3;
            k2 = k3;
        }
        if(k2 < j)
        {
            encode3to4(abyte0, k2 + i, j - k2, abyte1, j2, k);
            l2 = j2 + 4;
        } else
        {
            l2 = j2;
        }
        s = new String(abyte1, 0, l2, "UTF-8");
        s1 = s;
          goto _L3
        unsupportedencodingexception;
        s1 = new String(abyte1, 0, l2);
          goto _L3
        exception12;
        gzipoutputstream = null;
        bytearrayoutputstream1 = bytearrayoutputstream;
        exception = exception12;
        outputstream = null;
          goto _L4
        exception11;
        bytearrayoutputstream1 = bytearrayoutputstream;
        exception = exception11;
        gzipoutputstream = null;
          goto _L4
        exception7;
        bytearrayoutputstream1 = bytearrayoutputstream;
        exception = exception7;
          goto _L4
        exception;
          goto _L4
        ioexception3;
        gzipoutputstream = null;
        bytearrayoutputstream1 = bytearrayoutputstream;
        ioexception1 = ioexception3;
        outputstream = null;
          goto _L5
        ioexception2;
        bytearrayoutputstream1 = bytearrayoutputstream;
        ioexception1 = ioexception2;
        gzipoutputstream = null;
          goto _L5
        ioexception;
        bytearrayoutputstream1 = bytearrayoutputstream;
        ioexception1 = ioexception;
          goto _L5
    }

    public static void encodeFileToFile(String s, String s1)
    {
        String s2;
        BufferedOutputStream bufferedoutputstream;
        s2 = encodeFromFile(s);
        bufferedoutputstream = null;
        BufferedOutputStream bufferedoutputstream1 = new BufferedOutputStream(new FileOutputStream(s1));
        bufferedoutputstream1.write(s2.getBytes("US-ASCII"));
        bufferedoutputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        ioexception.printStackTrace();
        try
        {
            bufferedoutputstream.close();
        }
        catch(Exception exception2) { }
          goto _L1
        Exception exception;
        exception;
_L2:
        Exception exception3;
        try
        {
            bufferedoutputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        exception3;
          goto _L1
        exception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L2
        ioexception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L3
    }

    public static String encodeFromFile(String s)
    {
        byte abyte0[];
        InputStream inputstream2;
        File file = new File(s);
        abyte0 = new byte[Math.max((int)(1.3999999999999999D * (double)file.length()), 40)];
        inputstream2 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
        int i = 0;
        String s2;
        do
        {
            int j = inputstream2.read(abyte0, i, 4096);
            if(j < 0)
                break;
            i += j;
        } while(true);
        s2 = new String(abyte0, 0, i, "UTF-8");
        inputstream2.close();
        String s1 = s2;
_L1:
        return s1;
        Exception exception4;
        exception4;
        s1 = s2;
          goto _L1
        IOException ioexception;
        ioexception;
        InputStream inputstream1 = null;
_L4:
        System.err.println((new StringBuilder()).append("Error encoding from file ").append(s).toString());
        inputstream1.close();
        s1 = null;
          goto _L1
        Exception exception3;
        exception3;
        s1 = null;
          goto _L1
        Exception exception;
        exception;
        InputStream inputstream = null;
_L3:
        try
        {
            inputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        exception;
        inputstream = inputstream2;
        continue; /* Loop/switch isn't completed */
        Exception exception2;
        exception2;
        inputstream = inputstream1;
        exception = exception2;
        if(true) goto _L3; else goto _L2
_L2:
        IOException ioexception1;
        ioexception1;
        inputstream1 = inputstream2;
          goto _L4
    }

    public static String encodeObject(Serializable serializable)
    {
        return encodeObject(serializable, 0);
    }

    public static String encodeObject(Serializable serializable, int i)
    {
        int j;
        j = i & 2;
        i & 8;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        OutputStream outputstream;
        ObjectOutputStream objectoutputstream1;
        GZIPOutputStream gzipoutputstream;
        ByteArrayOutputStream bytearrayoutputstream1;
        GZIPOutputStream gzipoutputstream1;
        IOException ioexception1;
        ObjectOutputStream objectoutputstream2;
        OutputStream outputstream1;
        ObjectOutputStream objectoutputstream;
        Exception exception;
        Exception exception1;
        Exception exception2;
        Exception exception3;
        Exception exception4;
        Exception exception5;
        Exception exception6;
        Exception exception7;
        Exception exception8;
        String s;
        Exception exception9;
        Exception exception10;
        Exception exception11;
        Exception exception12;
        Exception exception13;
        UnsupportedEncodingException unsupportedencodingexception;
        Exception exception14;
        try
        {
            outputstream = new OutputStream(bytearrayoutputstream, i | 1);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception1)
        {
            objectoutputstream2 = null;
            outputstream1 = null;
            bytearrayoutputstream1 = bytearrayoutputstream;
            gzipoutputstream1 = null;
            continue; /* Loop/switch isn't completed */
        }
        if(j != 2)
            break MISSING_BLOCK_LABEL_104;
        try
        {
            gzipoutputstream = new GZIPOutputStream(outputstream);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception1)
        {
            outputstream1 = outputstream;
            bytearrayoutputstream1 = bytearrayoutputstream;
            gzipoutputstream1 = null;
            objectoutputstream2 = null;
            continue; /* Loop/switch isn't completed */
        }
        try
        {
            objectoutputstream1 = new ObjectOutputStream(gzipoutputstream);
        }
        catch(IOException ioexception2)
        {
            bytearrayoutputstream1 = bytearrayoutputstream;
            gzipoutputstream1 = gzipoutputstream;
            ioexception1 = ioexception2;
            outputstream1 = outputstream;
            objectoutputstream2 = null;
            continue; /* Loop/switch isn't completed */
        }
        objectoutputstream1.writeObject(serializable);
        try
        {
            objectoutputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception10) { }
        try
        {
            gzipoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception11) { }
        try
        {
            outputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception12) { }
        try
        {
            bytearrayoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception13) { }
        try
        {
            s = new String(bytearrayoutputstream.toByteArray(), "UTF-8");
        }
        // Misplaced declaration of an exception variable
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            s = new String(bytearrayoutputstream.toByteArray());
        }
        return s;
        objectoutputstream = new ObjectOutputStream(outputstream);
        objectoutputstream1 = objectoutputstream;
        gzipoutputstream = null;
        if(true)
            break MISSING_BLOCK_LABEL_59;
        ioexception1;
        gzipoutputstream1 = null;
        objectoutputstream2 = null;
        outputstream1 = null;
        bytearrayoutputstream1 = null;
_L4:
        ioexception1.printStackTrace();
        try
        {
            objectoutputstream2.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception5) { }
        try
        {
            gzipoutputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception6) { }
        try
        {
            outputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception7) { }
        try
        {
            bytearrayoutputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception8) { }
        s = null;
        break MISSING_BLOCK_LABEL_101;
        exception;
        gzipoutputstream1 = null;
        objectoutputstream2 = null;
        outputstream1 = null;
        bytearrayoutputstream1 = null;
_L2:
        try
        {
            objectoutputstream2.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        try
        {
            gzipoutputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        try
        {
            outputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3) { }
        try
        {
            bytearrayoutputstream1.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception4) { }
        throw exception;
        exception;
        objectoutputstream2 = null;
        outputstream1 = null;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipoutputstream1 = null;
        continue; /* Loop/switch isn't completed */
        exception;
        outputstream1 = outputstream;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipoutputstream1 = null;
        objectoutputstream2 = null;
        continue; /* Loop/switch isn't completed */
        exception14;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipoutputstream1 = gzipoutputstream;
        exception = exception14;
        outputstream1 = outputstream;
        objectoutputstream2 = null;
        continue; /* Loop/switch isn't completed */
        exception9;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipoutputstream1 = gzipoutputstream;
        exception = exception9;
        objectoutputstream2 = objectoutputstream1;
        outputstream1 = outputstream;
        continue; /* Loop/switch isn't completed */
        exception;
        if(true) goto _L2; else goto _L1
_L1:
        IOException ioexception;
        ioexception;
        bytearrayoutputstream1 = bytearrayoutputstream;
        gzipoutputstream1 = gzipoutputstream;
        ioexception1 = ioexception;
        objectoutputstream2 = objectoutputstream1;
        outputstream1 = outputstream;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static boolean encodeToFile(byte abyte0[], String s)
    {
        OutputStream outputstream = null;
        OutputStream outputstream1 = new OutputStream(new FileOutputStream(s), 1);
        outputstream1.write(abyte0);
        outputstream1.close();
        boolean flag = true;
_L1:
        return flag;
        Exception exception3;
        exception3;
        flag = true;
          goto _L1
        IOException ioexception1;
        ioexception1;
_L4:
        outputstream.close();
        flag = false;
          goto _L1
        Exception exception;
        exception;
        flag = false;
          goto _L1
        Exception exception4;
        exception4;
        Exception exception1;
        outputstream1 = outputstream;
        exception1 = exception4;
_L3:
        try
        {
            outputstream1.close();
        }
        catch(Exception exception2) { }
        throw exception1;
        exception1;
        if(true) goto _L3; else goto _L2
_L2:
        IOException ioexception;
        ioexception;
        outputstream = outputstream1;
          goto _L4
    }

    private static final byte[] getAlphabet(int i)
    {
        byte abyte0[];
        if((i & 0x10) == 16)
            abyte0 = _URL_SAFE_ALPHABET;
        else
        if((i & 0x20) == 32)
            abyte0 = _ORDERED_ALPHABET;
        else
            abyte0 = _STANDARD_ALPHABET;
        return abyte0;
    }

    private static final byte[] getDecodabet(int i)
    {
        byte abyte0[];
        if((i & 0x10) == 16)
            abyte0 = _URL_SAFE_DECODABET;
        else
        if((i & 0x20) == 32)
            abyte0 = _ORDERED_DECODABET;
        else
            abyte0 = _STANDARD_DECODABET;
        return abyte0;
    }

    public static final void main(String args[])
    {
        if(args.length < 3)
        {
            usage("Not enough arguments.");
        } else
        {
            String s = args[0];
            String s1 = args[1];
            String s2 = args[2];
            if(s.equals("-e"))
                encodeFileToFile(s1, s2);
            else
            if(s.equals("-d"))
                decodeFileToFile(s1, s2);
            else
                usage((new StringBuilder()).append("Unknown flag: ").append(s).toString());
        }
    }

    private static final void usage(String s)
    {
        System.err.println(s);
        System.err.println("Usage: java Base64 -e|-d inputfile outputfile");
    }

    public static final int DECODE = 0;
    public static final int DONT_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "UTF-8";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte _ORDERED_ALPHABET[];
    private static final byte _ORDERED_DECODABET[];
    private static final byte _STANDARD_ALPHABET[];
    private static final byte _STANDARD_DECODABET[];
    private static final byte _URL_SAFE_ALPHABET[];
    private static final byte _URL_SAFE_DECODABET[];

    static 
    {
        byte abyte0[] = new byte[64];
        abyte0[0] = 65;
        abyte0[1] = 66;
        abyte0[2] = 67;
        abyte0[3] = 68;
        abyte0[4] = 69;
        abyte0[5] = 70;
        abyte0[6] = 71;
        abyte0[7] = 72;
        abyte0[8] = 73;
        abyte0[9] = 74;
        abyte0[10] = 75;
        abyte0[11] = 76;
        abyte0[12] = 77;
        abyte0[13] = 78;
        abyte0[14] = 79;
        abyte0[15] = 80;
        abyte0[16] = 81;
        abyte0[17] = 82;
        abyte0[18] = 83;
        abyte0[19] = 84;
        abyte0[20] = 85;
        abyte0[21] = 86;
        abyte0[22] = 87;
        abyte0[23] = 88;
        abyte0[24] = 89;
        abyte0[25] = 90;
        abyte0[26] = 97;
        abyte0[27] = 98;
        abyte0[28] = 99;
        abyte0[29] = 100;
        abyte0[30] = 101;
        abyte0[31] = 102;
        abyte0[32] = 103;
        abyte0[33] = 104;
        abyte0[34] = 105;
        abyte0[35] = 106;
        abyte0[36] = 107;
        abyte0[37] = 108;
        abyte0[38] = 109;
        abyte0[39] = 110;
        abyte0[40] = 111;
        abyte0[41] = 112;
        abyte0[42] = 113;
        abyte0[43] = 114;
        abyte0[44] = 115;
        abyte0[45] = 116;
        abyte0[46] = 117;
        abyte0[47] = 118;
        abyte0[48] = 119;
        abyte0[49] = 120;
        abyte0[50] = 121;
        abyte0[51] = 122;
        abyte0[52] = 48;
        abyte0[53] = 49;
        abyte0[54] = 50;
        abyte0[55] = 51;
        abyte0[56] = 52;
        abyte0[57] = 53;
        abyte0[58] = 54;
        abyte0[59] = 55;
        abyte0[60] = 56;
        abyte0[61] = 57;
        abyte0[62] = 43;
        abyte0[63] = 47;
        _STANDARD_ALPHABET = abyte0;
        byte abyte1[] = new byte[127];
        abyte1[0] = -9;
        abyte1[1] = -9;
        abyte1[2] = -9;
        abyte1[3] = -9;
        abyte1[4] = -9;
        abyte1[5] = -9;
        abyte1[6] = -9;
        abyte1[7] = -9;
        abyte1[8] = -9;
        abyte1[9] = -5;
        abyte1[10] = -5;
        abyte1[11] = -9;
        abyte1[12] = -9;
        abyte1[13] = -5;
        abyte1[14] = -9;
        abyte1[15] = -9;
        abyte1[16] = -9;
        abyte1[17] = -9;
        abyte1[18] = -9;
        abyte1[19] = -9;
        abyte1[20] = -9;
        abyte1[21] = -9;
        abyte1[22] = -9;
        abyte1[23] = -9;
        abyte1[24] = -9;
        abyte1[25] = -9;
        abyte1[26] = -9;
        abyte1[27] = -9;
        abyte1[28] = -9;
        abyte1[29] = -9;
        abyte1[30] = -9;
        abyte1[31] = -9;
        abyte1[32] = -5;
        abyte1[33] = -9;
        abyte1[34] = -9;
        abyte1[35] = -9;
        abyte1[36] = -9;
        abyte1[37] = -9;
        abyte1[38] = -9;
        abyte1[39] = -9;
        abyte1[40] = -9;
        abyte1[41] = -9;
        abyte1[42] = -9;
        abyte1[43] = 62;
        abyte1[44] = -9;
        abyte1[45] = -9;
        abyte1[46] = -9;
        abyte1[47] = 63;
        abyte1[48] = 52;
        abyte1[49] = 53;
        abyte1[50] = 54;
        abyte1[51] = 55;
        abyte1[52] = 56;
        abyte1[53] = 57;
        abyte1[54] = 58;
        abyte1[55] = 59;
        abyte1[56] = 60;
        abyte1[57] = 61;
        abyte1[58] = -9;
        abyte1[59] = -9;
        abyte1[60] = -9;
        abyte1[61] = -1;
        abyte1[62] = -9;
        abyte1[63] = -9;
        abyte1[64] = -9;
        abyte1[65] = 0;
        abyte1[66] = 1;
        abyte1[67] = 2;
        abyte1[68] = 3;
        abyte1[69] = 4;
        abyte1[70] = 5;
        abyte1[71] = 6;
        abyte1[72] = 7;
        abyte1[73] = 8;
        abyte1[74] = 9;
        abyte1[75] = 10;
        abyte1[76] = 11;
        abyte1[77] = 12;
        abyte1[78] = 13;
        abyte1[79] = 14;
        abyte1[80] = 15;
        abyte1[81] = 16;
        abyte1[82] = 17;
        abyte1[83] = 18;
        abyte1[84] = 19;
        abyte1[85] = 20;
        abyte1[86] = 21;
        abyte1[87] = 22;
        abyte1[88] = 23;
        abyte1[89] = 24;
        abyte1[90] = 25;
        abyte1[91] = -9;
        abyte1[92] = -9;
        abyte1[93] = -9;
        abyte1[94] = -9;
        abyte1[95] = -9;
        abyte1[96] = -9;
        abyte1[97] = 26;
        abyte1[98] = 27;
        abyte1[99] = 28;
        abyte1[100] = 29;
        abyte1[101] = 30;
        abyte1[102] = 31;
        abyte1[103] = 32;
        abyte1[104] = 33;
        abyte1[105] = 34;
        abyte1[106] = 35;
        abyte1[107] = 36;
        abyte1[108] = 37;
        abyte1[109] = 38;
        abyte1[110] = 39;
        abyte1[111] = 40;
        abyte1[112] = 41;
        abyte1[113] = 42;
        abyte1[114] = 43;
        abyte1[115] = 44;
        abyte1[116] = 45;
        abyte1[117] = 46;
        abyte1[118] = 47;
        abyte1[119] = 48;
        abyte1[120] = 49;
        abyte1[121] = 50;
        abyte1[122] = 51;
        abyte1[123] = -9;
        abyte1[124] = -9;
        abyte1[125] = -9;
        abyte1[126] = -9;
        _STANDARD_DECODABET = abyte1;
        byte abyte2[] = new byte[64];
        abyte2[0] = 65;
        abyte2[1] = 66;
        abyte2[2] = 67;
        abyte2[3] = 68;
        abyte2[4] = 69;
        abyte2[5] = 70;
        abyte2[6] = 71;
        abyte2[7] = 72;
        abyte2[8] = 73;
        abyte2[9] = 74;
        abyte2[10] = 75;
        abyte2[11] = 76;
        abyte2[12] = 77;
        abyte2[13] = 78;
        abyte2[14] = 79;
        abyte2[15] = 80;
        abyte2[16] = 81;
        abyte2[17] = 82;
        abyte2[18] = 83;
        abyte2[19] = 84;
        abyte2[20] = 85;
        abyte2[21] = 86;
        abyte2[22] = 87;
        abyte2[23] = 88;
        abyte2[24] = 89;
        abyte2[25] = 90;
        abyte2[26] = 97;
        abyte2[27] = 98;
        abyte2[28] = 99;
        abyte2[29] = 100;
        abyte2[30] = 101;
        abyte2[31] = 102;
        abyte2[32] = 103;
        abyte2[33] = 104;
        abyte2[34] = 105;
        abyte2[35] = 106;
        abyte2[36] = 107;
        abyte2[37] = 108;
        abyte2[38] = 109;
        abyte2[39] = 110;
        abyte2[40] = 111;
        abyte2[41] = 112;
        abyte2[42] = 113;
        abyte2[43] = 114;
        abyte2[44] = 115;
        abyte2[45] = 116;
        abyte2[46] = 117;
        abyte2[47] = 118;
        abyte2[48] = 119;
        abyte2[49] = 120;
        abyte2[50] = 121;
        abyte2[51] = 122;
        abyte2[52] = 48;
        abyte2[53] = 49;
        abyte2[54] = 50;
        abyte2[55] = 51;
        abyte2[56] = 52;
        abyte2[57] = 53;
        abyte2[58] = 54;
        abyte2[59] = 55;
        abyte2[60] = 56;
        abyte2[61] = 57;
        abyte2[62] = 45;
        abyte2[63] = 95;
        _URL_SAFE_ALPHABET = abyte2;
        byte abyte3[] = new byte[127];
        abyte3[0] = -9;
        abyte3[1] = -9;
        abyte3[2] = -9;
        abyte3[3] = -9;
        abyte3[4] = -9;
        abyte3[5] = -9;
        abyte3[6] = -9;
        abyte3[7] = -9;
        abyte3[8] = -9;
        abyte3[9] = -5;
        abyte3[10] = -5;
        abyte3[11] = -9;
        abyte3[12] = -9;
        abyte3[13] = -5;
        abyte3[14] = -9;
        abyte3[15] = -9;
        abyte3[16] = -9;
        abyte3[17] = -9;
        abyte3[18] = -9;
        abyte3[19] = -9;
        abyte3[20] = -9;
        abyte3[21] = -9;
        abyte3[22] = -9;
        abyte3[23] = -9;
        abyte3[24] = -9;
        abyte3[25] = -9;
        abyte3[26] = -9;
        abyte3[27] = -9;
        abyte3[28] = -9;
        abyte3[29] = -9;
        abyte3[30] = -9;
        abyte3[31] = -9;
        abyte3[32] = -5;
        abyte3[33] = -9;
        abyte3[34] = -9;
        abyte3[35] = -9;
        abyte3[36] = -9;
        abyte3[37] = -9;
        abyte3[38] = -9;
        abyte3[39] = -9;
        abyte3[40] = -9;
        abyte3[41] = -9;
        abyte3[42] = -9;
        abyte3[43] = -9;
        abyte3[44] = -9;
        abyte3[45] = 62;
        abyte3[46] = -9;
        abyte3[47] = -9;
        abyte3[48] = 52;
        abyte3[49] = 53;
        abyte3[50] = 54;
        abyte3[51] = 55;
        abyte3[52] = 56;
        abyte3[53] = 57;
        abyte3[54] = 58;
        abyte3[55] = 59;
        abyte3[56] = 60;
        abyte3[57] = 61;
        abyte3[58] = -9;
        abyte3[59] = -9;
        abyte3[60] = -9;
        abyte3[61] = -1;
        abyte3[62] = -9;
        abyte3[63] = -9;
        abyte3[64] = -9;
        abyte3[65] = 0;
        abyte3[66] = 1;
        abyte3[67] = 2;
        abyte3[68] = 3;
        abyte3[69] = 4;
        abyte3[70] = 5;
        abyte3[71] = 6;
        abyte3[72] = 7;
        abyte3[73] = 8;
        abyte3[74] = 9;
        abyte3[75] = 10;
        abyte3[76] = 11;
        abyte3[77] = 12;
        abyte3[78] = 13;
        abyte3[79] = 14;
        abyte3[80] = 15;
        abyte3[81] = 16;
        abyte3[82] = 17;
        abyte3[83] = 18;
        abyte3[84] = 19;
        abyte3[85] = 20;
        abyte3[86] = 21;
        abyte3[87] = 22;
        abyte3[88] = 23;
        abyte3[89] = 24;
        abyte3[90] = 25;
        abyte3[91] = -9;
        abyte3[92] = -9;
        abyte3[93] = -9;
        abyte3[94] = -9;
        abyte3[95] = 63;
        abyte3[96] = -9;
        abyte3[97] = 26;
        abyte3[98] = 27;
        abyte3[99] = 28;
        abyte3[100] = 29;
        abyte3[101] = 30;
        abyte3[102] = 31;
        abyte3[103] = 32;
        abyte3[104] = 33;
        abyte3[105] = 34;
        abyte3[106] = 35;
        abyte3[107] = 36;
        abyte3[108] = 37;
        abyte3[109] = 38;
        abyte3[110] = 39;
        abyte3[111] = 40;
        abyte3[112] = 41;
        abyte3[113] = 42;
        abyte3[114] = 43;
        abyte3[115] = 44;
        abyte3[116] = 45;
        abyte3[117] = 46;
        abyte3[118] = 47;
        abyte3[119] = 48;
        abyte3[120] = 49;
        abyte3[121] = 50;
        abyte3[122] = 51;
        abyte3[123] = -9;
        abyte3[124] = -9;
        abyte3[125] = -9;
        abyte3[126] = -9;
        _URL_SAFE_DECODABET = abyte3;
        byte abyte4[] = new byte[64];
        abyte4[0] = 45;
        abyte4[1] = 48;
        abyte4[2] = 49;
        abyte4[3] = 50;
        abyte4[4] = 51;
        abyte4[5] = 52;
        abyte4[6] = 53;
        abyte4[7] = 54;
        abyte4[8] = 55;
        abyte4[9] = 56;
        abyte4[10] = 57;
        abyte4[11] = 65;
        abyte4[12] = 66;
        abyte4[13] = 67;
        abyte4[14] = 68;
        abyte4[15] = 69;
        abyte4[16] = 70;
        abyte4[17] = 71;
        abyte4[18] = 72;
        abyte4[19] = 73;
        abyte4[20] = 74;
        abyte4[21] = 75;
        abyte4[22] = 76;
        abyte4[23] = 77;
        abyte4[24] = 78;
        abyte4[25] = 79;
        abyte4[26] = 80;
        abyte4[27] = 81;
        abyte4[28] = 82;
        abyte4[29] = 83;
        abyte4[30] = 84;
        abyte4[31] = 85;
        abyte4[32] = 86;
        abyte4[33] = 87;
        abyte4[34] = 88;
        abyte4[35] = 89;
        abyte4[36] = 90;
        abyte4[37] = 95;
        abyte4[38] = 97;
        abyte4[39] = 98;
        abyte4[40] = 99;
        abyte4[41] = 100;
        abyte4[42] = 101;
        abyte4[43] = 102;
        abyte4[44] = 103;
        abyte4[45] = 104;
        abyte4[46] = 105;
        abyte4[47] = 106;
        abyte4[48] = 107;
        abyte4[49] = 108;
        abyte4[50] = 109;
        abyte4[51] = 110;
        abyte4[52] = 111;
        abyte4[53] = 112;
        abyte4[54] = 113;
        abyte4[55] = 114;
        abyte4[56] = 115;
        abyte4[57] = 116;
        abyte4[58] = 117;
        abyte4[59] = 118;
        abyte4[60] = 119;
        abyte4[61] = 120;
        abyte4[62] = 121;
        abyte4[63] = 122;
        _ORDERED_ALPHABET = abyte4;
        byte abyte5[] = new byte[127];
        abyte5[0] = -9;
        abyte5[1] = -9;
        abyte5[2] = -9;
        abyte5[3] = -9;
        abyte5[4] = -9;
        abyte5[5] = -9;
        abyte5[6] = -9;
        abyte5[7] = -9;
        abyte5[8] = -9;
        abyte5[9] = -5;
        abyte5[10] = -5;
        abyte5[11] = -9;
        abyte5[12] = -9;
        abyte5[13] = -5;
        abyte5[14] = -9;
        abyte5[15] = -9;
        abyte5[16] = -9;
        abyte5[17] = -9;
        abyte5[18] = -9;
        abyte5[19] = -9;
        abyte5[20] = -9;
        abyte5[21] = -9;
        abyte5[22] = -9;
        abyte5[23] = -9;
        abyte5[24] = -9;
        abyte5[25] = -9;
        abyte5[26] = -9;
        abyte5[27] = -9;
        abyte5[28] = -9;
        abyte5[29] = -9;
        abyte5[30] = -9;
        abyte5[31] = -9;
        abyte5[32] = -5;
        abyte5[33] = -9;
        abyte5[34] = -9;
        abyte5[35] = -9;
        abyte5[36] = -9;
        abyte5[37] = -9;
        abyte5[38] = -9;
        abyte5[39] = -9;
        abyte5[40] = -9;
        abyte5[41] = -9;
        abyte5[42] = -9;
        abyte5[43] = -9;
        abyte5[44] = -9;
        abyte5[45] = 0;
        abyte5[46] = -9;
        abyte5[47] = -9;
        abyte5[48] = 1;
        abyte5[49] = 2;
        abyte5[50] = 3;
        abyte5[51] = 4;
        abyte5[52] = 5;
        abyte5[53] = 6;
        abyte5[54] = 7;
        abyte5[55] = 8;
        abyte5[56] = 9;
        abyte5[57] = 10;
        abyte5[58] = -9;
        abyte5[59] = -9;
        abyte5[60] = -9;
        abyte5[61] = -1;
        abyte5[62] = -9;
        abyte5[63] = -9;
        abyte5[64] = -9;
        abyte5[65] = 11;
        abyte5[66] = 12;
        abyte5[67] = 13;
        abyte5[68] = 14;
        abyte5[69] = 15;
        abyte5[70] = 16;
        abyte5[71] = 17;
        abyte5[72] = 18;
        abyte5[73] = 19;
        abyte5[74] = 20;
        abyte5[75] = 21;
        abyte5[76] = 22;
        abyte5[77] = 23;
        abyte5[78] = 24;
        abyte5[79] = 25;
        abyte5[80] = 26;
        abyte5[81] = 27;
        abyte5[82] = 28;
        abyte5[83] = 29;
        abyte5[84] = 30;
        abyte5[85] = 31;
        abyte5[86] = 32;
        abyte5[87] = 33;
        abyte5[88] = 34;
        abyte5[89] = 35;
        abyte5[90] = 36;
        abyte5[91] = -9;
        abyte5[92] = -9;
        abyte5[93] = -9;
        abyte5[94] = -9;
        abyte5[95] = 37;
        abyte5[96] = -9;
        abyte5[97] = 38;
        abyte5[98] = 39;
        abyte5[99] = 40;
        abyte5[100] = 41;
        abyte5[101] = 42;
        abyte5[102] = 43;
        abyte5[103] = 44;
        abyte5[104] = 45;
        abyte5[105] = 46;
        abyte5[106] = 47;
        abyte5[107] = 48;
        abyte5[108] = 49;
        abyte5[109] = 50;
        abyte5[110] = 51;
        abyte5[111] = 52;
        abyte5[112] = 53;
        abyte5[113] = 54;
        abyte5[114] = 55;
        abyte5[115] = 56;
        abyte5[116] = 57;
        abyte5[117] = 58;
        abyte5[118] = 59;
        abyte5[119] = 60;
        abyte5[120] = 61;
        abyte5[121] = 62;
        abyte5[122] = 63;
        abyte5[123] = -9;
        abyte5[124] = -9;
        abyte5[125] = -9;
        abyte5[126] = -9;
        _ORDERED_DECODABET = abyte5;
    }





}
