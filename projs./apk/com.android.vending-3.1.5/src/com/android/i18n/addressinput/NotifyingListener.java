package com.android.i18n.addressinput;

import com.android.i18n.addressinput.DataLoadListener;

public class NotifyingListener implements DataLoadListener {

   private boolean mDone;
   private Object mSleeper;


   NotifyingListener(Object var1) {
      this.mSleeper = var1;
      this.mDone = (boolean)0;
   }

   public void dataLoadingBegin() {}

   public void dataLoadingEnd() {
      // $FF: Couldn't be decompiled
   }

   void waitLoadingEnd() throws InterruptedException {
      // $FF: Couldn't be decompiled
   }
}
