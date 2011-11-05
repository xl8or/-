package org.apache.commons.httpclient.cookie;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.cookie.CookieSpecBase;
import org.apache.commons.httpclient.cookie.IgnoreCookiesSpec;
import org.apache.commons.httpclient.cookie.NetscapeDraftSpec;
import org.apache.commons.httpclient.cookie.RFC2109Spec;
import org.apache.commons.httpclient.cookie.RFC2965Spec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CookiePolicy {

   public static final String BROWSER_COMPATIBILITY = "compatibility";
   public static final int COMPATIBILITY = 0;
   public static final String DEFAULT = "default";
   public static final String IGNORE_COOKIES = "ignoreCookies";
   protected static final Log LOG;
   public static final String NETSCAPE = "netscape";
   public static final int NETSCAPE_DRAFT = 1;
   public static final int RFC2109 = 2;
   public static final int RFC2965 = 3;
   public static final String RFC_2109 = "rfc2109";
   public static final String RFC_2965 = "rfc2965";
   private static Map SPECS = Collections.synchronizedMap(new HashMap());
   private static int defaultPolicy;


   static {
      registerCookieSpec("default", RFC2109Spec.class);
      registerCookieSpec("rfc2109", RFC2109Spec.class);
      registerCookieSpec("rfc2965", RFC2965Spec.class);
      registerCookieSpec("compatibility", CookieSpecBase.class);
      registerCookieSpec("netscape", NetscapeDraftSpec.class);
      registerCookieSpec("ignoreCookies", IgnoreCookiesSpec.class);
      defaultPolicy = 2;
      LOG = LogFactory.getLog(CookiePolicy.class);
   }

   public CookiePolicy() {}

   public static CookieSpec getCompatibilitySpec() {
      return getSpecByPolicy(0);
   }

   public static CookieSpec getCookieSpec(String var0) throws IllegalStateException {
      if(var0 == null) {
         throw new IllegalArgumentException("Id may not be null");
      } else {
         Map var1 = SPECS;
         String var2 = var0.toLowerCase();
         Class var3 = (Class)var1.get(var2);
         if(var3 != null) {
            try {
               CookieSpec var12 = (CookieSpec)var3.newInstance();
               return var12;
            } catch (Exception var11) {
               Log var5 = LOG;
               String var6 = "Error initializing cookie spec: " + var0;
               var5.error(var6, var11);
               StringBuilder var7 = (new StringBuilder()).append(var0).append(" cookie spec implemented by ");
               String var8 = var3.getName();
               String var9 = var7.append(var8).append(" could not be initialized").toString();
               throw new IllegalStateException(var9);
            }
         } else {
            String var10 = "Unsupported cookie spec " + var0;
            throw new IllegalStateException(var10);
         }
      }
   }

   public static int getDefaultPolicy() {
      return defaultPolicy;
   }

   public static CookieSpec getDefaultSpec() {
      CookieSpec var0;
      Object var1;
      try {
         var0 = getCookieSpec("default");
      } catch (IllegalStateException var3) {
         LOG.warn("Default cookie policy is not registered");
         var1 = new RFC2109Spec();
         return (CookieSpec)var1;
      }

      var1 = var0;
      return (CookieSpec)var1;
   }

   public static String[] getRegisteredCookieSpecs() {
      Set var0 = SPECS.keySet();
      String[] var1 = new String[SPECS.size()];
      return (String[])((String[])var0.toArray(var1));
   }

   public static CookieSpec getSpecByPolicy(int var0) {
      Object var1;
      switch(var0) {
      case 0:
         var1 = new CookieSpecBase();
         break;
      case 1:
         var1 = new NetscapeDraftSpec();
         break;
      case 2:
         var1 = new RFC2109Spec();
         break;
      case 3:
         var1 = new RFC2965Spec();
         break;
      default:
         var1 = getDefaultSpec();
      }

      return (CookieSpec)var1;
   }

   public static CookieSpec getSpecByVersion(int var0) {
      Object var1;
      switch(var0) {
      case 0:
         var1 = new NetscapeDraftSpec();
         break;
      case 1:
         var1 = new RFC2109Spec();
         break;
      default:
         var1 = getDefaultSpec();
      }

      return (CookieSpec)var1;
   }

   public static void registerCookieSpec(String var0, Class var1) {
      if(var0 == null) {
         throw new IllegalArgumentException("Id may not be null");
      } else if(var1 == null) {
         throw new IllegalArgumentException("Cookie spec class may not be null");
      } else {
         Map var2 = SPECS;
         String var3 = var0.toLowerCase();
         var2.put(var3, var1);
      }
   }

   public static void setDefaultPolicy(int var0) {
      defaultPolicy = var0;
   }

   public static void unregisterCookieSpec(String var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("Id may not be null");
      } else {
         Map var1 = SPECS;
         String var2 = var0.toLowerCase();
         var1.remove(var2);
      }
   }
}
