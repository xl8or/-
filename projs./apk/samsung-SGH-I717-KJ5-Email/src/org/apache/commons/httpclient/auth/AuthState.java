package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScheme;

public class AuthState {

   public static final String PREEMPTIVE_AUTH_SCHEME = "basic";
   private boolean authAttempted = 0;
   private boolean authRequested = 0;
   private AuthScheme authScheme = null;
   private boolean preemptive = 0;


   public AuthState() {}

   public AuthScheme getAuthScheme() {
      return this.authScheme;
   }

   public String getRealm() {
      String var1;
      if(this.authScheme != null) {
         var1 = this.authScheme.getRealm();
      } else {
         var1 = null;
      }

      return var1;
   }

   public void invalidate() {
      this.authScheme = null;
      this.authRequested = (boolean)0;
      this.authAttempted = (boolean)0;
      this.preemptive = (boolean)0;
   }

   public boolean isAuthAttempted() {
      return this.authAttempted;
   }

   public boolean isAuthRequested() {
      return this.authRequested;
   }

   public boolean isPreemptive() {
      return this.preemptive;
   }

   public void setAuthAttempted(boolean var1) {
      this.authAttempted = var1;
   }

   public void setAuthRequested(boolean var1) {
      this.authRequested = var1;
   }

   public void setAuthScheme(AuthScheme var1) {
      if(var1 == null) {
         this.invalidate();
      } else {
         if(this.preemptive && !this.authScheme.getClass().isInstance(var1)) {
            this.preemptive = (boolean)0;
            this.authAttempted = (boolean)0;
         }

         this.authScheme = var1;
      }
   }

   public void setPreemptive() {
      if(!this.preemptive) {
         if(this.authScheme != null) {
            throw new IllegalStateException("Authentication state already initialized");
         } else {
            AuthScheme var1 = AuthPolicy.getAuthScheme("basic");
            this.authScheme = var1;
            this.preemptive = (boolean)1;
         }
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("Auth state: auth requested [");
      boolean var3 = this.authRequested;
      var1.append(var3);
      StringBuffer var5 = var1.append("]; auth attempted [");
      boolean var6 = this.authAttempted;
      var1.append(var6);
      if(this.authScheme != null) {
         StringBuffer var8 = var1.append("]; auth scheme [");
         String var9 = this.authScheme.getSchemeName();
         var1.append(var9);
         StringBuffer var11 = var1.append("]; realm [");
         String var12 = this.authScheme.getRealm();
         var1.append(var12);
      }

      StringBuffer var14 = var1.append("] preemptive [");
      boolean var15 = this.preemptive;
      var1.append(var15);
      StringBuffer var17 = var1.append("]");
      return var1.toString();
   }
}
