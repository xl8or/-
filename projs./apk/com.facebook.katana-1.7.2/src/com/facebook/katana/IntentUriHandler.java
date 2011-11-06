// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntentUriHandler.java

package com.facebook.katana;

import android.app.Activity;
import android.app.Application;
import android.content.*;
import android.content.pm.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import com.facebook.katana.activity.chat.BuddyListActivity;
import com.facebook.katana.activity.events.EventsActivity;
import com.facebook.katana.activity.faceweb.FacewebChromeActivity;
import com.facebook.katana.activity.feedback.FeedbackActivity;
import com.facebook.katana.activity.media.AlbumsActivity;
import com.facebook.katana.activity.media.PhotosActivity;
import com.facebook.katana.activity.media.UploadPhotoActivity;
import com.facebook.katana.activity.media.ViewPhotoActivity;
import com.facebook.katana.activity.media.ViewVideoActivity;
import com.facebook.katana.activity.messages.MailboxTabHostActivity;
import com.facebook.katana.activity.messages.MessageComposeActivity;
import com.facebook.katana.activity.places.FriendCheckinsActivity;
import com.facebook.katana.activity.places.StubPlacesActivity;
import com.facebook.katana.activity.profilelist.GroupListActivity;
import com.facebook.katana.activity.stream.StreamActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.features.DeepLinkUriMap;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.features.faceweb.FacewebUriMap;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.net.URLDecoder;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            HomeActivity, ProfileTabHostActivity, UsersTabHostActivity, LoginActivity

public class IntentUriHandler extends Activity
{
    private static class DeepLinkIntentUriHandler
        implements UriHandler
    {

        public Intent handle(Context context, Bundle bundle)
        {
            String s = uriTemplate;
            for(Iterator iterator = bundle.keySet().iterator(); iterator.hasNext();)
            {
                String s1 = (String)iterator.next();
                s = s.replaceAll((new StringBuilder()).append("<").append(s1).append(">").toString(), Utils.getStringValue(bundle, s1));
            }

            return IntentUriHandler.getIntentForUri(context, s);
        }

        public final String uriTemplate;

        public DeepLinkIntentUriHandler(String s)
        {
            uriTemplate = s;
        }
    }

    private static class NativeUriHandler
        implements UriHandler
    {

        public Intent handle(Context context, Bundle bundle)
        {
            Intent intent = new Intent(context, mTarget);
            if(mOtherExtras != null)
                intent.putExtras(mOtherExtras);
            if(bundle != null)
                intent.putExtras(bundle);
            intent.setAction("android.intent.action.VIEW");
            return intent;
        }

        private final Bundle mOtherExtras;
        private final Class mTarget;

        public NativeUriHandler(Class class1, Bundle bundle)
        {
            mTarget = class1;
            mOtherExtras = bundle;
        }
    }

    public static interface UriHandler
    {

        public abstract Intent handle(Context context, Bundle bundle);
    }


    public IntentUriHandler()
    {
    }

