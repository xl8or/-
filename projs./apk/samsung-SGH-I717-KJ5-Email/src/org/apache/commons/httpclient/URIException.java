package org.apache.commons.httpclient;

import org.apache.commons.httpclient.HttpException;

public class URIException extends HttpException {

   public static final int ESCAPING = 3;
   public static final int PARSING = 1;
   public static final int PUNYCODE = 4;
   public static final int UNKNOWN = 0;
   public static final int UNSUPPORTED_ENCODING = 2;
   protected String reason;
   protected int reasonCode;


   public URIException() {}

   public URIException(int var1) {
      this.reasonCode = var1;
   }

   public URIException(int var1, String var2) {
      super(var2);
      this.reason = var2;
      this.reasonCode = var1;
   }

   public URIException(String var1) {
      super(var1);
      this.reason = var1;
      this.reasonCode = 0;
   }

   public String getReason() {
      return this.reason;
   }

   public int getReasonCode() {
      return this.reasonCode;
   }

   public void setReason(String var1) {
      this.reason = var1;
   }

   public void setReasonCode(int var1) {
      this.reasonCode = var1;
   }
}
