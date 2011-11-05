package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class HttpException extends IOException {

   private final Throwable cause;
   private String reason;
   private int reasonCode = 200;


   public HttpException() {
      this.cause = null;
   }

   public HttpException(String var1) {
      super(var1);
      this.cause = null;
   }

   public HttpException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;

      try {
         Class[] var3 = new Class[]{Throwable.class};
         Method var4 = Throwable.class.getMethod("initCause", var3);
         Object[] var5 = new Object[]{var2};
         var4.invoke(this, var5);
      } catch (Exception var8) {
         ;
      }
   }

   public Throwable getCause() {
      return this.cause;
   }

   public String getReason() {
      return this.reason;
   }

   public int getReasonCode() {
      return this.reasonCode;
   }

   public void printStackTrace() {
      PrintStream var1 = System.err;
      this.printStackTrace(var1);
   }

   public void printStackTrace(PrintStream var1) {
      try {
         Class[] var2 = new Class[0];
         Method var3 = this.getClass().getMethod("getStackTrace", var2);
         super.printStackTrace(var1);
      } catch (Exception var5) {
         super.printStackTrace(var1);
         if(this.cause != null) {
            var1.print("Caused by: ");
            this.cause.printStackTrace(var1);
         }
      }
   }

   public void printStackTrace(PrintWriter var1) {
      try {
         Class[] var2 = new Class[0];
         Method var3 = this.getClass().getMethod("getStackTrace", var2);
         super.printStackTrace(var1);
      } catch (Exception var5) {
         super.printStackTrace(var1);
         if(this.cause != null) {
            var1.print("Caused by: ");
            this.cause.printStackTrace(var1);
         }
      }
   }

   public void setReason(String var1) {
      this.reason = var1;
   }

   public void setReasonCode(int var1) {
      this.reasonCode = var1;
   }
}
