package org.apache.harmony.javax.security.sasl;

import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslException;
import org.apache.harmony.javax.security.sasl.SaslServer;

public interface SaslServerFactory {

   SaslServer createSaslServer(String var1, String var2, String var3, Map<String, ?> var4, CallbackHandler var5) throws SaslException;

   String[] getMechanismNames(Map<String, ?> var1);
}
