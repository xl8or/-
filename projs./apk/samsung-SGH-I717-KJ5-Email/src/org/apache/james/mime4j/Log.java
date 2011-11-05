package org.apache.james.mime4j;


public class Log {

   public Log(Class var1) {}

   private static String toString(Object var0, Throwable var1) {
      String var2;
      if(var0 == null) {
         var2 = "(null)";
      } else {
         var2 = var0.toString();
      }

      String var3;
      if(var1 == null) {
         var3 = var2;
      } else {
         StringBuilder var4 = (new StringBuilder()).append(var2).append(" ");
         String var5 = var1.getMessage();
         var3 = var4.append(var5).toString();
      }

      return var3;
   }

   public void debug(Object var1) {
      if(this.isDebugEnabled()) {
         String var2 = toString(var1, (Throwable)null);
         int var3 = android.util.Log.d("Email", var2);
      }
   }

   public void debug(Object var1, Throwable var2) {
      if(this.isDebugEnabled()) {
         String var3 = toString(var1, var2);
         int var4 = android.util.Log.d("Email", var3);
      }
   }

   public void error(Object var1) {
      String var2 = toString(var1, (Throwable)null);
      int var3 = android.util.Log.e("Email", var2);
   }

   public void error(Object var1, Throwable var2) {
      String var3 = toString(var1, var2);
      int var4 = android.util.Log.e("Email", var3);
   }

   public void fatal(Object var1) {
      String var2 = toString(var1, (Throwable)null);
      int var3 = android.util.Log.e("Email", var2);
   }

   public void fatal(Object var1, Throwable var2) {
      String var3 = toString(var1, var2);
      int var4 = android.util.Log.e("Email", var3);
   }

   public void info(Object var1) {
      if(this.isInfoEnabled()) {
         String var2 = toString(var1, (Throwable)null);
         int var3 = android.util.Log.i("Email", var2);
      }
   }

   public void info(Object var1, Throwable var2) {
      if(this.isInfoEnabled()) {
         String var3 = toString(var1, var2);
         int var4 = android.util.Log.i("Email", var3);
      }
   }

   public boolean isDebugEnabled() {
      return false;
   }

   public boolean isErrorEnabled() {
      return true;
   }

   public boolean isFatalEnabled() {
      return true;
   }

   public boolean isInfoEnabled() {
      return false;
   }

   public boolean isTraceEnabled() {
      return false;
   }

   public boolean isWarnEnabled() {
      return true;
   }

   public void trace(Object var1) {
      if(this.isTraceEnabled()) {
         String var2 = toString(var1, (Throwable)null);
         int var3 = android.util.Log.v("Email", var2);
      }
   }

   public void trace(Object var1, Throwable var2) {
      if(this.isTraceEnabled()) {
         String var3 = toString(var1, var2);
         int var4 = android.util.Log.v("Email", var3);
      }
   }

   public void warn(Object var1) {
      String var2 = toString(var1, (Throwable)null);
      int var3 = android.util.Log.w("Email", var2);
   }

   public void warn(Object var1, Throwable var2) {
      String var3 = toString(var1, var2);
      int var4 = android.util.Log.w("Email", var3);
   }
}
