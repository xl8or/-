// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.map.annotate;

import java.lang.annotation.Annotation;

public interface JsonSerialize
    extends Annotation
{
    public static final class Inclusion extends Enum
    {

        public static Inclusion valueOf(String s)
        {
            return (Inclusion)Enum.valueOf(org/codehaus/jackson/map/annotate/JsonSerialize$Inclusion, s);
        }

        public static Inclusion[] values()
        {
            return (Inclusion[])$VALUES.clone();
        }

        private static final Inclusion $VALUES[];
        public static final Inclusion ALWAYS;
        public static final Inclusion NON_DEFAULT;
        public static final Inclusion NON_NULL;

        static 
        {
            ALWAYS = new Inclusion("ALWAYS", 0);
            NON_NULL = new Inclusion("NON_NULL", 1);
            NON_DEFAULT = new Inclusion("NON_DEFAULT", 2);
            Inclusion ainclusion[] = new Inclusion[3];
            ainclusion[0] = ALWAYS;
            ainclusion[1] = NON_NULL;
            ainclusion[2] = NON_DEFAULT;
            $VALUES = ainclusion;
        }

        private Inclusion(String s, int i)
        {
            super(s, i);
        }
    }


    public abstract Class as();

    public abstract Inclusion include();

    public abstract Class using();
}
