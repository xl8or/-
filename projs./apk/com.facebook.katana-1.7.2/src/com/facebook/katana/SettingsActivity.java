// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SettingsActivity.java

package com.facebook.katana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.*;
import android.webkit.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.webview.MRoot;
import java.util.ArrayList;

// Referenced classes of package com.facebook.katana:
//            SyncContactsChangeActivity

public class SettingsActivity extends PreferenceActivity
{

    public SettingsActivity()
    {
    }

    private PreferenceScreen createPreferenceHierarchy()
    {
        PreferenceScreen preferencescreen = getPreferenceManager().createPreferenceScreen(this);
        PreferenceCategory preferencecategory = new PreferenceCategory(this);
        preferencecategory.setTitle(0x7f0a0193);
        preferencescreen.addPreference(preferencecategory);
        final ListPreference checkIntervalPref = new ListPreference(this);
        ArrayList arraylist = new ArrayList();
        arraylist.add(getString(0x7f0a018c));
        arraylist.add(getString(0x7f0a018d));
        arraylist.add(getString(0x7f0a018e));
        arraylist.add(getString(0x7f0a018f));
        arraylist.add(getString(0x7f0a0190));
        ArrayList arraylist1 = new ArrayList();
        arraylist1.add("30");
        arraylist1.add("60");
        arraylist1.add("120");
        arraylist1.add("240");
        arraylist1.add("0");
        if(FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone())
        {
            arraylist.add("Every minute (beta-only option)");
            arraylist1.add("1");
        }
        final CharSequence timeouts[] = (CharSequence[])arraylist.toArray(new CharSequence[arraylist.size()]);
        final CharSequence values[] = (CharSequence[])arraylist1.toArray(new CharSequence[arraylist1.size()]);
        checkIntervalPref.setEntries(timeouts);
        checkIntervalPref.setEntryValues(values);
        checkIntervalPref.setDefaultValue(values[1]);
        checkIntervalPref.setDialogTitle(0x7f0a019b);
        checkIntervalPref.setKey("polling_interval");
        checkIntervalPref.setTitle(0x7f0a019b);
        checkIntervalPref.setSummary(valueToString(values, timeouts, PreferenceManager.getDefaultSharedPreferences(this).getString("polling_interval", values[1].toString())));
        preferencecategory.addPreference(checkIntervalPref);
        android.preference.Preference.OnPreferenceChangeListener onpreferencechangelistener = new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object obj)
            {
                ((ListPreference)preference).setSummary(valueToString(values, timeouts, (String)obj));
                mPollingSettingChanged = true;
                return true;
            }

            final SettingsActivity this$0;
            final CharSequence val$timeouts[];
            final CharSequence val$values[];

            
            {
                this$0 = SettingsActivity.this;
                values = acharsequence;
                timeouts = acharsequence1;
                super();
            }
        }
