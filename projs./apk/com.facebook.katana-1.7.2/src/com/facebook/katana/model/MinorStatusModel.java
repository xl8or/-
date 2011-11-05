package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class MinorStatusModel extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "is_minor"
   )
   public final boolean isMinor = 0;


   private MinorStatusModel() {}
}
