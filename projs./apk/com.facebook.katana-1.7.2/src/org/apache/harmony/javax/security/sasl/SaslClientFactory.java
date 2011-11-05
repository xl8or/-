package org.apache.harmony.javax.security.sasl;

import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;

public interface SaslClientFactory {

   SaslClient createSaslClient(String[] var1, String var2, String var3, String var4, Map<String, ?> var5, CallbackHandler var6) throws SaslException;

   String[] getMechanismNames(Map<String, ?> var1);
}
