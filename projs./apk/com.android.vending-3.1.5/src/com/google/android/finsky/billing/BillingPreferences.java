package com.google.android.finsky.billing;

import com.google.android.finsky.config.PreferenceFile;

public class BillingPreferences {

   public static PreferenceFile.SharedPreference<String> ACCEPTED_CARRIER_TOS_VERSION;
   public static PreferenceFile.SharedPreference<String> BILLING_COUNTRIES;
   public static PreferenceFile.SharedPreference<Long> EARLIEST_PROVISIONING_CHECK_TIME_MILLIS;
   private static final String LAST_ADD_CREDITCARD_CANCELED_PREFIX = "last_add_creditcard_canceled_millis:";
   public static PreferenceFile.SharedPreference<Long> LAST_BILLING_COUNTRIES_REFRESH_MILLIS;
   public static PreferenceFile.SharedPreference<Long> LAST_PROVISIONING_TIME_MILLIS;
   public static PreferenceFile.SharedPreference<Boolean> LOG_BILLING_EVENTS;
   private static PreferenceFile sBillingPrefs = new PreferenceFile("billing_preferences", 0);


   static {
      PreferenceFile var0 = sBillingPrefs;
      Long var1 = Long.valueOf(0L);
      LAST_PROVISIONING_TIME_MILLIS = var0.value("last_dcb_provisioning_time_millis", var1);
      PreferenceFile var2 = sBillingPrefs;
      Long var3 = Long.valueOf(0L);
      EARLIEST_PROVISIONING_CHECK_TIME_MILLIS = var2.value("earliest_dcb_provisioning_check_time_millis", var3);
      PreferenceFile var4 = sBillingPrefs;
      Boolean var5 = Boolean.valueOf((boolean)0);
      LOG_BILLING_EVENTS = var4.value("metadata_billing_events_enabled", var5);
      PreferenceFile var6 = sBillingPrefs;
      String var7 = (String)false;
      BILLING_COUNTRIES = var6.value("billing_countries", var7);
      PreferenceFile var8 = sBillingPrefs;
      Long var9 = Long.valueOf(0L);
      LAST_BILLING_COUNTRIES_REFRESH_MILLIS = var8.value("last_billing_countries_check", var9);
      PreferenceFile var10 = sBillingPrefs;
      String var11 = (String)false;
      ACCEPTED_CARRIER_TOS_VERSION = var10.value("accepted_carrier_tos_version", var11);
   }

   public BillingPreferences() {}

   public static PreferenceFile getBillingPrefs() {
      return sBillingPrefs;
   }

   public static PreferenceFile.SharedPreference<Long> getLastAddCreditcardCanceledMillis(String var0) {
      PreferenceFile var1 = sBillingPrefs;
      String var2 = "last_add_creditcard_canceled_millis:" + var0;
      Long var3 = Long.valueOf(0L);
      return var1.value(var2, var3);
   }
}
