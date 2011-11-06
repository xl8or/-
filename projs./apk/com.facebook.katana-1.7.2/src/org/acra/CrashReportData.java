// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CrashReportData.java

package org.acra;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

// Referenced classes of package org.acra:
//            ReportField

public class CrashReportData extends EnumMap
{

    public CrashReportData()
    {
        super(org/acra/ReportField);
    }

    public CrashReportData(CrashReportData crashreportdata)
    {
        super(org/acra/ReportField);
        defaults = crashreportdata;
    }

    private void dumpString(StringBuilder stringbuilder, String s, boolean flag)
    {
label0:
        {
label1:
            {
label2:
                {
label3:
                    {
                        int i = 0;
                        if(!flag && i < s.length() && s.charAt(i) == ' ')
                        {
                            stringbuilder.append("\\ ");
                            i++;
                        }
label4:
                        do
                        {
                            {
                                if(i >= s.length())
                                    break label0;
                                char c = s.charAt(i);
                                switch(c)
                                {
                                case 11: // '\013'
                                default:
                                    if("\\#!=:".indexOf(c) >= 0 || flag && c == ' ')
                                        stringbuilder.append('\\');
                                    if(c >= ' ' && c <= '~')
                                    {
                                        stringbuilder.append(c);
                                    } else
                                    {
                                        String s1 = Integer.toHexString(c);
                                        stringbuilder.append("\\u");
                                        for(int j = 0; j < 4 - s1.length(); j++)
                                            stringbuilder.append("0");

                                        stringbuilder.append(s1);
                                    }
                                    break;

                                case 9: // '\t'
                                    break label4;

                                case 10: // '\n'
                                    break label3;

                                case 12: // '\f'
                                    break label2;

                                case 13: // '\r'
                                    break label1;
                                }
                            }
                            i++;
                        } while(true);
                        stringbuilder.append("\\t");
                        break MISSING_BLOCK_LABEL_141;
                    }
                    stringbuilder.append("\\n");
                    break MISSING_BLOCK_LABEL_141;
                }
                stringbuilder.append("\\f");
                break MISSING_BLOCK_LABEL_141;
            }
            stringbuilder.append("\\r");
            break MISSING_BLOCK_LABEL_141;
        }
    }

    private boolean isEbcdic(BufferedInputStream bufferedinputstream)
        throws IOException
    {
_L2:
        byte byte0 = (byte)bufferedinputstream.read();
        boolean flag;
        if(byte0 != -1)
        {
            if(byte0 != 35 && byte0 != 10 && byte0 != 61)
                continue; /* Loop/switch isn't completed */
            flag = false;
        } else
        {
            flag = false;
        }
_L3:
        return flag;
        if(byte0 != 21) goto _L2; else goto _L1
_L1:
        flag = true;
          goto _L3
    }

    private Enumeration keys()
    {
        return Collections.enumeration(keySet());
    }

    private String substitutePredefinedEntries(String s)
    {
        return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
    }

    public String getProperty(ReportField reportfield)
    {
        String s = (String)super.get(reportfield);
        if(s == null && defaults != null)
            s = defaults.getProperty(reportfield);
        return s;
    }

    public String getProperty(ReportField reportfield, String s)
    {
        String s1 = (String)super.get(reportfield);
        if(s1 == null && defaults != null)
            s1 = defaults.getProperty(reportfield);
        String s2;
        if(s1 == null)
            s2 = s;
        else
            s2 = s1;
        return s2;
    }

    public void list(PrintStream printstream)
    {
        if(printstream == null)
            throw new NullPointerException();
        StringBuilder stringbuilder = new StringBuilder(80);
        Enumeration enumeration = keys();
        while(enumeration.hasMoreElements()) 
        {
            ReportField reportfield = (ReportField)enumeration.nextElement();
            stringbuilder.append(reportfield);
            stringbuilder.append('=');
            String s = (String)super.get(reportfield);
            for(CrashReportData crashreportdata = defaults; s == null; crashreportdata = crashreportdata.defaults)
                s = (String)crashreportdata.get(reportfield);

            if(s.length() > 40)
            {
                stringbuilder.append(s.substring(0, 37));
                stringbuilder.append("...");
            } else
            {
                stringbuilder.append(s);
            }
            printstream.println(stringbuilder.toString());
            stringbuilder.setLength(0);
        }
    }

