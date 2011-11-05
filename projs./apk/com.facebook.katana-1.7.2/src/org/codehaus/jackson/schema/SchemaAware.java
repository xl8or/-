package org.codehaus.jackson.schema;

import java.lang.reflect.Type;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;

public interface SchemaAware {

   JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException;
}
