// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OriginalImageFilter.java

package com.facebook.katana.features.imagefilters;

import android.graphics.Bitmap;

// Referenced classes of package com.facebook.katana.features.imagefilters:
//            ImageFilter

public class OriginalImageFilter extends ImageFilter
{

    public OriginalImageFilter()
    {
        super("Original");
    }

    public Bitmap applyFilter(Bitmap bitmap)
    {
        return bitmap;
    }

    private static final String NAME = "Original";
}
