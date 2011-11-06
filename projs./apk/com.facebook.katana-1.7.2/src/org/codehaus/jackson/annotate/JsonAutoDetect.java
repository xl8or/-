// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.annotate;

import java.lang.annotation.Annotation;

// Referenced classes of package org.codehaus.jackson.annotate:
//            JsonMethod

public interface JsonAutoDetect
    extends Annotation
{

    public abstract JsonMethod[] value();
}
