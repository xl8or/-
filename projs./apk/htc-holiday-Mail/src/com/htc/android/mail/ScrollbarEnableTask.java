package com.htc.android.mail;

import android.app.Activity;
import android.view.View;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.util.Timer;
import java.util.TimerTask;

public class ScrollbarEnableTask extends TimerTask {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private final int DISABLE_SCROLLBAR_DELAY_TIME = 1000;
   private final String TAG = "ScrollbarEnableTask";
   private Activity mActivity = null;
   private View mView = null;


   ScrollbarEnableTask(View var1, Activity var2) {
      this.mView = var1;
      this.mActivity = var2;
   }

   public void run() {
      Activity var1 = this.mActivity;
      ScrollbarEnableTask.1 var2 = new ScrollbarEnableTask.1();
      var1.runOnUiThread(var2);
   }

   public void start(Timer var1) {
      try {
         var1.schedule(this, 1000L);
      } catch (IllegalStateException var3) {
         if(DEBUG) {
            ll.e("ScrollbarEnableTask", "scroll bar disable fail>");
         }
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         if(ScrollbarEnableTask.DEBUG) {
            ll.i("ScrollbarEnableTask", "disable scroll bar");
         }

         if(ScrollbarEnableTask.this.mView.getVerticalScrollbarWidth() > 0) {
            ScrollbarEnableTask.this.mView.setVerticalScrollBarEnabled((boolean)0);
            ScrollbarEnableTask.this.mView.invalidate();
         }
      }
   }
}
