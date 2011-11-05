package org.acra.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;

@AnnotationDefault(   @ReportsCrashes(
      additionalDropBoxTags = {},
      additionalSharedPreferences = {},
      customReportContent = {},
      deleteUnapprovedReportsOnApplicationStart = true,
      dropboxCollectionMinutes = 5,
      forceCloseDialogAfterToast = false,
      formUri = "",
      formUriBasicAuthLogin = "ACRA-NULL-STRING",
      formUriBasicAuthPassword = "ACRA-NULL-STRING",
      includeDropBoxSystemTags = false,
      logcatArguments = {"-t", "200", "-v", "time"},
      mailTo = "",
      mode = ReportingInteractionMode.SILENT,
      resDialogCommentPrompt = 0,
      resDialogEmailPrompt = 0,
      resDialogIcon = 17301543,
      resDialogOkToast = 0,
      resDialogText = 0,
      resDialogTitle = 0,
      resNotifIcon = 17301624,
      resNotifText = 0,
      resNotifTickerText = 0,
      resNotifTitle = 0,
      resToastText = 0,
      sharedPreferencesMode = 0,
      sharedPreferencesName = "",
      socketTimeout = 3000
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ReportsCrashes {

   String[] additionalDropBoxTags();

   String[] additionalSharedPreferences();

   ReportField[] customReportContent();

   boolean deleteUnapprovedReportsOnApplicationStart();

   int dropboxCollectionMinutes();

   boolean forceCloseDialogAfterToast();

   String formKey();

   String formUri();

   String formUriBasicAuthLogin();

   String formUriBasicAuthPassword();

   boolean includeDropBoxSystemTags();

   String[] logcatArguments();

   String mailTo();

   ReportingInteractionMode mode();

   int resDialogCommentPrompt();

   int resDialogEmailPrompt();

   int resDialogIcon();

   int resDialogOkToast();

   int resDialogText();

   int resDialogTitle();

   int resNotifIcon();

   int resNotifText();

   int resNotifTickerText();

   int resNotifTitle();

   int resToastText();

   int sharedPreferencesMode();

   String sharedPreferencesName();

   int socketTimeout();
}
