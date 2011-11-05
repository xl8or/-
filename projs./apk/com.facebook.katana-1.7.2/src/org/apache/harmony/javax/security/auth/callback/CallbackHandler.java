package org.apache.harmony.javax.security.auth.callback;

import java.io.IOException;
import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;

public interface CallbackHandler {

   void handle(Callback[] var1) throws IOException, UnsupportedCallbackException;
}
