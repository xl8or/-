// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Lookup.java

package org.xbill.DNS;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

// Referenced classes of package org.xbill.DNS:
//            Name, TextParseException, Type, DClass, 
//            Options, Mnemonic, Cache, Record, 
//            Message, Resolver, Header, Rcode, 
//            NameTooLongException, SetResponse, RRset, CNAMERecord, 
//            ExtendedResolver, ResolverConfig

public final class Lookup
{

    public Lookup(String s)
        throws TextParseException
    {
        Lookup(Name.fromString(s), 1, 1);
    }

    public Lookup(String s, int i)
        throws TextParseException
    {
        Lookup(Name.fromString(s), i, 1);
    }

    public Lookup(String s, int i, int j)
        throws TextParseException
    {
        Lookup(Name.fromString(s), i, j);
    }

    public Lookup(Name name1)
    {
        Lookup(name1, 1, 1);
    }

    public Lookup(Name name1, int i)
    {
        Lookup(name1, i, 1);
    }

    public Lookup(Name name1, int i, int j)
    {
        Type.check(i);
        DClass.check(j);
        if(!Type.isRR(i) && i != 255)
            throw new IllegalArgumentException("Cannot query for meta-types other than ANY");
        name = name1;
        type = i;
        dclass = j;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        resolver = getDefaultResolver();
        searchPath = getDefaultSearchPath();
        cache = getDefaultCache(j);
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        credibility = 3;
        verbose = Options.check("verbose");
        result = -1;
        return;
        Exception exception;
        exception;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void checkDone()
    {
        if(done && result != -1)
            return;
        StringBuffer stringbuffer = new StringBuffer((new StringBuilder()).append("Lookup of ").append(name).append(" ").toString());
        if(dclass != 1)
            stringbuffer.append((new StringBuilder()).append(DClass.string(dclass)).append(" ").toString());
        stringbuffer.append((new StringBuilder()).append(Type.string(type)).append(" isn't done").toString());
        throw new IllegalStateException(stringbuffer.toString());
    }

    private void follow(Name name1, Name name2)
    {
        foundAlias = true;
        badresponse = false;
        networkerror = false;
        timedout = false;
        nxdomain = false;
        referral = false;
        iterations = 1 + iterations;
        if(iterations >= 6 || name1.equals(name2))
        {
            result = 1;
            error = "CNAME loop";
            done = true;
        } else
        {
            if(aliases == null)
                aliases = new ArrayList();
            aliases.add(name2);
            lookup(name1);
        }
    }

    /**
     * @deprecated Method getDefaultCache is deprecated
     */

    public static Cache getDefaultCache(int i)
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        Cache cache1;
        DClass.check(i);
        cache1 = (Cache)defaultCaches.get(Mnemonic.toInteger(i));
        if(cache1 == null)
        {
            cache1 = new Cache(i);
            defaultCaches.put(Mnemonic.toInteger(i), cache1);
        }
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return cache1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getDefaultResolver is deprecated
     */

    public static Resolver getDefaultResolver()
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        Resolver resolver1 = defaultResolver;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return resolver1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getDefaultSearchPath is deprecated
     */

    public static Name[] getDefaultSearchPath()
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        Name aname[] = defaultSearchPath;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return aname;
        Exception exception;
        exception;
        throw exception;
    }

