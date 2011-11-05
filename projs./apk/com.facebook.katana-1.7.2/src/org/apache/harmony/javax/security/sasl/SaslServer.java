package org.apache.harmony.javax.security.sasl;

import org.apache.harmony.javax.security.sasl.SaslException;

public interface SaslServer {

   void dispose() throws SaslException;

   byte[] evaluateResponse(byte[] var1) throws SaslException;

   String getAuthorizationID();

   String getMechanismName();

   Object getNegotiatedProperty(String var1);

   boolean isComplete();

   byte[] unwrap(byte[] var1, int var2, int var3) throws SaslException;

   byte[] wrap(byte[] var1, int var2, int var3) throws SaslException;
}
