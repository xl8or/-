package com.htc.android.mail.pim.vcard;


public class VCardException extends Exception {

   public static final int FILE_NOT_FOUNT = 11;
   public static final int FILE_PARSE_FAIL = 10;
   public static final int IO_ERROR = 1;
   public static final int MANUAL_STOP = 20;
   public static final int UNKNOW_ERROR = 0;
   public static final int VERSION_MISMATCH = 15;
   private int mCode;
   private String mMessage;


   public VCardException() {}

   public VCardException(int var1, String var2) {
      super(var2);
      this.mCode = var1;
      this.mMessage = var2;
   }

   public VCardException(String var1) {
      super(var1);
      this.mCode = 0;
      this.mMessage = var1;
   }

   public int getCode() {
      return this.mCode;
   }

   public String getMessage() {
      return this.mMessage;
   }
}
