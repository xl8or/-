package com.facebook.katana;

import android.os.Handler;

public class UserTask extends Thread {

   private final Handler mHandler;


   public UserTask(Handler var1) {
      this.mHandler = var1;
   }

   protected void doInBackground() {}

   public void execute() {
      this.onPreExecute();
      this.start();
   }

   protected void onPostExecute() {}

   protected void onPreExecute() {}

   public void run() {
      this.doInBackground();
      Handler var1 = this.mHandler;
      UserTask.1 var2 = new UserTask.1();
      var1.post(var2);
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         UserTask.this.onPostExecute();
      }
   }
}
