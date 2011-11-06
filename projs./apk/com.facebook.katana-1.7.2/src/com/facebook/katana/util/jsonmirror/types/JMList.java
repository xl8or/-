// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMList.java

package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.jsonmirror.JMFatalException;
import java.util.*;

// Referenced classes of package com.facebook.katana.util.jsonmirror.types:
//            JMBase, JMDict, JMSimpleDict, JMString

public class JMList extends JMBase
{

    public JMList(Set set)
    {
        this(set, 0, 0);
    }

    public JMList(Set set, int i, int j)
    {
        int k = 0;
        int l = 0;
        int i1 = 0;
        Iterator iterator = set.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            JMBase jmbase = (JMBase)iterator.next();
            if(jmbase instanceof JMList)
                l++;
            else
            if((jmbase instanceof JMDict) || (jmbase instanceof JMSimpleDict))
                i1++;
            else
            if(jmbase instanceof JMString)
                k++;
        } while(true);
        if(l > 1)
            throw new JMFatalException("We don't handle multiple types of child lists in the same list.");
        if(i1 > 1)
            throw new JMFatalException("We don't handle multiple types of child dictionaries in the same list.");
        if(k > 1)
        {
            throw new JMFatalException("We don't handle multiple types of strings in the same list.");
        } else
        {
            mObjectTypes = set;
            mMinCount = i;
            mMaxCount = j;
            return;
        }
    }

    public Set getObjectTypes()
    {
        return Collections.unmodifiableSet(mObjectTypes);
    }

    public final int mMaxCount;
    public final int mMinCount;
    protected final Set mObjectTypes;
}
