package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class UserServerSetting extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "project"
   )
   public final String mProjectName = null;
   @JMAutogen.InferredType(
      jsonFieldName = "setting"
   )
   public final String mSettingName = null;
   @JMAutogen.InferredType(
      jsonFieldName = "value"
   )
   public final String mValue = null;


   private UserServerSetting() {}
}
