package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.ProtocolException;

public class MalformedChallengeException extends ProtocolException {

   public MalformedChallengeException() {}

   public MalformedChallengeException(String var1) {
      super(var1);
   }

   public MalformedChallengeException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