    public void list(PrintWriter printwriter)
    {
        if(printwriter == null)
            throw new NullPointerException();
        StringBuilder stringbuilder = new StringBuilder(80);
        Enumeration enumeration = keys();
        while(enumeration.hasMoreElements()) 
        {
            ReportField reportfield = (ReportField)enumeration.nextElement();
            stringbuilder.append(reportfield);
            stringbuilder.append('=');
            String s = (String)super.get(reportfield);
            for(CrashReportData crashreportdata = defaults; s == null; crashreportdata = crashreportdata.defaults)
                s = (String)crashreportdata.get(reportfield);

            if(s.length() > 40)
            {
                stringbuilder.append(s.substring(0, 37));
                stringbuilder.append("...");
            } else
            {
                stringbuilder.append(s);
            }
            printwriter.println(stringbuilder.toString());
            stringbuilder.setLength(0);
        }
    }

    /**
     * @deprecated Method load is deprecated
     */

    public void load(InputStream inputstream)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        if(inputstream != null)
            break MISSING_BLOCK_LABEL_21;
        throw new NullPointerException();
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        BufferedInputStream bufferedinputstream;
        bufferedinputstream = new BufferedInputStream(inputstream);
        bufferedinputstream.mark(0x7fffffff);
        boolean flag = isEbcdic(bufferedinputstream);
        bufferedinputstream.reset();
        if(flag)
            break MISSING_BLOCK_LABEL_67;
        load(((Reader) (new InputStreamReader(bufferedinputstream, "ISO8859-1"))));
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
        load(((Reader) (new InputStreamReader(bufferedinputstream))));
          goto _L1
    }

    /**
     * @deprecated Method load is deprecated
     */

    public void load(Reader reader)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        int j;
        int k;
        i = 0;
        j = 0;
        k = 0;
        char ac[];
        int l;
        boolean flag;
        BufferedReader bufferedreader;
        int i1;
        ac = new char[40];
        l = -1;
        flag = true;
        bufferedreader = new BufferedReader(reader);
        i1 = 0;
_L9:
        int j1 = bufferedreader.read();
        if(j1 != -1) goto _L2; else goto _L1
_L1:
        if(i == 2 && k <= 4)
            throw new IllegalArgumentException("luni.08");
          goto _L3
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        char c = (char)j1;
        int k1 = ac.length;
        if(i1 == k1)
        {
            char ac1[] = new char[2 * ac.length];
            System.arraycopy(ac, 0, ac1, 0, i1);
            ac = ac1;
        }
        if(i != 2) goto _L5; else goto _L4
_L4:
        int j2 = Character.digit(c, 16);
        if(j2 < 0) goto _L7; else goto _L6
_L6:
        j = j2 + (j << 4);
        if(++k < 4) goto _L9; else goto _L8
_L8:
        int k2;
        i = 0;
        k2 = i1 + 1;
        ac[i1] = (char)j;
        if(c == '\n' || c == '\205') goto _L11; else goto _L10
_L10:
        i1 = k2;
          goto _L9
_L7:
        if(k > 4) goto _L8; else goto _L12
_L12:
        throw new IllegalArgumentException("luni.09");
_L30:
        int l1 = i1 + 1;
        ac[i1] = c;
        i1 = l1;
          goto _L9
_L31:
        if(!Character.isWhitespace(c)) goto _L14; else goto _L13
_L13:
        if(i == 3)
            i = 5;
          goto _L15
_L17:
        int i2 = bufferedreader.read();
        if(i2 == -1) goto _L9; else goto _L16
_L16:
        char c1 = (char)i2;
        if(c1 != '\r' && c1 != '\n' && c1 != '\205') goto _L17; else goto _L9
