package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsICharsetDetector;
import org.mozilla.intl.chardet.nsPSMDetector;

public class nsDetector extends nsPSMDetector implements nsICharsetDetector {

   nsICharsetDetectionObserver mObserver = null;


   public nsDetector() {}

   public nsDetector(int var1) {
      super(var1);
   }

   public boolean DoIt(byte[] var1, int var2, boolean var3) {
      boolean var4;
      if(var1 != null && !var3) {
         this.HandleData(var1, var2);
         var4 = this.mDone;
      } else {
         var4 = false;
      }

      return var4;
   }

   public void Done() {
      this.DataEnd();
   }

   public void Init(nsICharsetDetectionObserver var1) {
      this.mObserver = var1;
   }

   public void Report(String var1) {
      if(this.mObserver != null) {
         this.mObserver.Notify(var1);
      }
   }

   public boolean isAscii(byte[] var1, int var2) {
      int var3 = 0;

      boolean var4;
      while(true) {
         if(var3 >= var2) {
            var4 = true;
            break;
         }

         if((var1[var3] & 128) != 0) {
            var4 = false;
            break;
         }

         ++var3;
      }

      return var4;
   }
}
