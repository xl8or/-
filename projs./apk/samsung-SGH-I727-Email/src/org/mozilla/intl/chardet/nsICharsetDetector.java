package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public interface nsICharsetDetector {

   boolean DoIt(byte[] var1, int var2, boolean var3);

   void Done();

   void Init(nsICharsetDetectionObserver var1);
}
