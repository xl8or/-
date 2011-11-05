package org.apache.commons.httpclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpState {

   private static final Log LOG = LogFactory.getLog(HttpState.class);
   public static final String PREEMPTIVE_DEFAULT = "false";
   public static final String PREEMPTIVE_PROPERTY = "httpclient.authentication.preemptive";
   private int cookiePolicy;
   protected ArrayList cookies;
   protected HashMap credMap;
   private boolean preemptive;
   protected HashMap proxyCred;


   public HttpState() {
      HashMap var1 = new HashMap();
      this.credMap = var1;
      HashMap var2 = new HashMap();
      this.proxyCred = var2;
      ArrayList var3 = new ArrayList();
      this.cookies = var3;
      this.preemptive = (boolean)0;
      this.cookiePolicy = -1;
   }

   private static String getCookiesStringRepresentation(List var0) {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Cookie var3 = (Cookie)var2.next();
         if(var1.length() > 0) {
            StringBuffer var4 = var1.append("#");
         }

         String var5 = var3.toExternalForm();
         var1.append(var5);
      }

      return var1.toString();
   }

   private static String getCredentialsStringRepresentation(Map var0) {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = var0.keySet().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         Credentials var4 = (Credentials)var0.get(var3);
         if(var1.length() > 0) {
            StringBuffer var5 = var1.append(", ");
         }

         var1.append(var3);
         StringBuffer var7 = var1.append("#");
         String var8 = var4.toString();
         var1.append(var8);
      }

      return var1.toString();
   }

   private static Credentials matchCredentials(HashMap var0, AuthScope var1) {
      Credentials var2 = (Credentials)var0.get(var1);
      if(var2 == null) {
         int var3 = -1;
         AuthScope var4 = null;
         Iterator var5 = var0.keySet().iterator();

         while(var5.hasNext()) {
            AuthScope var6 = (AuthScope)var5.next();
            int var7 = var1.match(var6);
            if(var7 > var3) {
               var3 = var7;
               var4 = var6;
            }
         }

         if(var4 != null) {
            var2 = (Credentials)var0.get(var4);
         }
      }

      return var2;
   }

   public void addCookie(Cookie var1) {
      synchronized(this){}

      try {
         LOG.trace("enter HttpState.addCookie(Cookie)");
         if(var1 != null) {
            Iterator var2 = this.cookies.iterator();

            while(var2.hasNext()) {
               Cookie var3 = (Cookie)var2.next();
               if(var1.equals(var3)) {
                  var2.remove();
                  break;
               }
            }

            if(!var1.isExpired()) {
               this.cookies.add(var1);
            }
         }
      } finally {
         ;
      }

   }

   public void addCookies(Cookie[] param1) {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      this.clearCookies();
      this.clearCredentials();
      this.clearProxyCredentials();
   }

   public void clearCookies() {
      synchronized(this){}

      try {
         this.cookies.clear();
      } finally {
         ;
      }

   }

   public void clearCredentials() {
      this.credMap.clear();
   }

   public void clearProxyCredentials() {
      this.proxyCred.clear();
   }

   public int getCookiePolicy() {
      return this.cookiePolicy;
   }

   public Cookie[] getCookies() {
      synchronized(this){}

      Cookie[] var3;
      try {
         LOG.trace("enter HttpState.getCookies()");
         ArrayList var1 = this.cookies;
         Cookie[] var2 = new Cookie[this.cookies.size()];
         var3 = (Cookie[])((Cookie[])var1.toArray(var2));
      } finally {
         ;
      }

      return var3;
   }

   public Cookie[] getCookies(String var1, int var2, String var3, boolean var4) {
      synchronized(this){}

      try {
         LOG.trace("enter HttpState.getCookies(String, int, String, boolean)");
         CookieSpec var5 = CookiePolicy.getDefaultSpec();
         int var6 = this.cookies.size();
         ArrayList var7 = new ArrayList(var6);
         int var8 = 0;

         for(int var9 = this.cookies.size(); var8 < var9; ++var8) {
            Cookie var10 = (Cookie)((Cookie)this.cookies.get(var8));
            if(var5.match(var1, var2, var3, var4, var10)) {
               var7.add(var10);
            }
         }

         Cookie[] var16 = new Cookie[var7.size()];
         Cookie[] var17 = (Cookie[])((Cookie[])var7.toArray(var16));
         return var17;
      } finally {
         ;
      }
   }

   public Credentials getCredentials(String var1, String var2) {
      synchronized(this){}
      boolean var10 = false;

      Credentials var6;
      try {
         var10 = true;
         LOG.trace("enter HttpState.getCredentials(String, String");
         HashMap var3 = this.credMap;
         String var4 = AuthScope.ANY_SCHEME;
         AuthScope var5 = new AuthScope(var2, -1, var1, var4);
         var6 = matchCredentials(var3, var5);
         var10 = false;
      } finally {
         if(var10) {
            ;
         }
      }

      return var6;
   }

   public Credentials getCredentials(AuthScope param1) {
      // $FF: Couldn't be decompiled
   }

   public Credentials getProxyCredentials(String var1, String var2) {
      synchronized(this){}
      boolean var10 = false;

      Credentials var6;
      try {
         var10 = true;
         LOG.trace("enter HttpState.getCredentials(String, String");
         HashMap var3 = this.proxyCred;
         String var4 = AuthScope.ANY_SCHEME;
         AuthScope var5 = new AuthScope(var2, -1, var1, var4);
         var6 = matchCredentials(var3, var5);
         var10 = false;
      } finally {
         if(var10) {
            ;
         }
      }

      return var6;
   }

   public Credentials getProxyCredentials(AuthScope param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isAuthenticationPreemptive() {
      return this.preemptive;
   }

   public boolean purgeExpiredCookies() {
      synchronized(this){}
      boolean var6 = false;

      boolean var2;
      try {
         var6 = true;
         LOG.trace("enter HttpState.purgeExpiredCookies()");
         Date var1 = new Date();
         var2 = this.purgeExpiredCookies(var1);
         var6 = false;
      } finally {
         if(var6) {
            ;
         }
      }

      return var2;
   }

   public boolean purgeExpiredCookies(Date param1) {
      // $FF: Couldn't be decompiled
   }

   public void setAuthenticationPreemptive(boolean var1) {
      this.preemptive = var1;
   }

   public void setCookiePolicy(int var1) {
      this.cookiePolicy = var1;
   }

   public void setCredentials(String var1, String var2, Credentials var3) {
      synchronized(this){}

      try {
         LOG.trace("enter HttpState.setCredentials(String, String, Credentials)");
         HashMap var4 = this.credMap;
         String var5 = AuthScope.ANY_SCHEME;
         AuthScope var6 = new AuthScope(var2, -1, var1, var5);
         var4.put(var6, var3);
      } finally {
         ;
      }

   }

   public void setCredentials(AuthScope param1, Credentials param2) {
      // $FF: Couldn't be decompiled
   }

   public void setProxyCredentials(String var1, String var2, Credentials var3) {
      synchronized(this){}

      try {
         LOG.trace("enter HttpState.setProxyCredentials(String, String, Credentials");
         HashMap var4 = this.proxyCred;
         String var5 = AuthScope.ANY_SCHEME;
         AuthScope var6 = new AuthScope(var2, -1, var1, var5);
         var4.put(var6, var3);
      } finally {
         ;
      }

   }

   public void setProxyCredentials(AuthScope param1, Credentials param2) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      synchronized(this){}
      boolean var16 = false;

      String var12;
      try {
         var16 = true;
         StringBuffer var1 = new StringBuffer();
         StringBuffer var2 = var1.append("[");
         String var3 = getCredentialsStringRepresentation(this.proxyCred);
         var1.append(var3);
         StringBuffer var5 = var1.append(" | ");
         String var6 = getCredentialsStringRepresentation(this.credMap);
         var1.append(var6);
         StringBuffer var8 = var1.append(" | ");
         String var9 = getCookiesStringRepresentation(this.cookies);
         var1.append(var9);
         StringBuffer var11 = var1.append("]");
         var12 = var1.toString();
         var16 = false;
      } finally {
         if(var16) {
            ;
         }
      }

      return var12;
   }
}