    private void bypassNativeNotificationHandler(Uri uri)
    {
        AppSession appsession = AppSession.getActiveSession(this, false);
        if(appsession != null && appsession.getSessionInfo() != null && !deepLinkedRecently(uri))
        {
            AuthDeepLinkMethod authdeeplinkmethod = new AuthDeepLinkMethod(this, System.currentTimeMillis() / 1000L, appsession.getSessionInfo().userId, uri.toString(), null, appsession.getSessionInfo().sessionKey, appsession.getSessionInfo().sessionSecret);
            authdeeplinkmethod.start();
            uri = Uri.parse(authdeeplinkmethod.getUrl());
        }
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        PackageManager packagemanager = getPackageManager();
        ApplicationInfo applicationinfo = getApplication().getApplicationInfo();
        List list = packagemanager.queryIntentActivities(intent, 0x10000);
        android.content.pm.ApplicationInfo.DisplayNameComparator displaynamecomparator = new android.content.pm.ApplicationInfo.DisplayNameComparator(packagemanager);
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ResolveInfo resolveinfo = (ResolveInfo)iterator.next();
            if(displaynamecomparator.compare(resolveinfo.activityInfo.applicationInfo, applicationinfo) == 0)
                continue;
            ComponentName componentname = new ComponentName(resolveinfo.activityInfo.applicationInfo.packageName, resolveinfo.activityInfo.name);
            intent.setComponent(componentname);
            startActivity(intent);
            break;
        } while(true);
    }

    /**
     * @deprecated Method deepLinkedRecently is deprecated
     */

    private static boolean deepLinkedRecently(Uri uri)
    {
        com/facebook/katana/IntentUriHandler;
        JVM INSTR monitorenter ;
        boolean flag = false;
        long l;
        ListIterator listiterator;
        l = SystemClock.elapsedRealtime();
        listiterator = recentNotificationUrls.listIterator();
_L2:
        Tuple tuple;
        do
        {
            if(!listiterator.hasNext())
                break;
            tuple = (Tuple)listiterator.next();
            if(0x493e0L + ((Long)tuple.d0).longValue() >= l)
                continue; /* Loop/switch isn't completed */
            listiterator.remove();
        } while(true);
        break MISSING_BLOCK_LABEL_104;
        Exception exception;
        exception;
        throw exception;
        if(!((Uri)tuple.d1).equals(normalizeUri(uri))) goto _L2; else goto _L1
_L1:
        flag = true;
        listiterator.remove();
        recentNotificationUrls.add(new Tuple(Long.valueOf(l), normalizeUri(uri)));
        com/facebook/katana/IntentUriHandler;
        JVM INSTR monitorexit ;
        return flag;
    }

    public static Intent getIntentForUri(Context context, String s)
    {
        com.facebook.katana.util.UriTemplateMap.UriMatch urimatch = null;
        if(Integer.parseInt(android.os.Build.VERSION.SDK) >= 7)
        {
            Boolean boolean2 = Gatekeeper.get(context, "faceweb_android");
            if(boolean2 != null && boolean2.booleanValue())
            {
                UriTemplateMap uritemplatemap1 = FacewebUriMap.get(context);
                if(uritemplatemap1 != null)
                    urimatch = uritemplatemap1.get(s);
            }
        }
        if(urimatch == null)
        {
            Boolean boolean1 = Gatekeeper.get(context, "android_deep_links");
            if(boolean1 != null && boolean1.booleanValue())
            {
                UriTemplateMap uritemplatemap = DeepLinkUriMap.get(context);
                if(uritemplatemap != null)
                    urimatch = uritemplatemap.get(s);
            }
        }
        if(urimatch == null)
            urimatch = nativeUriTemplateMap.get(s);
        if(urimatch == null)
            urimatch = deepLinkIntentsMap.get(s);
        if(urimatch == null) goto _L2; else goto _L1
_L1:
        Intent intent1 = ((UriHandler)urimatch.value).handle(context, urimatch.parameters);
        Intent intent = intent1;
_L4:
        return intent;
_L2:
        intent = null;
        continue; /* Loop/switch isn't completed */
        com.facebook.katana.util.UriTemplateMap.InvalidUriException invaliduriexception;
        invaliduriexception;
        intent = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static boolean handleUri(Context context, String s)
    {
        Intent intent = getIntentForUri(context, s);
        boolean flag;
        if(intent != null)
        {
            context.startActivity(intent);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    private boolean isNotificationUri(Uri uri)
    {
        String s = uri.getScheme();
        if(s == null || !s.equals("http") && !s.equals("https")) goto _L2; else goto _L1
_L1:
        String s1 = uri.getHost();
        if(s1 == null || !s1.toLowerCase().endsWith(".facebook.com")) goto _L2; else goto _L3
_L3:
        String s2 = uri.getPath();
        if(s2 == null || !s2.equals("/n/")) goto _L2; else goto _L4
_L4:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static void mapDeepLinkIntent(String s, String s1)
    {
        deepLinkIntentsMap.put(s, new DeepLinkIntentUriHandler(s1));
_L1:
        return;
        com.facebook.katana.util.UriTemplateMap.InvalidUriTemplateException invaliduritemplateexception;
        invaliduritemplateexception;
        Log.e("IntentUriHandler", (new StringBuilder()).append("Invalid uri template: ").append(s).toString(), invaliduritemplateexception);
          goto _L1
    }

    private static void mapNative(String s, Class class1)
    {
        mapNative(s, class1, null);
    }

    private static void mapNative(String s, Class class1, Bundle bundle)
    {
        nativeUriTemplateMap.put(s, new NativeUriHandler(class1, bundle));
_L1:
        return;
        com.facebook.katana.util.UriTemplateMap.InvalidUriTemplateException invaliduritemplateexception;
        invaliduritemplateexception;
        Log.e("IntentUriHandler", (new StringBuilder()).append("Invalid uri template: ").append(s).toString(), invaliduritemplateexception);
          goto _L1
    }

    private static Uri normalizeUri(Uri uri)
    {
        Uri uri1;
        if("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))
            uri1 = uri.buildUpon().scheme("http").build();
        else
            uri1 = uri;
        return uri1;
    }

    private Uri transformedNotificationUri(Uri uri)
    {
        uri1 = null;
_L2:
        return uri1;
        android.net.Uri.Builder builder = null;
        String s = uri.getEncodedQuery();
        Uri uri1;
        if(s != null)
        {
            builder = new android.net.Uri.Builder();
            builder.scheme("fbn");
            String as[] = s.split("&");
            int i = as.length;
            int j = 0;
            while(j < i) 
            {
                String s1 = as[j];
                int k = s1.indexOf('=');
                int l = s1.lastIndexOf('=');
                if(k == -1)
                {
                    builder.appendEncodedPath(URLDecoder.decode(s1));
                } else
                {
label0:
                    {
                        if(k != l)
                            break label0;
                        builder.appendQueryParameter(URLDecoder.decode(s1.substring(0, k)), URLDecoder.decode(s1.substring(k + 1)));
                    }
                }
                j++;
            }
        }
        if(builder != null)
            uri1 = builder.build();
        else
            uri1 = uri;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void onCreate(Bundle bundle)
    {
        Intent intent;
        super.onCreate(bundle);
        intent = getIntent();
        if(AppSession.getActiveSession(this, true) != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this, intent);
_L4:
        finish();
        return;
_L2:
        Uri uri = intent.getData();
        if(!handleUri(this, uri.toString()))
            if(uri.getScheme().equals("facebook"))
                handleUri(this, "fb://feed");
            else
            if(isNotificationUri(uri) && !handleUri(this, transformedNotificationUri(uri).toString()))
            {
                Utils.reportSoftError("Unhandled Notification Link", (new StringBuilder()).append("URI not found in DeepLinkURIMap: ").append(uri).toString());
                bypassNativeNotificationHandler(uri);
            }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final int NOTIFICATION_HISTORY_EXPIRY_MS = 0x493e0;
    public static final String QUERY_KEY_ALBUM = "album";
    public static final String QUERY_KEY_PHOTO = "photo";
    public static final String QUERY_KEY_USER = "user";
    public static final String TAG = "IntentUriHandler";
    private static UriTemplateMap deepLinkIntentsMap = new UriTemplateMap();
    private static UriTemplateMap nativeUriTemplateMap = new UriTemplateMap();
    private static List recentNotificationUrls = new LinkedList();

    static 
    {
        Object aobj[] = new Object[1];
        aobj[0] = "mobile_page";
        mapNative(String.format("fb://faceweb/f?href={%s}", aobj), com/facebook/katana/activity/faceweb/FacewebChromeActivity);
        mapNative("fb://root", com/facebook/katana/HomeActivity);
        mapNative("fb://feed", com/facebook/katana/activity/stream/StreamActivity);
        mapNative("fb://feed/{#user_id}", null);
        mapNative("fb://profile", com/facebook/katana/ProfileTabHostActivity);
        Object aobj1[] = new Object[1];
        aobj1[0] = "extra_user_id";
        String s = String.format("fb://profile/{#%s}", aobj1);
        Object aobj2[] = new Object[2];
        aobj2[0] = "extra_user_type";
        aobj2[1] = Integer.valueOf(0);
        mapNative(s, com/facebook/katana/ProfileTabHostActivity, Utils.makeBundle(aobj2));
        Object aobj3[] = new Object[1];
        aobj3[0] = "extra_user_id";
        String s1 = String.format("fb://profile/{#%s}/wall/inner", aobj3);
        Object aobj4[] = new Object[4];
        aobj4[0] = "within_tab";
        aobj4[1] = Boolean.valueOf(true);
        aobj4[2] = "extra_type";
        aobj4[3] = FacebookStreamType.PROFILE_WALL_STREAM.toString();
        mapNative(s1, com/facebook/katana/activity/stream/StreamActivity, Utils.makeBundle(aobj4));
        Object aobj5[] = new Object[1];
        aobj5[0] = "extra_user_id";
        String s2 = String.format("fb://page/{#%s}", aobj5);
        Object aobj6[] = new Object[2];
        aobj6[0] = "extra_user_type";
        aobj6[1] = Integer.valueOf(1);
        mapNative(s2, com/facebook/katana/ProfileTabHostActivity, Utils.makeBundle(aobj6));
        Object aobj7[] = new Object[1];
        aobj7[0] = "extra_user_id";
        String s3 = String.format("fb://group/{#%s}", aobj7);
        Object aobj8[] = new Object[2];
        aobj8[0] = "extra_user_type";
        aobj8[1] = Integer.valueOf(3);
        mapNative(s3, com/facebook/katana/ProfileTabHostActivity, Utils.makeBundle(aobj8));
        Object aobj9[] = new Object[1];
        aobj9[0] = "extra_user_id";
        String s4 = String.format("fb://group/{#%s}/wall/inner", aobj9);
        Object aobj10[] = new Object[4];
        aobj10[0] = "within_tab";
        aobj10[1] = Boolean.valueOf(true);
        aobj10[2] = "extra_type";
        aobj10[3] = FacebookStreamType.GROUP_WALL_STREAM.toString();
        mapNative(s4, com/facebook/katana/activity/stream/StreamActivity, Utils.makeBundle(aobj10));
        Object aobj11[] = new Object[1];
        aobj11[0] = "extra_user_id";
        mapNative(String.format("fb://place/fw?pid={#%s}", aobj11), com/facebook/katana/activity/places/StubPlacesActivity);
        Object aobj12[] = new Object[2];
        aobj12[0] = "extra_user_id";
        aobj12[1] = "tab";
        String s5 = String.format("fb://profile/{#%s}/{%s}", aobj12);
        Object aobj13[] = new Object[2];
        aobj13[0] = "extra_user_type";
        aobj13[1] = Integer.valueOf(0);
        mapNative(s5, com/facebook/katana/ProfileTabHostActivity, Utils.makeBundle(aobj13));
        mapNative("fb://profile/{#user_id}/mutualfriends", null);
        mapNative("fb://profile/{#user_id}/friends", null);
        mapNative("fb://profile/{#user_id}/fans", null);
        Object aobj14[] = new Object[1];
        aobj14[0] = "extra_user_id";
        String s6 = String.format("fb://event/{#%s}", aobj14);
        Object aobj15[] = new Object[2];
        aobj15[0] = "extra_user_type";
        aobj15[1] = Integer.valueOf(4);
        mapNative(s6, com/facebook/katana/ProfileTabHostActivity, Utils.makeBundle(aobj15));
        Object aobj16[] = new Object[1];
        aobj16[0] = "extra_user_id";
        String s7 = String.format("fb://event/{#%s}/wall/inner", aobj16);
        Object aobj17[] = new Object[4];
        aobj17[0] = "within_tab";
        aobj17[1] = Boolean.valueOf(true);
        aobj17[2] = "extra_type";
        aobj17[3] = FacebookStreamType.EVENT_WALL_STREAM.toString();
        mapNative(s7, com/facebook/katana/activity/stream/StreamActivity, Utils.makeBundle(aobj17));
        Object aobj18[] = new Object[2];
        aobj18[0] = "com.facebook.katana.DefaultTab";
        aobj18[1] = "friends";
        mapNative("fb://search", com/facebook/katana/UsersTabHostActivity, Utils.makeBundle(aobj18));
        mapNative("fb://friends", com/facebook/katana/UsersTabHostActivity);
        mapNative("fb://pages", null);
        mapNative("fb://messaging", com/facebook/katana/activity/messages/MailboxTabHostActivity);
        mapNative("fb://messaging/{#user_id}", null);
        Object aobj19[] = new Object[1];
        aobj19[0] = "extra_user_id";
        mapNative(String.format("fb://messaging/compose/{#%s}", aobj19), com/facebook/katana/activity/messages/MessageComposeActivity, null);
        mapNative("fb://online", com/facebook/katana/activity/chat/BuddyListActivity);
        Object aobj20[] = new Object[2];
        aobj20[0] = "com.facebook.katana.DefaultTab";
        aobj20[1] = "requests";
        mapNative("fb://requests", com/facebook/katana/UsersTabHostActivity, Utils.makeBundle(aobj20));
        mapNative("fb://events", com/facebook/katana/activity/events/EventsActivity);
        mapNative("fb://places", com/facebook/katana/activity/places/FriendCheckinsActivity);
        mapNative("fb://birthdays", null);
        mapNative("fb://notes", null);
        mapNative("fb://places", com/facebook/katana/activity/places/FriendCheckinsActivity);
        mapNative("fb://groups", com/facebook/katana/activity/profilelist/GroupListActivity);
        Object aobj21[] = new Object[2];
        aobj21[0] = HomeActivity.EXTRA_SHOW_NOTIFICATIONS;
        aobj21[1] = Boolean.valueOf(true);
        mapNative("fb://notifications", com/facebook/katana/HomeActivity, Utils.makeBundle(aobj21));
        mapNative("fb://albums", com/facebook/katana/activity/media/AlbumsActivity);
        mapNative("fb://online", com/facebook/katana/activity/chat/BuddyListActivity);
        Object aobj22[] = new Object[2];
        aobj22[0] = "album";
        aobj22[1] = "owner";
        mapNative(String.format("fb://album/{%s}?owner={#%s}", aobj22), com/facebook/katana/activity/media/PhotosActivity);
        Object aobj23[] = new Object[5];
        aobj23[0] = "owner";
        aobj23[1] = "album";
        aobj23[2] = "photo";
        aobj23[3] = "action";
        aobj23[4] = "android.intent.action.VIEW";
        mapNative(String.format("fb://photo/{%1$s}/{%2$s}/{%3$s}?action={%4$s %5$s}", aobj23), com/facebook/katana/activity/media/ViewPhotoActivity);
        Object aobj24[] = new Object[4];
        aobj24[0] = "owner";
        aobj24[1] = "photo";
        aobj24[2] = "action";
        aobj24[3] = "android.intent.action.VIEW";
        mapNative(String.format("fb://photo/{%1$s}/{%2$s}?action={%3$s %4$s}", aobj24), com/facebook/katana/activity/media/ViewPhotoActivity);
        Object aobj25[] = new Object[1];
        aobj25[0] = "photo_uri";
        mapNative(String.format("fb://upload/photo?uri={%s}", aobj25), com/facebook/katana/activity/media/UploadPhotoActivity);
        mapNative("fb://video/?href={href}", com/facebook/katana/activity/media/ViewVideoActivity);
        Object aobj26[] = new Object[2];
        aobj26[0] = "extra_post_id";
        aobj26[1] = "extra_uid";
        mapNative(String.format("fb://post/{%s}?owner={#%s}", aobj26), com/facebook/katana/activity/feedback/FeedbackActivity);
        mapDeepLinkIntent("facebook:/chat", "fb://online");
        mapDeepLinkIntent("facebook:/events", "fb://events");
        mapDeepLinkIntent("facebook:/friends", "fb://friends");
        mapDeepLinkIntent("facebook:/inbox", "fb://messaging");
        mapDeepLinkIntent("facebook:/newsfeed", "fb://feed");
        mapDeepLinkIntent("facebook:/places", "fb://places");
        mapDeepLinkIntent("facebook:/requests", "fb://requests");
        mapDeepLinkIntent("facebook:/wall?user={user}", "fb://profile/<user>/wall");
        mapDeepLinkIntent("facebook:/info?user={user}", "fb://profile/<user>/info");
        mapDeepLinkIntent("facebook:/notifications", "fb://notifications");
        mapDeepLinkIntent("facebook:/photos?user={uid}&album={aid}&photo={pid}", "fb://photo/<uid>/<aid>/<pid>");
        mapDeepLinkIntent("facebook:/photos?user={uid}&photo={pid}", "fb://photo/<uid>/<pid>");
        mapDeepLinkIntent("facebook:/photos?user={uid}&album={aid}", "fb://album/<aid>?owner=<uid>");
        mapDeepLinkIntent("facebook:/photos?user={uid}", "fb://profile/<uid>/photos");
        mapDeepLinkIntent("facebook:/photos", "fb://albums");
        mapDeepLinkIntent("facebook:/feedback?user={uid}&post={post_id}", "fb://post/<post_id>?owner=<uid>");
    }
}
