// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResolverConfig.java

package org.xbill.DNS;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            TextParseException, Options, Name

public class ResolverConfig
{

    public ResolverConfig()
    {
        servers = null;
        searchlist = null;
        break MISSING_BLOCK_LABEL_14;
        if(!findProperty() && !findSunJVM() && (servers == null || searchlist == null))
        {
            String s = System.getProperty("os.name");
            String s1 = System.getProperty("java.vendor");
            if(s.indexOf("Windows") != -1)
            {
                if(s.indexOf("95") != -1 || s.indexOf("98") != -1 || s.indexOf("ME") != -1)
                    find95();
                else
                    findNT();
            } else
            if(s.indexOf("NetWare") != -1)
                findNetware();
            else
            if(s1.indexOf("Android") != -1)
                findAndroid();
            else
                findUnix();
        }
        return;
    }

    private void addSearch(String s, List list)
    {
        if(Options.check("verbose"))
            System.out.println((new StringBuilder()).append("adding search ").append(s).toString());
        Name name = Name.fromString(s, Name.root);
        if(!list.contains(name))
            list.add(name);
_L2:
        return;
        TextParseException textparseexception;
        textparseexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void addServer(String s, List list)
    {
        if(!list.contains(s))
        {
            if(Options.check("verbose"))
                System.out.println((new StringBuilder()).append("adding server ").append(s).toString());
            list.add(s);
        }
    }

    private void configureFromLists(List list, List list1)
    {
        if(servers == null && list.size() > 0)
            servers = (String[])(String[])list.toArray(new String[0]);
        if(searchlist == null && list1.size() > 0)
            searchlist = (Name[])(Name[])list1.toArray(new Name[0]);
    }

    private void find95()
    {
        Runtime.getRuntime().exec((new StringBuilder()).append("winipcfg /all /batch ").append("winipcfg.out").toString()).waitFor();
        findWin(new FileInputStream(new File("winipcfg.out")));
        (new File("winipcfg.out")).delete();
_L2:
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void findAndroid()
    {
        try
        {
            ArrayList arraylist = new ArrayList();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream()));
            do
            {
                String s = bufferedreader.readLine();
                if(s == null)
                    break;
                StringTokenizer stringtokenizer = new StringTokenizer(s, ":");
                if(stringtokenizer.nextToken().indexOf(".dns") > -1)
                {
                    String s1 = stringtokenizer.nextToken().replaceAll("[ \\[\\]]", "");
                    if((s1.matches("^\\d+(\\.\\d+){3}$") || s1.matches("^[0-9a-f]+(:[0-9a-f]*)+:[0-9a-f]+$")) && !arraylist.contains(s1))
                        arraylist.add(s1);
                }
            } while(true);
            configureFromLists(arraylist, null);
        }
        catch(Exception exception) { }
    }

