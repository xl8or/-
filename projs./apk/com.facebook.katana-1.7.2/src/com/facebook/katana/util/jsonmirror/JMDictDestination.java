package com.facebook.katana.util.jsonmirror;

import com.facebook.katana.util.jsonmirror.JMException;
import java.util.List;
import java.util.Map;

public abstract class JMDictDestination {

   public JMDictDestination() {}

   protected void postprocess() throws JMException {}

   protected abstract void setBoolean(String var1, boolean var2) throws JMException;

   protected abstract void setDictionary(String var1, JMDictDestination var2) throws JMException;

   protected abstract void setDouble(String var1, double var2) throws JMException;

   protected abstract void setList(String var1, List<Object> var2) throws JMException;

   protected abstract void setLong(String var1, long var2) throws JMException;

   protected abstract void setSimpleDictionary(String var1, Map<String, Object> var2) throws JMException;

   protected abstract void setString(String var1, String var2) throws JMException;
}
