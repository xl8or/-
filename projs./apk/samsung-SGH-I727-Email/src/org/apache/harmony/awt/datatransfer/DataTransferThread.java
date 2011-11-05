package org.apache.harmony.awt.datatransfer;

import org.apache.harmony.awt.datatransfer.DTK;

public class DataTransferThread extends Thread {

   private final DTK dtk;


   public DataTransferThread(DTK var1) {
      super("AWT-DataTransferThread");
      this.setDaemon((boolean)1);
      this.dtk = var1;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void start() {
      synchronized(this) {
         super.start();

         try {
            this.wait();
         } catch (InterruptedException var3) {
            throw new RuntimeException(var3);
         }

      }
   }
}
