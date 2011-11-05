package org.apache.commons.httpclient.params;


public interface HttpParams {

   boolean getBooleanParameter(String var1, boolean var2);

   HttpParams getDefaults();

   double getDoubleParameter(String var1, double var2);

   int getIntParameter(String var1, int var2);

   long getLongParameter(String var1, long var2);

   Object getParameter(String var1);

   boolean isParameterFalse(String var1);

   boolean isParameterSet(String var1);

   boolean isParameterSetLocally(String var1);

   boolean isParameterTrue(String var1);

   void setBooleanParameter(String var1, boolean var2);

   void setDefaults(HttpParams var1);

   void setDoubleParameter(String var1, double var2);

   void setIntParameter(String var1, int var2);

   void setLongParameter(String var1, long var2);

   void setParameter(String var1, Object var2);
}
