// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReportsCrashes.java

package org.acra.annotation;

import java.lang.annotation.Annotation;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;

public interface ReportsCrashes
    extends Annotation
{

    public abstract String[] additionalDropBoxTags();

    public abstract String[] additionalSharedPreferences();

    public abstract ReportField[] customReportContent();

    public abstract boolean deleteUnapprovedReportsOnApplicationStart();

    public abstract int dropboxCollectionMinutes();

    public abstract boolean forceCloseDialogAfterToast();

    public abstract String formKey();

    public abstract String formUri();

    public abstract String formUriBasicAuthLogin();

    public abstract String formUriBasicAuthPassword();

    public abstract boolean includeDropBoxSystemTags();

    public abstract String[] logcatArguments();

    public abstract String mailTo();

    public abstract ReportingInteractionMode mode();

    public abstract int resDialogCommentPrompt();

    public abstract int resDialogEmailPrompt();

    public abstract int resDialogIcon();

    public abstract int resDialogOkToast();

    public abstract int resDialogText();

    public abstract int resDialogTitle();

    public abstract int resNotifIcon();

    public abstract int resNotifText();

    public abstract int resNotifTickerText();

    public abstract int resNotifTitle();

    public abstract int resToastText();

    public abstract int sharedPreferencesMode();

    public abstract String sharedPreferencesName();

    public abstract int socketTimeout();
}
