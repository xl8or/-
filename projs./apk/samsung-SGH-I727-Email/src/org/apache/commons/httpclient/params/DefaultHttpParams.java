package org.apache.commons.httpclient.params;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.httpclient.params.DefaultHttpParamsFactory;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.httpclient.params.HttpParamsFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultHttpParams implements HttpParams, Serializable, Cloneable {

   private static final Log LOG = LogFactory.getLog(DefaultHttpParams.class);
   private static HttpParamsFactory httpParamsFactory = new DefaultHttpParamsFactory();
   private HttpParams defaults;
   private HashMap parameters;


   public DefaultHttpParams() {
      HttpParams var1 = getDefaultParams();
      this(var1);
   }

   public DefaultHttpParams(HttpParams var1) {
      this.defaults = null;
      this.parameters = null;
      this.defaults = var1;
   }

   public static HttpParams getDefaultParams() {
      return httpParamsFactory.getDefaultParams();
   }

   public static void setHttpParamsFactory(HttpParamsFactory var0) {
      if(var0 == null) {
         throw new IllegalArgumentException("httpParamsFactory may not be null");
      } else {
         httpParamsFactory = var0;
      }
   }

   public void clear() {
      this.parameters = null;
   }

   public Object clone() throws CloneNotSupportedException {
      DefaultHttpParams var1 = (DefaultHttpParams)super.clone();
      if(this.parameters != null) {
         HashMap var2 = (HashMap)this.parameters.clone();
         var1.parameters = var2;
      }

      HttpParams var3 = this.defaults;
      var1.setDefaults(var3);
      return var1;
   }

   public boolean getBooleanParameter(String var1, boolean var2) {
      Object var3 = this.getParameter(var1);
      boolean var4;
      if(var3 == null) {
         var4 = var2;
      } else {
         var4 = ((Boolean)var3).booleanValue();
      }

      return var4;
   }

   public HttpParams getDefaults() {
      synchronized(this){}

      HttpParams var1;
      try {
         var1 = this.defaults;
      } finally {
         ;
      }

      return var1;
   }

   public double getDoubleParameter(String var1, double var2) {
      Object var4 = this.getParameter(var1);
      double var5;
      if(var4 == null) {
         var5 = var2;
      } else {
         var5 = ((Double)var4).doubleValue();
      }

      return var5;
   }

   public int getIntParameter(String var1, int var2) {
      Object var3 = this.getParameter(var1);
      int var4;
      if(var3 == null) {
         var4 = var2;
      } else {
         var4 = ((Integer)var3).intValue();
      }

      return var4;
   }

   public long getLongParameter(String var1, long var2) {
      Object var4 = this.getParameter(var1);
      long var5;
      if(var4 == null) {
         var5 = var2;
      } else {
         var5 = ((Long)var4).longValue();
      }

      return var5;
   }

   public Object getParameter(String param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isParameterFalse(String var1) {
      boolean var2;
      if(!this.getBooleanParameter(var1, (boolean)0)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isParameterSet(String var1) {
      boolean var2;
      if(this.getParameter(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isParameterSetLocally(String var1) {
      boolean var2;
      if(this.parameters != null && this.parameters.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isParameterTrue(String var1) {
      return this.getBooleanParameter(var1, (boolean)0);
   }

   public void setBooleanParameter(String var1, boolean var2) {
      Boolean var3;
      if(var2) {
         var3 = Boolean.TRUE;
      } else {
         var3 = Boolean.FALSE;
      }

      this.setParameter(var1, var3);
   }

   public void setDefaults(HttpParams var1) {
      synchronized(this){}

      try {
         this.defaults = var1;
      } finally {
         ;
      }

   }

   public void setDoubleParameter(String var1, double var2) {
      Double var4 = Double.valueOf(var2);
      this.setParameter(var1, var4);
   }

   public void setIntParameter(String var1, int var2) {
      Integer var3 = Integer.valueOf(var2);
      this.setParameter(var1, var3);
   }

   public void setLongParameter(String var1, long var2) {
      Long var4 = Long.valueOf(var2);
      this.setParameter(var1, var4);
   }

   public void setParameter(String var1, Object var2) {
      synchronized(this){}

      try {
         if(this.parameters == null) {
            HashMap var3 = new HashMap();
            this.parameters = var3;
         }

         this.parameters.put(var1, var2);
         if(LOG.isDebugEnabled()) {
            Log var5 = LOG;
            String var6 = "Set parameter " + var1 + " = " + var2;
            var5.debug(var6);
         }
      } finally {
         ;
      }

   }

   public void setParameters(String[] var1, Object var2) {
      synchronized(this){}
      int var3 = 0;

      while(true) {
         boolean var8 = false;

         try {
            var8 = true;
            int var4 = var1.length;
            if(var3 >= var4) {
               var8 = false;
               return;
            }

            String var5 = var1[var3];
            this.setParameter(var5, var2);
            var8 = false;
         } finally {
            if(var8) {
               ;
            }
         }

         ++var3;
      }
   }
}
