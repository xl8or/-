// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageFilterManager.java

package com.facebook.katana.features.imagefilters;


// Referenced classes of package com.facebook.katana.features.imagefilters:
//            OriginalImageFilter, ImageFilter

public class ImageFilterManager
{
    public static final class Filters extends Enum
    {

        public static Filters valueOf(String s)
        {
            return (Filters)Enum.valueOf(com/facebook/katana/features/imagefilters/ImageFilterManager$Filters, s);
        }

        public static Filters[] values()
        {
            return (Filters[])$VALUES.clone();
        }

        private static final Filters $VALUES[];
        public static final Filters ORIGINAL_FILTER;

        static 
        {
            ORIGINAL_FILTER = new Filters("ORIGINAL_FILTER", 0);
            Filters afilters[] = new Filters[1];
            afilters[0] = ORIGINAL_FILTER;
            $VALUES = afilters;
        }

        private Filters(String s, int i)
        {
            super(s, i);
        }
    }


    public ImageFilterManager()
    {
    }

    public static ImageFilter createFilter(Filters filters)
    {
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$features$imagefilters$ImageFilterManager$Filters[];

            static 
            {
                $SwitchMap$com$facebook$katana$features$imagefilters$ImageFilterManager$Filters = new int[Filters.values().length];
                $SwitchMap$com$facebook$katana$features$imagefilters$ImageFilterManager$Filters[Filters.ORIGINAL_FILTER.ordinal()] = 1;
_L2:
                return;
                NoSuchFieldError nosuchfielderror;
                nosuchfielderror;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.com.facebook.katana.features.imagefilters.ImageFilterManager.Filters[filters.ordinal()];
        JVM INSTR tableswitch 1 1: default 28
    //                   1 32;
           goto _L1 _L2
_L1:
        Object obj = null;
_L4:
        return ((ImageFilter) (obj));
_L2:
        obj = new OriginalImageFilter();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public int getNumFilters()
    {
        return Filters.values().length;
    }
}