_L41:
        String s = new String(ac, 0, i1);
        put(Enum.valueOf(org/acra/ReportField, s.substring(0, l)), s.substring(l));
          goto _L18
_L43:
        if(l >= 0)
        {
            String s1 = new String(ac, 0, i1);
            ReportField reportfield = (ReportField)Enum.valueOf(org/acra/ReportField, s1.substring(0, l));
            String s2 = s1.substring(l);
            if(i == 1)
                s2 = (new StringBuilder()).append(s2).append("\uFFFD\uFFFD").toString();
            put(reportfield, s2);
        }
        this;
        JVM INSTR monitorexit ;
        return;
_L11:
        i1 = k2;
_L5:
        if(i != 1) goto _L20; else goto _L19
_L19:
        i = 0;
        c;
        JVM INSTR lookupswitch 9: default 512
    //                   10: 534
    //                   13: 529
    //                   98: 539
    //                   102: 546
    //                   110: 553
    //                   114: 560
    //                   116: 567
    //                   117: 574
    //                   133: 534;
           goto _L21 _L22 _L23 _L24 _L25 _L26 _L27 _L28 _L29 _L22
_L21:
        flag = false;
        if(i == 4)
        {
            l = i1;
            i = 0;
        }
          goto _L30
_L23:
        i = 3;
          goto _L9
_L22:
        i = 5;
          goto _L9
_L24:
        c = '\b';
          goto _L21
_L25:
        c = '\f';
          goto _L21
_L26:
        c = '\n';
          goto _L21
_L27:
        c = '\r';
          goto _L21
_L28:
        c = '\t';
          goto _L21
_L29:
        i = 2;
        k = 0;
        j = k;
          goto _L9
_L20:
        c;
        JVM INSTR lookupswitch 8: default 226
    //                   10: 697
    //                   13: 707
    //                   33: 689
    //                   35: 689
    //                   58: 765
    //                   61: 765
    //                   92: 751
    //                   133: 707;
           goto _L31 _L32 _L33 _L34 _L34 _L35 _L35 _L36 _L33
_L35:
        continue; /* Loop/switch isn't completed */
_L15:
        if(i1 == 0 || i1 == l || i == 5) goto _L9; else goto _L37
_L37:
        if(l != -1) goto _L14; else goto _L38
_L38:
        i = 4;
          goto _L9
_L34:
        if(!flag) goto _L31; else goto _L17
_L32:
        if(i != 3) goto _L33; else goto _L39
_L39:
        i = 5;
          goto _L9
_L33:
        i = 0;
        flag = true;
        if(i1 <= 0 && (i1 != 0 || l != 0)) goto _L18; else goto _L40
_L40:
        if(l == -1)
            l = i1;
          goto _L41
_L18:
        l = -1;
        i1 = 0;
          goto _L9
_L36:
        if(i == 4)
            l = i1;
        i = 1;
          goto _L9
        if(l != -1) goto _L31; else goto _L42
_L42:
        i = 0;
        l = i1;
          goto _L9
_L14:
        if(i == 5 || i == 3)
            i = 0;
          goto _L21
