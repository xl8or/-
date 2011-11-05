package org.apache.commons.httpclient.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdleConnectionHandler {

   private static final Log LOG = LogFactory.getLog(IdleConnectionHandler.class);
   private Map connectionToAdded;


   public IdleConnectionHandler() {
      HashMap var1 = new HashMap();
      this.connectionToAdded = var1;
   }

   public void add(HttpConnection var1) {
      Long var2 = Long.valueOf(System.currentTimeMillis());
      if(LOG.isDebugEnabled()) {
         Log var3 = LOG;
         String var4 = "Adding connection at: " + var2;
         var3.debug(var4);
      }

      this.connectionToAdded.put(var1, var2);
   }

   public void closeIdleConnections(long var1) {
      long var3 = System.currentTimeMillis() - var1;
      if(LOG.isDebugEnabled()) {
         Log var5 = LOG;
         String var6 = "Checking for connections, idleTimeout: " + var3;
         var5.debug(var6);
      }

      Iterator var7 = this.connectionToAdded.keySet().iterator();

      while(var7.hasNext()) {
         HttpConnection var8 = (HttpConnection)var7.next();
         Long var9 = (Long)this.connectionToAdded.get(var8);
         if(var9.longValue() <= var3) {
            if(LOG.isDebugEnabled()) {
               Log var10 = LOG;
               String var11 = "Closing connection, connection time: " + var9;
               var10.debug(var11);
            }

            var7.remove();
            var8.close();
         }
      }

   }

   public void remove(HttpConnection var1) {
      this.connectionToAdded.remove(var1);
   }

   public void removeAll() {
      this.connectionToAdded.clear();
   }
}
