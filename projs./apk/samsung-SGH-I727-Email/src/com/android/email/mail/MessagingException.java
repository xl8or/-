package com.android.email.mail;


public class MessagingException extends Exception {

   public static final int ACTIVATION_ERROR = 12;
   public static final int AUTHENTICATION_FAILED = 5;
   public static final int AUTH_REQUIRED = 3;
   public static final int DEVICE_ACCESS_EXCEPTION_BASE = 262144;
   public static final int DEVICE_BLOCKED_EXCEPTION = 262145;
   public static final int DEVICE_QURANTINED_EXCEPTION = 262146;
   public static final int DUPLICATE_ACCOUNT = 6;
   public static final int EMPTYTRASH_EXCEPTION = 131072;
   public static final int FOLDER_CMD_LIST_ERROR = 30;
   public static final int FOLDER_CMD_RESULT_ALREADY_EXIST_ERROR = 35;
   public static final int FOLDER_CMD_RESULT_CONNECTION_ERROR = 34;
   public static final int FOLDER_CMD_RESULT_CREATE_ERROR = 31;
   public static final int FOLDER_CMD_RESULT_DELETE_ERROR = 32;
   public static final int FOLDER_CMD_RESULT_FOLDER_NAME_INVALID_ERROR = 37;
   public static final int FOLDER_CMD_RESULT_FOLDER_NOT_EXIST_ERROR = 36;
   public static final int FOLDER_CMD_RESULT_MAX = 39;
   public static final int FOLDER_CMD_RESULT_PERMISSION_ERROR = 38;
   public static final int FOLDER_CMD_RESULT_UPDATE_ERROR = 33;
   public static final int GAL_EXCEPTION = 10;
   public static final int GENERAL_SECURITY = 4;
   public static final int IOERROR = 1;
   public static final int LOADMORE_EXCEPTION = 196608;
   public static final int NO_ERROR = 255;
   public static final int OOO_EXCEPTION = 11;
   public static final int PROTOCOL_VERSION_UNSUPPORTED = 9;
   public static final int REMOTE_EXCEPTION_ERROR_FOR_Z7 = 20;
   public static final int SECURITY_POLICIES_REQUIRED = 7;
   public static final int SECURITY_POLICIES_UNSUPPORTED = 8;
   public static final int TLS_REQUIRED = 2;
   public static final int UNSPECIFIED_EXCEPTION = 0;
   public static final int UNSUPPORTED = 65536;
   public static final long serialVersionUID = 255L;
   protected int mExceptionType;


   public MessagingException(int var1) {
      this.mExceptionType = var1;
   }

   public MessagingException(int var1, int var2) {
      String var3 = String.valueOf(var2);
      super(var3);
      this.mExceptionType = var1;
   }

   public MessagingException(int var1, String var2) {
      super(var2);
      this.mExceptionType = var1;
   }

   public MessagingException(String var1) {
      super(var1);
      this.mExceptionType = 0;
   }

   public MessagingException(String var1, Throwable var2) {
      super(var1, var2);
      this.mExceptionType = 0;
   }

   public int getExceptionType() {
      return this.mExceptionType;
   }
}
