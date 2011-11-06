// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReportingInteractionMode.java

package org.acra;


public final class ReportingInteractionMode extends Enum
{

    private ReportingInteractionMode(String s, int i)
    {
        super(s, i);
    }

    public static ReportingInteractionMode valueOf(String s)
    {
        return (ReportingInteractionMode)Enum.valueOf(org/acra/ReportingInteractionMode, s);
    }

    public static ReportingInteractionMode[] values()
    {
        return (ReportingInteractionMode[])$VALUES.clone();
    }

    private static final ReportingInteractionMode $VALUES[];
    public static final ReportingInteractionMode NOTIFICATION;
    public static final ReportingInteractionMode SILENT;
    public static final ReportingInteractionMode TOAST;

    static 
    {
        SILENT = new ReportingInteractionMode("SILENT", 0);
        NOTIFICATION = new ReportingInteractionMode("NOTIFICATION", 1);
        TOAST = new ReportingInteractionMode("TOAST", 2);
        ReportingInteractionMode areportinginteractionmode[] = new ReportingInteractionMode[3];
        areportinginteractionmode[0] = SILENT;
        areportinginteractionmode[1] = NOTIFICATION;
        areportinginteractionmode[2] = TOAST;
        $VALUES = areportinginteractionmode;
    }
}
