package com.facebook.katana.util.jsonmirror.types;

import com.facebook.katana.util.jsonmirror.JMFatalException;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import com.facebook.katana.util.jsonmirror.types.JMList;
import com.facebook.katana.util.jsonmirror.types.JMSimpleDict;
import org.codehaus.jackson.JsonFactory;

public class JMEscaped extends JMBase {

   public final JsonFactory mFactory;
   public final JMBase mInnerObject;


   public JMEscaped(JMBase var1, JsonFactory var2) {
      if(!(var1 instanceof JMDict) && !(var1 instanceof JMSimpleDict) && !(var1 instanceof JMList)) {
         throw new JMFatalException("stringified values can only encapsulate complex types.");
      } else {
         this.mInnerObject = var1;
         this.mFactory = var2;
      }
   }
}
