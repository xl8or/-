package org.apache.commons.httpclient.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.httpclient.auth.DigestScheme;
import org.apache.commons.httpclient.auth.NTLMScheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AuthPolicy {

   public static final String AUTH_SCHEME_PRIORITY = "http.auth.scheme-priority";
   public static final String BASIC = "Basic";
   public static final String DIGEST = "Digest";
   protected static final Log LOG;
   public static final String NTLM = "NTLM";
   private static final HashMap SCHEMES = new HashMap();
   private static final ArrayList SCHEME_LIST = new ArrayList();


   static {
      registerAuthScheme("NTLM", NTLMScheme.class);
      registerAuthScheme("Digest", DigestScheme.class);
      registerAuthScheme("Basic", BasicScheme.class);
      LOG = LogFactory.getLog(AuthPolicy.class);
   }

   public AuthPolicy() {}

   public static AuthScheme getAuthScheme(String param0) throws IllegalStateException {
      // $FF: Couldn't be decompiled
   }

   public static List getDefaultAuthPrefs() {
      synchronized(AuthPolicy.class){}

      List var0;
      try {
         var0 = (List)SCHEME_LIST.clone();
      } finally {
         ;
      }

      return var0;
   }

   public static void registerAuthScheme(String param0, Class param1) {
      // $FF: Couldn't be decompiled
   }

   public static void unregisterAuthScheme(String param0) {
      // $FF: Couldn't be decompiled
   }
}
