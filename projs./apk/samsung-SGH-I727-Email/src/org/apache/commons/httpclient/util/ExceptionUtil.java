package org.apache.commons.httpclient.util;

import java.io.InterruptedIOException;
import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExceptionUtil {

   private static final Method INIT_CAUSE_METHOD = getInitCauseMethod();
   private static final Log LOG = LogFactory.getLog(ExceptionUtil.class);
   private static final Class SOCKET_TIMEOUT_CLASS = SocketTimeoutExceptionClass();


   public ExceptionUtil() {}

   private static Class SocketTimeoutExceptionClass() {
      Class var0;
      Class var1;
      try {
         var0 = Class.forName("java.net.SocketTimeoutException");
      } catch (ClassNotFoundException var3) {
         var1 = null;
         return var1;
      }

      var1 = var0;
      return var1;
   }

   private static Method getInitCauseMethod() {
      Method var1;
      Method var2;
      try {
         Class[] var0 = new Class[]{Throwable.class};
         var1 = Throwable.class.getMethod("initCause", var0);
      } catch (NoSuchMethodException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public static void initCause(Throwable var0, Throwable var1) {
      if(INIT_CAUSE_METHOD != null) {
         try {
            Method var2 = INIT_CAUSE_METHOD;
            Object[] var3 = new Object[]{var1};
            var2.invoke(var0, var3);
         } catch (Exception var6) {
            LOG.warn("Exception invoking Throwable.initCause", var6);
         }
      }
   }

   public static boolean isSocketTimeoutException(InterruptedIOException var0) {
      boolean var1;
      if(SOCKET_TIMEOUT_CLASS != null) {
         var1 = SOCKET_TIMEOUT_CLASS.isInstance(var0);
      } else {
         var1 = true;
      }

      return var1;
   }
}
