package org.apache.commons.httpclient.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.httpclient.HttpConnectionManager;

public class IdleConnectionTimeoutThread extends Thread {

   private List connectionManagers;
   private long connectionTimeout;
   private boolean shutdown;
   private long timeoutInterval;


   public IdleConnectionTimeoutThread() {
      ArrayList var1 = new ArrayList();
      this.connectionManagers = var1;
      this.shutdown = (boolean)0;
      this.timeoutInterval = 1000L;
      this.connectionTimeout = 3000L;
      this.setDaemon((boolean)1);
   }

   public void addConnectionManager(HttpConnectionManager var1) {
      synchronized(this){}

      try {
         if(this.shutdown) {
            throw new IllegalStateException("IdleConnectionTimeoutThread has been shutdown");
         }

         this.connectionManagers.add(var1);
      } finally {
         ;
      }

   }

   protected void handleCloseIdleConnections(HttpConnectionManager var1) {
      long var2 = this.connectionTimeout;
      var1.closeIdleConnections(var2);
   }

   public void removeConnectionManager(HttpConnectionManager var1) {
      synchronized(this){}

      try {
         if(this.shutdown) {
            throw new IllegalStateException("IdleConnectionTimeoutThread has been shutdown");
         }

         this.connectionManagers.remove(var1);
      } finally {
         ;
      }

   }

   public void run() {
      synchronized(this){}

      try {
         while(!this.shutdown) {
            Iterator var1 = this.connectionManagers.iterator();

            while(var1.hasNext()) {
               HttpConnectionManager var2 = (HttpConnectionManager)var1.next();
               this.handleCloseIdleConnections(var2);
            }

            try {
               long var4 = this.timeoutInterval;
               this.wait(var4);
            } catch (InterruptedException var9) {
               ;
            }
         }

         this.connectionManagers.clear();
      } finally {
         ;
      }

   }

   public void setConnectionTimeout(long var1) {
      synchronized(this){}

      try {
         if(this.shutdown) {
            throw new IllegalStateException("IdleConnectionTimeoutThread has been shutdown");
         }

         this.connectionTimeout = var1;
      } finally {
         ;
      }

   }

   public void setTimeoutInterval(long var1) {
      synchronized(this){}

      try {
         if(this.shutdown) {
            throw new IllegalStateException("IdleConnectionTimeoutThread has been shutdown");
         }

         this.timeoutInterval = var1;
      } finally {
         ;
      }

   }

   public void shutdown() {
      synchronized(this){}

      try {
         this.shutdown = (boolean)1;
         this.notifyAll();
      } finally {
         ;
      }

   }
}
