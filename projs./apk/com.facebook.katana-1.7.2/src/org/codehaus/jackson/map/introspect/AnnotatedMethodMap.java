// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.introspect;

import java.lang.reflect.Method;
import java.util.*;

// Referenced classes of package org.codehaus.jackson.map.introspect:
//            MethodKey, AnnotatedMethod

public final class AnnotatedMethodMap
    implements Iterable
{

    public AnnotatedMethodMap()
    {
    }

    public void add(AnnotatedMethod annotatedmethod)
    {
        if(_methods == null)
            _methods = new LinkedHashMap();
        _methods.put(new MethodKey(annotatedmethod.getAnnotated()), annotatedmethod);
    }

    public AnnotatedMethod find(String s, Class aclass[])
    {
        AnnotatedMethod annotatedmethod;
        if(_methods == null)
            annotatedmethod = null;
        else
            annotatedmethod = (AnnotatedMethod)_methods.get(new MethodKey(s, aclass));
        return annotatedmethod;
    }

    public AnnotatedMethod find(Method method)
    {
        AnnotatedMethod annotatedmethod;
        if(_methods == null)
            annotatedmethod = null;
        else
            annotatedmethod = (AnnotatedMethod)_methods.get(new MethodKey(method));
        return annotatedmethod;
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(_methods == null || _methods.size() == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public Iterator iterator()
    {
        Iterator iterator1;
        if(_methods != null)
            iterator1 = _methods.values().iterator();
        else
            iterator1 = Collections.emptyList().iterator();
        return iterator1;
    }

    public void remove(AnnotatedMethod annotatedmethod)
    {
        if(_methods != null)
            _methods.remove(new MethodKey(annotatedmethod.getAnnotated()));
    }

    public int size()
    {
        int i;
        if(_methods == null)
            i = 0;
        else
            i = _methods.size();
        return i;
    }

    LinkedHashMap _methods;
}
