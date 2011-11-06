// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringUtils.java

package org.jivesoftware.smack.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

// Referenced classes of package org.jivesoftware.smack.util:
//            Base64

public class StringUtils
{

    private StringUtils()
    {
    }

    public static byte[] decodeBase64(String s)
    {
        return Base64.decode(s);
    }

    public static String encodeBase64(String s)
    {
        byte abyte0[] = null;
        byte abyte1[] = s.getBytes("ISO-8859-1");
        abyte0 = abyte1;
_L2:
        return encodeBase64(abyte0);
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        unsupportedencodingexception.printStackTrace();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static String encodeBase64(byte abyte0[])
    {
        return encodeBase64(abyte0, false);
    }

    public static String encodeBase64(byte abyte0[], int i, int j, boolean flag)
    {
        int k;
        if(flag)
            k = 0;
        else
            k = 8;
        return Base64.encodeBytes(abyte0, i, j, k);
    }

    public static String encodeBase64(byte abyte0[], boolean flag)
    {
        return encodeBase64(abyte0, 0, abyte0.length, flag);
    }

    public static String encodeHex(byte abyte0[])
    {
        StringBuilder stringbuilder = new StringBuilder(2 * abyte0.length);
        int i = abyte0.length;
        for(int j = 0; j < i; j++)
        {
            byte byte0 = abyte0[j];
            if((byte0 & 0xff) < 16)
                stringbuilder.append("0");
            stringbuilder.append(Integer.toString(byte0 & 0xff, 16));
        }

        return stringbuilder.toString();
    }

    public static String escapeForXML(String s)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        String s1 = null;
_L4:
        return s1;
_L2:
        char ac[] = s.toCharArray();
        int i = ac.length;
        StringBuilder stringbuilder = new StringBuilder((int)(1.3D * (double)i));
        int j = 0;
        int k = 0;
        while(k < i) 
        {
            char c = ac[k];
            if(c <= '>')
                if(c == '<')
                {
                    if(k > j)
                        stringbuilder.append(ac, j, k - j);
                    j = k + 1;
                    stringbuilder.append(LT_ENCODE);
                } else
                if(c == '>')
                {
                    if(k > j)
                        stringbuilder.append(ac, j, k - j);
                    j = k + 1;
                    stringbuilder.append(GT_ENCODE);
                } else
                if(c == '&')
                {
                    if(k > j)
                        stringbuilder.append(ac, j, k - j);
                    if(i <= k + 5 || ac[k + 1] != '#' || !Character.isDigit(ac[k + 2]) || !Character.isDigit(ac[k + 3]) || !Character.isDigit(ac[k + 4]) || ac[k + 5] != ';')
                    {
                        j = k + 1;
                        stringbuilder.append(AMP_ENCODE);
                    }
                } else
                if(c == '"')
                {
                    if(k > j)
                        stringbuilder.append(ac, j, k - j);
                    j = k + 1;
                    stringbuilder.append(QUOTE_ENCODE);
                } else
                if(c == '\'')
                {
                    if(k > j)
                        stringbuilder.append(ac, j, k - j);
                    j = k + 1;
                    stringbuilder.append(APOS_ENCODE);
                }
            k++;
        }
        if(j == 0)
        {
            s1 = s;
        } else
        {
            if(k > j)
                stringbuilder.append(ac, j, k - j);
            s1 = stringbuilder.toString();
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static String escapeNode(String s)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        String s1 = null;
_L4:
        return s1;
_L2:
        StringBuilder stringbuilder;
label0:
        {
label1:
            {
label2:
                {
label3:
                    {
label4:
                        {
label5:
                            {
label6:
                                {
label7:
                                    {
label8:
                                        {
                                            stringbuilder = new StringBuilder(8 + s.length());
                                            int i = 0;
                                            int j = s.length();
label9:
                                            do
                                            {
                                                {
                                                    if(i >= j)
                                                        break label0;
                                                    char c = s.charAt(i);
                                                    switch(c)
                                                    {
                                                    default:
                                                        if(Character.isWhitespace(c))
                                                            stringbuilder.append("\\20");
                                                        else
                                                            stringbuilder.append(c);
                                                        break;

                                                    case 34: // '"'
                                                        break label9;

                                                    case 38: // '&'
                                                        break label8;

                                                    case 39: // '\''
                                                        break label7;

                                                    case 47: // '/'
                                                        break label6;

                                                    case 58: // ':'
                                                        break label5;

                                                    case 60: // '<'
                                                        break label4;

                                                    case 62: // '>'
                                                        break label3;

                                                    case 64: // '@'
                                                        break label2;

                                                    case 92: // '\\'
                                                        break label1;
                                                    }
                                                }
                                                i++;
                                            } while(true);
                                            stringbuilder.append("\\22");
                                            break MISSING_BLOCK_LABEL_143;
                                        }
                                        stringbuilder.append("\\26");
                                        break MISSING_BLOCK_LABEL_143;
                                    }
                                    stringbuilder.append("\\27");
                                    break MISSING_BLOCK_LABEL_143;
                                }
                                stringbuilder.append("\\2f");
                                break MISSING_BLOCK_LABEL_143;
                            }
                            stringbuilder.append("\\3a");
                            break MISSING_BLOCK_LABEL_143;
                        }
                        stringbuilder.append("\\3c");
                        break MISSING_BLOCK_LABEL_143;
                    }
                    stringbuilder.append("\\3e");
                    break MISSING_BLOCK_LABEL_143;
                }
                stringbuilder.append("\\40");
                break MISSING_BLOCK_LABEL_143;
            }
            stringbuilder.append("\\5c");
            break MISSING_BLOCK_LABEL_143;
        }
        s1 = stringbuilder.toString();
        if(true) goto _L4; else goto _L3
_L3:
    }

    /**
     * @deprecated Method hash is deprecated
     */

    public static String hash(String s)
    {
        org/jivesoftware/smack/util/StringUtils;
        JVM INSTR monitorenter ;
        MessageDigest messagedigest = digest;
        if(messagedigest != null)
            break MISSING_BLOCK_LABEL_19;
        digest = MessageDigest.getInstance("SHA-1");
_L1:
        digest.update(s.getBytes("UTF-8"));
_L2:
        String s1 = encodeHex(digest.digest());
        org/jivesoftware/smack/util/StringUtils;
        JVM INSTR monitorexit ;
        return s1;
        NoSuchAlgorithmException nosuchalgorithmexception;
        nosuchalgorithmexception;
        System.err.println("Failed to load the SHA-1 MessageDigest. Jive will be unable to function normally.");
          goto _L1
        Exception exception;
        exception;
        throw exception;
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        System.err.println(unsupportedencodingexception);
          goto _L2
    }

    public static String parseBareAddress(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            int i = s.indexOf("/");
            if(i < 0)
                s1 = s;
            else
            if(i == 0)
                s1 = "";
            else
                s1 = s.substring(0, i);
        }
        return s1;
    }

