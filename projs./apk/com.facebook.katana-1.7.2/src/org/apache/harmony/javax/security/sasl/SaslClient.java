package org.apache.harmony.javax.security.sasl;

import org.apache.harmony.javax.security.sasl.SaslException;

public interface SaslClient {

   void dispose() throws SaslException;

   byte[] evaluateChallenge(byte[] var1) throws SaslException;

   String getMechanismName();

   Object getNegotiatedProperty(String var1);

   boolean hasInitialResponse();

   boolean isComplete();

   byte[] unwrap(byte[] var1, int var2, int var3) throws SaslException;

   byte[] wrap(byte[] var1, int var2, int var3) throws SaslException;
}
