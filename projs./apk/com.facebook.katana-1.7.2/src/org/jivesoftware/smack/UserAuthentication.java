package org.jivesoftware.smack;

import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.XMPPException;

interface UserAuthentication {

   String authenticate(String var1, String var2, String var3) throws XMPPException;

   String authenticate(String var1, String var2, CallbackHandler var3) throws XMPPException;

   String authenticateAnonymously() throws XMPPException;
}