    public static String parseName(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            int i = s.lastIndexOf("@");
            if(i <= 0)
                s1 = "";
            else
                s1 = s.substring(0, i);
        }
        return s1;
    }

    public static String parseResource(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            int i = s.indexOf("/");
            if(i + 1 > s.length() || i < 0)
                s1 = "";
            else
                s1 = s.substring(i + 1);
        }
        return s1;
    }

    public static String parseServer(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            int i = s.lastIndexOf("@");
            if(i + 1 > s.length())
            {
                s1 = "";
            } else
            {
                int j = s.indexOf("/");
                if(j > 0 && j > i)
                    s1 = s.substring(i + 1, j);
                else
                    s1 = s.substring(i + 1);
            }
        }
        return s1;
    }

    public static String randomString(int i)
    {
        String s;
        if(i < 1)
        {
            s = null;
        } else
        {
            char ac[] = new char[i];
            for(int j = 0; j < ac.length; j++)
                ac[j] = numbersAndLetters[randGen.nextInt(71)];

            s = new String(ac);
        }
        return s;
    }

    public static String unescapeNode(String s)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        String s1 = null;
_L24:
        return s1;
_L2:
        char ac[];
        StringBuilder stringbuilder;
        int i;
        int j;
        ac = s.toCharArray();
        stringbuilder = new StringBuilder(ac.length);
        i = 0;
        j = ac.length;
_L14:
        if(i >= j) goto _L4; else goto _L3
_L3:
        char c = s.charAt(i);
        if(c != '\\' || i + 2 >= j) goto _L6; else goto _L5
_L5:
        char c1;
        char c2;
        c1 = ac[i + 1];
        c2 = ac[i + 2];
        if(c1 != '2') goto _L8; else goto _L7
_L7:
        c2;
        JVM INSTR lookupswitch 5: default 132
    //                   48: 145
    //                   50: 158
    //                   54: 171
    //                   55: 184
    //                   102: 197;
           goto _L6 _L9 _L10 _L11 _L12 _L13
_L6:
        stringbuilder.append(c);
_L15:
        i++;
          goto _L14
_L9:
        stringbuilder.append(' ');
        i += 2;
          goto _L15
_L10:
        stringbuilder.append('"');
        i += 2;
          goto _L15
_L11:
        stringbuilder.append('&');
        i += 2;
          goto _L15
_L12:
        stringbuilder.append('\'');
        i += 2;
          goto _L15
_L13:
        stringbuilder.append('/');
        i += 2;
          goto _L15
_L8:
        if(c1 != '3') goto _L17; else goto _L16
_L16:
        c2;
        JVM INSTR tableswitch 97 101: default 252
    //                   97 255
    //                   98 252
    //                   99 268
    //                   100 252
    //                   101 281;
           goto _L6 _L18 _L6 _L19 _L6 _L20
_L18:
        stringbuilder.append(':');
        i += 2;
          goto _L15
_L19:
        stringbuilder.append('<');
        i += 2;
          goto _L15
_L20:
        stringbuilder.append('>');
        i += 2;
          goto _L15
_L17:
        if(c1 != '4')
            continue; /* Loop/switch isn't completed */
        if(c2 != '0') goto _L6; else goto _L21
_L21:
        stringbuilder.append("@");
        i += 2;
          goto _L15
        if(c1 != '5' || c2 != 'c') goto _L6; else goto _L22
_L22:
        stringbuilder.append("\\");
        i += 2;
          goto _L15
_L4:
        s1 = stringbuilder.toString();
        if(true) goto _L24; else goto _L23
_L23:
    }

    private static final char AMP_ENCODE[] = "&amp;".toCharArray();
    private static final char APOS_ENCODE[] = "&apos;".toCharArray();
    private static final char GT_ENCODE[] = "&gt;".toCharArray();
    private static final char LT_ENCODE[] = "&lt;".toCharArray();
    private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();
    private static MessageDigest digest = null;
    private static char numbersAndLetters[] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static Random randGen = new Random();

}