_L3:
        if(l == -1 && i1 > 0)
            l = i1;
          goto _L43
    }

    public void save(OutputStream outputstream, String s)
    {
        store(outputstream, s);
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Object setProperty(ReportField reportfield, String s)
    {
        return put(reportfield, s);
    }

    /**
     * @deprecated Method store is deprecated
     */

    public void store(OutputStream outputstream, String s)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        OutputStreamWriter outputstreamwriter;
        StringBuilder stringbuilder = new StringBuilder(200);
        outputstreamwriter = new OutputStreamWriter(outputstream, "ISO8859_1");
        if(s != null)
        {
            outputstreamwriter.write("#");
            outputstreamwriter.write(s);
            outputstreamwriter.write(lineSeparator);
        }
        for(Iterator iterator = entrySet().iterator(); iterator.hasNext(); stringbuilder.setLength(0))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            dumpString(stringbuilder, ((ReportField)entry.getKey()).toString(), true);
            stringbuilder.append('=');
            dumpString(stringbuilder, (String)entry.getValue(), false);
            stringbuilder.append(lineSeparator);
            outputstreamwriter.write(stringbuilder.toString());
        }

        break MISSING_BLOCK_LABEL_159;
        Exception exception;
        exception;
        throw exception;
        outputstreamwriter.flush();
        this;
        JVM INSTR monitorexit ;
    }

    /**
     * @deprecated Method store is deprecated
     */

    public void store(Writer writer, String s)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        StringBuilder stringbuilder = new StringBuilder(200);
        if(s != null)
        {
            writer.write("#");
            writer.write(s);
            writer.write(lineSeparator);
        }
        for(Iterator iterator = entrySet().iterator(); iterator.hasNext(); stringbuilder.setLength(0))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            dumpString(stringbuilder, ((ReportField)entry.getKey()).toString(), true);
            stringbuilder.append('=');
            dumpString(stringbuilder, (String)entry.getValue(), false);
            stringbuilder.append(lineSeparator);
            writer.write(stringbuilder.toString());
        }

        break MISSING_BLOCK_LABEL_142;
        Exception exception;
        exception;
        throw exception;
        writer.flush();
        this;
        JVM INSTR monitorexit ;
    }

    public void storeToXML(OutputStream outputstream, String s)
        throws IOException
    {
        storeToXML(outputstream, s, "UTF-8");
    }

    /**
     * @deprecated Method storeToXML is deprecated
     */

    public void storeToXML(OutputStream outputstream, String s, String s1)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        if(outputstream != null && s1 != null)
            break MISSING_BLOCK_LABEL_25;
        throw new NullPointerException();
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        String s5 = Charset.forName(s1).name();
        String s2 = s5;
_L2:
        PrintStream printstream;
        printstream = new PrintStream(outputstream, false, s2);
        printstream.print("<?xml version=\"1.0\" encoding=\"");
        printstream.print(s2);
        printstream.println("\"?>");
        printstream.print("<!DOCTYPE properties SYSTEM \"");
        printstream.print("http://java.sun.com/dtd/properties.dtd");
        printstream.println("\">");
        printstream.println("<properties>");
        if(s != null)
        {
            printstream.print("<comment>");
            printstream.print(substitutePredefinedEntries(s));
            printstream.println("</comment>");
        }
        for(Iterator iterator = entrySet().iterator(); iterator.hasNext(); printstream.println("</entry>"))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            String s3 = ((ReportField)entry.getKey()).toString();
            String s4 = (String)entry.getValue();
            printstream.print("<entry key=\"");
            printstream.print(substitutePredefinedEntries(s3));
            printstream.print("\">");
            printstream.print(substitutePredefinedEntries(s4));
        }

        break; /* Loop/switch isn't completed */
        IllegalCharsetNameException illegalcharsetnameexception;
        illegalcharsetnameexception;
        System.out.println((new StringBuilder()).append("Warning: encoding name ").append(s1).append(" is illegal, using UTF-8 as default encoding").toString());
        s2 = "UTF-8";
        continue; /* Loop/switch isn't completed */
        UnsupportedCharsetException unsupportedcharsetexception;
        unsupportedcharsetexception;
        System.out.println((new StringBuilder()).append("Warning: encoding ").append(s1).append(" is not supported, using UTF-8 as default encoding").toString());
        s2 = "UTF-8";
        if(true) goto _L2; else goto _L1
_L1:
        printstream.println("</properties>");
        printstream.flush();
        this;
        JVM INSTR monitorexit ;
    }

    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final int NONE = 0;
    private static final String PROP_DTD_NAME = "http://java.sun.com/dtd/properties.dtd";
    private static final int SLASH = 1;
    private static final int UNICODE = 2;
    private static String lineSeparator = "\n";
    private static final long serialVersionUID = 0x70363e98L;
    protected CrashReportData defaults;

}
