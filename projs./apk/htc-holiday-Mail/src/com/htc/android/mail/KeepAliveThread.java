package com.htc.android.mail;


public abstract class KeepAliveThread extends Thread {

   private boolean alive = 1;
   private int sleepTime = '\uea60';


   public KeepAliveThread() {}

   public void disconnect() {
      this.alive = (boolean)0;
   }

   public abstract void keepalive();

   public final void run() {
      while(this.alive) {
         try {
            Thread var1 = currentThread();
            Thread.sleep((long)this.sleepTime);
            this.keepalive();
         } catch (InterruptedException var2) {
            var2.printStackTrace();
         }
      }

   }

   public void setAlive(boolean var1) {
      this.alive = var1;
   }

   public void setSleepTime(int var1) {
      this.sleepTime = var1;
   }
}
