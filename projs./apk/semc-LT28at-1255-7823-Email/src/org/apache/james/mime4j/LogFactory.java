package org.apache.james.mime4j;

import org.apache.james.mime4j.Log;

public final class LogFactory {

   private LogFactory() {}

   public static Log getLog(Class var0) {
      return new Log(var0);
   }
}
