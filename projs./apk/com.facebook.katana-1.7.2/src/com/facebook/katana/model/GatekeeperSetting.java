package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class GatekeeperSetting extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "result"
   )
   public final boolean mEnabled = 0;
   @JMAutogen.InferredType(
      jsonFieldName = "project_name"
   )
   public final String mProjectName = null;


   private GatekeeperSetting() {}
}