;
        checkIntervalPref.setOnPreferenceChangeListener(onpreferencechangelistener);
        CheckBoxPreference checkboxpreference = new CheckBoxPreference(this);
        checkboxpreference.setKey("notif");
        checkboxpreference.setTitle(0x7f0a0198);
        int i;
        PreferenceCategory preferencecategory1;
        final CheckBoxPreference messages;
        final CheckBoxPreference friendRequests;
        final CheckBoxPreference eventInvites;
        final CheckBoxPreference vibratePref;
        final CheckBoxPreference useLedPref;
        final RingtonePreference ringtonePref;
        PreferenceCategory preferencecategory2;
        PreferenceScreen preferencescreen1;
        Intent intent;
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notif", true))
            i = 0x7f0a0196;
        else
            i = 0x7f0a0197;
        checkboxpreference.setSummary(i);
        checkboxpreference.setDefaultValue(Boolean.valueOf(true));
        preferencecategory.addPreference(checkboxpreference);
        preferencecategory1 = new PreferenceCategory(this);
        preferencecategory1.setTitle(0x7f0a0195);
        preferencescreen.addPreference(preferencecategory1);
        messages = new CheckBoxPreference(this);
        messages.setKey("notif_messages");
        messages.setTitle(0x7f0a0194);
        messages.setDefaultValue(Boolean.valueOf(true));
        preferencecategory1.addPreference(messages);
        friendRequests = new CheckBoxPreference(this);
        friendRequests.setKey("notif_friend_requests");
        friendRequests.setTitle(0x7f0a0192);
        friendRequests.setDefaultValue(Boolean.valueOf(true));
        preferencecategory1.addPreference(friendRequests);
        eventInvites = new CheckBoxPreference(this);
        eventInvites.setKey("notif_event_invites");
        eventInvites.setTitle(0x7f0a0191);
        eventInvites.setDefaultValue(Boolean.valueOf(true));
        preferencecategory1.addPreference(eventInvites);
        vibratePref = new CheckBoxPreference(this);
        vibratePref.setKey("vibrate");
        vibratePref.setTitle(0x7f0a01a5);
        vibratePref.setSummary(0x7f0a01a4);
        preferencecategory1.addPreference(vibratePref);
        useLedPref = new CheckBoxPreference(this);
        useLedPref.setKey("use_led");
        useLedPref.setTitle(0x7f0a01a3);
        useLedPref.setSummary(0x7f0a01a2);
        preferencecategory1.addPreference(useLedPref);
        ringtonePref = new RingtonePreference(this);
        ringtonePref.setRingtoneType(2);
        ringtonePref.setKey("ringtone");
        ringtonePref.setShowSilent(true);
        ringtonePref.setShowDefault(true);
        ringtonePref.setTitle(0x7f0a019f);
        ringtonePref.setSummary(0x7f0a019e);
        preferencecategory1.addPreference(ringtonePref);
        checkboxpreference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference, Object obj)
            {
                boolean flag = ((Boolean)obj).booleanValue();
                int j;
                if(flag)
                    j = 0x7f0a0196;
                else
                    j = 0x7f0a0197;
                preference.setSummary(j);
                checkIntervalPref.setEnabled(flag);
                vibratePref.setEnabled(flag);
                useLedPref.setEnabled(flag);
                ringtonePref.setEnabled(flag);
                messages.setEnabled(flag);
                friendRequests.setEnabled(flag);
                eventInvites.setEnabled(flag);
                return true;
            }

            final SettingsActivity this$0;
            final ListPreference val$checkIntervalPref;
            final CheckBoxPreference val$eventInvites;
            final CheckBoxPreference val$friendRequests;
            final CheckBoxPreference val$messages;
            final RingtonePreference val$ringtonePref;
            final CheckBoxPreference val$useLedPref;
            final CheckBoxPreference val$vibratePref;

            
            {
                this$0 = SettingsActivity.this;
                checkIntervalPref = listpreference;
                vibratePref = checkboxpreference;
                useLedPref = checkboxpreference1;
                ringtonePref = ringtonepreference;
                messages = checkboxpreference2;
                friendRequests = checkboxpreference3;
                eventInvites = checkboxpreference4;
                super();
            }
        }
);
        if(!checkboxpreference.isChecked())
        {
            checkIntervalPref.setEnabled(false);
            vibratePref.setEnabled(false);
            useLedPref.setEnabled(false);
            ringtonePref.setEnabled(false);
            messages.setEnabled(false);
            friendRequests.setEnabled(false);
            eventInvites.setEnabled(false);
        }
        preferencecategory2 = new PreferenceCategory(this);
        preferencecategory2.setTitle(0x7f0a0199);
        preferencescreen.addPreference(preferencecategory2);
        if(PlatformUtils.platformStorageSupported(this))
        {
            AppSession appsession = AppSession.getActiveSession(this, false);
            if(appsession != null)
            {
                PreferenceScreen preferencescreen2 = getPreferenceManager().createPreferenceScreen(this);
                preferencescreen2.setKey("sync_contacts");
                preferencescreen2.setTitle(0x7f0a01a0);
                updateSyncContactsSummary(appsession, preferencescreen2);
                Intent intent1 = new Intent(this, com/facebook/katana/SyncContactsChangeActivity);
                preferencescreen2.setIntent(intent1);
                preferencecategory2.addPreference(preferencescreen2);
            }
        }
        preferencescreen1 = getPreferenceManager().createPreferenceScreen(this);
        preferencescreen1.setTitle(0x7f0a018b);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(AuthDeepLinkMethod.getDeepLinkUrl(this, Constants.URL.getPrivacySettingsUrl(this))));
        preferencescreen1.setIntent(intent);
        preferencecategory2.addPreference(preferencescreen1);
        if(FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone())
        {
            EditTextPreference edittextpreference = new EditTextPreference(this);
            edittextpreference.setKey("sandbox");
            edittextpreference.setDefaultValue("facebook.com");
            edittextpreference.setTitle("Sandbox");
            edittextpreference.setSummary("e.g., dev.facebook.com, facebook.com");
            edittextpreference.setDialogTitle("Sandbox");
            android.preference.Preference.OnPreferenceChangeListener onpreferencechangelistener1 = new android.preference.Preference.OnPreferenceChangeListener() {

                public boolean onPreferenceChange(Preference preference, Object obj)
                {
                    if(!(obj instanceof String) || !(preference instanceof EditTextPreference)) goto _L2; else goto _L1
_L1:
                    String s;
                    EditTextPreference edittextpreference1;
                    String s1;
                    s = (String)obj;
                    edittextpreference1 = (EditTextPreference)preference;
                    s1 = s.trim();
                    if(s1.equals(s)) goto _L2; else goto _L3
_L3:
                    boolean flag;
                    edittextpreference1.setText(s1);
                    flag = false;
_L5:
                    return flag;
_L2:
                    flag = true;
                    if(true) goto _L5; else goto _L4
_L4:
                }

                final SettingsActivity this$0;

            
            {
                this$0 = SettingsActivity.this;
                super();
            }
            }
;
            edittextpreference.setOnPreferenceChangeListener(onpreferencechangelistener1);
            preferencecategory2.addPreference(edittextpreference);
            CheckBoxPreference checkboxpreference1 = new CheckBoxPreference(this);
            checkboxpreference1.setKey("check_certs");
            checkboxpreference1.setTitle("Check SSL Certificates");
            checkboxpreference1.setSummary("Should be off when using your sandbox.");
            android.preference.Preference.OnPreferenceChangeListener onpreferencechangelistener2 = new android.preference.Preference.OnPreferenceChangeListener() {

                public boolean onPreferenceChange(Preference preference, Object obj)
                {
                    HttpOperation.initSchemeRegistry(((Boolean)obj).booleanValue());
                    return true;
                }

                final SettingsActivity this$0;

            
            {
                this$0 = SettingsActivity.this;
                super();
            }
            }
;
            checkboxpreference1.setOnPreferenceChangeListener(onpreferencechangelistener2);
            checkboxpreference1.setDefaultValue(Boolean.valueOf(true));
            preferencecategory2.addPreference(checkboxpreference1);
            ListPreference listpreference = new ListPreference(this);
            CharSequence acharsequence[] = new CharSequence[2];
            acharsequence[0] = "Clear cookies";
            acharsequence[1] = "Clear cache";
            listpreference.setEntries(acharsequence);
            CharSequence acharsequence1[] = new CharSequence[2];
            acharsequence1[0] = "cookies";
            acharsequence1[1] = "cache";
            listpreference.setEntryValues(acharsequence1);
            listpreference.setDialogTitle("Reset webviews");
            listpreference.setTitle("Webview control");
            listpreference.setSummary("Clears webview cookies or caches");
            listpreference.setPositiveButtonText("Clear");
            listpreference.setNegativeButtonText("Cancel");
            listpreference.setKey("reset");
            android.preference.Preference.OnPreferenceChangeListener onpreferencechangelistener3 = new android.preference.Preference.OnPreferenceChangeListener() {

                public boolean onPreferenceChange(Preference preference, Object obj)
                {
                    if(!obj.equals("cookies")) goto _L2; else goto _L1
_L1:
                    CookieSyncManager.createInstance(SettingsActivity.this);
                    CookieManager.getInstance().removeAllCookie();
_L4:
                    return false;
_L2:
                    if(obj.equals("cache"))
                    {
                        (new WebView(SettingsActivity.this)).clearCache(true);
                        MRoot.reset(SettingsActivity.this);
                    }
                    if(true) goto _L4; else goto _L3
_L3:
                }

                final SettingsActivity this$0;

            
            {
                this$0 = SettingsActivity.this;
                super();
            }
            }
;
            listpreference.setOnPreferenceChangeListener(onpreferencechangelistener3);
            preferencecategory2.addPreference(listpreference);
        }
        return preferencescreen;
    }

    private void updateSyncContactsSummary(AppSession appsession, Preference preference)
    {
        if(appsession != null)
        {
            String s = appsession.getSessionInfo().username;
            boolean flag = FacebookAuthenticationService.isSyncEnabled(this, s);
            boolean flag1 = FacebookAuthenticationService.doesShowUngroupedContacts(this, s);
            if(flag)
            {
                if(flag1)
                    preference.setSummary(0x7f0a01e3);
                else
                    preference.setSummary(0x7f0a01e5);
            } else
            {
                preference.setSummary(0x7f0a01df);
            }
        }
    }

    private String valueToString(CharSequence acharsequence[], CharSequence acharsequence1[], String s)
    {
        int i = 0;
_L3:
        if(i >= acharsequence.length)
            break MISSING_BLOCK_LABEL_42;
        if(!acharsequence[i].toString().equals(s)) goto _L2; else goto _L1
_L1:
        String s1 = acharsequence1[i].toString();
_L4:
        return s1;
_L2:
        i++;
          goto _L3
        s1 = null;
          goto _L4
    }

    public void onBackPressed()
    {
        if(mPollingSettingChanged)
            setResult(-1);
        finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setPreferenceScreen(createPreferenceHierarchy());
        mPollingSettingChanged = false;
    }

    public void onResume()
    {
        super.onResume();
        Preference preference = findPreference("sync_contacts");
        if(preference != null)
            updateSyncContactsSummary(AppSession.getActiveSession(this, false), preference);
    }

    public static final String PREF_NOTIFICATIONS = "notif";
    public static final String PREF_NOTIF_EVENT_INVITES = "notif_event_invites";
    public static final String PREF_NOTIF_FRIEND_REQUESTS = "notif_friend_requests";
    public static final String PREF_NOTIF_MESSAGES = "notif_messages";
    public static final String PREF_POLLING_INTERVAL = "polling_interval";
    public static final String PREF_RELOAD_PAGE = "reload_page";
    public static final String PREF_RESET = "reset";
    public static final String PREF_RINGTONE = "ringtone";
    public static final String PREF_SANDBOX = "sandbox";
    public static final String PREF_SSL_CHECK_CERTS = "check_certs";
    public static final String PREF_SYNC_CONTACTS = "sync_contacts";
    public static final String PREF_USE_LED = "use_led";
    public static final String PREF_VIBRATE = "vibrate";
    protected boolean mPollingSettingChanged;

}
