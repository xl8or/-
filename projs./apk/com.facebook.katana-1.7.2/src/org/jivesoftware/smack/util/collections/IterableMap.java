// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IterableMap.java

package org.jivesoftware.smack.util.collections;

import java.util.Map;

// Referenced classes of package org.jivesoftware.smack.util.collections:
//            MapIterator

public interface IterableMap
    extends Map
{

    public abstract MapIterator mapIterator();
}