    private void lookup(Name name1)
    {
        SetResponse setresponse = cache.lookupRecords(name1, type, credibility);
        if(verbose)
        {
            System.err.println((new StringBuilder()).append("lookup ").append(name1).append(" ").append(Type.string(type)).toString());
            System.err.println(setresponse);
        }
        processResponse(name1, setresponse);
        if(!done && !doneCurrent) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Message message = Message.newQuery(Record.newRecord(name1, type, dclass));
        Message message1 = resolver.send(message);
        int i = message1.getHeader().getRcode();
        IOException ioexception;
        if(i != 0 && i != 3)
        {
            badresponse = true;
            badresponse_error = Rcode.string(i);
        } else
        if(!message.getQuestion().equals(message1.getQuestion()))
        {
            badresponse = true;
            badresponse_error = "response does not match query";
        } else
        {
            SetResponse setresponse1 = cache.addMessage(message1);
            if(setresponse1 == null)
                setresponse1 = cache.lookupRecords(name1, type, credibility);
            if(verbose)
            {
                System.err.println((new StringBuilder()).append("queried ").append(name1).append(" ").append(Type.string(type)).toString());
                System.err.println(setresponse1);
            }
            processResponse(name1, setresponse1);
        }
        continue; /* Loop/switch isn't completed */
        ioexception;
        if(ioexception instanceof InterruptedIOException)
            timedout = true;
        else
            networkerror = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void processResponse(Name name1, SetResponse setresponse)
    {
        if(!setresponse.isSuccessful()) goto _L2; else goto _L1
_L1:
        RRset arrset[] = setresponse.answers();
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < arrset.length; i++)
        {
            for(Iterator iterator = arrset[i].rrs(); iterator.hasNext(); arraylist.add(iterator.next()));
        }

        result = 0;
        answers = (Record[])(Record[])arraylist.toArray(new Record[arraylist.size()]);
        done = true;
_L4:
        return;
_L2:
        if(setresponse.isNXDOMAIN())
        {
            nxdomain = true;
            doneCurrent = true;
            if(iterations > 0)
            {
                result = 3;
                done = true;
            }
        } else
        if(setresponse.isNXRRSET())
        {
            result = 4;
            answers = null;
            done = true;
        } else
        if(setresponse.isCNAME())
            follow(setresponse.getCNAME().getTarget(), name1);
        else
        if(setresponse.isDNAME())
        {
            DNAMERecord dnamerecord = setresponse.getDNAME();
            try
            {
                follow(name1.fromDNAME(dnamerecord), name1);
            }
            catch(NameTooLongException nametoolongexception)
            {
                result = 1;
                error = "Invalid DNAME target";
                done = true;
            }
        } else
        if(setresponse.isDelegation())
            referral = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    /**
     * @deprecated Method refreshDefault is deprecated
     */

    public static void refreshDefault()
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        defaultResolver = new ExtendedResolver();
        defaultSearchPath = ResolverConfig.getCurrentConfig().searchPath();
        defaultCaches = new HashMap();
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return;
        UnknownHostException unknownhostexception;
        unknownhostexception;
        throw new RuntimeException("Failed to initialize resolver");
        Exception exception;
        exception;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private final void reset()
    {
        iterations = 0;
        foundAlias = false;
        done = false;
        doneCurrent = false;
        aliases = null;
        answers = null;
        result = -1;
        error = null;
        nxdomain = false;
        badresponse = false;
        badresponse_error = null;
        networkerror = false;
        timedout = false;
        nametoolong = false;
        referral = false;
        if(temporary_cache)
            cache.clearCache();
    }

    private void resolve(Name name1, Name name2)
    {
        doneCurrent = false;
        if(name2 != null) goto _L2; else goto _L1
_L1:
        Name name4 = name1;
_L3:
        lookup(name4);
_L4:
        return;
_L2:
        Name name3 = Name.concatenate(name1, name2);
        name4 = name3;
          goto _L3
        NameTooLongException nametoolongexception;
        nametoolongexception;
        nametoolong = true;
          goto _L4
    }

    /**
     * @deprecated Method setDefaultCache is deprecated
     */

    public static void setDefaultCache(Cache cache1, int i)
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        DClass.check(i);
        defaultCaches.put(Mnemonic.toInteger(i), cache1);
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method setDefaultResolver is deprecated
     */

    public static void setDefaultResolver(Resolver resolver1)
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        defaultResolver = resolver1;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method setDefaultSearchPath is deprecated
     */

    public static void setDefaultSearchPath(String as[])
        throws TextParseException
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        if(as != null) goto _L2; else goto _L1
_L1:
        defaultSearchPath = null;
_L4:
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return;
_L2:
        Name aname[] = new Name[as.length];
        for(int i = 0; i < as.length; i++)
            aname[i] = Name.fromString(as[i], Name.root);

        defaultSearchPath = aname;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method setDefaultSearchPath is deprecated
     */

    public static void setDefaultSearchPath(Name aname[])
    {
        org/xbill/DNS/Lookup;
        JVM INSTR monitorenter ;
        defaultSearchPath = aname;
        org/xbill/DNS/Lookup;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public Name[] getAliases()
    {
        checkDone();
        Name aname[];
        if(aliases == null)
            aname = noAliases;
        else
            aname = (Name[])(Name[])aliases.toArray(new Name[aliases.size()]);
        return aname;
    }

    public Record[] getAnswers()
    {
        checkDone();
        return answers;
    }

    public String getErrorString()
    {
        checkDone();
        if(error == null) goto _L2; else goto _L1
_L1:
        String s = error;
_L4:
        return s;
_L2:
        switch(result)
        {
        default:
            throw new IllegalStateException("unknown result");

        case 0: // '\0'
            s = "successful";
            break;

        case 1: // '\001'
            s = "unrecoverable error";
            break;

        case 2: // '\002'
            s = "try again";
            break;

        case 3: // '\003'
            s = "host not found";
            break;

        case 4: // '\004'
            s = "type not found";
            break;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getResult()
    {
        checkDone();
        return result;
    }

    public Record[] run()
    {
        Record arecord[];
        if(done)
            reset();
        if(name.isAbsolute())
        {
            resolve(name, null);
        } else
        {
label0:
            {
                if(searchPath != null)
                    break label0;
                resolve(name, Name.root);
            }
        }
_L3:
        int i;
        if(!done)
            if(badresponse)
            {
                result = 2;
                error = badresponse_error;
                done = true;
            } else
            if(timedout)
            {
                result = 2;
                error = "timed out";
                done = true;
            } else
            if(networkerror)
            {
                result = 2;
                error = "network error";
                done = true;
            } else
            if(nxdomain)
            {
                result = 3;
                done = true;
            } else
            if(referral)
            {
                result = 1;
                error = "referral";
                done = true;
            } else
            if(nametoolong)
            {
                result = 1;
                error = "name too long";
                done = true;
            }
        arecord = answers;
_L1:
        return arecord;
label1:
        {
            if(name.labels() > 1)
                resolve(name, Name.root);
            if(!done)
                break label1;
            arecord = answers;
        }
          goto _L1
        i = 0;
_L5:
        if(i >= searchPath.length) goto _L3; else goto _L2
_L2:
        resolve(name, searchPath[i]);
        if(!done)
            continue; /* Loop/switch isn't completed */
        arecord = answers;
          goto _L1
        if(foundAlias) goto _L3; else goto _L4
_L4:
        i++;
          goto _L5
    }

    public void setCache(Cache cache1)
    {
        if(cache1 == null)
        {
            cache = new Cache(dclass);
            temporary_cache = true;
        } else
        {
            cache = cache1;
            temporary_cache = false;
        }
    }

    public void setCredibility(int i)
    {
        credibility = i;
    }

    public void setResolver(Resolver resolver1)
    {
        resolver = resolver1;
    }

    public void setSearchPath(String as[])
        throws TextParseException
    {
        if(as == null)
        {
            searchPath = null;
        } else
        {
            Name aname[] = new Name[as.length];
            for(int i = 0; i < as.length; i++)
                aname[i] = Name.fromString(as[i], Name.root);

            searchPath = aname;
        }
    }

    public void setSearchPath(Name aname[])
    {
        searchPath = aname;
    }

    public static final int HOST_NOT_FOUND = 3;
    public static final int SUCCESSFUL = 0;
    public static final int TRY_AGAIN = 2;
    public static final int TYPE_NOT_FOUND = 4;
    public static final int UNRECOVERABLE = 1;
    private static Map defaultCaches;
    private static Resolver defaultResolver;
    private static Name defaultSearchPath[];
    private static final Name noAliases[] = new Name[0];
    private List aliases;
    private Record answers[];
    private boolean badresponse;
    private String badresponse_error;
    private Cache cache;
    private int credibility;
    private int dclass;
    private boolean done;
    private boolean doneCurrent;
    private String error;
    private boolean foundAlias;
    private int iterations;
    private Name name;
    private boolean nametoolong;
    private boolean networkerror;
    private boolean nxdomain;
    private boolean referral;
    private Resolver resolver;
    private int result;
    private Name searchPath[];
    private boolean temporary_cache;
    private boolean timedout;
    private int type;
    private boolean verbose;

    static 
    {
        refreshDefault();
    }
}
