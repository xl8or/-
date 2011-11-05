package com.htc.android.mail.pim;

import java.util.Collection;

public interface VBuilder {

   void end();

   void endProperty();

   void endRecord();

   void propertyName(String var1);

   void propertyParamType(String var1);

   void propertyParamValue(String var1);

   void propertyValues(Collection<String> var1);

   void start();

   void startProperty();

   void startRecord(String var1);
}
