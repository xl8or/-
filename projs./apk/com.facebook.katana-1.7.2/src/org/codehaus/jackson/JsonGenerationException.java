package org.codehaus.jackson;

import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonProcessingException;

public class JsonGenerationException extends JsonProcessingException {

   static final long serialVersionUID = 123L;


   public JsonGenerationException(String var1) {
      JsonLocation var2 = (JsonLocation)false;
      super(var1, var2);
   }

   public JsonGenerationException(String var1, Throwable var2) {
      JsonLocation var3 = (JsonLocation)false;
      super(var1, var3, var2);
   }

   public JsonGenerationException(Throwable var1) {
      super(var1);
   }
}
