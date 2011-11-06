// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JMEscaped.java

package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.jsonmirror.JMFatalException;
import org.codehaus.jackson.JsonFactory;

// Referenced classes of package com.facebook.katana.util.jsonmirror.types:
//            JMBase, JMDict, JMSimpleDict, JMList

public class JMEscaped extends JMBase
{

    public JMEscaped(JMBase jmbase, JsonFactory jsonfactory)
    {
        if(!(jmbase instanceof JMDict) && !(jmbase instanceof JMSimpleDict) && !(jmbase instanceof JMList))
        {
            throw new JMFatalException("stringified values can only encapsulate complex types.");
        } else
        {
            mInnerObject = jmbase;
            mFactory = jsonfactory;
            return;
        }
    }

    public final JsonFactory mFactory;
    public final JMBase mInnerObject;
}
