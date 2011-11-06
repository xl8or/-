// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SetResponse.java

package org.xbill.DNS;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.xbill.DNS:
//            RRset, CNAMERecord, DNAMERecord

public class SetResponse
{

    private SetResponse()
    {
    }

    SetResponse(int i)
    {
        if(i < 0 || i > 6)
        {
            throw new IllegalArgumentException("invalid type");
        } else
        {
            type = i;
            data = null;
            return;
        }
    }

    SetResponse(int i, RRset rrset)
    {
        if(i < 0 || i > 6)
        {
            throw new IllegalArgumentException("invalid type");
        } else
        {
            type = i;
            data = rrset;
            return;
        }
    }

    static SetResponse ofType(int i)
    {
        i;
        JVM INSTR tableswitch 0 6: default 44
    //                   0 54
    //                   1 60
    //                   2 67
    //                   3 74
    //                   4 74
    //                   5 74
    //                   6 74;
           goto _L1 _L2 _L3 _L4 _L5 _L5 _L5 _L5
_L1:
        throw new IllegalArgumentException("invalid type");
_L2:
        SetResponse setresponse = unknown;
_L7:
        return setresponse;
_L3:
        setresponse = nxdomain;
        continue; /* Loop/switch isn't completed */
_L4:
        setresponse = nxrrset;
        continue; /* Loop/switch isn't completed */
_L5:
        setresponse = new SetResponse();
        setresponse.type = i;
        setresponse.data = null;
        if(true) goto _L7; else goto _L6
_L6:
    }

    void addRRset(RRset rrset)
    {
        if(data == null)
            data = new ArrayList();
        ((List)data).add(rrset);
    }

    public RRset[] answers()
    {
        RRset arrset[];
        if(type != 6)
        {
            arrset = null;
        } else
        {
            List list = (List)data;
            arrset = (RRset[])(RRset[])list.toArray(new RRset[list.size()]);
        }
        return arrset;
    }

    public CNAMERecord getCNAME()
    {
        return (CNAMERecord)((RRset)data).first();
    }

    public DNAMERecord getDNAME()
    {
        return (DNAMERecord)((RRset)data).first();
    }

    public RRset getNS()
    {
        return (RRset)data;
    }

    public boolean isCNAME()
    {
        boolean flag;
        if(type == 4)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isDNAME()
    {
        boolean flag;
        if(type == 5)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isDelegation()
    {
        boolean flag;
        if(type == 3)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isNXDOMAIN()
    {
        boolean flag;
        if(type == 1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isNXRRSET()
    {
        boolean flag;
        if(type == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isSuccessful()
    {
        boolean flag;
        if(type == 6)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isUnknown()
    {
        boolean flag;
        if(type == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String toString()
    {
        type;
        JVM INSTR tableswitch 0 6: default 48
    //                   0 56
    //                   1 61
    //                   2 67
    //                   3 73
    //                   4 99
    //                   5 125
    //                   6 151;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
        throw new IllegalStateException();
_L2:
        String s = "unknown";
_L10:
        return s;
_L3:
        s = "NXDOMAIN";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "NXRRSET";
        continue; /* Loop/switch isn't completed */
_L5:
        s = (new StringBuilder()).append("delegation: ").append(data).toString();
        continue; /* Loop/switch isn't completed */
_L6:
        s = (new StringBuilder()).append("CNAME: ").append(data).toString();
        continue; /* Loop/switch isn't completed */
_L7:
        s = (new StringBuilder()).append("DNAME: ").append(data).toString();
        continue; /* Loop/switch isn't completed */
_L8:
        s = "successful";
        if(true) goto _L10; else goto _L9
_L9:
    }

    static final int CNAME = 4;
    static final int DELEGATION = 3;
    static final int DNAME = 5;
    static final int NXDOMAIN = 1;
    static final int NXRRSET = 2;
    static final int SUCCESSFUL = 6;
    static final int UNKNOWN;
    private static final SetResponse nxdomain = new SetResponse(1);
    private static final SetResponse nxrrset = new SetResponse(2);
    private static final SetResponse unknown = new SetResponse(0);
    private Object data;
    private int type;

}
