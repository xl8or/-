// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShareLinkActivity.java

package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.activity.PlatformDialogActivity;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import com.facebook.katana.version.SDK8;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShareLinkActivity extends PlatformDialogActivity
{

    public ShareLinkActivity()
    {
    }

    private String getUrl(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            Pattern pattern;
            Matcher matcher;
            if(android.os.Build.VERSION.SDK_INT < 8)
                pattern = WEB_URL;
            else
                pattern = SDK8.getWebUrlPattern();
            matcher = pattern.matcher(s);
            if(matcher.find())
                s1 = matcher.group();
            else
                s1 = null;
        }
        return s1;
    }

    protected void setupDialogURL()
    {
        Bundle bundle = new Bundle();
        Bundle bundle1 = getIntent().getExtras();
        String s;
        String s1;
        if(bundle1 == null)
            s = null;
        else
            s = bundle1.getString("android.intent.extra.TEXT");
        s1 = getUrl(s);
        if(s1 != null && s1.length() > 0)
        {
            bundle.putString("link", s1);
            Log.d("ShareLinkActivity", (new StringBuilder()).append("URL: ").append(s1).toString());
        }
        bundle.putString("message", s);
        bundle.putString("app_id", Long.toString(0xa67c8e50L));
        bundle.putString("display", "touch");
        bundle.putString("redirect_uri", "fbconnect://success");
        mUrl = (new StringBuilder()).append(Constants.URL.getFeedUrl(this)).append("?").append(URLQueryBuilder.buildQueryString(bundle)).toString();
        Log.d("ShareLinkActivity", mUrl);
    }

    public static final String GOOD_IRI_CHAR = "a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    public static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL = "(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)|y[etu]|z[amw]))";
    public static final Pattern WEB_URL = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)");

}
