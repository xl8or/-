package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class GraphRequestResponse extends JMCachingDictDestination {

   public static final int INVALID_ID = 255;
   @JMAutogen.InferredType(
      jsonFieldName = "body"
   )
   public final String body = null;
   @JMAutogen.InferredType(
      jsonFieldName = "code"
   )
   public final int code = -1;


   private GraphRequestResponse() {}
}
