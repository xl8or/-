// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferenceMap.java

package org.jivesoftware.smack.util.collections;

import java.io.*;

// Referenced classes of package org.jivesoftware.smack.util.collections:
//            AbstractReferenceMap

public class ReferenceMap extends AbstractReferenceMap
    implements Serializable
{

    public ReferenceMap()
    {
        super(0, 1, 16, 0.75F, false);
    }

    public ReferenceMap(int i, int j)
    {
        super(i, j, 16, 0.75F, false);
    }

    public ReferenceMap(int i, int j, int k, float f)
    {
        super(i, j, k, f, false);
    }

    public ReferenceMap(int i, int j, int k, float f, boolean flag)
    {
        super(i, j, k, f, flag);
    }

    public ReferenceMap(int i, int j, boolean flag)
    {
        super(i, j, 16, 0.75F, flag);
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        doReadObject(objectinputstream);
    }

    private void writeObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        objectoutputstream.defaultWriteObject();
        doWriteObject(objectoutputstream);
    }

    private static final long serialVersionUID = 0x984908d7L;
}