    private void findNT()
    {
        Process process = Runtime.getRuntime().exec("ipconfig /all");
        findWin(process.getInputStream());
        process.destroy();
_L2:
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void findNetware()
    {
        findResolvConf("sys:/etc/resolv.cfg");
    }

    private boolean findProperty()
    {
        ArrayList arraylist = new ArrayList(0);
        ArrayList arraylist1 = new ArrayList(0);
        String s = System.getProperty("dns.server");
        if(s != null)
        {
            for(StringTokenizer stringtokenizer = new StringTokenizer(s, ","); stringtokenizer.hasMoreTokens(); addServer(stringtokenizer.nextToken(), arraylist));
        }
        String s1 = System.getProperty("dns.search");
        if(s1 != null)
        {
            for(StringTokenizer stringtokenizer1 = new StringTokenizer(s1, ","); stringtokenizer1.hasMoreTokens(); addSearch(stringtokenizer1.nextToken(), arraylist1));
        }
        configureFromLists(arraylist, arraylist1);
        boolean flag;
        if(servers != null && searchlist != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void findResolvConf(String s)
    {
        FileInputStream fileinputstream = new FileInputStream(s);
        BufferedReader bufferedreader;
        ArrayList arraylist;
        ArrayList arraylist1;
        bufferedreader = new BufferedReader(new InputStreamReader(fileinputstream));
        arraylist = new ArrayList(0);
        arraylist1 = new ArrayList(0);
_L5:
        String s1 = bufferedreader.readLine();
        if(s1 == null) goto _L2; else goto _L1
_L1:
        if(!s1.startsWith("nameserver")) goto _L4; else goto _L3
_L3:
        StringTokenizer stringtokenizer = new StringTokenizer(s1);
        stringtokenizer.nextToken();
        addServer(stringtokenizer.nextToken(), arraylist);
          goto _L5
        IOException ioexception;
        ioexception;
_L7:
        configureFromLists(arraylist, arraylist1);
_L8:
        return;
_L4:
        if(!s1.startsWith("domain"))
            continue; /* Loop/switch isn't completed */
        StringTokenizer stringtokenizer1 = new StringTokenizer(s1);
        stringtokenizer1.nextToken();
        if(stringtokenizer1.hasMoreTokens() && arraylist1.isEmpty())
            addSearch(stringtokenizer1.nextToken(), arraylist1);
          goto _L5
        if(!s1.startsWith("search")) goto _L5; else goto _L6
_L6:
        if(!arraylist1.isEmpty())
            arraylist1.clear();
        StringTokenizer stringtokenizer2 = new StringTokenizer(s1);
        stringtokenizer2.nextToken();
        while(stringtokenizer2.hasMoreTokens()) 
            addSearch(stringtokenizer2.nextToken(), arraylist1);
          goto _L5
_L2:
        bufferedreader.close();
          goto _L7
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
          goto _L8
    }

    private boolean findSunJVM()
    {
label0:
        {
            ArrayList arraylist = new ArrayList(0);
            ArrayList arraylist1 = new ArrayList(0);
            boolean flag;
            List list;
            List list1;
            try
            {
                Class aclass[] = new Class[0];
                Object aobj[] = new Object[0];
                Class class1 = Class.forName("sun.net.dns.ResolverConfiguration");
                Object obj = class1.getDeclaredMethod("open", aclass).invoke(null, aobj);
                list = (List)class1.getMethod("nameservers", aclass).invoke(obj, aobj);
                list1 = (List)class1.getMethod("searchlist", aclass).invoke(obj, aobj);
            }
            catch(Exception exception)
            {
                flag = false;
                if(false)
                    ;
                else
                    break label0;
            }
            if(list.size() == 0)
            {
                flag = false;
            } else
            {
                if(list.size() > 0)
                {
                    for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); addServer((String)iterator1.next(), arraylist));
                }
                if(list1.size() > 0)
                {
                    for(Iterator iterator = list1.iterator(); iterator.hasNext(); addSearch((String)iterator.next(), arraylist1));
                }
                configureFromLists(arraylist, arraylist1);
                flag = true;
            }
        }
        return flag;
    }

    private void findUnix()
    {
        findResolvConf("/etc/resolv.conf");
    }

    private void findWin(InputStream inputstream)
    {
        String s1;
        String s2;
        String s3;
        String s4;
        BufferedReader bufferedreader;
        String s = org/xbill/DNS/ResolverConfig.getPackage().getName();
        ResourceBundle resourcebundle = ResourceBundle.getBundle((new StringBuilder()).append(s).append(".windows.DNSServer").toString());
        s1 = resourcebundle.getString("host_name");
        s2 = resourcebundle.getString("primary_dns_suffix");
        s3 = resourcebundle.getString("dns_suffix");
        s4 = resourcebundle.getString("dns_servers");
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        bufferedreader = new BufferedReader(inputstreamreader);
        ArrayList arraylist;
        ArrayList arraylist1;
        boolean flag;
        boolean flag1;
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        flag = false;
        flag1 = false;
_L5:
        String s5 = bufferedreader.readLine();
        if(s5 == null) goto _L2; else goto _L1
_L1:
        StringTokenizer stringtokenizer = new StringTokenizer(s5);
        if(stringtokenizer.hasMoreTokens()) goto _L4; else goto _L3
_L3:
        flag = false;
        flag1 = false;
          goto _L5
_L4:
        String s6;
        s6 = stringtokenizer.nextToken();
        if(s5.indexOf(":") != -1)
        {
            flag = false;
            flag1 = false;
        }
        if(s5.indexOf(s1) == -1) goto _L7; else goto _L6
_L6:
        String s10 = s6;
_L10:
        if(!stringtokenizer.hasMoreTokens()) goto _L9; else goto _L8
_L8:
        String s11 = stringtokenizer.nextToken();
        s10 = s11;
          goto _L10
_L9:
        Name name = Name.fromString(s10, null);
        if(name.labels() != 1)
            addSearch(s10, arraylist1);
          goto _L5
        IOException ioexception1;
        ioexception1;
        bufferedreader.close();
_L14:
        return;
_L7:
label0:
        {
            if(s5.indexOf(s2) == -1)
                break label0;
            String s9;
            for(s9 = s6; stringtokenizer.hasMoreTokens(); s9 = stringtokenizer.nextToken());
            if(!s9.equals(":"))
            {
                addSearch(s9, arraylist1);
                flag1 = true;
            }
        }
          goto _L5
        if(!flag1 && s5.indexOf(s3) == -1) goto _L12; else goto _L11
_L15:
        String s7;
        while(stringtokenizer.hasMoreTokens()) 
            s7 = stringtokenizer.nextToken();
        if(!s7.equals(":"))
        {
            addSearch(s7, arraylist1);
            flag1 = true;
        }
          goto _L5
_L12:
        if(!flag && s5.indexOf(s4) == -1) goto _L5; else goto _L13
_L13:
        break MISSING_BLOCK_LABEL_488;
_L16:
        String s8;
        while(stringtokenizer.hasMoreTokens()) 
            s8 = stringtokenizer.nextToken();
        if(!s8.equals(":"))
        {
            addServer(s8, arraylist);
            flag = true;
        }
          goto _L5
_L2:
        configureFromLists(arraylist, arraylist1);
        try
        {
            bufferedreader.close();
        }
        catch(IOException ioexception3) { }
          goto _L14
        Exception exception;
        exception;
        IOException ioexception2;
        TextParseException textparseexception;
        try
        {
            bufferedreader.close();
        }
        catch(IOException ioexception) { }
        throw exception;
        textparseexception;
          goto _L5
        ioexception2;
          goto _L14
_L11:
        s7 = s6;
          goto _L15
        s8 = s6;
          goto _L16
    }

    /**
     * @deprecated Method getCurrentConfig is deprecated
     */

    public static ResolverConfig getCurrentConfig()
    {
        org/xbill/DNS/ResolverConfig;
        JVM INSTR monitorenter ;
        ResolverConfig resolverconfig = currentConfig;
        org/xbill/DNS/ResolverConfig;
        JVM INSTR monitorexit ;
        return resolverconfig;
        Exception exception;
        exception;
        throw exception;
    }

    public static void refresh()
    {
        ResolverConfig resolverconfig = new ResolverConfig();
        org/xbill/DNS/ResolverConfig;
        JVM INSTR monitorenter ;
        currentConfig = resolverconfig;
        return;
    }

    public Name[] searchPath()
    {
        return searchlist;
    }

    public String server()
    {
        String s;
        if(servers == null)
            s = null;
        else
            s = servers[0];
        return s;
    }

    public String[] servers()
    {
        return servers;
    }

    private static ResolverConfig currentConfig;
    private Name searchlist[];
    private String servers[];

    static 
    {
        refresh();
    }
}
