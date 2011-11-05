package com.facebook.katana.binding;

import android.os.Handler;
import android.os.Looper;

public class WorkerThread extends Thread {

   private final Handler m_handler;
   private Looper m_looper;
   private Handler m_threadHandler;


   public WorkerThread() {
      Handler var1 = new Handler();
      this.m_handler = var1;
      this.start();

      while(this.m_threadHandler == null) {
         long var2 = 100L;

         try {
            Thread.sleep(var2);
         } catch (InterruptedException var5) {
            return;
         }
      }

   }

   public Handler getHandler() {
      return this.m_handler;
   }

   public Handler getThreadHandler() {
      return this.m_threadHandler;
   }

   public void quit() {
      if(this.m_looper != null) {
         this.m_looper.quit();
         this.m_looper = null;
         long var1 = 3000L;

         try {
            this.join(var1);
         } catch (InterruptedException var4) {
            ;
         }
      }
   }

   public void run() {
      this.setPriority(1);
      Looper.prepare();
      Handler var1 = new Handler();
      this.m_threadHandler = var1;
      Looper var2 = Looper.myLooper();
      this.m_looper = var2;
      Looper.loop();
   }
}
