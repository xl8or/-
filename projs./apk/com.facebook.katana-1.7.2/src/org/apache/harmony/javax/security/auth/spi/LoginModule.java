package org.apache.harmony.javax.security.auth.spi;

import java.util.Map;
import org.apache.harmony.javax.security.auth.Subject;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.login.LoginException;

public interface LoginModule {

   boolean abort() throws LoginException;

   boolean commit() throws LoginException;

   void initialize(Subject var1, CallbackHandler var2, Map<String, ?> var3, Map<String, ?> var4);

   boolean login() throws LoginException;

   boolean logout() throws LoginException;
}
