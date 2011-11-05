package org.apache.commons.httpclient;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.ResponseConsumedWatcher;

class AutoCloseInputStream extends FilterInputStream {

   private boolean selfClosed = 0;
   private boolean streamOpen = 1;
   private ResponseConsumedWatcher watcher = null;


   public AutoCloseInputStream(InputStream var1, ResponseConsumedWatcher var2) {
      super(var1);
      this.watcher = var2;
   }

   private void checkClose(int var1) throws IOException {
      if(var1 == -1) {
         this.notifyWatcher();
      }
   }

   private boolean isReadAllowed() throws IOException {
      if(!this.streamOpen && this.selfClosed) {
         throw new IOException("Attempted read on closed stream.");
      } else {
         return this.streamOpen;
      }
   }

   private void notifyWatcher() throws IOException {
      if(this.streamOpen) {
         super.close();
         this.streamOpen = (boolean)0;
         if(this.watcher != null) {
            this.watcher.responseConsumed();
         }
      }
   }

   public int available() throws IOException {
      int var1 = 0;
      if(this.isReadAllowed()) {
         var1 = super.available();
      }

      return var1;
   }

   public void close() throws IOException {
      if(!this.selfClosed) {
         this.selfClosed = (boolean)1;
         this.notifyWatcher();
      }
   }

   public int read() throws IOException {
      int var1 = -1;
      if(this.isReadAllowed()) {
         var1 = super.read();
         this.checkClose(var1);
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = -1;
      if(this.isReadAllowed()) {
         var2 = super.read(var1);
         this.checkClose(var2);
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = -1;
      if(this.isReadAllowed()) {
         var4 = super.read(var1, var2, var3);
         this.checkClose(var4);
      }

      return var4;
   }
}
