package com.google.android.finsky.billing.carrierbilling.model;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.EncryptedSubscriberInfo;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.utils.Md5Util;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.finsky.utils.persistence.FileBasedKeyValueStore;
import com.google.android.finsky.utils.persistence.WriteThroughKeyValueStore;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarrierBillingStorage {

   private static final String CREDENTIALS_API_VERSION = "api_version";
   private static final String CREDENTIALS_CREDENTIALS = "credentials";
   private static final String CREDENTIALS_EXPIRATION_TIME = "expiration_time";
   private static final String CREDENTIALS_INVALID_PASSWORD = "invalid_password";
   private static final String CREDENTIALS_IS_PROVISIONED = "is_provisioned";
   static final String CREDENTIALS_KEY = "credentials";
   private static final String CSV_DELIMITER = ",";
   private static final String DEFAULT_SIM_IDENTIFIER = "invalid_sim_id";
   private static final String FILE_SUBDIR = "carrier_billing";
   private static final String PARAMS_CARRIER_API_VERSION = "carrier_api_version";
   private static final String PARAMS_CARRIER_ICON_ID = "carrier_icon_id";
   private static final String PARAMS_CARRIER_ID = "carrier_id";
   private static final String PARAMS_CARRIER_NAME = "carrier_name";
   private static final String PARAMS_GET_CREDENTIALS_URL = "get_credentials_url";
   private static final String PARAMS_GET_PROVISIONING_URL = "get_provisioning_url";
   static final String PARAMS_KEY = "params";
   private static final String PARAMS_MNC_MCC_CSV = "mnc_mcc_csv";
   private static final String PARAMS_PER_TRANSACTION_CREDENTIALS_REQUIRED = "per_transaction_credentials_required";
   private static final String PARAMS_SEND_SUBSCRIBER_INFO_WITH_CARRIER_REQUESTS = "send_subscriber_info_with_carrier_requests";
   private static final String PARAMS_SHOW_CARRIER_TOS = "show_carrier_tos";
   private static final String PROVISIONING_ACCOUNT_TYPE = "account_type";
   private static final String PROVISIONING_ADDRESS_SNIPPET = "address_snippet";
   private static final String PROVISIONING_API_VERSION = "api_version";
   private static final String PROVISIONING_COUNTRY = "country";
   private static final String PROVISIONING_ENCRYPTED_CARRIER_KEY_VERSION = "encrypted_carrier_key_version";
   private static final String PROVISIONING_ENCRYPTED_GOOGLE_KEY_VERSION = "encrypted_google_key_version";
   private static final String PROVISIONING_ENCRYPTED_INIT_VECTOR = "encrypted_init_vector";
   private static final String PROVISIONING_ENCRYPTED_KEY = "encrypted_key";
   private static final String PROVISIONING_ENCRYPTED_MESSAGE = "encrypted_message";
   private static final String PROVISIONING_ENCRYPTED_SIGNATURE = "encrypted_signature";
   private static final String PROVISIONING_ID = "id";
   private static final String PROVISIONING_IS_PROVISIONED = "is_provisioned";
   static final String PROVISIONING_KEY = "provisioning";
   private static final String PROVISIONING_OBFUSCATED_SUBSCRIBER_INFO = "subscriber_token";
   private static final String PROVISIONING_PASSWORD_FORGOT_URL = "password_forgot_url";
   private static final String PROVISIONING_PASSWORD_PROMPT = "password_prompt";
   private static final String PROVISIONING_PASSWORD_REQUIRED = "password_required";
   private static final String PROVISIONING_SUBSCRIBER_CURRENCY = "subscriber_currency";
   private static final String PROVISIONING_TOS_URL = "tos_url";
   private static final String PROVISIONING_TOS_VERSION = "tos_version";
   private static final String PROVISIONING_TRANSACTION_LIMIT = "transaction_limit";
   private final String mCurrentSimIdentifier;
   private volatile boolean mIsInit = 0;
   private final WriteThroughKeyValueStore mStore;


   public CarrierBillingStorage(Context var1) {
      String var2 = this.initCurrentSimIdentifier(var1);
      this.mCurrentSimIdentifier = var2;
      File var3 = var1.getDir("carrier_billing", 0);
      String var4 = this.mCurrentSimIdentifier;
      FileBasedKeyValueStore var5 = new FileBasedKeyValueStore(var3, var4);
      WriteThroughKeyValueStore var6 = new WriteThroughKeyValueStore(var5);
      this.mStore = var6;
   }

   CarrierBillingStorage(WriteThroughKeyValueStore var1) {
      this.mStore = var1;
      this.mCurrentSimIdentifier = "invalid_sim_id";
   }

   private String booleanToString(Boolean var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = Boolean.toString(var1.booleanValue());
      }

      return var2;
   }

   private String getParamsKey() {
      String var1;
      if(FinskyApp.get().getCurrentAccountName() == null) {
         var1 = "params";
      } else {
         StringBuilder var2 = (new StringBuilder()).append("params");
         String var3 = Md5Util.secureHash(FinskyApp.get().getCurrentAccountName().getBytes());
         var1 = var2.append(var3).toString();
      }

      return var1;
   }

   private String initCurrentSimIdentifier(Context var1) {
      String var2 = ((TelephonyManager)var1.getSystemService("phone")).getSubscriberId();
      String var3;
      if(var2 != null) {
         var3 = Sha1Util.secureHash(var2.getBytes());
      } else {
         var3 = "invalid_sim_id";
      }

      return var3;
   }

   private String integerToString(Integer var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = Integer.toString(var1.intValue());
      }

      return var2;
   }

   private String longToString(Long var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = Long.toString(var1.longValue());
      }

      return var2;
   }

   private Boolean stringToBoolean(String var1) {
      Boolean var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = Boolean.valueOf(Boolean.parseBoolean(var1));
      }

      return var2;
   }

   private Integer stringToInteger(String var1) {
      Integer var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = Integer.valueOf(Integer.parseInt(var1));
      }

      return var2;
   }

   private Long stringToLong(String var1) {
      Long var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = Long.valueOf(Long.parseLong(var1));
      }

      return var2;
   }

   public void clearCredentials() {
      this.mStore.delete("credentials");
   }

   public void clearParams() {
      WriteThroughKeyValueStore var1 = this.mStore;
      String var2 = this.getParamsKey();
      var1.delete(var2);
   }

   public void clearProvisioning() {
      this.mStore.delete("provisioning");
   }

   public CarrierBillingCredentials getCredentials() {
      if(!this.isInit()) {
         throw new IllegalStateException("Attempt to fetch credentials when key store isn\'t ready.");
      } else {
         Map var1 = this.mStore.get("credentials");
         CarrierBillingCredentials var2;
         if(var1 == null) {
            var2 = null;
         } else {
            CarrierBillingCredentials.Builder var3 = new CarrierBillingCredentials.Builder();
            String var4 = (String)var1.get("credentials");
            CarrierBillingCredentials.Builder var5 = var3.setCredentials(var4);
            String var6 = (String)var1.get("api_version");
            Integer var7 = this.stringToInteger(var6);
            if(var7 != null) {
               int var8 = var7.intValue();
               var5.setApiVersion(var8);
            }

            String var10 = (String)var1.get("expiration_time");
            Long var11 = this.stringToLong(var10);
            if(var11 != null) {
               long var12 = var11.longValue();
               var5.setExpirationTime(var12);
            }

            String var15 = (String)var1.get("is_provisioned");
            Boolean var16 = this.stringToBoolean(var15);
            if(var16 != null) {
               boolean var17 = var16.booleanValue();
               var5.setIsProvisioned(var17);
            }

            String var19 = (String)var1.get("invalid_password");
            Boolean var20 = this.stringToBoolean(var19);
            if(var20 != null) {
               boolean var21 = var20.booleanValue();
               var5.setInvalidPassword(var21);
            }

            var2 = var5.build();
         }

         return var2;
      }
   }

   public String getCurrentSimIdentifier() {
      return this.mCurrentSimIdentifier;
   }

   public CarrierBillingParameters getParams() {
      if(!this.isInit()) {
         throw new IllegalStateException("Attempt to fetch params when key store isn\'t ready.");
      } else {
         WriteThroughKeyValueStore var1 = this.mStore;
         String var2 = this.getParamsKey();
         Map var3 = var1.get(var2);
         CarrierBillingParameters var4;
         if(var3 == null) {
            var4 = null;
         } else {
            CarrierBillingParameters.Builder var5 = new CarrierBillingParameters.Builder();
            String var6 = (String)var3.get("carrier_id");
            CarrierBillingParameters.Builder var7 = var5.setId(var6);
            String var8 = (String)var3.get("carrier_name");
            CarrierBillingParameters.Builder var9 = var7.setName(var8);
            String var10 = (String)var3.get("mnc_mcc_csv");
            List var11 = this.stringToList(var10);
            CarrierBillingParameters.Builder var12 = var9.setMncMccs(var11);
            String var13 = (String)var3.get("get_provisioning_url");
            CarrierBillingParameters.Builder var14 = var12.setGetProvisioningUrl(var13);
            String var15 = (String)var3.get("get_credentials_url");
            CarrierBillingParameters.Builder var16 = var14.setGetCredentialsUrl(var15);
            String var17 = (String)var3.get("carrier_icon_id");
            CarrierBillingParameters.Builder var18 = var16.setCarrierIconId(var17);
            String var19 = (String)var3.get("carrier_api_version");
            Integer var20 = this.stringToInteger(var19);
            if(var20 != null) {
               int var21 = var20.intValue();
               var18.setCarrierApiVersion(var21);
            }

            String var23 = (String)var3.get("show_carrier_tos");
            Boolean var24 = this.stringToBoolean(var23);
            if(var24 != null) {
               boolean var25 = var24.booleanValue();
               var18.setShowCarrierTos(var25);
            }

            String var27 = (String)var3.get("per_transaction_credentials_required");
            Boolean var28 = this.stringToBoolean(var27);
            if(var28 != null) {
               boolean var29 = var28.booleanValue();
               var18.setPerTransactionCredentialsRequired(var29);
            }

            String var31 = (String)var3.get("per_transaction_credentials_required");
            Boolean var32 = this.stringToBoolean(var31);
            if(var32 != null) {
               boolean var33 = var32.booleanValue();
               var18.setSendSubscriberInfoWithCarrierRequests(var33);
            }

            var4 = var18.build();
         }

         return var4;
      }
   }

   public CarrierBillingProvisioning getProvisioning() {
      if(!this.isInit()) {
         throw new IllegalStateException("Attempt to fetch provisioning when key store isn\'t ready.");
      } else {
         Map var1 = this.mStore.get("provisioning");
         CarrierBillingCredentials var2 = this.getCredentials();
         CarrierBillingProvisioning var3;
         if(var1 == null) {
            if(var2 != null) {
               var3 = (new CarrierBillingProvisioning.Builder()).setCredentials(var2).build();
            } else {
               var3 = null;
            }
         } else {
            CarrierBillingProvisioning.Builder var4 = new CarrierBillingProvisioning.Builder();
            String var5 = (String)var1.get("id");
            CarrierBillingProvisioning.Builder var6 = var4.setProvisioningId(var5);
            String var7 = (String)var1.get("tos_url");
            CarrierBillingProvisioning.Builder var8 = var6.setTosUrl(var7);
            String var9 = (String)var1.get("tos_version");
            CarrierBillingProvisioning.Builder var10 = var8.setTosVersion(var9);
            String var11 = (String)var1.get("subscriber_currency");
            CarrierBillingProvisioning.Builder var12 = var10.setSubscriberCurrency(var11);
            String var13 = (String)var1.get("account_type");
            CarrierBillingProvisioning.Builder var14 = var12.setAccountType(var13);
            String var15 = (String)var1.get("password_prompt");
            CarrierBillingProvisioning.Builder var16 = var14.setPasswordPrompt(var15);
            String var17 = (String)var1.get("password_forgot_url");
            CarrierBillingProvisioning.Builder var18 = var16.setPasswordForgotUrl(var17);
            String var19 = (String)var1.get("address_snippet");
            CarrierBillingProvisioning.Builder var20 = var18.setAddressSnippet(var19);
            String var21 = (String)var1.get("country");
            CarrierBillingProvisioning.Builder var22 = var20.setCountry(var21).setCredentials(var2);
            String var23 = (String)var1.get("api_version");
            Integer var24 = this.stringToInteger(var23);
            if(var24 != null) {
               int var25 = var24.intValue();
               var4.setApiVersion(var25);
            }

            String var27 = (String)var1.get("is_provisioned");
            Boolean var28 = this.stringToBoolean(var27);
            if(var28 != null) {
               boolean var29 = var28.booleanValue();
               var4.setIsProvisioned(var29);
            }

            String var31 = (String)var1.get("transaction_limit");
            Long var32 = this.stringToLong(var31);
            if(var32 != null) {
               long var33 = var32.longValue();
               var4.setTransactionLimit(var33);
            }

            String var36 = (String)var1.get("password_required");
            Boolean var37 = this.stringToBoolean(var36);
            if(var37 != null) {
               boolean var38 = var37.booleanValue();
               var4.setPasswordRequired(var38);
            }

            String var40 = (String)var1.get("subscriber_token");
            if(!TextUtils.isEmpty(var40)) {
               SubscriberInfo var41 = SubscriberInfo.fromObfuscatedString(var40);
               if(var41 != null) {
                  var4.setSubscriberInfo(var41);
               }
            }

            EncryptedSubscriberInfo.Builder var43 = new EncryptedSubscriberInfo.Builder();
            String var44 = (String)var1.get("encrypted_message");
            EncryptedSubscriberInfo.Builder var45 = var43.setMessage(var44);
            String var46 = (String)var1.get("encrypted_key");
            EncryptedSubscriberInfo.Builder var47 = var45.setEncryptedKey(var46);
            String var48 = (String)var1.get("encrypted_signature");
            EncryptedSubscriberInfo.Builder var49 = var47.setSignature(var48);
            String var50 = (String)var1.get("encrypted_init_vector");
            EncryptedSubscriberInfo.Builder var51 = var49.setInitVector(var50);
            String var52 = (String)var1.get("encrypted_google_key_version");
            Integer var53 = this.stringToInteger(var52);
            if(var53 != null) {
               int var54 = var53.intValue();
               var51.setGoogleKeyVersion(var54);
            }

            String var56 = (String)var1.get("encrypted_carrier_key_version");
            Integer var57 = this.stringToInteger(var56);
            if(var57 != null) {
               int var58 = var57.intValue();
               var51.setCarrierKeyVersion(var58);
            }

            EncryptedSubscriberInfo var60 = var51.build();
            if(!var60.isEmpty()) {
               var4.setEncryptedSubscriberInfo(var60);
            }

            var3 = var4.build();
         }

         return var3;
      }
   }

   public void init(Runnable var1) {
      WriteThroughKeyValueStore var2 = this.mStore;
      CarrierBillingStorage.1 var3 = new CarrierBillingStorage.1(var1);
      var2.load(var3);
   }

   public boolean isInit() {
      return this.mIsInit;
   }

   String listToString(List<String> var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = TextUtils.join(",", var1);
      }

      return var2;
   }

   public void setCredentials(CarrierBillingCredentials var1) {
      HashMap var2 = new HashMap();
      String var3 = var1.getCredentials();
      var2.put("credentials", var3);
      Long var5 = Long.valueOf(var1.getExpirationTime());
      String var6 = this.longToString(var5);
      var2.put("expiration_time", var6);
      Boolean var8 = Boolean.valueOf(var1.isProvisioned());
      String var9 = this.booleanToString(var8);
      var2.put("is_provisioned", var9);
      Boolean var11 = Boolean.valueOf(var1.invalidPassword());
      String var12 = this.booleanToString(var11);
      var2.put("invalid_password", var12);
      this.mStore.put("credentials", var2);
   }

   void setInit(boolean var1) {
      this.mIsInit = var1;
   }

   public void setParams(CarrierBillingParameters var1) {
      HashMap var2 = new HashMap();
      String var3 = var1.getId();
      var2.put("carrier_id", var3);
      String var5 = var1.getName();
      var2.put("carrier_name", var5);
      List var7 = var1.getMncMccs();
      String var8 = this.listToString(var7);
      var2.put("mnc_mcc_csv", var8);
      String var10 = var1.getGetProvisioningUrl();
      var2.put("get_provisioning_url", var10);
      String var12 = var1.getGetCredentialsUrl();
      var2.put("get_credentials_url", var12);
      String var14 = var1.getCarrierIconId();
      var2.put("carrier_icon_id", var14);
      Boolean var16 = Boolean.valueOf(var1.showCarrierTos());
      String var17 = this.booleanToString(var16);
      var2.put("show_carrier_tos", var17);
      Integer var19 = Integer.valueOf(var1.getCarrierApiVersion());
      String var20 = this.integerToString(var19);
      var2.put("carrier_api_version", var20);
      Boolean var22 = Boolean.valueOf(var1.perTransactionCredentialsRequired());
      String var23 = this.booleanToString(var22);
      var2.put("per_transaction_credentials_required", var23);
      Boolean var25 = Boolean.valueOf(var1.sendSubscriberInfoWithCarrierRequests());
      String var26 = this.booleanToString(var25);
      var2.put("send_subscriber_info_with_carrier_requests", var26);
      WriteThroughKeyValueStore var28 = this.mStore;
      String var29 = this.getParamsKey();
      var28.put(var29, var2);
   }

   public void setProvisioning(CarrierBillingProvisioning var1) {
      HashMap var2 = new HashMap();
      Integer var3 = Integer.valueOf(var1.getApiVersion());
      String var4 = this.integerToString(var3);
      var2.put("api_version", var4);
      Boolean var6 = Boolean.valueOf(var1.isProvisioned());
      String var7 = this.booleanToString(var6);
      var2.put("is_provisioned", var7);
      String var9 = var1.getProvisioningId();
      var2.put("id", var9);
      String var11 = var1.getTosUrl();
      var2.put("tos_url", var11);
      String var13 = var1.getTosVersion();
      var2.put("tos_version", var13);
      String var15 = var1.getSubscriberCurrency();
      var2.put("subscriber_currency", var15);
      Long var17 = Long.valueOf(var1.getTransactionLimit());
      String var18 = this.longToString(var17);
      var2.put("transaction_limit", var18);
      String var20 = var1.getAccountType();
      var2.put("account_type", var20);
      if(var1.getSubscriberInfo() != null) {
         String var22 = var1.getSubscriberInfo().toObfuscatedString();
         var2.put("subscriber_token", var22);
      }

      Boolean var24 = Boolean.valueOf(var1.isPasswordRequired());
      String var25 = this.booleanToString(var24);
      var2.put("password_required", var25);
      String var27 = var1.getPasswordPrompt();
      var2.put("password_prompt", var27);
      String var29 = var1.getPasswordForgotUrl();
      var2.put("password_forgot_url", var29);
      String var31 = var1.getAddressSnippet();
      var2.put("address_snippet", var31);
      String var33 = var1.getCountry();
      var2.put("country", var33);
      EncryptedSubscriberInfo var35 = var1.getEncryptedSubscriberInfo();
      if(var35 != null) {
         String var36 = var35.getMessage();
         var2.put("encrypted_message", var36);
         String var38 = var35.getEncryptedKey();
         var2.put("encrypted_key", var38);
         String var40 = var35.getSignature();
         var2.put("encrypted_signature", var40);
         String var42 = var35.getInitVector();
         var2.put("encrypted_init_vector", var42);
         Integer var44 = Integer.valueOf(var35.getCarrierKeyVersion());
         String var45 = this.integerToString(var44);
         var2.put("encrypted_carrier_key_version", var45);
         Integer var47 = Integer.valueOf(var35.getGoogleKeyVersion());
         String var48 = this.integerToString(var47);
         var2.put("encrypted_google_key_version", var48);
      }

      CarrierBillingCredentials var50 = var1.getCredentials();
      if(var50 != null) {
         this.setCredentials(var50);
      }

      this.mStore.put("provisioning", var2);
   }

   List<String> stringToList(String var1) {
      ArrayList var2;
      if(var1 == null) {
         var2 = null;
      } else {
         List var3 = Arrays.asList(var1.split(","));
         var2 = new ArrayList(var3);
      }

      return var2;
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final Runnable val$runnable;


      1(Runnable var2) {
         this.val$runnable = var2;
      }

      public void run() {
         CarrierBillingStorage.this.setInit((boolean)1);
         this.val$runnable.run();
      }
   }
}
