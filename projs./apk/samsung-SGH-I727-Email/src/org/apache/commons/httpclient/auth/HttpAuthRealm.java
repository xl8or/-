package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.auth.AuthScope;

public class HttpAuthRealm extends AuthScope {

   public HttpAuthRealm(String var1, String var2) {
      String var3 = ANY_SCHEME;
      super(var1, -1, var2, var3);
   }
}
