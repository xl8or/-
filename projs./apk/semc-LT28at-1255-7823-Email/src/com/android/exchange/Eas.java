package com.android.exchange;

import android.util.Log;

public class Eas {

   public static final String ACCOUNT_MAILBOX_PREFIX = "__eas";
   public static final String BODY_PREFERENCE_HTML = "2";
   public static final String BODY_PREFERENCE_TEXT = "1";
   public static boolean DEBUG = 0;
   public static final int DEBUG_BIT = 1;
   public static final int DEBUG_EXCHANGE_BIT = 2;
   public static final int DEBUG_FILE_BIT = 4;
   public static final String DEFAULT_PROTOCOL_VERSION = "2.5";
   public static final String EAS12_TRUNCATION_SIZE = "200000";
   public static final String EAS2_5_TRUNCATION_SIZE = "7";
   public static final int EXCHANGE_ERROR_NOTIFICATION = 16;
   public static boolean FILE_LOG = 0;
   public static final String FILTER_1_DAY = "1";
   public static final String FILTER_1_MONTH = "5";
   public static final String FILTER_1_WEEK = "3";
   public static final String FILTER_2_WEEKS = "4";
   public static final String FILTER_3_DAYS = "2";
   public static final String FILTER_3_MONTHS = "6";
   public static final String FILTER_6_MONTHS = "7";
   public static final String FILTER_ALL = "0";
   public static final int FOLDER_STATUS_INVALID_KEY = 9;
   public static final int FOLDER_STATUS_OK = 1;
   public static boolean PARSER_LOG = 0;
   public static boolean SERIALIZER_LOG = 0;
   public static final String SUPPORTED_PROTOCOL_EX2003 = "2.5";
   public static final double SUPPORTED_PROTOCOL_EX2003_DOUBLE = 2.5D;
   public static final String SUPPORTED_PROTOCOL_EX2007 = "12.0";
   public static final double SUPPORTED_PROTOCOL_EX2007_DOUBLE = 12.0D;
   public static boolean USER_LOG = 0;
   public static final String VERSION = "0.3";
   public static boolean WAIT_DEBUG = 0;


   public Eas() {}

   public static void setUserDebug(int var0) {
      if(!DEBUG) {
         byte var1;
         if((var0 & 1) != 0) {
            var1 = 1;
         } else {
            var1 = 0;
         }

         USER_LOG = (boolean)var1;
         byte var2;
         if((var0 & 2) != 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         PARSER_LOG = (boolean)var2;
         byte var3;
         if((var0 & 4) != 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         FILE_LOG = (boolean)var3;
         if(FILE_LOG || PARSER_LOG) {
            USER_LOG = (boolean)1;
         }

         String var4 = "Eas Debug";
         StringBuilder var5 = (new StringBuilder()).append("Logging: ");
         String var6;
         if(USER_LOG) {
            var6 = "User ";
         } else {
            var6 = "";
         }

         StringBuilder var7 = var5.append(var6);
         String var8;
         if(PARSER_LOG) {
            var8 = "Parser ";
         } else {
            var8 = "";
         }

         StringBuilder var9 = var7.append(var8);
         String var10;
         if(FILE_LOG) {
            var10 = "File";
         } else {
            var10 = "";
         }

         String var11 = var9.append(var10).toString();
         Log.d(var4, var11);
      }
   }
}
