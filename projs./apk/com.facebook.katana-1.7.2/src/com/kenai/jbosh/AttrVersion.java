// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttrVersion.java

package com.kenai.jbosh;


// Referenced classes of package com.kenai.jbosh:
//            AbstractAttr, BOSHException

final class AttrVersion extends AbstractAttr
    implements Comparable
{

    private AttrVersion(String s)
        throws BOSHException
    {
        super(s);
        int i = s.indexOf('.');
        if(i <= 0)
            throw new BOSHException((new StringBuilder()).append("Illegal ver attribute value (not in major.minor form): ").append(s).toString());
        String s1 = s.substring(0, i);
        try
        {
            major = Integer.parseInt(s1);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new BOSHException((new StringBuilder()).append("Could not parse ver attribute value (major ver): ").append(s1).toString(), numberformatexception);
        }
        if(major < 0)
            throw new BOSHException("Major version may not be < 0");
        String s2 = s.substring(i + 1);
        try
        {
            minor = Integer.parseInt(s2);
        }
        catch(NumberFormatException numberformatexception1)
        {
            throw new BOSHException((new StringBuilder()).append("Could not parse ver attribute value (minor ver): ").append(s2).toString(), numberformatexception1);
        }
        if(minor < 0)
            throw new BOSHException("Minor version may not be < 0");
        else
            return;
    }

    static AttrVersion createFromString(String s)
        throws BOSHException
    {
        AttrVersion attrversion;
        if(s == null)
            attrversion = null;
        else
            attrversion = new AttrVersion(s);
        return attrversion;
    }

    static AttrVersion getSupportedVersion()
    {
        return DEFAULT;
    }

    public int compareTo(Object obj)
    {
        byte byte0;
        if(obj instanceof AttrVersion)
        {
            AttrVersion attrversion = (AttrVersion)obj;
            if(major < attrversion.major)
                byte0 = -1;
            else
            if(major > attrversion.major)
                byte0 = 1;
            else
            if(minor < attrversion.minor)
                byte0 = -1;
            else
            if(minor > attrversion.minor)
                byte0 = 1;
            else
                byte0 = 0;
        } else
        {
            byte0 = 0;
        }
        return byte0;
    }

    int getMajor()
    {
        return major;
    }

    int getMinor()
    {
        return minor;
    }

    private static final AttrVersion DEFAULT;
    private final int major;
    private final int minor;

    static 
    {
        try
        {
            DEFAULT = createFromString("1.8");
        }
        catch(BOSHException boshexception)
        {
            throw new IllegalStateException(boshexception);
        }
    }
}
