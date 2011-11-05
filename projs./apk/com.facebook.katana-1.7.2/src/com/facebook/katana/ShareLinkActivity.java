package com.facebook.katana;

import android.os.Bundle;
import android.os.Build.VERSION;
import com.facebook.katana.Constants;
import com.facebook.katana.activity.PlatformDialogActivity;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import com.facebook.katana.version.SDK8;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShareLinkActivity extends PlatformDialogActivity {

   public static final String GOOD_IRI_CHAR = "a-zA-Z0-9 -퟿豈-﷏ﷰ-￯";
   public static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL = "(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)|y[etu]|z[amw]))";
   public static final Pattern WEB_URL = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯][a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnprwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agksyz]|v[aceginu]|w[fs]|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9 -퟿豈-﷏ﷰ-￯\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)");


   public ShareLinkActivity() {}

   private String getUrl(String var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         Pattern var3;
         if(VERSION.SDK_INT < 8) {
            var3 = WEB_URL;
         } else {
            var3 = SDK8.getWebUrlPattern();
         }

         Matcher var4 = var3.matcher(var1);
         if(var4.find()) {
            var2 = var4.group();
         } else {
            var2 = null;
         }
      }

      return var2;
   }

   protected void setupDialogURL() {
      Bundle var1 = new Bundle();
      Bundle var2 = this.getIntent().getExtras();
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getString("android.intent.extra.TEXT");
      }

      String var4 = this.getUrl(var3);
      if(var4 != null && var4.length() > 0) {
         var1.putString("link", var4);
         String var5 = "URL: " + var4;
         Log.d("ShareLinkActivity", var5);
      }

      var1.putString("message", var3);
      String var6 = Long.toString(350685531728L);
      var1.putString("app_id", var6);
      var1.putString("display", "touch");
      var1.putString("redirect_uri", "fbconnect://success");
      StringBuilder var7 = new StringBuilder();
      String var8 = Constants.URL.getFeedUrl(this);
      StringBuilder var9 = var7.append(var8).append("?");
      StringBuilder var10 = URLQueryBuilder.buildQueryString(var1);
      String var11 = var9.append(var10).toString();
      this.mUrl = var11;
      String var12 = this.mUrl;
      Log.d("ShareLinkActivity", var12);
   }
}
