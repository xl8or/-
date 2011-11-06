// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Constants.java

package com.facebook.katana;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.facebook.katana.features.Gatekeeper;

public class Constants
{
    public static class Faceweb
    {

        public static final String UserAgentKey = "FB_FW";
        public static final String Version = "1";

        public Faceweb()
        {
        }
    }

    public static class URL
    {
        public static class Chat
        {

            public static final String CHAT_HOST = "chat.facebook.com";
            public static final int CHAT_PORT = 5222;
            public static final String HIBERNATE_URL_BASE = "https://www.%s/ajax/chat/mobile_ping.php";

            public Chat()
            {
            }
        }


        public static String getApiReadUrl(Context context)
        {
            return getEndpointUrl(context, "https://api-read.%s/restserver.php");
        }

        public static String getApiUrl(Context context)
        {
            return getEndpointUrl(context, "https://api.%s/restserver.php");
        }

        public static String getApiVideoUrl(Context context)
        {
            return getEndpointUrl(context, "https://api-video.%s/restserver.php");
        }

        public static String getChatHibernateUrl(Context context)
        {
            return getEndpointUrl(context, "https://www.%s/ajax/chat/mobile_ping.php");
        }

        public static String getCrashReportUrl(Context context)
        {
            return getEndpointUrl(context, "https://www.%s/mobile/android_crash_logs/");
        }

        public static String getDealsUrl(Context context)
        {
            return getEndpointUrl(context, "https://m.%s/promotion.php");
        }

        public static String getEndpointUrl(Context context, String s)
        {
            Object aobj[] = new Object[1];
            aobj[0] = getSharedPreferences(context).getString("sandbox", "facebook.com");
            return String.format(s, aobj);
        }

        public static String getFacewebRootUrl(Context context)
        {
            Boolean boolean1 = Gatekeeper.get(context, "android_fw_ssl");
            String s;
            Object aobj[];
            if(Boolean.TRUE.equals(boolean1))
                s = "https";
            else
                s = "http";
            aobj = new Object[2];
            aobj[0] = s;
            aobj[1] = getSharedPreferences(context).getString("sandbox", "facebook.com");
            return String.format("%s://m.%s/root.php", aobj);
        }

        protected static String getFeedUrl(Context context)
        {
            return getEndpointUrl(context, "http://www.%s/dialog/feed");
        }

        public static String getGraphUrl(Context context)
        {
            return getEndpointUrl(context, "https://graph.%s/");
        }

        public static String getJapanKddiContactImporterUrl(Context context)
        {
            return getEndpointUrl(context, "http://m.%s/jp/kddi/ml/relay.php?state=i2p&android_app=1");
        }

        public static String getLoggingUrl(Context context)
        {
            return getEndpointUrl(context, LOGGING_URL_FMT);
        }

        public static String getMAuthUrl(Context context)
        {
            return getEndpointUrl(context, "https://m.%s/auth.php");
        }

        public static String getMSuccessUrl(Context context)
        {
            return getEndpointUrl(context, "https://m.%s/root.php");
        }

        protected static String getOAuthUrl(Context context)
        {
            return getEndpointUrl(context, "https://www.%s/dialog/oauth");
        }

        public static String getPrivacySettingsUrl(Context context)
        {
            return getEndpointUrl(context, "https://m.%s/privacy");
        }

        static SharedPreferences getSharedPreferences(Context context)
        {
            if(sharedPreferences == null)
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences;
        }

        public static boolean isFacebookUrl(Uri uri)
        {
            return uri.getHost().endsWith(".facebook.com");
        }

        public static boolean isFacebookUrl(String s)
        {
            return isFacebookUrl(Uri.parse(s));
        }

        private static final String API_HTTPS_URL_FMT = "https://api.%s/restserver.php";
        private static final String API_READ_HTTPS_URL_FMT = "https://api-read.%s/restserver.php";
        private static final String API_VIDEO_HTTP_URL_FMT = "https://api-video.%s/restserver.php";
        private static final String CRASH_REPORT_URL_FMT = "https://www.%s/mobile/android_crash_logs/";
        private static final String DEALS_URL_FMT = "https://m.%s/promotion.php";
        public static final String DEFAULT_SITE = "facebook.com";
        private static final String FACEWEB_ROOT_URL_FMT = "%s://m.%s/root.php";
        private static final String FEED_HTTP_URL_FMT = "http://www.%s/dialog/feed";
        private static final String GRAPH_HTTPS_URL_FMT = "https://graph.%s/";
        private static final String JAPAN_KDDI_CONTACT_IMPORTER_URL_FMT = "http://m.%s/jp/kddi/ml/relay.php?state=i2p&android_app=1";
        private static String LOGGING_URL_FMT = "https://www.%s/impression.php";
        private static final String M_AUTH_URL_FMT = "https://m.%s/auth.php";
        private static final String M_SUCCESS_URL_FMT = "https://m.%s/root.php";
        private static final String OAUTH_HTTPS_URL_FMT = "https://www.%s/dialog/oauth";
        private static final String PRIVACY_SETTINGS_HTTPS_URL_FMT = "https://m.%s/privacy";
        public static final String SITE_MINIMUM_SUFFIX = ".facebook.com";
        private static SharedPreferences sharedPreferences;


        public URL()
        {
        }
    }


    public Constants()
    {
    }

    public static boolean isBetaBuild()
    {
        return IS_BETA_BUILD;
    }

    public static final String ANDROID_CI_ALERT_GATEKEEPER = "android_ci_alert_enabled";
    public static final String ANDROID_CI_KDDI_INTRO_ENABLED = "android_ci_kddi_intro_enabled";
    public static final String ANDROID_CI_LEGAL_BAR_GATEKEEPER = "android_ci_legal_bar";
    public static final String ANDROID_CI_LEGAL_SCREEN_GATEKEEPER = "android_ci_legal_screen";
    public static final String BETA_BUILD_AUTHORIZED_GATEKEEPER = "android_beta";
    public static final String COMPOSER_GATEKEEPER = "meta_composer";
    public static final String DEEP_LINK_GATEKEEPER = "android_deep_links";
    public static final String DEEP_LINK_PROJECT_MAP_SETTING = "deeplinkurimap";
    public static final String DEEP_LINK_PROJECT_NAME = "android_deep_links";
    public static final String EXTRA_CONTINUATION_INTENT = "com.facebook.katana.continuation_intent";
    public static final String FACEWEB_GATEKEEPER = "faceweb_android";
    public static final String FACEWEB_PROJECT_MAP_SETTING = "urimap";
    public static final String FACEWEB_PROJECT_NAME = "android_faceweb";
    public static final String FACEWEB_SSL_GATEKEEPER = "android_fw_ssl";
    private static boolean IS_BETA_BUILD = false;
    public static final int MIN_SECONDS_BETWEEN_GK_SYNC = 3600;
    public static final String NOTIFICATION_UPLOAD_CLEAR = "com.facebook.katana.clear_notification";
    public static final String NOTIFICATION_UPLOAD_ERROR = "com.facebook.katana.upload.notification.error";
    public static final String NOTIFICATION_UPLOAD_OK = "com.facebook.katana.upload.notification.ok";
    public static final String NOTIFICATION_UPLOAD_PENDING = "com.facebook.katana.upload.notification.pending";
    public static final String PRODUCT_NAME = "FB4A";
    public static final String SCHEME_FACEBOOK = "facebook";
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";
    public static final String SOFT_ERROR_CATEGORY = "soft_error_category";
    public static final String SOFT_ERROR_MESSAGE = "soft_error_message";

    static 
    {
        IS_BETA_BUILD = false;
    }
}
