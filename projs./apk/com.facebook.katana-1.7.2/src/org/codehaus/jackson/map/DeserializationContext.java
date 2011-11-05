package org.codehaus.jackson.map;

import java.util.Calendar;
import java.util.Date;
import org.codehaus.jackson.Base64Variant;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.util.ArrayBuilders;
import org.codehaus.jackson.map.util.ObjectBuffer;

public abstract class DeserializationContext {

   protected final DeserializationConfig _config;


   protected DeserializationContext(DeserializationConfig var1) {
      this._config = var1;
   }

   public abstract Calendar constructCalendar(Date var1);

   public abstract ArrayBuilders getArrayBuilders();

   public Base64Variant getBase64Variant() {
      return this._config.getBase64Variant();
   }

   public DeserializationConfig getConfig() {
      return this._config;
   }

   public abstract JsonParser getParser();

   public abstract JsonMappingException instantiationException(Class<?> var1, Exception var2);

   public boolean isEnabled(DeserializationConfig.Feature var1) {
      return this._config.isEnabled(var1);
   }

   public abstract ObjectBuffer leaseObjectBuffer();

   public abstract JsonMappingException mappingException(Class<?> var1);

   public abstract Date parseDate(String var1) throws IllegalArgumentException;

   public abstract void returnObjectBuffer(ObjectBuffer var1);

   public abstract JsonMappingException unknownFieldException(Object var1, String var2);

   public abstract JsonMappingException weirdKeyException(Class<?> var1, String var2, String var3);

   public abstract JsonMappingException weirdNumberException(Class<?> var1, String var2);

   public abstract JsonMappingException weirdStringException(Class<?> var1, String var2);
}
